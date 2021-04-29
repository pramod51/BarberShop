package barber.user.mybarber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static barber.user.mybarber.UserRegestration.SHARED_PREFS;
import static barber.user.mybarber.UserRegestration.USER_ID;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent;
                if (user==null) {
                   mainIntent = new Intent(SplashScreen.this, EmailAuthLogIn.class);
               } else {
                   mainIntent = new Intent(SplashScreen.this, MainActivity.class);
               }
                SplashScreen.this.startActivity(mainIntent);

                SplashScreen.this.finish();
            }
        }, 2000);
    }
}