var objDataTableCriterioSeleccion;
var confirmDialogPromocion;
var validaDomicilioGeo;
var mensajeDialog;
var fnLlenaCritSel;
var flagRPValido=false;

var Selector = function(cveSelector, registroPatronal, descripcion, razonSocial){
    this.cveSelector = '';  
    this.registroPatronal = '';
    this.descripcion = '';
    this.razonSocial = '';
    
}

var Deteccion = function(domCalle, numCodigopostal, numNroext, numNroint, refColonia){
	this.domCalle = '';
	this.numCodigopostal = '';
	this.numNroext = '';
	this.numNroint = '';
	this.refColonia = '';
}

var Promocion = function(cveSelector, fechaNotificacion, numeroOficio, fechaPromocion, observaciones, regPatronal, idCriterioSeleccion, cveFkPatron){
	this.cveSelector = 0;
	this.fechaNotificacion = '';
	this.numeroOficio = '';
	this.fechaPromocion = '';
	this.observaciones = '';
	this.regPatronal = '';
	this.idCriterioSeleccion = 0;
	this.cveFkPatron = '';
}

var selector = new Selector('','','','');
var deteccion = new Deteccion('','','','','');
var promocion = new Promocion(0,'','','','','',0,null);



$(document).ready(function() {
	
	$("#fechaPromocionID").datepicker( { 
		dateFormat: 'dd/mm/yy',
		beforeShowDay: $.datepicker.noWeekends,
		onSelect: function(dateText, inst) { 
			$("form#promocionOrdinarioSubForm #labelFecOficio").html('');
	    }
	});
	
	

		$('#fechaPromocionID').datepicker('option', 'maxDate', getFechaServidor());
		$('#fechaNotificacion').datepicker('option', 'maxDate',getFechaServidor());


		$('#fechaPromocionID').datepicker('option', 'minDate', getFechaServidorMenos45Dias());
		$('#fechaNotificacion').datepicker('option', 'minDate', getFechaServidorMenos45Dias());

	
	$("#criterioSeleccionadoDivID").hide();
	
	$(function() {		   
	   $('#tableCriteriosSeleccion tbody').delegate("tr", "click", muestraConfirmacionPromocion);
	});
	
	objDataTableCriterioSeleccion = $('#tableCriteriosSeleccion').dataTable({
		bJQueryUI : true,
		bFilter : false,
		bInfo:true,
		bSort: false,
		"bPaginate": true,
		"bAutoWidth" : false,
		"bServerSide" : true,
		"iDeferLoading": 0,
		"sAjaxSource" : "ordinario/buscarCriteriosSeleccion.do",
		"aoColumns" : [ {
							"sTitle" : "Promover",
							fnRender :function(oObj){
								var retVal = '<input type="radio" value="' + oObj.aData['cveSelector'] +'" id="radioTable" class="radioDeteccion" name="radio"/> ';
								return retVal;
							 }, 
							 aTargets: [0]
						}, {
							"sTitle" : "Registro Patronal",
							"mDataProp" : "registroPatronal",
							"sClass": "dtCenterClassColumn"
						}, {
							  "sTitle" : "Nombre o Raz\u00F3n Social",
							  "mDataProp" : "razonSocial",
							  "Class": "dtCenterClassColumn"
						}
					],
		"fnServerData" : function(sSource, aoData, fnCallback) {
			var wrapper = new Object();
			wrapper.aoData = aoData;								
	
			var oForm = $("#promocionOrdinarioSubForm").toObject({mode:'first'});
			wrapper.oForm = oForm;
			bloquear();
			$.postJSON(sSource, wrapper, function(data) {										
				fnCallback(data);					
			}).error(function(data){ 
				validarSesionExpirada(data);				 
				mensajeDialog.html(data.responseText);
				mensajeDialog.dialog("open");
			}).complete(function(){
				desbloquear(); 
			});	
		}
	});
	
	confirmDialogPromocion = $("#dialog-confirm-promocion").dialog({
		autoOpen: false,
		resizable: false,
		height:160,
		width: 500,
		modal: true,
		buttons: {
			"Si deseo promocionar este registro patronal": function() {
				$( this ).dialog( "close" );
				
				$("#wrapperDataTableCriteriosSeleccion").hide();
				$("#cargaCedula").hide();
				$("#criterioSeleccionadoDivID").show();
				
				$("#tituloArriba").html('Generar nuevo folio  Promoci\u00F3n Ordinario');
				$("#resgistroPatronalSeleccionadoID").html('<b>' + selector.registroPatronal + '</b>');
				$("#descCriterioseleccion").html('<b>' + selector.descripcion + '</b>');
				$("#razonSocialSeleccionadoID").html('<b>' + selector.razonSocial + '</b>');
				
				
				//alert(selector.cveSelector);
				$.postJSON("ordinario/quitarDomicilioGeo.do", promocion,function(data) {
					
				}).error(function(data){
					validarSesionExpirada(data);
				}).complete(function(){
					
				});
			},
			"NO deseo promocionar este registro patronal": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
	validaDomicilioGeo = $("#promocionOrdinarioSubForm").validate({
		 rules: {			 
			 domCalle: {
				 required: true
			 },
			 refColonia: {
				 required: true
			 },			 
			 numNroext: {
				 required: true
			 },
			 numCodigopostal: {
				 required: function(element) {
		  				return (document.getElementById('numCodigopostal').value == '' || document.getElementById('numCodigopostal').value == '-2') ;
		  	         },
		  	       min : 1
			 },
			 fechaPromocionID: {
				 required: true
			 },
			 numeroOficioID: {
				 required: true
			 }, 
			 regPat: {
				 required: true
			 },
			 razonSocial: {
				 required: true
			 }
		 }
	});
	
	$("a#btnBuscarCriterios").click(function(event){
		objDataTableCriterioSeleccion.fnDraw();
	});
	
//	$("a#btnPromocionar").click(function(event){
//		
//		if(validaRequeridos() == true){
//			validarRegPatronal('regPat');
//			 var sPatron = '{"registroPatronal":"'+document.getElementById('regPat').value+'"}';
//			  var patron = jQuery.parseJSON(sPatron);
//			  //domicilio.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getCveEnt();
//			  var userEntidad = '';
//			  var domGeoEntidad = '';
//			  bloquear();
//			  $.postJSON("captura/obtenerDomGeo.do", patron, function(domGeo) {
//				  domGeoEntidad = domGeo.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt; 
//				  bloquear();
//				  $.postJSON("captura/validaRegPat.do", patron, function(data) {
//					  if(data!=null){
//						  bloquear();
//						  userEntidad = data.ubicacion.municipio.sacEntidadFederativa.cvePk;
//	// ENRIQUE DURAN JIMENEZ  - Se comenta esta parte donde valida que el registro patronal sea de la misma cve_entidad que el domicilio
//	//                          capturado, devido a que no coinciden los valores de la cve entidad en las tablas de DG_CAT_ESTADO y SAC_ENTIDADFED
//	//					        11/07/2012
//	//						if(parseInt(userEntidad) != parseInt(domGeoEntidad)){
//	//							//alert('El domicilio Geografico Seleccionado no corresponde con la ubicacion de la subdelegacion');
//	//							alert('El domicilio Geografico Seleccionado no corresponde con la entidad donde se ubica la subdelegaci\u00F3n');
//	//							return false;
//	//						}else{
//									promocion.cveSelector = selector.cveSelector;
//									promocion.idCriterioSeleccion = $("#critSele").val();
//									promocion.fechaNotificacion = $("#fechaNotificacion").val();
//									promocion.fechaPromocion = $("#fechaPromocionID").val();
//									promocion.observaciones = $("#observacionesId").val();
//									promocion.numeroOficio = $("#numeroOficioID").val();
//									promocion.regPatronal = $("#regPat").val();
//									$.postJSON("captura/promocionar.do", promocion,function(data) {									
//	//									$("#observacionesId").val(JSON.stringify(data, null, 4));
//										mensajeDialog.html('El n\u00FAmero de registro patronal '+ $("#regPat").val() +' ha sido promovido con el folio :' + data.nuFoliopromocion);
//										mensajeDialog.dialog("open");
//									}).error(function(data){
//										validarSesionExpirada(data);
//										mensajeDialog.html(data.responseText);
//										mensajeDialog.dialog("open");
//									}).complete(function(){
//										limpiaFormularioInicial();
//										llenaCriterioSeleccion()
//										mostrarCriteriosSeleccion();
//										//objDataTableCriterioSeleccion.fnDraw();
//										desbloquear();
//									});
//								
//							//}  
//					  }
//					  if(data==null){
//						  alert('El patr\u00F3n no existe o no esta activo');
//						  document.getElementById(regPat).value = '';
//						  desbloquear();
//						  return false;
//					  }
//					  
//					  
//				
//				  }).error(function(data){ 
//						alert('Ocurrio un error al consultar al patr\u00F3n, intentelo nuevamente por favor');
//						document.getElementById(regPat).value = '';
//						validarSesionExpirada(data);
//					}).complete(function(data){
//						
//					});
//	          }).error(function(domGeo){ 
//					alert('Ocurrio un error al consultar al patr\u00F3n, intentelo nuevamente por favor');
//					document.getElementById('regPat').value = '';
//					
//					validarSesionExpirada(domGeo);
//				}).complete(function(domGeo){
//					
//				});
//			  
//			  
//			}
//		});
	
	
	$("a#btnPromocionar").click(function(event){
		if($("#regPat").val()!='' && $("#razonSocial").val()==''){
			alert("Favor de validar el registro patronal");
		}else if(validaRequeridos() == true){
			var registro = $("form#promocionOrdinarioSubForm #regPat").val();
			var fechaPromocion = $("form#promocionOrdinarioSubForm #fechaPromocionID").val();
			var numeroOficio = $("form#promocionOrdinarioSubForm #numeroOficioID").val();
			var cveSubdel = $("form#promocionOrdinarioSubForm #cveSubdelegacionHdnEx").val();
			var cveDel =  $("form#promocionOrdinarioSubForm #cveDelegacionHdnEx").val();
			
			var sPromModel = '{"regPatron":"'+registro+'","nuOficiopro":"'+numeroOficio+'","fechaOficio":"'+fechaPromocion+
			'","cveSubdel":"'+cveSubdel+'","cveDel":"'+cveDel+'"}';
			var promocionModel = jQuery.parseJSON(sPromModel);
			
			bloquear();
			//Validar si existe una promocion para ese RegPat, para el mismo año y num oficio
			$.postJSON("captura/validaPromocionExistePatron.do", promocionModel, function(data) {
			if(data== null){
				
			
				$.postJSON("captura/validaPatron.do", registro, function(data) {
						if(data!=null){
							promocion.cveSelector = selector.cveSelector;
							promocion.idCriterioSeleccion = $("#critSele").val();
							promocion.fechaNotificacion = $("#fechaNotificacion").val();
							promocion.fechaPromocion = $("#fechaPromocionID").val();
							promocion.observaciones = $("#observacionesId").val();
							promocion.numeroOficio = $("#numeroOficioID").val();
							promocion.regPatronal = $("#regPat").val();
							promocion.cveFkPatron = data.cvePK;
							validaOficio(promocion);				
							
					}else if(data==null){
						  alert('El patr\u00F3n no existe o no esta activo');
						  desbloquear();
						  return false;
					} 
				
			}).error(function(data){ 
					("form#promocionOrdinarioSubForm #cveFkPatron").val('');
					alert('Ocurrio un error al consultar al patr\u00F3n, intentelo nuevamente por favor');
					document.getElementById(regPat).value = '';
					validarSesionExpirada(data);
			}).complete(function(data){
				
			});//fin validaPatron
			  
			}else{
				alert("Ya existe una Promoci\u00F3n generada con la misma informaci\u00F3n, intente cambiando los datos")
				desbloquear();
			}
		}).error(function(data){ 
				alert('Ocurrio un error al validar si existe una Promoci\u00F3n, intentelo nuevamente por favor');
				validarSesionExpirada(data);
		}).complete(function(data){
			
			
		});
			  
			}//fin validar requeridos
		});//fin  click de boton 
		
		mensajeDialog = $("#dialog-mensaje").dialog({
			autoOpen: false,
			modal: true,
			resizable: true,
			width: 500,
			buttons: {
				Ok: function() {
					$(this).dialog("close");
				}
			}
		});
		
		
		llenaCriterioSeleccion();
		//fnLlenaCritSel();
		mostrarCriteriosSeleccion();
	
});

function validaOficio(promocion){
	$.postJSON_Sync("captura/validaNumeroOficioPromocion.do", promocion,function(data) {									
		//								$("#observacionesId").val(JSON.stringify(data, null, 4));
		if((data.bandera=="true")){
			mensajeDialog.html('Folio no promovido,el n\u00FAmero oficio de la promocion ya existe ');
			mensajeDialog.dialog("open");
			desbloquear();
			return false;
		}else{
			bloquear();
			promoverFolio(promocion);
			return true;
		}
	})
}

function promoverFolio(promocion){

	$.postJSON("captura/promocionar.do", promocion,function(data) {									
		//								$("#observacionesId").val(JSON.stringify(data, null, 4));
		if((data.bandera=="true")){
			mensajeDialog.html('Folio no promovido,el n\u00FAmero oficio de la promocion ya existe ');
			mensajeDialog.dialog("open");
		}else{
			mensajeDialog.html('El n\u00FAmero de registro patronal '+ $("#regPat").val() +' ha sido promovido con el folio :' + data.nuFoliopromocion);
			mensajeDialog.dialog("open");	
			limpiaFormularioInicial();
		}
	}).error(function(data){
		validarSesionExpirada(data);
		mensajeDialog.html(data.responseText);
		mensajeDialog.dialog("open");
	}).complete(function(){		
		llenaCriterioSeleccion()
		mostrarCriteriosSeleccion();
		desbloquear();
	});

}
function formatDate(value){
	return value.getDate() + "/" + value.getMonth()+1 + "/" + value.getYear();
}


function limpiaFormularioInicial(){
	$("form#promocionOrdinarioSubForm #critSele").val('-1');
	$("form#promocionOrdinarioSubForm #fechaNotificacion").val('');
	$("form#promocionOrdinarioSubForm #fechaPromocionID").val('');
	$("form#promocionOrdinarioSubForm #numeroRegistroPatronalInputID").val('');
	$("form#promocionOrdinarioSubForm #observacionesId").val('');
	$("form#promocionOrdinarioSubForm #numeroOficioID").val('');
	$("form#promocionOrdinarioSubForm #domCalle").val('');
	
	$("form#promocionOrdinarioSubForm #refColonia").val('');
	$("form#promocionOrdinarioSubForm #numNroint").val('');
	$("form#promocionOrdinarioSubForm #numNroext").val('');
	$("form#promocionOrdinarioSubForm #numCodigopostal").val('');
	$("form#promocionOrdinarioSubForm #razonSocial").val('');
	$("form#promocionOrdinarioSubForm #regPat").val('');
	$('select#critSele').html('');
}

function muestraConfirmacionPromocion(){
	$('#radioTable',this).attr("checked", "checked");
	var registroPatronal = $("td:eq(1)", this).text();
	var razon = $("td:eq(2)", this).text();
	var cveSelector = $('#:checked').val();
	var txt = '<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>El registro Patronal: ' + registroPatronal +' sera promovido</p>'
	$("#dialog-confirm-promocion").html(txt);
	selector.cveSelector = cveSelector;  
	selector.registroPatronal = registroPatronal;
	selector.razonSocial = razon;
	selector.descripcion = $('#idCriterio :selected').text(); 
	confirmDialogPromocion.dialog("open");
}

function mostrarCriteriosSeleccion(){
	$("#wrapperDataTableCriteriosSeleccion").show();
	$("#cargaCedula").show();
	$("#criterioSeleccionadoDivID").hide();
}

function mostarDomGeo(context,fuente){
	
	var url = context + '/catalogo/deteccion/obtenerDomicilioSession.do';
	var func;
	if(fuente == 'registro'){
		func="refrescar('promocionOrdinarioSubForm', '"+url+"')";
		$("form#promocionOrdinarioSubForm #labelDomGeo").html('');
	}if(fuente == 'buscar'){
		func="refrescar('promocionOrdinarioSubForm', '"+url+"')";
	}if(fuente == 'validar'){
		func="refrescar('promocionOrdinarioSubForm', '"+url+")";
	}
	
	var resultado = openWindowregistraDomicilioInegi(context,'catalogo/deteccion/deteccionDomGeografico.do',null,func);
}

function refrescar(form, url){
	$.postJSON(url, deteccion,function(data) {
		$("form#"+form+" #domCalle").val(data.domCalle);
		$("form#"+form+" #refColonia").val(data.refColonia);
		//alert("data.numNroint :" + data.numNroint);
		if(data.numNroint != null){
			$("form#"+form+" #numNroint").val(data.numNroint);	
		}
		$("form#"+form+" #numNroext").val(data.numNroext);
		$("form#"+form+" #numCodigopostal").val(data.numCodigopostal);
		
	}).error(function(data){
		validarSesionExpirada(data);
		mensajeDialog.html(data.responseText);
		mensajeDialog.dialog("open");
	}).complete(function(){
		
	});
}

function llenaCriterioSeleccion(){
	var idTipo = 2;
	var idOrigen = 3;
	var sParam =  '["'+idTipo+'","'+idOrigen+'"]';
	 var param = jQuery.parseJSON(sParam);
	 $.postJSON("captura/consultaComboCriterios.do", param, function(data) {
		 if(data!= null){
			 var combo = document.getElementById("critSele");
			 combo.add(new Option('--Por Favor Seleccione--', '-1'));
				for(var i = 0 ; i < data.length ; i++){
					combo.add(new Option(data[i][3], data[i][0]));
				}
		 }
		//Agrega las opciones al control
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});

}

function validarRegPatronal() {
	
	var registro = $("form#promocionOrdinarioSubForm #regPat").val();
	if(registro != '' && registro.length == 10){							 
		 bloquear();
			$.postJSON("captura/validaPatron.do",registro,function(data) { 
				if(data == null){
					$("#labelRegPatronal").html('<label class="etiquetaError">El registro patronal no es v&aacute;lido</label>');
					$("#razonSocial").val('');					
				}
				else if(data != null && data.cveRespuestaWS <= JSERROR_WS){
						$("#labelRegPatronal").html('<label class="etiquetaError">'+ data.descRespuestaWS +'</label>');
						$("#razonSocial").val('');						  
					 } else if(data.razonSocial != null){
						 nombre = data.razonSocial;
						 $("#razonSocial").val(data.razonSocial);
						  $("form#promocionOrdinarioSubForm #cveSubdelegacionHdnEx").val(data.ubicacion.municipio.sacSubdelegacion.cvePk);
  						  $("form#promocionOrdinarioSubForm #cveDelegacionHdnEx").val(data.ubicacion.municipio.sacSubdelegacion.sacDelegacion.cvePk);
  						flagRPValido=true;
  						$("#labelRegPatronal").html('');
					 }					

			}).error(function(data){ 
				alert('Ocurri\u00F3 un error al consultar al patr\u00F3n, intentelo nuevamente por favor');
				validarSesionExpirada(data);
			}).complete(function(){
				desbloquear();	
			});
	 }else{
		 $("#labelRegPatronal").html('<label class="etiquetaError">Capture un registro patronal v&aacute;lido</label>');
		 $("#razonSocial").val('');	
	 }
}


/**
 * Funcion que valida en pantalla los campos que son requeridos y no han sido capturados
 */
function validaRequeridos(){
	
	$("form#promocionOrdinarioSubForm #labelFecOficio").html('');
	$("form#promocionOrdinarioSubForm #labelNumOficio").html('');
	$("form#promocionOrdinarioSubForm #labelDomGeo").html('');
	$("form#promocionOrdinarioSubForm #labelRegPatronal").html('');
	$("form#promocionOrdinarioSubForm #labelCriterioSeleccion").html('');
	var resp = false;
	if($("form#promocionOrdinarioSubForm #critSele").val() == '-1'){
		$("form#promocionOrdinarioSubForm #labelCriterioSeleccion").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("form#promocionOrdinarioSubForm #fechaPromocionID").val() == ''){
		$("form#promocionOrdinarioSubForm #labelFecOficio").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("form#promocionOrdinarioSubForm #numeroOficioID").val() == ''){
		$("form#promocionOrdinarioSubForm #labelNumOficio").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("form#promocionOrdinarioSubForm #domCalle").val() == '' && $("form#promocionOrdinarioSubForm #refColonia").val() == ''){
		$("form#promocionOrdinarioSubForm #labelDomGeo").html('<label class="etiquetaError">Domicilio Geogr&aacute;fico Requerido</label>');
	}
	if($("form#promocionOrdinarioSubForm #regPat").val() == ''){
		$("form#promocionOrdinarioSubForm #labelRegPatronal").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	
	
	
//	if(!flagRPValido){
//		$("form#promocionOrdinarioSubForm #labelRegPatronal").html('<label class="etiquetaError">El Registro Patronal debe ser validado</label>');
//	}
	if(		$("form#promocionOrdinarioSubForm #critSele").val() != '-1' &&
			$("form#promocionOrdinarioSubForm #fechaPromocionID").val() != '' && $("form#promocionOrdinarioSubForm #numeroOficioID").val() != '' &&
			$("form#promocionOrdinarioSubForm #domCalle").val() != '' && $("form#promocionOrdinarioSubForm #refColonia").val() != '' &&
			$("form#promocionOrdinarioSubForm #regPat").val() != '' && $("form#promocionOrdinarioSubForm #regPat").val().length == 10 )
	{
		resp = true;
	}
	return resp;
}

function jsCortaTextArea(valor){
	if(valor.length > 200){
		valor = valor.substring(0,199);
	}
	
	$("#observacionesId").val(valor);
}

function resetRP(){
	$('form#promocionOrdinarioSubForm #labelRegPatronal').html('');
	$('#razonSocial').val("");
	flagRPValido=false;
}