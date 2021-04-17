package barber.user.mybarber;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class SelectBarberFragment extends Fragment {
    ListView selectBarberListView;
    String[] barbersNameArray;
    ArrayList<HashMap<String, String>> employeeArrayHashMap;

    public SelectBarberFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        employeeArrayHashMap = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_barber, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectBarberListView = view.findViewById(R.id.select_barber_listView);


        FirebaseDatabase.getInstance().getReference().child("AdminDB").child("Shops").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    //Toast.makeText(view.getContext(), datasnapshot.toString(), Toast.LENGTH_SHORT).show();

                    int i = 0;
                    for (DataSnapshot datasnapshot1 : datasnapshot.child("Employee").getChildren()) {
//                        Toast.makeText(view.getContext(), "Dhiru", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(view.getContext(), datasnapshot1.toString(), Toast.LENGTH_SHORT).show();

                        String barberName = datasnapshot1.child("Name").getValue(String.class);
                        String barberPhone = datasnapshot1.child("PhoneNo").getValue(String.class);
                        String barberImageUrl = datasnapshot1.child("Pic").getValue(String.class);

                        HashMap<String, String> hashMap = new HashMap();
                        hashMap.put("barberName", barberName);
                        hashMap.put("barberPhone", barberPhone);
                        hashMap.put("barberImageUrl", barberImageUrl);

                        employeeArrayHashMap.add(hashMap);
                        i++;

//                        Log.i("barberName", barberName);
//                        Log.i("barberPhone", barberPhone);
//                        Log.i("barberImageUrl", barberImageUrl);
//                        Log.i("Dhiraj", datasnapshot.toString());
//
//                        Toast.makeText(view.getContext(), barberName, Toast.LENGTH_SHORT).show();
                    }
                }

                EmployeeCustomAdapter employeeCustomAdapter = new EmployeeCustomAdapter(getContext(), employeeArrayHashMap);
                selectBarberListView.setAdapter(employeeCustomAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}