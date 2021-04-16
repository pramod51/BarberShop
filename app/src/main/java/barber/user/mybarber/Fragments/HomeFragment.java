package barber.user.mybarber.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import barber.user.mybarber.R;
import barber.user.mybarber.ShopAdopter.ShopItems;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<ShopItems> shopItems;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private Context context;

    Button createBookingButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        context = getContext();
//        final View view = inflater.inflate(R.layout.fragment_home, container, false);
//        recyclerView = view.findViewById(R.id.recyclerView);
//        progressBar = view.findViewById(R.id.progressbar);
//        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
//        progressBar.setVisibility(View.VISIBLE);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        return view;
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createBookingButton = view.findViewById(R.id.create_booking);
        createBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController homeFragmentNavController = Navigation.findNavController(view);
                homeFragmentNavController.navigate(HomeFragmentDirections.actionHomeFragmentToSelectBarberShopFragment());

            }
        });
    }
}