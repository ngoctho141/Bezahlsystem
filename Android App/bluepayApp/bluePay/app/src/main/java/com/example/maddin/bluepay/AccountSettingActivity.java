package com.example.maddin.bluepay;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AccountSettingActivity extends AppCompatActivity {
    String tmp;
    private EditText customerID;
    private EditText userName;
    private EditText password;
    private EditText email;
    private EditText telNumber;
    private EditText balance;
    private EditText birthday;
    private CheckBox visibility;
    private Button cancel;
    private static Data data;

    private Button change;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        customerID = findViewById(R.id.acc_id);
        userName = findViewById(R.id.acc_name);
        password = findViewById(R.id.acc_password);
        email = findViewById(R.id.acc_email);
        telNumber = findViewById(R.id.acc_tel);
        balance = findViewById(R.id.acc_balance);
        birthday = findViewById(R.id.acc_birthday);
        visibility = findViewById(R.id.visibility);
        change = findViewById(R.id.btn_change);
        cancel = findViewById(R.id.btn_cancel);

        data = MainActivity.getData();
        customerID.setText(data.getUserID());
        userName.setText(data.getUsername());
        password.setText(data.getPassword());
        email.setText(data.getEmail());
        telNumber.setText(data.getTelNumber());
        balance.setText(data.getValue() + "");
        birthday.setText(data.getBirthday());

        visibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(visibility.isChecked()) password.setTransformationMethod(null);
                else password.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(){
                    public void run(){
                        Network_Connection con = Network_Connection.getInstance();
                        tmp = con.postConnection("command=UpdateInfo&password="
                                + password.getText().toString()
                                + "&email=" + email.getText().toString()
                                + "&telefon=" + telNumber.getText().toString()
                                + "&geburtsdatum=" + birthday.getText().toString());

                    }
                };
                thread.start();
                try{
                    thread.join();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

                if(tmp.equals("") || tmp.substring(0, 2).equals("-1")){
                    Toast.makeText(AccountSettingActivity.this, "Update unsuccessfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AccountSettingActivity.this, "Update successfully", Toast.LENGTH_LONG).show();
                    data.setPassword(password.getText().toString());
                    data.setEmail(password.getText().toString());
                    data.setBirthday(password.getText().toString());
                    data.setTelNumber(password.getText().toString());
                    startActivity(new Intent(AccountSettingActivity.this, MainActivity.class));
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountSettingActivity.this, MainActivity.class));
            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = new DatePickerDialog(AccountSettingActivity.this, date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dpd.getDatePicker().setMaxDate(System.currentTimeMillis());

                dpd.show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        birthday.setText(sdf.format(myCalendar.getTime()));
    }
}
