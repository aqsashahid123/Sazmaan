package octapult.com.sazmaan.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import octapult.com.sazmaan.Pojo.SalesReportPojo;
import octapult.com.sazmaan.R;

/**
 * Created by AQSA SHaaPARR on 12/12/2017.
 */

public class SalesReportAdapter extends RecyclerView.Adapter<SalesReportAdapter.holder>  {

    Context context;
    List<SalesReportPojo> salesReportList;

    public SalesReportAdapter(Context context, List<SalesReportPojo> listSales){

        this.context = context;
        this.salesReportList = listSales;



    }



    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_report_row,parent,false);
        holder holder = new holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {

        holder.tvGrandTotal.setText(salesReportList.get(position).getGrandPrice());
        holder.tvUnitPrice.setText(salesReportList.get(position).getUnitPrice());
        holder.tvTotalSaleItem.setText(salesReportList.get(position).getTotalSaleItem());
        holder.tvBillNum.setText(salesReportList.get(position).getBillNo());
        holder.tvClientNum.setText(salesReportList.get(position).getContact());
        holder.tvSaleDate.setText(salesReportList.get(position).getSaleDate());
        holder.tvProductName.setText(salesReportList.get(position).getProductName());
        holder.tvClientName.setText(salesReportList.get(position).getClientName());
        holder.tvGst.setText(salesReportList.get(position).getGst());



    }

    @Override
    public int getItemCount() {
        return salesReportList.size();
    }

    public class holder extends RecyclerView.ViewHolder{


        TextView tvBillNum,tvClientName,tvClientNum,tvGst,tvSaleDate,tvProductName, tvTotalSaleItem,tvUnitPrice,tvGrandTotal;

        public holder(View itemView) {
            super(itemView);
            itemView = (CardView)itemView.findViewById(R.id.cvSales);
            tvBillNum = (TextView) itemView.findViewById(R.id.billNo);
            tvClientName = (TextView) itemView.findViewById(R.id.clientName);
            tvClientNum = (TextView) itemView.findViewById(R.id.contact);
            tvGst = (TextView) itemView.findViewById(R.id.gst);
            tvSaleDate = (TextView) itemView.findViewById(R.id.saleDate);
            tvProductName = (TextView) itemView.findViewById(R.id.productName);
            tvTotalSaleItem = (TextView) itemView.findViewById(R.id.totalSaleItem);
            tvUnitPrice = (TextView) itemView.findViewById(R.id.unitPrice);
            tvGrandTotal = (TextView) itemView.findViewById(R.id.grandTotal);
         //   tvBillNum = (TextView) itemView.findViewById(R.id.billNo);



        }
    }

}
