package com.ncsoft.line;

import static com.ncsoft.line.MainActivity.qhxsgdfync;
import static com.ncsoft.line.NewAct.qazwsxedcrfv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.ncsoft.line.gam.ScarletGame;
import com.orhanobut.hawk.Hawk;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SecNewAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec_new);
        new asyncFunc().execute();
    }


    public class asyncFunc extends AsyncTask<Void, Void, Void> {


        String result;
        String cAdder = Hawk.get(qazwsxedcrfv);
        String dAdder = Hawk.get(qhxsgdfync);
        String cor = "http://scarlet";
        String knil = "effulgence.xyz/go.php?to=1&";
        String oneis = "sub_id_1=";
        String namelnk = cor + knil+ oneis + cAdder;
        String deeplnk = cor + knil +oneis + dAdder;

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                Document doc;
                if (!cAdder.equals("null")){
                    doc = Jsoup.connect(namelnk).get();
                } else {
                    doc = Jsoup.connect(deeplnk).get();
                }
                result = doc.text();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Intent i1 = new Intent(getApplicationContext(), ScarletGame.class);

            Intent i2 = new Intent(getApplicationContext(), Point.class);
            if (result.equals("1v4g")) {
                startActivity(i1);
            } else {
                startActivity(i2);
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }

    }
}