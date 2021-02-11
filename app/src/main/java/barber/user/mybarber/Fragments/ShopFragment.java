package barber.user.mybarber.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import barber.user.mybarber.R;
import barber.user.mybarber.ShopAdopter.ShopAdopter;
import barber.user.mybarber.ShopAdopter.ShopItems;

import java.util.ArrayList;

import static barber.user.mybarber.UserRegestration.SHARED_PREFS;
import static barber.user.mybarber.UserRegestration.USER_ID;

public class ShopFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<ShopItems> shopItems;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getContext();
        final View view = inflater.inflate(R.layout.fragment_shop, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        progressBar=view.findViewById(R.id.progressbar);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadShopsData(view);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadShopsData(view);
                mAdapter.notifyDataSetChanged();

            }
        });






        return view;
    }
    private void loadShopsData(final View view) {
        // get shops in shop fragment

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString(USER_ID, "");

        String url = "https://mybarber.herokuapp.com/customer/api/shop/"+userId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                   /// Log.d("test", response.getString("msg"));
                    JSONArray data = response.getJSONArray("data");
                    shopItems =new ArrayList<>();
                    for(int i = 0; i < data.length(); i++)
                    {
                        JSONObject object = data.getJSONObject(i);

                        String id = object.getString("_id");
                        String name = object.getString("name");
                        String owner = object.getString("owner");
                        String phone = object.getString("phone");
                        String address = object.getString("address");

                        //code here

                        shopItems.add(new ShopItems(id,name,owner,phone,address));


                    }
                    mAdapter=new ShopAdopter(shopItems,getContext());
                    recyclerView.setAdapter(mAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("err", error.toString());
                loadShopsData(view);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}