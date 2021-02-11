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
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import barber.user.mybarber.HistoryAdopter.HistoryAdopter;
import barber.user.mybarber.HistoryAdopter.HistoryItems;
import barber.user.mybarber.R;

import java.util.ArrayList;
import java.util.Objects;

import static barber.user.mybarber.UserRegestration.SHARED_PREFS;
import static barber.user.mybarber.UserRegestration.USER_ID;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<HistoryItems> historyItems;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView emptyMessage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        progressBar=view.findViewById(R.id.progressbar);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh);
        emptyMessage=view.findViewById(R.id.message);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // get shops in shop fragment

        loadHistoryAppoinments(view);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadHistoryAppoinments(view);
                mAdapter.notifyDataSetChanged();

            }
        });


        return view;
    }

    private void loadHistoryAppoinments(final View view) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString(USER_ID, "");
        String url = "https://mybarber.herokuapp.com/customer/api/appointment/"+userId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("test", response.getString("msg"));
                    JSONArray data = response.getJSONArray("data");
                    historyItems=new ArrayList<>();
                    for(int i = 0; i < data.length(); i++)
                    {
                        JSONObject object = data.getJSONObject(i);

                        String id = object.getString("_id");
                        String date = object.getString("date");
                        String time = object.getString("time");
                        String description = object.getString("description");
                        String shopName = object.getJSONObject("shop").getString("name");
                        String shopPhone = object.getJSONObject("shop").getString("phone");
                        String shopOwner = object.getJSONObject("shop").getString("owner");
                        String shopAddress = object.getJSONObject("shop").getString("address");
                        boolean accepted = object.getBoolean("accepted");
                        boolean declined = object.getBoolean("declined");

                        //code here
                        historyItems.add(new HistoryItems(id,date,time,description,shopName,shopPhone,accepted,declined));

                    }
                    if (historyItems.size()==0)
                        emptyMessage.setVisibility(View.VISIBLE);
                    mAdapter=new HistoryAdopter(historyItems,getContext(),emptyMessage);
                    recyclerView.setAdapter(mAdapter);
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("err", error.toString());
                loadHistoryAppoinments(view);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


}