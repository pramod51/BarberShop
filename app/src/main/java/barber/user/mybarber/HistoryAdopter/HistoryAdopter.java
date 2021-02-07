package barber.user.mybarber.HistoryAdopter;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import barber.user.mybarber.R;


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
        holder.name.setText(currentItems.getShopName());
        holder.phoneNumber.setText(currentItems.getShopPhone());
        holder.date.setText(currentItems.getDate());
        holder.time.setText(currentItems.getTime());
        holder.description.setText(currentItems.getDescription());
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //user registration
                final ProgressDialog dialog = new ProgressDialog(context);
                dialog.setMessage("please wait...");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                RequestQueue requestQueue = Volley.newRequestQueue(context);

                String url = "https://mybarber.herokuapp.com/customer/api/appointment/cancel/601d7f7ea6ab7300157e90f0/"+currentItems.getId();

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String msg = response.getString("msg");

                            // code here
                            dialog.dismiss();
                            items.remove(position);
                            notifyItemRemoved(position);



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
        if (currentItems.isAccepted()){
            holder.status.setText("Accepted");
            holder.name.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.green_dot,0);
            holder.status.setBackground(ContextCompat.getDrawable(context, R.drawable.hollw_circle_background));
        }
        else if (currentItems.isDeclined()){
            holder.status.setText("Declined");
            holder.name.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.red_dot,0);
            holder.status.setBackground(ContextCompat.getDrawable(context, R.drawable.hollw_red_circle_background));
        }else {
            holder.status.setText("Pending");
            holder.name.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.green_dot,0);
            holder.status.setBackground(ContextCompat.getDrawable(context, R.drawable.hollw_yellow_circle_background));
        }



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView name,phoneNumber,date,status,time,description;
        private Button cancel;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cancel=itemView.findViewById(R.id.cancel);
            name=itemView.findViewById(R.id.name);
            phoneNumber=itemView.findViewById(R.id.phone_number);
            date=itemView.findViewById(R.id.date);
            time=itemView.findViewById(R.id.time);
            status=itemView.findViewById(R.id.status);
            description=itemView.findViewById(R.id.description);
        }
    }
}
