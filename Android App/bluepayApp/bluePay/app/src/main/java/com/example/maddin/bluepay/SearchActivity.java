package com.example.maddin.bluepay;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;

    private ListView listView;
    private List<String> list = new ArrayList<String>();
    private TextView txt;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(bluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, bluetoothAdapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF: {
                        txt.setVisibility(TextView.VISIBLE);
                        break;
                    }

                    case BluetoothAdapter.STATE_TURNING_OFF: {
                        break;
                    }

                    case BluetoothAdapter.STATE_ON: {
                        txt.setVisibility(TextView.INVISIBLE);
                        break;
                    }

                    case BluetoothAdapter.STATE_TURNING_ON: {
                        break;
                    }
                }
            }
        }
    };
    /**
     @Override
     protected void onDestroy() {
     super.onDestroy();
     unregisterReceiver(mReceiver);
     }
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        onCreateDialog();

        Button scan = findViewById(R.id.search_button_scan);
        listView = findViewById(R.id.search_listView);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        txt = findViewById(R.id.search_tv_note);

        enableBT();
        if (bluetoothAdapter.isEnabled())txt.setVisibility(TextView.INVISIBLE);



        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableBT();
            }
        });
    }

    public void onCreateDialog() {
        AlertDialog.Builder popup;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popup = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }

        else {
            popup = new AlertDialog.Builder(this);
        }

        popup.setTitle("Suche auswählen")
                .setMessage("Wie wollen Sie suchen?")
                .setPositiveButton("NFC (in Arbeit)", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        scanNFC();
                    }
                })
                .setNegativeButton("QR-Code", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scanQRCode();
                    }
                });

        popup.show();
    }

    public void scanNFC() {
        Toast.makeText(SearchActivity.this, "NFC started", Toast.LENGTH_LONG).show();
    }

    public void scanQRCode() {

//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                Toast.makeText(SearchActivity.this, "QR-Scanner gestartet", Toast.LENGTH_LONG).show();
//                startActivity(new Intent (SearchActivity.this, QRCode_Scanner_Activity.class));
//            }
//        };
//
//
//        thread.start();
//
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        runOnUiThread(() -> {
                    Toast.makeText(SearchActivity.this, "QR-Scanner gestartet", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SearchActivity.this, QRCode_Scanner_Activity.class));
                }
        );
        finish();
    }

    public void enableBT() {

        if (bluetoothAdapter == null) {
            new AlertDialog.Builder(this)
                    .setTitle("Nicht kompatibel")
                    .setMessage("Dein Telefon unterstützt kein Bluetooth")
                    .setPositiveButton("Verlassen", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .show();
        }

        if (!bluetoothAdapter.isEnabled()) {
            startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));
            registerReceiver(mReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        }
    }
}
