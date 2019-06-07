package com.example.maddin.bluepay;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    String tmp;
    private static Bluetooth_Connection bluetoothConnection;
    private static Data data = new Data();

    private EditText value;

    public static final String PAYPAL_CLIENT_ID = "AVRFMgMSOxVtSTCYkh7LdVHmdpYTNVHdtphfTUsdAd1tXIGNozE6uZ_2WSfjpnsOlhLJvFh45Ktcq7jf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button logOut = findViewById(R.id.main_logout);
        final Button scan = findViewById(R.id.main_scan);
        final Button charge = findViewById(R.id.main_money);
        final Button account = findViewById(R.id.main_account);
        value = findViewById(R.id.main_value_edit);




        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(){
                    public void run(){
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        finish();
                        //  Toast.makeText(MainActivity.this, "Erfolgreich abmelden", Toast.LENGTH_LONG).show();
                    }
                };
                thread.start();
                try{
                    thread.join();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        });

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

        charge.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(){
                    public void run(){
                        startActivity(new Intent(MainActivity.this, AufladenActivity.class));
                    }
                };
                thread.start();
                try{
                    thread.join();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        account.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        Thread thread2 = new Thread() {
                            @Override
                            public void run() {
                                data = MainActivity.getData();
                                Network_Connection con = Network_Connection.getInstance();
                                tmp = con.postConnection("command=Login&name=" + data.getUsername() + "&password=" + data.getPassword());
                            }
                        };

                        thread2.start();
                        try {
                            thread2.join();
                        } catch (InterruptedException e) {
                            Log.e("MainActivity", "Error in Thread.join()", e);
                        }
                        try {
                            String[]dataArr = tmp.split(";");
//                            tmp = tmp.substring(tmp.indexOf(";")+1);
                            data.setUserID(dataArr[0]);
                            data.setUsername(dataArr[1]);
                            data.setPassword(dataArr[2]);
                            data.setTelNumber(dataArr[3]);
                            data.setBirthday(dataArr[4]);
                            data.setValue(Double.parseDouble(dataArr[5]));
                            data.setEmail(dataArr[6]);
                            startActivity(new Intent(MainActivity.this, AccountSettingActivity.class));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
                try{
                    thread.join();
                } catch (InterruptedException e){
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
