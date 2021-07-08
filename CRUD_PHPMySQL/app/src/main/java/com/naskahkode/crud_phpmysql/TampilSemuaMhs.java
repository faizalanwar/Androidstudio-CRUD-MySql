package com.naskahkode.crud_phpmysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class TampilSemuaMhs extends AppCompatActivity implements ListView.OnItemClickListener{
    private ListView listView;
    private String JSON_STRING;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_semua_mhs);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();
    }

    private void showMhs(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(konfigurasi.TAG_ID);

                String name = jo.getString(konfigurasi.TAG_NAMA);
                HashMap<String,String> mhs = new HashMap<>();
                mhs.put(konfigurasi.TAG_ID,id);
                mhs.put(konfigurasi.TAG_NAMA,name);
                list.add(mhs);
            }
        }
        catch (JSONException e) { e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                TampilSemuaMhs.this, list, R.layout.list_item,
                new String[]{konfigurasi.TAG_ID,konfigurasi.TAG_NAMA},
                new int[]{R.id.id, R.id.name});
        listView.setAdapter(adapter);
    }
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSemuaMhs.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showMhs();
            }
            @Override protected String doInBackground(Void... params) { RequestHandler rh = new RequestHandler();
                Log.d("testing","test");
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ALL);
                Log.d("response",s);

                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, TampilMhs.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String mhsId = map.get(konfigurasi.TAG_ID).toString();
        intent.putExtra(konfigurasi.MHS_ID,mhsId);
        startActivity(intent);
    }
}