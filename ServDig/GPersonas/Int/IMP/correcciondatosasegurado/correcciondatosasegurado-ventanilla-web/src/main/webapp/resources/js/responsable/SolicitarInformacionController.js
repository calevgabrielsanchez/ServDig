function SolicitarInformacionController(module) {
    this.module = module;    
}

SolicitarInformacionController.prototype.guardar = function(){
  
  this.model.solicitarInformacion = { folio: this.module.controller.model.consultaSolicitud.folio};
  this.module.validator.formToModel("FormPanelComponent-solicitarInformacion",
   this.model.solicitarInformacion );
  
  if( this.module.render.isEmpty( this.model.solicitarInformacion.detalle)  ){
    this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"Indique la informaci\u00f3n adicional que el Asegurado debe proporcionar."} );
    return;
  }
  this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:""} );
  
  this.model.solicitarInformacion.idTramite = this.module.controller.model.consultaSolicitud.idTramite;
  this.model.solicitarInformacion.idSolicitud = this.module.controller.model.consultaSolicitud.id;
  this.model.solicitarInformacion.curpResponsable = this.module.controller.model.consultaSolicitud.curpResponsable;
  this.model.solicitarInformacion.idTarea = this.module.controller.model.consultaSolicitud.idTarea;
  this.model.solicitarInformacion.folio = this.module.controller.model.consultaSolicitud.folio;
  this.model.solicitarInformacion.informacionRENAPO = this.module.controller.model.consultaSolicitud.gridNSS.data[0].informacionRENAPO;
  this.model.solicitarInformacion.fechaInicio = this.module.controller.model.consultaSolicitud.fechaInicio;
  this.model.solicitarInformacion.subDelegacion = this.module.controller.model.consultaSolicitud.subDelegacion;
  this.module.service.solicitarInformacion();
  this.model.solicitarInformacion.resumen="";
  this.model.solicitarInformacion.detalle="";	
};

SolicitarInformacionController.prototype.regresar = function(){
	if( !this.module.render.isEmpty( this.model.solicitarInformacion.resumen )  ){
		this.model.solicitarInformacion.resumen="";
	}
	if( !this.module.render.isEmpty( this.model.solicitarInformacion.detalle)  ){
		this.model.solicitarInformacion.detalle="";
	}
	$("#modal").modal('show');
	this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:""} );
	this.module.updateModel("CardLayoutComponent","responsableCardLayout", 1);
	$("#modal").modal('hide');
};

SolicitarInformacionController.prototype.mostrarCapturaCorreo = function(){
	$("#modalInfoCorreo").modal('hide');
	$('div.modal-backdrop.fade').remove();
	$('#modalCapturaCorreo').modal();
};

SolicitarInformacionController.prototype.guardarCapturaCorreo = function(){
	//Validaciones

	//Guardar Correo
	this.module.service.guardarCorreoAsegurado();

};

SolicitarInformacionController.prototype.cancelarCapturaCorreo = function(){
	$("#modal").modal('show');
	$("#modalCapturaCorreo").modal('hide');
	$("#modal").modal('hide');
};