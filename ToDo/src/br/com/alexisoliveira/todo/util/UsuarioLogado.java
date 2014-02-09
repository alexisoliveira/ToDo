package br.com.alexisoliveira.todo.util;

public class UsuarioLogado {
	private static long id_usuario;
	private static String senha;
	private static String telefone;

	public static long getId_usuario() {
		return id_usuario;
	}

	public static void setId_usuario(long id_usuario) {
		UsuarioLogado.id_usuario = id_usuario;
	}

	public static String getSenha() {
		return senha;
	}

	public static void setSenha(String senha) {
		UsuarioLogado.senha = senha;
	}

	public static String getTelefone() {
		return telefone;
	}

	public static void setTelefone(String telefone) {
		UsuarioLogado.telefone = telefone;
	}
	
	
}
