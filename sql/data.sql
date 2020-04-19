INSERT INTO airport VALUES ('LKXB', 'KXB', N'Kobeřice International Airport');
INSERT INTO airport VALUES ('EDDF', 'FRA', 'Frankfurt Airport');
INSERT INTO airport VALUES ('KLAX', 'LAX', 'Los Angeles International Airport');
INSERT INTO airport VALUES ('OMDB', 'DXB', 'Dubai International');
INSERT INTO airport VALUES ('WSSS', 'SIN', 'Singapore Changi Airport');
INSERT INTO airport VALUES ('LKPR', 'PRG', N'Václav Havel Airport Prague');
INSERT INTO airport VALUES ('EGLL', 'LHR', 'London Heathrow');
INSERT INTO airport VALUES ('EGKK', 'LGW', 'London Gatwick');
INSERT INTO airport VALUES ('KJFK', 'JFK', 'John F. Kennedy International Airport');


INSERT INTO route (distance, etops_requirement, origin, destination) VALUES (400, 0, 'LKXB', 'EDDF');
INSERT INTO route (distance, etops_requirement, origin, destination) VALUES (400, 0, 'EDDF', 'LKXB');

INSERT INTO route (distance, etops_requirement, origin, destination) VALUES (5300, 180, 'LKXB', 'KLAX');
INSERT INTO route (distance, etops_requirement, origin, destination) VALUES (5300, 180, 'KLAX', 'LKXB');

INSERT INTO route (distance, etops_requirement, origin, destination) VALUES (2300, 0, 'LKXB', 'OMDB');
INSERT INTO route (distance, etops_requirement, origin, destination) VALUES (2300, 0, 'OMDB', 'LKXB');

INSERT INTO route (distance, etops_requirement, origin, destination) VALUES (5200, 0, 'LKXB', 'WSSS');
INSERT INTO route (distance, etops_requirement, origin, destination) VALUES (5200, 0, 'WSSS', 'LKXB');

INSERT INTO route (distance, etops_requirement, origin, destination) VALUES (180, 0, 'LKXB', 'LKPR');
INSERT INTO route (distance, etops_requirement, origin, destination) VALUES (180, 0, 'LKPR', 'LKXB');

INSERT INTO route (distance, etops_requirement, origin, destination) VALUES (750, 0, 'LKXB', 'EGLL');
INSERT INTO route (distance, etops_requirement, origin, destination) VALUES (750, 0, 'EGLL', 'LKXB');

INSERT INTO route (distance, etops_requirement, origin, destination) VALUES (740, 0, 'LKXB', 'EGKK');
INSERT INTO route (distance, etops_requirement, origin, destination) VALUES (740, 0, 'EGKK', 'LKXB');

INSERT INTO route (distance, etops_requirement, origin, destination) VALUES (3700, 180, 'LKXB', 'KJFK');
INSERT INTO route (distance, etops_requirement, origin, destination) VALUES (3700, 180, 'KJFK', 'LKXB');

INSERT INTO aircraft_model (designator, manufacturer, family, full_type, etops_certified, etops_rating, range_nmi, mtow)
    VALUES ('A320', 'Airbus', 'A320', 'Airbus A320neo', 'yes', 180, 3500, 174200);

INSERT INTO aircraft_model (designator, manufacturer, family, full_type, etops_certified, etops_rating, range_nmi, mtow)
    VALUES ('A321', 'Airbus', 'A320', 'Airbus A321XLR', 'yes', 180, 4700, 222667);

INSERT INTO aircraft_model (designator, manufacturer, family, full_type, etops_certified, etops_rating, range_nmi, mtow)
    VALUES ('BCS3', 'Airbus', 'A220', 'Airbus A220-300', 'yes', 180, 3350, 154000);

INSERT INTO aircraft_model (designator, manufacturer, family, full_type, etops_certified, etops_rating, range_nmi, mtow)
    VALUES ('A339', 'Airbus', 'A330', 'Airbus A330-900neo', 'yes', 330, 7200, 553000);

INSERT INTO aircraft_model (designator, manufacturer, family, full_type, etops_certified, etops_rating, range_nmi, mtow)
    VALUES ('A35K', 'Airbus', 'A350', 'Airbus A350-1000', 'yes', 370, 8700, 696661);

INSERT INTO aircraft_model (designator, manufacturer, family, full_type, etops_certified, etops_rating, range_nmi, mtow)
    VALUES ('CONC', 'Airbus', 'Concorde', 'Concorde', 'NA', 0, 3900, 408000);

-- A220

INSERT INTO aircraft (identifier, engine, economy_seats, business_seats, first_seats, last_check, aircraft_model_designator)
    VALUES ('OK-BCS', 'Pratt & Whitney PW1500G', 115, 30, 0, '2019-12-06', 'BCS3');

INSERT INTO aircraft (identifier, engine, economy_seats, business_seats, first_seats, last_check, aircraft_model_designator)
    VALUES ('OK-BCR', 'Pratt & Whitney PW1500G', 115, 30, 0, '2019-12-06', 'BCS3');

-- A320neo

INSERT INTO aircraft (identifier, engine, economy_seats, business_seats, first_seats, last_check, aircraft_model_designator)
    VALUES ('OK-NEO', 'Pratt & Whitney PW1000G', 138, 28, 0, '2019-12-05', 'A320');

INSERT INTO aircraft (identifier, engine, economy_seats, business_seats, first_seats, last_check, aircraft_model_designator)
    VALUES ('OK-NEP', 'Pratt & Whitney PW1000G', 138, 28, 0, '2019-12-03', 'A320');

-- 321XLR

INSERT INTO aircraft (identifier, engine, economy_seats, business_seats, first_seats, last_check, aircraft_model_designator)
    VALUES ('OK-FAG', 'Pratt & Whitney PW1100G', 167, 32, 0, '2019-12-06', 'A321');

INSERT INTO aircraft (identifier, engine, economy_seats, business_seats, first_seats, last_check, aircraft_model_designator)
    VALUES ('OK-FBG', 'Pratt & Whitney PW1100G', 167, 32, 0, '2019-12-04', 'A321');

INSERT INTO aircraft (identifier, engine, economy_seats, business_seats, first_seats, last_check, aircraft_model_designator)
    VALUES ('OK-FCG', 'Pratt & Whitney PW1100G', 139, 44, 0, '2019-12-04', 'A321');

INSERT INTO aircraft (identifier, engine, economy_seats, business_seats, first_seats, last_check, aircraft_model_designator)
    VALUES ('OK-FDG', 'Pratt & Whitney PW1100G', 0, 80, 0, '2019-12-04', 'A321');

-- A330neo

INSERT INTO aircraft (identifier, engine, economy_seats, business_seats, first_seats, last_check, aircraft_model_designator)
    VALUES ('OK-BMR', 'Rolls-Royce Trent 7000', 264, 34, 0, '2019-12-05', 'A339');

INSERT INTO aircraft (identifier, engine, economy_seats, business_seats, first_seats, last_check, aircraft_model_designator)
    VALUES ('OK-BMT', 'Rolls-Royce Trent 7000', 264, 34, 0, '2019-12-03', 'A339');

-- A350

INSERT INTO aircraft (identifier, engine, economy_seats, business_seats, first_seats, last_check, aircraft_model_designator)
    VALUES ('OK-XWB', 'Rolls-Royce Trent XWB', 281, 46, 0, '2019-12-06', 'A35K');

INSERT INTO aircraft (identifier, engine, economy_seats, business_seats, first_seats, last_check, aircraft_model_designator)
    VALUES ('OK-XWC', 'Rolls-Royce Trent XWB', 281, 46, 0, '2019-12-06', 'A35K');

INSERT INTO aircraft (identifier, engine, economy_seats, business_seats, first_seats, last_check, aircraft_model_designator)
    VALUES ('OK-XWD', 'Rolls-Royce Trent XWB', 281, 46, 0, '2019-12-06', 'A35K');

INSERT INTO aircraft (identifier, engine, economy_seats, business_seats, first_seats, last_check, aircraft_model_designator)
    VALUES ('OK-XWE', 'Rolls-Royce Trent XWB', 281, 46, 0, '2019-12-06', 'A35K');

INSERT INTO aircraft (identifier, engine, economy_seats, business_seats, first_seats, last_check, aircraft_model_designator)
    VALUES ('OK-XWF', 'Rolls-Royce Trent XWB', 281, 46, 0, '2019-12-06', 'A35K');

-- Concorde

INSERT INTO aircraft (identifier, engine, economy_seats, business_seats, first_seats, last_check, aircraft_model_designator)
    VALUES ('OK-CCD', 'Rolls-Royce/Snecma Olympus 593', 0, 128, 0, '2019-12-06', 'CONC');

INSERT INTO aircraft (identifier, engine, economy_seats, business_seats, first_seats, last_check, aircraft_model_designator)
    VALUES ('OK-CND', 'Rolls-Royce/Snecma Olympus 593', 0, 128, 0, '2019-12-06', 'CONC');

-- Passengers

-- Dummy passenger used to replace deleted passengers
INSERT INTO passenger VALUES (0, 'DummyPax', 'DummyPax', 'default', 'aisle');

INSERT INTO passenger (first_name, last_name, preferred_meal, preferred_seat)
    VALUES ('Carson', 'Mosley', 'kosher', 'window');

INSERT INTO passenger (first_name, last_name, preferred_meal, preferred_seat)
    VALUES ('Mira', 'Morris', 'default', 'window');

INSERT INTO passenger (first_name, last_name, preferred_meal, preferred_seat)
    VALUES ('Areli', 'Blevins', 'vegan', 'window');

INSERT INTO passenger (first_name, last_name, preferred_meal, preferred_seat)
    VALUES ('Angie', 'Harding', 'vegetarian', 'window');

INSERT INTO passenger (first_name, last_name, preferred_meal, preferred_seat)
    VALUES ('Deborah', 'Bauer', 'halal', 'window');

INSERT INTO passenger (first_name, last_name, preferred_meal, preferred_seat)
    VALUES ('Fatima', 'Hess', 'default', 'window');

INSERT INTO passenger (first_name, last_name, preferred_meal, preferred_seat)
    VALUES ('Elliana', 'Hayes', 'default', 'aisle');

INSERT INTO passenger (first_name, last_name, preferred_meal, preferred_seat)
    VALUES ('Octavio', 'Ayers', 'vegan', 'aisle');

INSERT INTO passenger (first_name, last_name, preferred_meal, preferred_seat)
    VALUES ('Anabel', 'Bentley', 'halal', 'aisle');

INSERT INTO passenger (first_name, last_name, preferred_meal, preferred_seat)
    VALUES ('Damian', 'Boyer', 'default', 'aisle');


INSERT INTO crew (first_name, last_name, role, seniority)
    VALUES ('Haleigh', 'Hurst', 'leader', 'senior');

INSERT INTO crew (first_name, last_name, role, seniority)
    VALUES ('Malachi', 'Cain', 'leader', 'senior');

INSERT INTO crew (first_name, last_name, role, seniority)
    VALUES ('Lilian', 'Solomon', 'safety', 'senior');

INSERT INTO crew (first_name, last_name, role, seniority)
    VALUES ('Evangeline', 'Novak', 'safety', 'senior');

INSERT INTO crew (first_name, last_name, role, seniority)
    VALUES ('Braylon', 'Farmer', 'safety', 'senior');

INSERT INTO crew (first_name, last_name, role, seniority)
    VALUES ('Howard', 'Fletcher', 'attendant', 'senior');

INSERT INTO crew (first_name, last_name, role, seniority)
    VALUES ('Elizabeth', 'Hines', 'attendant', 'junior');

INSERT INTO crew (first_name, last_name, role, seniority)
    VALUES ('Zachariah', 'Oliver', 'attendant', 'junior');

INSERT INTO crew (first_name, last_name, role, seniority)
    VALUES ('Trey', 'Huffman', 'attendant', 'junior');

INSERT INTO crew (first_name, last_name, role, seniority)
    VALUES ('Kyson', 'McGrath', 'attendant', 'junior');

-- A320 pilots

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Tony', 'Farley', 'A320', true);

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Kristopher', 'Pope', 'A320', false);

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Rishi', 'Bailey', 'A320', true);

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Donavan', 'Padilla', 'A320', false);

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Regan', 'Maxwell', 'A320', true);

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Tony', 'Maxwell', 'A320', false);

-- A220 pilots

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Ramon', 'Baker', 'A220', true);

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Amare', 'Rogers', 'A220', false);

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Karsyn', 'Hayden', 'A220', true);

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Damarion', 'Wright', 'A220', false);

-- A330 pilots

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Kole', 'McCarthy', 'A330', true);

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Jovanny', 'Powell', 'A330', false);

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Quinten', 'Pittman', 'A330', true);

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Coleman', 'Mejia', 'A330', false);

-- A350 pilots

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Barbara', 'Cowan', 'A350', true);

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Sarai', 'Stout', 'A350', false);

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Krystal', 'Ali', 'A350', true);

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Ignacio', 'Harmon', 'A350', false);

-- Concorde pilots

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Sage', 'Garrett', 'Concorde', true);

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Sidney', 'Kirby', 'Concorde', false);

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Anabel', 'Cross', 'Concorde', true);

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Giselle', 'Watts', 'Concorde', false);

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Hillary', 'Sanford', 'Concorde', true);

INSERT INTO pilot (first_name, last_name, certification, is_captain)
    VALUES ('Reese', 'Suarez', 'Concorde', false);

-- Flights

-- Dummy flight and dummy route

INSERT INTO route (route_id, distance, etops_requirement, origin, destination) VALUES (0, 1, 0, 'LKXB', 'LKXB');

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
VALUES ('VU0000', '01:00:00', '12:00:00', 'CONC', 0);

-- Concorde flights

-- KXB - JFK

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0001', '09:00:00', '09:30:00', 'CONC', 15);

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0002', '12:15:00', '22:30:00', 'CONC', 16);

-- A220-300 flights

-- KXB - FRA

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0025', '09:00:00', '10:45:00', 'BCS3', 1);

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0026', '11:30:00', '13:15:00', 'BCS3', 2);

-- KXB - PRG

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0003', '10:00:00', '10:45:00', 'BCS3', 9);

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0004', '11:30:00', '12:15:00', 'BCS3', 10);

-- A320neo flights

-- KXB - LGW

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0005', '09:30:00', '12:00:00', 'A320', 13);

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0006', '13:00:00', '16:30:00', 'A320', 14);

-- KXB - LGW

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0007', '12:30:00', '15:00:00', 'A320', 13);

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0008', '16:00:00', '19:15:00', 'A320', 14);

-- A321XLR flights

-- KXB - LHR

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0009', '09:00:00', '11:30:00', 'A321', 11);

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0010', '12:15:00', '15:30:00', 'A321', 12);

-- KXB - LHR

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0011', '14:00:00', '16:30:00', 'A321', 11);

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0012', '17:15:00', '20:45:00', 'A321', 12);

-- KXB - JFK

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0013', '10:15:00', '13:40:00', 'A321', 15);

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0014', '16:00:00', '06:00:00', 'A321', 16);

-- A330-900neo flights

-- KXB - LHR

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0015', '12:00:00', '14:30:00', 'A339', 11);

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0016', '15:45:00', '19:00:00', 'A339', 12);

-- KXB - DXB

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0139', '14:00:00', '22:00:00', 'A339', 5);

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0140', '09:00:00', '13:00:00', 'A339', 6);

-- KXB - LAX

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0017', '10:00:00', '13:30:00', 'A339', 3);

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0018', '15:30:00', '12:15:00', 'A339', 4);

-- A350-1000 flights

-- KXB - SIN

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0019', '13:00:00', '08:00:00', 'A35K', 7);

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0020', '12:00:00', '19:00:00', 'A35K', 8);

-- KXB - SIN

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0021', '18:00:00', '13:00:00', 'A35K', 7);

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0022', '16:00:00', '22:00:00', 'A35K', 8);

-- KXB - LAX

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0023', '15:00:00', '18:30:00', 'A35K', 3);

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0024', '21:15:00', '18:00:00', 'A35K', 4);

-- KXB - DXB

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0141', '08:00:00', '16:00:00', 'A35K', 5);

INSERT INTO flight (flight_id, departure_time, arrival_time, aircraft_model_designator, route_id)
    VALUES ('VU0142', '18:00:00', '22:30:00', 'A35K', 6);

-- Concrete Concorde flights

-- Concorde flight 1

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('08:55:00', '09:00:00', 'VU0001', 'OK-CCD', '2019-12-04');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (1, 1);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (3, 1);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (6, 1);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (19, 1);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (20, 1);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (21, 1);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('kosher', '2A', 'business', 32, 1, 1);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegan', '2D', 'business', 32, 1, 3);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '5A', 'business', 32, 1, 4);

-- Concorde flight 2

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('12:30:00', '22:15:00', 'VU0002', 'OK-CCD', '2019-12-04');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (2, 2);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (4, 2);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (8, 2);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (22, 2);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (23, 2);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (24, 2);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegan', '3A', 'business', 32, 2, 2);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('halal', '6D', 'business', 32, 2, 5);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegetarian', '12A', 'business', 32, 2, 8);

-- Concorde flight 3

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('09:10:00', '09:15:00', 'VU0001', 'OK-CND', '2019-12-05');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (1, 3);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (3, 3);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (6, 3);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (22, 3);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (23, 3);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (24, 3);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('kosher', '2D', 'business', 32, 3, 1);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegan', '7C', 'business', 32, 3, 6);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '14A', 'business', 32, 3, 7);

-- Concorde flight 4

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('12:35:00', '22:20:00', 'VU0002', 'OK-CND', '2019-12-05');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (2, 4);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (4, 4);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (8, 4);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (22, 4);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (23, 4);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (24, 4);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegan', '3A', 'business', 32, 4, 8);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('halal', '6D', 'business', 32, 4, 9);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegetarian', '12A', 'business', 32, 4, 10);

-- Frankfurt flights (A220)

-- KXB - FRA flight 1

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('08:55:00', '10:20:00', 'VU0025', 'OK-BCS', '2019-12-04');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (2, 5);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (4, 5);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (10, 5);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (7, 5);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (8, 5);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('halal', '2A', 'business', 32, 5, 1);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegan', '17D', 'economy', 18, 5, 3);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegetarian', '16A', 'economy', 18, 5, 4);

-- FRA - KXB flight 2

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('08:55:00', '10:20:00', 'VU0026', 'OK-BCS', '2019-12-04');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (1, 6);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (5, 6);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (9, 6);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (9, 6);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (10, 6);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '1D', 'business', 32, 6, 2);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '12D', 'economy', 18, 6, 8);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '13A', 'economy', 18, 6, 9);

-- Prague flights (A220)

-- KXB - PRG flight 1

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('10:05:00', '10:40:00', 'VU0003', 'OK-BCS', '2019-12-05');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (2, 7);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (4, 7);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (10, 7);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (7, 7);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (8, 7);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegan', '12A', 'economy', 18, 7, 1);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegan', '12D', 'economy', 18, 7, 3);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegetarian', '15A', 'economy', 18, 7, 4);

-- PRG - KXB flight 2

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('11:30:00', '12:15:00', 'VU0004', 'OK-BCS', '2019-12-05');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (1, 8);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (3, 8);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (8, 8);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (9, 8);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (10, 8);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '1D', 'business', 32, 8, 6);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '12D', 'economy', 18, 8, 7);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('halal', '13A', 'economy', 18, 8, 10);

-- Gatwick flights (A320)

-- KXB - LGW flight 1

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('09:20:00', '12:00:00', 'VU0005', 'OK-NEO', '2019-12-05');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (2, 9);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (4, 9);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (8, 9);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (1, 9);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (2, 9);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '1D', 'business', 32, 9, 1);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '2F', 'business', 32, 9, 2);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('kosher', '20A', 'economy', 18, 9, 3);

-- LGW - KXB flight 2

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('13:15:00', '16:20:00', 'VU0006', 'OK-NEO', '2019-12-05');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (1, 10);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (5, 10);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (9, 10);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (3, 10);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (4, 10);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '2C', 'business', 32, 10, 5);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '3D', 'business', 32, 10, 7);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '4C', 'business', 32, 10, 9);

-- KXB - LGW flight 3

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('12:30:00', '15:00:00', 'VU0007', 'OK-NEP', '2019-12-06');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (1, 11);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (5, 11);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (8, 11);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (3, 11);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (4, 11);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '12C', 'economy', 18, 11, 4);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('halal', '13D', 'economy', 18, 11, 8);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '14C', 'economy', 18, 11, 10);

-- LGW - KXB flight 4

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('13:15:00', '16:20:00', 'VU0008', 'OK-NEP', '2019-12-06');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (2, 12);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (5, 12);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (6, 12);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (5, 12);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (6, 12);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '2C', 'business', 32, 12, 5);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '10F', 'economy', 18, 12, 7);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '18A', 'economy', 18, 12, 9);

-- Heathrow flights (A321)

-- KXB - LHR flight 1

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('09:15:00', '11:25:00', 'VU0009', 'OK-FAG', '2019-12-07');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (1, 13);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (3, 13);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (7, 13);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (3, 13);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (4, 13);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '12C', 'economy', 18, 13, 2);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('kosher', '20D', 'economy', 18, 13, 7);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegan', '14C', 'economy', 18, 13, 9);

-- LHR - KXB flight 2

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('12:30:00', '15:20:00', 'VU0010', 'OK-FAG', '2019-12-07');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (2, 14);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (5, 14);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (9, 14);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (5, 14);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (6, 14);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegan', '3C', 'business', 32, 14, 4);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('halal', '1F', 'business', 32, 14, 8);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegetarian', '15E', 'economy', 18, 14, 10);

-- KXB - LHR flight 3

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('14:20:00', '16:10:00', 'VU0011', 'OK-FCG', '2019-12-08');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (1, 15);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (4, 15);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (10, 15);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (5, 15);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (6, 15);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '17B', 'economy', 18, 15, 3);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '13E', 'economy', 18, 15, 5);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('kosher', '6C', 'business', 32, 15, 6);

-- LHR - KXB flight 4

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('17:05:00', '20:40:00', 'VU0012', 'OK-FCG', '2019-12-08');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (2, 16);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (5, 16);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (8, 16);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (3, 16);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (4, 16);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('kosher', '12C', 'economy', 18, 16, 2);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('halal', '4D', 'business', 32, 16, 5);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('halal', '14C', 'economy', 18, 16, 7);

-- New York flights (A321)

-- KXB - JFK flight 1

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('10:30:00', '13:40:00', 'VU0013', 'OK-FDG', '2019-12-09');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (2, 17);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (5, 17);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (8, 17);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (3, 17);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (4, 17);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '1A', 'business', 32, 17, 2);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '4F', 'business', 32, 17, 3);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '8A', 'business', 32, 17, 7);

-- JFK - KXB flight 2

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('16:45:00', '05:55:00', 'VU0014', 'OK-FDG', '2019-12-09');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (1, 18);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (3, 18);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (6, 18);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (5, 18);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (6, 18);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('halal', '1F', 'business', 32, 18, 1);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '5A', 'business', 32, 18, 4);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegetarian', '7F', 'business', 32, 18, 6);

-- Heathrow flights (A330)

-- KXB - LHR flight 1

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('12:10:00', '14:25:00', 'VU0015', 'OK-BMR', '2019-12-10');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (1, 19);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (3, 19);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (6, 19);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (11, 19);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (12, 19);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '1F', 'business', 32, 19, 2);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '15A', 'economy', 18, 19, 5);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegetarian', '30F', 'economy', 18, 19, 7);

-- LHR - KXB flight 2

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('15:40:00', '19:00:00', 'VU0016', 'OK-BMR', '2019-12-10');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (2, 20);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (4, 20);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (8, 20);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (13, 20);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (14, 20);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '1F', 'business', 32, 20, 1);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '1A', 'business', 18, 20, 8);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegetarian', '30F', 'economy', 18, 20, 9);

-- Dubai flights (A330)

-- KXB - DXB flight 1

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('14:30:00', '21:55:00', 'VU0139', 'OK-BMR', '2019-12-11');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (1, 21);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (5, 21);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (10, 21);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (13, 21);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (14, 21);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '1F', 'business', 32, 21, 2);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '15A', 'economy', 18, 21, 5);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegetarian', '30F', 'economy', 18, 21, 7);

-- KXB - DXB flight 2

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('09:20:00', '12:45:00', 'VU0140', 'OK-BMR', '2019-12-12');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (2, 22);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (4, 22);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (8, 22);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (11, 22);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (12, 22);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('halal', '2F', 'business', 32, 22, 1);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '3A', 'business', 32, 22, 8);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '22F', 'economy', 18, 22, 9);

-- Los Angeles flights (A330)

-- KXB - LAX flight 1

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('10:30:00', '13:20:00', 'VU0017', 'OK-BMT', '2019-12-13');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (1, 23);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (4, 23);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (6, 23);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (13, 23);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (14, 23);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '2F', 'business', 32, 23, 3);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '21A', 'economy', 18, 23, 4);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '29F', 'economy', 18, 23, 5);

-- LAX - KXB flight 2

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('15:20:00', '11:45:00', 'VU0018', 'OK-BMT', '2019-12-13');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (2, 24);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (3, 24);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (8, 24);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (11, 24);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (12, 24);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegan', '2F', 'business', 32, 24, 1);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegan', '3A', 'business', 32, 24, 2);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegan', '7C', 'business', 32, 24, 6);

-- Singapore flights (A350)

-- KXB - SIN flight 1

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('13:30:00', '07:30:00', 'VU0019', 'OK-XWB', '2019-12-13');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (1, 25);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (4, 25);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (9, 25);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (15, 25);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (16, 25);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '3F', 'business', 32, 25, 2);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('kosher', '16A', 'economy', 18, 25, 7);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('halal', '29F', 'economy', 18, 25, 10);

-- SIN - KXB flight 2

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('12:20:00', '18:45:00', 'VU0020', 'OK-XWB', '2019-12-14');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (2, 26);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (3, 26);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (8, 26);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (17, 26);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (18, 26);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegan', '2F', 'business', 32, 26, 3);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '3A', 'business', 32, 26, 5);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '7C', 'business', 32, 26, 8);

-- KXB - SIN flight 3

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('13:20:00', '07:45:00', 'VU0019', 'OK-XWC', '2019-12-15');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (2, 27);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (5, 27);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (10, 27);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (15, 27);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (16, 27);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '3F', 'business', 32, 27, 2);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegetarian', '26A', 'economy', 18, 27, 3);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '29F', 'economy', 18, 27, 6);

-- SIN - KXB flight 4

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('12:15:00', '18:40:00', 'VU0020', 'OK-XWC', '2019-12-16');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (1, 28);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (3, 28);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (6, 28);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (17, 28);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (18, 28);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '2F', 'business', 32, 28, 1);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '8A', 'business', 32, 28, 4);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('halal', '22C', 'economy', 18, 28, 5);

-- KXB - SIN flight 5

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES (null, null, 'VU0021', 'OK-XWF', '2020-07-15');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (2, 29);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (5, 29);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (9, 29);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (15, 29);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (16, 29);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '3F', 'business', 32, 29, 2);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegetarian', '27A', 'economy', 18, 29, 3);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '33F', 'economy', 18, 29, 6);

-- SIN - KXB flight 6

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES (null, null, 'VU0022', 'OK-XWF', '2020-07-16');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (1, 30);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (3, 30);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (4, 30);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (17, 30);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (18, 30);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '4F', 'business', 32, 30, 1);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '3A', 'business', 32, 30, 4);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('kosher', '25C', 'economy', 18, 30, 10);

-- Los Angeles flights (A350)

-- KXB - LAX flight 1

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('15:20:00', '18:25:00', 'VU0023', 'OK-XWB', '2019-12-17');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (2, 31);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (5, 31);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (9, 31);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (15, 31);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (16, 31);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '7F', 'business', 32, 31, 1);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '23A', 'economy', 18, 31, 4);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '31F', 'economy', 18, 31, 10);

-- LAX - KXB flight 2

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('21:15:00', '17:35:00', 'VU0024', 'OK-XWB', '2019-12-17');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (1, 32);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (3, 32);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (7, 32);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (17, 32);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (18, 32);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('halal', '2F', 'business', 32, 32, 2);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('halal', '8A', 'business', 32, 32, 6);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('halal', '12C', 'business', 32, 32, 8);

-- Dubai flights (A350)

-- KXB - DXB flight 1

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('08:10:00', '15:45:00', 'VU0141', 'OK-XWD', '2019-12-18');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (2, 33);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (3, 33);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (6, 33);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (15, 33);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (16, 33);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '4F', 'business', 32, 33, 3);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('halal', '25A', 'economy', 18, 33, 5);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '32F', 'economy', 18, 33, 7);

-- DXB - KXB flight 2

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('18:00:00', '22:15:00', 'VU0142', 'OK-XWD', '2019-12-18');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (1, 34);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (4, 34);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (8, 34);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (17, 34);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (18, 34);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('halal', '2F', 'business', 32, 34, 2);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '8A', 'business', 32, 34, 4);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegetarian', '21C', 'economy', 18, 34, 9);

-- KXB - DXB flight 3

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('08:30:00', '15:55:00', 'VU0141', 'OK-XWD', '2019-12-19');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (2, 35);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (5, 35);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (10, 35);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (17, 35);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (18, 35);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('halal', '4A', 'business', 32, 35, 3);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('default', '25D', 'economy', 18, 35, 5);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegan', '32C', 'economy', 18, 35, 6);

-- DXB - KXB flight 4

INSERT INTO operated_flight (actual_departure, actual_arrival, flight_id, aircraft_identifier, date)
    VALUES ('17:50:00', '22:00:00', 'VU0142', 'OK-XWD', '2019-12-19');

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (1, 36);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (3, 36);

INSERT INTO crew_on_flight (crew_id, operated_id)
    VALUES (6, 36);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (15, 36);

INSERT INTO pilot_on_flight (pilot_id, operated_id)
    VALUES (16, 36);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('halal', '1A', 'business', 32, 36, 1);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegan', '8C', 'business', 32, 36, 8);

INSERT INTO passenger_on_flight (meal, seat, class, baggage_allowance, operated_id, passenger_id)
    VALUES  ('vegetarian', '21B', 'economy', 18, 36, 10);
