package octapult.com.sazmaan.Activities;

import android.app.ProgressDialog;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Formatter;

import octapult.com.sazmaan.Network.EndPoints;
import octapult.com.sazmaan.R;

public class GraphActivity extends AppCompatActivity {
    GraphView graph ;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        graph = (GraphView) findViewById(R.id.graph);



        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"jan", "feb", "mar","apr","may","june","july","aug","sep","oct","nov","dec"});
       // staticLabelsFormatter.setVerticalLabels(new String[] {"0","25,000" ,"50,000","75,000","100,000","125,000","150,000" });

        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


        graph.setTitle("Expenses/Sales Report");
        graph.setTitleColor(getResources().getColor(R.color.colorGraph));
        graph.setTitleTextSize(120);
//
//        final ProgressDialog pd = new ProgressDialog(GraphActivity.this);
//        pd.setMessage("loading");
//        pd.show();
         pd = new ProgressDialog(GraphActivity.this);
        pd.setMessage("loading");
        pd.show();

        StringRequest request = new StringRequest(Request.Method.POST, EndPoints.GET_GRAPH_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            //    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                pd.dismiss();
                try {

                    JSONObject object = new JSONObject(response);
                    JSONObject sales = object.getJSONObject("sales");
                    String jan,feb,mar,apr,may,jun,jul,aug,sep,oct,nov,dec;
                    String exJan,exFeb,exMar,exApr,exMay,exJun,exJul,exAug,exSep,exOct,exNov,exDec;

                    ////////////////////////
                    jan = sales.getString("jan");

                    if (jan == "null"){
                        jan = "0";
                    }
                    else {


                      jan = sales.getString("jan");
                    }


//////////////////////////////////
                    feb = sales.getString("feb");

                    if (feb == "null"){
                        feb = "0";
                    }
                    else {

                     feb = sales.getString("feb");
                    }
////////////////////////////////////
                    mar = sales.getString("mar");

                    if (mar == "null"){
                        mar = "0";
                    }
                    else {

                        mar = sales.getString("mar");
                    }
/////////////////////////////////////////////
                    apr = sales.getString("apr");

                    if (apr == "null"){
                        apr = "0";
                    }
                    else {

                        apr = sales.getString("apr");
                    }
///////////////////////////////////////
                    may = sales.getString("may");

                    if (may == "null"){
                        may = "0";
                    }
                    else {

                        may = sales.getString("may");
                    }
/////////////////////////
                    jun = sales.getString("jun");

                    if (jun == "null"){
                        jun = "0";
                    }
                    else {

                        jun = sales.getString("jun");
                    }
///////////////////////////////////
                    jul = sales.getString("jul");

                    if (jul == "null"){
                        jul = "0";
                    }
                    else {

                        jul = sales.getString("jul");
                    }
///////////////////////////////////
                    aug = sales.getString("aug");

                    if (aug == "null"){
                        aug = "0";
                    }
                    else {

                        aug = sales.getString("aug");
                    }
///////////////////////////////////////
                    sep = sales.getString("sep");

                    if (sep == "null"){
                        sep = "0";
                    }
                    else {

                        sep = sales.getString("sep");
                    }

///////////////////////////////////////////////
                    oct = sales.getString("oct");

                    if (oct == "null"){
                        oct = "0";
                    }
                    else {

                        oct = sales.getString("oct");
                    }

/////////////////////////////////////////////
                    nov = sales.getString("nov");

                    if (nov == "null"){
                        nov = "0";
                    }
                    else {

                        nov = sales.getString("nov");
                    }




/////////////////////////////////////////////
                    dec = sales.getString("dec");

                    if (dec == "null"){
                        dec = "0";
                    }
                    else {

                        dec = sales.getString("dec");
                    }



//                        String apr = sales.getString("jan");
//                    String may = sales.getString("jan");

                  //  GraphViewSeriesStyle seriesStyle = new GraphViewSeriesStyle();




                    LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                            new DataPoint(0,Double.valueOf(jan) ),
                            new DataPoint(1, Double.valueOf(feb)),
                           new DataPoint(2, Double.valueOf(mar)),
                            new DataPoint(3, Double.valueOf(apr)),

                            new DataPoint(4, Double.valueOf(may)),
                            new DataPoint(5, Double.valueOf(jun)),

                            new DataPoint(6, Double.valueOf(jul)),

                            new DataPoint(7, Double.valueOf(aug)),

                            new DataPoint(8, Double.valueOf(sep)),

                            new DataPoint(9, Double.valueOf(oct)),

                            new DataPoint(10, Double.valueOf(nov)),

                            new DataPoint(11, Double.valueOf(dec)),



                    });

                    series.setTitle("Expenses");
                    series.setColor(getResources().getColor(R.color.colorRed));
                    series.setDrawDataPoints(true);

                  //
                      series.setDataPointsRadius(12);
                    series.setOnDataPointTapListener(new OnDataPointTapListener() {
                        @Override
                        public void onTap(Series series, DataPointInterface dataPoint) {
                            DecimalFormat formatter = new DecimalFormat("#,###,###");
                            String num = formatter.format(dataPoint.getY());
                            Toast.makeText(getApplicationContext(), num,Toast.LENGTH_SHORT).show();



                            //   Toast.makeText(getApplicationContext(), String.format(String.valueOf(dataPoint.getY()),'\u002c'),Toast.LENGTH_SHORT).show();
                        }
                    });
                    series.setThickness(8);



                    Paint paint = new Paint();
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(10);
                   // paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
                    paint.setColor(getResources().getColor(R.color.colorPrimaryDark));

                    series.setCustomPaint(paint);
//
//                    graph.addSeries(series);
//                    graph.getLegendRenderer().setVisible(true)



                    JSONObject expenses = object.getJSONObject("expenses");
////////////////////////////////////////////////////////////
                    exJan = expenses.getString("jan");

                    if (exJan == "null"){
                        exJan = "0";
                    }
                    else {


                        exJan = expenses.getString("jan");
                    }
/////////////////////////////////////////////

                    exFeb = expenses.getString("feb");

                    if (exJan == "null"){
                        exFeb = "0";
                    }
                    else {


                        exFeb = expenses.getString("feb");
                    }


                    /////////////////////////////////////////////

                    exMar = expenses.getString("mar");

                    if (exMar == "null"){
                        exMar = "0";
                    }
                    else {


                        exMar = expenses.getString("mar");
                    }
                    /////////////////////////////////////////////

                    exApr = expenses.getString("apr");

                    if (exApr == "null"){
                        exApr = "0";
                    }
                    else {


                        exApr = expenses.getString("apr");
                    }

                    /////////////////////////////////////////////

                    exMay = expenses.getString("may");

                    if (exMay == "null"){
                        exMay = "0";
                    }
                    else {


                        exMay = expenses.getString("may");
                    }

                    /////////////////////////////////////////////

                    exJun = expenses.getString("jun");

                    if (exJun == "null"){
                        exJun = "0";
                    }
                    else {


                        exJun = expenses.getString("jun");
                    }

                    /////////////////////////////////////////////

                    exJul = expenses.getString("jul");

                    if (exJul == "null"){
                        exJul = "0";
                    }
                    else {


                        exJul = expenses.getString("jul");
                    }


                    /////////////////////////////////////////////

                    exAug = expenses.getString("aug");

                    if (exAug == "null"){
                        exAug = "0";
                    }
                    else {


                        exAug = expenses.getString("jul");
                    }

                    /////////////////////////////////////////////

                    exSep = expenses.getString("sep");

                    if (exSep == "null"){
                        exSep = "0";
                    }
                    else {


                        exSep = expenses.getString("sep");
                    }

                    /////////////////////////////////////////////

                    exOct = expenses.getString("oct");

                    if (exOct == "null"){
                        exOct = "0";
                    }
                    else {


                        exOct = expenses.getString("oct");
                    }

                    /////////////////////////////////////////////

                    exNov = expenses.getString("nov");

                    if (exNov == "null"){
                        exNov = "0";
                    }
                    else {


                        exNov = expenses.getString("nov");
                    }

                    /////////////////////////////////////////////

                    exDec = expenses.getString("dec");

                    if (exDec == "null"){
                        exDec = "0";
                    }
                    else {


                        exDec = expenses.getString("dec");
                    }


                    LineGraphSeries<DataPoint> seriesExpenses = new LineGraphSeries<DataPoint>(new DataPoint[] {
                            new DataPoint(0,Double.valueOf(exJan) ),
                            new DataPoint(1, Double.valueOf(exFeb)),
                            new DataPoint(2, Double.valueOf(exMar)),
                            new DataPoint(3, Double.valueOf(exApr)),

                            new DataPoint(4, Double.valueOf(exMay)),
                            new DataPoint(5, Double.valueOf(exJun)),

                            new DataPoint(6, Double.valueOf(exJul)),

                            new DataPoint(7, Double.valueOf(exAug)),

                            new DataPoint(8, Double.valueOf(exSep)),

                            new DataPoint(9, Double.valueOf(exOct)),

                            new DataPoint(10, Double.valueOf(exNov)),

                            new DataPoint(11, Double.valueOf(exDec)),






//                            new DataPoint(3, 2),
//                            new DataPoint(4, 6)
                    });

                    seriesExpenses.setTitle("Sales");

                    seriesExpenses.setDrawDataPoints(true);
                    seriesExpenses.setDataPointsRadius(12);
                    seriesExpenses.setThickness(8);

                    seriesExpenses.setOnDataPointTapListener(new OnDataPointTapListener() {
                        @Override
                        public void onTap(Series series, DataPointInterface dataPoint) {
                            DecimalFormat formatter = new DecimalFormat("#,###,###");
                            String num = formatter.format(dataPoint.getY());
                            Toast.makeText(getApplicationContext(), num,Toast.LENGTH_SHORT).show();



                            //   Toast.makeText(getApplicationContext(), String.format(String.valueOf(dataPoint.getY()),'\u002c'),Toast.LENGTH_SHORT).show();
                        }
                    });


                    Paint paintExpenses = new Paint();
                    paintExpenses.setStyle(Paint.Style.STROKE);
                    paintExpenses.setStrokeWidth(10);
                    paintExpenses.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
                 //   paintExpenses.setPathEffect(new DashPathEffect(new float[]{0, 5}, 0));

                    paintExpenses.setColor(getResources().getColor(R.color.colorRed));
                    seriesExpenses.setCustomPaint(paintExpenses);


              //      graph.getLegendRenderer().setVisible(true);

                    graph.addSeries(series);
                    graph.getViewport().setScalable(true);

                    graph.addSeries(seriesExpenses);
                    graph.getLegendRenderer().setVisible(true);







                } catch (Exception e) {
                    e.printStackTrace();
                }
                // object.get("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   pd.dismiss();
                //  Log.e("Error",error.printStackTrace());
                Toast.makeText(getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //    params.put("password", password);


                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);








    }










}
