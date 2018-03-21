package com.recyclingsg.app;

import java.util.Date;

/**
 * Created by quzhe on 2018-3-20.
 */

public class DepositRecord {
    String userId = UserManager.getUserId();
    Date date = new Date();
    // Unit
    // Score
    // TrashCollectionPointId
    // revenue


    public String getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }
}
