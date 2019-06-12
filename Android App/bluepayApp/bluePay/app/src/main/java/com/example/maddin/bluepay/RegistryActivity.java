package com.example.maddin.bluepay;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class RegistryActivity extends AppCompatActivity {
    String tmp;
    private TextInputLayout name, email, password, c_password, telefon, geburtsdatum ;

    private  EditText  id, bl2;
    private Button btn_regist;
    private ProgressBar loading;
    //  private static String URL_REGIST ="http://192.168.178.32/testDb/registrierung.php";
    private static Data data;
    private TextView warning;
    private TextView warning2;

    final Calendar myCalendar = Calendar.getInstance();

    private static final Pattern PASSWORT_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registry);

        bl2 = findViewById(R.id.acc_balance2_R);
        id = findViewById(R.id.acc_id2_R);
        telefon = findViewById(R.id.telefon_R);
        geburtsdatum = findViewById(R.id.geburtsdatum_R);
        loading = findViewById(R.id.loading_R);
        name = findViewById(R.id.name_R);
        email = findViewById(R.id.email_R);
        password = findViewById(R.id.password_R);
        c_password = findViewById(R.id.password_c_R);
        warning = findViewById(R.id.textView_warning_Reg);


        warning.setVisibility(TextView.INVISIBLE);
/*
        data = MainActivity.getData();
        //id.setText(data.getUserID());
        name(data.getUsername());
        password.setHint(data.getPassword());
        email.setHint(data.getEmail());
        telefon.setHint(data.getTelNumber());
        bl2.setHint(data.getValue() + "");
        geburtsdatum.setHint(data.getBirthday());
*/

        String emailInput = email.getEditText().toString().trim();
        String passwortInput = password.getEditText().toString().trim();


        btn_regist = findViewById(R.id.btn_regist_R);

               btn_regist.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Thread thread = new Thread() {
                           public void run() {
                               Thread thread;
                               if (!validateEmail() | !validateUsername() | !validatePassword()) {
                                   thread = new Thread(){
                                       @Override
                                       public void run() {
                                           runOnUiThread(new Runnable() {
                                               @Override
                                               public void run() {
                                                   Toast.makeText(RegistryActivity.this,"Sie haben falsch eingegeben", Toast.LENGTH_LONG).show();
                                               }
                                           });
                                       }
                                   };
                                   thread.start();
                               }else {
                                   Network_Connection con = Network_Connection.getInstance();
                                   tmp = con.postConnection("command=Registry&password=" + password.getEditText().getText().toString().trim()
                                           + "&email=" + email.getEditText().getText().toString().trim()
                                           + "&telefon=" + telefon.getEditText().getText().toString().trim()
                                           + "&geburtsdatum=" + geburtsdatum.getEditText().getText().toString().trim()
                                           + "&name=" + name.getEditText().getText().toString().trim()
                                   );
                                   if (tmp.equals("-1")) {
                                       thread = new Thread(){
                                           @Override
                                           public void run() {
                                               runOnUiThread(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       Toast.makeText(RegistryActivity.this, "Benutzername ist schon vergeben.", Toast.LENGTH_LONG).show();
                                                   }
                                               });
                                           }
                                       };
                                       thread.start();
                                   } else {
                                       thread = new Thread(){
                                           @Override
                                           public void run() {
                                               runOnUiThread(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       Toast.makeText(RegistryActivity.this, "Registrierung erfolgreich", Toast.LENGTH_LONG).show();
                                                   }
                                               });
                                           }
                                       };
                                       thread.start();
                                       startActivity(new Intent(RegistryActivity.this, LoginActivity.class));
                                   }
                               }

                           }
                       };
                       thread.start();
                       try {
                           thread.join();
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }

                       /*


                       } else {
                           data.setPassword(password.getEditText().getText().toString().trim());
                           data.setEmail(email.getEditText().getText().toString().trim());
                           data.setTelNumber(telefon.getEditText().getText().toString().trim());
                           data.setBirthday(geburtsdatum.getEditText().getText().toString().trim());
                           data.setName(name.getEditText().getText().toString().trim());
                           Toast.makeText(RegistryActivity.this, "Registrierung erfolgreich", Toast.LENGTH_LONG).show();
                           startActivity(new Intent(RegistryActivity.this, LoginActivity.class));
                       }
                       */
                   }


               });

           }

/*
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

        geburtsdatum.setOnClickListener(new View.OnClickListener() {
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

        geburtsdatum.setText(sdf.format(myCalendar.getTime()));
    }*/
private boolean validateEmail() {
    String emailInput = email.getEditText().getText().toString().trim();
    Thread thread;
    if (emailInput.isEmpty()) {
        thread = new Thread(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        email.setError("Feld darf nicht leer sein");
                    }
                });
            }
        };
        thread.start();
        return false;
    } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
        thread = new Thread(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        email.setError("Bitte geben Sie eine gültige E-Mail-Adresse ein");
                    }
                });
            }
        };
        thread.start();
        return false;
    } else {
        thread = new Thread(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        email.setError(null);
                    }
                });
            }
        };
        thread.start();
        return true;
    }
}
    private boolean validateUsername() {
        String usernameInput = name.getEditText().getText().toString().trim();
        Thread thread;
        if (usernameInput.isEmpty()) {
            thread = new Thread(){
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            name.setError("Feld darf nicht leer sein ");
                        }
                    });
                }
            };
            thread.start();
            return false;
        } else if (usernameInput.length() > 15) {
            thread = new Thread(){
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            name.setError("Benutzername zu lang");
                        }
                    });
                }
            };
            thread.start();
            return false;
        } else {
            thread = new Thread(){
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            name.setError(null);
                        }
                    });
                }
            };
            thread.start();
            return true;
        }
    }


    private boolean validatePassword() {
        String passwordInput = password.getEditText().getText().toString().trim();
        Thread thread;
        if (passwordInput.isEmpty()) {
            thread = new Thread(){
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            password.setError("Feld darf nicht leer sein");
                        }
                    });
                }
            };
            thread.start();
            return false;
        } else if (!PASSWORT_PATTERN.matcher(passwordInput).matches()) {
            thread = new Thread(){
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            password.setError("das Passwort ist zu schwach");
                        }
                    });
                }
            };
            thread.start();
            return false;
        } else {
            thread = new Thread(){
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            password.setError(null);
                        }
                    });
                }
            };
            thread.start();
            return true;
        }
    }
}
