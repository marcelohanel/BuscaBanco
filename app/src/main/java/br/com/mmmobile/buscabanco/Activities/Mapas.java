package br.com.mmmobile.buscabanco.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.mmmobile.buscabanco.DataBase.TableBancos;
import br.com.mmmobile.buscabanco.DataBase.TableConfig;
import br.com.mmmobile.buscabanco.R;

public class Mapas extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapas);

        leitura();
    }

    private void leitura() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(Mapas.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return;
            }
        }

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(Mapas.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap != null) {

            String sBanco_1 = "";
            String sBanco_2 = "";
            String sBanco_3 = "";
            String sBanco_4 = "";
            String sBanco_5 = "";
            String sEstado = "";

            TableConfig tableConfig = new TableConfig();

            tableConfig.getRecord("edtBanco_1");
            sBanco_1 = tableConfig.getValor();

            tableConfig.getRecord("edtBanco_2");
            sBanco_2 = tableConfig.getValor();

            tableConfig.getRecord("edtBanco_3");
            sBanco_3 = tableConfig.getValor();

            tableConfig.getRecord("edtBanco_4");
            sBanco_4 = tableConfig.getValor();

            tableConfig.getRecord("edtBanco_5");
            sBanco_5 = tableConfig.getValor();

            Double dLatitude = 0.0;
            Double dLongitude = 0.0;

            for (int i = 0; i <= Principal.adapterBancos.mListBancos.size() - 1; i++) {

                Boolean lModifica;

                if (dLatitude == Double.parseDouble(Principal.adapterBancos.mListBancos.get(i).getLatitude()))
                    lModifica = true;
                else
                    lModifica = false;

                dLatitude = Double.parseDouble(Principal.adapterBancos.mListBancos.get(i).getLatitude());
                dLongitude = Double.parseDouble(Principal.adapterBancos.mListBancos.get(i).getLongitude());

                if (lModifica) {
                    dLongitude = Double.parseDouble(Principal.adapterBancos.mListBancos.get(i).getLongitude()) + ((i + 1) * 0.0001);
                }

                LatLng latLng = new LatLng(dLatitude, dLongitude);

                if (lModifica) {
                    dLongitude = Double.parseDouble(Principal.adapterBancos.mListBancos.get(i).getLongitude());
                }

                if (sBanco_1.equalsIgnoreCase(Principal.adapterBancos.mListBancos.get(i).getNomeBanco())) {
                    googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .snippet(Principal.adapterBancos.mListBancos.get(i).getFone())
                            .title(Principal.adapterBancos.mListBancos.get(i).getNomeBanco())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                } else if (sBanco_2.equalsIgnoreCase(Principal.adapterBancos.mListBancos.get(i).getNomeBanco())) {
                    googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .snippet(Principal.adapterBancos.mListBancos.get(i).getFone())
                            .title(Principal.adapterBancos.mListBancos.get(i).getNomeBanco())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                } else if (sBanco_3.equalsIgnoreCase(Principal.adapterBancos.mListBancos.get(i).getNomeBanco())) {
                    googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .snippet(Principal.adapterBancos.mListBancos.get(i).getFone())
                            .title(Principal.adapterBancos.mListBancos.get(i).getNomeBanco())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                } else if (sBanco_4.equalsIgnoreCase(Principal.adapterBancos.mListBancos.get(i).getNomeBanco())) {
                    googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .snippet(Principal.adapterBancos.mListBancos.get(i).getFone())
                            .title(Principal.adapterBancos.mListBancos.get(i).getNomeBanco())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                } else if (sBanco_5.equalsIgnoreCase(Principal.adapterBancos.mListBancos.get(i).getNomeBanco())) {
                    googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .snippet(Principal.adapterBancos.mListBancos.get(i).getFone())
                            .title(Principal.adapterBancos.mListBancos.get(i).getNomeBanco())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                } else {
                    googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .snippet(Principal.adapterBancos.mListBancos.get(i).getFone())
                            .title(Principal.adapterBancos.mListBancos.get(i).getNomeBanco()));
                }

                if (i == 0) {
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(latLng)
                            .zoom(12)
                            .build();

                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }

                googleMap.setOnInfoWindowClickListener(Mapas.this);
            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        TableBancos tableBancos = new TableBancos();
        int iID = tableBancos.getIdByLatLng(marker.getTitle(), marker.getPosition().latitude, marker.getPosition().longitude);

        if (iID != 0) {
            Intent intent = new Intent(Mapas.this, Detalhes.class);
            intent.putExtra("id", iID);
            startActivity(intent);
        }
    }
}
