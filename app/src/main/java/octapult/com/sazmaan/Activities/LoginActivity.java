package octapult.com.sazmaan.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import octapult.com.sazmaan.Network.EndPoints;
import octapult.com.sazmaan.R;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername,etPassword;
    Button btnLogin;
    String username,password;

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etpassword);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                if (username.length()==0 || password.length() == 0){


                    Toast.makeText(getApplicationContext(),"username and password are required",Toast.LENGTH_SHORT).show();

                }

                else{
                    Login();
                }



            }
        });


    }

    public void Login()
    {

        loading = ProgressDialog.show(LoginActivity.this, "Please wait...", "Checking Credentails ...", false, false);

        StringRequest request = new StringRequest(Request.Method.POST, EndPoints.LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
           // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                loading.dismiss();
                String checkCredentials=response.trim().toString();

                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        String user_id = jsonResponse.getString("userID");
//                        String user_name = jsonResponse.get("username").toString();
//                        String email = jsonResponse.get("email").toString();


                        // Toast.makeText(Login_Activity.this, response, Toast.LENGTH_LONG).show();

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("user_id", user_id);
//                        editor.putString("user_name", user_name);
//                        editor.putString("email", email);
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                        startActivity(intent);
                        finish();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



            }

        }
                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();




            }


        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("userName", username);
                map.put("password", password);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(request);







    }


}
