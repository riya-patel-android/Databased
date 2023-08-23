package com.example.offlinedatabase.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offlinedatabase.CreateContactActivity;
import com.example.offlinedatabase.DBHelper;
import com.example.offlinedatabase.MainActivity;
import com.example.offlinedatabase.R;
import com.example.offlinedatabase.model.User;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    MainActivity context;
    ArrayList<User> arrayList;

    public ContactAdapter(MainActivity mainActivity, ArrayList<User> arrayList) {
        this.context = mainActivity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.contact_items_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        User user = arrayList.get(position);

        int id = user.getId();
        String name = user.getName();
        String contact = user.getContact();


        holder.tvinitial.setText(name);
        holder.tvname.setText(name);
        holder.tvcontact.setText(contact);

        holder.makeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri call = Uri.parse("tel:" + contact);
                Intent intent = new Intent(Intent.ACTION_CALL, call);
                context.startActivity(intent);
            }
        });

        holder.openMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(context, holder.openMenu);

                context.getMenuInflater().inflate(R.menu.update_delete_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        if (menuItem.getItemId() == R.id.update) {

                            Intent intent = new Intent(context, CreateContactActivity.class);
                            intent.putExtra("id",id);
                            intent.putExtra("name",name);
                            intent.putExtra("contact",contact);
                            context.startActivity(intent);
                            context.finish();

                        } else if (menuItem.getItemId() == R.id.delete) {


                            DBHelper dbHelper = new DBHelper(context);

                            dbHelper.deleteData(id);

                            arrayList.remove(position);

                            notifyDataSetChanged();
                        }

                        return false;
                    }
                });

                popupMenu.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvinitial;
        TextView tvname;
        TextView tvcontact;
        ImageView makeCall;
        ImageView openMenu;
        RelativeLayout relative;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvinitial = itemView.findViewById(R.id.tvinitial);
            tvname = itemView.findViewById(R.id.tvname);
            tvcontact = itemView.findViewById(R.id.tvcontact);
            makeCall = itemView.findViewById(R.id.makeCall);
            openMenu = itemView.findViewById(R.id.openMenu);
            relative = itemView.findViewById(R.id.relative);
        }
    }
}
