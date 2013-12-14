package br.com.alexisoliveira.todo.model;

public class Usuario {

	int id;
	String email;
	String telefone;
	boolean notificar;
	String senha;

	public Usuario(int id, String email, String telefone, boolean notificar,
			String senha) {
		this.id = id;
		this.email = email;
		this.telefone = telefone;
		this.notificar = notificar;
		this.senha = senha;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public boolean isNotificar() {
		return notificar;
	}

	public void setNotificar(boolean notificar) {
		this.notificar = notificar;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
