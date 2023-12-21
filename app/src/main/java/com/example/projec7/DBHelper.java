package com.example.projec7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {
    public static final int POST_DATABASE_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, "post.db", null,  POST_DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        println("onCreate 호출됨");
        String postSQL = "create table post_db (" +
                "_id integer primary key autoincrement," +
                "title," +
                "content," +
                "local_category," +
                "theme_category," +
                "password," +
                "image_data)";

        String commentSQL = "create table comment_db (" +
                "_id integer primary key autoincrement," +
                "post_id integer," +
                "comment_text," +
                "foreign key(post_id) references post_db(_id) ON DELETE CASCADE)";
        db.execSQL(postSQL);
        db.execSQL(commentSQL);

        String memoSQL = "create table tb_memo (" +
                "_id integer primary key autoincrement,"+
                "address text," +
                "count integer)";
        db.execSQL(memoSQL);


    }
    public void onOpen(SQLiteDatabase db){
        println("onOpen 호출됨");
    }

    public void println(String data){
        Log.d("DatabaseHelper" , data);
    }

    //글을 쓰고 데이터베이스에 게시글을 삽입하는 메소드
    public long insertPost(String title, String content, String localCategory, String themeCategory, String imageData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        values.put("local_category", localCategory);
        values.put("theme_category", themeCategory);
        values.put("image_data", imageData);
        String tableName="post_db";

        long newRowId = db.insert(tableName, null, values);

        return newRowId;
    }

    //모든 게시글을 가져오는 메소드
    public List<Post> getAllPosts() {
        List<Post> postList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                "_id",
                "title",
                "content",
                "local_category",
                "theme_category",
                "image_data"
        };

        Cursor cursor = db.query(
                "post_db",
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Post post = new Post();

            post.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
            post.setContent(cursor.getString(cursor.getColumnIndexOrThrow("content")));
            post.setLocalCategory(cursor.getString(cursor.getColumnIndexOrThrow("local_category" )));
            post.setThemeCategory(cursor.getString(cursor.getColumnIndexOrThrow("theme_category" )));
            post.setImageData(cursor.getString(cursor.getColumnIndexOrThrow( "image_data")));

            postList.add(post);
        }

        cursor.close();
        db.close();

        return postList;
    }

    //category에 따라 해당 게시글만 뜨도록 하는 메소드
    public List<Post> getFilteredPosts(String localCategory, String themeCategory) {
        List<Post> filteredPosts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                "_id",
                "title",
                "content",
                "local_category",
                "theme_category",
                "image_data"
        };

        String selection;
        String[] selectionArgs;

        if ("구 선택".equals(localCategory) && "전체".equals(themeCategory)) {
            // Show all posts
            selection = null;
            selectionArgs = null;
        } else if ("구 선택".equals(localCategory)) {
            // Show posts for the selected theme category
            selection = "theme_category = ?";
            selectionArgs = new String[]{themeCategory};
        } else if ("전체".equals(themeCategory)) {
            // Show posts for the selected local category
            selection = "local_category = ?";
            selectionArgs = new String[]{localCategory};
        } else {
            // Show posts for both local and theme categories
            selection = "local_category = ? AND theme_category = ?";
            selectionArgs = new String[]{localCategory, themeCategory};
        }

        Cursor cursor = db.query(
                "post_db",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Post post = new Post();
            post.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
            post.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
            post.setContent(cursor.getString(cursor.getColumnIndexOrThrow("content")));
            post.setLocalCategory(cursor.getString(cursor.getColumnIndexOrThrow("local_category")));
            post.setThemeCategory(cursor.getString(cursor.getColumnIndexOrThrow("theme_category")));
            post.setImageData(cursor.getString(cursor.getColumnIndexOrThrow("image_data")));

            filteredPosts.add(post);
        }

        cursor.close();
        db.close();

        return filteredPosts;
    }

    //댓글 리스트를 가져오는 메소드
    public List<Comment> getCommentsForPost(int postId) {
        List<Comment> comments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                "_id",
                "post_id",
                "comment_text"
        };

        String selection = "post_id = ?";
        String[] selectionArgs = {String.valueOf(postId)};

        Cursor cursor = db.query(
                "comment_db",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        println("getCommentsForPost - Query result count: " + cursor.getCount()); // 로그 추가

        while (cursor.moveToNext()) {
            Comment comment = new Comment();
            comment.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
            comment.setPostId(cursor.getInt(cursor.getColumnIndexOrThrow("post_id")));
            comment.setCommentText(cursor.getString(cursor.getColumnIndexOrThrow("comment_text")));
            comments.add(comment);
        }

        cursor.close();
        db.close();

        return comments;
    }

    //댓글 등록하는 메소드
    public long insertComment(int postId, String commentText) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put("post_id", postId);
            values.put("comment_text", commentText);

            long newRowId = db.insert("comment_db", null, values);

            db.setTransactionSuccessful();

            return newRowId;
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    //게시글 찾는 메소드
    public List<Post> searchPosts(String query) {
        List<Post> searchResults = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                "_id",
                "title",
                "content",
                "local_category",
                "theme_category",
                "image_data"
        };

        // 제목 또는 내용에 대한 검색 쿼리
        String selection = "title LIKE ? OR content LIKE ?";
        String[] selectionArgs = {"%" + query + "%", "%" + query + "%"};

        Cursor cursor = db.query(
                "post_db",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Post post = new Post();
            post.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
            post.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
            post.setContent(cursor.getString(cursor.getColumnIndexOrThrow("content")));
            post.setLocalCategory(cursor.getString(cursor.getColumnIndexOrThrow("local_category")));
            post.setThemeCategory(cursor.getString(cursor.getColumnIndexOrThrow("theme_category")));
            post.setImageData(cursor.getString(cursor.getColumnIndexOrThrow("image_data")));

            searchResults.add(post);
        }

        cursor.close();
        db.close();

        return searchResults;
    }


    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        println("onUpgrade 호출됨 : " + oldVersion + " -> " + newVersion);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        // 다운그레이드 허용 코드
    }
    public int deleteComment(int commentId) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "_id = ?";
        String[] whereArgs = {String.valueOf(commentId)};
        int deletedRows = db.delete("comment_db", whereClause, whereArgs);
        db.close();
        return deletedRows;
    }
    public int deletePost(int postId) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "_id = ?";
        String[] whereArgs = {String.valueOf(postId)};

        // 게시글 삭제
        int deletedRows = db.delete("post_db", whereClause, whereArgs);

        // 게시글에 속한 댓글들도 삭제
        db.delete("comment_db", "post_id = ?", new String[]{String.valueOf(postId)});

        db.close();
        return deletedRows;
    }


    public long insertAddress(String address, int count) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;

        try {
            // Begin the transaction
            db.beginTransaction();

            // Check if the address already exists in the table
            Cursor cursor = db.rawQuery("SELECT * FROM tb_memo WHERE address = ?", new String[]{address});
            if (cursor.moveToFirst()) {
                int countColumnIndex = cursor.getColumnIndex("count");

                if (countColumnIndex != -1) {
                    int currentCount = cursor.getInt(countColumnIndex);
                    int newCount = currentCount + count;

                    ContentValues updateValues = new ContentValues();
                    updateValues.put("count", newCount);

                    result = db.update("tb_memo", updateValues, "address = ?", new String[]{address});
                }
                else {

                }

            } else {
                // If the address doesn't exist, insert a new record
                ContentValues insertValues = new ContentValues();
                insertValues.put("address", address);
                insertValues.put("count", count);

                result = db.insert("tb_memo", null, insertValues);
            }

            // Commit the transaction if everything is successful
            db.setTransactionSuccessful();
        } finally {
            // End the transaction, regardless of success or failure
            db.endTransaction();
            db.close();
        }
            return result;

    }

    public ArrayList getOneData(){

        ArrayList res = new ArrayList<votelist>();


        Cursor cur = getWritableDatabase().rawQuery("SELECT address, count FROM tb_memo WHERE count > 0", null);
        int addressIndex = cur.getColumnIndex("address");
        int countIndex = cur.getColumnIndex("count");

        if (cur != null && addressIndex != -1 && countIndex != -1 && cur.moveToFirst()) {
            do {
                String address = cur.getString(addressIndex);
                int count = cur.getInt(countIndex);
                res.add(new votelist(address, count));
            } while (cur.moveToNext());
        }


        if (cur != null) {
            cur.close();
        }

        return res;
    }



}