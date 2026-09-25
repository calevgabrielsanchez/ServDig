$(document).ready(
				
				function() {
					$(".cuerpo_seccion").hide();
					$(".separadorseccion").click(function() {
						$(this).next(".cuerpo_seccion").slideToggle(60);
					});
					$("#cuerpo_datosgenerales").show();
					var $dialogAutorizaRatificacion = $('<div></div>')
							.html('\u00BFEst\u00E1 seguro de autorizar la ratificaci\u00F3n?')
							.dialog(
									{
										autoOpen : false,
										title : 'Autorizar Ratificaci\u00F3n',
										resizable : false,
										height : 140,
										modal : true,
										resizable: false,
										autoOpen : false,
										buttons : {
											"Aceptar" : function() {
												$.blockUI();
												x = 1;
												var idForm = "#autorizaRatificacionForm";
												$(idForm).submit();
												$(this).dialog("close");
											},
											"Regresar" : function() {
												$(this).dialog("close");
											}
										}
									});

					$('#Ratificar').click(function() {
						ratificar();
						return false;
					});

					$('#AutorizarRatificacion').click(function() {
						$dialogAutorizaRatificacion.dialog('open');
						return false;
					});

					$('#Rectificar').click(function() {
						var idForm = "#rectificaForm";
						$.blockUI();
						$(idForm).submit();
					});

					$('#capturaClem').click(function() {
						var idForm = "#datosClemForm";
						$.blockUI();
						$(idForm).submit();
					});

					$('#RechazarRatificacion').click(function() {
						ejecutarRatificacion();
						return false;
					});

					$('#RechazarRectificacion').click(function() {
						ejecutarRectificacion('Rectificacion');
						return false;
					});
/*
					$('#VerClem').click(function() {
						var idForm = "#verClemForm";
						$.blockUI();
						$(idForm).submit();
					});
*/
					$('#actualizarClem').click(function() {
						var idForm = "#modificarClemForm";
						$.blockUI();
						$(idForm).submit();
					});

					$('#modificarAutorizacion1').click(function() {
						modificarAutorizacion();
						return false;
					});

					$('#modificarAutorizacion2').click(function() {
						modificarAutorizacion();
						return false;
					});

					$('#Regresar').click(function() {
						$.blockUI();
						var idForm = "#regresaForm";
						$(idForm).submit();
					});

				});

/* Funcion para obtener la descripcion de la fraccion seleccionada */
function rechazarAnalisis() {
	$.blockUI();

	/* Obtenemos los valores */
	var comentarios = $("textarea#comentarios").val();

	/* Asignamos los valores a la forma pivote */
	var idForm = "form#rechazarAnalisisForm";

	/* Comentario */
	$(idForm + " input#comentario").val(comentarios);
	/* Clasificacion */

	/* Hacemos submit de la forma */
	$(idForm).submit();

};

function ejecutarRatificacion() {
	var idForm = "";
	idForm = "#rechazarForm1";
	// Dialog
	$("#dialog-form-1")
			.dialog(
					{
						autoOpen : false,
						height : 230,
						width : 500,
						modal : true,
						resizable: false,
						buttons : {
							"Aceptar" : function() {

								var comentarios = $(
										idForm + ' textarea#comentarios').val();
								if (comentarios == "") {
									alert("El campo comentario no puede ser una cadena vacia.");
								} else {
									$.blockUI();
									$(idForm).submit();
									$(this).dialog("close");
								}
							},
							"Cancelar" : function() {
								$(idForm + ' textarea#comentarios').val('');
								$(this).dialog("close");
							}
						},
						close : function() {
						}
					});

	document.getElementById("dialog-form-1").style.visibility = "visible";
	$("#dialog-form-1").dialog("open");
}

function ejecutarRectificacion() {
	var idForm = "";
	idForm = "#rechazarForm2";

	// Dialog
	$("#dialog-form-2")
			.dialog(
					{
						autoOpen : false,
						height : 230,
						width : 500,
						modal : true,
						resizable: false,
						buttons : {
							"Aceptar" : function() {
								//$.blockUI();
								var comentarios = $(idForm + ' textarea#comentarios').val();
								if (comentarios == "") {
									alert("El campo comentario no puede ser una cadena vacia.");
								} else {
									$.blockUI();
									$(idForm).submit();
									$(this).dialog("close");
								}
							},
							"Cancelar" : function() {
								$(idForm + ' textarea#comentarios').val('');
								$(this).dialog("close");
							}
						},
						close : function() {
						}
					});

	document.getElementById("dialog-form-2").style.visibility = "visible";

	$("#dialog-form-2").dialog("open");
}

function ratificar() {
	
	var idForm = "";
	idForm = "#ratificaForm"; 
	
	// Dialog
	$("#dialog-form-3")
			.dialog(
					{
						autoOpen : false,
						height : 230,
						width : 500,
						modal : true,
						resizable: false,
						buttons : {
							"Aceptar" : function() {
								
								var comentarios = $(idForm + ' textarea#comentarios').val();
								if (comentarios == "") {
									alert("El campo comentario no puede ser una cadena vacia.");
								} else {
									$.blockUI();
									$(idForm).submit();
									$(this).dialog("close");
								}

							},
							"Cancelar" : function() {
								$(idForm + ' textarea#comentarios').val('');
								$(this).dialog("close");
							}
						},
						close : function() {
						}
					});

	document.getElementById("dialog-form-3").style.visibility = "visible";
	$("#dialog-form-3").dialog("open");
}

function modificarAutorizacion() {
	var idForm = "#modificacionForm";

	// Dialog
	$("#dialog-form-modificacion")
			.dialog(
					{
						autoOpen : false,
						height : 230,
						width : 500,
						modal : true,
						resizable: false,
						buttons : {
							"Aceptar" : function() {

								var comentarios = $(idForm + ' textarea#comentarios').val();
								if (comentarios == "") {
									alert("El campo comentario no puede ser una cadena vacia.");
								} else {
									$.blockUI();
									$(idForm).submit();
									$(this).dialog("close");
								}
							},
							"Cancelar" : function() {
								$(idForm + ' textarea#comentarios').val('');
								$(this).dialog("close");
							}
						},
						close : function() {
						}
					});

	document.getElementById("dialog-form-modificacion").style.visibility = "visible";

	$("#dialog-form-modificacion").dialog("open");
}

String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g, '')
}