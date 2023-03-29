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