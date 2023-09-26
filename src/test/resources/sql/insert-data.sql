INSERT INTO classroom(id, label,availability) values(1, '1234',false);
INSERT INTO course(id, name_course, classroom_id) values (1, 'Python',1);
INSERT INTO homework(id, name, description,due_date,teacher_id ) values(1, 'exercises to do','exercises todo on tomorrow','2023-03-08',1);
INSERT INTO teacher(id, lastname,firstname,phone,birth_date,address,subject,course_id) values(1, 'ARA','AbdelHA','0678905678','2002-03-03','Avenue Paris','JAVA',1);
INSERT INTO comment (id, text , enable,teacher_id) values (1, 'this is a just a comment',false,1);
INSERT INTO student(id, lastname,firstname,phone,birth_date,address,note) values(1, 'rachida','elmo','0677903456','2001-04-04','Avenue Louis pasteur',14.0);
INSERT INTO teacher(id, lastname,firstname,phone,birth_date,address,subject,course_id) values(2, 'rachida','kadi','0678903456','2000-05-15','avenue paris','JAVA', 1);