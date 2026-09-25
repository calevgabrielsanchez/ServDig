 function SolicitarInformacionController(module) {
    this.module = module;    
}

SolicitarInformacionController.prototype.guardar = function(){
  var reg = /^[A-Za-z\d\s]+$/;
  this.model.solicitarInformacion = { folio: this.module.controller.model.consultaSolicitud.folio};
  this.module.validator.formToModel("FormPanelComponent-solicitarInformacion",
   this.model.solicitarInformacion );
  
  if( this.module.render.isEmpty( this.model.solicitarInformacion.detalle)  ){
	    this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"Indique la informaci\u00f3n adicional que el Asegurado debe proporcionar."} );
	    return;
   }

  this.model.solicitarInformacion.detalle.trim();

  if(reg.test(this.model.solicitarInformacion.detalle) != true ){
	    this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"No se permite capturar caracteres especiales"} );
	    return;
	  }

  this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:""} );
  console.log("MODELO SOLICITAR INFORMACION.- " + this.model.solicitarInformacion);
  this.model.solicitarInformacion.idTramite = this.module.controller.model.consultaSolicitud.idTramite;
  this.model.solicitarInformacion.idSolicitud = this.module.controller.model.consultaSolicitud.idSolicitud;  
  this.model.solicitarInformacion.curpResponsable = this.module.controller.model.consultaSolicitud.curpResponsable;
  this.model.solicitarInformacion.idTarea = this.module.controller.model.consultaSolicitud.idTarea;
  this.model.solicitarInformacion.folio = this.module.controller.model.consultaSolicitud.folio;
  this.model.solicitarInformacion.informacionRENAPO = obtenerInfoRenapo();
  this.model.solicitarInformacion.fechaInicio = this.module.controller.model.consultaSolicitud.fechaInicio;
  this.model.solicitarInformacion.subDelegacion = this.module.controller.model.consultaSolicitud.subDelegacion;
  this.model.solicitarInformacion.tareasTramites = obtenerTareasTramites(this.model.solicitarInformacion.folio);
  this.model.solicitarInformacion.isAutorizador = true;
  this.module.service.solicitarInformacion();
  this.model.solicitarInformacion.resumen="";
  this.model.solicitarInformacion.detalle="";
};

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
}

function obtenerInfoRenapo(){
	var gridsNss = this.module.controller.model.consultaSolicitud.gridsNSS
	var infoRenapo;
	for(var i = 0;i< gridsNss.length; i++ ){
		if (gridsNss[i].data[0].informacionRENAPO !=null){
			infoRenapo = gridsNss[i].data[0].informacionRENAPO;
		}
	}
	return infoRenapo;
}

SolicitarInformacionController.prototype.regresar = function(){
	if( !this.module.render.isEmpty( this.model.solicitarInformacion.resumen )  ){
		this.model.solicitarInformacion.resumen="";
	}
	if( !this.module.render.isEmpty( this.model.solicitarInformacion.detalle)  ){
		this.model.solicitarInformacion.detalle="";
	}
	$("#modal").modal('show');
	this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:""} );
//	this.module.updateModel("CardLayoutComponent","responsableCardLayout", 1);
	this.module.controller.transition("informacionSolicitud");
	$("#modal").modal('hide');
};

SolicitarInformacionController.prototype.mostrarCapturaCorreo = function(){
	$("#modalInfoCorreo").modal('hide');
	$('div.modal-backdrop.fade').remove();
	$('#modalCapturaCorreo').modal();
};

SolicitarInformacionController.prototype.guardarCapturaCorreo = function(){
	if(validacionesCorreo ($("#correoAseguradoText").val())){
		this.module.service.guardarCorreoAsegurado();
		$("#correoAseguradoText").val("");
		$("#message").remove();
		$('.has-error').removeClass('has-error');
	}

};

function isValidEmail(mail) { 
	  return /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,4})+$/.test(mail); 
};

function validacionesCorreo (correo){
	if(correo ===""){
		TextFieldComponent.prototype.validate('correoAseguradoText', {isValid:false, message :'Campo requerido.',modal:true});
		return false;
	}else if(!isValidEmail(correo)) {
		TextFieldComponent.prototype.validate('correoAseguradoText', {isValid:false, message :'El formato del correo electr\u00f3nico no es v\u00E1lido.',modal:true});
	    return false;
	}
	return true;
};

SolicitarInformacionController.prototype.cancelarCapturaCorreo = function(){
	$("#modal").modal('show');
	$("#correoAseguradoText").val("");
	$("#message").remove();
	$('.has-error').removeClass('has-error');
	$("#modalCapturaCorreo").modal('hide');
	$("#modal").modal('hide');
};


