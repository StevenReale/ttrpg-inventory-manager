BEGIN TRANSACTION;

DROP TABLE IF EXISTS character_item;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS character;

CREATE TABLE item (
   item_id serial,
   item_name varchar(50) NOT NULL,
   item_description varchar(200) NULL,
   item_effect varchar(200) NULL,
   item_value numeric(6,2) NULL,
   PRIMARY KEY (item_id)
);

INSERT INTO item (item_name, item_description, item_effect, item_value) VALUES ('Item 1', 'The First Item', 'Appears first on a list of items', 100);
INSERT INTO item (item_name, item_description, item_effect, item_value) VALUES ('Item 2', 'The Second Item', 'Appears second on a list of items', 20);
INSERT INTO item (item_name, item_description, item_effect, item_value) VALUES ('Item 3', 'The Third Item', 'Appears third on a list of items', 3000);
INSERT INTO item (item_name, item_description, item_effect, item_value) VALUES ('Item 4', 'The Fourth Item', 'Appears fourth on a list of items', 4);

CREATE TABLE character (
   character_id serial,
   character_name varchar(50) NOT NULL,
   character_class varchar(50) NOT NULL,
   character_level integer NOT NULL,
   character_description varchar(200) NULL,
   PRIMARY KEY (character_id)
);

INSERT INTO character (character_name, character_class, character_level, character_description) VALUES ('Char 1', 'Monk', 10, 'A Known Tester');

CREATE TABLE character_item (
   character_id integer NOT NULL,
   item_id integer NOT NULL,
   quantity integer NOT NULL,
   FOREIGN KEY (character_id) REFERENCES character(character_id),
   FOREIGN KEY (item_id) REFERENCES item(item_id)
);

INSERT INTO character_item (character_id, item_id, quantity) VALUES (1, 1, 1);
INSERT INTO character_item (character_id, item_id, quantity) VALUES (1, 2, 1);

COMMIT;