package br.com.mmmobile.buscabanco.Diversos;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import com.google.android.gms.ads.AdRequest;

import java.text.DecimalFormat;

import br.com.mmmobile.buscabanco.DataBase.DBHelper;

public class Funcoes {

    public static SQLiteDatabase mDataBase;
    public static AdRequest adRequest;
    public static int SPACE_BETWEEN_ITEMS = 10;
    public static DecimalFormat decimalFormat = new DecimalFormat("###,##0.0");
    public static String latitude = "0.0";
    public static String longitude = "0.0";

    public static void openDataBase(Context c) {
        DBHelper dataBase = new DBHelper(c);
        mDataBase = dataBase.getWritableDatabase();
    }

    public static void closeDataBase() {
        if (mDataBase != null && mDataBase.isOpen())
            mDataBase.close();
    }

    public static void showMessage(Context context, String title, String message, String textButton) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(textButton,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );

        builder.create();
        builder.show();
    }

    public static String getVersionName(Context context) {
        try {
            ComponentName comp = new ComponentName(context, context.getClass());
            PackageInfo pinfo = context.getPackageManager().getPackageInfo(comp.getPackageName(), 0);
            return pinfo.versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}
