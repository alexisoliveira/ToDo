package br.com.alexisoliveira.todo.util;

public class UsuarioLogado {
	private static long id_usuario;

	public static long getId_usuario() {
		return id_usuario;
	}

	public static void setId_usuario(long id_usuario) {
		UsuarioLogado.id_usuario = id_usuario;
	}
}
