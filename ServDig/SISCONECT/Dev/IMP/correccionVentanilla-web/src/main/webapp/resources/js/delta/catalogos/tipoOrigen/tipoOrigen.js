/**
 * JS para el soporte del catalogo de clase.
 */


var idDataTable 	= "#dttipoOrigen";
var dgtipoGuardar = "#dgtipoOrigenGuardar";
var dgtipoEliminar = "#dgTipoOrigenBorrar";
	
// Objeto del DataTable
var oDtTipo;
//Dialogos
var oDgRegistro;
var oDgRegistroEliminar;



/**
 * Iniciamos la configuracion de los componentes visuales de jQuery.
 */
$(document).ready(function() {
	

	/**
	 * Inicializacion del data table
	 */
		oDtTipo = $(idDataTable).dataTable({
		bJQueryUI : true,
		bFilter : false,
		bInfo:true,
		bSort: false,
		"bPaginate": true,
		"bAutoWidth" : true,
		"bServerSide" :	true,
		"aoColumns" : [ {
			fnRender :function(oObj){
				var retVal = '<input type="radio" value="' + oObj.aData['idTipoorigen'] + '" id="radioTable" class="radioDeteccion" name="radio"/> ';
				return retVal;
			}, 
			aTargets: [0]
			},{
				"sTitle" : "Tipo",
				"mDataProp" : "cgcCatTipo.descripcion",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Origen",
				"mDataProp" : "cgcCatOrigen.descOrigen",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Descripcion",
				"mDataProp" : "descripcion",
				"sClass": "dtCenterClassColumn"
			}
			],"bProcessing" : true,
			"sAjaxSource" : 'tipoOrigen/paginar.do',
			"fnServerData" : function(sSource, aoData, fnCallback) {				
				
				var wrapper = new Object();
				wrapper.aoData = aoData;								
		
				var descripcion = $("#descTipoOrigen").val();
				var tipo = $("#tipo\\.idTipo").val();
				var origen = $("#origen\\.idOrigen").val();
				
				var variable = '{' +
				   '"descripcion":"'+descripcion+'",'+
				   '"cgcCatTipo" : {"idTipo":"'+tipo+'"},'+
				   '"cgcCatOrigen" : {"idOrigen":"'+origen+'"}'+
				   '}';					
				var oForm = jQuery.parseJSON(variable);
				
				wrapper.oForm = oForm;
				bloquear();
				$.postJSON(sSource, wrapper, function(data) {										
					fnCallback(data);	
				desbloquear();	
				 }).error(function(datas){ 
						validarSesionExpirada(datas);				
				});
			}
		});
		
		// Dialog de Elemento Nuevo			
		 oDgRegistro = $(dgtipoGuardar).dialog({
			autoOpen: false,
			modal:true,
			resizable:false,
			width: 930,
			beforeClose :function(event,ui){
			   
			},
			buttons: {
			
				"Guardar": function() {
					var descripcion = $("#descTipoOrigenGuardar").val();
					var tipo = $("form#guardarForm #selectTipo").val();
					var origen = $("form#guardarForm #selectOrigen").val();
					var idTipoOrigen = $("#guardarForm,#idTipoorigen").val();
					
					if(idTipoOrigen != ''){
						var variable = '{' +
						   '"descripcion":"'+descripcion+'",'+
						   '"idTipoorigen":"'+idTipoOrigen+'",'+
						   '"idTipo":"'+tipo+'",'+
						   '"idOrigen":"'+origen+'"'+
						   '}';		
					}else{
						var variable = '{' +
						   '"descripcion":"'+descripcion+'",'+
						   '"idTipo":"'+tipo+'",'+
						   '"idOrigen":"'+origen+'"'+
						   '}';		
					}
							
					
					var variableJson = jQuery.parseJSON(variable);
					 bloquear();
					 $.postJSON("tipoOrigen/guardar.do", variableJson, function(data) {
						 
						 oDgRegistro.dialog('close');
						 $("#idTipoorigen").val("");
						 oDtTipo.fnDraw()
						 alert("El registro se guardo satisfactoriamente");
						 
					 }).error(function(data){ 
							alert("error" + data);
						}).complete(function(){
							desbloquear();//Instrucciones para el 'complete'
						});
				}, 
				"Cancelar": function() {
					
					$("#descTipoOrigenGuardar").val("");
					$("form#guardarForm #selectTipo").val("");
					$("form#guardarForm #selectOrigen").val("");
					$("#guardarForm,#idTipoorigen").val("");
					oDgRegistro.dialog('close');
				} 
			}
		});
		 
			// Dialog de Eliminar			
		 oDgRegistroEliminar = $(dgtipoEliminar).dialog({
			autoOpen: false,
			modal:true,
			resizable:false,
			width: 930,
			beforeClose :function(event,ui){
			   
			},
			buttons: {
			
				"Confirmar": function() {
					var idTipoorigen = $('#:checked').val();
					var variable = '{' +
					   '"idTipoorigen":"'+idTipoorigen+'"}';					
					var variableJson = jQuery.parseJSON(variable);
					
					 bloquear();
					 $.postJSON("tipoOrigen/eliminar.do", variableJson, function(data) {
						 
						 alert('Se elimino el registro satisfactoriamente');
						 oDgRegistroEliminar.dialog('close');
						 oDtTipo.fnDraw();
						 
					 }).error(function(data){ 
							alert("error" + data);
						}).complete(function(){
							desbloquear();//Instrucciones para el 'complete'
						});
					
					
				}, 
				"Cancelar": function() {
					
					$("#formBorrar,#idTipoorigen").val("");
					oDgRegistroEliminar.dialog('close');
				} 
			}
		});
	

});

	function buscar(){
		resetDisplayStart(oDtTipo);
		oDtTipo.fnDraw();
	}
	
	function modificar(){
		
		var idTipoorigen = $('#:checked').val();
			if(idTipoorigen != undefined){
				
				
				var variable = '{' +
				   '"idTipoorigen":"'+idTipoorigen+'"}';					
				var variableJson = jQuery.parseJSON(variable);
				
				 bloquear();
				 $.postJSON("tipoOrigen/buscar.do", variableJson, function(data) {
					 
					 
					 var tipo = data.cgcCatTipo.idTipo;
					 var origen = data.cgcCatOrigen.idOrigen;
					 $("#guardarForm,#idTipoorigen").val(idTipoorigen);
					 $("#descTipoOrigenGuardar").val(data.descripcion);
					 jsLlenaCboTipo(tipo);
					 jsLlenaCboOrigen(origen);
					
	
						oDgRegistro.dialog('open');
					 
				 }).error(function(data){ 
						alert("error" + data);
					}).complete(function(){
						desbloquear();//Instrucciones para el 'complete'
					});
				
				
			
				
			
				
			}else alert("Seleccione un elemento de la lista");
		
	}
	
	
	function eliminar(){
		
		var idTipoorigen = $('#:checked').val();
			if(idTipoorigen != undefined){
				
				oDgRegistroEliminar.dialog('open');
				
			}else alert("Seleccione un elemento de la lista");
		
	}
	
	function agregar(){
		
		jsLlenaCboTipo(null);
		 jsLlenaCboOrigen(null);
		$("#descTipoOrigenGuardar").val("");
		$("form#guardarForm #selectTipo").val("");
		$("form#guardarForm #selectOrigen").val("");
		$("#guardarForm,#idTipoorigen").val("");
		oDgRegistro.dialog('open');
	}
	
	function jsLlenaCboTipo(tipo){
		
		
		var oForm = $("#guardarForm").toObject({mode : 'first'});
		$.postJSON("tipoOrigen/cboTipo.do", oForm,  function(data) {
			
			var myselect=document.getElementById("selectTipo");
			myselect.options.length = 1;
			
			for(var i = 0 ; i < data.length ; i++){
				
				myselect.add(new Option(data[i][1], data[i][0]));
			}
				
			if(tipo != '' || tipo != null)
				$("form#guardarForm #selectTipo").val(tipo);
			
		}).error(function(data){ 
				alert("error" + data);
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
	}
	
	function jsLlenaCboOrigen(origen){
		
		var oForm = $("#guardarForm").toObject({mode : 'first'});
		$.postJSON("tipoOrigen/cboOrigen.do", oForm, function(data) {
			
			var myselect=document.getElementById("selectOrigen");
			myselect.options.length = 1;
			
			for(var i = 0 ; i < data.length ; i++){
				myselect.add(new Option(data[i][1], data[i][0]));
			}
			
			if(origen != '' || origen != null)
				$("form#guardarForm #selectOrigen").val(origen);
			
		}).error(function(data){ 
				alert("error" + data);
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
	}
	
	
