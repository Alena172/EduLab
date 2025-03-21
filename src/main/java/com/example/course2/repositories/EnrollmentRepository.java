package com.example.course2.repositories;

import com.example.course2.models.Course;
import com.example.course2.models.Enrollment;
import com.example.course2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findAllByStudent(User student);
    Optional<Enrollment> findByStudentAndCourse(User student, Course course);
    Optional<Enrollment> findByCourseIdAndStudentId(Long courseId, Long studentId);

    List<Enrollment> findByCourse(Course course);

    boolean existsByCourseAndStudent(Course course, User student);

    boolean existsByCourseIdAndStudentId(Long courseId, Long studentId);
}
