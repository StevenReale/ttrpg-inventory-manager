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

COMMIT;
