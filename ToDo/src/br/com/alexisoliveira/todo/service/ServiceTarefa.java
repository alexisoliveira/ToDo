package br.com.alexisoliveira.todo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.alexisoliveira.todo.model.Tarefa;

public class ServiceTarefa {

	
	private ServiceTarefa() {
		
	}
	
	public static ServiceTarefa getInstance() {
		return new ServiceTarefa();
	}
	
	public List<Tarefa> findAll() {
		List<Tarefa> result = new ArrayList<Tarefa>();
		
		result.add(new Tarefa(1, "Tarefa 01", "", new Date(), true));
		result.add(new Tarefa(1, "Tarefa 02", "", new Date(), true));
		result.add(new Tarefa(1, "Tarefa 03", "", new Date(), true));
		result.add(new Tarefa(1, "Tarefa 04", "", new Date(), true));
		result.add(new Tarefa(1, "Tarefa 05", "", new Date(), true));
				
		
		return result;
	}
}
