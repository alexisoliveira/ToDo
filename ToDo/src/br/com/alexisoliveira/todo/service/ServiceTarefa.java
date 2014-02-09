package br.com.alexisoliveira.todo.service;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.com.alexisoliveira.todo.datasource.DataSource;
import br.com.alexisoliveira.todo.model.Tarefa;
import br.com.alexisoliveira.todo.util.Constant;
import br.com.alexisoliveira.todo.util.UsuarioLogado;

public class ServiceTarefa {

	// Database fields
	private SQLiteDatabase database;
	private DataSource dbHelper;
	private String[] allColumns = { Constant.COLUMN_ID_TAREFA,
			Constant.COLUMN_DATA_FINALIZACAO, Constant.COLUMN_NOME,
			Constant.COLUMN_NOTIFICAR, Constant.COLUMN_OBSERVACAO,
			Constant.COLUMN_STATUS, Constant.COLUMN_ID_USUARIO };

	public ServiceTarefa(Context context) {
		dbHelper = new DataSource(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Tarefa createTarefa(Tarefa tarefa) {
		ContentValues values = new ContentValues();
		values.put(Constant.COLUMN_DATA_FINALIZACAO,
				tarefa.getDataFinalizacao());
		values.put(Constant.COLUMN_NOME, tarefa.getNome());
		values.put(Constant.COLUMN_NOTIFICAR,
				tarefa.isNotificar() == true ? 1 : 0);
		values.put(Constant.COLUMN_OBSERVACAO, tarefa.getObservacao());
		values.put(Constant.COLUMN_STATUS, tarefa.isStatus() == true ? 1 : 0);
		values.put(Constant.COLUMN_ID_USUARIO, UsuarioLogado.getId_usuario());

		long insertId = database.insert(Constant.TABLE_TAREFA, null, values);

		Cursor cursor = database.query(Constant.TABLE_TAREFA, allColumns,
				Constant.COLUMN_ID_TAREFA + " = " + insertId, null, null,
				null, null);

		cursor.moveToFirst();
		Tarefa newTarefa = cursorToTarefa(cursor);
		cursor.close();
		return newTarefa;
	}

	public void updateTarefa(Tarefa tarefa) {
		ContentValues values = new ContentValues();
		values.put(Constant.COLUMN_DATA_FINALIZACAO,
				tarefa.getDataFinalizacao());
		values.put(Constant.COLUMN_NOME, tarefa.getNome());
		values.put(Constant.COLUMN_NOTIFICAR,
				tarefa.isNotificar() == true ? 1 : 0);
		values.put(Constant.COLUMN_OBSERVACAO, tarefa.getObservacao());
		values.put(Constant.COLUMN_STATUS, tarefa.isStatus() == true ? 1 : 0);
		values.put(Constant.COLUMN_ID_TAREFA, tarefa.getId());
		values.put(Constant.COLUMN_ID_USUARIO, UsuarioLogado.getId_usuario());

		database.update(Constant.TABLE_TAREFA, values,
				Constant.COLUMN_ID_TAREFA + " = " + tarefa.getId(), null);
	}

	public void deleteTarefa(Tarefa Tarefa) {
		long id = Tarefa.getId();
		System.out.println("Tarefa deletada com o ID: " + id);
		database.delete(Constant.TABLE_TAREFA, Constant.COLUMN_ID_TAREFA
				+ " = " + id, null);
	}

	public List<Tarefa> getAllTarefas() {
		List<Tarefa> tarefas = new ArrayList<Tarefa>();

		Cursor cursor = database
				.query(Constant.TABLE_TAREFA, allColumns,
						Constant.COLUMN_STATUS + " = 1 and "
								+ Constant.COLUMN_ID_USUARIO + " = "
								+ UsuarioLogado.getId_usuario(), null, null,
						null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Tarefa tarefa = cursorToTarefa(cursor);
			tarefas.add(tarefa);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return tarefas;
	}

	private Tarefa cursorToTarefa(Cursor cursor) {
		Tarefa tarefa = new Tarefa();
		tarefa.setId(cursor.getLong(0));
		tarefa.setDataFinalizacao(cursor.getString(1));
		tarefa.setNome(cursor.getString(2));
		tarefa.setNotificar(cursor.getString(3) == "1" ? true : false);
		tarefa.setObservacao(cursor.getString(4));
		tarefa.setStatus(cursor.getString(5) == "1" ? true : false);
		tarefa.setId_usuario(cursor.getLong(6));

		return tarefa;
	}
}
