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