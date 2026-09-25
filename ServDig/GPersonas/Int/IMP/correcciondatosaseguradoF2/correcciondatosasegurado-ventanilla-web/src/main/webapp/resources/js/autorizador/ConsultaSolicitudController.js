function ConsultaSolicitudController(module) {
    this.module = module;    
}

ConsultaSolicitudController.prototype.bandeja = function(){
  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 0);
  this.module.controller.transition("bandejaSolicitudesUI");
};
ConsultaSolicitudController.prototype.cambiosAutorizar = function(){
//  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 2);
	  this.module.controller.transition("cambiosAutorizarUI");
};

ConsultaSolicitudController.prototype.reasignar = function(){
//  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 3);
	this.module.controller.transition("reasignacionUI");
	
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
//		  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 4);
		  this.module.controller.transition("solicitarInformacionUI");
	  }
};
ConsultaSolicitudController.prototype.rechazar = function(){
//  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 5);
	this.module.controller.transition("rechazarUI");
};

ConsultaSolicitudController.prototype.cancelar = function(){
//	  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 9);
	this.module.controller.transition("cancelarUI");
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
//    this.module.updateModel("CardLayoutComponent","responsableCardLayout", 0);
    this.module.controller.transition("bandejaSolicitudesUI");
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

ConsultaSolicitudController.prototype.checkMotivoAclaracion = function(){
  // Si esta seleccionado algun motivo de aclaraci�n se habilita el boton de iniciar.
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

ConsultaSolicitudController.prototype.iniciar = function(){
//	this.model.currentNSSIndex = 0;
//	this.model.currentPersonaNSSIndex = 0;
//	this.model.detalle = this.model.consultaSolicitud.gridsNSS[this.model.currentNSSIndex].data;
//	this.model.resumenCorreccion = this.model.consultaSolicitud.gridsNSS[this.model.currentNSSIndex].data;
//	this.model.detalle.informacionBDTU = this.model.detalle[this.model.currentPersonaNSSIndex].informacionFuentesNSS;
//	this.module.updateModel("NavegacionPersonaNSSComponent","navegacionPersonaNSS", "detalle");
//	this.module.render.notify("ResumenCorreccionComponent", "resumenCorreccion", "resumenCorreccion");
//	this.module.updateModel("CardLayoutComponent", "correccionCardLayout", 0);
//	this.module.updateModel("CardLayoutComponent","responsableCardLayout", 7);
//	$("#btnAnteriorPersona").prop('disabled',true);
//	if(this.model.detalle[0].informacionFuentesNSS.length <= 1 ){
		//solo hay un registro inhabilitar boton siguiente
//		$("#btnSiguientePersona").prop('disabled',true);
//	}
	
	this.module.service.correccionDatosService.fetch( this.module );
//	this.module.updateModel("CardLayoutComponent","responsableCardLayout", 17);
	this.module.controller.transition("correccionDatosAutorizar");
	

};

ConsultaSolicitudController.prototype.postFetchCorreccionDatos = function( modelName ){
  // console.log( modelName );
  
  if( modelName === "correccionDatosLectura"){    
    this.module.service.confirmarCorreccionDatos.fetch( this.module, this.model.correccionDatosLectura, "confirmarCorreccionDatosLectura");
    this.module.controller.transition("confirmarCorreccionDatosLectura"); 
  }else{  
    
    this.module.controller.transition(modelName);
  }
};

ConsultaSolicitudController.prototype.consultaPrevia = function(){
  this.module.service.correccionDatosService.fetch( this.module, "correccionDatosConsultaPrevia" );  
};

ConsultaSolicitudController.prototype.consultarCambios = function(){
  this.module.service.correccionDatosService.fetch( this.module, "correccionDatosLectura" );  
};

ConsultaSolicitudController.prototype.consultaPreviaContinuar = function(){
  this.module.service.correccionDatosService.fetch( this.module, "correccionDatosAutorizar" );  
};
	
	ConsultaSolicitudController.prototype.consultaPreviaDetalle = function(){
		ConsultaPreviaController.prototype.siguienteCorreccion(this.model);
//		 this.module.updateModel("CardLayoutComponent","responsableCardLayout", 11);
		this.module.controller.transition("consultaPreviaDetalleUI");
	};

	ConsultaSolicitudController.prototype.regresarPreviaDetalle = function(){
//		 this.module.updateModel("CardLayoutComponent","responsableCardLayout", 11);
		this.module.controller.transition("consultaPreviaDetalleUI");
	};


	ConsultaSolicitudController.prototype.continuarAtencionResponsable = function(){
    if( this.module.controller.model.consultaSolicitud.cuentaIndividual.data.length === 0 ){
      this.module.controller.model.consultaSolicitud.cuentaIndividual.data[0] ={};
    }
//		 this.module.updateModel("CardLayoutComponent","responsableCardLayout", 12);
    this.module.controller.transition("consultaAtencionResponsableUI");
	};


	ConsultaSolicitudController.prototype.continuarDetalleAtencionResponsable = function(){
		ConsultaPreviaController.prototype.siguienteDetalleCorreccion(this.model);
//		 this.module.updateModel("CardLayoutComponent","responsableCardLayout", 13);
		this.module.controller.transition("consultaDetalleAtencionResponsableUI");
	};

	ConsultaSolicitudController.prototype.regresarConsultaPrevia = function(){
//		 this.module.updateModel("CardLayoutComponent","responsableCardLayout", 10);
		this.module.controller.transition("consultaPreviaUI");
	};
