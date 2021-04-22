package barber.user.mybarber;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import barber.user.mybarber.Fragments.HomeFragment;


public class ConfirmBookingFragment extends Fragment {
    Button previousButton;
    Button confirmButton;
    TextView addressOfSaloonTextView, dateAndTimeTextView, selectedBarberNameTextView, shopNumberTextView;
    String[] values;
    ConfirmBookingFragmentArgs args;

    public ConfirmBookingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        values = new String[]{
                "Seremban(Main Branch)\n" +
                        "Address: No 38, Jalan Dato Lee Fong Yee, Bandar Seremban, 70000 Seremban, Negeri Sembilan, Malaysia\n",
                "Rembau\n" +
                        "Address: No 11, Taman Seri N.Sembilan, 381, Lorong Tsr 36, 71300 Rembau, Negeri Sembilan, Malaysia\n",
                "Bahau\n" +
                        "Address: No 27, Shoplot Level 1, Kiara Square Bahau, Bahau, Jempol, Negeri Sembilan\n"
        };


        args = ConfirmBookingFragmentArgs.fromBundle(getArguments());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_booking, container, false);

        previousButton = view.findViewById(R.id.previous_button);
        confirmButton = view.findViewById(R.id.confirm_booking_button);
        addressOfSaloonTextView = view.findViewById(R.id.address_of_saloon_text_view);
        dateAndTimeTextView = view.findViewById(R.id.date_and_time_text_view);
        selectedBarberNameTextView = view.findViewById(R.id.selected_barber_name);
        shopNumberTextView = view.findViewById(R.id.shop_number);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addressOfSaloonTextView.setText(values[args.getBarberShopSelectedNo()]);
        dateAndTimeTextView.setText(SelectBarberFragment.dateSelected + " at " + SelectBarberFragment.timeSelected);
        selectedBarberNameTextView.setText(args.getSelectedBarberName());
        shopNumberTextView.setText(HomeFragment.selectedShopNumber);

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}