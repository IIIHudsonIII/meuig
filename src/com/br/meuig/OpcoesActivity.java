package com.br.meuig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class OpcoesActivity extends ListActivity implements OnItemClickListener {
	private static final int OPCAO_MEDICAO = 1;
	private static final int OPCAO_GRAFICOS = 2;
	private HashMap<String, String> registroUsuario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_opcoes);
		registroUsuario = (HashMap<String, String>) getIntent()
				.getSerializableExtra("HASHMAP_USUARIO");

		getActionBar().setTitle(registroUsuario.get("nome"));
		getActionBar().setSubtitle("Opções");
		getListView().setOnItemClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		updateListView();
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.opcoes, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_Info) {
			Toast.makeText(getApplicationContext(),
					"Informações a serem implementadas", Toast.LENGTH_LONG)
					.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void updateListView() {
		String[] from = { "descricao", "detalhe" };
		int[] to = { android.R.id.text1, android.R.id.text2 };
		SimpleAdapter adapter = new SimpleAdapter(this, getOpcoes(),
				android.R.layout.simple_list_item_2, from, to);
		getListView().setAdapter(adapter);
	}

	private List<Map<String, String>> getOpcoes() {
		List<Map<String, String>> lista = new ArrayList<Map<String, String>>();

		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("_id", OPCAO_MEDICAO + "");
		mapa.put("descricao", "Medições");
		mapa.put("detalhe", "Medições de IG realizadas");
		lista.add(mapa);

		mapa = new HashMap<String, String>();
		mapa.put("_id", OPCAO_GRAFICOS + "");
		mapa.put("descricao", "Gráficos");
		mapa.put("detalhe", "veja o andamento em gráficos");
		lista.add(mapa);

		return lista;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Map<String, String> registro = (Map<String, String>) parent
				.getItemAtPosition(position);
		int id_opcao = Integer.parseInt(registro.get("_id"));

		Intent intent;

		switch (id_opcao) {
		case OPCAO_MEDICAO:
			intent = new Intent(getApplicationContext(), MedicoesActivity.class);
			intent.putExtra("HASHMAP_USUARIO", registroUsuario);
			startActivity(intent);
			break;
		case OPCAO_GRAFICOS:
			Toast.makeText(getApplicationContext(), "falta implementar",
					Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
	}

}
