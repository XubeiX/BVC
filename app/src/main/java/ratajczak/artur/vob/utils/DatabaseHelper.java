package ratajczak.artur.vob.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Artur Ratajczak on 31.05.16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BatmanVillains.db";
    private static final int DATABASE_VERSION = 1;
    private static final String ARTICLES_TABLE_NAME = "likedArticles";
    private static final String ARTICLES_COLUMN_ID = "ID";
    private static final String ARTICLES_COLUMN_ARTICLE_ID ="articleID";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ARTICLES_TABLE_NAME +
                " (" + ARTICLES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ARTICLES_COLUMN_ARTICLE_ID + " INTEGER UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public boolean insertArticle(SQLiteDatabase db,int ArticleID){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ARTICLES_COLUMN_ARTICLE_ID, ArticleID);
        long id = db.insert(ARTICLES_TABLE_NAME,null,contentValues);
        return id != -1;
    }

    public void deleteArticle(SQLiteDatabase db, int ArticleID){
        db.delete(ARTICLES_TABLE_NAME,ARTICLES_COLUMN_ARTICLE_ID + " = ? ",new String[]{Integer.toString(ArticleID)});
    }

    public boolean articleIsLiked(SQLiteDatabase db,int articleID){
        boolean inDatabase = false;
        Cursor cursor = db.rawQuery("SELECT * FROM " + ARTICLES_TABLE_NAME + " where " + ARTICLES_COLUMN_ARTICLE_ID + " = " + articleID,null);
        if(cursor.getCount()>0) {
            inDatabase = true;
        }
        cursor.close();
        return inDatabase;
    }

}