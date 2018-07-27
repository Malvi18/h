package com.incipient.secondtest.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.incipient.secondtest.MainActivity;
import com.incipient.secondtest.R;
import com.incipient.secondtest.activities.UpdateDeleteActivity;
import com.incipient.secondtest.adapter.EmployeeDisplayAdapter;
import com.incipient.secondtest.dao.EmployeeDao;
import com.incipient.secondtest.pojo.Employee;
import com.incipient.secondtest.recyclerview.RecyclerItemClickListener;
import com.incipient.secondtest.recyclerview.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements View.OnClickListener, EmployeeDisplayAdapter.AdapterInterface {

    ActionMode mActionMode;
    boolean isMultiSelect = false;
    Menu context_menu;

    private final String URL = "";
    private Context context;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private Button btn_viewData;
    private EmployeeDao employeeDao;
    private ArrayList<Employee> employees;
    private EmployeeDisplayAdapter employeeDisplayAdapter;
    private RecyclerView recyclerView;
    private Employee employee;

    ArrayList<Employee> multiselect_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        try {
            initArray();
            context = getActivity();
            requestQueueData();
            init(view);
            clickListener();
            //clickListenerRecyc();
            click();
            getUserJson();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void initArray() throws Exception {
        employeeDao = new EmployeeDao();
        employees = new ArrayList<Employee>();
    }

    private void fetchData() throws Exception {
        EmployeeDao employeeDao = new EmployeeDao();
        employees = (ArrayList<Employee>) employeeDao.selectData(context);
        setMainRecyclerView();
    }

    private void setMainRecyclerView() throws Exception {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        employeeDisplayAdapter = new EmployeeDisplayAdapter(getActivity(), employees, HomeFragment.this);
        recyclerView.setAdapter(employeeDisplayAdapter);employeeDisplayAdapter = new EmployeeDisplayAdapter(getActivity(), employees, HomeFragment.this);
        recyclerView.setAdapter(employeeDisplayAdapter);
        employeeDisplayAdapter.notifyDataSetChanged();
    }

    private void clickListener() throws Exception {

        btn_viewData.setOnClickListener(this);
    }

    private void getUserJson() throws Exception {
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Log.e("resp","jj"+response.toString());
                employeeDao.deleteDatabase(context);

                JSONArray jsonArray = response.optJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("name");

                    String gender = jsonObject.optString("gender");

                    String first = jsonObject1.optString("first");
                    String last = jsonObject1.optString("last");

                    String email = jsonObject.optString("email");


                    JSONObject jsonObject2 = jsonObject.optJSONObject("dob");
                    String dob = jsonObject2.optString("date");

                    JSONObject jsonObject3 = jsonObject.optJSONObject("picture");
                    String pic = jsonObject3.optString("large");


                    int result = (int) employeeDao.insertData(getActivity(), first, last, email, dob, gender, pic);

                    if (result > 0) {
                        Snackbar.make(getView(), "Inserted", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        Snackbar.make(getView(), "Not Inserted", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void requestQueueData() throws Exception {
        requestQueue = Volley.newRequestQueue(getActivity());

    }

    private void init(View view) throws Exception {
        btn_viewData = view.findViewById(R.id.btn_viewData);
        recyclerView = view.findViewById(R.id.recyclerView);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.btn_viewData:

                try {
                    fetchData();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
        }
    }


    private void click() throws Exception {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new RecyclerItemClickListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                try {
                    passData(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

                CheckBox checkBox = view.findViewById(R.id.checkbox);

                //mActionMode = getActivity().startActionMode(mActionModeCallback);

                if (!isMultiSelect) {
                    multiselect_list = new ArrayList<Employee>();
                    isMultiSelect = true;

                    if (mActionMode == null) {
                        mActionMode = getActivity().startActionMode(mActionModeCallback);
                    }
                }

                if (mActionMode != null) {

                    if (multiselect_list.contains(employees.get(position))) {
                        multiselect_list.remove(employees.get(position));
                        checkBox.setVisibility(View.GONE);
                    } else {
                        multiselect_list.add(employees.get(position));
                        checkBox.setVisibility(View.VISIBLE);
                    }

                    if (multiselect_list.size() > 0)
                        mActionMode.setTitle("" + multiselect_list.size());
                    else
                        mActionMode.setTitle("");

                    //refreshAdapter();

                }
                multi_select(position);
               /*if (employeeDisplayAdapter.getBtnVisibility(view)){
                   employeeDisplayAdapter.pressedOnClick(view,position);
               }else {
                   employeeDisplayAdapter.showData(position);
               }*/


            }
        }));
    }

    /* private void clickListenerRecyc() throws Exception{
         recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
             @Override
             public void onItemClick(View view, int position) {



             }

             @Override
             public void onLongClick(View view, int position) {
                 Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();


             }
         }));
     }*/
    public void multi_select(int position) {

    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.multi_menu, menu);
            context_menu = menu;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    //  alertDialogHelper.showAlertDialog("","Delete Contact","DELETE","CANCEL",1,false);

                    EmployeeDao employeeDao = new EmployeeDao();

                    Log.e("delete", "delete");

                    for (int i = 0; i < multiselect_list.size(); i++) {

                        employeeDao.deleteData(getActivity(), multiselect_list.get(i).getId());

                        if (employees.contains(multiselect_list.get(i))) {

                            employees.remove(multiselect_list.get(i));
                            employeeDisplayAdapter.notifyDataSetChanged();

                            //employeeDisplayAdapter.getBtnVisibility(recyclerView);
                        }

                    }
                    multiselect_list = new ArrayList<>();
                    if (mActionMode != null) {
                        mActionMode.finish();
                    }
                    employeeDisplayAdapter = new EmployeeDisplayAdapter(getActivity(), employees, HomeFragment.this);
                    recyclerView.setAdapter(employeeDisplayAdapter);
                   /* try {
                        click();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    employeeDisplayAdapter.notifyDataSetChanged();


                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            isMultiSelect = false;
            multiselect_list = new ArrayList<Employee>();
            // refreshAdapter();
            employeeDisplayAdapter.notifyDataSetChanged();
        }
    };

    private void passData(int position) throws Exception {
        employee = employees.get(position);
        final String first = employee.getFirst();
        final String last = employee.getLast();
        final String email = employee.getEmail();
        final String dob = employee.getDob();
        final String gender = employee.getGender();
        final String img = employee.getProfile();
        final int employeeID = employee.getId();


        Intent intent = new Intent(context, UpdateDeleteActivity.class);
        Bundle bundle = new Bundle();

        bundle.putInt("ID", employeeID);
        bundle.putString("NAME", first);
        bundle.putString("LAST", last);
        bundle.putString("EMAIL", email);
        bundle.putString("DOB", dob);
        bundle.putString("GENDER", gender);

        bundle.putString("IMG", img);

        intent.putExtras(bundle);

        context.startActivity(intent);
    }

   /* private void viewData() {
        EmployeeDao employeeDao=new EmployeeDao();
        users=employeeDao.selectData(context);
        ArrayAdapter<Employee> adapter=new ArrayAdapter<Employee>(getActivity()
                ,android.R.layout.simple_list_item_1,users);
        lv.setAdapter(adapter);

    }*/

    @Override
    public void onResume() {
        super.onResume();
        employees.clear();
        employees.addAll(employeeDao.selectData(context));

        try {
            fetchData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        employeeDisplayAdapter.notifyDataSetChanged();
    }

    @Override
    public void buttonPressed(int position) {

    }
}