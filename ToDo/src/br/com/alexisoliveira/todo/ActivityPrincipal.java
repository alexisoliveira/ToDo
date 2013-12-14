package br.com.alexisoliveira.todo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class ActivityPrincipal extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);

		((Button) findViewById(R.id.btnLogin)).setOnClickListener(this);
		((ProgressBar) findViewById(R.id.progressBar)).setVisibility(View.GONE);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_principal, menu);
		return true;
	}

	private void startCadastrarUsuarioActivity() {
		Intent i = new Intent(this, ActivityCadastrarUsuario.class);
		startActivity(i);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnLogin) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Info");
			alertDialog.setMessage("Usuário logado com sucesso!");
			// Set the Icon for the Dialog
			alertDialog.show();
			startListaTarefaActivity();
			
		} else if (v.getId() == R.id.lblUsuarioNaoCadastrado) {
			startCadastrarUsuarioActivity();
		}
		((ProgressBar) findViewById(R.id.progressBar))
				.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		((ProgressBar) findViewById(R.id.progressBar)).setVisibility(View.GONE);
	}
	
	private void startListaTarefaActivity() {
		Intent i = new Intent(this, ActivityListaTarefas.class);
		startActivity(i);
	}

}
