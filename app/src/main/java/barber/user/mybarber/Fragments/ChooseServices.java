package barber.user.mybarber.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import barber.user.mybarber.Cart.CartAdopter;
import barber.user.mybarber.Cart.CartItems;
import barber.user.mybarber.R;
import de.hdodenhof.circleimageview.CircleImageView;

import static barber.user.mybarber.Fragments.BarberFragment.SHARED_PREFS;


public class ChooseServices extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<CartItems> cartItems;
    private RecyclerView.Adapter mAdapter;
    String uId= FirebaseAuth.getInstance().getCurrentUser().getUid();
    Spinner spinner;
    TextView name,rating,phone;
    CircleImageView imageView;
    String[] items={"Choose Services","Modern Haircut + Styling - RM25 - 30mins",
    "Hair Wash + Cut + Blow - RM40 - 30mins",
    "Wash + Blow + Styling - RM20 - 30mins",
    "Wash + Blow - RM18 - 30 mins",
    "Facial + Black Mask - RM25 - 30mins",
    "Haircut + Wash + Styling + Scalp & Shoulder Massage - RM60 - 1 hour",
    "Modern hair cut for gents - RM20 - 30mins",
    "Modern hair cut for ladies - RM20 - 30mins",
    "Men’s Basic Haircut - RM13 - 30mins",
    "Student’s Basic Haircut - RM9 - 30mins",
    "Hot Towel Shave - RM15 - 30mins",
    "Normal Shave - RM7 - 30mins",
    "Beard Trim - RM5 - 30mins",
    "Face Waxing - RM10 - 30mins",
    "Head & Shoulder Massage - RM10 - 30mins",
    "Facial - RM15 - 30mins",
    "Black Mask - RM15 - 30mins",
    "Hair coloring - RM50 - 1 hour 30 mins",
    "Hair Highlights - RM60 - 1 hour 30 mins",
    "Hair bleaching per scoop - RM15 - 1 hour 30 mins",
    "Hair perming - RM80 - 1 hour 30 mins",
    "Cover Grey Hair - RM30 - 1 hour",
    "Hair rebonding (Short) - RM 90 - 2 mins",
    "Hair rebonding (Medium) - RM 150 - 2 hours",
    "Hair rebonding (Long) - RM 180 - 2 hour 30 mins",
    "Hair tattoo - RM50 - 1 hour",
    "Hair setting - RM 35 - 30 mins",
    "Hair cornrows -RM80 - 1 hour",
    "Hair blow dry setting - RM45 - 1 hour"};




    String[] prices={"RM 25","RM 40","RM 20","RM 18","RM 25","RM 60","RM 20","RM 20","RM 13",
            "RM 9 ","RM 15","RM 7 ","RM 5 ","RM 10","RM 10","RM 15","RM 15","RM 50","RM 60","RM 15",
            "RM 80","RM 30","RM 90","RM 150","RM 180","RM 50","RM  35","RM 80","RM 45",};
    int p=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_choose_services, container, false);

        name=view.findViewById(R.id.name);
        imageView=view.findViewById(R.id.image);
        phone=view.findViewById(R.id.phone);
        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        spinner=view.findViewById(R.id.autoComplete);
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        SharedPreferences sharedPreferences=getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        FirebaseDatabase.getInstance().getReference().child("AdminDB").child("Shops").child(sharedPreferences.getString("key","xyz"))
                .child("Employee").child(sharedPreferences.getString("bkey","1")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("Name").getValue(String.class));
                phone.setText(snapshot.child("PhoneNo").getValue(String.class));
                Picasso.get().load(snapshot.child("Pic").getValue(String.class)).into(imageView);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FirebaseDatabase.getInstance().getReference().child("UserDB").child("Users").child(uId)
                .child("Cart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartItems=new ArrayList<>();
                int total=0;
                for (DataSnapshot ds:snapshot.getChildren()) {
                    cartItems.add(new CartItems(ds.child("Image").getValue(String.class),ds.child("Title").getValue(String.class),
                            ds.child("Price").getValue(String.class),ds.child("Quantity").getValue(String.class),ds.getKey()));
                    Log.v("tag",ds.child("Image").getValue(String.class));
                    String price=ds.child("Price").getValue(String.class);
                    total+=Integer.parseInt(price.substring(3,price.length()));
                }

                Editor editor = sharedPreferences.edit();
                editor.putInt("totalPrice",total);
                editor.apply();

                mAdapter=new CartAdopter(cartItems,getContext());
                recyclerView.setAdapter(mAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v("tag",error.toString());
            }
        });
        Log.v("tag","lekjlh");






        return  view;
    }
}