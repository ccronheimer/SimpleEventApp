package com.cameroncronheimer.eventapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MaterialButton addNoteBtn = findViewById(R.id.addneweventbtn);

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddEventActivity.class));
            }
        });

        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();


        Realm.setDefaultConfiguration(config);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Event> eventsList = realm.where(Event.class).findAll().sort("datetime",Sort.ASCENDING);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter myAdapter = new MyAdapter(getApplicationContext(),eventsList);
        recyclerView.setAdapter(myAdapter);

        eventsList.addChangeListener(new RealmChangeListener<RealmResults<Event>>() {
            @Override
            public void onChange(RealmResults<Event> notes) {
                myAdapter.notifyDataSetChanged();
            }
        });


    }
}