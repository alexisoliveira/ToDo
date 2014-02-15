package br.com.alexisoliveira.todo.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.com.alexisoliveira.todo.datasource.DataSource;
import br.com.alexisoliveira.todo.model.Usuario;
import br.com.alexisoliveira.todo.util.Constant;
import br.com.alexisoliveira.todo.util.UsuarioLogado;

import com.google.gson.Gson;

public class ServiceUsuario {

	// Database fields
	private SQLiteDatabase database;
	private DataSource dbHelper;
	private String[] allColumns = { Constant.COLUMN_ID_USUARIO,
			Constant.COLUMN_EMAIL, Constant.COLUMN_SENHA,
			Constant.COLUMN_TELEFONE,
			Constant.COLUMN_FL_SINCRONIZADO };

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
		values.put(Constant.COLUMN_EMAIL, usuario.getEmail());
		values.put(Constant.COLUMN_SENHA, usuario.getSenha());
		values.put(Constant.COLUMN_TELEFONE, usuario.getTelefone());
		values.put(Constant.COLUMN_FL_SINCRONIZADO, 0);

		long insertId = database.insert(Constant.TABLE_USUARIO, null, values);

		Cursor cursor = database.query(Constant.TABLE_USUARIO, allColumns,
				Constant.COLUMN_ID_USUARIO + " = " + insertId, null, null,
				null, null);

		cursor.moveToFirst();
		Usuario newUsuario = cursorToUsuario(cursor);
		cursor.close();
		return newUsuario;
	}

	public void deleteUsuario(Usuario usuario) {
		long id = usuario.getId();
		System.out.println("Usuario deletado com o ID: " + id);
		database.delete(Constant.TABLE_USUARIO, Constant.COLUMN_ID_USUARIO
				+ " = " + id, null);
	}

	public List<Usuario> getAllUsuario() {
		List<Usuario> usuarios = new ArrayList<Usuario>();

		Cursor cursor = database.query(Constant.TABLE_USUARIO, allColumns,
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

		String sql = " select " + Constant.COLUMN_ID_USUARIO + " from "
				+ Constant.TABLE_USUARIO + " where " + Constant.COLUMN_TELEFONE
				+ " = ?";

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

		String sql = " select " + Constant.COLUMN_ID_USUARIO + ", "
				+ Constant.COLUMN_SENHA + ", " + Constant.COLUMN_TELEFONE
				+ " from " + Constant.TABLE_USUARIO + " where "
				+ Constant.COLUMN_TELEFONE + " = ? and "
				+ Constant.COLUMN_SENHA + " = ?";

		Cursor cursor = database.rawQuery(sql, args);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			result = true;
			UsuarioLogado.setId_usuario(cursor.getLong(0));
			UsuarioLogado.setSenha(cursor.getString(1));
			UsuarioLogado.setTelefone(cursor.getString(2));
			break;
		}
		// make sure to close the cursor
		cursor.close();

		return result;
	}

	public void AtualizarFlagSincronizacaoUsuario(Usuario usuario) {
		long id = usuario.getId();
		ContentValues values = new ContentValues();
		values.put(Constant.COLUMN_FL_SINCRONIZADO, 1);
		System.out.println("Usuario com o ID atualizado: " + id);
		database.update(Constant.TABLE_USUARIO, values,
				Constant.COLUMN_ID_USUARIO + " = " + id, null);
	}

	/*** SERVICE ***/
	public boolean efetuarLoginWS(Usuario usuario) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constant.SERVICE_URI
				+ Constant.SERVICE_EFETUAR_LOGIN);

		Gson gson = new Gson();
		String obj = gson.toJson(usuario);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("usuario", obj));
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		// Execute HTTP Post Request
		HttpResponse response = httpclient.execute(httppost);
		JSONObject json = new JSONObject(EntityUtils.toString(response
				.getEntity()));
		boolean retorno = json
				.getBoolean(Constant.SERVICE_RETORNO_EFETUAR_LOGIN);
		return retorno;

	}

	public boolean CadastrarAtualizarUsuarioWS(Usuario usuario)
			throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constant.SERVICE_URI
				+ Constant.SERVICE_CADASTRAR_ATUALIZAR_USUARIO);

		Gson gson = new Gson();
		String obj = gson.toJson(usuario);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("usuario", obj));
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		// Execute HTTP Post Request
		HttpResponse response = httpclient.execute(httppost);
		JSONObject json = new JSONObject(EntityUtils.toString(response
				.getEntity()));
		boolean retorno = json
				.getBoolean(Constant.SERVICE_RETORNO_CADASTRAR_ATUALIZAR_USUARIO);
		return retorno;

	}

}