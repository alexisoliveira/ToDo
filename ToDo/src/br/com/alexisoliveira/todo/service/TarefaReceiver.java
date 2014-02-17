package br.com.alexisoliveira.todo.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TarefaReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			HttpPostTaskTarefa httpTask = new HttpPostTaskTarefa(context);
			httpTask.execute();
		} catch (Exception ex) {
			Toast toast = Toast.makeText(context, ex.getMessage(),
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}
}
