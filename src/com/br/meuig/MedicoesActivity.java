package com.br.meuig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MedicoesActivity extends ListActivity implements OnItemClickListener{

    HashMap<String, String> registroUsuario;
    DataBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	// setContentView(R.layout.activity_anotacao);
	try {
	    registroUsuario = (HashMap<String, String>) getIntent()
		    .getSerializableExtra("HASHMAP_USUARIO");
	} catch (Exception e) {
	    Toast.makeText(getApplicationContext(),
		    "erro ao obter informacoes de usuario!" + e.getMessage(),
		    Toast.LENGTH_LONG);
	}

	helper = new DataBaseHelper(this);
	
    }

    @Override
    protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();

	updateListaMedicoes();
    }

    private void updateListaMedicoes() {

	try {
	    String[] from = new String[] { "medicao_ds", "informacoes" };
	    int to[] = new int[] { android.R.id.text1, android.R.id.text2 };
	    SimpleAdapter adapter = new SimpleAdapter(this,
		    getMedicoes(), android.R.layout.simple_list_item_2, from,
		    to);
	    adapter.setDropDownViewResource(android.R.layout.simple_list_item_2);
	    setListAdapter(adapter);
	} catch (Exception e) {
	    Toast.makeText(
		    getApplicationContext(),
		    "erro ao atualizar lista de medicoes do usuario!"
			    + e.getMessage(), Toast.LENGTH_LONG).show();
	}

    }

    private List<Map<String, String>> getMedicoes() {
	// TODO Auto-generated method stub
	SQLiteDatabase db = helper.getReadableDatabase();
	StringBuilder sb = new StringBuilder();
	sb.append("SELECT 	a._id,      		");
	sb.append("		a.usuario_id,		");
	sb.append("		a.valorMedido,		");
	sb.append("		a.momento_id,		");
	sb.append("		a.dataMedicao,		");
	sb.append("		b.descricao 	momento_ds	");	
	sb.append(" FROM 	medicoes a, momentos b	");
	sb.append(" WHERE 	a.usuario_id = ?	AND a.momento_id = b._id	");
	sb.append("	ORDER BY a.dataMedicao	DESC	");

	Cursor cursor = db.rawQuery(sb.toString(),
		new String[] { registroUsuario.get("_id") });
	cursor.moveToFirst();

	List<Map<String, String>> lista = new ArrayList<Map<String, String>>();
	for (int i = 0; i < cursor.getCount(); i++) {
	    Map<String, String> mapa = new HashMap<String, String>();
	    mapa.put("_id", cursor.getString(0));
	    mapa.put("usuario_id", cursor.getString(1));
	    mapa.put("valorMedido", cursor.getString(2));
	    mapa.put("momento_id", cursor.getString(3));
	    mapa.put("dataMedicao", cursor.getString(4));
	    
	    mapa.put("medicao_ds","  "+cursor.getString(2)+" mg/dcl");
	    mapa.put("informacoes", cursor.getString(5) + "  Data: "+cursor.getString(4)+" hrs ");
	    lista.add(mapa);
	    cursor.moveToNext();
	}

	return lista;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.anotacao, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

	int id = item.getItemId();
	if (id == R.id.NovaAnotacao) {
	    // chama o cadastro de de medicoes
	    Intent intent = new Intent(getApplicationContext(),
		    CadmedicaoActivity.class);
	    intent.putExtra("HASHMAP_USUARIO", registroUsuario);
	    startActivity(intent);
	    return true;
	}
	return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
	    long id) {
	
	Map<String,String> map = (Map<String, String>) parent.getSelectedItem();
	
 	Intent intent = new Intent(this,CadmedicaoActivity.class);
	intent.putExtra("ID_MEDICAO", map.get("_id"));//pega o id da medicao que esta no item clicado
	startActivity(intent);
    }

}
