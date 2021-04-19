package barber.user.mybarber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class PaymentSuccessedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_successed);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(PaymentSuccessedActivity.this, MainActivity.class);
                PaymentSuccessedActivity.this.startActivity(mainIntent);
                PaymentSuccessedActivity.this.finish();
            }
        }, 1700);

    }
}