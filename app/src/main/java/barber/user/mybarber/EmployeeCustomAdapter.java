package barber.user.mybarber;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import static java.security.AccessController.getContext;

public class EmployeeCustomAdapter extends BaseAdapter {
    Context applicationContext;
    private ArrayList<HashMap<String, String>> employeeArrayHashMap;

    public EmployeeCustomAdapter(Context applicationContext, ArrayList<HashMap<String, String>> employeeArrayHashMap) {
        this.employeeArrayHashMap = employeeArrayHashMap;
        this.applicationContext = applicationContext;
    }

    @Override
    public int getCount() {
        return employeeArrayHashMap.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = ((Activity) applicationContext).getLayoutInflater().inflate(R.layout.barber_list_items, viewGroup, false);
        }

        TextView employeeName = (TextView) view.findViewById(R.id.employee_name);
        TextView employeePhone = (TextView) view.findViewById(R.id.employee_phone);
        ImageView employeeImage = (ImageView) view.findViewById(R.id.employee_pic);

        employeeName.setText(employeeArrayHashMap.get(i).get("barberName"));
        employeePhone.setText(employeeArrayHashMap.get(i).get("barberPhone"));

        Glide.with(applicationContext)
                .load(employeeArrayHashMap.get(i).get("barberImageUrl"))
                .into(employeeImage);

        return view;
    }
}
