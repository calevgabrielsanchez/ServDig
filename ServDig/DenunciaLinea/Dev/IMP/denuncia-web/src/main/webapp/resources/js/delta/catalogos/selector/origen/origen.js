/**
 * JS para el soporte del catalogo de clase.
 */


var idDataTable 	= "#dtorigen";
var dgtipoGuardar = "#dgorigenGuardar";
	
// Objeto del DataTable
var oDtTipo;
//Dialogos
var oDgRegistro;



/**
 * Iniciamos la configuracion de los componentes visuales de jQuery.
 */
$(document).ready(function() {
	$("#labelTipo").html('');

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
				var retVal = '<input type="radio" value="' + oObj.aData['idOrigen'] + '" id="radioTable" class="radioDeteccion" name="radio"/> ';
				return retVal;
			}, 
			aTargets: [0]
			},{
				"sTitle" : "Descripci&oacute;n",
				"mDataProp" : "descOrigen",
				"sClass": "dtCenterClassColumn"
			}
			],"bProcessing" : true,
			"sAjaxSource" : 'origen/paginar.do',
			"fnServerData" : function(sSource, aoData, fnCallback) {				
				
				var wrapper = new Object();
				wrapper.aoData = aoData;								
		
				var oForm = $("#formOrigen").toObject({mode:'first'});
				wrapper.oForm = oForm;
				bloquear();
				$.postJSON(sSource, wrapper, function(data) {										
					fnCallback(data);	
				desbloquear();	
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
					var desc = $("#descOrigenGuardar").val();
					var idOrigen = $("#idOrigen").val();
					
					if(idOrigen != ''){
						var variable = '{' +
						   '"idOrigen":"'+idOrigen+'",'+
						   '"descOrigen":"'+desc+'"}';	
					
					}else{
						var variable = '{' +
						   '"descOrigen":"'+desc+'"}';	
					}
					var variableJson = jQuery.parseJSON(variable);
					 bloquear();
					 $.postJSON("origen/guardar.do", variableJson, function(data) {
						 
						 oDgRegistro.dialog('close');
						 $("#idOrigen").val("");
						 $("#descOrigenGuardar").val("");
						 oDtTipo.fnDraw()
						 alert("El registro se guardo satisfactoriamente");
						 
					 }).error(function(data){ 
							alert("error" + data);
						}).complete(function(){
							desbloquear();//Instrucciones para el 'complete'
						});
					
				}, 
				"Cancelar": function() { 
					 $("#idOrigen").val("");
					 $("#descOrigenGuardar").val("");
					oDgRegistro.dialog('close');
				} 
			}
		});
	

});

	function buscar(){
		
		oDtTipo.fnDraw();
	}
	
	function modificar(){
		
		var idOrigen = $('#:checked').val();
			if(idOrigen != undefined){
				
				
				var variable = '{' +
				   '"idOrigen":"'+idOrigen+'"}';					
				var variableJson = jQuery.parseJSON(variable);
				
				 bloquear();
				 $.postJSON("origen/buscar.do", variableJson, function(data) {
					 
					 $("#idOrigen").val(data.idOrigen);
					 $("#descOrigenGuardar").val(data.descOrigen);
					 
						oDgRegistro.dialog('open');
					 
				 }).error(function(data){ 
						alert("error" + data);
					}).complete(function(){
						desbloquear();//Instrucciones para el 'complete'
					});
				
				
			
				
			
				
			}else alert("Seleccione un elemento de la lista");
		
	}
	
	function agregar(){
		
		$("#idOrigen").val("");
		$("#descOrigenGuardar").val("");
		oDgRegistro.dialog('open');
	}
	
	function jsvalidarAlfaNumerico(e) { 
		
	    tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true;
	    patron = /[1234567890abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ ]/;
	    te = String.fromCharCode(tecla);
	    
	    return patron.test(te);
	}
	
	
	
