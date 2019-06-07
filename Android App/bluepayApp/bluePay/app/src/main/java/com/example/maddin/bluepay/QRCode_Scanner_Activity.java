package com.example.maddin.bluepay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class QRCode_Scanner_Activity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonScan;
    private TextView uuid, mac;

    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode__scanner_);

        buttonScan = findViewById(R.id.qr_scan);
        uuid = findViewById(R.id.qr_uuid);
        mac = findViewById(R.id.qr_mac);

        qrScan = new IntentIntegrator(this).setBeepEnabled(true);

        buttonScan.setOnClickListener(this);

        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null) {

            if (result.getContents() == null) {
                Toast.makeText(this, "Result not found", Toast.LENGTH_LONG).show();
            }

            else {
                try {
                    String info[] = new String [2];
                    JSONObject obj = new JSONObject(result.getContents());
                    uuid.setText(obj.getString("uuid"));
                    mac.setText(obj.getString("mac"));

                    info[0] = obj.getString("uuid");
                    info[1] = obj.getString("mac");
                    MainActivity.initBluetoothConnection(info[0], info[1]);
                    startActivity(new Intent(QRCode_Scanner_Activity.this, ProductlistActivity.class));
                    finish();


                } catch (JSONException e) {
                    Log.e("QRCode_Scanner_Activity", "Scanner failed", e);
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        }

        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {
        qrScan.initiateScan();

    }
}