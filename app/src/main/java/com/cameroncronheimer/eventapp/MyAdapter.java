package com.cameroncronheimer.eventapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    RealmResults<Event> eventsList;

    public MyAdapter(Context context, RealmResults<Event> eventsList) {
        this.context = context;
        this.eventsList = eventsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Event event = eventsList.get(position);
        holder.titleOutput.setText(event.getTitle()); // get title
        holder.dateOutput.setText(event.getDate());  // get date
        holder.timeOutput.setText(event.getTime()); // get Time
        holder.locationOutput.setText(event.getLocation()); // get location

        // delete button for the card
        holder.deleteButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                event.deleteFromRealm();
                realm.commitTransaction();
                Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    // vars.....
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleOutput;
        TextView dateOutput;
        TextView timeOutput;
        TextView locationOutput;
        Button deleteButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOutput = itemView.findViewById(R.id.titleoutput);
            dateOutput = itemView.findViewById(R.id.dateoutput);
            timeOutput = itemView.findViewById(R.id.timeoutput);
            locationOutput = itemView.findViewById(R.id.locationoutput);
            deleteButton = itemView.findViewById(R.id.deletebtn);
        }
    }
}