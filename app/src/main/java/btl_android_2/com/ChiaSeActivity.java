package btl_android_2.com;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import btl_android_2.com.ui.DBSQLite.DatabaseHelper;

public class ChiaSeActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText etTenTaiLieu;
    EditText etMoTa;
    EditText etNoiDung;
    Button btnChiaSe;
    Button btnChonFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chiase);
        etTenTaiLieu = findViewById(R.id.ten_tai_lieu);
        etMoTa = findViewById(R.id.mo_ta);
        etNoiDung = findViewById(R.id.noi_dung);
        btnChiaSe = findViewById(R.id.chia_se);
        btnChonFile = findViewById(R.id.chon_file);


        btnChiaSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mota = etMoTa.getText().toString().trim();
                String noidung = etNoiDung.getText().toString().trim();
                String tentailieu = etTenTaiLieu.getText().toString().trim();

                if (TextUtils.isEmpty(mota) || TextUtils.isEmpty(noidung) || TextUtils.isEmpty(tentailieu)) {
                    Toast.makeText(ChiaSeActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                myDb.chiaSeTaiLieu(tentailieu, mota, noidung);
                showDialog(ChiaSeActivity.this, "Chia sẻ tài liệu thành công");
            }

            public void showDialog(Context context, String message) {
                // Tạo một AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(message);
                builder.setCancelable(true);

                final AlertDialog dialog = builder.create();
                dialog.show();

                // Sử dụng Handler để đóng dialog sau 3 giây (3000 milliseconds)
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }, 3000); // 3000 milliseconds = 3 seconds
            }
        });
    }
}

