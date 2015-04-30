
CREATE TABLE IF NOT EXISTS questions (
  id_question int(11) NOT NULL AUTO_INCREMENT,
  numero_question int(11) NOT NULL,
  enonce_question text NOT NULL,
  is_correct_A tinyint(1) NOT NULL,
  is_correct_B tinyint(1) NOT NULL,
  is_correct_C tinyint(1) NOT NULL,
  is_correct_D tinyint(1) NOT NULL,
  enonce_A text NOT NULL,
  enonce_B text NOT NULL,
  enonce_C text NOT NULL,
  enonce_D text NOT NULL,
  id_image int(11) NOT NULL,
  id_examen int(11) NOT NULL,
  PRIMARY KEY (id_question)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;