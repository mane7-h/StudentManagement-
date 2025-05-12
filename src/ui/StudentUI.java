package ui;

import core.*;
import managers.*;
import utils.FileUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

        // Use Nimbus Look & Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Student Management System", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // Panel with buttons
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 50, 10, 50));

        String[] buttonLabels = {
                "Add Student", "Add Course", "Enroll Student", "Unenroll Student",
                "View Students", "View Courses", "View Enrollments",
                "Save to CSV", "Load from CSV"
        };

        JButton[] buttons = new JButton[buttonLabels.length];

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton btn = new JButton(buttonLabels[i]);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(200, 30));
            btn.setFocusPainted(false);
            panel.add(btn);
            panel.add(Box.createRigidArea(new Dimension(0, 8)));
            buttons[i] = btn;
        }

        // Button actions
        buttons[0].addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Student ID:");
            String name = JOptionPane.showInputDialog("Enter Name:");
            String ageStr = JOptionPane.showInputDialog("Enter Age:");
            try {
                sm.addStudent(new Student(id, name, Integer.parseInt(ageStr)));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        buttons[1].addActionListener(e -> {
            String code = JOptionPane.showInputDialog("Enter Course Code:");
            String titleTxt = JOptionPane.showInputDialog("Enter Title:");
            String creditsStr = JOptionPane.showInputDialog("Enter Credits:");
            try {
                cm.addCourse(new Course(code, titleTxt, Integer.parseInt(creditsStr)));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        buttons[2].addActionListener(e -> {
            String sid = JOptionPane.showInputDialog("Enter Student ID:");
            String cc = JOptionPane.showInputDialog("Enter Course Code:");
            try {
                em.enroll(sid, cc);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        buttons[3].addActionListener(e -> {
            String sid = JOptionPane.showInputDialog("Enter Student ID:");
            String cc = JOptionPane.showInputDialog("Enter Course Code:");
            try {
                em.unenroll(sid, cc);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        buttons[4].addActionListener(e -> {
            DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Name", "Age"}, 0);
            for (Student s : sm.listStudents()) {
                model.addRow(new Object[]{s.getId(), s.getName(), s.getAge()});
            }
            showTable("Students", model);
        });

        buttons[5].addActionListener(e -> {
            DefaultTableModel model = new DefaultTableModel(new Object[]{"Code", "Title", "Credits"}, 0);
            for (Course c : cm.listCourses()) {
                model.addRow(new Object[]{c.getCode(), c.getTitle(), c.getCredits()});
            }
            showTable("Courses", model);
        });

        buttons[6].addActionListener(e -> {
            DefaultTableModel model = new DefaultTableModel(new Object[]{"Student ID", "Course Code", "Status"}, 0);
            for (Enrollment en : em.listEnrollments()) {
                model.addRow(new Object[]{en.getStudentId(), en.getCourseCode(), en.getStatus()});
            }
            showTable("Enrollments", model);
        });

        buttons[7].addActionListener(e -> {
            try {
                FileUtils.saveStudents(sm.listStudents(), "students.csv");
                FileUtils.saveCourses(cm.listCourses(), "courses.csv");
                FileUtils.saveEnrollments(em.listEnrollments(), "enrollments.csv");
                JOptionPane.showMessageDialog(this, "Data saved.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Save error: " + ex.getMessage());
            }
        });

        buttons[8].addActionListener(e -> {
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

        add(panel, BorderLayout.CENTER);
        setSize(400, 550);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showTable(String title, DefaultTableModel model) {
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JFrame tableFrame = new JFrame(title);
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableFrame.add(scrollPane);
        tableFrame.setSize(500, 300);
        tableFrame.setLocationRelativeTo(null);
        tableFrame.setVisible(true);
    }
}
