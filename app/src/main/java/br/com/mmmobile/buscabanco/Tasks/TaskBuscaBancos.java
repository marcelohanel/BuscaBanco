package br.com.mmmobile.buscabanco.Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.com.mmmobile.buscabanco.DataBase.TableBancos;
import br.com.mmmobile.buscabanco.DataBase.TableConfig;
import br.com.mmmobile.buscabanco.Diversos.Funcoes;
import br.com.mmmobile.buscabanco.Interfaces.InterfaceBuscaBancos;
import br.com.mmmobile.buscabanco.R;

public class TaskBuscaBancos extends AsyncTask<String, String, List<TableBancos>> {

    private Context mContext;
    private ProgressDialog mProgressDialog;
    private InterfaceBuscaBancos mInterface;

    public TaskBuscaBancos(Context c, InterfaceBuscaBancos i) {
        this.mContext = c;
        this.mInterface = i;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle(mContext.getString(R.string.a_003));
        mProgressDialog.setMessage(mContext.getString(R.string.b_010));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected List<TableBancos> doInBackground(String... params) {

        try {

            Integer iRaio = 3;

            TableConfig tableConfig = new TableConfig();

            tableConfig.getRecord("edtRaio");
            if (tableConfig.getValor().trim().length() != 0) {

                if (tableConfig.getValor().equalsIgnoreCase(mContext.getString(R.string._001))) {
                    iRaio = 1;
                } else if (tableConfig.getValor().equalsIgnoreCase(mContext.getString(R.string._002))) {
                    iRaio = 3;
                } else if (tableConfig.getValor().equalsIgnoreCase(mContext.getString(R.string._003))) {
                    iRaio = 5;
                } else if (tableConfig.getValor().equalsIgnoreCase(mContext.getString(R.string._004))) {
                    iRaio = 10;
                } else if (tableConfig.getValor().equalsIgnoreCase(mContext.getString(R.string._005))) {
                    iRaio = 20;
                } else if (tableConfig.getValor().equalsIgnoreCase(mContext.getString(R.string._006))) {
                    iRaio = 30;
                }
            }

            tableConfig.getRecord("edtEstado");
            String sEstado = tableConfig.getValor();

            tableConfig.getRecord("edtCidade");
            String sCidade = tableConfig.getValor();

            tableConfig.getRecord("edtBairro");
            String sBairro = tableConfig.getValor();

            TableBancos record = new TableBancos();

            if (sCidade.trim().length() != 0) {
                return record.getListCidadeBairro(Funcoes.latitude, Funcoes.longitude, sEstado, sCidade, sBairro);
            } else {
                return record.getListProximidade(Funcoes.latitude, Funcoes.longitude, String.valueOf(iRaio));
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<TableBancos> s) {
        super.onPostExecute(s);

        mProgressDialog.dismiss();
        mInterface.onFinishTask(s);
    }
}
