package com.br.meuig.medicao;

import java.util.ArrayList;
import java.util.Collection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.br.meuig.DataBaseHelper;

public class MedicaoDAO implements IMedicaoDao {
	// private static MedicaoDAO instance;
	private static final String TABLE_MEDICOES = "medicoes";
	DataBaseHelper helper;

	public MedicaoDAO(Context context) {
		helper = new DataBaseHelper(context);
	}

	@Override
	public void Insert(Medicao medicao) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.insert(TABLE_MEDICOES, null, getContentValuesFrom(medicao));
	}

	@Override
	public void DeleteAllWhereUserID(Long usuario_id) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] whereArgs = { usuario_id + "" };
		String whereClause = " usuario_id = ?";
		db.delete("medicoes", whereClause, whereArgs);

	}

	@Override
	public Medicao getWhereID(Long id) {
		SQLiteDatabase db = helper.getReadableDatabase();
		String[] where = { String.valueOf(id) };
		String s = "SELECT _id, usuario_id, valorMedido, momento_id, dataMedicao, observacao FROM medicoes WHERE _id = ?";
		Cursor cursor = db.rawQuery(s, where);
		Medicao medicao = null;
		if (cursor.getCount() > 0) {
			medicao = new Medicao();
			medicao.setId(cursor.getInt(0));
			medicao.setUsuario_id(cursor.getInt(1));
			medicao.setValorMedido(cursor.getInt(2));
			medicao.setMomento_id(cursor.getInt(3));
			medicao.setDataMedicao(cursor.getString(4));
			medicao.setObservacao(cursor.getString(5));
		}
		cursor.close();
		return medicao;
	}

	@Override
	public Collection<Medicao> getWhereUsuarioID(Long usuario_id) {
		SQLiteDatabase db = helper.getReadableDatabase();
		String[] where = { usuario_id + "" };
		String s = "SELECT _id, usuario_id, valorMedido, momento_id, dataMedicao, observacao FROM medicoes WHERE usuario_id = ?";
		Cursor cursor = db.rawQuery(s, where);
		cursor.moveToFirst();

		Collection<Medicao> colecao = new ArrayList<Medicao>();
		for (int i = 0; i < cursor.getCount(); i++) {
			Medicao medicao = new Medicao();
			medicao.setId(cursor.getInt(0));
			medicao.setUsuario_id(cursor.getInt(1));
			medicao.setValorMedido(cursor.getInt(2));
			medicao.setMomento_id(cursor.getInt(3));
			medicao.setDataMedicao(cursor.getString(4));
			medicao.setObservacao(cursor.getString(5));

			colecao.add(medicao);
			cursor.moveToNext();
		}
		cursor.close();

		return colecao;
	}

	@Override
	public void update(Medicao medicao) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String whereClause = " id = ?";
		String[] whereArgs = { medicao.getId() + "" };
		db.update("usuarios", getContentValuesFrom(medicao), whereClause,
				whereArgs);
	}

	private ContentValues getContentValuesFrom(Medicao medicao) {
		ContentValues values = new ContentValues();

		values.put("usuario_id", medicao.getUsuario_id());
		values.put("valorMedido", medicao.getValorMedido());
		values.put("momento_id", medicao.getMomento_id());
		values.put("dataMedicao", medicao.getDataMedicao());
		values.put("observacao", medicao.getObservacao());

		return values;
	}

}
