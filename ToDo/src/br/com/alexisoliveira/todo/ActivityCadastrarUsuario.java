package br.com.alexisoliveira.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.alexisoliveira.todo.model.Usuario;
import br.com.alexisoliveira.todo.service.ServiceUsuario;
import br.com.alexisoliveira.todo.util.CodeActivities;
import br.com.alexisoliveira.todo.util.ValidatorEmail;

public class ActivityCadastrarUsuario extends Activity implements
		OnClickListener {
	private ServiceUsuario datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_usuario);

		datasource = new ServiceUsuario(this);
		datasource.open();

		((Button) findViewById(R.id.btnOk)).setOnClickListener(this);
		((Button) findViewById(R.id.btnCancelar)).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.btnOk) {
			String telefone = ((EditText) findViewById(R.id.txtTelefone))
					.getText().toString();
			String email = ((EditText) findViewById(R.id.txtEmail)).getText()
					.toString();
			String senha = ((EditText) findViewById(R.id.txtSenha)).getText()
					.toString();

			Usuario usuario = new Usuario();
			usuario.setEmail(email);
			usuario.setSenha(senha);
			usuario.setTelefone(telefone);

			if (Validacao(usuario)) {
				try {
					usuario = datasource.createUsuario(usuario);
				} catch (Exception ex) {
					String msg = "Erro ao cadastrar usuário.";
					Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER_HORIZONTAL
							| Gravity.CENTER_VERTICAL, 0, 0);
					toast.show();
					return;
				}
				try {
					if (datasource.CadastrarAtualizarUsuarioWS(usuario)) {
						datasource.AtualizarFlagSincronizacaoUsuario(usuario);
					}
				} catch (Exception ex) {
					String msg = "Erro ao sincronizar com o servidor.";
					Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER_HORIZONTAL
							| Gravity.CENTER_VERTICAL, 0, 0);
					toast.show();
				}

				Intent intent = new Intent(ActivityCadastrarUsuario.this,
						ActivityPrincipal.class);
				intent.putExtra("telefone", telefone);
				setResult(CodeActivities.ACTVITY_CADASTRAR_USUARIO, intent);
				finish();

				String msg = "Usuário cadastrado com sucesso.";
				Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER_HORIZONTAL
						| Gravity.CENTER_VERTICAL, 0, 0);
				toast.show();
			}

		} else {
			finish();
		}
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

	private boolean Validacao(Usuario u) {
		// Validar Email
		ValidatorEmail ve = new ValidatorEmail();
		if (!u.getEmail().equals("") && !ve.validate(u.getEmail())) {
			String msg = "Email inválido.";
			Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_HORIZONTAL
					| Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();

			return false;
		}
		// Validar Telefone
		else if (u.getTelefone().length() < 10) {
			String msg = "Informe o telefone com o DDD. Ex: 7912345678";
			Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_HORIZONTAL
					| Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();

			return false;
		} else if (datasource.exists(u.getTelefone())) {
			String msg = "Usuário já cadastrado.";
			Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_HORIZONTAL
					| Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();

			return false;
		}
		return true;
	}
}
