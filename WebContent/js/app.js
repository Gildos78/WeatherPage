//Obtém a data/hora atual
var data = new Date();

//Guarda cada pedaço em uma variável
var dia     = data.getDate();           // 1-31
var dia_sem = data.getDay();            // 0-6 (zero=domingo)
var mes     = data.getMonth();          // 0-11 (zero=janeiro)
var ano2    = data.getYear();           // 2 dígitos
var ano4    = data.getFullYear();       // 4 dígitos
var hora    = data.getHours();          // 0-23
var min     = data.getMinutes();        // 0-59
var seg     = data.getSeconds();        // 0-59
var mseg    = data.getMilliseconds();   // 0-999
var tz      = data.getTimezoneOffset(); // em minutos

var c = 0;

const xhttp = new XMLHttpRequest();
xhttp.open('GET','JlleJson.json', true);
xhttp.send(null);
xhttp.onreadystatechange = function(){
	if(this.readyState === 4 && this.status == 200){


		let dados = JSON.parse(this.responseText);


	
		for(let item of dados){
		
			for (var i=0;i<5;i++){
				let grauTemp = document.querySelector(('.grauTemp'+i));
				let descTemp = document.querySelector('.descTemp'+i);
				let data = document.querySelector('.data'+i)
				let icon = document.querySelector('.icon'+i);
				var str_data = ano4 + '-0' + (mes+1) + '-' + (dia+i);
				var str_hora = hora + ':' + min + ':' + seg;

				
				if(i===c&&item.Data===str_data+" 18:00:00"){
					descTemp.textContent = item.Descrição;
					data.textContent = item.Data;
					grauTemp.textContent = item.temp;
					icon.innerHTML = `<img src="icons/${item.icon}.png"/>`;
					c++;
				}

			}
		}
	}




}

