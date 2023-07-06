package com.si61.listoutletmixueplg.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.si61.listoutletmixueplg.API.APIRequestData;
import com.si61.listoutletmixueplg.API.RetroServer;
import com.si61.listoutletmixueplg.Activity.MainActivity;
import com.si61.listoutletmixueplg.Activity.TambahActivity;
import com.si61.listoutletmixueplg.Activity.UbahActivity;
import com.si61.listoutletmixueplg.Model.ModelOutlet;
import com.si61.listoutletmixueplg.Model.ModelResponse;
import com.si61.listoutletmixueplg.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterOutlet extends RecyclerView.Adapter<AdapterOutlet.VHOutlet> {
    private Context ctx;
    private List<ModelOutlet> listOutlet;

    public AdapterOutlet(Context ctx, List<ModelOutlet> listOutlet) {
        this.ctx = ctx;
        this.listOutlet = listOutlet;
    }

    @NonNull
    @Override
    public VHOutlet onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(ctx).inflate(R.layout.list_item_outlet, parent, false);
        return new VHOutlet(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHOutlet holder, int position) {
        ModelOutlet MW = listOutlet.get(position);

        holder.tvId.setText(MW.getId());
        holder.tvNama.setText(MW.getNama());
        holder.tvLokasi.setText(MW.getLokasi());
        holder.tvDeskripsi.setText(MW.getDeskripsi());
        holder.tvUrlMap.setText(MW.getUrlmap());
    }

    @Override
    public int getItemCount() {
        return listOutlet.size();
    }

    public class VHOutlet extends RecyclerView.ViewHolder{
        TextView tvId, tvNama, tvLokasi, tvDeskripsi, tvUrlMap;
        public VHOutlet(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvLokasi = itemView.findViewById(R.id.tv_lokasi);
            tvDeskripsi = itemView.findViewById(R.id.tv_deskripsi);
            tvUrlMap = itemView.findViewById(R.id.tv_urlmap);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian");
                    pesan.setMessage("Anda memilih"+ tvNama.getText().toString() +"Operasi apa yang akan dilakukan?");

                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent kirim = new Intent(ctx, UbahActivity.class);
                            kirim.putExtra("xId", tvId.getText().toString());
                            kirim.putExtra("xNama", tvNama.getText().toString());
                            kirim.putExtra("xLokasi", tvLokasi.getText().toString());
                            kirim.putExtra("xDeskripsi", tvDeskripsi.getText().toString());
                            kirim.putExtra("xUrlmap", tvUrlMap.getText().toString());
                            ctx.startActivity(kirim);
                        }
                    });

                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            prosesHapus(tvId.getText().toString());
                        }
                    });

                    pesan.show();
                    return false;
                }
            });
        }

        void prosesHapus(String id){
            APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = API.ardDelete(id);

            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx , "kode : " + kode + "Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity) ctx).retrieveOutlet();
                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Tidak Bisa Terhubung", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
