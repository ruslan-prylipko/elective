INSERT INTO user_status (id, name) VALUES (1, 'unlocked');
INSERT INTO user_status (id, name) VALUES (2, 'locked');
INSERT INTO role (id, name) VALUES (1, 'admin');
INSERT INTO role (id, name) VALUES (3, 'student');
INSERT INTO role (id, name) VALUES (2, 'teacher');
INSERT INTO status (id, status) VALUES (3, 'Finished');
INSERT INTO status (id, status) VALUES (1, 'Open');
INSERT INTO status (id, status) VALUES (2, 'Running');
INSERT INTO topic (id, name) VALUES (4, 'Blockchain Technology');
INSERT INTO topic (id, name) VALUES (1, 'Introduction to Web Development');
INSERT INTO topic (id, name) VALUES (2, 'Mobile App Development');
INSERT INTO topic (id, name) VALUES (3, 'Programming Fundamentals');
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id, user_status_id) VALUES (1, 'asmith-asmith', '5tG6#kL9@', 'Alice', 'Elizabeth', 'Smith', 'alice_smith@example.com', 3, 1);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id, user_status_id) VALUES (2, 'sthompson-sthompson', '9pL6#mF7@', 'Sarah', 'Elizabeth', 'Thompson', 'sarah_thompson@example.com', 2, 1);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id, user_status_id) VALUES (3, 'djackson-djackson', '3gT8$nJ5!', 'David', 'Alexander', 'Jackson', 'david.jackson@example.com', 2, 1);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id, user_status_id) VALUES (4, 'jwhite-jwhite', '7kR4#fH2^', 'Julia', 'Marie', 'White', 'julia.white@example.com', 2, 1);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id, user_status_id) VALUES (5, 'badams-badams', '4rM6#pK9@', 'Benjamin', 'Robert', 'Adams', 'benjamin_adams@example.com', 1, 1);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id, user_status_id) VALUES (6, 'jjohnson-jjohnson', '7pM4$sK1%', 'John', 'Michael', 'Johnson', 'jjohnson@example.com', 3, 1);
INSERT INTO user (id, username, password, first_name, middle_name, last_name, email, role_id, user_status_id) VALUES (7, 'edavis-edavis', '3dR8#nC2!', 'Emily', 'Jane', 'Davis', 'emily.davis@example.com', 3, 1);
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
INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, mark, status_id) VALUES (1, 1, '2023-04-04', '2023-05-01', '2023-05-28', NULL, 1);
INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, mark, status_id) VALUES (1, 7, '2023-03-28', '2023-05-01', '2023-05-28', NULL, 1);
INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, mark, status_id) VALUES (2, 7, '2023-03-29', '2023-07-03', '2023-08-13', NULL, 1);
INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, mark, status_id) VALUES (3, 1, '2023-03-29', '2023-06-05', '2023-06-18', NULL, 1);
INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, mark, status_id) VALUES (3, 6, '2023-03-28', '2023-06-05', '2023-06-18', NULL, 1);
INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, mark, status_id) VALUES (4, 6, '2023-04-04', '2023-05-01', '2023-05-29', NULL, 1);
INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, mark, status_id) VALUES (4, 7, '2023-03-31', '2023-05-01', '2023-05-29', NULL, 1);
INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, mark, status_id) VALUES (6, 1, '2023-03-29', '2023-05-01', '2023-05-28', NULL, 1);
INSERT INTO journal (course_id, student_id, registration_date, start_date, end_date, mark, status_id) VALUES (6, 6, '2023-03-29', '2023-05-01', '2023-05-28', NULL, 1);