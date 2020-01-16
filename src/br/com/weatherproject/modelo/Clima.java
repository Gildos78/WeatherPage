package br.com.weatherproject.modelo;

import java.io.Serializable;

public class Clima implements Serializable {

	private static final  long serialVersionUID = 1L;
	
	private int id;
	private String descricao;
	private String temp;
	private String icon;
	private String dataClima;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getIcon() {
		return icon;
	}

	public String getDataClima() {
		return dataClima;
	}
	public void setDataClima(String dataClima) {
		this.dataClima = dataClima;
	}
	}
