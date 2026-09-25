/**
* Java Script de Representante Legal
*
**/

var arrayDatos = new Array();

//Objeto de datable de RepresentanteLegal
var dtRepresentanteLegal;
var dtRepresentanteLegalForSession;
var sIdNameFormPaginarRepresentanteLegal="#representanteLegalFormPaginar";
var sIdNameFormNuevoRepresentanteLegal="#representanteLegalFormNuevo";

var sIdDialgoAgregarRepresentanteLegal = "#dgNuevoRepresentanteLegal";
var oDialogAgregarRepresentanteLegal;

var sIdDialogEliminarRepresentanteLegal = "#dgEliminarRepresentanteLegal";
var oDialogEliminarRepresentanteLegal;

var sIdDialogEliminarRepresentanteLegalForSession = "#dgEliminarRepresentanteLegalTramite";
var oDialogEliminarRepresentanteLegalForSession;

var sIdDialogErrorSinSeleccionRepresentanteLegal = "#dgErrorSinSeleccionRepresentanteLegal";
var oDialogErrorSinSeleccionRepresentanteLegal;

var sIdFormModificarRepresentanteLegal = '#representanteLegalFormModificar';

var sIdDialogModificarRepresentanteLegal = "#dgModificarRepresentanteLegal";
var oDialogModificarRepresentanteLegal;

var sIdDialogActualizarDatosRepLegal = "#dgActualizaDatosRepLegal"
var oDialogActualizarDatosRepLegal;

	/** Seccion de codigo a ejectuar cuando el DOM este listo **/
$(function() {	
	
	/* Configuracion del dialogo de actualizacion exitosa */
	oDialogActualizarDatosRepLegal = $(
			sIdDialogActualizarDatosRepLegal).dialog( {
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
	
	
	/*
	 * Configuracion de los submits de las formas
	 */
	$(sIdNameFormNuevoRepresentanteLegal).submit(function(){
		agregarRepresentanteLegal();
		return false;
	});
	
	$(sIdFormModificarRepresentanteLegal).submit(function(){
		modificarRepresentanteLegal();
		return false;
	});
		
	/*Configuracion del dialogo de agregar nuevo elemento*/
	oDialogAgregarRepresentanteLegal = 	$( sIdDialgoAgregarRepresentanteLegal).dialog({
		autoOpen:false,
		resizable: false,
		modal: true,
		height:pDialogHeigthRepresentanteLegal,
		width:pDialogWidthRepresentanteLegal,
		buttons: {
			"Aceptar": function() {				
				agregarRepresentanteLegal();
			},
			"Cancelar":function(){
				fnHideErrores('#representanteLegalFormNuevo');
				limpiarFormulario('#representanteLegalFormNuevo');
				$( this ).dialog( "close" );
			}
		},
		open: function(event, ui) { 
			fnHideErrores('#representanteLegalFormNuevo');
			limpiarFormulario('#representanteLegalFormNuevo');
		}

	});

	//fnHideErrores
				
	/*Configuracion del dialogo de confirmar*/
	oDialogEliminarRepresentanteLegal = 	$( sIdDialogEliminarRepresentanteLegal ).dialog({
		autoOpen:false,
		resizable: false,
		height:200,
		modal: true,
		buttons: {
			"Eliminar": function(data) {
                                
                                fnHideErrores(sIdDialogEliminarRepresentanteLegal);
				
				/*Obtenemos el radio seleccionado*/
				var obRowSelected = fnGetRowSelected(dtRepresentanteLegalForSession);
				var idRepresentanteLegal = obRowSelected.cveIdPersona;
				
				var cveIdPatronSujetoObligado = $('#representanteLegalFormPaginar:hidden #cveIdPatronSujetoObligado').val();
				var tipoPersonaFiscal = $('#representanteLegal #tipoPersonaFiscalHidden').val();
                                    			
				var sSource = 'representanteLegal/eliminar';
				$.getJSON(sSource,{ idRepresentanteLegal: idRepresentanteLegal,  cveIdPatronSujetoObligado: cveIdPatronSujetoObligado, tipoPersonaFiscal:tipoPersonaFiscal} , function(data) {
					//refrescar el data table
					dtRepresentanteLegal.fnDraw();                                            
                     oDialogEliminarRepresentanteLegal.dialog("close");
				}).error(function(data) {
                fnProcesarErrores(data, sIdDialogEliminarRepresentanteLegal);
            });
			},
			'Cancelar': function() {
                                fnHideErrores(sIdDialogEliminarRepresentanteLegal);
				$( this ).dialog( "close" );
			}
		}
	});
	
	/*Configuracion del dialogo de confirmar la eliminacion de un elemento del grid de los objetos de sesion*/
	oDialogEliminarRepresentanteLegalForSession = 	$( sIdDialogEliminarRepresentanteLegalForSession ).dialog({
		autoOpen:false,
		resizable: false,
		height:200,
		modal: true,
		buttons: {
			"Eliminar": function(data) {
                                
                                fnHideErrores(sIdDialogEliminarRepresentanteLegalForSession);
				
				/*Obtenemos el radio seleccionado*/
				var obRowSelected = fnGetRowSelected(dtRepresentanteLegal);
				//var idRepresentanteLegal = obRowSelected.cveIdRepresentanteLegal;
				var cveIdPersona = obRowSelected.cveIdPersona;
				var cveIdPatronSujetoObligado = $('#representanteLegalFormPaginar:hidden #cveIdPatronSujetoObligado').val();
				var tipoPersonaFiscal = $('#representanteLegal #tipoPersonaFiscalHidden').val();
					
				var sSource = '/delta-gestionPatronal-web/representanteLegal/eliminar';
				$.getJSON(sSource,{ cveIdPersona: cveIdPersona,  cveIdPatronSujetoObligado: cveIdPatronSujetoObligado, tipoPersonaFiscal:tipoPersonaFiscal} , function(data) {
					//refrescar el data table
					dtRepresentanteLegalForSession.fnDraw();                                           
                    oDialogEliminarRepresentanteLegalForSession.dialog("close");
				}).error(function(data) {
					alert("error"+data);				
                //fnProcesarErrores(data, sIdDialogEliminarRepresentanteLegalForSession);
            });
			},
			'Cancelar': function() {
                                fnHideErrores(sIdDialogEliminarRepresentanteLegalForSession);
				$( this ).dialog( "close" );
			}
		}
	});

	/*Configuracion del dialogo del mensaje de aviso de registro no seleccionado*/
	
	oDialogErrorSinSeleccionRepresentanteLegal =  $( sIdDialogErrorSinSeleccionRepresentanteLegal ).dialog({
		autoOpen:false,
		resizable: false,
		height:140,
		modal: true,
		buttons: {
			'Aceptar': function() {
				$( this ).dialog( "close" );
			}
		}
	});

	
	

	
	/*Configuracion del dialogo de modificar  elemento*/
	oDialogModificarRepresentanteLegal = 	$( sIdDialogModificarRepresentanteLegal).dialog({
		autoOpen:false,
		resizable: false,
		modal: true,	
		height:pDialogHeigthRepresentanteLegal,
		width:pDialogWidthRepresentanteLegal,
		buttons: {
			"Aceptar": function() {
				modificarRepresentanteLegal();
			},
			"Cancelar":function(){
				fnHideErrores(sIdDialogModificarRepresentanteLegal);
				$(this).dialog('close');
			}
		},
		open: function(event, ui) { 
			fnHideErrores(sIdDialogModificarRepresentanteLegal);

		}
	});

	/* Configuracion del data table de Representante Legal*/
	dtRepresentanteLegal = $('#tbRepresentanteLegal').dataTable({
		"bJQueryUI": true,
		"bPaginate": false,
		"bLengthChange": false,
		"iDisplayLength": 5,
		"bFilter": false,
		"bSort": false,
		"bInfo": false,
		"bAutoWidth": false,
			"bServerSide" : true,			
			//"sPaginationType": "full_numbers",
			"aoColumns" : [ 
			               	{			            	   
			               	"mDataProp" : "cveIdPersona",
			               	"bVisible": false
			               	},
			     
				{ 
					"sTitle" : "RFC",
					"mDataProp" : "personaFisica.rfc"
					//"sClass":"dtJustifyClassColumn"
					
				},
				{ 
					"sTitle" : "CURP",
					"mDataProp" : "personaFisica.curp"
					//"sClass":"dtJustifyClassColumn"					
				}
				,
				{ 
					"sTitle" : "Nombre",
					"mDataProp" : "personaFisica.nombre"
					//"sClass":"dtJustifyClassColumn"
				}
				,
				{ 
					"sTitle" : "Actos de Administraci&oacute;n",
					"mDataProp" : "indActAdmon",
					"fnRender": function ( oObj ) {						
						return parseIndicador(oObj.aData.indActAdmon);
					}
					//"sClass":"dtJustifyClassColumn"
				},
				{ 
					"sTitle" : "Actos de Dominio",
					"mDataProp" : "indActDominio",
					"fnRender": function ( oObj ) {
						return parseIndicador(oObj.aData.indActDominio);
					}
					//"sClass":"dtJustifyClassColumn"
				},
				{ 
					"sTitle" : "",					
					"fnRender": function ( oObj ) {						
						index=oObj.aData.cveIdPersona;
						arrayDatos[index]=oObj.aData;						
						return construyeLiga(index);
					}
					
				}
				
				
			],

				"bProcessing" : true,
				"sAjaxSource" : '/delta-gestionPatronal-web/representanteLegal/fb/paginar',
				"fnServerData" : function(sSource, aoData, fnCallback) {
					aoData.push({
						"name" : "sSearch",
						"value" : ''
					});
					
					var wrapper = new Object();
					wrapper.aoData = aoData;
					var oForm = $(sIdNameFormPaginarRepresentanteLegal).serializeObject(true);
					wrapper.oForm = oForm;
					
					$.postJSON(sSource, wrapper, function(data) {								
						fnCallback(data);
					});
					
				}
			});
	
	$("#tbRepresentanteLegal tbody").hover(
			function(){
				$(this).css('cursor', 'pointer');
			}
		);


	/* Add a click handler to the rows - this could be used as a callback */
	$("#tbRepresentanteLegal tbody").click(function(event) {
		$(dtRepresentanteLegal.fnSettings().aoData).each(function (){ 
			$(this.nTr).removeClass('row_selected'); 
		});
		
		
		if($(event.target.parentNode).hasClass('row_selected')){
			$(event.target.parentNode).removeClass('row_selected');
		}
		else{
			$(event.target.parentNode).addClass('row_selected');
		}
		
	});
	
	
	
	
	/* Configuracion del data table de Representante Legal para el objeto de sesion*/
	dtRepresentanteLegalForSession = $('#tbRepresentanteLegalForSession').dataTable({
			bJQueryUI : true,
			bFilter : false,
			bInfo:false,
			bSort: false,
			"bPaginate": false,
			"bAutoWidth" : true,
			"bServerSide" : true,
			//"sPaginationType": "full_numbers",
			"aoColumns" : [ 
			               {			            	   
								"mDataProp" : "cveIdPersona",
								"bVisible": false
			               },
						     
							{ 
								"sTitle" : "RFC",
								"mDataProp" : "personaFisica.rfc"								
							},
							{ 
								"sTitle" : "CURP",
								"mDataProp" : "personaFisica.curp"													
							}
							,
							{ 
								"sTitle" : "Nombre",
								"mDataProp" : "personaFisica.nombre"								
							}
							,
							{ 
								"sTitle" : "Actos de Administraci&oacute;n",
								"mDataProp" : "indActAdmon",
								"fnRender": function ( oObj ) {						
									return parseIndicador(oObj.aData.indActAdmon);
								}
							},
							{ 
								"sTitle" : "Actos de Dominio",
								"mDataProp" : "indActDominio",
								"fnRender": function ( oObj ) {
									return parseIndicador(oObj.aData.indActDominio);
								}
							},							
							{ 
								"sTitle" : "Acci&oacute;n a Realizar",					
								"fnRender": function ( oObj ) {						
									index=oObj.aData.indAccionAfectacion;														
									return showAfectacion(index);
								}
								
							}
							
							
				],

				"bProcessing" : true,
				"sAjaxSource" : '/delta-gestionPatronal-web/representanteLegal/paginarForSession',
				"fnServerData" : function(sSource, aoData, fnCallback) {
					aoData.push({
						"name" : "sSearch",
						"value" : ''
					});
					
					var wrapper = new Object();
					wrapper.aoData = aoData;
					var oForm = $(sIdNameFormPaginarRepresentanteLegal).serializeObject(true);
					oForm.cveIdRepresentanteLegal=$('#idSolicitudRL').val();

					wrapper.oForm = oForm;
					
					$.postJSON(sSource, wrapper, function(data) {
						fnCallback(data);
					});
					
				}
			});
			
			/*$("#tbRepresentanteLegalForSession tbody").hover(
				function(){
					$(this).css('cursor', 'pointer');
				}
			);
			
			/* Add a click handler to the rows - this could be used as a callback */
			/*$("#tbRepresentanteLegalForSession tbody").click(function(event) {
				$(dtRepresentanteLegalForSession.fnSettings().aoData).each(function (){ 
					$(this.nTr).removeClass('row_selected'); 
				});
				
				
				if($(event.target.parentNode).hasClass('row_selected')){
					$(event.target.parentNode).removeClass('row_selected');
				}
				else{
					$(event.target.parentNode).addClass('row_selected');
				}
				
			});*/
			
			
			
			/*
			 * configuracion para buscar a la persona fisica
			 */
			$.getScript("http://localhost:7001/gestionIndividuo-web/static/resources/js/delta/personas/fisica/PersonaFisica.js", function(){

				PersonaFisicaCtrl.init('personaFisica'); 
				PersonaFisicaCtrl.setOnCloseCallback(fnOnPersonaReturn); 
				
			});

	
	
	
	
});


	/* 
	 * Callback de la busqueda de personas fisica. 
	 */ 
	var fnOnPersonaReturn = function(){ 
	        var p = this; 
	        
			
			if (p != null) {
			
				var idPersona = p.idPersona;
				var rfc = p.rfc;
				var curp = p.curp;
				var nombre = p.nombre;
				var primerApellido = p.primerApellido;
				var segundoApellido = p.segundoApellido;
				var calif = p.personaCalificacion; // TODO YORCH: pendiente de proporcionar por parte de personas (Junio 12, 2012)
				var mediosContacto = p.mediosContacto;
				var correoElectronico = p.correoElectronico; // esto va en el jsp: personaFisica.correoElectronico.clave, para unicamente guardar la clave, o puede que baste con las claves de los medios de contacto.
				var telefonoFijo = p.telefonoFijo;
				var telefonoMovil = p.telefonoMovil;
				
				$('#representanteLegalFormNuevo #cveIdPersona').val(idPersona); // para el rep legal
				$('#representanteLegalFormNuevo #personaFisicaCveIdPersona').val(idPersona); // de la persona fisica en rep legal
				
				
				$('#representanteLegalFormNuevo #rfc').val(rfc);
				$('#representanteLegalFormNuevo #curp').val(curp);
				$('#representanteLegalFormNuevo	#nombre').val(nombre);
				$('#representanteLegalFormNuevo #primerApellido').val(primerApellido);
				$('#representanteLegalFormNuevo #segundoApellido').val(segundoApellido);
				
				if (mediosContacto != null){
					//alert(mediosContacto);
				}

				if (calif == undefined){
					alert('No se puede establecer la calificación para la persona indicada, \nverifique los datos introducidos o pruebe seleccionando a otra persona \ncomo representante legal.');
					//return;
				}
			} else {
				alert('persona fisica viene null desde el servicio de personas');
			}
			
			
	        
	}


	/**
	 * Funcion para invocar al proceso de buscar persona fisica
	 */
	function fnOpenBuscarPersonaFisica() {
		PersonaFisicaCtrl.buscar();
	}
	
	
	
	/*Funcion para agregar el elemento nuevo de Representante Legal*/
	var fnOpenDialogNuevoRepresentanteLegal = function(){
		oDialogAgregarRepresentanteLegal.dialog('open');
	}
	
	/*Funcion para abrir el dialogo de eliminar*/
	var fnOpenDialogEliminarRepresentanteLegal = function(){
		//Validamos que exista un elemento seleccionado.
		if(fnValidaRegistroSeleccionado(dtRepresentanteLegal)){
			oDialogEliminarRepresentanteLegal.dialog('open');
		}else if (fnValidaRegistroSeleccionado(dtRepresentanteLegalForSession)){
			
			oDialogEliminarRepresentanteLegalForSession.dialog('open');
		} else {
			//Mostramos mensaje de error
			fnDialogErrorSinSeleccionRepresentanteLegal();
		}
		
		
		
	}
	

	

	
	
	/*FUncion para mostrar el mensaje de error cuando no existe un registro seleccionado*/
	var fnDialogErrorSinSeleccionRepresentanteLegal = function(){
		oDialogErrorSinSeleccionRepresentanteLegal.dialog('open');
	}
	
	/*Funcion para obtener el elemento seleccionado*/
	var fnGetElementoRepresentanteLegal = function(cveIdPersona){

		var sSource = 'representanteLegal/get';
		
		$.getJSON(sSource,{ cveIdPersona: cveIdPersona } , function(data) {
			
			 
			 $('#representanteLegalFormModificar  #rfc').val( data.personaFisica.rfc);
			 $('#representanteLegalFormModificar  #curp').val( data.personaFisica.curp);
			 $('#representanteLegalFormModificar  #primerApellido').val( data.personaFisica.primerApellido);
			 $('#representanteLegalFormModificar  #segundoApellido').val( data.personaFisica.segundoApellido);
			 $('#representanteLegalFormModificar  #nombre').val( data.personaFisica.nombre);
			 $('#representanteLegalFormModificar  #telefonoFijoNumero').val( data.personaFisica.telefonoFijo.numero);
			 $('#representanteLegalFormModificar  #telefonoFijoClaveLada').val( data.personaFisica.telefonoFijo.claveLada);
			 $('#representanteLegalFormModificar  #telefonoFijoExtension').val( data.personaFisica.telefonoFijo.extension);
			 $('#representanteLegalFormModificar  #telefonoMovilNumero').val( data.personaFisica.telefonoMovil.numero);
			 $('#representanteLegalFormModificar  #dirCorreo').val( data.personaFisica.correoElectronico.correo);
			 
			 if (data.indActAdmon == 1){
				 $('#indActAdmon2').attr('checked', 'checked');
			 } else {
				 $('#indActAdmon2').removeAttr('checked');
			 }
			 
			 if (data.indActDominio == 1){
				 $('#indActDominio2').attr('checked', 'checked');
			 } else {
				 $('#indActDominio2').removeAttr('checked');
			 }
			 
			 
			 $('#representanteLegalFormModificar #cveIdPatronSujetoObligado').val( data.cveIdPatronSujetoObligado);
			 $('#representanteLegalFormModificar #cveIdRepresentanteLegal').val( data.cveIdRepresentanteLegal);
			 $('#representanteLegalFormModificar #cveIdPersona').val( data.cveIdPersona);
			 
			 $('#representanteLegalFormModificar #fecRegistroActualizado').val(data.fecRegistroActualizado);
			 $('#representanteLegalFormModificar #fecRegistroAlta').val(makeDateFromIsoString(data.fecRegistroAlta));
			 
			 /*Abrimos el dialogo*/
			 oDialogModificarRepresentanteLegal.dialog('open');
			 
		});
		
		
	}
	
	
	/*Funcion para abrir el dialogo de modificar*/
	
	
	var fnOpenDialogModificarRepresentanteLegal = function(){
		
		//Validamos que exista un elemento seleccionado.
		if(fnValidaRegistroSeleccionado( dtRepresentanteLegal )){
			/*Obtenemos el radio seleccionado*/
			var obRowSelected = fnGetRowSelected(dtRepresentanteLegal);
			var cveIdPersona = obRowSelected.cveIdPersona;
			
			fnGetElementoRepresentanteLegal(cveIdPersona);
			
		}else if (fnValidaRegistroSeleccionado(dtRepresentanteLegalForSession)){
			/*Obtenemos el radio seleccionado*/
			var obRowSelected = fnGetRowSelected(dtRepresentanteLegalForSession);
			var cveIdPersona = obRowSelected.cveIdPersona;
			
			fnGetElementoRepresentanteLegal(cveIdPersona);
		} else {
			//Mostramos mensaje de error
			fnDialogErrorSinSeleccionRepresentanteLegal();
		}
		
		
		

	}

/*
 * funciones para navegacion
 */
	function fnRepresentanteLegalGoBack(){
		//$("#form-back").submit();
		alert('Not implemented yet.');
	}
	
	function fnRepresentanteLegalGoAhead(){
		$("#form-representante-legal-forward").submit();
	}
	
	
	
	function agregarRepresentanteLegal( ){
		
		
		fnHideErrores(sIdNameFormNuevoRepresentanteLegal);
		
		/*la invocacion a guardar un nuevo elemento*/
		var cveIdPatronSujetoObligado = $('#representanteLegalFormPaginar:hidden #cveIdPatronSujetoObligado').val();
		$('#representanteLegalFormNuevo:hidden #cveIdPatronSujetoObligado').val(cveIdPatronSujetoObligado);
		
		$('#indActAdmon').val($('#indActAdmon1').is(':checked') ? 1 : 0);
		$('#indActDominio').val($('#indActDominio1').is(':checked') ? 1 : 0);
		
		
		
		/*var objPersonaFisica = new Object();
		var objTelefonoFijo = new Object();
		var objTelefonoMovil = new Object();
		var objCorreoElectronico = new Object();

		var numero = $("#telefonoFijoNumero").val();
		var claveLada = $("#telefonoFijoClaveLada").val();
		var extension = $("#telefonoFijoExtension").val();
		var numeroMovil = $("#telefonoMovilNumero").val();
		var dirCorreo = $("#dirCorreo").val();*/
		
		var oForm = $(sIdNameFormNuevoRepresentanteLegal).toObject(true);
		
		/*objTelefonoFijo.numero = numero;
		objTelefonoFijo.claveLada = claveLada;
		objTelefonoFijo.extension = extension;
		
		objTelefonoMovil.numero = numeroMovil;
		
		objCorreoElectronico.correo = dirCorreo;
		
		objPersonaFisica.telefonoFijo = objTelefonoFijo;
		objPersonaFisica.telefonoMovil = objTelefonoMovil;
		objPersonaFisica.correoElectronico = objCorreoElectronico;
		oForm.personaFisica = objPersonaFisica;*/
		
		var sSource = 'representanteLegal/agregar';
		$.postJSON(sSource, oForm, function(data) {
			fnHideErrores(sIdNameFormNuevoRepresentanteLegal);
			//refrescar el data table
			dtRepresentanteLegalForSession.fnDraw();
			//cerramos el dialogo
			oDialogAgregarRepresentanteLegal.dialog( "close" );
		}).error(function(data) {
			fnProcesarErrores(data, sIdNameFormNuevoRepresentanteLegal);
		});
	}
	
	function modificarRepresentanteLegal(){
		fnHideErrores(sIdFormModificarRepresentanteLegal);
		$('#representanteLegalFormModificar #indActAdmon').val($('#modificar #indActAdmon2').is(':checked') ? 1 : 0);
		$('#representanteLegalFormModificar #indActDominio').val($('#modificar #indActDominio2').is(':checked') ? 1 : 0);
		
		/*la invocacion a modificar el elemento*/
		var oForm = $(sIdFormModificarRepresentanteLegal).toObject(true);
		var sSource = 'representanteLegal/modificar';
		
		$.postJSON(sSource, oForm, function(data) {			
			//refrescar el data table
			dtRepresentanteLegalForSession.fnDraw();
                        
                        fnHideErrores(sIdDialogModificarRepresentanteLegal);
                        
			$( oDialogModificarRepresentanteLegal ).dialog( "close" );
		}).error(function(data) {
			fnProcesarErrores(data, sIdDialogModificarRepresentanteLegal);
		});
	}
	
	
	
	

/**
 * Funcion para invocar al proceso de buscar persona. Diferenciar entre p. fisica y p. moral 
 */
function fnOpenBuscarPersona() {

	var oSendData = new Object();
	var sUrl = "/gestionIndividuo-web/persona/fisica/busqueda-embebida";
	
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
		var calif = oReturn.personaCalificacion; // TODO YORCH: pendiente de proporcionar por parte de personas (Junio 12, 2012)
		
		
		
		//alert('idPersona: ' + idPersona + ', rfc:  ' + rfc + 'curp: ' + curp
		//		+ ', nombre: ' + nombre + ', apellido p: ' + primerApellido
		//		+ ', apellido m:' + segundoApellido + 'calficacion: ' + calif);
			

		if (calif == undefined){
			alert('No se puede establecer la calificación para la persona indicada, \nverifique los datos introducidos o pruebe seleccionando a otra persona \ncomo representante legal.');
			//return;
		}
		
		
		
		
		
		$('#representanteLegalFormNuevo #cveIdPersona').val(idPersona); // para el rep legal
		$('#representanteLegalFormNuevo #personaFisicaCveIdPersona').val(idPersona); // de la persona fisica en rep legal
		
		
		$('#representanteLegalFormNuevo #rfc').val(rfc);
		$('#representanteLegalFormNuevo #curp').val(curp);
		$('#representanteLegalFormNuevo	 #nombre').val(nombre);
		$('#representanteLegalFormNuevo #primerApellido').val(primerApellido);
		$('#representanteLegalFormNuevo #segundoApellido').val(segundoApellido);
		
		
	}

}

/**
 * Funcion para invocar al proceso de buscar persona. Diferenciar entre p. fisica y p. moral 
 */
function fnOpenAgregarDatosContacto() {
	alert('Pendiente de implementación');
}




function parseIndicador( o ) {
	if (o == '1' || o == 1){
		return 'si';		
		
	}else{
		return 'no';		
	}
}

function construyeLiga( object ){
	
	return "<a href='#' onclick='showRepLegal("+object+")'>Mostrar Detalle</a>";
}

function makeDateFromIsoString(date){




	if (date != null){
	
		if (date.indexOf("-") != -1){
			var d = new Date(Date.parse(date));
			var dd = (d.getDate() + 1) + '/' + (d.getMonth() + 1) + '/' + d.getFullYear();
			//alert(dd);
			return dd;
		} else {
			//alert(date);
			return date;
		}

	}else {
		return new Date();
	}

}



			 
function actualizarDatosRepLegal(accion) {
	
	//sSource = context_path + "/sujetoObligado/actualizarEscrituraConstitutiva/"+$("#idSolicitud").val();
	sSource = context_path + "/representanteLegal/finalizar";
	//$.postJSON(sSource, objForm, function(data) {
	$.postJSON(sSource, {}, function(data) {
		/* Actualizamos datos */
		oDialogActualizarDatosRepLegal.dialog("open");
	}).error(function(data) {
		alert('algo feo pasó :(');
	});
}

//Aquí se abre el diálogo para mostrar los detalles del representante legal
function showRepLegal(indice){	
	dialogFirma =  $(representanteLegalConsultar).dialog({
		autoOpen:false,
		resizable: false,
		height: 550,
		width: 1000,
		modal: true		
	});		
	$("#representanteLegalConsultar").css("display", "block");
	document.getElementById("fisica.nombre").value=arrayDatos[indice].personaFisica.nombre;
	document.getElementById("fisica.primerApellido").value=arrayDatos[indice].personaFisica.primerApellido;	
	document.getElementById("fisica.segundoApellido").value=arrayDatos[indice].personaFisica.segundoApellido;
	document.getElementById("fisica.rfc").value=arrayDatos[indice].personaFisica.rfc;
	document.getElementById("fisica.curp").value=arrayDatos[indice].personaFisica.curp;	
	dialogFirma.dialog('open');
}

function showAfectacion(index){
	if (index==1)
		return "Agregar";
	if (index==2)
		return "Modificar";
	if (index==3)
		return "Eliminar";
}
