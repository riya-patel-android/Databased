package com.example.offlinedatabase;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offlinedatabase.Adapter.ContactAdapter;
import com.example.offlinedatabase.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.intuit.sdp.BuildConfig;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton createContact;
    RecyclerView recyclerView;

    ArrayList<User> arrayList = new ArrayList<>();
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createContact = findViewById(R.id.createContact);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);

        getAllData();

        createContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateContactActivity.class);
                startActivity(intent);
                finish();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<User> templist = new ArrayList<>();

                for(int i=0;i<arrayList.size();i++)
                {
                    User user = arrayList.get(i);

                    if(user.getName().toLowerCase().contains(newText.toLowerCase())  || user.getContact().toLowerCase().contains(newText.toLowerCase()))
                    {
                        templist.add(user);
                    }
                }

                ContactAdapter contactAdapter = new ContactAdapter(MainActivity.this,templist );
                recyclerView.setAdapter(contactAdapter);

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.app_setting_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.searchView);

        SearchView searchView = (SearchView) searchItem.getActionView();

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    ArrayList<User> templist = new ArrayList<>();

                    for(int i=0;i<arrayList.size();i++)
                    {
                        User user = arrayList.get(i);

                        if(user.getName().toLowerCase().contains(newText.toLowerCase())  || user.getContact().toLowerCase().contains(newText.toLowerCase()))
                        {
                            templist.add(user);
                        }
                    }

                    ContactAdapter contactAdapter = new ContactAdapter(MainActivity.this,templist );
                    recyclerView.setAdapter(contactAdapter);

                    return false;
                }
            });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.share) {
            String appurl = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, appurl);
            startActivity(Intent.createChooser(intent, "My Application"));

        } else if (item.getItemId() == R.id.rate) {
            String appurl = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
            Uri uri = Uri.parse(appurl);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        } else if (item.getItemId() == R.id.more) {

            // Account name = WhatsApp LLC
            String appurl = "https://play.google.com/store/apps/developer?id=WhatsApp+LLC";
            Uri uri = Uri.parse(appurl);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    void getAllData() {
        DBHelper dbHelper = new DBHelper(MainActivity.this);

        Cursor cursor = dbHelper.getData();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String contact = cursor.getString(2);

            User user = new User(id, name, contact);
            arrayList.add(user);
        }

        Log.e("skjshjifgh", "" + arrayList);

        ContactAdapter contactAdapter = new ContactAdapter(MainActivity.this, arrayList);
        recyclerView.setAdapter(contactAdapter);

    }
}