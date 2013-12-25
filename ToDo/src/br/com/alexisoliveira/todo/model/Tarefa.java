package br.com.alexisoliveira.todo.model;

import java.io.Serializable;



public class Tarefa implements Serializable {

	private static final long serialVersionUID = -5233171294883715611L;
	private long id;
	private String nome;
	private String observacao;
	private String dataFinalizacao;
	private boolean notificar;
	private boolean status;
	private long id_usuario;

	public Tarefa(){
	}
	
	public Tarefa(int id, String nome, String observacao, String dataFinalizacao, boolean notificar, boolean status) {
		this.id = id;
		this.nome = nome;
		this.observacao = observacao;
		this.dataFinalizacao = dataFinalizacao;
		this.notificar = notificar;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(String dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	public boolean isNotificar() {
		return notificar;
	}

	public void setNotificar(boolean notificar) {
		this.notificar = notificar;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}
	
	
}
