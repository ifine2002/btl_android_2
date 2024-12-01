package btl_android_2.com;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import btl_android_2.com.ui.DBSQLite.DatabaseHelper;
import btl_android_2.com.ui.danhSach.activity_tailieu;
import btl_android_2.com.ui.trangChu.fragment_trangchu;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editUserName, editPassword;
    Button btnDongY, btnDangKy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);
        myDb = new DatabaseHelper(this);
        editUserName = findViewById(R.id.editUserName);
        editPassword = findViewById(R.id.editPassword);
        btnDongY = findViewById(R.id.btnDongY);
        btnDangKy = findViewById(R.id.btnDangKy);

        // Handle the "Đồng ý" button click
        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUserName.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                Cursor cursor = myDb.checkUser(username, password);
                if (cursor.moveToFirst()) {
                    int isAdmin = cursor.getInt(cursor.getColumnIndexOrThrow("isAdmin"));
                    MainActivity.tenDangNhap = username;
                    MainActivity.Id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    cursor.close();

                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                    if (isAdmin == 1) {
                        // User is an admin, navigate to AdminActivity
                        Intent intent = new Intent(LoginActivity.this, TrangchuquantriActivity.class);
                        startActivity(intent);
                    } else {
                        // User is not an admin, navigate to MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                } else {
                    cursor.close();
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Handle the "Đăng ký" button click
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
