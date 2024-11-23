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
    private Button btn1,btn2;
    private List<String> spinnerItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danhsach, container, false);


        spinner = view.findViewById(R.id.sp_loc);
        btn1=view.findViewById(R.id.btn_xem1);
        btn2=view.findViewById(R.id.btn_xem2);




        spinnerItems = new ArrayList<>();
        spinnerItems.add("Tất cả");
        spinnerItems.add("Tài liệu mất phí ");
        spinnerItems.add("Tài liệu không mất phí");



        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = spinnerItems.get(position);

                handleSelectedItem(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn();

        return view;
    }
    private void btn(){
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_tailieu.class);
                // Start the new activity
                startActivity(intent);


            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_tailieu_free.class);
                // Start the new activity
                startActivity(intent);


            }
        });

    }

    private void handleSelectedItem(String selectedItem) {

    }
}