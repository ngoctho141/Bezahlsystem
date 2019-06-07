package com.example.maddin.bluepay;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductlistActivity extends AppCompatActivity {

    private Bluetooth_Connection bluetoothConnection = MainActivity.getBluetoothConnection();
    private ListView listView;
    private String tmp;
    private String aid;
    private List<String> list = new ArrayList<>();
    private TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);
        listView = findViewById(R.id.product_lv_list);
        text = findViewById(R.id.product_tv_choose);



        Thread thread = new Thread () {
            @Override
            public void run() {
                Network_Connection con = Network_Connection.getInstance();
                tmp = con.postConnection("command=List&aid=" + bluetoothConnection.getMacAdress());
                String temp;
                String temporary = "";

                // from now just WTF
                do {
                    temp = tmp.substring(0, tmp.indexOf("|"));
                    temporary += temp.substring(0, 1).toUpperCase() + temp.substring(1, temp.indexOf(";")) + ": ";


                    if (temp.contains(".")) {
                        temporary += temp.substring(temp.indexOf(";") + 1, temp.indexOf("."));
                        temporary += ";";
                        temp = temp.substring(temp.indexOf(";") + 1);

                        if (temp.substring(temp.indexOf(".") + 1).length() > 1) {
                            temporary += temp.substring(temp.indexOf(".") + 1) + " €";
                        } else {
                            temporary += temp.substring(temp.indexOf(".") + 1) + "0 €";
                        }
                    } else {
                        temporary += temp.substring(temp.indexOf(";") + 1) + ",- €";
                    }

                    list.add(temporary);
                    temporary = "";

                    tmp = tmp.substring(tmp.indexOf("|") + 1);

                }  while(tmp.contains("|"));

                temp = tmp;
                temporary += temp.substring(0, 1).toUpperCase() + temp.substring(1, temp.indexOf(";")) + ": ";


                if (temp.contains(".")) {
                    temporary += temp.substring(temp.indexOf(";") + 1, temp.indexOf("."));
                    temporary += ",";
                    temp = temp.substring(temp.indexOf(";") + 1);

                    if (temp.substring(temp.indexOf(".") + 1).length() > 1) {
                        temporary += temp.substring(temp.indexOf(".") + 1) + " €";
                    } else {
                        temporary += temp.substring(temp.indexOf(".") + 1) + "0 €";
                    }
                } else {
                    temporary += temp.substring(temp.indexOf(";") + 1) + ",- €";
                }

                list.add(temporary);
                tmp = tmp.substring(tmp.indexOf("|") + 1);
            }
        };

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final ArrayAdapter<String> productList = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list);

        listView.setAdapter(productList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = list.get(position);
                buySomething(item);
            }
        });
    }

    public void buySomething(final String buy) {
        Thread threadSec = new Thread() {
            @Override
            public void run() {
                bluetoothConnection.connect();

                bluetoothConnection.send("ACCEPT" + buy.split(":")[0]);

                String msg = "";


                while (true) {
                    msg = bluetoothConnection.receive();
                    if (msg.isEmpty()) {
                        continue;
                    }
                    if (msg.contains("STOP")) {
                        break;
                    }
                }
                bluetoothConnection.disconnect();
                aid = msg;
            }
        };
        threadSec.start();


        try {
            threadSec.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (aid.split("S")[0].equals("OK")) {
            Intent intent = new Intent(ProductlistActivity.this, BuyActivity.class);
            Bundle b = new Bundle();
            b.putString("key", buy.split(":")[0]);
            intent.putExtras(b);
            startActivity(intent);
        }
    }
}
