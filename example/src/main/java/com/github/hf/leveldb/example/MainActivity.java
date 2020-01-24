package com.github.hf.leveldb.example;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.github.hf.leveldb.LevelDB;
import com.github.hf.leveldb.exception.LevelDBException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ArrayList<String> permissions = new ArrayList<>();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            ActivityCompat.requestPermissions(
                    this,
                    permissions.toArray(new String[]{}),
                    0);
        }

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LevelDB levelDB = null;
                try {
                    String path = Environment.getExternalStorageDirectory().getPath() + "/sml/banco.db";

                    levelDB = LevelDB.open(path, LevelDB.configure().createIfMissing(true));

                    levelDB.put("leveldb".getBytes(), "Is awesome!".getBytes());
                    byte[] value = levelDB.get("leveldb".getBytes());

                    levelDB.put("magic".getBytes(), new byte[]{0, 1, 2, 3, 4});
                    byte[] magic = levelDB.get("magic".getBytes());

                    levelDB.close(); // closing is a must!
                } catch (LevelDBException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
