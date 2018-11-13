package com.farizdotid.belajarautocomplete;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class AutoCompleteDBActivity extends AppCompatActivity {

    private static final String TAG = AutoCompleteDBActivity.class.getSimpleName();
    private AutoCompleteTextView actext_namaprov;
    private AutoCompleteTextView autoCompleteProvincesTextView;
    private DBHelperNamaProvinsi dbHelperNamaProvinsi;
    private ArrayAdapter autoCompleteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autocomplete_db_main);

        dbHelperNamaProvinsi = new DBHelperNamaProvinsi(this);
        dbHelperNamaProvinsi.loadContent();

        initAutoCompleteNamaProvDB();
    }

    private void initAutoCompleteNamaProvDB() {
        autoCompleteProvincesTextView = (AutoCompleteTextView) findViewById(R.id.actext_namaprovdb);

//        final String[] namaProvDB = dbHelperNamaProvinsi.SelectAllDataNamaProv();
        final String[] namaProvDB = {""};
        autoCompleteAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, namaProvDB);
        autoCompleteProvincesTextView.setAdapter(autoCompleteAdapter);
        autoCompleteProvincesTextView.setThreshold(1);
        autoCompleteProvincesTextView.dismissDropDown();

        /*autoCompleteProvincesTextView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String chars = keyEvent.getCharacters();
                Log.d("MyAPP", "chars:" + chars);
                return false;
            }
        });*/

        autoCompleteProvincesTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Log.d("MyAPP", "beforeTextChanged chars:" + charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("MyAPP", "onTextChanged chars:" + charSequence.toString());
                final String[] namaProvDB = dbHelperNamaProvinsi.getByName(charSequence.toString());
                if (namaProvDB != null) {
                    Log.d("MyAPP", "namaProvDB.length:" + namaProvDB.length);
                    autoCompleteAdapter.notifyDataSetChanged();
                    autoCompleteAdapter = new ArrayAdapter(AutoCompleteDBActivity.this, android.R.layout.simple_list_item_1, namaProvDB);
                    autoCompleteProvincesTextView.setAdapter(autoCompleteAdapter);
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {
//                Log.d("MyAPP", "afterTextChanged");
            }
        });


        autoCompleteProvincesTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), (CharSequence) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
