package btl_android_2.com.ui.taiLieu;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import btl_android_2.com.MainActivity;
import btl_android_2.com.R;
import btl_android_2.com.ui.DBSQLite.DatabaseHelper;

public class fragment_tailieucuatoi extends Fragment {
    private DatabaseHelper databaseHelper;
    private LinearLayout documentContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tailieucuatoi, container, false);
        documentContainer = view.findViewById(R.id.documentContainer); // Kiểm tra khởi tạo và ID
        databaseHelper = new DatabaseHelper(getActivity());
        // In giá trị MainActivity.Id ra Logcat
        Log.d("fragment_tailieucuatoi", "MainActivity.Id: " + MainActivity.Id);
        loadDocuments();
        return view;
    }

    private void loadDocuments() {
        Cursor cursor = databaseHelper.getDocumentsByUserId(MainActivity.Id); // Kiểm tra truy vấn

        if (cursor != null && cursor.moveToFirst()) { // Kiểm tra kết quả truy vấn
            do {
                int documentId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("tieuDe"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("moTa"));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow("gia"));
                int status = cursor.getInt(cursor.getColumnIndexOrThrow("trangThai"));

                // Add document view to container
                addDocumentView(documentId, title, description, price, status);
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(getActivity(), "No documents found", Toast.LENGTH_SHORT).show();
        }
        if (cursor != null) {
            cursor.close(); // Đóng cursor để giải phóng tài nguyên
        }
    }

    private void addDocumentView(int documentId, String title, String description, int price, int status) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View documentItem = inflater.inflate(R.layout.item_tailieucuatoi, documentContainer, false);

        TextView textViewTitle = documentItem.findViewById(R.id.txtTieuDe);
        TextView textViewDescription = documentItem.findViewById(R.id.txtMoTa);
        TextView textViewPrice = documentItem.findViewById(R.id.txtGia);
        TextView textViewStatus = documentItem.findViewById(R.id.txtTrangThai);
        Button buttonDelete = documentItem.findViewById(R.id.btnXoa);

        textViewTitle.setText(title);
        textViewDescription.setText(description);
        if(price == 0)
            textViewPrice.setText("Miễn phí");
        else
            textViewPrice.setText(price + " VNĐ");
        textViewStatus.setText(getStatusText(status));

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa tài liệu từ cơ sở dữ liệu
                boolean isDeleted = databaseHelper.deleteDocumentById(documentId);
                if (isDeleted) {
                    Toast.makeText(getActivity(), "Đã xóa", Toast.LENGTH_SHORT).show();
                    // Xóa mục từ container
                    documentContainer.removeView(documentItem);
                } else {
                    Toast.makeText(getActivity(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        documentContainer.addView(documentItem);
    }
    private String getStatusText(int status) {
        switch (status) {
            case 0:
                return "Chờ duyệt";
            case 1:
                return "Đã duyệt";
            case -1:
                return "Từ chối";
            default:
                return "Unknown";
        }
    }

}
