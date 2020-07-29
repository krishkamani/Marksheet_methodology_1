package com.example.marksheet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText srollno,sname,ssem;
    private Button ssubmit,next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        srollno=findViewById(R.id.student_rollno);
        sname=findViewById(R.id.student_name);
        ssem=findViewById(R.id.student_semester);
        ssubmit=findViewById(R.id.student_submit_button);
        next=findViewById(R.id.next_button);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextintent=new Intent(MainActivity.this,CeMarksActivity.class);
                startActivity(nextintent);
            }
        });

        ssubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roll=srollno.getText().toString();
                String name=sname.getText().toString();
                String sem=ssem.getText().toString();

                if(TextUtils.isEmpty(roll) || TextUtils.isEmpty(name) || TextUtils.isEmpty(sem))
                {
                    Toast.makeText(MainActivity.this,"Please enter all fields...",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MainActivity.this,"Please enter valid roll number",Toast.LENGTH_SHORT).show();
                    }
                    if(k==0)
                    {
                        int semnumber=Integer.parseInt(sem);
                        if(semnumber<=0 || semnumber>8)
                        {
                            Toast.makeText(MainActivity.this,"Please enter valid semester",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (semnumber == 1) sem = "I";
                            else if (semnumber == 2) sem = "II";
                            else if (semnumber == 3) sem = "III";
                            else if (semnumber == 4) sem = "IV";
                            else if (semnumber == 5) sem = "V";
                            else if (semnumber == 6) sem = "VI";
                            else if (semnumber == 7) sem = "VII";
                            else sem = "VIII";

                            DBHelper dbHelper=new DBHelper(MainActivity.this);
                            dbHelper.open();
                            try {
                                double x=dbHelper.insertdata(roll,name,sem);
                                x=x+1;
                                Toast.makeText(MainActivity.this,"Data inserted",Toast.LENGTH_SHORT).show();

                                srollno.setText("");sname.setText("");ssem.setText("");
                            }catch (Exception e)
                            {
                                Toast.makeText(MainActivity.this,"Data is not inserted",Toast.LENGTH_SHORT).show();
                            }
                            dbHelper.close();
                        }
                    }

                }
            }
        });
    }
}
