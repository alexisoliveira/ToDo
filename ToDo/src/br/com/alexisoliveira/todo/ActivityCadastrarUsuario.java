package br.com.alexisoliveira.todo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ActivityCadastrarUsuario extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_usuario);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cadastrar_usuario, menu);
		return true;
	}

}
