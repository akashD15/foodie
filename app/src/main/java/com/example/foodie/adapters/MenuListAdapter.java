package com.example.foodie.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodie.R;
import com.example.foodie.model.Menu;

import java.util.List;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MyViewHolder> {

    private List<Menu> menuList;
    private final MenuListClickListener clickListener;

    public MenuListAdapter(List<Menu> menuList, MenuListClickListener clickListener){
        this.menuList = menuList;
        this.clickListener = clickListener;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Menu> menuList){
        this.menuList = menuList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MenuListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_recycler_row,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MenuListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.menuName.setText(menuList.get(position).getName());
        holder.menuPrice.setText("Price: $"+menuList.get(position).getPrice());
        holder.addtoCartButton.setOnClickListener(v -> {
                  Menu menu = menuList.get(position);
                  menu.setTotalInCart(1);
                  clickListener.onAddtoCartClick(menu);
                  holder.addMoreLayout.setVisibility(View.VISIBLE);
                  holder.addtoCartButton.setVisibility(View.GONE);
                  holder.tvCount.setText(menu.getTotalInCart()+"");
        });
        holder.imageMinus.setOnClickListener(v -> {
            Menu menu = menuList.get(position);
            int total = menu.getTotalInCart();
            total--;
            if(total > 0){
                menu.setTotalInCart(total);
                clickListener.onUpdateCartClick(menu);
                holder.tvCount.setText(total +"");
            }else{
                holder.addMoreLayout.setVisibility(View.GONE);
                holder.addtoCartButton.setVisibility(View.VISIBLE);
                menu.setTotalInCart(total);
                clickListener.onRemoveFromCartClick(menu);
            }

        });
        holder.imageAddOne.setOnClickListener(v -> {
            Menu menu = menuList.get(position);
            int total = menu.getTotalInCart();
            total++;
            if(total <= 10){
                menu.setTotalInCart(total);
                clickListener.onUpdateCartClick(menu);
                holder.tvCount.setText(total +"");
            }

        });
        Glide.with(holder.thumbImage)
                .load(menuList.get(position).getUrl())
                .into(holder.thumbImage);


    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView menuName;
        TextView menuPrice;
        TextView addtoCartButton;
        ImageView thumbImage;
        ImageView imageMinus;
        ImageView imageAddOne;
        TextView tvCount;
        LinearLayout addMoreLayout;

        public MyViewHolder(View view){
            super(view);
            menuName = view.findViewById(R.id.menuName);
            menuPrice = view.findViewById(R.id.menuPrice);
            addtoCartButton = view.findViewById(R.id.addToCartButton);
            thumbImage = view.findViewById(R.id.thumbImage);
            imageMinus = view.findViewById(R.id.imageMinus);
            imageAddOne = view.findViewById(R.id.imageAddOne);
            tvCount = view.findViewById(R.id.tvCount);
            addMoreLayout = view.findViewById(R.id.addMoreLayout);
        }
    }
    public interface MenuListClickListener {
        public void onAddtoCartClick(Menu menu);
        public void onUpdateCartClick(Menu menu);
        public void onRemoveFromCartClick(Menu menu);
    }
}
