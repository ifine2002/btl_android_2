package btl_android_2.com.ui.danhSach;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import btl_android_2.com.R;

public class activity_tailieu extends AppCompatActivity {

    ImageButton btn;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailieu);
        getView();
    }
    public void getView(){
//        txt=findViewById(R.id.txt_sđt);
        btn=findViewById(R.id.btn_call);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+txt.getText().toString().trim()));
                if (ActivityCompat.checkSelfPermission(activity_tailieu.this,
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity_tailieu.this, new
                            String[]{android.Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                startActivity(call);
            }
        });
    }
}