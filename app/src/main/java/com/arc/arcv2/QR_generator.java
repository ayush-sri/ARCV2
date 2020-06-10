package com.arc.arcv2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.arc.arcv2.Utils.Helper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QR_generator extends AppCompatActivity implements View.OnClickListener {
    private Button generate;
    private EditText code_qr;
    private ImageView qrView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_generator);
        initUi();
        try {
            getQr("subject code, date, time");
        } catch (WriterException e) {
            e.printStackTrace();
        }
        generate.setOnClickListener(this);
    }

    private void initUi() {
        Helper.changeStatusBar(getWindow());
        generate = findViewById(R.id.share_to_class);
        qrView = findViewById(R.id.qr_code);
    }

    @Override
    public void onClick(View view){
        int id = view.getId();
        switch (id)
        {
            case R.id.share_to_class:
                share();
                break;
        }
    }

    private void share() {
    }

    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
    private void getQr(String code)throws WriterException {
        BitMatrix bitMatrix = multiFormatWriter.encode(code, BarcodeFormat.QR_CODE,350,300);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
        qrView.setImageBitmap(bitmap);

    }
}
