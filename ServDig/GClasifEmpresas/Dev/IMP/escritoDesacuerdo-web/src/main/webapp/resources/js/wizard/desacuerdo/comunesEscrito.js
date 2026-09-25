var funcionesComunes = {
	contextApp: '/${mvn.web.app.root}',
	origenApp: '${mvn.web.app.origin.id}',
	elementoFolio: '#noFolioSolicitud',
	init: function() {
		$.each(eventosComunes.eventos, function(index, value){
			//verificamos que exista el elemento al que le queremos aplicar el evento
			if($(value.id).length) {
				$(value.id).on("click", value.funcion);
			}
		});
	},
	iniciarTramite: function() {
		$("#solicitudEscrito").submit();
	},
	cancelarTramite: function() {
		var opcionesDialog = dialogosComunes.cancelarTramite;
		opcionesDialog.mensaje = opcionesDialog.mensaje.replace("%FOLIO%",$(funcionesComunes.elementoFolio).val());
		dialogosCtrl.abrirDialogo(opcionesDialog);
	},
	salirTramite: function() {
		dialogosCtrl.abrirDialogo(dialogosComunes.salirTramite);
	},
	verDocumentos: function() {
		$(this).dialog('close');
		$.blockUI();
		funcionesComunes.limpiarSession(function(){
			//console.log("Voy a ver los documentos del folio " + $("#noFolioSolicitud").val());
			parent.ejecutarConsultaSolicitudPorFolio($("#noFolioSolicitud").val());
			parent.WizardEscritoCtrl.cerrar();
		});
		
	},
	procesarSalirTramite: function() {
		$(this).dialog('close');
		$.blockUI();
		funcionesComunes.limpiarSession(function(){
			if(funcionesComunes.origenApp == "1") {
				location.href = funcionesComunes.contextApp + "/escrito/";
			} else {
				parent.WizardEscritoCtrl.cerrar();
			}
		});
	},
	procesarCancelar: function() {
		$.blockUI();
		$.postJSON(funcionesComunes.contextApp + "/escrito/wizard/cancelarTramite", {noFolioSolicitud: $(funcionesComunes.elementoFolio).val()}, function(data) {
			if(data.correcto) {
				dialogosCtrl.abrirDialogo(dialogosComunes.tramiteCancelado);
			} else {
				console.log("error al cancelar la solicitud");
			}
		}).error(function(){ 
		}).complete($.unblockUI);
	},
	limpiarSession: function(funcion) {
		$.blockUI();
		$.postJSON(funcionesComunes.contextApp + "/escrito/wizard/limpiarSession", null, function(data) {	
		}).error(function(){ 
		}).complete(funcion);
	}
};



var validarCierre = {
	valSelPr1:0,
	validaDtsDirecc:0,
	aproOptMod:false,
	fecNotficaResol:null,
	salirModals: function() { //Revisar si se elimina
		dialogosCtrl.abrirDialogo(dialogosComunes.salirTramite);
	},procesaDatosModal : function () {
		var $formLario =  $("#forModalDlg");
		var $fomExtrs = $("#fomExtrs");
		//registroDesacuerdoCtrl.vaidacorAnterior();
		if($formLario.valid() && $fomExtrs.valid()) {
			$("#campoObligatorioRadio").addClass("hidden");

			var lasModal = validarCamposModal();

			if (validarCierre.valSelPr1 != 0) {
				validarCierre.aproOptMod = true;
			} else {
				$("#campoObligatorioRadio").removeClass("hidden");
			}

			if (lasModal && validarCierre.aproOptMod) {
				validarCierre.vaidaRequired();
			}
		}
	},vaidaRequired : function (){
		console.log("inicio el validador del Modal");

		var preg1 = validarCierre.valSelPr1;
		var preg2 = [];
		var preg3 = [];

		preg2.push($('#claseAnt').val());
		preg2.push($('#fracAnt').val());
		preg2.push($('#primaAnt').val());

		preg3.push($('#claseDet').val());
		preg3.push($('#fracDet').val());
		preg3.push($('#primaDet').val());

		validarCierre.validaRespuesta(preg1, preg2, preg3);
	},validaRespuesta:function (preg1, preg2, preg3) {
		document.getElementById("lblRadioMateria").setAttribute("style", "display:none");
		document.getElementById("lblRadioClasificacion").setAttribute("style", "display:none");

			if (preg2[0] != preg3[0] && preg2[1] != preg3[1] && preg2[2] != preg3[2]){
				var $radios = $('input:radio[name=causaDesacuerdo.materiaDesacuerdo.idMateria]');
				$radios.filter("#radioClasificacion").prop('checked', true);
				$('#lblRadioClasificacion').removeAttr("style");
				$("#radioClasificacion").trigger("click");
				$("#modalDlg").addClass("hidden");
				validarCierre.avanzarEnMateria();
				$("#principal").removeClass("hidden");
				//Ocultar Trabajadores
				//trabajadorProm
				$('#trabajadorProm').val("0");
				//trabHide
				$('#trabHide').addClass("hidden");
			}else if (preg2[0] == preg3[0] && preg2[1] == preg3[1] && preg2[2] != preg3[2]){
				var $radios = $('input:radio[name=causaDesacuerdo.materiaDesacuerdo.idMateria]');
				$radios.filter("#radioMateria").prop('checked', true);
				$('#lblRadioMateria').removeAttr("style");
				$("#radioMateria").trigger("click");
				$("#modalDlg").addClass("hidden");
				dialogosCtrl.openDialAviso(dialogosComunes.deterPrimaSRT);
				validarCierre.avanzarEnMateria();
			}else {
				var $radios = $('input:radio[name=causaDesacuerdo.materiaDesacuerdo.idMateria]');
				$radios.filter("#radioClasificacion").prop('checked', true);
				$('#lblRadioClasificacion').removeAttr("style");
				$("#radioClasificacion").trigger("click");
				$("#modalDlg").addClass("hidden");
				validarCierre.avanzarEnMateria();
				$("#principal").removeClass("hidden");
				//trabajadorProm
				$('#trabajadorProm').val("0");
				//trabHide
				$('#trabHide').addClass("hidden");
			}
	},avanzarEnMateria: function() {
		$('#claseAnterior').val($('#claseAnt').val());
		$('#fracAnterior').val($('#fracAnt').val());
		$('#primAnterior').val($('#primaAnt').val());

	},initValForm: function() {
		$(this).dialog('close');
		$("#principal").removeClass("hidden");
	},valMosBtnAdr: function() {
		validarCierre.validaDtsDirecc = 1;
		$("#mosBtnAdr").addClass("hidden");
		$("#hidBtnAdr").removeClass("hidden");
		$("#addAdrees").removeClass("hidden");

		$("#calleInp").prop('required',true);
		$("#cpInp").prop('required',true);
		$("#ciudadInp").prop('required',true);
		$("#edoInp").prop('required',true);

	},valHidBtnAdr: function() {
		validarCierre.validaDtsDirecc = 0;
		$("#hidBtnAdr").addClass("hidden");
		$("#mosBtnAdr").removeClass("hidden");
		$("#addAdrees").addClass	("hidden");

		$('#calleInp').removeAttr("required");
		$('#cpInp').removeAttr("required");
		$('#ciudadInp').removeAttr("required");
		$('#edoInp').removeAttr("required");

	}};

function validarCamposModal() {
	$('div#divErrorCampos').hide();
	$('div#mensajesError').hide();
	var result;
	var reglas = [ {
		validacion : 'campoObligatorio',
		campos : [{campo : 'claseAnt'}, { campo : 'fracAnt'},
            {campo : 'primaAnt'}, {campo : 'claseDet'},
            {campo : 'fracDet'}, {campo : 'primaDet'} ]
	} ];

	if (validarCampo(reglas)) {
        var reglas2 = [ {
            validacion : 'limiteClase',
            campos : [{campo : 'claseAnt'}, {campo : 'claseDet'}]} ];
        if (validarCampo(reglas2)) {
            var reglas3 = [ {
                validacion : 'limitePrima',
                campos : [{campo : 'primaAnt'}, {campo : 'primaDet'}]} ];
            if (validarCampo(reglas3)) {
                result = true;
            }else {result = false;}
        }else{result = false;}
	} else {result = false;}

	return result;
}

function convertDateCorrect(date) {
	const dats = date;
	const [year, month, day] = dats.split('-');
	const res = [day, month, year].join('/');
	return res;
}

$(document).ready(function() {
	validarCierre.valSelPr1 = 0;
	funcionesComunes.init();

	$('#forModalDlg input').on('change',function() {
		validarCierre.valSelPr1 = $('input[name=radioOptMateria]:checked', '#forModalDlg').val();
	});


	$('#fechNotRes').on('change',function() {
		validarCierre.fecNotficaResol = convertDateCorrect($(this).val());
		registroDesacuerdoCtrl.validaFecNotificacion(validarCierre.fecNotficaResol);
	});

	$('#materiaDeterminacion').change(function() {
		var newVal = $(this).val();
		if(newVal == 1){
			dialogosCtrl.openDialAviso(dialogosComunes.resolCubPrima);
		}else if(newVal == 3){
			dialogosCtrl.openDialAviso(dialogosComunes.resolRecPrima);
		}
	});
});


var eventosComunes = {
	eventos: [{
		id: "#cancelarTramite",
		funcion: funcionesComunes.cancelarTramite
	}, {
		id: "#salirTramite",
		funcion: funcionesComunes.salirTramite
	},{
		id: "#iniciarTramite",
		funcion: funcionesComunes.iniciarTramite
	},{
		id: "#retomarTramite",
		funcion:  funcionesComunes.iniciarTramite
	},{
		id: "#salirModal",
		funcion:  funcionesComunes.salirTramite
	},{
		id: "#avanzarModal",
		funcion:  validarCierre.procesaDatosModal
	},{
		id: "#mosBtnAdr",
		funcion:  validarCierre.valMosBtnAdr
	},{
		id: "#hidBtnAdr",
		funcion:  validarCierre.valHidBtnAdr
	}]
};

var dialogosComunes = {
	"salirTramite" : {
		titulo: 'Confirmaci&oacute;n',
		mensaje:"&iquest; Estas seguro que deseas salir ?",
		buttons: {
			'No': dialogosCtrl.close,
			'Si': funcionesComunes.procesarSalirTramite
		}
	},
	"cancelarTramite" : {
		titulo: 'Confirmaci&oacute;n',
		mensaje:"&iquest; Estas seguro que deseas cancelar la solicitud con folio %FOLIO%?",
		buttons: {
			'No': dialogosCtrl.close,
			'Si': funcionesComunes.procesarCancelar
		}
	},
	"tramiteCancelado": {
		titulo: 'Informaci&oacute;n',
		mensaje:"La solicitud ha sido cancelada exitosamente",
		buttons: {
			'Salir': funcionesComunes.procesarSalirTramite
		}
	},
	"deterPrimaSRT": {
		titulo: 'Informaci&oacute;n',
		mensaje:"Conforme a la opci&oacute;n elegida, la resoluci&oacute;n a recurrir tiene como base "+
			"la obligaci&oacute;n que tienen las empresas de presentar durante el mes de febrero, la "+
			"Determinaci&oacute;n de la Prima en el Seguro de Riesgos de Trabajo derivada de la revisi&oacute;n "+
			"Anual de la Siniestralidad y la facultad del Instituto de determinar la Prima a patrones omisos "+
			"o rectificar la prima a aquellos que presentaron su Determinaci&oacute;n con datos falsos o incompletos.",
		largo: 300,
		ancho:700,
		buttons: {
			'Continuar': validarCierre.initValForm
		}
	},
	"avisoValIncomp": {
		titulo: 'Informaci&oacute;n',
		mensaje:"Por favor verifique que los valores ingresados sean correctos, ya que no se puede determinar la materia",
		buttons: {
			'Continuar': dialogosCtrl.close
		}
	},
	"resolCubPrima": {
		titulo: 'Resoluci&oacute;n para cubrir la prima media',
		mensaje:"Conforme a la opci&oacute;n elegida, la resoluci&oacute;n del Instituto tiene como "+
			"motivo que esa empresa omiti&oacute; presentar durante el mes de febrero la Determinaci&oacute;n de la "+
			"Prima en el Seguro de Riesgos de Trabajo derivada de la Revisi&oacute;n Anual de la Siniestridad.",
        largo: 250,
        ancho:600,
		buttons: {
			'Continuar': dialogosCtrl.close
		}
	},
	"resolRecPrima": {
		titulo: 'Resoluci&oacute;n de Rectificación de la Prima en el Seguro de Riesgos de Trabajo',
		mensaje:"Conforme a la opci&oacute;n elegida, la resoluci&oacute;n del Instituto tiene como "+
			"motivaci&oacute;n el detectar que esa empresa presentó su Determinación de la Prima derivada "+
			"de la Revisi&oacute;n Anual de la Siniestridad, con datos falsos o incompletos.",
        largo: 250,
        ancho:600,
		buttons: {
			'Continuar': dialogosCtrl.close
		}
	},
	"menFecNot": {
		titulo: 'Informaci&oacute;n',
		mensaje:"Ha sobrepasado los 15 d&iacute;as h&aacute;biles establecido en el Art. 41 del RACERF para presentar su Escrito de Desacuerdo",
        largo: 220,
        ancho:350,
		buttons: {
			'Continuar': dialogosCtrl.close
		}
	},
	"valAnVigMsj": {
		titulo: 'Importante',
		mensaje:"Capture el A&ntilde;o de Vigencia de la prima contenido en la resoluci&oacute;n materia de desacuerdo. Si no se encuentra "+
			"dentro del par&aacute;metro indicado a continuaci&oacute;n se le sugiere revisar que la materia de su desacuerdo sea la correcta",
		largo: 220,
		ancho:600,
		buttons: {
			'Continuar': dialogosCtrl.close
		}
	}
};
