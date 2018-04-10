package com.recyclingsg.app;

import android.graphics.Bitmap;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by jiahengzhang on 16/3/18.
 */

public class UserManager {

    private static String userID;
    private static String userName;
    private static Bitmap bitmap;



    private static UserManager instance;
    //this ensures that there is only one instance of  User Manager in the whole story
    public static UserManager getInstance(){
        if (instance == null) {
            try {
                instance = new UserManager();
            } catch (Exception e) {
                Log.e(TAG, "failed to construct UserManager instance");
                e.printStackTrace();
            }
        }
        return instance;
    }

    public UserManager(){}
    /*public static Picture getPicture(){
        Picture picture = new Picture();
        Canvas canvas = picture.beginRecording(bitmap.getWidth(), bitmap.getHeight());
        canvas.drawBitmap(bitmap, null, new RectF(0f, 0f, (float) bitmap.getWidth(), (float) bitmap.getHeight()), null);
        picture.endRecording();
        return picture;
    }*/

    /*public static void setFacebookProfilePicture(final String userID){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final URL imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
                        Log.d(TAG, "bitmap url: "+imageURL);
                        bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
                        Log.d(TAG, "Got bitmap");
                    }
                    catch(Exception e){
                        Log.e(TAG,"fail to get url");
                    }

                }
            });
            thread.start();
    }*/
    //public static void setBitMap(Bitmap thebitmap){bitmap=thebitmap;}
    //public static Bitmap getFacebookProfilePicture(){return bitmap;}
    public static String getUserId() {
        return userID;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserID(String id) {
        userID = id;
    }

    public static void setUserName(String name) {
        userName = name;
    }

    public static void addPrivateTrashCollectionPointToUser(PrivateTrashCollectionPoint ptcp){
        ptcp.setOwnerId(userID);
        ptcp.setOwnerName(userName);
        DatabaseManager.getInstance();
        DatabaseManager.savePrivateTrashCollectionPoint(ptcp);
        Log.d(TAG, "addPrivateTrashCollectionPointToUser: ");
    }
}


