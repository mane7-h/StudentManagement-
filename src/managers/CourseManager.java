package managers;

import core.Course;
import exceptions.EntityExistsException;
import exceptions.EntityNotFoundException;

import java.util.*;

public class CourseManager {
    private Map<String, Course> courses = new HashMap<>();

    public void addCourse(Course c) throws EntityExistsException {
        if (courses.containsKey(c.getCode()))
            throw new EntityExistsException("Course " + c.getCode() + " already exists.");
        courses.put(c.getCode(), c);
    }

    public Course getCourse(String code) throws EntityNotFoundException {
        Course c = courses.get(code);
        if (c == null) throw new EntityNotFoundException("Course not found");
        return c;
    }

    public List<Course> listCourses() {
        return new ArrayList<>(courses.values());
    }
}