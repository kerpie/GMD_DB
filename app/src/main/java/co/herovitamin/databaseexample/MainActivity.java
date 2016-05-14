package co.herovitamin.databaseexample;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    SQLiteDatabase db;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DBHelper(this);

        db = helper.getWritableDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Random random = new Random();

        int id = 1442;
        String title = "Titulo " + Math.abs(random.nextInt());
        String subtitle = "Subtitulo " + Math.abs(random.nextInt());
        String content = "Contenido " + Math.abs(random.nextInt());

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID, id);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, subtitle);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CONTENT, content);

        long newRowId;
        newRowId = db.insert(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NULLABLE,
                values);

        String[] projection = new String[]{
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
        };

        String sortOrder = FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " DESC";
        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );


        cursor.moveToFirst();

        Log.i(TAG, "ROWS: " + cursor.getCount());
        Log.i(TAG, "CURRENT VERSION: " + db.getVersion());

        do{
            String savedtitle = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE));
            Log.i(TAG, savedtitle);
        }while (cursor.moveToNext());

    }
}