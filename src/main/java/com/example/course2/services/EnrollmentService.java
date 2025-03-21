package com.example.course2.services;

import com.example.course2.models.*;
import com.example.course2.repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing enrollments.
 */
@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private LessonProgressService lessonProgressService;

    /**
     * Find enrollment by student and course.
     *
     * @param student the student
     * @param course the course
     * @return the enrollment
     */
    public Optional<Enrollment> findEnrollment(User student, Course course) {
        return enrollmentRepository.findByStudentAndCourse(student, course);
    }

    /**
     * Enroll a user in a course.
     *
     * @param student the student
     * @param course the course
     * @return the enrollment
     */
    public Enrollment enrollUserInCourse(User student, Course course) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus(Status.ACTIVE);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        enrollment.setProgress(0);
        return enrollmentRepository.save(enrollment);
    }

    /**
     * Find enrollments by student.
     *
     * @param student the student
     * @return the list of enrollments
     */
    public List<Enrollment> findEnrollmentsByStudent(User student) {
        return enrollmentRepository.findAllByStudent(student);
    }

    /**
     * Calculate course completion percentage for a user.
     *
     * @param user the user
     * @param course the course
     * @return the completion percentage
     */
    public double calculateCourseCompletion(User user, Course course) {
        List<Lesson> lessons = lessonService.findLessonsByCourse(course);
        long completedLessonsCount = lessons.stream()
                .filter(lesson -> lessonProgressService.hasCompletedLesson(user, lesson))
                .count();
        double completionPercentage = 0;
        if (lessons.size() > 0) {
            completionPercentage = (double) completedLessonsCount / lessons.size() * 100;
        }

        return completionPercentage;
    }
}
