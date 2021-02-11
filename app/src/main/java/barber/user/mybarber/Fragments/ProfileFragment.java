package barber.user.mybarber.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import barber.user.mybarber.R;

import static android.content.Context.MODE_PRIVATE;
import static barber.user.mybarber.UserRegestration.PHONE_NO;
import static barber.user.mybarber.UserRegestration.SHARED_PREFS;
import static barber.user.mybarber.UserRegestration.USER_NAME;


public class ProfileFragment extends Fragment {
    private TextView name,mobNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_profile, container, false);
        name=view.findViewById(R.id.name);
        mobNumber=view.findViewById(R.id.bio);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        name.setText(sharedPreferences.getString(USER_NAME,""));
        mobNumber.setText(sharedPreferences.getString(PHONE_NO,""));
        view.findViewById(R.id.privacy_policy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="https://docs.google.com/document/d/162Um4jaz2hwtsn1GwbHgAL4Ktyw2QT4U1Kyi7OjH1bs/edit";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        return view;
    }

}