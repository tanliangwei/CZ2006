package com.recyclingsg.app;

import android.graphics.Bitmap;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * This class is a the configuration class. It is used at the start of the application runtime to load and call certain data and functions.
 * @author Honey Stars
 * @version 1.0
 */

public class UserManager {
    private String userName;
    private String userID;
    private Bitmap bitmap;
    private DatabaseInterface databaseManager;

    public void addDatabaseInterface(DatabaseInterface db){
        this.databaseManager = db;
    }



    private static UserManager instance;
    //this ensures that there is only one instance of  User Manager in the whole story
    /**
     * This returns a singleton instance of the User Manager.
     * @return Singleton instance of User Manager
     */
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

    /**
     * Creates an instance of User Manager
     */
    public UserManager(){}

    public String getUserId() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserID(String id) {
        userID = id;
    }

    public void setUserName(String name) {
        userName = name;
    }

    /**
     * This adds the Private Trash Collection Point created to the user and then push it to the Database Manager to be saved.
     * @param ptcp The Private Trash Collection Point created by this user.
     */
    public void addPrivateTrashCollectionPointToUser(PrivateTrashCollectionPoint ptcp){
        ptcp.setOwnerId(userID);
        ptcp.setOwnerName(userName);
        databaseManager.savePrivateTrashCollectionPoint(ptcp);
        Log.d(TAG, "addPrivateTrashCollectionPointToUser: ");
    }

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
}


