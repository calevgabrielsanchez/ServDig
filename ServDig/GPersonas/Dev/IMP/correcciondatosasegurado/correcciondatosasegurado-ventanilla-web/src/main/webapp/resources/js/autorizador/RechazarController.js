function RechazarController(module) {
    this.module = module;    
}

RechazarController.prototype.guardar = function(){
  
  var component = this.module.render.getComponentById("rechazarFormPanelComponent");
  
  //var valid = this.module.validator.validForm(component);
  //console.log(valid);
  this.model.rechazar = { folio: this.module.controller.model.consultaSolicitud.folio};
  this.module.validator.formToModel("FormPanelComponent-rechazar",
   this.model.rechazar );
  
  if( this.module.render.isEmpty( this.model.rechazar.resumen ) && this.module.render.isEmpty( this.model.rechazar.detalle)){
	    this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"Indique el resumen y el detalle"} );
	    return;
	  }
  
  if( this.module.render.isEmpty( this.model.rechazar.resumen )  ){
    this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"Indique el resumen"} );
    return;
  }
  if( this.module.render.isEmpty( this.model.rechazar.detalle)  ){
    this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"Indique el detalle"} );
    return;
  }
  this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:""} );
  
  this.model.rechazar.idTramite = this.module.controller.model.consultaSolicitud.idTramite;
  this.model.rechazar.idSolicitud = this.module.controller.model.consultaSolicitud.id;
  this.model.rechazar.idTarea = this.module.controller.model.consultaSolicitud.idTarea;
  this.model.rechazar.fechaInicio = this.module.controller.model.consultaSolicitud.fechaInicio;
  this.model.rechazar.responsable = this.module.controller.model.consultaSolicitud.curpResponsable;
  this.model.rechazar.nss = this.module.controller.model.consultaSolicitud.nss;
  this.model.rechazar.folio = this.module.controller.model.consultaSolicitud.folio;
  this.model.rechazar.informacionRENAPO = this.module.controller.model.consultaSolicitud.gridNSS.data[0].informacionRENAPO;
  this.module.service.rechazar();
  this.model.rechazar.resumen = "";
  this.model.rechazar.detalle = "";
};

RechazarController.prototype.regresar = function(){
	if( this.module.render.isEmpty( this.model.rechazar.resumen )  ){
		this.model.rechazar.resumen = "";
	  }
	if( this.module.render.isEmpty( this.model.rechazar.detalle)  ){
		this.model.rechazar.detalle = "";
	  }
	$("#modal").modal('show');
	this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:""} );
	this.module.updateModel("CardLayoutComponent","responsableCardLayout", 2);
	$("#modal").modal('hide');
};