package com.example.hp.barcodesand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonscan, buttonscan1;
    private TextView judul,penerbit,nama,kelas;
    private IntentIntegrator intentIntegrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonscan = (Button) findViewById(R.id.buttonscan);
        buttonscan1 = (Button) findViewById(R.id.buttonscan1);
        judul = (TextView) findViewById(R.id.judul);
        penerbit = (TextView) findViewById(R.id.penerbit);
        nama = (TextView) findViewById(R.id.nama);
        kelas = (TextView) findViewById(R.id.kelas);
        buttonscan.setOnClickListener(this);
        buttonscan1.setOnClickListener(this);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "Hasil tidak ditemukan", Toast.LENGTH_SHORT).show();
            } else {
                // jika qrcode berisi data
                try{
                    // converting the data json
                    JSONObject object = new JSONObject(result.getContents());
                    // atur nilai ke textviews

                    nama.setText(object.getString("nama"));
                    kelas.setText(object.getString("kelas"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                }
                try {
                    JSONObject object = new JSONObject(result.getContents());
                    // atur nilai ke textviews
                    judul.setText(object.getString("judul"));
                    penerbit.setText(object.getString("penerbit"));
                }

                catch (JSONException e){
                    e.printStackTrace();
                    // jika format encoded tidak sesuai maka hasil
                    // ditampilkan ke toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {

        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }
}
