package barber.user.mybarber;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import barber.user.mybarber.Fragments.HomeFragmentDirections;


public class SelectBarberFragment extends Fragment {
    ListView selectBarberListView;
    ArrayList<HashMap<String, String>> employeeArrayHashMap;
    int shopSelectedNo;
    String shopSelectedName;
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    EmployeeCustomAdapter employeeCustomAdapter;

    public SelectBarberFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        employeeArrayHashMap = new ArrayList<>();

        SelectBarberFragmentArgs args = SelectBarberFragmentArgs.fromBundle(getArguments());
        shopSelectedNo = args.getBarberShopSelectedNo();
        getBarberShopSelectedInString(shopSelectedNo);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datasnapshot : snapshot.child("Employee").getChildren()) {
                    String barberName = datasnapshot.child("Name").getValue(String.class);
                    String barberPhone = datasnapshot.child("PhoneNo").getValue(String.class);
                    String barberImageUrl = datasnapshot.child("Pic").getValue(String.class);

                    HashMap<String, String> hashMap = new HashMap();
                    hashMap.put("barberName", barberName);
                    hashMap.put("barberPhone", barberPhone);
                    hashMap.put("barberImageUrl", barberImageUrl);

                    employeeArrayHashMap.add(hashMap);
                }

                employeeCustomAdapter = new EmployeeCustomAdapter(getContext(), employeeArrayHashMap);
                selectBarberListView.setAdapter(employeeCustomAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_barber, container, false);

        selectBarberListView = view.findViewById(R.id.select_barber_listView);
        progressBar = view.findViewById(R.id.progress_bar_select_barber);

        selectBarberListView.setAdapter(employeeCustomAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectBarberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NavController selectBarberFragmentNavController = Navigation.findNavController(view);
                selectBarberFragmentNavController.navigate(SelectBarberFragmentDirections.actionSelectBarberFragmentToConfirmBookingFragment());
            }
        });
    }

    private void getBarberShopSelectedInString(int shopSelectedNo) {
        switch (shopSelectedNo) {
            case 2:
                shopSelectedName = "kds";
                break;
            case 1:
                shopSelectedName = "lkjfd";
                break;
            case 0:
                shopSelectedName = "xyz";
                break;
        }
        databaseReference = FirebaseDatabase.getInstance().getReference().child("AdminDB").child("Shops").child(shopSelectedName);
    }

    @Override
    public void onPause() {
        super.onPause();

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStop() {
        super.onStop();

        progressBar.setVisibility(View.GONE);
    }
}