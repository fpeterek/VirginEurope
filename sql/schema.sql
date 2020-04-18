CREATE TABLE aircraft (
    identifier                  VARCHAR(6) NOT NULL,
    engine                      VARCHAR(60) NOT NULL,
    economy_seats               INTEGER NOT NULL CHECK (economy_seats >= 0),
    business_seats              INTEGER NOT NULL CHECK (business_seats >= 0),
    first_seats                 INTEGER NOT NULL CHECK (first_seats >= 0),
    last_check                  DATE NOT NULL,
    aircraft_model_designator   CHAR(4) NOT NULL
);

ALTER TABLE aircraft ADD constraint aircraft_pk PRIMARY KEY (identifier);

CREATE TABLE aircraft_model (
    designator        CHAR(4) NOT NULL,
    manufacturer      VARCHAR(60) NOT NULL,
    family            VARCHAR(60) NOT NULL,
    full_type         VARCHAR(60) NOT NULL,
    etops_certified   VARCHAR(60) NOT NULL CHECK (etops_certified in ('yes', 'no', 'NA')),
    etops_rating      INTEGER NOT NULL CHECK (etops_rating >= 0),
    range_nmi         INTEGER NOT NULL CHECK (range_nmi >= 500),
    mtow              INTEGER NOT NULL CHECK (mtow >= 1000)
);

ALTER TABLE aircraft_model ADD constraint aircraft_model_pk PRIMARY KEY (designator);

CREATE TABLE airport (
    icao   CHAR(4) NOT NULL,
    iata   CHAR(3),
    name   VARCHAR(120) NOT NULL
);

ALTER TABLE airport ADD constraint airport_pk PRIMARY KEY (icao);

CREATE TABLE crew (
    crew_id      SERIAL NOT NULL,
    first_name   VARCHAR(60) NOT NULL,
    last_name    VARCHAR(60) NOT NULL,
    role         VARCHAR(60) NOT NULL CHECK (role in ('attendant', 'safety', 'leader')),
    seniority    VARCHAR(60) NOT NULL CHECK (seniority in ('junior', 'senior'))
);

ALTER TABLE crew ADD constraint crew_pk PRIMARY KEY (crew_id);

CREATE TABLE crew_on_flight (
    crew_id       INTEGER NOT NULL,
    operated_id   INTEGER NOT NULL
);

ALTER TABLE crew_on_flight ADD constraint crew_on_flight_pk PRIMARY KEY (crew_id, operated_id);

CREATE TABLE flight (
    flight_id                   VARCHAR(6) NOT NULL CHECK (flight_id LIKE 'VU%'),
    departure_time              time NOT NULL,
    arrival_time                time NOT NULL,
    aircraft_model_designator   CHAR(4),
    route_id                    INTEGER NOT NULL
);

ALTER TABLE flight ADD constraint flight_pk PRIMARY KEY (flight_id);

CREATE TABLE operated_flight (
    operated_id           SERIAL NOT NULL,
    actual_departure      time,
    actual_arrival        time,
    flight_id             VARCHAR(6) NOT NULL,
    aircraft_identifier   VARCHAR(6),
    date                  DATE NOT NULL
);

ALTER TABLE operated_flight ADD constraint operated_flight_pk PRIMARY KEY (operated_id);


CREATE TABLE passenger (
    passenger_id     SERIAL NOT NULL,
    first_name       VARCHAR(60) NOT NULL,
    last_name        VARCHAR(60) NOT NULL,
    preferred_meal   VARCHAR(60) CHECK (preferred_meal in ('default', 'vegetarian', 'vegan', 'halal', 'kosher')),
    preferred_seat   VARCHAR(60) CHECK (preferred_seat in ('aisle', 'window'))
);

ALTER TABLE passenger ADD constraint passenger_pk PRIMARY KEY (passenger_id);

CREATE TABLE passenger_on_flight (
    meal                VARCHAR(60),
    seat                VARCHAR(60),
    class               VARCHAR(60) NOT NULL CHECK (class in ('economy', 'business', 'first')),
    baggage_allowance   INTEGER NOT NULL CHECK (baggage_allowance >= 0),
    operated_id         INTEGER NOT NULL,
    passenger_id        INTEGER NOT NULL
);

ALTER TABLE passenger_on_flight ADD constraint passenger_on_flight_pk PRIMARY KEY (operated_id, passenger_id);


CREATE TABLE pilot (
    pilot_id        SERIAL NOT NULL,
    first_name      VARCHAR(60) NOT NULL,
    last_name       VARCHAR(60) NOT NULL,
    certification   VARCHAR(60) NOT NULL,
    is_captain      BOOL NOT NULL
);

ALTER TABLE pilot ADD constraint pilot_pk PRIMARY KEY (pilot_id);


CREATE TABLE pilot_on_flight (
    pilot_id      INTEGER NOT NULL,
    operated_id   INTEGER NOT NULL
);

ALTER TABLE pilot_on_flight ADD constraint pilot_on_flight_pk PRIMARY KEY (pilot_id, operated_id);

CREATE TABLE route (
    route_id            SERIAL NOT NULL,
    distance            INTEGER NOT NULL CHECK (distance > 0),
    etops_requirement   INTEGER NOT NULL CHECK (etops_requirement >= 0),
    origin              CHAR(4) NOT NULL,
    destination         CHAR(4) NOT NULL
);

ALTER TABLE route ADD constraint route_pk PRIMARY KEY (route_id);

ALTER TABLE aircraft
    ADD CONSTRAINT aircraft_aircraft_model_fk FOREIGN KEY ( aircraft_model_designator )
        REFERENCES aircraft_model ( designator );

ALTER TABLE crew_on_flight
    ADD CONSTRAINT crew_on_flight_crew_fk FOREIGN KEY ( crew_id )
        REFERENCES crew ( crew_id );

ALTER TABLE crew_on_flight
    ADD CONSTRAINT crew_on_flight_operated_flight_fk FOREIGN KEY ( operated_id )
        REFERENCES operated_flight ( operated_id );

ALTER TABLE flight
    ADD CONSTRAINT flight_aircraft_model_fk FOREIGN KEY ( aircraft_model_designator )
        REFERENCES aircraft_model ( designator );

ALTER TABLE flight
    ADD CONSTRAINT flight_route_fk FOREIGN KEY ( route_id )
        REFERENCES route ( route_id );

ALTER TABLE operated_flight
    ADD CONSTRAINT operated_flight_aircraft_fk FOREIGN KEY ( aircraft_identifier )
        REFERENCES aircraft ( identifier );

ALTER TABLE operated_flight
    ADD CONSTRAINT operated_flight_flight_fk FOREIGN KEY ( flight_id )
        REFERENCES flight ( flight_id );

ALTER TABLE passenger_on_flight
    ADD CONSTRAINT passenger_on_flight_operated_flight_fk FOREIGN KEY ( operated_id )
        REFERENCES operated_flight ( operated_id );

ALTER TABLE passenger_on_flight
    ADD CONSTRAINT passenger_on_flight_passenger_fk FOREIGN KEY ( passenger_id )
        REFERENCES passenger ( passenger_id );

ALTER TABLE pilot_on_flight
    ADD CONSTRAINT pilot_on_flight_operated_flight_fk FOREIGN KEY ( operated_id )
        REFERENCES operated_flight ( operated_id );

ALTER TABLE pilot_on_flight
    ADD CONSTRAINT pilot_on_flight_pilot_fk FOREIGN KEY ( pilot_id )
        REFERENCES pilot ( pilot_id );

ALTER TABLE route
    ADD CONSTRAINT route_airport_fk FOREIGN KEY ( origin )
        REFERENCES airport ( icao );

ALTER TABLE route
    ADD CONSTRAINT route_airport_fkv2 FOREIGN KEY ( destination )
        REFERENCES airport ( icao );



