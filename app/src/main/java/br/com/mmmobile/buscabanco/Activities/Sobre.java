package br.com.mmmobile.buscabanco.Activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import br.com.mmmobile.buscabanco.Diversos.Funcoes;
import br.com.mmmobile.buscabanco.Interfaces.InterfaceTaskImport;
import br.com.mmmobile.buscabanco.R;
import br.com.mmmobile.buscabanco.Tasks.TaskImport;

public class Sobre extends AppCompatActivity implements InterfaceTaskImport {

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getString(R.string.admob_banner));
        LinearLayout layout = (LinearLayout) findViewById(R.id.banner_layout);
        layout.addView(adView);
        adView.loadAd(Funcoes.adRequest);

        TextView edtVersao = (TextView) findViewById(R.id.edtVersao);
        Button btnCriarBaseDados = (Button) findViewById(R.id.btnCriarBase);

        edtVersao.setText(Funcoes.getVersionName(this));

        btnCriarBaseDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarBaseDados();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adView != null) {
            adView.destroy();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (adView != null) {
            adView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mnu_sobre, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
    */

    @Override
    public void onFinishTask(String s) {
        Funcoes.showMessage(Sobre.this, getString(R.string.a_002), s, getString(R.string.o_001));
    }

    private void criarBaseDados() {
        TaskImport taskImport = new TaskImport(Sobre.this, Sobre.this);
        taskImport.execute();
    }
}
