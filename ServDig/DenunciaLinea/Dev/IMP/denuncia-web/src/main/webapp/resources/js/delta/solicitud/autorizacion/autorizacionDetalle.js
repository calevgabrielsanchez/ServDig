/**
 * JS para el soporte del catalogo de division.
 */

var idDataTable 	= "#dtPatronesInscritos";
var idDgRechazar 	= "#dgSolRechazar";


var oDtPatronesIscritos;
var oDgRechazar;


$(document).ready(function() {

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
				var retVal = '<input type="radio" value="' + oObj.aData['registroPatronalSD'] +'" id="radioTable" class="radioClase" name="radio" onclick=""/> ';
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
				"mDataProp" : "trabajadoresN",
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "Clase",
				"mDataProp" : "txClase",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Fraccion",
				"mDataProp" : "txFraccion",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Pirma",
				"mDataProp" : "txPrima",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Actividad",
				"mDataProp" : "txActividad",
				"sClass": "dtCenterClassColumn"
			}
			],"bProcessing" : true,
			"sAjaxSource" : '../autorizacionDetalle/paginar.do',
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
	oDgRechazar = $(idDgRechazar).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 600,
		beforeClose :function(event,ui){
		    limpiarFormulario("#patInsFormMotivo");
		},
		buttons: {
			"Aceptar": function() { 
				if(confirm("¿Esta Seguro de Rechazar la Solicitud de Correeción?")){
					// Buscamos el elemento
					var motivo = $('#motivoRechazo').val();
					var idMotivo = $('#idMotivoRechazo').val();
					if(idMotivo<0)
						alert('seleccione un motivo');
					else{
						var sMotivo = '"motivoRechazo":'+'"'+motivo+'"';
						var sIdMotivo = '"idMotivoRechazo":'+'"'+idMotivo+'"';
						var sSolCorr = '{'+sMotivo+','+sIdMotivo+'}';
						var solCorr = jQuery.parseJSON(sSolCorr);
						bloquear();
						$.postJSON("../autorizacionDetalle/rechazar.do", solCorr, function(data) {
							desbloquear();
							if(data==null)
							{
								alert('Se rechazo correctamente la solicitud de correci&oacute;n');
								document.forms[0].submit();
							}
						}).error(function(data){ 
							desbloquear();
							alert("error" + data);
						}).complete(function(){
							//Instrucciones para el 'complete'
						});

						$(this).dialog("close"); 
					}
				}
			}, 
			"Cancelar": function() { 
				$(this).dialog("close"); 
			} 
		}
	});

	
	$("a#btnAutorizar").click(function(event){
		event.preventDefault();
		// Buscamos el elemento
		if(confirm("¿Esta Seguro de Autorizar la Solicitud de Correeción?")){
			var sPatron = '{"patron":'+'""}';
			var crcPatron = jQuery.parseJSON(sPatron);
			bloquear();
			$.postJSON("../autorizacionDetalle/autorizar.do", crcPatron, function(data) {
				desbloquear();
				if(data==null)
				{
					alert('Se autorizo correctamente la solicitud de correci&oacute;n');
					document.forms[0].submit();
				}
			}).error(function(data){ 
				desbloquear();
				alert("error" + data);
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
		}
	});

	$("a#btnRechazar").click(function(event){
		event.preventDefault();
		oDgRechazar.dialog('open');
	});


	
});//$(document).ready(function()

//Inicializa el paginador
function inicializaPosicionPaginador(){
	oDtPatronesIscritos.fnDisplayStart(0);
}

function paginar(){
	oDtPatronesIscritos.fnDraw();
}

function getSolicitud(){
	var sPatron = '{"patron":'+'""}';
	var crtSoicitud = jQuery.parseJSON(sPatron);
	$.postJSON("../autorizacionDetalle/getSolicitud.do", crtSoicitud, function(data) {
		if(data!=null)
		{
			$('#wrapperDialogSolCorr,#correccionForm,#folioInput').val(data.nuFolio);
			$('#wrapperDialogSolCorr,#correccionForm,#regPatronPrInput').val(data.patronCorregir.registroPatronalSD);
			$('#wrapperDialogSolCorr,#correccionForm,#razonSocialRegPatInput').val(data.patronCorregir.razonSocial);
			$('#wrapperDialogSolCorr,#correccionForm,#curpPatInput').val(data.patronCorregir.rfc);
			$('#wrapperDialogSolCorr,#correccionForm,#rfcRegPatInput').val(data.patronCorregir.curp);
			$('#wrapperDialogSolCorr4,#correccionForm,#calleRegPatInput').val(data.patronCorregir.calle);
			$('#wrapperDialogSolCorr4,#correccionForm,#numExteriorRegPatInput').val(data.patronCorregir.numeroExterior);
			$('#wrapperDialogSolCorr4,#correccionForm,#numInteriorRegPatInput').val(data.patronCorregir.numeroInterior);
			$('#wrapperDialogSolCorr4,#correccionForm,#coloniaRegPatInput').val(data.patronCorregir.colonia);
			$('#wrapperDialogSolCorr4,#correccionForm,#municipioRegPatInput').val(data.patronCorregir.municipio);
			$('#wrapperDialogSolCorr4,#correccionForm,#localidadRegPatInput').val(data.patronCorregir.municipio);
			$('#wrapperDialogSolCorr4,#correccionForm,#cpRegPatInput').val(data.patronCorregir.codigoPotal);
			$('#wrapperDialogSolCorr4,#correccionForm,#entidadFederativaRegPatInput').val(data.patronCorregir.entidadFederativa);
			$('#wrapperDialogSolCorr5,#correccionForm,#fechaPresentacion').val(data.fechaPresentacion);
			$('#wrapperDialogSolCorr3,#correccionForm,#fechaInicial').val(data.fechaInicial);
			$('#wrapperDialogSolCorr3,#correccionForm,#fechaFinal').val(data.fechaFinal);
			$('#wrapperDialogSolCorr3,#correccionForm,#fechaFolio').val(data.fechaRecepcionOficio);
			$('#wrapperDialogSolCorr4,#correccionForm,#actividadRegPatInput').val(data.actividad);
			$('#wrapperDialogSolCorr5,#correccionForm,#txRepLegalInput').val(data.representante);
			$('#wrapperDialogSolCorr5,#correccionForm,#lugarPresentacion').val(data.patronCorregir.ubicacion.municipio.sacSubdelegacion.nomNombre);
			$('#wrapperDialogSolCorr4,#correccionForm,#claseRegPatInput').val(data.clasePatronCorregir);
			$('#wrapperDialogSolCorr4,#correccionForm,#fraccionRegPatInput').val(data.fraccionPatronCorregir);
			$('#wrapperDialogSolCorr4,#correccionForm,#primaRegPatInput').val(data.primaPatronCorregir);
			$('#wrapperDialogSolCorr3,#correccionForm,#numeroTrabajadores').val(data.patronCorregir.trabajadores);
			$('#wrapperDialogSolCorr2,#correccionForm,#telefonoRegPatInput').val(data.telefonoPatron);
			$('#wrapperDialogSolCorr2,#correccionForm,#emailRegPatInput').val(data.patronCorregir.txEmail);
			$('#wrapperDialogSolCorr2,#correccionForm,#subdelegacionRegPatInput').val(data.patronCorregir.ubicacion.municipio.sacSubdelegacion.nomNombre);
			
			
			$('#wrapperDialogSolCorr2,#correccionForm,#regPatronInputDom').val(data.patronPrincipal.registroPatronalSD);
			$('#wrapperDialogSolCorr2,#correccionForm,#calleRegPatInputDom').val(data.patronPrincipal.calle);
			$('#wrapperDialogSolCorr2,#correccionForm,#numExteriorRegPatInputDom').val(data.patronPrincipal.numeroExterior);
			$('#wrapperDialogSolCorr2,#correccionForm,#numInteriorRegPatInputDom').val(data.patronPrincipal.numeroInterior);
			$('#wrapperDialogSolCorr2,#correccionForm,#coloniaRegPatInputDom').val(data.patronPrincipal.colonia);
			$('#wrapperDialogSolCorr2,#correccionForm,#municipioRegPatInputDom').val(data.patronPrincipal.municipio);
			$('#wrapperDialogSolCorr2,#correccionForm,#localidadRegPatInputDom').val(data.patronPrincipal.municipio);
			$('#wrapperDialogSolCorr2,#correccionForm,#codigoPostalRegPatInputDom').val(data.patronPrincipal.codigoPotal);
			$('#wrapperDialogSolCorr2,#correccionForm,#entidadFederativaRegPatInputDom').val(data.patronPrincipal.entidadFederativa);

			var radios = document.getElementsByName("tipoSol");
			alert(data.tipo);
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
		alert("error" + data);
	}).complete(function(){
	});
}



