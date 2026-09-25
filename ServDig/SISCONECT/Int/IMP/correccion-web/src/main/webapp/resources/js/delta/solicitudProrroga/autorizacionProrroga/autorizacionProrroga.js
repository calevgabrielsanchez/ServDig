/**
 * JS para el soporte del modulo AUTORIZACION DE PRORROGA
 */
var tableResult     = "#tableAutorizacionProrrogaData";
var idRegistro	= "#dgAutorizacionProrrogaGuardar";
var oDtTableResult;
var arreglo = '';
//Dialogos
var oDgRegistro;

/**
 * Estatus para solicitud autorizada
 */
var STATUS_AUTORIZADA=2;
/**
 * Estatus para solicitud rechazada
 */
var STATUS_RECHAZADA=3;
/**
 * Estatus para aplicar a la solicitud de prorroga 
 * basado en STATUS_AUTORIZADA o STATUS_RECHAZADA 
 */
var STATUS_PRORROGA;

	$(document).ready(function() {
	
	// Fecha de Inicio y Termino
	$( "#fecInicio, #fecFinal" ).datepicker({ dateFormat: 'dd-mm-yy' });
	$( "#fecInicio, #fecFinal" ).datepicker('option', 'beforeShowDay', null);
	$( "#fecInicio, #fecFinal" ).datepicker('option', 'maxDate', getFechaServidor());
	$( "#fecInicio, #fecFinal" ).datepicker('option', 'minDate', getFechaServidorMenos45Dias());
	
		oDtTableResult = $(tableResult).dataTable(
				{
					bJQueryUI : true,
					bFilter : false,
					bInfo : true,
					bSort : false,
					
					"bPaginate" : true,
					"bAutoWidth" : true,
					"bServerSide" : true,
					"iDeferLoading": 0,
					"aoColumns" : [
					        {  fnRender :function(oObj){
					         	   var retValPro = '<input type="radio" value="' + oObj.aData['cveSolprorroga'] +'" id="radioAutProrroga" name="radioAutProrroga" class="radioClase" onclick="$(&quot;#dgAutorizacionProrrogaButtons&quot;).show()"/> ';
					           	   return retValPro;
					        } },
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
								"sTitle" : "Fecha L&iacute;mite",
								"mDataProp" : "fecLimite",
								"sClass" : "dtCenterClassColumn"
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
						 }).error(function(datas){ 
								validarSesionExpirada(datas);				 
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
		$("#dgAutorizacionProrrogaButtons").hide();
		if(validaGuardar()){
			var fecInicio = $("#fecInicio").val();
			var fecFinal  = $("#fecFinal").val();
			if(jsValidaFechas(fecInicio,fecFinal)){
				resetDisplayStart(oDtTableResult);
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
		var obj = '"' + $("input[name='radioAutProrroga']:checked").val()+ "/" + STATUS_PRORROGA + "$" + '"';
		var variable = jQuery.parseJSON(obj);
		$.postJSON("autorizacionProrroga/guardar.do",variable,function(data) {
			
				oDtTableResult.fnDraw();
				arreglo = '';
				STATUS_PRORROGA='';						
				alert('Operaci\u00f3n Exitosa');
		});		
	}

	function jsConfirmar(param_estatus){
		var radioProrroga=$("input[name='radioAutProrroga']:checked").val();
		
		if(radioProrroga==null || radioProrroga==""){
			alert('Seleccione una solicitud de pr\u00f3rroga');
		}else{			
			STATUS_PRORROGA=param_estatus;
			oDgRegistro.dialog('open');
		}
	}
	