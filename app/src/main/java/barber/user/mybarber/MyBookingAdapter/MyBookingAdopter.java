package barber.user.mybarber.MyBookingAdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import barber.user.mybarber.Fragments.HomeFragmentDirections;
import barber.user.mybarber.R;
import barber.user.mybarber.SelectBarberFragmentDirections;

import static barber.user.mybarber.Fragments.BarberFragment.SHARED_PREFS;

public class MyBookingAdopter extends RecyclerView.Adapter<MyBookingAdopter.ViewHolder> {
    ArrayList<MyBookingModel> models;
    Context context;
    String uId= FirebaseAuth.getInstance().getCurrentUser().getUid();
    TextView emptyMessage;
    SharedPreferences sharedPreferences;

    public MyBookingAdopter(ArrayList<MyBookingModel> models, Context context, TextView emptyMessage) {
        this.models = models;
        this.context = context;
        this.emptyMessage = emptyMessage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemCount()==0)
            emptyMessage.setVisibility(View.VISIBLE);
        else
            emptyMessage.setVisibility(View.GONE);
        MyBookingModel model=models.get(position);
        sharedPreferences=context.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        holder.address.setText(model.getAddress());
        holder.status.setText(model.getStatus());
        holder.stylist.setText(model.getStylist());
        holder.time.setText(model.getDate()+" "+model.getTime());

        ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                models.remove(position);
                notifyDataSetChanged();
                HashMap<String, Object> map1=new HashMap<>();
                map1.put("Address",model.getAddress());
                map1.put("date",model.getDate());
                map1.put("time",model.getTime());
                map1.put("Name",sharedPreferences.getString("Name","No Name"));
                map1.put("Phone",model.getPhoneNumber());
                map1.put("status","Cancelled");
                FirebaseDatabase.getInstance().getReference().child("UserDB").child("Users").child(uId).child("History").
                        push().setValue(map1);
            }
        };

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView address,stylist,time,status;
        MaterialButton change,remove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            address=itemView.findViewById(R.id.address);
            stylist=itemView.findViewById(R.id.stylist);
            time=itemView.findViewById(R.id.time);
            status=itemView.findViewById(R.id.status);

        }

    }
}
