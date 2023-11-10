package com.example.aviaryquest.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaryquest.Data.Models.AccountEditOptions;
import com.example.aviaryquest.R;
import com.example.aviaryquest.Windows;

import java.util.ArrayList;

public class AccountEditOpts_Adapter extends RecyclerView.Adapter<AccountEditOpts_Adapter.MyHolder>{

    Context context;
    ArrayList<AccountEditOptions> accountEditOptions;

    public AccountEditOpts_Adapter(Context context, ArrayList<AccountEditOptions> accountEditOptions) {
        this.context = context;
        this.accountEditOptions = accountEditOptions;
    }

    @NonNull
    @Override
    public AccountEditOpts_Adapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.account_task_options,parent,false);
        MyHolder myHolder=new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AccountEditOpts_Adapter.MyHolder holder, int position) {
        holder.name.setText(accountEditOptions.get(position).getName());
        holder.image.setImageResource(accountEditOptions.get(position).getIcon());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // Show the dialog using your Windows class
                    Windows windows = new Windows();
                    windows.updateUsernameWindow(accountEditOptions.get(position).getName(),(Activity) context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountEditOptions.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        CardView card;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.acc_optText);
            image=itemView.findViewById(R.id.acc_optImage);
            card=itemView.findViewById(R.id.acc_optCard);
        }
    }
}
