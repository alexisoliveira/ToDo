package br.com.alexisoliveira.todo.util;

public class Constant {
	public static final String PREF_FILE = "TodoByAlexis";
	
	/* ************************** DATABASE ************************** */
	public static final String DATABASE_NAME = "todo_alexis.db";
	public static final int DATABASE_VERSION = 5;
		
	/* ************************** TABELAS ************************** */
	public static final String TABLE_USUARIO = "usuario";
	public static final String TABLE_TAREFA = "tarefa";
	
	/* ************************** COLUNAS ************************** */
	public static final String COLUMN_ID_USUARIO = "id_usuario";
	public static final String COLUMN_TELEFONE = "telefone";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_SENHA = "senha";
	public static final String COLUMN_FL_SINCRONIZADO = "fl_sincronizado";
	public static final String COLUMN_ID_TAREFA = "id_tarefa";
	public static final String COLUMN_DATA_FINALIZACAO = "data_finalizacao";
	public static final String COLUMN_NOME = "nome";
	public static final String COLUMN_NOTIFICAR = "notificar";
	public static final String COLUMN_OBSERVACAO = "observacao";
	public static final String COLUMN_STATUS = "status";
	
	/* ************************** SERVICE ************************** */
	public final static String SERVICE_URI = "http://192.168.10.251/todo/ToDo.svc/";
	public final static String SERVICE_EFETUAR_LOGIN = "efetuarlogin";
	public final static String SERVICE_GET_TAREFA = "gettarefa";
	public final static String SERVICE_VERIFICAR_SESSAO = "verificarsessao";
	public final static String SERVICE_SINCRONIZAR_TAREFA = "sincronizar";
	public final static String SERVICE_CADASTRAR_ATUALIZAR_USUARIO = "cadastraratualizarusuario";
	
	public final static String SERVICE_RETORNO_CADASTRAR_ATUALIZAR_USUARIO = "CadastrarAtualizarUsuarioResult";
	public final static String SERVICE_RETORNO_GET_TAREFA = "GetTarefasResult";
	public final static String SERVICE_RETORNO_EFETUAR_LOGIN = "EfetuarLoginResult";
	public final static String SERVICE_RETORNO_SINCRONIZAR = "SincronizarResult";
}