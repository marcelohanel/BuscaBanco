package br.com.mmmobile.buscabanco.Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import br.com.mmmobile.buscabanco.Diversos.Funcoes;
import br.com.mmmobile.buscabanco.Interfaces.InterfaceTaskAtualizaBanco;
import br.com.mmmobile.buscabanco.Interfaces.InterfaceTaskImport;
import br.com.mmmobile.buscabanco.R;

public class TaskAtualizaBanco extends AsyncTask<String, String, String> {

    private Context mContext;
    private ProgressDialog mProgressDialog;
    private InterfaceTaskAtualizaBanco mInterface;

    public TaskAtualizaBanco(Context c, InterfaceTaskAtualizaBanco i) {
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
            return copyDatabase();
        } catch (Exception e) {
            return null;
        }
    }
/*
    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        mProgressDialog.setMessage(values[0]);
    }
*/

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        mProgressDialog.dismiss();
        mInterface.onFinishTask(s);
    }

    private String copyDatabase() {

        try {
            Funcoes.closeDataBase();

            String dbPath = "/data/data/br.com.mmmobile.buscabanco/databases/banco_dados.db";

            OutputStream dbStream = new FileOutputStream(dbPath);
            InputStream dbInputStream = mContext.getResources().openRawResource(R.raw.banco_dados);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dbInputStream.read(buffer)) > 0) {
                dbStream.write(buffer, 0, length);
            }

            dbInputStream.close();
            dbStream.flush();
            dbStream.close();

            Funcoes.openDataBase(mContext);

            return "ok";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
