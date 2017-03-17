package com.example.roberasd.signature.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.roberasd.signature.R;

import java.io.File;
import java.util.List;

/**
 * Created by roberasd on 17/03/17.
 */

public class SignaturesAdapter extends BaseAdapter {

    private Context mContext;
    private File[] mFiles;

    public SignaturesAdapter(Context context, File[] files){
        mContext = context;
        mFiles = files;
    }

    @Override
    public int getCount() {
        if (mFiles == null)
            return 0;
        else
            return mFiles.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        File file = mFiles[position];

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_signatrues, null);
        }

        ImageView signature = (ImageView) convertView.findViewById(R.id.signature_image);
        signature.setImageURI(Uri.parse(file.getPath()));

        return convertView;
    }
}
