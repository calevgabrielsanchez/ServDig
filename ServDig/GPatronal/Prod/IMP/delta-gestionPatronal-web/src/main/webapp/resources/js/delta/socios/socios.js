/**
* Java Script de los socios
*
**/
//Objeto de datable de los Socios
var dtSocios;
var dtSociosForSession;

var sIdNameFormPaginarSocios="#socioFormPaginar";
var sIdNameFormNuevoSocio="#socioFormNuevo";
var dgNuevoSocio = "#dgNuevoSocio";
var oDialogAgregarSocio;
var sIdDialogEliminarSocio = "#dgEliminarSocio";
var oDialogEliminarSocio;
var sIdDialogErrorSinSeleccionSocio = "#dgErrorSinSeleccionSocio";
var oDialogErrorSinSeleccionSocio;
var oDialogModificarSocio;
var dialogSociosAlto = 800;
var dialogSociosAncho = 850;

var sIdDialogActualizarDatosSocio = "#dgActualizaDatosSocio"
var oDialogActualizarDatosSocio;

/** Seccion de codigo a ejectuar cuando el DOM este listo **/
$(function() {
	
	/* Configuracion del dialogo de actualizacion exitosa */
	oDialogActualizarDatosSocio = $(
			sIdDialogActualizarDatosSocio).dialog( {
		autoOpen : false,
		resizable : true,
		modal : true,
		height : 350,
		width : 350,
		buttons : {
			"Aceptar" : function() {
				$(this).dialog('close');
				history.back();
			}
		}
	});


	$(".slidingDivFisica").show();
	$(".slidingDivMoral").hide();
	//$(".slidingDivDomicilioNacional").show();
	//$(".slidingDivDomicilioExtranjero").hide();
	
    $(".show_hide_fisica").show();
	$(".show_hide_moral").show();
	$(".show_hide_domicilio_nacional").show();
	$(".show_hide_domicilio_extranjero").show();
	
	$('#divRFC').show();
	$('#divTipoSociedad').show();
	$('#divCurp').show('slow');
 
    $('.show_hide_fisica').click(function(){
	
	$(".slidingDivMoral").hide('fast');
			$(".slidingDivFisica").show('fast');
	
		if ($('#esDomicilioNacional2').is(':checked') && $('#esNacional2').is(':checked')){
			
			fnOcultarDatos1();
		} else {
			
			//$(".slidingDivFisica").show('fast');
			fnMostrarDatos1();
		}
		
		
		
		
		
    });
	
	$('.show_hide_moral').click(function(){
	
	$(".slidingDivMoral").show('fast');
			$(".slidingDivFisica").hide('fast');
		
		if ($('#esDomicilioNacional2').is(':checked') && $('#esNacional2').is(':checked')){
			
			fnOcultarDatos1();
		} else {
			//$(".slidingDivMoral").show('fast');
			fnMostrarDatos1();
		}
		
		
		
    });
	
	$('.show_hide_nacional').click(function(){
		fnMostrarDatos1();
    });
	
	$('.show_hide_extranjero').click(function(){
		if ($('#esDomicilioNacional2').is(':checked')){
			fnOcultarDatos1();
		}
    });
	
	$('.show_hide_domicilio_nacional').click(function(){
		//$(".slidingDivDomicilioExtranjero").hide('fast');
		//$(".slidingDivDomicilioNacional").show('fast');
		
		fnMostrarDatos1();
    });
	
	$('.show_hide_domicilio_extranjero').click(function(){
		if ($('#esNacional2').is(':checked') && $('#esPersonaFisica2').is(':checked')){
			$(".slidingDivMoral").show('fast');
			fnOcultarDatos1();
		}
		else if($('#esNacional2').is(':checked')){  // en runtime se resuelve a  esNacional1 (nacional) y esNacional2 (extranjero), el path el esNacional en el form
			fnOcultarDatos1();
		}
    });

	
	/*
	 * Configuracion de los submits de las formas
	 */
	$(sIdNameFormNuevoSocio).submit(function(){
		agregarSocio();
		return false;
	});
	
	
	
	
	/*Configuracion del dialogo de agregar nuevo elemento*/
	oDialogAgregarSocio = 	$( dgNuevoSocio).dialog({
		autoOpen:false,
		resizable: false,
		modal: true,
		height:dialogSociosAlto,
		width:dialogSociosAncho,
		buttons: {
			"Aceptar": function() {
				var sSource = 'socios/agregar';
				enviarSolicitud(sSource);
				//$( this ).dialog( "close" );
			},
			"Cancelar":function(){
			
				$( this ).dialog( "close" );
			}
		},
		open: function(event, ui) { 
			fnHideErrores('#socioFormNuevo');
			limpiarFormulario('#socioFormNuevo');
			resetRadioButtons();
		},
		close: function(event, ui) {
		//alert("se cerro la ventana modal");
		}
	});
	
	
	/*Configuracion del dialogo de confirmar*/
	oDialogEliminarSocio = 	$( sIdDialogEliminarSocio ).dialog({
		autoOpen:false,
		resizable: false,
		height:200,
		modal: true,
		buttons: {
			"Eliminar": function(data) {
                fnHideErrores(sIdDialogEliminarSocio);
				var obRowSelected = fnGetRowSelected(dtSociosForSession);
				//var idSocio = obRowSelected.cveIdSocio;
				var cveIdPersona = obRowSelected.idPersona;
				var cveIdPatronSujetoObligado = $('#socioFormPaginar:hidden #cveIdPatronSujetoObligado').val();
                var sSource = 'socios/eliminar';
				$.getJSON(sSource,{ cveIdPersona: cveIdPersona,  cveIdPatronSujetoObligado: cveIdPatronSujetoObligado} , function(data) {
					//refrescar el data table
					//dtSocios.fnDraw();
					dtSociosForSession.fnDraw();
					
                    oDialogEliminarSocio.dialog("close");
				}).error(function(data) {
					fnProcesarErrores(data, sIdDialogEliminarSocio);
				});
			},
			'Cancelar': function() {
				fnHideErrores(sIdDialogEliminarSocio);
				$( this ).dialog( "close" );
			}
		}
	});

	/**
	 * Incializción del diálogo con el mensaje "No se ha seleccionado ningún registro para la acción."
	 */
	oDialogErrorSinSeleccionSocio =  $( sIdDialogErrorSinSeleccionSocio).dialog({
		autoOpen:false,
		resizable: false,
		height:140,
		modal: true,
		buttons: {'Aceptar': function(){ $( this ).dialog( "close" ); }}
	});

	/* Configuracion del data table de socios*/
	dtSocios = $('#tbSocios').dataTable({
			bJQueryUI : false,
			bFilter : false,
			bInfo:false,
			bSort: false,
			bPaginate: true,
			bAutoWidth : false,
			bServerSide : true,
			aoColumns : [ 
					     {sTitle : "Identificador", 			mDataProp : "idSocio", 					bVisible: false },
					     {sTitle : "Registro Patronal", 		mDataProp : "registroPatronal", 		sWidth : "150px"},
						 {sTitle : "Nombre o Razon Social", 	mDataProp : "denominacionRazonSocial", 	sWidth : "350px"},
						 {sTitle : "RFC", 						mDataProp : "rfc",						sWidth : "150px"},
						 {sTitle : "CURP", 						mDataProp : "curp", 					sWidth : "175px"},
						 {sTitle : "Nacionalidad", 				mDataProp : "esNacional", 				
						 "fnRender": function ( oObj ) {	
								return parseIndicadorNacionalidad(	oObj.aData.esNacional);
							},
						 sWidth : "150px"},
						 {sTitle : "Tipo Persona",				mDataProp : "esPersonaFisica",			
						 "fnRender": function ( oObj ) {	
								return parseIndicadorTipoPersona(	oObj.aData.esPersonaFisica);
							},
						 sWidth :"125px"}
				 		 ],
			"bProcessing" : true,
			"sAjaxSource" : 'socios/paginar',
			"fnServerData" : function(sSource, aoData, fnCallback) {
				aoData.push({
					"name" : "sSearch",
					"value" : ''
				});
				var wrapper = new Object();
				wrapper.aoData = aoData;
				var oForm = $(sIdNameFormPaginarSocios).serializeObject(true);
				oForm.idSocio=$('#idSolicitud').val();
				
//				alert(oForm.idSocio);
				wrapper.oForm = oForm;
				$.postJSON(sSource, wrapper, function(data) {
					fnCallback(data);
				});			
			}
	});
	
	/* Configuracion del data table de socios para el objeto de sesion*/
	dtSociosForSession = $('#tbSociosForSession').dataTable({
			bJQueryUI : false,
			bFilter : false,
			bInfo:false,
			bSort: false,
			bPaginate: true,
			bAutoWidth : false,
			bServerSide : true,
			aoColumns : [ 
					     {sTitle : "Identificador", 			mDataProp : "idSocio", 					bVisible: false },
					     {sTitle : "Registro Patronal", 		mDataProp : "registroPatronal", 		sWidth : "150px"},
						 {sTitle : "Nombre o Razon Social", 	mDataProp : "denominacionRazonSocial", 	sWidth : "350px"},
						 {sTitle : "RFC", 						mDataProp : "rfc",						sWidth : "150px"},
						 {sTitle : "CURP", 						mDataProp : "curp", 					sWidth : "175px"},
						 {sTitle : "Nacionalidad", 				mDataProp : "esNacional", 				
						 "fnRender": function ( oObj ) {	
								return parseIndicadorNacionalidad(	oObj.aData.esNacional);
							},
						 sWidth : "150px"},
						 {sTitle : "Tipo Persona",				mDataProp : "esPersonaFisica",			
						 "fnRender": function ( oObj ) {	
								return parseIndicadorTipoPersona(	oObj.aData.esPersonaFisica);
							},
						 sWidth :"125px"}
				 		 ],
			"bProcessing" : true,
			"sAjaxSource" : 'socios/paginarForSession',
			"fnServerData" : function(sSource, aoData, fnCallback) {
				aoData.push({
					"name" : "sSearch",
					"value" : ''
				});
				var wrapper = new Object();
				wrapper.aoData = aoData;
				var oForm = $(sIdNameFormPaginarSocios).serializeObject(true);
				wrapper.oForm = oForm;
				$.postJSON(sSource, wrapper, function(data) {
					fnCallback(data);
				});			
			}
	});

	/* Add a click handler to the rows - this could be used as a callback */
	/*$("#tbSocios tbody").click(function(event) {
		$(dtSocios.fnSettings().aoData).each(function (){ 
			$(this.nTr).removeClass('row_selected'); 
		});
		if($(event.target.parentNode).hasClass('row_selected')){
			$(event.target.parentNode).removeClass('row_selected');
		}
		else{
			$(event.target.parentNode).addClass('row_selected');
		}
	});*/
	
	/* Add a click handler to the rows - this could be used as a callback */
	$("#tbSociosForSession tbody").click(function(event) {
		$(dtSociosForSession.fnSettings().aoData).each(function (){ 
			$(this.nTr).removeClass('row_selected'); 
		});
		if($(event.target.parentNode).hasClass('row_selected')){
			$(event.target.parentNode).removeClass('row_selected');
		}
		else{
			$(event.target.parentNode).addClass('row_selected');
		}
	});
	
	
	/*
	 * configuracion para buscar a la persona moral
	 */
	$.getScript("http://localhost:7001/gestionIndividuo-web/static/resources/js/delta/personas/moral/PersonaMoral.js", function(){

        PersonaMoralCtrl.init('personaMoral'); 
        PersonaMoralCtrl.setOnCloseCallback(fnOnPersonaMoralReturn); 
        
	});
	
	/*
	 * configuracion para buscar a la persona fisica
	 */
	$.getScript("http://localhost:7001/gestionIndividuo-web/static/resources/js/delta/personas/fisica/PersonaFisica.js", function(){

        PersonaFisicaCtrl.init('personaFisica'); 
        PersonaFisicaCtrl.setOnCloseCallback(fnOnPersonaFisicaReturn); 
        
	});
});


	var fnOcultarDatos1 = function(){
		//if($('#esNacional2').is(':checked')){  // en runtime se resuelve a  esNacional1 (nacional) y esNacional2 (extranjero), el path el esNacional en el form
			$('#divRFC').hide('fast');
			$('#divCurp').hide('fast');
		
			$('#divTipoSociedad').hide('fast');
			
		
		//} 
	}
	
	var fnMostrarDatos1 = function(){
		$('#divRFC').show('fast');
		$('#divCurp').show('fast');
		
		$('#divTipoSociedad').show('fast');
	}

	/*Funcion para agregar el elemento Socio*/
	var fnOpenDialogNuevoSocio = function(){
		oDialogAgregarSocio.dialog('open');
	}
	
	function agregarSocio(){
		oDialogAgregarSocio.dialog('open');
	}

	function enviarSolicitud(sSource){
				
		fnHideErrores(sIdNameFormNuevoSocio);
		
		$(".slidingDivFisica").show();
		
		/*la invocacion a guardar un nuevo elemento*/
		var cveIdPatronSujetoObligado = $('#socioFormPaginar:hidden #cveIdPatronSujetoObligado').val();
		$('#socioFormNuevo:hidden #cveIdPatronSujetoObligado').val(cveIdPatronSujetoObligado);
		
		var socio = $(sIdNameFormNuevoSocio).toObject(true);
		
		$.postJSON(sSource, socio, function(data) {	
			// Mostrar ventana de confirmación de guardado
			alert('Datos de socio guardados.'); // reemplazar por algo mejor Danilo.
			// limpiamos errores
			fnHideErrores(sIdNameFormNuevoSocio);
			//refrescar el data table para los objetos en session
			dtSociosForSession.fnDraw();
			//cerramos el dialogo
			oDialogAgregarSocio.dialog( "close" );
		}).error(function(data) {
			fnProcesarErrores(data, sIdNameFormNuevoSocio);
		});	
	}
	
	function modificarSocio(){
		$("#componentePersona").attr("src","http://11.254.16.100/everest/");
		var obRowSelected = fnGetRowSelected(dtSocios);
		if(obRowSelected != undefined){
			oDialogAgregarSocio = construirDialogoPersona("Modificar Socio");
			oDialogAgregarSocio.dialog('open');
		}else{
			oDialogErrorSinSeleccionSocio.dialog('open');
		}
	}
	
	
	/*Funcion para abrir el dialogo de eliminar*/
	function fnOpenDialogEliminarSocio(){
		//Validamos que exista un elemento seleccionado.
		//if(fnValidaRegistroSeleccionado(dtSocios)){
		if(fnValidaRegistroSeleccionado(dtSociosForSession)){
			oDialogEliminarSocio.dialog('open');
		}else{
			//Mostramos mensaje de error
			oDialogErrorSinSeleccionSocio.dialog('open');
		}
	}
	
	/* 
	 * Callback de la busqueda de personas moral. 
	 */ 
	var fnOnPersonaMoralReturn = function(){ 
	        var p = this; 
	        
			if (p != null) {
			
			var idPersona = p.idPersona;
			var rfc = p.rfc;
			var denominacionRazonSocial = p.razonSocial;
			var tipoSociedadDesc = p.tipoSociedad.descripcion;

			
			$('#socioFormNuevo #idPersona').val(idPersona);
			$('#socioFormNuevo #rfc').val(rfc);
			$('#socioFormNuevo #denominacionRazonSocial').val(denominacionRazonSocial);
			$('#socioFormNuevo #tipoSociedad').val(tipoSociedadDesc);
			} else {
				alert('persona moral viene null desde el servicio de personas');
			}
			
			
	        
	}
	
	
	/* 
	 * Callback de la busqueda de personas fisicas 
	 */ 
	var fnOnPersonaFisicaReturn = function(){ 
	        var p = this; 
	        
			if (p != null) {
			
				var idPersona = p.idPersona;
				var rfc = p.rfc;
				var curp = p.curp;
				var nombre = p.nombre;
				var primerApellido = p.primerApellido;
				var segundoApellido = p.segundoApellido;
				
				$('#socioFormNuevo #idPersona').val(idPersona);
				$('#socioFormNuevo #rfc').val(rfc);
				$('#socioFormNuevo #curp').val(curp);
				$('#socioFormNuevo #nombres').val(nombre);
				$('#socioFormNuevo #primerApellido').val(primerApellido);
				$('#socioFormNuevo #segundoApellido').val(segundoApellido);

			} else {
				alert('persona fisica viene null desde el servicio de personas');
			}
			
			
	        
	}
	
	
	
	/**
	 * Funcion para invocar al proceso de buscar persona moral
	 */
	function fnOpenBuscarPersonaMoral() {
		PersonaMoralCtrl.buscar();
	}
	
	/**
	 * Funcion para invocar al proceso de buscar persona fisica
	 */
	function fnOpenBuscarPersonaFisica() {
		PersonaFisicaCtrl.buscar();
	}


		/*var oSendData = new Object();
		var sUrl = "/gestionIndividuo-web/persona/fisica/busqueda-seleccion";
		
		oSendData.session = sessionId;

		var oReturn = window
				.showModalDialog(
						sUrl,
						oSendData,
						"dialogWidth:1000px;dialogHeight:800px;status=yes,toolbar=no,menubar=no,location=no");	

		if (oReturn != null) {
			
			var idPersona = oReturn.idPersona;
			var rfc = oReturn.rfc;
			var curp = oReturn.curp;
			var nombre = oReturn.nombre;
			var primerApellido = oReturn.primerApellido;
			var segundoApellido = oReturn.segundoApellido;
			
//			alert('idPersona: ' + idPersona + ', rfc:  ' + rfc + 'curp: ' + curp
//					+ ', nombre: ' + nombre + ', apellido p: ' + primerApellido
//					+ ', apellido m:' + segundoApellido);
			
			$('#socioFormNuevo #idPersona').val(idPersona);
			
			$('#socioFormNuevo #rfc').val(rfc);
			$('#socioFormNuevo #curp').val(curp);
			$('#socioFormNuevo #nombres').val(nombre);
			$('#socioFormNuevo #primerApellido').val(primerApellido);
			$('#socioFormNuevo #segundoApellido').val(segundoApellido);
			
			
		}*/

	
	
function parseIndicadorNacionalidad( o ) {
	if (o == false){
		return 'Extranjero';
	}else{
		return 'Nacional';
	}
}
function parseIndicadorTipoPersona( o ) {
	if (o == false){
		return 'Moral';
	}else{
		return 'Fisica';
	}
}

function resetRadioButtons(){
	$('#socioFormNuevo #esPersonaFisica1').attr('checked', 'checked');
	$('#socioFormNuevo #esNacional1').attr('checked', 'checked');
	$('#socioFormNuevo #esDomicilioNacional1').attr('checked', 'checked');
}

function actualizarDatosSocio(accion) {
	
	sSource = context_path + "/socios/finalizar";
	
	$.postJSON(sSource, {}, function(data) {
		/* Actualizamos datos */
		oDialogActualizarDatosSocio.dialog("open");
	}).error(function(data) {
		alert('error al actualizar los datos de socio, notifíque al administrador del sistema.');
	});
}