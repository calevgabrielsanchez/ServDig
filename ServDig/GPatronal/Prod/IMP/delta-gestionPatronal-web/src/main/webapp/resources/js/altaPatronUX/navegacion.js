/**
 * 
 */

var paginaActual;
var totalPaginas=13;

function paginaSiguiente(){ 
	if(paginaActual>=totalPaginas){
		paginaActual=totalPaginas;
		return;
	}
	paginaActual=paginaActual+1;

	if(paginaActual == 9 && !requiereActaSindicato && !requiereSocios) {
		visualizaPrevio();
		paginaSiguiente();
	}

    if(paginaActual == 9 && !requiereActaSindicato && requiereSocios) {
        pedirSoloSocios();
    }
	
	$(".seccionTramite").hide();
	$("#"+$(".seccionTramite")[paginaActual].id).show();
	habilitaPaso(paginaActual);
    $(window).scrollTop(0);
}

function paginaPrevia(){
	if(paginaActual<=0){
		paginaActual=0;
		return;
	}

	paginaActual=paginaActual-1;
	
	if(paginaActual == 9 && !requiereActaSindicato && !requiereSocios) {
		paginaPrevia()
	}
	
	$(".seccionTramite").hide();
	$("#"+$(".seccionTramite")[paginaActual].id).show();
	habilitaPaso(paginaActual);
    $(window).scrollTop(0);
}


function habilitaPaso(pagina){    
	$("#pasosTramite").find('li').each(function(){ 
		$(this).removeClass("completed");
	});  

	var paso;  
	switch(pagina){
	case 0:
	case 1:
		paso=0;
		break;
	case 2:
		paso=1;
		break;
	case 3:
		paso=2;
		break;
	case 4:
	case 5:
		paso=3;
		break;
	case 6:
	case 7:
		paso=4;
		break;
	case 8:    
		paso=5;
		break;
	case 9:
		paso=6;
		break;
	case 10:
	case 11:
	case 12:
		paso=7;
		break;
	}  
	seleccionaPaso(paso)
}



function seleccionaPaso(pagina){
	var contador=0;  
	$("#pasosTramite").find('li').each(function(){       
		if(contador<=pagina){
			$(this).addClass("completed");
		}      
		contador++;
	});  
}

$(document).ready(function(){

	//Inicializacion parametros


	$(".seccionTramite").hide();//Se ocultan todos los divs
	paginaActual=0;//Inicializamos a primera pagina

	//Limpia pasos
	$("#pasosTramite").find('li').each(function(){ 
		$(this).removeClass("completed");
	}); 

	//Mostramos el primer div
	$("#"+$(".seccionTramite")[0].id).show();
	//inicializamos pagina y paso
	paginaActual=0;
	habilitaPaso(0);

});