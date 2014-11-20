package com.br.meuig;

import com.br.meuig.usuario.Usuario;
import com.br.meuig.usuario.UsuarioDAO;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class CadUsuarioActivity extends Activity {

    private DataBaseHelper helper;
    private EditText nomeEd, dataNascEd, AlturaEd, pesoEd;
    private RadioGroup ie_sexo;
    private Spinner tipoGlicemiaSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_cad_usuario);

	this.nomeEd = (EditText) findViewById(R.id.nome_ed);
	this.dataNascEd = (EditText) findViewById(R.id.dataNasc_et);
	this.AlturaEd = (EditText) findViewById(R.id.altura_et);
	this.pesoEd = (EditText) findViewById(R.id.peso_et);
	this.tipoGlicemiaSpinner = (Spinner) findViewById(R.id.tipo_glicemia_spinner);
	this.ie_sexo = (RadioGroup) findViewById(R.id.radioSexo);

	ie_sexo.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "usuario " + v.getId(),
			Toast.LENGTH_LONG).show();
	    }
	});
	helper = new DataBaseHelper(this);

	loadDataSpinner();// carrega spinner
	ActionBar ab = getActionBar();
	// tratar aqui pegando os extras da activity que invocou esta... pode
	// editar ou incluir novo usuario.
	ab.setTitle("Novo usuário");
	ab.setSubtitle("Informações básicas");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.cad_usuario, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	// Handle action bar item clicks here. The action bar will
	// automatically handle clicks on the Home/Up button, so long
	// as you specify a parent activity in AndroidManifest.xml.
	int id = item.getItemId();
	if (id == R.id.Salvar_actionIcon) {
	    if (!Salvar()) {
		Toast.makeText(getApplicationContext(),
			"Preencha os campos corretamente.", Toast.LENGTH_LONG)
			.show();
		return false;
	    }
	    Toast.makeText(getApplicationContext(),
		    "usuário salvo com sucesso!", Toast.LENGTH_LONG).show();
	    finishHim();// aqui retorna para a activity anterior a esta, que
	    // esta na pilha.
	    return true;
	}
	return super.onOptionsItemSelected(item);
    }

    private void finishHim() {
	super.finish();
    }

    public void loadDataSpinner() {
	SQLiteDatabase db = helper.getReadableDatabase();
	Cursor cursor = db.rawQuery(
		"SELECT _id , descricao FROM glicemias ORDER BY _id", null);
	cursor.moveToFirst();

	String[] from = new String[] { "descricao" };
	int[] to = new int[] { android.R.id.text1 };
	SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
		android.R.layout.simple_spinner_item, cursor, from, to);

	sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	tipoGlicemiaSpinner.setAdapter(sca);
    }

    private boolean Salvar() {
	UsuarioDAO dao = new UsuarioDAO(this);
	Usuario u = getUsuarioFromFields();

	if ("".equals(u.getNome())) {
	    return false;
	}
	if ("".equals(u.getDataNasc())) {
	    return false;
	}
	if ("".equals(u.getAltura())) {
	    return false;
	}
	if ("".equals(u.getPeso())) {
	    return false;
	}
	dao.insert(u);
	
	return true;
    }

    private Usuario getUsuarioFromFields() {
	Usuario u = new Usuario();
	u.setNome(nomeEd.getText().toString());
	u.setAltura(Integer.parseInt(AlturaEd.getText().toString()));
	u.setDataNasc(dataNascEd.getText().toString());
	u.setPeso(Double.parseDouble(pesoEd.getText().toString()));

	switch (ie_sexo.getCheckedRadioButtonId()) {
	case (R.id.feminino_radio):
	    u.setSexo("F");
	    break;
	case (R.id.masculino_radio):
	    u.setSexo("M");
	    break;
	}
	u.setGlicemia_id(this.tipoGlicemiaSpinner.getSelectedItemId());
	return u;
    }

}
