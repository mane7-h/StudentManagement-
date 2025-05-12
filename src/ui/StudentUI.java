package ui;

import core.*;
import managers.*;
import utils.FileUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class StudentUI extends JFrame {
    private final StudentManager sm;
    private final CourseManager cm;
    private final EnrollmentManager em;

    public StudentUI(StudentManager sm, CourseManager cm, EnrollmentManager em) {
        this.sm = sm;
        this.cm = cm;
        this.em = em;

        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(9, 1, 5, 5));

        JButton addStudentBtn = new JButton("Add Student");
        JButton addCourseBtn = new JButton("Add Course");
        JButton enrollBtn = new JButton("Enroll Student");
        JButton unenrollBtn = new JButton("Unenroll Student");
        JButton viewStudentsBtn = new JButton("View Students");
        JButton viewCoursesBtn = new JButton("View Courses");
        JButton viewEnrollmentsBtn = new JButton("View Enrollments");
        JButton saveBtn = new JButton("Save to CSV");
        JButton loadBtn = new JButton("Load from CSV");

        addStudentBtn.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Student ID:");
            String name = JOptionPane.showInputDialog("Enter Name:");
            String ageStr = JOptionPane.showInputDialog("Enter Age:");
            try {
                sm.addStudent(new Student(id, name, Integer.parseInt(ageStr)));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        addCourseBtn.addActionListener(e -> {
            String code = JOptionPane.showInputDialog("Enter Course Code:");
            String title = JOptionPane.showInputDialog("Enter Title:");
            String creditsStr = JOptionPane.showInputDialog("Enter Credits:");
            try {
                cm.addCourse(new Course(code, title, Integer.parseInt(creditsStr)));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        enrollBtn.addActionListener(e -> {
            String sid = JOptionPane.showInputDialog("Enter Student ID:");
            String cc = JOptionPane.showInputDialog("Enter Course Code:");
            try {
                em.enroll(sid, cc);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        unenrollBtn.addActionListener(e -> {
            String sid = JOptionPane.showInputDialog("Enter Student ID:");
            String cc = JOptionPane.showInputDialog("Enter Course Code:");
            try {
                em.unenroll(sid, cc);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        viewStudentsBtn.addActionListener(e -> {
            DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Name", "Age"}, 0);
            for (Student s : sm.listStudents()) {
                model.addRow(new Object[]{s.getId(), s.getName(), s.getAge()});
            }
            showTable("Students", model);
        });

        viewCoursesBtn.addActionListener(e -> {
            DefaultTableModel model = new DefaultTableModel(new Object[]{"Code", "Title", "Credits"}, 0);
            for (Course c : cm.listCourses()) {
                model.addRow(new Object[]{c.getCode(), c.getTitle(), c.getCredits()});
            }
            showTable("Courses", model);
        });

        viewEnrollmentsBtn.addActionListener(e -> {
            DefaultTableModel model = new DefaultTableModel(new Object[]{"Student ID", "Course Code", "Status"}, 0);
            for (Enrollment en : em.listEnrollments()) {
                model.addRow(new Object[]{en.getStudentId(), en.getCourseCode(), en.getStatus()});
            }
            showTable("Enrollments", model);
        });

        saveBtn.addActionListener(e -> {
            try {
                FileUtils.saveStudents(sm.listStudents(), "students.csv");
                FileUtils.saveCourses(cm.listCourses(), "courses.csv");
                FileUtils.saveEnrollments(em.listEnrollments(), "enrollments.csv");
                JOptionPane.showMessageDialog(this, "Data saved.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Save error: " + ex.getMessage());
            }
        });

        loadBtn.addActionListener(e -> {
            try {
                for (Student s : FileUtils.loadStudents("students.csv")) sm.addStudent(s);
                for (Course c : FileUtils.loadCourses("courses.csv")) cm.addCourse(c);
                for (Enrollment en : FileUtils.loadEnrollments("enrollments.csv")) {
                    em.enroll(en.getStudentId(), en.getCourseCode());
                    em.getEnrollment(en.getStudentId(), en.getCourseCode()).setStatus(en.getStatus());
                }
                JOptionPane.showMessageDialog(this, "Data loaded.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Load error: " + ex.getMessage());
            }
        });

        panel.add(addStudentBtn);
        panel.add(addCourseBtn);
        panel.add(enrollBtn);
        panel.add(unenrollBtn);
        panel.add(viewStudentsBtn);
        panel.add(viewCoursesBtn);
        panel.add(viewEnrollmentsBtn);
        panel.add(saveBtn);
        panel.add(loadBtn);

        add(panel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    private void showTable(String title, DefaultTableModel model) {
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JFrame tableFrame = new JFrame(title);
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableFrame.add(scrollPane);
        tableFrame.setSize(500, 300);
        tableFrame.setVisible(true);
    }
}