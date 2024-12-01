package btl_android_2.com.ui.chiaSe;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
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
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.Intent;
import android.app.Activity;


public class fragment_chiase extends Fragment {

    private static final int REQUEST_CODE_PICK_FILE = 1;
    DatabaseHelper myDb;
    EditText etTenTaiLieu;
    EditText etMoTa;
    EditText etNoiDung;
    Button btnChiaSe;
        Button btnChonFile;

    public fragment_chiase() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chiase, container, false);
        etTenTaiLieu = view.findViewById(R.id.ten_tai_lieu);
        etMoTa = view.findViewById(R.id.mo_ta);
        etNoiDung = view.findViewById(R.id.noi_dung);
        btnChiaSe = view.findViewById(R.id.chia_se);
        btnChonFile = view.findViewById(R.id.chon_file);

        myDb = new DatabaseHelper(getContext());


        btnChiaSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mota = etMoTa.getText().toString().trim();
                String noidung = etNoiDung.getText().toString().trim();
                String tentailieu = etTenTaiLieu.getText().toString().trim();

                Log.d("FragmentChiase", "Button ChiaSe clicked");

                if (TextUtils.isEmpty(mota) || TextUtils.isEmpty(noidung) || TextUtils.isEmpty(tentailieu)) {
                    Toast.makeText(requireContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("FragmentChiase", "All fields are filled");

                myDb.chiaSeTaiLieu(tentailieu, mota, noidung);
                Log.d("FragmentChiase", "Document shared to database");

                showDialog(requireContext(), "Chia sẻ tài liệu thành công");
                etMoTa.setText("");
                etTenTaiLieu.setText("");
                etNoiDung.setText("");
            }
        });


        btnChonFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker();
            }
        });

        return view;
    }

    private void showDialog(Context context, String message) {
        Log.d("FragmentChiase", "showDialog called with message: " + message);

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

    public void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Để chọn tất cả các loại file
        Toast.makeText(requireContext(), "Noi dung: " , Toast.LENGTH_SHORT).show();
        startActivityForResult(intent, REQUEST_CODE_PICK_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_FILE && resultCode == Activity.RESULT_OK) {
            Toast.makeText(requireContext(), "Noi dung 2: " , Toast.LENGTH_SHORT).show();
            if (data != null) {
                Toast.makeText(requireContext(), "Noi dung 3: " , Toast.LENGTH_SHORT).show();
                Uri uri = data.getData();
                readFile(uri);
            }
        }
    }

    public void readFile(Uri uri) {
        try {
            InputStream inputStream = requireActivity().getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            //Toast.makeText(requireContext(), "Noi dung: " + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            reader.close();
            String fileContent = stringBuilder.toString();
            Log.d("FileContent", fileContent);
            //Toast.makeText(requireContext(), "Noi dung: " + fileContent, Toast.LENGTH_SHORT).show();
            etNoiDung.setText(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
