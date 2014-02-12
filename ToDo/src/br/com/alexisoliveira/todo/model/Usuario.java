package br.com.alexisoliveira.todo.model;


import java.io.Serializable;

public class Usuario implements Serializable{

	private static final long serialVersionUID = 5807287653157808570L;
	long id;
	String email;
	String telefone;
	String senha;

	public Usuario(int id, String email, String telefone, String senha) {
		this.id = id;
		this.email = email;
		this.telefone = telefone;
		this.senha = senha;
	}

	public Usuario() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
