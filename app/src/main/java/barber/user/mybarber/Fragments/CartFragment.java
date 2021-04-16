package barber.user.mybarber.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import barber.user.mybarber.Cart.CartAdopter;
import barber.user.mybarber.Cart.CartItems;
import barber.user.mybarber.R;
import barber.user.mybarber.ShopAdopter.ShopItems;
import barber.user.mybarber.ShoppingAdopter.ShoppingAdopter;


public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<CartItems> cartItems;
    private RecyclerView.Adapter mAdapter;
    String uId="xyz";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseDatabase.getInstance().getReference().child("UserDb").child("Users").child(uId)
                .child("Cart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartItems=new ArrayList<>();
                for (DataSnapshot ds:snapshot.getChildren())
                cartItems.add(new CartItems(ds.child("Image").getValue(String.class),ds.child("Title").getValue(String.class),
                        ds.child("Price").getValue(String.class),ds.child("Quantity").getValue(String.class)));

                mAdapter=new CartAdopter(cartItems,getContext());
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.v("tag", "Kya hal h");

        return view;
    }



}