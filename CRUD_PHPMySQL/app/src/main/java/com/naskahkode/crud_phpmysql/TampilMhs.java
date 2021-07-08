package com.naskahkode.crud_phpmysql;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;


import android.widget.ArrayAdapter;


public class TampilMhs extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextId;
    private EditText editTextName;
    private EditText editTextNRP;
//    private EditText editTextKelas;

    private Spinner spinnerKelas2;
    private EditText editTextBlog;
    private Button buttonUpdate;
    private Button buttonDelete;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_mhs);
        Intent intent = getIntent();
        id = intent.getStringExtra(konfigurasi.MHS_ID);
        editTextId = (EditText) findViewById(R.id.editTextId);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextNRP = (EditText) findViewById(R.id.editTextNRP);
//        editTextKelas = (EditText) findViewById(R.id.editTextKelas);
        spinnerKelas2 = (Spinner) findViewById(R.id.spinnerKelas2);

        editTextBlog = (EditText) findViewById(R.id.editTextBlog);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        editTextId.setText(id);
        getMhs();
    }
    private void getMhs(){
        class GetMhs extends AsyncTask<Void,Void,String>{ ProgressDialog loading;
            @Override
            protected void onPreExecute() { super.onPreExecute();
                loading = ProgressDialog.show(TampilMhs.this,"Fetching...","Wait...",false,false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showMhs(s);
            }
            @Override
            protected String doInBackground(Void... params) { RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_EMP,id);
                return s;
            }
        }
        GetMhs ge = new GetMhs();
        ge.execute();
    }
    private void showMhs(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String name = c.getString(konfigurasi.TAG_NAMA);
            String nrp = c.getString(konfigurasi.TAG_NRP);
            String kelas = c.getString(konfigurasi.TAG_KELAS);
            String blog = c.getString(konfigurasi.TAG_BLOG);
            editTextName.setText(name);
            editTextNRP.setText(nrp);
//            editTextKelas.setText(kelas);
            spinnerKelas2 = (Spinner) findViewById(R.id.spinnerKelas2);
            String[] spinnerItem = new String[]{"TI-1","TI-2","TI-3","TI-Mlm"};
            ArrayAdapter<String> adapter = new  ArrayAdapter<>(this, android.R.layout.simple_spinner_item,spinnerItem);

            spinnerKelas2.setAdapter(adapter);
            editTextBlog.setText(blog);
        }
        catch (JSONException e) { e.printStackTrace();
        }
    }
    private void updateMhs(){
        final String name = editTextName.getText().toString().trim();
        final String nrp = editTextNRP.getText().toString().trim();

//        final String kelas = editTextKelas.getText().toString().trim();
        final String kelas =(String) spinnerKelas2.getSelectedItem();
        final String blog = editTextBlog.getText().toString().trim();
        class UpdateMhs extends AsyncTask<Void,Void,String>{ ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilMhs.this,"Updating...","Wait...",false,
                        false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilMhs.this,s,Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... params) { HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_MHS_ID,id);
                hashMap.put(konfigurasi.KEY_MHS_NAMA,name);
                hashMap.put(konfigurasi.KEY_MHS_NRP,nrp);
                hashMap.put(konfigurasi.KEY_MHS_KELAS,kelas);
                hashMap.put(konfigurasi.KEY_MHS_BLOG,blog);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_EMP,hashMap);
                return s;
            }
        }
        UpdateMhs ue = new UpdateMhs();
        ue.execute();
    }
    private void deleteMhs(){
        class DeleteMhs extends AsyncTask<Void,Void,String> { ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilMhs.this, "Updating...", "Tunggu...", false, false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilMhs.this, s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... params) { RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_DELETE_EMP, id);
                return s;
            }
        }
        DeleteMhs de = new DeleteMhs();
        de.execute();
    }
    private void confirmDeleteMhs(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Data ini?");
        alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteMhs();
                        startActivity(new Intent(TampilMhs.this,TampilSemuaMhs.class));
                    }
                }
        );
        alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) { } });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public void onClick(View v) { if(v == buttonUpdate){ updateMhs();
    }
        if(v == buttonDelete){ confirmDeleteMhs();
        }
    }
}