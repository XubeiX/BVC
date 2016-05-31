package ratajczak.artur.vob.utils;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by xubeix on 31.05.16.
 */
public class DatabaseManager {
    private int mOpenConnection;

    private static DatabaseManager mInstance;
    private static DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(DatabaseHelper helper){
        if(mInstance == null){
            mInstance = new DatabaseManager();
            mDatabaseHelper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance() throws IllegalStateException{
        if(mInstance == null){
            throw new IllegalStateException(DatabaseManager.class.getName() + " is not initialized, call initializeInstance(...) method first.");
        }
        return mInstance;
    }

    public boolean articleIsLiked(int articleID){
      boolean isLiked = mDatabaseHelper.articleIsLiked(openDatabase(),articleID);
        closeDatabase();
      return isLiked;
    }

    public boolean insertArticle(int articleID){
        boolean success = mDatabaseHelper.insertArticle(openDatabase(),articleID);
        closeDatabase();
        return success;
    }

    public void deleteArticle(int articleID){
        mDatabaseHelper.deleteArticle(openDatabase(),articleID);
        closeDatabase();
    }

    private synchronized SQLiteDatabase openDatabase(){
        mOpenConnection++;
        if(mOpenConnection == 1){
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    private synchronized void closeDatabase(){
        mOpenConnection--;
        if(mOpenConnection == 0){
            mDatabase.close();
        }
    }
}

