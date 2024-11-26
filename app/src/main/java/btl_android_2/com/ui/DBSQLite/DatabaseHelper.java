package btl_android_2.com.ui.DBSQLite;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import btl_android_2.com.MainActivity;

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
                    "gia INTEGER, " +
                    "idAccount INTEGER, " +
                    "FOREIGN KEY (idAccount) REFERENCES Account(id)" + // Thiết lập khóa ngoại
                    ");";


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        insertAdmin();


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
    public Cursor checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Account WHERE tenDangNhap = ? AND matKhau = ?";
        return db.rawQuery(query, new String[]{username, password});
    }

    public Cursor getLatestDocuments(int limit) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM TaiLieu ORDER BY id DESC LIMIT ?";
        return db.rawQuery(query, new String[]{String.valueOf(limit)});
    }

    /////////////////////////Insert dữ liệu để test////////////
    public boolean insertAdmin() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", "admin@example.com");
        contentValues.put("tenDangNhap", "admin");
        contentValues.put("tenNguoiDung", "Admin User");
        contentValues.put("matKhau", "admin");
        contentValues.put("isAdmin", 1);
        contentValues.put("soDienThoai", "0123456789");
        long result = db.insert("Account", null, contentValues);
        return result != -1;
    }

    //////////////////////////////////////////////////////end/////////////////

    public boolean chiaSeTaiLieu(String tenTaiLieu, String moTa, String noiDung)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
        contentValues.put("tieuDe", tenTaiLieu);
        contentValues.put("noiDung", noiDung);
        contentValues.put("moTa", moTa);
        contentValues.put("trangThai", 1);
        contentValues.put("isFree", 1);
        contentValues.put("gia", 0);
        contentValues.put("idAccount", MainActivity.Id);
        long result = db.insert("Tailieu", null, contentValues);
        return result != - 1;
    }



}

