function eliminaOpcionesSelect(selector) {
	$(selector).html('');
	$(selector).html("<option value='-1'>--Por favor seleccione--</option>");
}

function comboCtrlSimple(pUrl, pEntidad, pIdHtml, pValueSelected,
		pValidaVigencia, pCampoVigencia, pIdHtmlCorto,pValidaEntidades) {

	this.cargar = function() {
		
		$.ajax({
			url : pUrl,
			dataType : 'json',
			async : false,
			data : {
				clazEntityName : pEntidad,
				mostrarSoloActivos : pValidaVigencia,
				campovigencia : pCampoVigencia,
				mostrarSoloEntidades : pValidaEntidades
			},
			beforeSend : function() {
				/* Se checa si tiene la clase form-control, si es así
				 * se le cambia provisionalmente el estilo display para que
				 * la imagen aparezca a un lado y no abajo
				 */
				var isFormControl = $('#' + pIdHtmlCorto).hasClass('form-control');
				if (isFormControl) {
					$('#' + pIdHtmlCorto).css('display', 'inline-block');
				}
	           	$('img#' + pIdHtmlCorto + 'ImgCargando').show();
	        },
			success : function(objData) {
				var options = "<option value='-1' >--Por favor seleccione--</option>";

				if (objData != null)
					for ( var i = 0; i < objData.length; i++) {

						if (objData[i].id == pValueSelected) {
							options += "<option value='"
									+ objData[i].id
									+ "' selected='selected' >"
									+ objData[i].descripcion
									+ "</option>";
						} else {
							options += "<option value='"
									+ objData[i].id + "'>"
									+ objData[i].descripcion
									+ "</option>";
						}
					}
				// Agrega las opciones al control
				$(pIdHtml).html(options);
			},
			complete : function() {
				$('img#' + pIdHtmlCorto + 'ImgCargando').hide();
				var isFormControl = $('#' + pIdHtmlCorto).hasClass('form-control');
				if (isFormControl) {
					$('#' + pIdHtmlCorto).css('display', 'block');
				}
            }
		});
		
		try {
			$(pIdHtml).trigger("change");
		} catch (err) {
		}
	};
}

function comboCtrlDependiente(pUrl, pEntidad, pIdHtml, pEntidadPadre,
		pIdHtmlPadre, pValueSelected, pValueSelectedParent, pValidaVigencia,
		pCampoVigencia, pIdHtmlCorto) {

	this.cargardep = function() {
		
		var valorPadre = $(pIdHtmlPadre).val();

		if (valorPadre == -1) {
			eliminaOpcionesSelect(pIdHtml);
		} else {

			/*
			 * Se checa si el valor del combo padre cambió del original, en caso
			 * de ser así, el valor seleccionado del combo hijo se pasa a -1
			 * para evitar que se estén seleccionado opciones que coincidan con
			 * el valor original del combo hijo
			 */
			if (pValueSelectedParent != undefined
					&& pValueSelectedParent != ''
					&& pValueSelectedParent != valorPadre) {
				pValueSelected = -1;
			}

			$.ajax({
				url : pUrl,
				dataType : 'json',
				async : false,
				data : {
					clazEntityName : pEntidad,
					entityParentName : pEntidadPadre,
					valueParent : valorPadre,
					mostrarSoloActivos : pValidaVigencia,
					campoVigencia : pCampoVigencia
				},
				beforeSend : function() {
					/* Se checa si tiene la clase form-control, si es así­
					 * se le cambia provisionalmente el estilo display para que
					 * la imagen aparezca a un lado y no abajo
					 */
					var isFormControl = $('#' + pIdHtmlCorto).hasClass('form-control');
					if (isFormControl) {
						$('#' + pIdHtmlCorto).css('display', 'inline-block');
					}
		           	$('img#' + pIdHtmlCorto + 'ImgCargando').show();
		        },
				success : function(objData) {
					var options = "<option value='-1' >--Por favor seleccione--</option>";
					if (objData != null)
						for ( var i = 0; i < objData.length; i++) {

							if (objData[i].id == pValueSelected) {
								options += "<option value='"
										+ objData[i].id
										+ "' selected='selected' >"
										+ objData[i].descripcion
										+ "</option>";
							} else {
								options += "<option value='"
										+ objData[i].id + "'>"
										+ objData[i].descripcion
										+ "</option>";
							}
						}

					//Agrega las opciones al control
					$(pIdHtml).html(options);
				},
				complete : function() {
					$('img#' + pIdHtmlCorto + 'ImgCargando').hide();
					var isFormControl = $('#' + pIdHtmlCorto).hasClass('form-control');
					if (isFormControl) {
						$('#' + pIdHtmlCorto).css('display', 'block');
					}
	            }
			});
			
			try {
				$(pIdHtml).trigger('change');
			} catch (err) {
			}
		}
	};
}