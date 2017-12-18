package octapult.com.sazmaan.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import octapult.com.sazmaan.Network.EndPoints;
import octapult.com.sazmaan.R;

public class BillingActivity extends AppCompatActivity {

    TextView tvGst,tvSubAmount,tvTotalAmount,tvGrandTotal,tvDueAmount;
    String total,quantity,totalValue,rateVale;
    Spinner paymentTypeSpinner, paymentStatusSpinner;

    String gstS;

    List<String> paymentTypeList;
    List<String> paymentStatusList;



    Button btnBilling;
    EditText etPaidAmount;
    String gst;

    String clientName,clientNumber,date,productQuantity,rates,totalRateString,productName;
//    int gstValue;
    float gstValue;

    float  tt,totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

         gstS = getGST();

        paymentStatusSpinner = (Spinner) findViewById(R.id.paymentStatus);
        tvGst = (TextView) findViewById(R.id.tvGst);
        tvSubAmount = (TextView) findViewById(R.id.tvSubAmount);
        etPaidAmount = (EditText) findViewById(R.id.etPaidAmount);
        tvGrandTotal = (TextView) findViewById(R.id.tvGrandTotal);
        tvDueAmount = (TextView) findViewById(R.id.tvDueAmount);
        tvTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);
        paymentTypeSpinner = (Spinner) findViewById(R.id.paymentType);


        btnBilling = (Button) findViewById(R.id.btnBilling);

        paymentTypeList = new ArrayList<>();
        paymentTypeList.add("Cash");
        paymentTypeList.add("Cheque");
        paymentTypeList.add("Credit Card");
        paymentStatusList = new ArrayList<>();
        paymentStatusList.add("Full Payment");
        paymentStatusList.add("Advance Payment");
        paymentStatusList.add("No Payment");

        total = getIntent().getExtras().getString("subAmount");
        productName = getIntent().getExtras().getString("productName");
        totalRateString =getIntent().getExtras().getString("totalRateString");
        rates = getIntent().getExtras().getString("rates");
        productQuantity =getIntent().getExtras().getString("productQuantity");
        date = getIntent().getExtras().getString("date");
        clientNumber =  getIntent().getExtras().getString("clientNumber");
        clientName =  getIntent().getExtras().getString("clientName");
        tvSubAmount.setText(total);
        ////////////////////////////////////////////////////////////////////
        ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(BillingActivity.this, R.layout.bg_spinner_item, paymentTypeList);

        paymentTypeSpinner.setAdapter(regionAdapter);

        ArrayAdapter<String> paymentStatusAdapter = new ArrayAdapter<String>(BillingActivity.this,R.layout.bg_spinner_item,paymentStatusList);
        paymentStatusSpinner.setAdapter(paymentStatusAdapter);

        etPaidAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

              tvDueAmount.setText(String.valueOf(totalAmount-Integer.valueOf(String.valueOf( s))));



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btnBilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                postData();



            }
        });



    }


    public String getGST() {

        final ProgressDialog pd = new ProgressDialog(BillingActivity.this);
        pd.setMessage("loading");
        pd.show();

        StringRequest request = new StringRequest(Request.Method.POST, EndPoints.GET_GST_PERCENTAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    pd.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    gst = object.getString("value");
                    tvGst.setText( gst);


                   // gstValue =Integer.valueOf(gst );
//                    gstValue = Integer.parseInt(gst);
//
                    gstValue = Float.valueOf(gst);
                    //   tt = gstValue/100 * Integer.valueOf(total);

                    tt = gstValue/100 * Integer.parseInt(total);

                    totalAmount = Integer.valueOf(total) + tt;
                    tvTotalAmount.setText(String.valueOf(totalAmount));
                    tvGrandTotal.setText(String.valueOf(totalAmount));





                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();

                Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);


            return gst;

    }



        public void postData(){


            final ProgressDialog pd = new ProgressDialog(BillingActivity.this);
            pd.setMessage("loading");
            pd.show();

            StringRequest request = new StringRequest(Request.Method.POST, EndPoints.CREATE_ORDER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // object.get("");

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pd.dismiss();
                    //  Log.e("Error",error.printStackTrace());
                    Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();

                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("orderDate", date);
                    params.put("clientName", clientName);

                    params.put("clientContact", clientNumber);
                    params.put("subTotalValue",String.valueOf(totalAmount));
                    params.put("gstValue",String.valueOf( gstValue));
                    params.put("totalAmountValue", String.valueOf(totalAmount));
                    params.put("grandTotalValue", String.valueOf(totalAmount));
                    params.put("dueValue", tvDueAmount.getText().toString());
                    params.put("paid", etPaidAmount.getText().toString());
                    params.put("paymentType", paymentTypeSpinner.getSelectedItem().toString());
                    params.put("paymentStatus", paymentStatusSpinner.getSelectedItem().toString());
                    params.put("productName", productName);
                    params.put("quantity", productQuantity);
                    params.put("rateValue", rates);
                    params.put("totalValue",totalRateString);
                    //    params.put("password", password);


                    return params;
                }


            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);








        }







}
