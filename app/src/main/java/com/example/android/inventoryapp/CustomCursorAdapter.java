package com.example.android.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CustomCursorAdapter extends CursorAdapter {

    private LayoutInflater cursorInflater;

    public CustomCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.from(parent.getContext()).inflate(R.layout.listiteminfo, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView prodName = (TextView) view.findViewById(R.id.itemName);
        prodName.setText(cursor.getString(cursor.getColumnIndex(ItemDetailsHelperClass.COLUMN_PRODUCT_NAME)));

        final TextView prodQuantity = (TextView) view.findViewById(R.id.quantityRemaining);
        prodQuantity.setText(cursor.getString(cursor.getColumnIndex(ItemDetailsHelperClass.COLUMN_PRODUCT_QUANTITY)));

        final TextView prodPrice = (TextView) view.findViewById(R.id.price);
        prodPrice.setText(cursor.getString(cursor.getColumnIndex(ItemDetailsHelperClass.COLUMN_PRODUCT_PRICE)));

        Button sellBtn = (Button) view.findViewById(R.id.sellBtn);
        sellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemDetailsHelperClass db = new ItemDetailsHelperClass(context);
                int quantity = db.readColumnQuantity();
                Log.i("Value", "" + quantity);
                quantity = quantity - 1;
                if (quantity < 0) quantity = 0;
                db.updateQuantity(quantity);
                prodQuantity.setText("" + quantity);
            }
        });
    }
}
