package br.com.weatherproject.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;

import br.com.weatherproject.bd.Conexao;
import br.com.weatherproject.jdbc.JDBCClimaDAO;
import br.com.weatherproject.modelo.Clima;
import br.com.weatherproject.rest.UtilRest;


@Path("clima")
public class ClimaRest extends UtilRest{

	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String climaParam) {

		try {
			Clima clima = new Gson().fromJson(climaParam, Clima.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();

			JDBCClimaDAO jdbcClima = new JDBCClimaDAO(conexao);
			boolean retorno = jdbcClima.inserir(clima);
			String msg ="";

			if(retorno) {
				msg = "Dados inseridos com sucesso!";
				
			}else {
				msg = "Erro ao inserir dados!";
			}

			conec.fecharConexao();

			return this.buildResponse(msg);

		}catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	@GET
	@Path("/buscarPorNome")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorNome(@QueryParam("valorBusca") String descricao) {
		
		try {
			
			List<Clima> listaClimas = new ArrayList<Clima>();
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCClimaDAO jdbcClima = new JDBCClimaDAO(conexao);
			listaClimas = jdbcClima.buscarPorNome(descricao);
			conec.fecharConexao();
			return this.buildResponse(listaClimas);					
			
		}catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}		
	}
	@DELETE
	@Path("/excluir/{id}")
	@Consumes("application/*")
	public Response excluir(@PathParam("id") int id) {
		
		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCClimaDAO jdbcClima = new JDBCClimaDAO(conexao);
			
			boolean retorno = jdbcClima.deletar(id);
			
			
			String msg = "";
			if(retorno) {
				msg = "Info excluido com sucesso!";
			}else{
				msg = "Erro ao excluir info.";
			}
			
			conec.fecharConexao();
			
			return buildResponse(msg);
			
		}catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}		
	}
	@GET
	@Path("/buscarPorId")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorId(@QueryParam("id") int id) {
		try {
			Clima clima = new Clima();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCClimaDAO jdbcClima = new JDBCClimaDAO(conexao);
			
			clima = jdbcClima.buscarPorId(id);
			
			conec.fecharConexao();
			
			return this.buildResponse(clima);
			
		}catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}				
	}
	
	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public Response alterar(String climaParam) {
	try {
		Clima clima = new Gson().fromJson(climaParam, Clima.class);
		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		JDBCClimaDAO jdbcClima = new JDBCClimaDAO(conexao);
		boolean retorno = jdbcClima.alterar(clima);
		
		String msg = "";
		if(retorno) {
			msg = "Produto alterado com sucesso!";
		}else {
			msg = "Erro ao alterar produto!";
		}
		
		conec.fecharConexao();
		return this.buildResponse(msg);
	}catch (Exception e) {
		e.printStackTrace();
	return this.buildErrorResponse(e.getMessage());
	}
		
	}	
}
