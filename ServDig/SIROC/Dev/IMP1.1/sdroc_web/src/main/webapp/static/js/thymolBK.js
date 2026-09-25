/**
 * 
 */
var tipoObra = 0;
var tipoPatron = "";
var datosRegObra = {};
var jsonDomi = "";
var valFechaIni = true;
var valFechaFin = true;

var intervalo;
function iniciaSenso() {
	ejecutaIntervalo($("#lblNumRegistroObra").text());
}

function ejecutaIntervalo(numeroRegistroObra) {

	intervalo = setInterval(function() {
		sensaObra(numeroRegistroObra);
	}, 60000);
}

function sensaObra(numeroRegistroObra) {
	var boolean;
	var accion = "/sdroc_web/sensaObra";

	$.blockUI();
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : accion,
		async : false,
		data : numeroRegistroObra,
		cache : false,
		success : function(response) {
			$.unblockUI();
			boolean = response;
			if (boolean == 2) {
				clearInterval(intervalo);
				$('#msgsValidacionObraReinicio').removeClass('hidden');
				crearDialogo("#msgsValidacionObraReinicio", {
					"Aceptar" : function() {
						reiniciarObra(numeroRegistroObra);
						ejecutaIntervalo(numeroRegistroObra);
						$(this).dialog("close");
					},
					"Cancelar" : function() {
						liberarObra(numeroRegistroObra);
						$(this).dialog("close")
					}
				}, "Registro Obra", "340px");
			} else if (boolean == 0) {
				liberarObra(numeroRegistroObra);
				clearInterval(intervalo);
			}
		}
	});
	return boolean;
}

function getDate(){

	var d;
	$.ajax({
		type : "GET",
		contentType : "application/json",
		async : false,
		url : '/sdroc_web/fechaActual',
		success : function(response) {
			d = new Date(response);
			
			
		},
		error:function(response){
			console.log('error recuperando fecha ... ' + response);
		}
	});
	
	return d;
}

function udm_(e){var t="comScore=",n=document,r=n.cookie,i="",s="indexOf",o="substring",u="length",a=2048,f,l="&ns_",c="&",h,p,d,v,m=window,g=m.encodeURIComponent||escape;if(r[s](t)+1)for(d=0,p=r.split(";"),v=p[u];d<v;d++)h=p[d][s](t),h+1&&(i=c+unescape(p[d][o](h+t[u])));e+=l+"_t="+ +(new Date)+l+"c="+(n.characterSet||n.defaultCharset||"")+"&c8="+g(n.title)+i+"&c7="+g(n.URL)+"&c9="+g(n.referrer),e[u]>a&&e[s](c)>0&&(f=e[o](0,a-8).lastIndexOf(c),e=(e[o](0,f)+l+"cut="+g(e[o](f+1)))[o](0,a)),n.images?(h=new Image,m.ns_p||(ns_p=h),h.src=e):n.write("<","p","><",'img src="',e,'" height="1" width="1" alt="*"',"><","/p",">")};
function uid_call(a, b){
       ui_c2 = 17183199; // your corporate c2 client value
       ui_ns_site = 'gobmx'; // your sites identifier
       window.b_ui_event = window.c_ui_event != null ? window.c_ui_event:"",window.c_ui_event = a;
       var ui_pixel_url = 'https://sb.scorecardresearch.com/p?c1=2&c2='+ui_c2+'&ns_site='+ui_ns_site+'&name='+a+'&ns_type=hidden&type=hidden&ns_ui_type='+b;
       var b="comScore=",c=document,d=c.cookie,e="",f="indexOf",g="substring",h="length",i=2048,j,k="&ns_",l="&",m,n,o,p,q=window,r=q.encodeURIComponent||escape;if(d[f](b)+1)for(o=0,n=d.split(";"),p=n[h];o<p;o++)m=n[o][f](b),m+1&&(e=l+unescape(n[o][g](m+b[h])));ui_pixel_url+=k+"_t="+ +(new Date)+k+"c="+(c.characterSet||c.defaultCharset||"")+"&c8="+r(c.title)+e+"&c7="+r(c.URL)+"&c9="+r(c.referrer)+"&b_ui_event="+b_ui_event+"&c_ui_event="+c_ui_event,ui_pixel_url[h]>i&&ui_pixel_url[f](l)>0&&(j=ui_pixel_url[g](0,i-8).lastIndexOf(l),ui_pixel_url=(ui_pixel_url[g](0,j)+k+"cut="+r(ui_pixel_url[g](j+1)))[g](0,i)),c.images?(m=new Image,q.ns_p||(ns_p=m),m.src=ui_pixel_url):c.write("<p><img src='",ui_pixel_url,"' height='1' width='1' alt='*'></p>");
}

function liberarObra(numeroRegistroObra) {
	var accion = "/sdroc_web/liberaRegistroObra";
	$.blockUI();
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : accion,
		async : false,
		data : numeroRegistroObra,
		cache : false,
		success : function(response) {
			$.unblockUI();
			boolean = response;
		}
	});
}

function reiniciarObra(numeroRegistroObra) {
	var accion = "/sdroc_web/reiniciarRegistroObra";
	$.blockUI();
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : accion,
		async : false,
		data : numeroRegistroObra,
		cache : false,
		success : function(response) {
			$.unblockUI();
			boolean = response;
		}
	});
}

$(document).ready(function() {
	$('.collapse').on('shown.bs.collapse', function(){
			$("#imgCollapsable").removeAttr("src").attr("src","/sdroc_web/static/images/panel-collapsed.png")
		}).on('hidden.bs.collapse', function(){
			$("#imgCollapsable").removeAttr("src").attr("src","/sdroc_web/static/images/panel.png")
		});
});

$(document).ready(function() {	
	 
      udm_('https://sb.scorecardresearch.com/b?c1=2&c2=17183199&ns_site=gobmx&name=imss.asegurados.asignacionNSS.inicio');
	
	calcularSemaforoEvaluacionSAT();

	cargarEtiquetasConsulta();

	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

	var tableConfig = {
		"lengthMenu" : "Mostrar _MENU_ registros por p&aacute;gina",
		"zeroRecords" : "No se han encontrado registros",
		"info" : "Mostrando p&aacute;gina _PAGE_ de _PAGES_",
		"infoEmpty" : "No hay registros disponibles",
		"infoFiltered" : "(filtrada a partir de _MAX_ registros totales)",
		"search" : "Buscar:",
		"pageLength": 5,
		"paginate" : {
			"previous" : "Anterior",
			"next" : "Siguiente",
			"first" : "Primer p&aacute;gina",
			"last" : "&Uacute;ltima p&aacute;gina"
		}
	};

	$('#tabRegPatronal').DataTable({
		"language" : tableConfig
	});

	$('#tblConsultaObraRFC').DataTable({
		"language" : tableConfig
	});

	$('#tblEscritorio').DataTable({
		"language" : tableConfig
	});

	$('#tblConsultaAvisoUbicacionObra').DataTable({
		"language" : tableConfig
	});

	if ($('#tblConsultaAvisoUbicacionObra').length) {
		$('#tblConsultaAvisoUbicacionObra').removeClass('dataTable');
		$('.table thead tr th').css('border-bottom', '1px solid #000');
	}
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

	function onlyNumber(e) {
		if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
			return false;
		} else {
			return true;
		}
	}

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

	$('#txtMonto').maskMoney(configMask);
	$('#txtSuperficie').maskMoney(configMask);
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

					allNextBtn
							.click(function() {
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

$(document)
		.ready(
				function() {

					// Firma

					function firmarRegistroObra() {

						parent.FirmaDigitalCtrl.init("firmaDigitalComponent",
								"");

						parent.FirmaDigitalCtrl
								.setOnCloseCallback(function() {

									if (parent.FirmaDigitalCtrl.datosSalida == null) {
										alert("La validacion de la firma no pudo ser realizada");
									} else {
										if (parent.FirmaDigitalCtrl.datosSalida.Resultado == 0) {
											var firmaResponse = {
												cadenaOriginal : parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cadori,
												recibo : parent.FirmaDigitalCtrl.datosSalida.firmas[0],
												reciboNotarial : parent.FirmaDigitalCtrl.datosSalida.folio,
												urlAcuseFirma : parent.FirmaDigitalCtrl.datosSalida.acuse,
												serialCertificado : parent.FirmaDigitalCtrl.datosSalida.serie_cert,
												strIniciaVigenciaCertificado : parent.FirmaDigitalCtrl.datosSalida.vigIni,
												strFinVigenciaCertificado : parent.FirmaDigitalCtrl.datosSalida.vigFin,
												rfc : parent.FirmaDigitalCtrl.datosSalida.rfc
											};

											firmarTramite(firmaResponse);
										} else {
											alert("La validacion de la firma no pudo ser realizada");
										}
									}
								});

						var cveRfc = $("#lblRFCPatron").text();
						var cveRegPatronal = $("#lblRegPatron").text();
						var refRazonSocial = $("#lblRazonSocialPatron").text().replace(/"/g,"\\\"");
						var numRegistro = 0;
						var numLicitacion = 0;
						var cveObjetoContrato = 1;
						var impEjercido = 0;
						var impContratado = 0;
						var cadenaOriginal = "";

						cadenaOriginal = "||Invocante:portalimssdigital%NOMBRE_TRAMITE%|%FECHA_ACTUAL%"
								 // + "|NÃºmero de Registro de
						// Obra: ";
						cadenaOriginal = cadenaOriginal + "%NUM_REG_OBRA%"
								+ "|RFC: ";
						cadenaOriginal = cadenaOriginal + cveRfc;

						cadenaOriginal = cadenaOriginal
								+ "|Nombre o razon social: ";

						cadenaOriginal = cadenaOriginal + refRazonSocial + "|";

						cadenaOriginal = cadenaOriginal + "Registro patronal: "
								+ cveRegPatronal + "||";

						var componenteFirma = {
							tipo_operacion : 'firmaCMS', // debe ser fijo
							acuse : 'AcuseV1.0', // este es fijo
							rfc : cveRfc, // rfc de la persona que firma
							validarRFC : true, // este siempre es tru
							curp : '', // este puede ir nulo solo si la persona
							// es moral
							firma_archivo : false, // este es false
							min_archivos : 0, // 0
							max_archivos : 0, // 0
							fechaElectronica : getDate(), // date fecha
							// tramite
							cad_original : cadenaOriginal, // cadena original
							// con pipes
							registroPatronal : cveRegPatronal, // no debe ir si
							// no lo tienes
							nombreCompleto : refRazonSocial, // el nombre o
							// razÃ³n social
							// de la empresa
							idTipoSolicitud : 1, // 1
							descripcionTipoSolicitud : 'Alta', // el nombre del
							// tramite
							folioSolicitud : '',// â€œâ€�
							idTipoTramite : [ 1 ]
						// [1]
						};

						parent.iniciarFirmaDigital(componenteFirma);

					}

					function firmarTramite(firmaResponse) {

						var accion = "/sdroc_web/datosFirma";

						$.blockUI();
						$.ajax({
							type : "POST",
							contentType : "application/json",
							url : accion,
							data : JSON.stringify(firmaResponse),
							cache : false,
							success : function(response) {
								$.unblockUI();
								guardarDatos();

							}
						});
					}
					;

					parent.DomicilioCtrl.init("domiciliosComponent");

					$("#btnValidarRegObra").click(
									function() {
							
										$("#msgValAlfanum").addClass("hidden");

										if ($("#txtNumRegistro").val() == '') {

											if ($('#chkSinRegistro').is(":checked")) {
												$("#txtNumRegistro").css('border-color', '#ccc');

											} else {
												$("#msgSinSeleccion").removeClass("hidden");
												$("#txtNumRegistro").css('border-color','#a94442');
												$("#btnUbicacion").addClass("hidden");

											}

										} else {
											if (/^[A-Za-z0-9]+$/.test($("#txtNumRegistro").val())) {

												var numRegObra = $("#txtNumRegistro").val();
												if (numRegObra.length == 8) {
													$("#msgLongitudIncorrecta").addClass("hidden");
													$("#txtNumRegistro").css('border-color','#ccc');

													var accion = "/sdroc_web/consultaPorNumReg";
													$.ajax({
															type : "POST",
															contentType : "application/json",
															url : accion,
															data : numRegObra,
															timeout : 100000,
															success : function(e) {
																if (e.resultado == null) {
																	$("#pnlNumRegObra").removeClass("hidden");
																	$("#datosObraGenerales").addClass("hidden");
																	$("#txtObervacionesUbicacion").addClass("hidden");
																	$("#pnlDatosObraRegistrado").addClass("hidden");
																	$("#btnUbiContratista").addClass("hidden");
																	$("#msgSinCoincidencias").removeClass("hidden");
																	$("#msgSinCoincidencias").html(e.mensaje);
																	$("#btnUbicacion").addClass("hidden");
																	$('#lblMsgObraNE').css("border-color","#a94442");
																	$('#cpValidar').text("");

																} else {
																	
																	$("#btnUbicacion").removeClass("hidden");
																	$("#btnUbicacion").html("Validar Ubicaci&oacute;n");
																	$("#pnlDatosObraRegistrado").removeClass("hidden");
																	$("#datosObraGenerales").removeClass("hidden");
																	$("#txtObervacionesUbicacion").removeClass("hidden");
																	$('#lblMsgObraNE').text('');
																	$("#pnlDatosObraNoEncontrado").addClass("hidden");
																	$("#btnUbiContratista").removeClass("hidden");
																	$("#idCveRegistroObraPrincipal").val(e.resultado.cveInformacionObra);
																	$("#idCveRegistroObraPrincipal").text(e.resultado.cveInformacionObra);
																	$('#cpValidar').val(e.resultado.ubicacionObraDTO.codigoPostal);
																	var direccionTmp = construyeDireccion(e.resultado.ubicacionObraDTO);
																	$('#lblUbicacionObraRes').text(direccionTmp.toUpperCase());
																	$('#commentUbicacion').text('');
																}

																$("#msgNumRegistro").addClass("hidden");
																$("#txtNumRegistro").css('border-color','#ccc');
															},
															error : function(e) {
																console.log("ERROR: ",e);
															}
														});
												} else {
													$("#msgLongitudIncorrecta")
															.removeClass(
																	"hidden");
													$("#txtNumRegistro").css(
															'border-color',
															'#a94442');
												}

											} else {
												$("#msgValAlfanum")
														.removeClass("hidden");
												$("#msgNumRegistro").addClass(
														"hidden");
												$("#msgErrorAjax").addClass(
														"hidden");
												$("#msgSinSeleccion").addClass(
														"hidden");
												$("#msgSinCoincidencias")
														.addClass("hidden");
												$("#msgFormatoIncorrecto")
														.addClass("hidden");
												$("#msgLongitudIncorrecta")
														.addClass("hidden");
											}

										}

									});

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

					$("#btnValidarRFC")
							.click(
									function() {
										if ($("#rfcSinNumReg").val() == '') {
											$("#msgRFC").removeClass("hidden");
											$("#rfcSinNumReg").css(
													'border-color', '#a94442');
											$("#btnUbicacion").addClass(
													"hidden");
											if (tipoPatron == "2") {
												$("#idContraRFC").removeClass(
														"hidden");
												$("#idSubContraRFC").addClass(
														"hidden");
												$("#idInterRFC").addClass(
														"hidden");
											} else if (tipoPatron == "3") {
												$("#idContraRFC").addClass(
														"hidden");
												$("#idSubContraRFC")
														.removeClass("hidden");
												$("#idInterRFC").addClass(
														"hidden");
											} else if (tipoPatron == "4") {
												$("#idContraRFC").addClass(
														"hidden");
												$("#idSubContraRFC").addClass(
														"hidden");
												$("#idInterRFC").removeClass(
														"hidden");
											}

										} else {
											$("#msgRFC").addClass("hidden");
											// DomicilioCtrl.localizar();
											if ($("#rfcSinNumReg").val() != 'IMS421231I45') {

												var accion = "/sdroc_web/validaRfc/"
														+ $("#rfcSinNumReg")
																.val()
																.toUpperCase();
												$
														.ajax({
															type : "GET",
															contentType : "application/json",
															async : false,
															url : accion,
															timeout : 100000,
															cache : false,
															success : function(
																	response) {
																
																$(
																		"#rfcSinNumReg")
																		.css(
																				'border-color',
																				'#ccc');
																$("#msgRFC")
																		.addClass(
																				"hidden");
																$(
																		"#btnUbicacion")
																		.removeClass(
																				"hidden");
																$(
																		"#lblRazonSocial")
																		.removeClass(
																				"hidden");
																// $('#texRazonSocial').text(response);
																$(
																		"#btnValidarRFC")
																		.removeClass(
																				"btn-primary");
																$(
																		"#btnValidarRFC")
																		.addClass(
																				"btn-default");
																$(
																		"#btnUbicacion")
																		.addClass(
																				"hidden");
																if (response == '') {
																	$(
																			"#rfcSinNumReg")
																			.css(
																					'border-color',
																					'#a94442');
																	$("#msgRFC")
																			.removeClass(
																					"hidden");
																	$(
																			"#lblRazonSocial")
																			.addClass(
																					"hidden");
																	$(
																			"#btnUbicacion")
																			.addClass(
																					"hidden");
																	if (tipoPatron == "2") {
																		$(
																				"#idContraRFC")
																				.removeClass(
																						"hidden");
																		$(
																				"#idSubContraRFC")
																				.addClass(
																						"hidden");
																		$(
																				"#idInterRFC")
																				.addClass(
																						"hidden");
																	} else if (tipoPatron == "3") {
																		$(
																				"#idContraRFC")
																				.addClass(
																						"hidden");
																		$(
																				"#idSubContraRFC")
																				.removeClass(
																						"hidden");
																		$(
																				"#idInterRFC")
																				.addClass(
																						"hidden");
																	} else if (tipoPatron == "4") {
																		$(
																				"#idContraRFC")
																				.addClass(
																						"hidden");
																		$(
																				"#idSubContraRFC")
																				.addClass(
																						"hidden");
																		$(
																				"#idInterRFC")
																				.removeClass(
																						"hidden");
																	}
																} else {
																	cargaEtiquetas();
																	// $("#pnlDomicilioFiscal").removeClass("hidden");
																	parent.DomicilioCtrl
																			.localizar();
																}

															},
															error : function(
																	response) {
																$(
																		"#rfcSinNumReg")
																		.css(
																				'border-color',
																				'#a94442');
																$("#msgRFC")
																		.removeClass(
																				"hidden");
																$(
																		"#lblRazonSocial")
																		.addClass(
																				"hidden");
																$(
																		"#btnUbicacion")
																		.addClass(
																				"hidden");
																if (tipoPatron == "2") {
																	$(
																			"#idContraRFC")
																			.removeClass(
																					"hidden");
																	$(
																			"#idSubContraRFC")
																			.addClass(
																					"hidden");
																	$(
																			"#idInterRFC")
																			.addClass(
																					"hidden");
																} else if (tipoPatron == "3") {
																	$(
																			"#idContraRFC")
																			.addClass(
																					"hidden");
																	$(
																			"#idSubContraRFC")
																			.removeClass(
																					"hidden");
																	$(
																			"#idInterRFC")
																			.addClass(
																					"hidden");
																} else if (tipoPatron == "4") {
																	$(
																			"#idContraRFC")
																			.addClass(
																					"hidden");
																	$(
																			"#idSubContraRFC")
																			.addClass(
																					"hidden");
																	$(
																			"#idInterRFC")
																			.removeClass(
																					"hidden");
																}
															}
														});

											}
										}
									});

					$("#chkSinRegistro")
							.click(
									function() {


										$('#lblMsgObraNE').text('');
										$('#lblMsgObraNE').addClass("hidden");
										$("#msgRFC").addClass("hidden");

										if ($('#chkSinRegistro').is(":checked")) {
											$("#txtNumRegistro").prop(
													'disabled', true);
											$("#txtNumRegistro").val('');
											$("#pnlSinNumeroReg").removeClass(
													"hidden");
											$("#msgNumRegistro").addClass(
													"hidden");
											$("#txtNumRegistro").css(
													'border-color', '#ccc');
											$("#msgSinSeleccion").addClass(
													"hidden");
											$("#msgSinCoincidencias").addClass(
													"hidden");
											$("#pnlDatosPatron").removeClass(
													"hidden");
											$("#btnValidarRegObra").addClass(
													"disabled");
											$("#btnValidarRegObra")
													.removeClass("btn-primary");
											$("#btnValidarRegObra").addClass(
													"btn-default");
											$("#idTituloUbicacion").html("Aviso de Ubicaci&oacute;n de la Obra");
											$("#btnUbicacion").addClass(
													"hidden");

										} else {
											$("#idTituloUbicacion").html("Ubicaci&oacute;n de la Obra");
											$("#txtNumRegistro").prop(
													'disabled', false);
											$("#pnlSinNumeroReg").addClass(
													"hidden");
											$("#btnValidarRegObra")
													.removeClass("disabled");
											$("#msgNumRegistro").addClass(
													"hidden");
											$("#msgSinSeleccion").addClass(
													"hidden");
											$("#msgSinCoincidencias").addClass(
													"hidden");
											$("#pnlDatosPatron").addClass(
													"hidden");
											$("#btnValidarRegObra")
													.removeClass("btn-default");
											$("#btnValidarRegObra").addClass(
													"btn-primary");

										}
									});
					
					$("#rfcSinNumReg").keyup(function(e) {
						this.value = this.value.toUpperCase();
					});
					

					$("#pnlPrivado").click(
							function() {
								$("#pnlPrivado").css("border-color", "green");
								$("#pnlPublico").css("border-color", "");

								$("#pnlPrivado").css("box-shadow",
										"3px 3px 10px rgba(0,0,0,0.6)");
								$("#pnlPublico").css("box-shadow", "");

								$("#imgPriv").addClass("hidden");
								$("#imgPrivSel").removeClass("hidden");
								$("#imgPublicSel").addClass("hidden");
								$("#imgPublic").removeClass("hidden");

								$("#msgObra").addClass("hidden");
								tipoObra = 1;

							});

					$("#pnlPublico").click(
							function() {
								$("#pnlPublico").css("border-color", "green");
								$("#pnlPrivado").css("border-color", "");

								$("#pnlPublico").css("box-shadow",
										"3px 3px 10px rgba(0,0,0,0.6)");
								$("#pnlPrivado").css("box-shadow", "");

								$("#imgPublic").addClass("hidden");
								$("#imgPublicSel").removeClass("hidden");
								$("#imgPrivSel").addClass("hidden");
								$("#imgPriv").removeClass("hidden");

								$("#msgObra").addClass("hidden");
								tipoObra = 2;

							});

					$("#pnlPropietario")
							.click(
									function() {
										$("#pnlPropietario").css(
												"border-color", "green");
										$("#pnlPropietario").css("box-shadow",
												"3px 3px 10px rgba(0,0,0,0.6)");

										// propietario seleccionado
										$("#propietary").addClass("hidden");
										$("#propietarySel").removeClass(
												"hidden");

										$("#contratist").removeClass("hidden");
										$("#contratistSel").addClass("hidden");

										$("#subcontratist").removeClass(
												"hidden");
										$("#subcontratistSel").addClass(
												"hidden");

										$("#intermediary")
												.removeClass("hidden");
										$("#intermediarySel")
												.addClass("hidden");

										$("#pnlContratista").css(
												"border-color", "");
										$("#pnlContratista").css("box-shadow",
												"");

										$("#pnlSubcontratista").css(
												"border-color", "");
										$("#pnlSubcontratista").css(
												"box-shadow", "");

										$("#pnlIntermediario").css(
												"border-color", "");
										$("#pnlIntermediario").css(
												"box-shadow", "");

										$("#txtPropietary").removeClass(
												"hidden");
										$("#txtContratist").addClass("hidden");
										$("#txtSubcontratist").addClass(
												"hidden");
										$("#txtIntermediary")
												.addClass("hidden");

										$("#msgPatron").addClass("hidden");
										$("#numProcedimiento").addClass(
												"hidden");
										$("#pnlObjetoContrato").addClass(
												"hidden");
										tipoPatron = "1";
									});

					$("#pnlContratista")
							.click(
									function() {
										$("#pnlContratista").css(
												"border-color", "green");
										$("#pnlContratista").css("box-shadow",
												"3px 3px 10px rgba(0,0,0,0.6)");

										$("#contratist").addClass("hidden");
										$("#contratistSel").removeClass(
												"hidden");

										$("#propietary").removeClass("hidden");
										$("#propietarySel").addClass("hidden");

										$("#subcontratist").removeClass(
												"hidden");
										$("#subcontratistSel").addClass(
												"hidden");

										$("#intermediary")
												.removeClass("hidden");
										$("#intermediarySel")
												.addClass("hidden");

										$("#pnlPropietario").css(
												"border-color", "");
										$("#pnlPropietario").css("box-shadow",
												"");

										$("#pnlSubcontratista").css(
												"border-color", "");
										$("#pnlSubcontratista").css(
												"box-shadow", "");

										$("#pnlIntermediario").css(
												"border-color", "");
										$("#pnlIntermediario").css(
												"box-shadow", "");

										$("#txtPropietary").addClass("hidden");
										$("#txtContratist").removeClass(
												"hidden");
										$("#txtSubcontratist").addClass(
												"hidden");
										$("#txtIntermediary")
												.addClass("hidden");

										$("#msgPatron").addClass("hidden");
										$("#pnlObjetoContrato").addClass(
												"hidden");

										tipoPatron = "2";

										if (tipoObra == 2) {
											$("#numProcedimiento").removeClass(
													"hidden");
										} else {
											$("#numProcedimiento").addClass(
													"hidden");
										}

									});

					$("#pnlSubcontratista").click(
							function() {
								$("#pnlSubcontratista").css("border-color",
										"green");
								$("#pnlSubcontratista").css("box-shadow",
										"3px 3px 10px rgba(0,0,0,0.6)");

								$("#subcontratist").addClass("hidden");
								$("#subcontratistSel").removeClass("hidden");

								$("#contratist").removeClass("hidden");
								$("#contratistSel").addClass("hidden");

								$("#propietary").removeClass("hidden");
								$("#propietarySel").addClass("hidden");

								$("#intermediary").removeClass("hidden");
								$("#intermediarySel").addClass("hidden");

								$("#pnlContratista").css("border-color", "");
								$("#pnlContratista").css("box-shadow", "");

								$("#pnlPropietario").css("border-color", "");
								$("#pnlPropietario").css("box-shadow", "");

								$("#pnlIntermediario").css("border-color", "");
								$("#pnlIntermediario").css("box-shadow", "");

								$("#txtSubcontratist").removeClass("hidden");
								$("#txtContratist").addClass("hidden");
								$("#txtPropietary").addClass("hidden");
								$("#txtIntermediary").addClass("hidden");

								$("#msgPatron").addClass("hidden");
								// $("#numProcedimiento").addClass("hidden");
								$("#pnlObjetoContrato").addClass("hidden");
								tipoPatron = "3";

								if (tipoObra == 2) {
									$("#numProcedimiento")
											.removeClass("hidden");
								} else {
									$("#numProcedimiento").addClass("hidden");
								}
							});

					$("#pnlIntermediario")
							.click(
									function() {
										$("#pnlIntermediario").css(
												"border-color", "green");
										$("#pnlIntermediario").css(
												"box-shadow",
												"3px 3px 10px rgba(0,0,0,0.6)");

										$("#intermediary").addClass("hidden");
										$("#intermediarySel").removeClass(
												"hidden");

										$("#subcontratist").removeClass(
												"hidden");
										$("#subcontratistSel").addClass(
												"hidden");

										$("#contratist").removeClass("hidden");
										$("#contratistSel").addClass("hidden");

										$("#propietary").removeClass("hidden");
										$("#propietarySel").addClass("hidden");

										$("#pnlPropietario").css(
												"border-color", "");
										$("#pnlPropietario").css("box-shadow",
												"");

										$("#pnlContratista").css(
												"border-color", "");
										$("#pnlContratista").css("box-shadow",
												"");

										$("#pnlSubcontratista").css(
												"border-color", "");
										$("#pnlSubcontratista").css(
												"box-shadow", "");

										$("#txtIntermediary").removeClass(
												"hidden");
										$("#txtContratist").addClass("hidden");
										$("#txtPropietary").addClass("hidden");
										$("#txtSubcontratist").addClass(
												"hidden");

										$("#msgPatron").addClass("hidden");
										// $("#numProcedimiento").addClass("hidden");
										$("#pnlObjetoContrato").removeClass(
												"hidden");
										tipoPatron = "4";

										if (tipoObra == 2) {
											$("#numProcedimiento").removeClass(
													"hidden");
										} else {
											$("#numProcedimiento").addClass(
													"hidden");
										}
									});

					$("#btnTipoPatron").click(
									function() {

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
											if ((tipoObra == 1 && tipoPatron == "1")
													|| (tipoObra == 2 && tipoPatron == "2")) {
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
												$("#idContratista").addClass("hidden");
												$("#idIntermediario").addClass("hidden");
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
												$("#idContratista").addClass("hidden");
												$("#idIntermediario").addClass("hidden");
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
											

											var setearDomicilio = function() {
												var d = this;
												jsonDomi = d;

												cargaEtiquetas();
												$("#pnlDomicilioFiscal").removeClass("hidden");
												var cveRegPatronal = $("#lblRegPatron").text();

												$("#pnlDatosUbicacionObra").removeClass("hidden");
												var direccionTmp = construyeDireccion(jsonDomi);
												$("#lblUbicacionObraCap").text(direccionTmp.toUpperCase());

												var url = "/sdroc_web/validaCodigoPostal/"
														+ cveRegPatronal
														+ "/"
														+ jsonDomi.codigoPostal.codigoPostal;

												$.blockUI();
												$
														.ajax({
															type : "GET",
															contentType : "application/json",
															url : url,
															cache : false,
															success : function(
																	response) {
																$.unblockUI();

																if (response) {
																	var curStep = $("#step-3"), curStepBtn = curStep
																			.attr("id"), nextStepWizard = $(
																			'ul.wizard-steps-extensive li a[href="#'
																					+ curStepBtn
																					+ '"]')
																			.parent()
																			.next()
																			.children(
																					"a"), curInputs = curStep
																			.find("input[type='text'],input[type='url']"), isValid = true;

																	nextStepWizard
																			.removeAttr(
																					'disabled')
																			.trigger(
																					'click');

																	var stepLi = curStepBtn
																			+ '0';
																	$(
																			"#"
																					+ stepLi)
																			.children(
																					'a')
																			.remove();
																	$(
																			"#"
																					+ stepLi)
																			.addClass(
																					"completed");

																} else {
																	mostrarMensajeErrorDomicilio(jsonDomi.codigoPostal.codigoPostal);
																}

															}
														});

											};

											var setearDomicilioSinSepomex = function() {
												var d = this;
												jsonDomi = d;

												$("#pnlDatosUbicacionObra")
														.removeClass("hidden");
												var direccionTmp = construyeDireccion(jsonDomi);
												$("#lblUbicacionObraCap").text(
														direccionTmp
																.toUpperCase());

												var curStep = $("#step-3"), curStepBtn = curStep
														.attr("id"), nextStepWizard = $(
														'ul.wizard-steps-extensive li a[href="#'
																+ curStepBtn
																+ '"]')
														.parent().next()
														.children("a"), curInputs = curStep
														.find("input[type='text'],input[type='url']"), isValid = true;

												nextStepWizard.removeAttr(
														'disabled').trigger(
														'click');

												var stepLi = curStepBtn + '0';

												$("#" + stepLi).addClass(
														"completed");
												$("#" + stepLi).children('a')
														.remove();

											};

											if ((tipoObra == 1 && tipoPatron == "1")
													|| (tipoObra == 2 && tipoPatron == "2")) {
												parent.DomicilioCtrl
														.setOnCloseCallback(setearDomicilio);
											} else {
												parent.DomicilioCtrl
														.setOnCloseCallback(setearDomicilioSinSepomex);
											}
											// parent.DomicilioCtrl.setOnCloseCallback(setearDomicilio);
											// DomicilioCtrl.localizar();

										} else {
											$("#msgPatron").removeClass(
													"hidden");
										}

									});

					$("#btnDatosObra")
							.click(
									function() {

										if (validarDatosRegistroObra() == true
												&& valFechaIni && valFechaFin) {
											// $("#regObraModal").dialog();

											// BRHG
											var rfcSinNumReg = $(
													"#rfcSinNumReg").val();
											var tituloMensaje = '';
											var textoMensaje = '';
											if (rfcSinNumReg != ''
													&& rfcSinNumReg != null) {
												tituloMensaje = 'Registro de Aviso de Ubicaci&oacute;n de Obra';
												textoMensaje = '&iquest;Est&aacute; seguro de generar el registro de Aviso de Ubicaci&oacute;n de Obra con la informaci&oacute;n capturada?';
											} else {
												tituloMensaje = 'Registro de obra';
												textoMensaje = 'Declaro bajo protesta de decir verdad que la informaci&oacute;n, proporcionada en el presente registro de obra es ver&iacute;dica. &iquest;Est&aacute; seguro de generar el registro de la obra con la informaci&oacute;n capturada?';
											}

											var $mensajeConfirmacionRegistro = $('#regObraModal');
											$mensajeConfirmacionRegistro
													.dialog(
															{
																autoOpen : false,
																title : tituloMensaje,
																resizable : false,
																closeOnEscape : false,
																modal : true,
																width : 500,
																buttons : {
																	"No" : function() {
																		$(this)
																				.dialog(
																						"close");
																		$(this)
																				.dialog(
																						"destroy");

																	},
																	"Si" : function() {
																		firmarRegistroObra();
																		$(this)
																				.dialog(
																						"close");
																		// FORM_REGISTRO.on("submit",function(){$.blockUI();});
																		// FORM_REGISTRO.submit();
																		//						               
																	}
																}
															})
													.parent('.ui-dialog')
													.find(
															'.ui-dialog-titlebar-close')
													.hide();
											$mensajeConfirmacionRegistro
													.html(textoMensaje);
											$mensajeConfirmacionRegistro
													.dialog('open');

										} else {
											console.log('algo salio muy mal ');
										}
									});

					function validarMonto() {
						var resultadol = true;
						if ($("#txtMonto").val() == '') {
							if(tipoPatron == 4){
								$("#msgeMonto").text("Capture monto del contrato");
							}else{
								$("#msgeMonto").text("Capture el monto de la obra");
							}
							$("#msgeMonto").removeClass("hidden");
							$("#txtMonto").css('border-color', '#a94442');
							resultadol = false;
						} else {
							
							if (parseInt($("#txtMonto").val()) < 1) {
								if(tipoPatron == 4){
									$("#msgeMonto").text("El monto de contrato a capturar debe ser mayor a cero");
								}else{
									$("#msgeMonto").text("El monto de obra a capturar debe ser mayor a cero");
								}
								
								$("#msgeMonto").removeClass("hidden");
								$("#txtMonto").css('border-color', '#a94442');
								resultadol = false;
							} else {
								$("#msgeMonto").addClass("hidden");
								$("#txtMonto").css('border-color', '#ccc');
							}
						}
						return resultadol;

					}
					function validarSuperficie() {
						var resultadol = true;
						if ($("#txtSuperficie").val() != '') {
							if (parseInt($("#txtSuperficie").val()) < 1) {
								$("#msgeSuperficie").removeClass("hidden");
								$("#txtSuperficie").css('border-color',
										'#a94442');
								resultadol = false;
							} else {
								$("#msgeSuperficie").addClass("hidden");
								$("#txtSuperficie").css('border-color', '#ccc');
							}
						} else {
							$("#msgeSuperficie").removeClass("hidden");
							$("#txtSuperficie").css('border-color', '#a94442');
							resultadol = false;

						}
						return resultadol;

					}
					function validarTipoObra() {
						var resultadol = true;
						if ($("#selTipoObra").val() == '0'
								|| $("#selTipoObra").val() == 'Seleccione ...') {
							$("#msgeTipoObra").removeClass("hidden");
							$("#selTipoObra").css('border-color', '#a94442');
							resultadol = false;
						} else {
							$("#msgeTipoObra").addClass("hidden");
							$("#selTipoObra").css('border-color', '#ccc');
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

					function validarNumProcedimiento() {
						var resultadol = true;
						var numeroProc = $("#txtNumProcedimiento").val();

						$("#msgeNumProcedimientoCaracteres").addClass("hidden");
						$("#msgeNumProcedimiento").addClass("hidden");

						if (numeroProc == '') {
							$("#txtNumProcedimiento").css('border-color',
									'#a94442');
							$("#msgeNumProcedimiento").removeClass("hidden");
							resultadol = false;
						} else {
							$("#msgeNumProcedimiento").addClass("hidden");
							$("#txtNumProcedimiento").css('border-color',
									'#ccc');

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

						var resultado = true;

						var resulMonto = true;
						var resulPeriodo = true;
						var resulSuperficie = true;
						var resulTipoObra = true;
						var resulObjetoContra = true;
						var resultadoProc = true;
						var resulAviso = true;

						var rfcSinNumReg = $("#rfcSinNumReg").val();

						if (tipoPatron == "1") {
							resulMonto = validarMonto();
							resulPeriodo = validarPeriodo();
							resulTipoObra = validarTipoObra();

							resulSuperficie = validarSuperficie();
							// resulObjetoContra = validarObjetoContrato();

							if (resulMonto && resulPeriodo && resulTipoObra
									&& resulSuperficie && resulObjetoContra) {
								resultado = true;
							} else {
								resultado = false;
							}

						}

						// tipo de obra privada con contratista
						if (tipoObra == 1 && tipoPatron == "2") {
							if (!$('#chkSinRegistro').is(":checked")) {
								resulAviso = validarNumeroAviso();
							}
							if (rfcSinNumReg == '' || rfcSinNumReg == null) {
								resulPeriodo = validarPeriodo();
								resulMonto = validarMonto();
								resulSuperficie = validarSuperficie();
								resulTipoObra = validarTipoObra();

								// resulObjetoContra = validarObjetoContrato();

								if (resulMonto && resulPeriodo && resulTipoObra
										&& resulSuperficie && resulObjetoContra
										&& resulAviso) {
									resultado = true;
								} else {
									resultado = false;
								}
							} else {
								resultado = true;
							}

						}

						// obra privada para subcontratista
						if (tipoObra == 1 && tipoPatron == "3") {
							if (!$('#chkSinRegistro').is(":checked")) {
								resulAviso = validarNumeroAviso();
							}
							if (rfcSinNumReg == '' || rfcSinNumReg == null) {
								resulPeriodo = validarPeriodo();
								resulMonto = validarMonto();
								resulTipoObra = validarTipoObra();
								resulSuperficie = validarSuperficie();

								// resulObjetoContra = validarObjetoContrato();

								if (resulMonto && resulPeriodo && resulTipoObra
										&& resulSuperficie && resulObjetoContra
										&& resulAviso) {
									resultado = true;
								} else {
									resultado = false;
								}
							} else {
								resultado = true;
							}

						}
						// obra privado para intermediarios
						if (tipoObra == 1 && tipoPatron == "4") {
							if (!$('#chkSinRegistro').is(":checked")) {
								resulAviso = validarNumeroAviso();
							}
							if (rfcSinNumReg == '' || rfcSinNumReg == null) {
								resulPeriodo = validarPeriodo();
								resulMonto = validarMonto();
								resulObjetoContra = validarObjetoContrato();

								// BHG Se modifica para quitar la superficie de
								// la obra
								// resulSuperficie = validarSuperficie();

								// resulTipoObra = validarTipoObra();

								// if (resulMonto && resulPeriodo &&
								// resulObjetoContra && resulSuperficie
								// && resulTipoObra) {

								if (resulMonto && resulPeriodo
										&& resulObjetoContra && resulTipoObra
										&& resulAviso) {
									resultado = true;
								} else {
									resultado = false;
								}
							} else {
								resultado = true;
							}
						}

						// tipo de obra publica con contratista
						if (tipoObra == 2 && tipoPatron == "2") {
							resulMonto = validarMonto();
							resulPeriodo = validarPeriodo();
							resulTipoObra = validarTipoObra();
							resultadoProc = validarNumProcedimiento();

							// resultado = validarObjetoContrato();
							// resultado = validarSuperficie();

							if (resulMonto && resulPeriodo && resulTipoObra
									&& resultadoProc) {
								resultado = true;
							} else {
								resultado = false;
							}
						}

						// Obra publica para el subcontratista msgeNumAviso
						if (tipoObra == 2 && tipoPatron == "3") {
							if (!$('#chkSinRegistro').is(":checked")) {
								resulAviso = validarNumeroAviso();
							}
							if (rfcSinNumReg == '' || rfcSinNumReg == null) {
								resulPeriodo = validarPeriodo();
								resulMonto = validarMonto();
								resulTipoObra = validarTipoObra();

								if (resulMonto && resulPeriodo && resulTipoObra
										&& resulAviso) {
									resultado = true;
								} else {
									resultado = false;
								}
							} else {
								resultado = true;
							}
						}
						// obra publica para intermediarios
						if (tipoObra == 2 && tipoPatron == "4") {
							if (!$('#chkSinRegistro').is(":checked")) {
								resulAviso = validarNumeroAviso();
							}
							if (rfcSinNumReg == '' || rfcSinNumReg == null) {
								resulPeriodo = validarPeriodo();
								resulMonto = validarMonto();
								resulObjetoContra = validarObjetoContrato();

								// resultado = validarTipoObra();

								if (resulMonto && resulPeriodo
										&& resulObjetoContra && resulAviso) {
									resultado = true;
								} else {
									resultado = false;
								}
							} else {
								resultado = true;
							}
						}

						// Validacion de fechas para avisos de ubicaciÃ³n de obra
						if ($('#chkSinRegistro').is(":checked")) {
							var fecInicio = $("#fecInicio").val();
							var fecTermino = $("#fecTermino").val();
							$("#msgeFecFin").text('');
							$("#msgeFecIni").text('');

							if ((fecInicio != undefined && fecInicio != null && fecInicio != '')
									&& (fecTermino == undefined
											|| fecTermino == null || fecTermino == '')) {
								
								$("#msgeFecFin").removeClass("hidden");
								$("#fecTermino").css('border-color', '#a94442');
								$("#msgeFecFin").html('Seleccione la fecha de t&eacute;rmino');
								resultado = false;
							} else if ((fecTermino != undefined
									&& fecTermino != null && fecTermino != '')
									&& (fecInicio == undefined
											|| fecInicio == null || fecInicio == '')) {
								
								$("#msgeFecIni").removeClass("hidden");
								$("#fecInicio").css('border-color', '#a94442');
								$("#msgeFecIni").text(
										'Seleccione la fecha de inicio');
								resultado = false;
							} else {
								
								$("#msgeFecIni").addClass("hidden");
								$("#msgeFecFin").addClass("hidden");
								$("#fecInicio").css('border-color', '#ccc');
								$("#fecTermino").css('border-color', '#ccc');

								var fechaInicioDP = convertirFechaFormatoDatePicker(fecInicio);
								var fechaTermino = convertirFechaFormatoDatePicker(fecTermino);
								if (fechaInicioDP > fechaTermino) {
									banderaFecIni = false;
									$("#msgeFecFin").removeClass("hidden");
									$("#fecTermino").css('border-color','#a94442');
									$("#msgeFecFin").html('La fecha de t&eacute;rmino no puede ser menor a la fecha de inicio');
									// $("#fecInicio").css('border-color',
									// '#a94442');
									// $("#msgeFecIni").text('La fecha de inicio
									// no puede ser mayor a la fecha de
									// tÃ©rmino');
									// $("#msgeFecIni").removeClass("hidden");
									resultado = false;
								} else {
									$("#msgeFecIni").addClass("hidden");
									$("#msgeFecFin").addClass("hidden");
									$("#fecInicio").css('border-color', '#ccc');
									$("#fecTermino")
											.css('border-color', '#ccc');
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
					
					$("#btnEncuesta")
							.click(
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

					$("#fecTermino")
							.datepicker(
									{
										beforeShow : function() {
											setTimeout(function() {
												$('.ui-datepicker').css(
														'z-index',
														99999999999999);
											}, 0);
										},
										onSelect : function() {

											dateObjectFin = $(this).datepicker(
													'getDate');

											$("#msgeFecFin").addClass("hidden");
											$("#fecTermino").css(
													'border-color', '#ccc');
											$("#msgeFecFin").text('');

											if (dateObjectInicio != '') {
												// revisar que la fecha
												// seleccionada sea mayor a la
												// fecha de inicio
												// revisar que la fecha de
												// inicio sea mayor a los 30
												// dias a aprtir del dia de
												// registro
												var fechaSeleccionadaIni = new Date(
														dateObjectInicio
																.getFullYear(),
														dateObjectInicio
																.getMonth(),
														dateObjectInicio
																.getDate());
												var fechaSeleccionadafin = new Date(
														dateObjectFin
																.getFullYear(),
														dateObjectFin
																.getMonth(),
														dateObjectFin.getDate());

												// si la fecha de inicio es
												// menor o igual a la fecja de
												// termino todo bien
												if (fechaSeleccionadaIni <= fechaSeleccionadafin) {
													valFechaFin = true;
													$("#msgeFecFin").addClass(
															"hidden");
													$("#msgeFecIni").addClass(
															"hidden");
													$("#fecInicio").css(
															'border-color',
															'#ccc');
													$("#fecTermino").css(
															'border-color',
															'#ccc');
													$("#msgeFecIni").text('');
													$("#msgeFecFin").text('');

													// se valida nuevamente la
													// fecha de inicio
													// solo para registro de
													// obra
													if (!$('#chkSinRegistro')
															.is(":checked")) {
														if (validaFechaInicioMenor30Dias()) {
															$("#msgeFecFin")
																	.addClass(
																			"hidden");
															$("#fecTermino")
																	.css(
																			'border-color',
																			'#ccc');
															$("#msgeFecFin")
																	.text('');
															valFechaIni = true;
														}
													} else {
														valFechaIni = true;
													}

												} else {
													$("#msgeFecFin").removeClass("hidden");
													$("#fecTermino").css('border-color','#a94442');
													$("#msgeFecFin").text('La fecha de t&eacute;rmino no puede ser menor a la fecha de inicio');
													valFechaFin = false;
												}

											} else {
												$("#msgeFecIni").removeClass(
														"hidden");
												$("#fecInicio").css(
														'border-color',
														'#a94442');
												$("#msgeFecIni")
														.text(
																'Seleccione la fecha de inicio');
												valFechaIni = false;
											}

										

										}
									});

					var banderaFecIni = true;
					$("#fecInicio")
							.datepicker(
									{
										beforeShow : function() {
											setTimeout(function() {
												$('.ui-datepicker').css(
														'z-index',
														99999999999999);
											}, 0);
										},
										onSelect : function() {
											$("#msgeFecIni").addClass("hidden");
											$("#msgeFecIni").text("");
											$("#fecInicio").css('border-color',
													'#ccc');

											valFechaIni = true;
											dateObjectInicio = $(this)
													.datepicker('getDate');

											// valida el periodo de 30 dias
											if (!$('#chkSinRegistro').is(
													":checked")) {

												if (validaFechaInicioMenor30Dias()) {
													// valido que la fecha
													// seleccionada sea mayor a
													// la fecha de termino
													if (dateObjectFin != '') {
														$("#msgeFecFin")
																.addClass(
																		"hidden");
														$("#fecTermino").css(
																'border-color',
																'#ccc');
														$("#msgeFecFin").text(
																'');

														var fechaSeleccionadaIni = new Date(
																dateObjectInicio
																		.getFullYear(),
																dateObjectInicio
																		.getMonth(),
																dateObjectInicio
																		.getDate());
														var fechaSeleccionadafin = new Date(
																dateObjectFin
																		.getFullYear(),
																dateObjectFin
																		.getMonth(),
																dateObjectFin
																		.getDate());

														if (fechaSeleccionadaIni > fechaSeleccionadafin) {
															// banderaFecIni =
															// false;
															$("#msgeFecFin")
																	.removeClass(
																			"hidden");
															$("#fecTermino")
																	.css(
																			'border-color',
																			'#a94442');
															$("#msgeFecFin").html('La fecha de t&eacute;rmino no puede ser menor a la fecha de inicio');
															valFechaIni = false;
														} else {
															$("#msgeFecFin")
																	.text('');
															$("#msgeFecFin")
																	.addClass(
																			"hidden");
															$("#fecInicio")
																	.css(
																			'border-color',
																			'#ccc');
															$("#fecTermino")
																	.css(
																			'border-color',
																			'#ccc');
															valFechaFin = true;
															valFechaIni = true;
															// banderaFecIni =
															// true;
														}
													} else {
														
														$("#msgeFecFin")
																.removeClass(
																		"hidden");
														$("#fecTermino").css(
																'border-color',
																'#a94442');
														$("#msgeFecFin").html('Seleccione la fecha de t&eacute;rmino');
														valFechaFin = false;
													}
												} else {
													valFechaIni = false;
												}
											}

											

										}
									});

					function guardarDatos() {
						// validacion de datos requeridos
						if ($("#passCvePrivada").val() == '') {

							$("#mesgError").removeClass("hidden");

						} else {
							$("#mesgError").addClass('hidden');

							var registro = jsonRegistroObras(curStepBtn);

							if (registro == true) {
								
								var curStep = $("#step-4"), curStepBtn = curStep
										.attr("id"), nextStepWizard = $(
										'ul.wizard-steps-extensive li a[href="#'
												+ curStepBtn + '"]').parent()
										.next().children("a"), curInputs = curStep
										.find("input[type='text'],input[type='url']"), isValid = true;

								nextStepWizard.removeAttr('disabled').trigger(
										'click');

								var stepLi = curStepBtn + '0';

								$("#" + stepLi).addClass("completed");
								$("#" + stepLi).children('a').remove();
							} else {
								console.log('ERROR : redireccion');
							}
						}

					}

					/**
					 * registrar obra
					 */
					function jsonRegistroObras() {

						var url = "/sdroc_web/registrarObra";
						if (tipoObra == 1) {
							cveTipoObra = 74;
							desTipoObra = 'Privada';
						} else {
							cveTipoObra = 75;
							desTipoObra = 'Pubica';
						}
						var desTipoPatron = descripcionPatron(parseInt(tipoPatron));
						var cveSubDelegacion = $("#idCveSubDelegacion").val();
						var cveDelegacion = $("#idCveDelegacion").val();
						var desSubDelegacion = $("#idDesSubDelegacion").val();
						var desDelegacion = $("#idDesDelegacion").val();
						var numRegistro = Math.floor((Math.random() * 50000000) + 1);

						var numRegistroPrincipal = 0;
						if ($("#idCveRegistroObraPrincipal").val() != '') {
							// numRegistroPrincipal =
							// $("#txtNumRegistro").val();
							numRegistroPrincipal = $(
									"#idCveRegistroObraPrincipal").val();
							
						}
						

						var refApellidoPaterno = $("#idApellidoPaterno").val();
						var refApellidoMaterno = $("#idApellidoMaterno").val();
						var nomPatron = $("#idNombre").val();
						var cveRfc = $("#lblRFCPatron").text();
						var cveRegPatronal = $("#lblRegPatron").text();
						var refRazonSocial = $("#lblRazonSocialPatron").text();
						var numLicitacion = null;
						// var numLicitacion = $("#txtNumAviso").val();
						// if(numLicitacion == '' || numLicitacion == null){
						// numLicitacion = 0;
						// }
						var cveObjetoContrato = $("#selObjetoContrato").val();
						var desObjetoContrato = $("#selObjetoContrato option:selected").text();
						if (cveObjetoContrato == ''
								|| cveObjetoContrato == null
								|| cveObjetoContrato == '0') {
							cveObjetoContrato = 1;
						}
						var impEjercido = 0;
						var impContratado = 0;

						var rfcSinNumReg = $("#rfcSinNumReg").val();

						if (rfcSinNumReg != '' && rfcSinNumReg != null) {
							$("#txtAcuseReg").html('Aviso de Ubicaci&oacute;n de Obra');
							$("#txtExitoReg").html('Aviso de Ubicaci&oacute;n de Obra exitoso.');
							$("#txtGeneraReg").html('Aviso de Ubicaci&oacute;n de Obra generado correctamente.');
							
							if(parseInt(tipoPatron) == 2){
								$("#msgAvisoContratista").removeClass("hidden");
							}else if(parseInt(tipoPatron) == 3){
								$("#msgAvisoSubcontratista").removeClass("hidden");
							}else if(parseInt(tipoPatron) == 4){
								$("#msgAvisoIntemediario").removeClass("hidden");
							}
							
						} else {
							$("#txtAcuseReg").text('Acuse de registro de la obra');
							$("#txtExitoReg").text('Registro de obra exitoso.');
							$("#txtGeneraReg").text('Registro de obra generado correctamente.');
							
						}
						// var texRazonSocial = $('#texRazonSocial').val();

						var fecInicio = $("#fecInicio").val();
						var dateIni = null;
						if (fecInicio != '') {
							var partsIn = fecInicio.split('/');
							dateIni = new Date(partsIn[2],
									parseInt(partsIn[1]) - 1, partsIn[0]);
						}
						var fecTermino = $("#fecTermino").val();
						var dateFin = null;
						if (fecTermino != '') {
							var partsFin = fecTermino.split('/');
							dateFin = new Date(partsFin[2],
									parseInt(partsFin[1]) - 1, partsFin[0]);
						}
						var selecTipoObra = $("#selTipoObra").val();
						var desSelecTipoObra = $("#selTipoObra option:selected")
								.text();

						if (selecTipoObra == undefined || selecTipoObra == null
								|| selecTipoObra == 0) {
							selecTipoObra = cveTipoObra;
						}

						var numAviso = 0;

						if ($("#txtNumAviso").val() != "") {
							numAviso = $("#txtNumAviso").val();
						}
						

						var monto = $("#txtMonto").val().replace(/,/g, '');
						if (monto == null || monto == '') {
							monto = '0';
						}
						var superficie = $("#txtSuperficie").val().replace(
								/,/g, '');
						if (superficie == null || superficie == '') {
							superficie = '0';
						}

						var numProcedimiento = null;
						// BRHG ModificaciÃ³n para asignar el valo rde 0 al
						// procedimiento por que ya es alfanumerico.
						if ($("#txtNumProcedimiento").val() != undefined
								&& $("#txtNumProcedimiento").val() != null
								&& $("#txtNumProcedimiento").val() != '') {
							numProcedimiento = $("#txtNumProcedimiento").val();
						} else if ($("#txtNumAviso").val() != undefined
								&& $("#txtNumAviso").val() != null
								&& $("#txtNumAviso").val() != '') {
							numProcedimiento = $("#txtNumAviso").val();
							// if(numProcedimiento == null || numProcedimiento
							// == '') {
							// numProcedimiento = 0;
							// }
						}
						

						var commentario = $("#comment").val();
						var passCvePrivada = $("#passCvePrivada").val();

						var cadenaOriginal = "";
						var cveTipoPersona = $("#idCveTipoPersona").val();

						var jsonDomiFinal = {};

						jsonDomiFinal = validaDomicilio();


						cadenaOriginal = "||Invocante:portalimssdigital%NOMBRE_TRAMITE%|%FECHA_ACTUAL%"
						cadenaOriginal = cadenaOriginal + "%NUM_REG_OBRA%" + "|RFC: ";
						
						if (rfcSinNumReg != '') {
							cadenaOriginal = cadenaOriginal + rfcSinNumReg;
						} else {
							cadenaOriginal = cadenaOriginal + cveRfc;
						}
						cadenaOriginal = cadenaOriginal + "|Nombre o razÃ³n social: ";
						cadenaOriginal = cadenaOriginal + refRazonSocial + "|";
						

						if (cveTipoPersona == 2) {
							cadenaOriginal = cadenaOriginal + "CURP: " + $("#idCurp").val() + "|";
						}
						cadenaOriginal = cadenaOriginal + "Registro patronal: "	+ cveRegPatronal + "||";

						datosRegObra = {
							subDelegacionDTO : {
								cveSubdelegacion : cveSubDelegacion,
								nomSubdelegacion : desSubDelegacion,
								cveCodigo : 0,
								delegacionDTO : {
									cveDelegacion : cveDelegacion,
									nomDelegacion : desDelegacion
								}
							},
							cveRegistroObra : numRegistro,
							cveRegistroAvisoObra : numRegistro,
							cveRegistroObraPrincipal : numRegistroPrincipal,
							cveRfcPatron : rfcSinNumReg,
							tipoObraDTO : {
								cveTipoObra : selecTipoObra,
								desTipoObra : desSelecTipoObra,
								clasificacionObraDTO : {
									cveClasificacionObra : tipoObra,
									desClasificacionObra : desTipoObra
								}
							},
							informacionPatronDTO : {
								tipoPatronDTO : {
									cveTipoPatron : tipoPatron,
									desTipoPatron : desTipoPatron
								},
								tipoPersonaDTO : {
									cveTipoPersona : cveTipoPersona
								// ,
								// desTipoPersona : tipoPersona
								},
								cveRfc : cveRfc,
								cveRegPatronal : cveRegPatronal,
								refApellidoPaterno : refApellidoPaterno,
								refApellidoMaterno : refApellidoMaterno,
								nomPatron : nomPatron,
								refRazonSocial : refRazonSocial
							},
							fecIniObra : dateIni,
							fecFinObra : dateFin,
							impObra : parseFloat(monto),
							refSupConstruccion : parseFloat(superficie),
							refObservacion : commentario,
							numProcedimiento : numProcedimiento,
							numLicitacion : numLicitacion,
							fecIniContrato : dateIni,
							fecFinContrato : dateFin,
							objetoContratoDTO : {
								cveObjetoContrato : cveObjetoContrato,
								desObjetoContrato : desObjetoContrato
							},
							impEjercido : parseFloat(impEjercido),
							impContratado : parseFloat(impContratado),
							refAcuseReg : passCvePrivada,
							refCveAvisoObra: numRegistro,
							// numIncumplimientos : parseFloat(numAviso),

							ubicacionObraDTO : jsonDomiFinal,
							estatusObraDTO : {
								cveEstatusObra : 1
							},
							refCadenaOriginal : cadenaOriginal
						};

						$("#selTipoObra").val(0);
						var resulta = enviarController(url, datosRegObra);
						return resulta;

					}

					function validaDomicilio() {
						// var jsonDomi = {};
						if ((tipoObra == 1 && tipoPatron == "1")
								|| (tipoObra == 2 && tipoPatron == "2")) {
							jsonDomi = jsonUbicacion();
						} else if ((tipoObra == 1 && tipoPatron == "2" && $(
								'#chkSinRegistro').is(":checked"))
								|| (tipoObra == 1 && tipoPatron == "3" && $(
										'#chkSinRegistro').is(":checked"))
								|| (tipoObra == 1 && tipoPatron == "4" && $(
										'#chkSinRegistro').is(":checked"))
								|| (tipoObra == 2 && tipoPatron == "3" && $(
										'#chkSinRegistro').is(":checked"))
								|| (tipoObra == 2 && tipoPatron == "4" && $(
										'#chkSinRegistro').is(":checked"))) {
							jsonDomi = jsonUbicacion();
						} else {
							jsonDomi = {
								refObservacion : $('#commentUbicacion').val()
							};
						}

						return jsonDomi;
					}

					function descripcionPatron(cvePatron) {
						var descripcion = "";
						switch (cvePatron) {
						case 1:
							descripcion = "Propietario";
							break;
						case 2:
							descripcion = "Contratista";
							break;
						case 3:
							descripcion = "Subcontratista";
							break;
						case 4:
							descripcion = "Intermediario";
							break;
						}
						return descripcion;
					}

					function jsonUbicacion() {
						var jsonUbicacion = {};
						var colonia = jsonDomi.asentamiento.tipoAsentamiento.descripcion
								+ ' ' + jsonDomi.asentamiento.nombre;
						var calle = "";
						if (typeof jsonDomi.vialidadPrimaria.tipoVialidad == 'undefined') {
							calle = jsonDomi.calle;
						} else {
							calle = jsonDomi.vialidadPrimaria.tipoVialidad.descripcion
									+ ' ' + jsonDomi.vialidadPrimaria.nombre
						}
						jsonUbicacion = {
							calle : calle,
							numExterior : jsonDomi.numExterior1,
							codigoPostal : jsonDomi.codigoPostal.codigoPostal,
							refEntidad : jsonDomi.asentamiento.localidad.municipio.entidadFederativa.nombre,
							refMunicipio : jsonDomi.asentamiento.localidad.municipio.nombre,
							cveEntidad : jsonDomi.asentamiento.localidad.municipio.entidadFederativa.clave,
							cveMunicipio : jsonDomi.asentamiento.localidad.municipio.clave,
							cveLocalidad : jsonDomi.asentamiento.localidad.clave,
							refColonia : colonia.toUpperCase(),
							numExteriorAlf : jsonDomi.numExteriorAlf == null ? ''
									: jsonDomi.numExteriorAlf.toUpperCase(),
							numInterior : jsonDomi.numInterior == null ? 0
									: jsonDomi.numInterior,
							numInteriorAlf : jsonDomi.numInteriorAlf == null ? ''
									: jsonDomi.numInteriorAlf.toUpperCase(),
							numExteriorDos : jsonDomi.numExterior2 == null ? 0
									: jsonDomi.numExterior2,
							refObservacion : jsonDomi.descripcion == null ? ''
									: jsonDomi.descripcion.toUpperCase(),
							refPosterior : jsonDomi.vialidadReferenciaPosterior == null ? ''
									: jsonDomi.vialidadReferenciaPosterior.tipoVialidad.descripcion
											.toUpperCase()
											+ ' '
											+ jsonDomi.vialidadReferenciaPosterior.nombre,
							refPrimaria : jsonDomi.vialidadReferenciaPrimaria == null ? ''
									: jsonDomi.vialidadReferenciaPrimaria.tipoVialidad.descripcion
											.toUpperCase()
											+ ' '
											+ jsonDomi.vialidadReferenciaPrimaria.nombre,
							refSecundaria : jsonDomi.vialidadReferenciaSecundaria == null ? ''
									: jsonDomi.vialidadReferenciaSecundaria.tipoVialidad.descripcion
											.toUpperCase()
											+ ' '
											+ jsonDomi.vialidadReferenciaSecundaria.nombre
													.toUpperCase()

						};

						return jsonUbicacion;

					}

					/**
					 * 
					 */
					function enviarController(accion, data) {
						var resultado = true;
						$.blockUI();
						$.ajax({
							type : "POST",
							contentType : "application/json",
							url : accion,
							async : false,
							data : JSON.stringify(data),
							timeout : 100000,
							cache : false,
							success : function(response) {

								$('#lblEjemplo').text(response);
								$('#pnlRepAcuse').attr("src",
										'/sdroc_web/getRegistroObraPDF');

								$.unblockUI();
							},
							error : function(response) {
								$.unblockUI();
								resultado = false;
							}
						});

						return resultado;
					}

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
					$("#btnUbicacion")
							.click(
									function() {
										var datosVal = true;
										var datosValNum = true;
										var rfcSinNumReg = $("#rfcSinNumReg")
												.val();
										var varNumRegistro = $(
												"#txtNumRegistro").val();
										$("#pnlDomicilioFiscal").removeClass(
												"hidden");
										if ($('#chkSinRegistro').is(":checked")) {
											if (rfcSinNumReg == '') {
												$("#msgRFC").removeClass("hidden");
												$("#rfcSinNumReg").css(
														'border-color',
														'#a94442');
												$("#btnUbicacion").addClass(
														"hidden");
												if (tipoPatron == "2") {
													$("#idContraRFC")
															.removeClass(
																	"hidden");
													$("#idSubContraRFC")
															.addClass("hidden");
													$("#idInterRFC").addClass(
															"hidden");
												} else if (tipoPatron == "3") {
													$("#idContraRFC").addClass(
															"hidden");
													$("#idSubContraRFC")
															.removeClass(
																	"hidden");
													$("#idInterRFC").addClass(
															"hidden");
												} else if (tipoPatron == "4") {
													$("#idContraRFC").addClass(
															"hidden");
													$("#idSubContraRFC")
															.addClass("hidden");
													$("#idInterRFC")
															.removeClass(
																	"hidden");
												}
												datosVal = false;
											}
										} else {
											if ((tipoObra == 1 && tipoPatron == "2")
													|| (tipoObra == 1 && tipoPatron == "3")
													|| (tipoObra == 1 && tipoPatron == "4")
													|| (tipoObra == 2 && tipoPatron == "3")
													|| (tipoObra == 2 && tipoPatron == "4")) {
												if (varNumRegistro == '') {
													$("#msgNumRegistro")
															.removeClass(
																	"hidden");
													$("#txtNumRegistro").css(
															'border-color',
															'#a94442');
													$("#btnUbicacion")
															.addClass("hidden");
													datosValNum = false;
												} else {
													$("#pnlDatosUbicacionObra")
															.removeClass(
																	"hidden");
													var cadenaRegObra = $(
															"#lblUbicacionObraRes")
															.text();
													$("#lblUbicacionObraCap")
															.text(
																	cadenaRegObra
																			.toUpperCase());
												}
											} else {
												$("#pnlDatosUbicacionObra")
														.removeClass("hidden");
												var direccionTmp = construyeDireccion(jsonDomi);
												$("#lblUbicacionObraCap").text(
														direccionTmp
																.toUpperCase());
											}
										}

										var cpValidar = $('#cpValidar').val();

										if (cpValidar != null
												&& cpValidar != "") {
											var cveRegPatronal = $(
													"#lblRegPatron").text();

											var url = "/sdroc_web/validaCodigoPostal/"
													+ cveRegPatronal
													+ "/"
													+ cpValidar;

											$.blockUI();
											$
													.ajax({
														type : "GET",
														contentType : "application/json",
														url : url,
														cache : false,
														success : function(
																response) {
															$.unblockUI();

															if (response) {
																var curStep = $("#step-3"), curStepBtn = curStep
																		.attr("id"), nextStepWizard = $(
																		'ul.wizard-steps-extensive li a[href="#'
																				+ curStepBtn
																				+ '"]')
																		.parent()
																		.next()
																		.children(
																				"a"), curInputs = curStep
																		.find("input[type='text'],input[type='url']"), isValid = true;

																nextStepWizard
																		.removeAttr(
																				'disabled')
																		.trigger(
																				'click');

																var stepLi = curStepBtn
																		+ '0';
																$("#" + stepLi)
																		.addClass(
																				"completed");
																$("#" + stepLi)
																		.children(
																				'a')
																		.remove();
															} else {
																mostrarMensajeErrorDomicilio(cpValidar);
															}

														}
													});

										}
										if (datosVal && datosValNum) {
											cargaEtiquetas();

											var curStep = $(this).closest(
													".setup-content"), curStepBtn = curStep
													.attr("id"), nextStepWizard = $(
													'ul.wizard-steps-extensive li a[href="#'
															+ curStepBtn + '"]')
													.parent().next().children(
															"a"), curInputs = curStep
													.find("input[type='text'],input[type='url']"), isValid = true;

											nextStepWizard.removeAttr(
													'disabled')
													.trigger('click');
											var stepLi = curStepBtn + '0';

											$("#" + stepLi).addClass(
													"completed");
											$("#" + stepLi).children('a')
													.remove();
										}

									});

					function cargaEtiquetas() {
						var rfcSinNumReg = $("#rfcSinNumReg").val();
						var numAviso = $("#txtNumRegistro").val();
						//$("#txtNumAviso").attr("disabled", true);
						
						if (rfcSinNumReg != '' && rfcSinNumReg != null) {
							$("#btnDatosObra").html('Enviar Informaci&oacute;n');
							$("#idLabelFechaIni").text('Fecha de inicio :');
							$("#idLabelFechaFin").html('Fecha de t&eacute;rmino :');
							$("#idLabObjeto").text('Objeto del contrato :');
							$("#idLabMonto").text('Monto de la obra :');
							$("#idLabSuper").html('Superficie de construcci&oacute;n :');
							$("#idLabObra").text('Tipo de obra :');
						} else {
							$("#btnDatosObra").text('Registrar Obra');
							$("#idLabelFechaIni").text('Fecha de inicio *:');
							$("#idLabelFechaFin").html('Fecha de t&eacutermino *:');
							$("#idLabObjeto").text('Objeto del contrato *:');
							$("#idLabMonto").text('Monto de la obra *:');
							$("#idLabSuper").html('Superficie de construcci&oacute;n *:');
							$("#idLabObra").text('Tipo de obra *:');
						}
						if (tipoPatron == "4") {
							$("#fechasEjecucion").text("Vigencia del contrato");
							if (rfcSinNumReg != '' && rfcSinNumReg != null) {
								$("#idLabMonto").text('Monto del contrato :');
							} else {
								$("#idLabMonto").text('Monto del contrato *:');
							}
							$("#msgeMonto").text("Capture monto del contrato");
							$("#txtMonto").attr('placeholder','Monto del contrato');
						} else {
							$("#fechasEjecucion").html("Periodo de ejecuci&oacute;n");
							$("#msgeMonto").text("Capture el monto de la obra");
							$("#txtMonto").attr('placeholder','Monto de la obra');
						}

						// Privado Propietario
						if (tipoObra == 1 && tipoPatron == "1") {
							$("#idLabSuper").html('Superficie de construcci&oacute;n *:');
							$("#btnDatosObra").text('Registrar Obra');
							$("#pnlRFC").addClass("hidden");
						}
						// obra privada para un contratista
						if (tipoObra == 1 && tipoPatron == "2") {

							if ($('#chkSinRegistro').is(":checked")) {
								$("#pnlDatosUbicacionObra").removeClass(
										"hidden");
								$("#pnlDomicilioFiscal").addClass("hidden");
								$("#pnlRegistroPatronal").addClass("hidden");
								$("#pnlRazonSocial").addClass("hidden");

								$("#pnlObjetoContrato").addClass("hidden");
								$("#pnlSuperficie").addClass("hidden");
								$("#pnlTipoObra").addClass("hidden");
								$("#numProcedimiento").addClass("hidden");
								$("#numAviso").addClass("hidden");
								$("#pnlObservaciones").addClass("hidden");
							} else {
								$("#pnlRFC").addClass("hidden");
								$("#pnlObjetoContrato").addClass("hidden");
								$("#pnlSuperficie").removeClass("hidden");
								$("#pnlTipoObra").removeClass("hidden");
								$("#numProcedimiento").addClass("hidden");
								$("#numAviso").removeClass("hidden");
								$("#pnlObservaciones").removeClass("hidden");
							}
						}

						// obra privada para subcontratista
						if (tipoObra == 1 && tipoPatron == "3") {

							if ($('#chkSinRegistro').is(":checked")) {
								$("#pnlDatosUbicacionObra").removeClass(
										"hidden");
								$("#pnlDomicilioFiscal").addClass("hidden");
								$("#pnlRegistroPatronal").addClass("hidden");
								$("#pnlRazonSocial").addClass("hidden");

								$("#pnlObjetoContrato").addClass("hidden");
								$("#pnlSuperficie").addClass("hidden");
								$("#pnlTipoObra").addClass("hidden");
								$("#numProcedimiento").addClass("hidden");
								$("#numAviso").addClass("hidden");
								$("#pnlObservaciones").addClass("hidden");
							} else {
								$("#pnlRFC").addClass("hidden");
								$("#idLabSuper").text(
										'Superficie de construcciÃ³n*');
								$("#pnlObjetoContrato").addClass("hidden");
								$("#pnlSuperficie").removeClass("hidden");
								$("#pnlTipoObra").removeClass("hidden");
								$("#numProcedimiento").addClass("hidden");
								$("#numAviso").removeClass("hidden");
								$("#pnlObservaciones").removeClass("hidden");
							}
						}

						// obra privada para Intermediario
						if (tipoObra == 1 && tipoPatron == "4") {
							$("#fechasEjecucion").text("Vigencia del contrato");

							if ($('#chkSinRegistro').is(":checked")) {
								$("#pnlDatosUbicacionObra").removeClass(
										"hidden");
								$("#pnlDomicilioFiscal").addClass("hidden");
								$("#pnlRegistroPatronal").addClass("hidden");
								$("#pnlRazonSocial").addClass("hidden");

								$("#pnlObjetoContrato").addClass("hidden");
								$("#pnlSuperficie").addClass("hidden");
								$("#pnlTipoObra").addClass("hidden");
								$("#numProcedimiento").addClass("hidden");
								$("#numAviso").addClass("hidden");
								$("#pnlObservaciones").addClass("hidden");
							} else {
								$("#pnlRFC").addClass("hidden");
								$("#pnlObjetoContrato").removeClass("hidden");
								$("#pnlSuperficie").addClass("hidden");
								$("#pnlTipoObra").addClass("hidden");
								$("#numProcedimiento").addClass("hidden");
								$("#numAviso").removeClass("hidden");
								$("#pnlObservaciones").removeClass("hidden");

							}
						}

						// obra publica para contratista
						if (tipoObra == 2 && tipoPatron == "2") {
							$("#idLaProcedimiento").text(
									'NÃºmero de procedimiento*');
							$("#idLabSuper").html('Superficie de construcci&oacute;n');
							$("#btnDatosObra").text('Registrar Obra');
							$("#pnlRFC").addClass("hidden");
						}
						// obra publica para subcontratista
						if (tipoObra == 2 && tipoPatron == "3") {

							if ($('#chkSinRegistro').is(":checked")) {
								$("#pnlDatosUbicacionObra").removeClass(
										"hidden");
								$("#pnlDomicilioFiscal").addClass("hidden");
								$("#pnlRegistroPatronal").addClass("hidden");
								$("#pnlRazonSocial").addClass("hidden");

								$("#pnlObjetoContrato").addClass("hidden");
								$("#pnlSuperficie").addClass("hidden");
								$("#pnlTipoObra").addClass("hidden");
								$("#numProcedimiento").addClass("hidden");
								$("#numAviso").addClass("hidden");
								$("#pnlObservaciones").addClass("hidden");
							} else {
								$("#pnlRFC").addClass("hidden");
								$("#idLabSuper").text(
										'Superficie de construcciÃ³n');
								$("#pnlObjetoContrato").addClass("hidden");
								$("#pnlSuperficie").removeClass("hidden");
								$("#pnlTipoObra").removeClass("hidden");
								$("#numProcedimiento").addClass("hidden");
								$("#numAviso").removeClass("hidden");
								$("#pnlObservaciones").removeClass("hidden");
							}
						}

						// obra publica para Intermediario
						if (tipoObra == 2 && tipoPatron == "4") {
							$("#fechasEjecucion").text("Vigencia del contrato");

							if ($('#chkSinRegistro').is(":checked")) {
								$("#pnlDatosUbicacionObra").removeClass(
										"hidden");
								$("#pnlDomicilioFiscal").addClass("hidden");
								$("#pnlRegistroPatronal").addClass("hidden");
								$("#pnlRazonSocial").addClass("hidden");

								$("#pnlObjetoContrato").addClass("hidden");
								$("#pnlSuperficie").addClass("hidden");
								$("#pnlTipoObra").addClass("hidden");
								$("#numProcedimiento").addClass("hidden");
								$("#numAviso").addClass("hidden");
								$("#pnlObservaciones").addClass("hidden");
							} else {
								$("#pnlRFC").addClass("hidden");
								$("#pnlObjetoContrato").removeClass("hidden");
								$("#pnlSuperficie").addClass("hidden");
								$("#pnlTipoObra").addClass("hidden");
								$("#numProcedimiento").addClass("hidden");
								$("#numAviso").removeClass("hidden");
								$("#pnlObservaciones").removeClass("hidden");

							}
						}

						// Etiqueta de Registro de Obra o Registro de Aviso de
						// Obra
						if (!((tipoObra == 1 && tipoPatron == "1") || (tipoObra == 2 && tipoPatron == "2"))
								&& $("#chkSinRegistro").is(":checked")) {
							$("#lblTituloHeader").html("Registro de Aviso de Ubicaci&oacute;n de Obra");
							$("#lblIndicacionHeader").html("Complete los pasos para realizar el registro de un Aviso de Ubicaci&oacute;n de Obra.");
							$("#lblObligatorios").hide();
						}
						
						if(numAviso != "" && (numAviso.indexOf("C") == -1)){
							$("#txtNumAviso").val(numAviso);
							$("#txtNumAviso").attr("disabled", true);
						}
						
						
					}
					$("#btnTipoObra")
							.click(
									function() {

										if (tipoObra > 0) {	

											var url = "/sdroc_web/categoriasObras";

											$.ajax({
													type : "POST",
													contentType : "application/json",
													url : url,
													data : JSON.stringify(tipoObra),
													timeout : 100000,
													success : function(response) {
															var dataArray = response.resultado;
															var selectbox = $('#selTipoObra');
															var populateSelectBox = function(selectbox,dataArray) {
																dataArray.forEach(function(data) {
																			selectbox.append('<option value="'+ data.cveTipoObra+ '">'+ data.desTipoObra+ '</option>');
																		});
															};

															populateSelectBox(selectbox,dataArray);
														},
														error : function(response) {
															console.log("ERROR: " + response);

														}
													});

											var curStep = $(this).closest(".setup-content-default"), 
												curStepBtn = curStep.attr("id"), 
												nextStepWizard = $('ul.wizard-steps-extensive li a[href="#'+ curStepBtn + '"]').parent().next().children("a"), 
												curInputs = curStep.find("input[type='text'],input[type='url']"), 
												isValid = true;

											$(".form-group").removeClass("has-error");
											for (var i = 0; i < curInputs.length; i++) {
												if (!curInputs[i].validity.valid) {
													isValid = false;
													$(curInputs[i]).closest(".form-group").addClass("has-error");
												}
											}

											if (isValid) {
												
												crearDialogo("#tipoObraModal", {
													"Aceptar" : function() {$(this).dialog("close")}
												}, "Confirmaci&oacute;n", "340px");
												$("#idMensajeModal").removeClass("hidden");
												
												
												if (tipoObra == 1) {
													$("#pnlPropietario").removeClass("hidden");
													$("#pnlClaseObra").removeClass("hidden");
													$("#pageResumenDatosObra").removeClass("hidden");

													$("#lblClaseObra").text('Privada');

												} else if (tipoObra == 2) {
													$("#pnlPropietario").addClass("hidden");
													$("#pnlClaseObra").removeClass("hidden");
													$("#pageResumenDatosObra").removeClass("hidden");

													$("#lblClaseObra").text('Publica');
												}

												var stepLi = curStepBtn + '0';

												$("#" + stepLi).addClass("completed")
												nextStepWizard.removeAttr('disabled').trigger('click');
												$("#" + stepLi).children('a').remove();
												
											}

										} else {
											$("#msgObra").removeClass("hidden");
										}

									});

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

function mostrarMensajeErrorDomicilio(codigoPostal) {
	var url = "/sdroc_web/obtenerSubDelegacionPorCodigoPostal/" + codigoPostal;
	$.blockUI();
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : url,
		cache : false,
		success : function(response) {
			$.unblockUI();
			$('#desSubdelegacionText').text(
					$('#idDesSubDelegacion').val().toLowerCase());
			$('#listaSubdelegacionesText').text(response);
			crearDialogo("#errorCodigoPostalPatron", {
				"Aceptar" : function() {
					$(this).dialog("close");
					parent.WizardRegistroObraCtrl.cerrar();
				}
			}, "Error");
		}
	});

}

function accionIncidencias(sel) {
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
		sensaObra(cveObra);
	}

	var accion = null;
	var numRegObra = null;
	var idaction = null;

	accion = $selectAtual.attr("value");
	idaction = obtenerAccion(accion);

	if (idaction > 0 && accion != null) {
		if (idaction == 4
				&& $selectAtual.parent().parent().parent().find('td input')
						.val() >= 3) {
			crearDialogo("#msgsEscritorio", {
				"Aceptar" : function() {
					$(this).dialog("close")
				}
			}, "Actualizaci&oacute;n", "340px");
			$('#msgsEscritorio').removeClass('hidden');
			$selectAtual.val("");
			return;
		} else {
			var tds = $selectAtual.parent().parent().parent().find('td');
			if (idaction == 4 && $(tds[1]).find('label').text() > 0) {
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
	var boolean;
	var accion = "/sdroc_web/validaRegistroObra";

	$.blockUI();
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : accion,
		async : false,
		data : numeroRegistroObra,
		cache : false,
		success : function(response) {
			$.unblockUI();
			boolean = response;
		}
	});
	return boolean;
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
	var tipoPersonaSem = 0;
	
	if(cveRfcSemaforo.length == 13){
		// fisicas
		tipoPersonaSem = 1;
	}else{
		tipoPersonaSem = 2;
		// morales
	}
	
	$.postJSON("/gestionCobranza-web/wizard/cartaNoAdeudo/getEstatus",{'rfc':cveRfcSemaforo,tipoPersona:{idTipoPersona:tipoPersonaSem}},
			function(data){
		
		var protocol = window.location.protocol;
		var host = window.location.host;
		var image = "";
		var evaluation = "";
		
		if(data.codigoRespuesta == 1){
			
			image = "green";			
			evaluation = "POSITIVO";
			
		}else if(data.codigoRespuesta == 2 || data.codigoRespuesta == 3 || data.codigoRespuesta == 4 || data.codigoRespuesta == 6){
			image= "red";
			evaluation = "NEGATIVO";
		}else {
			image ="yellow";
			evaluation = "SIN OPINIÃ“N";
		}
		
		$('#imgSemaforo').attr('src',protocol+'//'+host+'/sdroc_web/static/images/' + image + '.png');
		$('#lblSemaforo').text(evaluation); 
		
			});
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

function construyeDireccion(ubicacionObj) {
	var direccion = '';
	var colonia = '';

	if (ubicacionObj.vialidadPrimaria == undefined) { // ubicacionDTO
		colonia = ubicacionObj.refColonia;
		direccion = ubicacionObj.calle
				+ ' Ext.'
				+ ((ubicacionObj.numExterior == undefined
						|| ubicacionObj.numExterior == null || ubicacionObj.numExterior == 'null') ? ''
						: ' ' + ubicacionObj.numExterior)
				+ ((ubicacionObj.numExteriorAlf == undefined
						|| ubicacionObj.numExteriorAlf == null || ubicacionObj.numExteriorAlf == 'null') ? ((ubicacionObj.numExterior == undefined
						|| ubicacionObj.numExterior == null || ubicacionObj.numExterior == 'null') ? ' SN'
						: '')
						: ' ' + ubicacionObj.numExteriorAlf)
				+ ' Int.'
				+ ((ubicacionObj.numInterior == undefined
						|| ubicacionObj.numInterior == null
						|| ubicacionObj.numInterior == 'null' || ubicacionObj.numInterior == '0') ? ''
						: ' ' + ubicacionObj.numInterior)
				+ ((ubicacionObj.numInteriorAlf == undefined
						|| ubicacionObj.numInteriorAlf == null || ubicacionObj.numInteriorAlf == 'null') ? ((ubicacionObj.numInterior == undefined
						|| ubicacionObj.numInterior == null
						|| ubicacionObj.numInterior == 'null' || ubicacionObj.numInterior == '0') ? ' SN'
						: '')
						: ' ' + ubicacionObj.numInteriorAlf) + ', '
				+ colonia.toUpperCase() + ', ' + ubicacionObj.refMunicipio
				+ ', ' + ubicacionObj.refEntidad + ',  CP '
				+ ubicacionObj.codigoPostal;
	} else { // json
		direccion = ubicacionObj.vialidadPrimaria.nombre
				+ ' Ext.'
				+ ((ubicacionObj.numExterior1 == undefined
						|| ubicacionObj.numExterior1 == null || ubicacionObj.numExterior1 == 'null') ? ''
						: ' ' + ubicacionObj.numExterior1)
				+ ((ubicacionObj.numExteriorAlf == undefined
						|| ubicacionObj.numExteriorAlf == null || ubicacionObj.numExteriorAlf == 'null') ? ((ubicacionObj.numExterior1 == undefined
						|| ubicacionObj.numExterior1 == null || ubicacionObj.numExterior1 == 'null') ? ' SN'
						: '')
						: ' ' + ubicacionObj.numExteriorAlf)
				+ ' Int.'
				+ ((ubicacionObj.numInterior == undefined
						|| ubicacionObj.numInterior == null
						|| ubicacionObj.numInterior == 'null' || ubicacionObj.numInterior == '0') ? ''
						: ' ' + ubicacionObj.numInterior)
				+ ((ubicacionObj.numInteriorAlf == undefined
						|| ubicacionObj.numInteriorAlf == null || ubicacionObj.numInteriorAlf == 'null') ? ((ubicacionObj.numInterior == undefined
						|| ubicacionObj.numInterior == null
						|| ubicacionObj.numInterior == 'null' || ubicacionObj.numInterior == '0') ? ' SN'
						: '')
						: ' ' + ubicacionObj.numInteriorAlf)
				+ ', '
				+ ubicacionObj.asentamiento.tipoAsentamiento.descripcion
						.toUpperCase()
				+ ' '
				+ ubicacionObj.asentamiento.nombre.toUpperCase()
				+ ', '
				+ ubicacionObj.asentamiento.localidad.municipio.nombre
						.toUpperCase()
				+ ', '
				+ ubicacionObj.asentamiento.localidad.municipio.entidadFederativa.nombre
				+ ', CP ' + ubicacionObj.codigoPostal.codigoPostal;
	}
	return direccion;
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