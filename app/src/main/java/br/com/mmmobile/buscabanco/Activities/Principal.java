package br.com.mmmobile.buscabanco.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.github.javiersantos.appupdater.AppUpdater;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import br.com.mmmobile.buscabanco.Adapters.AdapterBancos;
import br.com.mmmobile.buscabanco.DataBase.TableConfig;
import br.com.mmmobile.buscabanco.Diversos.Funcoes;
import br.com.mmmobile.buscabanco.Diversos.SpacesItemDecoration;
import br.com.mmmobile.buscabanco.Interfaces.InterfaceTaskAtualizaBanco;
import br.com.mmmobile.buscabanco.R;
import br.com.mmmobile.buscabanco.Tasks.TaskAtualizaBanco;

public class Principal extends AppCompatActivity implements InterfaceTaskAtualizaBanco, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static AdapterBancos adapterBancos;
    private RecyclerView recyclerView;
    private GoogleApiClient googleApiClient;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getString(R.string.admob_banner));
        LinearLayout layout = (LinearLayout) findViewById(R.id.banner_layout);
        layout.addView(adView);

        Funcoes.adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("86FB55B8A258F52D3C551AD4901688AC")
                .build();

        adView.loadAd(Funcoes.adRequest);

        recyclerView = (RecyclerView) findViewById(R.id.rvBancos);
        recyclerView.setLayoutManager(new LinearLayoutManager(Principal.this));
        recyclerView.addItemDecoration(new SpacesItemDecoration(Funcoes.SPACE_BETWEEN_ITEMS));

        Funcoes.openDataBase(Principal.this);
        verificaBancoDados();
        verifyUpdate();

        //callConnection();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (adView != null) {
            adView.destroy();
        }

        Funcoes.closeDataBase();
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
        getMenuInflater().inflate(R.menu.mnu_principal, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.mnu_sobre) {
            Intent intent = new Intent(Principal.this, Sobre.class);
            startActivity(intent);
        }

        if (id == R.id.mnu_atualizar) {
            callConnection();
        }

        if (id == R.id.mnu_filtro) {
            Intent intent = new Intent(Principal.this, Filtro.class);
            startActivityForResult(intent, 1);
        }

        if (id == R.id.mnu_mapa) {
            Intent intent = new Intent(Principal.this, Mapas.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                callConnection();
            }
        }
    }

    private void verifyUpdate() {

        AppUpdater appUpdater = new AppUpdater(this);

        appUpdater.setDialogTitleWhenUpdateAvailable(getString(R.string.update_001));
        appUpdater.setDialogDescriptionWhenUpdateAvailable(getString(R.string.update_002));
        appUpdater.setDialogButtonUpdate(getString(R.string.update_003));
        appUpdater.setDialogButtonDoNotShowAgain(getString(R.string.update_004));
        appUpdater.setDialogTitleWhenUpdateNotAvailable(getString(R.string.update_005));
        appUpdater.setDialogDescriptionWhenUpdateNotAvailable(getString(R.string.update_006));

        appUpdater.start();
    }

    private void verificaBancoDados() {

        TableConfig tblConfig = new TableConfig();
        tblConfig.getRecord("versao_dados");

        if (!tblConfig.getValor().equalsIgnoreCase(getString(R.string.versao_dados))) {
            TaskAtualizaBanco taskAtualizaBanco = new TaskAtualizaBanco(Principal.this, Principal.this);
            taskAtualizaBanco.execute();
        } else {
            adapterBancos = new AdapterBancos(Principal.this, recyclerView);
            recyclerView.setAdapter(adapterBancos);

            if (Funcoes.longitude.equals("0.0"))
                callConnection();
        }
    }

    private synchronized void callConnection() {

        googleApiClient = new GoogleApiClient.Builder(Principal.this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    @Override
    public void onFinishTask(String s) {

        if (!s.equalsIgnoreCase("ok")) {
            Funcoes.showMessage(Principal.this, getString(R.string.a_002), s, getString(R.string.o_001));
        } else {
            adapterBancos = new AdapterBancos(Principal.this, recyclerView);
            recyclerView.setAdapter(adapterBancos);

            if (Funcoes.longitude.equals("0.0"))
                callConnection();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(Principal.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }
        }

        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (location != null) {
            Funcoes.latitude = String.valueOf(location.getLatitude());
            Funcoes.longitude = String.valueOf(location.getLongitude());

            adapterBancos.refresh();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
