package br.com.mmmobile.buscabanco.Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.com.mmmobile.buscabanco.DataBase.TableBancos;
import br.com.mmmobile.buscabanco.DataBase.TableConfig;
import br.com.mmmobile.buscabanco.Interfaces.InterfaceTaskImport;
import br.com.mmmobile.buscabanco.R;

public class TaskImport extends AsyncTask<String, String, String> {

    private Context mContext;
    private ProgressDialog mProgressDialog;
    private InterfaceTaskImport mInterface;

    public TaskImport(Context c, InterfaceTaskImport i) {
        this.mContext = c;
        this.mInterface = i;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle(mContext.getString(R.string.a_003));
        mProgressDialog.setMessage(mContext.getString(R.string.c_004));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            return importa();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        mProgressDialog.setMessage(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        mProgressDialog.dismiss();
        mInterface.onFinishTask(s);
    }

    private String importa() {

        try {
            String sAux;
            String sFone;
            int iAux;
            int iCont = 0;

            TableBancos tableBancos = new TableBancos();
            tableBancos.deleteAll();

            InputStream inputStream = mContext.getResources().openRawResource(R.raw.dados);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String sLinha = reader.readLine();
            while (sLinha != null) {

                iCont++;

                String vLinha[] = sLinha.split(";");

                tableBancos.empty();

                tableBancos.setNomeBanco(vLinha[0]);

                sAux = vLinha[1].replaceAll("[^0-9]", "");
                if (sAux.trim().length() != 0) {
                    tableBancos.setCodAgencia(String.format("%04d", Long.valueOf(sAux)));
                }

                tableBancos.setNomeAgencia(vLinha[2]);
                tableBancos.setTipo(vLinha[3]);

                sAux = vLinha[4];
                if (vLinha[5].trim().length() != 0)
                    sAux = sAux + ", " + vLinha[5].trim();

                tableBancos.setEndereco(sAux);
                tableBancos.setComplemento(vLinha[6]);
                tableBancos.setBairro(vLinha[7]);

                sAux = vLinha[8].replaceAll("[^0-9]", "");
                if (sAux.length() != 0) {
                    sAux = String.format("%08d", Long.valueOf(sAux));
                    sAux = sAux.substring(0, 2) + "." + sAux.substring(2, 5) + "-" + sAux.substring(5, 8);
                    tableBancos.setCep(sAux);
                }

                tableBancos.setCidade(vLinha[9]);
                tableBancos.setUf(vLinha[10]);

                sFone = "";
                sAux = vLinha[11].replaceAll("[^0-9]", "");
                if (!sAux.trim().equalsIgnoreCase("0") && sAux.trim().length() != 0) {
                    sAux = "(" + sAux.trim() + ")";
                    sFone = sAux;
                }

                sAux = vLinha[12].replaceAll("[^0-9]", "");
                if (!sAux.trim().equalsIgnoreCase("0") && sAux.trim().length() != 0) {

                    if (sAux.trim().length() == 8)
                        sAux = sAux.substring(0, 4) + "-" + sAux.substring(4, 8);
                    else if (sAux.trim().length() == 9)
                        sAux = sAux.substring(0, 5) + "-" + sAux.substring(5, 9);

                    sFone += " " + sAux;
                }

                tableBancos.setFone(sFone);

                if (vLinha.length >= 14)
                    tableBancos.setLatitude(vLinha[13]);

                if (vLinha.length >= 15)
                    tableBancos.setLongitude(vLinha[14]);

                tableBancos.insert();

                sLinha = reader.readLine();

                publishProgress(String.valueOf(iCont));
            }

            reader.close();
            inputStream.close();

            TableConfig tblConfig = new TableConfig();

            tblConfig.setChave("versao_dados");
            tblConfig.delete();

            tblConfig.setChave("versao_dados");
            tblConfig.setValor(mContext.getString(R.string.versao_dados));
            tblConfig.insert();

            return mContext.getString(R.string.b_002);

        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
