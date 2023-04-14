CREATE TABLE `students_on_course` (
  `course_id` int NOT NULL,
  `count_student` int NOT NULL,
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `course_id` (`course_id`),
  CONSTRAINT `fk_students_on_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);