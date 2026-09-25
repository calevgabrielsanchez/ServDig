function ReasignacionController(module) {
    this.module = module;    
}

ReasignacionController.prototype.reasignar = function(){
  var reg = /^[A-Za-z\d\s]+$/;
  this.model.reasignacion = { folio: this.module.controller.model.consultaSolicitud.folio};
  this.module.validator.formToModel("FormPanelComponent-reasignacion",
   this.model.reasignacion );
  
  if( this.model.reasignacion.responsable === "0"  ){
    this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"Indique el responsable"} );
    return;
  }
  if( this.module.render.isEmpty( this.model.reasignacion.detalle)  ){
    this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"Indique el detalle de Reasignaci\u00F3n de la Solicitud."} );
    return;
  }

  this.model.reasignacion.detalle.trim();

  if(reg.test(this.model.reasignacion.detalle) != true ){
	    this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"No se permite capturar caracteres especiales"} );
	    return;
	  }

  this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:""} );
  console.log("MODELO DE REASIGNACION.- " + this.model.reasignacion);
  this.model.reasignacion.curp=this.module.controller.model.responsableSelectField[this.model.reasignacion.responsable-1].curp;
  this.model.reasignacion.correoElectronico=this.module.controller.model.responsableSelectField[this.model.reasignacion.responsable-1].correoElectronico;
  this.model.reasignacion.nombreCompleto=this.module.controller.model.responsableSelectField[this.model.reasignacion.responsable-1].value;
  this.model.reasignacion.idTramite = this.module.controller.model.consultaSolicitud.idTramite;
  this.model.reasignacion.idSolicitud = this.module.controller.model.consultaSolicitud.idSolicitud;
  this.model.reasignacion.idTarea = this.module.controller.model.consultaSolicitud.idTarea;
  this.model.reasignacion.folio = this.module.controller.model.consultaSolicitud.folio;
  this.model.reasignacion.tareasTramites = obtenerTareasTramites(this.model.reasignacion.folio);
  this.model.reasignacion.fechaInicio = this.module.controller.model.consultaSolicitud.fechaInicio;
  this.model.reasignacion.responsable = this.module.controller.model.consultaSolicitud.responsable;
  this.model.reasignacion.informacionRENAPO = obtenerInfoRenapo();
  this.module.service.reasignar();
  this.model.reasignacion.detalle="";
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

ReasignacionController.prototype.regresar = function(){
	if( this.module.render.isEmpty( this.model.reasignacion.detalle)  ){
		this.model.reasignacion.detalle="";
	  }
	$("#modal").modal('show');
	this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:""} );
//	this.module.updateModel("CardLayoutComponent","responsableCardLayout", 1);
	this.module.controller.transition("informacionSolicitud");
	$("#modal").modal('hide');
};


