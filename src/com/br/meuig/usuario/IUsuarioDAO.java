package com.br.meuig.usuario;

import java.util.Collection;

public interface IUsuarioDAO {
    void insert(Usuario usuario);
    void deleteWhereId(int id);
    void update(Usuario usuario);
    Usuario getWhereId(int id);
    Collection <Usuario> getAll();
}
