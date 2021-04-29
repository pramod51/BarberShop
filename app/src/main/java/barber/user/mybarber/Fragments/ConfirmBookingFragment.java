package barber.user.mybarber.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import barber.user.mybarber.R;

import static barber.user.mybarber.Fragments.BarberFragment.SHARED_PREFS;


public class ConfirmBookingFragment extends Fragment {
    Button previousButton;
    Button confirmButton;
    private TextView name,dateTime,address,phone,totalAmt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_booking, container, false);
        ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        totalAmt=view.findViewById(R.id.total);
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        totalAmt.setText("RM "+sharedPreferences.getInt("totalPrice",0));
        name=view.findViewById(R.id.name);
        dateTime=view.findViewById(R.id.date_time);
        address=view.findViewById(R.id.address);
        phone=view.findViewById(R.id.phone);
        FirebaseDatabase.getInstance().getReference().child("AdminDB").child("Shops").child(sharedPreferences.getString("key","abc"))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DataSnapshot employee=snapshot.child("Employee").child(sharedPreferences.getString("bkey","1"));
                        name.setText(employee.child("Name").getValue(String.class));
                        dateTime.setText(sharedPreferences.getString("date",""));
                        address.setText(snapshot.child("Address").getValue(String.class));
                        phone.setText(employee.child("PhoneNo").getValue(String.class));
                        Calendar c=Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("EEE,d,MMM");
                        String strDate = sdf.format(c.getTime());
                        Editor editor=sharedPreferences.edit();
                        editor.putString("Name",name.getText().toString());
                        editor.putString("Address",address.getText().toString());
                        editor.putString("PhoneNo",phone.getText().toString());
                        editor.apply();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



        return view;
    }




}