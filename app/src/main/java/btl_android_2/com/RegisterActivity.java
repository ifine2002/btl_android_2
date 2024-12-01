package btl_android_2.com;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import btl_android_2.com.ui.DBSQLite.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editSDT, editUserName, editPassword, editConfirm;
    Button btnDongY, btnThoat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);

        myDb = new DatabaseHelper(this);

        editSDT = findViewById(R.id.editSDT);
        editUserName = findViewById(R.id.editUserName);
        editPassword = findViewById(R.id.editPassword);
        editConfirm = findViewById(R.id.editConfirm);
        btnDongY = findViewById(R.id.btnDongY);
        btnThoat = findViewById(R.id.btnThoat);

        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = editSDT.getText().toString().trim();
                String username = editUserName.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                String confirmPassword = editConfirm.getText().toString().trim();

                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.length() != 10) {
                    Toast.makeText(RegisterActivity.this, "Số điện thoại phải đúng 10 số", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu xác nhận không trùng khớp", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Kiểm tra username đã tồn tại
                if (myDb.checkUsernameExist(username)) {
                    Toast.makeText(RegisterActivity.this, "Tên đăng nhập đã được sử dụng", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra số điện thoại đã tồn tại
                if (myDb.checkPhoneExist(phone)) {
                    Toast.makeText(RegisterActivity.this, "Số điện thoại đã được sử dụng", Toast.LENGTH_SHORT).show();
                    return;
                }


                boolean isInserted = myDb.insertData(phone, username, password);
                if (isInserted) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
    }

}
