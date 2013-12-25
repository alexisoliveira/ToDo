package br.com.alexisoliveira.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.com.alexisoliveira.todo.model.Tarefa;
import br.com.alexisoliveira.todo.service.ServiceTarefa;

public class ActivityFinalizarTarefa extends Activity implements
		OnClickListener {

	private static Tarefa tarefa = new Tarefa();
	private ServiceTarefa datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finalizar_tarefa);
		
		datasource = new ServiceTarefa(this);
		datasource.open();

		Intent intent = getIntent();
		Bundle params = intent.getExtras();

		if (params != null) {
			tarefa = (Tarefa) params.getSerializable("tarefa");
			((TextView) findViewById(R.id.lblTarefa)).setText(tarefa.getNome());
			((EditText) findViewById(R.id.txtObservacao)).setText(tarefa
					.getObservacao());
		}

		((Button) findViewById(R.id.btnOk)).setOnClickListener(this);
		((Button) findViewById(R.id.btnCancelar)).setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_finalizar_tarefa, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnOk) {

			boolean status = !((CheckBox) findViewById(R.id.ckbFinalizarTarefa))
					.isChecked();
			String observacao = ((EditText) findViewById(R.id.txtObservacao))
					.getText().toString();

			tarefa.setObservacao(observacao);
			tarefa.setStatus(status);

			datasource.updateTarefa(tarefa);

			String msg = "";
			if (!status) {
				msg = "Tarefa finalizada com sucesso.";
			} else {
				msg = "Tarefa alterada com sucesso.";
			}
			
			Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_HORIZONTAL
					| Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();

			finish();

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

}
