	/**
	 * indica el tab seleccionado
	 */
	activeTab = '##seguimientoCorreccionResumen';
	
	/**
	 * Indica la forma seleccionada
	 */
	FORMA_ACTUAL ='seguimientoCorreccionResumen';
	var rolUsuario;
/**
 * Controla el valor del hidden el cual indica
 * en que sección se encuentra el usuario.
 * Esta variable es utilizada por JAVA.
 */
function setToFormSeccionActual(){
	
	}




function redireccionaTipo(){
	
	alert("se redirecciona a un jsp");
}


/**
 * MEtodo para definir la navegacion de todas las pestañas de Seg de la correccion
 * @param data es un objeto de tipo de CrtRevRecepcion
 */
function habilitaNavegacion(data,numFolio){
	rolUsuario = $('form#seguimientoCorreccionForm #cveRolUsuario').val();

	var arrayFolio = numFolio.split("/");
	var tipoFolio = arrayFolio[1];
	
	if(data.nuDoctoSustento==1 && (data.nuComprobantePago==null || data.nuComprobantePago == 0) && (data.nuComproMovAfil==null || data.nuComproMovAfil == 0)){
		//alert("escenario1");
		//setTabHabilitado('seguimientoCorreccionCedRevision');
		$("form#formRecepcionSeguimiento #btnDatosRegularizacionSegCorr").prop('disabled','disabled');
		
		if(rolUsuario == AUDITOR){

			
		}else 	if(rolUsuario == JEFE_OF_CORRECCION || rolUsuario == SUPERVISOR_OF_CORRECCION || rolUsuario == JEFE_OF_CORRECCION_Y_DICTAMEN){

			$("form#formRecepcionSeguimiento #cbRecDocu").removeAttr('disabled');
			$("form#formRecepcionSeguimiento #cbRecPagoTra").removeAttr('disabled');
			$("form#formRecepcionSeguimiento #cbRecAvisoAfi").removeAttr('disabled');
			$("form#formRecepcionSeguimiento #taRecObservacion").removeAttr('disabled')
		
			$("form#formRecepcionSeguimiento #cbRecDocu").addClass("red");
			$("form#formRecepcionSeguimiento #cbRecPagoTra").addClass("red");
			$("form#formRecepcionSeguimiento #cbRecAvisoAfi").addClass("red");
			$("form#formRecepcionSeguimiento #taRecObservacion").addClass("red");
			
			if(tipoFolio =='CCE' || tipoFolio =='CCI'){
				$("form#formRecepcionSeguimiento #porcentajeAvanceSegCorr").removeAttr('disabled');
				$("form#formRecepcionSeguimiento #porcentajeAvanceSegCorr").addClass("red");
				$("form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr").removeAttr('disabled');
				$("form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr").addClass("red");
			}
			
		}
		
		
	}else
	
	if(data.nuDoctoSustento==1 && (data.nuComprobantePago == 1 || data.nuComproMovAfil==1) && (data.comprobanteConvenio == null || data.comprobanteConvenio ==0)){
		//alert("escenario2");
		//setTabHabilitado('seguimientoCorreccionCedRevision');
		$("form#formRecepcionSeguimiento #btnDatosRegularizacionSegCorr").removeAttr('disabled');
		if(rolUsuario == AUDITOR){

			
		}else 	if(rolUsuario == JEFE_OF_CORRECCION || rolUsuario == SUPERVISOR_OF_CORRECCION || rolUsuario == JEFE_OF_CORRECCION_Y_DICTAMEN){
			$("form#formRecepcionSeguimiento #cbRecDocu").removeAttr('disabled');
			$("form#formRecepcionSeguimiento #cbRecPagoTra").removeAttr('disabled');
			$("form#formRecepcionSeguimiento #cbRecAvisoAfi").removeAttr('disabled');
			$("form#formRecepcionSeguimiento #taRecObservacion").removeAttr('disabled')
		
			$("form#formRecepcionSeguimiento #cbRecDocu").addClass("red");
			$("form#formRecepcionSeguimiento #cbRecPagoTra").addClass("red");
			$("form#formRecepcionSeguimiento #cbRecAvisoAfi").addClass("red");
			$("form#formRecepcionSeguimiento #taRecObservacion").addClass("red");
			
			
			if(data.nuComprobantePago == 1){
				//$("form#formRecepcionSeguimiento #cbRecPagoTra").attr('checked', true);
				$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").removeAttr('disabled');
				$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").addClass("red");
				$('form#formRecepcionSeguimiento #cbxComprobConvenio').removeAttr('disabled');
				$("form#formRecepcionSeguimiento #cbxComprobConvenio").addClass("red");
			}else{
				//$("form#formRecepcionSeguimiento #cbRecPagoTra").attr('checked', false);
				$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").prop("disabled", "disabled");
				$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").removeClass("red");
				$('form#formRecepcionSeguimiento #cbxComprobConvenio').prop("disabled", "disabled");
				$("form#formRecepcionSeguimiento #cbxComprobConvenio").removeClass("red");
			}
			
			if(data.nuComproMovAfil == 1){
				$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").removeAttr('disabled');
				$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").addClass("red");
									
				$("form#formRecepcionSeguimiento #trabOmisosUniSegCorr").removeAttr('disabled');
				$("form#formRecepcionSeguimiento #trabOmisosUniSegCorr").addClass("red");
									
				$("form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr").removeAttr('disabled');
				$("form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr").addClass("red");
			}else{
				$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").prop("disabled", "disabled");
				$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").removeClass("red");
									
				$("form#formRecepcionSeguimiento #trabOmisosUniSegCorr").prop("disabled", "disabled");
				$("form#formRecepcionSeguimiento #trabOmisosUniSegCorr").removeClass("red");
									
				$("form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr").prop("disabled", "disabled");
				$("form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr").removeClass("red");
			}
			
			if(tipoFolio =='CCE' || tipoFolio =='CCI'){
				$("form#formRecepcionSeguimiento #porcentajeAvanceSegCorr").removeAttr('disabled');
				$("form#formRecepcionSeguimiento #porcentajeAvanceSegCorr").addClass("red");
				$("form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr").removeAttr('disabled');
				$("form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr").addClass("red");
			}
		}
	}else
	if(data.nuDoctoSustento==1 && (data.nuComprobantePago == 1 || data.nuComproMovAfil==1) && data.comprobanteConvenio == 1){
		//alert("escenario3");
		//setTabHabilitado('seguimientoCorreccionCedRevision');
		$("form#formRecepcionSeguimiento #btnDatosRegularizacionSegCorr").removeAttr('disabled');
		if(rolUsuario == AUDITOR){

			
		}else 	if(rolUsuario == JEFE_OF_CORRECCION || rolUsuario == SUPERVISOR_OF_CORRECCION || rolUsuario == JEFE_OF_CORRECCION_Y_DICTAMEN){
			$("form#formRecepcionSeguimiento #cbRecDocu").removeAttr('disabled');
			$("form#formRecepcionSeguimiento #cbRecPagoTra").removeAttr('disabled');
			$("form#formRecepcionSeguimiento #cbRecAvisoAfi").removeAttr('disabled');
			$("form#formRecepcionSeguimiento #taRecObservacion").removeAttr('disabled')
		
			$("form#formRecepcionSeguimiento #cbRecDocu").addClass("red");
			$("form#formRecepcionSeguimiento #cbRecPagoTra").addClass("red");
			$("form#formRecepcionSeguimiento #cbRecAvisoAfi").addClass("red");
			$("form#formRecepcionSeguimiento #taRecObservacion").addClass("red");
			
			if(data.nuComprobantePago == 1){
				//$("form#formRecepcionSeguimiento #cbRecPagoTra").attr('checked', true);
				$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").removeAttr('disabled');
				$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").addClass("red");
				$('form#formRecepcionSeguimiento #cbxComprobConvenio').removeAttr('disabled');
				$("form#formRecepcionSeguimiento #cbxComprobConvenio").addClass("red");
			}else{
				//$("form#formRecepcionSeguimiento #cbRecPagoTra").attr('checked', false);
				$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").prop("disabled", "disabled");
				$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").removeClass("red");
				$('form#formRecepcionSeguimiento #cbxComprobConvenio').prop("disabled", "disabled");
				$("form#formRecepcionSeguimiento #cbxComprobConvenio").removeClass("red");
			}
			
			if(data.nuComproMovAfil == 1){
				$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").removeAttr('disabled');
				$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").addClass("red");
									
				$("form#formRecepcionSeguimiento #trabOmisosUniSegCorr").removeAttr('disabled');
				$("form#formRecepcionSeguimiento #trabOmisosUniSegCorr").addClass("red");
									
				$("form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr").removeAttr('disabled');
				$("form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr").addClass("red");
			}else{
				$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").prop("disabled", "disabled");
				$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").removeClass("red");
									
				$("form#formRecepcionSeguimiento #trabOmisosUniSegCorr").prop("disabled", "disabled");
				$("form#formRecepcionSeguimiento #trabOmisosUniSegCorr").removeClass("red");
									
				$("form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr").prop("disabled", "disabled");
				$("form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr").removeClass("red");
			}
			
			if(data.comprobanteConvenio == 1){
				//$("form#formRecepcionSeguimiento #cbxComprobConvenio").attr('checked', true);
			}else{
				//$("form#formRecepcionSeguimiento #cbxComprobConvenio").attr('checked', false);
			}
			
			if(tipoFolio =='CCE' || tipoFolio =='CCI'){
				$("form#formRecepcionSeguimiento #porcentajeAvanceSegCorr").removeAttr('disabled');
				$("form#formRecepcionSeguimiento #porcentajeAvanceSegCorr").addClass("red");
				$("form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr").removeAttr('disabled');
				$("form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr").addClass("red");
			}
		}
	}
	
}