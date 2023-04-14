DROP DATABASE IF EXISTS elective_db_test;

CREATE DATABASE IF NOT EXISTS elective_db_test;
USE elective_db_test;

-- topic definition

CREATE TABLE `topic` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
);

-- status definition

CREATE TABLE `status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `status` (`status`)
);

-- role definition

CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
);

-- user definition

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `middle_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `password` (`password`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_user_role_id` (`role_id`),
  CONSTRAINT `fk_user_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- course definition

CREATE TABLE `course` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `duration` varchar(45) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `topic_id` int NOT NULL,
  `teacher_id` int NOT NULL,
  `status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `fk_course_topic_id` (`topic_id`),
  KEY `fk_cource_status_id` (`status_id`),
  KEY `fk_cource_teacher_id` (`teacher_id`),
  CONSTRAINT `fk_cource_status_id` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_cource_teacher_id` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_course_topic_id` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- students_on_course definition

CREATE TABLE `students_on_course` (
  `course_id` int NOT NULL,
  `count_student` int NOT NULL,
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `course_id` (`course_id`),
  CONSTRAINT `fk_students_on_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

-- journal definition

CREATE TABLE `journal` (
  `course_id` int NOT NULL,
  `student_id` int NOT NULL,
  `registration_date` date NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `mark` int DEFAULT NULL,
  `status_id` int NOT NULL,
  PRIMARY KEY (`course_id`,`student_id`),
  KEY `fk_journal_student_id` (`student_id`),
  KEY `fk_journal_status_id` (`status_id`),
  CONSTRAINT `fk_journal_status_id` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_journal_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_journal_student_id` FOREIGN KEY (`student_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Inserts
-- -----------------------------------------------------

-- role
INSERT INTO role (id, name) VALUES (1, 'admin');
INSERT INTO role (id, name) VALUES (3, 'student');
INSERT INTO role (id, name) VALUES (2, 'teacher');

-- status
INSERT INTO status (id, status) VALUES (3, 'Finished');
INSERT INTO status (id, status) VALUES (1, 'Open');
INSERT INTO status (id, status) VALUES (2, 'Running');

-- topic
INSERT INTO topic (id, name) VALUES (4, 'Blockchain Technology');
INSERT INTO topic (id, name) VALUES (1, 'Introduction to Web Development');
INSERT INTO topic (id, name) VALUES (2, 'Mobile App Development');
INSERT INTO topic (id, name) VALUES (3, 'Programming Fundamentals');

-- user
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id) VALUES (1, 'asmith-asmith', '5tG6#kL9@', 'Alice', 'Elizabeth', 'Smith', 'alice_smith@example.com', 3);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id) VALUES (2, 'sthompson-sthompson', '9pL6#mF7@', 'Sarah', 'Elizabeth', 'Thompson', 'sarah_thompson@example.com', 2);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id) VALUES (3, 'djackson-djackson', '3gT8$nJ5!', 'David', 'Alexander', 'Jackson', 'david.jackson@example.com', 2);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id) VALUES (4, 'jwhite-jwhite', '7kR4#fH2^', 'Julia', 'Marie', 'White', 'julia.white@example.com', 2);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id) VALUES (5, 'badams-badams', '4rM6#pK9@', 'Benjamin', 'Robert', 'Adams', 'benjamin_adams@example.com', 1);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id) VALUES (6, 'jjohnson-jjohnson', '7pM4$sK1%', 'John', 'Michael', 'Johnson', 'jjohnson@example.com', 3);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id) VALUES (7, 'edavis-edavis', '3dR8#nC2!', 'Emily', 'Jane', 'Davis', 'emily.davis@example.com', 3);

-- course
INSERT INTO course (id, name, duration, start_date, end_date, topic_id, teacher_id, status_id) VALUES (1, 'Web Development Fundamentals', '4 weeks', '2023-05-01', '2023-05-28', 1, 2, 1);
INSERT INTO course (id, name, duration, start_date, end_date, topic_id, teacher_id, status_id) VALUES (2, 'JavaScript for Web Development', '6 weeks', '2023-07-03', '2023-08-13', 1, 2, 1);
INSERT INTO course (id, name, duration, start_date, end_date, topic_id, teacher_id, status_id) VALUES (3, 'HTML and CSS Basics', '2 weeks', '2023-06-05', '2023-06-18', 1, 2, 1);
INSERT INTO course (id, name, duration, start_date, end_date, topic_id, teacher_id, status_id) VALUES (4, 'Introduction to Mobile App Development', '4 weeks', '2023-05-01', '2023-05-29', 2, 3, 1);
INSERT INTO course (id, name, duration, start_date, end_date, topic_id, teacher_id, status_id) VALUES (5, 'Advanced Mobile App Development', '6 weeks', '2023-06-05', '2023-07-17', 2, 3, 1);
INSERT INTO course (id, name, duration, start_date, end_date, topic_id, teacher_id, status_id) VALUES (6, 'Introduction to Python Programming', '4 weeks', '2023-05-01', '2023-05-28', 3, 4, 1);
INSERT INTO course (id, name, duration, start_date, end_date, topic_id, teacher_id, status_id) VALUES (7, 'C++ Programming Essentials', '6 weeks', '2023-06-05', '2023-07-16', 3, 4, 1);
INSERT INTO course (id, name, duration, start_date, end_date, topic_id, teacher_id, status_id) VALUES (8, 'Java Fundamentals for Beginners', '8 weeks', '2023-08-07', '2023-09-29', 3, 4, 1);
INSERT INTO course (id, name, duration, start_date, end_date, topic_id, teacher_id, status_id) VALUES (9, 'Blockchain Fundamentals', '4 weeks', '2023-05-01', '2023-05-28', 4, 2, 1);
INSERT INTO course (id, name, duration, start_date, end_date, topic_id, teacher_id, status_id) VALUES (10, 'Blockchain for Business', '6 weeks', '2023-06-05', '2023-07-17', 4, 3, 1);

-- journal
INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, mark, status_id) VALUES (1, 1, '2023-04-04', '2023-05-01', '2023-05-28', NULL, 1);
INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, mark, status_id) VALUES (1, 7, '2023-03-28', '2023-05-01', '2023-05-28', NULL, 1);
INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, mark, status_id) VALUES (2, 7, '2023-03-29', '2023-07-03', '2023-08-13', NULL, 1);
INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, mark, status_id) VALUES (3, 1, '2023-03-29', '2023-06-05', '2023-06-18', NULL, 1);
INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, mark, status_id) VALUES (3, 6, '2023-03-28', '2023-06-05', '2023-06-18', NULL, 1);
INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, mark, status_id) VALUES (4, 6, '2023-04-04', '2023-05-01', '2023-05-29', NULL, 1);
INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, mark, status_id) VALUES (4, 7, '2023-03-31', '2023-05-01', '2023-05-29', NULL, 1);
INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, mark, status_id) VALUES (6, 1, '2023-03-29', '2023-05-01', '2023-05-28', NULL, 1);
INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, mark, status_id) VALUES (6, 6, '2023-03-29', '2023-05-01', '2023-05-28', NULL, 1);

-- students_on_course
INSERT INTO students_on_course (course_id, count_student) VALUES (1, 2);
INSERT INTO students_on_course (course_id, count_student) VALUES (2, 1);
INSERT INTO students_on_course (course_id, count_student) VALUES (3, 2);
INSERT INTO students_on_course (course_id, count_student) VALUES (4, 2);
INSERT INTO students_on_course (course_id, count_student) VALUES (6, 2);

