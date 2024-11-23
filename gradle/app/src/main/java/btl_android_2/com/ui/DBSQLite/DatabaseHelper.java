package btl_android_2.com.ui.DBSQLite;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SQLiteDB.db";

    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper instance;

    private static final String CREATE_TABLE_ACCOUNT =
            "CREATE TABLE Account (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "email TEXT, " +
                    "tenDangNhap TEXT, " +
                    "tenNguoiDung TEXT, " +
                    "matKhau TEXT, " +
                    "isAdmin INTEGER, " +
                    "soDienThoai TEXT" +
                    ");";

    private static final String CREATE_TABLE_TAILIEU =
            "CREATE TABLE TaiLieu (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "tieuDe TEXT, " +
                    "moTa TEXT, " +
                    "noiDung TEXT, " +
                    "trangThai INTEGER, " +
                    "isFree INTEGER, " +
                    "gia INTEGER" +
                    ");";


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ACCOUNT);
        db.execSQL(CREATE_TABLE_TAILIEU);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Account");
        db.execSQL("DROP TABLE IF EXISTS TaiLieu");
        onCreate(db);
    }


    public boolean insertData(String phone, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("soDienThoai", phone);
        contentValues.put("tenDangNhap", username);
        contentValues.put("matKhau", password);
        long result = db.insert("Account", null, contentValues);
        return result != -1;
    }
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT * FROM " + "Account" + " WHERE " + COL_3 + " = ? AND " + COL_4 + " = ?";
        String query = "SELECT * FROM Account WHERE tenDangNhap = ? AND matKhau = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public Cursor getLatestDocuments(int limit) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM TaiLieu ORDER BY id DESC LIMIT ?";
        return db.rawQuery(query, new String[]{String.valueOf(limit)});
    }

}

