package btl_android_2.com.ui.danhSach;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import btl_android_2.com.R;

public class ChiTietTaiLieuActivity extends AppCompatActivity {

    private TextView txtTieuDe, txtTacGia, txtMoTa, txtNoiDung, txtGia, txtSoDienThoai;
    ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailieu);

        txtTieuDe = findViewById(R.id.tieuDe1);
        txtTacGia = findViewById(R.id.tacGia1);
        txtMoTa = findViewById(R.id.moTa1);
        txtNoiDung = findViewById(R.id.noiDung1);
        txtGia = findViewById(R.id.gia1);
        txtSoDienThoai=findViewById(R.id.soDienThoai1);
        btn=findViewById(R.id.btn_call);

        Intent intent = getIntent();
        if (intent != null) {
            TaiLieu taiLieu = intent.getParcelableExtra("taiLieu");
            if (taiLieu != null) {
                txtTieuDe.setText(taiLieu.getTieuDe());
//                txtTacGia.setText(taiLieu.getTacGia());
                txtMoTa.setText(taiLieu.getMoTa());
                txtNoiDung.setText(taiLieu.getNoiDung());
//                txtSoDienThoai.setText(taiLieu.getSoDienThoai());
                txtGia.setText(taiLieu.isFree() ? "Miễn phí" : taiLieu.getGia() + " VND");
            } else {
                Toast.makeText(this, "Tài liệu không tồn tại", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Lỗi khi nhận dữ liệu", Toast.LENGTH_SHORT).show();
        }
        getView();
    }

    public static void startActivity(Context context, TaiLieu taiLieu) {
        Intent intent = new Intent(context, ChiTietTaiLieuActivity.class);
        intent.putExtra("taiLieu", taiLieu);
        context.startActivity(intent);
    }
    public void getView(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+txtSoDienThoai.getText().toString().trim()));
                if (ActivityCompat.checkSelfPermission(ChiTietTaiLieuActivity.this,
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ChiTietTaiLieuActivity.this, new
                            String[]{android.Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                startActivity(call);
            }
        });
    }
}
