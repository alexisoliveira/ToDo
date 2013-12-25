package br.com.alexisoliveira.todo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import br.com.alexisoliveira.todo.model.Tarefa;
import br.com.alexisoliveira.todo.service.ServiceTarefa;
import br.com.alexisoliveira.todo.util.Mask;
import br.com.alexisoliveira.todo.util.ValidatorDate;

public class ActivityCadastrarTarefa extends Activity implements
		OnClickListener {

	private ServiceTarefa datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_tarefa);

		datasource = new ServiceTarefa(this);
		datasource.open();

		((Button) findViewById(R.id.btnOk)).setOnClickListener(this);
		((Button) findViewById(R.id.btnCancelar)).setOnClickListener(this);

		EditText data = (EditText) findViewById(R.id.txtDataFinalizacao);
		data.addTextChangedListener(Mask.insert("##/##/####", data));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cadastrar_tarefa, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnOk) {
			String dataFinalizacao = ((EditText) findViewById(R.id.txtDataFinalizacao))
					.getText().toString();
			String nome = ((EditText) findViewById(R.id.txtNome)).getText()
					.toString();
			boolean notificar = ((CheckBox) findViewById(R.id.ckbNotificar))
					.isChecked();

			String observacao = ((EditText) findViewById(R.id.txtObservacao))
					.getText().toString();

			boolean status = true;

			Tarefa tarefa = new Tarefa();
			tarefa.setDataFinalizacao(dataFinalizacao);
			tarefa.setNome(nome);
			tarefa.setNotificar(notificar);
			tarefa.setObservacao(observacao);
			tarefa.setStatus(status);

			if (Validacao(tarefa)) {
				datasource.createTarefa(tarefa);

				String msg = "Tarefa cadastrada com sucesso.";
				Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER_HORIZONTAL
						| Gravity.CENTER_VERTICAL, 0, 0);
				toast.show();

				finish();
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

	private boolean Validacao(Tarefa t) {
		ValidatorDate vd = new ValidatorDate();
		
		//Validação Nome
		if(t.getNome().length() < 3){
			String msg = "O nome é muito curto, informe pelo menos 3 caracteres.";
			Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_HORIZONTAL
					| Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
			
			return false;
		}
		//Validação Data
		else if (!t.getDataFinalizacao().equals("") && !vd.isThisDateValid(t.getDataFinalizacao(), "dd/MM/yyyy")) {
			String msg = "Data inválida.";
			Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_HORIZONTAL
					| Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
			
			return false;
		}

		return true;
	}

}
