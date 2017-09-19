package br.com.mmmobile.buscabanco.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import br.com.mmmobile.buscabanco.DataBase.TableBancos;
import br.com.mmmobile.buscabanco.Diversos.Funcoes;
import br.com.mmmobile.buscabanco.R;

public class Detalhes extends AppCompatActivity {

    private AdView adView;

    private Integer iID;
    private TextView edtNomeBanco;
    private TextView edtCodAgencia;
    private TextView edtNomeAgencia;
    private TextView edtTipo;
    private TextView edtEndereco;
    private TextView edtComplemento;
    private TextView edtBairro;
    private TextView edtCEP;
    private TextView edtCidade;
    private TextView edtUF;
    private TextView edtTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getString(R.string.admob_banner));
        LinearLayout layout = (LinearLayout) findViewById(R.id.banner_layout);
        layout.addView(adView);
        adView.loadAd(Funcoes.adRequest);

        edtNomeBanco = (TextView) findViewById(R.id.edtNomeBanco);
        edtCodAgencia = (TextView) findViewById(R.id.edtCodAgencia);
        edtNomeAgencia = (TextView) findViewById(R.id.edtNomeAgencia);
        edtTipo = (TextView) findViewById(R.id.edtTipo);
        edtEndereco = (TextView) findViewById(R.id.edtEndereco);
        edtComplemento = (TextView) findViewById(R.id.edtComplemento);
        edtBairro = (TextView) findViewById(R.id.edtBairro);
        edtCEP = (TextView) findViewById(R.id.edtCEP);
        edtCidade = (TextView) findViewById(R.id.edtCidade);
        edtUF = (TextView) findViewById(R.id.edtUF);
        edtTelefone = (TextView) findViewById(R.id.edtTelefone);

        iID = getIntent().getExtras().getInt("id");

        leitura();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mnu_detalhes, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.mnu_mapa) {
            chamaMapa();
        }

        if (id == R.id.mnu_fone) {
            chamaFone();
        }

        return super.onOptionsItemSelected(item);
    }

    private void chamaFone() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(Detalhes.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
                return;
            }
        }

        String sFone = edtTelefone.getText().toString().trim();
        sFone = sFone.replace("(","");
        sFone = sFone.replace(")","");
        sFone = sFone.replace("-","");
        sFone = sFone.replace(" ","");

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + sFone));
        startActivity(intent);
    }

    private void chamaMapa() {

        TableBancos tableBancos = new TableBancos();
        tableBancos.getRecord(iID);

        String sEndereco = "geo:0,0?q=" + tableBancos.getEndereco().trim() + " - " + tableBancos.getBairro() + " - " + tableBancos.getCidade() + " - " + tableBancos.getUf() + " - Brasil - CEP: " + tableBancos.getCep();

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sEndereco));
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    private void leitura() {

        TableBancos tableBancos = new TableBancos();
        tableBancos.getRecord(iID);

        edtNomeBanco.setText(tableBancos.getNomeBanco());
        edtCodAgencia.setText(tableBancos.getCodAgencia());
        edtNomeAgencia.setText(tableBancos.getNomeAgencia());
        edtTipo.setText(tableBancos.getTipo());
        edtEndereco.setText(tableBancos.getEndereco());
        edtComplemento.setText(tableBancos.getComplemento());
        edtBairro.setText(tableBancos.getBairro());
        edtCEP.setText(tableBancos.getCep());
        edtCidade.setText(tableBancos.getCidade());
        edtUF.setText(tableBancos.getUf());
        edtTelefone.setText(tableBancos.getFone());
    }
}
