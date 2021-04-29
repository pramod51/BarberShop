package barber.user.mybarber.BarberAdopter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import barber.user.mybarber.BookingAdopter.BookingModel;
import barber.user.mybarber.R;
import de.hdodenhof.circleimageview.CircleImageView;

import static barber.user.mybarber.Fragments.BarberFragment.SHARED_PREFS;

public class BarberAdopter extends RecyclerView.Adapter<BarberAdopter.ViewHolder> {
    ArrayList<BarberModel> barberModels;
    Context context;
    private int selectedPosition=0;

    public BarberAdopter(ArrayList<BarberModel> barberModels, Context context) {
        this.barberModels = barberModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.barber_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BarberModel model=barberModels.get(position);
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE );
        Editor editor = sharedPreferences.edit();
        holder.name.setText(model.getName());
        Picasso.get().load(model.getPicUrl()).into(holder.imageView);
        holder.ratingBar.setRating((float) model.getRating());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("tag","clicked");

                if (selectedPosition >= 0)
                    notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                barberModels.get(selectedPosition).setSelectedPosition(selectedPosition);
                Log.v("tag",""+selectedPosition);
                holder.cardView.setCardBackgroundColor(Color.parseColor("#FB8DB2"));
                editor.putString("bkey",model.getChildrenKey());
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
        return barberModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        RatingBar ratingBar;
        CircleImageView imageView;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            cardView=itemView.findViewById(R.id.card);
            ratingBar=itemView.findViewById(R.id.rating);
            imageView=itemView.findViewById(R.id.image);

        }
    }
}
