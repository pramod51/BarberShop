package barber.user.mybarber.ShopAdopter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
    public void onBindViewHolder(@NonNull ShopViewHolder holder, final int position) {
        final ShopItems currentItem= shopItems.get(position);

        holder.phoneNumber.setText(currentItem.getPhoneNumber());







        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return shopItems.size();
    }

    public static class ShopViewHolder extends RecyclerView.ViewHolder {

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



        }
    }
}
