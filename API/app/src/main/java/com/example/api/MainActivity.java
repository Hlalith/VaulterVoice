package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;


import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    //Global variables
    TextView text;
    Button button;
    JSONObject json = new JSONObject();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reference text
        text = findViewById(R.id.textview);

        //reference button
        button = findViewById(R.id.button2);

        // Add data to json object
        try {
            json.accumulate("action", "details");
            json.accumulate("nick_name", "sandeep");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Fetch data when button is clicked
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Fetch data from URL
                AndroidNetworking.post("http://events.respark.iitm.ac.in:5000/rp_bank_api")
                        .addJSONObjectBody(json)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    //Display data to User
                                    text.setText(response.getString("upi_id"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                // handle error
                            }
                        });
            }
        });
    }
}
