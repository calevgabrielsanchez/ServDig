function ConsultaSolicitudController(module) {
    this.module = module;    
}

ConsultaSolicitudController.prototype.bandeja = function(){
//  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 1);
	this.module.controller.transition("bandejaSolicitudesUI");
  
};

ConsultaSolicitudController.prototype.cancelar = function(){  
//  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 5);
	this.module.controller.transition("cancelarUI");
};

ConsultaSolicitudController.prototype.agregarNssDocumento = function(){  
//	  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 12);
	this.module.controller.transition("agregarNss");
};

ConsultaSolicitudController.prototype.solicitarInformacion = function(){
	   $('#modal').modal();
	   var correo = "";
		  var grupoNss = this.module.controller.model.consultaSolicitud.gridsNSS;
		  for (var i = 0;i<grupoNss.length; i++){
			  if (grupoNss[i].data[0].informacionRENAPO !=null){
				  correo = grupoNss[i].data[0].informacionRENAPO.correoElectronico;
				  break;
			  }  
		  }
	  
	  if (correo == null || correo == ""){
		  $("#modal").modal('hide');
		  $('div.modal-backdrop.fade').remove();
		  $('#modalInfoCorreo').modal();
	  }else{
		  $("#modal").modal('hide');
		  $('div.modal-backdrop.fade').remove();
//		  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 7);
		  this.module.controller.transition("solicitarInformacionUI");
	  }
};

ConsultaSolicitudController.prototype.checkMotivoAclaracion = function(){
  // Si esta seleccionado algun motivo de aclaración se habilita el boton de iniciar.
  var panel = this.module.render.getComponentById("panelTipoRegularizacion");  
  var disabled = true;
  var i;
  for( i=0; i< panel.components.length; i++){
    if( $("#" + this.module.render.replaceAll( panel.components[i].field, ".", "_" ) ).prop('checked') ){
      disabled = false;
      break;
    }    
  }
  $("#btnIniciar").prop('disabled',disabled);    
};

ConsultaSolicitudController.prototype.postFetchCorreccionDatos = function( modelName ){
  
  if( modelName === "correccionDatosLectura"){    
    this.module.service.confirmarCorreccionDatos.fetch( this.module, this.model.correccionDatosLectura, "confirmarCorreccionDatosLectura");
    this.module.controller.transition("confirmarCorreccionDatosLectura"); 
  }else{  
    
    this.module.controller.transition(modelName);
  }
};

ConsultaSolicitudController.prototype.iniciar = function(){  
  this.module.service.correccionDatosService.fetch( this.module, "correccionDatos" );   
};

ConsultaSolicitudController.prototype.consultaPrevia = function(){
  this.module.service.correccionDatosService.fetch( this.module, "correccionDatosConsultaPrevia" );  
};

ConsultaSolicitudController.prototype.consultarCambios = function(){
  this.module.service.correccionDatosService.fetch( this.module, "correccionDatosLectura" );  
};

ConsultaSolicitudController.prototype.generar = function(){
	this.model.generar = { folio: this.module.controller.model.consultaSolicitud.folio};
	  
	  this.model.generar.idTramite = this.module.controller.model.consultaSolicitud.idTramite;
	  this.model.generar.idSolicitud = this.module.controller.model.consultaSolicitud.idSolicitud;
	  this.model.generar.idTarea = this.module.controller.model.consultaSolicitud.idTarea;
	  this.model.generar.responsable = this.module.controller.model.consultaSolicitud.responsable;
	  this.model.generar.nss = this.module.controller.model.consultaSolicitud.nss;
	  this.model.generar.folio = this.module.controller.model.consultaSolicitud.folio;
      this.model.generar.tareasTramites = obtenerTareasTramites(this.module.controller.model.consultaSolicitud.folio);
	  this.model.generar.informacionRENAPO = obtenerInfoRenapo();
	  
	  
	  if ($("#seguimientoSolicitud").val() == null || typeof $("#seguimientoSolicitud").val() === 'undefined') {
		  $('form[name=auxForm]').append("<input type='hidden' name='seguimientoSolicitud' id='seguimientoSolicitud' />");
		}    

    $("#seguimientoSolicitud").val(JSON.stringify(this.model.generar));
    $('form[name=auxForm]').attr('action', 'atencionAutorizador/generarCertificacion.do');
    $('form[name=auxForm]').submit();    
//    this.module.updateModel("CardLayoutComponent","responsableCardLayout", 2);
    this.module.controller.transition("informacionSolicitud");
    
	};
	
    function obtenerInfoRenapo(){
	var gridsNss = this.module.controller.model.consultaSolicitud.gridsNSS;
	var infoRenapo;
	for(var i = 0;i< gridsNss.length; i++ ){
		if (gridsNss[i].data[0].informacionRENAPO !== null){
			infoRenapo = gridsNss[i].data[0].informacionRENAPO;
		}
	}
	return infoRenapo;
}

    function obtenerTareasTramites(folio){
	var solicitudes = this.module.controller.model.gridTramites.data;
	var tareasTramites;
	for(var i = 0;i< solicitudes.length; i++ ){
		if (solicitudes[i].folio ===folio){
			tareasTramites = solicitudes[i].tareasTramites;
			break;
		}
	}
	return tareasTramites;
};

ConsultaSolicitudController.prototype.certificadoNSS = function(){
//	var NUMEROS_NSS_CERTIFICADOR_POSICIONES_1_2 = ["36","77","79","80","97"];
//	var NUMERO_NSS_CERTIFICADOR_POSICIONES_1_2_CASO_2 = "89";
//	var NUMEROS_NSS_CERTIFICADOR_COMBINACION_POSICIONES_3_4 = ["97","98","99","00","01","02"];
//	var INICIO_CASO_1 =0 ;
//	var FIN_CASO_1=2;
//	var INICIO_CASO_2=2;
//	var FIN_CASO_2=4;
//	var NSS = this.model.gridNSS.data;//arreglo de nss
//	
//	for (var i = 0; i < NSS.length; i++) {
//		if(NUMEROS_NSS_CERTIFICADOR_POSICIONES_1_2.lastIndexOf(NSS[i].nss.substring(INICIO_CASO_1, FIN_CASO_1)) >=0){
//			$("#modalcertificadoNSS").modal();
//		} else if(NUMERO_NSS_CERTIFICADOR_POSICIONES_1_2_CASO_2.lastIndexOf(NSS[i].nss.substring(INICIO_CASO_1, FIN_CASO_1)) >=0 &&
//				NUMEROS_NSS_CERTIFICADOR_COMBINACION_POSICIONES_3_4.lastIndexOf(NSS[i].nss.substring(INICIO_CASO_2, FIN_CASO_2)) >=0){
//			$("#modalcertificadoNSS").modal();
//		}
//	}
//	
};	

//COnsulta Actual Y previa
	


ConsultaSolicitudController.prototype.consultaPreviaDetalle = function(){
	ConsultaPreviaController.prototype.siguienteCorreccion(this.model);
//	 this.module.updateModel("CardLayoutComponent","responsableCardLayout", 9);
	 this.module.controller.transition("confirmarCorreccionDatosLectura");
};

ConsultaSolicitudController.prototype.regresarPreviaDetalle = function(){
//	 this.module.updateModel("CardLayoutComponent","responsableCardLayout", 9);
	 this.module.controller.transition("confirmarCorreccionDatosLectura");
};


ConsultaSolicitudController.prototype.continuarAtencionResponsable = function(){
  if( this.module.controller.model.consultaSolicitud.cuentaIndividual.data.length === 0 ){
      this.module.controller.model.consultaSolicitud.cuentaIndividual.data[0] ={};
    }
//	 this.module.updateModel("CardLayoutComponent","responsableCardLayout", 10);
  this.module.controller.transition("consultaAtencionResponsableUI");
};


ConsultaSolicitudController.prototype.continuarDetalleAtencionResponsable = function(){
	ConsultaPreviaController.prototype.siguienteDetalleCorreccion(this.model);
//	 this.module.updateModel("CardLayoutComponent","responsableCardLayout", 11);
	this.module.controller.transition("consultaDetalleAtencionResponsableUI");
};

ConsultaSolicitudController.prototype.regresarConsultaPrevia = function(){
//	 this.module.updateModel("CardLayoutComponent","responsableCardLayout", 3);
	this.module.controller.transition("correccionDatosLectura");
	
};


