CREATE TABLE IF NOT EXISTS QUESTIONS (
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
  date_creation datetime NOT NULL,
  date_modification datetime NOT NULL,
  is_deleted tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (id_question)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS EXAMENS (
  id_examen int(11) NOT NULL AUTO_INCREMENT,
  nom varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  numero int(11) NOT NULL,
  id_theme int(11) NOT NULL,
  date_creation datetime NOT NULL,
  date_modification datetime NOT NULL,
  is_published tinyint(1) NOT NULL DEFAULT '0',
  is_deleted tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (id_examen)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS THEMES (
  id_theme int(11) NOT NULL AUTO_INCREMENT,
  nom varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  numero int(11) NOT NULL,
  date_creation datetime NOT NULL,
  date_modification datetime NOT NULL,
  is_published tinyint(1) NOT NULL DEFAULT '0',
  is_deleted tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (id_theme)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS COURS (
  id_cours int(11) NOT NULL AUTO_INCREMENT,
  nom varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  nom_pdf varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  id_theme int(11) NOT NULL,
  date_creation datetime NOT NULL,
  is_deleted tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (id_cours)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS IMAGES (
  id_image int(11) NOT NULL AUTO_INCREMENT,
  nom_image varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (id_image)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE IF NOT EXISTS `USERS` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `firstname` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;