
var bovedaCtrl = {
	documentosAdjuntos: [],
	documentosTramite: [],
	documentosTramiteCapturados: [],
	init: function() {
		bovedaCtrl.getDocumentosTramite();
		bovedaCtrl.initFormulario();
	}, 
	initFormulario: function(){
		//Solo si el tipo de componente es 1 inicializaremos el formulario, ya que si es 2, significa que no es necesario
		//presentar el formulario para adjuntar el archivo ni validar ningun campo
		if($("#tipoComponente").val() ==  1) {
			var $formulario = $("#adjuntarDoctoForm"), $campoDocumento = $formulario.find("#documento"),
			$botonAdjuntar = $formulario.find("#adjuntarDocumento"), $barraProgreso = $formulario.find("#progressbar");
			//iniciamos el validador del formulario
			bovedaCtrl.initValidator($formulario);
			//modificamos el estilo del boton de tipo file
			$campoDocumento.filestyle({htmlIcon: '<span class="glyphicon glyphicon-search" aria-hidden="true"></span>', text: 'Buscar'});
			$botonAdjuntar.on("click", bovedaCtrl.uploadDocument)
			//iniciamos la barra de progreso que muestra el avance al subir el archivo
			$barraProgreso.progressbar({
				value: 0,      
				change: function() {
					$( ".progress-label" ).text( $("#progressbar").progressbar( "value" ) + "%" );
				},
				complete: function() {
					$( ".progress-label" ).text( "Guardando en boveda..." );
				}
			});
		} 
	},
	initValidator: function($formulario) {
		//anadimos al validador el metodo para verificar que un archivo no pese mas de 2 megas
		$.validator.addMethod('validar2Mb', function (value, element) {
			var tamanioMaximo = 1000 * 2048;
		    return this.optional(element) || (element.files[0].size <= tamanioMaximo)
		}, 'El archivo debe tener un tama&ntilde;o maximo de 2 MB.');
		//anadimos al validador el metodo para verificar que no se agregue el mismo documento mas de una vez
		//ya que el componente de boveda valida los mismo, asi nos evitamos una peticion innecesaria
		$.validator.addMethod('validarNombre', function (value, element) {
			var nameFile = element.files[0].name, existeArchivo = false;
			if(bovedaCtrl.documentosAdjuntos.length) {
				for(var i=0; i < bovedaCtrl.documentosAdjuntos.length; i++) {
					//console.log("el nombre del archivo a comparar es " + bovedaCtrl.documentosAdjuntos[i].nomNombreDocumento)
					if(bovedaCtrl.documentosAdjuntos[i].nomNombreDocumento == nameFile) {
						existeArchivo = true;
						break;
					}
				}
			}
		    return this.optional(element) || !existeArchivo
		}, 'Ya existe un archivo con el mismo nombre.');
		
		//iniciamos el validador del formulario
		$formulario.validate({
			errorClass: "errorDocs",
			errorElement: "span",
			rules:{
				tipoDocumento: {
					required: true
				},
				documento:{
					required: true,
					accept: "pdf", 
					validar2Mb: true,
					validarNombre: true
				}
			},
			messages: { documento: {accept: "El tipo de archivo debe ser PDF."}}
		});
	},
	validar: function() {
		var terminado = true, doctos = bovedaCtrl.documentosTramite;
		//verificamos si existe al menos un documento en la lista de tipos requeridos 
			if(doctos.length) {
			//empezamos a recorrer el array de documentos
				for(var i=0; i < doctos.length; i++) {
				//si al menos en la lista hay uno que sea obligatorio
					if(doctos[i].refCapturaDocumentoObligatorio == 1){
						//la captura no esta terminada
						terminado = false;
						break;
					}
				}
			}
		//si existe al menos un documento obligatorio sin capturar mostraremos un mensaje
		$("#errorDoctosNecesarios")[terminado?"hide":"show"]()
		return terminado;
	},
	marcarComoOpcional: function(idDocumento, opcional) {
		//obtememos el elemento que se marcara como opcional u obligatorio
		var elementoArray = bovedaCtrl.getFromArray("documentosTramite", "cveIdDoctoReqTramite", idDocumento);
		//si no esta en la lista de tipos de documentos por adjuntar, lo buscamos en los tipos ya adjuntados
		if(elementoArray.elemento == null) {
			elementoArray = bovedaCtrl.getFromArray("documentosTramiteCapturados", "cveIdDoctoReqTramite", idDocumento);
		}
		if(elementoArray.elemento != null) {
			//una vez que localizamos el elemento lo marcamos
			elementoArray.elemento.refCapturaDocumentoObligatorio = opcional ? 0 : 1
			//refrescamos el formulario para adjuntar documentos
			bovedaCtrl.refrescarListado();
		}
	},
	getDocumentosTramite: function() {
		//si no se especifica un tipo de tramite de tomara el 25
		var idTipoTramite = $("#idTipoTramite").val() != "" ? $("#idTipoTramite").val() : 25;
		$.blockUI();
		$.ajax({
			url: "/gestionDocumentoProbatorio-web/boveda/getDocumentos",
			type: "POST",
			contentType: 'application/json',
	        dataType: 'JSON',
			data : JSON.stringify({tramiteId: $("#idTramiteBoveda").val(),tipoTramite: {idTipoTramite: idTipoTramite}}),
			success: function(result) {
				//procesamos los documentos que nos regrese la llamada
				bovedaCtrl.documentosAdjuntos = result.doctosCapturados;
				bovedaCtrl.documentosTramite = result.doctosRequeridos;
				bovedaCtrl.procesarDocumentos();
				$.unblockUI();
			}
		});
	},
	procesarDocumentos: function(){
		var documentoAdjuntos = bovedaCtrl.documentosAdjuntos, tipoComponente = $("#tipoComponente").val();
		if(documentoAdjuntos && documentoAdjuntos.length) {
			for(var i= 0; i < documentoAdjuntos.length; i++) {
				var tipoCapturado = bovedaCtrl.eliminarDeArray("documentosTramite", "documentoPorTipo.idDocumentoPorTipo", documentoAdjuntos[i].documentoPorTipo.idDocumentoPorTipo);
				if(tipoCapturado != null) {
					bovedaCtrl.documentosTramiteCapturados.push(tipoCapturado);
				}
			}
		}
		//si el tipo de componente es 1 refrescamos el listado de documentos, de lo contrario no tiene caso
		//ya que solo se mostraran los documentos adjuntos y resultantes
		if(tipoComponente == 1) {
			bovedaCtrl.refrescarListado();
		}
		bovedaCtrl.refrescarTabla();
	},
	refrescarListado : function() {
		var $divDoctos = $("#adjuntarDoctos"), 
		documentosTramite = bovedaCtrl.documentosTramite;
		
		if(documentosTramite != null && documentosTramite.length) {
		
			var options = "<option value=\"\">-- Selecciona por favor --</option>";
			for(var i=0; i < documentosTramite.length; i++) {
				options += "<option value=\""+documentosTramite[i].cveIdDoctoReqTramite+"\">";
				options += documentosTramite[i].documentoPorTipo.documento.desDocumento+" - ";
				options += documentosTramite[i].refCapturaDocumentoObligatorio == 1?"Obligatorio": "Opcional"+" </option>";
			}
			$("#tipoDocumento").html(options);
			$divDoctos.show();
		} else {
			$divDoctos.hide();
		}
	},
	uploadDocument : function() {
		$("#errorGuardado").hide();
		if($("#adjuntarDoctoForm").valid()) {
		
			$("#adjuntarDocumento").hide()
			$(".bootstrap-filestyle").hide();
			$("#progressbar").show();
			var file_data = $("#documento").prop("files")[0],
			form_data = new FormData(),
			elementoArray = bovedaCtrl.getFromArray("documentosTramite", "cveIdDoctoReqTramite", $("#tipoDocumento").val());
			
			documentoSeleccionado = elementoArray.elemento;
			form_data.append("documento", file_data);
			form_data.append("tipoDocumento",documentoSeleccionado.documentoPorTipo.documento.cveIdDocumento);
			form_data.append("desDocumento",documentoSeleccionado.documentoPorTipo.documento.desDocumento)
			form_data.append("idDocumentoPorTipo",documentoSeleccionado.documentoPorTipo.idDocumentoPorTipo);
			
			$("#tipoDocumento").attr("disabled", "disabled");
			$("#documento").attr("disabled", "disabled");
			
			$.ajax({
				xhr: bovedaCtrl.mostrarProceso,
				url: "/gestionDocumentoProbatorio-web/boveda/api",
				type: "POST",
				cache : false,
				contentType : false,
				processData : false,
				data : form_data,
				success: function(result) {
					$.blockUI();
					//console.log(result);
					if(result.resultado) {
						var doctoCapturado = result.documento;
						doctoCapturado.documentoPorTipo = documentoSeleccionado.documentoPorTipo;
						bovedaCtrl.marcarCapturado(doctoCapturado);
						bovedaCtrl.refrescarTabla();
					} else {
						//console.log("no fue posible adjuntar el documento");
						$("#errorGuardado").html("No fue posible almacenar el documento ("+result.mensaje+")")
						$("#errorGuardado").show();
					}
					
					bovedaCtrl.habilitarCaptura(true);
					$("#adjuntarDocumento").show()
					$(".bootstrap-filestyle").show();
					$(".bootstrap-filestyle").find(":text").val("");
					$("#progressbar").hide();
					$("#progressbar").progressbar({value: 0})
					$.unblockUI()
				}
			});
		} 
	},
	marcarCapturado: function(doctoCapturado) {
		//seteamos el documento en el array de documentos adjuntados
		bovedaCtrl.documentosAdjuntos.push(doctoCapturado);
		//Obtenemos el tipo de documento que del elemento adjuntado y lo eliminamos del array de documentos por adjuntar
		var tipoCapturado = bovedaCtrl.eliminarDeArray("documentosTramite", "documentoPorTipo.idDocumentoPorTipo", doctoCapturado.documentoPorTipo.idDocumentoPorTipo);
		//anadimos el tipo de documento a los tipos ya adjuntados
		bovedaCtrl.documentosTramiteCapturados.push(tipoCapturado);
		//refrescamos la lista de documentos requeridos
		bovedaCtrl.refrescarListado();
	},
	getFromArray: function(nombreArray, atributo, valor ) {
		//obtenemos el array en el que realizaremos la busqueda
		var arrayBusqueda = bovedaCtrl[nombreArray], elementoEncontrado = {
			index: null,
			elemento: null
		};
		//si existe el array y tiene al menos un elemento
		if(arrayBusqueda && arrayBusqueda.length) {
			for(var i=0; i < arrayBusqueda.length; i++) {
				//recorremos cada elemento y verificaremos si es el que tiene el valor buscado
				var elementoArray = arrayBusqueda[i],
				//en caso de que la propiedad no este en el primer nivel por ejemplo array[0].atributo1.atributodelatributo1
				atributos = atributo.split("."), variableFinal = elementoArray[atributos[0]];
				//vamos recorriendo el objeto a traves de todas las propiedades especificads
				for(var j=1; j < atributos.length; j++) {
					variableFinal = variableFinal[atributos[j]];
				}
				//cuando obtenemos el valor del ultimo atributo ahora si checamos contra el valos buscado
				if(variableFinal == valor) {
					//Seteamos el elemento que coincide y su indice para su posible eliminado
					elementoEncontrado.elemento = elementoArray;
					elementoEncontrado.index = i;
					break;
				}
			}
		}
		//retornamos la informacion encontrada
		return elementoEncontrado;
	},
	eliminarDeArray: function(nombreArray, propiedad, id) {
		var indiceDocto = null, documentos = bovedaCtrl[nombreArray], elementoEliminado=null, elementoEncontrado = null;
		
		elementoEncontrado = bovedaCtrl.getFromArray(nombreArray, propiedad, id)
		indiceDocto = elementoEncontrado.index;
		elementoEliminado = elementoEncontrado.elemento;
		
		if(indiceDocto != null) {
			bovedaCtrl[nombreArray].splice(indiceDocto,1);
		}
		
		return elementoEliminado;

	},
	habilitarCaptura: function(habilitar) {
		$("#tipoDocumento").removeAttr("disabled");
		$("#documento").removeAttr("disabled");
		$("#documento").val("");
	},
	eliminarDocumento : function(idDocumento) {
		var elementoArray = bovedaCtrl.getFromArray("documentosAdjuntos", "idDocumentoProbatorio",idDocumento);
		var doctoEliminado = elementoArray.elemento;
		
		$.ajax({
			beforeSend: $.blockUI,
			url: "/gestionDocumentoProbatorio-web/boveda/api",
			type: "DELETE",
			contentType: 'application/json',
	        dataType: 'JSON',
			data : JSON.stringify(doctoEliminado),
			success: function(result) {
				//console.log("termino de eliminar")
				doctoEliminado = bovedaCtrl.eliminarDeArray("documentosAdjuntos", "idDocumentoProbatorio",idDocumento);
				var elementoNoCapturado = bovedaCtrl.eliminarDeArray("documentosTramiteCapturados", "documentoPorTipo.idDocumentoPorTipo",doctoEliminado.documentoPorTipo.idDocumentoPorTipo);
				bovedaCtrl.documentosTramite.push(elementoNoCapturado);
				bovedaCtrl.refrescarListado();
				bovedaCtrl.refrescarTabla();
				
				$.unblockUI();
			}
		});
		
		
	},
	refrescarTabla: function() {

		var $divMostrar = $("#mostrarDoctos"), $divResultantes = $("#mostrarDoctosResultantes"),
		$areaDoctos = $("#documentosCapturados").find("tbody"), $areaDoctosResultantes = $("#documentosResultantes").find("tbody");
		//limpiamos el area de documentos adjuntos
		$areaDoctos.empty();
		//limpiamos el area de documentos adjuntos
		$areaDoctosResultantes.empty();
		
		//console.log("se han capturado " + bovedaCtrl.documentosAdjuntos.length + " documentos" )
		var documentos = bovedaCtrl.documentosAdjuntos;
		if(documentos.length) {
			$divMostrar.show();
			for(var i = 0; i < documentos.length; i++){
				//construimos la fila que insertaremos en el cuerpo de la tabla
				var $registroArchivo = $('<tr id=\"docto'+documentos[i].idDocumentoProbatorio+'\">'),
				$celdaTipo = $('<td>'+documentos[i].documentoPorTipo.documento.desDocumento+'</td>'),
				$celdaNombreDocto = $('<td>'+documentos[i].nomNombreDocumento+'</td>');
				$celdaOperaciones = $('<td>').append($('<button>').attr('type', 'button').attr("onclick","bovedaCtrl.verDocumento('"+documentos[i].bovedaDocId+"')")
        		.html(' Ver documento').addClass("btn btn-link btn-sm"));
				//Si el tipo de componente es 1(escritura y lectura) mostraremos el boton de eliminar documento
				if($("#tipoComponente").val() ==  1) {
					$celdaOperaciones.append(" ").append(
			        		$('<button>').attr('type', 'button')
			        		.attr("onclick","bovedaCtrl.eliminarDocumento('"+documentos[i].idDocumentoProbatorio+"')")
			        		.html('<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>Eliminar')
			        		.addClass("btn btn-danger btn-sm") 
			        )
				}
				//anadimos las celdas de nombre, tipo documento y accviones a la fila creada
				$registroArchivo.append($celdaTipo).append($celdaNombreDocto).append($celdaOperaciones);
				//verificamos a que tabla tenemos que anadir el registro recien creado, si a la documentos adjuntos o a la de documentos probatorios
				if(documentos[i].documentoPorTipo.tipoDocumentoProbatorio != null && documentos[i].documentoPorTipo.tipoDocumentoProbatorio.idTipoDocumentoProbatorio == 6) {
					$areaDoctosResultantes.append($registroArchivo);
				} else {
					$areaDoctos.append($registroArchivo);
				}
			} 
			//obtenemos el numero de documentos capturados por categoria
			var archivosAdjuntos = $areaDoctos.find("tr").length,
			archivosResultantes = $areaDoctosResultantes.find("tr").length;
			//si tenemos archivos resultantes mostramos la lista de documentos generados por el sistema
			$divResultantes[archivosResultantes?"show":"hide"]();
			//si existe algun documento adjunto mostramos la lista de documentos que el usuario adjunto
			$divMostrar[archivosAdjuntos?"show":"hide"]();
			
		} else {
			$divMostrar.hide();
			$divResultantes.hide();
		}
		
	},
	verDocumento: function(idDocumento) {
		$("#bovedaDocId").val(idDocumento);
		$("#formularioMostrar").submit();
	},
	mostrarProceso: function() {
		var xhr = new window.XMLHttpRequest();
		xhr.upload.addEventListener("progress", function(evt) {
			if (evt.lengthComputable) {
				//obtenemos el porcentaje subido
				var percentComplete = evt.loaded / evt.total;
				percentComplete = parseInt(percentComplete * 100);
				//ponemos el la barra de progreso el 
				$("#progressbar").progressbar({value: percentComplete});
			}
		}, false);

		return xhr;
	}
}

$(document).ready(bovedaCtrl.init);
