package com.example.maddin.bluepay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private String tmp;

    private Button login;
    private Button registry;

    private EditText name;
    private EditText password;

    private TextView warning;

    private CheckBox visibility;
    private CheckBox save;

    private static Data data;

    final private static String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.button_login);
        registry = findViewById(R.id.button_registry);
        name = findViewById(R.id.input_name);
        password = findViewById(R.id.input_password);
        warning = findViewById(R.id.textView_warning);
        visibility = findViewById(R.id.checkBox_visibility);
        save = findViewById(R.id.checkBox_save);
        data = MainActivity.getData();
        warning.setVisibility(TextView.INVISIBLE);

        if (data.statsSaved()) {
            name.setText(data.getUsername());
            password.setText(data.getPassword());
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEmpty(name) || isEmpty(password)) {
                    warning.setVisibility(TextView.VISIBLE);
                }

                else {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            Network_Connection con = Network_Connection.getInstance();
                            tmp = con.postConnection("command=Login&name=" + name.getText().toString() + "&password=" + password.getText().toString());
                        }
                    };

                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        Log.e(TAG, "Error in Thread.join()", e);
                    }
                    if (!tmp.equals("") && tmp != null && !tmp.substring(0, 2).equals("-1")) {
                            tmp = tmp.substring(tmp.indexOf(";")+1);
                            data.setValue(Double.parseDouble(tmp));
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            warning.setVisibility(TextView.INVISIBLE);
                            finish();
                    }
                    else {
                        warning.setText("Benutzereingaben überprüfen");
                    }
                }
            }
        });

        registry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistryActivity.class));
            }
        });

        visibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(visibility.isChecked()) password.setTransformationMethod(null);
                else password.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

    }
    public boolean isEmpty(EditText text) {
        return text.getText().toString().equals("");
    }

    public static void registry (String ln, String n, String s, String t, String c) {
        data.registry(ln, n, s, t, c);
    }
}
