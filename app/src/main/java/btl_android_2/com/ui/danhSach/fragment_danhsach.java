package btl_android_2.com.ui.danhSach;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import btl_android_2.com.R;

/**
// * A simple {@link Fragment} subclass.
// * Use the {@link fragment_danhsach# newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_danhsach extends Fragment {
    private Spinner spinner;
    Button btn1,btn2;
    private List<String> spinnerItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danhsach, container, false);

        // Khởi tạo Spinner
        spinner = view.findViewById(R.id.sp_loc);

        // Tạo dữ liệu cho Spinner
        spinnerItems = new ArrayList<>();
        spinnerItems.add("Tất cả");
        spinnerItems.add("Tài liệu mất phí ");
        spinnerItems.add("Tài liệu không mất phí");


        // Tạo ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Gán adapter cho Spinner
        spinner.setAdapter(adapter);

        // Xử lý sự kiện khi chọn một mục trong Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý khi chọn một mục
                String selectedItem = spinnerItems.get(position);
                // Gọi phương thức để xử lý dữ liệu theo mục được chọn
                handleSelectedItem(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có mục nào được chọn
            }
        });

        return view;
    }

    private void handleSelectedItem(String selectedItem) {
        // Thực hiện xử lý dữ liệu tương ứng với mục được chọn
        // Ví dụ: hiển thị dữ liệu tùy thuộc vào mục được chọn
    }
}