package br.com.alexisoliveira.todo.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.alexisoliveira.todo.R;
import br.com.alexisoliveira.todo.model.Tarefa;


public class AdapterTarefa extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Tarefa> tarefas;

	public AdapterTarefa(Context context, List<Tarefa> tarefas) {
		this.tarefas = tarefas;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return tarefas.size();
	}

	@Override
	public Object getItem(int position) {
		return tarefas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_tarefa, null);
		}
		
		TextView idTarefa = (TextView) convertView.findViewById(R.id.id_tarefa);
		TextView nomeTarefa = (TextView) convertView.findViewById(R.id.nm_tarefa);

		Tarefa tarefa = tarefas.get(position);
		
		idTarefa.setText(String.valueOf(tarefa.getId()));
		nomeTarefa.setText(tarefa.getNome());		
		
		return convertView;
	}

}
