package ratajczak.artur.vob.utils;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Artur Ratajczak on 31.05.16.
 */
public class DatabaseManager {

    private static DatabaseManager mInstance;
    private static DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public static void initializeInstance(DatabaseHelper helper){
        if(mInstance == null){
            mInstance = new DatabaseManager();
            mDatabaseHelper = helper;
        }
    }

    public static DatabaseManager getInstance() throws IllegalStateException{
        if(mInstance == null){
            throw new IllegalStateException(DatabaseManager.class.getName() + " is not initialized, call initializeInstance(...) method first.");
        }
        return mInstance;
    }

    public boolean articleIsLiked(int articleID){
        boolean isLiked = mDatabaseHelper.articleIsLiked(openDatabase(), articleID);
        DatabaseManager.getInstance().closeDatabase();
        return isLiked;
    }

    public boolean insertArticle(int articleID){
        boolean success = mDatabaseHelper.insertArticle(openDatabase(), articleID);
        DatabaseManager.getInstance().closeDatabase();
        return success;
    }

    public void deleteArticle(int articleID){
        mDatabaseHelper.deleteArticle(openDatabase(), articleID);
        DatabaseManager.getInstance().closeDatabase();
    }

    private SQLiteDatabase openDatabase(){
        mDatabase = mDatabaseHelper.getWritableDatabase();
        return mDatabase;
    }

    public void closeDatabase(){
            mDatabase.close();
    }
}

