package com.example.android.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

public class MainActivity extends AppCompatActivity {

    Button addProd;
    ItemDetailsHelperClass db;
    CustomCursorAdapter adapter;
    ListView prodLists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.InitializerBuilder initializerBuilder = Stetho.newInitializerBuilder(this);
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this));
        initializerBuilder.enableDumpapp(Stetho.defaultDumperPluginsProvider(this));
        Stetho.Initializer initializer = initializerBuilder.build();
        Stetho.initialize(initializer);
        prodLists = (ListView) findViewById(R.id.prodLists);
        db = new ItemDetailsHelperClass(this);
        addProd = (Button) findViewById(R.id.addProdBtn);
        addProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddProdActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        prodLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ItemDetailActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 2);
            }
        });
        loadList();
        prodLists.setEmptyView((TextView) findViewById(R.id.emptyList));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        loadList();
    }

    public void loadList() {
        Cursor cursor = db.readValues();
        adapter = new CustomCursorAdapter(MainActivity.this, cursor);
        prodLists.setAdapter(adapter);
    }

}
