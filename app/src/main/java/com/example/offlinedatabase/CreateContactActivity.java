package com.example.offlinedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreateContactActivity extends AppCompatActivity {
    Button save;
    EditText ename, econtact;
    int id;
    String name, contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        ename = findViewById(R.id.ename);
        econtact = findViewById(R.id.econtact);
        save = findViewById(R.id.save);

        if (getIntent().getExtras() != null) {
            id = getIntent().getIntExtra("id", 0);
            name = getIntent().getStringExtra("name");
            contact = getIntent().getStringExtra("contact");
            ename.setText(name);
            econtact.setText(contact);
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ename.getText().toString();
                String contact = econtact.getText().toString();
                DBHelper dbHelper = new DBHelper(CreateContactActivity.this);
                if (getIntent().getExtras() != null) {
                    dbHelper.updateData(id, name, contact);
                } else {
                    dbHelper.insertData(name, contact);
                }
                Intent intent = new Intent(CreateContactActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(CreateContactActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}