function CambiosAutorizarController(module) {
    this.module = module;    
}

CambiosAutorizarController.prototype.init = function(){
  this.model.detalle = this.model.consultaSolicitud;
  this.model.resumenCorreccion = this.model.consultaSolicitud.gridNSS.data;
  $("#modal").modal('show');
  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 2);
  this.module.render.notify("ResumenCorreccionComponent", "resumenCorreccion", "resumenCorreccion");
  $("#modal").modal('hide');
};

CambiosAutorizarController.prototype.reasignar = function(){
  
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
  
  this.module.service.reasignar();
};

CambiosAutorizarController.prototype.regresar = function(){
  this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:""} );
  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 1);
};

CambiosAutorizarController.prototype.regresarCorreccion = function() {
	// Guardar el modelo los datos actuales de la forma antes de hacer el cambio
	this.module.validator.formToModel("FormPanelComponent-correccionDatos",
			this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex]);
	if (this.model.currentNSSIndex > 0) {
		this.model.currentNSSIndex--;
		this.model.detalle = this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex];
		this.module.render.notify("FormPanelComponent", "panelCorreccionDatos","detalle");
	} else {
	    this.model.currentNSSIndex = 0;
	    this.model.currentPersonaNSSIndex = 0;
	    this.model.detalle = this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex];
	    this.model.resumenCorreccion = this.model.consultaSolicitud.gridNSS.data;
	    this.model.detalle.informacionBDTU = this.model.detalle.informacionFuentesNSS[this.model.currentPersonaNSSIndex];
	    $("#modal").modal('show');
	    this.module.updateModel("NavegacionPersonaNSSComponent","navegacionPersonaNSS", "detalle");
	    this.module.render.notify("ResumenCorreccionComponent", "resumenCorreccion", "resumenCorreccion");
	    this.module.updateModel("CardLayoutComponent", "correccionCardLayout", 0);
	    this.module.updateModel("CardLayoutComponent","responsableCardLayout", 7);
	    $("#modal").modal('hide');
	    if(this.model.detalle.informacionFuentesNSS.length <= 1 ){
		    //solo hay un registro inhabilitar boton siguiente
	    	$("#btnSiguientePersona").prop('disabled',true);
		}
	    $("#btnAnteriorPersona").prop('disabled',true);
	}
};