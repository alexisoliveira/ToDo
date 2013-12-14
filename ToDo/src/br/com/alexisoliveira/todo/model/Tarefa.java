package br.com.alexisoliveira.todo.model;

import java.util.Date;


public class Tarefa {

	private int id;
	private String nome;
	private String observacao;
	private Date dataFinalizacao;
	private boolean notificar;

	public Tarefa(int id, String nome, String observacao, Date dataFinalizacao, boolean notificar) {
		this.id = id;
		this.nome = nome;
		this.observacao = observacao;
		this.dataFinalizacao = dataFinalizacao;
		this.notificar = notificar;
	}

	public int getId() {
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

	public Date getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(Date dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	public boolean isNotificar() {
		return notificar;
	}

	public void setNotificar(boolean notificar) {
		this.notificar = notificar;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
