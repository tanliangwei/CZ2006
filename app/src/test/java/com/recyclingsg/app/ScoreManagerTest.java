package com.recyclingsg.app;

import com.recyclingsg.app.control.ScoreManager;
import com.recyclingsg.app.entity.TrashInfo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by quzhe on 2018-4-21.
 */
public class ScoreManagerTest {
    @Test
    public void calculateScore() throws Exception {
        ScoreManager scoreManager = ScoreManager.getInstance();
        TrashInfo trashInfo = new TrashInfo("Second Hand Goods");
        float score = scoreManager.calculateScore(trashInfo, 1);
        assertEquals(2, score,0.001);
        score = scoreManager.calculateScore(trashInfo, -1);
        assertEquals(0, score,0.001);

        trashInfo = new TrashInfo("    EWaste    ");
        score = scoreManager.calculateScore(trashInfo, 1);
        assertEquals(3, score,0.001 );
        score = scoreManager.calculateScore(trashInfo, -1);
        assertEquals(0, score,0.001);

        trashInfo = new TrashInfo("Cash For Trash");
        score = scoreManager.calculateScore(trashInfo, 1);
        assertEquals(4, score, 0.001);
        score = scoreManager.calculateScore(trashInfo, -1);
        assertEquals(0, score,0.001);
    }

}