package managers;

import core.Enrollment;
import exceptions.EntityExistsException;
import exceptions.EntityNotFoundException;

import java.util.*;

public class EnrollmentManager {
    private Map<String, Enrollment> enrollments = new HashMap<>();

    private String key(String studentId, String courseCode) {
        return studentId + ":" + courseCode;
    }

    public void enroll(String studentId, String courseCode) throws EntityExistsException {
        String k = key(studentId, courseCode);
        if (enrollments.containsKey(k))
            throw new EntityExistsException("Already enrolled.");
        enrollments.put(k, new Enrollment(studentId, courseCode));
    }

    public void unenroll(String studentId, String courseCode) throws EntityNotFoundException {
        String k = key(studentId, courseCode);
        if (!enrollments.containsKey(k))
            throw new EntityNotFoundException("Enrollment not found");
        enrollments.remove(k);
    }

    public Enrollment getEnrollment(String studentId, String courseCode) throws EntityNotFoundException {
        Enrollment e = enrollments.get(key(studentId, courseCode));
        if (e == null) throw new EntityNotFoundException("Enrollment not found");
        return e;
    }

    public List<Enrollment> listEnrollments() {
        return new ArrayList<>(enrollments.values());
    }
}