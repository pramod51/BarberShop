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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SelectBarberFragment extends Fragment {
    ListView selectBarberListView;
    String[] barbersNameArray;

    public SelectBarberFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_barber, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectBarberListView = view.findViewById(R.id.select_barber_listView);


        FirebaseDatabase.getInstance().getReference().child("AdminDB").child("Shops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datasnapshot : snapshot.child("Employee").getChildren()) {
                    String barberName = datasnapshot.child("Name:").getValue(String.class);
                    String barberPhone = datasnapshot.child("PhoneNo:").getValue(String.class);
                    String barberImageUrl = datasnapshot.child("Pic:").getValue(String.class);

                    Log.i("barberName", barberName);
                    Log.i("barberPhone", barberPhone);
                    Log.i("barberImageUrl", barberImageUrl);
                    Log.i("Dhiraj", datasnapshot.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}