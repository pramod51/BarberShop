package barber.user.mybarber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import static barber.user.mybarber.UserRegestration.SHARED_PREFS;
import static barber.user.mybarber.UserRegestration.USER_ID;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        final String userId = sharedPreferences.getString(USER_ID, "");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent;
//                if (userId.isEmpty()) {
//                    mainIntent = new Intent(SplashScreen.this, UserRegestration.class);
//                } else {
//                    mainIntent = new Intent(SplashScreen.this, MainActivity.class);
//                }
                mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                SplashScreen.this.startActivity(mainIntent);

                SplashScreen.this.finish();
            }
        }, 2000);
    }
}