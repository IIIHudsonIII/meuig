package com.br.meuig;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.br.meuig.medicao.Medicao;
import com.br.meuig.medicao.MedicaoDAO;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class CadmedicaoActivity extends Activity {
    DataBaseHelper helper;
    Map<String, String> registroUsuario;
    private Spinner momentoSpinner;
    private EditText medicaoEd, dataEd, horaEd, obs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_cadmedicao);
	helper = new DataBaseHelper(this);

	momentoSpinner = (Spinner) findViewById(R.id.momento_spinner);
	medicaoEd = (EditText) findViewById(R.id.medicao_et);
	dataEd = (EditText) findViewById(R.id.data_et);
	horaEd = (EditText) findViewById(R.id.hora_et);
	obs = (EditText) findViewById(R.id.observacao_et);

	// quem chama esta activity passa um hashmap do usuario corrente. da pra
	// criar um singleton pro usuario.
	trataInicializacao();
	// carrega spinner
	loadMomentos();

	// carrega data hora
	dataEd.setText(getDataAtual());
	horaEd.setText(getHoraAtual());

    }

    private void trataInicializacao() {
	// trata titulo e subtitulo
	ActionBar ab = getActionBar();
	// String id_medicao = getIntent().getStringExtra("ID_MEDICAO");
	// if (id_medicao.isEmpty()) {
	registroUsuario = (Map<String, String>) getIntent()
		.getSerializableExtra("HASHMAP_USUARIO");
	ab.setSubtitle("" + registroUsuario.get("nome"));
	// } else
	// preencheCampos();

    }

    private void preencheCampos() {
	// SQLiteDatabase db = helper.getWritableDatabase();
	// ContentValues values = new ContentValues();
	// values.put("usuario_id", registroUsuario.get("_id"));
	// values.put("valorMedido",
	// Integer.parseInt(medicaoEd.getText().toString()));
	// values.put("dataMedicao", dataEd.getText().toString() + " "
	// + horaEd.getText().toString());
	// values.put("momento_id", momentoSpinner.getSelectedItemId());
	// values.put("observacao", obs.getText().toString());

    }

    private String getDataAtual() {
	Calendar c = Calendar.getInstance();
	return new StringBuilder().append(c.get(Calendar.DAY_OF_MONTH))
		.append("/").append(c.get(Calendar.MONTH) + 1).append("/")
		.append(c.get(Calendar.YEAR)).toString();
    }

    private String getHoraAtual() {
	Calendar c = Calendar.getInstance();
	return new StringBuilder().append(c.get(Calendar.HOUR_OF_DAY))
		.append(":").append(c.get(Calendar.MINUTE)).toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.cadmedicao, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

	int id = item.getItemId();
	if (id == R.id.salvar_ai) {
	    return salvarRegistro();
	}
	return super.onOptionsItemSelected(item);
    }

    private boolean salvarRegistro() {

	try {
	    if (!camposValidos()) {
		Toast.makeText(this, "Preencha os campos corretamente",
			Toast.LENGTH_LONG).show();
		return false;
	    }
	} catch (ParseException e1) {
	    Toast.makeText(this, "Data/hora informada está incorreta",
		    Toast.LENGTH_LONG).show();
	    return false;
	} catch (Exception e) {
	    Toast.makeText(this, "Erro o validar campos." + e.getMessage(),
		    Toast.LENGTH_LONG).show();
	    return false;
	}

	// chamar
	try {
	    Medicao medicao = new Medicao();

	    medicao.setUsuario_id(Long.parseLong(registroUsuario.get("_id")));
	    medicao.setDataMedicao(new StringBuilder().append(dataEd.getText().toString()).append(" ").append(horaEd.getText().toString()).toString());
	    medicao.setValorMedido(Integer.parseInt(medicaoEd.getText()
		    .toString()));
	    medicao.setMomento_id((long) momentoSpinner.getSelectedItemId());
	    medicao.setObservacao(obs.getText().toString());
	    MedicaoDAO m = new MedicaoDAO(this);
	    m.Insert( medicao);

	    finish();// vai voltar a pilha
	    Toast.makeText(getApplicationContext(),
		    "Medição salva com sucesso!", Toast.LENGTH_LONG).show();

	} catch (Exception e) {
	    Toast.makeText(
		    getApplicationContext(),
		    "Erro ao salvar registro!" + e.getClass().toString()
			    + " Erro:" + e.getMessage(), Toast.LENGTH_LONG)
		    .show();
	    return false;
	}
	return true;
    }

    private boolean camposValidos() throws ParseException {
	// private Spinner momentoSpinner;
	// private EditText medicaoEd,dataEd,horaEd,obs;
	if ("".equals(medicaoEd.getText().toString())) {
	    return false;
	}

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	Date data = new Date(sdf.parse(dataEd.getText().toString()).getTime());

	sdf = new SimpleDateFormat("HH:mm");
	data = new Date(sdf.parse(horaEd.getText().toString()).getTime());

	return true;
    }

    private void loadMomentos() {
	String[] from = new String[] { "descricao" };
	int[] to = new int[] { android.R.id.text1 };
	// SimpleAdapter adapter =
	// new SimpleAdapter(getApplicationContext(), getMomentos(),
	// android.R.layout.simple_list_item_1, from, to);
	// adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
	// momentoSpinner.setAdapter(adapter);

	SQLiteDatabase db = helper.getReadableDatabase();
	Cursor cursor = db.rawQuery(
		"SELECT _id, descricao FROM momentos ORDER BY _id", null);
	cursor.moveToFirst();
	SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
		android.R.layout.simple_spinner_item, cursor, from, to);
	sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	momentoSpinner.setAdapter(sca);

    }

}
