package com.incipient.secondtest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.incipient.secondtest.R;
import com.incipient.secondtest.pojo.Employee;

import java.util.ArrayList;

public class EmployeeDisplayAdapter extends RecyclerView.Adapter<EmployeeDisplayAdapter.MyViewHolder>  {
    private Context context;
    private ArrayList<Employee> employees;
    private Employee employee;
    private int lastPosition=-1;
    private AdapterInterface inter;
    private int currentSelectedPosition=-1;
    private Boolean btnVisibility= false;

    public EmployeeDisplayAdapter(Context context, ArrayList<Employee> employees, AdapterInterface inter) {
        this.context = context;
        this.employees = employees;
        this.inter = inter;
    }

/*public EmployeeDisplayAdapter(Context context, ArrayList<Employee> employees) {
        this.context = context;
        this.employees = employees;
    }*/


    public void showData(int currentSelectedPosition) {
        this.currentSelectedPosition = currentSelectedPosition;

        notifyDataSetChanged();

    }


    public void pressedOnClick(View v, int position) {

        if (v != null) {
            inter.buttonPressed(position);
        }
    }

    public interface AdapterInterface {
        public void buttonPressed(int position);
    }

    @NonNull
    @Override
    public EmployeeDisplayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_employee_layout, parent, false);
        return new EmployeeDisplayAdapter.MyViewHolder(view);

    }

    public Boolean getBtnVisibility(View view) {
        if (view.findViewById(R.id.checkbox).getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;
    }

    @Override
    public void onBindViewHolder(@NonNull final EmployeeDisplayAdapter.MyViewHolder holder, final int position) {

        employee = employees.get(position);

        final String first = employee.getFirst();
        final String last = employee.getLast();
        final String email = employee.getEmail();
        final String dob = employee.getDob();
        final String gender = employee.getGender();
        final String img = employee.getProfile();
        final int employeeID = employee.getId();

        holder.txt_empDisFirst.setText(first);
        holder.txt_empDisLast.setText(last);
        holder.txt_empDisEmail.setText(email);
        holder.txt_empDisDOB.setText(dob);
        holder.txt_empDisGender.setText(gender);

        Glide.with(context).load(img).into(holder.img_empDispProfile);

        final int pos = holder.getAdapterPosition();



        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // holder.checkbox.setVisibility(View.VISIBLE);
                pressedOnClick(v,pos);
            }
        });


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // holder.checkbox.setVisibility(View.VISIBLE);
            }
        });


        //for animation recyclerview
        if(position >lastPosition) {

            Animation animation = AnimationUtils.loadAnimation(context,
                    (position > lastPosition) ? R.anim.up_from_bottom
                            : R.anim.down_from_top);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }




    }



    @Override
    public int getItemCount() {
        return employees.size();
    }



        public class MyViewHolder extends RecyclerView.ViewHolder {
          private TextView txt_empDisFirst;
          private TextView txt_empDisLast;
          private TextView txt_empDisEmail;
          private TextView txt_empDisDOB;
          private TextView txt_empDisGender;
          private ImageView img_empDispProfile;
          private CheckBox checkbox;
          private CardView cardView;

            public MyViewHolder(View itemView) {
                super(itemView);
                txt_empDisFirst = itemView.findViewById(R.id.txt_empDisFirst);
                txt_empDisLast = itemView.findViewById(R.id.txt_empDisLast);
                txt_empDisEmail = itemView.findViewById(R.id.txt_empDisEmail);
                txt_empDisDOB = itemView.findViewById(R.id.txt_empDisDOB);
                txt_empDisGender = itemView.findViewById(R.id.txt_empDisGender);
                img_empDispProfile = itemView.findViewById(R.id.img_empDispProfile);
                checkbox = itemView.findViewById(R.id.checkbox);
                cardView=itemView.findViewById(R.id.cardView);
            }
        }


    }
