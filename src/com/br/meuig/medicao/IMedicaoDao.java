package com.br.meuig.medicao;

import java.util.Collection;

import android.content.Context;

public interface IMedicaoDao {
    void Insert( Medicao medicao);
    void DeleteAllWhereUserID(int usuario_id);
    Medicao getWhereID(int id);
    Collection <Medicao> getWhereUsuarioID(int usuario_id); 
    void update(Medicao medicao);
    
}
