/**
* Java Script de Personal
**/


//Objeto de datable del personal
var dtPersonal;
var sIdNameFormPaginarPersonal="#personalFormPaginar";
var sIdNameFormNuevoPersonal="#personalFormNuevo";

var oDialogAgregarPersonal;
var sIdDialgoAgregarPersonal = "#dgNuevoPersonal";


var sIdDialogEliminarPersonal = "#dgEliminarPersonal";
var oDialogEliminarPersonal;

var sIdDialogErrorSinSeleccionPersonal = "#dgErrorSinSeleccionPersonal";
var oDialogErrorSinSeleccionPersonal;


var sIdFormModificarPersonal = '#personalFormModificar';



var sIdDialogModificarPersonal = "#dgModificarPersonal";
var oDialogModificarPersonal;

var pDialogHeigthPersonal = 350;
var pDialogWidthPersonal = 450;

	/** Seccion de codigo a ejectuar cuando el DOM este listo **/
	$(function() {
		
		// validamos que solo se ingresen numeros
	    $("#personalFormNuevo #numTrabajadores").keydown(function(event) {
	    	
	    	// Prevent shift key since its not needed
	        if (event.shiftKey == true) {
	            event.preventDefault();
	        }
	        // Allow Only: keyboard 0-9, numpad 0-9, backspace, tab, left arrow, right arrow, delete
	        if ((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105) || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 37 || event.keyCode == 39 || event.keyCode == 46) {
	            // Allow normal operation
	        } else {
	            // Prevent the rest
	            event.preventDefault();
	        }
	        
	    });	
	    
	 // validamos que solo se ingresen numeros
	    $("#personalFormModificar #numTrabajadores").keydown(function(event) {
	    	
	    	// Prevent shift key since its not needed
	        if (event.shiftKey == true) {
	            event.preventDefault();
	        }
	        // Allow Only: keyboard 0-9, numpad 0-9, backspace, tab, left arrow, right arrow, delete
	        if ((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105) || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 37 || event.keyCode == 39 || event.keyCode == 46) {
	            // Allow normal operation
	        } else {
	            // Prevent the rest
	            event.preventDefault();
	        }
	        
	    });
		
		/*Configuracion del dialogo de agregar nuevo elemento*/
		oDialogAgregarPersonal = 	$( sIdDialgoAgregarPersonal).dialog({
			autoOpen:false,
			resizable: false,
			modal: true,
			height:pDialogHeigthPersonal,
			width:pDialogWidthPersonal,
			buttons: {
				"Aceptar": function() {
					
					agregarPersonal();
					
				},
                'Cancelar': function() {
                    fnHideErrores('#personalFormNuevo');
                    limpiarFormulario('#personalFormNuevo');
                    $( this ).dialog( "close" );
				}
			},open: function(event, ui) { 
	            fnHideErrores('#personalFormNuevo');
	            limpiarFormulario('#personalFormNuevo');
			}
		});

		
	/*Configuracion del dialogo de confirmar*/
		oDialogEliminarPersonal = 	$( sIdDialogEliminarPersonal ).dialog({
			autoOpen:false,
			resizable: false,
			height:200,
			modal: true,
			buttons: {
				"Eliminar": function() {
					var obRowSelected = fnGetRowSelected(dtPersonal);
					var idPersonal = obRowSelected.cveIdPersonal;
                                        
                    var idSolicitud = $('#personalFormPaginar:hidden #cveIdSolicitud').val();
					var sSource = 'personal/eliminar';
					$.getJSON(sSource,{ idPersonal: idPersonal} , function(data) {
						//refrescar el data table
						dtPersonal.fnDraw();
						oDialogEliminarPersonal.dialog("close");
					}).error(function(data) {
						fnProcesarErrores(data, sIdDialogEliminarPersonal);
					});
					
				},
				'Cancelar': function() {
					fnHideErrores(sIdDialogEliminarPersonal);
					$( this ).dialog( "close" );
				}
			}
		});

		/*Configuracion del dialogo del mensaje de aviso de registro no seleccionado*/
		oDialogErrorSinSeleccionPersonal =  $( sIdDialogErrorSinSeleccionPersonal ).dialog({
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
		oDialogModificarPersonal = 	$( sIdDialogModificarPersonal).dialog({
			autoOpen:false,
			resizable: false,
			modal: true,
			height:pDialogHeigthPersonal,
			width:pDialogWidthPersonal,
			buttons: {
				"Aceptar": function() {
					modificarPersonal();
				},
				'Cancelar': function() {
					fnHideErrores(sIdDialogModificarPersonal);
					$(this).dialog('close');
				}
			},
			open: function(event, ui) { 
				fnHideErrores(sIdDialogModificarPersonal);
			}
		});

		/* Configuracion del data table de personal*/
		dtPersonal = $('#tbPersonal').dataTable({
				bJQueryUI : false,
				bFilter : false,
				bInfo:false,
				bSort: false,
				"bPaginate": false,
				"bAutoWidth" : false,
				"bServerSide" : true,
				//"sPaginationType": "full_numbers",
				"aoColumns" : [               
				    { 
						"sTitle" : "63. No. Trabajadores",
						"mDataProp" : "numTrabajadores",
						"sClass":"dtJustifyClassColumn"
					},
					{ 
						"sTitle" : "64. Oficio u Ocupaci&oacute;n",
						"mDataProp" : "oficioOcupacion",
						"sClass":"dtJustifyClassColumn"
					}
				],

				"bProcessing" : true,
				"sAjaxSource" : 'personal/paginar',
				"fnServerData" : function(sSource, aoData, fnCallback) {
					aoData.push({
						"name" : "sSearch",
						"value" : ''
					});
					
					var wrapper = new Object();
					wrapper.aoData = aoData;
					var oForm = $(sIdNameFormPaginarPersonal).serializeObject(true);
					wrapper.oForm = oForm;
					
					$.postJSON(sSource, wrapper, function(data) {
						fnCallback(data);
					});
				}
			});
		
			/* Add a click handler to the rows - this could be used as a callback */
			$("#tbPersonal tbody").click(function(event) {
				
				$(dtPersonal.fnSettings().aoData).each(function (){ 
					$(this.nTr).removeClass('row_selected'); 
				});
				
				$(event.target.parentNode).addClass('row_selected');
			});
	});
	
	
	/*Funcion para agregar el elemento nuevo de Personal*/
	var fnOpenDialogNuevoPersonal = function(){
		oDialogAgregarPersonal.dialog('open');
	}
	
	/*Funcion para abrir el dialogo de eliminar*/
	var fnOpenDialogEliminarPersonal = function(){
		//Validamos que exista un elemento seleccionado.
		if(fnValidaRegistroSeleccionado(dtPersonal)){
			oDialogEliminarPersonal.dialog('open');
		}else{
			//Mostramos mensaje de error
			fnDialogErrorSinSeleccionPersonal();
		}
	}
	

	/*Funcion para mostrar el mensaje de error cuando no existe un registro seleccionado*/
	var fnDialogErrorSinSeleccionPersonal = function(){
		oDialogErrorSinSeleccionPersonal.dialog('open');
	}
	
	/*Funcion para obtener el elemento seleccionado*/
	var fnGetElementoPersonal = function(idPersonal){

		var sSource = 'personal/get';
		
		$.getJSON(sSource,{ idPersonal: idPersonal } , function(data) {
			
			$('#personalFormModificar:hidden  #cveIdSolicitud').val( data.cveIdSolicitud);
			$('#personalFormModificar:hidden  #cveIdPersonal').val( data.cveIdPersonal);
			$('#personalFormModificar #numTrabajadores').val( data.numTrabajadores);
			$('#personalFormModificar #oficioOcupacion').val( data.oficioOcupacion);
			 
			 /*Abrimos el dialogo*/
			 oDialogModificarPersonal.dialog('open');
		});
	}
	
	
	/*Funcion para abrir el dialogo de modificar*/
	
	
	var fnOpenDialogModificarPersonal = function(){
		
		//Validamos que exista un elemento seleccionado.
		if(fnValidaRegistroSeleccionado(dtPersonal)){
			/*Obtenemos el radio seleccionado*/
			var obRowSelected = fnGetRowSelected(dtPersonal);
			var idPersonal = obRowSelected.cveIdPersonal;
			
			fnGetElementoPersonal(idPersonal);
			
		}else{
			//Mostramos mensaje de error
			fnDialogErrorSinSeleccionPersonal();
		}
	}
	
	
	
	
	
	/*
	 * funciones para navegacion
	 */
	function fnPersonalGoBack(){
		$("#form-personal-back").submit();
	}
	
	function fnPersonalGoAhead(){
		$("#form-personal-forward").submit();
	}
	
	
	
	function agregarPersonal()
	{
		fnHideErrores(sIdNameFormNuevoPersonal);
		/*la invocacion a guardar un nuevo elemento*/
		var idSolicitud = $('#personalFormPaginar:hidden #cveIdSolicitud').val();
		$('#personalFormNuevo:hidden #cveIdSolicitud').val(idSolicitud);
		var oForm = $(sIdNameFormNuevoPersonal).serializeObject(true);
		var sSource = 'personal/agregar';
	    $.postJSON(sSource, oForm, function(data) {
	         //cerramos el dialogo
	         oDialogAgregarPersonal.dialog( "close" );
			//refrescar el data table
			dtPersonal.fnDraw();
		}).error(function(data) {
			fnProcesarErrores(data, sIdNameFormNuevoPersonal);
		});
	}
	
	
	function modificarPersonal()
	{
		fnHideErrores(sIdFormModificarPersonal);
		
		/*la invocacion a modificar el elemento*/
		var oForm = $(sIdFormModificarPersonal).serializeObject(true);
		var sSource = 'personal/modificar';
		
		$.postJSON(sSource, oForm, function(data) {
			
			//refrescar el data table
			dtPersonal.fnDraw();
			fnHideErrores(sIdDialogModificarPersonal);
			$( oDialogModificarPersonal ).dialog( "close" );
		}).error (function (data){
			fnHideErrores(sIdDialogModificarPersonal);
		});
	}
		