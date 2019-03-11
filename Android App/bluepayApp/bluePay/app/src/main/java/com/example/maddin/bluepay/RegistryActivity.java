package com.example.maddin.bluepay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegistryActivity extends AppCompatActivity {

    private boolean flag = false;

    private TextView error;

    private Button registry;

    private final static String TAG = "RegistryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        final EditText username = findViewById(R.id.reg_et_username);
        final EditText name = findViewById(R.id.reg_et_name);
        final EditText prename = findViewById(R.id.reg_et_prename);
        final EditText street = findViewById(R.id.reg_et_street);
        final EditText number = findViewById(R.id.reg_et_number);
        final EditText postal = findViewById(R.id.reg_et_postal);
        final EditText city = findViewById(R.id.reg_et_city);
        final EditText country = findViewById(R.id.reg_et_country);
        final EditText password = findViewById(R.id.reg_et_password);
        final EditText sec_password = findViewById(R.id.reg_et_sec_password);

        final List<EditText>list = new ArrayList<>(Arrays.asList(username, name, prename, street, number, postal, city, country, password, sec_password));

        error = findViewById(R.id.reg_tv_error);

        registry = findViewById(R.id.reg_button_registry);

        registry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(EditText e : list) {
                    if (isEmpty(e)) {
                        e.setTextColor(getResources().getColor(R.color.RED));
                        flag = true;
                    }
                }

                if (flag) {
                    flag = !flag;
                    error.setVisibility(TextView.VISIBLE);
                }

                else if (password.getText().toString().equals(sec_password.getText().toString()) && !isEmpty(password)) {
                    LoginActivity.registry(getText(username), getText(prename) + " " + getText(name), getText(street)
                            + " " + getText(number), getText(postal) + " " + getText(city), getText(country));
                    startActivity(new Intent(RegistryActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


    public boolean isEmpty(EditText text) {
        return text.getText().toString().equals("");
    }

    public String getText(EditText s) {
        return s.getText().toString();
    }
}
