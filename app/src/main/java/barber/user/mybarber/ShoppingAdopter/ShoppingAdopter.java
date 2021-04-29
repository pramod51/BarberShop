package barber.user.mybarber.ShoppingAdopter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


import barber.user.mybarber.R;


public class ShoppingAdopter extends RecyclerView.Adapter<ShoppingAdopter.ViewHolder> {
    private ArrayList<ShoppingItems> items;
    private Context context;
    String uId= FirebaseAuth.getInstance().getCurrentUser().getUid();

    public ShoppingAdopter(ArrayList<ShoppingItems> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ShoppingItems currentItems=items.get(position);
        Picasso.get().load(currentItems.getImageUrl()).into(holder.imageView);
        holder.title.setText(currentItems.getTitle());
        holder.price.setText(currentItems.getPrice());
        holder.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> map=new HashMap<>();
                map.put("Image",currentItems.getImageUrl());
                map.put("Title",currentItems.getTitle());
                map.put("Price",currentItems.getPrice());
                map.put("Quantity","1");
                FirebaseDatabase.getInstance().getReference().child("UserDB").child("Users").child(uId)
                        .child("Cart").push().setValue(map);
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title,price,addCart;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            title=itemView.findViewById(R.id.title);
            price=itemView.findViewById(R.id.price);
            addCart=itemView.findViewById(R.id.add);
        }
    }
}
