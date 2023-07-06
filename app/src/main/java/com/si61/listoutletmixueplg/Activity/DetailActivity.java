package com.si61.listoutletmixueplg.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.si61.listoutletmixueplg.R;

public class DetailActivity extends AppCompatActivity {
    private TextView tvNama, tvLokasi, tvDeskripsi, tvUrlMap;
    private String yId, yNama, yLokasi, yDeskripsi, yUrlmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvNama = findViewById(R.id.tv_nama);
        tvLokasi = findViewById(R.id.tv_lokasi);
        tvDeskripsi = findViewById(R.id.tv_deskripsi);
        tvUrlMap = findViewById(R.id.tv_urlmap);

        Intent tangkap = getIntent();

        yId = tangkap.getStringExtra("xId");
        yNama = tangkap.getStringExtra("xNama");
        yLokasi = tangkap.getStringExtra("xLokasi");
        yDeskripsi = tangkap.getStringExtra("xDeskripsi");
        yUrlmap = tangkap.getStringExtra("xUrlMap");



        tvNama.setText(yNama);
        tvLokasi.setText(yLokasi);
        tvDeskripsi.setText(yDeskripsi);
        tvUrlMap.setText("UrlMap : " + yUrlmap);
    }
}