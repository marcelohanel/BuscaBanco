package br.com.mmmobile.buscabanco.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.mmmobile.buscabanco.Comparators.ComparatorDomainValores;
import br.com.mmmobile.buscabanco.DataBase.TableBancos;
import br.com.mmmobile.buscabanco.DataBase.TableConfig;
import br.com.mmmobile.buscabanco.Diversos.Funcoes;
import br.com.mmmobile.buscabanco.Domains.DomainValores;
import br.com.mmmobile.buscabanco.R;

public class Filtro extends AppCompatActivity {

    private AdView adView;

    private EditText edtRaio;
    private EditText edtBanco_1;
    private EditText edtBanco_2;
    private EditText edtBanco_3;
    private EditText edtBanco_4;
    private EditText edtBanco_5;
    private EditText edtTipo;
    private EditText edtEstado;
    private EditText edtCidade;
    private EditText edtBairro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getString(R.string.admob_banner));
        LinearLayout layout = (LinearLayout) findViewById(R.id.banner_layout);
        layout.addView(adView);
        adView.loadAd(Funcoes.adRequest);

        edtRaio = (EditText) findViewById(R.id.edtRaio);
        edtBanco_1 = (EditText) findViewById(R.id.edtBanco_1);
        edtBanco_2 = (EditText) findViewById(R.id.edtBanco_2);
        edtBanco_3 = (EditText) findViewById(R.id.edtBanco_3);
        edtBanco_4 = (EditText) findViewById(R.id.edtBanco_4);
        edtBanco_5 = (EditText) findViewById(R.id.edtBanco_5);
        edtTipo = (EditText) findViewById(R.id.edtTipo);
        edtEstado = (EditText) findViewById(R.id.edtEstado);
        edtCidade = (EditText) findViewById(R.id.edtCidade);
        edtBairro = (EditText) findViewById(R.id.edtBairro);

        edtRaio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montaComboRaio();
            }
        });

        edtBanco_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montaComboBanco(edtBanco_1);
            }
        });

        edtBanco_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montaComboBanco(edtBanco_2);
            }
        });

        edtBanco_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montaComboBanco(edtBanco_3);
            }
        });

        edtBanco_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montaComboBanco(edtBanco_4);
            }
        });

        edtBanco_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montaComboBanco(edtBanco_5);
            }
        });

        edtTipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montaComboTipo();
            }
        });

        edtEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montaComboEstado();
            }
        });

        edtCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montaComboCidade();
            }
        });

        edtBairro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montaComboBairro();
            }
        });

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
        getMenuInflater().inflate(R.menu.mnu_filtro, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.mnu_salvar) {
            grava();
        }

        return super.onOptionsItemSelected(item);
    }

    private void leitura() {

        TableConfig tableConfig = new TableConfig();

        tableConfig.getRecord("edtRaio");
        if (tableConfig.getValor().trim().length() == 0)
            edtRaio.setText(getString(R.string._002));
        else
            edtRaio.setText(tableConfig.getValor());

        tableConfig.getRecord("edtBanco_1");
        edtBanco_1.setText(tableConfig.getValor());

        tableConfig.getRecord("edtBanco_2");
        edtBanco_2.setText(tableConfig.getValor());

        tableConfig.getRecord("edtBanco_3");
        edtBanco_3.setText(tableConfig.getValor());

        tableConfig.getRecord("edtBanco_4");
        edtBanco_4.setText(tableConfig.getValor());

        tableConfig.getRecord("edtBanco_5");
        edtBanco_5.setText(tableConfig.getValor());

        tableConfig.getRecord("edtTipo");
        edtTipo.setText(tableConfig.getValor());

        tableConfig.getRecord("edtEstado");
        edtEstado.setText(tableConfig.getValor());

        tableConfig.getRecord("edtCidade");
        edtCidade.setText(tableConfig.getValor());

        tableConfig.getRecord("edtBairro");
        edtBairro.setText(tableConfig.getValor());

        if (edtEstado.getText().toString().trim().length() == 0) {
            Funcoes.showMessage(Filtro.this, getString(R.string.i_002), getString(R.string.i_003), getString(R.string.o_001));
        }
    }

    private void grava() {

        if (!validaTela())
            return;

        TableConfig tableConfig = new TableConfig();

        tableConfig.setChave("edtRaio");
        tableConfig.setValor(edtRaio.getText().toString());
        tableConfig.delete();
        tableConfig.insert();

        tableConfig.setChave("edtBanco_1");
        tableConfig.setValor(edtBanco_1.getText().toString());
        tableConfig.delete();
        tableConfig.insert();

        tableConfig.setChave("edtBanco_2");
        tableConfig.setValor(edtBanco_2.getText().toString());
        tableConfig.delete();
        tableConfig.insert();

        tableConfig.setChave("edtBanco_3");
        tableConfig.setValor(edtBanco_3.getText().toString());
        tableConfig.delete();
        tableConfig.insert();

        tableConfig.setChave("edtBanco_4");
        tableConfig.setValor(edtBanco_4.getText().toString());
        tableConfig.delete();
        tableConfig.insert();

        tableConfig.setChave("edtBanco_5");
        tableConfig.setValor(edtBanco_5.getText().toString());
        tableConfig.delete();
        tableConfig.insert();

        tableConfig.setChave("edtTipo");
        tableConfig.setValor(edtTipo.getText().toString());
        tableConfig.delete();
        tableConfig.insert();

        tableConfig.setChave("edtEstado");
        tableConfig.setValor(edtEstado.getText().toString());
        tableConfig.delete();
        tableConfig.insert();

        tableConfig.setChave("edtCidade");
        tableConfig.setValor(edtCidade.getText().toString());
        tableConfig.delete();
        tableConfig.insert();

        tableConfig.setChave("edtBairro");
        tableConfig.setValor(edtBairro.getText().toString());
        tableConfig.delete();
        tableConfig.insert();

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtRaio.getWindowToken(), 0);

        if (edtCidade.getText().toString().trim().length() != 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Filtro.this);

            builder.setTitle(getString(R.string.i_002));
            builder.setMessage(getString(R.string.q_001));
            builder.setCancelable(false);
            builder.setPositiveButton(getString(R.string.o_001),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent returnIntent = new Intent();
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        }
                    }
            );

            builder.create();
            builder.show();
        } else {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

    private boolean validaTela() {

        if (edtRaio.getText().toString().trim().length() == 0) {
            Funcoes.showMessage(Filtro.this, getString(R.string.a_004), getString(R.string.i_001), getString(R.string.o_001));
            return false;
        }

        return true;
    }

    private void montaComboBanco(final EditText editText) {

        int iPosition = 0;

        List<DomainValores> mList = new ArrayList<>();

        TableBancos tableBancos = new TableBancos();
        mList = tableBancos.getListBancos();

        Collections.sort(mList, new ComparatorDomainValores(0));

        final String[] vNome = new String[mList.size()];

        for (int i = 0; i <= mList.size() - 1; i++) {

            vNome[i] = mList.get(i).getValor();

            if (editText.getText().toString().equalsIgnoreCase(vNome[i]))
                iPosition = i;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(Filtro.this);
        builder.setTitle(R.string.b_008);
        builder.setCancelable(false);
        builder.create();
        builder.setNegativeButton(R.string.l_001, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editText.setText("");
                dialog.dismiss();
            }
        });
        builder.setSingleChoiceItems(vNome, iPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editText.setText(vNome[which]);
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void montaComboEstado() {

        int iPosition = 0;

        List<DomainValores> mList = new ArrayList<>();

        TableBancos tableBancos = new TableBancos();
        mList = tableBancos.getListEstados();

        Collections.sort(mList, new ComparatorDomainValores(0));

        final String[] vNome = new String[mList.size()];

        for (int i = 0; i <= mList.size() - 1; i++) {

            vNome[i] = mList.get(i).getValor();

            if (edtEstado.getText().toString().equalsIgnoreCase(vNome[i]))
                iPosition = i;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(Filtro.this);
        builder.setTitle(R.string.e_003);
        builder.setCancelable(false);
        builder.create();
        builder.setNegativeButton(R.string.l_001, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtEstado.setText("");
                edtCidade.setText("");
                edtBairro.setText("");
                dialog.dismiss();
            }
        });
        builder.setSingleChoiceItems(vNome, iPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtEstado.setText(vNome[which]);
                edtCidade.setText("");
                edtBairro.setText("");
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void montaComboCidade() {

        int iPosition = 0;

        List<DomainValores> mList = new ArrayList<>();

        TableBancos tableBancos = new TableBancos();
        mList = tableBancos.getListCidades(edtEstado.getText().toString());

        Collections.sort(mList, new ComparatorDomainValores(0));

        final String[] vNome = new String[mList.size()];

        for (int i = 0; i <= mList.size() - 1; i++) {

            vNome[i] = mList.get(i).getValor();

            if (edtCidade.getText().toString().equalsIgnoreCase(vNome[i]))
                iPosition = i;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(Filtro.this);
        builder.setTitle(R.string.c_009);
        builder.setCancelable(false);
        builder.create();
        builder.setNegativeButton(R.string.l_001, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtCidade.setText("");
                edtBairro.setText("");
                dialog.dismiss();
            }
        });
        builder.setSingleChoiceItems(vNome, iPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtCidade.setText(vNome[which]);
                edtBairro.setText("");
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void montaComboBairro() {

        int iPosition = 0;

        List<DomainValores> mList = new ArrayList<>();

        TableBancos tableBancos = new TableBancos();
        mList = tableBancos.getListBairros(edtEstado.getText().toString(), edtCidade.getText().toString());

        Collections.sort(mList, new ComparatorDomainValores(0));

        final String[] vNome = new String[mList.size()];

        for (int i = 0; i <= mList.size() - 1; i++) {

            vNome[i] = mList.get(i).getValor();

            if (edtBairro.getText().toString().equalsIgnoreCase(vNome[i]))
                iPosition = i;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(Filtro.this);
        builder.setTitle(R.string.b_011);
        builder.setCancelable(false);
        builder.create();
        builder.setNegativeButton(R.string.l_001, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtBairro.setText("");
                dialog.dismiss();
            }
        });
        builder.setSingleChoiceItems(vNome, iPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtBairro.setText(vNome[which]);
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void montaComboTipo() {

        int iPosition = 0;

        List<DomainValores> mList = new ArrayList<>();

        TableBancos tableBancos = new TableBancos();
        mList = tableBancos.getListTipos();

        Collections.sort(mList, new ComparatorDomainValores(0));

        final String[] vNome = new String[mList.size()];

        for (int i = 0; i <= mList.size() - 1; i++) {

            vNome[i] = mList.get(i).getValor();

            if (edtTipo.getText().toString().equalsIgnoreCase(vNome[i]))
                iPosition = i;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(Filtro.this);
        builder.setTitle(R.string.t_003);
        builder.setCancelable(false);
        builder.create();
        builder.setNegativeButton(R.string.l_001, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtTipo.setText("");
                dialog.dismiss();
            }
        });
        builder.setSingleChoiceItems(vNome, iPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtTipo.setText(vNome[which]);
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void montaComboRaio() {

        int iPosition = 1;

        final String[] vNome = new String[6];

        if (edtRaio.getText().toString().equalsIgnoreCase(getString(R.string._001)))
            iPosition = 0;
        else if (edtRaio.getText().toString().equalsIgnoreCase(getString(R.string._002)))
            iPosition = 1;
        else if (edtRaio.getText().toString().equalsIgnoreCase(getString(R.string._003)))
            iPosition = 2;
        else if (edtRaio.getText().toString().equalsIgnoreCase(getString(R.string._004)))
            iPosition = 3;
        else if (edtRaio.getText().toString().equalsIgnoreCase(getString(R.string._005)))
            iPosition = 4;
        else if (edtRaio.getText().toString().equalsIgnoreCase(getString(R.string._006)))
            iPosition = 5;

        vNome[0] = getString(R.string._001);
        vNome[1] = getString(R.string._002);
        vNome[2] = getString(R.string._003);
        vNome[3] = getString(R.string._004);
        vNome[4] = getString(R.string._005);
        vNome[5] = getString(R.string._006);

        AlertDialog.Builder builder = new AlertDialog.Builder(Filtro.this);
        builder.setTitle(R.string.r_001);
        builder.setCancelable(false);
        builder.create();
        builder.setSingleChoiceItems(vNome, iPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtRaio.setText(vNome[which]);
                dialog.dismiss();
            }
        });

        builder.show();
    }

}
