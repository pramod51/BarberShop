package barber.user.mybarber.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;

import barber.user.mybarber.BarberAdopter.BarberModel;
import barber.user.mybarber.BookingAdopter.BookingAdopter;
import barber.user.mybarber.BookingAdopter.BookingModel;
import barber.user.mybarber.R;


public class BookingFragment extends Fragment  {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<BookingModel> bookingModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_booking, container, false);
        ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Log.v("tag","booking frag");
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseDatabase.getInstance().getReference().child("AdminDB").child("Shops").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingModels=new ArrayList<>();
                for (DataSnapshot ds:snapshot.getChildren()){
                    bookingModels.add(new BookingModel(ds.child("Address").getValue(String.class),ds.getKey(),ds.child("Name").getValue(String.class),-1));
                }
                adapter=new BookingAdopter(bookingModels,getContext());
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