package com.example.maddin.bluepay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetails extends AppCompatActivity {

    TextView txtId, txtAmount, txtStatus;
    Button btnOk;
    private final static String TAG = "PaymentDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        txtId = (TextView) findViewById(R.id.txtId);
        txtAmount = (TextView) findViewById(R.id.txtAmount);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        btnOk = (Button) findViewById(R.id.btnOk);

        Intent intent = getIntent();

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"), intent.getStringExtra("paymentAmount"));
            updateAccount(intent.getStringExtra("paymentAmount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentDetails.this, MainActivity.class));
                //finish();
            }
        });

    }

    private void showDetails(JSONObject response, String paymentAmount) {
        try{
            txtId.setText(response.getString("id"));
            txtStatus.setText(response.getString("state"));
            txtAmount.setText("$" + paymentAmount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateAccount(String paymentAmount){
        Thread thread = new Thread(){
            @Override
            public void run() {
                Network_Connection con = Network_Connection.getInstance();
                String tmp = con.postConnection("command=Charge&amount=" + paymentAmount);
                MainActivity.getData().setValue(Double.parseDouble(tmp.split("[|]")[1]));
            }
        };

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e(TAG, "Error in Thread.join()", e);
        }
    }
}
