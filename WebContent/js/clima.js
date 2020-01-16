
WEATHER.clima = new Object();
$(document).ready (function(){
	
//	Cadastra no BD o produto informado
	WEATHER.clima.cadastrar = function(val){
		console.log(val);
	
		
			for(i=0;i<5;i++){

				var clima = new Object();	
				clima.temp = document.getElementById('grauTemp'+i).innerHTML;
				clima.descricao = document.getElementById('descTemp'+i).innerHTML;
				clima.dataClima =document.getElementById('data'+i).innerHTML;





				$.ajax({
					type: "POST",
					url: WEATHER.PATH + "clima/inserir",
					data:JSON.stringify(clima),
					success:function(msg) {
						WEATHER.exibirAviso(msg);
						WEATHER.clima.buscarPorNome();
						


					},
					error:function(info){
						WEATHER.exibirAviso("Erro ao cadastrar uma nova data: "+ info.status + " - "+ info.statusText);
					}
				});

				
		};

	}
	

	//Busca no BD e exibe na página os produtos que atendam à solicitação do usuário
	WEATHER.clima.buscarPorNome = function(){

		var valorBusca = $("#campoBuscaClima").val();

		$.ajax({
			type: "GET",
			url: WEATHER.PATH + "clima/buscarPorNome",
			data: "valorBusca="+valorBusca,
			success: function(dados){


				//console.log(dados);
				$("#listaClimas").html(WEATHER.clima.exibir(dados));

			},
			error: function(info){
				WEATHER.exibirAviso("Erro ao consultar os contatos: "+info.status+" - "+info.statusText);
			}
		});
	};

	//Transforma os dados das marcas recebidas do servidor em uma tela HTML
	WEATHER.clima.exibir = function(listaDeClimas){

		var tabela = "<table>"+ 
		"<tr>"+
		"<th>Info do banco de dados</th>"+

		"</tr>";

		if(listaDeClimas != undefined && listaDeClimas.length > 0){
			for (var i=0; i<listaDeClimas.length; i++){
				tabela += "<tr>" + 
				"<td>"+listaDeClimas[i].descricao+"</td>" +
				"<td>"+listaDeClimas[i].temp+"</td>" +
				"<td>"+listaDeClimas[i].dataClima+"</td>" +
				"<td>" + 
				"<a onclick=\"WEATHER.clima.exibirEdicao('"+listaDeClimas[i].id+"')\"><img src='imgs/edit.png' alt='Editar registro'></a> " +
				"<a onclick=\"WEATHER.clima.excluir('"+listaDeClimas[i].id+"')\"><img src='imgs/delete.png' alt='Excluir registro'></a> " +
				"</td>" + 
				"</tr>"
			}

		}else if (listaDeClimas==""){
			tabela+= "<tr><td colspan='6'>Nenhum registro encontrado</td></tr>"+"<fieldset>"+
			"<a onclick=\"WEATHER.clima.cadastrar()\"><img src='imgs/refresh.png' alt='Refrescar registro'> Reinserir dados</a> "+
			"</fieldset>";			
		}
		tabela+= "</table>";
		
		return tabela;
	};
	WEATHER.clima.buscarPorNome();

	WEATHER.clima.excluir = function(id){
		$.ajax({
			type:"DELETE",
			url: WEATHER.PATH + "clima/excluir/"+id,
			success: function(msg){
				console.log(WEATHER.exibirAviso(msg));
				WEATHER.clima.buscarPorNome();
			},
			error: function(info){
				WEATHER.exibirAviso("Erro ao excluir produto: "+ info.status + " - " + info.statusText);
			}

		});
	};
	WEATHER.clima.exibirEdicao = function(id){
		$.ajax({
			type:"GET",
			url: WEATHER.PATH + "clima/buscarPorId",
			data: "id="+id,
			success: function(clima){

				document.frmEditaClima.idClima.value = clima.id;
				document.frmEditaClima.nome.value = clima.descricao;


				var modalEditaClima = {
						title: "Editar Clima",
						height: 200,
						width: 550,
						modal: true,
						buttons:{
							"Salvar": function(){
								WEATHER.clima.editar();
							},
							"Cancelar": function(){
								$(this).dialog("close");
							}
						},
						close: function(){
							//caso o usuário simplesmente feche a caixa de edição
							//não deve acontecer nada
						}
				};

				$("#modalEditaClima").dialog(modalEditaClima);				
			},
			error: function(info){
				WEATHER.exibirAviso("Erro ao buscar marca para edição: "+info.status+" - "+info.statusText);
			}

		});
	};

	WEATHER.clima.editar = function(){
		var clima = new Object();
		clima.id = document.frmEditaClima.idClima.value;
		clima.descricao = document.frmEditaClima.nome.value;

		$.ajax({
			type:"PUT",
			url: WEATHER.PATH + "clima/alterar",
			data: JSON.stringify (clima),
			success: function(msg){
				console.log(WEATHER.exibirAviso(msg));
				WEATHER.clima.buscarPorNome();
				$("#modalEditaClima").dialog("close");
				console.log(msg);
			},
			error: function(info){
				WEATHER.exibirAviso("Erro ao editar marca" + info.status + " - " + info.statusText);	
			}			
		});
	}
	
});