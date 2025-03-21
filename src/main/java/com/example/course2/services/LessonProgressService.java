package com.example.course2.services;

import com.example.course2.models.Lesson;
import com.example.course2.models.LessonProgress;
import com.example.course2.models.User;
import com.example.course2.repositories.LessonProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing lesson progress.
 */
@Service
public class LessonProgressService {

    @Autowired
    private LessonProgressRepository lessonProgressRepository;

    /**
     * Find lesson progress by user and lesson.
     *
     * @param user the user
     * @param lesson the lesson
     * @return the lesson progress
     */
    public Optional<LessonProgress> findByUserAndLesson(User user, Lesson lesson) {
        return lessonProgressRepository.findByUserAndLesson(user, lesson);
    }

    /**
     * Save lesson progress.
     *
     * @param lessonProgress the lesson progress to save
     */
    public void saveLessonProgress(LessonProgress lessonProgress) {
        lessonProgressRepository.save(lessonProgress);
    }

    /**
     * Start or update lesson progress.
     *
     * @param user the user
     * @param lesson the lesson
     * @return the lesson progress
     */
    public LessonProgress startOrUpdateLessonProgress(User user, Lesson lesson) {
        return lessonProgressRepository.findByUserAndLesson(user, lesson)
                .orElseGet(() -> {
                    LessonProgress lessonProgress = new LessonProgress();
                    lessonProgress.setUser(user);
                    lessonProgress.setLesson(lesson);
                    return lessonProgress;
                });
    }

    /**
     * Check if a user has completed a lesson.
     *
     * @param user the user
     * @param lesson the lesson
     * @return true if the lesson is completed, false otherwise
     */
    public boolean hasCompletedLesson(User user, Lesson lesson) {
        return lessonProgressRepository.findByUserAndLesson(user, lesson)
                .map(LessonProgress::isCompleted)
                .orElse(false);
    }
}
