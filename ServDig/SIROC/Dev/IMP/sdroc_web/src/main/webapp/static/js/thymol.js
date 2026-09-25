/*
*Fecha UM    : 29 de Agosto del 2021
*Version UM  : 3.6
*Autor UM    : Victor Hernandez,Erika Gutierrez
*Descripcion : Cambios EREQ-SIROC-OUTSOURCING, se deja la validaci�n de REPSE y fecha solo para Subcontratista de Ejecuci�n
                de Obra Especializada, para obra p�blica y privada.
                
*Fecha UM    : 07 de Octubre del 2021
*Version UM  : 3.7
*Autor UM    : Erika Gutierrez
*Descripcion : Cambios para el mantenimiento SIROC para tratamiento de patrones con amparo
*/
var tipoObra = 0;
var tipoPatron = "";
var datosRegObra = {};
var jsonDomi = "";
var valFechaIni = true;
var valFechaFin = true;
var IniciaContrOutsourcing = new Date(2021, 8, 1);

var intervalo;
function iniciaSenso() {
	ejecutaIntervalo($("#lblNumRegistroObra").text());
}

function ejecutaIntervalo(numeroRegistroObra) {

	intervalo = setInterval(function() {
		serviciosObraCtrl.sensaObra(numeroRegistroObra);
	}, 60000);
}

function udm_(e){var t="comScore=",n=document,r=n.cookie,i="",s="indexOf",o="substring",u="length",a=2048,f,l="&ns_",c="&",h,p,d,v,m=window,g=m.encodeURIComponent||escape;if(r[s](t)+1)for(d=0,p=r.split(";"),v=p[u];d<v;d++)h=p[d][s](t),h+1&&(i=c+unescape(p[d][o](h+t[u])));e+=l+"_t="+ +(new Date)+l+"c="+(n.characterSet||n.defaultCharset||"")+"&c8="+g(n.title)+i+"&c7="+g(n.URL)+"&c9="+g(n.referrer),e[u]>a&&e[s](c)>0&&(f=e[o](0,a-8).lastIndexOf(c),e=(e[o](0,f)+l+"cut="+g(e[o](f+1)))[o](0,a)),n.images?(h=new Image,m.ns_p||(ns_p=h),h.src=e):n.write("<","p","><",'img src="',e,'" height="1" width="1" alt="*"',"><","/p",">")};
function uid_call(a, b){
       ui_c2 = 17183199; // your corporate c2 client value
       ui_ns_site = 'gobmx'; // your sites identifier
       window.b_ui_event = window.c_ui_event != null ? window.c_ui_event:"",window.c_ui_event = a;
       var ui_pixel_url = 'https://sb.scorecardresearch.com/p?c1=2&c2='+ui_c2+'&ns_site='+ui_ns_site+'&name='+a+'&ns_type=hidden&type=hidden&ns_ui_type='+b;
       var b="comScore=",c=document,d=c.cookie,e="",f="indexOf",g="substring",h="length",i=2048,j,k="&ns_",l="&",m,n,o,p,q=window,r=q.encodeURIComponent||escape;if(d[f](b)+1)for(o=0,n=d.split(";"),p=n[h];o<p;o++)m=n[o][f](b),m+1&&(e=l+unescape(n[o][g](m+b[h])));ui_pixel_url+=k+"_t="+ +(new Date)+k+"c="+(c.characterSet||c.defaultCharset||"")+"&c8="+r(c.title)+e+"&c7="+r(c.URL)+"&c9="+r(c.referrer)+"&b_ui_event="+b_ui_event+"&c_ui_event="+c_ui_event,ui_pixel_url[h]>i&&ui_pixel_url[f](l)>0&&(j=ui_pixel_url[g](0,i-8).lastIndexOf(l),ui_pixel_url=(ui_pixel_url[g](0,j)+k+"cut="+r(ui_pixel_url[g](j+1)))[g](0,i)),c.images?(m=new Image,q.ns_p||(ns_p=m),m.src=ui_pixel_url):c.write("<p><img src='",ui_pixel_url,"' height='1' width='1' alt='*'></p>");
}

$(document).ready(function() {
	$('.collapse').on('shown.bs.collapse', function(){
		$("#imgCollapsable").removeAttr("src").attr("src","/sdroc_web/static/images/panel-collapsed.png")
	}).on('hidden.bs.collapse', function(){
		$("#imgCollapsable").removeAttr("src").attr("src","/sdroc_web/static/images/panel.png")
	});

    udm_('https://sb.scorecardresearch.com/b?c1=2&c2=17183199&ns_site=gobmx&name=imss.asegurados.asignacionNSS.inicio');

    if($("#imgSemaforo").length) {
    	console.log("consulto el semaforo de evaluacion del sat")
    	calcularSemaforoEvaluacionSAT();
    }

	cargarEtiquetasConsulta();

	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

	tablasCtrl.initDataTables();


});

$(document).ready(function() {

	$("#btnVerAcuseVisor").click(function() {
		$("#visorAcusesReporte").removeClass("hidden");
	});

	$("#txtSuperficie").keypress(function(e) {
		if (!onlyNumber(e)) {
			return false;
		}
	});

	$("#txtMonto").keypress(function(e) {
		if (!onlyNumber(e)) {
			return false;
		}
	});

	$("#txtNumTrabajadores").keypress(function (e) {
		if (!onlyNumber(e)) {
			return false;
		}
	});

	var configMask = {
		prefix : '',
		thousands : ',',
		allowZero : true,
		allowNegative : false,
		defaultZero : false,
		decimal : '.',
		precision : 0,
		affixesStay : false,
		symbolPosition : 'left'
	};

    var configMaskSub = {
        prefix: '',
        thousands: ',',
        allowNegative: false,
        defaultZero: false,
        precision: 0,
        affixesStay: false
    }

	$('#txtMonto').maskMoney(configMask);
	$('#txtSuperficie').maskMoney(configMask);
    $('#txtNumTrabajadores').maskMoney(configMaskSub);


});

$(document)
		.ready(
				function() {

					var navListItems = $('ul.wizard-steps-extensive li a'), allWells = $('.setup-content'), allNextBtn = $('.nextBtn');

					allWells.hide();

					navListItems.click(function(e) {
						e.preventDefault();
						$("#step-1").hide();
						var $target = $($(this).attr('href')), $item = $(this);

						if (!$item.hasClass('disabled')) {
							navListItems.removeClass('btn-primary').addClass(
									'btn-default');
							$item.addClass('btn-primary');
							allWells.hide();
							$target.show();
							$target.find('input:eq(0)').focus();
						}
					});

					allNextBtn.click(function() {
								var curStep = $(this).closest(".setup-content"), curStepBtn = curStep
										.attr("id"), nextStepWizard = $(
										'ul.wizard-steps-extensive li a[href="#'
												+ curStepBtn + '"]').parent()
										.next().children("a"), curInputs = curStep
										.find("input[type='text'],input[type='url']"), isValid = true;

								$(".form-group").removeClass("has-error");
								for (var i = 0; i < curInputs.length; i++) {
									if (!curInputs[i].validity.valid) {
										isValid = false;
										$(curInputs[i]).closest(".form-group")
												.addClass("has-error");
									}
								}

								if (isValid)
									nextStepWizard.removeAttr('disabled')
											.trigger('click');
							});

					$('div.setup-panel div a.btn-primary').trigger('click');


});

$(document).ready(function() {

					ubicacionCtrl.init();
					panelesCtrl.init();

					$("#btnValidarRegObra").click(registroObraCtrl.eventoValidarRegObra);

					$("#txtNumRegistro")
							.keyup(
									function(e) {
										var dInput = this.value;
										$("#msgValAlfanum").addClass("hidden");

										if ($.trim(dInput) == '') {

											$("#btnUbicacion").addClass("hidden");
											$("#pnlDatosObraRegistrado").addClass("hidden");
											$("#msgSinCoincidencias").addClass("hidden");
											$("#chkSinRegistro").removeAttr("disabled");
											$("#txtNumRegistro").css('border-color', '#a94442');
										} else {
											if (/^[A-Za-z0-9]+$/.test($("#txtNumRegistro").val())) {
												this.value = this.value.toUpperCase();
												$("#msgNumRegistro").addClass("hidden");
												$("#msgSinSeleccion").addClass("hidden");
												$("#msgSinCoincidencias").addClass("hidden");
												$("#msgFormatoIncorrecto").addClass("hidden");
												$("#msgLongitudIncorrecta").addClass("hidden");
												$("#msgErrorAjax").addClass("hidden");
												$("#txtNumRegistro").css('border-color', '#ccc');
												$("#chkSinRegistro").attr("disabled", true);
											} else {
												$("#msgValAlfanum").removeClass("hidden");
												$("#msgNumRegistro").addClass("hidden");
												$("#msgErrorAjax").addClass("hidden");
												$("#msgSinSeleccion").addClass("hidden");
												$("#msgSinCoincidencias").addClass("hidden");
												$("#msgFormatoIncorrecto").addClass("hidden");
												$("#msgLongitudIncorrecta").addClass("hidden");
											}
										}
									});

					$("#btnValidarRFC").click(function() {
						if ($("#rfcSinNumReg").val() == '') {
							$("#msgRFC").removeClass("hidden");
							$("#rfcSinNumReg").css('border-color', '#a94442');
							$("#btnUbicacion").addClass("hidden");
							if (tipoPatron == "2") {
								$("#idContraRFC").removeClass("hidden");
								$("#idSubContraRFC").addClass("hidden");
								$("#idInterRFC").addClass("hidden");
							} else if (tipoPatron == "3") {
								$("#idContraRFC").addClass("hidden");
								$("#idSubContraRFC").removeClass("hidden");
								$("#idInterRFC").addClass("hidden");
							} else if (tipoPatron == "4") {
								$("#idContraRFC").addClass("hidden");
								$("#idSubContraRFC").addClass("hidden");
								$("#idInterRFC").removeClass("hidden");
							}

						} else {
							registroObraCtrl.validarRFC();
						}
					});

					$("#chkSinRegistro").click(function() {
						$('#lblMsgObraNE').text('');
						$('#lblMsgObraNE').addClass("hidden");
						$("#msgRFC").addClass("hidden");

						if ($('#chkSinRegistro').is(":checked")) {
							$("#txtNumRegistro").prop('disabled', true);
							$("#txtNumRegistro").val('');
							$("#pnlSinNumeroReg").removeClass("hidden");
							$("#msgNumRegistro").addClass("hidden");
							$("#txtNumRegistro").css('border-color', '#ccc');
							$("#msgSinSeleccion").addClass("hidden");
							$("#msgSinCoincidencias").addClass("hidden");
							$("#pnlDatosPatron").removeClass("hidden");
							$("#btnValidarRegObra").addClass("disabled");
							$("#btnValidarRegObra").removeClass("btn-primary");
							$("#btnValidarRegObra").addClass("btn-default");
							$("#idTituloUbicacion").html("Aviso de Ubicaci&oacute;n de la Obra");
							$("#btnUbicacion").addClass("hidden");

						} else {
							$("#idTituloUbicacion").html("Ubicaci&oacute;n de la Obra");
							$("#txtNumRegistro").prop('disabled', false);
							$("#pnlSinNumeroReg").addClass("hidden");
							$("#btnValidarRegObra").removeClass("disabled");
							$("#msgNumRegistro").addClass("hidden");
							$("#msgSinSeleccion").addClass("hidden");
							$("#msgSinCoincidencias").addClass("hidden");
							$("#pnlDatosPatron").addClass("hidden");
							$("#btnValidarRegObra").removeClass("btn-default");
							$("#btnValidarRegObra").addClass("btn-primary");

						}



});

					$("#rfcSinNumReg").keyup(function(e) {
						this.value = this.value.toUpperCase();
					});


    crearDialogo("#idOutsourcing", {
        "Aceptar" : function() {$(this).dialog("close")}}, "Confirmaci&oacute;n"
        );
        $("#idOutsourcing").removeClass("hidden");

					$("#btnTipoPatron").click(function() {

						var titulo = "Registro de Obra";
						var tituloNumeroRegistro = "Confirmaci&oacute;n";

						if (tipoPatron != '') {
							$("#btnUbicacion").addClass("hidden");
							var curStep = $(this).closest(".setup-content"),
								curStepBtn = curStep.attr("id"),
								nextStepWizard = $('ul.wizard-steps-extensive li a[href="#'+ curStepBtn + '"]').parent().next().children("a"),
								curInputs = curStep.find("input[type='text'],input[type='url']"),
								isValid = true;
							nextStepWizard.removeAttr('disabled').trigger('click');

							var stepLi = curStepBtn + '0';

							$("#" + stepLi).addClass("completed");
							$("#" + stepLi).children('a').remove();
							if ((tipoObra == 1 && tipoPatron == "1") || (tipoObra == 2 && tipoPatron == "2")) {
								parent.DomicilioCtrl.localizar();
							} else if (tipoObra == 1 && tipoPatron == "2") {
								// tipo de obra privada
								// registrada por el contratista

								// $("#numeroRegistroModal").modal();
								$("#pnlRegistroUbicacion").removeClass("hidden");
								$("#pnlComponenteUbicacion").addClass("hidden");
								$("#idContraNum").removeClass("hidden");
								$("#idSubContraNum").addClass("hidden");
								$("#idInterNum").addClass("hidden");
								$("#idContra").removeClass("hidden");
								$("#idSubContra").addClass("hidden");
								$("#idInter").addClass("hidden");
								$("#idContratista").removeClass("hidden");
								$("#idSubContratista").addClass("hidden");
								$("#idIntermediario").addClass("hidden");
								crearDialogo("#numeroRegistroModal",
										{
											"Continuar" : function() {
												$(this).dialog("close")
											}
										}, tituloNumeroRegistro);

							} else if (tipoObra == 2
									&& tipoPatron == "3") {
								// tipo de obra publica
								// registrada por el subcontratista

								// $("#numeroRegistroModal").modal();
								$("#pnlRegistroUbicacion").removeClass("hidden");
								$("#pnlComponenteUbicacion").addClass("hidden");

								$("#idSubContraNum").removeClass("hidden");
								$("#idContraNum").addClass("hidden");
								$("#idInterNum").addClass("hidden");
								$("#idSubContra").removeClass("hidden");
								$("#idContra").addClass("hidden");
								$("#idInter").addClass("hidden");
								$("#idSubContratista").removeClass("hidden");
								$("#idSubEspecializado").removeClass("hidden");
								$("#idContratista").addClass("hidden");
								$("#idIntermediario").addClass("hidden");

								crearDialogo("#subEspecializado",
									{
										"SI": function () {
											console.log(typeof tipoPatron);
											console.log(tipoPatron);
											tipoPatron = "5";
											console.log('>>>>>>>>>>>>>> el tipo patron sera: ' + tipoPatron);
											$(this).dialog("close");
										},
										"NO": function () {
											$(this).dialog("close");
										}
                    				}, tituloNumeroRegistro);

								crearDialogo("#numeroRegistroModal",
										{
											"Continuar" : function() {
												$(this).dialog("close")
											}
										}, tituloNumeroRegistro);
							} else if (tipoObra == 1 && tipoPatron == "3") {
								// tipo de obra privada
								// registrada por el subcontratista

								// $("#numeroRegistroModal").modal();
								$("#pnlRegistroUbicacion").removeClass("hidden");
								$("#pnlComponenteUbicacion").addClass("hidden");
								$("#idSubContraNum").removeClass("hidden");
								$("#idContraNum").addClass("hidden");
								$("#idInterNum").addClass("hidden");
								$("#idSubContra").removeClass("hidden");
								$("#idContra").addClass("hidden");
								$("#idInter").addClass("hidden");
								$("#idSubContratista").removeClass("hidden");
								$("#idSubEspecializado").removeClass("hidden");
								$("#idContratista").addClass("hidden");
								$("#idIntermediario").addClass("hidden");

								crearDialogo("#subEspecializado",
									{
										"SI": function () {
											tipoPatron = "5";
											$(this).dialog("close");
										},
										"NO": function () {
											$(this).dialog("close");
										}
                    				}, tituloNumeroRegistro);

								crearDialogo("#numeroRegistroModal",
										{
											"Continuar" : function() {
												$(this).dialog("close")
											}
										}, tituloNumeroRegistro);

							} else if (tipoObra == 2 && tipoPatron == "4") {
								// tipo de obra publica
								// registrada por el intermediario

								// $("#numeroRegistroModal").modal();
								$("#pnlRegistroUbicacion").removeClass("hidden");
								$("#pnlComponenteUbicacion").addClass("hidden");

								$("#idInterNum").removeClass("hidden");
								$("#idContraNum").addClass("hidden");
								$("#idSubContraNum").addClass("hidden");
								$("#idInter").removeClass("hidden");
								$("#idContra").addClass("hidden");
								$("#idSubContra").addClass("hidden");

								$("#idIntermediario").removeClass("hidden");
								$("#idContratista").addClass("hidden");
								$("#idSubContratista").addClass("hidden");
								crearDialogo("#numeroRegistroModal",
										{
											"Continuar" : function() {
												$(this).dialog("close")
											}
										}, tituloNumeroRegistro);
							} else if (tipoObra == 1 && tipoPatron == "4") {
								// tipo de obra publica
								// registrada por el Intermediario

								// $("#numeroRegistroModal").modal();
								$("#pnlRegistroUbicacion").removeClass("hidden");
								$("#pnlComponenteUbicacion").addClass("hidden");

								$("#idInterNum").removeClass("hidden");
								$("#idContraNum").addClass("hidden");
								$("#idSubContraNum").addClass("hidden");
								$("#idInter").removeClass("hidden");
								$("#idContra").addClass("hidden");
								$("#idSubContra").addClass("hidden");

								$("#idIntermediario").removeClass("hidden");
								$("#idContratista").addClass("hidden");
								$("#idSubContratista").addClass("hidden");
								crearDialogo("#numeroRegistroModal",
										{
											"Continuar" : function() {
												$(this).dialog("close")
											}
										}, tituloNumeroRegistro);
							}


							if ((tipoObra == 1 && tipoPatron == "1") || (tipoObra == 2 && tipoPatron == "2")) {
								parent.DomicilioCtrl.setOnCloseCallback(ubicacionCtrl.validarDelegacion);
							} else {
								parent.DomicilioCtrl.setOnCloseCallback(ubicacionCtrl.setDomicilioSinSepomex);
							}

						} else {
							$("#msgPatron").removeClass("hidden");
						}

					});

					$("#btnDatosObra").click(function() {

						if (validarDatosRegistroObra() == true && valFechaIni && valFechaFin) {
							// $("#regObraModal").dialog();

							// BRHG
							var rfcSinNumReg = $("#rfcSinNumReg").val();
							var tituloMensaje = '';
							var textoMensaje = '';
							if (rfcSinNumReg != '' && rfcSinNumReg != null) {
								tituloMensaje = 'Registro de Aviso de Ubicaci&oacute;n de Obra';
								textoMensaje = '&iquest;Est&aacute; seguro de generar el registro de Aviso de Ubicaci&oacute;n de Obra con la informaci&oacute;n capturada?';
							} else {
								tituloMensaje = 'Registro de obra';
								textoMensaje = '&iquest;Est&aacute; seguro de generar el registro de la obra con la informaci&oacute;n capturada?';

							if(tipoPatron == 4){
								tituloMensaje = 'Registro de contrato';
								textoMensaje = '&iquest;Est&aacute; seguro de generar el registro del contrato con la informaci&oacute;n capturada?';
							}

							}

							var $mensajeConfirmacionRegistro = $('#regObraModal');
							$mensajeConfirmacionRegistro.dialog({
								autoOpen : false,
								title : tituloMensaje,
								resizable : false,
								closeOnEscape : false,
								modal : true,
								width : 500,
								buttons : {
									"No" : function() {
										$(this).dialog("close");
										$(this).dialog("destroy");

									},
									"Si" : function() {
										firmaExternaCtrl.firmarRegistroObra(registroObraCtrl.procesarFirma);
										$(this).dialog("close");
									}
								}
							}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
							$mensajeConfirmacionRegistro.html(textoMensaje);
							$mensajeConfirmacionRegistro.dialog('open');

						} else {
							console.log('algo salio muy mal ');
						}
					});

					function validarMonto() {
						var resultadol = true, mensajeError = null, valorMonto = $("#txtMonto").val();
						if (valorMonto == '') {
							mensajeError =  tipoPatron == 4 ? "Capture monto del contrato" : "Capture el monto de la obra";
							$("#txtMonto").marcarBordeError(true,"#msgeMonto", mensajeError);
							resultadol = false;
						} else if (parseInt(valorMonto,10) < 1) {
							mensajeError = "El monto de " + (tipoPatron == 4 ? "contrato" : "obra") +" a capturar debe ser mayor a cero";
							$("#txtMonto").marcarBordeError(true,"#msgeMonto", mensajeError);
							resultadol = false;
						} else {
							$("#txtMonto").marcarBordeError(false,"#msgeMonto");
						}
						return resultadol;

					}

					function validarSuperficie() {
						var resultadol = true, $txtSuperficie = $("#txtSuperficie");
						if ($txtSuperficie.val() != '') {
							if (parseInt($txtSuperficie.val()) < 1) {
								$txtSuperficie.marcarBordeError(true,"#msgeSuperficie", "Error en Superficie de construcci\u00F3n");
								resultadol = false;
							} else {
								$txtSuperficie.marcarBordeError(false,"#msgeSuperficie");
							}
						} else {
							$txtSuperficie.marcarBordeError(true,"#msgeSuperficie", "Error en Superficie de construcci\u00F3n");
							resultadol = false;
						}
						return resultadol;
					}

					function validarTipoObra() {
						var resultadol = true, $selTipoObra = $("#selTipoObra");
						if ($selTipoObra.val() == '0' || $selTipoObra.val() == 'Seleccione ...') {
							$selTipoObra.marcarBordeError(true,"#msgeTipoObra");
							resultadol = false;
						} else {
							$selTipoObra.marcarBordeError(false,"#msgeTipoObra");
						}
						return resultadol;

					}

					function validarObjetoContrato() {
						var resultadol = true;
						if ($("#selObjetoContrato").val() == '0'
								|| $("#selObjetoContrato").val() == 'Seleccione ...') {
							$("#msgeObjetoContrato").removeClass("hidden");
							$("#selObjetoContrato").css('border-color','#a94442');
							resultadol = false;
						} else {
							$("#msgeObjetoContrato").addClass("hidden");
							$("#selObjetoContrato").css('border-color', '#ccc');
						}
						return resultadol;

					}

					function validarObjetoContratoSubEsp() {
						var resultado1 = true, txtObjetoContratoSubEsp = $("#txtObjetoContratoSubEsp").val();
						if (txtObjetoContratoSubEsp == "") {
							$("#txtObjetoContratoSubEsp").marcarBordeError(true, "#msgeObjetoContratoSubEsp");
							resultado1 = false;
						} else {
							$("#txtObjetoContratoSubEsp").marcarBordeError(false, "#msgeObjetoContratoSubEsp")
						}
						return resultado1;
					}

					function validarOtroObjetoContrato() {
						var resultado1 = true, $valorObjetoContrato = $("#selObjetoContrato").val(),
							$valorOtroObjetoContrato = $("#otroObjetoContrato").val();
						if ($valorObjetoContrato == '6' && $valorOtroObjetoContrato == "") {
							$("#otroObjetoContrato").marcarBordeError(true, "#msgeOtroObjetoContrato");
							resultado1 = false;
						} else {
							$("#otroObjetoContrato").marcarBordeError(false, "#msgeOtroObjetoContrato")
						}
						return resultado1;
					}

					function validarNumAproxTrabajadores() {
						var resultado1 = true, txtNumTrabajadores = $("#txtNumTrabajadores").val();
						if (txtNumTrabajadores == "") {
							$("#txtNumTrabajadores").marcarBordeError(true, "#msgeNumTrabajadores");
							resultado1 = false;
						} else if (parseInt(txtNumTrabajadores) < 1) {
							mensajeError = "El numero de trabajadores debe de ser mayor a cero.";
							$("#txtNumTrabajadores").marcarBordeError(true, "#msgeNumTrabajadores", mensajeError);
							resultado1 = false;
						} else {
							console.log('todo ok');
							$("#txtNumTrabajadores").marcarBordeError(false, "#msgeNumTrabajadores");
						}
						return resultado1;
					}


					function validarNumSTPS() {
						console.log("Inicia validar num STPS");
						var resultado1 = true, txtNumRegSTPS = $("#txtNumRegSTPS").val();
						console.log(txtNumRegSTPS);
						if (txtNumRegSTPS == "") {
							console.log('cadena vacia');
							$("#txtNumRegSTPS").marcarBordeError(true, "#msgeNumRegSTPS");
							resultado1 = false;
						} else {
							console.log('todo ok');
							$("#txtNumRegSTPS").marcarBordeError(false, "#msgeNumRegSTPS")
						}
						return resultado1;
					}

					function validarNumProcedimiento() {
						var resultadol = true;
						var numeroProc = $("#txtNumProcedimiento").val();

						$("#msgeNumProcedimientoCaracteres").addClass("hidden");
						$("#msgeNumProcedimiento").addClass("hidden");

						if (numeroProc == '') {
							$("#txtNumProcedimiento").css('border-color','#a94442');
							$("#msgeNumProcedimiento").removeClass("hidden");
							resultadol = false;
						} else {
							$("#msgeNumProcedimiento").addClass("hidden");
							$("#txtNumProcedimiento").css('border-color','#ccc');

							if (/^[A-Za-z0-9]+$/.test(numeroProc)) {
								resultadol = true;
								$("#msgeNumProcedimientoCaracteres").addClass("hidden");
								$("#txtNumProcedimiento").css('border-color','#ccc');
							} else {
								$("#msgeNumProcedimientoCaracteres").removeClass("hidden");
								$("#txtNumProcedimiento").css('border-color','#a94442');
								resultadol = false;
							}
							// $("#regObraModal").modal();
						}
						return resultadol;
					}

					function validarNumeroAviso() {
						var resultadol = true;
						var numAviso = $("#txtNumAviso").val();

						if (numAviso != undefined && numAviso.length > 0) {

							if (/^[0-9]+$/.test(numAviso)) {
								resultadol = true;
								$("#msgeNumAviso").addClass("hidden");
								$("#txtNumAviso").css('border-color', '#ccc');
							} else {
								resultadol = false;
								$("#msgeNumAviso").removeClass("hidden");
								$("#msgeNumAviso").html("&Uacute;nicamente se admiten caracteres num&eacute;ricos.");
								$("#txtNumAviso").css('border-color', '#a94442');
							}
						} else {
							resultadol = true;
							$("#msgeNumAviso").text('');
							$("#msgeNumAviso").addClass("hidden");
							$("#txtNumAviso").css('border-color', '#ccc');
						}
						return resultadol;
					}

					function validarPeriodo() {
						var resFecIni = true;
						var resFecTer = true;
						var resultadol = true;
						var rpAmparo = $("#patronAmparo").val();

						if ($("#fecInicio").val() == '') {
							$("#msgeFecIni").text(
									'Seleccione la fecha de inicio');
							$("#msgeFecIni").removeClass("hidden");
							$("#fecInicio").css('border-color', '#a94442');
							resFecIni = false;
						}

						if ($("#fecTermino").val() == '') {
							$("#msgeFecFin").removeClass("hidden");
							$("#fecTermino").css('border-color', '#a94442');
							$("#msgeFecFin").html('Seleccione la fecha de t&eacute;rmino');
							resFecTer = false;
						}

						if (tipoPatron == 4 || tipoPatron == 5) {
							var fecInicio = convertirFechaFormatoDatePicker($("#fecInicio").val());

							if (fecInicio < IniciaContrOutsourcing) {
								if(rpAmparo != 1){
									$("#msgeFecIni").text(
										'La fecha de inicio no puede ser menor a la fecha del 1 de septiembre del 2021');
									$("#msgeFecIni").removeClass("hidden");
									$("#fecInicio").css('border-color', '#a94442');
									resFecIni = false;
								}
							}
						}

						if (resFecIni != true || resFecTer != true) {
							resultadol = false;
						}



						return resultadol;
					}

					/*
					 * validarDatosRegistroObra: Metodo que realiza las
					 * validaciones para el registro de la obra
					 */
					function validarDatosRegistroObra() {

						var resultado = true,resulMonto = true,resulPeriodo = true,
						resulSuperficie = true,resulTipoObra = true, resulObjetoContra = true,
						resultadoProc = true, resulAviso = true, resulOtroObjContra = true,
						resultadoNumAproxTrabajadores = true, resultadoSTPS = true, resulObjetoObraSubEsp = true,
						rfcSinNumReg = $("#rfcSinNumReg").val(),
						sinRegistroCheck = $('#chkSinRegistro').is(":checked");

						if (tipoPatron == "1") {
							resulMonto = validarMonto();
							resulPeriodo = validarPeriodo();
							resulTipoObra = validarTipoObra();
							resulSuperficie = validarSuperficie();
							// resulObjetoContra = validarObjetoContrato();
						}else if(tipoObra == 1) {//TIPO DE OBRA PRIVADA
							if(tipoPatron == "2" || tipoPatron == "3") {// 2- tipo de obra privada con contratista, 3- obra privada para subcontratista
								if (!sinRegistroCheck) {
									resulAviso = validarNumeroAviso();
								}
								if (rfcSinNumReg == '' || rfcSinNumReg == null) {
									resulPeriodo = validarPeriodo();
									resulMonto = validarMonto();
									resulSuperficie = validarSuperficie();
									resulTipoObra = validarTipoObra();
                    				//resultadoSTPS = validarNumSTPS();
									// resulObjetoContra = validarObjetoContrato();
								}
							} else if(tipoPatron == "4") {// obra privado para intermediarios
								if (!sinRegistroCheck) {
									resulAviso = validarNumeroAviso();
								}
								if (rfcSinNumReg == '' || rfcSinNumReg == null) {
									resulPeriodo = validarPeriodo();
									resulMonto = validarMonto();
									resulObjetoContra = validarObjetoContrato();
									resulOtroObjContra = validarOtroObjetoContrato();
								}
							} else if (tipoPatron == '5') {// obra privada para subcontratista especializado
								if (!sinRegistroCheck) {
									resulAviso = validarNumeroAviso();
								}
								if (rfcSinNumReg == '' || rfcSinNumReg == null) {
									console.log('Validaciones objeto Contrato subcontratista especializado');
									resulPeriodo = validarPeriodo();
									resulObjetoObraSubEsp = validarObjetoContratoSubEsp();
									resulMonto = validarMonto();
									resultadoNumAproxTrabajadores = validarNumAproxTrabajadores();
									resultadoSTPS = validarNumSTPS();
								}
							}
						}else if(tipoObra == 2) {
							if(tipoPatron == "2") {// tipo de obra publica con contratista
								resulMonto = validarMonto();
								resulPeriodo = validarPeriodo();
								resulTipoObra = validarTipoObra();
								resultadoProc = validarNumProcedimiento();
               					//resultadoSTPS = validarNumSTPS();
								// resultado = validarObjetoContrato();
								//resultado = validarSuperficie();
							} else if(tipoPatron == "3" || tipoPatron == "4") {//3- Obra publica para el subcontratista msgeNumAviso, 4- obra publica para intermediarios
								if (!sinRegistroCheck) {
									resulAviso = validarNumeroAviso();
								}
								if (rfcSinNumReg == '' || rfcSinNumReg == null) {
									resulMonto = validarMonto();
									resulPeriodo = validarPeriodo();
									if(tipoPatron == "3"){
										resulTipoObra = validarTipoObra();
                       					//resultadoSTPS = validarNumSTPS();
									} else {
										resulObjetoContra = validarObjetoContrato();
										resulOtroObjContra = validarOtroObjetoContrato();
									}
								}
							} else if (tipoPatron == '5') {// obra privada para subcontratista especializado
								if (!sinRegistroCheck) {
									resulAviso = validarNumeroAviso();
								}
								if (rfcSinNumReg == '' || rfcSinNumReg == null) {

									resulPeriodo = validarPeriodo();
									resulObjetoObraSubEsp = validarObjetoContratoSubEsp();
									resulMonto = validarMonto();
									resultadoNumAproxTrabajadores = validarNumAproxTrabajadores();
									resultadoSTPS = validarNumSTPS();
								}
							}
						}
						//Si alguna de las validaciones es incorrecta ponemos en false la bandera de la validacion
						if (!resulMonto || !resulPeriodo || !resulTipoObra || !resulSuperficie || !resulObjetoContra || !resultadoProc || !resulAviso
							|| !resulOtroObjContra || !resultadoNumAproxTrabajadores || !resultadoSTPS || !resulObjetoObraSubEsp) {
							resultado = false;
						}

						// Validacion de fechas para avisos de ubicacion de obra
						if (sinRegistroCheck) {
							console.log("Entro a validar las fechas")
							var fecInicio = $("#fecInicio").val(),
							fecTermino = $("#fecTermino").val();
							$("#msgeFecFin").html('');
							$("#msgeFecIni").html('');

							if ((fecInicio != undefined && fecInicio != null && fecInicio != '') && (fecTermino == undefined || fecTermino == null || fecTermino == '')) {

								$("#fecTermino").marcarBordeError(true,"#msgeFecFin",'Seleccione la fecha de t&eacute;rmino');
								resultado = false;
							} else if ((fecTermino != undefined && fecTermino != null && fecTermino != '') && (fecInicio == undefined || fecInicio == null || fecInicio == '')) {

								$("#fecInicio").marcarBordeError(true,"#msgeFecIni",'Seleccione la fecha de inicio');
								resultado = false;
							} else {

								$("#fecTermino").marcarBordeError(false,"#msgeFecFin");
								$("#fecInicio").marcarBordeError(false,"#msgeFecIni");

								var fechaInicioDP = convertirFechaFormatoDatePicker(fecInicio),
								fechaTermino = convertirFechaFormatoDatePicker(fecTermino);

								if (fechaInicioDP > fechaTermino) {

									banderaFecIni = false;
									$("#fecTermino").marcarBordeError(true,"#msgeFecFin",'La fecha de t&eacute;rmino no puede ser menor a la fecha de inicio');
									resultado = false;
								} else {
									$("#fecTermino").marcarBordeError(false,"#msgeFecFin");
									$("#fecInicio").marcarBordeError(false,"#msgeFecIni");
									resultado = true;
								}
							}
						}

						return resultado;
					}

					$("#btnClosePantalla").click(function() {
						parent.WizardRegistroObraCtrl.cerrar();
					});

					$("#btnTerminar").click(function() {
						$("#divAcuse").addClass("hidden");
						$("#btnEncuesta").removeClass("hidden");
						$("#pnlEncuesta").remove();
					});

					$("#btnEncuesta").click(
									function() {
										$.blockUI();
										$("#pageResumenDatosObra").addClass("hidden");
										$("#datosObraGenerales").addClass("hidden");
										$("#pnlPrivado").css("box-shadow", "");
										$("#pnlPublico").css("box-shadow", "");
										$("#pnlPublico").css("border-color", "");
										$("#pnlPrivado").css("border-color", "");

										$("#contratist").removeClass("hidden");
										$("#contratistSel").addClass("hidden");
										$("#subcontratist").removeClass("hidden");
										$("#subcontratistSel").addClass("hidden");
										$("#intermediary").removeClass("hidden");
										$("#intermediarySel").addClass("hidden");
										$("#propietary").removeClass("hidden");
										$("#propietarySel").addClass("hidden");

										$("#pnlContratista").css("border-color", "");
										$("#pnlContratista").css("box-shadow","");
										$("#pnlPropietario").css("border-color", "");
										$("#pnlPropietario").css("box-shadow","");
										$("#pnlSubcontratista").css("border-color", "");
										$("#pnlSubcontratista").css("box-shadow", "");
										$("#pnlIntermediario").css("border-color", "");
										$("#pnlIntermediario").css("box-shadow", "");

										$("ul.wizard-steps-extensive").children().removeClass("completed");
										$("#imgPublic").removeClass("hidden");
										$("#imgPublicSel").addClass("hidden");
										$("#imgPriv").removeClass("hidden");
										$("#imgPrivSel").addClass("hidden");

										tipoPaciente = undefined;
										tipoObra = undefined;

										var navListItems = $('ul.wizard-steps-extensive li a'), allWells = $('.setup-content'), allNextBtn = $('.nextBtn');
										allWells.hide();
										var path = '/sdroc_web/escritorio'
										location = path;
									});

					var dateObjectInicio = '';
					var dateObjectFin = '';

					$("#fecTermino").datepicker({
						beforeShow : function() {
							setTimeout(function() {
								$('.ui-datepicker').css('z-index',99999999999999);
							}, 0);
						},
						onSelect : function() {

							dateObjectFin = $(this).datepicker('getDate');

							$("#msgeFecFin").addClass("hidden");
							$("#fecTermino").css('border-color', '#ccc');
							$("#msgeFecFin").text('');

							if (dateObjectInicio != '') {
								// revisar que la fecha
								// seleccionada sea mayor a la
								// fecha de inicio
								// revisar que la fecha de
								// inicio sea mayor a los 30
								// dias a aprtir del dia de
								// registro
								var fechaSeleccionadaIni = new Date(dateObjectInicio.getFullYear(),
										dateObjectInicio.getMonth(),
										dateObjectInicio.getDate());
								var fechaSeleccionadafin = new Date(
										dateObjectFin.getFullYear(),
										dateObjectFin.getMonth(),
										dateObjectFin.getDate());

								// si la fecha de inicio es
								// menor o igual a la fecja de
								// termino todo bien
								if (fechaSeleccionadaIni <= fechaSeleccionadafin) {
									valFechaFin = true;
									$("#msgeFecFin").addClass("hidden");
									$("#msgeFecIni").addClass("hidden");
									$("#fecInicio").css('border-color','#ccc');
									$("#fecTermino").css('border-color','#ccc');
									$("#msgeFecIni").text('');
									$("#msgeFecFin").text('');

									// se valida nuevamente la
									// fecha de inicio
									// solo para registro de
									// obra
									if (!$('#chkSinRegistro').is(":checked")) {
										if (validaFechaInicioMenor30Dias()) {
											$("#msgeFecFin").addClass("hidden");
											$("#fecTermino").css('border-color','#ccc');
											$("#msgeFecFin").text('');
											valFechaIni = true;
										}
									} else {
										valFechaIni = true;
									}

								} else {
									$("#msgeFecFin").removeClass("hidden");
									$("#fecTermino").css('border-color','#a94442');
									$("#msgeFecFin").html('La fecha de t&eacute;rmino no puede ser menor a la fecha de inicio');
									valFechaFin = false;
								}

							} else {
								$("#msgeFecIni").removeClass("hidden");
								$("#fecInicio").css('border-color','#a94442');
								$("#msgeFecIni").text('Seleccione la fecha de inicio');
								valFechaIni = false;
							}
						}
					});

					var banderaFecIni = true;
					$("#fecInicio").datepicker({
						beforeShow : function() {
							setTimeout(function() {
									$('.ui-datepicker').css('z-index',99999999999999);
							}, 0);
						},
						onSelect : function() {
							$("#msgeFecIni").addClass("hidden");
							$("#msgeFecIni").text("");
							$("#fecInicio").css('border-color','#ccc');

							valFechaIni = true;
							dateObjectInicio = $(this).datepicker('getDate');
							var rpAmparo = $("#patronAmparo").val();
							// valida el periodo de 30 dias
							if (!$('#chkSinRegistro').is(":checked")) {

								if (validaFechaInicioMenor30Dias()) {

									var fechaSeleccionadaIni = new Date(
										dateObjectInicio.getFullYear(),
										dateObjectInicio.getMonth(),
										dateObjectInicio.getDate());

                  			if (tipoPatron == '4' || tipoPatron == '5'){

										if (fechaSeleccionadaIni < IniciaContrOutsourcing) {
											if(rpAmparo != 1){
												$("#msgeFecIni").removeClass("hidden");
												$("#fecInicio").css('border-color', '#a94442');
												$("#msgeFecIni").html('La fecha de inicio no puede ser menor a la fecha del 1 de septiembre del 2021');
												valFechaIni = false;
											}
										}
									}

									// valido que la fecha seleccionada sea mayor a la fecha de termino
									if (dateObjectFin != '') {

										var fechaSeleccionadafin = new Date(
											dateObjectFin.getFullYear(),
											dateObjectFin.getMonth(),
											dateObjectFin.getDate());

										$("#msgeFecFin").addClass("hidden");
										$("#fecTermino").css('border-color', '#ccc');
										$("#msgeFecFin").text('');

										if (fechaSeleccionadaIni > fechaSeleccionadafin) {
											// banderaFecIni = false;
											$("#msgeFecFin").removeClass("hidden");
											$("#fecTermino").css('border-color','#a94442');
											$("#msgeFecFin").html('La fecha de t&eacute;rmino no puede ser menor a la fecha de inicio');
											valFechaIni = false;
										} else {
											$("#msgeFecFin").text('');
											$("#msgeFecFin").addClass("hidden");
											$("#fecInicio").css('border-color','#ccc');
											$("#fecTermino").css('border-color','#ccc');
											valFechaFin = true;
											valFechaIni = true;
										}
									} else {
										$("#msgeFecFin").removeClass("hidden");
										$("#fecTermino").css('border-color','#a94442');
										$("#msgeFecFin").html('Seleccione la fecha de t&eacute;rmino');
										valFechaFin = false;
									}
								} else {
									valFechaIni = false;
								}
							}
						}
					});

					$("#btnRegistrar").click(function() {
						// validacion de datos requeridos
						$("#firmaDigitalModal").modal();

					});

					/**
					 * Cancelacion del registro de la obra.
					 */
					$("#btnCancelarRegistro").click(function() {
						$("#regObraModal").dialog('toggle');
					});

					/**
					 * deprecated
					 */
					$("#btnUbicacion").click(registroObraCtrl.eventoUbicacionObra);


					$("#btnTipoObra").click(registroObraCtrl.avanzarATipoPatron);

				});

/**
 * Validaciones para los campos en donde se especifica la ubicacion.
 */
$(document).ready(function() {
	$("#btnValidarUbicacion").click(function() {
		if (validarDatosUbicacion()) {

		}
	});

	/**
	 * validar Datos Ubicacion
	 *
	 * @returns
	 */
	function validarDatosUbicacion() {
		var resultado = true;

		if (!validarAtributo($("#txtCP").val(), $("#txtCP").attr('id'))) {

			mostrarMensaje($("#lblCP").attr('id'));
			resultado = false;
		} else {
			ocultarMensaje($("#lblCP").attr('id'));
			resultado = true;
		}
	}

	/**
	 * ocultar Mensaje
	 *
	 * @param id
	 * @returns
	 */
	function ocultarMensaje(id) {
		$("#" + id).addClass("hidden");
	}
	/**
	 * mostrar Mensaje
	 *
	 * @param id
	 * @returns
	 */
	function mostrarMensaje(id) {
		$("#" + id).removeClass("hidden");
	}
	/**
	 * validar Atributo
	 *
	 * @param obj
	 * @param id
	 * @returns
	 */
	function validarAtributo(obj, id) {

		if (obj == 'Seleccione ...' || obj == '') {
			$("#" + id).css("border-color", "#a94442");
			return false;
		} else {
			$("#" + id).css("border-color", "#ccc");
			return true;
		}
	}


});

function accionIncidencias(sel) {
	if($('#tblEscritorioR').length) {
		serviciosIncidencia.procesarTipoIncidencia(sel);
	} else {
		accionIncidenciasBack(sel);
	}
}

function accionIncidenciasBack(sel) {
	var $selectAtual = $(sel);
	var cveObra = $selectAtual.find(":selected").attr("id");
	var estadoObra = validarObra(cveObra);
	if (estadoObra == '0') {
		crearDialogo("#msgsValidacionObra", {
			"Aceptar" : function() {
				$(this).dialog("close")
			}
		}, "Estado registro obra", "340px");
		$('#msgsValidacionObra').removeClass('hidden');
		return;
	} else {
		serviciosObraCtrl.sensaObra(cveObra);
	}

	var accion = null;
	var numRegObra = null;
	var idaction = null;

	accion = $selectAtual.attr("value");
	idaction = obtenerAccion(accion);
	var valorNumActualiza = null,
	evauacion32D = null;
	if($('#tblEscritorioR').length) {
		var trCercano = $selectAtual.closest("tr");
		var datosObra = dataTableObras.row(trCercano).data();
		valorNumActualiza = datosObra.numActualiza;
		evauacion32D = datosObra.numEvaluacionD32;
	}else {
		var tds = $selectAtual.parent().parent().parent().find('td');
		valorNumActualiza = $selectAtual.parent().parent().parent().find('td input').val();
		evauacion32D =$(tds[1]).find('label').text();
	}


	if (idaction > 0 && accion != null) {
		if (idaction == 4
				&& valorNumActualiza >= 3) {
			crearDialogo("#msgsEscritorio", {
				"Aceptar" : function() {
					$(this).dialog("close")
				}
			}, "Actualizaci&oacute;n", "340px");
			$('#msgsEscritorio').removeClass('hidden');
			$selectAtual.val("");
			return;
		} else {
			if (idaction == 4 && evauacion32D > 0) {
				crearDialogo("#msgsBimestreIncumplido", {
					"Aceptar" : function() {
						$(this).dialog("close")
					}
				}, "Actualizaci&oacute;n", "340px");
				$('#msgsBimestreIncumplido').removeClass('hidden');
				$selectAtual.val("");
				return;
			}
			numRegObra = $selectAtual.find(":selected").attr("id");
			var path = accion + numRegObra + "/" + idaction;
			location = path;
		}

	}
}

function validarObra(numeroRegistroObra) {

	return serviciosObraCtrl.validarObra(numeroRegistroObra);
}

function obtenerAccion(accion) {
	var action = null;

	if (accion.search('Selecci') != -1) {
		action = 0;
	} else if (accion.search('cancela') != -1) {
		action = 1;
	} else if (accion.search('suspend') != -1) {
		action = 2;
	} else if (accion.search('termina') != -1) {
		action = 3;
	} else if (accion.search('actuali') != -1) {
		action = 4;
	} else if (accion.search('reanuda') != -1) {
		action = 5;
	} else if (accion.search('reporte') != -1) {
		action = 6;
	}

	return action;
}

function regresarResumen() {
	var numRegPatronal = $("#cveRegPatronalObra").text();
	var urlRegreso = "/sdroc_web/registroPatronal/" + numRegPatronal;
	location = urlRegreso;
}

function regresarAcuses() {
	var numRegPatronal = $("#cveRP").val();
	var urlRegreso = "/sdroc_web/registroPatronal/" + numRegPatronal;
	location = urlRegreso;
}

function regresarSubcontrato() {
	var numRegPatronal = $("#cveRP").val();
	var urlRegreso = "/sdroc_web/registroPatronal/" + numRegPatronal;
	location = urlRegreso;
}

function calcularSemaforoEvaluacionSAT(){
	var cveRfcSemaforo = $("#lblRFCPatron").text();
	var tipoPersonaSem = cveRfcSemaforo.length == 13 ? 1 : 2;

	$.postJSON("/gestionCobranza-web/wizard/cartaNoAdeudo/getEstatus",
		{'rfc':cveRfcSemaforo,'tipoPersona':{idTipoPersona:tipoPersonaSem}},
		function(data){

			var protocol = window.location.protocol,
			host = window.location.host,
			image = "",
			evaluation = "";

			if(data.codigoRespuesta == 1){
				image = "green";
				evaluation = "POSITIVO";
			}else if(data.codigoRespuesta == 2 || data.codigoRespuesta == 3 || data.codigoRespuesta == 4 || data.codigoRespuesta == 6){
				image= "red";
				evaluation = "NEGATIVO";
			}else {
				image ="yellow";
				evaluation = "SIN OPINI&Oacute;N";
			}

			$('#imgSemaforo').attr('src',protocol+'//'+host+'/sdroc_web/static/images/' + image + '.png');
			$('#lblSemaforo').html(evaluation);
	}, {complete: function(){$("#imgSemaforo").show()}});

}

function cargarEtiquetasConsulta() {
	// Consulta
	var pasoConsulta = $('#pasoConsulta');
	if (pasoConsulta != undefined) {
		if (pasoConsulta.val() == "1") {
			$("#pnlRFC").removeClass("hidden");
			$("#pnlRazonSocial").removeClass("hidden");
			$("#pnlFecha").removeClass("hidden");
			$("#pnlRegistroPatronal").addClass("hidden");
			$("#pnlDomicilioFiscal").addClass("hidden");
			$("#pnlDatosUbicacionObra").addClass("hidden");
		}
		if (pasoConsulta.val() == "2") {
			$("#pnlRFC").removeClass("hidden");
			$("#pnlRazonSocial").removeClass("hidden");
			$("#pnlRegistroPatronal").addClass("hidden");
			$("#pnlFecha").removeClass("hidden");
			$("#pnlDomicilioFiscal").addClass("hidden");
			$("#pnlDatosUbicacionObra").addClass("hidden");
			$("#pnlRegistroPatronalPatron").removeClass("hidden");
		}
		// ????????
		if (pasoConsulta.val() == "4") {
			$("#pnlRFC").removeClass("hidden");
			$("#pnlRazonSocial").removeClass("hidden");
			$("#pnlRegistroPatronal").addClass("hidden");
			$("#pnlNoRegistroObra").removeClass("hidden");
			$("#pnlFecha").removeClass("hidden");
			$("#pnlDomicilioFiscal").addClass("hidden");
			$("#lblNoRegistroObra").text($('#numeroObra').val());
			$("#pnlRegistroPatronalPatron").removeClass("hidden");
		}
		if (pasoConsulta.val() == "5") {
			$("#pnlRFC").removeClass("hidden");
			$("#pnlRazonSocial").removeClass("hidden");
			$("#pnlFecha").removeClass("hidden");
			$("#pnlRegistroPatronal").addClass("hidden");
			$("#pnlDomicilioFiscal").addClass("hidden");
			$("#pnlDatosUbicacionObra").addClass("hidden");
		}
	}
}

function reiniciarValores() {
	tipoObra = 0;
	tipoPatron = "";
	datosRegObra = {};
	jsonDomi = "";
	valFechaIni = true;
	valFechaFin = true;
	location = "/sdroc_web/escritorio";
	$.blockUI();
}


/**
 * convertirFechaFormatoDatePicker
 */
function convertirFechaFormatoDatePicker(fechaIntroducida) {

	var fechaIntro = fechaIntroducida.split("/");

	var diaIni = fechaIntro[0];
	var mesIni = fechaIntro[1];
	var anioIni = fechaIntro[2];

	return new Date(anioIni, mesIni - 1, diaIni);
}
/*
 * Valida que la fecha de inicio seleccionada no supere 30 dias naturales a
 * partir de la fecha del sistema
 */
function validaFechaInicioMenor30Dias() {
	var fecInicio = $("#fecInicio").datepicker('getDate');

	if (fecInicio != undefined && fecInicio != '' && fecInicio != null) {
		// se forma fecha seleccionada
		var mesIni = $("#fecInicio").datepicker('getDate').getMonth();
		var anioIni = $("#fecInicio").datepicker('getDate').getFullYear();
		var diaIni = $("#fecInicio").datepicker('getDate').getDate();

		var d = getDate();
		var month = d.getMonth();
		var year = d.getFullYear();
		var day = d.getDate() - 1;

		// seleccionada
		var firstDate = new Date(anioIni, mesIni, diaIni);
		// fecha actual
		var secondDate = new Date(year, month, day);

		// si mi fecha seleccionada es menor a la fecha actual no hago
		// validaciones
		if (firstDate < secondDate) {
			return true;
		} else {
			var oneDay = 24 * 60 * 60 * 1000; // hours*minutes*seconds*milliseconds
			var diffDays = Math.round(Math
					.abs((firstDate.getTime() - secondDate.getTime())
							/ (oneDay)));

			if (diffDays > 30) {
				$("#msgeFecIni").removeClass("hidden");
				$("#msgeFecIni").html('La fecha de inicio no puede ser mayor a treinta d&iacute;as naturales a partir del d&iacute;a de registro');
				$("#fecInicio").css('border-color', '#a94442');
				return false;
			} else {
				$("#fecInicio").css('border-color', '#ccc');
				$("#msgeFecIni").addClass("hidden");
				$("#msgeFecIni").text('');
				return true;
			}
		}

	} else {
		return true;
	}
}
