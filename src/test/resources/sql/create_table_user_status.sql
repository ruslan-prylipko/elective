CREATE TABLE `user_status` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(45) NOT NULL,
	PRIMARY KEY (`id`),
  	UNIQUE KEY `name` (`name`)
);