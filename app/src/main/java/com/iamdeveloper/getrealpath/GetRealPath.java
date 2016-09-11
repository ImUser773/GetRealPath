package com.iamdeveloper.getrealpath;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.Log;

/**
 * Created by IamDeveloper on 8/29/2016.
 */
public class GetRealPath {

    @SuppressLint("NewApi")
    public static String getRealPathFromAPI19(Context context, Uri uri) {
        Log.i("GetRealPath", uri.toString());

        String filePath = null;
        try {

            String getID = DocumentsContract.getDocumentId(uri);
            Log.i("GetRealPath", "getID = " + getID);
            String id = getID.split(":")[1];
            Log.i("GetRealPath", "id = " + id);
            String[] column = {MediaStore.Files.FileColumns.DATA};
            Log.i("GetRealPath", "column = " + column[0]);

            String select = MediaStore.Files.FileColumns._ID + "=?";
            Log.i("GetRealPath", "select = " + select);


            Cursor cursor = context.getContentResolver().query(MediaStore.Files.getContentUri("external"), column,
                    select, new String[]{id}, null);

            Log.i("GetRealPath", "cursor = " + cursor + "");
            assert cursor != null;
            int columnIndex = cursor.getColumnIndex(column[0]);
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            Log.i("GetRealPath", "filePath = " + filePath);
            cursor.close();
            return filePath;
        } catch (RuntimeException e) {
            Log.d("RuntimeException", e.toString());
        }
        return filePath;
    }

    @SuppressLint("NewApi")
    public static String getRealPathFromAPI11_to18(Context context,Uri uri){
        String filePath = null;
        try {
            String[] proj = {MediaStore.Files.FileColumns.DATA};

            CursorLoader cursorLoader = new CursorLoader(context, uri, proj, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();

            assert cursor != null;
            int column = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(column);
            }
        }catch(RuntimeException e){
            Log.d("RuntimeException",e.toString());
        }

        return filePath;
    }

    @SuppressLint("NewApi")
    public static String getRealPathFromSD_CARD(Uri uri){

        String realPath = null;
        try{
            String getID = DocumentsContract.getDocumentId(uri);
            String id = getID.split(":")[1];
            realPath = "/storage/sdcard/"+id;


        }catch(RuntimeException e){
            Log.d("RuntimeException",e.toString());
        }
        return realPath;
    }
}
