package btl_android_2.com.ui.trangChu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import btl_android_2.com.MainActivity;
import btl_android_2.com.R;


public class fragment_trangchu extends Fragment {
    private Button btnTaiLieuCuaToi, btnDanhSachTaiLieu, btnChiaSeTaiLieu, btnDangBanTaiLieu;


    public fragment_trangchu() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_trangchu, container, false);

        btnTaiLieuCuaToi = view.findViewById(R.id.btnTaiLieuCuaToi);
        btnDanhSachTaiLieu = view.findViewById(R.id.btnDanhSachTaiLieu);
        btnChiaSeTaiLieu = view.findViewById(R.id.btnChiaSeTaiLieu);
        btnDangBanTaiLieu = view.findViewById(R.id.btnDangBanTaiLieu);

        // Lắng nghe sự kiện click cho nút "Tài liệu của tôi"
        btnTaiLieuCuaToi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển sang màn hình "Tài liệu của tôi"
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        // Lắng nghe sự kiện click cho nút "Danh sách tài liệu"
        btnDanhSachTaiLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển sang màn hình "Danh sách tài liệu"
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        // Lắng nghe sự kiện click cho nút "Chia sẻ tài liệu"
        btnChiaSeTaiLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển sang màn hình "Chia sẻ tài liệu"
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        // Lắng nghe sự kiện click cho nút "Đăng bán tài liệu"
        btnDangBanTaiLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển sang màn hình "Đăng bán tài liệu"
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}