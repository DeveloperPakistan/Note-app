package com.devpk.note_app.activity.About;

import com.devpk.note_app.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devpk.note_app.Constant.Constant;
import com.devpk.note_app.SharedPref.Setting;

public class AboutActivity extends AppCompatActivity {


    private Toolbar toolbarAbout;
    private TextView company, email, website, contact;
    private LinearLayout ll_share, ll_rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Setting.Dark_Mode) {
            //setTheme(R.style.AppTheme2);
        } else {
            setTheme(R.style.Base_Theme_Noteapp);
        }
        setContentView(R.layout.activity_about);

        toolbarAbout = findViewById(R.id.toolbar_ab);
        setSupportActionBar(toolbarAbout);
        setTitle(getResources().getString(R.string.about));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarAbout.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        company = findViewById(R.id.company);
        email = findViewById(R.id.email);
        website = findViewById(R.id.website);
        contact = findViewById(R.id.contact);

        company.setText(Constant.company);
        email.setText(Constant.email);
        website.setText(Constant.website);
        contact.setText(Constant.contact);

        ll_share = findViewById(R.id.ll_share);
        ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appName = getPackageName();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appName)));
            }
        });

        ll_rate = findViewById(R.id.ll_rate);
        ll_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appName = getPackageName();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appName)));
            }
        });

        TextView version = findViewById(R.id.version);
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String myVersion = pInfo.versionName;
            version.setText(myVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}