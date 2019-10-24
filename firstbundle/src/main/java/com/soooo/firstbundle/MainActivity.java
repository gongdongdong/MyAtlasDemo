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

        startremoteBundle();

    }


    private void startremoteBundle(){
        String remoteDir = Environment.getExternalStorageDirectory().getPath() + "/libcom_soooo_secondbundle.so";
        File remoteBundleFile = new File(remoteDir);
        String path = "";
        if (!remoteBundleFile.exists()) {
            Toast.makeText(this, " 远程bundle不存在，请确定 : " + remoteBundleFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            return;
        }

        path = remoteBundleFile.getAbsolutePath();
        PackageInfo info = this.getPackageManager().getPackageArchiveInfo(path, 0);

        try {
            Log.i((String)null, "getBundleClassLoader start:");
            ClassLoader classLoader = Atlas.getInstance().getBundleClassLoader(remoteDir);
            Log.i((String)null, "getBundleClassLoader end:" + remoteDir);
            if (null != classLoader) {
                Log.i((String)null, "uninstall exe:" + remoteDir);
                Atlas.getInstance().uninstallBundle(remoteDir);
            } else {
                Log.i((String)null, "uninstall no:" + remoteDir);
            }
        } catch (BundleException var9) {
            var9.printStackTrace();
            Log.i((String)null, "uninstall error:" + var9.getMessage() + "xxx:" + remoteDir);
        }

        try {
            Log.i((String)null, "installBundle start:" + info.packageName);
            Atlas.getInstance().installBundle(info.packageName, new File(path));
        } catch (BundleException var8) {
            Log.i((String)null, "远程bundle 安装失败:" + var8.getMessage());
            Toast.makeText(this, " 远程bundle 安装失败，" + var8.getMessage(), Toast.LENGTH_SHORT).show();
            var8.printStackTrace();
        }

        Intent intent = new Intent();
        intent.setClassName(this, "com.soooo.secondbundle.FullscreenActivity");
        startActivity(intent);

    }


}
