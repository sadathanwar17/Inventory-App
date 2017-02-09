package com.example.android.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddProdActivity extends AppCompatActivity {

    EditText prodName, prodDesc, prodPrice, prodQuantity;
    String name, desc, temp;
    int price, quantity, flag;
    Button save, imageSelect;
    byte[] img;
    ItemDetailsHelperClass db;
    String imgDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prod);
        db = new ItemDetailsHelperClass(this);
        img = null;
        prodName = (EditText) findViewById(R.id.prodNameEditText);
        prodDesc = (EditText) findViewById(R.id.prodDescEditText);
        prodPrice = (EditText) findViewById(R.id.prodPriceEditText);
        prodQuantity = (EditText) findViewById(R.id.prodQuantityEdiText);
        save = (Button) findViewById(R.id.saveBtn);
        imageSelect = (Button) findViewById(R.id.imageSelect);
        imageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                name = prodName.getText().toString();
                desc = prodDesc.getText().toString();
                temp = prodPrice.getText().toString();
                if (!TextUtils.isEmpty(temp)) {
                    price = Integer.parseInt(prodPrice.getText().toString());
                    flag = 1;
                } else {
                    prodPrice.setError("Field cant be empty");
                    flag = 0;
                }
                temp = prodQuantity.getText().toString();
                if (!TextUtils.isEmpty(temp)) {
                    quantity = Integer.parseInt(prodQuantity.getText().toString());
                    flag = 1;
                } else {
                    prodQuantity.setError("Field cant be empty");
                    flag = 0;
                }
                if (TextUtils.isEmpty(name)) {
                    prodName.setError("Field cant be empty");
                    flag = 0;
                }
                if (TextUtils.isEmpty(desc)) {
                    prodDesc.setError("Field Cant be empty");
                    flag = 0;
                }
                if (flag == 1) {
                    db.write(name, desc, price, quantity, imgDecodableString);
                    Toast.makeText(AddProdActivity.this, "Saved Successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == 1 && resultCode == RESULT_OK
                    && null != data) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
            e.printStackTrace();
        }
    }
}
