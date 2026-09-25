/**
 * JS para el soporte del catalogo de division.
 */

var flagDomCorregido=false;
var idDataTable 	= "#dtPatronesInscritos";
var idDgModificar 	= "#dgPatInsModificar";


var oDtPatronesIscritos;
var oDgModificar;
var dataEmpty = {"sEcho":"undefined","iTotalRecords":0,"iTotalDisplayRecords":0,"sColumns":null,"aaData":[]};
var selloDigitalSolCorr;
var ROL_USUARIO_INTERNET=5;
var folioTemporal="";
var respuestFirmadoSimple;



function recuperaObraDomi(){
	var sPatron = '{"patron":'+'"DOM_OBRA"}';
	var crcPatron = jQuery.parseJSON(sPatron);
	bloquear();
	$.postJSON("correcion/actualizaDomObra.do", crcPatron, function(data) {

		if(data!=null){
			var numExt = "";
			var numInt = "";
			
			if(data.numextnum!=null){numExt=data.numextnum;}
			if(data.numextalf!=null){numExt = numExt + "-" + data.numextalf;}
			
			if(data.numintnum!=null){numInt=data.numintnum;}
			if(data.numintalf!=null){numInt = numInt + "-" + data.numintalf;}
			
			$('#wrapperDialogSolCorrObra,#correccionForm,#calleRegPatInputObra').val(data.dgVialidadByCveViaPrin.nomVia);
			$('#wrapperDialogSolCorrObra,#correccionForm,#numExteriorRegPatInputObra').val(numExt);
			$('#wrapperDialogSolCorrObra,#correccionForm,#numInteriorRegPatInputObra').val(numInt);
			$('#wrapperDialogSolCorrObra,#correccionForm,#coloniaRegPatInputObra').val(data.dgAsentamiento.nomAsen);
			$('#wrapperDialogSolCorrObra,#correccionForm,#municipioRegPatInputObra').val(data.dgCatLocalidad.dgCatMunicipio.nomMun);
			$('#wrapperDialogSolCorrObra,#correccionForm,#localidadRegPatInputObra').val(data.dgCatLocalidad.nomLoc);
			$('#wrapperDialogSolCorrObra,#correccionForm,#entidadFederativaRegPatInputObra').val(data.dgCatLocalidad.dgCatMunicipio.dgCatEstado.nomEnt);
			$('#wrapperDialogSolCorrObra,#correccionForm,#cpRegPatInputObra').val(data.dgCodigosPostales.id.codigo);
		}else{
			alert("Domicilio Invalido");
		}	
		desbloquear();
		
	}).error(function(data){ 
		desbloquear();
		alert("error" + data);
	}).complete(function(){
		desbloquear();
	});
	
	
}

$(document).ready(function() {
	
	
	errorDialog = $("#dialog-error").dialog({
		autoOpen: false,
		modal: true,
		resizable: true,
		width: 930,
		buttons: {
			Ok: function() {
				$(this).dialog("close");
			}
		}
	});
	
	
	jQuery.validator.addMethod('regexp', function(value, element, param) {
	     return this.optional(element) || value.match(param);
	  },'El valor no coincide con la estructura requerida.');
	
	jQuery.validator.addMethod('regexpPrima', function(value, element, param) {
	     return this.optional(element) || value.match(param);
	  },'Requerido, Valor Inv\u00e1lido');
	
	divControl('hide','divBtnCorrigeDom');
	
	 $( "#fechaInicial, #fechaFinal" ).datepicker( { dateFormat: 'dd-mm-yy' });
	 $( "#fechaPresentacion" ).datepicker( { dateFormat: 'dd-mm-yy' });
	 
	 
	 $( "#fechaPresentacion" ).datepicker('option', 'minDate', getFechaServidorMenos14Dias());
	 $( "#fechaPresentacion" ).datepicker('option', 'maxDate', getFechaServidor());
	 $.postJSON("correcion/obtenerFechaServidor.do", null,function(data) {
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(data){


			$('form#correccionForm #fechaInicial, form#correccionForm #fechaFinal').datepicker('option', 'maxDate', data.responseText);

			$('form#correccionForm #fechaInicial, form#correccionForm #fechaFinal').datepicker('option', 'beforeShowDay', null);
		
		});

	/**
	 * Inicializacion del data table
	 */
	oDtPatronesIscritos = $(idDataTable).dataTable({
		bJQueryUI : true,
		bFilter : false,
		bInfo:true,
		bSort: false,
		"bPaginate": true,
		"bAutoWidth" : false,
		"bServerSide" :	true,
		"iDeferLoading": 0,
		"aoColumns" : [ {
			fnRender :function(oObj){
				var retVal = '<input type="radio" value="' +
				oObj.aData['registroPatronalSD'] +'" id="radioTable" class="radioClase" name="radio" onclick=""/> ';
				return retVal;
			}, 
			aTargets: [0]
			},
		        {
				"sTitle" : "Registro Patronal",
				"mDataProp" : "registroPatronalSD",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Domicilio",
				"mDataProp" : "domicilioCompleto",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "No Trabajadores",
				"mDataProp" : "trabajadores",
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "Clase",
				"mDataProp" : "clase",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Fraccion",
				"mDataProp" : "fraccion",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Prima",
				"mDataProp" : "prima",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Actividad",
				"mDataProp" : "actividad",
				"sClass": "dtCenterClassColumn"
			}
			],"bProcessing" : true,
			"sAjaxSource" : 'correcion/paginar.do',
			"fnServerData" : function(sSource, aoData, fnCallback) {
				
				
				var wrapper = new Object();
				wrapper.aoData = aoData;

				var oForm = $("#correccionForm2").toObject({mode:'first'});
				wrapper.oForm = oForm;
				
				$.postJSON(sSource, wrapper, function(data) {
					fnCallback(data);
				 }).error(function(datas){ 
						validarSesionExpirada(datas);				 
				 });
			}
		});


	
	// Dialog de Elemento Nuevo			
	oDgModificar = $(idDgModificar).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 600,
		beforeClose :function(event,ui){
		    limpiarFormulario("#patInsFormModificar");
		},
		buttons: {
			"Aceptar": function() { 
				
				var regpat = $('#registroPatronal').val();
				var trab = $('#trabajadores').val();
				var clase = $('#clase').val();
				var fraccion = $('#fraccion').val();
				var prima = $('#prima').val();
				var actividad = $('#actividad').val();

				var sRegpat = '"registroPatronal":'+'"'+regpat+'"';
				var sTrab = '"trabajadores":'+'"'+trab+'"';
				var sClase = '"clase":'+'"'+clase+'"';
				var sFraccion = '"fraccion":'+'"'+fraccion+'"';
				var sPrima = '"prima":'+'"'+prima+'"';
				var sActividad = '"actividad":'+'"'+actividad+'"';
				var sPatron = '{'+sRegpat+','+sTrab+','+sClase+','+sFraccion+','+sPrima+','+sActividad+'}';
				var patron = jQuery.parseJSON(sPatron);
				$.postJSON("correcion/actualizaPatIns.do", patron, function(data) {
					if(data==null)
						inicializaPosicionPaginador();						
				}).error(function(data){ 
					validarSesionExpirada(data);
					alert("error" + data);
				}).complete(function(){
				});
				$(this).dialog("close"); 
			}, 
			"Cancelar": function() { 
				$(this).dialog("close"); 
			} 
		}
	});
	
	
	
	
	$("a#btnBuscarPatronDom").click(function(event){
		event.preventDefault();
		var idPatron = $('#regPatronInputDom').val();
		if(idPatron.length>0)
		{
			// Buscamos el elemento
			var idPatron = $('#regPatronInputDom').val();
			var sPatron = '{"patron":'+'"'+idPatron+'"}';
			var satPatron = jQuery.parseJSON(sPatron);
			bloquear();
			
			$.postJSON("correcion/consultarDom.do", satPatron, function(data) {
				desbloquear();
				flagDomCorregido=false;
				if(data==null)
				{
					alert('Registro patronal inv\u00e1lido o se encuentra dado de baja');
				}
				else
				{

					$('#wrapperDialogSolCorr2,#correccionForm,#calleRegPatInputDom').val(data.patronPrincipal.ubicacion.calle);
					$('#wrapperDialogSolCorr2,#correccionForm,#numExteriorRegPatInputDom').val(data.patronPrincipal.ubicacion.numeroExterior);
					$('#wrapperDialogSolCorr2,#correccionForm,#numInteriorRegPatInputDom').val(data.patronPrincipal.ubicacion.numeroInterior);
					$('#wrapperDialogSolCorr2,#correccionForm,#coloniaRegPatInputDom').val(data.patronPrincipal.ubicacion.colonia);
					$('#wrapperDialogSolCorr2,#correccionForm,#municipioRegPatInputDom').val(data.patronPrincipal.ubicacion.municipio.nombre);
					$('#wrapperDialogSolCorr2,#correccionForm,#localidadRegPatInputDom').val(data.patronPrincipal.ubicacion.municipio.nombre);
					$('#wrapperDialogSolCorr2,#correccionForm,#codigoPostalRegPatInputDom').val(data.patronPrincipal.ubicacion.codigoPostal);
					$('#wrapperDialogSolCorr2,#correccionForm,#entidadFederativaRegPatInputDom').val(data.patronPrincipal.ubicacion.municipio.sacEntidadFederativa.nomNombre);
					$('#wrapperDialogSolCorr2,#correccionForm,#telefonoRegPatInput').val(data.patronPrincipal.ubicacion.telefono);
					$('#wrapperDialogSolCorr2,#correccionForm,#emailRegPatInput').val(data.patronPrincipal.ubicacion.eMail);
					$('#wrapperDialogSolCorr2,#correccionForm,#subdelegacionRegPatInput').val(data.patronPrincipal.ubicacion.municipio.sacSubdelegacion.nomNombre);
					
					divControl('show','divBtnCorrigeDom');
					
				}
			}).error(function(data){ 
				desbloquear();
				validarSesionExpirada(data);
				alert("Error: Conexi\u00f3n no disponible, intente de nuevo");
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
			
		}
		else
		{
			alert('Se requiere de un registro patronal');
		}
	});

	

	$("a#btnBuscarPatron").click(function(event){
		event.preventDefault();
		
		divControl('hide','dgControladorVRP');
		
		document.getElementById("regPatronInputDom").disabled = false;
		document.getElementById("regPatronInputDom").value = "";
		
		document.getElementById("chkSolicitudObra").checked = false;		
		document.getElementById("chkSolicitudObra").disabled = false;
		
		document.getElementById("numeroRegistroObra").disabled = true;
		document.getElementById("numeroRegistroObra").value="";
		
		$(":radio[name='unoVariosRp'][value='" + 1 + "']").attr('checked', true);
		$(":radio[name='unoVariosRp'][value='" + 1 + "']").attr('disabled', false);
		$(":radio[name='unoVariosRp'][value='" + 2 + "']").attr('disabled', false);
		
		divControl('hide','divHeadObra');
		divControl('hide','dgsolicitudCorreccionObra');
	
		$('#wrapperDialogSolCorr2,#correccionForm,#calleRegPatInputDom').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#numExteriorRegPatInputDom').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#numInteriorRegPatInputDom').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#coloniaRegPatInputDom').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#municipioRegPatInputDom').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#localidadRegPatInputDom').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#entidadFederativaRegPatInputDom').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#codigoPostalRegPatInputDom').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#telefonoRegPatInput').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#emailRegPatInput').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#numeroTrabajadores').val("");
		
		
		var idPatron = $('#regPatronPrInput').val();
		if(idPatron.length>0)
		{
			// Buscamos el elemento
			var idPatron = $('#regPatronPrInput').val();
			var sPatron = '{"patron":'+'"'+idPatron+'"}';
			var satPatron = jQuery.parseJSON(sPatron);
			bloquear();
			$.postJSON("correcion/consultar.do", satPatron, function(data) {
				desbloquear();
				if(data==null)
				{
					alert('Registro Patronal invalido');
					limpiarFormulario('#correccionForm');
				}
				else
				{
					if(data.error!=undefined && data.error!=null && data.error!="" ){
						alert(data.error);
						limpiarFormulario('#correccionForm');
						return;
					}
					
					$('#wrapperDialogSolCorr,#correccionForm,#razonSocialRegPatInput').val(data.patronCorregir.razonSocial);

					if(data.personaFisica){
						$('#wrapperDialogSolCorr,#correccionForm,#curpPatInput').val(data.patronCorregir.rfc);
						
					}
					
					$('#wrapperDialogSolCorr5,#correccionForm,#cveAuditorAsignado').val(data.cveAuditorAsignado);
					$('#wrapperDialogSolCorr,#correccionForm,#rfcRegPatInput').val(data.patronCorregir.rfc);
					$('#wrapperDialogSolCorr4,#correccionForm,#calleRegPatInput').val(data.patronCorregir.ubicacion.calle);
					$('#wrapperDialogSolCorr4,#correccionForm,#numExteriorRegPatInput').val(data.patronCorregir.ubicacion.nuExterior);
					$('#wrapperDialogSolCorr4,#correccionForm,#numInteriorRegPatInput').val(data.patronCorregir.ubicacion.nuInterior);
					$('#wrapperDialogSolCorr4,#correccionForm,#coloniaRegPatInput').val(data.patronCorregir.ubicacion.colonia);
					$('#wrapperDialogSolCorr4,#correccionForm,#municipioRegPatInput').val(data.patronCorregir.ubicacion.municipio.nombre);
					$('#wrapperDialogSolCorr4,#correccionForm,#localidadRegPatInput').val(data.patronCorregir.ubicacion.municipio.nombre);
					$('#wrapperDialogSolCorr4,#correccionForm,#cpRegPatInput').val(data.patronCorregir.ubicacion.codigoPostal);
					$('#wrapperDialogSolCorr4,#correccionForm,#entidadFederativaRegPatInput').val(data.patronCorregir.ubicacion.municipio.sacEntidadFederativa.nomNombre);
					$('#wrapperDialogSolCorr5,#correccionForm,#fechaPresentacion').val(data.fechaPresentacion);
					$('#wrapperDialogSolCorr3,#correccionForm,#fechaInicial').val(data.fechaInicial);
					$('#wrapperDialogSolCorr3,#correccionForm,#fechaFinal').val(data.fechaFinal);
					$('#wrapperDialogSolCorr5,#correccionForm,#lugarPresentacion').val(data.patronCorregir.ubicacion.municipio.sacSubdelegacion.nomNombre);
					$('#wrapperDialogSolCorr3,#correccionForm,#fechaFolio').val(data.fechaRecepcionOficio);
					if(data.tipo=="INVITACION")
					{
						$('#wrapperDialogSolCorr,#correccionForm,#folioInput').val(data.folioInvitacion);
						
						var folioInv =data.folioInvitacion+"";
						$("form#correccionForm #fechaInicial").val(castFecha(data.invitacion.fecPeriodoIni));
						$("form#correccionForm #fechaFinal").val(castFecha(data.invitacion.fecPeriodoFin));
						$("form#correccionForm #fechaFolio").val(castFecha(data.invitacion.fecFechanotifi));
						if(folioInv.search("CCI")>0){
							
							document.getElementById("chkSolicitudObra").checked = true;
							document.getElementById("chkSolicitudObra").disabled = true;
							$(":radio[name='unoVariosRp'][value='" + 1 + "']").attr('disabled', true);
							$(":radio[name='unoVariosRp'][value='" + 2 + "']").attr('disabled', true);
							
							showDiv();
						}else if(folioInv.search("CI")>0){

								document.getElementById("chkSolicitudObra").disabled = true;
								document.getElementById("chkSolicitudObra").checked = false;
								//document.getElementById("unoVariosRp").disabled = false;
								
								
								
								
								if(data.unoVariosRp==2){

									$(":radio[name='unoVariosRp'][value='" + 1 + "']").attr('disabled', true);
									$(":radio[name='unoVariosRp'][value='" + 2 + "']").attr('disabled', true);
									
									$(":radio[name='unoVariosRp'][value='" + 2 + "']").attr('checked', true);
									
									divControl('show','dgControladorVRP'); 
									gestionaVariosRPs('VRP');
									divControl('show','domicilioCentroTrabajoDIV');
									
									//
									
									for(var i=0;i<data.lsPatronesInscInvitacion.length;i++){
									
										var sPatron = '{"patron":'+'"'+data.lsPatronesInscInvitacion[i].cveFkPatron+'"}';
										var crcPatron = jQuery.parseJSON(sPatron);
										
										$.postJSON("correcion/addRPInvitacion.do", crcPatron, function(data) {
											paginar();
											inicializaPosicionPaginador();
										}).error(function(data){ 
											desbloquear();
											alert("error" + data);
										}).complete(function(){
											//Instrucciones para el 'complete'
										});

										
									}
									
									//
									
								}else{
									
									$(":radio[name='unoVariosRp'][value='" + 1 + "']").attr('disabled', true);
									$(":radio[name='unoVariosRp'][value='" + 2 + "']").attr('disabled', true);
									
									$(":radio[name='unoVariosRp'][value='" + 1 + "']").attr('checked', true);
									
																		
								}
								 

								
						}
						
						
						
						alert('Se encontr\u00f3 un antecedente de invitaci\u00f3n');
					}
					else
					{
						$('#wrapperDialogSolCorr,#correccionForm,#folioInput').val('');
					}
					if(data.tipo=="PROMOCION")
					{
						alert('Se encontr\u00f3 un antecedente de promoci\u00f3n');
					}
					
					
					
					var radios = document.getElementsByName("tipoSol");
					for (i=0;i<radios.length;i++)
					 {
						if(radios[i].value==data.tipo)
						{
							radios[i].checked = true;
						}
						
					 }
					
				}
			}).error(function(data){ 
				desbloquear();
				validarSesionExpirada(data);
				alert("Error: Conexi\u00f3n no disponible, intente de nuevo");
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
		}
		else
		{
			alert('Se requiere de un registro patronal');
		}

	});

	$("a#btnAddPatron").click(function(event){
		event.preventDefault();
		// Buscamos el elemento
		var idPatron = $('#regPatronInsInput').val();
		var regPatronPrInput = $('#regPatronPrInput').val();
		if(idPatron.length>0)
		{
			if(idPatron==regPatronPrInput)
			{
				alert('El registro patronal ya est\u00e1 incluido en la solicitud de correcci\u00f3n');
			}
			else
			{
				var sPatron = '{"patron":'+'"'+idPatron+'"}';
				var crcPatron = jQuery.parseJSON(sPatron);
				bloquear();
				$.postJSON("correcion/add.do", crcPatron, function(data) {
					desbloquear();
					if(data==null)
					{	
						alert('Registro patronal es invalido');
					}
					else
					{
						$('#wrapperDialogSolCorr6,#correccionForm2,#regPatronInsInput').val('');
						$('#wrapperDialogSolCorr3,#correccionForm,#fechaFolio').val(data.fechaRecepcionOficio);
						//$('#wrapperDialogSolCorr3,#correccionForm,#fechaInicial').val(data.fechaInicial);
						//RBG $('#wrapperDialogSolCorr3,#correccionForm,#fechaFinal').val(data.fechaFinal);
						//RBG $('#wrapperDialogSolCorr,#correccionForm,#folioInput').val(data.folioInvitacion);

						var radios = document.getElementsByName("tipoSol");
						for (i=0;i<radios.length;i++)
						 {
							if(radios[i].value==data.tipo)
							{
								radios[i].checked = true;
							}
							
						 }
					}
					inicializaPosicionPaginador();
				}).error(function(data){ 
					desbloquear();
					validarSesionExpirada(data);
					alert("error" + data);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
			}
		}
		else
		{
			alert('Requiere ingresar un registro patronal');
		}
	});

	$("a#btnDelPatron").click(function(event){
		event.preventDefault();
		var radios = document.getElementsByName("radio");
		var seleccionado = false;
		for (i=0;i<radios.length;i++)
		 {
			if(radios[i].checked)
			{
				var idPatron = radios[i].value;
				var sPatron = '{"patron":'+'"'+idPatron+'"}';
				var crcPatron = jQuery.parseJSON(sPatron);
				// Buscamos el elemento
				bloquear();
				$.postJSON("correcion/del.do", crcPatron, function(data) {
					desbloquear();
					inicializaPosicionPaginador();
					$('#wrapperDialogSolCorr3,#correccionForm,#fechaFolio').val(data.fechaRecepcionOficio);
					$('#wrapperDialogSolCorr3,#correccionForm,#fechaInicial').val(data.fechaInicial);
					$('#wrapperDialogSolCorr3,#correccionForm,#fechaFinal').val(data.fechaFinal);
					$('#wrapperDialogSolCorr,#correccionForm,#folioInput').val(data.folioInvitacion);
					var radios = document.getElementsByName("tipoSol");
					for (i=0;i<radios.length;i++)
					{
						if(radios[i].value==data.tipo)
						{
							radios[i].checked = true;
						}
						
					}
				}).error(function(data){ 
					desbloquear();
					validarSesionExpirada(data);
					alert("error" + data);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
				seleccionado = true;
			}
		 }
		if(seleccionado == false)
		{
			alert('Debe seleccionar un registro de la lista de patrones');
		}
		
	});

	$("a#btnCorrigeDom").click(function(event){
		event.preventDefault();
		var idPatron =  $('#regPatronInputDom').val();
		
		if(idPatron!=null && idPatron!= undefined && idPatron!=""){
			var sPatron = '{"patron":'+'"'+idPatron+'F"}';//Indica F que es par fiscal RP+F
			var crcPatron = jQuery.parseJSON(sPatron);
			bloquear();
			$.postJSON("correcion/setPatron.do", crcPatron, function(data) {
				desbloquear();
				
			}).error(function(data){ 
				desbloquear();
				alert("error" + data);
			}).complete(function(){
				
				despliegaDialog("1");

			});
		}else{
			
			alert("El registro patronal del domicilio fiscal es obligatorio");
			
		}
		

	});

	$("a#btnCorrigeCentro").click(function(event){
		event.preventDefault();
		var idPatron =  $('#regPatronPrInput').val();
		
		if(idPatron!=null && idPatron!= undefined && idPatron!=""){
		
			
			var sPatron = '{"patron":'+'"'+idPatron+'C"}';//indica que es de Correccion de Centro Trabajo RP+C
			var crcPatron = jQuery.parseJSON(sPatron);
			bloquear();
			$.postJSON("correcion/setPatron.do", crcPatron, function(data) {
				desbloquear();
			}).error(function(data){ 
				desbloquear();
				validarSesionExpirada(data);
				alert("error" + data);
			}).complete(function(){
				despliegaDialog("2");
			});
		}else{
			
			alert("El registro patronal del domicilio de trabajo es obligatorio");
			
		}
		
	});
	
function actualizaCorreccionDOmi(){
	var sPatron = '{"patron":'+'"DOM_OBRA"}';
	var crcPatron = jQuery.parseJSON(sPatron);
	bloquear();
	$.postJSON("correcion/actualizaDomObra.do", crcPatron, function(data) {

		if(data!=null){
			var numExt = "";
			var numInt = "";
			
			if(data.numextnum!=null){numExt=data.numextnum;}
			if(data.numextalf!=null){numExt = numExt + "-" + data.numextalf;}
			
			if(data.numintnum!=null){numInt=data.numintnum;}
			if(data.numintalf!=null){numInt = numInt + "-" + data.numintalf;}
			
			$('#wrapperDialogSolCorrObra,#correccionForm,#calleRegPatInputObra').val(data.dgVialidadByCveViaPrin.nomVia);
			$('#wrapperDialogSolCorrObra,#correccionForm,#numExteriorRegPatInputObra').val(numExt);
			$('#wrapperDialogSolCorrObra,#correccionForm,#numInteriorRegPatInputObra').val(numInt);
			$('#wrapperDialogSolCorrObra,#correccionForm,#coloniaRegPatInputObra').val(data.dgAsentamiento.nomAsen);
			$('#wrapperDialogSolCorrObra,#correccionForm,#municipioRegPatInputObra').val(data.dgCatLocalidad.dgCatMunicipio.nomMun);
			$('#wrapperDialogSolCorrObra,#correccionForm,#localidadRegPatInputObra').val(data.dgCatLocalidad.nomLoc);
			$('#wrapperDialogSolCorrObra,#correccionForm,#entidadFederativaRegPatInputObra').val(data.dgCatLocalidad.dgCatMunicipio.dgCatEstado.nomEnt);
			$('#wrapperDialogSolCorrObra,#correccionForm,#cpRegPatInputObra').val(data.dgCodigosPostales.id.codigo);
		}else{
			alert("Domicilio Invalido");
		}	
		desbloquear();
		
	}).error(function(data){ 
		desbloquear();
		alert("error" + data);
	}).complete(function(){
		desbloquear();
	});
}	
	
	
	
	$("a#btnCorrigeDomObra").click(function(event){
		event.preventDefault();
		
		
		
		var context =  $('#cont').val();
		var resultado = openWindowregistraDomicilioInegi(context,'solicitud/correcion/solicitudDomGeograficoObra.do',"DOM_OBRA","recuperaObraDomi()");
		
		var sPatron = '{"patron":'+'"DOM_OBRA"}';
		var crcPatron = jQuery.parseJSON(sPatron);
		bloquear();
		$.postJSON("correcion/actualizaDomObra.do", crcPatron, function(data) {
	
			if(data!=null){
				var numExt = "";
				var numInt = "";
				
				if(data.numextnum!=null){numExt=data.numextnum;}
				if(data.numextalf!=null){numExt = numExt + "-" + data.numextalf;}
				
				if(data.numintnum!=null){numInt=data.numintnum;}
				if(data.numintalf!=null){numInt = numInt + "-" + data.numintalf;}
				
				$('#wrapperDialogSolCorrObra,#correccionForm,#calleRegPatInputObra').val(data.dgVialidadByCveViaPrin.nomVia);
				$('#wrapperDialogSolCorrObra,#correccionForm,#numExteriorRegPatInputObra').val(numExt);
				$('#wrapperDialogSolCorrObra,#correccionForm,#numInteriorRegPatInputObra').val(numInt);
				$('#wrapperDialogSolCorrObra,#correccionForm,#coloniaRegPatInputObra').val(data.dgAsentamiento.nomAsen);
				$('#wrapperDialogSolCorrObra,#correccionForm,#municipioRegPatInputObra').val(data.dgCatLocalidad.dgCatMunicipio.nomMun);
				$('#wrapperDialogSolCorrObra,#correccionForm,#localidadRegPatInputObra').val(data.dgCatLocalidad.nomLoc);
				$('#wrapperDialogSolCorrObra,#correccionForm,#entidadFederativaRegPatInputObra').val(data.dgCatLocalidad.dgCatMunicipio.dgCatEstado.nomEnt);
				$('#wrapperDialogSolCorrObra,#correccionForm,#cpRegPatInputObra').val(data.dgCodigosPostales.id.codigo);
			}else{
				alert("Domicilio Invalido");
			}	
			desbloquear();
			
		}).error(function(data){ 
			desbloquear();
			alert("error" + data);
		}).complete(function(){
			desbloquear();
		});
				
	});

	$("a#btnModPatron").click(function(event){
		event.preventDefault();
		var radios = document.getElementsByName("radio");
		var seleccionado = false;
		for (i=0;i<radios.length;i++)
		 {
			if(radios[i].checked)
			{
				var idPatron = radios[i].value;
				var sPatron = '{"patron":'+'"'+idPatron+'"}';
				var crcPatron = jQuery.parseJSON(sPatron);
				bloquear();
				$.postJSON("correcion/setPatron.do", crcPatron, function(data) {
					desbloquear();
				}).error(function(data){ 
					desbloquear();
					validarSesionExpirada(data);
					alert("error" + data);
				}).complete(function(){
					despliegaDialog("3");
				});
				seleccionado = true;
			}
		 }
		if(seleccionado == false)
		{
			alert('Debe seleccionar un registro de la lista de patrones');
		}
	});
	
	
	$("a#chkSolicitudObra").click(function(event){
		$("#divHeadObra").show("fast");
		$("#dgsolicitudCorreccionObra").show("fast");

	});

	
	$("a#btnGuardar").click(function(event){
		event.preventDefault();
		
		if(!flagDomCorregido){
			alert("Se requiere corregir el domicilio fiscal");
			return;
		}
		
		if(validaCaptura.form() && validaFechaPeriodo()){
			if($('#calleRegPatInputObra').val()=="" && document.getElementById("chkSolicitudObra").checked){				
				alert("Se requiere capturar el domicilio de la obra");				
				return;				
			}
						
			if(confirm("Est\u00e1 Seguro de Generar la solicitud con la informaci\u00f3n recopilada?")){
				folioTemporal=recuperaFolioTemporal();
				$("#folioTemporalForma").val(folioTemporal);
				
		//		bloquear();
				
			//	abrirDialogoFirma();
				
//				firmarDocumento();
				
				if((recuperarRolUsuario()!=ROL_USUARIO_INTERNET)){
					selloDigitalSolCorr=recuperaSelloImss();
					if(selloDigitalSolCorr==null){
						alert("No se puede firmar el documento");
						return;
					}
					recuperaMensaje(TRAMITE_CORRECCION);
					objMensajeFirma.cveMensaje=null;						
					registrarFirmado(selloDigitalSolCorr.tramite,selloDigitalSolCorr.sello,generaCadenaPrincipal());					
				}else{
					if(confirm(recuperaMensaje(TRAMITE_CORRECCION))){
						var param=generaParamatrosSolCorr();
						if(param!=null){
							firmarDocumentoSISCONET('contenedorFirma',callBackFirmaSolCorr,param);
						}else{
							alert("No se puede generar el documento,favor de reintentar");
						}			
					}
				}
				
				
				
				//registrarFirmado(); //este metodo es el q guarda
				
		//		desbloquear();						
			}
		}else{
			alert("Existen errores en la solicitud, favor de revisar");
		}
	});

	$("a#btnMod2Patron").click(function(event){
		event.preventDefault();
		var radios = document.getElementsByName("radio");
		var seleccionado = false;
		for (i=0;i<radios.length;i++)
		 {
			if(radios[i].checked)
			{
				$('#wrapperDialogModif,#patInsFormModificar,#registroPatronal').val(radios[i].value);
				oDgModificar.dialog('open');
				seleccionado = true;
			}
		 }
		if(seleccionado == false)
		{
			alert('Debe seleccionar un registro de la lista de patrones');
		}

	});

	
	
	$("a#btnBuscarObra").click(function(event){
		event.preventDefault();

		if(document.getElementById("chkSolicitudObra").checked)
		{
			// Buscamos el elemento
			var idObra = $('#numeroRegistroObra').val();
			if(idObra.length>0)
			{
				var sObra = '{"numeroObra":'+'"'+idObra+'"}';
				var satPatron = jQuery.parseJSON(sObra);
				bloquear();
				$.postJSON("correcion/consultarObra.do", satPatron, function(data) {
					desbloquear();
					
					if(data!=null && data.error!=null && data.error!=undefined && data.error!=""){
						alert(data.error);
						return;
					}
					
					if(data==null)
					{
						alert('El n\u00famero de la obra no existe o no pertenece al p\u00e1tron a corregir');
						$('#wrapperDialogSolCorr3,#correccionForm,#numeroRegistroObra').val("");
						$('#wrapperDialogSolCorrObra,#correccionForm,#calleRegPatInputObra').val("");
						$('#wrapperDialogSolCorrObra,#correccionForm,#numExteriorRegPatInputObra').val("");
						$('#wrapperDialogSolCorrObra,#correccionForm,#numInteriorRegPatInputObra').val("");
						$('#wrapperDialogSolCorrObra,#correccionForm,#coloniaRegPatInputObra').val("");
						$('#wrapperDialogSolCorrObra,#correccionForm,#municipioRegPatInputObra').val("");
						$('#wrapperDialogSolCorrObra,#correccionForm,#localidadRegPatInputObra').val("");
						$('#wrapperDialogSolCorrObra,#correccionForm,#codigoPostalRegPatInputObra').val("");
						$('#wrapperDialogSolCorrObra,#correccionForm,#subdelegacionRegPatInputObra').val("");
						$('#wrapperDialogSolCorrObra,#correccionForm,#entidadFederativaRegPatInputObra').val("");
					}
					else
					{
						$('#wrapperDialogSolCorrObra,#correccionForm,#calleRegPatInputObra').val(data.ubicacion.calle);
						$('#wrapperDialogSolCorrObra,#correccionForm,#numExteriorRegPatInputObra').val(data.ubicacion.numeroExterior);
						$('#wrapperDialogSolCorrObra,#correccionForm,#numInteriorRegPatInputObra').val(data.ubicacion.numeroInterior);
						$('#wrapperDialogSolCorrObra,#correccionForm,#coloniaRegPatInputObra').val(data.ubicacion.colonia);
						$('#wrapperDialogSolCorrObra,#correccionForm,#municipioRegPatInputObra').val(data.ubicacion.municipio.nombre);
						$('#wrapperDialogSolCorrObra,#correccionForm,#localidadRegPatInputObra').val(data.ubicacion.municipio.nombre);
						$('#wrapperDialogSolCorrObra,#correccionForm,#codigoPostalRegPatInputObra').val(data.ubicacion.codigoPostal);
						$('#wrapperDialogSolCorrObra,#correccionForm,#subdelegacionRegPatInputObra').val(data.ubicacion.municipio.sacSubdelegacion.nomNombre);
						$('#wrapperDialogSolCorrObra,#correccionForm,#entidadFederativaRegPatInputObra').val(data.ubicacion.municipio.sacEntidadFederativa.nomNombre);
						//RBG $('#wrapperDialogSolCorr3,#correccionForm,#fechaInicial').val(data.fechaInicial);
						//RBG $('#wrapperDialogSolCorr3,#correccionForm,#fechaFinal').val(data.fechaFinal);
						
						
					}
				}).error(function(data){ 
					desbloquear();
					validarSesionExpirada(data);
					alert("Error: La conexi\u00f3n no est\u00e1 disponible, intente de nuevo");
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
			}
			else
			{
				alert('Se requiere capture el n\u00famero de registro de obra');
			}
		}
		else
		{
			alert('Debe seleccionar el recuadro de construcci\u00f3n');
		}
	});
	
	
	 var validaCaptura = $("#correccionForm").validate({
	  	  rules: {
	  		  regPatronPrInput: {
	  	      required: true,
	  	      maxlength: 10,
	  	      minlength:10,
	  	      alphanumeric: true
	  	  },
	  	      regPatronInputDom:{
			  required: true,
	  		  maxlength: 10,
	  		  minlength:10,
	   		  alphanumeric: true
	  	  },
	  	  rfcRegPatInput:{
	  		alphanumeric: true,
  	    	maxlength : 13,
  	    	minlength : 10,
  	    	regexp: /^[a-zA-Z&]{3,4}(\d{6})((\D|\d){3})?$/
	  	  },
	  	curpPatInput:{
	  		maxlength : 18,
  	    	minlength : 18,
  	    	alphanumeric: true,  	    	
  	    	regexp: /^[a-zA-Z]{1}[aeiouxAEIOUX]{1}[a-zA-Z]{2}\d{2}([0][1-9]|[1][0-2])([0][1-9]|[1][0-9]|[2][0-9]|[3][0-1])[hmHM]{1}(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE|  as|bc|bs|cc|cs|ch|cl|cm|df|dg|gt|gr|hg|jc|mc|mn|ms|nt|nl|oc|pl|qt|qr|sp|sp|sr|tc|ts|tl|vz|yn|zs|ne)[^aeiouAEIOU]{3}(\d|[a-zA-Z])\d$/ 
	  	},
	  	 telefonoRegPatInput:{
			  required: true,
	  		  maxlength: 10,
	  		  minlength:10,
    	  	  digits: true
	  	  },
	  	  	  emailRegPatInput:{
 			  required: true,
	  		  maxlength: 30,
	  		  email: true
	  	  },
	  	  	  numeroTrabajadores:{
 			  required: true,
	  		  maxlength: 10,
	  		  digits: true
	  	  },
	  	  	  actividadRegPatInput:{
 			  required: true,
	  		  maxlength: 60,
	  		  alphanumeric: true
	  	  },
	  	  	  claseRegPatInput:{
 			  required: true,
	  		  maxlength: 5,
	  		  number: true
	  	  },
	  	  	  fraccionRegPatInput:{
 			  required: true,
	  		  maxlength: 5,
	  		  number: true
	  	  },
	  	  	  primaRegPatInput:{
 			  required: true,
	  		  maxlength: 8,
	  		  alphanumeric: true,
	  		  regexpPrima: /^[\d]{1,2}([.][\d]{1,5})?$/
	  	  },
	  	  	  txRepLegalInput:{
 			  required: true,
	  		  maxlength: 300,
	  		  alphanumeric: true
	  	  	  }
	  	  },
//	  	messages:{
//	  		regPatronPrInput:"Requerido",
//	  		regPatronInputDom:"Requerido",
//	  		telefonoRegPatInput:"No escriba menos de 10 caracteres",
//	  		emailRegPatInput:"Requerido",
//	  		numeroTrabajadores:"Requerido",
//	  		actividadRegPatInput:"Requerido",
//	  		claseRegPatInput:"Requerido(Num\u00e9rico)",
//	  		fraccionRegPatInput:"Requerido(Num\u00e9rico)",
//	  		txRepLegalInput:"Requerido"
//	  	  }
	    	messages:{
		  		regPatronPrInput:"Este campo es obligatorio",
		  		regPatronInputDom:"Este campo es obligatorio",
		  		telefonoRegPatInput:"Este campo es obligatorio",
		  		emailRegPatInput:"Este campo es obligatorio",
		  		numeroTrabajadores:"Este campo es obligatorio",
		  		actividadRegPatInput:"Este campo es obligatorio",
		  		claseRegPatInput:"Este campo es obligatorio(Num\u00e9rico)",
		  		fraccionRegPatInput:"Este campo es obligatorio(Num\u00e9rico)",
		  		txRepLegalInput:"Este campo es obligatorio"
		  	  }
	  });
	 
	 
	 var validaPatronCorr = $("#correccionForm").validate({
	  	  rules: {
	  		  regPatronPrInput: {
	  	      required: true,
	  	      maxlength: 10,
	  	      minlength:10,
	  	      alphanumeric: true
		  	  }
	  	  },
		  	messages:{
		  		regPatronPrInput:"Se requiere Ingresar un registro patronal a corregir"
		  	  }
		  });

	 var validaPatronDom = $("#correccionForm").validate({
	  	  rules: {
	  		  regPatronInputDom: {
	  	      required: true,
	  	      maxlength: 10,
	  	      minlength:10,
	  	      alphanumeric: true
		  	  }
	  	  },
		  	messages:{
		  		regPatronInputDom:"Se requiere especificar el registro patronal del domicilio fiscal"
		  	  }
		  });

	 
	 if($("#regPresentanteLegal").val()!='null' && $("#regPresentanteLegal").val()!=undefined && $("#regPresentanteLegal").val()!=''){
		 $("#txRepLegalInput").val($("#regPresentanteLegal").val());
	 }
	 
	 if($("#regPatronPrInput").val()!=""){
		 $("#btnBuscarPatron").trigger("click");				
	 }
	 
	 
	 
});//$(document).ready(function()


//Inicializa el paginador
function inicializaPosicionPaginador(){
	oDtPatronesIscritos.fnDisplayStart(0);
}


function paginar(){
	oDtPatronesIscritos.fnDraw();
}


function showDiv(){
	if(document.getElementById("chkSolicitudObra").checked){
		
		document.getElementById("numeroRegistroObra").disabled = false;		
		$("#divHeadObra").show("fast");
		$("#dgsolicitudCorreccionObra").show("fast");
		$('#wrapperDialogSolCorr3,#correccionForm,#numeroRegistroObra').removeAttr("readonly");
	}
	else
	{
		document.getElementById("numeroRegistroObra").disabled = true;
		$("#divHeadObra").hide();
		$("#dgsolicitudCorreccionObra").hide();
		$('#wrapperDialogSolCorr3,#correccionForm,#numeroRegistroObra').val("");
		$('#wrapperDialogSolCorr3,#correccionForm,#numeroRegistroObra').attr("readonly", true);
	}
}


function despliegaDialog(tpoAct){
	var context =  $('#cont').val();
	var URL = "solicitud/correcion/solicitudDomGeografico.do";
	
//	if(tpoAct=="3"){
//		URL = URL + "?bloquearEstado=false";
//	}else if(tpoAct=="1"){
//		if($("#regPatronInputDom").val()!=$("#regPatronPrInput").val()){
//			URL = URL + "?bloquearEstado=false";
//		}else{
//			URL = URL + "?bloquearEstado=true";
//		}		
//	}else if(tpoAct=="2"){
//		URL = URL + "?bloquearEstado=false";
//	}
	
	var funcion;
	var parametro="";
	
	if(tpoAct=="1"){
		actualizaDatosDom();
		parametro=$("#regPatronInputDom").val()+"F";//Fiscal
		funcion="actualizaDatosDom();";
	}
	if(tpoAct=="2"){
		actualizaDatosCorr();
		parametro=$("#regPatronPrInput").val()+"C";//Tipo de centro trabajo
		funcion="actualizaDatosCorr()";
	}
	if(tpoAct=="3"){
		var radios = document.getElementsByName("radio");
		var seleccionado = false;
		var idPatron;
		for (i=0;i<radios.length;i++){
			if(radios[i].checked){
				idPatron = radios[i].value;
			}
		 }	
		
		
		actualizaDatosIns();
		parametro=idPatron;
		funcion="actualizaDatosIns();"
	}
	
	var resultado = openWindowregistraDomicilioInegi(context,URL,parametro,funcion);
}


function actualizaDatosDom(){
	var idPatron =  $('#regPatronInputDom').val();
	var sPatron = '{"patron":'+'"'+idPatron+'F"}';
	var satPatron = jQuery.parseJSON(sPatron);
	bloquear();
	$.postJSON("correcion/actualizaRegPat.do", satPatron, function(data) {
		desbloquear();
		flagDomCorregido=true;
		
		if(data!=null){
			$('#wrapperDialogSolCorr2,#correccionForm,#calleRegPatInputDom').val(data.calle);
			
			$('#wrapperDialogSolCorr2,#correccionForm,#numExteriorRegPatInputDom').val(data.numeroExterior);
			
			$('#wrapperDialogSolCorr2,#correccionForm,#numInteriorRegPatInputDom').val(data.numeroInterior);
			
			$('#wrapperDialogSolCorr2,#correccionForm,#coloniaRegPatInputDom').val(data.colonia);
			
			$('#wrapperDialogSolCorr2,#correccionForm,#municipioRegPatInputDom').val(data.municipio);
			
			$('#wrapperDialogSolCorr2,#correccionForm,#localidadRegPatInputDom').val(data.localidad);
			
			$('#wrapperDialogSolCorr2,#correccionForm,#entidadFederativaRegPatInputDom').val(data.entidadFederativa);
			
			$('#wrapperDialogSolCorr2,#correccionForm,#codigoPostalRegPatInputDom').val(data.codigoPostal);
			
			if($('#regPatronPrInput').val()==$('#regPatronInputDom').val()){
				
			
				$('#wrapperDialogSolCorr4,#correccionForm,#calleRegPatInput').val(data.calle);
			
				$('#wrapperDialogSolCorr4,#correccionForm,#numExteriorRegPatInput').val(data.numeroExterior);
			
				$('#wrapperDialogSolCorr4,#correccionForm,#numInteriorRegPatInput').val(data.numeroInterior);
			
				$('#wrapperDialogSolCorr4,#correccionForm,#coloniaRegPatInput').val(data.colonia);
			
				$('#wrapperDialogSolCorr4,#correccionForm,#municipioRegPatInput').val(data.municipio);
			
				$('#wrapperDialogSolCorr4,#correccionForm,#localidadRegPatInput').val(data.localidad);
			
				$('#wrapperDialogSolCorr4,#correccionForm,#entidadFederativaRegPatInput').val(data.entidadFederativa);
			
				$('#wrapperDialogSolCorr4,#correccionForm,#cpRegPatInput').val(data.codigoPostal);
			
			}
		}
		
	}).error(function(data){ 
		desbloquear();
		validarSesionExpirada(data);
		alert("error" + data);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
}

function actualizaDatosCorr(){
	var idPatron =  $('#regPatronPrInput').val();
	var sPatron = '{"patron":'+'"'+idPatron+'C"}';
	var satPatron = jQuery.parseJSON(sPatron);
	bloquear();
	$.postJSON("correcion/actualizaRegPat.do", satPatron, function(data) {
		desbloquear();
		
		if(data!=null){
			
			$('#wrapperDialogSolCorr4,#correccionForm,#calleRegPatInput').val(data.calle);
			$('#wrapperDialogSolCorr4,#correccionForm,#numExteriorRegPatInput').val(data.numeroExterior);
			$('#wrapperDialogSolCorr4,#correccionForm,#numInteriorRegPatInput').val(data.numeroInterior);
			$('#wrapperDialogSolCorr4,#correccionForm,#coloniaRegPatInput').val(data.colonia);
			$('#wrapperDialogSolCorr4,#correccionForm,#municipioRegPatInput').val(data.municipio);
			$('#wrapperDialogSolCorr4,#correccionForm,#localidadRegPatInput').val(data.localidad);
			$('#wrapperDialogSolCorr4,#correccionForm,#entidadFederativaRegPatInput').val(data.entidadFederativa);
			$('#wrapperDialogSolCorr4,#correccionForm,#cpRegPatInput').val(data.codigoPostal);
			
		}
		
		
	}).error(function(data){ 
		desbloquear();
		validarSesionExpirada(data);
		alert("error" + data);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
	
}

function actualizaDatosIns(){
	var idPatron =  $('#regPatronPrInput').val();
	var sPatron = '{"patron":'+'"'+idPatron+'"}';
	var satPatron = jQuery.parseJSON(sPatron);
	bloquear();
	$.postJSON("correcion/actualizaInscritos.do", satPatron, function(data) {
		desbloquear();
		inicializaPosicionPaginador();
	}).error(function(data){ 
		desbloquear();
		validarSesionExpirada(data);
		alert("error" + data);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
	
}

function gestionaVariosRPs(estatus){
	
	if(estatus=="VRP"){
		$("#regPatronInputDom").val($("#regPatronPrInput").val());
		document.getElementById("regPatronInputDom").disabled = true;
		document.getElementById("chkSolicitudObra").checked = false;		
		try{ $("#btnBuscarPatronDom").trigger('click'); }catch(err){}
	}else if(estatus=="RP"){
		$("#regPatronInputDom").val("");
		document.getElementById("regPatronInputDom").disabled = false;
		
		$('#wrapperDialogSolCorr2,#correccionForm,#calleRegPatInputDom').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#numExteriorRegPatInputDom').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#numInteriorRegPatInputDom').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#coloniaRegPatInputDom').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#municipioRegPatInputDom').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#localidadRegPatInputDom').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#codigoPostalRegPatInputDom').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#entidadFederativaRegPatInputDom').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#telefonoRegPatInput').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#emailRegPatInput').val("");
		$('#wrapperDialogSolCorr2,#correccionForm,#subdelegacionRegPatInput').val("");
		
	}else{
		
		alert("ERROR: gestionaVariosRPs(): Solo existen los estatus: vrp: Varios registros patronales \n y rp: Un registo patronal");
	}
		
	
}

function validaSeleccionConstrucion(){
	
	if(document.getElementById("chkSolicitudObra").checked){
		alert("Solo podra presentar su solicitud de correccion por un registro patronal");
		divControl('hide','dgControladorVRP');
		$(":radio[name='unoVariosRp'][value='" + 1 + "']").attr('checked', true);
		document.getElementById("regPatronInputDom").disabled = false;
		document.getElementById("idTipoSolicitud").value = 1;
	}else{
		document.getElementById("idTipoSolicitud").value = 0;
	}

}


function validaExistenciaObra(obj){
	
	if(obj.value==""){
		divControl('show','btnCorrigeDomObra');
	}else{
		divControl('hide','btnCorrigeDomObra');
	}
}

function validaAntecedentesCambioFecha(){
	divControl('hide','divHeadObra');
	divControl('hide','dgsolicitudCorreccionObra');
	if(	$("input[name=unoVariosRp]:checked").val()!=null){
		
		
	var valorRadio = $("input[name=unoVariosRp]:checked").val();
	if(	valorRadio==1){
	if(confirm("Se realizar\u00e1 la b\u00fasqueda de los antecedentes en este periodo.\n\u00bfDesea Continuar?")){
		document.getElementById("chkSolicitudObra").checked = false;		
		document.getElementById("chkSolicitudObra").disabled = false;
		document.getElementById("numeroRegistroObra").disabled = true;
		var fechaInicial = document.getElementById("fechaInicial").value;
		var fechaFinal = document.getElementById("fechaFinal").value;
		

		if(!comparaFechas(fechaInicial,fechaFinal,"-")){
			alert("La fecha inicial no puede ser mayor a la fecha final");
			return false;
		}
		
		//
		var idPatron = $('#regPatronPrInput').val();
		if(idPatron.length>0)
		{
			// Buscamos el elemento
			var idPatron = $('#regPatronPrInput').val();
		
			var sIdPatron = '"patron":'+'"'+idPatron+'"';
			var sPeriodoInicial = '"fechaInicial":'+'"'+fechaInicial+'"';
			var sPeriodoFinal = '"fechaFinal":'+'"'+fechaFinal+'"';
			var sPatron = '{'+sIdPatron+','+sPeriodoInicial+','+sPeriodoFinal+'}';
			var satPatron = jQuery.parseJSON(sPatron);

			bloquear();
			
			$.postJSON("correcion/consultar.do", satPatron, function(data) {
				desbloquear();
				if(data==null)
				{
					alert('Registro Patronal invalido');
					limpiarFormulario('#correccionForm');
				}else{
					if(data.error!=undefined && data.error!=null && data.error!="" ){
						alert(data.error);
						limpiarFormulario('#correccionForm');
						return;
					}
					
					
					
					$('#wrapperDialogSolCorr,#correccionForm,#razonSocialRegPatInput').val(data.patronCorregir.razonSocial);

					if(data.personaFisica){
						$('#wrapperDialogSolCorr,#correccionForm,#curpPatInput').val(data.patronCorregir.rfc);
						
					}
					
					//Tiene antecedente
					//if(data.invitacion!=null || data.promocion!=null){
						$('#wrapperDialogSolCorr2,#correccionForm,#regPatronInputDom').val("");					
						$('#wrapperDialogSolCorr2,#correccionForm,#calleRegPatInputDom').val("");					
						$('#wrapperDialogSolCorr2,#correccionForm,#numExteriorRegPatInputDom').val("");					
						$('#wrapperDialogSolCorr2,#correccionForm,#numInteriorRegPatInputDom').val("");					
						$('#wrapperDialogSolCorr2,#correccionForm,#coloniaRegPatInputDom').val("");					
						$('#wrapperDialogSolCorr2,#correccionForm,#municipioRegPatInputDom').val("");					
						$('#wrapperDialogSolCorr2,#correccionForm,#localidadRegPatInputDom').val("");					
						$('#wrapperDialogSolCorr2,#correccionForm,#entidadFederativaRegPatInputDom').val("");					
						$('#wrapperDialogSolCorr2,#correccionForm,#codigoPostalRegPatInputDom').val("");						
//					}else{
//						
//						if($('#wrapperDialogSolCorr2,#correccionForm,#regPatronInputDom').val()!=""	){
//							$("#btnBuscarPatronDom").trigger("click");
//						}
//					}
					
					
					$('#wrapperDialogSolCorr5,#correccionForm,#cveAuditorAsignado').val(data.cveAuditorAsignado);
					$('#wrapperDialogSolCorr,#correccionForm,#rfcRegPatInput').val(data.patronCorregir.rfc);
					$('#wrapperDialogSolCorr4,#correccionForm,#calleRegPatInput').val(data.patronCorregir.ubicacion.calle);
					$('#wrapperDialogSolCorr4,#correccionForm,#numExteriorRegPatInput').val(data.patronCorregir.ubicacion.nuExterior);
					$('#wrapperDialogSolCorr4,#correccionForm,#numInteriorRegPatInput').val(data.patronCorregir.ubicacion.nuInterior);
					$('#wrapperDialogSolCorr4,#correccionForm,#coloniaRegPatInput').val(data.patronCorregir.ubicacion.colonia);
					$('#wrapperDialogSolCorr4,#correccionForm,#municipioRegPatInput').val(data.patronCorregir.ubicacion.municipio.nombre);
					$('#wrapperDialogSolCorr4,#correccionForm,#localidadRegPatInput').val(data.patronCorregir.ubicacion.municipio.nombre);
					$('#wrapperDialogSolCorr4,#correccionForm,#cpRegPatInput').val(data.patronCorregir.ubicacion.codigoPostal);
					$('#wrapperDialogSolCorr4,#correccionForm,#entidadFederativaRegPatInput').val(data.patronCorregir.ubicacion.municipio.sacEntidadFederativa.nomNombre);
					$('#wrapperDialogSolCorr5,#correccionForm,#fechaPresentacion').val(data.fechaPresentacion);
//					$('#wrapperDialogSolCorr3,#correccionForm,#fechaInicial').val(data.fechaInicial);
//					$('#wrapperDialogSolCorr3,#correccionForm,#fechaFinal').val(data.fechaFinal);
					$('#wrapperDialogSolCorr5,#correccionForm,#lugarPresentacion').val(data.patronCorregir.ubicacion.municipio.sacSubdelegacion.nomNombre);
					$('#wrapperDialogSolCorr3,#correccionForm,#fechaFolio').val(data.fechaRecepcionOficio);
					if(data.tipo=="INVITACION")
					{
						$('#wrapperDialogSolCorr,#correccionForm,#folioInput').val(data.folioInvitacion);
						
						var folioInv =data.folioInvitacion+"";
						
						if(folioInv.search("CCI")>0){
							
							document.getElementById("chkSolicitudObra").checked = true;
							document.getElementById("chkSolicitudObra").disabled = true;
							$(":radio[name='unoVariosRp'][value='" + 1 + "']").attr('disabled', true);
							$(":radio[name='unoVariosRp'][value='" + 2 + "']").attr('disabled', true);
							
							showDiv();
						}else if(folioInv.search("CI")>0){

								document.getElementById("chkSolicitudObra").disabled = true;
								document.getElementById("chkSolicitudObra").checked = false;
								//document.getElementById("unoVariosRp").disabled = false;
								
								if(data.unoVariosRp==2){

									$(":radio[name='unoVariosRp'][value='" + 1 + "']").attr('disabled', true);
									$(":radio[name='unoVariosRp'][value='" + 2 + "']").attr('disabled', true);
									
									$(":radio[name='unoVariosRp'][value='" + 2 + "']").attr('checked', true);
									
									divControl('show','dgControladorVRP'); 
									gestionaVariosRPs('VRP');
									divControl('show','domicilioCentroTrabajoDIV');
									
									//
									
									for(var i=0;i<data.lsPatronesInscInvitacion.length;i++){
									
										var sPatron = '{"patron":'+'"'+data.lsPatronesInscInvitacion[i].cveFkPatron+'"}';
										var crcPatron = jQuery.parseJSON(sPatron);
										
										$.postJSON("correcion/addRPInvitacion.do", crcPatron, function(data) {
											paginar();
											inicializaPosicionPaginador();
										}).error(function(data){ 
											desbloquear();
											alert("error" + data);
										}).complete(function(){
											//Instrucciones para el 'complete'
										});

										
									}
									
									//
									
								}else{
									
									$(":radio[name='unoVariosRp'][value='" + 1 + "']").attr('disabled', true);
									$(":radio[name='unoVariosRp'][value='" + 2 + "']").attr('disabled', true);
									
									$(":radio[name='unoVariosRp'][value='" + 1 + "']").attr('checked', true);
									
																		
								}
								 

								
						}
						
						
						
						alert('Se encontr\u00f3 un antecedente de invitaci\u00f3n');
					}
					else
					{
						$('#wrapperDialogSolCorr,#correccionForm,#folioInput').val('');
					}
					if(data.tipo=="PROMOCION")
					{
						if (data.promocion !=null ){
							$('#wrapperDialogSolCorr,#correccionForm,#folioInput').val(data.promocion.nuFoliopromocion);
							alert('Se encontr\u00f3 un antecedente de promoci\u00f3n');
						}						
					}
					
					var radios = document.getElementsByName("tipoSol");
					for (i=0;i<radios.length;i++)
					 {
						if(radios[i].value==data.tipo)
						{
							radios[i].checked = true;
						}
						
					 }
					
				}
			}).error(function(data){ 
				desbloquear();
				validarSesionExpirada(data);
				alert("Error: Conexi�n no disponible, intente de nuevo");
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
		}

		//

	}else{
		document.getElementById("fechaFinal").value ="";
	}
	}else {
		//if(data.invitacion!=null || data.promocion!=null){
					
		$('#wrapperDialogSolCorr2,#correccionForm,#calleRegPatInputDom').val("");					
		$('#wrapperDialogSolCorr2,#correccionForm,#numExteriorRegPatInputDom').val("");					
		$('#wrapperDialogSolCorr2,#correccionForm,#numInteriorRegPatInputDom').val("");					
		$('#wrapperDialogSolCorr2,#correccionForm,#coloniaRegPatInputDom').val("");					
		$('#wrapperDialogSolCorr2,#correccionForm,#municipioRegPatInputDom').val("");					
		$('#wrapperDialogSolCorr2,#correccionForm,#localidadRegPatInputDom').val("");					
		$('#wrapperDialogSolCorr2,#correccionForm,#entidadFederativaRegPatInputDom').val("");					
		$('#wrapperDialogSolCorr2,#correccionForm,#codigoPostalRegPatInputDom').val("");
	}
	}
}

function validaFechaPeriodo(){
		
	var fechaInicial = document.getElementById("fechaInicial").value;
	var fechaFinal = document.getElementById("fechaFinal").value;
		

	if(!comparaFechas(fechaInicial,fechaFinal,"-")){
		alert("La fecha inicial no puede ser mayor a la fecha final");
		return false;
	}
				
	return true;
	
}

function getCadenaOriginal(){

	var cveDelegacion = $('#cveDelegacion').val();	
	cveDelegacion =  $.trim(cveDelegacion);	
	if(cveDelegacion.length=1){
		cveDelegacion = '0' + cveDelegacion;
	}
	
	var cveSubDelegacion = $('#cveSubDelegacion').val();
	cveSubDelegacion =  $.trim(cveSubDelegacion);	
	if(cveSubDelegacion.length=1){
		cveSubDelegacion = '0' + cveSubDelegacion;
	}
	

	var idSubDelegacion = $('#idSubDelegacion').val();
	idSubDelegacion =  $.trim(idSubDelegacion);	

	var regPatronCorregir = $('#regPatronPrInput').val();
	regPatronCorregir =  $.trim(regPatronCorregir);
	
	var regPatronFiscal = $('#regPatronInputDom').val();
	regPatronFiscal =  $.trim(regPatronFiscal);

	var unoVariosRp = $('input[name=unoVariosRp]:checked', '#correccionForm').val();
	unoVariosRp =  $.trim(unoVariosRp);

	var idTipoSolicitud = $('#idTipoSolicitud').val();
	idTipoSolicitud =  $.trim(idTipoSolicitud);
	
	var antecedente = ''; 	
	if($(":radio[name='tipoSol'][value='" + 1 + "']").checked=true){
		antecedente='ESPONTANEA';
	}else if($(":radio[name='tipoSol'][value='" + 2 + "']").checked=true){
		antecedente='PROMOCION';
	}else if($(":radio[name='tipoSol'][value='" + 3 + "']").checked=true){
		antecedente='INVITACION';
	}
	
	var fecIni = $('#fechaInicial').val();
	fecIni =  $.trim(fecIni);
	
	var fecFin = $('#fechaFinal').val();
	fecFin =  $.trim(fecFin);

	var fechaAntecedente = $('#fechaFolio').val();  // checar
	fechaAntecedente =  $.trim(fechaAntecedente);

	var obraSatic = $('#numeroRegistroObra').val();
	obraSatic =  $.trim(obraSatic);	

	var actividadRegPatInput = $('#actividadRegPatInput').val();
	actividadRegPatInput =  $.trim(actividadRegPatInput);
	
	var claseRegPatInput = $('#claseRegPatInput').val();
	claseRegPatInput =  $.trim(claseRegPatInput);
	var fraccionRegPatInput = $('#fraccionRegPatInput').val();
	fraccionRegPatInput =  $.trim(fraccionRegPatInput);
	var primaRegPatInput = $('#primaRegPatInput').val();
	primaRegPatInput =  $.trim(primaRegPatInput);

	var txRepLegalInput = $('#txRepLegalInput').val();
	txRepLegalInput =  $.trim(txRepLegalInput);
	
	var trabajadoresDom = $('#numeroTrabajadores').val();
	trabajadoresDom =  $.trim(trabajadoresDom);
	
	var fechaHora = $('#fechaCadenaOriginal').val();
	fechaHora =  $.trim(fechaHora);
	
	var tipoDocumento= 'CORP-001'
	var procedencia= $('#procedencia').val();
	procedencia =  $.trim(procedencia);
	
	var resultado = cveDelegacion + cveSubDelegacion + '|' + idSubDelegacion + '|' + regPatronCorregir + '|'+regPatronFiscal +'|';
	resultado = resultado + unoVariosRp + '|' + idTipoSolicitud + '|'+antecedente +'|';	
	resultado = resultado + fecIni + '|' + fecFin + '|' + fechaAntecedente +'|';	
	resultado = resultado + obraSatic + '|' + actividadRegPatInput + '|' + claseRegPatInput +'|';	
	resultado = resultado + fraccionRegPatInput+'|'+ primaRegPatInput +'|'+ txRepLegalInput +'|'+ fechaHora +'|';
	resultado = resultado + tipoDocumento+'|'+ procedencia;
	
	return resultado;
}

function registrarFirmado(sello, selloIMSS, cadenaOriginal,urlAcuse,firmaObjeto) {

	bloquear();
	
		var razonSocialRegPatInput = $('#razonSocialRegPatInput').val();
		var unoVariosRp = $('input[name=unoVariosRp]:checked', '#correccionForm').val();
		var curpPatInput = $('#curpPatInput').val();
		var rfcRegPatInput = $('#rfcRegPatInput').val();
		var calleRegPatInput = $('#calleRegPatInput').val();
		var numExteriorRegPatInput = $('#numExteriorRegPatInput').val();
		var numInteriorRegPatInput = $('#numInteriorRegPatInput').val();
		var coloniaRegPatInput = $('#coloniaRegPatInput').val();
		var telefonoRegPatInput = $('#telefonoRegPatInput').val();
		var emailRegPatInput = $('#emailRegPatInput').val();
		var actividadRegPatInput = $('#actividadRegPatInput').val();
		var claseRegPatInput = $('#claseRegPatInput').val();
		var fraccionRegPatInput = $('#fraccionRegPatInput').val();
		var primaRegPatInput = $('#primaRegPatInput').val();
		var trabajadoresDom = $('#numeroTrabajadores').val();
		var txRepLegalInput = $('#txRepLegalInput').val();
		var fecIni = $('#fechaInicial').val();
		var fecFin = $('#fechaFinal').val();
		var fecha = $('#fechaPresentacion').val();
		var chkObra = document.getElementById("chkSolicitudObra").checked;
		var idTipoSolicitud = $('#idTipoSolicitud').val();
		var cveAuditorAsignado = $('#cveAuditorAsignado').val();
		var folioInput=$('#folioInput').val();
		
		
		var srazonSocialRegPatInput = '"razonSocialPatronCorregir":'+'"'+razonSocialRegPatInput+'"';
		var sunoVariosRp = '"unoVariosRp":'+'"'+unoVariosRp+'"';				
		var scurpPatInput = '"curpPatronCorregir":'+'"'+curpPatInput+'"';
		var srfcRegPatInput = '"rfcPatronCorregir":'+'"'+rfcRegPatInput+'"';
		var scalleRegPatInput = '"callePatronCorregir":'+'"'+calleRegPatInput+'"';
		var snumExteriorRegPatInput = '"numExteriorPatronCorregir":'+'"'+numExteriorRegPatInput+'"';
		var snumInteriorRegPatInput = '"numInteriorPatronCorregir":'+'"'+numInteriorRegPatInput+'"';
		var scoloniaRegPatInput = '"coloniaPatronCorregir":'+'"'+coloniaRegPatInput+'"';
		var stelefonoRegPatInput = '"telefonoPatron":'+'"'+telefonoRegPatInput+'"';
		var semailRegPatInput = '"emailPatron":'+'"'+emailRegPatInput+'"';
		var sactividadRegPatInput = '"actividad":'+'"'+actividadRegPatInput+'"';
		var sclaseRegPatInputDom = '"clasePatronCorregir":'+'"'+claseRegPatInput+'"';
		var sfraccionRegPatInput = '"fraccionPatronCorregir":'+'"'+fraccionRegPatInput+'"';
		var sprimaRegPatInput = '"primaPatronCorregir":'+'"'+primaRegPatInput+'"';
		var strabajadoresDom = '"numeroTrabajadores":'+'"'+trabajadoresDom+'"';
		var stxRepLegalInput = '"representante":'+'"'+txRepLegalInput+'"';
		var sfechaIni = '"fechaInicial":'+'"'+fecIni+'"';
		var sfechaFin = '"fechaFinal":'+'"'+fecFin+'"';
		var sfecha = '"fechaPresentacion":'+'"'+fecha+'"';
		var schkObra = '"tipoObra":'+'"'+chkObra+'"';
		var sidTipoSolicitud = '"idTipoSolicitud":'+'"'+idTipoSolicitud+'"';
		var scveAuditorAsignado = '"cveAuditorAsignado":'+'"'+cveAuditorAsignado+'"';
		
		var regPatronInputDom = $('#regPatronInputDom').val();
		var calleRegPatInputDom = $('#calleRegPatInputDom').val();
		var numExteriorRegPatInputDom = $('#numExteriorRegPatInputDom').val();
		var numInteriorRegPatInputDom = $('#numInteriorRegPatInputDom').val();
		var coloniaRegPatInputDom = $('#coloniaRegPatInputDom').val();

		var sregPatronInputDom = '"patronDom":'+'"'+regPatronInputDom+'"';
		var scalleRegPatInputDom = '"callePatronDom":'+'"'+calleRegPatInputDom+'"';
		var snumExteriorRegPatInputDom = '"numExteriorPatronDom":'+'"'+numExteriorRegPatInputDom+'"';
		var snumInteriorRegPatInputDom = '"numInteriorPatronDom":'+'"'+numInteriorRegPatInputDom+'"';
		var scoloniaRegPatInputDom = '"coloniaPatronDom":'+'"'+coloniaRegPatInputDom+'"';
		var folio='"nuFolio":'+'"'+folioInput+'"';
		var sSolicitudCorr = '{'+srazonSocialRegPatInput+','+sunoVariosRp+','+scurpPatInput+',';
		var sello = '"firmaElectronica":'+'"'+ sello +'"';
		var selloIMSS = '"selloIMSS":'+'"'+ selloIMSS +'"';
		var cadenaOriginal = '"cadenaOriginal":'+'"'+ cadenaOriginal +'"';
		sSolicitudCorr = sSolicitudCorr + srfcRegPatInput+','+scalleRegPatInputDom+','+snumExteriorRegPatInputDom+',';

//		sSolicitudCorr = sSolicitudCorr + sfirmaElectronica+','+ scadenaOriginal +','+ stipoCertificado+',';
		
		sSolicitudCorr = sSolicitudCorr + snumInteriorRegPatInputDom+','+scoloniaRegPatInputDom+','+sactividadRegPatInput+',';
		sSolicitudCorr = sSolicitudCorr + sclaseRegPatInputDom+','+strabajadoresDom+','+sfraccionRegPatInput+','+sprimaRegPatInput+',';
		sSolicitudCorr = sSolicitudCorr + stxRepLegalInput+','+sregPatronInputDom+','+scalleRegPatInput+','+snumExteriorRegPatInput+',';
		sSolicitudCorr = sSolicitudCorr + snumInteriorRegPatInput+','+scoloniaRegPatInput+','+stelefonoRegPatInput+','+semailRegPatInput+',';
		sSolicitudCorr = sSolicitudCorr + sfechaIni+','+sfechaFin+','+sfecha+','+schkObra +','+sidTipoSolicitud+','+folio+','+scveAuditorAsignado+',';
		sSolicitudCorr = sSolicitudCorr + sello + ',' + selloIMSS + ',' + cadenaOriginal;
		sSolicitudCorr = sSolicitudCorr + '}';
		
		var crtSolicitudCorr = jQuery.parseJSON(sSolicitudCorr);

		bloquear();
	
		
		crtSolicitudCorr.mensaje=objMensajeFirma;
		
		
		if(urlAcuse!=undefined){
			crtSolicitudCorr.urlAcuseFirma=urlAcuse;
		}
		
		crtSolicitudCorr.respuestaObjetoFirmadoSimple=respuestFirmadoSimple;
		crtSolicitudCorr.firmaElectroResultado=firmaObjeto;
		crtSolicitudCorr.nuFolio=$("#folioTemporalForma").val();		
		
		$.postJSON("correcion/guardar.do", crtSolicitudCorr, function(data) {
	
			desbloquear();
			
			if(data.error !=null && data.error!=undefined && data.error != '' )
			{
				
				alert(data.error);
				desbloquear();

			}
			else
			{
				
				alert('La solicitud se guardo exitosamente , No. Folio : ' + data.nuFolio +"\n se generar\u00e1 su comprobante PDF, favor de esperar unos minutos");
				startEncuestaHC(400,'imss_sisconet');
				document.forms[0].submit();
			}
		
	}).error(function(data){
		desbloquear();
		
		validarSesionExpirada(data);
		alert("error" + data);
	}).complete(function(data){
		
		
		
	});
	
	
//	oDgValida.dialog("close");
}




function recuperaFolioTemporal(){
	
	var folio;
	var razonSocialRegPatInput = $('#razonSocialRegPatInput').val();
	var unoVariosRp = $('input[name=unoVariosRp]:checked', '#correccionForm').val();
	var curpPatInput = $('#curpPatInput').val();
	var rfcRegPatInput = $('#rfcRegPatInput').val();
	var calleRegPatInput = $('#calleRegPatInput').val();
	var numExteriorRegPatInput = $('#numExteriorRegPatInput').val();
	var numInteriorRegPatInput = $('#numInteriorRegPatInput').val();
	var coloniaRegPatInput = $('#coloniaRegPatInput').val();
	var telefonoRegPatInput = $('#telefonoRegPatInput').val();
	var emailRegPatInput = $('#emailRegPatInput').val();
	var actividadRegPatInput = $('#actividadRegPatInput').val();
	var claseRegPatInput = $('#claseRegPatInput').val();
	var fraccionRegPatInput = $('#fraccionRegPatInput').val();
	var primaRegPatInput = $('#primaRegPatInput').val();
	var trabajadoresDom = $('#numeroTrabajadores').val();
	var txRepLegalInput = $('#txRepLegalInput').val();
	var fecIni = $('#fechaInicial').val();
	var fecFin = $('#fechaFinal').val();
	var fecha = $('#fechaPresentacion').val();
	var chkObra = document.getElementById("chkSolicitudObra").checked;
	var idTipoSolicitud = $('#idTipoSolicitud').val();
	var cveAuditorAsignado = $('#cveAuditorAsignado').val();
	var folioInput=$('#folioInput').val();
	
	
	var srazonSocialRegPatInput = '"razonSocialPatronCorregir":'+'"'+razonSocialRegPatInput+'"';
	var sunoVariosRp = '"unoVariosRp":'+'"'+unoVariosRp+'"';				
	var scurpPatInput = '"curpPatronCorregir":'+'"'+curpPatInput+'"';
	var srfcRegPatInput = '"rfcPatronCorregir":'+'"'+rfcRegPatInput+'"';
	var scalleRegPatInput = '"callePatronCorregir":'+'"'+calleRegPatInput+'"';
	var snumExteriorRegPatInput = '"numExteriorPatronCorregir":'+'"'+numExteriorRegPatInput+'"';
	var snumInteriorRegPatInput = '"numInteriorPatronCorregir":'+'"'+numInteriorRegPatInput+'"';
	var scoloniaRegPatInput = '"coloniaPatronCorregir":'+'"'+coloniaRegPatInput+'"';
	var stelefonoRegPatInput = '"telefonoPatron":'+'"'+telefonoRegPatInput+'"';
	var semailRegPatInput = '"emailPatron":'+'"'+emailRegPatInput+'"';
	var sactividadRegPatInput = '"actividad":'+'"'+actividadRegPatInput+'"';
	var sclaseRegPatInputDom = '"clasePatronCorregir":'+'"'+claseRegPatInput+'"';
	var sfraccionRegPatInput = '"fraccionPatronCorregir":'+'"'+fraccionRegPatInput+'"';
	var sprimaRegPatInput = '"primaPatronCorregir":'+'"'+primaRegPatInput+'"';
	var strabajadoresDom = '"numeroTrabajadores":'+'"'+trabajadoresDom+'"';
	var stxRepLegalInput = '"representante":'+'"'+txRepLegalInput+'"';
	var sfechaIni = '"fechaInicial":'+'"'+fecIni+'"';
	var sfechaFin = '"fechaFinal":'+'"'+fecFin+'"';
	var sfecha = '"fechaPresentacion":'+'"'+fecha+'"';
	var schkObra = '"tipoObra":'+'"'+chkObra+'"';
	var sidTipoSolicitud = '"idTipoSolicitud":'+'"'+idTipoSolicitud+'"';
	var scveAuditorAsignado = '"cveAuditorAsignado":'+'"'+cveAuditorAsignado+'"';
	
	var regPatronInputDom = $('#regPatronInputDom').val();
	var calleRegPatInputDom = $('#calleRegPatInputDom').val();
	var numExteriorRegPatInputDom = $('#numExteriorRegPatInputDom').val();
	var numInteriorRegPatInputDom = $('#numInteriorRegPatInputDom').val();
	var coloniaRegPatInputDom = $('#coloniaRegPatInputDom').val();

	var sregPatronInputDom = '"patronDom":'+'"'+regPatronInputDom+'"';
	var scalleRegPatInputDom = '"callePatronDom":'+'"'+calleRegPatInputDom+'"';
	var snumExteriorRegPatInputDom = '"numExteriorPatronDom":'+'"'+numExteriorRegPatInputDom+'"';
	var snumInteriorRegPatInputDom = '"numInteriorPatronDom":'+'"'+numInteriorRegPatInputDom+'"';
	var scoloniaRegPatInputDom = '"coloniaPatronDom":'+'"'+coloniaRegPatInputDom+'"';
	var folio='"nuFolio":'+'"'+folioInput+'"';
	var sSolicitudCorr = '{'+srazonSocialRegPatInput+','+sunoVariosRp+','+scurpPatInput+',';
	var sello = '"firmaElectronica":'+'"'+ sello +'"';
	var selloIMSS = '"selloIMSS":'+'"'+ selloIMSS +'"';
	var cadenaOriginal = '"cadenaOriginal":'+'"'+ cadenaOriginal +'"';
	sSolicitudCorr = sSolicitudCorr + srfcRegPatInput+','+scalleRegPatInputDom+','+snumExteriorRegPatInputDom+',';

//	sSolicitudCorr = sSolicitudCorr + sfirmaElectronica+','+ scadenaOriginal +','+ stipoCertificado+',';
	
	sSolicitudCorr = sSolicitudCorr + snumInteriorRegPatInputDom+','+scoloniaRegPatInputDom+','+sactividadRegPatInput+',';
	sSolicitudCorr = sSolicitudCorr + sclaseRegPatInputDom+','+strabajadoresDom+','+sfraccionRegPatInput+','+sprimaRegPatInput+',';
	sSolicitudCorr = sSolicitudCorr + stxRepLegalInput+','+sregPatronInputDom+','+scalleRegPatInput+','+snumExteriorRegPatInput+',';
	sSolicitudCorr = sSolicitudCorr + snumInteriorRegPatInput+','+scoloniaRegPatInput+','+stelefonoRegPatInput+','+semailRegPatInput+',';
	sSolicitudCorr = sSolicitudCorr + sfechaIni+','+sfechaFin+','+sfecha+','+schkObra +','+sidTipoSolicitud+','+folio+','+scveAuditorAsignado+',';
	sSolicitudCorr = sSolicitudCorr + sello + ',' + selloIMSS + ',' + cadenaOriginal;
	sSolicitudCorr = sSolicitudCorr + '}';
	
	var crtSolicitudCorr = jQuery.parseJSON(sSolicitudCorr);

	$.postJSON_Sync("correcion/recuperaFolioTemporal.do", crtSolicitudCorr, function(data) {		
		folio=data.numFolio;
	});
	
	return folio;
	
}



//convierte yyyy-mm-dd to  dd-mm-yyyy
function castFecha(fecha){
	var fec = fecha.split("-");
	return fec[2]+'-'+fec[1]+'-'+fec[0]
	
}
///////////////////////  Ejecucion de la firma digital ////////////////////


var resFirmadoDocumento;
var objFirmaRecuperado;
function callBackFirmaSolCorr(){	
	objFirmaRecuperado=firma;
	resFirmadoDocumento=firma.getDatosSalida();
	if(resFirmadoDocumento!=null){
		if(resFirmadoDocumento.Resultado==0){
			window.open(resFirmadoDocumento.acuse,'', "scrollbars=1,height=500,width=700");
			var sello=recuperaSelloImss(resFirmadoDocumento.folio);
			if(sello==null){
				alert("No se puede sellar el documento");
				return;
			}
			
			
			var firmaResponse = {
			        cadenaOriginal : resFirmadoDocumento.contenedores[0].cadori,
			        recibo : resFirmadoDocumento.firmas[0],
			        reciboNotarial : resFirmadoDocumento.folio,
			        urlAcuseFirma : resFirmadoDocumento.acuse,
			        serialCertificado : resFirmadoDocumento.serie_cert,
			        strIniciaVigenciaCertificado : resFirmadoDocumento.vigIni,
			        strFinVigenciaCertificado : resFirmadoDocumento.vigFin
			};

			
			
			registrarFirmado(resFirmadoDocumento.folio,sello.sello, generaCadenaPrincipal(),resFirmadoDocumento.acuse,firmaResponse);
		}else{
			errorDialog.html('<font size="2px">No se ha podido firmar el documento,favor de reintentar</font>');
			errorDialog.dialog("open");
		}
	}
	
	
}


function generaParamatrosSolCorr(){   
	//selloDigitalSolCorr=recuperaSelloImss();
//	if(selloDigitalSolCorr=="-1"){
//		return null;
//	}
	var parmVals = {			
			acuse:"AcuseV1.0",
			aplicacion:"portalimssdigital",
			operacion:"firmaCMS",
//			origen:"http://dnassd.imss.gob.mx",
			origen:"http://correcciondigital.imss.gob.mx",
			tipo_archivos:"",
			forma_firma_archivos:"0",
			firma_archivo:true,
			//val_rfc:true,
			max_archivos:5,
			min_archivos:1,
			salida:"resultado,descripcion,folio,acuse,qr,rfc,curp,serie_cert,archivos,firmas,contenedores,vigencias",
			curp:$("#curpPatInput").val().trim(),
			rfc:$("#rfcRegPatInput").val().trim(),
			nombreCompleto:$("#razonSocialRegPatInput").val(),
			registroPatronal:$("#regPatronPrInput").val()+recuperaDigitoVerificador($("#regPatronPrInput").val()),
			idTipoSolicitud:6,								
			cad_original:quitaAcentos(generaCadenaPrincipal().substring(0,generaCadenaPrincipal().length-2)+"||"),			
			descripcionTipoSolicitud:"SOLICITUD DE CORRECCI�N",
			fechaElectronica:getFechaServidor()		
						
		};
		
		return parmVals;
		
	}

function generaCadenaPrincipal(){
	var campos = new Object();
	var cadenaPrincipal="||";
	

	
	
    campos['regPatronPrInput'] = 'Registro Patronal a Corregir';
    campos['folioTemporalForma'] = 'Folio de Correccion Asignado';
	campos['razonSocialRegPatInput'] = 'Nombre o Denominacion Social';
	campos['rfcRegPatInput'] = 'RFC';
	campos['curpPatInput'] = 'CURP';
	campos['fechaInicial'] = 'Ejercicio o Periodo a Regularizar Del';
	campos['fechaFinal'] = 'Ejercicio o Periodo a Regularizar Al';
	campos['regPatronInputDom'] = 'Registro Patronal del Domicilio Fiscal';
	campos['numeroRegistroObra'] = 'Numero de Registro de Obra';
	campos['fechaFolio'] = 'Fecha de Recepcion de Oficio';
	campos['numeroTrabajadores'] = 'Numero de Trabajadores';
	campos['actividadRegPatInput'] = 'Actividad';
	campos['claseRegPatInput'] = 'Clase';
	campos['fraccionRegPatInput'] = 'Fraccion';
	campos['primaRegPatInput'] = 'Prima';
	campos['txRepLegalInput'] = 'Nombre y Firma del Patron o Representante Legal';
	campos['lugarPresentacion'] = 'Lugar';
	campos['fechaPresentacion'] = 'Fecha';
	var val="";
	var total=0;
	for (var k in campos) {
		total++;
	}
	
	var s=0;
	for (var k in campos) {		
	    if (campos.hasOwnProperty(k)) {
	    	 	cadenaPrincipal=cadenaPrincipal+campos[k]+"|"+$("#"+k).val();	
	    	 	if(s<total-1){
	    	 		cadenaPrincipal+="|";
	    	 	 }
	    		s++;
	      }
	   
	}
	cadenaPrincipal+="||";

	return cadenaPrincipal;	
	
}





function recuperaSelloImss(idTramite){
	
	
	var datos='{"selloIMSS":""}';
	var crtSolicitudCorr = jQuery.parseJSON(datos);
	crtSolicitudCorr.selloIMSS=generaCadenaPrincipal();
	if(idTramite!=undefined){
		crtSolicitudCorr.idTramite=idTramite;
	}
	
	var sello;
	$.postJSON_Sync("correcion/recuperaSelloDigital.do", crtSolicitudCorr, function(data) {
		respuestFirmadoSimple=data.respuestaObjetoFirmadoSimple;
		sello=data.respuestaObjetoFirmadoSimple;
		if(sello==null){
			alert("No se pudo sellar el documento");
			return null;
		}
		desbloquear();
		
	});
	
	
	 
	return sello;
}


function recuperarRolUsuario(){
	
	var cveRol;
	$.postJSON_Sync("correcion/consultarRolUsuario.do", null, function(data) {
		cveRol=data.cveRol;
		desbloquear();
		
	});
	return cveRol;
}


function recuperaDigitoVerificador(rp){
	
	var digitoVerificador;
	
	var valor='{"registroPatronal":"'+rp+'"}';
	var crtSolicitudCorr = jQuery.parseJSON(valor);
	$.postJSON_Sync("correcion/generaDigitoVerificador.do", crtSolicitudCorr, function(data) {
		digitoVerificador=data;
		desbloquear();		
	});
	return digitoVerificador;
}





