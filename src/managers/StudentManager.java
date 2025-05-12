package managers;

import core.Student;
import exceptions.EntityExistsException;
import exceptions.EntityNotFoundException;

import java.util.*;

public class StudentManager {
    private Map<String, Student> students = new HashMap<>();

    public void addStudent(Student s) throws EntityExistsException {
        if (students.containsKey(s.getId()))
            throw new EntityExistsException("Student with ID " + s.getId() + " already exists.");
        students.put(s.getId(), s);
    }

    public Student getStudent(String id) throws EntityNotFoundException {
        Student s = students.get(id);
        if (s == null) throw new EntityNotFoundException("Student not found");
        return s;
    }

    public List<Student> listStudents() {
        return new ArrayList<>(students.values());
    }
}