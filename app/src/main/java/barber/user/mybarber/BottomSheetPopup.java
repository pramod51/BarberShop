package barber.user.mybarber;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker;
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener;

import java.util.Date;

public class BottomSheetPopup extends BottomSheetDialogFragment {

    private TimePicker timePicker;
    private EditText description;
    private DayScrollDatePicker mPicker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_bottom_popup,container,false);
        description=view.findViewById(R.id.description);
        mPicker = (DayScrollDatePicker) view.findViewById(R.id.day_date_picker);
        timePicker=view.findViewById(R.id.time_picker);
        mPicker.setStartDate(01, 02, 2021);
        mPicker.setEndDate(11, 11, 2022);
        mPicker.getSelectedDate(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@Nullable Date date) {
                if(date != null){
                    // do something with selected date
                }
            }
        });

        return view;
    }



}
