package octapult.com.sazmaan.Adapters;

import android.content.Context;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import octapult.com.sazmaan.Activities.AddSaleActivity;
import octapult.com.sazmaan.Pojo.AddSalePojo;
import octapult.com.sazmaan.R;

/**
 * Created by AQSA SHaaPARR on 12/2/2017.
 */

public class AddSaleAdapter extends RecyclerView.Adapter<AddSaleAdapter.Holder> {


    LayoutInflater inflater;
    List<AddSalePojo> salePojo;
    Context context;

    public  AddSaleAdapter (Context context,List<AddSalePojo> salePojo){

        this.context = context;
        this.salePojo = salePojo;

    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_add_data_sales,parent,false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {

        holder.tvCategoryName.setText(salePojo.get(position).getCategory());
        holder.tvPrice.setText(salePojo.get(position).getPrice());
        holder.tvProductName.setText(salePojo.get(position).getProd());
        holder.tvTotal.setText(salePojo.get(position).getTotal());
        holder.tvQuantity.setText(salePojo.get(position).getQuantity());
        holder.tvDate.setText(salePojo.get(position).getDate());

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context wrapper = new  ContextThemeWrapper(context,R.style.MyPopupMenu);
                final PopupMenu popupMenu = new PopupMenu(wrapper,holder.ivDelete);
                popupMenu.getMenuInflater().inflate(R.menu.menu_delete_sale,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){
                            
                            case (R.id.deleteSale):

                                Toast.makeText(context, "Delete Pressed", Toast.LENGTH_SHORT).show();
                                   // salePojo.remove(position);
                                removeAt(position);

                                break;
                            
                        }



                        return true;
                    }
                });

                popupMenu.show();



            }
        });





    }

    @Override
    public int getItemCount() {
        return salePojo.size();
    }


    public  class Holder extends RecyclerView.ViewHolder{


        TextView  tvProductName,tvCategoryName,tvQuantity,tvTotal,tvPrice,tvDate;

        ImageView ivDelete;

        public Holder(View itemView) {
        super(itemView);
        itemView = (CardView)itemView.findViewById(R.id.cardView);
        tvProductName = (TextView) itemView.findViewById(R.id.tvProducts);
        tvCategoryName = (TextView) itemView.findViewById(R.id.tvCategory);
        tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
        tvQuantity = (TextView) itemView.findViewById(R.id.tvQuantity);
        tvTotal = (TextView) itemView.findViewById(R.id.tvTotalPrice);
        ivDelete = (ImageView) itemView.findViewById(R.id.ivDelete);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);


    }
}

    public void removeAt(int position) {


        AddSaleActivity.acuratePrice = AddSaleActivity.acuratePrice - Integer.valueOf(salePojo.get(position).getTotal());
        AddSaleActivity.quantityList.remove(salePojo.get(position).getQuantity());
        AddSaleActivity.totalRateList.remove(salePojo.get(position).getTotal());
        AddSaleActivity.productIdList.remove(salePojo.get(position).getProdId());
        salePojo.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, salePojo.size());
        Toast.makeText(context,String.valueOf( AddSaleActivity.acuratePrice),Toast.LENGTH_SHORT).show();
    }


}
