package btl_android_2.com;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import btl_android_2.com.ui.DBSQLite.DatabaseHelper;

public class TrangchuquantriActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private LinearLayout mainLayout;
    private TextView pendingCountTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchuquantri);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        db = DatabaseHelper.getInstance(this);
        mainLayout = findViewById(R.id.main);
        pendingCountTextView = findViewById(R.id.txtDemChoDuyet);
//        SQLiteDatabase db = DatabaseHelper.getInstance(this).getWritableDatabase();
//        int rowsDeleted = db.delete("TaiLieu", null, null);
//        int rowsDeleted2 = db.delete("Account", null, null);
//        int delete= db.delete("LoaiTaiLieu",null,null);
        loadPendingDocuments();
    }

    private void loadPendingDocuments() {
        Cursor cursor = db.getPendingDocuments();
        int count = cursor.getCount();
        pendingCountTextView.setText(String.valueOf(count));

        if (cursor.moveToFirst()) {
            do {
                int documentId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("tieuDe"));
                int authorId = cursor.getInt(cursor.getColumnIndexOrThrow("idAccount"));
                // Kiểm tra authorId có giá trị hợp lệ hay không
                String author;
                author = db.getTacGiaByIdAccount(authorId);
                if (author == null) {
                    author = "Unknown Author"; // Xử lý khi không tìm thấy tên tác giả
                }
                Log.d("trangchuquantri", "idAccount: " + authorId);
                String description = cursor.getString(cursor.getColumnIndexOrThrow("moTa"));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow("gia"));
                addDocumentView(title, author, description, price, documentId);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }


    private void updatePendingCount() {
        Cursor cursor = db.getPendingDocuments();
        int count = cursor.getCount();
        pendingCountTextView.setText(String.valueOf(count));
        cursor.close();
    }


    private void addDocumentView(String title, String author, String description, int price, int documentId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View documentView = inflater.inflate(R.layout.item_tailieuquantri, mainLayout, false);

        TextView titleTextView = documentView.findViewById(R.id.txtTieuDe);
        TextView authorTextView = documentView.findViewById(R.id.txtTacGiaName); // TextView để hiển thị tên tác giả
        TextView descriptionTextView = documentView.findViewById(R.id.txtMoTa);
        TextView priceTextView = documentView.findViewById(R.id.txtGia);
        Button btnXemChiTiet = documentView.findViewById(R.id.btnXemChiTiet);
        Button btnTuChoi = documentView.findViewById(R.id.btnTuChoi);

        titleTextView.setText(title);

        // Kiểm tra và hiển thị tên tác giả
        if (author != null) {
            authorTextView.setText(author);
        } else {
            authorTextView.setText("Unknown Author");
        }

        descriptionTextView.setText(description);
        priceTextView.setText(String.valueOf(price) + " VNĐ");

        btnXemChiTiet.setOnClickListener(v -> {
            Intent intent = new Intent(TrangchuquantriActivity.this, tailieuquantriActivity.class);
            intent.putExtra("documentId", documentId);
            startActivity(intent);
        });

        btnTuChoi.setOnClickListener(v -> {
            boolean success = db.capNhatTrangThai(documentId, -1, -1); // Trạng thái -1 cho từ chối
            if (success) {
                mainLayout.removeView(documentView);
                updatePendingCount();
            }
        });

        mainLayout.addView(documentView);
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
}
