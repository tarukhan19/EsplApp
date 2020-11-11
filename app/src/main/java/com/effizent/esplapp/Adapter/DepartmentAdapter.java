package com.effizent.esplapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.effizent.esplapp.Model.DepartmentDTO;
import com.effizent.esplapp.R;
import com.effizent.esplapp.session.SessionManager;

import java.util.ArrayList;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.CustomViewHodler> {

    private Context mContext;
    ArrayList<DepartmentDTO> departmentDTOS;
    DepartmentAdp departmentAdp;
    RecyclerView departmentrecycle;
    ArrayList<String> departmentlistnamerray;
    SessionManager session;
    public DepartmentAdapter(Context context, ArrayList<DepartmentDTO> departmentDTOS, RecyclerView departmentrecycle, ArrayList<String> departmentlistnamerray) {
        this.mContext = context;
        this.departmentDTOS = departmentDTOS;
        this.departmentrecycle = departmentrecycle;
        this.departmentlistnamerray = departmentlistnamerray;
    }

    @Override
    public CustomViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_spinner, parent, false);
        session = new SessionManager(mContext);

        return new CustomViewHodler(itemView);
    }

    @Override
    public void onBindViewHolder(final CustomViewHodler holder, final int position) {

        final DepartmentDTO departmentDTO = departmentDTOS.get(position);
        holder.text.setText(departmentDTO.getDeptName());
        holder.text.setTextColor(departmentDTO.isSelected() ? Color.CYAN : Color.BLACK);

        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                departmentDTO.setSelected(!departmentDTO.isSelected());
                holder.text.setTextColor(departmentDTO.isSelected() ? Color.CYAN : Color.BLACK);


                if (departmentDTO.isSelected()) {
                    departmentrecycle.setVisibility(View.VISIBLE);
                    departmentlistnamerray.add(departmentDTO.getDeptName());

                } else {
                    departmentlistnamerray.remove(departmentDTO.getDeptName());
                    Log.e("lenslistsentrrayrem", departmentlistnamerray + " ");

                }
                departmentrecycle.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                departmentAdp = new DepartmentAdp(mContext, holder.text, departmentlistnamerray);
                departmentrecycle.setAdapter(departmentAdp);
                notifyDataSetChanged();
                departmentAdp.notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return departmentDTOS == null ? 0 : departmentDTOS.size();
    }

    public class CustomViewHodler extends RecyclerView.ViewHolder {
        //            ImageView imageView;
        TextView text;

        public CustomViewHodler(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }

    public String getDepartmentName() {


        StringBuilder sbString = new StringBuilder();

        //iterate through ArrayList
        for (String services : departmentlistnamerray) {

            //append ArrayList element followed by comma
            sbString.append(services).append(",");
        }

        String selectedDepartment = sbString.toString().trim();
        if (selectedDepartment.length() > 0) {
            selectedDepartment = selectedDepartment.substring(0, selectedDepartment.length() - 1);
            Log.e("selectedDepartment",selectedDepartment);
        }
        return selectedDepartment;
    }
}
