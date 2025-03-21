package com.example.course2.services;

import com.example.course2.models.Block;
import com.example.course2.models.Lesson;
import com.example.course2.repositories.BlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service class for managing blocks.
 */
@RequiredArgsConstructor
@Service
public class BlockService {

    @Autowired
    private BlockRepository blockRepository;

    /**
     * Save a block.
     *
     * @param block the block to save
     * @return the saved block
     */
    public Block saveBlock(Block block) {
        return blockRepository.save(block);
    }

    /**
     * Find blocks by lesson.
     *
     * @param lesson the lesson
     * @return the list of blocks
     */
    public List<Block> findBlocksByLesson(Lesson lesson) {
        return blockRepository.findByLesson(lesson);
    }

    /**
     * Save all blocks.
     *
     * @param blocks the list of blocks to save
     * @return the saved blocks
     */
    public List<Block> saveAll(List<Block> blocks) {
        return blockRepository.saveAll(blocks);
    }

    /**
     * Find a block by its ID.
     *
     * @param id the ID of the block
     * @return the block
     */
    public Block findById(Long id) {
        return blockRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Block not found with id: " + id));
    }

    /**
     * Delete a block by its ID.
     *
     * @param id the ID of the block
     */
    public void deleteBlock(Long id) {
        blockRepository.deleteById(id);
    }

    /**
     * Delete blocks by lesson.
     *
     * @param lesson the lesson
     */
    public void deleteBlocksByLesson(Lesson lesson) {
        List<Block> blocks = blockRepository.findByLesson(lesson);
        if (!blocks.isEmpty()) {
            blockRepository.deleteAll(blocks);
        }
    }
}
