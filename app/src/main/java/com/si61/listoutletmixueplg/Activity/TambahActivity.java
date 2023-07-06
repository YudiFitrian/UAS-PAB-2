package com.si61.listoutletmixueplg.Activity;

import androidx.appcompat.app.AppCompatActivity;

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

public class TambahActivity extends AppCompatActivity {
    private EditText etNama, etLokasi, etDeskripsi, etUrlmap;
    private Button btnSimpan;
    private String nama, lokasi, deskripsi, urlmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.et_nama);
        etLokasi = findViewById(R.id.et_lokasi);
        etDeskripsi = findViewById(R.id.et_deskripsi);
        etUrlmap = findViewById(R.id.et_url);
        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                lokasi = etLokasi.getText().toString();
                deskripsi = etDeskripsi.getText().toString();
                urlmap = etUrlmap.getText().toString();

                if(nama.trim().isEmpty()){
                    etNama.setError("Nama Harus Diisi");
                }
                else if(lokasi.trim().isEmpty()){
                    etLokasi.setError("Lokasi Harus Diisi");
                }
                else if(deskripsi.trim().isEmpty()){
                    etDeskripsi.setError("Deskripsi Harus Diisi");
                }
                else if(urlmap.trim().isEmpty()){
                    etUrlmap.setError("UrlMap Harus Diisi");
                }
                else{
                    prosesSimpan();
                }
            }
        });
    }

    private void prosesSimpan(){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardCreate(nama, lokasi, deskripsi, urlmap);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "kode : " + kode + "Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menghubungi Server!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}