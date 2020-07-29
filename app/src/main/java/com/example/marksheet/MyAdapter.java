package com.example.marksheet;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    List<Data> detail = new ArrayList<>();

    public class Data{
        String rollno,name,semester;
        int classtest,sessional,assignment,total;
    }
    DBHelper dbHelper;
    public MyAdapter(Context applicationContext) {
        context=applicationContext;
        dbHelper=new DBHelper(context);
        inflter = (LayoutInflater.from(applicationContext));
        dbHelper.open();
        String sql="select * from StudentDetails";
        Cursor c1=dbHelper.getstudentdata(sql);
        c1.moveToFirst();
        for(int i=0;i<c1.getCount();i++)
        {
            Data ab=new Data();
            ab.rollno=c1.getString(c1.getColumnIndex("RollNumber"));
            String l=c1.getString(c1.getColumnIndex("RollNumber"));
            ab.name=c1.getString(c1.getColumnIndex("Name"));
            ab.semester=c1.getString(c1.getColumnIndex("Semester"));


            String sql1="select * from CeComponent";
            Cursor c=dbHelper.getstudentmarks(sql1);
            c.moveToFirst();
            for(int j=0;j<c.getCount();j++)
            {
                if(c.getString(c.getColumnIndex("RollNumber")).equals(l))
                {
                    ab.classtest = c.getInt(c.getColumnIndex("classtest"));
                    ab.sessional = c.getInt(c.getColumnIndex("Sessional"));
                    ab.assignment = c.getInt(c.getColumnIndex("Assignment"));
                    ab.total = c.getInt(c.getColumnIndex("classtest")) + c.getInt(c.getColumnIndex("Sessional")) +
                            c.getInt(c.getColumnIndex("Assignment"));
                    break;
                }
                c.moveToNext();
            }
            c.close();
            detail.add(ab);
            c1.moveToNext();
        }
        c1.close();


        dbHelper.close();

    }

    @Override
    public int getCount() {
        return detail.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.raw_layout, null);

        TextView roll=convertView.findViewById(R.id.rollnot1);
        TextView nam=convertView.findViewById(R.id.namet2);
        TextView semes=convertView.findViewById(R.id.semestert6);
        TextView classtest=convertView.findViewById(R.id.classtestt4);
        TextView sessional=convertView.findViewById(R.id.sessionalt5);
        TextView assignment=convertView.findViewById(R.id.assignmentt7);
        TextView total=convertView.findViewById(R.id.Totalt8);

        roll.setText(detail.get(position).rollno);
        nam.setText(detail.get(position).name);
        semes.setText(detail.get(position).semester);
        classtest.setText(String.valueOf(detail.get(position).classtest));
        sessional.setText(String.valueOf(detail.get(position).sessional));
        assignment.setText(String.valueOf(detail.get(position).assignment));
        total.setText(String.valueOf(detail.get(position).total));
        return convertView;
    }
}
