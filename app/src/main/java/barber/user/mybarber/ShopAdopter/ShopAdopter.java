package barber.user.mybarber.ShopAdopter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import barber.user.mybarber.MobileAuthentication;
import barber.user.mybarber.R;


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
                HorizontalPicker picker = (HorizontalPicker)view1.findViewById(R.id.datePicker);
                Button submit=view1.findViewById(R.id.submit);
                final TimePicker timePicker=view1.findViewById(R.id.time_picker);
                final EditText description=view1.findViewById(R.id.description);
                // initialize it and attach a listener
                final String[] date = {""};
                final String[] time = {""};
                picker.setListener(new DatePickerListener() {
                    @Override
                    public void onDateSelected(DateTime dateSelected) {
                        date[0] +=dateSelected.toString();
                    }
                })
                        .setDays(20)
                        .setOffset(10)
                        .setDateSelectedColor(Color.DKGRAY)
                        .setDateSelectedTextColor(Color.WHITE)
                        .setMonthAndYearTextColor(Color.DKGRAY)
                        .setTodayButtonTextColor(context.getResources().getColor(R.color.steel_blue))
                        .setTodayDateTextColor(context.getResources().getColor(R.color.colorAccent))
                        .setTodayDateBackgroundColor(Color.GRAY)
                        .setUnselectedDayTextColor(Color.DKGRAY)
                        .setDayOfWeekTextColor(Color.DKGRAY)
                        .setUnselectedDayTextColor(context.getResources().getColor(R.color.gray))
                        .showTodayButton(true)
                        .init();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //set Description,date,time
                        final ProgressDialog dialog = new ProgressDialog(context);
                        dialog.setMessage("please wait...");
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                        Log.v("tag", "Selected date is " + date[0]);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            Log.v("tag", "Selected time is " + timePicker.getHour());
                            time[0] =timePicker.getHour()+":"+timePicker.getMinute();
                        }
                        else {
                            Calendar c=Calendar.getInstance();
                            time[0] =c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);

                        }
                        //user registration

                        RequestQueue requestQueue = Volley.newRequestQueue(context);

                        String url = "https://mybarber.herokuapp.com/customer/api/appointment/new/601d7f7ea6ab7300157e90f0/"+currentItem.getId();

                        JSONObject postData = new JSONObject();
                        try {
                            postData.put("date", date);
                            postData.put("time", time[0]);
                            postData.put("description", description.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String msg = response.getString("msg");

                                    // code here
                                    dialog.dismiss();
                                    bottomSheetDialog.dismiss();
                                    Toast.makeText(context,"Request Successful",Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Log.d("err", error.toString());
                                dialog.dismiss();
                                Toast.makeText(context,"Something went wrong! Try Again",Toast.LENGTH_LONG).show();
                            }
                        });

                        requestQueue.add(jsonObjectRequest);

                    }
                });

            }

        });
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
            call=itemView.findViewById(R.id.call);
            request=itemView.findViewById(R.id.request);
            shopName=itemView.findViewById(R.id.shop_name);
            phoneNumber=itemView.findViewById(R.id.phone_number);
            address=itemView.findViewById(R.id.address);
            ownerName=itemView.findViewById(R.id.owner_name);
            linearLayout=itemView.findViewById(R.id.bottom_popup);



        }
    }
}
