package br.com.alexisoliveira.todo.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataSource extends SQLiteOpenHelper {

	/* ************************** DATABASE ************************** */
	private static final String DATABASE_NAME = "todo_alexis.db";
	private static final int DATABASE_VERSION = 2;
		
	/* ************************** TABELAS ************************** */
	public static final String TABLE_USUARIO = "usuario";
	public static final String TABLE_TAREFA = "tarefa";
	
	/* ************************** COLUNAS ************************** */
	public static final String COLUMN_ID_USUARIO = "id_usuario";
	public static final String COLUMN_TELEFONE = "telefone";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_SENHA = "senha";
	
	public static final String COLUMN_ID_TAREFA = "id_tarefa";
	public static final String COLUMN_DATA_FINALIZACAO = "data_finalizacao";
	public static final String COLUMN_NOME = "nome";
	public static final String COLUMN_NOTIFICAR = "notificar";
	public static final String COLUMN_OBSERVACAO = "observacao";
	public static final String COLUMN_STATUS = "status";
	
	// Database creation sql statement
	private static final String DATABASE_CREATE_USUARIO = 
			"create table " + TABLE_USUARIO + 
			"(" +
					COLUMN_ID_USUARIO	+ " integer primary key autoincrement, " +
					COLUMN_TELEFONE + " text not null, " +
					COLUMN_EMAIL + " text not null, " +
					COLUMN_SENHA + " text not null" +
			");";

	private static final String DATABASE_CREATE_TAREFA = 
			"create table " + TABLE_TAREFA + 
			"(" +
					COLUMN_ID_TAREFA	+ " integer primary key autoincrement, " +
					COLUMN_DATA_FINALIZACAO + " text not null, " +
					COLUMN_NOME + " text not null, " +
					COLUMN_NOTIFICAR + " integer not null, " +
					COLUMN_OBSERVACAO + " text not null, " +
					COLUMN_STATUS + " integer not null, " +
					COLUMN_ID_USUARIO + " integer not null, " +
					"FOREIGN KEY (" + DataSource.COLUMN_ID_USUARIO + ")" +
							" REFERENCES " + DataSource.TABLE_USUARIO+ " (" + DataSource.COLUMN_ID_USUARIO + ")" +  
					
			");";

	public DataSource(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAREFA);
		onCreate(db);
	}

}