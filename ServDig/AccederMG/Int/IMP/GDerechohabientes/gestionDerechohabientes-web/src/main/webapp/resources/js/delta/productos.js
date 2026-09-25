/**
* Java Script de los productos
*
**/


//Objeto de datable de los productos
var dtProductos;
var sIdNameFormPaginarProductos="#productoFormPaginar";
var sIdNameFormNuevoProductos="#productoFormNuevo";



var sIdDialgoAgregarProductos = "#dgNuevoProductos";
var oDialogAgregarProductos;


var sIdDialogEliminarProductos = "#dgEliminarProductos";
var oDialogEliminarProductos;

var sIdDialogErrorSinSeleccionProductos = "#dgErrorSinSeleccionProductos";
var oDialogErrorSinSeleccionProductos;


var sIdFormModificarProductos = '#productoFormModificar';



var sIdDialogModificarProductos = "#dgModificarProductos";
var oDialogModificarProductos;






	/** Seccion de codigo a ejectuar cuando el DOM este listo **/
	$(function() {
		
		
		
		/*
		 * Configuracion de los submits de las formas
		 */
		
	$(sIdNameFormNuevoProductos).submit(function(){
		agregarProductos();
		return false;
	});
	
	$(sIdFormModificarProductos).submit(function(){
		modificarProductos();
		return false;
	});
		
		
		
		/*Configuracion del dialogo de agregar nuevo elemento*/
		oDialogAgregarProductos = 	$( sIdDialgoAgregarProductos).dialog({
			autoOpen:false,
			resizable: false,
			modal: true,
			height:pDialogHeigthProductos,
			width:pDialogWidthProductos,
			buttons: {
				"Aceptar": function() {
					
					agregarProductos();
				},
				"Cancelar":function(){
					fnHideErrores('#productoFormNuevo');
					limpiarFormulario('#productoFormNuevo');
					$( this ).dialog( "close" );
				}
			},
			open: function(event, ui) { 
				fnHideErrores('#productoFormNuevo');
				limpiarFormulario('#productoFormNuevo');
			}

		});
		
		
		//fnHideErrores
		
		
	/*Configuracion del dialogo de confirmar*/
		oDialogEliminarProductos = 	$( sIdDialogEliminarProductos ).dialog({
			autoOpen:false,
			resizable: false,
			height:200,
			modal: true,
			buttons: {
				"Eliminar": function(data) {
                                    
                                    fnHideErrores(sIdDialogEliminarProductos);
					
					/*Obtenemos el radio seleccionado*/
					var obRowSelected = fnGetRowSelected(dtProductos);
					var idProductoServicio = obRowSelected.cveIdProducto;
                                        
                                        var idSolicitud = $('#productoFormPaginar:hidden #cveIdSolicitud').val();
                                        
					
					var sSource = 'productos/eliminar';
					$.getJSON(sSource,{ idProductoServicio: idProductoServicio,  idSolicitud: idSolicitud} , function(data) {
						//refrescar el data table
						dtProductos.fnDraw();
                                                
                                                 oDialogEliminarProductos.dialog("close");
					}).error(function(data) {
                    fnProcesarErrores(data, sIdDialogEliminarProductos);
                });
				},
				'Cancelar': function() {
                                    fnHideErrores(sIdDialogEliminarProductos);
					$( this ).dialog( "close" );
				}
			}
		});

		/*Configuracion del dialogo del mensaje de aviso de registro no seleccionado*/
		
		oDialogErrorSinSeleccionProductos =  $( sIdDialogErrorSinSeleccionProductos ).dialog({
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
		oDialogModificarProductos = 	$( sIdDialogModificarProductos).dialog({
			autoOpen:false,
			resizable: false,
			modal: true,	
			height:pDialogHeigthProductos,
			width:pDialogWidthProductos,
			buttons: {
				"Aceptar": function() {
					modificarProductos();
				},
				"Cancelar":function(){
					fnHideErrores(sIdDialogModificarProductos);
					$(this).dialog('close');
				}
			},
			open: function(event, ui) { 
				fnHideErrores(sIdDialogModificarProductos);

			}
		});

		/* Configuracion del data table de productos*/
		dtProductos = $('#tbProductos').dataTable({
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
						"sTitle" : "47. Principales productos elaborados o servicios prestados:",
						"mDataProp" : "desProducto",
						"sClass":"dtJustifyClassColumn"
					}
				],

					"bProcessing" : true,
					"sAjaxSource" : 'productos/paginar',
					"fnServerData" : function(sSource, aoData, fnCallback) {
						aoData.push({
							"name" : "sSearch",
							"value" : ''
						});
						
						var wrapper = new Object();
						wrapper.aoData = aoData;
						var oForm = $(sIdNameFormPaginarProductos).serializeObject(true);
						wrapper.oForm = oForm;
						
						$.postJSON(sSource, wrapper, function(data) {
							fnCallback(data);
						});
						
					}
				});

		/* Add a click handler to the rows - this could be used as a callback */
		$("#tbProductos tbody").click(function(event) {
			
			$(dtProductos.fnSettings().aoData).each(function (){ 
				$(this.nTr).removeClass('row_selected'); 
			});
			
			$(event.target.parentNode).addClass('row_selected');
		});

		
		
		
		
	});
	
	
	
	/*Funcion para agregar el elemento nuevo de productos*/
	var fnOpenDialogNuevoProductos = function(){
		oDialogAgregarProductos.dialog('open');
	}
	
	/*Funcion para abrir el dialogo de eliminar*/
	var fnOpenDialogEliminarProductos = function(){
		//Validamos que exista un elemento seleccionado.
		if(fnValidaRegistroSeleccionado(dtProductos)){
			oDialogEliminarProductos.dialog('open');
		}else{
			//Mostramos mensaje de error
			fnDialogErrorSinSeleccionProductos();
		}
		
		
		
	}
	

	

	
	
	/*FUncion para mostrar el mensaje de error cuando no existe un registro seleccionado*/
	var fnDialogErrorSinSeleccionProductos = function(){
		oDialogErrorSinSeleccionProductos.dialog('open');
	}
	
	/*Funcion para obtener el elemento seleccionado*/
	var fnGetElementoProductos = function(idProductoServicio){

		var sSource = 'productos/get';
		
		$.getJSON(sSource,{ idProductoServicio: idProductoServicio } , function(data) {
			
			$('#productoFormModificar:hidden #cveIdSolicitud').val( data.cveIdSolicitud);
			 $('#productoFormModificar  #desProducto').val( data.desProducto);
			 $('#productoFormModificar:hidden #cveIdProducto').val( data.cveIdProducto);
			 
			 /*Abrimos el dialogo*/
			 oDialogModificarProductos.dialog('open');
			 
		});
		
		
	}
	
	
	/*Funcion para abrir el dialogo de modificar*/
	
	
	var fnOpenDialogModificarProductos = function(){
		
		//Validamos que exista un elemento seleccionado.
		if(fnValidaRegistroSeleccionado( dtProductos )){
			/*Obtenemos el radio seleccionado*/
			var obRowSelected = fnGetRowSelected(dtProductos);
			var idProductoServicio = obRowSelected.cveIdProducto;
			
			fnGetElementoProductos(idProductoServicio);
			
		}else{
			//Mostramos mensaje de error
			fnDialogErrorSinSeleccionProductos();
		}
		
		
		

	}

/*
 * funciones para navegacion
 */
	function fnProductosGoBack(){
		//$("#form-back").submit();
		alert('Not implemented yet.');
	}
	
	function fnProductosGoAhead(){
		$("#form-productos-forward").submit();
	}
	
	
	
	function agregarProductos( ){
		
		
		fnHideErrores(sIdNameFormNuevoProductos);
		
		/*la invocacion a guardar un nuevo elemento*/
		var idSolicitud = $('#productoFormPaginar:hidden #cveIdSolicitud').val();
		$('#productoFormNuevo:hidden #cveIdSolicitud').val(idSolicitud);
		var oForm = $(sIdNameFormNuevoProductos).serializeObject(true);
		var sSource = 'productos/agregar';
		
		$.postJSON(sSource, oForm, function(data) {
			
			fnHideErrores(sIdNameFormNuevoProductos);
			//refrescar el data table
			dtProductos.fnDraw();
			//cerramos el dialogo
			oDialogAgregarProductos.dialog( "close" );
			
		}).error(function(data) {
			fnProcesarErrores(data, sIdNameFormNuevoProductos);
		});
		
	}
	
	
	
	function modificarProductos(){
		
		fnHideErrores(sIdFormModificarProductos);
		
		/*la invocacion a modificar el elemento*/
		var oForm = $(sIdFormModificarProductos).serializeObject(true);
		var sSource = 'productos/modificar';
		
		$.postJSON(sSource, oForm, function(data) {			
			//refrescar el data table
			dtProductos.fnDraw();
                        
                        fnHideErrores(sIdDialogModificarProductos);
                        
			$( oDialogModificarProductos ).dialog( "close" );
		}).error(function(data) {
			fnProcesarErrores(data, sIdDialogModificarProductos);
		});
	}
