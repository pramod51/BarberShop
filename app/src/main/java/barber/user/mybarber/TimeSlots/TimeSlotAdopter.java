package barber.user.mybarber.TimeSlots;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import barber.user.mybarber.BarberAdopter.BarberModel;
import barber.user.mybarber.R;

import static barber.user.mybarber.Fragments.BarberFragment.SHARED_PREFS;

public class TimeSlotAdopter extends RecyclerView.Adapter<TimeSlotAdopter.ViewHolder> {
    ArrayList<TimeSlotModel> timeSlotModels;
    Context context;
    int selectedPosition=-1;

    public TimeSlotAdopter(ArrayList<TimeSlotModel> timeSlotModels, Context context) {
        this.timeSlotModels = timeSlotModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.time_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimeSlotModel model=timeSlotModels.get(position);
        holder.timing.setText(model.getTime());
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("tag","clicked");

                if (selectedPosition >= 0)
                    notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                timeSlotModels.get(selectedPosition).setSelectedPosition(selectedPosition);
                Log.v("tag",""+selectedPosition);
                holder.cardView.setCardBackgroundColor(Color.parseColor("#FB8DB2"));
                editor.putString("time",model.getTime());
                editor.apply();
                notifyItemChanged(selectedPosition);
            }
        });

        if (selectedPosition == position) {

            holder.itemView.setSelected(true); //using selector drawable
            //holder.textView.setBackground(ContextCompat.getDrawable(context,R.drawable.background_drawable));
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FB8DB2"));
            //holder.radioButton.setTextColor(context.getResources().getColor(R.color.white));
            model.setSelectedPosition(position);
            //editor.putString("key",model.getChildrenKey());
            //editor.apply();

        } else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#F6F6D9"));
            holder.itemView.setSelected(false);
        }

    }

    @Override
    public int getItemCount() {
        return timeSlotModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView timing;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.card);
            timing=itemView.findViewById(R.id.time);
        }
    }
}
