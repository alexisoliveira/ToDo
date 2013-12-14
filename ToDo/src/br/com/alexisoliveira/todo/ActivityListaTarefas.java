package br.com.alexisoliveira.todo;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import br.com.alexisoliveira.todo.adapter.AdapterTarefa;
import br.com.alexisoliveira.todo.model.Tarefa;
import br.com.alexisoliveira.todo.service.ServiceTarefa;

public class ActivityListaTarefas extends Activity {

	private ListView listViewCategoria;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_tarefas);
		
		listViewCategoria = (ListView) findViewById(R.id.lw_tarefa);
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
}
