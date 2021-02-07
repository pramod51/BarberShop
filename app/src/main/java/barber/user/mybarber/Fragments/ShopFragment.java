package barber.user.mybarber.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import barber.user.mybarber.R;
import barber.user.mybarber.ShopAdopter.ShopAdopter;
import barber.user.mybarber.ShopAdopter.ShopItems;

import java.util.ArrayList;

public class RequestFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<ShopItems> shopItems;
    private RecyclerView.Adapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        shopItems =new ArrayList<>();
        shopItems.add(new ShopItems("Pramod","9956118310","","","Pending","Hair Cut"));
        for (int i=1;i<6;i++)
        shopItems.add(new ShopItems("Himanshu","9956118310","","","Pending","Hair Cut"));
        mAdapter=new ShopAdopter(shopItems,getContext());
        recyclerView.setAdapter(mAdapter);






        return view;
    }
}