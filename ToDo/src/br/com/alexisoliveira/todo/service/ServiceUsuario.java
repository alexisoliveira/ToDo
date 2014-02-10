package br.com.alexisoliveira.todo.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.alexisoliveira.todo.datasource.DataSource;
import br.com.alexisoliveira.todo.model.Usuario;
import br.com.alexisoliveira.todo.util.Constant;
import br.com.alexisoliveira.todo.util.UsuarioLogado;

public class ServiceUsuario {

	// Database fields
	private SQLiteDatabase database;
	private DataSource dbHelper;
	private String[] allColumns = { Constant.COLUMN_ID_USUARIO,
			Constant.COLUMN_EMAIL, Constant.COLUMN_SENHA,
			Constant.COLUMN_TELEFONE };

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
		values.put(Constant.COLUMN_FL_OPERACAO, Constant.OPERACAO_INCLUIR);
		values.put(Constant.COLUMN_FL_SINCRONIZADO, false);

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

	public boolean efetuarLoginWS(String telefone, String senha) {
		String strURLGET = Constant.SERVICE_URI
				+ Constant.SERVICE_EFETUAR_LOGIN + telefone + ";" + senha;
		HttpGet request = new HttpGet(strURLGET);

		HttpResponse response = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			response = httpClient.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		HttpEntity responseEntity = response.getEntity();
		char[] buffer = new char[(int) responseEntity.getContentLength()];

		try {
			InputStream stream = responseEntity.getContent();
			InputStreamReader reader = new InputStreamReader(stream);
			reader.read(buffer);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Log.i("Recebendo:", new String(buffer));
		String teste = new String(buffer);
		JSONObject json = new JSONObject();
		boolean loginOK = false;
		try {
			json = new JSONObject(teste);
			loginOK = json.getBoolean("EfetuarLoginResult");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return loginOK;
	}

	public boolean CadastrarUsuarioWS(Usuario u) {
		try {
			
			HttpPost request = new HttpPost(Constant.SERVICE_URI+Constant.SERVICE_CADASTRAR_USUARIO);
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-type", "application/json");

			// Build JSON string
			JSONStringer userJson = new JSONStringer()
			.object().key("usuario").object()
				.key("IdUsuario").value(0)
				.key("Email").value(u.getEmail())
				.key("Telefone").value(u.getTelefone())
				.key("Senha").value(u.getSenha())
			.endObject().endObject();

			StringEntity entity = new StringEntity(userJson.toString(),"UTF-8");                                                
			entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			entity.setContentType("application/json");


			request.setEntity(entity);
			Log.i("Enviando:", userJson.toString());
			// Send request to WCF service
			DefaultHttpClient httpClient = new DefaultHttpClient();             
			HttpResponse response = httpClient.execute(request);

			

			

			// Execute HTTP Post Request

			HttpEntity responseEntity = response.getEntity();
			char[] buffer = new char[(int) responseEntity.getContentLength()];

			try {
				InputStream stream = responseEntity.getContent();
				InputStreamReader reader = new InputStreamReader(stream);

				reader.read(buffer);
				stream.close();
				reader.close();

			} catch (IOException e) {

				e.printStackTrace();
			}

			String teste = new String(buffer);
			return true;
		} catch (Exception e) {
			return false;
		}
		/*
		 * HttpClient httpclient = new DefaultHttpClient(); HttpPost httppost =
		 * new HttpPost(Constant.SERVICE_URI +
		 * Constant.SERVICE_CADASTRAR_USUARIO);
		 * 
		 * try {
		 * 
		 * List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		 * nameValuePairs.add(new BasicNameValuePair("nrTelefone",
		 * u.getTelefone())); nameValuePairs.add(new
		 * BasicNameValuePair("dsSenha",u.getSenha())); nameValuePairs.add(new
		 * BasicNameValuePair("dsEmail", u.getEmail())); httppost.setEntity(new
		 * UrlEncodedFormEntity(nameValuePairs));
		 * 
		 * Log.i("Enviando:", nameValuePairs.toString());
		 * 
		 * // Execute HTTP Post Request HttpResponse response =
		 * httpclient.execute(httppost);
		 * 
		 * HttpEntity responseEntity = response.getEntity(); char[] buffer = new
		 * char[(int) responseEntity.getContentLength()];
		 * 
		 * try { InputStream stream = responseEntity.getContent();
		 * InputStreamReader reader = new InputStreamReader(stream);
		 * 
		 * reader.read(buffer); stream.close(); reader.close();
		 * 
		 * } catch (IOException e) {
		 * 
		 * e.printStackTrace(); }
		 * 
		 * String teste = new String(buffer); return true; } catch (Exception e)
		 * { return false; }
		 */
	}
}