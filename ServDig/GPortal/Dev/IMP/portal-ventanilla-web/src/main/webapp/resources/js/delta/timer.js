horas=0; 
minutos=0; 
segundos=0;
puntos=true;
tituloDialogo = null;
function titulo(titulo_dialogo){
	tituloDialogo = titulo_dialogo;
}
function Tiempo() {
	if(puntos) ++segundos;
	if(segundos==60) 
		{ 
		segundos=0; 
		++minutos 
		}
	if(minutos==60) 
		{ 
		minutos=0; ++horas 
		}
	if(horas==24) 
		horas=0;
		cad="";
	if(horas<10) 
		cad+="0";
		cad+=horas;
	if(puntos) cad+=":"; 
		else cad+=":";
		if(minutos<10) cad+="0";cad=cad+minutos;
		if(puntos) cad+=":"; 
			else cad+=":";
			if(segundos<10) cad+="0";
				cad=cad+segundos;puntos=!puntos;
				
				parent.document.getElementById('tiempoServicio').value = cad;
				$('span#timerDialog').text(tituloDialogo + cad);
				
				
}
var id;
function iniciar() 
	{ 
	$('span.ui-dialog-title').append('<span id="timerDialog"></span>');
	id=setInterval("Tiempo()",500) 
	}
function parar() 
	{ 
	clearInterval(id) 
	horas=0; 
	minutos=0; 
	segundos=0;
	$('span#timerDialog').remove();
	}