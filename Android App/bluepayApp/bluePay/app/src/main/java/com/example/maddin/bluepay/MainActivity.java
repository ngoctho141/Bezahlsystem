package com.example.maddin.bluepay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class MainActivity extends AppCompatActivity {

    private static Bluetooth_Connection bluetoothConnection;
    private static Data data = new Data();

    private EditText value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button scan = findViewById(R.id.main_scan);
        value = findViewById(R.id.main_value_edit);

        value.setText(getData().getValue() + " €");

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, SearchActivity.class));
                    }
                };
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void initBluetoothConnection (String uuid, String mac) {
        bluetoothConnection = new Bluetooth_Connection(uuid, mac);
    }

    public static Data getData() {
        return data;
    }

    public static Bluetooth_Connection getBluetoothConnection () {
        return bluetoothConnection;
    }
}
