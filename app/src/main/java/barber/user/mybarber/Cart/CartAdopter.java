package barber.user.mybarber.Cart;


import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import barber.user.mybarber.R;


public class CartAdopter extends RecyclerView.Adapter<CartAdopter.ViewHolder> {
    private ArrayList<CartItems> items;
    private Context context;
    String uId= FirebaseAuth.getInstance().getCurrentUser().getUid();
    public CartAdopter(ArrayList<CartItems> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final CartItems currentItems=items.get(position);
        Picasso.get().load(currentItems.getImageUrl()).into(holder.imageView);
        holder.title.setText(currentItems.getTitle());
        holder.price.setText(currentItems.getPrice());
        holder.quantity.setText(currentItems.getQuantity());
        holder.inr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("UserDB").child("Users").child(uId).child("Cart")
                        .child(currentItems.getChildrenKey()).child("Quantity").setValue(""+(Integer.parseInt(currentItems.getQuantity())+1));
                currentItems.setQuantity(""+(Integer.parseInt(currentItems.getQuantity())+1));
                notifyItemChanged(position);
                notifyDataSetChanged();
            }
        });
        holder.dcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(currentItems.getQuantity())==1){
                    items.remove(position);
                    notifyItemRemoved(position);
                    FirebaseDatabase.getInstance().getReference().child("UserDB").child("Users").child(uId).child("Cart")
                            .child(currentItems.getChildrenKey()).removeValue();

                }
                else{

                    FirebaseDatabase.getInstance().getReference().child("UserDB").child("Users").child(uId).child("Cart")
                            .child(currentItems.getChildrenKey()).child("Quantity").setValue(""+(Integer.parseInt(currentItems.getQuantity())-1));
                    currentItems.setQuantity(""+(Integer.parseInt(currentItems.getQuantity())-1));
                    notifyItemChanged(position);

                }
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title,price,inr,dcr,quantity;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            title=itemView.findViewById(R.id.title);
            price=itemView.findViewById(R.id.price);
            inr=itemView.findViewById(R.id.inr);
            dcr=itemView.findViewById(R.id.dcr);
            quantity=itemView.findViewById(R.id.quantity);

        }
    }
}
