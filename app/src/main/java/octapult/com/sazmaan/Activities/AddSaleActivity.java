package octapult.com.sazmaan.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import octapult.com.sazmaan.Adapters.AddSaleAdapter;
import octapult.com.sazmaan.Network.EndPoints;
import octapult.com.sazmaan.Pojo.AddSalePojo;
import octapult.com.sazmaan.R;

public class AddSaleActivity extends AppCompatActivity {


    String CatId,ProId;

    String productName,productQuantity, totalRateString;
    String rate;

    String price;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    EditText etClientName, etContactNum;
    String quantity;



   public static List<String> quantityList;
    public static List<String> totalRateList;

    public static List<String> rateList;
    public static List<String> productIdList;

    HashMap<String, String> cat_map, prod_map;
    TextView dateData;
    int p;
    TextView etDate,tvTotalPrice;
    String regionKey, prodKey;
    int year, month, day;
    Button addNewSale;
    Button btnProceed;


    Spinner spCategories, spproducts;
    List<String> spinnerDataCatList, prodDataList;
    EditText etQuantity;
    AddSaleAdapter adapter;
    Calendar calendar;
    Toolbar toolbar;
    List<String> prodIdList;

    public static int acuratePrice = 0;

    List<AddSalePojo> addSalePojo;
    RecyclerView rvAddSale;
    TextView tvPrice;
    String proId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);
        quantityList = new ArrayList<>();
        totalRateList = new ArrayList<>();
        rateList =new ArrayList<>();
        productIdList = new ArrayList<>();


        tvPrice = (TextView) findViewById(R.id.tvPrice);

        spinnerDataCatList = new ArrayList<>();

        addSalePojo = new ArrayList<>();
        cat_map = new HashMap<>();
        prod_map = new HashMap<>();
        prodDataList = new ArrayList<>();

        tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
        toolbar = (Toolbar) findViewById(R.id.appBar);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        btnProceed = (Button) findViewById(R.id.btnProceed);
        toolbar.setTitle("Add Sale");
        dateData = (TextView) findViewById(R.id.dateData);
        addNewSale = (Button) findViewById(R.id.addSale);
        rvAddSale = (RecyclerView) findViewById(R.id.rvProducts);
        rvAddSale.setNestedScrollingEnabled(false);


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);







        etQuantity = (EditText) findViewById(R.id.etQuantity);


        spCategories = (Spinner) findViewById(R.id.getCategory);
        spproducts = (Spinner) findViewById(R.id.getProducts);
        getCategories();


        spCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getProductsData(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spproducts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getSelectedProductData(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        etDate = (TextView) findViewById(R.id.etDate);
        etClientName = (EditText) findViewById(R.id.etClientName);
        etContactNum = (EditText) findViewById(R.id.etContactNumber);


        addNewSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spCategories.getSelectedItem().toString()!= null && spproducts.getSelectedItem().toString()!= null && p!=0 &&etQuantity.getText().toString()!="0" &&etQuantity.getText().toString()!="0") {

                    acuratePrice = acuratePrice  + p;
                    quantityList.add(etQuantity.getText().toString());
                    totalRateList.add(tvTotalPrice.getText().toString());
                    rateList.add(tvPrice.getText().toString());
                    productIdList.add(proId);

                    Toast.makeText(getApplicationContext(), "Sale Added Scroll Down to proceed", Toast.LENGTH_SHORT).show();
                    btnProceed.setVisibility(View.VISIBLE);
                    AddSalePojo addSale = new AddSalePojo();
                    addSale.setCategory(spCategories.getSelectedItem().toString());
                    addSale.setCategoryId(CatId);
                    addSale.setProdId(ProId);
                    addSale.setPrice(tvPrice.getText().toString());
                    addSale.setProd(spproducts.getSelectedItem().toString());
                    addSale.setTotal(String.valueOf(p));
                    addSale.setDate(etDate.getText().toString());
                     addSale.setProdId(proId);
                    addSale.setQuantity(etQuantity.getText().toString());

                    addSalePojo.add(addSale);
                    rvAddSale.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter = new AddSaleAdapter(getApplicationContext(), addSalePojo);
                    rvAddSale.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
                else {

                    Toast.makeText(getApplicationContext(),"Enter complete Data",Toast.LENGTH_SHORT).show();

                }


            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DATE);


                final DatePickerDialog datePickerDialog = new DatePickerDialog(AddSaleActivity.this,
                        new DatePickerDialog.OnDateSetListener() {


                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dateData.setVisibility(View.VISIBLE);
                                dateData.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();


            }
        });


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.openMenu:
                        drawerLayout.openDrawer(Gravity.RIGHT);
                        break;

                }


                return true;
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case (R.id.dashboard):
                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(intent);

                        break;

                    case R.id.addSale:
                        drawerLayout.closeDrawer(Gravity.END);
                        // Toast.makeText(getApplicationContext(),"Add Sale", Toast.LENGTH_SHORT).show();
                        Intent addSaleIntent = new Intent(getApplicationContext(), AddSaleActivity.class);
                        startActivity(addSaleIntent);
                        break;

                    case R.id.graph:
                        drawerLayout.closeDrawer(Gravity.END);
                        // Toast.makeText(getApplicationContext(),"Add Sale", Toast.LENGTH_SHORT).show();
                        Intent graphIntent = new Intent(getApplicationContext(), GraphActivity.class);
                        startActivity(graphIntent);

                        break;
                    case R.id.salesReport:
                        //    drawerLayout.closeDrawer(Gravity.END);
                        //   Toast.makeText(getApplicationContext(),"Add Sale", Toast.LENGTH_SHORT).show();
                        Intent salesReportIntent = new Intent(getApplicationContext(), SalesReportActivity.class);
                        startActivity(salesReportIntent);

                        break;



                }


                return true;
            }
        });


        etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s!="null" &&s.length()>0 && Integer.valueOf(String.valueOf(s))<=Integer.valueOf(quantity) ) {
                             p = Integer.valueOf(String.valueOf(s)) * Integer.valueOf(tvPrice.getText().toString());

                        }
                        else {
                            Toast.makeText(AddSaleActivity.this, "only " +quantity + " are available"  , Toast.LENGTH_SHORT).show();
                         //   tvTotalPrice.setText("0");
                            p = 0;
                        }


                tvTotalPrice.setText(String.valueOf(p));

                        }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            productQuantity =    android.text.TextUtils.join(",", quantityList);
                totalRateString = android.text.TextUtils.join(",", totalRateList);
            rate = android.text.TextUtils.join(",",rateList);
                String str = android.text.TextUtils.join(",",productIdList);


               Toast.makeText(getApplicationContext(),String.valueOf(acuratePrice),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),BillingActivity.class);

                intent.putExtra("clientName",etClientName.getText().toString());
                intent.putExtra("clientNumber",etContactNum.getText().toString());
                intent.putExtra("date",dateData.getText().toString());
                intent.putExtra("subAmount",String.valueOf(acuratePrice));
                intent.putExtra("productQuantity",productQuantity);
                intent.putExtra("productIdList",str);
                intent.putExtra("rates",rate);
                intent.putExtra("totalRateString",totalRateString);
                intent.putExtra("productName",str);



                startActivity(intent);
            }
        });



    }


    public void getCategories() {

        final ProgressDialog pd = new ProgressDialog(AddSaleActivity.this);
        pd.setMessage("loading");
        pd.show();

        StringRequest request = new StringRequest(Request.Method.POST, EndPoints.GET_ALL_CATEGORIES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pd.dismiss();
                try {
                    JSONArray arrr = new JSONArray(response);
                    // JSONObject object = new JSONObject(response);
                    //    String regions =   object.get("regions").toString();


                    //  JSONArray regionArray = object.getJSONArray("regions");

                    for (int i = 0; i < arrr.length(); i++) {

                        JSONObject obj = new JSONObject(arrr.getString(i));

                        String cat_id = obj.getString("categories_id");
                        String cat_name = obj.getString("categories_name");
                        //   regionMap.put(cat_id, lmn);
                        cat_map.put(cat_id, cat_name);
                        //regionsMapList.add(regionMap);
                        spinnerDataCatList.add(cat_name);
                        //   spinnerDataCountry.add(lmn);
                    }

                    ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(AddSaleActivity.this, R.layout.bg_spinner_item, spinnerDataCatList);

                    spCategories.setAdapter(regionAdapter);


                    // regionsMapList.add()


                    //   String status = object.get("success").toString();

                } catch (JSONException e) {
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
//                params.put("email", email);
//                params.put("password", password);
//
                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);


    }


    public void getProductsData(int pos) {


        for (HashMap.Entry<String, String> e : cat_map.entrySet()) {

            String key = e.getKey();
            String val = e.getValue();
            if (val == spCategories.getSelectedItem()) {

                regionKey = key;
                Toast.makeText(getApplicationContext(), key, Toast.LENGTH_SHORT).show();
               // prodKey = key;
                CatId = key;
                getProducts(key);
            }

        }


    }


    public void getProducts(final String Key) {

//
//        citiesMap.clear();
        prod_map.clear();
        //citiesMapList.clear();
        //spinnerDataCity.clear();
        prodDataList.clear();
        regionKey = Key;

        final ProgressDialog pd = new ProgressDialog(AddSaleActivity.this);
        pd.setMessage("loading");
        pd.show();


        StringRequest request = new StringRequest(Request.Method.POST, EndPoints.GET_PRODUCTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    pd.dismiss();
                    JSONArray arr = new JSONArray(response);
                    //  JSONObject object = new JSONObject(response);
                    //    String regions =   object.get("regions").toString();

                    //JSONArray CitiesArray = object.getJSONArray("cities");

                    for (int i = 0; i < arr.length(); i++) {

                        JSONObject obj = new JSONObject(arr.getString(i));

                        String abc = obj.getString("product_id");
                        String lmn = obj.getString("product_name");
                      //  String quantity = obj.getString("quantity");
                        //  citiesMap.put(abc, lmn);
                        prod_map.put(abc, lmn);
                        prod_map.put("prodId",Key);

                        //citiesMapList.add(citiesMap);
                        // spinnerDataCity.add(lmn);
                        prodDataList.add(lmn);
                        //  spinnerDataCountry.add(lmn);
                    }

                    ArrayAdapter<String> citiesAdapter = new ArrayAdapter<String>(AddSaleActivity.this, R.layout.bg_spinner_item, prodDataList);

                    spproducts.setAdapter(citiesAdapter);


                    // regionsMapList.add()


                    //  String status = object.get( "success").toString();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // object.get("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cat_id", regionKey);
//                params.put("password", password);
//
                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);


    }





    public void getSelectedProductData(int pos) {


        for (HashMap.Entry<String, String> e : prod_map.entrySet()) {

            String key = e.getKey();
            String val = e.getValue();
            if (val == spproducts.getSelectedItem()) {

                prodKey = key;
                ProId = key;

                Toast.makeText(getApplicationContext(), key, Toast.LENGTH_SHORT).show();
                getProdDetails(key);
            }

        }


    }

        public void getProdDetails(String Key) {

//
//        citiesMap.clear();
        //   prod_map.clear();
        //citiesMapList.clear();
        //spinnerDataCity.clear();
        // prodDataList.clear();
        // regionKey = Key;
        final String abK = Key;
            prodKey = Key;
            proId = Key;
        final ProgressDialog pd = new ProgressDialog(AddSaleActivity.this);
        pd.setMessage("loading");
        pd.show();


        StringRequest request = new StringRequest(Request.Method.POST, EndPoints.GET_PRODUCT_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    pd.dismiss();
                    JSONArray arr = new JSONArray(response);
                    //  JSONObject object = new JSONObject(response);
                    //    String regions =   object.get("regions").toString();

                    //JSONArray CitiesArray = object.getJSONArray("cities");

                    for (int i = 0; i < arr.length(); i++) {

                        JSONObject obj = new JSONObject(arr.getString(i));

                        price = obj.getString("rate");
                        quantity = obj.getString("quantity");

                        //  citiesMap.put(abc, lmn);
                        // prod_map.put(abc,lmn);
                        //citiesMapList.add(citiesMap);
                        // spinnerDataCity.add(lmn);
                        //prodDataList.add(lmn);
                        //  spinnerDataCountry.add(lmn);
                    }
                    tvPrice.setText(price);
//                    ArrayAdapter<String> citiesAdapter = new ArrayAdapter<String>(AddSaleActivity.this, R.layout.bg_spinner_item, prodDataList);
//
//                    spproducts.setAdapter(citiesAdapter);





                    // regionsMapList.add()


                    //  String status = object.get( "success").toString();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // object.get("");

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
                params.put("prod_id", abK);
//                params.put("password", password);
//
                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);


    }






}
