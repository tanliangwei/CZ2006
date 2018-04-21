package com.recyclingsg.app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by quzhe on 2018-4-21.
 */
public class ScoreManagerTest {
    @Test
    public void calculateScore() throws Exception {
        TrashInfo trashInfo = new TrashInfo("Second Hand Goods");
        float score = ScoreManager.calculateScore(trashInfo, 1);
        assertEquals(2, score,0.001);
        score = ScoreManager.calculateScore(trashInfo, -1);
        assertEquals(0, score,0.001);

        trashInfo = new TrashInfo("    EWaste    ");
        score = ScoreManager.calculateScore(trashInfo, 1);
        assertEquals(3, score,0.001 );
        score = ScoreManager.calculateScore(trashInfo, -1);
        assertEquals(0, score,0.001);

        trashInfo = new TrashInfo("Cash For Trash");
        score = ScoreManager.calculateScore(trashInfo, 1);
        assertEquals(4, score, 0.001);
        score = ScoreManager.calculateScore(trashInfo, -1);
        assertEquals(0, score,0.001);
    }

}