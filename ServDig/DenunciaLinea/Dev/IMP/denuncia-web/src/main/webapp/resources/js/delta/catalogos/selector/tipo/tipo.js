/**
 * JS para el soporte del catalogo de clase.
 */


var idDataTable 	= "#dttipo";
var dgtipoGuardar = "#dgtipoGuardar";
	
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
				var retVal = '<input type="radio" value="' + oObj.aData['idTipo'] + '" id="radioTable" class="radioDeteccion" name="radio"/> ';
				return retVal;
			}, 
			aTargets: [0]
			},{
				"sTitle" : "Secci&oacute;n",
				"mDataProp" : "cgcCatflujo.descFlujo",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Descripci&oacute;n",
				"mDataProp" : "descripcion",
				"sClass": "dtCenterClassColumn"
			}
			],"bProcessing" : true,
			"sAjaxSource" : 'tipo/paginar.do',
			"fnServerData" : function(sSource, aoData, fnCallback) {				
				
				var wrapper = new Object();
				wrapper.aoData = aoData;								
		
				var oForm = $("#formTipoCbo").toObject({mode:'first'});
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
					var flujo = $('form#guardarForm select#cgcCatflujo\\.idFlujo').val();
					
					var desc = $("#cboSeccionDescGuardar").val();
					var idTipo = $("#idTipo").val();
					
					if(idTipo != ''){
						var variable = '{' +
						   '"idTipo":"'+idTipo+'",'+
						   '"cgcCatflujo" : {"idFlujo":"'+flujo+'"},'+
						   '"descripcion":"'+desc+'"}';	
					
					}else{
						var variable = '{' +
						   '"cgcCatflujo" : {"idFlujo":"'+flujo+'"},'+
						   '"descripcion":"'+desc+'"}';	
					}
					var variableJson = jQuery.parseJSON(variable);
					 bloquear();
					 $.postJSON("tipo/guardar.do", variableJson, function(data) {
						 
						 oDgRegistro.dialog('close');
						 $("#idTipo").val("");
						 oDtTipo.fnDraw()
						 alert("El registro se guardo satisfactoriamente");
						 
					 }).error(function(data){ 
							alert("error" + data);
						}).complete(function(){
							desbloquear();//Instrucciones para el 'complete'
						});
					
				}, 
				"Cancelar": function() { 
					$("#idTipo").val("");
					$("#cboSeccionDescGuardar").val("");
					oDgRegistro.dialog('close');
				} 
			}
		});
	

});

	function buscar(){
		
		oDtTipo.fnDraw();
	}
	
	function modificar(){
		
		var idTipo = $('#:checked').val();
			if(idTipo != undefined){
				
				
				var variable = '{' +
				   '"idTipo":"'+idTipo+'"}';					
				var variableJson = jQuery.parseJSON(variable);
				
				 bloquear();
				 $.postJSON("tipo/buscar.do", variableJson, function(data) {
					 
					 $("#idTipo").val(data.idTipo);
					 $("#cboSeccionDescGuardar").val(data.descripcion);
					 alert('para el select : ' + data.cgcCatflujo.idFlujo);
					
					 
					 
					 

	
						oDgRegistro.dialog('open');
					 
				 }).error(function(data){ 
						alert("error" + data);
					}).complete(function(){
						desbloquear();//Instrucciones para el 'complete'
					});
				
				
			
				
			
				
			}else alert("Seleccione un elemento de la lista");
		
	}
	
	function agregar(){
		
		$("#idTipo").val("");
		$("#cboSeccionDescGuardar").val("");
		oDgRegistro.dialog('open');
	}
	
	function jsvalidarAlfaNumerico(e) { 
			
		    tecla = (document.all) ? e.keyCode : e.which;
		    if (tecla==8) return true;
		    patron = /[1234567890abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ ]/;
		    te = String.fromCharCode(tecla);
		    
		    return patron.test(te);
		} 
	
	
	
