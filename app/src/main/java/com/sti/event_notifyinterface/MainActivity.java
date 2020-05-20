package com.sti.event_notifyinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerFragment.TimePickerListener {
    DatabaseReference dbref,check_dbref,insert_ref;
    Spinner sp_dept, sp_sem, sp_sec;
    ImageButton add_sem, add_sec,add_date,add_time;
    Button btn_next, btn_post;
    EditText txt_event_name,txt_event_description,txt_even_venue;
    TextView txt_date,txt_time;
    CardView layout_sem,layout_section,layout_event;

    String[] list_departments;
    String[] list_semesters;
    String[] list_sections;
    String sem="";
    String dept= "";
    String sec="";
    String ans="";
    Event_Name ev;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbref= FirebaseDatabase.getInstance().getReference().child("LoginStudent");

        ev= new Event_Name();
        
        txt_date= findViewById(R.id.date_of_event);
        txt_time= findViewById(R.id.time_of_event);
        
        add_date= findViewById(R.id.date_picker);
        add_time= findViewById(R.id.time_picker);
        
        txt_event_description= findViewById(R.id.description_of_event);
        txt_even_venue= findViewById(R.id.name_of_venue);

        sp_dept= findViewById(R.id.dept);
        sp_sec = findViewById(R.id.section);
        sp_sem= findViewById(R.id.sem);

        add_sem= findViewById(R.id.addSem);
        add_sec= findViewById(R.id.addSec);

        btn_next= findViewById(R.id.next);
        btn_post= findViewById(R.id.post);

        txt_event_name= findViewById(R.id.name_of_event);

        layout_sem= findViewById(R.id.sem_layout);
        layout_section= findViewById(R.id.section_layout);
        layout_event= findViewById(R.id.event_layout);
        
        
        add_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDate();
            }
        });
        
        add_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTime();
            }
        });
        
        add_sem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_sem.setVisibility(View.VISIBLE);
            }

        });

        add_sec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_section.setVisibility(View.VISIBLE);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_event.setVisibility(View.VISIBLE);
            }
        });
        list_departments= getResources().getStringArray(R.array.departments);
        list_semesters= getResources().getStringArray(R.array.semesters);
        list_sections= getResources().getStringArray(R.array.section);

        ArrayAdapter<String> adapter_dept= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,list_departments);
        ArrayAdapter<String> adapter_semesters= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,list_semesters);
        ArrayAdapter<String> adapter_sections= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,list_sections);

        adapter_dept.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        adapter_semesters.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        adapter_sections.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        sp_dept.setAdapter(adapter_dept);
        sp_sem.setAdapter(adapter_semesters);
        sp_sec.setAdapter(adapter_sections);

        sp_dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dept=list_departments[i];

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        sp_sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sem= list_semesters[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_sec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sec= list_sections[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventName= txt_event_name.getText().toString();
                String eventTitle= txt_event_name.getText().toString();
                String eventVenue= txt_even_venue.getText().toString();
                String eventDate= txt_date.getText().toString();
                String eventTime= txt_time.getText().toString();
                ans= dept+sem+sec;
                verify(ans,eventName,eventTitle,eventDate,eventTime,eventVenue);
            }
        });



    }

    private void addTime() {
        DialogFragment timePickerFragment= new TimePickerFragment();
        timePickerFragment.setCancelable(false);
        timePickerFragment.show(getSupportFragmentManager(),"Pick time of event");
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String d= ""+i2+":"+i1+":"+i;
        txt_date.setText(d);
    }

    private void addDate()
    {
        DatePickerDialog datePickerDialog= new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.YEAR)
        );
        datePickerDialog.show();
    }


    public void verify(String ans, final String event_descr, final String event_Title,final String event_Date,final String event_Time,final String event_Venue) {

        check_dbref= FirebaseDatabase.getInstance().getReference().child(ans);
        check_dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    final String check= child.getKey();
                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot child: dataSnapshot.getChildren())
                            {
                                uid= child.getKey();
//                                Toast.makeText(MainActivity.this, "UID: "+uid+" and CSE 4B ID is: "+check, Toast.LENGTH_SHORT).show();
                                if(check.equals(uid))
                                {
                                    insert_ref= dbref.child(uid).child("CollegeEvent");
                                    ev.setTitle(event_Title);
                                    ev.setReminderSet(1);
                                    ev.setDescription(event_descr);
                                    ev.setEventDate(event_Date);
                                    ev.setEventTime(event_Time);
                                    ev.setVenue(event_Venue);
                                    insert_ref.push().setValue(ev);
                                    Toast.makeText(MainActivity.this, "Successfully Posted!!! with ID: "+uid, Toast.LENGTH_SHORT).show();
                                    break;
                                }
//                                    Toast.makeText(MainActivity.this, "UID Selected is: "+uid, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min) {
        txt_time.setText(hour+":"+min);
    }
}
