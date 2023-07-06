package com.si61.listoutletmixueplg.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.si61.listoutletmixueplg.API.APIRequestData;
import com.si61.listoutletmixueplg.API.RetroServer;
import com.si61.listoutletmixueplg.Adapter.AdapterOutlet;
import com.si61.listoutletmixueplg.Model.ModelOutlet;
import com.si61.listoutletmixueplg.Model.ModelResponse;
import com.si61.listoutletmixueplg.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvOutlet;
    private ProgressBar pbOutlet;
    private FloatingActionButton fabtambah;

    private RecyclerView.Adapter adOutlet;
    private RecyclerView.LayoutManager lmOutlet;
    private List<ModelOutlet> listOutlet = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvOutlet = findViewById(R.id.rv_outlet);
        pbOutlet = findViewById(R.id.pb_outlet);
        fabtambah = findViewById(R.id.fab_tambah);

        lmOutlet = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvOutlet.setLayoutManager(lmOutlet);

        fabtambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });
    }

    public void retrieveOutlet(){
        pbOutlet.setVisibility(View.VISIBLE);

        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardRetrieve();

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listOutlet = response.body().getData();

                adOutlet = new AdapterOutlet(MainActivity.this, listOutlet);
                rvOutlet.setAdapter(adOutlet);
                adOutlet.notifyDataSetChanged();

                pbOutlet.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error : Gagal Menghubungkan Server", Toast.LENGTH_SHORT).show();
                pbOutlet.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveOutlet();
    }
}