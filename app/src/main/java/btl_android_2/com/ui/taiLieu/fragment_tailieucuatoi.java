package btl_android_2.com.ui.taiLieu;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import btl_android_2.com.MainActivity;
import btl_android_2.com.R;
import btl_android_2.com.ui.DBSQLite.DatabaseHelper;


// Trong fragment_tailieucuatoi.java
public class fragment_tailieucuatoi extends Fragment {
    private DatabaseHelper databaseHelper;
    private LinearLayout documentContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tailieucuatoi, container, false);
        documentContainer = view.findViewById(R.id.documentContainer);
        databaseHelper = new DatabaseHelper(getActivity());

        loadDocuments();

        return view;
    }

    private void loadDocuments() {
        Cursor cursor = databaseHelper.getDocumentsByUserId(MainActivity.Id);
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow("tieuDe"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("moTa"));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow("gia"));
                int status = cursor.getInt(cursor.getColumnIndexOrThrow("trangThai"));

                // Inflate layout for each document item
                View documentItem = LayoutInflater.from(getActivity()).inflate(R.layout.item_tailieucuatoi, null);

                // Set data to views
                TextView textViewTitle = documentItem.findViewById(R.id.txtTieuDe);
                TextView textViewDescription = documentItem.findViewById(R.id.txtMoTa);
                TextView textViewPrice = documentItem.findViewById(R.id.txtGia);
                TextView textViewStatus = documentItem.findViewById(R.id.txtTrangThai);
                Button buttonDelete = documentItem.findViewById(R.id.btnXoa);

                textViewTitle.setText(title);
                textViewDescription.setText(description);
                textViewPrice.setText("Price: " + price);
                textViewStatus.setText("Status: " + (status == 0 ? "Pending" : "Approved"));

                // Set click listener for delete button
                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle delete button click
                        // You can implement delete logic here
                    }
                });

                // Add document item to container
                documentContainer.addView(documentItem);

            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
