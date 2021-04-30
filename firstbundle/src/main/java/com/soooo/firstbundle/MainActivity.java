package com.soooo.firstbundle;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.taobao.atlas.framework.Atlas;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.osgi.framework.BundleException;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_first);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.startremoteBundle();
            }
        }).start();

    }


    private void startremoteBundle(){
        try {
            Atlas.getInstance().installBundle("com.soooo.secondbundle", getAssets().open("libcom_soooo_secondbundle.so"));
        } catch (BundleException var8) {
            Log.i("gddtest", "远程bundle 安装失败:" + var8.getMessage());
            Toast.makeText(this, " 远程bundle 安装失败，" + var8.getMessage(), Toast.LENGTH_LONG).show();
            var8.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent();
        intent.setClassName(this, "com.soooo.secondbundle.FullscreenActivity");
        startActivity(intent);

    }


}
