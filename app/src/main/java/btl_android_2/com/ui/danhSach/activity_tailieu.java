package btl_android_2.com.ui.danhSach;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import btl_android_2.com.R;
import btl_android_2.com.ui.DBSQLite.DatabaseHelper;
//import btl_android_2.com.ui.models.TaiLieu;

public class activity_tailieu extends AppCompatActivity {

    private TextView txtTieuDe, txtTacGia, txtMoTa, txtGia, txtSoDienThoai;
    private Button btn;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailieu);

        txtTieuDe = findViewById(R.id.tieuDe1);
        txtTacGia = findViewById(R.id.tacGia1);
        txtMoTa = findViewById(R.id.moTa1);
        txtGia = findViewById(R.id.gia1);
        txtSoDienThoai = findViewById(R.id.soDienThoai1);
        btn = findViewById(R.id.btn_call);

        databaseHelper = new DatabaseHelper(this);
        // nhận dữ liệu tài liệu
        Intent intent = getIntent();
        if (intent != null) {
            TaiLieu taiLieu = intent.getParcelableExtra("taiLieu");
            if (taiLieu != null) {
                txtTieuDe.setText(taiLieu.getTieuDe());
                if (taiLieu.getIdAccount() != -1) {
                    String tenTacGia = databaseHelper.getTacGiaByIdAccount(taiLieu.getIdAccount());
                    if (tenTacGia != null) {
                        txtTacGia.setText(tenTacGia);
                    } else {
                        txtTacGia.setText("Không có tác giả");
                    }
                    String sdt = databaseHelper.getSDTByIdAccount(taiLieu.getIdAccount());
                    if (sdt != null) {
                        txtSoDienThoai.setText(sdt);
                    }
                }
                String mota_html = taiLieu.getMoTa();
                txtMoTa.setText(Html.fromHtml(mota_html, Html.FROM_HTML_MODE_COMPACT));

                if (taiLieu.getGia() == 0) {
                    txtGia.setText("Miễn phí");
                } else {
                    txtGia.setText(taiLieu.getGia() + " VND");
                }
            } else {
                Toast.makeText(this, "Tài liệu không tồn tại", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Lỗi khi nhận dữ liệu", Toast.LENGTH_SHORT).show();
        }

        // Xử lý sự kiện cho nút gọi điện
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumber = txtSoDienThoai.getText().toString().trim();
                if (!phoneNumber.isEmpty()) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));

                    if (ActivityCompat.checkSelfPermission(activity_tailieu.this,
                            android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(activity_tailieu.this, new
                                String[]{android.Manifest.permission.CALL_PHONE}, 1);
                    } else {
                        startActivity(callIntent);
                    }
                } else {
                    Toast.makeText(activity_tailieu.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
