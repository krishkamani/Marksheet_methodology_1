package com.example.marksheet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CeMarksActivity extends AppCompatActivity {

    private EditText rollno,classtest,sessional,assignment;
    private Button submit,previous,show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ce_marks);

        rollno=findViewById(R.id.ce_rollno);
        classtest=findViewById(R.id.ce_classtest);
        sessional=findViewById(R.id.ce_sessional);
        assignment=findViewById(R.id.ce_assignment);
        submit=findViewById(R.id.submit_ce_marks);
        previous=findViewById(R.id.previous_button);
        show=findViewById(R.id.show_marks);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showintent=new Intent(CeMarksActivity.this,showActivity.class);
                startActivity(showintent);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent previousintent=new Intent(CeMarksActivity.this,MainActivity.class);
                startActivity(previousintent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String roll=rollno.getText().toString();
                String classtestmarks=classtest.getText().toString();
                String sessionalmarks=sessional.getText().toString();
                String assignmentmarks=assignment.getText().toString();

                if(TextUtils.isEmpty(roll) || TextUtils.isEmpty(classtestmarks) || TextUtils.isEmpty(sessionalmarks) || TextUtils.isEmpty(assignmentmarks))
                {
                    Toast.makeText(CeMarksActivity.this,"Please enter all fields..",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int k=0;
                    try {
                        int checkroll=Integer.parseInt(roll.substring(roll.length()-3));
                        String checkmiddle=roll.substring(roll.length()-6,roll.length()-3);

                    }catch (Exception e)
                    {
                        k=1;
                        Toast.makeText(CeMarksActivity.this,"Please enter valid roll number",Toast.LENGTH_SHORT).show();
                    }
                    if(k==0)
                    {
                            DBHelper dbHelper=new DBHelper(CeMarksActivity.this);
                            dbHelper.open();
                            String sql="select * from StudentDetails";
                            Cursor c=dbHelper.getstudentdata(sql);
                            try {
                                c.moveToFirst();
                                int check=0;
                                for(int i=0;i<c.getCount();i++)
                                {
                                    String a=c.getString(c.getColumnIndex("RollNumber"));
                                    if(a.equals(roll))
                                    {
                                        check=1;
                                        break;
                                    }
                                    c.moveToNext();
                                }
                                c.close();

                                if(check==0)
                                {
                                    Toast.makeText(CeMarksActivity.this,"Please first enter data in student detail..",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    try {

                                        double y=dbHelper.insertmarksdata(roll,Integer.parseInt(classtestmarks),Integer.parseInt(sessionalmarks),
                                                Integer.parseInt(assignmentmarks));
                                        dbHelper.close();
                                        y=y+1;
                                        Toast.makeText(CeMarksActivity.this,"Marks inserted",Toast.LENGTH_SHORT).show();
                                    }catch (Exception e)
                                    {
                                        dbHelper.close();
                                        Toast.makeText(CeMarksActivity.this,"Marks not inserted",Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }catch (Exception e)
                            {
                                c.close();
                                Toast.makeText(CeMarksActivity.this,"Please first enter data in student detail..",Toast.LENGTH_SHORT).show();
                            }
                    }
                }
            }
        });

    }
}
