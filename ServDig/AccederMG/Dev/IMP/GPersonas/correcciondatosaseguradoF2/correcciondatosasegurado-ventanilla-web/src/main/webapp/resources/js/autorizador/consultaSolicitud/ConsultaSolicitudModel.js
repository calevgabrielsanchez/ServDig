function ConsultaSolicitudModel( module ){
  this.module = module;
  this.init();
};
ConsultaSolicitudModel.prototype.init = function(){
  
  
  var btnCancelar = new Boton("Cancelar solicitud","consultaSolicitudController.cancelar","btn-danger");  
  var btnSolicitarInformacion = new Boton("Solicitar informaci\u00F3n", "consultaSolicitudController.solicitarInformacion", "btn-default");
  var btnObtenerCertificacion = new Boton("Obtener Certificaci\u00F3n", "consultaSolicitudController.generar", "btn-default");
  var btnConsultaPrevia = new Boton("Consulta previa", "consultaSolicitudController.consultaPrevia", "btn-default");  
  var btnReasignar = new Boton("Reasignar", "consultaSolicitudController.reasignar", "btn-default"); 
  
  var btnSalir = new Boton("Salir", "bandeja", "btn-danger");  
  var btnConsultarCambios = new Boton("Consultar cambios", "consultaSolicitudController.consultarCambios", "btn-default");
  var btnConsultarCambiosContinuar = new Boton("Consultar cambios", "consultaSolicitudController.consultaPreviaContinuar", "btn-primary");
  
  this.botonesPorEstadoSolicitudAutorizador = [
    new EstadoSolicitud(87,"SIN RESPONSABLE", [ btnCancelar, btnSolicitarInformacion, btnReasignar  ], [ btnSalir ] ),
    new EstadoSolicitud(4,"ASIGNADA", [ btnCancelar, btnSolicitarInformacion, btnReasignar  ], [ btnSalir ] ),
    new EstadoSolicitud(5,"INFORMACIÓN ADICIONAL REQUERIDA", [ btnCancelar, btnSolicitarInformacion, btnReasignar  ], [ btnSalir ] ),
    new EstadoSolicitud(4,"REASIGNADA", [ btnCancelar, btnSolicitarInformacion, btnReasignar  ], [ btnSalir ] ),
    new EstadoSolicitud(3,"POR AUTORIZAR", [ btnCancelar ], [ btnSalir, btnConsultarCambiosContinuar ] ),
    new EstadoSolicitud(75,"AUTORIZADA", [  ], [ btnSalir, btnConsultarCambios ] ),
    new EstadoSolicitud(85,"ERROR SINDO", [ btnSolicitarInformacion, btnReasignar ], [ btnSalir, btnConsultarCambios ] ),
    new EstadoSolicitud(88,"OPERADA", [ btnObtenerCertificacion, btnConsultaPrevia, btnReasignar ], [ btnSalir, btnConsultarCambios ] ),    
    new EstadoSolicitud(70,"RECHAZADA", [ btnSolicitarInformacion, btnReasignar ], [ btnSalir, btnConsultarCambios ] ),    
    new EstadoSolicitud(9,"CANCELADA", [], [ btnSalir,btnConsultarCambios ] ),
    new EstadoSolicitud(2,"ATENDIDA", [ btnObtenerCertificacion, btnConsultaPrevia ], [ btnSalir, btnConsultarCambios ] ),   
    new EstadoSolicitud(37,"ENVIADA SINDO", [ ], [ btnSalir, btnConsultarCambios ] ),
    new EstadoSolicitud(89,"ATENDIDA POR DERECHOHABIENTE", [ ], [ btnSalir] )
  ];
};

ConsultaSolicitudModel.prototype.getBotonesSuperiores = function( estadoSolicitud ){
  var botones = [];
  var i=0;
  for(i=0; i< this.botonesPorEstadoSolicitudAutorizador.length;i++ ){
    if( estadoSolicitud === this.botonesPorEstadoSolicitudAutorizador[i].id ){
      return this.botonesPorEstadoSolicitudAutorizador[i].top;
    }
  }
  return botones;
};
ConsultaSolicitudModel.prototype.getBotonesInferiores = function( estadoSolicitud ){
  var botones = [];
  var i=0;
  for(i=0; i< this.botonesPorEstadoSolicitudAutorizador.length;i++ ){
    if( estadoSolicitud === this.botonesPorEstadoSolicitudAutorizador[i].id ){
      return this.botonesPorEstadoSolicitudAutorizador[i].bottom;
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


  