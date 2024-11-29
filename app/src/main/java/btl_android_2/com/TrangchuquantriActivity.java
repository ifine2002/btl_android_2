package btl_android_2.com;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        db = DatabaseHelper.getInstance(this);
        mainLayout = findViewById(R.id.main);
        pendingCountTextView = findViewById(R.id.txtDemChoDuyet);
//        SQLiteDatabase db = DatabaseHelper.getInstance(this).getWritableDatabase();
//        int rowsDeleted = db.delete("TaiLieu", null, null);
//        int rowsDeleted2 = db.delete("Account", "tenDangNhap = ?", new String[]{"user1"});
//        int rowsDeleted3 = db.delete("Account", "tenDangNhap = ?", new String[]{"user1"});
//        int delete= db.delete("LoaiTaiLieu",null,null);
        loadPendingDocuments();

    }

    private void loadPendingDocuments() {
//        db.insertDummyUsers();
//        db.insertDummyLoaiTaiLieu();
//        db.insertDummyDocuments();
        Cursor cursor = db.getPendingDocuments();
        int count = cursor.getCount();
        pendingCountTextView.setText(String.valueOf(count));

        if (cursor.moveToFirst()) {
            do {

                if (!cursor.isAfterLast()) {
                    int documentId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow("tieuDe"));
                    String author = cursor.getString(cursor.getColumnIndexOrThrow("idAccount"));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow("moTa"));
                    int price = cursor.getInt(cursor.getColumnIndexOrThrow("gia"));
                    addDocumentView(title, author, description, price, documentId);
                }


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
        TextView authorTextView = documentView.findViewById(R.id.txtTacGia);
        TextView descriptionTextView = documentView.findViewById(R.id.txtMoTa);
        TextView priceTextView = documentView.findViewById(R.id.txtGia);
        Button btnXemChiTiet = documentView.findViewById(R.id.btnXemChiTiet);
        Button btnTuChoi = documentView.findViewById(R.id.btnTuChoi);

        titleTextView.setText(title);
        authorTextView.setText(author);
        descriptionTextView.setText(description);
        priceTextView.setText(String.valueOf(price) + " VNĐ");

        btnXemChiTiet.setOnClickListener(v -> {
            Intent intent = new Intent(TrangchuquantriActivity.this, tailieuquantriActivity.class);
            intent.putExtra("documentId", documentId);
            startActivity(intent);
        });

        btnTuChoi.setOnClickListener(v -> {
            boolean success = db.capNhatTrangThai(documentId, -1,-1); // Trạng thái -1 cho từ chối
            if (success) {
                // Tuỳ chọn xóa view hoặc cập nhật UI
                mainLayout.removeView(documentView);
                updatePendingCount();
            }
        });

        mainLayout.addView(documentView);
    }

}
