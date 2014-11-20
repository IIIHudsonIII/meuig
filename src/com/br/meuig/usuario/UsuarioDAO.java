package com.br.meuig.usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.br.meuig.DataBaseHelper;

public class UsuarioDAO implements IUsuarioDAO {
    DataBaseHelper helper;

    public UsuarioDAO(Context context) {
	helper = new DataBaseHelper(context);
    }

    @Override
    public void insert(Usuario usuario) {
	SQLiteDatabase db = helper.getWritableDatabase();
	db.insert("usuarios", null, getUsuarioValues(usuario));
    }

    @Override
    public void deleteWhereId(int id) {
	SQLiteDatabase db = helper.getWritableDatabase();
	String[] whereArgs = { id + "" };
	String whereClause = " _id = ?";
	db.delete("usuarios", whereClause, whereArgs);
    }

    @Override
    public void update(Usuario usuario) {
	SQLiteDatabase db = helper.getWritableDatabase();
	String whereClause = " id = ?";
	String[] whereArgs = { usuario.getId() + "" };
	db.update("usuarios", getUsuarioValues(usuario), whereClause, whereArgs);
    }

    @Override
    public Usuario getWhereId(int id) {
	SQLiteDatabase db = helper.getWritableDatabase();
	String[] selectionArgs = { id + "" };
	Cursor cursor = db
		.rawQuery(
			"SELECT _id, nome, dataNasc, altura, peso,sexo, glicemia_id FROM usuarios WHERE _id = ? ORDER BY _id DESC",
			selectionArgs);
	cursor.moveToFirst();

	Usuario usuario = null;
	if (cursor.getCount() > 0) {
	    usuario = getUsuario(cursor);
	}

	return usuario;
    }

    @Override
    public Collection<Usuario> getAll() {
	SQLiteDatabase db = helper.getReadableDatabase();
	Cursor cursor = db
		.rawQuery(
			"SELECT _id, nome, dataNasc, altura, peso,sexo, glicemia_id FROM usuarios ORDER BY _id DESC ",
			null);
	Collection<Usuario> usuarios = new ArrayList<Usuario>();
	cursor.moveToFirst();

	for (int i = 0; i < cursor.getCount(); i++) {
	    usuarios.add(getUsuario(cursor));
	    cursor.moveToNext();
	}
	return usuarios;
    }

    public List<Map<String,String>> getAll2(){
	SQLiteDatabase db = helper.getReadableDatabase();	
	Cursor cursor = db.rawQuery("SELECT _id, nome, dataNasc, altura, peso,sexo, glicemia_id FROM usuarios ORDER BY _id DESC ", null);	
	cursor.moveToFirst();
	
	List<Map<String,String>> colecao = new ArrayList<Map<String,String>>();
	
	for (int i = 0; i < cursor.getCount(); i++) {
	    Map<String,String> mapa = new HashMap<String, String>();
	    mapa.put("_id", cursor.getString(0));
	    mapa.put("nome", cursor.getString(1));
	    mapa.put("dataNasc", cursor.getString(2));
	    mapa.put("altura", cursor.getString(3));
	    mapa.put("peso", cursor.getString(4));
	    mapa.put("sexo", cursor.getString(5));
	    mapa.put("glicemia_id", cursor.getString(6));
	    colecao.add(mapa);
	    cursor.moveToNext();
	}
	return colecao;
    }

    private ContentValues getUsuarioValues(Usuario usuario) {
	ContentValues values = new ContentValues();
	values.put("nome", usuario.getNome());
	values.put("dataNasc", usuario.getDataNasc());
	values.put("altura", usuario.getAltura());
	values.put("peso", usuario.getPeso());
	values.put("sexo", usuario.getSexo() + "");
	values.put("glicemia_id", usuario.getGlicemia_id());
	return values;
    }

    private Usuario getUsuario(Cursor cursor) {
	Usuario usuario = new Usuario();
	usuario.setId(cursor.getInt(0));
	usuario.setNome(cursor.getString(1));
	usuario.setDataNasc(cursor.getString(2));
	usuario.setAltura(cursor.getInt(3));
	usuario.setPeso(cursor.getDouble(4));
	usuario.setSexo(cursor.getString(5));
	usuario.setGlicemia_id(cursor.getLong(6));
	return usuario;
    }

}
