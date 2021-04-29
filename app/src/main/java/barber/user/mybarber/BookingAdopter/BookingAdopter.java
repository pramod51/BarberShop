package barber.user.mybarber.BookingAdopter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import barber.user.mybarber.BarberAdopter.BarberModel;
import barber.user.mybarber.Fragments.BarberFragment;
import barber.user.mybarber.Fragments.ShoppingFragment;
import barber.user.mybarber.R;

import static barber.user.mybarber.Fragments.BarberFragment.SHARED_PREFS;

public class BookingAdopter extends RecyclerView.Adapter<BookingAdopter.ViewHolder> {

    ArrayList<BookingModel> bookingModels;
    Context context;
    int selectedPosition=0;
    SharedPreferences sharedPreferences;

    public BookingAdopter(ArrayList<BookingModel> bookingModels, Context context) {
        this.bookingModels = bookingModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.barber_location_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        sharedPreferences=context.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        BookingModel model=bookingModels.get(position);
        holder.textView.setText(model.getName()+"\n"+model.getAddress());
        holder.layout.setVisibility(View.GONE);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("tag","clicked");

                if (selectedPosition >= 0)
                    notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                bookingModels.get(selectedPosition).setSelectedPosition(selectedPosition);
                Log.v("tag",""+selectedPosition);
                notifyItemChanged(selectedPosition);

            }
        });

        if (selectedPosition == position) {

            holder.itemView.setSelected(true); //using selector drawable
            //holder.textView.setBackground(ContextCompat.getDrawable(context,R.drawable.background_drawable));
            holder.layout.setVisibility(View.VISIBLE);
            //holder.radioButton.setTextColor(context.getResources().getColor(R.color.white));
            model.setSelectedPosition(position);
            editor.putString("key",model.getChildrenKey());
            editor.apply();

        } else {
            holder.layout.setVisibility(View.GONE);
            holder.itemView.setSelected(false);
        }

        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingFragment shoppingFragment=new ShoppingFragment();
                Bundle bundle=new Bundle();
                bundle.putString("key",model.getChildrenKey());
                shoppingFragment.setArguments(bundle);
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment,shoppingFragment).commit();

            }
        });
        holder.rates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return bookingModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        LinearLayout layout;
        CardView cardView;
        RelativeLayout cart,rates;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.details);
            layout=itemView.findViewById(R.id.layout);
            cardView=itemView.findViewById(R.id.card);
            cart=itemView.findViewById(R.id.cart);
            rates=itemView.findViewById(R.id.rate);

        }
    }
}
