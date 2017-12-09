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
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import octapult.com.sazmaan.Network.EndPoints;
import octapult.com.sazmaan.R;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    EditText etEmail,etUsername,etPassword;
    String email,password,username;
    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = (EditText) findViewById(R.id.etemail);
        etPassword = (EditText) findViewById(R.id.etpassword);
        etUsername = (EditText) findViewById(R.id.etUsername);
        btnLogin = (Button) findViewById(R.id.btnSignup);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                username = etUsername.getText().toString();
                if (email.length()==0 || password.length()==0 || username.length()==0){
                    Toast.makeText(getApplicationContext(),"Email , Password or username are missing",Toast.LENGTH_SHORT).show();
                }
                else {
                    Signup();

                }
                }

        });




    }


    public void Signup()
    {

        loading = ProgressDialog.show(MainActivity.this, "Please wait...", "Checking Credentails ...", false, false);

        StringRequest request = new StringRequest(Request.Method.POST, EndPoints.SIGN_UP, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                loading.dismiss();
                String checkCredentials=response.trim().toString();
                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
                if(checkCredentials.equals("username or email already exist")){
                   // Toast.makeText(MainActivity.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                            String user_id = jsonResponse.getString("user_id").toString();
                            String user_name = jsonResponse.get("username").toString();
                            String email = jsonResponse.get("email").toString();


                            // Toast.makeText(Login_Activity.this, response, Toast.LENGTH_LONG).show();

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("user_id", user_id);
                            editor.putString("user_name", user_name);
                            editor.putString("email", email);
                            editor.apply();
                            Intent intent = new Intent(MainActivity.this, Dashboard.class);
                            startActivity(intent);
                            finish();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

        }
                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                loading.dismiss();




            }


        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("userName", username );
                map.put("password", password);
                map.put("email",email);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);







        }
}





