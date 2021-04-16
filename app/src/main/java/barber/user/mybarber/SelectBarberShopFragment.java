package barber.user.mybarber;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import barber.user.mybarber.Fragments.HomeFragmentDirections;


public class SelectBarberShopFragment extends Fragment {
    ListView barberShopListView;
    String[] values;

    public SelectBarberShopFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_barber_shop, container, false);
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
                NavController selectBarberShopFragmentNavController = Navigation.findNavController(view);
                selectBarberShopFragmentNavController.navigate(SelectBarberShopFragmentDirections.actionSelectBarberShopFragmentToSelectBarberFragment());

            }
        });
    }
}