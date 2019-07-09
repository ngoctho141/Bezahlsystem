package com.example.maddin.bluepay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BuyActivity extends AppCompatActivity {

    private Network_Connection network = Network_Connection.getInstance();
    private Bluetooth_Connection bluetooth = MainActivity.getBluetoothConnection();
    private String string;
    private TextView text;
    private String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        Bundle b = getIntent().getExtras();
        text = findViewById(R.id.buy_tv);

        buyProcess(b.getString("key"));
    }

    public boolean buyProcess (final String wantTo) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                string = network.postConnection("command=Buy&aid=" + bluetooth.getMacAdress() +"&produkt=" + wantTo.substring(0, 1).toLowerCase() + wantTo.substring(1));

                String[] tmp = string.split("\\|");

                bluetooth.connect();
                String[] halfFirst = tmp[0].split(";");
                if (halfFirst[1].equals("1")) {
                    bluetooth.send("BUY:" + wantTo + ":" + tmp[1]);

                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    string = bluetooth.receive();

                    if (string.split("S")[0].equals("OK")) {
                        temp = "Vielen Dank für Ihren Einkauf";
                        MainActivity.getData().setValue(Double.parseDouble(halfFirst[2]));
                        startActivity(new Intent(BuyActivity.this, MainActivity.class));
                    }
                    else {
                        temp = "Verbindungsabbruch\nBitte wenden Sie sich an den Support";
                        MainActivity.getData().setValue(Double.parseDouble(halfFirst[2]));
                        startActivity(new Intent(BuyActivity.this, MainActivity.class));
                    }
                }
                bluetooth.disconnect();
            }
        };

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        text.setText(temp);
        return true;
    }
}
