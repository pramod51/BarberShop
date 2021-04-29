package barber.user.mybarber;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.HashMap;


public class SelectBarberFragment extends Fragment {
    ListView selectBarberListView;
    ArrayList<HashMap<String, String>> employeeArrayHashMap;
    int shopSelectedNo;
    String shopSelectedName;
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    EmployeeCustomAdapter employeeCustomAdapter;
    Button selectAutomaticallyBarberButton;
    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    public static String dateSelected;
    public static String timeSelected;

    public SelectBarberFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        employeeArrayHashMap = new ArrayList<>();

        assert getArguments() != null;
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

                    HashMap<String, String> hashMap = new HashMap<>();
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
        selectAutomaticallyBarberButton = view.findViewById(R.id.select_automatically_barber_button);

        selectBarberListView.setAdapter(employeeCustomAdapter);

        btnDatePicker = view.findViewById(R.id.btn_date);
        btnTimePicker = view.findViewById(R.id.btn_time);
        txtDate = view.findViewById(R.id.in_date);
        txtTime = view.findViewById(R.id.in_time);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController selectBarberFragmentNavController = Navigation.findNavController(view);

        selectBarberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectBarberFragmentNavController.navigate(SelectBarberFragmentDirections.actionSelectBarberFragmentToConfirmBookingFragment(shopSelectedNo, employeeArrayHashMap.get(i).get("barberName")));

            }
        });

        selectAutomaticallyBarberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBarberFragmentNavController.navigate(SelectBarberFragmentDirections.actionSelectBarberFragmentToConfirmBookingFragment(shopSelectedNo, employeeArrayHashMap.get(0).get("barberName")));

            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateSelected = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        txtDate.setText(dateSelected);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeSelected = hourOfDay + ":" + minute;
                        txtTime.setText(timeSelected);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();
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