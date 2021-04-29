package barber.user.mybarber.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import barber.user.mybarber.R;
import barber.user.mybarber.TimeSlots.TimeSlotAdopter;
import barber.user.mybarber.TimeSlots.TimeSlotModel;

import static barber.user.mybarber.Fragments.BarberFragment.SHARED_PREFS;


public class TimeSlotFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<TimeSlotModel> timeSlotModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_time_slot, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Calendar c=Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE,d,MMM");
        String strDate = sdf.format(c.getTime());
        Log.d("tag","DATE : " + strDate);
        HorizontalPicker picker = (HorizontalPicker) view.findViewById(R.id.datePicker);

        // initialize it and attach a listener
        picker.setListener(new DatePickerListener() {
                    @Override
                    public void onDateSelected(DateTime dateSelected) {
                        editor.putString("date",dateSelected.getDayOfWeek()+","+dateSelected.getDayOfMonth()+","+dateSelected.getDayOfYear());
                        editor.apply();
                        Log.v("tag",sharedPreferences.getString("date",""));
                    }
                })
                .init();

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        timeSlotModels=new ArrayList<>();
        for (int i=10;i<=21;i++) {
            timeSlotModels.add(new TimeSlotModel(i+":00-"+i+":"+(i+30),-1));
            timeSlotModels.add(new TimeSlotModel(i+":30-"+(i+1)+":00",-1));

        }
        adapter=new TimeSlotAdopter(timeSlotModels,getContext());
        recyclerView.setAdapter(adapter);



        return view;
    }
}