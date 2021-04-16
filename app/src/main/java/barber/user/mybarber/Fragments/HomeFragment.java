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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import barber.user.mybarber.R;

import barber.user.mybarber.ShopAdopter.ShopItems;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ListView barberShopListView;
    String[] values;

    private RecyclerView recyclerView;
    private ArrayList<ShopItems> shopItems;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private Context context;

    Button createBookingButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        values = new String[]{"Seremban(Main Branch)\n" +
                "\n" +
                "Address: No 38, Jalan Dato Lee Fong Yee, Bandar Seremban, 70000 Seremban, Negeri Sembilan, Malaysia\n",


                "Rembau\n" +
                        "\n" +
                        "Address: No 11, Taman Seri N.Sembilan, 381, Lorong Tsr 36, 71300 Rembau, Negeri Sembilan, Malaysia\n",


                "Bahau\n" +
                        "\n" +
                        "Address: No 27, Shoplot Level 1, Kiara Square Bahau, Bahau, Jempol, Negeri Sembilan\n"
        };

    }

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



        barberShopListView = view.findViewById(R.id.barber_shop_list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        barberShopListView.setAdapter(adapter);

        barberShopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NavController homeFragmentNavController = Navigation.findNavController(view);
                homeFragmentNavController.navigate(HomeFragmentDirections.actionHomeFragmentToSelectBarberFragment(i));
            }
        });
    }
}