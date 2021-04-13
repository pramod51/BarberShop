package barber.user.mybarber.ShopAdopter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import barber.user.mybarber.R;

import static barber.user.mybarber.UserRegestration.SHARED_PREFS;
import static barber.user.mybarber.UserRegestration.USER_ID;


public class ShopAdopter extends RecyclerView.Adapter<ShopAdopter.ShopViewHolder> {

    private ArrayList<ShopItems> shopItems;
    private Context context;

    public ShopAdopter(ArrayList<ShopItems> shopItems, Context context) {
        this.shopItems = shopItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_shops_items, parent, false);
        return new ShopViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopViewHolder holder, final int position) {
        final ShopItems currentItem= shopItems.get(position);
        holder.ownerName.setText(currentItem.getOwner());
        holder.shopName.setText(currentItem.getName());
        holder.phoneNumber.setText(currentItem.getPhone());
        holder.address.setText(currentItem.getAddress());







        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = currentItem.getPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                context.startActivity(intent);
            }
        });
        holder.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context,R.style.BottomSheetDialogTheme);
                View view1=LayoutInflater.from(context).inflate(R.layout.activity_bottom_popup,holder.linearLayout);
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();
                Button submit=view1.findViewById(R.id.submit);
                final TimePicker timePicker=view1.findViewById(R.id.time_picker);
                timePicker.setIs24HourView(false);

                final EditText description=view1.findViewById(R.id.description);
                // initialize it and attach a listener
                final String[] date = {""};
                final String[] time = {""};
                date[0] +=getCurrDate();




            }

        });

    }
    public String getCurrDate()  {
        Date d = new Date();
        CharSequence s  = DateFormat.format("E, dd MMM yyyy ", d.getTime());
        return s.toString();
    }
    public static String convert24To12System (int hour, int minute) {
        String time = "";
        String am_pm = "";
        if (hour < 12 ) {
            if (hour == 0) hour = 12;
            am_pm = "AM";
        }
        else {
            if (hour != 12)
                hour-=12;
            am_pm = "PM";
        }
        String h = hour+"", m = minute+"";
        if(h.length() == 1) h = "0"+h;
        if(m.length() == 1) m = "0"+m;
        time = h+":"+m+" "+am_pm;
        return time;
    }

    @Override
    public int getItemCount() {
        return shopItems.size();
    }

    public static class ShopViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private Button call,request;
        private TextView shopName,phoneNumber,ownerName,address;
        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.bottom_popup);



        }
    }
}
