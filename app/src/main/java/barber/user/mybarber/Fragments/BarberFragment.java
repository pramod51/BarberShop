package barber.user.mybarber.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;

import barber.user.mybarber.BarberAdopter.BarberAdopter;
import barber.user.mybarber.BarberAdopter.BarberModel;
import barber.user.mybarber.BookingAdopter.BookingAdopter;
import barber.user.mybarber.BookingAdopter.BookingModel;
import barber.user.mybarber.R;


public class BarberFragment extends Fragment  {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<BarberModel> barberModels;
    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS="sharedpffs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_barber, container, false);
        ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        sharedPreferences=getActivity().getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE );

        Log.v("tag","barber frag");


        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        FirebaseDatabase.getInstance().getReference().child("AdminDB").child("Shops").child(sharedPreferences.getString("key","xyz"))
                .child("Employee").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                barberModels=new ArrayList<>();
                for (DataSnapshot ds:snapshot.getChildren()){
                    barberModels.add(new BarberModel(ds.child("Name").getValue(String.class),ds.child("Pic").getValue(String.class),ds.getKey(),4.5,-1));
                }
                adapter=new BarberAdopter(barberModels,getContext());
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return view;
    }




}