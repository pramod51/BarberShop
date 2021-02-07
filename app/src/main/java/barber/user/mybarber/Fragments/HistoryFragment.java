package barber.user.mybarber.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import barber.user.mybarber.HistoryAdopter.HistoryAdopter;
import barber.user.mybarber.HistoryAdopter.HistoryItems;
import barber.user.mybarber.R;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<HistoryItems> historyItems;
    private RecyclerView.Adapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        historyItems=new ArrayList<>();
        for (int i=1;i<6;i++)
        historyItems.add(new HistoryItems("Pramod","+919956118310","10/02/2021","","Pending","Hair Cut"));
        historyItems.add(new HistoryItems("Himanshu","+919956118310","10/02/2021","","Pending","Hair Cut"));
        mAdapter=new HistoryAdopter(historyItems,getContext());
        recyclerView.setAdapter(mAdapter);



        return view;
    }
}