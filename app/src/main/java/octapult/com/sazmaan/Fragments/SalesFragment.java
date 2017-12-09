package octapult.com.sazmaan.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

import octapult.com.sazmaan.Activities.AddSaleActivity;
import octapult.com.sazmaan.Network.EndPoints;
import octapult.com.sazmaan.R;


public class SalesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    TextView tvBeddingCount,tvBeddingPaid,tvCushionCount,tvCushionPrice,tvAccessoriesCount,tvAccessoriesPrice;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SalesFragment() {
        // Required empty public constructor
    }
    public static SalesFragment newInstance(String param1, String param2) {
        SalesFragment fragment = new SalesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Casting
        View view = inflater.inflate(R.layout.fragment_sales, container, false);
        tvAccessoriesCount= (TextView) view.findViewById(R.id.accessoriesCount);
        tvAccessoriesPrice= (TextView)view.findViewById(R.id.accessoriesPrice);
        tvBeddingCount = (TextView)view.findViewById(R.id.tvBeddingCount);
        tvBeddingPaid = (TextView)view.findViewById(R.id.tvBeddingPaid);
        tvCushionCount = (TextView)view.findViewById(R.id.tvCushionCount);
        tvCushionPrice = (TextView)view.findViewById(R.id.cushionPrice);

        final ProgressDialog pd = new ProgressDialog(SalesFragment.super.getContext());
        pd.setMessage("loading");
        pd.show();

        StringRequest request = new StringRequest(Request.Method.POST, EndPoints.BEDING_SALES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity().getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                pd.dismiss();
                try {
//                    JSONArray arrr = new JSONArray(response);
                     JSONObject object = new JSONObject(response);

                    JSONObject obj = object.getJSONObject("item");
                    String beddingCount = obj.getString("bedding_count");
                    String beddingPaid = obj.getString("bedding_paid");

                    tvBeddingCount.setText(beddingCount);
                    tvBeddingPaid.setText(beddingPaid);





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
                Toast.makeText(getActivity().getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();

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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(request);

/////////////////////////////////////////////////////////////////////
        StringRequest reques = new StringRequest(Request.Method.POST, EndPoints.CUSHION_SALE, new Response.Listener<String>() {
            @Override
            public void onResponse(String respons) {
                Toast.makeText(getActivity().getApplicationContext(),respons,Toast.LENGTH_SHORT).show();
                pd.dismiss();
                try {
//                    JSONArray arrr = new JSONArray(response);
                    JSONObject object = new JSONObject(respons);

                    JSONObject obj = object.getJSONObject("item_2");
                    String cushionCount = obj.getString("cushion_count");
                    String cushionPaid = obj.getString("cushion_paid");

                    tvCushionCount.setText(cushionCount);
                    tvCushionPrice.setText(cushionPaid);

                    /////////////////////////////////////



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
                Toast.makeText(getActivity().getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();

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
        RequestQueue requestQueu = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueu.add(reques);
//////////////////////////////////////////////////////////////////////////////////////////////




        StringRequest requ = new StringRequest(Request.Method.POST, EndPoints.ACCESSORIES_SALE, new Response.Listener<String>() {
            @Override
            public void onResponse(String respon) {
                Toast.makeText(getActivity().getApplicationContext(),respon,Toast.LENGTH_SHORT).show();
                pd.dismiss();
                try {
//                    JSONArray arrr = new JSONArray(response);
                    JSONObject object = new JSONObject(respon);

                    JSONObject obj = object.getJSONObject("item_3");
                    String beddingCount = obj.getString("accessories_count");
                    String beddingPaid = obj.getString("accessories_paid");

                  //  tvBeddingPaid.setText(beddingPaid);
                    tvAccessoriesPrice.setText(beddingPaid);
                    tvAccessoriesCount.setText(beddingCount);









                } catch (Exception e) {
                    e.printStackTrace();
                }
                // object.get("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();

                Toast.makeText(getActivity().getApplicationContext(), "Volley Error" + error, Toast.LENGTH_SHORT).show();

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
        RequestQueue requestQue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQue.add(requ);



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }





    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }





















}
