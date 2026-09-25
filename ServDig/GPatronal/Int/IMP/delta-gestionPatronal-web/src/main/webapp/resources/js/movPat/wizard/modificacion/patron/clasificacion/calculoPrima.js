var PrimaCtrl = {
	mostrarSeccionPrima: function(clasesIguales) {
		if(!$("#divPrimaMSRT").is(":visible")) {
			$("#divPrimaMSRT").show();
		}
		if(!clasesIguales) {
			console.log("se habilita el campo de prima")
			$("#primaSRTFusionSust").val($("#primaAnterior").val());
			PrimaCtrl.mostrarPrimaResultante();
			$("#habilitarCapturaPrima").show();
		} else {
/*			console.log("Entro al calculo de la prima");
			PrimaCtrl.habilitarCapturaPrima();
*/		}
	},
	mostrarBotonesPrima: function(mostrar) {
		if(mostrar) {
			$("#btnCalcularPrima").hide();
			$("#botonesPrima").show();
		} else {
			$("#btnCalcularPrima").show();
			$("#botonesPrima").hide();	
		}
	},

	limpiarCalculoPrima: function(){
		$("#diasSubsidiados").val("");
		$("#porcentajeIncapacidades").val("");
		$("#numeroDefunciones").val("");
		$("#numeroTrabajadores").val("");
		$("#primaCalculada").val("");
		$("#primaSRTFusionSust").val("");
		$("#primaResultado").val("");
		$(".prima-bloqueable").removeAttr("disabled");
		PrimaCtrl.mostrarBotonesPrima(false);
	},

	mostrarPrimaResultante: function()  {
		$("#habilitarCapturaPrima").hide();
		$("#primaPatronPrincipal").show();
		$("#datosCalculoPrima").hide();
	},

	habilitarCapturaPrima: function() {
		PrimaCtrl.limpiarCalculoPrima();
	//Modificacion para Mm AMSRT-2 - WO436058
			console.log("::: Se consulta el valor de la prima historica de acuerdo con la fecha surte efecto");
			console.log("-- Consultando prima historica");	
			console.log("-- nrp: " + $("#nrpFus").val() + ", fechaEfecto: " + $("#fechaEfecto").val() + ", primaAnterior: " + $("#primaAnterior").val());		
				
			var form_data = new FormData();
			form_data.append("nrp", $("#nrpFus").val());
			form_data.append("fechaSurteEfecto", $("#fechaEfecto").val());

			$.ajax({
				url: "/${mvn.web.app.root}/componente/busquedaRP/obtienePrimaHistorica",
				type: 'post',
				dataType: 'json',
				processData: false,
				cache: false,
				contentType: false,
				data: form_data,
				success: function(data) {
					console.log("-- Prima historica obtenida: " + data);
					if (data == "error" || data == "") {
	//$("#primaAnterior").val("0.55555");
						console.log("::: No se encontro prima historica, se asigna la prima actual: " + $("#primaAnterior").val());
	//					construirDialogoMensajes(" ", "No se encontr&oacute; prima hist&oacute;rica, se asigna "+$("#primaAnterior").val()+" como prima anterior", true);
						return;
					}else{
						console.log("-- Prima historica encontrada, se asigna al campo de prima anterior: " + data);
						$("#primaAnterior").val(data);
					}
					
				},
				error: function(error) {
				console.log("::: Error interno al consultar prima historica");
				construirDialogoMensajes("ERROR", "\u00A1Error\u0021 ".bold() + "Error interno al consultar prima hist&oacute;rica", true);
				}
			});			

			console.log("::: Se muestran campos de captura para el calculo de la prima");
		$("#primaPatronPrincipal").hide();
		$("#datosCalculoPrima").show();
	},
	
	modificarPrima: function() {
		console.log("------ Le iba quitar el readOnly");
		//$("#primaSRTFusionSust").removeAttr("readonly");
		$("#habilitarCapturaPrima").hide();
	},

	aceptarPrimaCalculada: function() {
		var pregunta = "\u00BFEst&aacute;s de acuerdo con el c&aacute;lculo de la prima?";
		$("#textoConfirmacion").html(pregunta);
		var dialogo = $("#dialogoConfirmacion").dialog({
			autoOpen : false,
			resizable : false,
			modal : true,
			height : 200,
			width : 400,
			title : "Confirmaci&oacute;n",
			buttons : {
				"No" : function() {
					$(this).dialog("close");
				},
				"Si" : function() {
					PrimaCtrl.mostrarPrimaResultante();
					$(this).dialog("close");
				}
			}
		});
		dialogo.dialog('open');
	},

	calcularPrima: function() {
		if($("#formCalculoPrima").valid()) {
			let diasSubsidiados = +$("#diasSubsidiados").val(),
			porcentajeIncapacidades = +$("#porcentajeIncapacidades").val(),
			numeroDefunciones = +$("#numeroDefunciones").val(),
			numeroTrabajadores = +$("#numeroTrabajadores").val(),
			primaAnterior = parseFloat($("#primaAnterior").val()),
			$primaFusionSust = $("#primaSRTFusionSust"),
			primaResultante = null,
			diasEntre365 = +(diasSubsidiados/365).toFixed(8),
			sumaIncapacidadesEntreDefunciones= 28*(porcentajeIncapacidades+numeroDefunciones),
			factorPrimaEntreTrabajadores = +(2.3/numeroTrabajadores).toFixed(8);
			primaCalculada = [diasEntre365+sumaIncapacidadesEntreDefunciones]*(factorPrimaEntreTrabajadores)+0.0050;
			primaCalculada = primaCalculada * 100;
			primaCalculada = +primaCalculada.toFixed(5);
			if(primaCalculada <= 0.5 && primaAnterior <= 1.5) {
				primaResultante = primaCalculada;
			} else if(primaCalculada > 15 && primaAnterior > 14 ){
				primaResultante = 15;
			} else if(primaCalculada > (primaAnterior + 1)) {
				primaResultante = primaAnterior+1;
			} else if(primaCalculada >= primaAnterior && primaCalculada <= (primaAnterior + 1)) {
				primaResultante = primaCalculada;
			} else if(primaCalculada < (primaAnterior-1)) {
				primaResultante = primaAnterior-1;
			} else if(primaCalculada <= primaAnterior && primaCalculada >= (primaAnterior-1)) {
				primaResultante= primaCalculada;
			}
			
			primaResultante = +primaResultante.toFixed(5);
	
			$("#primaCalculada").val(primaCalculada);
			$primaFusionSust.val(primaResultante);

			console.log("------ Ya NO seras readOnly");			
/*			if(!$primaFusionSust.is("[readOnly]")) {
				$primaFusionSust.attr("readonly","readonly");
			}
*/			
			$(".prima-bloqueable").attr("disabled","disabled");
			$("#primaResultado").val(primaResultante);
			PrimaCtrl.mostrarBotonesPrima(true);
		}
	},
	
	
	init: function() {
		//Validamos que exista el boton de calcular prima
		if($("#btnCalcularPrima").length) {
			$("#btnCalcularPrima").on("click",PrimaCtrl.calcularPrima);
			$("#btnAceptarPrima").click(PrimaCtrl.aceptarPrimaCalculada);
			$("#btnLimpiarPrima").click(PrimaCtrl.limpiarCalculoPrima);
			$("#habilitarCapturaPrima").click(PrimaCtrl.modificarPrima);
			//verificamos si da backspace y si el campo es readonly quitamos la accion por default ya que regresa a la pantalla anterior
			$("#seccionEmpresaFusionada").find('input[type=text][readonly]').on('keydown',function(e){ 
				if( e.which == 8 && $(this).is("[readOnly]")){
					e.preventDefault();  
					return false;   
				} 
			});
			
			$("#diasSubsidiados").numeric({
				maxDigits: 4,
				maxDecimalPlaces: 0,
				maxPreDecimalPlaces: 4
			});
			
			$("#porcentajeIncapacidades").numeric({
				maxDigits: 5,
				maxDecimalPlaces: 2,
				maxPreDecimalPlaces: 3
			});
			
			$("#numeroDefunciones").numeric({
				maxDigits: 3,
				maxDecimalPlaces: 0,
				maxPreDecimalPlaces: 3
			});
			
			$("#numeroTrabajadores").numeric({
				maxDigits: 6,
				maxDecimalPlaces: 1,
				maxPreDecimalPlaces: 5
			});
			
			$("#formCalculoPrima").validate({
				errorClass: "errorDocs",
				errorElement: "span",
				rules: { 
					"diasSubsidiados": {
						required:true
					}, 
					"porcentajeIncapacidades": {
						required: true
					},
					"numeroDefunciones": {
						required: true
					},
					"numeroTrabajadores": {
						required: true
					}
				}
			});
		}
	}
}

$(document).ready(PrimaCtrl.init);