package br.com.alexisoliveira.todo;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import br.com.alexisoliveira.todo.adapter.AdapterTarefa;
import br.com.alexisoliveira.todo.model.Tarefa;
import br.com.alexisoliveira.todo.service.ServiceTarefa;

public class ActivityListaTarefas extends Activity implements OnClickListener,
		OnItemClickListener {

	private ListView listViewCategoria;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_tarefas);

		listViewCategoria = (ListView) findViewById(R.id.lw_tarefa);
		// listViewCategoria.setOnItemClickListener(this);

		((Button) findViewById(R.id.btnAdicionarTarefa))
				.setOnClickListener(this);

		listViewCategoria.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lista_tarefa, menu);

		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		List<Tarefa> tarefas = ServiceTarefa.getInstance().findAll();

		AdapterTarefa adapter = new AdapterTarefa(this, tarefas);

		listViewCategoria.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnAdicionarTarefa) {
			startCadastrarTarefaActivity();
		}
	}

	private void startCadastrarTarefaActivity() {
		Intent i = new Intent(this, ActivityCadastrarTarefa.class);
		startActivity(i);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Tarefa t = (Tarefa)parent.getItemAtPosition(position);
		String keyword = t.getNome();
		Log.v("value ", "result is " + keyword);

	}
}
