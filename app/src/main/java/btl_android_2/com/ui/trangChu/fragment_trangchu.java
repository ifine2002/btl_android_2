package btl_android_2.com.ui.trangChu;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import btl_android_2.com.LoginActivity;
import btl_android_2.com.MainActivity;
import btl_android_2.com.R;
import btl_android_2.com.ui.DBSQLite.DatabaseHelper;
import btl_android_2.com.ui.danhSach.TaiLieu;
import btl_android_2.com.ui.danhSach.activity_tailieu;
import btl_android_2.com.ui.danhSach.activity_tailieu_free;

public class fragment_trangchu extends Fragment {
    private DatabaseHelper dbHelper;
    private LinearLayout latestDocumentsLayout;

    public fragment_trangchu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trangchu, container, false);
        latestDocumentsLayout = view.findViewById(R.id.latestDocumentsLayout);
        dbHelper = DatabaseHelper.getInstance(getActivity()); // Initialize dbHelper using activity context
        displayLatestDocuments();
        return view;
    }

    private void displayLatestDocuments() {
        Cursor cursor = dbHelper.getLatestDocuments(7);
        latestDocumentsLayout.removeAllViews();

        if (cursor.moveToFirst()) {
            do {
                String documentTitle = cursor.getString(cursor.getColumnIndexOrThrow("tieuDe"));
                String documentDescription = cursor.getString(cursor.getColumnIndexOrThrow("moTa"));
                int documentId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));

                // Create a parent LinearLayout to hold title and description
                LinearLayout documentLayout = new LinearLayout(getContext());
                documentLayout.setOrientation(LinearLayout.VERTICAL);
                documentLayout.setPadding(8, 8, 8, 8);
                documentLayout.setBackgroundResource(R.drawable.document_item_background);

                // Set margins
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(0, 0, 0, 16);
                documentLayout.setLayoutParams(layoutParams);

                // Create TextView for document title
                TextView titleTextView = new TextView(getContext());
                titleTextView.setText(documentTitle);
                titleTextView.setTextSize(18);
                titleTextView.setTypeface(null, Typeface.BOLD);
                titleTextView.setPadding(0, 0, 0, 4);

                // Create TextView for document description
                TextView descriptionTextView = new TextView(getContext());
                descriptionTextView.setText(documentDescription);
                descriptionTextView.setTextSize(16);

                // Add TextViews to documentLayout
                documentLayout.addView(titleTextView);
                documentLayout.addView(descriptionTextView);

                // Set onClickListener to the documentLayout
                documentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openDocumentDetail(documentId);
                    }
                });

                // Add documentLayout to latestDocumentsLayout
                latestDocumentsLayout.addView(documentLayout);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void openDocumentDetail(int documentId) {
        Cursor cursor = dbHelper.getDocumentById(documentId);
        if (cursor.moveToFirst()) {
            String tieuDe = cursor.getString(cursor.getColumnIndexOrThrow("tieuDe"));
            String moTa = cursor.getString(cursor.getColumnIndexOrThrow("moTa"));
            String noiDung = cursor.getString(cursor.getColumnIndexOrThrow("noiDung"));
            int trangThai = cursor.getInt(cursor.getColumnIndexOrThrow("trangThai"));
            boolean isFree = cursor.getInt(cursor.getColumnIndexOrThrow("isFree")) == 1;
            int gia = cursor.getInt(cursor.getColumnIndexOrThrow("gia"));
            int idAccount = cursor.getInt(cursor.getColumnIndexOrThrow("idAccount"));
            int idLoaiTaiLieu = cursor.getInt(cursor.getColumnIndexOrThrow("idLoaiTaiLieu"));

            TaiLieu taiLieu = new TaiLieu(documentId, tieuDe, moTa, noiDung, trangThai, isFree, gia, idAccount, idLoaiTaiLieu);
            showDetails(taiLieu);
        }
        cursor.close();
    }

    private void showDetails(TaiLieu taiLieu) {
        Intent intent;
        if (taiLieu.isFree()) {
            intent = new Intent(getContext(), activity_tailieu_free.class);
        } else {
            intent = new Intent(getContext(), activity_tailieu.class);
        }
        intent.putExtra("taiLieu", taiLieu);
        startActivity(intent);
    }
}
