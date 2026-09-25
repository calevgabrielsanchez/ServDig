/**
 * JS para el soporte del catalogo de clase.
 */


var idDataTable 	= "#dtcriterioSeleccion";
var dgtipoGuardar = "#dgcriterioSeleccionGuardar";

	
// Objeto del DataTable
var oDtTipo;
//Dialogos
var oDgRegistro;




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
		bSort: true,
		"bPaginate": true,
		"bAutoWidth" : true,
		"bServerSide" :	true,
		"aoColumns" : [ {
			fnRender :function(oObj){
				var retVal = '<input type="radio" value="' + oObj.aData['idCriterioseleccion'] + '" id="radioTable" class="radioDeteccion" name="radio"/> ';
				return retVal;
			}, 
			aTargets: [0]
			},{
				"sTitle" : "Tipo",
				"mDataProp" : "descTipo",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Origen",
				"mDataProp" : "descOrigen",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Descripcion",
				"mDataProp" : "descCriterioseleccion",
				"sClass": "dtCenterClassColumn"
			}
			],"bProcessing" : true,
			"sAjaxSource" : 'criterioSeleccion/paginar.do',
			"fnServerData" : function(sSource, aoData, fnCallback) {				
				
				var wrapper = new Object();
				wrapper.aoData = aoData;								
		
				var descripcion = $("#descCriterioseleccion").val();
				var tipo = $("#tipo\\.idTipo").val();
				var origen = $("#origen\\.idOrigen").val();
				
				var variable = '{' +
				   '"idTipo":"'+tipo+'",'+
				   '"idOrigen":"'+origen+'"' + '}';					
				var oForm = jQuery.parseJSON(variable);
				
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
					var descripcion = $("#descCriterioseleccionGuardar").val();
					var tipo = $("form#guardarForm #selectTipo").val();
					var origen = $("form#guardarForm #selectOrigen").val();
					
					
						var variable = '{' +
						   '"descCriterioseleccion":"'+descripcion+'",'+
						   '"idTipo":"'+tipo+'",'+
						   '"idOrigen":"'+origen+'"'+
						   '}';		
							
					
					var variableJson = jQuery.parseJSON(variable);
					 bloquear();
					 $.postJSON("criterioSeleccion/guardar.do", variableJson, function(data) {
						 
						 oDgRegistro.dialog('close');
						 oDtTipo.fnDraw()
						 alert("El registro se guardo satisfactoriamente");
						 
					 }).error(function(data){ 
							alert("error" + data);
						}).complete(function(){
							desbloquear();//Instrucciones para el 'complete'
						});
				}, 
				"Cancelar": function() {
					
					$("#descCriterioseleccionGuardar").val("");
					$("form#guardarForm #selectTipo").val("");
					$("form#guardarForm #selectOrigen").val("");
					oDgRegistro.dialog('close');
				} 
			}
		});
	

});

	function buscar(){
		
		oDtTipo.fnDraw();
	}
	
	function modificar(){
		
		var idTipoorigen = $('#:checked').val();
			if(idTipoorigen != undefined){
				
				
				var variable = '{' +
				   '"idTipoorigen":"'+idTipoorigen+'"}';					
				var variableJson = jQuery.parseJSON(variable);
				
				 bloquear();
				 $.postJSON("criterioSeleccion/buscar.do", variableJson, function(data) {
					 
					 
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
		 $("#descCriterioseleccionGuardar").val("");
		$("form#guardarForm #selectTipo").val("");
		$("form#guardarForm #selectOrigen").val("");
		oDgRegistro.dialog('open');
	}
	
	function jsLlenaCboTipo(tipo){
		
		
		var oForm = $("#guardarForm").toObject({mode : 'first'});
		$.postJSON("criterioSeleccion/cboTipo.do", oForm,  function(data) {
			
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
		$.postJSON("criterioSeleccion/cboOrigen.do", oForm, function(data) {
			
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
	
function jsvalidarAlfaNumerico(e) { 
		
	    tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true;
	    patron = /[1234567890abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ ]/;
	    te = String.fromCharCode(tecla);
	    
	    return patron.test(te);
	}
	
	
