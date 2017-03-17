package com.example.roberasd.signature.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.example.roberasd.signature.R;
import com.example.roberasd.signature.utils.Signature;

import java.io.File;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements Signature.SignatureListener {

    public static final String MEDIA_DIRECTORY = "signature/signatures";
    LinearLayout mContent;
    Signature mSignature;
    Button mSaveSignature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prepareDirectory();

        mContent = (LinearLayout) findViewById(R.id.linearLayout);
        mSaveSignature = (Button) findViewById(R.id.save_signature);
        mSignature = new Signature(this, this);

        mSignature.setBackgroundColor(Color.WHITE);
        mContent.addView(mSignature, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mSignature.clear();
                mSaveSignature.setEnabled(false);
            }
        });

        mSaveSignature.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (requestPermissions()){
                    mContent.setDrawingCacheEnabled(true);
                    if (mSignature.save(mContent)) {
                        Toast.makeText(MainActivity.this, "Signature saved it succesfuly", Toast.LENGTH_SHORT).show();
                        mSignature.clear();
                        mSaveSignature.setEnabled(false);

                    } else
                        Toast.makeText(MainActivity.this, "Something got wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.see_all_signatures).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignaturesSavedActivity.class));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mSignature.save(mContent);
            } else
                Toast.makeText(MainActivity.this, "Something got wrong -Permissions denied-", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(MainActivity.this, "Something got wrong", Toast.LENGTH_SHORT).show();
    }

    private boolean requestPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if ((checkSelfPermission(WRITE_EXTERNAL_STORAGE)) == PackageManager.PERMISSION_GRANTED)
            return true;

        if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
            Snackbar.make(findViewById(android.R.id.content), "Permissions must to be granted",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, 100);
                }
            });
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, 100);
        }

        return false;
    }

    private boolean prepareDirectory() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();
        if (!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        return isDirectoryCreated;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSignStart() {
        mSaveSignature.setEnabled(true);
    }
}
