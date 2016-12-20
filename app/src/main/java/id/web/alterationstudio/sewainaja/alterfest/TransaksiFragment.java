package id.web.alterationstudio.sewainaja.alterfest;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import id.web.alterationstudio.sewainaja.alterfest.Config.Config;
import id.web.alterationstudio.sewainaja.alterfest.Response.SetTransaksi;
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
public class TransaksiFragment extends Fragment implements View.OnClickListener, Callback<SetTransaksi> {
    Calendar calendar;
    int year, month, day;
    EditText nominalText, kategoriText, catatanText, tanggalText;
    DatePickerDialog tanggalDialog;
    Spinner spinnerKategori;
    Button postingBtn;

    List<String> kategoriList = new ArrayList<String>();
    List<String> kategoriIDList = new ArrayList<String>();
    String selectKategoriID, tanggalSelected;
    ProgressDialog progressDialog;

    public TransaksiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Setting kalender popup
         */
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        View view = inflater.inflate(R.layout.fragment_transaksi, container, false);

        nominalText = (EditText) view.findViewById(R.id.etNominalTransaksi);
        final TextWatcher nominalFormat = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                nominalFormat();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        nominalText.addTextChangedListener(nominalFormat);

        tanggalText = (EditText) view.findViewById(R.id.etTanggal);
        tanggalText.setOnClickListener(this);

        catatanText = (EditText) view.findViewById(R.id.etCatatan);

        postingBtn = (Button) view.findViewById(R.id.btnSubmit);
        postingBtn.setOnClickListener(this);

        progressDialog = new ProgressDialog(this.getActivity());

        /**
         * Looping kategori berdasarkan database
         */
        spinnerKategori = (Spinner) view.findViewById(R.id.kategoriSelect);
        String json = sharedPref.getString("kategoriTransaksi",null);
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i<jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                kategoriList.add(obj.getString("nama"));
                kategoriIDList.add(obj.getString("id"));
                selectKategoriID = obj.getString("id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, kategoriList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinnerKategori.setAdapter(arrayAdapter);
        spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectKategoriID = kategoriIDList.get(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /**
         * Setting Tanggal
         */
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        tanggalDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                showDate(i,i1,i2);
            }
        },year,month,day);

        return view;
    }

    @Override
    public void onResponse(Call<SetTransaksi> call, Response<SetTransaksi> response) {
        SetTransaksi transaksi = response.body();
        progressDialog.hide();
        if(transaksi.getStatus()){
            nominalText.setText("");
            catatanText.setText("");

            SharedPreferences sharedPref = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            int nominalResponse = transaksi.getSaldo();
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("userSaldo",nominalResponse);
            editor.apply();
            Toast.makeText(this.getActivity(),transaksi.getMsg()+transaksi.getSaldo(),Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(),"Error, Silahkan Coba Kembali",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<SetTransaksi> call, Throwable t) {

    }

    @Override
    public void onClick(View view) {
        if(view == tanggalText){
            tanggalDialog.show();
        } else if(view == postingBtn){
            String nominalStr = nominalText.getText().toString();
            String catatanStr = catatanText.getText().toString();
            String tanggalStr = tanggalText.getText().toString();

            if(nominalStr.matches("") || catatanStr.matches("") || tanggalStr.matches("")){
                Toast.makeText(this.getActivity(),"Field Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
            } else{
                /**
                 * Posting Transaksi via API
                 */

                progressDialog.setTitle("Tambah Transaksi");
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                SharedPreferences sharedPref = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                int nominal = Integer.parseInt(nominalStr);

                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Config.getServerUrl())
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TransaksiService transaksiService = retrofit.create(TransaksiService.class);
                Call<SetTransaksi> setTransaksiCall = transaksiService.postTransaksi(sharedPref.getString("userKey",""),
                        Config.getApiKey(),catatanStr,tanggalSelected,nominal,"From My Android",selectKategoriID);
                setTransaksiCall.enqueue(this);
            }

        }
    }

    private void showDate(int year, int month, int day){
        tanggalSelected = year+"-"+month+"-"+day;
        tanggalText.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }
}
