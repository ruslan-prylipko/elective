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