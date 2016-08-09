package com.mtn.info.finaldb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    String Name;
    String Location;
    String PhoneNumber;


    SQLiteDatabase db;

    TableRow tableRow;
    TextView textView, textView1, textView2, textView3, textView4, textView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            db = openOrCreateDatabase("MTNDB", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS Users(Name VARCHAR,Location VARCHAR,PhoneNumber VARCHAR);");
        }

       // Add data to the database. 
        public void Add_data(View view) {
            EditText edit_text1 = (EditText) findViewById(R.id.Name);
            EditText edit_text2 = (EditText) findViewById(R.id.Location);
            EditText edit_text3 = (EditText) findViewById(R.id.PhoneNumber);

            Name = edit_text1.getText().toString();
            Location = edit_text2.getText().toString();
            PhoneNumber = edit_text3.getText().toString();

            db.execSQL("INSERT INTO Users VALUES('" + Name + "','" + Location + "','" + PhoneNumber + "');");

            Log.i("Send SMS", "");

            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(PhoneNumber, null, Name, null, null);
                Toast.makeText(getApplicationContext(), "SMS Notification sent to " + Name,
                        Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "SMS Failed, please try again.",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        }

        public void show_data(View view) {
            Cursor c = db.rawQuery("SELECT * from Users", null);
            int count = c.getCount();
            c.moveToFirst();

            TableLayout tableLayout = new TableLayout(getApplicationContext());
            tableLayout.setVerticalScrollBarEnabled(true);

            tableRow = new TableRow(getApplicationContext());

            textView = new TextView(getApplicationContext());
            textView.setText("Name");
            textView.setTextColor(Color.RED);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setPadding(20, 20, 20, 20);
            tableRow.addView(textView);

            textView4 = new TextView(getApplicationContext());
            textView4.setText("Location");
            textView4.setTextColor(Color.RED);
            textView4.setTypeface(null, Typeface.BOLD);
            textView4.setPadding(20, 20, 20, 20);
            tableRow.addView(textView4);

            textView5 = new TextView(getApplicationContext());
            textView5.setText("Phone Number");
            textView5.setTextColor(Color.RED);
            textView5.setTypeface(null, Typeface.BOLD);
            textView5.setPadding(20, 20, 20, 20);
            tableRow.addView(textView5);


            tableLayout.addView(tableRow);

            for (Integer j = 0; j < count; j++) {
                tableRow = new TableRow(getApplicationContext());

                textView1 = new TextView(getApplicationContext());
                textView1.setText(c.getString(c.getColumnIndex("Name")));
                textView1.setTextColor(Color.BLACK);


                textView2 = new TextView(getApplicationContext());
                textView2.setText(c.getString(c.getColumnIndex("Location")));
                textView2.setTextColor(Color.BLACK);


                textView3 = new TextView(getApplicationContext());
                textView3.setText(c.getString(c.getColumnIndex("PhoneNumber")));
                textView3.setTextColor(Color.BLACK);


                textView1.setPadding(20, 20, 20, 20);
                textView2.setPadding(20, 20, 20, 20);
                textView3.setPadding(20, 20, 20, 20);

                tableRow.addView(textView1);
                tableRow.addView(textView2);
                tableRow.addView(textView3);

                tableLayout.addView(tableRow);
                c.moveToNext();


            }
            setContentView(tableLayout);
            db.close();
        }

        public void close(View view) {
            System.exit(0);
        }

}

