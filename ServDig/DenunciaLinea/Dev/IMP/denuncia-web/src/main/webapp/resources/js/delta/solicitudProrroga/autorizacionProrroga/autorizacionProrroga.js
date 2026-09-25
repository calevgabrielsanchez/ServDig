/**
 * JS para el soporte del modulo AUTORIZACION DE PRORROGA
 */
var tableResult     = "#tableAutorizacionProrrogaData";
var idRegistro	= "#dgAutorizacionProrrogaGuardar";
var oDtTableResult;
var arreglo = '';
//Dialogos
var oDgRegistro;


	$(document).ready(function() {
	
	// Fecha de Inicio y Termino
	 $( "#fecInicio, #fecFinal" ).datepicker( { dateFormat: 'dd-mm-yy' });
	
		oDtTableResult = $(tableResult).dataTable(
				{
					bJQueryUI : true,
					bFilter : false,
					bInfo : true,
					bSort : false,
					
					"bPaginate" : true,
					"bAutoWidth" : true,
					"bServerSide" : true,
					"aoColumns" : [
							{
								"sTitle" : "Folio",
								"mDataProp" : "nuFolio",
								"sClass" : "dtCenterClassColumn"
							},
							{
								"sTitle" : "Raz&oacute;n Social",
								"mDataProp" : "razonSocial",
								"sClass" : "dtCenterClassColumn"
							},
							{
								"sTitle" : "Registro Patronal",
								"mDataProp" : "regPatronal",
								"sClass" : "dtCenterClassColumn"
							},
							{
								"sTitle" : "Tipo Correcci&oacute;n",
								"mDataProp" : "tipoCorreccion",
								"sClass" : "dtCenterClassColumn"
							},{
								"sTitle" : "Periodo Inicial",
								"mDataProp" : "fecPeriodoIni",
								"sClass" : "dtCenterClassColumn"
							},
							{
								"sTitle" : "Periodo Final",
								"mDataProp" : "fecPeriodoFin",
								"sClass" : "dtCenterClassColumn"
							},
							{
								"sTitle" : "Fecha Limite",
								"mDataProp" : "fecLimite",
								"sClass" : "dtCenterClassColumn"
							},
							{
								fnRender : function(oObj) {
								
									var retVal = '<select id="comb'+ oObj.aData['cveSolprorroga'] +'" onchange="jsCambiaStatus(' + oObj.aData['cveSolprorroga'] + ');"><option id="-1">Seleccione...</option> ';
									if(oObj.aData['lstStatus'].length > 0){
										for(var i = 0; i < oObj.aData['lstStatus'].length; i++){
											retVal += '<option id="' + oObj.aData['lstStatus'][i].cveStatus + '">' + oObj.aData['lstStatus'][i].txDescripcion +'</option>'
										}
										retVal += '</select>';
									}
									return retVal;
								}
							}
							 ],
					"bProcessing" : true,
					"sAjaxSource" : 'autorizacionProrroga/paginar.do',
					"fnServerData" : function(sSource,aoData,fnCallback) {

						
						var wrapper = new Object();
						wrapper.aoData = aoData;

						var oForm = $("#crtProrrogaForm").toObject({mode : 'first'});
						wrapper.oForm = oForm;

						$.postJSON(sSource,wrapper,function(data) { fnCallback(data);
						
						$("#tableAutorizacionProrrogaData").show();
						});
					}
				});
		
		// Dialog de Confirmar			
		oDgRegistro = $(idRegistro).dialog({
			autoOpen: false,
			modal:true,
			resizable:false,
			buttons: {
				"Aceptar": function() {
					jsGuardar();
					$(this).dialog("close");
				}, 
				"Cancelar": function() { 
					$(this).dialog("close"); 
				} 
			}
		});	
		
	});

	function buscar(){
		
		$("#labelFecInicio").html('');
		$("#labelFecFinal").html('');
		if(validaGuardar()){
			var fecInicio = $("#fecInicio").val();
			var fecFinal  = $("#fecFinal").val();
			if(jsValidaFechas(fecInicio,fecFinal)){
				oDtTableResult.fnDraw();				
					
			}else{
				$("#labelFecInicio").html('<label style="color: red;">La fecha inicial no puede ser mayor a la fecha final</label>');
			}
		}
		
		
		
		
	}
	
	function validaGuardar(){
		var result = true;
		
		if( $("#fecInicio").val() == ''){
		
			$("#labelFecInicio").html('<label style="color: red;">Campo requerido</label>');
			result = false;
		}
		if(  $("#fecFinal").val() == ''){
			
			$("#labelFecFinal").html('<label style="color: red;">Campo requerido</label>');
			result = false;
		}
		
		return result;
	}
	
	function jsValidaFecFinal(fecFinal){
		
		if(jsValidaFecha(fecFinal)){
			 $("#fecFinal").val(fecFinal);
			 $("#labelFecFinal").html('');
			 var fecIni = $("#fecInicio").val();
			 if(fecIni != '' && fecFinal != ''){
				 if(jsValidaFechas(fecIni,fecFinal)){
					 $("#fecFinal").val(fecFinal);
					 $("#labelFecFinal").html('');
				 }else{
					 $("#fecFinal").val('');
					 $("#labelFecFinal").html('<label style="color: red;">La fecha final no puede ser menor a la fecha inicial</label>');
				 }
			 }
		}else{
			 $("#fecFinal").val("");
			 $("#labelFecFinal").html('<label style="color: red;">La fecha final no puede ser mayor al dia actual</label>');
		}
	}
	
	function jsValidaFecIni(fecIni){
		
		if(jsValidaFecha(fecIni)){
			 $("#fecInicio").val(fecIni);
			 $("#labelFecInicio").html('');
		}else{
			 $("#fecInicio").val("");
			 $("#labelFecInicio").html('<label style="color: red;">La fecha inicial no puede ser mayor al dia actual</label>');
		}
	}
	
	
	function jsValidaFechas(fecIni, fecFin){
		
		var array_fechaIni = fecIni.split("-"); 
		var array_fechaFin = fecFin.split("-"); 
		
		var anioIni = parseInt(array_fechaIni[2],10);
		var anioFin = parseInt(array_fechaFin[2],10);
		
		var mesIni = parseInt(array_fechaIni[1],10);
		var mesFin = parseInt(array_fechaFin[1],10);
		
		var diaIni = parseInt(array_fechaIni[0],10);
		var diaFin = parseInt(array_fechaFin[0],10);
		
		
		if(anioIni > anioFin){
			return false;
		}else {
			if(anioFin == anioIni){
				if(mesIni > mesFin){
					return false;
				}else{
					if(mesIni == mesFin){
						
						if(diaIni > diaFin){
							
							return false;
						}else{
							if(diaIni <= diaFin){
								
								return true;
							}
						}
					}else{
						if(mesIni < mesFin){
							return true;
						}
				  }
				}
			}else{
				if(anioIni < anioFin){
					return true;
				}
			}
		}
		
	}
	
	function jsValidaFecha(fecha){
		
		var resp = true;
		var fechaSis = new Date();
		var diaS = fechaSis.getDate();
		var mesS = fechaSis.getMonth() + 1;
		var anioS = fechaSis.getFullYear();
		var diaP = fecha.substring(0,2);
		var mesP = fecha.substring(3,5);
		var anioP = fecha.substring(6,10);
		
		
		if(anioP > anioS){
			resp = false;
		}else {
			if(anioP == anioS){
				if(mesP > mesS){
					resp = false;
				}else{
					if(mesP == mesS){
						if(diaP > diaS){
							resp = false;
						}else{
							if(diaP <= diaS){
								resp = true;
							}
						}
					}else if(mesP < mesS){
						resp = true;
					}
				}
			}else{
				if(anioP < anioS){
					resp = true;
				}
			}
		}
		
		return resp;
		
		
	}

	function jsCambiaStatus(id){
		var selectCon = $("#comb"+id).val();
		if(selectCon != '' && selectCon != 'Seleccione...'){
			arreglo += id + "/" + selectCon + "$";
		}
	}
	
	function jsGuardar(){
		
		var obj = '"' + arreglo + '"';
		var variable = jQuery.parseJSON(obj);
		$.postJSON("autorizacionProrroga/guardar.do",variable,function(data) {
			
				oDtTableResult.fnDraw();
				arreglo = '';
				alert('Operaci\u00f3n Exitosa');			
		});
		
	}
	
	function jsConfirmar(){
		if(arreglo == ''){
			alert('No se han realizado cambios');
		}else{
			oDgRegistro.dialog('open');
		}
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	