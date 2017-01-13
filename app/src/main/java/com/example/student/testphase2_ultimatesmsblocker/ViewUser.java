package com.example.student.testphase2_ultimatesmsblocker;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class ViewUser extends AppCompatActivity {

    private ArrayList<Object> arrayList;
    private RecyclerView recyclerView;
    Database db;
    private final int EDIT_REQUEST_CODE = 5;
    private final int REGISTER_REQUEST_CODE = 1;
    Cursor cursor;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new Database(this);
        search = (EditText) findViewById( R.id.search);

        viewItems();
        addTextListener();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.startAnActivityForResult(ViewUser.this, ViewUser.this, RegisterUser.class, REGISTER_REQUEST_CODE);
            }
        });
    }

    public void addTextListener(){

        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                final List<Object> filteredList = new ArrayList<>();
                cursor = db.selectBySearch(query);
                try {
                    cursor.moveToFirst();
                    if (cursor.getCount() == 0) {

                    } else {
                        for (int i = 0; i < cursor.getCount(); i++) {
                            String id = cursor.getString(cursor.getColumnIndex(db.ID_USER));
                            String name = cursor.getString(cursor.getColumnIndex(db.NAME_USER));
                            String password = cursor.getString(cursor.getColumnIndex(db.PASSWORD_USER));
                            String email = cursor.getString(cursor.getColumnIndex(db.EMAIL_USER));
                            String contact = cursor.getString(cursor.getColumnIndex(db.CONTACT_USER));
                            String type = cursor.getString(cursor.getColumnIndex(db.TYPE_USER));
                            String regDate = cursor.getString(cursor.getColumnIndex(db.REGISTRATION_DATE_USER));
                            String modfDate = cursor.getString(cursor.getColumnIndex(db.MODIFY_DATE_USER));

                            String typeName;
                            if(type.contains("1")||type.equals("1")){
                                typeName = "Normal User";
                            } else {
                                typeName = "Advanced User";
                            }

                            filteredList.add(new User(id, name, password, email, contact, typeName, regDate, modfDate));
                            cursor.moveToNext();
                        }
                        cursor.close();
                    }
                    enableSwipeExpense();
                } catch (Exception e) {
                    Log.d("sdf", e.getMessage());
                }

                if (cursor!=null){
                    recyclerView.setLayoutManager(new LinearLayoutManager(ViewUser.this));
                    Adapter adapter = new Adapter(ViewUser.this, filteredList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    private void viewItems() {
        try {
            getReferencesForViewItemsRecyclerView();
            Cursor cursor = db.selectAllUsers();
            addValuesToArrayList(cursor);
        } catch (Exception e) {
            Log.d("showItems", " failed " + e.getMessage());
        }
    }

    private void getReferencesForViewItemsRecyclerView() {
        arrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.view_item_recycle_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(new Adapter(ViewUser.this, arrayList));
    }

    private void addValuesToArrayList(Cursor cursor) {
        try {
            cursor.moveToFirst();
            if (cursor.getCount() == 0) {

            } else {
                for (int i = 0; i < cursor.getCount(); i++) {
                    String id = cursor.getString(cursor.getColumnIndex(db.ID_USER));
                    String name = cursor.getString(cursor.getColumnIndex(db.NAME_USER));
                    String password = cursor.getString(cursor.getColumnIndex(db.PASSWORD_USER));
                    String email = cursor.getString(cursor.getColumnIndex(db.EMAIL_USER));
                    String contact = cursor.getString(cursor.getColumnIndex(db.CONTACT_USER));
                    String type = cursor.getString(cursor.getColumnIndex(db.TYPE_USER));
                    String regDate = cursor.getString(cursor.getColumnIndex(db.REGISTRATION_DATE_USER));
                    String modfDate = cursor.getString(cursor.getColumnIndex(db.MODIFY_DATE_USER));

                    String typeName;
                    if(type.contains("1")||type.equals("1")){
                        typeName = "Normal User";
                    } else {
                        typeName = "Advanced User";
                    }

                    arrayList.add(new User(id, name, password, email, contact, typeName, regDate, modfDate));
                    cursor.moveToNext();
                }
                cursor.close();
            }
            enableSwipeExpense();
        } catch (Exception e) {
            Log.d("sdf", e.getMessage());
        }

    }

    private void enableSwipeExpense() {
        RecyclerTouchListener onTouchListener = new RecyclerTouchListener(this, recyclerView);
        onTouchListener
                .setSwipeOptionViews(R.id.edit, R.id.delete)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        User user = (User) arrayList.get(position);
                        if (viewID == R.id.delete) {
                            final String id = user.getId();
                            deleteExpense(id);

                        } else if (viewID == R.id.edit) {
                            SessionManager preference = new SessionManager();
                            final String PREFERENCES = "edit";
                            preference.setDatePreferences(getApplicationContext(), PREFERENCES, "id", user.getId());
                            preference.setDatePreferences(getApplicationContext(), PREFERENCES, "name", user.getTitle());
                            preference.setDatePreferences(getApplicationContext(), PREFERENCES, "password", user.getPassword());
                            preference.setDatePreferences(getApplicationContext(), PREFERENCES, "email", user.getEmail());
                            preference.setDatePreferences(getApplicationContext(), PREFERENCES, "contact", user.getContact());
                            Utility.startAnActivityForResult(ViewUser.this, getApplicationContext(), RegisterUser.class, EDIT_REQUEST_CODE);

                        }
                    }
                });
        recyclerView.addOnItemTouchListener(onTouchListener);
    }

    private void deleteExpense(final String idUser) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewUser.this);
        alertDialogBuilder.setTitle("Confirmation");
        alertDialogBuilder.setMessage("Do you want to delete?");
        alertDialogBuilder.setCancelable(true)
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (db.deleteUser(idUser)) {
                                    Utility.successSnackBar(recyclerView, "Expense deleted", ViewUser.this);
                                    viewItems();
                                } else if (!db.deleteUser(idUser)) {
                                    Utility.failSnackBar(recyclerView, "Error, Expense not deleted, try again", ViewUser.this);
                                }
                            }

                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case EDIT_REQUEST_CODE:
                    viewItems();
                    break;
                case REGISTER_REQUEST_CODE:
                    viewItems();
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view, menu);

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//            SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
//            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
//
//            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//                @Override
//                public boolean onQueryTextSubmit(String s) {
////                    Utility.shortToast(getApplicationContext(), "textsubmit");
////                    cursor=db.selectBySearch(s);
////                    if (cursor==null){
////                        Utility.shortToast(getApplicationContext(), "No record found");
////                    }else{
////                        Utility.shortToast(getApplicationContext(), "Record founds");
////                    }
////                    getReferencesForViewItemsRecyclerView();
////                    addValuesToArrayList(cursor);
////
////                    return false;
////
////                    Utility.shortToast(getApplicationContext(), "textsubmit");
////                    cursor=db.selectBySearch(s);
////                    if (cursor==null){
////                        Utility.shortToast(getApplicationContext(), "No record found");
////                    }else{
////                        Utility.shortToast(getApplicationContext(), "Record founds");
////                    }
////                    customAdapter.swapCursor(cursor);
//
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String s) {
//
//                    return false;
//                }
//
//            });
//
//        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.search) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        viewItems();
        // TODO Auto-generated method stub
        return super.onKeyDown(keyCode, event);
    }
}
