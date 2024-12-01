package btl_android_2.com.ui.DBSQLite;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import btl_android_2.com.MainActivity;
import btl_android_2.com.ui.danhSach.TaiLieu;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SQLiteDB.db";

    private static final int DATABASE_VERSION = 23;
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
    private static final String CREATE_TABLE_LOAITAILIEU =
            "CREATE TABLE LoaiTaiLieu (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "ten TEXT " +
                    ");";

    private static final String CREATE_TABLE_TAILIEU =
            "CREATE TABLE TaiLieu (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "tieuDe TEXT, " +
                    "moTa TEXT, " +
                    "noiDung TEXT, " +
                    "trangThai INTEGER, " + // 0: cho duyet  1: da duyet  2: da huy
                    "isFree INTEGER, " +
                    "gia INTEGER, " +
                    "idAccount INTEGER, " +
                    "idLoaiTaiLieu INTEGER, " + // Make sure this line is included
                    "FOREIGN KEY (idLoaiTaiLieu) REFERENCES LoaiTaiLieu(id)," +
                    "FOREIGN KEY (idAccount) REFERENCES Account(id)" + // Thiết lập khóa ngoại
                    ");";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        insertAdmin();
//        insertDummyUsers();
//        insertDummyLoaiTaiLieu();
//        insertDummyDocuments();
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_TABLE_ACCOUNT);
            db.execSQL(CREATE_TABLE_LOAITAILIEU);
            db.execSQL(CREATE_TABLE_TAILIEU);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Lỗi tạo bảng: " + e.getMessage());
        }
        String insertAccount="INSERT INTO Account (id,email,tenDangNhap,tenNguoiDung,matKhau,isAdmin,soDienThoai) VALUES"+
                "(1,'vinhbr@gmail.com','1','vinh','123',100,'09345835')";
        db.execSQL(insertAccount);
        String insertLoaiTaiLieu="INSERT INTO LoaiTaiLieu (ten) VALUES"+
                "('Tài liệu công nghệ thông tin'),"+
                "('Tài liệu kế toán'),"+
                "('Tài liệu kinh tế'),"+
                "('Tài liệu điện tử'),"+
                "('Tài liệu luật'),"+
                "('Tài liệu du lịch'),"+
                "('Tài liệu lý luận chính trị'),"+
                "('Tài liệu sư phạm')";
        db.execSQL(insertLoaiTaiLieu);
        String insertSampleData = "INSERT INTO TaiLieu (tieuDe, moTa, noiDung, trangThai, isFree, gia, idAccount, idLoaiTaiLieu) VALUES " +
                "('Tài liệu công nghệ thông tin1', 'Mô tả 1', '<html lang=\"vi\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>Tài liệu lập trình</title></head><body style=\"font-family: Arial, sans-serif;\"><h1>Tài liệu lập trình</h1><p>Lập trình là một lĩnh vực rất rộng và phong phú, bao gồm nhiều ngôn ngữ và công cụ khác nhau.</p><p>Để trở thành một lập trình viên giỏi, việc nắm vững tài liệu lập trình là vô cùng quan trọng.</p><p>Dưới đây là một số thông tin cơ bản về tài liệu lập trình và cách học lập trình hiệu quả.</p></body></html>\n', 1, 1, 0, 1, 0)," +
                "('Tài liệu du lịch1', 'Mô tả 2', 'Nội dung 2', 1, 0, 1000, 2, 5)";
        db.execSQL(insertSampleData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Account");
        db.execSQL("DROP TABLE IF EXISTS LoaiTaiLieu");
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
    public boolean checkUsernameExist(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Account WHERE tenDangNhap = ?", new String[]{username});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public boolean checkPhoneExist(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Account WHERE soDienThoai = ?", new String[]{phone});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
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
    //lấy tài liệu theo id
    public Cursor getDocumentById(int documentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM TaiLieu WHERE id = ?";
        return db.rawQuery(query, new String[]{String.valueOf(documentId)});
    }
    public boolean deleteDocumentById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("TaiLieu", "id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public Cursor getDocumentsByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM TaiLieu WHERE idAccount = ?";
        return db.rawQuery(query, new String[]{String.valueOf(userId)});
    }

    //lấy tất cả loại tài liệu
    public Cursor getAllLoaiTaiLieu() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM LoaiTaiLieu";
        return db.rawQuery(query, null);
    }

    /////////////////////////Insert dữ liệu để test////////////
    public boolean insertAdmin() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", "admin@example.com");
        contentValues.put("tenDangNhap", "admin2");
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
        contentValues.put("trangThai", 0);
        contentValues.put("isFree", 1);
        contentValues.put("gia", 0);
        contentValues.put("idAccount", MainActivity.Id);
        long result = db.insert("Tailieu", null, contentValues);
        return result != - 1;
    }
    public boolean dangBanTaiLieu(String tenTaiLieu, String moTa, String gia)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
        contentValues.put("tieuDe", tenTaiLieu);
        contentValues.put("noiDung", "");
        contentValues.put("moTa", moTa);
        contentValues.put("trangThai", 0);
        contentValues.put("isFree", 0);
        contentValues.put("gia", gia);
        contentValues.put("idAccount", MainActivity.Id);
        long result = db.insert("Tailieu", null, contentValues);
        return result != - 1;
    }
    //lấy dữ liệu từ bảng tài liệu
    public Cursor getAllDocuments() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM TaiLieu Where TrangThai = 1", null);
    }
//    WHERE  TrangThai = 1

    //    WHERE TrangThai = 1
    // Phương thức để lấy các tài liệu đang chờ duyệt
    // Giả sử 0 là trạng thái chờ duyệt, 1 là đã duyệt, -1 là từ chối
    public Cursor getPendingDocuments() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM TaiLieu WHERE trangThai = 0";
        return db.rawQuery(query, null);
    }

    // Phương thức để cập nhật trạng thái tài liệu
    public boolean capNhatTrangThai(int documentId, int status, long idLoaiTaiLieu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trangThai", status); // Cập nhật trạng thái

        if (idLoaiTaiLieu != -1) {
            contentValues.put("idLoaiTaiLieu", idLoaiTaiLieu); // Cập nhật ID loại tài liệu nếu được chỉ định
        }

        int rowsAffected = db.update("TaiLieu", contentValues, "id = ?", new String[]{String.valueOf(documentId)});
        return rowsAffected > 0;
    }
    //lọc tài liệu theo tài liệu có phí hay mất phí
    public Cursor getDocumentsByType(boolean isFree) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM TaiLieu WHERE isFree = ? AND trangThai = 1", new String[]{isFree ? "1" : "0"});
    }



    public boolean insertDummyUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Thêm dữ liệu giả định người dùng 1
        contentValues.put("email", "user1@example.com");
        contentValues.put("tenDangNhap", "trang");
        contentValues.put("tenNguoiDung", "User 1");
        contentValues.put("matKhau", "trang");
        contentValues.put("isAdmin", 0);
        contentValues.put("soDienThoai", "0123456789");
        long result1 = db.insert("Account", null, contentValues);

        // Thêm dữ liệu giả định người dùng 2
        contentValues.put("email", "user2@example.com");
        contentValues.put("tenDangNhap", "trang1");
        contentValues.put("tenNguoiDung", "User 2");
        contentValues.put("matKhau", "trang1");
        contentValues.put("isAdmin", 0);
        contentValues.put("soDienThoai", "9876543210");
        long result2 = db.insert("Account", null, contentValues);

        // Kiểm tra xem liệu thêm dữ liệu thành công hay không
        return (result1 != -1 && result2 != -1);
    }

    public boolean insertDummyDocuments() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Thêm dữ liệu giả định tài liệu 1
        contentValues.put("tieuDe", "Tài liệu 1");
        contentValues.put("moTa", "Mô tả tài liệu trang");
        contentValues.put("noiDung", "Nội dung tài liệu trang");
        contentValues.put("trangThai", 0); // Giả sử 0 là trạng thái chờ duyệt
        contentValues.put("isFree", 1);
        contentValues.put("gia", 0);
        contentValues.put("idAccount", 22); // ID của người dùng tạo tài liệu
 //       contentValues.put("idLoaiTaiLieu", 1); // ID của loại tài liệu
        long result1 = db.insert("TaiLieu", null, contentValues);

        // Thêm dữ liệu giả định tài liệu 2
        contentValues.put("tieuDe", "Tài liệu 2");
        contentValues.put("moTa", "Mô tả tài liệu 2");
        contentValues.put("noiDung", "Nội dung tài liệu 2");
        contentValues.put("trangThai", 0); // Giả sử 1 là trạng thái đã duyệt
        contentValues.put("isFree", 0);
        contentValues.put("gia",100000);
        contentValues.put("idAccount", 22); // ID của người dùng tạo tài liệu
//        contentValues.put("idLoaiTaiLieu", 2); // ID của loại tài liệu
        long result2 = db.insert("TaiLieu", null, contentValues);

        // Kiểm tra xem liệu thêm dữ liệu thành công hay không
        return (result1 != -1 && result2 != -1);
    }

    public boolean insertDummyLoaiTaiLieu() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Thêm dữ liệu giả định loại tài liệu 1
        contentValues.put("ten", "Loai Tai Lieu 3");
        long result1 = db.insert("LoaiTaiLieu", null, contentValues);

        // Thêm dữ liệu giả định loại tài liệu 2
        contentValues.put("ten", "Loai Tai Lieu 4");
        long result2 = db.insert("LoaiTaiLieu", null, contentValues);

        // Kiểm tra xem liệu thêm dữ liệu thành công hay không
        return (result1 != -1 && result2 != -1);
    }

    //lọc tài liệu theo loại tài liệu và theo loại tài liệu có phí hay mất  phí
    public Cursor getDocumentsByLoaiTaiLieuAndType(int loaiTaiLieuId, boolean isFree) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM TaiLieu WHERE idLoaiTaiLieu = ? AND isFree = ?";
        return db.rawQuery(query, new String[]{String.valueOf(loaiTaiLieuId), isFree ? "1" : "0"});
    }
//    public Cursor getAllDocuments() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.rawQuery("SELECT * FROM TaiLieu WHERE  TrangThai = 1", null);
//    }
    //lọc tài liệu theo loại tài liệu
    public Cursor getDocumentsByLoaiTaiLieu(int loaiTaiLieuId){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                "id",
                "tieuDe",
                "moTa",
                "noiDung",
                "trangThai",
                "isFree",
                "gia",
                "idAccount",
                "idLoaiTaiLieu"
        };

        String selection = "idLoaiTaiLieu = ?";
        String[] selectionArgs = { String.valueOf(loaiTaiLieuId) };

        return db.query("TaiLieu", projection, selection, selectionArgs, null, null, null);
    }
//    public String getTacGiaByIdAccount(int idAccount) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String tacGia = null;
//
//        Cursor cursor = db.rawQuery("SELECT tenNguoiDung FROM Account WHERE id = ?", new String[]{String.valueOf(idAccount)});
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                int columnIndex = cursor.getColumnIndex("tenNguoiDung");
//                if (columnIndex != -1) { // Kiểm tra xem cột có tồn tại không
//                    tacGia = cursor.getString(columnIndex);
//                } else {
//                    Log.e("DatabaseHelper", "Column 'tenNguoiDung' not found in cursor");
//                }
//            }
//            cursor.close(); // Đóng Cursor sau khi sử dụng
//        }
//        return tacGia;
//    }
    //lấy tác giả theo idAccount
    public String getTacGiaByIdAccount(int idAccount) {
        SQLiteDatabase db = this.getReadableDatabase();
        String tacGia = null;

        Cursor cursor = db.rawQuery("SELECT tenDangNhap FROM Account WHERE id = ?", new String[]{String.valueOf(idAccount)});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("tenDangNhap");
                if (columnIndex != -1) {
                    tacGia = cursor.getString(columnIndex);
                } else {
                    Log.e("DatabaseHelper", "Column 'tenDangNhap' not found in cursor");
                }
            }
            cursor.close();
        }
        return tacGia;
    }


    public String getSDTByIdAccount(int idAccount) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sdt = null;

        Cursor cursor = db.rawQuery("SELECT soDienThoai FROM Account WHERE id = ?", new String[]{String.valueOf(idAccount)});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("soDienThoai");
                if (columnIndex != -1) { // Kiểm tra xem cột có tồn tại không
                    sdt = cursor.getString(columnIndex);
                } else {
                    Log.e("DatabaseHelper", "Column 'soDienThoai' not found in cursor");
                }
            }
            cursor.close();
        }
        return sdt;
    }




}