package btl_android_2.com.ui.danhSach;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import btl_android_2.com.R;
import btl_android_2.com.ui.DBSQLite.DatabaseHelper;


public class fragment_danhsach extends Fragment {
    private RecyclerView recyclerView;
    private TaiLieuAdapter adapter;
    private List<TaiLieu> taiLieuList;
    private DatabaseHelper databaseHelper;
    private Spinner spinnerFilter, spinnerLoc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danhsach, container, false);

        spinnerFilter = view.findViewById(R.id.spinnerFilter);
        spinnerLoc = view.findViewById(R.id.spinner_loc);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taiLieuList = new ArrayList<>();
        adapter = new TaiLieuAdapter(getContext(), taiLieuList, this::showDetails);
        recyclerView.setAdapter(adapter);

        databaseHelper = DatabaseHelper.getInstance(getContext());
//        databaseHelper.deleteDatabase(context);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.filter_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(spinnerAdapter);

//        ArrayAdapter<CharSequence> spinnerAdapter2 = ArrayAdapter.createFromResource(getContext(),
//                R.array.fillter_loc1, android.R.layout.simple_spinner_item);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerLoc.setAdapter(spinnerAdapter2);

        loadLoaiTaiLieuToSpinner();

        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterDocuments();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        spinnerLoc.setOnItemSelectedListener(onItemSelectedListener);
        spinnerFilter.setOnItemSelectedListener(onItemSelectedListener);

        loadTaiLieu(0);

        return view;
    }

    private void loadLoaiTaiLieuToSpinner() {
        List<String> loaiTaiLieuList = new ArrayList<>();
        loaiTaiLieuList.add("Tất cả tài liệu"); // Thêm tùy chọn "Tất cả tài liệu"
        Cursor cursor = databaseHelper.getAllLoaiTaiLieu();
        int loaiTaiLieuId = spinnerLoc.getSelectedItemPosition();

        if (cursor != null && cursor.moveToFirst()) {
            int tenLoaiIndex = cursor.getColumnIndex("ten");
            loaiTaiLieuId++;
            do {

                if (tenLoaiIndex != -1) {
                    String tenLoai = cursor.getString(tenLoaiIndex);
                    loaiTaiLieuList.add(tenLoai);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, loaiTaiLieuList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoc.setAdapter(adapter);
    }

    private void loadTaiLieu(int filterType) {
        taiLieuList.clear();
        Cursor cursor;
        if (filterType == 0) {
            cursor = databaseHelper.getAllDocuments();
        } else {
            cursor = databaseHelper.getDocumentsByType(filterType == 1);
        }

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int tieuDeIndex = cursor.getColumnIndex("tieuDe");
            int moTaIndex = cursor.getColumnIndex("moTa");
            int noiDungIndex = cursor.getColumnIndex("noiDung");
            int trangThaiIndex = cursor.getColumnIndex("trangThai");
            int isFreeIndex = cursor.getColumnIndex("isFree");
            int giaIndex = cursor.getColumnIndex("gia");
            int idAccountIndex = cursor.getColumnIndex("idAccount");
            int idLoaiTaiLieuIndex = cursor.getColumnIndex("idLoaiTaiLieu");

            do {
                if (tieuDeIndex != -1 && moTaIndex != -1 && noiDungIndex != -1 && giaIndex != -1 && idAccountIndex != -1 && idLoaiTaiLieuIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String tieuDe = cursor.getString(tieuDeIndex);
                    String moTa = cursor.getString(moTaIndex);
                    String noiDung = cursor.getString(noiDungIndex);
                    int trangThai = cursor.getInt(trangThaiIndex);
                    boolean isFree = cursor.getInt(isFreeIndex) == 1;
                    int gia = cursor.getInt(giaIndex);
                    int idAccount = cursor.getInt(idAccountIndex);
                    int idLoaiTaiLieu = cursor.getInt(idLoaiTaiLieuIndex);
                    TaiLieu taiLieu = new TaiLieu(id, tieuDe, moTa, noiDung, trangThai, isFree, gia, idAccount, idLoaiTaiLieu);

                    taiLieuList.add(taiLieu);
                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        adapter.notifyDataSetChanged();
    }

    private void filterDocuments() {
        taiLieuList.clear();
        int loaiTaiLieuId = spinnerLoc.getSelectedItemPosition(); // Vị trí 0 tương ứng với "Tất cả tài liệu"
        boolean isFree = spinnerFilter.getSelectedItemPosition() == 1; // Giả định 0: Tất cả, 1: Miễn phí, 2: Mất phí

        Cursor cursor;

        if (loaiTaiLieuId == 0) { // Tất cả tài liệu
            if (spinnerFilter.getSelectedItemPosition() == 0) { // Tất cả tài liệu và trạng thái miễn phí/mất phí
                cursor = databaseHelper.getAllDocuments();
            } else {
                cursor = databaseHelper.getDocumentsByType(isFree);
            }
        } else {
            loaiTaiLieuId--; // Giảm 1 để khớp với idLoaiTaiLieu trong cơ sở dữ liệu
            if (spinnerFilter.getSelectedItemPosition() == 0) { // Tất cả tài liệu theo loại tài liệu
                cursor = databaseHelper.getDocumentsByLoaiTaiLieu(loaiTaiLieuId);
            } else {
                cursor = databaseHelper.getDocumentsByLoaiTaiLieuAndType(loaiTaiLieuId, isFree);
            }
        }

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int tieuDeIndex = cursor.getColumnIndex("tieuDe");
            int moTaIndex = cursor.getColumnIndex("moTa");
            int noiDungIndex = cursor.getColumnIndex("noiDung");
            int trangThaiIndex = cursor.getColumnIndex("trangThai");
            int isFreeIndex = cursor.getColumnIndex("isFree");
            int giaIndex = cursor.getColumnIndex("gia");
            int idAccountIndex = cursor.getColumnIndex("idAccount");
            int idLoaiTaiLieuIndex = cursor.getColumnIndex("idLoaiTaiLieu");

            do {
                if (tieuDeIndex != -1 && moTaIndex != -1 && noiDungIndex != -1 && giaIndex != -1 && idAccountIndex != -1 && idLoaiTaiLieuIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String tieuDe = cursor.getString(tieuDeIndex);
                    String moTa = cursor.getString(moTaIndex);
                    String noiDung = cursor.getString(noiDungIndex);
                    int trangThai = cursor.getInt(trangThaiIndex);
                    boolean isFreeValue = cursor.getInt(isFreeIndex) == 1;
                    int gia = cursor.getInt(giaIndex);
                    int idAccount = cursor.getInt(idAccountIndex);
                    int idLoaiTaiLieu = cursor.getInt(idLoaiTaiLieuIndex);

                    TaiLieu taiLieu = new TaiLieu(id, tieuDe, moTa, noiDung, trangThai, isFreeValue, gia, idAccount, idLoaiTaiLieu);
                    taiLieuList.add(taiLieu);
                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        adapter.notifyDataSetChanged();
    }

    //    private void showDetails(TaiLieu taiLieu) {
//        ChiTietTaiLieuActivity.startActivity(getContext(), taiLieu);
//    }
    private void showDetails(TaiLieu taiLieu) {
        try {
            if (taiLieu != null) {
                Intent intent;
                if (taiLieu.isFree()) {
                    intent = new Intent(getContext(), activity_tailieu_free.class);
                } else {
                    intent = new Intent(getContext(), activity_tailieu.class);
                }
                intent.putExtra("taiLieu", taiLieu);
                startActivity(intent);
            } else {
                Log.e("showDetails", "TaiLieu object is null");
            }
        } catch (Exception e) {
            Log.e("showDetails", "Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

}