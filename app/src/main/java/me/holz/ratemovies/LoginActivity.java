package me.holz.ratemovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import me.holz.ratemovies.util.SHA512;
import me.holz.ratemovies.util.loadJSON;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);

        Button loginbutton = findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty())
                {

                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }


                    Snackbar.make(view, "Invalid input. Enter username and password.", Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    String url = "http://faoiltiarna.ddns.net:4443/ratemovies/login/" + username.getText().toString() + "/" + SHA512.get_SHA_512_SecurePassword(password.getText().toString(), SHA512.SALT);
                    loadJSON lj = new loadJSON(url);
                    lj.thread.start();
                    try {
                        lj.thread.join();

                        System.out.println(lj.result);
                        JSONObject jo = new JSONObject(lj.result);
                        Snackbar.make(view, jo.getString("msg") + ", Username: " + jo.getString("username"), Snackbar.LENGTH_LONG).show();

                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }

                        if(jo.getString("msg").equals("Success"))
                        {
                            SharedPreferences sharedPref = getSharedPreferences("key", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("apikey", jo.getString("apikey"));
                            editor.putString("username", jo.getString("username"));
                            editor.putInt("userid", jo.getInt("userid"));
                            editor.apply();

                            //onBackPressed();
                            Intent intent = new Intent(getApplicationContext(), MainView.class);
                            startActivity(intent);

                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);

                startActivity(intent);

            }
        });

    }

}
