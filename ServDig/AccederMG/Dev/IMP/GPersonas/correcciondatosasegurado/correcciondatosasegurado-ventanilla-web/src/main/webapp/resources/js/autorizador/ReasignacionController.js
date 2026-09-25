function ReasignacionController(module) {
    this.module = module;    
}

ReasignacionController.prototype.reasignar = function(){
  
  this.model.reasignacion = { folio: this.module.controller.model.consultaSolicitud.folio};
  this.module.validator.formToModel("FormPanelComponent-reasignacion",
   this.model.reasignacion );
  
  if( this.model.reasignacion.responsable === "0"  ){
    this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"Indique el responsale"} );
    return;
  }
  if( this.module.render.isEmpty( this.model.reasignacion.detalle)  ){
    this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"Indique el detalle"} );
    return;
  }
  this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:""} );
  this.model.reasignacion.curp=this.module.controller.model.responsableSelectField[this.model.reasignacion.responsable-1].curp;
  this.model.reasignacion.correoElectronico=this.module.controller.model.responsableSelectField[this.model.reasignacion.responsable-1].correoElectronico;
  this.model.reasignacion.nombreCompleto=this.module.controller.model.responsableSelectField[this.model.reasignacion.responsable-1].value;
  this.model.reasignacion.idTramite = this.module.controller.model.consultaSolicitud.idTramite;
  this.model.reasignacion.idSolicitud = this.module.controller.model.consultaSolicitud.id;
  this.model.reasignacion.idTarea = this.module.controller.model.consultaSolicitud.idTarea;
  this.model.reasignacion.folio = this.module.controller.model.consultaSolicitud.folio;
  this.model.reasignacion.fechaInicio = this.module.controller.model.consultaSolicitud.fechaInicio;
  this.model.reasignacion.nss = this.module.controller.model.consultaSolicitud.nss;
  this.model.reasignacion.informacionRENAPO = this.module.controller.model.consultaSolicitud.gridNSS.data[0].informacionRENAPO;
  this.module.service.reasignar();
  this.model.reasignacion.detalle="";
};

ReasignacionController.prototype.regresar = function(){
	if( this.module.render.isEmpty( this.model.reasignacion.detalle)  ){
		this.model.reasignacion.detalle="";
	  }
	$("#modal").modal('show');
	this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:""} );
	this.module.updateModel("CardLayoutComponent","responsableCardLayout", 1);
	$("#modal").modal('hide');
};