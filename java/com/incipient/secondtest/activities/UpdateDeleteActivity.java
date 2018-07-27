package com.incipient.secondtest.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.incipient.secondtest.R;
import com.incipient.secondtest.dao.EmployeeDao;
import com.incipient.secondtest.fragments.HomeFragment;
import com.incipient.secondtest.pojo.Employee;

import java.util.List;

public class UpdateDeleteActivity extends AppCompatActivity  implements View.OnClickListener {


    private List<Employee> employee1;
    private EditText edt_eShowName, edt_eShowLast, edt_eShowGender, edt_eShowDob,edt_eShowEmail;
    private ImageView img_eShowProfile;
    private Button btn_eUpdate, btn_eDel;
    private Context context;
    private int employeeID;
    private Employee employee;
    private String strFname, strLname, strEmail, strDOB,strGender, strIMG;
    private String strUpdateFname,strUpdateLname,strUpdateEmail,strUpdateDOB,strUpdateGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_delete);

        context = this;

        try {
            init();
            employee = new Employee();
            fetchData();
            bundleData();
            clickListener();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void bundleData()throws Exception {
        Bundle b = getIntent().getExtras();
        employeeID = b.getInt("ID");
        strFname = b.getString("NAME");
        strLname = b.getString("LAST");
        strEmail = b.getString("EMAIL");
        strDOB = b.getString("DOB");
        strGender = b.getString("GENDER");


        edt_eShowName.setText(strFname);
        edt_eShowLast.setText(strLname);
        edt_eShowEmail.setText(strEmail);
        edt_eShowDob.setText(strDOB);
        edt_eShowGender.setText(strGender);
        strIMG = b.getString("IMG");
        Glide.with(this).load(strIMG).into(img_eShowProfile);

    }

    private void clickListener() throws Exception {
        btn_eUpdate.setOnClickListener(this);
        btn_eDel.setOnClickListener(this);

    }

    private void init() throws Exception {
        edt_eShowName = findViewById(R.id.edt_eShowName);
        edt_eShowLast = findViewById(R.id.edt_eShowLast);
        edt_eShowEmail = findViewById(R.id.edt_eShowEmail);
        edt_eShowDob = findViewById(R.id.edt_eShowDob);
        edt_eShowGender = findViewById(R.id.edt_eShowGender);
        img_eShowProfile = findViewById(R.id.img_eShowProfile);
        btn_eUpdate = findViewById(R.id.btn_eUpdate);
        btn_eDel = findViewById(R.id.btn_eDel);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_eUpdate:

                try {
                    updateOpertion();
                    fetchData();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btn_eDel:

                EmployeeDao employeeDao1=new EmployeeDao();
                int id=employeeID;
                employeeDao1.deleteData(context,id);
                try {
                    Log.e("jj1","jj");

                    fetchData();
                    finish();
                    Log.e("jj","jj");

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }

    }

    private void updateOpertion() throws Exception {
        EmployeeDao employeeDao = new EmployeeDao();

        // Log.e("hii", "dfdf" + edt_eShowName.getText().toString());

        strUpdateFname = edt_eShowName.getText().toString();
        strUpdateLname = edt_eShowLast.getText().toString();
        strUpdateEmail = edt_eShowEmail.getText().toString();
        strUpdateDOB = edt_eShowDob.getText().toString();
        strUpdateGender = edt_eShowGender.getText().toString();

        employee.setId(employeeID);
        employee.setFirst(strUpdateFname);
        employee.setLast(strUpdateLname);
        employee.setEmail(strUpdateEmail);
        employee.setDob(strUpdateDOB);
        employee.setGender(strUpdateGender);
        employee.setProfile(strIMG);


        employeeDao.UpdataData(context, employee);


    }

    private void fetchData() throws Exception {
        EmployeeDao employeeDao = new EmployeeDao();
        employee1 = employeeDao.selectData(context);

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            updateOpertion();
            fetchData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
