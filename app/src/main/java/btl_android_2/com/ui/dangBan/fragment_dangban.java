package btl_android_2.com.ui.dangBan;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import btl_android_2.com.R;
import btl_android_2.com.ui.DBSQLite.DatabaseHelper;


public class fragment_dangban extends Fragment {

    DatabaseHelper myDb;
    EditText etTenTaiLieu;
    EditText etMoTa;
    EditText etGia;
    Button btnDangBan;
    public fragment_dangban() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dangban, container, false);
        etTenTaiLieu = view.findViewById(R.id.ten_tai_lieu);
        etMoTa = view.findViewById(R.id.mo_ta);
        etGia = view.findViewById(R.id.gia);
        btnDangBan = view.findViewById(R.id.dang_ban);

        myDb = new DatabaseHelper(getContext());
        btnDangBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mota = etMoTa.getText().toString().trim();
                String tentailieu = etTenTaiLieu.getText().toString().trim();
                String gia = etGia.getText().toString().trim();

                Log.d("FragmentDangban", "Button DangBan clicked");

                if (TextUtils.isEmpty(mota) || TextUtils.isEmpty(gia) || TextUtils.isEmpty(tentailieu)) {
                    Toast.makeText(requireContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("FragmentDangban", "All fields are filled");

                myDb.dangBanTaiLieu(tentailieu, mota, gia);
                Log.d("FragmentDangban", "Document shared to database");

                showDialog(requireContext(), "Đăng bán tài liệu thành công");
                etTenTaiLieu.setText("");
                etMoTa.setText("");
                etGia.setText("");
            }
        });

        return view;
    }

    private void showDialog(Context context, String message) {
        Log.d("FragmentDangban", "showDialog called with message: " + message);

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
}