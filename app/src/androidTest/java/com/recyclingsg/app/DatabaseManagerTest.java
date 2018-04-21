package com.recyclingsg.app;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by quzhe on 2018-4-21.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseManagerTest {
    @Test
    public void testPullPublicEWasteFromDatabase() throws Exception {
        DatabaseManager.pullPublicEWasteFromDatabase();
        Thread.sleep(1000);
        assertEquals(407,DatabaseManager.getEWastePublicTrashCollectionPoints().size());
    }

    @Test
    public void pullPublicCashForTrash() throws Exception {
        DatabaseManager.pullPublicCashForTrash();
        Thread.sleep(1000);
        assertEquals(109,DatabaseManager.getCashForTrashPublicTrashCollectionPoints().size());
    }

    @Test
    public void pullPublicSecondHandFromDatabase() throws Exception {
        DatabaseManager.pullPublicEWasteFromDatabase();
        Thread.sleep(1000);
        assertEquals(21,DatabaseManager.getSecondHandPublicTrashCollectionPoints().size());
    }
}

