package btl_android_2.com;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

//import btl_android_2.com.databinding.ActivityMainBinding;
import btl_android_2.com.ui.DBSQLite.DatabaseHelper;
import btl_android_2.com.ui.dangBan.fragment_dangban;
import btl_android_2.com.ui.danhSach.TaiLieu;
import btl_android_2.com.ui.danhSach.fragment_danhsach;
import btl_android_2.com.ui.trangChu.fragment_trangchu;
import android.graphics.Typeface;
import btl_android_2.com.ui.danhSach.activity_tailieu;
import btl_android_2.com.ui.danhSach.activity_tailieu_free;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DatabaseHelper dbHelper;

    private TextView txttentaikhoan;
    private TextView txttennguoidung;

    public static String tenDangNhap;

    public static TextView txt;
    public static Integer Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_trangchu, R.id.nav_tailieu, R.id.nav_danhsach, R.id.nav_chiase, R.id.nav_dang)
                .setOpenableLayout(drawerLayout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
//        deletevdc();
        thamchieu_database();
//        deletevdc();
//        insertSampleDocuments();


//        Fragment fragment = new fragment_danhsach();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, fragment);
//        transaction.commit();
//        txt=findViewById(R.id.icon);
//        txt.setText(tenDangNhap);
    }
 private  void deletevdc(){
//         String dele="Delete from TaiLieu";
     SQLiteDatabase db = dbHelper.getWritableDatabase();
     db.delete("TaiLieu",null,null);


 }


    /////////////////////////Insert dữ liệu để test////////////
//    private void insertSampleDocuments() {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // Insert sample data into TaiLieu table
//        String[] sampleDocuments = {
//                "INSERT INTO TaiLieu (tieuDe, moTa, noiDung, trangThai, isFree, gia) VALUES ('Document 1', 'Description 1', 'Content 1', 1, 1, 0)",
//                "INSERT INTO TaiLieu (tieuDe, moTa, noiDung, trangThai, isFree, gia) VALUES ('Document 2', 'Description 2', 'Content 2', 1, 0, 100)",
//                "INSERT INTO TaiLieu (tieuDe, moTa, noiDung, trangThai, isFree, gia) VALUES ('Document 3', 'Description 3', 'Content 3', 0, 1, 0)",
//                "INSERT INTO TaiLieu (tieuDe, moTa, noiDung, trangThai, isFree, gia) VALUES ('Document 4', 'Description 4', 'Content 4', 1, 0, 200)",
//                "INSERT INTO TaiLieu (tieuDe, moTa, noiDung, trangThai, isFree, gia) VALUES ('Document 5', 'Description 5', 'Content 5', 0, 1, 0)"
//        };
//
//        for (String document : sampleDocuments) {
//            db.execSQL(document);
//        }
//    }
//////////////////////////////////////////////////////end/////////////////


    public void thamchieu_database(){
        dbHelper = DatabaseHelper.getInstance(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //ví dụ thêm dữ liệu vào database bảng Account. Chạy thử thấy hiển thị được thì xóa đi
//        String addContent = "INSERT INTO Account (email, tenDangNhap, tenNguoiDung, matKhau, isAdmin, soDienThoai) VALUES (?, ?, ?, ?, ?, ?)";
//        db.execSQL( addContent,
//                new Object[]{"example@example.com", "gitclone", "Example User", "password123", 0, "0123456789"});
////        txttentaikhoan = findViewById(R.id.txttentaikhoan);
////        txttennguoidung = findViewById(R.id.txttennguoidung);
//        String[] projection = {
//                "id",
//                "email",
//                "tenDangNhap",
//                "tenNguoiDung",
//                "matKhau",
//                "isAdmin",
//                "soDienThoai"
//        };
//
//        String orderBy = "id DESC";
//        String limit = "1";
//
//        //tạo con trỏ
//        Cursor cursor = db.query(
//                "Account",
//                projection,
//                null, // No selection clause
//                null, // No selection arguments
//                null, // No group by
//                null, // No having
//                orderBy, // Order by id descending
//                limit // Limit to 1 result
//        );
//
//        // Lấy ra dữ liệu hiển thị ở 2 text view mới thêm ở trong màn hình thêm
//        if (cursor.moveToFirst()) {
//            // Extract data from cursor
//            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
//            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
//            String tenDangNhap = cursor.getString(cursor.getColumnIndexOrThrow("tenDangNhap"));
//            String tenNguoiDung = cursor.getString(cursor.getColumnIndexOrThrow("tenNguoiDung"));
//            String matKhau = cursor.getString(cursor.getColumnIndexOrThrow("matKhau"));
//            String isAdmin = cursor.getString(cursor.getColumnIndexOrThrow("isAdmin"));
//            String soDienThoai = cursor.getString(cursor.getColumnIndexOrThrow("soDienThoai"));
//
//            // Display data (for example, log it or set it to a TextView)
////            txttentaikhoan.setText(tenDangNhap);
////            txttennguoidung.setText(tenNguoiDung);
//        }
//
//        cursor.close();
        // xóa đến đây nếu chạy thấy hiển thị được
/////////////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // Xử lý sự kiện khi nhấn vào menu "Đăng xuất"
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        // Chuyển đến màn hình đăng nhập
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish(); // Đóng màn hình MainActivity
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private void getview(){
//
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.fragment_container, new fragment_dangban());
//            transaction.commit();

    }

    private void themAccount(String email, String tenDangNhap, String tenNguoiDung, String matKhau, Integer isAdmin, String soDienThoai)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "INSERT INTO Account (email, tenDangNhap, tenNguoiDung, matKhau, isAdmin, soDienThoai) VALUES (?, ?, ?, ?, ?, ?)";
        Object[] bindArgs = {email, tenDangNhap, tenNguoiDung, matKhau, isAdmin, soDienThoai};
        db.execSQL(sql, bindArgs);
    }


}