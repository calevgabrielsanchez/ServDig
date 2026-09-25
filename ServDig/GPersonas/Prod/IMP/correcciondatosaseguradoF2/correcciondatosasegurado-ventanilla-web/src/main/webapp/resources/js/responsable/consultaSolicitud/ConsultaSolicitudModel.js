function ConsultaSolicitudModel( module ){
  this.module = module;
  this.init();
};
ConsultaSolicitudModel.prototype.init = function(){
  
  
  var btnCancelar = new Boton("Cancelar solicitud","consultaSolicitudController.cancelar","btn-danger");
  var btnAgregarNSS = new Boton("Agregar NSS Documentos","consultaSolicitudController.agregarNssDocumento", "btn-default");
  var btnSolicitarInformacion = new Boton("Solicitar informaci\u00F3n", "consultaSolicitudController.solicitarInformacion", "btn-default");
  var btnObtenerCertificacion = new Boton("Obtener Certificaci\u00F3n", "consultaSolicitudController.generar", "btn-default");
  var btnConsultaPrevia = new Boton("Consulta previa", "consultaSolicitudController.consultaPrevia", "btn-default");
  var btnDescargaComprobante = new Boton("Descargar Comprobante", "descargarComprobante", "btn-default");  
  
  var btnSalir = new Boton("Salir", "bandeja", "btn-danger");
  var btnContinuar = new Boton("Continuar", "consultaSolicitudController.iniciar", "btn-primary");
  var btnConsultarCambios = new Boton("Consultar cambios", "consultaSolicitudController.consultarCambios", "btn-default");
  
  this.botonesPorEstadoSolicitudResponsable = [
    new EstadoSolicitud(4,"ASIGNADA", [ btnCancelar, btnAgregarNSS, btnSolicitarInformacion, btnDescargaComprobante   ], [ btnSalir, btnContinuar ] ),
    new EstadoSolicitud(5,"INFORMACION ADICIONAL REQUERIDA", [ btnCancelar, btnAgregarNSS, btnSolicitarInformacion, btnDescargaComprobante ], [ btnSalir, btnContinuar ] ),
    new EstadoSolicitud(4,"REASIGNADA", [ btnCancelar, btnAgregarNSS, btnSolicitarInformacion, btnDescargaComprobante  ],  [ btnSalir, btnContinuar ] ),
    new EstadoSolicitud(3,"POR AUTORIZAR", [ btnDescargaComprobante ], [ btnSalir, btnConsultarCambios ] ),
    new EstadoSolicitud(75,"AUTORIZADA", [ btnDescargaComprobante ], [ btnSalir, btnConsultarCambios ] ),
    new EstadoSolicitud(85,"ERROR SINDO", [ btnCancelar, btnAgregarNSS, btnSolicitarInformacion, btnDescargaComprobante ], [ btnSalir, btnContinuar ] ),
    new EstadoSolicitud(88,"OPERADA", [ btnObtenerCertificacion, btnConsultaPrevia,  btnDescargaComprobante ], [ btnSalir, btnConsultarCambios ] ),
    new EstadoSolicitud(70,"RECHAZADA", [ btnCancelar, btnAgregarNSS, btnSolicitarInformacion, btnDescargaComprobante ], [ btnSalir, btnContinuar ] ),
    new EstadoSolicitud(9,"CANCELADA", [ btnDescargaComprobante ], [ btnSalir, btnConsultarCambios ] ),            
    new EstadoSolicitud(2,"ATENDIDA", [ btnObtenerCertificacion, btnConsultaPrevia, btnDescargaComprobante ], [ btnSalir, btnConsultarCambios ] ),    
    new EstadoSolicitud(37,"ENVIADA A SINDO", [ btnDescargaComprobante ], [ btnSalir, btnConsultarCambios ] ),
    new EstadoSolicitud(1,"USUARUI NO VALIDO", [ btnDescargaComprobante ], [ btnSalir, btnConsultarCambios ] ),
    new EstadoSolicitud(89,"ATENDIDA POR DERECHOHABIENTE", [ btnCancelar, btnAgregarNSS, btnSolicitarInformacion, btnDescargaComprobante   ], [ btnSalir, btnContinuar ] )
  ];

  //se agrega nuevo arreglo de botones para incluir boton de certificacion a responsables que no tienen asignada la solicitud
  // y el estado de la solicitud sea 88 OPERADA
  this.botonesPorEstadoSolicitudResponsableAut = [
   new EstadoSolicitud(88,"OPERADA", [ btnObtenerCertificacion, btnDescargaComprobante ], [ btnSalir, btnConsultarCambios ] ),
 ];

};

ConsultaSolicitudModel.prototype.getBotonesSuperiores = function( estadoSolicitud ){
 
  var botones = [];
  var i=0;
  if(this.module.controller.model.consultaSolicitud.idEstadoSolicitud === '2' )
  {
	  this.module.controller.model.correspondeUsuarioSolicitud = true;
  }
  else if(this.module.render.isEmpty(this.module.controller.model.usuarioasignado) || this.module.controller.model.usuarioasignado !== this.module.controller.model.usuariosession)
  {	 	  
	  this.module.controller.model.correspondeUsuarioSolicitud = false;
	  // si el estado de la solicitud es OPERADA
	  if(estadoSolicitud === 88){
		  return this.botonesPorEstadoSolicitudResponsableAut[0].top;
	  }else{
	  // Estado invalido
		  return this.botonesPorEstadoSolicitudResponsable[11].top;
	  }
  }
  else{
	  this.module.controller.model.correspondeUsuarioSolicitud = true;
  }
  //recorre hasta encontrar el estado de la solicitud y regresa el grupo de botones
  for(i=0; i< this.botonesPorEstadoSolicitudResponsable.length;i++ ){
    if( estadoSolicitud === this.botonesPorEstadoSolicitudResponsable[i].id ){
      return this.botonesPorEstadoSolicitudResponsable[i].top;
    }
  }
  return botones;
};
ConsultaSolicitudModel.prototype.getBotonesInferiores = function( estadoSolicitud ){
  var botones = [];
  var i=0;
  
  if( this.module.render.isEmpty(this.module.controller.model.usuarioasignado) || this.module.controller.model.usuarioasignado !== this.module.controller.model.usuariosession)
  {	 	  
	  this.module.controller.model.correspondeUsuarioSolicitud = false;
    // Estado invalido
    return this.botonesPorEstadoSolicitudResponsable[11].bottom;
  }
  else{
	  this.module.controller.model.correspondeUsuarioSolicitud = true;
  }
  
  
  for(i=0; i< this.botonesPorEstadoSolicitudResponsable.length;i++ ){
    if( estadoSolicitud === this.botonesPorEstadoSolicitudResponsable[i].id ){
      return this.botonesPorEstadoSolicitudResponsable[i].bottom;
    }
  }
  return botones;
};


function Boton( label, command, className ){
  this.label = label;
  this.command = command;
  this.className = className;
}

function EstadoSolicitud( id, description, top, bottom ){
  this.id = id;
  this.description = description;
  this.top = top;
  this.bottom = bottom;
  
}


