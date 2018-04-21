package com.recyclingsg.app;
import android.support.test.runner.AndroidJUnit4;

import com.recyclingsg.app.control.DatabaseManager;

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
        DatabaseManager.getInstance().pullPublicEWasteFromDatabase();
        Thread.sleep(1000);
        assertEquals(407,DatabaseManager.getInstance().getEWastePublicTrashCollectionPoints().size());
    }

    @Test
    public void pullPublicCashForTrash() throws Exception {
        DatabaseManager.getInstance().pullPublicCashForTrash();
        Thread.sleep(1000);
        assertEquals(109,DatabaseManager.getInstance().getCashForTrashPublicTrashCollectionPoints().size());
    }

    @Test
    public void pullPublicSecondHandFromDatabase() throws Exception {
        DatabaseManager.getInstance().pullPublicEWasteFromDatabase();
        Thread.sleep(1000);
        assertEquals(21,DatabaseManager.getInstance().getSecondHandPublicTrashCollectionPoints().size());
    }
}

