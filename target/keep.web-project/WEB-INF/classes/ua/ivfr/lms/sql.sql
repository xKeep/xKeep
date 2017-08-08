ALTER TABLE notes DROP FOREIGN KEY `note_user__fk`;
DROP TABLE IF EXISTS xkeep.users;
DROP TABLE IF EXISTS xkeep.notes;

CREATE TABLE users
(
  id              INT AUTO_INCREMENT PRIMARY KEY,
  username        VARCHAR(100) NOT NULL,
  password        VARCHAR(20)  NOT NULL,
  name            VARCHAR(30),
  date_registered VARCHAR(10) NOT NULL,
  CONSTRAINT user_username_uindex UNIQUE (username)
);

CREATE TABLE notes
(
  id          INT AUTO_INCREMENT PRIMARY KEY,
  note        TEXT,
  note_title  VARCHAR(255),
  is_archieve TINYINT(1) DEFAULT '0' NOT NULL,
  date_added   VARCHAR(10) NOT NULL,
  color       VARCHAR(6) DEFAULT 'FFFFFF',
  user_id     INT NOT NULL
);

CREATE INDEX note_user__fk ON notes (user_id);

ALTER TABLE notes
  ADD CONSTRAINT note_user__fk FOREIGN KEY (user_id) REFERENCES xkeep.users (id)
  ON UPDATE CASCADE
  ON DELETE CASCADE;

INSERT INTO users (id, username, password, name, date_registered) VALUES
  (1, "ihorlt@gmail.com", "1122", "Igor", "2017-07-24");