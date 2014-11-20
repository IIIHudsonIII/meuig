package com.br.meuig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.br.meuig.usuario.Usuario;
import com.br.meuig.usuario.UsuarioDAO;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class InicioActivity extends ListActivity implements
	OnItemClickListener, OnItemLongClickListener,
	android.content.DialogInterface.OnClickListener {

    private DataBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	// setContentView(R.layout.activity_inicio);
	helper = new DataBaseHelper(this);
	getListView().setOnItemClickListener(this);
	getListView().setOnItemLongClickListener(this);
	ActionBar ab = getActionBar();
	ab.setSubtitle("Perfis");

    }

    @Override
    protected void onStart() {
	// nao eh no onCreate e sim no onstart que deve ser carregada a view.
	super.onStart();
	updateListView();
    }

    private void updateListView() {
	String[] from = { "nome", "dataNasc" };
	int[] to = { android.R.id.text1, android.R.id.text2 };
	UsuarioDAO dao = new UsuarioDAO(this);	
	SimpleAdapter adapter = new SimpleAdapter(this, dao.getAll2(),
		android.R.layout.simple_list_item_2, from, to);
	setListAdapter(adapter);// listActivity ja implementa adapter
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// para criar itens para este meu, eh na pasta res/menu
	getMenuInflater().inflate(R.menu.inicio, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

	int id = item.getItemId();
	if (id == R.id.novoUsuario) {
	    // vai pra tela de cadastro
	    Intent intent = new Intent(this, CadUsuarioActivity.class);
	    startActivity(intent);

	    return true;
	}
	return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
	// TODO Auto-generated method stub

    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position,
	    long id) {
	// ou vai pra activity de usuariosm ou entra na tela de graficos e
	// medicoes.

	// vai obter um registro do adapter
	Map<String, String> registro = (Map<String, String>) adapter
		.getItemAtPosition(position);

	Toast.makeText(getApplicationContext(),
		"Selecionado perfil de " + registro.get("nome"),
		Toast.LENGTH_SHORT).show();
	// criarAlertdialog().show();

	Intent intent = new Intent(getApplicationContext(),
		OpcoesActivity.class);
	// pra mandar um map, tem que fazer typecast pra hashmap pois implementa
	// serializable
	intent.putExtra("HASHMAP_USUARIO", (HashMap<String, String>) registro);
	startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapter, View view,
	    int position, long id) {
	// vai abrir tela pra editar ou excluir

	Map<String, String> registro = (Map<String, String>) adapter
		.getItemAtPosition(position);
	Toast.makeText(this, "clique longo no item " + registro.get("_id"),
		Toast.LENGTH_LONG).show();
	return true;
    }

}
