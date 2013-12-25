package br.com.alexisoliveira.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import br.com.alexisoliveira.todo.service.ServiceUsuario;
import br.com.alexisoliveira.todo.util.CodeActivities;

public class ActivityPrincipal extends Activity implements OnClickListener {

	private ServiceUsuario datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);

		((Button) findViewById(R.id.btnLogin)).setOnClickListener(this);
		((ProgressBar) findViewById(R.id.progressBar)).setVisibility(View.GONE);

		datasource = new ServiceUsuario(this);
		datasource.open();

		Log.v("Classe", "Activity Principal");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_principal, menu);
		return true;
	}

	private void startCadastrarUsuarioActivity() {
		Intent i = new Intent(this, ActivityCadastrarUsuario.class);
		startActivityForResult(i, 1);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnLogin) {
			String telefone = ((EditText) findViewById(R.id.txtTelefone))
					.getText().toString();
			String senha = ((EditText) findViewById(R.id.txtSenha)).getText()
					.toString();
			if (datasource.efetuarLogin(telefone, senha)) {

				startListaTarefaActivity();

				((ProgressBar) findViewById(R.id.progressBar))
						.setVisibility(View.VISIBLE);
			} else {
				String msg = "Usuário e/ou senha inválidos.";
				Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER_HORIZONTAL
						| Gravity.CENTER_VERTICAL, 0, 0);
				toast.show();

				((EditText) findViewById(R.id.txtSenha)).setText("");

			}
		} else if (v.getId() == R.id.lblUsuarioNaoCadastrado) {
			startCadastrarUsuarioActivity();
			((ProgressBar) findViewById(R.id.progressBar))
					.setVisibility(View.VISIBLE);

		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		((ProgressBar) findViewById(R.id.progressBar)).setVisibility(View.GONE);
		((EditText) findViewById(R.id.txtSenha)).setText("");
	}

	private void startListaTarefaActivity() {
		Intent i = new Intent(this, ActivityListaTarefas.class);
		startActivity(i);
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == CodeActivities.ACTVITY_CADASTRAR_USUARIO) {
			if (data != null) {
				String telefone = data.getStringExtra("telefone");
				((EditText) findViewById(R.id.txtTelefone)).setText(telefone);
			}
		}
	}
}
