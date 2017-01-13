package com.example.student.testphase2_ultimatesmsblocker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class RegisterUser extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Database db;
    EditText id, name, password, email, contact;
    TextInputLayout idLayout, nameLayout, passwordLayout, emailLayout, contactLayout;
    String type;
    Button registerButton;
    SessionManager preference;;
    SharedPreferences editor;
    TextView title;
    private final String PREFERENCE_EDIT = "edit";
    private final String KEY_ID = "id";
    private final String KEY_NAME = "name";
    private final String KEY_PASS = "password";
    private final String KEY_EMAIL = "email";
    private final String KEY_CONTACT = "contact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new Database(this);
        getReference();

        preference = new SessionManager();
        editor = getSharedPreferences(PREFERENCE_EDIT, Context.MODE_PRIVATE);
        if(editor.contains(KEY_ID)){
            String idPre = preference.getDatePreferences(RegisterUser.this, PREFERENCE_EDIT, KEY_ID);
            String namePre = preference.getDatePreferences(RegisterUser.this, PREFERENCE_EDIT, KEY_NAME);
            String passPre = preference.getDatePreferences(RegisterUser.this, PREFERENCE_EDIT, KEY_PASS);
            String emailPre = preference.getDatePreferences(RegisterUser.this, PREFERENCE_EDIT, KEY_EMAIL);
            String contactPre = preference.getDatePreferences(RegisterUser.this, PREFERENCE_EDIT, KEY_CONTACT);

            id.setText(idPre);
            name.setText(namePre);
            password.setText(passPre);
            email.setText(emailPre);
            contact.setText(contactPre);
            registerButton.setText("UPDATE");
            title.setText("Update User");
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editor.contains(KEY_ID)) {
                    String idEdit = preference.getDatePreferences(RegisterUser.this, PREFERENCE_EDIT, KEY_ID);
                    if(validateEditText()) {
                        if (db.updateUser(idEdit,
                                id.getText().toString().trim(),
                                name.getText().toString().trim(),
                                password.getText().toString().trim(),
                                email.getText().toString().trim(),
                                contact.getText().toString().trim(),
                                type,
                                Utility.currentTimeInDateFormat(),
                                Utility.currentTimeInDateFormat())) {
                            Utility.successSnackBar(name, "User edited", getApplicationContext());
                            removeSharedPreferences();
                            Utility.setResultActivity(RegisterUser.this);
                            finish();
                        } else {

                        }
                    }
                } else {
                    if (validateEditText()) {
                        if (db.addNewUser(id.getText().toString().trim(),
                                name.getText().toString().trim(),
                                password.getText().toString().trim(),
                                email.getText().toString().trim(),
                                contact.getText().toString().trim(),
                                type,
                                Utility.currentTimeInDateFormat(),
                                Utility.currentTimeInDateFormat())) {
                            Utility.shortToast(RegisterUser.this, "User registered");
                            id.setText("");
                            name.setText("");
                            password.setText("");
                            email.setText("");
                            contact.setText("");
                            Utility.setResultActivity(RegisterUser.this);
                        } else {
                            Utility.shortToast(RegisterUser.this, "Error");
                        }
                    }
                }
            }
        });
    }

    private void getReference() {
        title = (TextView) findViewById(R.id.title);
        id = (EditText) findViewById(R.id.id);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        contact = (EditText) findViewById(R.id.contact);
        name = (EditText) findViewById(R.id.name);
        idLayout = (TextInputLayout) findViewById(R.id.idLayout);
        nameLayout = (TextInputLayout) findViewById(R.id.nameLayout);
        passwordLayout = (TextInputLayout) findViewById(R.id.passwordLayout);
        contactLayout = (TextInputLayout) findViewById(R.id.contactLayout);
        emailLayout = (TextInputLayout) findViewById(R.id.emailLayout);
        id.addTextChangedListener(new addNewItemTextWatcher(id));
        name.addTextChangedListener(new addNewItemTextWatcher(name));
        password.addTextChangedListener(new addNewItemTextWatcher(password));
        email.addTextChangedListener(new addNewItemTextWatcher(email));
        contact.addTextChangedListener(new addNewItemTextWatcher(contact));

        Spinner spinner = (Spinner) findViewById(R.id.typespinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, R.layout.adapter_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        registerButton = (Button) findViewById(R.id.btn_register);
    }

    private class addNewItemTextWatcher implements TextWatcher {
        private View view;

        private addNewItemTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.id:
                    Utility.validateEditText(id, idLayout, "Enter valid id");
                    break;
                case R.id.name:
                    Utility.validateEditText(name, nameLayout, "Enter valid name");
                    break;
                case R.id.password:
                    Utility.validateEditText(password, passwordLayout, "Enter valid password");
                    break;
                case R.id.contact:
                    Utility.validateEditText(contact, contactLayout, "Enter valid contact");
                    break;
                case R.id.email:
                    Utility.validateEditText(email, emailLayout, "Enter valid email");
                    break;
            }

        }
    }

    private boolean validateEditText() {
        if(Utility.validateEditText(id, idLayout, "Enter valid id") &&
                Utility.validateEditText(name, nameLayout, "Enter valid name") &&
                Utility.validateEditText(password, passwordLayout, "Enter valid password") &&
                Utility.validateEditText(contact, contactLayout, "Enter valid contact") &&
                Utility.validateEditText(email, emailLayout, "Enter valid email")){
            return true;
        }
            return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.finish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            removeSharedPreferences();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        Spinner spinner = (Spinner) adapterView;
        if (spinner.getId() == R.id.typespinner) {
            String typeName = String.valueOf(adapterView.getItemAtPosition(pos));
            String normalUser = "Normal User";
            if(typeName.contains(normalUser) || typeName.equals(normalUser)) {
                type = "1";
            } else {
                type = "2";
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {
        removeSharedPreferences();
        finish();
    }

    private void removeSharedPreferences() {
        SharedPreferences.Editor preferences = getSharedPreferences(PREFERENCE_EDIT, Context.MODE_PRIVATE).edit();
        preferences.remove(KEY_ID);
        preferences.remove(KEY_NAME);
        preferences.remove(KEY_PASS);
        preferences.remove(KEY_EMAIL);
        preferences.remove(KEY_CONTACT);
        preferences.apply();
    }
}
