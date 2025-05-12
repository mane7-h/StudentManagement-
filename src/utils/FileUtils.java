package utils;

import core.*;

import java.io.*;
import java.util.*;

public class FileUtils {
    public static void saveStudents(List<Student> students, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Student s : students) {
                writer.printf("%s,%s,%d\n", s.getId(), s.getName(), s.getAge());
            }
        }
    }

    public static List<Student> loadStudents(String filename) throws IOException {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                students.add(new Student(parts[0], parts[1], Integer.parseInt(parts[2])));
            }
        }
        return students;
    }

    public static void saveCourses(List<Course> courses, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Course c : courses) {
                writer.printf("%s,%s,%d\n", c.getCode(), c.getTitle(), c.getCredits());
            }
        }
    }

    public static List<Course> loadCourses(String filename) throws IOException {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                courses.add(new Course(parts[0], parts[1], Integer.parseInt(parts[2])));
            }
        }
        return courses;
    }

    public static void saveEnrollments(List<Enrollment> enrollments, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Enrollment e : enrollments) {
                writer.printf("%s,%s,%s\n", e.getStudentId(), e.getCourseCode(), e.getStatus().name());
            }
        }
    }

    public static List<Enrollment> loadEnrollments(String filename) throws IOException {
        List<Enrollment> enrollments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Enrollment e = new Enrollment(parts[0], parts[1]);
                e.setStatus(EnrollmentStatus.valueOf(parts[2]));
                enrollments.add(e);
            }
        }
        return enrollments;
    }
}