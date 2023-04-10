BEGIN TRANSACTION;

DROP TABLE IF EXISTS item, character, character_item CASCADE;

CREATE TABLE item (
   item_id serial,
   item_name varchar(50) NOT NULL,
   item_description varchar(200) NULL,
   item_effect varchar(200) NULL,
   item_value numeric(6,2) NULL,
   PRIMARY KEY (item_id)
);

CREATE TABLE character (
   character_id serial,
   character_name varchar(50) NOT NULL,
   character_class varchar(50) NOT NULL,
   character_level integer NOT NULL,
   character_description varchar(200) NULL,
   PRIMARY KEY (character_id)
);

CREATE TABLE character_item (
   character_id integer NOT NULL,
   item_id integer NOT NULL,
   quantity integer NOT NULL,
   FOREIGN KEY (character_id) REFERENCES character(character_id),
   FOREIGN KEY (item_id) REFERENCES item(item_id)
);

INSERT INTO character (character_name, character_class, character_level, character_description) VALUES ('Gandalf', 'Wizard', 100, 'The Grey Wizard');
INSERT INTO character (character_name, character_class, character_level, character_description) VALUES ('Frodo', 'Hobbit', 1, 'The Ring Bearer');
INSERT INTO character (character_name, character_class, character_level, character_description) VALUES ('Gimli', 'Dwarf', 1, 'The Axe Wielder');
INSERT INTO character (character_name, character_class, character_level, character_description) VALUES ('Legolas', 'Elf', 1, 'The Bow Wielder');

INSERT INTO item (item_name, item_description, item_effect, item_value) VALUES ('Ring of Power', 'The One Ring', 'Gives the wearer the power to rule the world', 100);
INSERT INTO item (item_name, item_description, item_effect, item_value) VALUES ('Sting', 'The Elven Sword', 'The sword of the Elven King', 10);
INSERT INTO item (item_name, item_description, item_effect, item_value) VALUES ('Mithril Shirt', 'The Elven Shirt', 'The shirt of the Elven King', 10);
INSERT INTO item (item_name, item_description, item_effect, item_value) VALUES ('Mithril Pants', 'The Elven Pants', 'The pants of the Elven King', 10);

INSERT INTO character_item (character_id, item_id, quantity) VALUES (1, 1, 1);
INSERT INTO character_item (character_id, item_id, quantity) VALUES (2, 2, 1);
INSERT INTO character_item (character_id, item_id, quantity) VALUES (2, 3, 1);
INSERT INTO character_item (character_id, item_id, quantity) VALUES (2, 4, 1);

COMMIT;
