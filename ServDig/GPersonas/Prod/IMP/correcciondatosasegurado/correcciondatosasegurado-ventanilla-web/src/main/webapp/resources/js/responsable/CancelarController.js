function CancelarController(module) {
    this.module = module;
    this.model = {};
    this.consultaSolicitudController = new ConsultaSolicitudController(this.module);
}

CancelarController.prototype.regresar = function(){
	  
	  if( !this.module.render.isEmpty( this.model.cancelar.resumen ) ){
	    this.model.cancelar.resumen="";
	  }
	  if( !this.module.render.isEmpty( this.model.cancelar.detalle ) ){
	    this.model.cancelar.detalle="";
	  }
	  $("#modal").modal('show');
	  this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:""} );
	  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 1);
	  $("#modal").modal('hide');
	};

CancelarController.prototype.cancelar = function(){
  // Guardar el modelo los datos actuales de la forma antes de hacer el cambio	
  this.module.validator.formToModel("FormPanelComponent-cancelar",
   this.model.cancelar );
  
  if( this.module.render.isEmpty( this.model.cancelar.detalle ) ){
    this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"Especifique el motivo de la Cancelaci\u00F3n de la Solicitud."} );
    return;
  }
  this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:""} );
  this.model.cancelar.idTramite = this.model.consultaSolicitud.idTramite;
  this.model.cancelar.idSolicitud = this.model.consultaSolicitud.id;
  this.model.cancelar.idTarea = this.model.consultaSolicitud.idTarea;
  this.model.cancelar.folio = this.model.consultaSolicitud.folio;
  this.model.cancelar.informacionRENAPO = this.model.consultaSolicitud.gridNSS.data[0].informacionRENAPO;
  this.model.cancelar.fechaInicio = this.model.consultaSolicitud.fechaInicio;
  this.model.cancelar.subDelegacion = this.model.consultaSolicitud.subDelegacion;
  this.model.cancelar.curpResponsable = this.module.controller.model.consultaSolicitud.curpResponsable;
  $("#modalCancelar").modal("show");
};


CancelarController.prototype.cancelarModal = function(){
	$("#modalCancelar").modal("hide");
};

CancelarController.prototype.cancelarAccion = function(){
	$("#modalCancelar").modal("hide");
	this.module.service.cancelar();
	this.model.cancelar.detalle="";
};
