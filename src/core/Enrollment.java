package core;

public class Enrollment {
    private String studentId;
    private String courseCode;
    private EnrollmentStatus status;

    public Enrollment(String studentId, String courseCode) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.status = EnrollmentStatus.ACTIVE;
    }

    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
    public EnrollmentStatus getStatus() { return status; }
    public void setStatus(EnrollmentStatus status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("[StudentID: %s, Course: %s, Status: %s]", studentId, courseCode, status);
    }
}