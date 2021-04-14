package barber.user.mybarber.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import barber.user.mybarber.Cart.CartItems;
import barber.user.mybarber.R;
import barber.user.mybarber.ShoppingAdopter.ShoppingAdopter;
import barber.user.mybarber.ShoppingAdopter.ShoppingItems;


public class ShoppingFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<ShoppingItems> shoppingItems;
    private RecyclerView.Adapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_shopping, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        FirebaseDatabase.getInstance().getReference().child("Shopping").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shoppingItems=new ArrayList<>();
                for (DataSnapshot ds:snapshot.child("Spray").getChildren())
                    shoppingItems.add(new ShoppingItems(ds.child("Image").getValue(String.class),ds.child("Title").getValue(String.class),ds.child("Price").getValue(String.class)));
                for (DataSnapshot ds:snapshot.child("HairCare").getChildren())
                    shoppingItems.add(new ShoppingItems(ds.child("Image").getValue(String.class),ds.child("Title").getValue(String.class),ds.child("Price").getValue(String.class)));
                for (DataSnapshot ds:snapshot.child("BodyCare").getChildren())
                    shoppingItems.add(new ShoppingItems(ds.child("Image").getValue(String.class),ds.child("Title").getValue(String.class),ds.child("Price").getValue(String.class)));

                mAdapter=new ShoppingAdopter(shoppingItems,getContext());
                recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        view.findViewById(R.id.first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                loadData("Spray");
            }
        });

        view.findViewById(R.id.second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData("HairCare");
            }
        });

        view.findViewById(R.id.third).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData("Spray");
            }
        });



        return view;
    }
    private void loadData(String category){
        FirebaseDatabase.getInstance().getReference().child("Shopping").child(category).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shoppingItems=new ArrayList<>();
                for (DataSnapshot ds:snapshot.getChildren())
                    shoppingItems.add(new ShoppingItems(ds.child("Image").getValue(String.class),ds.child("Title").getValue(String.class),ds.child("Price").getValue(String.class)));
                mAdapter=new ShoppingAdopter(shoppingItems,getContext());
                recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}