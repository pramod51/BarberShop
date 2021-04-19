package barber.user.mybarber;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class OtpPageActivity extends AppCompatActivity {
    private TextView next, resend;
    private EditText n1, n2, n3, n4, n5, n6;
    final static String PHONE = "phone";
    private ProgressBar progressBar;
    String id;
    String phoneN0 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_page);
        next = findViewById(R.id.next);
        resend = findViewById(R.id.resend);
        progressBar = findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);
        n1 = findViewById(R.id.et1);
        n2 = findViewById(R.id.et2);
        n3 = findViewById(R.id.et3);
        n4 = findViewById(R.id.et4);
        n5 = findViewById(R.id.et5);
        n6 = findViewById(R.id.et6);
        n1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    n1.clearFocus();
                    n2.setCursorVisible(true);
                    n2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        n2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    n2.clearFocus();
                    n3.setCursorVisible(true);
                    n3.requestFocus();
                }
                if (count == 0) {
                    n2.clearFocus();
                    n1.setCursorVisible(true);
                    n1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        n3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    n3.clearFocus();
                    n4.setCursorVisible(true);
                    n4.requestFocus();
                }
                if (count == 0) {
                    n3.clearFocus();
                    n2.setCursorVisible(true);
                    n2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        n4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    n4.clearFocus();
                    n5.setCursorVisible(true);
                    n5.requestFocus();
                }
                if (count == 0) {
                    n4.clearFocus();
                    n3.setCursorVisible(true);
                    n3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        n5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    n5.clearFocus();
                    n6.setCursorVisible(true);
                    n6.requestFocus();
                }
                if (count == 0) {
                    n5.clearFocus();
                    n4.setCursorVisible(true);
                    n4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        n6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    n6.clearFocus();
                    n5.setCursorVisible(true);
                    n5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String otp = n1.getText().toString() + n2.getText().toString()
                        + n3.getText().toString() + n4.getText().toString()
                        + n5.getText().toString() + n6.getText().toString();
                //Toast.makeText(OtpPage.this,otp,Toast.LENGTH_LONG).show();

                if (otp.isEmpty()) {
                    Toast.makeText(OtpPageActivity.this, "Please Enter Your OTP", Toast.LENGTH_LONG).show();
                } else if (otp.replace(" ", "").length() < 6) {
                    Toast.makeText(OtpPageActivity.this, "Please Enter 6 Digit OTP", Toast.LENGTH_LONG).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    startActivity(new Intent(OtpPageActivity.this, PaymentSuccessedActivity.class));
                }
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendVerificationCode(phoneN0);
            }
        });

    }

    private void SendVerificationCode(String phoneN0) {
        new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long l) {
                resend.setText("" + l / 1000);
                resend.setEnabled(false);
            }

            @Override
            public void onFinish() {
                resend.setText("Resend");
                resend.setEnabled(true);
            }
        }.start();
    }

}