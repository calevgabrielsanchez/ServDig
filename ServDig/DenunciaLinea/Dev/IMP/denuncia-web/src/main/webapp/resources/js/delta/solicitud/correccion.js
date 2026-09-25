/**
 * JS para el soporte del catalogo de division.
 */

var idDataTable 	= "#dtPatronesInscritos";
var idDgModificar 	= "#dgPatInsModificar";


var oDtPatronesIscritos;
var oDgModificar;
var dataEmpty = {"sEcho":"undefined","iTotalRecords":0,"iTotalDisplayRecords":0,"sColumns":null,"aaData":[]};


$(document).ready(function() {
	
	 $( "#fechaInicial, #fechaFinal, #fechaPresentacion" ).datepicker( { dateFormat: 'dd-mm-yy' });

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
				if(data==null)
				{
					alert('Registro patronal invalido');
				}
				else
				{
					$('#wrapperDialogSolCorr2,#correccionForm,#calleRegPatInputDom').val(data.patronPrincipal.ubicacion.calle);
					$('#wrapperDialogSolCorr2,#correccionForm,#numExteriorRegPatInputDom').val(data.patronPrincipal.ubicacion.numeroExterior);
					$('#wrapperDialogSolCorr2,#correccionForm,#numInteriorRegPatInputDom').val(data.patronPrincipal.ubicacion.numeroInterior);
					$('#wrapperDialogSolCorr2,#correccionForm,#coloniaRegPatInputDom').val(data.patronPrincipal.ubicacion.colonia);
					$('#wrapperDialogSolCorr2,#correccionForm,#municipioRegPatInputDom').val(data.patronPrincipal.ubicacion.municipio.nombre);
					$('#wrapperDialogSolCorr2,#correccionForm,#localidadRegPatInputDom').val(data.patronPrincipal.ubicacion.municipio.nombre);
					$('#wrapperDialogSolCorr2,#correccionForm,#codigoPostalRegPatInputDom').val(data.patronPrincipal.ubicacion.codigoPotal);
					$('#wrapperDialogSolCorr2,#correccionForm,#entidadFederativaRegPatInputDom').val(data.patronPrincipal.ubicacion.municipio.sacEntidadFederativa.nomNombre);
					$('#wrapperDialogSolCorr2,#correccionForm,#telefonoRegPatInput').val(data.patronPrincipal.ubicacion.telefono);
					$('#wrapperDialogSolCorr2,#correccionForm,#emailRegPatInput').val(data.patronPrincipal.ubicacion.eMail);
					$('#wrapperDialogSolCorr2,#correccionForm,#subdelegacionRegPatInput').val(data.patronPrincipal.ubicacion.municipio.sacSubdelegacion.nomNombre);
					

					
				}
			}).error(function(data){ 
				desbloquear();
				validarSesionExpirada(data);
				alert("error" + data);
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
					$('#correccionForm :input:text').prop("value", "");
				}
				else
				{
					$('#wrapperDialogSolCorr,#correccionForm,#razonSocialRegPatInput').val(data.patronCorregir.razonSocial);

					if(data.personaFisica){
						$('#wrapperDialogSolCorr,#correccionForm,#curpPatInput').val(data.patronCorregir.rfc);
						$('#wrapperDialogSolCorr,#correccionForm,#curpPatInput').removeAttr("readonly");
					}
					else{
						$('#wrapperDialogSolCorr,#correccionForm,#curpPatInput').attr("readonly", true);
						$('#wrapperDialogSolCorr,#correccionForm,#curpPatInput').val('');
					}
					
					$('#wrapperDialogSolCorr,#correccionForm,#rfcRegPatInput').val(data.patronCorregir.rfc);
					$('#wrapperDialogSolCorr4,#correccionForm,#calleRegPatInput').val(data.patronCorregir.ubicacion.calle);
					$('#wrapperDialogSolCorr4,#correccionForm,#numExteriorRegPatInput').val(data.patronCorregir.ubicacion.nuExterior);
					$('#wrapperDialogSolCorr4,#correccionForm,#numInteriorRegPatInput').val(data.patronCorregir.ubicacion.nuInterior);
					$('#wrapperDialogSolCorr4,#correccionForm,#coloniaRegPatInput').val(data.patronCorregir.ubicacion.colonia);
					$('#wrapperDialogSolCorr4,#correccionForm,#municipioRegPatInput').val(data.patronCorregir.ubicacion.municipio.nombre);
					$('#wrapperDialogSolCorr4,#correccionForm,#localidadRegPatInput').val(data.patronCorregir.ubicacion.municipio.nombre);
					$('#wrapperDialogSolCorr4,#correccionForm,#cpRegPatInput').val(data.patronCorregir.ubicacion.codigoPotal);
					$('#wrapperDialogSolCorr4,#correccionForm,#entidadFederativaRegPatInput').val(data.patronCorregir.ubicacion.municipio.sacEntidadFederativa.nomNombre);
					$('#wrapperDialogSolCorr5,#correccionForm,#fechaPresentacion').val(data.fechaPresentacion);
					$('#wrapperDialogSolCorr3,#correccionForm,#fechaInicial').val(data.fechaInicial);
					$('#wrapperDialogSolCorr3,#correccionForm,#fechaFinal').val(data.fechaFinal);
					$('#wrapperDialogSolCorr5,#correccionForm,#lugarPresentacion').val(data.patronCorregir.ubicacion.municipio.sacSubdelegacion.nomNombre);
					$('#wrapperDialogSolCorr3,#correccionForm,#fechaFolio').val(data.fechaRecepcionOficio);
					if(data.tipo=="INVITACION")
					{
						$('#wrapperDialogSolCorr,#correccionForm,#folioInput').val(data.folioInvitacion);
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
				alert("error" + data);
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
			var sPatron = '{"patron":'+'"'+idPatron+'"}';
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
				despliegaDialog("2");
			});
		}else{
			
			alert("El registro patronal del domicilio de trabajo es obligatorio");
			
		}
		
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
		if(validaCaptura.form())
		{
			if(confirm("Est\u00e1 Seguro de Generar la solicitud con la informaci\u00f3n recopilada?"))
			{
				// Buscamos el elemento
				var razonSocialRegPatInput = $('#razonSocialRegPatInput').val();
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
				var chkObra = document.getElementById("chkSolicitudObra").checked
				
				var srazonSocialRegPatInput = '"razonSocialPatronCorregir":'+'"'+razonSocialRegPatInput+'"';
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

				var sSolicitudCorr = '{'+srazonSocialRegPatInput+','+scurpPatInput+','+srfcRegPatInput+','+scalleRegPatInputDom+','+snumExteriorRegPatInputDom+','+snumInteriorRegPatInputDom+','+scoloniaRegPatInputDom+','+sactividadRegPatInput+','+sclaseRegPatInputDom+','+strabajadoresDom+','+sfraccionRegPatInput+','+sprimaRegPatInput+','+stxRepLegalInput+','+sregPatronInputDom+','+scalleRegPatInput+','+snumExteriorRegPatInput+','+snumInteriorRegPatInput+','+scoloniaRegPatInput+','+stelefonoRegPatInput+','+semailRegPatInput+','+sfechaIni+','+sfechaFin+','+sfecha+','+schkObra +'}';

				var crtSolicitudCorr = jQuery.parseJSON(sSolicitudCorr);

				bloquear();
				$.postJSON("correcion/guardar.do", crtSolicitudCorr, function(data) {
					desbloquear();
					if(data==null)
					{
						alert('La solicitud se guardo exitosamente');
						document.forms[0].submit();
					}
					else
					{
						alert(data.error);
					}
				}).error(function(data){ 
					desbloquear();
					validarSesionExpirada(data);
					alert("error" + data);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
			}
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
						$('#wrapperDialogSolCorrObra,#correccionForm,#codigoPostalRegPatInputObra').val(data.ubicacion.codigoPotal);
						$('#wrapperDialogSolCorrObra,#correccionForm,#subdelegacionRegPatInputObra').val(data.ubicacion.municipio.sacSubdelegacion.nomNombre);
						$('#wrapperDialogSolCorrObra,#correccionForm,#entidadFederativaRegPatInputObra').val(data.ubicacion.municipio.sacEntidadFederativa.nomNombre);
						$('#wrapperDialogSolCorr3,#correccionForm,#fechaInicial').val(data.fechaInicial);
						$('#wrapperDialogSolCorr3,#correccionForm,#fechaFinal').val(data.fechaFinal);
						
						
					}
				}).error(function(data){ 
					desbloquear();
					validarSesionExpirada(data);
					alert("error" + data);
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
	  	  	  telefonoRegPatInput:{
			  required: true,
	  		  maxlength: 50,
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
	  		  alphanumeric: true
	  	  },
	  	  	  fraccionRegPatInput:{
 			  required: true,
	  		  maxlength: 5,
	  		  alphanumeric: true
	  	  },
	  	  	  primaRegPatInput:{
 			  required: true,
	  		  maxlength: 5,
	  		  alphanumeric: true
	  	  },
	  	  	  txRepLegalInput:{
 			  required: true,
	  		  maxlength: 300,
	  		  alphanumeric: true
	  	  }
	  	  },
	  	messages:{
	  		regPatronPrInput:"Requerido",
	  		regPatronInputDom:"Requerido",
	  		telefonoRegPatInput:"Requerido",
	  		emailRegPatInput:"Requerido",
	  		numeroTrabajadores:"Requerido",
	  		actividadRegPatInput:"Requerido",
	  		claseRegPatInput:"Requerido",
	  		fraccionRegPatInput:"Requerido",
	  		primaRegPatInput:"Requerido",
	  		txRepLegalInput:"Requerido"
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
	var resultado = openWindowregistraDomicilioInegi(context,'solicitud/correcion/solicitudDomGeografico.do')
	if(tpoAct=="1")
		actualizaDatosDom();
	if(tpoAct=="2")
		actualizaDatosCorr();
	if(tpoAct=="3")
		actualizaDatosIns();
}


function actualizaDatosDom(){
	var idPatron =  $('#regPatronInputDom').val();
	var sPatron = '{"patron":'+'"'+idPatron+'"}';
	var satPatron = jQuery.parseJSON(sPatron);
	bloquear();
	$.postJSON("correcion/actualizaRegPat.do", satPatron, function(data) {
		desbloquear();
		$('#wrapperDialogSolCorr2,#correccionForm,#calleRegPatInputDom').val(data.calle);
		$('#wrapperDialogSolCorr2,#correccionForm,#numExteriorRegPatInputDom').val(data.numeroExterior);
		$('#wrapperDialogSolCorr2,#correccionForm,#numInteriorRegPatInputDom').val(data.numeroInterior);
		$('#wrapperDialogSolCorr2,#correccionForm,#coloniaRegPatInputDom').val(data.colonia);
		$('#wrapperDialogSolCorr2,#correccionForm,#municipioRegPatInputDom').val(data.municipio);
		$('#wrapperDialogSolCorr2,#correccionForm,#localidadRegPatInputDom').val(data.municipio);
		$('#wrapperDialogSolCorr2,#correccionForm,#entidadFederativaRegPatInputDom').val(data.entidadFederativa);
		$('#wrapperDialogSolCorr2,#correccionForm,#codigoPostalRegPatInputDom').val(data.codigoPostal);
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
	var sPatron = '{"patron":'+'"'+idPatron+'"}';
	var satPatron = jQuery.parseJSON(sPatron);
	bloquear();
	$.postJSON("correcion/actualizaRegPat.do", satPatron, function(data) {
		desbloquear();
		$('#wrapperDialogSolCorr4,#correccionForm,#calleRegPatInput').val(data.calle);
		$('#wrapperDialogSolCorr4,#correccionForm,#numExteriorRegPatInput').val(data.numeroExterior);
		$('#wrapperDialogSolCorr4,#correccionForm,#numInteriorRegPatInput').val(data.numeroInterior);
		$('#wrapperDialogSolCorr4,#correccionForm,#coloniaRegPatInput').val(data.colonia);
		$('#wrapperDialogSolCorr4,#correccionForm,#municipioRegPatInput').val(data.municipio);
		$('#wrapperDialogSolCorr4,#correccionForm,#localidadRegPatInput').val(data.municipio);
		$('#wrapperDialogSolCorr4,#correccionForm,#entidadFederativaRegPatInput').val(data.entidadFederativa);
		$('#wrapperDialogSolCorr4,#correccionForm,#cpRegPatInput').val(data.codigoPostal);
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


