package cli;

import core.Course;
import core.Student;
import exceptions.EntityExistsException;
import exceptions.EntityNotFoundException;
import managers.*;
import utils.FileUtils;
import core.Enrollment;
import core.EnrollmentStatus;


import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MainCLI {
    private static StudentManager sm = new StudentManager();
    private static CourseManager cm = new CourseManager();
    private static EnrollmentManager em = new EnrollmentManager();
    private static Scanner sc = new Scanner(System.in);

    public static void run(StudentManager smInput, CourseManager cmInput, EnrollmentManager emInput) {
        sm = smInput;
        cm = cmInput;
        em = emInput;
        boolean running = true;
        while (running) {
            showMenu();
            switch (sc.nextLine().trim()) {
                case "1": addStudent(); break;
                case "2": listStudents(); break;
                case "3": addCourse(); break;
                case "4": listCourses(); break;
                case "5": enroll(); break;
                case "6": listEnrollments(); break;
                case "7": save(); break;
                case "8": load(); break;
                case "9": unenroll(); break;
                case "0": running = false; break;
                default: System.out.println("Invalid choice.");
            }
        }
        System.out.println("Goodbye.");
    }

    private static void showMenu() {
        System.out.println("\n1) Add Student\n2) List Students\n3) Add Course\n4) List Courses\n5) Enroll Student\n6) List Enrollments\n7) Save\n8) Load\n9) Unenroll Student\n0) Exit\nChoose:");
    }

    private static void addStudent() {
        try {
            System.out.print("ID: ");
            String id = sc.nextLine();
            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Age: ");
            int age = Integer.parseInt(sc.nextLine());
            sm.addStudent(new Student(id, name, age));
            System.out.println("Student added.");
        } catch (EntityExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listStudents() {
        List<Student> list = sm.listStudents();
        list.forEach(System.out::println);
    }

    private static void addCourse() {
        try {
            System.out.print("Code: ");
            String code = sc.nextLine();
            System.out.print("Title: ");
            String title = sc.nextLine();
            System.out.print("Credits: ");
            int cr = Integer.parseInt(sc.nextLine());
            cm.addCourse(new Course(code, title, cr));
            System.out.println("Course added.");
        } catch (EntityExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listCourses() {
        List<Course> list = cm.listCourses();
        list.forEach(System.out::println);
    }

    private static void enroll() {
        try {
            System.out.print("Student ID: ");
            String sid = sc.nextLine();
            System.out.print("Course Code: ");
            String cc = sc.nextLine();
            em.enroll(sid, cc);
            System.out.println("Enrollment successful.");
        } catch (EntityExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void unenroll() {
        try {
            System.out.print("Student ID: ");
            String sid = sc.nextLine();
            System.out.print("Course Code: ");
            String cc = sc.nextLine();
            em.unenroll(sid, cc);
            System.out.println("Unenrolled successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listEnrollments() {
        em.listEnrollments().forEach(System.out::println);
    }

    private static void save() {
        try {
            FileUtils.saveStudents(sm.listStudents(), "students.csv");
            FileUtils.saveCourses(cm.listCourses(), "courses.csv");
            FileUtils.saveEnrollments(em.listEnrollments(), "enrollments.csv");
            System.out.println("Saved data to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    private static void load() {
        try {
            for (Student s : FileUtils.loadStudents("students.csv")) {
                sm.addStudent(s);
            }
            for (Course c : FileUtils.loadCourses("courses.csv")) {
                cm.addCourse(c);
            }
            for (Enrollment e : FileUtils.loadEnrollments("enrollments.csv")) {
                em.enroll(e.getStudentId(), e.getCourseCode());
                em.getEnrollment(e.getStudentId(), e.getCourseCode()).setStatus(e.getStatus());
            }
            System.out.println("Loaded data from CSV.");
        } catch (Exception e) {
            System.out.println("Error loading: " + e.getMessage());
        }
    }
}