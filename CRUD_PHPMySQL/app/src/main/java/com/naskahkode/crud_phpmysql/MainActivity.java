package com.naskahkode.crud_phpmysql;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.HashMap;

public   class MainActivity extends AppCompatActivity implements  View.OnClickListener {
    private EditText editTextName;
    private EditText editTextNRP;
    private EditText editTextBlog;
    private Button buttonAdd;
    private Button buttonView;
    private Spinner spinnerKelas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextNRP = (EditText) findViewById(R.id.editTextNRP);
        editTextBlog = (EditText) findViewById(R.id.editTextBlog);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);
        spinnerKelas = (Spinner) findViewById(R.id.spinnerkelas);
        String[] spinnerItem = new String[]{"TI-1","TI-2","TI-3","TI-Mlm"};
        ArrayAdapter<String> adapter = new  ArrayAdapter<>(this, android.R.layout.simple_spinner_item,spinnerItem);
        spinnerKelas.setAdapter(adapter);

        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
    }

    private void addMhs(){
        final String name = editTextName.getText().toString().trim();
        final String nrp = editTextNRP.getText().toString().trim();
        final String blog = editTextBlog.getText().toString().trim();
        final String kelas =(String) spinnerKelas.getSelectedItem();
        Log.d("kelas",kelas);

        class AddMhs extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Menambahkan...","Tunggu...",false,false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_MHS_NAMA,name);
                params.put(konfigurasi.KEY_MHS_NRP,nrp);
                params.put(konfigurasi.KEY_MHS_KELAS,kelas);
                params.put(konfigurasi.KEY_MHS_BLOG,blog);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD, params);
                Log.d("res",res);
                return res;
            }
        }
        AddMhs ae = new AddMhs();
        ae.execute();
    }
    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            addMhs();
        }
        if(v == buttonView){
            startActivity(new Intent(this,TampilSemuaMhs.class));
        }
    }
}