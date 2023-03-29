DROP DATABASE IF EXISTS elective_db;

CREATE DATABASE IF NOT EXISTS elective_db;
USE elective_db;

DROP TABLE IF EXISTS registration;
DROP TABLE IF EXISTS journal;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS status;
DROP TABLE IF EXISTS topic;
DROP TABLE IF EXISTS role;

CREATE TABLE topic (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE status (
	id INT AUTO_INCREMENT PRIMARY KEY,
	status VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE role (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE user (
	id INT AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(45) NOT NULL UNIQUE,
	password VARCHAR(45) NOT NULL UNIQUE,
	first_name VARCHAR(45) NOT NULL,
	middle_name VARCHAR(45) NOT NULL,
	last_name VARCHAR(45) NOT NULL,
	email VARCHAR(45) NOT NULL UNIQUE,
	role_id INT NOT NULL,
	CONSTRAINT fk_user_role_id FOREIGN KEY (role_id)
		REFERENCES role(id)
		ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE course (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(45) NOT NULL UNIQUE,
	duration VARCHAR(45) NOT NULL,
	start_date DATETIME NOT NULL,
	end_date DATETIME,
	count_student INT UNSIGNED NOT NULL,
	topic_id INT NOT NULL,
	teacher_id INT NOT NULL,
	status_id INT NOT NULL,
	CONSTRAINT fk_course_topic_id FOREIGN KEY (topic_id)
		REFERENCES topic(id)
		ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT fk_cource_status_id FOREIGN KEY (status_id)
		REFERENCES status(id)
		ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT fk_cource_teacher_id FOREIGN KEY (teacher_id)
		REFERENCES user(id)
		ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE registration (
	course_id INT,
	student_id INT,
	registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (course_id, student_id),
	CONSTRAINT registration_course_id FOREIGN KEY (course_id)
		REFERENCES course(id)
		ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT registration_student_id FOREIGN KEY (student_id)
		REFERENCES user(id)
		ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE journal (
	teacher_id INT NOT NULL,
	student_id INT NOT NULL,
	course_id INT NOT NULL,
	CONSTRAINT journal_teacher_id FOREIGN KEY (teacher_id)
		REFERENCES user(id)
		ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT journal_student_id FOREIGN KEY (student_id)
		REFERENCES user(id)
		ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT journal_course_id FOREIGN KEY (course_id)
		REFERENCES course(id)
		ON UPDATE CASCADE ON DELETE RESTRICT
);

-- -----------------------------------------------------
-- Inserts
-- -----------------------------------------------------

-- role
INSERT INTO role (id, name) VALUES (DEFAULT, "admin");
INSERT INTO role (id, name) VALUES (DEFAULT, "teacher");
INSERT INTO role (id, name) VALUES (DEFAULT, "student");

-- topic
INSERT INTO topic (id, name) VALUES (DEFAULT, "Java");
INSERT INTO topic (id, name) VALUES (DEFAULT, "C++");
INSERT INTO topic (id, name) VALUES (DEFAULT, "C");
INSERT INTO topic (id, name) VALUES (DEFAULT, "Swift");
INSERT INTO topic (id, name) VALUES (DEFAULT, "Database");
INSERT INTO topic (id, name) VALUES (DEFAULT, "Algorithm");
INSERT INTO topic (id, name) VALUES (DEFAULT, "Computer science");

-- status
INSERT INTO status (id, status) VALUES (DEFAULT, "Not started");
INSERT INTO status (id, status) VALUES (DEFAULT, "In progres");
INSERT INTO status (id, status) VALUES (DEFAULT, "Finished");

-- user
-- admins
SET @user_role = (SELECT id FROM role WHERE name="admin");
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id) VALUES (DEFAULT, "super_admin", "admin" , "Ivan", "Petrenko", "Pavlov", "pavlov.ivan@gmail.com", @user_role);

-- teachers
SET @user_role = (SELECT id FROM role WHERE name="teacher");
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id) VALUES (DEFAULT, "teacher_1", "teacher_1" , "Margo", "Ivankova", "Robby", "margo.robby@gmail.com", @user_role);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id) VALUES (DEFAULT, "teacher_2", "teacher_2" , "Dima", "Petrov", "Dally", "dima.dally@gmail.com", @user_role);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id) VALUES (DEFAULT, "teacher_3", "teacher_3" , "Olga", "Koplenko", "Marino", "olga.marino@gmail.com", @user_role);

-- students
SET @user_role = (SELECT id FROM role WHERE name="student");
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id) VALUES (DEFAULT, "student_1", "student_1" , "Maria", "E. Hoffmann", "Dorna", "maria.dorna@gmail.com", @user_role);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id) VALUES (DEFAULT, "student_2", "student_2" , "James", "M. Langston", "Hughes", "james.hughes@gmail.com", @user_role);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id) VALUES (DEFAULT, "student_3", "student_3" , "William", "Bradley", "Pitt", "william.pitt@gmail.com", @user_role);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id) VALUES (DEFAULT, "student_4", "student_4" , "Thomas", "Cruise", "Mapother", "thomas.mapother@gmail.com", @user_role);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id) VALUES (DEFAULT, "student_5", "student_5" , "Willard", "Carroll", "Smith", "willard.smith@gmail.com", @user_role);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id) VALUES (DEFAULT, "student_6", "student_6" , "Scarlett", "Ingrid", "Johansson", "scarlett.johansson@gmail.com", @user_role);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id) VALUES (DEFAULT, "student_7", "student_7" , "John", "Christopher", "Depp", "john.depp@gmail.com", @user_role);

-- course
SET @teacher_id = (SELECT id FROM user WHERE username="teacher_1");
INSERT INTO course (id, name, duration, start_date, end_date, count_student, topic_id, teacher_id, status_id) VALUES (DEFAULT, "SQL", "3 week" , "2022-12-14", "2023-01-04", 1, 5, @teacher_id, 2);
SET @teacher_id = (SELECT id FROM user WHERE username="teacher_2");
INSERT INTO course (id, name, duration, start_date, end_date, count_student, topic_id, teacher_id, status_id) VALUES (DEFAULT, "C++ for 21 days", "3 week" , "2022-11-23", "2022-12-14", 1, 2, @teacher_id, 3);
SET @teacher_id = (SELECT id FROM user WHERE username="teacher_3");
INSERT INTO course (id, name, duration, start_date, end_date, count_student, topic_id, teacher_id, status_id) VALUES (DEFAULT, "Java Basic", "3 week" , "2022-12-15", "2023-01-05", 1, 1, @teacher_id, 1);
