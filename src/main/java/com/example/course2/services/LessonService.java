package com.example.course2.services;

import com.example.course2.models.Block;
import com.example.course2.models.BlockType;
import com.example.course2.models.Course;
import com.example.course2.models.Lesson;
import com.example.course2.repositories.BlockRepository;
import com.example.course2.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing lessons.
 */
@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private BlockService blockService;

    /**
     * Find a lesson by its ID.
     *
     * @param id the ID of the lesson
     * @return the lesson
     */
    public Lesson findById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }

    /**
     * Find lessons by course.
     *
     * @param course the course
     * @return the list of lessons
     */
    public List<Lesson> findLessonsByCourse(Course course) {
        return lessonRepository.findByCourse(course);
    }

    /**
     * Save a lesson.
     *
     * @param lesson the lesson to save
     * @return the saved lesson
     */
    public Lesson saveLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    /**
     * Delete a lesson by its ID.
     *
     * @param id the ID of the lesson
     */
    public void deleteById(Long id) {
        lessonRepository.deleteById(id);
    }

    /**
     * Update blocks of a lesson.
     *
     * @param lesson the lesson
     * @param blockTitle the list of block titles
     * @param blockType the list of block types
     * @param blockContent the list of block contents
     */
    public void updateBlocks(Lesson lesson, List<String> blockTitle, List<String> blockType, List<String> blockContent) {
        List<Block> updatedBlocks = new ArrayList<>();
        for (int i = 0; i < blockTitle.size(); i++) {
            Block block = new Block();
            block.setTitle(blockTitle.get(i));
            block.setType(BlockType.valueOf(blockType.get(i)));
            block.setContent(blockContent.get(i));
            block.setLesson(lesson);
            updatedBlocks.add(block);
        }
        blockService.deleteBlocksByLesson(lesson);
        blockService.saveAll(updatedBlocks);
    }

    /**
     * Delete a lesson.
     *
     * @param lesson the lesson to delete
     */
    public void delete(Lesson lesson) {
        lessonRepository.delete(lesson);
    }

    /**
     * Save a lesson.
     *
     * @param lesson the lesson to save
     * @return the saved lesson
     */
    public Lesson save(Lesson lesson) {
        return lessonRepository.save(lesson);
    }
}
