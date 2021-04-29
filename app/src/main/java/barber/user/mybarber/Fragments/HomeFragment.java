package barber.user.mybarber.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import barber.user.mybarber.HistoryAdopter.HistoryItems;
import barber.user.mybarber.MyBookingAdapter.MyBookingAdopter;
import barber.user.mybarber.MyBookingAdapter.MyBookingModel;
import barber.user.mybarber.R;
import barber.user.mybarber.SelectBarberFragmentDirections;
import barber.user.mybarber.ShopAdopter.ShopItems;

import static barber.user.mybarber.Fragments.BarberFragment.SHARED_PREFS;

public class HomeFragment extends Fragment {
    ListView barberShopListView;
    String[] values;

    private RecyclerView recyclerView;
    private ArrayList<MyBookingModel> models;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<HistoryItems> items;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private Context context;
    CardView cardView;
    String uId= FirebaseAuth.getInstance().getCurrentUser().getUid();
    TextView emptyMessage;
    Button createBookingButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//
//        return view;
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        emptyMessage=view.findViewById(R.id.empty_message);
        TextView textView=view.findViewById(R.id.name);
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        FirebaseDatabase.getInstance().getReference().child("UserDB").child("Users").child(uId).
                child("Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                textView.setText(snapshot.getValue(String.class));
                editor.putString("Name",textView.getText().toString());
                editor.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FirebaseDatabase.getInstance().getReference().child("UserDB").child("Users").child(uId).child("MyBookings")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Calendar c=Calendar.getInstance();
                        Calendar cal=Calendar.getInstance();
                        items=new ArrayList<>();
                        SimpleDateFormat sdf = new SimpleDateFormat("EEE,d,MMM,yyyy");
                        String strDate = sdf.format(c.getTime());
                        HashMap<String, Object> map=new HashMap<>();
                        models=new ArrayList<>();
                        if (snapshot.exists())
                        for (DataSnapshot ds:snapshot.getChildren()){
                            try {
                                cal.setTime(sdf.parse(ds.child("date").getValue(String.class)));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (!cal.before(c))
                            models.add(new MyBookingModel(ds.child("Address").getValue(String.class),ds.child("Phone").getValue(String.class),
                                    ds.child("time").getValue(String.class),ds.child("date").getValue(String.class),
                                    ds.child("status").getValue(String.class),ds.getKey(),ds.child("stylist").getValue(String.class)));
                            else {
                                HashMap<String, Object> map1=new HashMap<>();
                                map1.put("Address",ds.child("Address").getValue(String.class));
                                map1.put("date",ds.child("date").getValue(String.class));
                                map1.put("time",ds.child("time").getValue(String.class));
                                map1.put("Name",sharedPreferences.getString("Name","No Name"));
                                map1.put("stylist",ds.child("stylist").getValue(String.class));
                                map1.put("Phone",ds.child("Phone").getValue(String.class));
                                map1.put("status","Expired");
                                FirebaseDatabase.getInstance().getReference().child("UserDB").child("Users").child(uId).child("History").
                                        push().setValue(map1);
                            }

                        }
                        mAdapter=new MyBookingAdopter(models,getContext(),emptyMessage);
                        recyclerView.setAdapter(mAdapter);

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });





        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}