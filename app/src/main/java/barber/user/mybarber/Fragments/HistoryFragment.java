package barber.user.mybarber.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import barber.user.mybarber.HistoryAdopter.HistoryAdopter;
import barber.user.mybarber.HistoryAdopter.HistoryItems;
import barber.user.mybarber.R;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<HistoryItems> historyItems;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView emptyMessage;
    String uId= FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        progressBar=view.findViewById(R.id.progressbar);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh);
        emptyMessage=view.findViewById(R.id.message);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // get shops in shop fragment
        FirebaseDatabase.getInstance().getReference().child("UserDB").child("Users").child(uId).child("History")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        historyItems=new ArrayList<>();
                        for (DataSnapshot ds:snapshot.getChildren()){
                            historyItems.add(new HistoryItems(ds.child("Address").getValue(String.class),ds.child("date").getValue(String.class),
                                    ds.child("time").getValue(String.class),ds.child("Name").getValue(String.class),ds.child("PhoneNo").getValue(String.class),
                                    ds.child("status").getValue(String.class)));
                        }
                        mAdapter=new HistoryAdopter(historyItems,getContext());
                        recyclerView.setAdapter(mAdapter);
                        progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



        return view;
    }



}