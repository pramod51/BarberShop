package barber.user.mybarber.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import barber.user.mybarber.HistoryAdopter.HistoryAdopter;
import barber.user.mybarber.HistoryAdopter.HistoryItems;
import barber.user.mybarber.R;

import java.util.ArrayList;
import java.util.Objects;

import static barber.user.mybarber.UserRegestration.SHARED_PREFS;
import static barber.user.mybarber.UserRegestration.USER_ID;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<HistoryItems> historyItems;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView emptyMessage;
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



        return view;
    }



}