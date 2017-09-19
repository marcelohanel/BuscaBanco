package br.com.mmmobile.buscabanco.DataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

import br.com.mmmobile.buscabanco.Diversos.Funcoes;
import br.com.mmmobile.buscabanco.Domains.DomainValores;

public class TableBancos {

    private String mTable = "bancos";

    private int id = -1;
    private String nomeBanco = "";
    private String codAgencia = "";
    private String nomeAgencia = "";
    private String tipo = "";
    private String endereco = "";
    private String complemento = "";
    private String bairro = "";
    private String cep = "";
    private String cidade = "";
    private String uf = "";
    private String fone = "";
    private String latitude = "";
    private String longitude = "";
    private Double distancia = 0.0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private void setId() {
        SQLiteStatement sStatement = Funcoes.mDataBase.compileStatement("SELECT MAX(id) FROM " + mTable);
        this.id = (int) sStatement.simpleQueryForLong() + 1;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

    public String getCodAgencia() {
        return codAgencia;
    }

    public void setCodAgencia(String codAgencia) {
        this.codAgencia = codAgencia;
    }

    public String getNomeAgencia() {
        return nomeAgencia;
    }

    public void setNomeAgencia(String nomeAgencia) {
        this.nomeAgencia = nomeAgencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    private ContentValues getValues() {

        ContentValues values = new ContentValues();

        values.put("id", getId());
        values.put("nome_banco", getNomeBanco());
        values.put("cod_agencia", getCodAgencia());
        values.put("nome_agencia", getNomeAgencia());
        values.put("tipo", getTipo());
        values.put("endereco", getEndereco());
        values.put("complemento", getComplemento());
        values.put("bairro", getBairro());
        values.put("cep", getCep());
        values.put("cidade", getCidade());
        values.put("uf", getUf());
        values.put("fone", getFone());
        values.put("latitude", getLatitude());
        values.put("longitude", getLongitude());

        return values;
    }

    public long count() {
        SQLiteStatement sStatement = Funcoes.mDataBase.compileStatement("SELECT COUNT(id) FROM " + mTable);
        return (long) sStatement.simpleQueryForLong();
    }

    public long insert() {
        setId();

        try {
            return Funcoes.mDataBase.insert(mTable, null, getValues());
        } catch (Exception e) {
            return -1;
        }
    }

    public long copy() {
        try {
            return Funcoes.mDataBase.insert(mTable, null, getValues());
        } catch (Exception e) {
            return -1;
        }
    }

    public int update() {
        try {
            return Funcoes.mDataBase.update(mTable, getValues(), "id = ?", new String[]{String.valueOf(getId())});
        } catch (Exception e) {
            return -1;
        }
    }

    public int delete() {
        try {
            return Funcoes.mDataBase.delete(mTable, "id = ?", new String[]{String.valueOf(getId())});
        } catch (Exception e) {
            return -1;
        }
    }

    public int deleteAll() {
        try {
            return Funcoes.mDataBase.delete(mTable, null, null);
        } catch (Exception e) {
            return -1;
        }
    }

    public void empty() {
        setId(0);
        setNomeBanco("");
        setCodAgencia("");
        setNomeAgencia("");
        setTipo("");
        setEndereco("");
        setComplemento("");
        setBairro("");
        setCep("");
        setCidade("");
        setUf("");
        setFone("");
        setLatitude("");
        setLongitude("");
    }

    public int getIdByLatLng(String banco, Double latitude, Double longitude) {

        int iID = 0;
        Cursor cursor;
        cursor = Funcoes.mDataBase.query(mTable, new String[]{"id"}, "nome_banco = ? AND latitude = ? AND longitude = ?", new String[]{String.valueOf(banco), String.valueOf(latitude), String.valueOf(longitude)}, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            iID = cursor.getInt(cursor.getColumnIndex("id"));
        }

        cursor.close();

        return iID;
    }

    public void getRecord(int id) {

        Cursor cursor;
        cursor = Funcoes.mDataBase.query(mTable, new String[]{"*"}, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        while (cursor.moveToNext()) {
            setId(cursor.getInt(cursor.getColumnIndex("id")));
            setNomeBanco(cursor.getString(cursor.getColumnIndex("nome_banco")));
            setCodAgencia(cursor.getString(cursor.getColumnIndex("cod_agencia")));
            setNomeAgencia(cursor.getString(cursor.getColumnIndex("nome_agencia")));
            setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
            setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            setComplemento(cursor.getString(cursor.getColumnIndex("complemento")));
            setBairro(cursor.getString(cursor.getColumnIndex("bairro")));
            setCep(cursor.getString(cursor.getColumnIndex("cep")));
            setCidade(cursor.getString(cursor.getColumnIndex("cidade")));
            setUf(cursor.getString(cursor.getColumnIndex("uf")));
            setFone(cursor.getString(cursor.getColumnIndex("fone")));
            setLatitude(cursor.getString(cursor.getColumnIndex("latitude")));
            setLongitude(cursor.getString(cursor.getColumnIndex("longitude")));
        }
        cursor.close();
    }

    public List<DomainValores> getListBancos() {

        List<DomainValores> mList = new ArrayList<>();

        Cursor cursor;
        cursor = Funcoes.mDataBase.query(mTable, new String[]{"nome_banco"}, null, null, "nome_banco", null, null);
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex("nome_banco")).trim().length() != 0) {
                DomainValores classValores = new DomainValores();
                classValores.setValor(cursor.getString(cursor.getColumnIndex("nome_banco")));
                mList.add(classValores);
            }
        }
        cursor.close();

        return mList;
    }

    public List<DomainValores> getListEstados() {

        List<DomainValores> mList = new ArrayList<>();

        Cursor cursor;
        cursor = Funcoes.mDataBase.query(mTable, new String[]{"uf"}, null, null, "uf", null, null);
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex("uf")).trim().length() != 0) {
                DomainValores classValores = new DomainValores();
                classValores.setValor(cursor.getString(cursor.getColumnIndex("uf")));
                mList.add(classValores);
            }
        }
        cursor.close();

        return mList;
    }

    public List<DomainValores> getListCidades(String uf) {

        List<DomainValores> mList = new ArrayList<>();

        Cursor cursor;
        cursor = Funcoes.mDataBase.query(mTable, new String[]{"cidade"}, "uf = ?", new String[]{uf}, "cidade", null, null);
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex("cidade")).trim().length() != 0) {
                DomainValores classValores = new DomainValores();
                classValores.setValor(cursor.getString(cursor.getColumnIndex("cidade")));
                mList.add(classValores);
            }
        }
        cursor.close();

        return mList;
    }

    public List<DomainValores> getListBairros(String uf, String cidade) {

        List<DomainValores> mList = new ArrayList<>();

        Cursor cursor;
        cursor = Funcoes.mDataBase.query(mTable, new String[]{"bairro"}, "uf = ? AND cidade = ?", new String[]{uf, cidade}, "bairro", null, null);
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex("bairro")).trim().length() != 0) {
                DomainValores classValores = new DomainValores();
                classValores.setValor(cursor.getString(cursor.getColumnIndex("bairro")));
                mList.add(classValores);
            }
        }
        cursor.close();

        return mList;
    }

    public List<DomainValores> getListTipos() {

        List<DomainValores> mList = new ArrayList<>();

        Cursor cursor;
        cursor = Funcoes.mDataBase.query(mTable, new String[]{"tipo"}, null, null, "tipo", null, null);
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex("tipo")).trim().length() != 0) {
                DomainValores classValores = new DomainValores();
                classValores.setValor(cursor.getString(cursor.getColumnIndex("tipo")));
                mList.add(classValores);
            }
        }
        cursor.close();

        return mList;
    }

    public List<TableBancos> getListProximidade(String latitude, String longitude, String distancia) {

        List<TableBancos> mList = new ArrayList<>();

        if (latitude.equals("0.0"))
            return mList;

        if (distancia.equals("0"))
            return mList;

        String sBanco_1 = "";
        String sBanco_2 = "";
        String sBanco_3 = "";
        String sBanco_4 = "";
        String sBanco_5 = "";
        String sEstado = "";
        String sTipo = "";

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

        tableConfig.getRecord("edtEstado");
        sEstado = tableConfig.getValor();

        tableConfig.getRecord("edtTipo");
        sTipo = tableConfig.getValor();

        Cursor cursor;

        if (sBanco_1.trim().length() == 0 && sBanco_2.trim().length() == 0 && sBanco_3.trim().length() == 0 && sBanco_4.trim().length() == 0 && sBanco_5.trim().length() == 0) {

            if (sEstado.trim().length() != 0) {

                if (sTipo.trim().length() != 0) {
                    cursor = Funcoes.mDataBase.query(mTable, new String[]{"*"}, "uf = ? AND tipo = ?", new String[]{sEstado, sTipo}, null, null, null);
                } else {
                    cursor = Funcoes.mDataBase.query(mTable, new String[]{"*"}, "uf = ?", new String[]{sEstado}, null, null, null);
                }

            } else {
                if (sTipo.trim().length() != 0) {
                    cursor = Funcoes.mDataBase.query(mTable, new String[]{"*"}, "tipo = ?", new String[]{sTipo}, null, null, null);
                } else {
                    cursor = Funcoes.mDataBase.query(mTable, new String[]{"*"}, null, null, null, null, null);
                }
            }

        } else {

            if (sEstado.trim().length() != 0) {

                if (sTipo.trim().length() != 0) {
                    cursor = Funcoes.mDataBase.query(mTable, new String[]{"*"}, "tipo = ? AND uf = ? AND (nome_banco = ? OR nome_banco = ? OR nome_banco = ? OR nome_banco = ? OR nome_banco = ?)", new String[]{sTipo, sEstado, sBanco_1, sBanco_2, sBanco_3, sBanco_4, sBanco_5}, null, null, null);
                } else {
                    cursor = Funcoes.mDataBase.query(mTable, new String[]{"*"}, "uf = ? AND (nome_banco = ? OR nome_banco = ? OR nome_banco = ? OR nome_banco = ? OR nome_banco = ?)", new String[]{sEstado, sBanco_1, sBanco_2, sBanco_3, sBanco_4, sBanco_5}, null, null, null);
                }

            } else {

                if (sTipo.trim().length() != 0) {
                    cursor = Funcoes.mDataBase.query(mTable, new String[]{"*"}, "tipo = ? AND (nome_banco = ? OR nome_banco = ? OR nome_banco = ? OR nome_banco = ? OR nome_banco = ?)", new String[]{sTipo, sBanco_1, sBanco_2, sBanco_3, sBanco_4, sBanco_5}, null, null, null);
                } else {
                    cursor = Funcoes.mDataBase.query(mTable, new String[]{"*"}, "nome_banco = ? OR nome_banco = ? OR nome_banco = ? OR nome_banco = ? OR nome_banco = ?", new String[]{sBanco_1, sBanco_2, sBanco_3, sBanco_4, sBanco_5}, null, null, null);
                }

            }
        }

        while (cursor.moveToNext()) {

            if (cursor.getString(cursor.getColumnIndex("latitude")).trim().length() != 0) {

                Location locationA = new Location("locationA");
                locationA.setLatitude(Double.valueOf(latitude));
                locationA.setLongitude(Double.valueOf(longitude));

                Location locationB = new Location("locationB");
                locationB.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
                locationB.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));

                double fDistancia = locationA.distanceTo(locationB);

                if (fDistancia <= (Double.valueOf(distancia) * 1000)) {

                    TableBancos record = new TableBancos();

                    record.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    record.setNomeBanco(cursor.getString(cursor.getColumnIndex("nome_banco")));
                    record.setCodAgencia(cursor.getString(cursor.getColumnIndex("cod_agencia")));
                    record.setNomeAgencia(cursor.getString(cursor.getColumnIndex("nome_agencia")));
                    record.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
                    record.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
                    record.setComplemento(cursor.getString(cursor.getColumnIndex("complemento")));
                    record.setBairro(cursor.getString(cursor.getColumnIndex("bairro")));
                    record.setCep(cursor.getString(cursor.getColumnIndex("cep")));
                    record.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));
                    record.setUf(cursor.getString(cursor.getColumnIndex("uf")));
                    record.setFone(cursor.getString(cursor.getColumnIndex("fone")));
                    record.setLatitude(cursor.getString(cursor.getColumnIndex("latitude")));
                    record.setLongitude(cursor.getString(cursor.getColumnIndex("longitude")));
                    record.setDistancia(fDistancia / 1000);

                    mList.add(record);
                }
            }
        }
        cursor.close();

        return mList;
    }

    public List<TableBancos> getListCidadeBairro(String latitude, String longitude, String uf, String cidade, String bairro) {

        List<TableBancos> mList = new ArrayList<>();

        String sBanco_1 = "";
        String sBanco_2 = "";
        String sBanco_3 = "";
        String sBanco_4 = "";
        String sBanco_5 = "";
        String sTipo = "";

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

        tableConfig.getRecord("edtTipo");
        sTipo = tableConfig.getValor();

        Cursor cursor;

        if (sBanco_1.trim().length() == 0 && sBanco_2.trim().length() == 0 && sBanco_3.trim().length() == 0 && sBanco_4.trim().length() == 0 && sBanco_5.trim().length() == 0) {
            if (sTipo.trim().length() != 0) {
                if (bairro.trim().length() != 0) {
                    cursor = Funcoes.mDataBase.query(mTable, new String[]{"*"}, "uf = ? AND cidade = ? AND bairro = ? AND tipo = ?", new String[]{uf, cidade, bairro, sTipo}, null, null, null);
                } else {
                    cursor = Funcoes.mDataBase.query(mTable, new String[]{"*"}, "uf = ? AND cidade = ? AND tipo = ?", new String[]{uf, cidade, sTipo}, null, null, null);
                }
            } else {
                if (bairro.trim().length() != 0) {
                    cursor = Funcoes.mDataBase.query(mTable, new String[]{"*"}, "uf = ? AND cidade = ? AND bairro = ?", new String[]{uf, cidade, bairro}, null, null, null);
                } else {
                    cursor = Funcoes.mDataBase.query(mTable, new String[]{"*"}, "uf = ? AND cidade = ?", new String[]{uf, cidade}, null, null, null);
                }
            }
        } else {
            if (sTipo.trim().length() != 0) {
                if (bairro.trim().length() != 0) {
                    cursor = Funcoes.mDataBase.query(mTable, new String[]{"*"}, "uf = ? AND cidade = ? AND bairro = ? AND tipo = ? AND (nome_banco = ? OR nome_banco = ? OR nome_banco = ? OR nome_banco = ? OR nome_banco = ?)", new String[]{uf, cidade, bairro, sTipo, sBanco_1, sBanco_2, sBanco_3, sBanco_4, sBanco_5}, null, null, null);
                } else {
                    cursor = Funcoes.mDataBase.query(mTable, new String[]{"*"}, "uf = ? AND cidade = ? AND tipo = ? AND (nome_banco = ? OR nome_banco = ? OR nome_banco = ? OR nome_banco = ? OR nome_banco = ?)", new String[]{uf, cidade, sTipo, sBanco_1, sBanco_2, sBanco_3, sBanco_4, sBanco_5}, null, null, null);
                }
            } else {
                if (bairro.trim().length() != 0) {
                    cursor = Funcoes.mDataBase.query(mTable, new String[]{"*"}, "uf = ? AND cidade = ? AND bairro = ? AND (nome_banco = ? OR nome_banco = ? OR nome_banco = ? OR nome_banco = ? OR nome_banco = ?)", new String[]{uf, cidade, bairro, sBanco_1, sBanco_2, sBanco_3, sBanco_4, sBanco_5}, null, null, null);
                } else {
                    cursor = Funcoes.mDataBase.query(mTable, new String[]{"*"}, "uf = ? AND cidade = ? AND (nome_banco = ? OR nome_banco = ? OR nome_banco = ? OR nome_banco = ? OR nome_banco = ?)", new String[]{uf, cidade, sBanco_1, sBanco_2, sBanco_3, sBanco_4, sBanco_5}, null, null, null);
                }
            }
        }

        while (cursor.moveToNext()) {

            if (cursor.getString(cursor.getColumnIndex("latitude")).trim().length() != 0) {

                Location locationA = new Location("locationA");
                locationA.setLatitude(Double.valueOf(latitude));
                locationA.setLongitude(Double.valueOf(longitude));

                Location locationB = new Location("locationB");
                locationB.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
                locationB.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));

                double fDistancia = locationA.distanceTo(locationB);

                TableBancos record = new TableBancos();

                record.setId(cursor.getInt(cursor.getColumnIndex("id")));
                record.setNomeBanco(cursor.getString(cursor.getColumnIndex("nome_banco")));
                record.setCodAgencia(cursor.getString(cursor.getColumnIndex("cod_agencia")));
                record.setNomeAgencia(cursor.getString(cursor.getColumnIndex("nome_agencia")));
                record.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
                record.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
                record.setComplemento(cursor.getString(cursor.getColumnIndex("complemento")));
                record.setBairro(cursor.getString(cursor.getColumnIndex("bairro")));
                record.setCep(cursor.getString(cursor.getColumnIndex("cep")));
                record.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));
                record.setUf(cursor.getString(cursor.getColumnIndex("uf")));
                record.setFone(cursor.getString(cursor.getColumnIndex("fone")));
                record.setLatitude(cursor.getString(cursor.getColumnIndex("latitude")));
                record.setLongitude(cursor.getString(cursor.getColumnIndex("longitude")));
                record.setDistancia(fDistancia / 1000);

                mList.add(record);
            }
        }
        cursor.close();

        return mList;
    }

}
