package com.br.meuig;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "meuIG.sqlite";
	private static final int DATABASE_VERSION = 1;

	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		StringBuilder sb = new StringBuilder();
		sb.append(" CREATE TABLE glicemias (			 ");
		sb.append("		_id 		INTEGER PRIMARY KEY, ");
		sb.append("		descricao 	TEXT  )				 ");
		db.execSQL(sb.toString());

		// só para ter mais de um perfil, mas no momento de compartilhar por sms
		// ou whats ou whatever, vai ser remetente o usuario
		// celular de fato.
		sb = new StringBuilder();
		sb.append(" CREATE TABLE usuarios (			");
		sb.append("     _id 		INTEGER PRIMARY KEY, ");
		sb.append("     nome 		TEXT, 			");
		sb.append("     dataNasc 	TEXT, 			");
		sb.append("		altura 		INTEGER, 		");
		sb.append("		peso 		NUMERIC, 		");
		sb.append("		sexo 		CHARACTER(1), 	");
		sb.append("		glicemia_id INTEGER REFERENCES glicemias(_id) ");// pega o
																		// index
																		// de um
																		// item
																		// de
																		// R.array.tipos_glicemia
		sb.append("		  )		");
		db.execSQL(sb.toString());

		sb = new StringBuilder();

		sb.append(" CREATE TABLE momentos ( _id INTEGER PRIMARY KEY, descricao TEXT )");
		db.execSQL(sb.toString());

		sb = new StringBuilder();
		sb = new StringBuilder();
		sb.append(" CREATE TABLE medicoes (   				");
		sb.append("		_id 		 INTEGER PRIMARY KEY , 	");	
		sb.append(" 	usuario_id 	 INTEGER REFERENCES usuarios(_id), 	");
		sb.append("		valorMedido	 INTEGER , 				");
		sb.append("		momento_id   INTEGER  REFERENCES momentos(_id), ");
		sb.append("     dataMedicao  TEXT,  				");
		sb.append("     observacao 	 TEXT  					");
		sb.append(" ) ");
		
		db.execSQL(sb.toString());

		loadGlicemias(db);// carrega registros de glicemias
		loadMomentos(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// ver como vai funcionar para atualizar a tabela com as mudancas de
		// campo.
		// db.execSQL("ALTER TABLE LIBRARY ADD COLUMN createdAt TEXT");
		// db.execSQL("ALTER TABLE LIBRARY ADD COLUMN openedAt INTEGER");
		// db.execSQL("ALTER TABLE LIBRARY ADD COLUMN lastPage TEXT");
	}

	private void loadGlicemias(SQLiteDatabase db) {
		String[] lista = { "Nenhum", "Diabetes tipo I", "Diabetes tipo II",
				"Gestacional", "Hipoglicemia", "HiperGlicemia" };

		for (int i = 0; i < lista.length; i++) {
			ContentValues values = new ContentValues();
			values.put("descricao", lista[i]);
			db.insert("glicemias", null, values);
		}

	}// glicemias

	private void loadMomentos(SQLiteDatabase db) {
		String[] lista = { "Antes de dormir", "Ao acordar", "Antes do almoço",
				"Depois do almoço", "Antes de lanche", "Depois de lanche",
				"Depois de medicação", "Antes de exercício",
				"Depois de exercício", "Em jejum" };

		for (int i = 0; i < lista.length; i++) {
			ContentValues values = new ContentValues();
			values.put("descricao", lista[i]);
			db.insert("momentos", null, values);
		}

	}

}
