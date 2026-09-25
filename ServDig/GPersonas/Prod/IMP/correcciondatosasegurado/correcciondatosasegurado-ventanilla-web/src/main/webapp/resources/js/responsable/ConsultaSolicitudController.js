function ConsultaSolicitudController(module) {
    this.module = module;    
}

ConsultaSolicitudController.prototype.bandeja = function(){
  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 0);
};

ConsultaSolicitudController.prototype.cancelar = function(){  
  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 4);
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
		  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 6);
	  }
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
  // Actualizar los tipos de regularización, si es correcto
  // mostrar la pantalla de inicio tramite
  
	  this.module.validator.formToModel("FormPanelComponent-consultaSolicitud",
	   this.model.consultaSolicitud );  
	  
	  this.model.currentNSSIndex = 0;
	  this.model.detalle = this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex];
	  
	  //multiples personas por NSS
	  this.model.currentPersonaNSSIndex = 0;
	  this.model.detalle.informacionBDTU = this.model.detalle.informacionFuentesNSS[this.model.currentPersonaNSSIndex];
	  
	  if(this.model.detalle.informacionFuentesNSS.length == 0 ){
	  $("#modalSalirInicioMen").modal();
      } else{
	  this.module.updateModel("NavegacionPersonaNSSComponent","navegacionPersonaNSS", "detalle");
	  $("#modal").modal('show');
	  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 2);
	  $("#modal").modal('hide');
	  $("#btnAnteriorPersona").prop('disabled',true);
	  }
	  
	  
	  if(this.model.detalle.informacionFuentesNSS.length <= 1 ){
		  //solo hay un registro inhabilitar boton siguiente
		  $("#btnSiguientePersona").prop('disabled',true);
	  } 
	  
	  this.certificadoNSS();
  
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
	
ConsultaSolicitudController.prototype.certificadoNSS = function(){
	var NUMEROS_NSS_CERTIFICADOR_POSICIONES_1_2 = ["36","77","79","80","97"];
	var NUMERO_NSS_CERTIFICADOR_POSICIONES_1_2_CASO_2 = "89";
	var NUMEROS_NSS_CERTIFICADOR_COMBINACION_POSICIONES_3_4 = ["97","98","99","00","01","02"];
	var INICIO_CASO_1 =0 ;
	var FIN_CASO_1=2;
	var INICIO_CASO_2=2;
	var FIN_CASO_2=4;
	var NSS = this.model.gridNSS.data;//arreglo de nss
	
	for (var i = 0; i < NSS.length; i++) {
		if(NUMEROS_NSS_CERTIFICADOR_POSICIONES_1_2.lastIndexOf(NSS[i].nss.substring(INICIO_CASO_1, FIN_CASO_1)) >=0){
			$("#modalcertificadoNSS").modal();
		} else if(NUMERO_NSS_CERTIFICADOR_POSICIONES_1_2_CASO_2.lastIndexOf(NSS[i].nss.substring(INICIO_CASO_1, FIN_CASO_1)) >=0 &&
				NUMEROS_NSS_CERTIFICADOR_COMBINACION_POSICIONES_3_4.lastIndexOf(NSS[i].nss.substring(INICIO_CASO_2, FIN_CASO_2)) >=0){
			$("#modalcertificadoNSS").modal();
		}
	}
		
};	
