package barber.user.mybarber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

public class UserRegestration extends AppCompatActivity {


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USER_ID = "userId";
    public static final String PHONE_NO = "phone_no";
    public static final String USER_NAME = "userName";
    private Button conti;
    private EditText userName,phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_regestration);
        conti=findViewById(R.id.continue_button);
        userName=findViewById(R.id.user_name);
        final CountryCodePicker ccp = (CountryCodePicker) findViewById(R.id.ccp);
        phoneNumber=findViewById(R.id.mobile_no);
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                //Toast.makeText(MobileAthuntication.this, "Updated " + selectedCountry.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneNumber.getText().toString().isEmpty()||userName.getText().toString().isEmpty()){
                    Toast.makeText(UserRegestration.this,"Plese fill all places",Toast.LENGTH_LONG).show();
                }
                else {
                    final ProgressDialog progressdialog = new ProgressDialog(UserRegestration.this);
                    progressdialog.setMessage("please wait....");
                    progressdialog.setCanceledOnTouchOutside(false);
                    progressdialog.show();
                    //user registration

                    RequestQueue requestQueue = Volley.newRequestQueue(UserRegestration.this);

                    JSONObject postData = new JSONObject();
                    try {
                        postData.put("name", userName.getText().toString());
                        postData.put("phone", ccp.getSelectedCountryCodeWithPlus()+phoneNumber.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://mybarber.herokuapp.com/customer/api/new", postData, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d("test", response.getString("msg"));
                                String user_id = response.getJSONObject("data").getString("_id");
                                String user_name = response.getJSONObject("data").getString("name");
                                String user_phone = response.getJSONObject("data").getString("phone");

                                // code here
                                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(USER_ID, user_id);
                                editor.putString(USER_NAME, user_name);
                                editor.putString(PHONE_NO, user_phone);
                                editor.apply();
                                Intent intent = new Intent(UserRegestration.this, MainActivity.class);
                                //intent.putExtra(PHONE,phoneNo.getText().toString());
                                //intent.putExtra(CUNTERYCODE,ccp.getSelectedCountryCodeWithPlus());
                                progressdialog.dismiss();
                                startActivity(intent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Log.d("err", error.toString());
                            progressdialog.dismiss();
                            Toast.makeText(UserRegestration.this, "Something went wrong! Try Again", Toast.LENGTH_LONG).show();
                        }
                    });

                    requestQueue.add(jsonObjectRequest);

                }
            }
        });
        
        
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        finishAffinity();
    }

}