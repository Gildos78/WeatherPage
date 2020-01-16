//Cria o objeto Coldigo, que será usado como identificador do projeto
WEATHER = new Object();

$(document).ready (function() {
	
	WEATHER.PATH = "/Weather/rest/";
	
	
	//Função para carregamento de páginas de conteúdo, que
	//recebe como parâmetro o nome da pasta com a página a ser carregada
	WEATHER.carregaPagina = function(pagename){
		//Remove o conteúdo criado na abertura de uma página modal pelo jQueryUI
		if($(".ui-dialog"))
			$(".ui-dialog").remove();		
		//Limpa a tag section, excluindo todo o conteúdo  de dentro dela
		$("section").empty();
		//carrega a página solicitada dentro da tag section
		$("section").load(pagename+"/", function(response, status, info){
			if(status == "error"){
				var msg = "Houve um erro ao encontrar a página: "+info.status + " -  " + info.statusText;
				$("section").html(msg);
			}
		});
	}
	

	//Define as configurações base de uma modal de aviso
	WEATHER.exibirAviso = function(aviso){
		var modal = {
				title: "Mensagem",
				height: 250,
				width: 400,
				modal: true,
				buttons:{
					"OK": function(){
						$(this).dialog("close");
					}
				}
		};
		$("#modalAviso").html(aviso);
		$("#modalAviso").dialog(modal);
	};
});