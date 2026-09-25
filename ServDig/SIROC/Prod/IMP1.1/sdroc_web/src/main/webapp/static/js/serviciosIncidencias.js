/**
 * 
 */
var serviciosIncidencia = {
	init: function() {
		
	}, 
	obtenerAccion: function(accion) {
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
		} else if (accion.search('remplaz') != -1) {
			action = 7;
		}

		return action;
	},
	tipoIncidencia: {
		ACTUALIZACION : 4
	},
	contantes : {
		MAX_ACTUALIZACIONES : 3,
		VALOR_32D: 0
	},
	mostrarMensajeError: function(idDiv, titulo, idMensaje){
		crearDialogo(idDiv, {
			"Aceptar" : function() {
				$(this).dialog("close");
			}
		}, titulo, "340px");
		$(idMensaje).removeClass('hidden');
	},
	procesarTipoIncidencia: function(select) {
		console.log("Entro al nuevo procesar incidencia");
		var $selectAtual = $(select),
		cveObra = $selectAtual.find(":selected").attr("id"),
		accion =  $selectAtual.attr("value"),
		tipoIncidencia = serviciosIncidencia.obtenerAccion(accion),
		estadoObra = 0,
		numeroActualizaciones = null,
		evauacion32D = null,
		datosObra = null;
		
		console.log("La accion a realizar es " + accion + " " + tipoIncidencia)
		//Validamos si no selecciono la opcion por default en el select
		if(accion != null &&  tipoIncidencia > 0) {
			estadoObra = serviciosObraCtrl.validarObra(cveObra);
			
			if (estadoObra == '0') {
				serviciosIncidencia.mostrarMensajeError("#msgsValidacionObra","Estado registro obra",'#msgsValidacionObra');
				return;
			} else {
				serviciosObraCtrl.sensaObra(cveObra);
			}
			
			//Obtenemos los datos de la obra a tablas del datatable que lo contiene
			datosObra = dataTableObras.row($selectAtual.closest("tr")).data();
			numeroActualizaciones = datosObra.numActualiza;
			evauacion32D = datosObra.numEvaluacionD32;
			//Si se ejecuta una actualizacion
			if(tipoIncidencia == serviciosIncidencia.tipoIncidencia.ACTUALIZACION) {
				//Verificamos si no se han hecho 3 o mas actualizaciones y si es asi mandamos un mensaje de error
				if(numeroActualizaciones >= serviciosIncidencia.contantes.MAX_ACTUALIZACIONES) {
					//Mostramos el mensaje de error
					serviciosIncidencia.mostrarMensajeError("#msgsEscritorio","Actualizaci&oacute;n",'#msgsEscritorio');
					$selectAtual.val("");
					return;
				} else if (evauacion32D > serviciosIncidencia.contantes.VALOR_32D) {
					//Mostramos el mensaje de error
					serviciosIncidencia.mostrarMensajeError("#msgsBimestreIncumplido","Actualizaci&oacute;n",'#msgsBimestreIncumplido');
					$selectAtual.val("");
					return;
				}
			}
			
			$.blockUI();
			var $form = $("<form/>", {'action': ""+accion + cveObra + "/" + tipoIncidencia, 'method' : "GET"});
			$form.appendTo("body").submit();
		}
	}
}