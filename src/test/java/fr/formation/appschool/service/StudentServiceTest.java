package fr.formation.appschool.service;

import fr.formation.appschool.persistence.model.CourseEntity;
import fr.formation.appschool.persistence.repository.*;
import fr.formation.appschool.web.model.*;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@ActiveProfiles("it")
class StudentServiceTest {

    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseService courseService;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    ClassroomService classroomService;
    @Autowired
    ClassroomRepository classroomRepository;
    @Autowired
    HomeworkService homeworkService;
    @Autowired
    HomeworkRepository homeworkRepository;
    @Autowired
    CommentService commentService;
    @Autowired
    FileDocumentService fileDocumentService;
    @Autowired
    CommentRepository commentRepository;
    @Test
    @Sql(
            value = "/sql/clean-db.sql",
            statements = {"INSERT INTO student(id, lastname,firstname,phone,birth_date,address,note) values(1, 'AKA','Abdel','0678904556','2013-03-03','AV Louis Pasteur',13.0) ",
            "INSERT INTO course (id, name_course) values (2, 'Philo')",
            "INSERT INTO course_student(student_id, course_id) values (1, 2)",
    })
    void should_get_all_when_exists() {
        List<Student> all = studentService.findAll();

        List<Course> courses = all.get(0).getCourses();
        System.out.println("courses = " + courses);

        Assertions.assertThat(all).isNotEmpty();
        Assertions.assertThat(all)
                .extracting(Student::getId,
                        Student::getLastname,
                        Student::getFirstname,
                        Student::getPhone,
                        Student::getBirthDate,
                        Student::getAddress,
                        Student::getNote)
                .containsExactlyInAnyOrder(Tuple.tuple(1, "AKA", "Abdel", "0678904556", LocalDate.of(2013, 3, 3), "AV Louis Pasteur", 13.0));


    }

    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements ={
            "INSERT INTO course (id, name_course) values (1, 'Math')",
            "INSERT INTO teacher(id, lastname,firstname,phone,birth_date,address,subject,course_id) values(1, 'ARA','AbdelHA','0678905678','2002-03-03','Avenue Paris','JAVA',1) "})
    void should_get_all_teachers_when_exists() {
        List<Teacher> all = teacherService.findAll();
Course course = all.get(0).getCourse();
System.out.println("course"+course);


        Assertions.assertThat(all).isNotEmpty();
        Assertions.assertThat(all)
                .extracting(Teacher::getId,
                        Teacher::getLastname,
                        Teacher::getFirstname,
                        Teacher::getPhone,
                        Teacher::getBirthDate,
                        Teacher::getAddress,
                        Teacher::getSubject,
                        Teacher::getCourse)
                .containsExactlyInAnyOrder(Tuple.tuple(1, "ARA", "AbdelHA", "0678905678", LocalDate.of(2002,3,3), "Avenue Paris", "JAVA", course));


    }

    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {
            "INSERT INTO course (id, name_course,file_course) values (1, 'JAVA','/tmp')",
                    "INSERT INTO teacher(id, lastname,firstname,phone,birth_date,address,subject,course_id) values(1, 'ARA','AbdelHA','0678905678','2002-03-03','Avenue Paris','JAVA',1) "

    })
    void should_get_all_courses_when_exists() {
        List<Course> all = courseService.findAll();
List<Teacher> teachers = all.get(0).getTeachers();
        byte[] buf =all.get(0).getFileCourse();

        Assertions.assertThat(all).isNotEmpty();
        Assertions.assertThat(all)
                .extracting(Course::getId,
                        Course::getNameCourse,
                        Course::getFileCourse,
                        Course::getTeachers)
                .containsExactlyInAnyOrder(Tuple.tuple(1, "JAVA",buf,teachers));
    }

    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {
            "INSERT INTO course (id, name_course) values (1, 'JAVA')",
            "INSERT INTO course (id, name_course) values (2, 'Philo')",
            "INSERT INTO teacher(id, lastname,firstname,phone,birth_date,address,subject,course_id) values(1, 'ARA','AbdelHA','0678905678','2001-04-04','Avenue Louis pasteur','JAVA', 1)",
    }
    )
    void should_get_teacher_and_courses_when_exists() {
        Teacher teacher = teacherService.findOne(1);


        Assertions.assertThat(teacher).isNotNull();
        Assertions.assertThat(teacher)
                .extracting(Teacher::getId,
                        Teacher::getLastname,
                        Teacher::getFirstname,
                        Teacher::getPhone,
                        Teacher::getBirthDate,
                        Teacher::getAddress,
                        Teacher::getSubject)
                .containsExactlyInAnyOrder(
                        1,
                        "ARA",
                        "AbdelHA",
                        "0678905678",
                        LocalDate.of(2001,4,4),
                        "Avenue Louis pasteur",
                        "JAVA");
        Assertions.assertThat(teacher).isNotNull();

        Course course = teacher.getCourse();
        Assertions.assertThat(course)
                .extracting(Course::getId, Course::getNameCourse)
                .containsExactlyInAnyOrder(1, "JAVA");


    }
    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {
            "INSERT INTO course (id, name_course) values (1, 'JAVA')",
            "INSERT INTO course (id, name_course) values (2, 'Philo')",
            "INSERT INTO teacher(id, lastname,firstname,phone,birth_date,address,subject,course_id) values(1, 'ARA','AbdelHA','0678905678','2001-04-04','Avenue Louis pasteur','JAVA', 1)",
    }
    )
    void should_get_course_and_teacher_when_exists() throws IOException {

        File file = File.createTempFile( "cours-1", "txt", new File("/tmp"));
        FileWriter myWriter = new FileWriter(file.getAbsolutePath());
        myWriter.write("Files in Java might be tricky, but it is fun enough!");
        myWriter.close();

        byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));

        Assertions.assertThat(bytes).isNotEmpty();

  /*      Teacher teacher = teacherService.findOne(1);


        Assertions.assertThat(teacher).isNotNull();
        Assertions.assertThat(teacher)
                .extracting(Teacher::getId,
                        Teacher::getLastname,
                        Teacher::getFirstname,
                        Teacher::getPhone,
                        Teacher::getBirthDate,
                        Teacher::getAddress,
                        Teacher::getSubject)
                .containsExactlyInAnyOrder(
                        1,
                        "ARA",
                        "AbdelHA",
                        "0678905678",
                        LocalDate.of(2001, 4, 4),
                        "Avenue Louis pasteur",
                        "JAVA");
        Assertions.assertThat(teacher).isNotNull();

        Course course = teacher.getCourse();
        Assertions.assertThat(course)
                .extracting(Course::getId, Course::getNameCourse)
                .containsExactlyInAnyOrder(1, "JAVA");*/


    }

    @Test
    @Sql(value = "/sql/clean-db.sql")
    void should_save_teacher_and_courses() {

        CourseEntity courseEntity = CourseEntity.builder()
                .nameCourse("JAVA")
                .build();

        CourseEntity savedCourse = courseRepository.save(courseEntity);


        Teacher entity = Teacher.builder()
                .firstname("AKA")
                .lastname("RAR")
                .phone("0678905678")
                .birthDate(LocalDate.of(2003,3,3))
                .address("Av Louis Pasteur")
                .subject("JAVASCRIPT")
                .course(Course.builder().id(savedCourse.getId()).nameCourse("JAVA").build())
                .build();
        teacherService.save(entity);

        Teacher teacher = teacherService.findOne(1);

        Assertions.assertThat(teacher).isNotNull();
        Assertions.assertThat(teacher)
                .extracting(Teacher::getId, Teacher::getLastname, Teacher::getFirstname,Teacher::getPhone,Teacher::getBirthDate,Teacher::getAddress, Teacher::getSubject)
                .containsExactlyInAnyOrder(1, "AKA", "RAR","0678905678",LocalDate.of(2003,3,3),"Av Louis Pasteur", "JAVASCRIPT");
        Assertions.assertThat(teacher).isNotNull();

        Course course = teacher.getCourse();
        Assertions.assertThat(course)
                .extracting(Course::getId, Course::getNameCourse)
                .containsExactlyInAnyOrder(1, "JAVA");


    }

    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {

            "INSERT INTO student(id, lastname,firstname,phone,birth_date,address,note) values(1, 'ARA','AbdelHA','0640567890','2003-03-03','Av louis pasteur',13.0)",
            "INSERT INTO course (id, name_course) values (2, 'Philo')",
            "INSERT INTO course_student(student_id, course_id) values (1, 2)",
    }
    )
    void should_get_students_and_courses_when_exists() {
        Student student = studentService.findOne(1);

        Assertions.assertThat(student).isNotNull();
        Assertions.assertThat(student)
                .extracting(Student::getId, Student::getLastname, Student::getFirstname,Student::getPhone,Student::getBirthDate,Student::getAddress, Student::getNote)
                .containsExactlyInAnyOrder(1, "ARA", "AbdelHA","0640567890",LocalDate.of(2003,3,3),"Av louis pasteur", 13.0);
        Assertions.assertThat(student).isNotNull();

        List<Course> courses = student.getCourses();
        Assertions.assertThat(courses)
                .extracting(Course::getId, Course::getNameCourse)
                .containsExactlyInAnyOrder(
                        Tuple.tuple(2, "Philo")
                );


    }

    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {

            "INSERT INTO course (id, name_course) values (2, 'Philo')",

    }
    )

    void should_save_teacher() {
        Course course = Course.builder().id(2)

                .build();
        Teacher teacher = Teacher.builder()
                .firstname("RAD")
                .lastname("ELMO")
                .phone("0640356789")
                .birthDate(LocalDate.of(2003,3,3))
                .address("Av Louis pasteur")
                .subject("Math")
                .course(course)
                .build();
        teacherService.save(teacher);

    }

    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {
            "INSERT INTO course (id, name_course) values (1, 'Physique')",
            "INSERT INTO teacher(id, lastname, firstname, subject, course_id) values(1, 'ARA','AbdelHA','javascript',2)",
    }
    )
    void should_delete_teacher() {
        //courseRepository.findAll();
       // courseRepository.deleteById(2);
        teacherRepository.deleteById(1);
        /*teacherRepository.deleteById(1);

        Optional<TeacherEntity> teacherOps = teacherRepository.findById(1);


        Assertions.assertThat(teacherOps).isEmpty();
        Assertions.assertThat(teacher)
                .extracting(Teacher::getId, Teacher::getLastname, Teacher::getFirstname, Teacher::getSubject)
                .containsExactlyInAnyOrder(1, "ARA","AbdelHA","javascript");
        Assertions.assertThat(teacher).isNotNull();

        Course course = teacher.getCourse();
        Assertions.assertThat(course)
                .extracting(Course::getId,Course::getNameCourse)
                .containsExactlyInAnyOrder(1,"Physique");



      teacherService.deleteTeacher(1);*/

    }

    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {
            "INSERT INTO classroom(id, label,availability) values(1, '1234',false)",
            "INSERT INTO course(id, name_course, classroom_id) values (2, 'Philo',1)",


    })
    void should_get_all_classroom_when_exists() {
        List<Classroom> all = classroomService.findAll();

        List<Course> courses = List.of(Course.builder().nameCourse("Philo").id(2).build());

        Assertions.assertThat(all).isNotEmpty();
        Assertions.assertThat(all)
                .extracting(Classroom::getId, Classroom::getLabel, Classroom::isAvailability, Classroom::getCourses)
                .containsExactlyInAnyOrder(Tuple.tuple(
                        1, "1234", false, courses));


    }
    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {
            "INSERT INTO classroom(id, label,availability) values(1, '1234',false)",
            "INSERT INTO course(id, name_course, classroom_id) values (1, 'Python',1)",
            "INSERT INTO teacher(id, lastname,firstname,phone,birth_date,address,subject,course_id) values(1, 'ARA','AKA','0678903456','2000-05-15','Avenue Louis pasteur','JAVA',1)",
            "INSERT INTO homework(id, name, description,due_date,teacher_id ) values(1, 'exercises to do','exercises todo on tomorrow','2023-03-07',1)",


    })
    void should_get_all_homework_when_exists() {
        List<Homework> all = homeworkService.findAll();

        Teacher teacher = all.get(0).getTeacher();

        Assertions.assertThat(all).isNotEmpty();
        Assertions.assertThat(all)
                .extracting(Homework::getId, Homework::getName, Homework::getDescription, Homework::getDueDate,Homework::getTeacher)
                .containsExactlyInAnyOrder(Tuple.tuple(
                        1L, "exercises to do","exercises todo on tomorrow",LocalDate.of(2023,3,7), teacher));


    }
    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {
            "INSERT INTO course(id, name_course, classroom_id) values (2, 'Philo',1)",
            "INSERT INTO classroom(id, label,availability) values(1, '1234',false)",



    })
    void should_get_classroom_and_courses_when_exists() {
        Classroom classroom =  classroomService.findOne(1);


        Assertions.assertThat(classroom).isNotNull();
        Assertions.assertThat(classroom)
                .extracting(Classroom::getId,
                        Classroom::getLabel,
                        Classroom::isAvailability)
                .containsExactlyInAnyOrder(
                        1,
                        "1234",
                        false);
        Assertions.assertThat(classroom).isNotNull();

        List<Course> courses = classroom.getCourses();
        Assertions.assertThat(courses)
                .extracting(Course::getId, Course::getNameCourse)
                .containsExactlyInAnyOrder(
                        Tuple.tuple(2, "Philo"));


    }
    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {
            "INSERT INTO classroom(id, label,availability) values(1, '1234',false)",
            "INSERT INTO course(id, name_course, classroom_id) values (1, 'Python',1)",
            "INSERT INTO teacher(id, lastname,firstname,phone,birth_date,address,subject,course_id) values(1, 'ARA','AKA','0678903456','2000-05-15','Avenue Louis pasteur','JAVA',1)",
            "INSERT INTO homework(id, name, description,due_date,teacher_id ) values(1, 'exercises to do','exercises todo on tomorrow','2023-03-08',1)",


    })
    void should_get_homework_and_teachers_when_exists() {
        Homework homework =  homeworkService.findOne(1L);


        Assertions.assertThat(homework).isNotNull();
        Assertions.assertThat(homework)
                .extracting(Homework::getId,
                        Homework::getName,
                       Homework::getDescription,
                        Homework::getDueDate)
                .containsExactlyInAnyOrder(
                        1L,
                        "exercises to do",
                        "exercises todo on tomorrow",
                        LocalDate.of(2023,3,8));
        Assertions.assertThat(homework).isNotNull();

       Teacher teacher = homework.getTeacher();
        Assertions.assertThat(teacher)
                .extracting(Teacher::getId,
                        Teacher::getLastname,
                        Teacher::getFirstname,
                        Teacher::getPhone,
                        Teacher::getBirthDate,
                        Teacher::getAddress,
                        Teacher::getSubject)
                .containsExactlyInAnyOrder(
                        1, "ARA","AKA","0678903456",LocalDate.of(2000,5,15),"Avenue Louis pasteur","JAVA");


    }
    @Test
    void should_save_classroom_and_courses() {

        Classroom entity = Classroom.builder()
                .label("12345")
                .availability(false)
                .courses(List.of(Course.builder()
                        .nameCourse("JAVA")
                        .build(),
                        Course.builder()
                                .nameCourse("Python")
                                .build()))

                .build();

        classroomService.save(entity);

//       Classroom classroom = classroomService.findOne(1);
//
//        Assertions.assertThat(classroom).isNotNull();
//        Assertions.assertThat(classroom)
//                .extracting(Classroom::getId, Classroom::getLabel,Classroom::isAvailability)
//                .containsExactlyInAnyOrder(1,"12345",false);
//        Assertions.assertThat(classroom).isNotNull();
//
//       List <Course> courses = classroom.getCourses();
//        Assertions.assertThat(courses)
//                .extracting(Course::getId, Course::getNameCourse)
//                .containsExactlyInAnyOrder(Tuple.tuple(1, "JAVA"),Tuple.tuple(2,"Python"));



    }
    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {

            "INSERT INTO course(id, name_course, classroom_id) values (1, 'Python',1)",
            "INSERT INTO teacher(id, lastname,firstname,phone,birth_date,address,subject,course_id) values(1, 'ARA','AKA','0678903456','2000-05-15','Avenue Louis pasteur','JAVA',1)",



    })
    void should_save_homework_and_teacher() {


        Teacher teacher = Teacher.builder()
                .id(1)
                .course(Course.builder().id(1).build())
                .build();
        Homework entity = Homework.builder()
                .name("exercises 3")
                .description("exercises 3 geometry")
                .dueDate(LocalDate.of(2023,3,7))
                .teacher(teacher)
                        .build();



        homeworkService.save(entity);

}
    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {

            "INSERT INTO course(id, name_course, classroom_id) values (1, 'Python',1)",
    })
    void should_save_student_and_course() {


        List<Course> courseList = List.of(Course.builder().id(1).build());

        Student entity = Student.builder()
                .lastname("Ines")
                .firstname("KADI")
                .phone("0678904567")
                .birthDate(LocalDate.of(2000,5,15))
                .address("Av Louis pasteur")
                .note(14.5)
                .courses(courseList)
                .build();
        studentService.save(entity);

    }
    @Test
    @Sql(value = {"/sql/clean-db.sql", "/sql/insert-data.sql"})
    void should_get_all_comment_when_exists() {
        List<Comment> all = commentService.findAll();
        Teacher teacher = all.get(0).getTeacher();
        //System.out.println("teacher"+teacher);


        Assertions.assertThat(all).isNotEmpty();
        Assertions.assertThat(all)
                .extracting(Comment::getId,
                       Comment::getText,
                       Comment::isEnable,
                        Comment::getTeacher

                       )
                .containsExactlyInAnyOrder(Tuple.tuple(1L, "this is a just a comment", false,teacher));


    }
    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {
                    "INSERT INTO course(id, name_course, classroom_id) values (1, 'Python',1)",
                    "INSERT INTO homework(id, name, description,due_date,teacher_id ) values(1, 'exercises to do','exercises todo on tomorrow','2023-03-08',1)",
                    "INSERT INTO teacher(id, lastname,firstname,phone,birth_date,address,subject,course_id) values(1, 'ARA','AKA','0678903456','2000-05-15','Avenue Louis pasteur','JAVA',1)",
                    "INSERT INTO comment (id, text , enable,teacher_id) values (1, 'this is a just a comment',false,1)"



            })
    void should_get_comment_and_teacher_when_exists() {
        Comment comment =  commentService.findOne(1L);


        Assertions.assertThat(comment).isNotNull();
        Assertions.assertThat(comment)
                .extracting(Comment::getId,
                        Comment::getText,
                       Comment::isEnable)
                .containsExactlyInAnyOrder(
                        1L,
                        "this is a just a comment",
                        false);
        Assertions.assertThat(comment).isNotNull();

        Teacher teacher = comment.getTeacher();
        Assertions.assertThat(teacher)
                .extracting(Teacher::getId,
                        Teacher::getLastname,
                        Teacher::getFirstname,
                        Teacher::getPhone,
                        Teacher::getBirthDate,
                        Teacher::getAddress,
                        Teacher::getSubject)
                .containsExactlyInAnyOrder(
                        1, "ARA","AKA","0678903456",LocalDate.of(2000,5,15),"Avenue Louis pasteur","JAVA");


    }
    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {

                    "INSERT INTO course(id, name_course, classroom_id) values (1, 'Python',1)",
                    "INSERT INTO teacher(id, lastname,firstname,phone,birth_date,address,subject,course_id) values(1, 'ARA','AKA','0678903456','2000-05-15','Avenue Louis pasteur','JAVA',1)",



            })
    void should_save_comment_and_teacher() {


        Teacher teacher = Teacher.builder()
                .id(1)
                .course(Course.builder().id(1).build())
                .build();
        Comment entity = Comment.builder()
                .text("exercises 3")
                .enable(false)
                .teacher(teacher)
                .build();



       commentService.save(entity);

    }
    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {
                    "INSERT INTO student(id, lastname,firstname,phone,birth_date,address,note) values(1, 'rachida','elmo','0677903456','2001-04-04','Avenue Louis pasteur',14.0)",

                    "INSERT INTO classroom (id, label,availability) values (1, '1234',false)",

                    "INSERT INTO course(id, name_course, classroom_id) values (1, 'Python',1)",
                    "INSERT INTO teacher(id, lastname,firstname,phone,birth_date,address,subject,course_id) values(2, 'rachida','kadi','0678903456','2000-05-15','avenue paris','JAVA', 1)",

            }
    )
    void should_get_course_and_teachers_when_exists() {
        Course course = courseService.findOne(1);

        byte[] buf =course.getFileCourse();
        Assertions.assertThat(course).isNotNull();
        Assertions.assertThat(course)
                .extracting(Course::getId,
                        Course::getNameCourse,
                        Course::getFileCourse)
                .containsExactlyInAnyOrder(
                        1,
                       "Python",
                        buf);
        Assertions.assertThat(course).isNotNull();

        List<Teacher> teacherList= course.getTeachers();
        Assertions.assertThat(teacherList)
                .extracting(Teacher::getId,
                        Teacher::getLastname,
                        Teacher::getFirstname,
                        Teacher::getPhone,
                        Teacher::getBirthDate,
                        Teacher::getAddress,
                        Teacher::getSubject)
                .containsExactlyInAnyOrder(Tuple.tuple(2, "rachida","kadi","0678903456",LocalDate.of(2000,5,15),"avenue paris","JAVA"));
        Assertions.assertThat(course).isNotNull();

       /* List<Student> studentList= course.getStudents();
        Assertions.assertThat(studentList)
                .extracting(Student::getId,
                        Student::getLastname,
                        Student::getFirstname,
                        Student::getPhone,
                        Student::getBirthDate,
                        Student::getAddress,
                        Student::getNote)
                .containsExactlyInAnyOrder(Tuple.tuple(1, "rachida","elmo","0677903456",LocalDate.of(2001,4,4),"Avenue Louis pasteur",14.0));
        Assertions.assertThat(course).isNotNull();

       Classroom classroom= course.getClassroom();
        Assertions.assertThat(classroom)
                .extracting(Classroom::getId,
                        Classroom::getLabel,
                        Classroom::isAvailability)
                .containsExactlyInAnyOrder(1, "1234",false);
*/

    }
    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {
                    "INSERT INTO student(id, lastname,firstname,phone,birth_date,address,note) values(1, 'rachida','elmo','0677903456','2001-04-04','Avenue Louis pasteur',14.0)",

                    "INSERT INTO classroom (id, label,availability) values (1, '1234',false)",

                    "INSERT INTO course(id, name_course, classroom_id) values (1, 'Python',1)",
                    "INSERT INTO teacher(id, lastname,firstname,phone,birth_date,address,subject,course_id) values(1, 'rachida','kadi','0678903456','2000-05-15','avenue paris','JAVA', 1)",

            }
    )
    void should_get_course_and_classroom_when_exists() {
        Course course = courseService.findOneCourseAndClassroom(1);

        byte[] buf =course.getFileCourse();
        Assertions.assertThat(course).isNotNull();
        Assertions.assertThat(course)
                .extracting(Course::getId,
                        Course::getNameCourse,
                        Course::getFileCourse)
                .containsExactlyInAnyOrder(
                        1,
                        "Python",
                        buf);
        Assertions.assertThat(course).isNotNull();

//        List<Teacher> teacherList= course.getTeachers();
//        Assertions.assertThat(teacherList)
//                .extracting(Teacher::getId,
//                        Teacher::getLastname,
//                        Teacher::getFirstname,
//                        Teacher::getPhone,
//                        Teacher::getBirthDate,
//                        Teacher::getAddress,
//                        Teacher::getSubject)
//                .containsExactlyInAnyOrder(Tuple.tuple(1, "rachida","kadi","0678903456",LocalDate.of(2000,5,15),"avenue paris","JAVA"));
//        Assertions.assertThat(course).isNotNull();

       /* List<Student> studentList= course.getStudents();
        Assertions.assertThat(studentList)
                .extracting(Student::getId,
                        Student::getLastname,
                        Student::getFirstname,
                        Student::getPhone,
                        Student::getBirthDate,
                        Student::getAddress,
                        Student::getNote)
                .containsExactlyInAnyOrder(Tuple.tuple(1, "rachida","elmo","0677903456",LocalDate.of(2001,4,4),"Avenue Louis pasteur",14.0));
        Assertions.assertThat(course).isNotNull();*/

       Classroom classroom= course.getClassroom();
        Assertions.assertThat(classroom)
                .extracting(Classroom::getId,
                        Classroom::getLabel,
                        Classroom::isAvailability)
                .containsExactlyInAnyOrder(1, "1234",false);


    }
    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {
                    "INSERT INTO student(id, lastname,firstname,phone,birth_date,address,note) values(1, 'rachida','elmo','0677903456','2001-04-04','Avenue Louis pasteur',14.0)",

                    "INSERT INTO classroom (id, label,availability) values (1, '1234',false)",
                    "INSERT INTO course_student (student_id,course_id) values (1,1)",
                    "INSERT INTO course(id, name_course, classroom_id) values (1, 'Python',1)",
                    "INSERT INTO teacher(id, lastname,firstname,phone,birth_date,address,subject,course_id) values(1, 'rachida','kadi','0678903456','2000-05-15','avenue paris','JAVA', 1)",

            }
    )
    void should_get_course_and_student_when_exists() {
        Course course = courseService.findOneCourseAndStudent(1);

        byte[] buf =course.getFileCourse();
        Assertions.assertThat(course).isNotNull();
        Assertions.assertThat(course)
                .extracting(Course::getId,
                        Course::getNameCourse,
                        Course::getFileCourse)
                .containsExactlyInAnyOrder(
                        1,
                        "Python",
                        buf);
        Assertions.assertThat(course).isNotNull();

//        List<Teacher> teacherList= course.getTeachers();
//        Assertions.assertThat(teacherList)
//                .extracting(Teacher::getId,
//                        Teacher::getLastname,
//                        Teacher::getFirstname,
//                        Teacher::getPhone,
//                        Teacher::getBirthDate,
//                        Teacher::getAddress,
//                        Teacher::getSubject)
//                .containsExactlyInAnyOrder(Tuple.tuple(1, "rachida","kadi","0678903456",LocalDate.of(2000,5,15),"avenue paris","JAVA"));
//        Assertions.assertThat(course).isNotNull();

       List<Student> studentList= course.getStudents();
        Assertions.assertThat(studentList)
                .extracting(Student::getId,
                        Student::getLastname,
                        Student::getFirstname,
                        Student::getPhone,
                        Student::getBirthDate,
                        Student::getAddress,
                        Student::getNote)
                .containsExactlyInAnyOrder(Tuple.tuple(1, "rachida","elmo","0677903456",LocalDate.of(2001,4,4),"Avenue Louis pasteur",14.0));
        Assertions.assertThat(course).isNotNull();

//        Classroom classroom= course.getClassroom();
//        Assertions.assertThat(classroom)
//                .extracting(Classroom::getId,
//                        Classroom::getLabel,
//                        Classroom::isAvailability)
//                .containsExactlyInAnyOrder(1, "1234",false);


    }
    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {

                    "INSERT INTO homework(id, name, description,due_date,teacher_id ) values(1, 'exercises to do','exercises todo on tomorrow','2023-03-08',1)",
                    "INSERT INTO teacher(id, lastname,firstname,phone,birth_date,address,subject,course_id) values(1, 'rachida','kadi','0678903456','2000-05-15','avenue paris','JAVA', 1)",
            })
    void should_save_course_and_teacher() throws IOException {

        File file = File.createTempFile( "cours-1", "txt", new File("/tmp"));
        FileWriter myWriter = new FileWriter(file.getAbsolutePath());
        myWriter.write("Files in Java might be tricky, but it is fun enough!");
        myWriter.close();

        byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));



        Assertions.assertThat(bytes).isNotEmpty();

        List<Homework> homeworkList =List.of(Homework.builder().id(1L).build());
        List<Teacher> teacherList = List.of(Teacher.builder().id(1).homeworks(homeworkList).build());
        Course entity = Course.builder()
                .nameCourse("Python")
                .fileCourse(bytes)
                .teachers(teacherList)
                .build();

       courseService.save(entity);

    }
    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {
                    "INSERT INTO course(id, name_course, classroom_id) values (1, 'Python',1)",

                    "INSERT INTO student(id, lastname,firstname,email,phone,birth_date,address,note) values(1, 'rachida','elmo','adicharidak@hotmail.com','0677903456','2001-04-04','Avenue Louis pasteur',14.0)",
                    "INSERT INTO course_student (student_id,course_id) values (1,1)",
                    "INSERT INTO teacher(id, lastname,firstname,email,phone,birth_date,address,subject,course_id) values(1, 'rachida','kadi','adicharidak@yahoo.com','0678903456','2000-05-15','avenue paris','JAVA', 1)",
            })
    void should_send_comment_teacher_to_student() {


        Teacher teacher = Teacher.builder().id(1).email("adicharidak@yahoo.com").build();
     Student student = Student.builder().id(1).email("adicharidak@hotmail.com").build();
      Comment entity = Comment.builder()
              .teacher(teacher)
              .student(student)
              .text("This is just a comment for student")

                .build();

        commentService.sendComment(1, 1, entity.getText());

    }
    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {
                    "INSERT INTO course (id, name_course,file_course) values (2, 'JavaScript','/tmp')",
                    "INSERT INTO file_document(id,name,description,type,content,course_id) values(1,'TD','TD Maths','pdf','/tmp',2) "
            })
    void should_get_all_file_document_when_exists() {
        List<FileDocument> all = fileDocumentService.findAll();

        byte[] buf =all.get(0).getContent();

        Assertions.assertThat(all).isNotEmpty();
        Assertions.assertThat(all)
                .extracting(FileDocument::getId,
                        FileDocument::getName,
                        FileDocument::getDescription,
                        FileDocument::getType,
                        FileDocument::getContent)
                .containsExactlyInAnyOrder(Tuple.tuple(1L, "TD","TD Maths","pdf",buf));
    }
    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {
                    "INSERT INTO student(id, lastname,firstname,phone,birth_date,address,note) values(1, 'rachida','elmo','0677903456','2001-04-04','Avenue Louis pasteur',14.0)",

                    "INSERT INTO classroom (id, label,availability) values (1, '1234',false)",

                    "INSERT INTO course(id, name_course, classroom_id) values (1, 'Python',1)",
                    "INSERT INTO teacher(id, lastname,firstname,phone,birth_date,address,subject,course_id) values(2, 'rachida','kadi','0678903456','2000-05-15','avenue paris','JAVA', 1)",
                    "INSERT INTO file_document(id,name,description,type,content,course_id) values(2,'TP','TP Physique','pdf','/tmp',1) "

            }
    )
    void should_get_file_document_and_course_when_exists() {
        FileDocument fileDocument = fileDocumentService.findOne(2L);

        byte[] buf =fileDocument.getContent();
        Assertions.assertThat(fileDocument).isNotNull();
        Assertions.assertThat(fileDocument)
                .extracting(FileDocument::getId,
                        FileDocument::getName,
                        FileDocument::getDescription,
                        FileDocument::getType,
                        FileDocument::getContent)
                .containsExactlyInAnyOrder(
                        2L,
                        "TP",
                        "TP Physique",
                        "pdf",
                        buf);
        Assertions.assertThat(fileDocument).isNotNull();

    Course course= fileDocument.getCourse();
        byte[] buf1 =course.getFileCourse();
        Assertions.assertThat(course)
                .extracting(Course::getId,
                        Course::getNameCourse,
                        Course::getFileCourse)


                .containsExactlyInAnyOrder(1,"Python",buf1);
        Assertions.assertThat(course).isNotNull();



    }

    @Test
    @Sql(value = "/sql/clean-db.sql",
            statements = {


                    "INSERT INTO classroom (id, label,availability) values (1, '1234',false)",
                    "INSERT INTO course(id, name_course, classroom_id) values (1, 'Python',1)",

            })
    void should_save_file_document_and_teacher() throws IOException {

        File file = File.createTempFile( "cours-1", "txt", new File("/tmp"));
        FileWriter myWriter = new FileWriter(file.getAbsolutePath());
        myWriter.write("Files in Java might be tricky, but it is fun enough!");
        myWriter.close();

        byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));



        Assertions.assertThat(bytes).isNotEmpty();

       Course course = Course.builder().id(1).build();

        FileDocument entity = FileDocument.builder()
                .name("TD")
                .description("TD Maths")
                .type("pdf")
                .content(bytes)
                .course(course)
                .build();

        fileDocumentService.save(entity);

    }
}