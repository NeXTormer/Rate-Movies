package me.holz.ratemovies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import me.holz.ratemovies.util.SHA512;
import me.holz.ratemovies.util.loadJSON;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Register");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final EditText username1 = findViewById(R.id.username);
        final EditText password1 = findViewById(R.id.password1);
        final EditText password2 = findViewById(R.id.password2);

        Button register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw1 = password1.getText().toString();
                String pw2 = password2.getText().toString();
                if(!pw1.equals(pw2))
                {
                    Snackbar.make(v, "The passwords don't match!", Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    String hash = SHA512.get_SHA_512_SecurePassword(pw1, SHA512.SALT);
                    String username = username1.getText().toString();

                    loadJSON lj = new loadJSON("http://faoiltiarna.ddns.net:4443/ratemovies/register/" + username + "/" + hash);
                    lj.thread.start();
                    try {
                        lj.thread.join();
                        JSONObject jo = new JSONObject(lj.result);
                        String msg = jo.getString("msg");

                        if(msg.equals("Success"))
                        {
                            onBackPressed();
                            return;
                        }
                        else
                        {
                            Snackbar.make(v, msg, Snackbar.LENGTH_LONG).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }

}
