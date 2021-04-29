package barber.user.mybarber.HistoryAdopter;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import barber.user.mybarber.R;

import static barber.user.mybarber.UserRegestration.SHARED_PREFS;
import static barber.user.mybarber.UserRegestration.USER_ID;


public class HistoryAdopter extends RecyclerView.Adapter<HistoryAdopter.HistoryViewHolder> {
    private ArrayList<HistoryItems> items;
    private Context context;

    public HistoryAdopter(ArrayList<HistoryItems> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_history_items,parent,false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, final int position) {
        final HistoryItems currentItems=items.get(position);
        holder.name.setText(currentItems.getName());
        holder.phoneNumber.setText(currentItems.getPhone());
        holder.date.setText(currentItems.getDate());
        holder.time.setText(currentItems.getTime());
        holder.description.setText(currentItems.getAddress());
        holder.status.setText(currentItems.getStatus());
        


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView name,phoneNumber,date,status,time,description;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.shop_name);
            phoneNumber=itemView.findViewById(R.id.phone_number);
            date=itemView.findViewById(R.id.date);
            time=itemView.findViewById(R.id.time);
            status=itemView.findViewById(R.id.status);
            description=itemView.findViewById(R.id.description);
        }
    }
}
