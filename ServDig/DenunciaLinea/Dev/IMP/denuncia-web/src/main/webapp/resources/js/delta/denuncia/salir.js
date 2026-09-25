var idDgSalir = "#dgSalirSinGuardar";
var oDgDalir;
var idDgSalirSistema = "#dgSalirSistema";
var oDgDalirSistema;
var idDenunciaPaso = "#hdIdPasoDenuncia";

$(document).ready(function() {
	
	oDgDalir = $(idDgSalir).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		beforeClose :function(event,ui){
		},
		buttons: {
			"Salir": function() {   //si está en el paso uno , tiene q llamar a salirAplicacion()
				if($(idDenunciaPaso).val() == "1"){
					alert("paso1");
					salirAplicacion();
				}else{
					abrirDialogoSalirSistema();
				}					
			}, 
			"Cancelar": function() { 
				$(this).dialog("close"); 
			} 
		}
	}).error(function(data){
		alert("error" + data);
	});
	
	oDgDalirSistema = $(idDgSalirSistema).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		beforeClose :function(event,ui){
		},
		buttons: [{
			text:  "Aceptar",
			click: function () {
                 salirAplicacion();					
			}
		  }]
	}).error(function(data){
		alert("error" + data);
	});
	
});


function abrirDialogoSalirSinGuardar(){
	oDgDalir.dialog('open');
}

function abrirDialogoSalirSistema(){
	alert("abre dialogo salir sistema");
	oDgDalirSistema.dialog('open');
}

function salirAplicacion(){
	alert("entraSalirAplicacion");
	var formulario =null;
	formulario = document.createElement("form");
	formulario.action =  getAppContextParaJS() + "/logoutDenuncia.do?tgt=salirAplicacion";
	formulario.method = "post";
	this.document.body.appendChild(formulario);
	formulario.submit();
}
