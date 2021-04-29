package barber.user.mybarber.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.HashMap;

import barber.user.mybarber.MainActivity;
import barber.user.mybarber.MakePaymentActivity;
import barber.user.mybarber.MakePaymentFragment;
import barber.user.mybarber.R;

import static barber.user.mybarber.Fragments.BarberFragment.SHARED_PREFS;


public class StartBookingFragment extends Fragment  {

    StepView stepView;
    int step=0;
    String uId= FirebaseAuth.getInstance().getCurrentUser().getUid();
    private MaterialButton prev,next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_start_booking, container, false);
        prev=view.findViewById(R.id.previous);;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment,new BookingFragment()).commit();
        Log.v("tag","startBooking frag");
        stepView=view.findViewById(R.id.step_view);
        stepView.setStepsNumber(5);
    stepView.getState()
                .selectedTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .animationType(StepView.ANIMATION_CIRCLE)
                .selectedCircleColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .selectedStepNumberColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                // You should specify only stepsNumber or steps array of strings.
                // In case you specify both steps array is chosen.
                .steps(new ArrayList<String>() {{
                    add("Start Booking");
                    add("Select Barber");
                    add("Choose Time");
                    add("Services");
                    add("Confirm Booking");
                    add("Make Payment");
                }})
                // You should specify only steps number or steps array of strings.
                // In case you specify both steps array is chosen.
                .stepsNumber(5)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .typeface(ResourcesCompat.getFont(getContext(), R.font.amaranth))
                // other state methods are equal to the corresponding xml attributes
                .commit();


        //FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        next=view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                step++;
                stepView.go(step,true);
                if (step==1) {
                    prev.setEnabled(false);
                    BarberFragment barberFragment=new BarberFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment,new BarberFragment()).commit();
                } else if (step==2) {
                    prev.setEnabled(true);
                    fragmentManager.beginTransaction().replace(R.id.fragment,new TimeSlotFragment()).commit();
                } else if (step==3) {
                    fragmentManager.beginTransaction().replace(R.id.fragment,new ChooseServices()).commit();
                    next.setText("Confirm");
                }
                else if (step==4) {

                    fragmentManager.beginTransaction().replace(R.id.fragment,new ConfirmBookingFragment()).commit();
                } else if (step==5)
                    fragmentManager.beginTransaction().replace(R.id.fragment,new MakePaymentActivity()).commit();
                else {
                    HashMap<String, Object> map=new HashMap<>();
                    SharedPreferences sharedPreferences=getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                    map.put("Name",sharedPreferences.getString("Name",""));
                    map.put("date",sharedPreferences.getString("date",""));
                    map.put("PhoneNo",sharedPreferences.getString("PhoneNo",""));
                    map.put("Address",sharedPreferences.getString("Address",""));
                    map.put("time",sharedPreferences.getString("time",""));
                    map.put("status","Pending");

                    FirebaseDatabase.getInstance().getReference().child("UserDB").child("Users").child(uId)
                            .child("MyBookings").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            getActivity().startActivity(new Intent(getContext(), MainActivity.class));
                        }
                    });
                }

            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                step--;
                Log.v("tag","step===>"+step);
                if (step==1) {
                    prev.setEnabled(false);
                    BarberFragment barberFragment=new BarberFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment,new BarberFragment()).commit();
                }
                if (step==2) {
                    prev.setEnabled(true);
                    fragmentManager.beginTransaction().replace(R.id.fragment,new TimeSlotFragment()).commit();
                } else if (step==3) {
                    fragmentManager.beginTransaction().replace(R.id.fragment,new ChooseServices()).commit();

                }
                else if (step==4) {
                    next.setText("Confirm");
                    fragmentManager.beginTransaction().replace(R.id.fragment,new ConfirmBookingFragment()).commit();
                } else if (step==5)
                    fragmentManager.beginTransaction().replace(R.id.fragment,new MakePaymentActivity()).commit();


            }
        });
        return view;
    }


}