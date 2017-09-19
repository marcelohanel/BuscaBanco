package br.com.mmmobile.buscabanco.Comparators;

import java.util.Comparator;

import br.com.mmmobile.buscabanco.Domains.DomainValores;

public class ComparatorDomainValores implements Comparator<DomainValores> {

    private int mOrdem;

    public ComparatorDomainValores(int ordem) {
        this.mOrdem = ordem;
    }

    @Override
    public int compare(DomainValores lhs, DomainValores rhs) {

        int iRetorno = 0;

        if (mOrdem == 0) {
            iRetorno = lhs.getValor().compareToIgnoreCase(rhs.getValor());
        }

        return iRetorno;
    }
}
