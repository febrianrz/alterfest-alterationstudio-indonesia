package id.web.alterationstudio.sewainaja.alterfest;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.web.alterationstudio.sewainaja.alterfest.Adapter.RingkasanAdapter;
import id.web.alterationstudio.sewainaja.alterfest.Config.Config;
import id.web.alterationstudio.sewainaja.alterfest.Response.GetTransaksi;
import id.web.alterationstudio.sewainaja.alterfest.Response.GetTransaksiData;
import id.web.alterationstudio.sewainaja.alterfest.Service.TransaksiService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class RingkasanFragment extends Fragment implements View.OnClickListener, Callback<GetTransaksi> {

    java.util.Calendar calendar;
    int year, month, day;
    private DatePickerDialog tanggalAwalDialog, tanggalAkhirDialog;
    private EditText tanggalAwalEt, tanggalAkhirEt;
    private RecyclerView recyclerView;
    private RingkasanAdapter adapter;
    private List<GetTransaksiData> transaksiDatas = new ArrayList<>();
    private ProgressDialog progressDialog;

    public RingkasanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_ringkasan, container, false);
        progressDialog = new ProgressDialog(this.getActivity());



        recyclerView = (RecyclerView) view.findViewById(R.id.listRingkasanView);
        this.setRingkasanList();

        return view;
    }

    private void setRingkasanList(){
        progressDialog.show();
        GetTransaksiData transaksiDataList = new GetTransaksiData();

        /**
         * Ambil dari API
         */
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.getServerUrl())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        TransaksiService transaksiService = retrofit.create(TransaksiService.class);
        Call<GetTransaksi> getTransaksiCall = transaksiService.postGetTransaksi(sharedPref.getString("userKey",""),Config.getApiKey());
        getTransaksiCall.enqueue(this);

    }

    @Override
    public void onResponse(Call<GetTransaksi> call, Response<GetTransaksi> response) {
        GetTransaksi getTransaksi = response.body();
        if(!getTransaksi.getStatus()){
            Toast.makeText(this.getActivity(),getTransaksi.getMsg(),Toast.LENGTH_LONG).show();
        } else {
            List<GetTransaksiData> getTransaksiData;
            getTransaksiData = getTransaksi.getData();
            for (GetTransaksiData a : getTransaksiData){
                this.transaksiDatas.add(a);
            }

            /**
             * Listing recylerview list ringkasan
             */
            adapter = new RingkasanAdapter(transaksiDatas);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext().getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            DividerItemDecoration mDividerItemDecoration;
            mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.HORIZONTAL);
            recyclerView.addItemDecoration(mDividerItemDecoration);
            recyclerView.setAdapter(adapter);

        }
        progressDialog.hide();
    }

    @Override
    public void onFailure(Call<GetTransaksi> call, Throwable t) {

    }

    @Override
    public void onClick(View view) {
        if(view == tanggalAwalEt){
            tanggalAwalDialog.show();
        } else if(view == tanggalAkhirEt){
            tanggalAkhirDialog.show();
        }
    }
}
