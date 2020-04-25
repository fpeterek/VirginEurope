
-- Function 4.5

SELECT operated_flight.flight_id, ft.class, COUNT(ft.passenger_id)
FROM operated_flight
JOIN flight_ticket ft on operated_flight.operated_id = ft.operated_id
WHERE flight_id = $flightId
GROUP BY flight_id, ft.class
ORDER BY flight_id, class;

-- Function 4.9

SELECT * FROM operated_flight
JOIN flight ON operated_flight.flight_id = flight.flight_id JOIN route ON flight.route_id = route.route_id
JOIN aircraft_model ON
flight.aircraft_model_designator = aircraft_model.designator WHERE
aircraft_model.family=$family AND route.origin=$origin AND operated_flight.date=$date

-- Function 7.1 - Assign pilots on flight

CREATE OR REPLACE FUNCTION AssignPilots(int) RETURNS boolean
LANGUAGE plpgsql
AS $$
DECLARE
    fl_date date;
    oper_id int := $1;
    ac_type varchar;
    ac_family varchar;
    fl_duration int;
    no_pilots int;
BEGIN

    -- Get flight date
    SELECT operated_flight.date INTO fl_date FROM operated_flight
    JOIN flight ON operated_flight.flight_id = flight.flight_id
    WHERE operated_flight.operated_id = oper_id;

    -- Get aircraft info
    SELECT aircraft_model.designator, aircraft_model.family INTO ac_type, ac_family
    FROM operated_flight
    JOIN aircraft ON aircraft.identifier = operated_flight.aircraft_identifier
    JOIN aircraft_model ON aircraft_model.designator = aircraft.aircraft_model_designator
    WHERE operated_flight.operated_id = oper_id;

    -- Estimate flight duration
    SELECT route.distance / 450 INTO fl_duration FROM operated_flight
    JOIN flight ON flight.flight_id = operated_flight.flight_id
    JOIN route ON route.route_id = flight.route_id
    WHERE operated_flight.operated_id = oper_id;

    -- Number of pilots required on flight
    IF (fl_duration > 12 OR ac_family = 'CONC') THEN
        no_pilots := 3;
    ELSE
        no_pilots := 2;
    END IF;

    -- Select and insert one captain
    -- Fetch pilots capable of flying desired aircraft, order them by date of last flight
    INSERT INTO pilot_on_flight(pilot_id, operated_id)
    SELECT pilot.pilot_id, oper_id FROM pilot
    JOIN pilot_on_flight ON pilot.pilot_id = pilot_on_flight.pilot_id
    JOIN operated_flight ON operated_flight.operated_id = pilot_on_flight.operated_id
    WHERE
        pilot.is_captain AND
        pilot.certification = ac_family AND
        operated_flight.date < fl_date
    GROUP BY pilot.pilot_id
    ORDER BY MAX(operated_flight.date)
    LIMIT 1;

    -- Select and insert one or two first offices
    INSERT INTO pilot_on_flight
    SELECT pilot.pilot_id, oper_id FROM pilot
    JOIN pilot_on_flight ON pilot.pilot_id = pilot_on_flight.pilot_id
    JOIN operated_flight ON operated_flight.operated_id = pilot_on_flight.operated_id
    WHERE
        not pilot.is_captain AND
        pilot.certification = ac_family AND
        operated_flight.date < fl_date
    GROUP BY pilot.pilot_id
    ORDER BY MAX(operated_flight.date)
    LIMIT (no_pilots-1);

    RETURN true;

    EXCEPTION
    WHEN OTHERS THEN
        RAISE INFO 'Failed to assign pilots with error %', SQLERRM;
        ROLLBACK;
        RETURN false;

END;
$$;

-- Function 5.4 - flight cancellation

-- This function differs from the specification a bit
-- Mainly because PL/pgSQL doesn't support Table variables
-- and I needed to change it up a bit
CREATE OR REPLACE FUNCTION cancelFlight(int) RETURNS boolean
LANGUAGE plpgsql
AS $$
DECLARE
    oper_id int := $1;
    fl_date date;
    fl_orig char(6);
    fl_dest char(6);

    pax_in_business int;
    pax_in_economy int;
    pax_in_first int;

    economy_seats_total int;
    business_seats_total int;
    first_seats_total int;
    taken_eco_seats int;
    taken_bus_seats int;
    taken_fst_seats int;

    empty_eco_seats int;
    empty_bus_seats int;
    empty_fst_seats int;

    moved_eco_pax int;
    moved_bus_pax int;
    moved_fst_pax int;

    other_flights refcursor;
    fl record;

BEGIN

    -- Fetch flight info
    SELECT operated_flight.date, route.origin, route.destination
    INTO fl_date, fl_orig, fl_dest
    FROM operated_flight
    JOIN flight ON operated_flight.flight_id = flight.flight_id
    JOIN route ON route.route_id = flight.route_id
    WHERE operated_flight.operated_id = oper_id;

    -- Fetch number of passengers in each class

    SELECT COALESCE(COUNT(1), 0) INTO pax_in_business FROM flight_ticket
    WHERE operated_id = oper_id AND flight_ticket.class= 'business';

    SELECT COALESCE(COUNT(1), 0) INTO pax_in_first FROM flight_ticket
    WHERE operated_id = oper_id AND flight_ticket.class= 'first';

    SELECT COALESCE(COUNT(1), 0) INTO pax_in_economy FROM flight_ticket
    WHERE operated_id = oper_id AND flight_ticket.class= 'economy';

    RAISE INFO 'Total passengers on cancelled flight per class: %, %, %', pax_in_economy, pax_in_business, pax_in_first;

    OPEN other_flights FOR SELECT * FROM operated_flight
        JOIN flight ON flight.flight_id = operated_flight.flight_id
        JOIN route ON flight.route_id = route.route_id
        WHERE operated_id != oper_id AND
              route.origin = fl_orig AND
              route.destination = fl_dest AND
              operated_flight.date >= fl_date
        ORDER BY operated_flight.date;

    LOOP

        FETCH other_flights INTO fl;
        EXIT WHEN NOT FOUND;

        IF pax_in_business <= 0 AND pax_in_first <= 0 AND pax_in_economy <= 0 THEN
            EXIT;
        END IF;

        RAISE info 'Moving to flight %', fl.operated_id;

        -- Fetch aircraft info
        SELECT a.economy_seats, a.business_seats, a.first_seats
        INTO economy_seats_total, business_seats_total, first_seats_total
        FROM operated_flight
        JOIN aircraft a on operated_flight.aircraft_identifier = a.identifier
        WHERE operated_id = fl.operated_id;

        -- Calculate empty seats

        SELECT COALESCE(COUNT(1), 0) INTO taken_eco_seats FROM flight_ticket
        JOIN operated_flight ON flight_ticket.operated_id = operated_flight.operated_id
        WHERE operated_flight.operated_id = fl.operated_id AND flight_ticket.class = 'economy';

        SELECT COALESCE(COUNT(1), 0) INTO taken_bus_seats FROM flight_ticket
        JOIN operated_flight ON flight_ticket.operated_id = operated_flight.operated_id
        WHERE operated_flight.operated_id = fl.operated_id AND flight_ticket.class = 'business';

        SELECT COALESCE(COUNT(1), 0) INTO taken_fst_seats FROM flight_ticket
        JOIN operated_flight ON flight_ticket.operated_id = operated_flight.operated_id
        WHERE operated_flight.operated_id = fl.operated_id AND flight_ticket.class = 'first';

        RAISE INFO 'Taken seats on flight per class: %, %, %', taken_eco_seats, taken_bus_seats, taken_fst_seats;

        empty_eco_seats = economy_seats_total - taken_eco_seats;
        empty_bus_seats = business_seats_total - taken_bus_seats;
        empty_fst_seats = first_seats_total - taken_fst_seats;

        RAISE INFO 'Empty seats on flight per class: %, %, %', empty_eco_seats, empty_bus_seats, empty_fst_seats;

        -- First, calculate how many passengers we will be moving

        SELECT coalesce(count(passenger_id), 0) INTO moved_eco_pax
        FROM flight_ticket
        WHERE class='economy' AND
              flight_ticket.operated_id=oper_id AND
              passenger_id NOT IN ( -- We shouldn't put a passenger on the same flight twice, though
                  SELECT newfl.passenger_id FROM flight_ticket newfl
                  WHERE newfl.operated_id=fl.operated_id
              );

        SELECT coalesce(count(passenger_id), 0) INTO moved_bus_pax
        FROM flight_ticket
        WHERE class='business' AND
              flight_ticket.operated_id=oper_id AND
              passenger_id NOT IN ( -- We shouldn't put a passenger on the same flight twice, though
                  SELECT newfl.passenger_id FROM flight_ticket newfl
                  WHERE newfl.operated_id=fl.operated_id
              );

        SELECT coalesce(count(passenger_id), 0) INTO moved_fst_pax
        FROM flight_ticket
        WHERE class='first' AND
              flight_ticket.operated_id=oper_id AND
              passenger_id NOT IN ( -- We shouldn't put a passenger on the same flight twice, though
                  SELECT newfl.passenger_id FROM flight_ticket newfl
                  WHERE newfl.operated_id=fl.operated_id
              );

        moved_eco_pax := LEAST(moved_eco_pax, empty_eco_seats);
        moved_bus_pax := LEAST(moved_bus_pax, empty_bus_seats);
        moved_fst_pax := LEAST(moved_fst_pax, empty_fst_seats);

        pax_in_economy := pax_in_economy - moved_eco_pax;
        pax_in_business := pax_in_business - moved_bus_pax;
        pax_in_first := pax_in_first - moved_fst_pax;

        -- Now move the passengers from their old flight onto the new one

        -- We also want to reset the seat as we don't want multiple passengers to fight over one seat
        UPDATE flight_ticket SET operated_id=fl.operated_id, seat=''
        WHERE operated_id=oper_id AND passenger_id IN (
            SELECT passenger_id
            FROM flight_ticket
            WHERE class='economy' AND
                  flight_ticket.operated_id=oper_id AND
                  passenger_id NOT IN ( -- We shouldn't put a passenger on the same flight twice, though
                      SELECT newfl.passenger_id FROM flight_ticket newfl
                      WHERE newfl.operated_id=fl.operated_id
                  )
            ORDER BY passenger_id LIMIT empty_eco_seats
        );

        -- We also want to reset the seat as we don't want multiple passengers to fight over one seat
        UPDATE flight_ticket SET operated_id=fl.operated_id, seat=''
        WHERE operated_id=oper_id AND passenger_id IN (
            SELECT passenger_id
            FROM flight_ticket
            WHERE class='business' AND
                  flight_ticket.operated_id=oper_id AND
                  passenger_id NOT IN ( -- We shouldn't put a passenger on the same flight twice, though
                      SELECT newfl.passenger_id FROM flight_ticket newfl
                      WHERE newfl.operated_id=fl.operated_id
                  )
            ORDER BY passenger_id LIMIT empty_bus_seats
        );

        -- We also want to reset the seat as we don't want multiple passengers to fight over one seat
        UPDATE flight_ticket SET operated_id=fl.operated_id, seat=''
        WHERE operated_id=oper_id AND passenger_id IN (
            SELECT passenger_id
            FROM flight_ticket
            WHERE class='first' AND
                  flight_ticket.operated_id=oper_id AND
                  passenger_id NOT IN ( -- We shouldn't put a passenger on the same flight twice, though
                      SELECT newfl.passenger_id FROM flight_ticket newfl
                      WHERE newfl.operated_id=fl.operated_id
                  )
            ORDER BY passenger_id LIMIT empty_fst_seats
        );

        RAISE INFO 'Moved pax this round per class: %, %, %', moved_eco_pax, moved_bus_pax, moved_fst_pax;

    END LOOP;

    RAISE INFO 'Unmoved pax per class: %, %, %', pax_in_economy, pax_in_business, pax_in_first;
    IF pax_in_first > 0 OR pax_in_business > 0 OR pax_in_economy > 0 THEN
        RAISE EXCEPTION 'Passengers could not be moved due to a lack of suitable flights';
    END IF;

    RETURN true;

    EXCEPTION
    WHEN OTHERS THEN
        RAISE INFO 'Failed to move passengers on other flights, error: %', SQLERRM;
        ROLLBACK;
        RETURN false;
END;
$$;

-- Function 4.8 - Destination focused flight selection

CREATE OR REPLACE FUNCTION FindFlights(varchar, varchar) RETURNS varchar
LANGUAGE plpgsql
AS $$
DECLARE
    orig varchar := $1;
    dest varchar := $2;
    no_direct_flights int;
    fl flight;
    flights varchar := '[';
    json varchar := '{"first_flight":';
BEGIN

    SELECT count(1) INTO no_direct_flights FROM flight
    JOIN route ON flight.route_id = route.route_id
    WHERE route.origin = orig AND route.destination = dest;

    if no_direct_flights > 0 then

        for fl in
            SELECT flight.* FROM flight
            JOIN route ON flight.route_id = route.route_id
            WHERE route.origin = orig AND route.destination = dest
        loop
            flights := flights || '"' || fl.flight_id || '",';
        end loop;

        flights := trim(trailing ',' from flights) || ']';
        json := json || flights || '}';

    else

        -- Inbound flights to Kobeřice

        for fl in
            SELECT flight.* FROM flight
            JOIN route ON flight.route_id = route.route_id
            WHERE route.origin = orig AND route.destination = 'LKXB'
        loop
            flights := flights || '"' || fl.flight_id || '",';
        end loop;

        flights := trim(trailing ',' from flights) || ']';
        json := json || flights || ',';
        flights := '[';

        -- Outbound flights from Kobeřice

        for fl in
            SELECT flight.* FROM flight
            JOIN route ON flight.route_id = route.route_id
            WHERE route.origin = 'LKXB' AND route.destination = dest
        loop
            flights := flights || '"' || fl.flight_id || '",';
        end loop;

        flights := trim(trailing ',' from flights) || ']';
        json := json || '"second_flight":' || flights || '}';

    end if;

    return json;

END;
$$;

-- Function 1.4 - account deactivation

-- I've only now realized this won't really work as there could be more than
-- one passenger who decides to delete their account on the same flight
-- if that were to happen, setting two passenger_ids to 0 would break
-- database integrity
-- Solutions: introduce a different primary key (not one composed of multiple FKs)
-- or create 650 dummy passengers so we are able to fill an entire A380 with dummies
-- I don' have time to fix it now, however, I can fix it later

-- Update: DB Schema has been updated due to the aforementioned reason

CREATE OR REPLACE FUNCTION delete_passenger_trigger_fun() RETURNS trigger LANGUAGE plpgsql AS
$$
BEGIN
    UPDATE flight_ticket SET passenger_id=0 WHERE passenger_id=old.passenger_id;
    RETURN old;
END;
$$;

CREATE TRIGGER delete_passenger_trigger BEFORE DELETE ON passenger
FOR EACH ROW EXECUTE PROCEDURE delete_passenger_trigger_fun();

-- Function 2.7 - removing aircraft from fleet

-- I'm using ON DELETE SET NULL here, so this trigger isn't really necessary
-- If, however, by any chance, ON DELETE SET NULL is prohibited (just like ON DELETE CASCADE),
-- I can use this trigger instead

/*
CREATE OR REPLACE FUNCTION delete_aircraft_trigger_fun() RETURNS trigger LANGUAGE plpgsql AS
$$
BEGIN
    UPDATE operated_flight SET aircraft_identifier=null WHERE aircraft_identifier=old.identifier;
    RETURN old;
END;
$$;

CREATE TRIGGER delete_aircraft_trigger BEFORE DELETE ON aircraft
FOR EACH ROW EXECUTE PROCEDURE delete_aircraft_trigger_fun();
*/


-- Function 4.7 -- even if we do decide to remove a flight, we still want to keep
                -- information about previous performance of this flight, so we just
                -- the flight_id value to a dummy flight in the operated_flight table

CREATE OR REPLACE FUNCTION delete_flight_trigger_fun() RETURNS trigger LANGUAGE plpgsql AS
$$
BEGIN
    UPDATE operated_flight SET flight_id='VU0000' WHERE flight_id=old.flight_id;
    RETURN old;
END;
$$;

CREATE TRIGGER delete_flight_trigger BEFORE DELETE ON flight
FOR EACH ROW EXECUTE PROCEDURE delete_flight_trigger_fun();

-- Function 6.4 -- when removing pilots or flight attendants from database,
                -- we should also remove them from tables [crew|pilot]_on_flight

CREATE OR REPLACE FUNCTION delete_crew_trigger_fun() RETURNS trigger LANGUAGE plpgsql AS
$$
BEGIN
    DELETE FROM crew_on_flight WHERE crew_on_flight.crew_id=old.crew_id;
    RETURN old;
END;
$$;

CREATE TRIGGER delete_crew_trigger BEFORE DELETE ON crew
FOR EACH ROW EXECUTE PROCEDURE delete_crew_trigger_fun();

CREATE OR REPLACE FUNCTION delete_pilot_trigger_fun() RETURNS trigger LANGUAGE plpgsql AS
$$
BEGIN
    DELETE FROM pilot_on_flight WHERE pilot_on_flight.pilot_id=old.pilot_id;
    RETURN old;
END;
$$;

CREATE TRIGGER delete_pilot_trigger BEFORE DELETE ON pilot
FOR EACH ROW EXECUTE PROCEDURE delete_pilot_trigger_fun();

