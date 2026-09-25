
var idSeguimiento = "#dgSeguimientoPromocion";
var idConsultaRegistros		= "#dtConsultaRegistros";
var idDgInvitacion	= "#dgInvitacionGuardar";
var idDgConfirmar = "#dgConfirmarInvitacion";
var idDgRegulariza = "#dgRegularizaPromocion";
var idDgPromocionConsultaDet = "#dgPromocionConsultaDet";
var idDatosSatic			= "#dgPromocionDatosSaticB";
var idInvitacionAntecedente = "#dgInvitacionAntecedente"; //dialogo invitacion
var oDgInvitacionAntecedente;
var oDgSeguimeinto;
var oDgConsultaRegistros;
var oDgCancelacion;
var oDgInvitacion;
var oDgRgulariza;
var validaCaptura;
var oDgPromocionConsultaDet;
var jsContextoPromocion= getAppContextParaJS() + "/promocion/";
//var oDgDatosSatic;

var valdaDetAct;
var limpiarForm = true;
var oDgConfirmar;

var ADHERIDO_AL_PROGRAMA_CORRECCION_INVITACION = 30;
var REGISTRO_DATOS_GENERALES = 1;

var PROMOCION_SATIC_A = "3";
var PROMOCION_SATIC_B = "4";
var PROMOCION_COSTRUCCION = "5";
var PROMOCION_ORDINARIO = "6";
var PROMOCION_SBC = "7";
var jsFechaMaxSeguimiento = getFechaServidor();
var jsFechaMinSeguimiento = getFechaServidorMenos45Dias();

//Variables para los roles
var JEFE_OF_CORRECCION = 4;
var JEFE_OF_CORR_Y_DIC = 6;
var JEFE_DEP_AUD_PAT = 7;


$(document).ready(function(){

	$( "form#promocionConsultaForm #fechaIncial, form#promocionConsultaForm #fechaFinal, form#CrtRegulapagosdetForm #fecEmision,form#promocionFormConsultaDetAct #fechaAtencion,form#promocionFormConsultaDetAct #fechaNotificacion,form#promocionFormConsultaDetAct #fechaEstimIncio,form#promocionFormConsultaDetAct #fechaEstTerm" ).datepicker( { dateFormat: 'dd-mm-yy' });
	$( "#fecAtencion, #fecNotificacion" ).datepicker( { dateFormat: 'dd-mm-yy' });
	// Para el modal de INVITAVION kIk
	$( "#fecNotificacionOficio, #fecAtencionOficio, #fecEmision, #fecCancelacion, #fechaAvisoDictamen, form#promocionCancelacionForm #fecCancelacion " ).datepicker( { dateFormat: 'dd-mm-yy', beforeShowDay: $.datepicker.noWeekends });
	$( "#fechaInicio, #fechaFin" ).datepicker( { dateFormat: 'dd-mm-yy' });	
	
	$( "#fechaInicio, #fechaFin" ).datepicker('option', 'beforeShowDay',null );
	
	
	$( "#fechaInicio" ).datepicker('option', 'onSelect',function(){
		if($( "#fechaInicio").val()!="" && $( "#fechaFin").val()!="" && !comparaFechas($( "#fechaInicio").val(),$( "#fechaFin").val(),"-")){
			$( "#fechaInicio" ).val("");
			$( "#labelFecFin" ).html('<label class="etiquetaError">La fecha de Del no puede ser mayor a la fecha Al</label>');			
		}else{
			$( "#labelFecFin" ).html('');
		}
	});
	

	$( "#fechaFin" ).datepicker('option', 'onSelect',function(){
		if($( "#fechaInicio").val()!="" && $( "#fechaFin").val()!="" && !comparaFechas($( "#fechaInicio").val(),$( "#fechaFin").val(),"-")){
			$( "#fechaFin" ).val("");
			$( "#labelFecFin" ).html('<label class="etiquetaError">La fecha de Del no puede ser menor a la fecha Al</label>');
		}else{
			$( "#labelFecFin" ).html('');
		}
	});
	
	
	
	$( "form#invitacionAntecedenteForm #fechaIncialinv").datepicker( { dateFormat: 'dd-mm-yy' ,
		onSelect: function(dateText, inst) { 
			$("#labelFechaEmisionGuardar").text("")
		    }
	});
	$("form#invitacionAntecedenteForm #fechaEmisionFec").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onSelect: function() { 
			$("#labelFechaEmisionGuardar").text("")
			jsValidaFecEmisionInvitacion();
			jsValidaFecEmisionAl();
	    }
	});
	
	$("form#invitacionAntecedenteForm #fechaFinalInv").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onSelect: function() { 
			$("#labelFechaEmisionGuardar").text("")
			jsValidaFecEmisionAl();
	    }
	});

	
	$("#tdFolioDet").hide();
	$("#tdInputFolioDet").hide();
	$("#tdInputFolioDet2").hide();
	$("#btnGuardar").hide();
	var URL = getAppContextParaJS() + "/promocion/consulta/paginar.do";
	//Datatable de resultado de consulta de promociones
	oDgConsultaRegistros = $(idConsultaRegistros).dataTable({
		"bJQueryUI" : true,
		bFilter : false,
		bInfo:true,
		bSort: true,
		"bPaginate": true,
		"bAutoWidth" : false,
		"bServerSide" :	true,
		"iDeferLoading": 0,		
		"aoColumns" : [ {
			fnRender :function(oObj){
				var retVal = '<input type="radio" value="' +
				oObj.aData['cvePromocion'] +'" id="radioTable" class="radioDeteccion" name="radio" onclick="muestraDiv()"/> ';
				return retVal;
			}, 
			aTargets: [0]
		},{
			"sTitle" : "Folio de Promoci\u00F3n",
			"mDataProp" : "nuFoliopromocion",
			"sClass": "dtCenterClassColumn"
		}, {
			"sTitle" : "Elemento de Selecci\u00F3n",
			"mDataProp" : "descCriterioseleccion",
			"sClass": "dtCenterClassColumn"
		}, {
			"sTitle" : "Registro Patronal",
			"mDataProp" : "regPatron",
			"sClass": "dtCenterClassColumn"
		},{
			"sTitle" : "Raz\u00F3n Social",
			"mDataProp" : "razonSocial",
			"sClass": "dtCenterClassColumn"
		},{
			"sTitle" : "Numero de Oficio de la promoci\u00F3n",
			"mDataProp" : "nuOficiopro",
			"sClass": "dtCenterClassColumn"
		}, {
			"sTitle" : "Fecha del oficio de promoci\u00F3n",
			"mDataProp" : "fechaOficio",
			"sClass":"dtCenterClassColumn"
		}, {
			"sTitle" : "Asignado",
			"mDataProp" : "cveAuditorAsignado",
			"sClass":"dtCenterClassColumn",
			"fnRender":function(o,val){
				if(o.aData['cveAuditorAsignado']!='0'){
					return "SI";
				}else{
					return "NO";
				}
			}
		}
		],"bProcessing" : true,
		"sAjaxSource" : URL,
		"fnServerData" : function(sSource, aoData, fnCallback) {				

			var wrapper = new Object();
			wrapper.aoData = aoData;								

			var oForm = $("#promocionConsultaForm").toObject({mode:'first'});
			var fechaInicial = $("form#promocionConsultaForm #fechaIncial").val();
			var fechaFinal = $("form#promocionConsultaForm #fechaFinal").val();
			
			
			wrapper.oForm = oForm;

			$.postJSON(sSource, wrapper, function(data) {
				fnCallback(data);
				if(data.aaData!=null){					
				 	if(data.aaData.length<=0 && fechaInicial!="" && fechaFinal!=""){
				 		$('#labelError').html('<label style="color: red;">No se encontraron resultados, por favor realice nueva b&uacute;squeda</label>');
				 	}else{
				 		$('#labelError').html('');
				 	}
				 }				 
			}).complete(function(){
				desbloquear();
			 }).error(function(datas){ 
					validarSesionExpirada(datas);				 
			});
		}
	});

	
	oDgPromocionConsultaDet = $(idDgPromocionConsultaDet).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		beforeClose :function(event,ui){	
			if(limpiarForm){
				$("#divNumReporte").hide();
				$("#divDatosCenso").hide();
				$("#divActividad").hide();
				$("#divDatosObra").hide();
				$("#divCombosObra").hide();
				limpiarFormulario("#dgPromocionConsultaDetAct");
			}
		},
		buttons: {
			"Regularizar": function() {
				if(valdaDetAct.form()){
					var deteccion = $("#promocionFormConsultaDetAct").serializeObject(true);
					$.postJSON(jsContextoPromocion + "consulta/actualiza.do", deteccion, function(data) {
						limpiarForm = true;
						oDgPromocionConsultaDet.dialog('close');
						regularizacion('promocionFormConsultaDetAct');						
					}).error(function(data){ 
						desbloquear();
						validarSesionExpirada(data);
					}).complete(function(){
						//Instrucciones para el 'complete'
					});					
				}															
			}, 
			"Regresar": function() { 
				$(this).dialog("close");
				limpiarForm = true;				
			} 
		}
	});
	

	
	/*oDgDatosSatic = $(idDatosSatic).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		open:function(event, ui)
        {					
			var deteccion = $("#promocionFormDatosSaticB").serializeObject(true);
			$.postJSON("consulta/periodos.do", deteccion, function(datas) {
				  	 var options;
				  	 var options2;
					 if(datas!=null)
					   for (var i = 0; i < datas.length; i++) {
						   if(datas[i].substring(6,7)==0) 
							   options += "<option value='"+ datas[i].substring(0,6) +"'>"+ datas[i].substring(0,6) +"</option>";
						   else
							   options2 += "<option value='"+ datas[i].substring(0,6) +"'>"+ datas[i].substring(0,6) +"</option>";
				       }
					 $('select#periodosfaltantes').html(options);
					 $('select#periodosPresentados').html(options2);
					//Agrega las opciones al control
				}).error(function(datas){ 
					validarSesionExpirada(datas);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
        },
		buttons: {
			"Regularizar": function(){
				oDgDatosSatic.dialog('close');
				regularizacion('promocionFormDatosSaticB');
			},
			"Regresar": function() { 
				$(this).dialog("close"); 								
			} 
		}
	});*/
 
	
	
	/**
	 * Modal para Seguimeinto
	 * @author EDJ
	 * 06/03/2012
	 */
	oDgSeguimeinto = $(idSeguimiento).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		closeOnEscape: false,

		buttons: {
			"Salir": function() {				
				$('#tab_container_href').tabs('destroy').tabs();
				$(this).dialog("close"); 	
				oDgConsultaRegistros.fnDraw();		
				$("#buttons").hide();
			} 
		},
		beforeClose:function(e,u){
			$('#seguimientoTabForm,#autAviDictamenSegForm,#promocionCancelacionForm').each (function(){
				this.reset();		
			});
		}
	});
	

	
	valdaDetAct = $("#promocionFormConsultaDetAct").validate({
		 rules: {
			 fechaAtencion:{
				 required: true
			 },
			 fechaNotificacion:{
				 required: true
			 },
			 fechaDeteccion: {
				 required: true
			 },
			 nomRazonsocial: {
				 required: true
			 },
			 regPatron: {
				 required: true				 
			 },
			 fechaEstimIncio: {
				 required: true
			 }			 
		 }
	});
	
	oDgRgulariza = $(idDgRegulariza).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 720,
		beforeClose: function(event,ui){
			$("form#promocionRegularizaForm #fechaAtencionPro").prop('disabled','');
			$("form#promocionRegularizaForm #fechaPAI").prop('disabled','');
			$("form#promocionRegularizaForm #porRegularizado").prop('disabled','disabled');
			$("form#promocionRegularizaForm #porAvance").prop('disabled','disabled');
			$("form#promocionRegularizaForm #fecPerIni").prop('disabled','disabled');
			$("form#promocionRegularizaForm #fecPerFin").prop('disabled','disabled');
			$("form#promocionRegularizaForm #numTrabrevisados").prop('disabled','disabled');
			$("form#promocionRegularizaForm #numTrabomisos").prop('disabled','disabled');
			$("form#promocionRegularizaForm #numTrabsubdclara").prop('disabled','disabled');
		},
		open:function(event, ui)
		{
			$('form#promocionRegularizaForm #txCopSp').val("");
			$('form#promocionRegularizaForm #txCopAct').val("");
			$('form#promocionRegularizaForm #txCopRec').val("");
			$('form#promocionRegularizaForm #txCopTp').val("");
			$('form#promocionRegularizaForm #txRcvSp').val("");
			$('form#promocionRegularizaForm #txRcvAct').val("");
			$('form#promocionRegularizaForm #txRcvRec').val("");
			$('form#promocionRegularizaForm #txRcvTp').val("");			
			resumenPagos();
			if($("form#promocionRegularizaForm #tipoProm").val()=='EX'){
				$("#convenio").show();
				$("#parcialidades").show();
			}
		}
	});
	
	$("#btnValidar").click(function(){
		bloquear();
		var patron = $("form#invitacionFormRegistro #registroPatronal").val();
		$.postJSON(jsContextoPromocion + "consulta/validaPatron.do",patron,function(data) { 
			if(data == null){
				alert("El registro Patronal es invalido");
				$("form#invitacionFormRegistro #razonSocial").val('');
			}
			else if(data != null && data.razonSocial != null){
				alert("El registro Patronal es valido");
				$("form#invitacionFormRegistro #razonSocial").val(data.razonSocial);							
			}
			desbloquear();
		}).error(function(data){ 
			desbloquear();
			validarSesionExpirada(data);			
		}).complete(function(){						
			desbloquear();												
		});	

	});
	
	$("form#promocionFormConsultaDetAct #cvePkTipObra").change(function(){
		if($("form#promocionFormConsultaDetAct #cvePkTipObra").val()!=-1){
			$("form#promocionFormConsultaDetAct #cvePkFaseConst").prop('disabled','disabled');			
		}else{
			$("form#promocionFormConsultaDetAct #cvePkFaseConst").prop('disabled','');
		}
	});
	
	$("form#promocionFormConsultaDetAct #cvePkFaseConst").change(function(){
		if($("form#promocionFormConsultaDetAct #cvePkFaseConst").val()!=-1){
			$("form#promocionFormConsultaDetAct #cvePkTipObra").prop('disabled','disabled');			
		}else{
			$("form#promocionFormConsultaDetAct #cvePkTipObra").prop('disabled','');
		}
	});
	
	//
	 $("#btnValidarSBC").click(function(event){
		 validaSBC(event);
	 });
	
	 llenarCombo();

	 //Funcion para boton de invitacion
	 $("#btnGuardarInv").click(
				function() {
					if(validaInvitacion()){
						var crtInvitacion = $("#invitacionFormRegistro").toObject({mode:'first'});
						$.postJSON(jsContextoPromocion + "consulta/agregaInvitacion.do", crtInvitacion, function(data) {
							$("form#seguimientoForm #fecOfiInvitacion").val($("form#invitacionFormRegistro #fecEmision").val());
							oDgConsultaRegistros.fnDraw();							
							oDgInvitacion.dialog("close");
						}).error(function(data){ 
							validarSesionExpirada(data);
							
						}).complete(function(){
							limpiarFormulario("#invitacionFormRegistro");
							//Instrucciones para el complete													
						});											
					}		
				});
	 
	 
	// Dialog de Invitacion Antecedentes
		oDgInvitacionAntecedente = $(idInvitacionAntecedente).dialog({
				autoOpen: false,
				modal:true,
				resizable:false,
				width: 930,
				closeOnEscape: false,
				beforeClose :function(event,ui){
				    
				    $("form#invitacionAntecedenteForm #fechaFinalInv").val(replaceAll($("form#invitacionAntecedenteForm #fechaFinalInv").val(),"/","-") );
					 $("form#invitacionAntecedenteForm #fechaFinal").val(replaceAll($("form#invitacionAntecedenteForm #fechaFinal").val(),"/","-"));
					 
					//alert("fechaIniTemp : " + fechaIniTemp);
					 $('form#invitacionAntecedenteForm #fechaIncialinv').val(replaceAll($('form#invitacionAntecedenteForm #fechaIncialinv').val(),"/","-") );
					 $('form#invitacionAntecedenteForm #fechaIncial').val(replaceAll($('form#invitacionAntecedenteForm #fechaIncial').val(),"/","-"));
					 limpiarFormulario("#invitacionAntecedenteForm");
				},
				buttons: {
					"Aceptar": function() {

						if(jsValidaInvitacion() == true){
							oDgConfirmar.dialog('open');
						}
					
					},
					"Cancelar": function() { 
						
						$(this).dialog("close"); 
						jsLimpiaFormaInvitacion();
						
					} 
				}
		 });
		
		//boton confirmar invitacions
		oDgConfirmar = $(idDgConfirmar).dialog({
			autoOpen: false,
			modal:true,
			resizable:false,
			height: 150,
			width: 700,
			closeOnEscape: false,
			buttons: {
				"Si": function() {	
					//alert("si");
					if(jsValidaFecEmisionDel() && jsValidaFecEmisionAl()){
						$(this).dialog("close");
						bloquear();
						$("form#invitacionAntecedenteForm #fechaEmision").val($("form#invitacionAntecedenteForm #fechaEmisionFec").val());
						$("form#invitacionAntecedenteForm #fechaIncial").val($("form#invitacionAntecedenteForm #fechaIncialinv").val());
						$("form#invitacionAntecedenteForm #fechaFinal").val($("form#invitacionAntecedenteForm #fechaFinalInv").val());
						var funcionSeguimiento = $("form#invitacionAntecedenteForm #funcionSeguimientoInv").val();
						var invitacion = $("#invitacionAntecedenteForm").toObject({mode:'first'});
						$.postJSON(getAppContextParaJS()+"/catalogo/invitacion/guardar.do", invitacion, function(data) {
							alert('La invitaci\u00f3n fue generada con el folio : ' + data.nuFolioInvitacion);
							if($("form#invitacionAntecedenteForm #funcionSeguimientoInv").val() != undefined || $("form#invitacionAntecedenteForm #funcionSeguimientoInv").val() != ''){
								eval($("form#invitacionAntecedenteForm #funcionSeguimientoInv").val());
							}
							completaFlujoInvitacion(data);
							
							jsLimpiaFormaInvitacion();
//							oDgInvitacionAntecedente.dialog('close');
							$(".ui-dialog-content").dialog('close');
							

							$("form#promocionConsultaForm #cveTemp").val('');
							
						}).error(function(data){ 
							desbloquear();
							validarSesionExpirada(data);
						}).complete(function(){	
							//Instrucciones para el 'complete'
							desbloquear();
							
						});
					}else{
						$(this).dialog("close");
					}
				}, 
				"No": function() { 
					$(this).dialog("close"); 
				} 
			}
		});
	 
	/*seccion para definir la fecha maxima del servidor y establecerle un limite maximo a las fechas
	 * maximas de los calendarios para las fechas siguientes , con el formato dd-MM-yyyy
	 */  
	if (jsFechaMaxSeguimiento!=null){
		$("form#seguimientoTabForm #fecNotificacionOficio").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
		$("form#seguimientoTabForm #fecAtencionOficio").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
		$("form#promocionCancelacionForm #fecCancelacion").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
		$("form#autAviDictamenSegForm #fechaAvisoDictamen").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
		$("form#autAviDictamenSegForm #fechaFin").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
		$("form#promocionConsultaForm #fechaFinal").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
		$("form#promocionConsultaForm #fechaIncial").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
		
		$("form#promocionConsultaForm #fechaFinal").datepicker('option', 'beforeShowDay', null);
		$("form#promocionConsultaForm #fechaIncial").datepicker('option', 'beforeShowDay', null);
		
		$("form#invitacionAntecedenteForm #fechaEmisionFec,form#invitacionAntecedenteForm #fechaIncialinv,form#invitacionAntecedenteForm #fechaFinalInv").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
	
	}
		
	/*seccion para definir la fecha minima del servidor y establecerle un limite minima a las fechas
	 * maximas de los calendarios para las fechas siguientes , con formato dd-MM-yyyy
	 */ 
	
	if (jsFechaMinSeguimiento != null){
		$("form#seguimientoTabForm #fecNotificacionOficio").datepicker('option', 'minDate', jsFechaMinSeguimiento);
		$("form#seguimientoTabForm #fecAtencionOficio").datepicker('option', 'minDate', jsFechaMinSeguimiento);
		$("form#promocionCancelacionForm #fecCancelacion").datepicker('option', 'minDate', jsFechaMinSeguimiento);
		$("form#autAviDictamenSegForm #fechaAvisoDictamen").datepicker('option', 'minDate', jsFechaMinSeguimiento);
		$("form#invitacionAntecedenteForm #fechaEmisionFec").datepicker('option', 'minDate', jsFechaMinSeguimiento);
	}
	
	//parte de los tabs del seguimiento de promocion
	 $('#tab_container_href').tabs({
		 cache: false,
	     spinner: 'Cargando...',
		 ajaxOptions: {
			cache: false,
			type:'POST',
			error: function( xhr, status, index, anchor ) {
				$( anchor.hash ).html(
					"No se pudo obtener la información..." );
				}
			}
		});
	 
	 //evento cuando se cierra dialogo de seguimiento Promocion desde el tach(X)
	 $(idSeguimiento).bind( "dialogclose", function(event, ui) {
		  oDgConsultaRegistros.fnDraw();	
		  $("#buttons").hide();
		  $('#tab_container_href').tabs('destroy').tabs();
		});
	 
	 $('#divSeguimientoPromocionSaticb').hide();
	 $('#saticaMain').hide();
	 
});//fin del ready

/**
 * funcion que llena el cambo  de tipos de Promocion 
 */
function llenarCombo() {

	var URL = getAppContextParaJS() + "/promocion/consulta/llenarTiposCorreccion.do";
	
	$.postJSON(URL,'null',function(data) { 
		if(data != null){
			var myselect=document.getElementById("tipoPromocion");
			myselect.options.length = 1;
			for(var i = 0 ; i < data.length ; i++){
			var elOptNew = document.createElement('option');
			elOptNew.text = data[i].txDescripcion;
			elOptNew.value = data[i].cveTipocorr;
				myselect.add(elOptNew);
			}	
		}
	}).complete(function(data){
		var tipoSelected = $("form#promocionConsultaForm #tipoPromocionHiden").val();
		if(tipoSelected != undefined || tipoSelected != null){
			$("form#promocionConsultaForm #tipoPromocion").val(tipoSelected);
		}
		
	});
}

function buscar(){
	if(jsValidaBuscar()){
		$("#promocionDisponiblesPromocion").show();
		$("#buttons").hide();
		resetDisplayStart(oDgConsultaRegistros);
		oDgConsultaRegistros.fnDraw();	
		bloquear();	
	}
}

function muestraDiv(){
	$("#buttons").show();
}

function Cancelar(){		
	var radios = document.getElementsByName("radio");
	for (i=0;i<radios.length;i++)
	 {
		if(radios[i].checked)
		{
			var idPromocion = radios[i].value;
			var sPromocion = '{"cvePromocion":'+idPromocion+'}';
			var promocion = jQuery.parseJSON(sPromocion);
			$.postJSON(jsContextoPromocion + "consulta/consultaPorClaveUser.do", promocion, function(data) {				
				if(data.fecFechaAtencion!=null){					
					alert('la promoci'+'\u00f3'+'n no se puede cancelar por que contiene pagos');
				}else{
					$('#wrapperDialogCancelacion,#promocionCancelacionForm,#cvePromocion').val(promocion.cvePromocion);			
					$('#promocionCancelacionForm #labelFuncionario').val(data.razonSocial);	
					oDgCancelacion.dialog('open');
				}									
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones para el complete													
			});			
		}
	 }
}

function invitacion(){		
	var radios = document.getElementsByName("radio");
	$('#cancelacion').attr('href','');
	for (i=0;i<radios.length;i++)
	 {
		if(radios[i].checked)
		{
			var idPromocion = radios[i].value;
			var sPromocion = '{"cvePromocion":'+idPromocion+'}';
			var crtPromocion = jQuery.parseJSON(sPromocion);									
			$.postJSON(jsContextoPromocion + "consulta/consultaInvitacion.do", crtPromocion, function(data) {	
				if(data.cvePromocion != null){
					$('form#invitacionFormRegistro #cvePromocion').val(data.cvePromocion);
				}
				if(data.cveDeteccion != null){
					$('form#invitacionFormRegistro #cveDeteccion').val(data.cveDeteccion);
				}
				$('form#invitacionFormRegistro #patron').val(data.cveFkPatron);
				$('form#invitacionFormRegistro #folioPromocion').val(data.folioPromocion);
				if(data.nuOficioinv != null){
					$('form#invitacionFormRegistro #oficio').val(data.nuOficioinv);
					$('form#invitacionFormRegistro #oficio').prop('disabled','disabled');
				}
				if(data.fechaEmision != null){
					$('form#invitacionFormRegistro #fecEmision').val(data.fechaEmision);
					$('form#invitacionFormRegistro #fecEmision').prop('disabled','disabled');
					$('form#invitacionFormRegistro #btnGuardarInv').prop('disabled','disabled');
				}
				if(data.RegPatronal != null){
					$('form#invitacionFormRegistro #registroPatronal').val(data.regPatronal);
					$('form#invitacionFormRegistro #registroPatronal').prop('disabled','disabled');
					$('form#invitacionFormRegistro #btnValidar').prop('disabled','disabled');				}
					$('form#invitacionFormRegistro #razonSocial').val(data.patron);
								
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones para el complete													
			});
			
		}
	 }
}

function validaInvitacion(){
	if($("form#invitacionFormRegistro #oficio").val()=="" && $("form#invitacionFormRegistro #fecEmision").val()==""){		
		$("#labelOficio").html('<label style="color: red;">Campo requerido</label>');
		$("#labelFecEmision").html('<label style="color: red;">Campo requerido</label>');
		return false;
	}else if($("form#invitacionFormRegistro #oficio").val()=="" && $("form#invitacionFormRegistro #fecEmision").val()!=""){
		$("#labelOficio").html('<label style="color: red;">Campo requerido</label>');
		return false;
	}else if($("form#invitacionFormRegistro #oficio").val()!="" && $("form#invitacionFormRegistro #fecEmision").val()==""){
		$("#labelFecEmision").html('<label style="color: red;">Campo requerido</label>');
		return false;
	}else return true;
}

function regularizacion(form){
	var idPromocion = $('form#'+form+' #cvePromocion').val();
	var sPromocion = '{"cvePromocion":'+idPromocion+'}';
	var crtPromocion = jQuery.parseJSON(sPromocion);									
	$.postJSON(jsContextoPromocion + "consulta/consultaRegularizacion.do", crtPromocion, function(data) {
		if(data.cveRegulapagos==0){
			$('form#promocionRegularizaForm #cveRegulapagos').val('');
		}else{
			$('form#promocionRegularizaForm #cveRegulapagos').val(data.cveRegulapagos);
		}
		if(data.fechaAtencionPro!=null && data.fechaAtencionPro!=""){
			$("form#promocionRegularizaForm #fechaPAI").prop('disabled','disabled');
			$("form#promocionRegularizaForm #porRegularizado").prop('disabled','');
			$("form#promocionRegularizaForm #porAvance").prop('disabled','');
			$("form#promocionRegularizaForm #fecPerIni").prop('disabled','');
			$("form#promocionRegularizaForm #fecPerFin").prop('disabled','');
			$("form#promocionRegularizaForm #numTrabrevisados").prop('disabled','');
			$("form#promocionRegularizaForm #numTrabomisos").prop('disabled','');
			$("form#promocionRegularizaForm #numTrabsubdclara").prop('disabled','');			
		}
		$('form#promocionRegularizaForm #fechaAtencionPro').val(data.fechaAtencionPro);
		$('form#promocionRegularizaForm #fechaPAI').val(data.fechaPAI);
		$('form#promocionRegularizaForm #tipoProm').val(data.tipoProm);
		$('form#promocionRegularizaForm #cvePromocion').val(data.cvePromocion);
		$('form#promocionRegularizaForm #fecPeriodo').val(data.fechaInicio);
		$('form#promocionRegularizaForm #regPatron').val(data.regPatron);
		$('form#promocionRegularizaForm #porRegularizado').val(data.porRegularizado);
		$('form#promocionRegularizaForm #porAvance').val(data.porAvance);
		$('form#promocionRegularizaForm #fecPerIni').val(data.fecPerIni);
		$('form#promocionRegularizaForm #fecPerFin').val(data.fecPerFin);
		$('form#promocionRegularizaForm #numTrabrevisados').val(data.numTrabrevisados);
		$('form#promocionRegularizaForm #numTrabomisos').val(data.numTrabomisos);
		$('form#promocionRegularizaForm #numTrabsubdclara').val(data.numTrabsubdclara);
		$('form#promocionRegularizaForm #numTrabReg').val(data.numTrabomisos+data.numTrabsubdclara);
		oDgRgulariza.dialog("open");
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el complete													
	});		
}

function validaFecha(form,fec,fec2,leyendafec,leyendafec2){
	
	fec = "form#"+form+" #"+fec+"";
	fec2 = "form#"+form+" #"+fec2+"";
	if($(fec2).val()!="") {
		if(!validaFechas($(fec2).val(),$(fec).val())) {
			alert("La "+leyendafec+" es menor a la "+leyendafec2);
			$(fec2).val("");
		}
		if (!jsValidaFechasEjercicio($(fec2).val(),$(fec).val())) {
			alert("La fechas deben corresponder al mismo ejercicio");
			$(fec2).val("");
		}
	}	
}

function validaFechaConsultaPeriodo(){
	
	var fechaInicialDel = $("form#promocionConsultaForm #fechaIncial").val();
	var fechaFinalAl  = $("form#promocionConsultaForm #fechaFinal").val();
	var respuesta = false;
	
	//cuando las dos fechas estan capturadas
	if(fechaInicialDel != null && fechaInicialDel != '' && fechaFinalAl != null && fechaFinalAl != '' ) {
		//alert("las dos fechas");
		
		if(validaFechas(fechaFinalAl,fechaInicialDel)) {
			//alert("La fecha inicial es menor a la fecha final");
			
			if (!jsValidaFechasEjercicio(fechaFinalAl,fechaInicialDel)) {
				alert("La fechas deben corresponder al mismo ejercicio");
				//$(fec2).val("");
				respuesta = false;
			}else{
				respuesta = true;
			}
		}else{
			alert("La fecha final NO puede ser menor a la fecha inicial");
			respuesta = false;
		}
		
	//cuando si hay fecha final, pero No fecha Inicial
	}else if ((fechaInicialDel == null || fechaInicialDel == '') && (fechaFinalAl != null && fechaFinalAl != '' ) )	{
		//alert("si fecha final");
		respuesta = false;
	//cuando si hay fecha inicial , pero no fecha final	
	}else if(( fechaFinalAl == null || fechaFinalAl == '') && (fechaInicialDel != null && fechaInicialDel != '')){
		//alert("si fecha inicial");
		respuesta = false;
	}
	//alert("respuesta :"+ respuesta);
	return respuesta;
}

function validafechaSistema(form,fec,leyendafec){
	fec = "form#"+form+" #"+fec+"";
	var fechaSis = new Date();
	var diaS = fechaSis.getDate();
	var mesS = fechaSis.getMonth() + 1;
	var anioS = fechaSis.getFullYear()+"";
	if(diaS<10){
		diaS = "0"+diaS;
	}
	if(mesS<10){
		mesS = "0"+mesS;
	}
	var fec2 = diaS+"-"+mesS+"-"+anioS;
	if($(fec).val()!=""){
		if(!validaFechas(fec2,$(fec).val())){
			alert("La "+leyendafec+" debe ser menor a la Fecha del sistema");
			$(fec).val("");
		}
	}
}

function valorMaxim(campo,leyenda,form){
	campo = "form#"+form+" #"+campo;
	if(!valorMaximo($(campo).val(),leyenda,100)){		
		$(campo).val("");		
	}
}

/** 
 * Funcion que ejecuta la peticion para el boton de "Seguimiento" en la pantalla de
 * seguimiento de promocion de acuerdo al criterio de seleccion Seleccionado:
 * SATIC A - 3
 * SATIC B - 4
 * EXHORTO DE CONSTRUCCION - 5
 * EXHORTO DE LO ORDINARIO - 6
 * SALARIO BASE DE COTIZACIÓN - 7
 * @author 
 * @version 1.0.1
 * */
function muestra(){
	
	var pantalla;
	var radios = document.getElementsByName("radio");
	var tipoPromocion = $("form#promocionConsultaForm #tipoPromocion").val();
	if(tipoPromocion == ''){
		tipoPromocion = jsValidaFolio();
	}
	if(validaRadioSeleccionado(radios)){
		limpiaVariablesSeguimiento();
		bloquear();
		if(tipoPromocion == PROMOCION_SATIC_A){
			inicializaDialogSaticA();
		} else if(tipoPromocion ==PROMOCION_SATIC_B){
			inicializaDialogSaticb();
		}else if(tipoPromocion ==PROMOCION_COSTRUCCION){
			inicializaDialogConstruccion();
		}else if(tipoPromocion==PROMOCION_SBC){
			inicializaDialogSBC();			
		}else if(tipoPromocion==PROMOCION_ORDINARIO){
			var idPromocionOrd = $('#:checked').val();
			
			for (i=0;i<radios.length;i++) {
				if(radios[i].checked){
			
					var idPromocion = radios[i].value;
					var sPromocion = '{"cvePromocion":'+idPromocion+'}';
					var promocion = jQuery.parseJSON(sPromocion);
					// Buscamos el elemento
					$.postJSON(jsContextoPromocion+"consulta/muestraEXO.do", promocion, function(data) {
						// se oculta el boton de generar invitacion
						$("form#seguimientoTabForm #btnGenInvita").hide();
						if(data.fechaNotificacion!='' || data.fechaNotificacion!=null){
							$('#fecNotificacionOficio').attr('disabled','disabled');
						}else{
							$('#fecNotificacionOficio').removeAttr('disabled');
						}
						//agregamos rol del usuario
						$("form#seguimientoForm #rolUsuarioOrdHdn").val(data.rolUsuario);
						// llenar JSP
						$("form#seguimientoForm #criterioSeleccion").html('<label>' + data.descCriterioseleccion + '</label>');
						$("form#seguimientoForm #numFolioPromocion").html('<label>' + data.nuFoliopromocion + '</label>');
						$("form#seguimientoForm #fecOficio").html('<label>' + data.fechaOficio + '</label>');
						$("form#seguimientoForm #numOficioPromocion").html('<label>' + data.nuOficiopro + '</label>');
						if(data.regPatron != null && data.regPatron.length >=10 ) {
							$("form#seguimientoForm #registroPatronal").html('<label>' + data.regPatron.substring(0,10) + '</label>');
							$("form#seguimientoForm #regPatronalOrdinarioHdn").val(data.regPatron.substring(0,10)); 
						}
						if(data.razonSocial != null){
							$("form#seguimientoForm #razonSocial").html('<label>' + data.razonSocial + '</label>');
						}else{
							$("form#seguimientoForm #razonSocial").html('<label></label>');
						}
						$("form#seguimientoForm #domCalle").html('<label>' + data.domCalle + '</label>');
						$("form#seguimientoForm #colonia").html('<label>' + data.refColonia + '</label>');
						if(data.numNroext != null){
							$("form#seguimientoForm #numExt").html('<label>' + data.numNroext + '</label>');
						}
						if(data.numNroint != null){
							$("form#seguimientoForm #numInt").html('<label>' + data.numNroint + '</label>');
						}
						$("form#seguimientoForm #cp").html('<label>' + data.numCodigopostal + '</label>');
						$("form#seguimientoForm #cvePromocion").val(data.cvePromocion);
						
						if(data.fecSolCorr != ''){
							$("form#seguimientoTabForm #fecSol").val(data.fecSolCorr);
						}
						if(data.fechaIncial != ''){
							$("form#seguimientoTabForm #fecSolCorrIni").val(data.fechaIncial);
						}
						if(data.fechaFinal != ''){
							$("form#seguimientoTabForm #fecSolCorrFin").val(data.fechaFinal);
						}
						
						
						if(data.fechaNotificacion!='' || data.fechaNotificacion!=null){
							$("form#seguimientoTabForm #btnGenInvita").removeAttr('disabled');							
						}
						
						
						if(data.fecOficioInvitacion != ''){
							$("form#seguimientoTabForm #fecOfiInvitacion").val(data.fecOficioInvitacion);
						}
						$("form#seguimientoTabForm #observaciones").val(data.txObservaciones);
						
						if(data.cveUsuario != ''){
							$("form#promocionCancelacionForm #funcionarioReg").val(data.cveUsuario);
							$("form#autAviDictamenSegForm #funcionarioReg").val(data.cveUsuario);
						}
						
						iniciaPantallaUISeguimiento(data);
						
						cargaFuncionarioAutorizaOrdinario();
						
						oDgSeguimeinto.dialog("open");	
					}).error(function(data){ 
						validarSesionExpirada(data);
					}).complete(function(){
						//Instrucciones para el 'complete'
						desbloquear();
					});
				}
			 }
		}else{
			//var radios = document.getElementsByName("radio");
			for (i=0;i<radios.length;i++)
			 {
				if(radios[i].checked)
				{
			
					var idPromocion = radios[i].value;
					var sPromocion = '{"cvePromocion":'+idPromocion+'}';
					var promocion = jQuery.parseJSON(sPromocion);
					// Buscamos el elemento
					$.postJSON(jsContextoPromocion + "consulta/mostrar.do", promocion, function(data) {
						$('form#promocionFormConsultaDetAct #cvePromocion').val(idPromocion);
						$('form#promocionFormConsultaDetAct #cveDeteccion').val(data.cveDeteccion);		
						$('form#promocionFormConsultaDetAct #sdelegOrig').val(data.sdelegOrig);
						$('form#promocionFormConsultaDetAct #cveTipocorr').val(data.cveTipocorr);
						$('form#promocionFormConsultaDetAct #cveFkPatron').val(data.cveFkPatron);
						$('form#promocionFormConsultaDetAct #domicilioId').val(data.domicilioId);
						$('form#promocionFormConsultaDetAct #idObligado').val(data.idObligado);
						$('form#promocionFormConsultaDetAct #idPromovido').val(data.idPromovido);
						$('form#promocionFormConsultaDetAct #fechaRegistro').val(data.fechaRegistro);				
						$('form#promocionFormConsultaDetAct #nuFoliodeteccion').val(data.nuFoliodeteccion);
						$('form#promocionFormConsultaDetAct #fechaDeteccion').val(data.fechaDeteccion);
						$('form#promocionFormConsultaDetAct #fechaEmision').val(data.fechaEmision);
						$('form#promocionFormConsultaDetAct #fechaNotificacion').val(data.fechaNotificacion);
						$('form#promocionFormConsultaDetAct #fechaAtencion').val(data.fechaAtencion);
						if(data.cveTipocorr==8){			
							$('form#promocionFormConsultaDetAct #divActividad').show('fast');			
							$('form#promocionFormConsultaDetAct #nomRazonsocial').val(data.nomRazonsocial);			
							$('form#promocionFormConsultaDetAct #regPatron').val(data.regPatron);
							$('form#promocionFormConsultaDetAct #desDepcontratante').val(data.desDepcontratante);
							$('form#promocionFormConsultaDetAct #desDependenciapub').val(data.desDependenciapub);
							$('form#promocionFormConsultaDetAct #numTrabajdores').val(data.numTrabajdores);
						}else{	
							$("#divDatosCenso").show("fast");
							$("#divNumReporte").show("fast");
							$('form#promocionFormConsultaDetAct #nuReportectrlobra').val(data.nuReportectrlobra);
							$('form#promocionFormConsultaDetAct #txRfcpatron').val(data.txRfcpatron);
							$('form#promocionFormConsultaDetAct #nomRazonsocial').val(data.nomRazonsocial);			
							$('form#promocionFormConsultaDetAct #regPatron').val(data.regPatron);
							$('form#promocionFormConsultaDetAct #txCurppatron').val(data.txCurppatron);
							$('form#promocionFormConsultaDetAct #txEmail').val(data.txEmail);
							$('form#promocionFormConsultaDetAct #txTelefono').val(data.txTelefono);													
							$('form#promocionFormConsultaDetAct #impCostoobra').val(data.impCostoobra);
							$('form#promocionFormConsultaDetAct #porAvanceobraEst').val(data.porAvanceobraEst);
							$('form#promocionFormConsultaDetAct #canSuperficie').val(data.canSuperficie);
							$('form#promocionFormConsultaDetAct #desDepcontratante').val(data.desDepcontratante);
							$('form#promocionFormConsultaDetAct #desDependenciapub').val(data.desDependenciapub);
							$('form#promocionFormConsultaDetAct #numTrabajdores').val(data.numTrabajdores);
	//						if((data.tipClaseobra!=null && data.tipClaseobra!="") && ((data.tipoObra!=null && data.tipoObra!="") || (data.faseObra!=null && data.faseObra!=""))){
	//							$("#divDatosObra").show("fast");						
	//							$('form#promocionFormConsultaDetAct #tipoObra').val(data.tipoObra);
	//							$('form#promocionFormConsultaDetAct #faseObra').val(data.faseObra);
	//						}else{
							$("#divCombosObra").show("fast");
							$('form#promocionFormConsultaDetAct #cvePkTipObra').val(data.cvePkTipObra);
							$('form#promocionFormConsultaDetAct #cvePkFaseConst').val(data.cvePkFaseConst);
	//						}
						}
						if(data.cveFkZona!=null)
							$('form#promocionFormConsultaDetAct #cveFkZona').val(data.cveFkZona);
						if(data.fecFechaterminoEst!=null)
							$('form#promocionFormConsultaDetAct #fecFechaterminoEst').val(data.fechaFinal);
						$('form#promocionFormConsultaDetAct #fechaEstimIncio').val(data.fechaIncial);
						$('form#promocionFormConsultaDetAct #tipClaseobra').val(data.tipClaseobra);				
						$('form#promocionFormConsultaDetAct #estado').val(data.estado);
						$('form#promocionFormConsultaDetAct #municipio').val(data.municipio);
						$('form#promocionFormConsultaDetAct #domCalle').val(data.domCalle);
						$('form#promocionFormConsultaDetAct #refColonia').val(data.refColonia);
						$('form#promocionFormConsultaDetAct #numNroint').val(data.numNroint);
						$('form#promocionFormConsultaDetAct #numNroext').val(data.numNroext);
						$('form#promocionFormConsultaDetAct #numCodigopostal').val(data.numCodigopostal);		
						oDgPromocionConsultaDet.dialog('open');
					}).error(function(data){ 
						validarSesionExpirada(data);
					}).complete(function(){
						//Instrucciones para el 'complete'
						desbloquear();
					});
				}
			 }
		}
		
  } //fin de if principal
  else {
	  alert('Se debe seleccionar un Folio de Promoci'+'\u00f3'+'n');
  }
}

function validaRegPatron(){
	limpiarForm = false;
	if(!longitudMandatoria($("form#promocionFormConsultaDetAct #regPatron").val(),10,"Registro Patronal")) return false;
	oDgPromocionConsultaDet.dialog('close');
	bloquear();	
	var deteccion = $("#promocionFormConsultaDetAct").serializeObject(true);	
	$.postJSON(jsContextoPromocion + "consulta/validaRegPatron.do", deteccion, function(data) {
		if(data.cveFkPatron==null){
			alert("El registro Patronal es invalido");
			$("form#promocionFormConsultaDetAct #nomRazonsocial").val("");
			$("form#promocionFormConsultaDetAct #txRfcpatron").val("");
			$("form#promocionFormConsultaDetAct #txCurppatron").val("");
			$("form#promocionFormConsultaDetAct #cveFkPatron").val("");
			$("form#promocionFormConsultaDetAct #actividad").val("");
		}else{
			alert("El registro Patronal es valido");
			$("form#promocionFormConsultaDetAct #nomRazonsocial").val(data.nomRazonsocial);
			$("form#promocionFormConsultaDetAct #txRfcpatron").val(data.txRfcpatron);
			$("form#promocionFormConsultaDetAct #txCurppatron").val(data.txCurppatron);
			$("form#promocionFormConsultaDetAct #cveFkPatron").val(data.cveFkPatron);
			$("form#promocionFormConsultaDetAct #actividad").val(data.actividad);
		}						
		oDgPromocionConsultaDet.dialog('open');
		desbloquear();	
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el 'complete'		
	});
}

	function jsValidaFecAte(fecAte){
		$("#labelFecAte").html('');
		if(jsValidaFecha(fecAte)){
			 $("#fecAtencion").val(fecAte);
			 $("#labelFecAte").html('');
		}else{
			 $("#fecAtencion").val("");
			 $("#labelFecAte").html('<label class="etiquetaError" >La fecha atencion no puede ser mayor al dia actual</label>');
		}
	}
	
	function jsValidaFecNot(fecNot){
		$("#labelFecNot").html('');
		if(jsValidaFecha(fecNot)){
			 $("#fecNotificacion").val(fecNot);
			 $("#labelFecNot").html('');
		}else{
			 $("#fecNotificacion").val("");
			 $("#labelFecNot").html('<label class="etiquetaError" >La fecha notificacion no puede ser mayor al dia actual</label>');
		}
	}
	
	function jsValidaFecha(fecha){
		
		if(fecha != undefined){
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
	
function jsValidaFechasEjercicio(fecIni, fecFin){
		
		var array_fechaIni = fecIni.split("-"); 
		var array_fechaFin = fecFin.split("-"); 
		
		var anioIni = parseInt(array_fechaIni[2],10);
		var anioFin = parseInt(array_fechaFin[2],10);
		
		if(anioIni != anioFin){
			return false;
		}
		return true;
	}
		
	
	function jsvalidarAlfaNumerico(e) { 
		
	    tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true;
	    patron = /[1234567890abcdefghijklmn–opqrstuvwxyzABCDEFGHIJKLMN„OPQRSTUVWXYZ]/;
	    te = String.fromCharCode(tecla);
	    
	    return patron.test(te);
	} 
	
	function regularizacionSBC(){
		var idPromocion = $('#cvePromocion').val();
		var sPromocion = '{"cvePromocion":'+idPromocion+'}';
		var crtPromocion = jQuery.parseJSON(sPromocion);									
		$.postJSON(jsContextoPromocion + "consulta/consultaRegularizacion.do", crtPromocion, function(data) {
			if(data.cveRegulapagos==0){
				$('form#promocionRegularizaForm #cveRegulapagos').val('');
			}else{
				$('form#promocionRegularizaForm #cveRegulapagos').val(data.cveRegulapagos);
			}
			if(data.fechaAtencionPro!=null && data.fechaAtencionPro!=""){
				$("form#promocionRegularizaForm #fechaPAI").prop('disabled','disabled');
				$("form#promocionRegularizaForm #porRegularizado").prop('disabled','');
				$("form#promocionRegularizaForm #porAvance").prop('disabled','');
				$("form#promocionRegularizaForm #fecPerIni").prop('disabled','');
				$("form#promocionRegularizaForm #fecPerFin").prop('disabled','');
				$("form#promocionRegularizaForm #numTrabrevisados").prop('disabled','');
				$("form#promocionRegularizaForm #numTrabomisos").prop('disabled','');
				$("form#promocionRegularizaForm #numTrabsubdclara").prop('disabled','');			
			}
			$('form#promocionRegularizaForm #fechaAtencionPro').val(data.fechaAtencionPro);
			$('form#promocionRegularizaForm #fechaPAI').val(data.fechaPAI);
			$('form#promocionRegularizaForm #tipoProm').val(data.tipoProm);
			$('form#promocionRegularizaForm #cvePromocion').val(data.cvePromocion);
			$('form#promocionRegularizaForm #fecPeriodo').val(data.fechaInicio);
			$('form#promocionRegularizaForm #regPatron').val(data.regPatron);
			$('form#promocionRegularizaForm #porRegularizado').val(data.porRegularizado);
			$('form#promocionRegularizaForm #porAvance').val(data.porAvance);
			$('form#promocionRegularizaForm #fecPerIni').val(data.fecPerIni);
			$('form#promocionRegularizaForm #fecPerFin').val(data.fecPerFin);
			$('form#promocionRegularizaForm #numTrabrevisados').val(data.numTrabrevisados);
			$('form#promocionRegularizaForm #numTrabomisos').val(data.numTrabomisos);
			$('form#promocionRegularizaForm #numTrabsubdclara').val(data.numTrabsubdclara);
			$('form#promocionRegularizaForm #numTrabReg').val(data.numTrabomisos+data.numTrabsubdclara);
			oDgRgulariza.dialog("open");
		}).error(function(data){ 
			validarSesionExpirada(data);
		}).complete(function(){
			//Instrucciones para el complete													
		});		
	}
	
	
	/**
	 * Funcion que valida la fecha que recibe como parametro contra la fecha del
	 * oficio de la promocion
	 * @param fecIni , fecha a Evaluar
	 * @returns {Boolean}
	 */
	function jsValidaVsfecOficio(fecIni){
		var fecOficioPromo = $("#fecOficio").text();
		var resp = false;
		if(fecIni != '' && fecOficioPromo != ''){
			if(jsValidaFechas(fecOficioPromo,fecIni)){
				 resp = true;
			 }
		}
		return resp;
	}
	
	
	function jsValidaFechasCorr(fecIni){
		var fecSolCorrIni = $("#fecSolCorrIni").val();
		
		if(fecIni != '' && fecSolCorrIni != ''){
			if(jsValidaFechas(fecSolCorrIni,fecIni)){
				$("#fecSolCorrFin").val(fecIni);
				$("#labelFecCorr").html('');
			 }else{
				 $("#fecSolCorrFin").val("");
				 $("#labelFecCorr").html('<label class="etiquetaError" >La fecha final no puede ser menor a la fecha inicial</label>');
			 }
		}
	}
	
	function jsvalidarNumerico(e) { 
		
	    tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true;
	    patron = /[1234567890]/;
	    te = String.fromCharCode(tecla);
	    
	    return patron.test(te);
	} 
	
	/**
	 * Funcion que valida la fecha de emision
	 * @param fecEmision
	 */
	function jsValidaFecEmision(fecEmision){
		
		if(jsValidaFecha(fecEmision)){
			 $("#fecEmision").val(fecEmision);
			 $("#labelOficio").html('');
		}else{
			 $("#fecEmision").val("");
			 $("#labelOficio").html('<label class="etiquetaError">La fecha emision no puede ser mayor al dia actual</label>');
		}
	}
	
	
	/**
	 * Funcion que activa un elemento HTML y lo pone disponible para captura o escritura
	 * , de acuerdo al ID que se le pase (elemento)
	 * @param elemento ID del elemento HTML
	 */
	function activa(elemento){
		if(elemento != null && elemento != ''){
			$('form#seguimientoTabForm input#'+elemento ).prop('disabled', false);
			$('form#seguimientoTabForm input#'+elemento ).addClass("red");
		}
	}
		
	
		
	/**
	 * 
	 * @returns {Boolean}
	 */
	function jsValidaBuscar(){
		
		$("#labelGeneral").html('');
		$("#labelTipoProm").html('');
		$("#labelFecFin").html('');
		
		var resp = false;
		
		if($("form#promocionConsultaForm #tipoPromocion").val() != '' && $("form#promocionConsultaForm #fechaIncial").val() != '' 
			&& $("form#promocionConsultaForm #fechaFinal").val()!='' ){
			//borrar los demas campos
			resp = validaFechaConsultaPeriodo()
			//resp = true;
		}else if($("form#promocionConsultaForm #tipoPromocion").val() != '' || $("form#promocionConsultaForm #fechaIncial").val() != '' 
			|| $("form#promocionConsultaForm #fechaFinal").val()!= '' ){
			if($("form#promocionConsultaForm #tipoPromocion").val() == ''){
				$("#labelTipoProm").html('<label style="color: red;">Campo obligatorio</label>');
			}
			if($("form#promocionConsultaForm #fechaIncial").val() == '' || $("form#promocionConsultaForm #fechaFinal").val() == ''){
			
				$("#labelFechas").html('<label style="color: red;">Campos obligatorios</label>');
			}
			
		}else{
			$("#labelGeneral").html('<label style="color: red;">Debe ingresar algun criterio de busqueda</label>');
		}
		return resp;
	}
	
	/**
	 * Funcion que limpia los campos de la seccion de consulta de promociones
	 */
	function limpiaCampos(campo){
		$("#labelGeneral").html('');
		$("#labelTipoProm").html('');
		$("#labelFechas").html('');
		
		if('tipoPromocion' == campo.name || 'fechaIncial' == campo.nombre || 'fechaFinal' == campo.name){
			$("form#promocionConsultaForm #nuFoliopromocion").val("");
		}else
		if('nuFoliopromocion' == campo.name){
			 $("form#promocionConsultaForm #fechaFinal").val("");
			 $("form#promocionConsultaForm #fechaIncial").val("");
			 $("form#promocionConsultaForm #tipoPromocion").val("");
		}
		
	}
	
	
	/**
	 * Funcion que inicializa la pantalla de seguimiento de promocion , en cuanto al estado
	 * inicial que deben tener los tabs.
	 * esta funcion define el comportamiento de los tabs en caso de guardado parcial o en caso ser captura
	 */
	function iniciaPantallaUISeguimiento(data){
		var TAB_SEGUIMIENTO =0;
		var TAB_CANCELACION =1;
		var TAB_AUT_DICTAMEN =2;
		
		
		
		if(data.rolUsuario == JEFE_OF_CORRECCION || data.rolUsuario == JEFE_OF_CORR_Y_DIC || 
					data.rolUsuario == JEFE_DEP_AUD_PAT){
			$("#tab_container_href").tabs( "enable", TAB_AUT_DICTAMEN);
			$("#tab_container_href").tabs( "enable" , TAB_CANCELACION );
			$('#autDictamenTAB').show('slow');
			$('#cancelacionTAB').show('slow');
			
		}else{
			$("#tab_container_href").tabs( "disable" , TAB_CANCELACION );
			$("#tab_container_href").tabs( "disable", TAB_AUT_DICTAMEN);
			$('#cancelacionTAB').hide('slow');
			$('#autDictamenTAB').hide('slow');
		}
		
		
		
		
 		 if(data.cveEstatus==28 ||data.cveEstatus==29){
 			 
 	 		//en caso de guardado parcial
 			 if(data.fechaNotificacion != '' && data.fechaNotificacion != null){
 			
				 $("form#seguimientoTabForm #fecNotificacionOficio").val(data.fechaNotificacion);
				 $("form#seguimientoTabForm #fecNotificacionOficio").addClass("red");
				 $('#spnFecNot').show("fast");
				 
 			 }//guardado parcial
 			 if(data.fechaNotificacion != '' && data.fechaNotificacion != null && (data.fechaAtencion == '' || data.fechaAtencion == null)){
 			
// 				$("#tab_container_href").tabs( "disable", TAB_AUT_DICTAMEN);
// 				$('#autDictamenTAB').hide('slow');
 				
 				$("form#seguimientoTabForm #fecAtencionOficio").removeAttr('disabled');
 				$("form#seguimientoTabForm #fecAtencionOficio").addClass("red");
 				$("form#seguimientoTabForm #fecAtencionOficio").val('');
 				$('#spnFecAtn').hide();
 				
 				
 				$("form#autAviDictamenSegForm #fechaAvisoDictamen").addClass("red");
				$("form#autAviDictamenSegForm #numAvisoDictamen").addClass("red");
				$("form#autAviDictamenSegForm #fechaInicio").addClass("red");
				$("form#autAviDictamenSegForm #fechaFin").addClass("red");
				$("form#seguimientoTabForm #fecAtencionOficio").addClass("red");
 			 }
 			 if(data.fechaAtencion != '' && data.fechaAtencion != null){
 			
				$("form#seguimientoTabForm #fecAtencionOficio").val(data.fechaAtencion);
			
				
				if(!(data.fechaAtencion!="" || data.fechaAtencion!=null || data.fechaAtencion!=undefined)){
					$("form#seguimientoTabForm #fecAtencionOficio").removeAttr('disabled');	
				}else{
					$("form#seguimientoTabForm #fecAtencionOficio").prop("disabled", "disabled");
				}
				
				
				if(data.rolUsuario == JEFE_OF_CORRECCION || data.rolUsuario == JEFE_OF_CORR_Y_DIC || 
						data.rolUsuario == JEFE_DEP_AUD_PAT){
							
					//se tiene que habilitar el tab de aut dictame
					$("#tab_container_href").tabs( "disable" , TAB_CANCELACION );
					//$("#tab_container_href").tabs({ disabled: [TAB_CANCELACION] });
					$("#tab_container_href").tabs( "enable", TAB_AUT_DICTAMEN);
					$('#cancelacionTAB').hide('slow');
					$('#autDictamenTAB').show('slow');
				}else{
					$("#tab_container_href").tabs( "disable" , TAB_CANCELACION );
					$("#tab_container_href").tabs( "disable" , TAB_AUT_DICTAMEN );
					$('#cancelacionTAB').hide('slow');
					$('#autDictamenTAB').hide('slow');
				}
				
				$("form#autAviDictamenSegForm #fechaAvisoDictamen").addClass("red");
				$("form#autAviDictamenSegForm #numAvisoDictamen").addClass("red");
				$("form#autAviDictamenSegForm #fechaInicio").addClass("red");
				$("form#autAviDictamenSegForm #fechaFin").addClass("red");
				$("form#seguimientoTabForm #fecAtencionOficio").addClass("red");
				
				$("#labelFecAutDictamen").html('');
				$("#labelNumAviso").html('');
				$("#labelFecFin").html('');
				
				$('#spnFecAtn').show("fast");
				$('#spnFecNot').show("fast");
				
 			 }
 			//caso inicial
 			 if(( data.fechaNotificacion == '' || data.fechaNotificacion == null) && (data.fechaAtencion == '' || data.fechaAtencion == null)){
 				 
 				$("#tab_container_href").tabs( "enable", TAB_SEGUIMIENTO);//tab seguimiento
 				$('#seguimientoTAB').show('slow');
 				 
 				if(data.rolUsuario == JEFE_OF_CORRECCION || data.rolUsuario == JEFE_OF_CORR_Y_DIC || 
						data.rolUsuario == JEFE_DEP_AUD_PAT){
 				
 					$("#tab_container_href").tabs( "enable", TAB_CANCELACION);
 					$("#tab_container_href").tabs( "disable" , TAB_AUT_DICTAMEN );
 					
 					$('#cancelacionTAB').show('slow');
 					$('#autDictamenTAB').hide('slow');
 				}else{
 					
 					$("#tab_container_href").tabs( "disable", TAB_CANCELACION);
 					$("#tab_container_href").tabs( "disable" , TAB_AUT_DICTAMEN );

 					$('#cancelacionTAB').hide('slow');
 					$('#autDictamenTAB').hide('slow');
 				}
				
				
				 //Datos estado inicial
				 $("form#seguimientoTabForm #fecNotificacionOficio").val("");
				 $("form#seguimientoTabForm #fecCancelaOficio").val("");
				 $("form#seguimientoTabForm #fecAtencionOficio").val("");
				 $("form#seguimientoTabForm #fecSol").val("");
				 $("form#seguimientoTabForm #fecOfiInvitacion").val("");
				 $("form#seguimientoTabForm #fecSolCorrIni").val("");
				 $("form#seguimientoTabForm #fecSolCorrFin").val("");
				 $("form#seguimientoTabForm #observaciones").val("");
				 $("form#seguimientoTabForm #fecAutDict").val("");
				 
				 
				 $("form#promocionCancelacionForm #refCancelacion").prop("value", "");
				 $("form#promocionCancelacionForm #fecCancelacion").val("");
				 $("form#promocionCancelacionForm #idMotivoCancelacion").val("-1");
				 
				 $("form#autAviDictamenSegForm #fechaAvisoDictamen").val("");
				 $("form#autAviDictamenSegForm #numAvisoDictamen").val("");
				 $("form#autAviDictamenSegForm #fechaInicio").val("");
				 $("form#autAviDictamenSegForm #fechaFin").val("");
				 
				 
				 //botones estado inicial
				 $('form#seguimientoTabForm :input#btnGenInvita').prop("disabled", "disabled");
				 $('form#seguimientoTabForm :input#btnGuardarSeg').removeAttr('disabled');
				 $('form#autAviDictamenSegForm :input#btnGuardarAut').removeAttr('disabled');
				 $('form#promocionCancelacionForm :input#btnConfirmarCancel').removeAttr('disabled');

				 //estilos iniciales
				 $('form#seguimientoTabForm :input#fecNotificacionOficio').addClass("red");
		 		 $('form#seguimientoTabForm :input#fecAtencionOficio').removeClass("red");
		 		$('form#seguimientoTabForm #observaciones').addClass("red");
		 		 $('form#promocionCancelacionForm :input#refCancelacion').addClass("red");
		 		 $('form#promocionCancelacionForm :input#fecCancelacion').addClass("red");
		 		 $('form#promocionCancelacionForm :input#idMotivoCancelacion').addClass("red");
		 		
		 		 
		 		//inputs edos iniciales
		 		 $("form#seguimientoTabForm #fecNotificacionOficio").removeAttr('disabled');
		 		 $("form#promocionCancelacionForm #refCancelacion").removeAttr('disabled');
		 		 $("form#promocionCancelacionForm #fecCancelacion").removeAttr('disabled');
		 		 $("form#promocionCancelacionForm #idMotivoCancelacion").removeAttr('disabled');
				 $("form#seguimientoTabForm #fecAtencionOficio").attr('disabled','disabled');
				 $("form#autAviDictamenSegForm #fechaAvisoDictamen").removeAttr('disabled');
				 $("form#autAviDictamenSegForm #numAvisoDictamen").removeAttr('disabled');
				 $("form#autAviDictamenSegForm #fechaInicio").removeAttr('disabled');
				 $("form#autAviDictamenSegForm #fechaFin").removeAttr('disabled');
				 
				 $('#spnFecAtn').hide();
				 $('#spnFecNot').hide();
				 $('#spnFecAut').hide();
				 $('#spnFecPer').hide();
				 $('#spnFecCan').hide();
				 
				 
			}
 			 
 			 //mensajes de error 
			 $("#labelFecAtencionOficio").html('');
			 $("#labelFecNotif").html('');
			 $("#labelRefCancelacion").html('');
			 $("#labelFecCancelacion").html('');
			 $("#labelMotivoCancelacion").html('');
			 $("#labelFecAutDictamen").html('');
			 $("#labelNumAviso").html('');
			 $("#labelFecFin").html('');
			 
		}
 		 
 		//para que aparzca siempre seleccionado el tab seguimiento
 		 $( "#tab_container_href" ).tabs( "select" ,TAB_SEGUIMIENTO);
 		 $( "#tab_container_href" ).tabs( "option", "selected", TAB_SEGUIMIENTO );

	}
	
	
	function validaRadioSeleccionado(radios){
	 var resultado = false;
		for (i=0;i<radios.length;i++){
			if(radios[i].checked){
				resultado= true;
			}
		}
		
		return resultado;
	}
	
	/**
	 * Funcion que completa el flujo al generar una invitacion , de los componentes en pantalla
	 * @param dataRecibida
	 */
	function completaFlujoInvitacion(dataRecibida){
		
		$("form#seguimientoTabForm #fecOfiInvitacion").val(dataRecibida.fechaOfInvitacionTx);
		var variable = '{' +
		   '"cvePromocion":"'+dataRecibida.cvePromocion+'",'+
		   //'"fechaNotificacion":"'+fecNotificacionOficio+'",'+
		   '"fecOficioInvitacion":"'+dataRecibida.fecFechaoficioinv+'",'+
		   '"cveEstatus":"'+ADHERIDO_AL_PROGRAMA_CORRECCION_INVITACION+'"}';
		var variableJson = jQuery.parseJSON(variable);
		
		$.postJSON(jsContextoPromocion + "consulta/actualizaPromocionEXO.do", variableJson, function(data) {
			alert('El registro se actualizo correctamente');
			
			
		}).error(function(data){ 
			desbloquear();
			validarSesionExpirada(data);
		}).complete(function(){
			desbloquear();
			
			completaFlujoSegOrdinario();
		});	
		
	}
	
	
	
	function completaFlujoSegOrdinario(){
		//inhabilitacion de pantalla por invitacion
		$("form#seguimientoTabForm #fecNotificacionOficio").prop('disabled','disabled');
		$("form#seguimientoTabForm #fecAtencionOficio").prop('disabled','disabled');
		$("form#seguimientoTabForm #observaciones").prop('disabled','disabled');
		$("form#promocionCancelacionForm #refCancelacion").prop('disabled','disabled');
		$("form#promocionCancelacionForm #fecCancelacion").prop('disabled','disabled');
		$("form#promocionCancelacionForm #idMotivoCancelacion").prop('disabled','disabled');
		
		$("form#promocionCancelacionForm #refCancelacion").val("");
		$("form#promocionCancelacionForm #fecCancelacion").val("");
		$("form#promocionCancelacionForm #idMotivoCancelacion").val("-1");
		//estilo
		$("form#seguimientoTabForm #fecNotificacionOficio").removeClass("red");
		$("form#seguimientoTabForm #fecAtencionOficio").removeClass("red");
		$('form#seguimientoTabForm #observaciones').removeClass("red");
		$("form#promocionCancelacionForm #refCancelacion").removeClass("red");
		$("form#promocionCancelacionForm #fecCancelacion").removeClass("red");
		$("form#promocionCancelacionForm #idMotivoCancelacion").removeClass("red");
		
		//botones
		$('form#seguimientoTabForm :input#btnGenInvita').prop("disabled", 'disabled');
		$('form#seguimientoTabForm :input#btnGuardarSeg').prop("disabled", 'disabled');
		$('form#promocionCancelacionForm :input#btnConfirmarCancel').prop("disabled", 'false');
		$('#spnFecNot').hide(); //btn fecha Notificacion
		$('#spnFecCan').hide();
		
		
		//errores
		$("#labelRefCancelacion").html('');
		$("#labelFecCancelacion").html('');
		$("#labelMotivoCancelacion").html('');
	}
		
	/**
	 * Funcion que valida el tipo de folio capturado para paginar
	 * Enrique Duran Jimenez
	 * 26/06/2012
	 *  PROMOCION_SATIC_A   = "3";
	 *  PROMOCION_SATIC_B   = "4";
	 *  PROMOCION_ORDINARIO = "6";
	 *	PROMOCION_SBC       = "7";
	 */
	function jsValidaFolio(){
		
		var folio = $("form#promocionConsultaForm #nuFoliopromocion").val();
		var array_folio = folio.split("/");
		if(array_folio != undefined && array_folio.length > 1){
			var tipo = array_folio[1];			
			var regresa;
			if(tipo != ''){
				if(tipo == 'EXO'){
					regresa = PROMOCION_ORDINARIO;
				}else if(tipo == 'SBC'){
					regresa = PROMOCION_SBC;
				}else if(tipo == 'SATICB'){
					regresa = PROMOCION_SATIC_B;
				}else if(tipo == 'SATICA'){
					regresa = PROMOCION_SATIC_A;
				}else if(tipo == 'EX'){
					regresa = PROMOCION_COSTRUCCION;
				}	
			}else{
				regresa = '';
			}
		}else{
			regresa = '';
		}
		return regresa;

	}
	
	/**
	 * Funcion que complementa (Deshabilita) el funcionamiento de la invitacion desde el flujo de 
	 * seguimiento de Promocion SaticB. al finalizar el flujo de invitacion
	 */
	function completaFlujoInvSegSaticb(){
		$("form#seguimientoSaticbTABForm #fecSolCorrIniSaticb").html('<label >' + $("form#invitacionAntecedenteForm #fechaIncialinv").val() + '</label>');
		$("form#seguimientoSaticbTABForm #fecSolCorrFinSaticb").html('<label >' + $("form#invitacionAntecedenteForm #fechaFinalInv").val() + '</label>');
		$("form#seguimientoSaticbTABForm #fecOficioInvSaticb").html('<label >' + $("form#invitacionAntecedenteForm #fechaEmisionFec").val() + '</label>');
		
	
		//seguimiento tab
		$('form#seguimientoSaticbTABForm input[type=text]').prop('disabled','disabled');
		$('form#seguimientoSaticbTABForm input[type=text]').removeClass("red");
		$('form#seguimientoSaticbTABForm input[type=button]').prop('disabled','disabled');
		$('form#seguimientoSaticbTABForm input[type=checkbox]').prop('disabled','disabled');
		$('form#seguimientoSaticbTABForm input[type=checkbox]').removeClass("red");
		$("#spnFecNotSaticb").hide();
		
		//estatus Obra
		/* $('form#estatusObraTABForm input[type=text]').prop('disabled','disabled');
		$('form#estatusObraTABForm input[type=text]').removeClass("red");
		$('form#estatusObraTABForm input[type=button]').prop('disabled','disabled');
		$('form#estatusObraTABForm input[type=checkbox]').prop('disabled','disabled');
		$('form#estatusObraTABForm input[type=checkbox]').removeClass("red");
		*/
		
		$( "form#seguimientoSaticbTABForm #fecSolCorrIniSaticb").val($("form#invitacionAntecedenteForm #fechaIncialinv").val());
		$( "form#seguimientoSaticbTABForm #fecSolCorrFinSaticb").val($("form#invitacionAntecedenteForm #fechaFinalInv").val());
		$( "form#seguimientoSaticbTABForm #fecOficioInvSaticb").val($("form#invitacionAntecedenteForm #fechaEmisionFec").val());
		
		setTabDesHabilitado("cancelacionGenericoTab_saticb");
		setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
		setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
		setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
		setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
		setTabDesHabilitado("estatusObraTAB_saticb");
		setTabDesHabilitado("regularizarObraGenericoTAB_saticb");
		
		guardarSeguimientoGenerico();
		guardarPeriodosPromocionSeguimientoSaticB($("form#invitacionAntecedenteForm #fechaIncialinv").val(),$("form#invitacionAntecedenteForm #fechaFinalInv").val());
		
	}
	
	
	function seleccionaPromocion(obj){
		if(obj.value != ''){
			var URL = getAppContextParaJS() + "/promocion/consulta/setupPromocion.do?tipoPromocion="+obj.value;
			
			document.promocionConsultaForm.action = URL;

			bloquear();
			document.promocionConsultaForm.submit();
		}	
		
	}
	
	
	function jsValidaFecPeriodoBusqueda(){
		
		 $("form#promocionConsultaForm #labelFechas").html('');
		var fecIni = $("form#promocionConsultaForm #fechaIncial").val();
		 var fecFinal = $("form#promocionConsultaForm #fechaFinal").val();
		 if(fecIni != '' && fecFinal != ''){
			 if(jsValidaFechas(fecIni,fecFinal)){
				 $("form#promocionConsultaForm #fechaFinal").val(fecFinal);
				 $("form#promocionConsultaForm #labelFechas").html('');
			 }else{
				 $("form#promocionConsultaForm #fechaFinal").val('');
				 $("form#promocionConsultaForm #labelFechas").html('<label class="etiquetaError">La fecha de emisi&oacute;n de: no puede ser mayor a la fecha a:</label>');
			 }
		 }
}
	
	function jsValidaFecEmisionInvitacion(){
		$("form#invitacionAntecedenteForm #labelFecEmision").html('');
		$("form#labelFechaEmisionGuardar#labelFechaEmisionGuardar").html('');
		 var fecIni = $("form#invitacionAntecedenteForm #fechaNotificacionInv").val();
		 var fecFinal = $("form#invitacionAntecedenteForm #fechaEmisionFec").val();
		 if(fecIni != '' && fecFinal != ''){
			 if(jsValidaFechas(fecIni,fecFinal)){
				 $("form#invitacionAntecedenteForm #fechaEmisionFec").val(fecFinal);
				 $("form#invitacionAntecedenteForm #labelFecEmision").html('');
				 if($("form#invitacionAntecedenteForm #fechaFinalInv").val() != ''){
					 //actualizacion de los periodos de correccion en la invitacion de los seg Promocion 
					 var fechaIniTemp =  $('form#invitacionAntecedenteForm #fechaIncialinv').val();
					 // $('form#invitacionAntecedenteForm #fechaIncialinv').datepicker('option', 'maxDate', fecFinal);
					 //	 $('form#invitacionAntecedenteForm #fechaFinalInv').datepicker('option', 'maxDate', fecFinal);
					 //alert("1fechaIncialinv" + $('form#invitacionAntecedenteForm #fechaIncialinv').val());
					 //alert("1fechaFinalInv"+ $('form#invitacionAntecedenteForm #fechaFinalInv').val());
					 
					 //$("form#invitacionAntecedenteForm #fechaFinalInv").val(replaceAll(fecFinal,"/","-") );
					 //$("form#invitacionAntecedenteForm #fechaFinal").val(replaceAll(fecFinal,"/","-"));
					// alert("afechaFinalInv: " + $("form#invitacionAntecedenteForm #fechaFinalInv").val());
					// alert("afechaFinal: " +$("form#invitacionAntecedenteForm #fechaFinal").val());
					
					 $('form#invitacionAntecedenteForm #fechaIncialinv').val(replaceAll(fechaIniTemp,"/","-") );
					 //$('form#invitacionAntecedenteForm #fechaIncial').val(replaceAll(fechaIniTemp,"/","-"));
					// alert("fechaIncialinv: " + $("form#invitacionAntecedenteForm #fechaIncialinv").val());
					// alert("fechaIncial: " + $("form#invitacionAntecedenteForm #fechaIncial").val());
					 
				 }
			 }else{
				 $("form#invitacionAntecedenteForm #fechaEmisionFec").val('');
				 $("form#invitacionAntecedenteForm #labelFecEmision").html('<label class="etiquetaError">La fecha de emisi&oacute;n no puede ser menor a la fecha notificaci&oacute;n</label>');
			 }
		 }
	}

	function jsValidaFecEmisionAl(){
		$("form#labelFechaEmisionGuardar#labelFechaEmisionGuardar").html('');
		 var fecIni = $("form#invitacionAntecedenteForm #fechaEmisionFec").val();
		 var fecFinal = $("form#invitacionAntecedenteForm #fechaFinalInv").val();
		  $('form#invitacionAntecedenteForm #fechaFinalInv').val(replaceAll(fecFinal ,"/","-") );
		  fecFinal = $('form#invitacionAntecedenteForm #fechaFinalInv').val();
		 if(fecIni != '' && fecFinal != ''){
			 if(jsValidaFechas(fecFinal,fecIni)){
				 $("form#invitacionAntecedenteForm #fechaFinalInv").val(fecFinal);
				 jsValidaFecEmisionDel()
				 return true;
			 }else{
				 
				 $("form#invitacionAntecedenteForm #labelFechaEmisionGuardar").html('<label class="etiquetaError">La fecha final del periodo a corregir no puede ser mayor a la fecha de emisi&oacute;n</label>');
				 $("form#invitacionAntecedenteForm #fechaFinalInv").val(fecFinal);
				 jsValidaFecEmisionInvitacion();
				 return false;
			 }
		 }
	}
	
	function jsValidaFecEmisionDel(){
		 //var fecEmision = $("form#invitacionAntecedenteForm #fechaEmisionFec").val();
		 var fechaIni =  $('form#invitacionAntecedenteForm #fechaIncialinv').val();
		 var fecFinal = $("form#invitacionAntecedenteForm #fechaFinalInv").val();

		 $('form#invitacionAntecedenteForm #fechaIncial').val(fechaIni);
		 $("form#invitacionAntecedenteForm #fechaFinal").val(fecFinal);
		 
		 var fechaIniHdn =  $('form#invitacionAntecedenteForm #fechaIncial').val();
		 var fecFinalHdn = $("form#invitacionAntecedenteForm #fechaFinal").val();
		 
		 if(fechaIni != '' && fecFinal != ''){
			 if(jsValidaFechas(fechaIni,fecFinal)){

				 return true;
			 }else{
				 $("form#invitacionAntecedenteForm #labelFechaEmisionGuardar").html('<label class="etiquetaError">La fecha final del periodo a corregir no puede ser menor a la fecha de inicio</label>');
				 $("form#invitacionAntecedenteForm #fechaFinalInv").val(fecFinal);
				 return false;
			 }
		 
		 }
		 
	}
	
	/*
	 * Funcion para inicializar las variables globales del seguimiento de promocion
	 * de los tabs: Cancelacion, Derivar a subdelegacion, fiscalizacion y dictamen 
	 *  
	 * @author Gerardo Salazar
	 */	
	function limpiaVariablesSeguimiento() {	
		limpiaCampoForma("cancelacionGenericoTabForm", "fechaNotificacionOficioGenerico");
		limpiaCampoForma("cancelacionGenericoTabForm", "fechaEmisionOficioGenerico");
		limpiaCampoForma("derivarSubdelegacionGenericoTabForm", "fechaEmisionOficioGenerico");
		limpiaCampoForma("derivarSubdelegacionGenericoTabForm", "fechaNotificacionOficioGenerico");	
		limpiaCampoForma("autAviDictamenGenericoTabForm", "fechaEmisionOficioGenerico");
		limpiaCampoForma("autAviDictamenGenericoTabForm", "fechaNotificacionOficioDictamenGenerico");	
		limpiaCampoForma("derivarFiscalizacionTABForm", "fecDerivacionGenerica");
		// limpiaCampoForma("forma", "campo");	
	}
	