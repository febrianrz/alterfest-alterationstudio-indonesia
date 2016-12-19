package id.web.alterationstudio.sewainaja.alterfest.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.web.alterationstudio.sewainaja.alterfest.R;
import id.web.alterationstudio.sewainaja.alterfest.Response.GetTransaksiData;

/**
 * Created by febrian on 12/19/16.
 */
public class RingkasanAdapter extends RecyclerView.Adapter<RingkasanAdapter.MyViewHolder> {
    List<GetTransaksiData> transaksiDatas;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transaksi_row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GetTransaksiData transaksiData = transaksiDatas.get(position);
        holder.judul.setText(transaksiData.getJudul());
        String s = (String.format("%,d", transaksiData.getNominal())).replace(',', ' ');
        holder.nominal.setText("Rp"+s);
        holder.jenis.setText(transaksiData.getKategori());
        holder.tanggal.setText(transaksiData.getTanggal());
    }

    @Override
    public int getItemCount() {
        return transaksiDatas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView judul,tanggal,nominal,jenis;
        public MyViewHolder(View itemView) {
            super(itemView);
            judul = (TextView) itemView.findViewById(R.id.judul);
            nominal = (TextView) itemView.findViewById(R.id.nominal);
            tanggal = (TextView) itemView.findViewById(R.id.tanggal);
            jenis = (TextView) itemView.findViewById(R.id.jenis);
        }
    }

    public RingkasanAdapter(List<GetTransaksiData> transaksiDatas){
        this.transaksiDatas = transaksiDatas;
    }

}
