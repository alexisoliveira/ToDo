package br.com.alexisoliveira.todo.service;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.com.alexisoliveira.todo.datasource.DataSource;
import br.com.alexisoliveira.todo.model.Usuario;
import br.com.alexisoliveira.todo.util.UsuarioLogado;

public class ServiceUsuario {

	// Database fields
	private SQLiteDatabase database;
	private DataSource dbHelper;
	private String[] allColumns = { DataSource.COLUMN_ID_USUARIO,
			DataSource.COLUMN_EMAIL, DataSource.COLUMN_SENHA,
			DataSource.COLUMN_TELEFONE };

	public ServiceUsuario(Context context) {
		dbHelper = new DataSource(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Usuario createUsuario(Usuario usuario) {
		ContentValues values = new ContentValues();
		values.put(DataSource.COLUMN_EMAIL, usuario.getEmail());
		values.put(DataSource.COLUMN_SENHA, usuario.getSenha());
		values.put(DataSource.COLUMN_TELEFONE, usuario.getTelefone());

		long insertId = database.insert(DataSource.TABLE_USUARIO, null, values);

		Cursor cursor = database.query(DataSource.TABLE_USUARIO, allColumns,
				DataSource.COLUMN_ID_USUARIO + " = " + insertId, null, null,
				null, null);

		cursor.moveToFirst();
		Usuario newUsuario = cursorToUsuario(cursor);
		cursor.close();
		return newUsuario;
	}

	public void deleteUsuario(Usuario usuario) {
		long id = usuario.getId();
		System.out.println("Usuario deletado com o ID: " + id);
		database.delete(DataSource.TABLE_USUARIO, DataSource.COLUMN_ID_USUARIO
				+ " = " + id, null);
	}

	public List<Usuario> getAllUsuario() {
		List<Usuario> usuarios = new ArrayList<Usuario>();

		Cursor cursor = database.query(DataSource.TABLE_USUARIO, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Usuario usuario = cursorToUsuario(cursor);
			usuarios.add(usuario);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return usuarios;
	}

	private Usuario cursorToUsuario(Cursor cursor) {
		Usuario usuario = new Usuario();
		usuario.setId(cursor.getLong(0));
		usuario.setEmail(cursor.getString(1));
		usuario.setSenha(cursor.getString(2));
		usuario.setTelefone(cursor.getString(3));
		return usuario;
	}

	public boolean exists(String telefone) {
		boolean result = false;

		String[] args = new String[1];
		args[0] = String.valueOf(telefone);

		String sql = " select " + DataSource.COLUMN_ID_USUARIO + " from "
				+ DataSource.TABLE_USUARIO + " where "
				+ DataSource.COLUMN_TELEFONE + " = ?";

		Cursor cursor = database.rawQuery(sql, args);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			result = true;
			break;
		}
		// make sure to close the cursor
		cursor.close();

		return result;
	}

	public boolean efetuarLogin(String telefone, String senha) {
		boolean result = false;

		String[] args = new String[2];
		args[0] = String.valueOf(telefone);
		args[1] = String.valueOf(senha);

		String sql = " select " + DataSource.COLUMN_ID_USUARIO + " from "
				+ DataSource.TABLE_USUARIO + " where "
				+ DataSource.COLUMN_TELEFONE + " = ? and "
				+ DataSource.COLUMN_SENHA + " = ?";

		Cursor cursor = database.rawQuery(sql, args);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			result = true;
			UsuarioLogado.setId_usuario(cursor.getLong(0));
			break;
		}
		// make sure to close the cursor
		cursor.close();

		return result;
	}
}
