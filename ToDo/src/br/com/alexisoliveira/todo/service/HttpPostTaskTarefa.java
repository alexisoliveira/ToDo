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

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import br.com.alexisoliveira.todo.model.Tarefa;
import br.com.alexisoliveira.todo.model.Usuario;
import br.com.alexisoliveira.todo.util.Constant;
import br.com.alexisoliveira.todo.util.UsuarioLogado;

import com.google.gson.Gson;

public class HttpPostTaskTarefa extends AsyncTask<Void, Void, String> {

	Context contexto = null;

	public HttpPostTaskTarefa(Context contexto) {
		this.contexto = contexto;
	}

	@Override
	protected String doInBackground(Void... arg0) {
		String result = "";

		ServiceTarefa datasource = new ServiceTarefa(contexto);
		datasource.open();

		try {
			Usuario usuario = new Usuario();
			usuario.setTelefone(UsuarioLogado.getTelefone());
			usuario.setSenha(UsuarioLogado.getSenha());
			usuario.setEmail("");
			usuario.setId(0);

			List<Tarefa> listaTarefa = datasource
					.getAllTarefasNaoSincronizadas();

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Constant.SERVICE_URI
					+ Constant.SERVICE_SINCRONIZAR_TAREFA);

			Gson gson = new Gson();
			String objListaTarefa = gson.toJson(listaTarefa);
			String objUsuario = gson.toJson(usuario);

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("listaTarefa",
					objListaTarefa));
			nameValuePairs.add(new BasicNameValuePair("usuario", objUsuario));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			JSONObject json = new JSONObject(EntityUtils.toString(response
					.getEntity()));
			boolean retorno = json
					.getBoolean(Constant.SERVICE_RETORNO_SINCRONIZAR);

			if (retorno) {
				for (Tarefa t : listaTarefa) {
					try {
						datasource.AtualizarFlagSincronizacaoTarefa(t);
					} catch (Exception ex) {
						Log.i("HttpPostTask", ex.getMessage());
					}
				}
				result = "Sincronização concluída. Qtd = " + listaTarefa.size();
			} else {
				result = "Erro na sincronização";
			}
		} catch (Exception e) {
			result = "Erro na comunicação com o WebService";
			datasource.close();
		}
		datasource.close();
		return result;
	}

	protected void onPostExecute(String page) {
		// textView.setText(page);
		Toast toast = Toast.makeText(this.contexto, page, Toast.LENGTH_SHORT);
		toast.show();
	}
}