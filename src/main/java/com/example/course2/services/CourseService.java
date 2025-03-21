package com.example.course2.services;

import com.example.course2.controllers.AdminController;
import com.example.course2.models.Course;
import com.example.course2.models.Role;
import com.example.course2.models.User;
import com.example.course2.repositories.CourseRepository;
import com.example.course2.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing courses.
 */
@Service
public class CourseService {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Add a course.
     *
     * @param course the course to add
     */
    public void addCourse(Course course) {
        if (course.getInstructor() != null) {
            User instructor = userRepository.findById(course.getInstructor().getId())
                    .orElseThrow(() -> new RuntimeException("Instructor not found"));
            if (instructor.getRole() != Role.Teacher) {
                throw new RuntimeException("User is not a teacher");
            }
            course.setInstructor(instructor);
        }

        courseRepository.save(course);
    }

    /**
     * Save a course.
     *
     * @param course the course to save
     */
    public void save(Course course) {
        Course existingCourse = courseRepository.findById(course.getId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        existingCourse.setTitle(course.getTitle());
        existingCourse.setDescription(course.getDescription());
        existingCourse.setPrice(course.getPrice());
        existingCourse.setCategory(course.getCategory());
        existingCourse.setDuration(course.getDuration());
        existingCourse.setStatus(course.getStatus());
        existingCourse.setImage(course.getImage());
        if (course.getInstructor() != null) {
            User instructor = userRepository.findById(course.getInstructor().getId())
                    .orElseThrow(() -> new RuntimeException("Instructor not found"));
            if (instructor.getRole() != Role.Teacher) {
                throw new RuntimeException("User is not a teacher");
            }
            existingCourse.setInstructor(instructor);
        } else {
            existingCourse.setInstructor(null);
        }

        courseRepository.save(existingCourse);
    }

    /**
     * Check if a course exists by its ID.
     *
     * @param id the ID of the course
     * @return true if the course exists, false otherwise
     */
    public boolean courseExists(Long id) {
        boolean exists = courseRepository.existsById(id);
        logger.info("Checking existence of course with ID: {}: {}", id, exists);
        return exists;
    }

    /**
     * Get all courses.
     *
     * @return the list of courses
     */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * Find all courses.
     *
     * @return the list of courses
     */
    public List<Course> findAll() {
        Iterable<Course> allCourses = courseRepository.findAll();
        List<Course> courses = new ArrayList<>();
        allCourses.forEach(courses::add);
        return courses;
    }

    /**
     * Find a course by its ID.
     *
     * @param id the ID of the course
     * @return the course
     */
    public Course findCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    /**
     * Delete a course by its ID.
     *
     * @param id the ID of the course
     */
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        courseRepository.delete(course);
    }

    /**
     * Find teachers.
     *
     * @return the list of teachers
     */
    public List<User> findTeachers() {
        return userRepository.findByRole(Role.Teacher);
    }

    /**
     * Find courses by instructor.
     *
     * @param instructor the instructor
     * @return the list of courses
     */
    public List<Course> findByInstructor(User instructor) {
        return courseRepository.findByInstructor(instructor);
    }
}
