package octapult.com.sazmaan.Activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.allyants.boardview.BoardView;
import com.allyants.boardview.SimpleBoardAdapter;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rmondjone.locktableview.LockTableView;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import octapult.com.sazmaan.Adapters.SalesReportAdapter;
import octapult.com.sazmaan.Network.EndPoints;
import octapult.com.sazmaan.Pojo.SalesReportPojo;
import octapult.com.sazmaan.R;

public class SalesReportActivity extends AppCompatActivity {

    LinearLayout laStartDate,laEndDate;
    TextView tvStartDate,tvEndDate,tvGenerateExcel,plainText;
    Calendar calendar;
    ScrollView scView;
    Button btnGenerateReport;

   // Button btnGenerateExcel;

    ArrayList<String> titleList = new ArrayList<>();
    ArrayList<ArrayList<String>> myList = new ArrayList<>();



    RecyclerView rvSales,rvHeader;
    SalesReportAdapter adapter;
    Toolbar toolbar;
 //   LockTableView mLockTableView;
    List<SalesReportPojo> saleReportList,headerList;
    LinearLayout mContentView;
    ProgressDialog loading;
    int year,day,month;
    TextView tvTotaAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_report);

    //     list = new ArrayList<String>();


        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int grant = ContextCompat.checkSelfPermission(SalesReportActivity.this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(SalesReportActivity.this, permission_list, 1);
        }
        tvTotaAmount = (TextView) findViewById(R.id.tvTotaAmount);


        saleReportList = new ArrayList<>();
        headerList = new ArrayList<>();
        rvHeader = (RecyclerView) findViewById(R.id.rvHeader);
        tvGenerateExcel = (TextView) findViewById(R.id.tvGenerateExcel);
      //  mContentView = (LinearLayout) findViewById(R.id.contentView);
      //  btnGenerateExcel = (Button) findViewById(R.id.btnGenerateExcel);
        plainText= (TextView) findViewById(R.id.plainText);
     //   scView = (ScrollView) findViewById(R.id.scView);

        toolbar = (Toolbar) findViewById(R.id.appBar);
        toolbar.setTitle("Sales Report");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SalesReportActivity.super.onBackPressed();


            }
        });
        rvSales = (RecyclerView) findViewById(R.id.rvSalesData);


        laStartDate = (LinearLayout) findViewById(R.id.laStartDate);

        laEndDate =(LinearLayout) findViewById(R.id.laEndDate);

        tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        tvEndDate = (TextView) findViewById(R.id.tvEndDate);
        btnGenerateReport = (Button) findViewById(R.id.btnGenerateReport);


        laStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DATE);


                final DatePickerDialog datePickerDialog = new DatePickerDialog(SalesReportActivity.this,
                        new DatePickerDialog.OnDateSetListener() {


                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                tvStartDate.setVisibility(View.VISIBLE);
                                tvStartDate.setText((monthOfYear + 1)+"-"+ dayOfMonth  + "-" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();




            }
        });

        laEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DATE);


                final DatePickerDialog datePickerDialog = new DatePickerDialog(SalesReportActivity.this,
                        new DatePickerDialog.OnDateSetListener() {


                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                tvEndDate.setVisibility(View.VISIBLE);
                                tvEndDate.setText((monthOfYear + 1)+"-"+ dayOfMonth  + "-" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();



            }
        });


        btnGenerateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                loading = ProgressDialog.show(SalesReportActivity.this, "Please wait...", "Checking Credentails ...", false, false);


                StringRequest request = new StringRequest(Request.Method.POST, EndPoints.GET_SALES_DATA, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        String checkCredentials=response.trim().toString();
                     //   Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();

//                        HSSFWorkbook workbook = new HSSFWorkbook();
////                        HSSFSheet firstSheet = workbook.createSheet("Sheet No_1");
//                        ArrayList<String> billNo= new ArrayList<String>();
//                        ArrayList<String> clientName= new ArrayList<String>();
//                        ArrayList<String> clientContact= new ArrayList<String>();
//                        ArrayList<String> grandTotal= new ArrayList<String>();
//                        ArrayList<String> productName= new ArrayList<String>();
//                        ArrayList<String> totalSaleItem= new ArrayList<String>();
//                        ArrayList<String> GST= new ArrayList<String>();
//                        ArrayList<String> unitPrice= new ArrayList<String>();
//                        ArrayList<String> saleDate= new ArrayList<String>();

//                        titleList.add("Bill no");
//                        titleList.add("Sale Date");
//                        titleList.add("Client Name");
//                        titleList.add("Client Contact");
//
//                       // titleList.add("");
//                        titleList.add("Bill no");
//                        titleList.add("Bill no");
//                        titleList.add("Bill no");
//                        titleList.add("Bill no");
//
//                        myList.add(titleList);



                        try {

                                JSONObject jsonResponse = new JSONObject(response);
                                JSONObject resp = jsonResponse.getJSONObject("resp");
                                JSONObject data = jsonResponse.getJSONObject("data");
                                String totalAmount = jsonResponse.getString("totalAmount");
                                tvTotaAmount.setText(totalAmount);
                                int f = resp.length() -5;
                             //   saleReportList.add(0,new SalesReportPojo("Bill No","Sale Date","Client Name","Client Contact","Product Name"," Total Sale Item","GST 16%","Unit Price","Grand Total"));
                              headerList.add(new SalesReportPojo("Bill No","Sale Date","Client Name","Client Contact","Product Name"," Total Sale Item","GST 16%","Unit Price","Grand Total"));
                                for (int i=0;i<f;i++){
                                    SalesReportPojo salesReportPojo = new SalesReportPojo();
                                    JSONObject currentRespObj = resp.getJSONObject(String.valueOf(i));
                                    JSONObject currentDataObj = data.getJSONObject(String.valueOf(i));
                                    salesReportPojo.setBillNo(currentRespObj.getString("bill_no"));
                                  //  billNo.add(currentRespObj.getString("bill_no"));
                                    salesReportPojo.setClientName(currentRespObj.getString("client_name"));
                                   // clientName.add(currentRespObj.getString("client_name"));
                                   salesReportPojo.setContact(currentRespObj.getString("client_contact"));
                                  //  clientContact.add(currentRespObj.getString("client_contact"));
                                    salesReportPojo.setGrandPrice(currentRespObj.getString("grand_total"));
                                  //  grandTotal.add(currentRespObj.getString("grand_total"));
                                    salesReportPojo.setGst(currentRespObj.getString("gst"));
                                  //  GST.add(currentRespObj.getString("gst"));
                                    salesReportPojo.setProductName(currentDataObj.getString("product_name"));
                                  //  productName.add(currentDataObj.getString("product_name"));
                                 salesReportPojo.setSaleDate(currentRespObj.getString("order_date"));
                               //     saleDate.add(currentRespObj.getString("order_date"));
                                   salesReportPojo.setTotalSaleItem(currentDataObj.getString("quantity"));
                                //    totalSaleItem.add(currentDataObj.getString("quantity"));
                                  salesReportPojo.setUnitPrice(currentDataObj.getString("rate"));
                                   // unitPrice.add(currentDataObj.getString("rate"));

                                    saleReportList.add(salesReportPojo);


                                }

                                laStartDate.setVisibility(View.GONE);
                                laEndDate.setVisibility(View.GONE);


                                rvSales.setVisibility(View.VISIBLE);
                            rvHeader.setVisibility(View.VISIBLE);


                                laStartDate.setVisibility(View.GONE);
                                laEndDate.setVisibility(View.GONE);
                            tvGenerateExcel.setVisibility(View.VISIBLE);
                            tvTotaAmount.setVisibility(View.VISIBLE);
                            plainText.setVisibility(View.VISIBLE);
                                rvSales.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            rvHeader.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            SalesReportAdapter rvHeaderAdapter = new SalesReportAdapter(getApplicationContext(),headerList);
                            rvHeader.setAdapter(rvHeaderAdapter);
                                adapter = new SalesReportAdapter(getApplicationContext(),saleReportList);
                                rvSales.setAdapter(adapter);
                                btnGenerateReport.setVisibility(View.GONE);
                              //  btnGenerateExcel.setVisibility(View.VISIBLE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                    }

                }
                        , new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SalesReportActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        loading.dismiss();




                    }


                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();
                        String startDate = tvStartDate.getText().toString().replace("-","/");
                        String endDate =  tvEndDate.getText().toString().replace("-","/");
                        map.put("startDate", startDate );
                        map.put("endDate",endDate );

                        return map;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(SalesReportActivity.this);
                requestQueue.add(request);


            }
        });

        tvGenerateExcel.setClickable(true);
    tvGenerateExcel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

           // Toast.makeText(getApplicationContext(),"Button C",Toast.LENGTH_SHORT).show();



//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
//                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//            {
//                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT);
//            }
//            else {
                //your code


                HSSFWorkbook workbook = new HSSFWorkbook();
                int currentTime = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                HSSFSheet firstSheet = workbook.createSheet(String.valueOf(currentTime));
                saleReportList.add(0,new SalesReportPojo("Bill No","Sale Date","Client Name","Client Contact","Product Name"," Total Sale Item","GST 16%","Unit Price","Grand Total"));
                for (int i = 0; i < saleReportList.size(); i++) {

                    HSSFRow row = firstSheet.createRow(i);

                    for (int c = 0; c <= 8; c++) {

                        HSSFCell cell = row.createCell(c);

                        if (c == 0) {
                            cell.setCellValue(saleReportList.get(i).getBillNo());
                        }
                        if (c == 1) {
                            cell.setCellValue(saleReportList.get(i).getSaleDate());
                        }
                        if (c == 2) {
                            cell.setCellValue(saleReportList.get(i).getClientName());
                        }
                        if (c == 3) {
                            cell.setCellValue(saleReportList.get(i).getContact());
                        }
                        if (c == 4) {
                            cell.setCellValue(saleReportList.get(i).getProductName());
                        }
                        if (c == 5) {
                            cell.setCellValue(saleReportList.get(i).getTotalSaleItem());
                        }
                        if (c == 6) {
                            cell.setCellValue(saleReportList.get(i).getGst());
                        }
                        if (c == 7) {
                            cell.setCellValue(saleReportList.get(i).getUnitPrice());
                        }
                        if (c == 8) {
                            cell.setCellValue(saleReportList.get(i).getGrandPrice());
                        }


                    }


                }
                FileOutputStream fos = null;
                try {

                    if (Build.VERSION.SDK_INT >= 23) {
                        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(SalesReportActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }
                    }


                    String str_path = Environment.getExternalStorageDirectory().getAbsolutePath();
                    File file;
                    file = new File(str_path, getString(R.string.app_name) + ".xls");
                   // file.createNewFile();
                    fos = new FileOutputStream(file);
                    workbook.write(fos);
                    fos.flush();
                  //  workbook.close();
                    fos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                } finally {
                    if (fos != null) {
                        try {
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(SalesReportActivity.this, "Excel Sheet Generated", Toast.LENGTH_SHORT).show();
                }


        }
    });




    }






}