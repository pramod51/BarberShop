package barber.user.mybarber;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class EmailAuthReg extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email,password,name,phone,address;
    private Button login;
    String uID;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_email);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#8984A6"));
        }
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Please wait...");

        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        password=findViewById(R.id.pass);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone_number);
        address=findViewById(R.id.address);


        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateUser())
                    registerEmail();
            }
        });
        /*findViewById(R.id.login_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EmailAuthReg.this,PhoneAuthentication.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });*/
        findViewById(R.id.login1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EmailAuthReg.this,EmailAuthLogIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }
    private void registerEmail(){
        pd.show();
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("tag", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            pd.hide();
                            uID=FirebaseAuth.getInstance().getUid();
                            HashMap<String, Object> map=new HashMap<>();
                            map.put("Name",name.getText().toString());
                            map.put("Phone",phone.getText().toString());
                            map.put("Address",address.getText().toString());
                            map.put("Email",email.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("UserDB").child("Users").child(uID).setValue(map);
                            startActivity(new Intent(EmailAuthReg.this,MainActivity.class));
                            finish();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.v("tag", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(EmailAuthReg.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                        pd.hide();
                    }

                });
    }
    private boolean validateUser(){
        if (name.getText().toString().isEmpty()){
            Toast.makeText(this,"Enter Name",Toast.LENGTH_LONG).show();
            return false;
        }
        if (phone.getText().toString().isEmpty()){
            Toast.makeText(this,"Enter Phone",Toast.LENGTH_LONG).show();
            return false;
        }
        if (address.getText().toString().isEmpty()){
            Toast.makeText(this,"Enter Address",Toast.LENGTH_LONG).show();
            return false;
        }
        if (email.getText().toString().isEmpty()){
            Toast.makeText(this,"Enter Email",Toast.LENGTH_LONG).show();
            return false;
        }
        if (password.getText().toString().isEmpty()){
            Toast.makeText(this,"Enter Password",Toast.LENGTH_LONG).show();
            return false;
        }
        if (password.getText().toString().length()<6){
            Toast.makeText(this,"Password length is short",Toast.LENGTH_LONG).show();
            return false;
        }

         return true;
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        finishAffinity();
    }
}