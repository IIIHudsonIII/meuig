package com.br.meuig.medicao;

import java.util.Collection;

import android.content.Context;

public interface IMedicaoDao {
    void Insert( Medicao medicao);
    void DeleteAllWhereUserID(Long usuario_id);
    Medicao getWhereID(Long id);
    Collection <Medicao> getWhereUsuarioID(Long usuario_id); 
    void update(Medicao medicao);
    
}
