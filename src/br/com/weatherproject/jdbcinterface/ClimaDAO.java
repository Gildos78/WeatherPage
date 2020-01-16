package br.com.weatherproject.jdbcinterface;

import java.util.List;

import br.com.weatherproject.modelo.Clima;

public interface ClimaDAO {
	public boolean inserir(Clima clima);
	public List<Clima> buscarPorNome(String nome);
	public boolean deletar(int id);

}
