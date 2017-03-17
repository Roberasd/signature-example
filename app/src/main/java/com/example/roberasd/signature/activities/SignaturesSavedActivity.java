package com.example.roberasd.signature.activities;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.roberasd.signature.R;
import com.example.roberasd.signature.adapters.SignaturesAdapter;

import java.io.File;

public class SignaturesSavedActivity extends AppCompatActivity {

    private SignaturesAdapter mSignaturesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signatures_saved);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ListView signatureList = (ListView) findViewById(R.id.signatures_list);

        File tempdir = new File(Environment.getExternalStorageDirectory(), MainActivity.MEDIA_DIRECTORY);

        if (tempdir.exists()) {
            if (tempdir.isDirectory()) {
                File[] files = tempdir.listFiles();
                mSignaturesAdapter = new SignaturesAdapter(this, files);
            }
        }

        if (mSignaturesAdapter != null)
            signatureList.setAdapter(mSignaturesAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
