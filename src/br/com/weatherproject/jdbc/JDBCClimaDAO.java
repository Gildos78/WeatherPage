package br.com.weatherproject.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.weatherproject.jdbcinterface.ClimaDAO;
import br.com.weatherproject.modelo.Clima;


public class JDBCClimaDAO implements ClimaDAO {

	private Connection conexao;

	public JDBCClimaDAO(Connection conexao) {
		this.conexao=conexao;
	}
	public boolean inserir(Clima clima) {
		
		
			
			String comando = "INSERT INTO marcas "
					+"(id, descricao, temp, dataClima) "
					+ "VALUES (?,?,?,?)";
			PreparedStatement p;

			try {
				
				//Prepara o comando para execução no BD em que nos conectamos
				p = this.conexao.prepareStatement(comando);

				//Substitui no comando os "?" pelos valores do produto
				p.setInt(1, clima.getId());
				p.setString(2, clima.getDescricao());
				p.setString(3, clima.getTemp());
				p.setString(4, clima.getDataClima());
				//Executa o comando no BD
				p.execute();
				

			}catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	
	public List<Clima> buscarPorNome (String descricao){
		//Inicia a criação do comando SQL de busca 

		String comando = "SELECT * FROM marcas ";

		//Se o nome não estiver vazio...
		if (!descricao.equals("")) {
			//concatena no comando o WHERE buscando no nome do produto 
			//o texto da variável nome
			comando += "WHERE marcas.descricao LIKE '%"+ descricao + "%' "; 
		}
		comando += "ORDER BY dataClima ASC ";


		List<Clima> listaClimas = new ArrayList<Clima>();
		Clima clima= null;
		try {

			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);

			while(rs.next()) {

				int id = rs.getInt("id");
				String marcaDescricao = rs.getString("descricao");
				String temp = rs.getString("temp");
				String dataClima = rs.getString("dataClima");




				clima = new Clima();
				clima.setId(id);

				clima.setDescricao(marcaDescricao);
				clima.setTemp(temp);
				clima.setDataClima(dataClima);
				listaClimas.add(clima);
			}


		}catch (Exception e) {
			e.printStackTrace();
		}


		//Retorna para quem chamou o método a lista criada
		return listaClimas;
	}
	public boolean deletar(int id) {
		String comando = "DELETE FROM marcas WHERE id = ?";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1,id);
			p.execute();
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public Clima buscarPorId(int id) {
		String comando = "SELECT * FROM marcas WHERE marcas.id = ?";
		Clima clima = new Clima();
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {

				String descricao = rs.getString("descricao");


				clima.setId(id);
				clima.setDescricao(descricao);


			}			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return clima;
	}

	public boolean alterar(Clima clima) {
		String comando = "UPDATE marcas "
				+ "SET descricao=?"
				+ " WHERE id=?";
		PreparedStatement p;

		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, clima.getDescricao());
			p.setInt(2, clima.getId());
			p.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
