/**
 * 
 */
$(document).ready(function(){
	
	$("#serviciosPersonalSi").on("click",radioServiciosPersonal);
	$("#serviciosPersonalNo").on("click",radioServiciosPersonal);
    $("#atrasGiroEmpresa").on("click",function(){
        paginaPrevia();
    });
	$("#siguienteGiroEmpresa").on("click",siguienteGiroEmpresa);
	
	$("#fecEfecto").datepicker({
		changeMonth : true,
		changeYear : true,
		minDate : -364,
		maxDate : 0,
		onClose : function(dateText, inst) {
			
		}

	});

    initReglaValidacionNumCentrosTraba();
    initReglaValidacionPrestaServicioPersonal();
    initReglaValidacionClasificacionElegida();
	initValidateGiroEmpresa();
    initValidateClasificElegida();

    $("#contenedorClasificador").clasificador({});
});


var siguienteGiroEmpresa = function() {

	var camposValidos = $("#formFechas").valid();
	mostrarMensajeErrorGenerico('formFechas', !camposValidos, MENSAJE_ERROR_FORM);
    var clasifValida = $("#formClasificacionElegida").valid();
	var continuar = (camposValidos && clasifValida);
	setColorAsteriscoPage(!continuar);

	if(continuar) {
        var clasificSeleccionado = $("#contenedorClasificador").clasificador("get");

		var clasificacion = new Object();

		clasificacion.fraccion = new Object();
		clasificacion.fraccion.id = clasificSeleccionado.id.cveFraccion;
		clasificacion.fraccion.grupo = new Object();
		clasificacion.fraccion.grupo.id = clasificSeleccionado.id.cveGrupo;
		clasificacion.fraccion.grupo.division = new Object();
		clasificacion.fraccion.grupo.division.id = clasificSeleccionado.id.cveDivision;

		clasificacion.sujetoObligado = new Object();
		clasificacion.sujetoObligado.tipoPersonaFiscal = "MORAL";
		clasificacion.sujetoObligado.moral = new Object();

		$.ajax({
			url : getContext()+ '/clasificacion/obtenerClasificacion?idTipoTramite=1',
			async: true,
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify( clasificacion),
			beforeSend : function() {
				$.blockUI();
	        },
			success: function(clasifComplem) {
				solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.clasificacion = clasifComplem.clasificacion;
				
				var formFechas = $("form#formFechas").toObject();

				clasificacion = solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.clasificacion;

				clasificacion.fecPresentacion = formFechas.fecPresentacion;
				clasificacion.fecEfecto = formFechas.fecEfecto;
				clasificacion.indPrestaServicioPersonal = formFechas.indPrestaServicioPersonal;

				if(formFechas.numCentrosTraba != undefined){
					clasificacion.numCentrosTraba = formFechas.numCentrosTraba
				}

				$.ajax({
					url : getContext()+ '/alta/patron/actualizar/clasificacion',
					async: true,
					type: 'POST',
					contentType: 'application/json',
					data: JSON.stringify( solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.clasificacion ),
					success: function(data) {
						solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.clasificacion = data;
						solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.modalidad = data.sujetoObligado.modalidad;
						
						$.ajax({
							url : getContext()+ '/alta/patron/valida/clasificacion',
							async: true,
							type: 'POST',
							contentType: 'application/json',
							data: JSON.stringify( solicitudPrincipal.tramiteSujetoObligado.sujetoObligado ),
							success: function(data) {
								if(data.mensajeError != undefined){
									mostrarMensajeErrorGiroEmpresa(true,data.mensajeError);
								}else{
									paginaSiguiente();
								}
								 $.unblockUI();
							}
						});
					}
				});
			}
		});
	}
	
}

function initReglaValidacionNumCentrosTraba() {
    $.validator.addMethod("validaNumCentrosTraba", function(value, elem, param) {
        if ($("#serviciosPersonalNo:checked").length == 1) {
            $("#numCentrosTraba").rules("remove");
            return true;
        } else if ($("#serviciosPersonalSi:checked").length == 1) {
            $("#numCentrosTraba").rules("remove");
            $("#numCentrosTraba").rules("add", {
                required: true,
                number: true
            });
            return $("#numCentrosTraba").valid();
        } else {
            return true;
        }
    },"");
}

function initReglaValidacionPrestaServicioPersonal() {
    $.validator.addMethod("validaPrestaServicioPersonal", function(value, elem, param) {
        if ($("input[name=indPrestaServicioPersonal]:checked").length > 0) {
            return true;
        } else {
            return false;
        }
    },"Campo requerido.");
}

function initReglaValidacionClasificacionElegida() {
    $.validator.addMethod("validaClasificacionElegida", function(value, elem, param) {
        if ($("#contenedorClasificador").clasificador("get") != null) {
            return true;
        } else {
            return false;
        }
    },"Campo requerido.");
}
/**
 * funcion para iniciar el validador del formulario
 */
var initValidateGiroEmpresa = function() {
	
	$("#formFechas").validate($.extend({},DEFAULTS_VALIDATE,{
		verifyErrors: function(existError) {
			marcarAsteriscos($("#formFechas"),".errorDocs","div");
		},
		rules: {
			fecEfecto: {
				required: true,
				fecha: true
			},
            prestaServicioPersonal: {
                validaPrestaServicioPersonal : true
            },
            validadoNumCentrosTraba: {
                validaNumCentrosTraba : true
			}
		},
        ignore: "",//esta propiedad se sobre escribe para que puedas usar campos hidden
        errorElement: "span"
	}));
}

var initValidateClasificElegida = function() {
    $("#formClasificacionElegida").validate($.extend({},DEFAULTS_VALIDATE,{
        verifyErrors: function(existError) {
            marcarAsteriscos($("#formClasificacionElegida"),".errorDocs","form");
        },
        rules: {
            clasificacionElegida: {
                validaClasificacionElegida : true
            }
        },
        ignore: "",//esta propiedad se sobre escribe para que puedas usar campos hidden
        errorElement: "span"
    }));
}

/**
 * funcion que se ejecuta cuando se elige alguna opcion de cuando 
 * presta o no servicios de personal
 */
var radioServiciosPersonal = function() {
	//verificamos si marco la opcion de si
	if(this.value == 1) {
		mostrarMensajePrestaServiciosPersonas();
	} else {
		mostrarCentrosTrabajo(false);
	}
}

/**
 * Funcion que muestra un mensaje si elige la opcion de que si presta servicios de persona
 */
var mostrarMensajePrestaServiciosPersonas = function(){
	var textoMensajePSP="La marca en 'SI', aplica para prestadoras de servicios especializados o de ejecuci\u00F3n de obras especializadas, en t\u00E9rminos de lo establecido en el artículo 13 de la Ley Federal del Trabajo.";
	
	var buttons = {
		"Aceptar" : function() {
			$(this).dialog("close");
			mostrarCentrosTrabajo(true);
		},
		"Cancelar" : function() {
			$(this).dialog("close");
			mostrarCentrosTrabajo(false);
		}
	};
	
	crearDialogo(textoMensajePSP, buttons, "Confirmaci&oacute;n","450px");
	
}

var mostrarCentrosTrabajo = function(mostrar) {
	
	if(mostrar) {
		$("#divCentrosTrabajo").show();
		$("#serviciosPersonalSi").attr("checked","true");
	} else {
		$("#divCentrosTrabajo").hide();
		$("#serviciosPersonalNo").attr("checked","true");
		$("#numeroCentrosTrabajo").val("");
	}
}

var mostrarMensajeErrorGiroEmpresa = function(mostrar,mensaje) {
	if(mostrar) {
		$("#errorFormGiroEmpresa").html(mensaje).show();
		$(window).scrollTop(0);
	} else {
		$("#errorFormGiroEmpresa").html("").hide();
	}
}