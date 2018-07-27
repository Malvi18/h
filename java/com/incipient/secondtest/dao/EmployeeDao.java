package com.incipient.secondtest.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.incipient.secondtest.helper.EmployeeHelper;
import com.incipient.secondtest.pojo.Employee;

import java.util.ArrayList;

public class EmployeeDao {



    public void deleteDatabase(Context context){
        EmployeeHelper employeeHelper=new EmployeeHelper(context);
        SQLiteDatabase db = employeeHelper.getWritableDatabase();
        db.delete(EmployeeHelper.TABLENAME,null,null);
        db.execSQL("DELETE from "+ EmployeeHelper.TABLENAME);

        db.close();
    }


    public long insertData(Context context,String first,String last,String email,String dob,String gender,String profile) {

        EmployeeHelper employeeHelper=new EmployeeHelper(context);
        SQLiteDatabase sqLiteOpenHelper=employeeHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(EmployeeHelper.FIRST,first);
        values.put(EmployeeHelper.LAST,last);
        values.put(EmployeeHelper.EMAIL,email);
        values.put(EmployeeHelper.DOB,dob);
        values.put(EmployeeHelper.GENDER,gender);
        values.put(EmployeeHelper.IMG,profile);

        long result = sqLiteOpenHelper.insert(EmployeeHelper.TABLENAME, null, values);

        return result;
    }

    public ArrayList<Employee> selectData(Context context) {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        EmployeeHelper employeeHelper=new EmployeeHelper(context);
        SQLiteDatabase sqLiteDatabase = employeeHelper.getReadableDatabase();
        String s = "SELECT * FROM " + EmployeeHelper.TABLENAME + "";

        Cursor cursor = sqLiteDatabase.rawQuery(s, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    Employee employee1 = new Employee();
                    employee1.setId(cursor.getInt(0));
                    employee1.setFirst(cursor.getString(1));
                    employee1.setLast(cursor.getString(2));
                    employee1.setEmail(cursor.getString(3));
                    employee1.setDob(cursor.getString(4));
                    employee1.setGender(cursor.getString(5));
                    employee1.setProfile(cursor.getString(6));

                    employees.add(employee1);

                } while (cursor.moveToNext());

                cursor.close();
            } else {
                cursor.close();
                return new ArrayList<Employee>();
            }
        }
        return employees;
    }

   /* public int UpdataData(Context context, Employee employee) {
        EmployeeHelper employeeHelper = new EmployeeHelper(context);
        SQLiteDatabase sqLiteDatabase = employeeHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        //   values.put(EmployeeHelper);
        // int id=employee.getId();
        //values.put(EmployeeHelper.ID,id);
        values.put(EmployeeHelper.FIRST, employee.getFirst());
        values.put(EmployeeHelper.LAST, employee.getLast());
        values.put(EmployeeHelper.CITY, employee.getCity());
        values.put(EmployeeHelper.DOB, employee.getDob());
        values.put(EmployeeHelper.IMG, employee.getProfile());
        int result = sqLiteDatabase.update(EmployeeHelper.TABLENAME, values, EmployeeHelper.ID+"=?",
                new String[]{String.valueOf(employee.getId())});
        Log.e("result", "jj" + result);
        return result;
    }

    public void deleteData(Context context, int id) {
        EmployeeHelper employeeHelper = new EmployeeHelper(context);
        SQLiteDatabase sqLiteDatabase = employeeHelper.getWritableDatabase();
        sqLiteDatabase.delete(EmployeeHelper.TABLENAME, EmployeeHelper.ID+"=?", new String[]{id+""});
    }*/


    public int UpdataData(Context context, Employee employee) {
        EmployeeHelper employeeHelper = new EmployeeHelper(context);
        SQLiteDatabase sqLiteDatabase = employeeHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        //   values.put(EmployeeHelper);
        // int id=employee.getId();
        //values.put(EmployeeHelper.ID,id);
        values.put(EmployeeHelper.FIRST, employee.getFirst());
        values.put(EmployeeHelper.LAST, employee.getLast());
        values.put(EmployeeHelper.EMAIL, employee.getEmail());
        values.put(EmployeeHelper.DOB, employee.getDob());
        values.put(EmployeeHelper.GENDER, employee.getGender());
        values.put(EmployeeHelper.IMG, employee.getProfile());
        int result = sqLiteDatabase.update(EmployeeHelper.TABLENAME, values, EmployeeHelper.ID+"=?",
                new String[]{String.valueOf(employee.getId())});
        Log.e("result", "jj" + result);
        return result;
    }

    public void deleteData(Context context, int id) {
        EmployeeHelper employeeHelper = new EmployeeHelper(context);
        SQLiteDatabase sqLiteDatabase = employeeHelper.getWritableDatabase();
        sqLiteDatabase.delete(EmployeeHelper.TABLENAME, EmployeeHelper.ID+"=?", new String[]{id+""});
    }



}
