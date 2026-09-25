/**
* Java Script de los Procesos
**/

//Objeto de datable del Proceso
var dtProceso;
var sIdFormModificar = '#procesoFormModificar';

var sIdDialogModificar = "#dgModificar";
var oDialogModificar;

var sIdDialogModificarProceso = "#dgModificarProceso";
var oDialogModificarProceso;

var pDialogHeigthProcesos = 400;
var pDialogWidthProcesos = 400;

var fnLoadInitProceso = function(){
	
	
	/*Configuracion del dialogo de modificar  elemento*/
	oDialogModificar = 	$( sIdDialogModificar).dialog({
		autoOpen:false,
		resizable: false,
		modal: true,
		height:pDialogHeigthProcesos,
		width:pDialogWidthProcesos,
		buttons: {
			"Aceptar": function() {
				
				/*la invocacion a modificar el elemento*/
			
				var oForm = $(sIdFormModificar).serializeObject(true);
				var sSource = 'proceso/modificar';
				// MODIFICAR
				$.postJSON(sSource, oForm, function(data) {
					$('#tbProceso #desInicialConsulta').text(data.desInicial);
					$('#tbProceso #desIntermedioConsulta').text(data.desIntermedio);
					$('#tbProceso #desFinalConsulta').text(data.desFinal);
				});
				$( this ).dialog( "close" );
			},
			'Cancelar': function() {
				$( this ).dialog( "close" );
			}
		}
	});
	/* CARGA LOS DATOS DEL PROCESO AL CARGAR LA PAGINA */
	fnCargaDatosProceso();
	
}


/** Seccion de codigo a ejectuar cuando el DOM este listo **/
//$(function() {
//	fnLoadInitProceso();
//});
	
var fnCargaDatosProceso = function () {
	/*Funcion para obtener el proceso de la solicitud*/
	var urlPagina = 'proceso/cargarDatos';
	$.getJSON(urlPagina,{ idSolicitud: $('#procesoFormPaginar:hidden  #cveIdSolicitud').val() } , function(data) {

		if (data != null)
		{
//			$('#tabladatos input:hidden#cveIdSolicitud2').val(data.cveIdSolicitud);
//			$('#tabladatos input:hidden#cveIdProceso2').val(data.cveIdProceso);
			
			$('#tbProceso #desInicialConsulta').text(data.desInicial);
			$('#tbProceso #desIntermedioConsulta').text(data.desIntermedio);
			$('#tbProceso #desFinalConsulta').text(data.desFinal);
		}
	});
}

/*Funcion para obtener el elemento seleccionado*/
var fnGetElemento = function(idProcesoForm, tipoProceso){

	var sSource = 'proceso/get';
	
	$.getJSON(sSource,{ idProceso: idProcesoForm } , function(data) {
		
		$('#procesoFormModificar:hidden  #cveIdSolicitud').val( data.cveIdSolicitud);
		$('#procesoFormModificar:hidden  #cveIdProceso').val( data.cveIdProceso);
		$('#procesoFormModificar #desInicial').val( data.desInicial);
		$('#procesoFormModificar #desIntermedio').val( data.desIntermedio);
		$('#procesoFormModificar #desFinal').val( data.desFinal);
		if (tipoProceso == 'Inicial')
		{
			$('#procesoFormModificar table tr:#trInicial').show();
			$('#procesoFormModificar table tr:#trIntermedio').hide();
			$('#procesoFormModificar table tr:#trFinal').hide();
		
		} else if (tipoProceso == 'Intermedio')
		{
			$('#procesoFormModificar table tr:#trInicial').hide();
			$('#procesoFormModificar table tr:#trIntermedio').show();
			$('#procesoFormModificar table tr:#trFinal').hide();
		} else if (tipoProceso == 'Final')
		{
			$('#procesoFormModificar table tr:#trInicial').hide();
			$('#procesoFormModificar table tr:#trIntermedio').hide();
			$('#procesoFormModificar table tr:#trFinal').show();
		}

		/*Abrimos el dialogo*/
		oDialogModificar.dialog('open');
	});
}
	/*Funcion para abrir el dialogo de modificar*/
	var fnOpenDialogModificarProcesos = function(tipoProceso){
			fnGetElemento($('#procesoFormPaginar:hidden  #cveIdProceso').val(), tipoProceso);
	}
	
	
	
	
	
	/*
	 * funciones para navegacion
	 */
		
		
		
		function fnGoBack(){
			$("#form-back").submit();
		}
		
		function fnGoAhead(){
			$("#form-forward").submit();
		}	