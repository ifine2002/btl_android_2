package btl_android_2.com;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import btl_android_2.com.ui.DBSQLite.DatabaseHelper;

public class tailieuquantriActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private int documentId;
    private TextView tvTitle, tvUser, tvPrice, tvContent;
    private Spinner spinnerLoaiTaiLieu;
    private Button btnDuyet, btnTuChoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailieuquantri);

        db = DatabaseHelper.getInstance(this);

        tvTitle = findViewById(R.id.txtTieuDe);
        tvUser = findViewById(R.id.txtTacGia);
        tvPrice = findViewById(R.id.txtGia);
        tvContent = findViewById(R.id.txtNoiDung);
        spinnerLoaiTaiLieu = findViewById(R.id.spinnerLoaiTaiLieu);
        btnDuyet = findViewById(R.id.btnDuyet);
        btnTuChoi = findViewById(R.id.btnTuChoi);

        // Get the document ID from the Intent
        documentId = getIntent().getIntExtra("documentId", -1);
        loadDocumentDetails();

        btnDuyet.setOnClickListener(v -> {
            // Lấy ID của loại tài liệu từ Spinner
            String selectedLoaiTaiLieu = (String) spinnerLoaiTaiLieu.getSelectedItem();
            int idLoaiTaiLieu = getIdLoaiTaiLieu(selectedLoaiTaiLieu);

            // Cập nhật trạng thái tài liệu thành đã duyệt (status = 1) và loại tài liệu
            updateDocumentStatus(1, idLoaiTaiLieu);
            Intent intent = new Intent(tailieuquantriActivity.this, TrangchuquantriActivity.class);
            intent.putExtra("documentId", documentId);
            startActivity(intent);
        });

        btnTuChoi.setOnClickListener(v -> {
            // Cập nhật trạng thái tài liệu thành từ chối (status = -1), không cần loại tài liệu
            updateDocumentStatus(-1, -1);
            Intent intent = new Intent(tailieuquantriActivity.this, TrangchuquantriActivity.class);
            intent.putExtra("documentId", documentId);
            startActivity(intent);
        });
    }

    private void loadDocumentDetails() {
        Cursor cursor = db.getDocumentById(documentId);
        if (cursor != null && cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow("tieuDe"));
            String user = cursor.getString(cursor.getColumnIndexOrThrow("idAccount"));
            int price = cursor.getInt(cursor.getColumnIndexOrThrow("gia"));
            String content = cursor.getString(cursor.getColumnIndexOrThrow("noiDung"));

            tvTitle.setText(title);
            tvUser.setText(user);
            tvPrice.setText(price + " VNĐ");
            tvContent.setText(content);

            cursor.close();
        }

        loadLoaiTaiLieu();
    }

    private void loadLoaiTaiLieu() {
        List<String> loaiTaiLieuList = new ArrayList<>();
        Cursor cursor = db.getAllLoaiTaiLieu();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String loaiTaiLieu = cursor.getString(cursor.getColumnIndexOrThrow("ten"));
                loaiTaiLieuList.add(loaiTaiLieu);
            } while (cursor.moveToNext());
            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, loaiTaiLieuList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoaiTaiLieu.setAdapter(adapter);
    }


    private void updateDocumentStatus(int status, int idLoaiTaiLieu) {
        boolean success = db.capNhatTrangThai(documentId, status, idLoaiTaiLieu);
        if (success) {
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish(); // Kết thúc hoạt động hiện tại và trở lại TrangchuquantriActivity
        }
    }
    private int getIdLoaiTaiLieu(String loaiTaiLieuName) {
        Cursor cursor = db.getAllLoaiTaiLieu();
        int id = -1; // Khởi tạo ID mặc định là -1 nếu không tìm thấy

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String loaiTaiLieu = cursor.getString(cursor.getColumnIndexOrThrow("ten"));
                if (loaiTaiLieu.equals(loaiTaiLieuName)) {
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    break;
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        return id;
    }

}
