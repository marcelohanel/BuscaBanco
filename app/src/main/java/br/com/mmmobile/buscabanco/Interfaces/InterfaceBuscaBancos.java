package br.com.mmmobile.buscabanco.Interfaces;

import java.util.List;

import br.com.mmmobile.buscabanco.DataBase.TableBancos;

public interface InterfaceBuscaBancos {
    void onFinishTask(List<TableBancos> s);
}
