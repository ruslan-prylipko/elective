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