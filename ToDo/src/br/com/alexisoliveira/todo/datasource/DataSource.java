package br.com.alexisoliveira.todo.datasource;

import br.com.alexisoliveira.todo.util.Constant;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataSource extends SQLiteOpenHelper {
	
	// Database creation sql statement
	private static final String DATABASE_CREATE_USUARIO = 
			"create table " + Constant.TABLE_USUARIO + 
			"(" +
					Constant.COLUMN_ID_USUARIO	+ " integer primary key autoincrement, " +
					Constant.COLUMN_TELEFONE + " text not null, " +
					Constant.COLUMN_EMAIL + " text not null, " +
					Constant.COLUMN_SENHA + " text not null," +
					Constant.COLUMN_FL_SINCRONIZADO + " integer not null" +
			");";

	private static final String DATABASE_CREATE_TAREFA = 
			"create table " + Constant.TABLE_TAREFA + 
			"(" +
					Constant.COLUMN_ID_TAREFA	+ " integer primary key autoincrement, " +
					Constant.COLUMN_DATA_FINALIZACAO + " text not null, " +
					Constant.COLUMN_NOME + " text not null, " +
					Constant.COLUMN_NOTIFICAR + " integer not null, " +
					Constant.COLUMN_OBSERVACAO + " text not null, " +
					Constant.COLUMN_STATUS + " integer not null, " +
					Constant.COLUMN_ID_USUARIO + " integer not null, " +
					Constant.COLUMN_FL_SINCRONIZADO + " integer not null," +
							"FOREIGN KEY (" + Constant.COLUMN_ID_USUARIO + ")" +
									" REFERENCES " + Constant.TABLE_USUARIO+ " (" + Constant.COLUMN_ID_USUARIO + ")" +  
							
			");";

	public DataSource(Context context) {
		super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_USUARIO);
		database.execSQL(DATABASE_CREATE_TAREFA);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DataSource.class.getName(), "Atualizando base da versão "
				+ oldVersion + " to " + newVersion
				+ ", todos os dados serão perdidos.");
		db.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_TAREFA);
		db.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_USUARIO);
		
		onCreate(db);
	}

}