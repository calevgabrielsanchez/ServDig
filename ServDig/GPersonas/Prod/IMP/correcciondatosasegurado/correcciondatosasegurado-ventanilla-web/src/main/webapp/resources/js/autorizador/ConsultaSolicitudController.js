function ConsultaSolicitudController(module) {
    this.module = module;    
}

ConsultaSolicitudController.prototype.bandeja = function(){
  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 0);
};
ConsultaSolicitudController.prototype.cambiosAutorizar = function(){
  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 2);
};

ConsultaSolicitudController.prototype.reasignar = function(){
  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 3);
};
ConsultaSolicitudController.prototype.solicitarInformacion = function(){
	   $('#modal').modal();
	  var correo =  this.module.controller.model.consultaSolicitud.gridNSS.data[0].informacionRENAPO.correoElectronico;
	  
	  if (correo == null || correo == ""){
		  $("#modal").modal('hide');
		  $('div.modal-backdrop.fade').remove();
		  $('#modalInfoCorreo').modal();
	  }else{
		  $("#modal").modal('hide');
		  $('div.modal-backdrop.fade').remove();
		  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 4);
	  }
};
ConsultaSolicitudController.prototype.rechazar = function(){
  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 5);
};

ConsultaSolicitudController.prototype.cancelar = function(){
	  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 9);
};

ConsultaSolicitudController.prototype.generar = function(){
	this.model.generar = { folio: this.module.controller.model.consultaSolicitud.folio};
	  
	  this.model.generar.idTramite = this.module.controller.model.consultaSolicitud.idTramite;
	  this.model.generar.idSolicitud = this.module.controller.model.consultaSolicitud.id;
	  this.model.generar.idTarea = this.module.controller.model.consultaSolicitud.idTarea;
	  this.model.generar.responsable = this.module.controller.model.consultaSolicitud.responsable;
	  this.model.generar.nss = this.module.controller.model.consultaSolicitud.nss;
	  this.model.generar.folio = this.module.controller.model.consultaSolicitud.folio;
	  this.model.generar.informacionRENAPO = this.module.controller.model.consultaSolicitud.gridNSS.data[0].informacionRENAPO;
	  
	  
	  if ($("#seguimientoSolicitud").val() == null || typeof $("#seguimientoSolicitud").val() === 'undefined') {
		  $('form[name=auxForm]').append("<input type='hidden' name='seguimientoSolicitud' id='seguimientoSolicitud' />");
		}    

    $("#seguimientoSolicitud").val(JSON.stringify(this.model.generar));
    $('form[name=auxForm]').attr('action', 'atencionAutorizador/generarCertificacion.do');
    $('form[name=auxForm]').submit();    
    this.module.updateModel("CardLayoutComponent","responsableCardLayout", 0);
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

ConsultaSolicitudController.prototype.iniciar = function(){
	this.model.currentNSSIndex = 0;
	this.model.currentPersonaNSSIndex = 0;
	this.model.detalle = this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex];
	this.model.resumenCorreccion = this.model.consultaSolicitud.gridNSS.data;
	this.model.detalle.informacionBDTU = this.model.detalle.informacionFuentesNSS[this.model.currentPersonaNSSIndex];
	this.module.updateModel("NavegacionPersonaNSSComponent","navegacionPersonaNSS", "detalle");
	this.module.render.notify("ResumenCorreccionComponent", "resumenCorreccion", "resumenCorreccion");
	this.module.updateModel("CardLayoutComponent", "correccionCardLayout", 0);
	this.module.updateModel("CardLayoutComponent","responsableCardLayout", 7);
	$("#btnAnteriorPersona").prop('disabled',true);
	if(this.model.detalle.informacionFuentesNSS.length <= 1 ){
		//solo hay un registro inhabilitar boton siguiente
		$("#btnSiguientePersona").prop('disabled',true);
	}
};