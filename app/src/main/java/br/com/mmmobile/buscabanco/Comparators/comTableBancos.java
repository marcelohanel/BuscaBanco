package br.com.mmmobile.buscabanco.Comparators;

import java.util.Comparator;

import br.com.mmmobile.buscabanco.DataBase.TableBancos;

public class comTableBancos implements Comparator<TableBancos> {

    private int mOrdem;

    public comTableBancos(int ordem) {
        this.mOrdem = ordem;
    }

    @Override
    public int compare(TableBancos lhs, TableBancos rhs) {

        int iRetorno = 0;

        if (mOrdem == 0) {
            iRetorno = lhs.getDistancia().compareTo(rhs.getDistancia());
        }

        return iRetorno;
    }
}

