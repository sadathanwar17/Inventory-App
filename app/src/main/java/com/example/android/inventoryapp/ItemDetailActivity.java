package com.example.android.inventoryapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemDetailActivity extends AppCompatActivity {

    Button deleteRecords, order, receiveShipment, sell;
    TextView name, desc, price, quantity;
    ImageView prodImage;
    ItemDetailsHelperClass db;
    String img = null;
    int quantty;
    String emailText, itemName, itemDesc, itemPrice, itemQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Intent intent = getIntent();
        final long id = intent.getExtras().getLong("id");
        initialisation();
        db = new ItemDetailsHelperClass(this);
        itemName = "PRODUCT NAME: " + db.readString(id, 1);
        itemDesc = "PRODUCT DESCRIPTION: " + db.readString(id, 2);
        itemPrice = "PRODUCT PRICE: " + db.readString(id, 3);
        itemQuantity = "PRODUCT QUANTITY: " + db.readString(id, 4);
        name.setText(itemName);
        desc.setText(itemDesc);
        price.setText(itemPrice);
        quantity.setText(itemQuantity);
        emailText = itemName + "\n" + itemDesc + "\n" + itemPrice;
        img = db.readString(id, 5);
        prodImage.setImageBitmap(BitmapFactory
                .decodeFile(img));
        deleteRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ItemDetailActivity.this);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete(id);
                        finish();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                }).setTitle("Do you really want to delete?");
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(android.content.Intent.EXTRA_TEXT, emailText);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantty = Integer.parseInt(db.readString(id, 4));
                quantty = quantty - 1;
                if (quantty < 0) quantty = 0;
                db.update(id, quantty);
                quantity.setText("PRODUCT QUANTITY: " + db.readString(id, 4));
            }
        });
        receiveShipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantty = Integer.parseInt(db.readString(id, 4));
                quantty = quantty + 1;
                db.update(id, quantty);
                quantity.setText("PRODUCT QUANTITY: " + db.readString(id, 4));
            }
        });
    }

    public void initialisation() {
        deleteRecords = (Button) findViewById(R.id.DeleteRecordBtn);
        order = (Button) findViewById(R.id.OrderBtn);
        receiveShipment = (Button) findViewById(R.id.ReceiveShipmentBtn);
        sell = (Button) findViewById(R.id.Sell);
        name = (TextView) findViewById(R.id.prodNameTextView);
        desc = (TextView) findViewById(R.id.prodDescTextView);
        price = (TextView) findViewById(R.id.priceTextView);
        quantity = (TextView) findViewById(R.id.quantityTextView);
        prodImage = (ImageView) findViewById(R.id.prodImageView);
    }
}
