package btl_android_2.com.ui.danhSach;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import btl_android_2.com.R;
import btl_android_2.com.ui.DBSQLite.DatabaseHelper;

public class activity_tailieu_free extends AppCompatActivity {

    private TextView txtTieuDe, txtTacGia, txtMoTa, txtGia;
    private Button btn;

    private WebView webView;
    private String nd;
    private DatabaseHelper databaseHelper;
    private static final int REQUEST_WRITE_STORAGE = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailieu_free);

        txtTieuDe = findViewById(R.id.tieuDe1);
        txtTacGia = findViewById(R.id.tacGia1);
        txtMoTa = findViewById(R.id.moTa1);
        webView = findViewById(R.id.webView);
        txtGia = findViewById(R.id.gia1);
        btn = findViewById(R.id.btnDownload);
        databaseHelper = new DatabaseHelper(this);
        // nhận dữ liệu tài liệu
        Intent intent = getIntent();
        if (intent != null) {
            TaiLieu taiLieu = intent.getParcelableExtra("taiLieu");
            if (taiLieu != null) {
                txtTieuDe.setText(taiLieu.getTieuDe());
                String tenTacGia = databaseHelper.getTacGiaByIdAccount(taiLieu.getIdAccount());
                if (tenTacGia != null) {
                    txtTacGia.setText(tenTacGia);
                } else {
                    txtTacGia.setText("Không có tác giả");
                }
                String noidung_html = taiLieu.getNoiDung();
                nd = noidung_html;
                webView.loadDataWithBaseURL(null, noidung_html, "text/html", "UTF-8", null);
                txtMoTa.setText(taiLieu.getMoTa());
                txtGia.setText("Miễn phí");
            } else {
                Toast.makeText(this, "Tài liệu không tồn tại", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Lỗi khi nhận dữ liệu", Toast.LENGTH_SHORT).show();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }
        TaiLieu taiLieu = intent.getParcelableExtra("taiLieu");
//        if (taiLieu != null) {
//            Log.d("ID_ACCOUNT_DEBUG", "idAccount: " + taiLieu.getIdAccount());
//            // Tiếp tục xử lý dữ liệu
//        }


        getView();
    }
        // sử lý quyền truy cập vào độ nhớ ngoài
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Quyền truy cập bộ nhớ ngoài đã được cấp", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Quyền truy cập bộ nhớ ngoài bị từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }
        // thuc hiện lưu file
    public void getView() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textData = nd;
                saveDataToFile("myDataFile_" + getCurrentTimeStamp() + ".txt", textData);
            }
        });
    }
    // luu file vào bộ nhớ ngoài trong thư mục Documents
    private void saveDataToFile(String fileName, String data) {
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(directory, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            Toast.makeText(this, "Lưu file thành công: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
            MediaScannerConnection.scanFile(this, new String[]{file.getAbsolutePath()}, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lưu file không thành công", Toast.LENGTH_SHORT).show();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // set thời gian lưu
    private String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
