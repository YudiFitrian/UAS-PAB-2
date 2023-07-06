package com.si61.listoutletmixueplg.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.si61.listoutletmixueplg.API.APIRequestData;
import com.si61.listoutletmixueplg.API.RetroServer;
import com.si61.listoutletmixueplg.Model.ModelResponse;
import com.si61.listoutletmixueplg.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahActivity extends AppCompatActivity {
    private EditText etNama, etLokasi, etDeskripsi, etUrlmap;
    private Button btnSimpan;
    private String nama, lokasi, deskripsi, urlmap;
    private String yId, yNama, yLokasi, yDeskripsi, yUrlmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        etNama = findViewById(R.id.et_nama);
        etLokasi = findViewById(R.id.et_lokasi);
        etDeskripsi = findViewById(R.id.et_deskripsi);
        etUrlmap = findViewById(R.id.et_url);
        btnSimpan = findViewById(R.id.btn_simpan);

        Intent tangkap = getIntent();
        yId = tangkap.getStringExtra("xId");
        yNama = tangkap.getStringExtra("xNama");
        yLokasi = tangkap.getStringExtra("xLokasi");
        yDeskripsi = tangkap.getStringExtra("yDeskripsi");
        yUrlmap = tangkap.getStringExtra("xUrlmap");

        etNama.setText(yNama);
        etLokasi.setText(yLokasi);
        etDeskripsi.setText(yDeskripsi);
        etUrlmap.setText(yUrlmap);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                lokasi = etLokasi.getText().toString();
                deskripsi = etDeskripsi.getText().toString();
                urlmap = etUrlmap.getText().toString();

                if (nama.trim().isEmpty()) {
                    etNama.setError("Nama Harus Diisi");
                } else if (lokasi.trim().isEmpty()) {
                    etLokasi.setError("Lokasi Harus Diisi");
                } else if (deskripsi.trim().isEmpty()) {
                    etDeskripsi.setError("Deskripsi Harus Diisi");
                } else if (urlmap.trim().isEmpty()) {
                    etUrlmap.setError("UrlMap Harus Diisi");
                } else {
                    prosesUbah();
                }
            }
        });
    }

    private void prosesUbah() {
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardUpdate(yId, nama, lokasi, deskripsi, urlmap);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this, "kode : " + kode + "Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(UbahActivity.this, "Gagal Menghubungi Server!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}