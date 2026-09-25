function AutorizadorController(module) {
    this.module = module;
    this.model = {};
    this.isConsulta= false;
    this.consultaSolicitudController = new ConsultaSolicitudController(this.module);
    this.reasignacionController = new ReasignacionController(this.module);
    this.solicitarInformacionController = new SolicitarInformacionController(this.module);
    this.cambiosAutorizarController = new CambiosAutorizarController(this.module);
    this.rechazarController = new RechazarController(this.module);
    this.cancelarController = new CancelarController(this.module);
	this.ESTADO_ATENDIDA = "ATENDIDA";
	this.ESTADO_OPERADA = "OPERADA";
	this.ESTADO_OPERADAV = "OPERADA - VENCIDA";

}

AutorizadorController.prototype.reasignarResponsable = function(){
	  this.module.service.reasignar();
	};

AutorizadorController.prototype.mostrarDocumento = function(index){
  var documento = this.model.consultaSolicitud.gridDocumentos.data[index];
  if( !this.module.render.isEmpty( documento ) ){
    document.forms['auxForm'].action = "atencionAutorizador/obtenerDocumento/"+documento.idPersona+"/"+documento.folio+"/"+documento.extension+"/"+documento.nombreArchivo+"/"+documento.idDocBoveda;
    document.forms['auxForm'].submit();
  }    
};

AutorizadorController.prototype.mostrarDocumentoBeneficiario = function(index){
	  var documento = this.model.consultaSolicitud.gridDocumentosBeneficiario.data[index];
	  if( !this.module.render.isEmpty( documento ) ){
	    document.forms['auxForm'].action = "atencionAutorizador/obtenerDocumento/"+documento.idPersona+"/"+documento.folio+"/"+documento.extension+"/"+documento.nombreArchivo+"/"+documento.idDocBoveda;
	    document.forms['auxForm'].submit();
	  }    
};

AutorizadorController.prototype.bandeja = function(){
  this.isConsulta = true;
//  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 0);
  this.module.controller.transition("bandejaSolicitudesUI");
  this.module.controller.model.paginaTramitesActualTramites = this.module.controller.model.gridTramites.currentPage;
  this.module.controller.model.paginaTramitesActualHistorico = this.module.controller.model.gridHistorico.currentPage;
};

AutorizadorController.prototype.recuperarFiltros = function(filtrosBusqueda){	
	$.each(filtrosBusqueda, function(){ 
		$("#" + arguments[0]).val(arguments[1]);	
		}
	);
	
	 this.module.updateModel("FormPanelComponent","filter", filtrosBusqueda);
	
};

var folioAutorizar;
AutorizadorController.prototype.confirmarCuentaindividual = function(){
  // Cerrar la modal, regresar a la bandeja y mostrar el mensaje de respuesta del server
//	$("#modalEnvioSindo").modal('show');
	folioAutorizar = $("#informacionRENAPO_folio").val();
//	this.module.updateModel( "CardLayoutComponent", "responsableCardLayout", 14);
	this.module.controller.transition("bandejaCuentaIndividualUI");
};

AutorizadorController.prototype.readCuentaIndividualResumen = function(){
	  // Cerrar la modal, regresar a la bandeja y mostrar el mensaje de respuesta del server
//		$("#modalEnvioSindo").modal('show');
	this.module.controller.transition("bandejaCuentaIndividualResumenUI");
//		this.module.updateModel( "CardLayoutComponent", "responsableCardLayout", 15);
	};

AutorizadorController.prototype.confirmar = function(){
	  // Cerrar la modal, regresar a la bandeja y mostrar el mensaje de respuesta del server
		$("#modalEnvioSindo").modal('show');
	};


AutorizadorController.prototype.cancelar = function(){  
  $('#cancelarModal').modal('show');
};

AutorizadorController.prototype.cancelarSolicitud = function(){
  $('#cancelarModal').modal('hide');
  this.module.service.cancelar();
};

AutorizadorController.prototype.checkMotivoAclaracion = function(){
  // Si esta seleccionado algun motivo de aclaracion se habilita el boton de iniciar.
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


AutorizadorController.prototype.checkEstadoAutorizacion = function(data,componentId){
	
	data.estadoAutorizacion = parseInt(data.estadoAutorizacion);
	
	//Botones inferiores por Default
	var buttonCardIndex=0; 
	//Botones superiores por Default 
	var buttonAccionesCardIndex=0;
	
	if(data.estadoAutorizacion == 0 ){
		buttonAccionesCardIndex = 4;
		buttonCardIndex = 1;
	}else if(data.estadoAutorizacion == 1 ){
		buttonAccionesCardIndex = 3;
		buttonCardIndex = 1;
	}else if(data.estadoAutorizacion == 2 ){
		buttonAccionesCardIndex = 2;
		buttonCardIndex = 1;
	}else if(data.estadoAutorizacion == 3 ){//VERIFICAR
		buttonAccionesCardIndex = 1;
	}else if(data.estadoAutorizacion == 4 ){
		buttonAccionesCardIndex = 1;
		buttonCardIndex = 1;
	}else if(data.estadoAutorizacion == 5 ){
		buttonCardIndex = 1;
	}
	
	this.module.updateModel( "CardLayoutComponent", "autorizadorButtonsCardLayout", buttonCardIndex);//Botones inferiores
//	this.module.updateModel( "CardLayoutComponent", "autorizadorButtonsCardLayout", 1);//Botones inferiores
	this.module.updateModel( "CardLayoutComponent", "autorizadorAccionesButtonsCardLayout", buttonAccionesCardIndex);//Botones superiores
	this.module.updateModel( "CardLayoutComponent", "autorizadorCorreccionButtonsCardLayout", buttonCardIndex);
};


AutorizadorController.prototype.estadoTabsConsulta = function(data,componentId){
	//Validar mostrar tab beneficiario o representante legal 
	if(data.informacionBeneficiario==null || data.informacionBeneficiario.curp==null){
		$("#tabButtonbeneficiario").hide()
		$("#panelbeneficiario").hide();
	}else if (data.informacionBeneficiario!=null){
		 if(data.informacionBeneficiario.parentesco!=""){
			 $("#representanteLabel").hide();
		 }else if(data.informacionBeneficiario.parentesco==""){
			 $("#beneficiarioText").hide();
		 }
	}
	
	//Validar mostrar tab informacion adicional 
	if((data.gridDocumentosNssAdicionales==null || data.gridDocumentosNssAdicionales.data==null)&&
	    (data.gridDocumentosAdicionales ==null|| data.gridDocumentosAdicionales.data ==null ) && 
	    (data.gridDocumentosBeneficiarioAdicionales ==null || data.gridDocumentosBeneficiarioAdicionales.data ==null)){
		$("#tabButtoninfoAdicional").hide();
		$("#panelinfoAdicional").hide();
		$("#grid_gridDoctosAdicionalesAsegurado").hide();
		$("#grid_gridDocumentosNssAdicional").hide();
		$("#grid_gridDocumentosBeneficiarioAdicionales").hide();
	}
	
}


AutorizadorController.prototype.colorCampos = function(){
//  this.module.view.correccionDatosUI.colorCampos();
};
AutorizadorController.prototype.mostrarDiferentes = function(){
	this.module.view.correccionDatosUI.mostrarSinCambios();
};
AutorizadorController.prototype.tamanioGrid = function(){
	  this.module.view.cambiosAutorizarUI.tamanioGrid();
};

AutorizadorController.prototype.postFetchConsultaSolicitudComponent = function(){
	this.module.render.componentTemplates['ConsultaSolicitudComponent'].validarCurpsHistoricas(this.module.controller.model.consultaSolicitud.gridsNSS, this.module.controller.model.consultaSolicitud.informacionBeneficiario);
	this.module.render.componentTemplates['ConsultaSolicitudComponent'].tamanioCamposConsultaSolicitud();
};

AutorizadorController.prototype.cambioTipoNSS = function(){
  // RN42 Tipo de NSS vs Tipo de Correccion
  var tipoNSS = $("#tipoCorreccion").val();
  this.module.updateModel("CardLayoutComponent","correccionesCardLayout", tipoNSS);
  // Si se cambia el tipo de NSS se limpian las opciones de los checks
  var that = this;
  $.each( this.model.gruposCorreccionLabels, function (key, value) {
     var matches = $.find("#FormPanelComponent-correccionDatos :input[name='grupoCorreccion."  + key + "']");
     if (matches.length === 1) {
       if (matches[0].type === "checkbox") {
         $("#" + matches[0].id).prop('checked', false);
         $("#" + matches[0].id).change();
         if( !that.module.render.isEmpty( that.model.consultaSolicitud.gridNSS.data[that.model.currentNSSIndex].grupoCorreccion ) ){
            that.model.consultaSolicitud.gridNSS.data[that.model.currentNSSIndex].grupoCorreccion[key] = false;
          }
       }
     }
  });
};


AutorizadorController.prototype.iniciar = function(){
  // Actualizar los tipos de regularizacion, si es correcto
  // mostrar la pantalla de inicio tramite
  
  this.module.validator.formToModel("FormPanelComponent-consultaSolicitud",
   this.model.consultaSolicitud );  
  
  this.model.currentNSSIndex = 0;
  this.model.detalle = this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex];
  this.module.view.correccionDatosUI.prepararGruposCorrecion();
  
//  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 2);
  this.module.controller.transition("cambiosAutorizarUI");
  
};

AutorizadorController.prototype.regresarCorreccion = function(){
	$("#modal").modal('show');
//    this.module.updateModel("CardLayoutComponent","responsableCardLayout", 7);
	this.module.controller.transition("correccionDatosUI");
    $("#modal").modal('hide');
};

AutorizadorController.prototype.siguienteCorreccion = function(){
  // Guardar el modelo los datos actuales de la forma antes de hacer el cambio
//  this.module.validator.formToModel("FormPanelComponent-correccionDatos",
//   this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex] );
//  
//  if( this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex].tipoNSS.idTipoNSSCorreccion < 1 ){
//    this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"Indique el tipo de NSS"} );
//    return;
//  }
  // Validar que tenga alguna opcion seleccionada
//  var valid = false;
//  if( this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex].grupoCorreccion !== null 
//    && typeof this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex].grupoCorreccion === 'object' ){
//    var that = this;
//    $.each( this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex].grupoCorreccion, function (key, value) {
//      if( that.model.consultaSolicitud.gridNSS.data[that.model.currentNSSIndex].grupoCorreccion[key] === true || that.model.consultaSolicitud.gridNSS.data[that.model.currentNSSIndex].grupoCorreccion[key] === "true"){
//        valid = true;
//      }
//    });
//  }
//  if( !valid ){
//    this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"Seleccione un tipo de correci\u00F3n"} );
//    return;
//  }else{
//    this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:""} );
//  }
//  
//  if( this.model.consultaSolicitud.gridNSS.data.length > (this.model.currentNSSIndex+1) ){
//    this.model.currentNSSIndex++;
//    this.model.detalle = this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex];
//    this.module.render.notify("FormPanelComponent", "panelCorreccionDatos", "detalle");
//  }else{
//    this.model.resumenCorreccion = this.model.consultaSolicitud.gridNSS.data;
//    this.module.render.notify("ResumenCorreccionComponent", "resumenCorreccion", "resumenCorreccion");
//    this.module.render.notify("FormPanelComponent", "panelCorreccionDatos", "detalle");
//    $('#confirmarModal').modal();
//  }
	
	if(origendbtab==undefined)
	{
		origendbtab ='canase';
	}
	ResponsableController.prototype.guardaractualizacionNSS(origendbtab);
	this.model.resumenCorreccion = this.model.consultaSolicitud.gridsNSS;
//this.module.render.notify("ResumenCorreccionComponent", "resumenCorreccion", "resumenCorreccion");
//$("#modal").modal('show');
//	this.module.updateModel("CardLayoutComponent","responsableCardLayout", 15);
	this.module.controller.transition("bandejaCuentaIndividualResumenUI");

	$("#modal").modal('hide');
};

//pjjt
AutorizadorController.prototype.iniciarCorreccion = function(){
  //FIX: Solo se usa el tipoNSS Correccion
  this.model.detalle.tipoNSS = "1";
//  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 3);
  this.module.controller.transition("reasignacionUI");
  if( this.model.consultaSolicitud.gridNSS.data.length === (this.model.currentNSSIndex+1) ){
      $("#btnSiguiente").html("Finalizar");
  }
};
	
AutorizadorController.prototype.selectTramite = function(index){
  this.module.service.fetchSolicitud("consultaSolicitud",this.module, index);
  this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );
};

AutorizadorController.prototype.selectHistorico = function(index){
	this.module.service.fetchSolicitudHistorico("consultaSolicitud",this.module, index);
	this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );  
};

AutorizadorController.prototype.nuevaSolicitud = function(){
  
};

AutorizadorController.prototype.init = function(){
    this.model = {
      userProfile:{},
      tiposNSS:[
        { key: "1", value: "Certificador"}
      ],
      tiposCorreccion:["Certificador","Corresponde a otra persona","Asociado al titular"],
      gruposCorreccionLabels:{
        nombre:"Correcci\u00F3n de nombre",
        datosEstadisticos:"Correcci\u00F3n de datos estad\u00EDsticos",
        regularizarCuentaIndividual:"Regularizar cuenta individual",
        duplicidad:"Cancelado por duplicidad",
        noExisteCanase:"No existe en CANASE",
        homonimio:"Corresponde a un Hom\u00F3nimio",
        otroAsegurado:"Corresponde a otro asegurado"
      },
      cancelar:{},
      solicitarInformacion:{},
      reasignacion:{},
      rechazar:{},
      correccionDatosAutorizar:{},
      correccionDatosLectura:{},
      confirmarCorreccionDatosAutorizar:{},
      confirmarCorreccionDatosLectura:{}
    };
    this.consultaSolicitudController.model = this.model;
    this.reasignacionController.model = this.model;
    this.solicitarInformacionController.model = this.model;
    this.cambiosAutorizarController.model = this.model;
    this.rechazarController.model = this.model;
    this.module.render.draw();
    this.module.service.getUserProfile(this.module);
	this.cancelarController.model = this.model;
};

AutorizadorController.prototype.salir = function(){
	  this.module.service.salir();
};
AutorizadorController.prototype.salirCancelar = function(){
	  this.module.service.salirCancelar();
};
AutorizadorController.prototype.salirAutorizar = function(){
	  this.module.service.salirAutorizar();
};
AutorizadorController.prototype.autorizarSindo = function(){
	  this.module.service.autorizarSindo();
};

AutorizadorController.prototype.salirAceptar = function(){
	module.updateModel("UserProfileComponent","userProfile", {});
	this.module.service.salirAceptar();
};

AutorizadorController.prototype.estatus = function(index){  
  this.module.service.fetchEstatus("bitacora",this.module, index);
  this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );
};

AutorizadorController.prototype.estatusHistorico = function(index){  
	  this.module.service.fetchEstatusHistorico("bitacora",this.module, index);
	  this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );
};

AutorizadorController.prototype.filtros = function(){
	this.model.filtros = {};
	this.module.validator.formToModel("FormPanelComponent-filter", this.model.filtros );
	this.model.filtrosBusqueda = this.model.filtros;
	this.module.service.fetchFiltros("gridTramites","gridHistorico",this.model);
	this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );
};

AutorizadorController.prototype.filtros = function(grid){
	this.model.filtros = {};
	this.module.validator.formToModel("FormPanelComponent-filter", this.model.filtros );
	this.model.filtrosBusqueda = this.model.filtros;
	console.log("Cambio el fetchFiltros de autorizador");

	if (grid === "buscar") {
		if($('#li-tabs-panelTabsBandejas-0').attr('class').trim() == "active"){
			this.module.service.fetchFiltros("gridTramites","",this.model);
		} else {
			this.module.service.fetchFiltros("","gridHistorico",this.model);
		}
	} else if(grid === "tramites"){
		this.module.service.fetchFiltros("gridTramites","",this.model);
	}else if ("historico") {
		this.module.service.fetchFiltros("","gridHistorico",this.model);
	}

	this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );
};

AutorizadorController.prototype.siguientePersona = function(){
//	var isLast = false;
//	var isFirst = true;
//	if(this.model.currentPersonaNSSIndex + 1 < this.model.detalle.informacionFuentesNSS.length){
//		this.model.currentPersonaNSSIndex++;
//		this.model.detalle.informacionBDTU = this.model.detalle.informacionFuentesNSS[this.model.currentPersonaNSSIndex];
//		this.module.render.notify("FormPanelComponent", "panelCorreccionDatos", "detalle");
//		this.module.updateModel("NavegacionPersonaNSSComponent","navegacionPersonaNSS", "detalle");
//		isFirst = false;
//	}
//	if(this.model.currentPersonaNSSIndex + 1 >= this.model.detalle.informacionFuentesNSS.length){
//		isLast = true;
//	}
//	$("#btnSiguientePersona").prop('disabled',isLast);
//	$("#btnAnteriorPersona").prop('disabled',isFirst);
};

AutorizadorController.prototype.anteriorPersona = function(){
//	var isLast = true;
//	var isFirst = false;
//	if(this.model.currentPersonaNSSIndex - 1  >= 0){
//		this.model.currentPersonaNSSIndex--;
//		this.model.detalle.informacionBDTU = this.model.detalle.informacionFuentesNSS[this.model.currentPersonaNSSIndex];
//		this.module.render.notify("FormPanelComponent", "panelCorreccionDatos", "detalle");
//		this.module.updateModel("NavegacionPersonaNSSComponent","navegacionPersonaNSS", "detalle");
//		isLast = false;
//	}
//	if(this.model.currentPersonaNSSIndex - 1 < 0){
//	}
//	$("#btnSiguientePersona").prop('disabled',isLast);
//	$("#btnAnteriorPersona").prop('disabled',isFirst);
};

AutorizadorController.prototype.cancelarEnvioSindo = function(){
	  this.module.service.cancelarEnvioSindo();
};

AutorizadorController.prototype.continuarAtencionResumen = function(){
	if(origendbtab==undefined)
	{
		origendbtab ='canase';
	}
if(origendbtab.fuente == undefined )
{
	origenDB = (origendbtab).toUpperCase(); 
}
else {origenDB = (origendbtab.fuente).toUpperCase(); }

var nombredat=$('#informacion'+origenDB+'_nombre').val();
var apellidoPdata=$('#informacion'+origenDB+'_apellidoPaterno').val();
var apellidoMdata=$('#informacion'+origenDB+'_apellidoMaterno').val();
var curpdata=$('#informacion'+origenDB+'_curp').val();
var sexodata=$('#informacion'+origenDB+'_sexo').val();
var fechaNacimientodata=$('#informacion'+origenDB+'_fechaNacimiento').val();
var lugarNacimientodata=$('#informacion'+origenDB+'_lugarNacimiento').val();
var nacionalidaddata=$('#informacion'+origenDB+'_nacionalidad').val();
var documentosdata=$('#informacion'+origenDB+'_datosDocumentoProbatorio').val();
//Checkbox
var noCanasee =origendbtab.NoCanasee;
var asociadoCertificado = origendbtab.asociadoCertificado;
var canceladoDuplicidad = origendbtab.canceladoDuplicidad;
var carrecEstad = origendbtab.carrecEstad;
var certificado = origendbtab.certificado;
var correcNombre = origendbtab.correcNombre;
var fuente = origendbtab.correcNombre;
var homonimo = origendbtab.homonimo;
var nocanase = origendbtab.nocanase;
var otraPersona = origendbtab.otraPersona;
var otroAsegu = origendbtab.otroAsegu;
//idTramite


datosModificados = new Object();
datosModificados.curp = $('#informacion'+origenDB+'_curp').val();
datosModificados.apellidoPaterno = $('#informacion'+origenDB+'_apellidoPaterno').val();
datosModificados.apellidoMaterno = $('#informacion'+origenDB+'_apellidoMaterno').val();
datosModificados.nombre = $('#informacion'+origenDB+'_nombre').val();
datosModificados.sexo = $('#informacion'+origenDB+'_sexo').val();
datosModificados.fechaNacimiento = $('#informacion'+origenDB+'_fechaNacimiento').val();
datosModificados.lugarNacimiento = $('#informacion'+origenDB+'_lugarNacimiento').val();
datosModificados.nacionalidad = $('#informacion'+origenDB+'_nacionalidad').val();
datosModificados.documentos = $('#informacion'+origenDB+'_datosDocumentoProbatorio').val();
datosModificados.pertenecebd = origenDB;

if($('#slide1:checked').val()!=undefined)
	{
	 datosModificados.tipoNSS = "certificador";
	}
else if ($('#slide2:checked').val()!=undefined)
	{
		datosModificados.tipoNSS = "asociadoCertificador";
	}
else if ($('#slide3:checked').val()!=undefined)
	{
		datosModificados.tipoNSS = "otraPersona";
	}


datosCDAModificado = [];
datosCDAModificado.push(datosModificados);


var idTramitePrincipal = idTramite;
//	 this.module.updateModel("CardLayoutComponent","responsableCardLayout", 16);
this.module.controller.transition("confirmarUI");
};

AutorizadorController.prototype.envioSindo = function(){
          $("#modalAutorizar").modal('hide');	
	  this.model.confirmar = { folio: this.module.controller.model.consultaSolicitud.folio};
	  this.model.confirmar.idTramite = this.module.controller.model.consultaSolicitud.idTramite;
	  this.model.confirmar.idTarea = this.module.controller.model.consultaSolicitud.idTarea;
          this.model.confirmar.tareasTramites = obtenerTareasTramites(this.module.controller.model.consultaSolicitud.folio);
	  this.model.confirmar.folio = this.module.controller.model.consultaSolicitud.folio;
	  this.model.confirmar.fechaInicio = this.module.controller.model.consultaSolicitud.fechaInicio;
	  this.model.confirmar.nombreCompletoAutorizador = this.module.controller.model.userProfile.nombreCompleto;
	  this.model.confirmar.nss = this.module.controller.model.consultaSolicitud.nss;
	  this.model.confirmar.informacionRENAPO = this.module.controller.model.consultaSolicitud.informacionRENAPO;
	  this.module.service.confirmar();
	  this.module.service.cancelarEnvioSindo();	  
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
};

AutorizadorController.prototype.reportes = function(){
	this.module.service.reportes();
};

AutorizadorController.prototype.clean = function(){
	this.model.filtros = {};
	this.model.filtrosBusqueda = this.model.filtros;
	this.module.validator.formToModel("FormPanelComponent-filter", this.model.filtros );  	
	this.module.service.cleanFiltros("gridTramites","",this.model);
	this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );
};

AutorizadorController.prototype.showBtnReportes = function(){
	if ($('#divCollapse').hasClass('in')){
		document.getElementById("spanmenuBandeja").classList.remove("glyphicon-chevron-down");
    	document.getElementById("spanmenuBandejaOposite").classList.remove("glyphicon-chevron-up");
    	document.getElementById("spanmenuBandeja").classList.add("glyphicon-chevron-up");
    	document.getElementById("spanmenuBandejaOposite").classList.add("glyphicon-chevron-down");
		$("#panelBtnReportes").show();
    }else{
    	document.getElementById("spanmenuBandeja").classList.remove("glyphicon-chevron-up");
		document.getElementById("spanmenuBandejaOposite").classList.remove("glyphicon-chevron-down");
		document.getElementById("spanmenuBandeja").classList.add("glyphicon-chevron-down");
		document.getElementById("spanmenuBandejaOposite").classList.add("glyphicon-chevron-up");
		$("#panelBtnReportes").hide();
    }
	
	this.module.validator.formToModel("FormPanelComponent-filter", this.model.filtros );
	this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );
	
};

AutorizadorController.prototype.disableNombre = function(){
	if($("#foliosAsociados").prop('checked')){
		$("#curp").val("");
		$("#curp").prop('disabled',true);
	}else {
		$("#curp").prop('disabled',false);
	} 
};

AutorizadorController.prototype.disableFilter = function(){
	this.model.filtros = {};
	this.module.validator.formToModel("FormPanelComponent-filter", this.model.filtros );
	this.module.service.disableElement(this.model.filtros);
};

/*
 * Moviemiento del Slaider hacia la izquierda
 */
var mov=0;
AutorizadorController.prototype.siguienteNSS = function(){
	var valor='';
	mov = mov -101; 
	valor = mov+'px';
	$('#contBoton').css('left', valor );
};

/*
 * Moviemiento del Slaider hacia la derecha
 */
AutorizadorController.prototype.anteriorNSS = function(){
	if(mov != 0){
		var valor='';
		mov = mov +101; 
		valor = mov+'px';
		 $('#contBoton').css('left', valor );
		}
};


	/*
	 * Consulta la informacion y llena los campos de todas las fuentes
	 */
//var datosfuentesprincipal;
//var datosCDA;
AutorizadorController.prototype.obtenerDatosNSSinicio = function(nss){
		var maxNSSvalores=0;
		var continua = true;
		var contadorr=0;
		while(continua)
		{			
			if($('#sliderNSS').find("#btonselect"+contadorr).length){
				maxNSSvalores = maxNSSvalores +1 ;
				$("#btonselect"+contadorr).css('background-color', '#FFF' );
				$("#btonselect"+contadorr).css('color', '#000' );
			}
			else
			{
				continua = false;
				contadorr = contadorr - 1;
			}
			contadorr = contadorr + 1;
		}		
		
		var url = "correccionDatosAseguradoNSS.do";
		$("#numeroNSS").text(nss.innerText);
		if (url !== null) {
			var nss0;
			if(nss.innerText!=undefined){
				nss0 = nss.innerText;
				}
			else{
					nss0 = nss;
				}
		for(var reccorreboton=0;reccorreboton < maxNSSvalores ;reccorreboton++){
			if($("#btonselect"+reccorreboton).val()==nss0){
				$("#btonselect"+reccorreboton).css('background-color', '#045FB4' );
				$("#btonselect"+reccorreboton).css('color', '#FFF' );
			}
		}			
				var documentos="";
				var params =JSON.stringify(eval({nss:nss0}));
				for(var x=0;x<6; x++)
				{
					var bd=['CANASE','CIZ1','CIZ2','CIZ3','HISTORICO','BDTU'];
					$("#informacion"+bd[x]+"_curp").val("");									
					$("#informacion"+bd[x]+"_apellidoPaterno").val("");									
					$("#informacion"+bd[x]+"_apellidoMaterno").val("");									
					$("#informacion"+bd[x]+"_nombre").val("");									
					$("#informacion"+bd[x]+"_sexo").val("");									
					$("#informacion"+bd[x]+"_fechaNacimiento").val("");									
					$("#informacion"+bd[x]+"_lugarNacimiento").val("");									
					$("#informacion"+bd[x]+"_nacionalidad").val("");	
				}
			
				$.ajax({
						url : url,
						type : 'POST',
						async : false,
						dataType : 'json',
						contentType : "application/json; charset=utf-8",
						data : params,
						success : function(data) {
							
							var i = 0;
							var canase=0;
							var max = data.length;
							for( i=0 ; i< data.length ; i++)
								{			
									var pertenecebd =data[i].pertenecebd;
									if(pertenecebd ==="SINDO CIZ 3"){pertenecebd = "CIZ3"}
									else if(pertenecebd ==="SINDO CIZ 2"){pertenecebd = "CIZ2"}
									else if(pertenecebd ==="SINDO CIZ 1"){pertenecebd = "CIZ1"}
									else if(pertenecebd ==="HIST&Oacute;RICO CENTRAL"||pertenecebd ==="HIST&Oacute;RICO"){pertenecebd = "HISTORICO"}
									
									$("#informacion"+pertenecebd+"_curp").val(data[i].curp);									
									$("#informacion"+pertenecebd+"_apellidoPaterno").val((data[i].apellidoPaterno).toUpperCase());									
									$("#informacion"+pertenecebd+"_apellidoMaterno").val((data[i].apellidoMaterno).toUpperCase());									
									$("#informacion"+pertenecebd+"_nombre").val((data[i].nombre).toUpperCase());									
									$("#informacion"+pertenecebd+"_sexo").val((data[i].sexo).toUpperCase());									
									$("#informacion"+pertenecebd+"_fechaNacimiento").val(data[i].fechaNacimiento);									
									$("#informacion"+pertenecebd+"_lugarNacimiento").val(data[i].lugarNacimiento);									
									$("#informacion"+pertenecebd+"_nacionalidad").val(data[i].nacionalidad);	
									documentos = data[i].datosDocumentoProbatorio;
									documentos = documentos.replace("Ano","año");
									documentos = documentos.replace("Numero: de Acta","N�mero: de Acta");
									documentos = documentos.replace("Numero de Libro","N�mero de Libro"); 
									documentos = documentos.replace("Numero de Foja","N�mero de Foja"); 
									$("#informacion"+pertenecebd+"_datosDocumentoProbatorio").val(documentos);				
									
									datosfuentesprincipal = new Object();
									datosfuentesprincipal.curp = data[i].curp;
									datosfuentesprincipal.apellidoPaterno = data[i].apellidoPaterno;
									datosfuentesprincipal.apellidoMaterno = data[i].apellidoMaterno;
									datosfuentesprincipal.nombre = data[i].nombre;
									datosfuentesprincipal.sexo = data[i].sexo;
									datosfuentesprincipal.fechaNacimiento = data[i].fechaNacimiento;
									datosfuentesprincipal.lugarNacimiento = data[i].lugarNacimiento;
									datosfuentesprincipal.nacionalidad = data[i].nacionalidad;
									datosfuentesprincipal.pertenecebd = data[i].pertenecebd;
									datosfuentesprincipal.documentos = data[i].datosDocumentoProbatorio;
									datosfuentesprincipal.nss= nss0;
									
									if($('#slide1:checked').val()!=undefined)
									{
										datosfuentesprincipal.tipoNSS = "certificador";
									}
								else if ($('#slide2:checked').val()!=undefined)
									{
									datosfuentesprincipal.tipoNSS = "asociadoCertificador";
									}
								else if ($('#slide3:checked').val()!=undefined)
									{
									datosfuentesprincipal.tipoNSS = "otraPersona";
									}
									
									datosCDA = [];
									datosCDA.push(datosfuentesprincipal);									
								}							
								ResponsableController.prototype.validarNSS();
								//$('#ErrorCANASE').modal();
//								$('#ErrorCANASE').modal('show');
						},
						error : function(error) {
							
							alert("No se pudo obtener la informaicon");							
							return false; 
//							this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"No se pudo obtener la informaicon"} );

						}
					});
			}				
		};
		
		
		/*
		 * Valida similitudes de los datos entre las fuentes
		 * y los datos de renapo
		 */
		
		AutorizadorController.prototype.validarNSS = function(){
			var pertenecebdARREGLO=["CANASE","CIZ1","CIZ2","CIZ3","HISTORICO","BDTU"];
			var pertenecebd;
			
			
			if($("#informacionCANASE_curp").val() === "" && $("#informacionCANASE_apellidoPaterno").val() === "" && $("#informacionCANASE_apellidoMaterno") ==="")
				
				{
				this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"No se pudo obtener la informaicon"} );
				}
					
				
			for(var i=0 ; i<6 ; i++)
				{
				pertenecebd=pertenecebdARREGLO[i];
				if(!( $("#informacion"+pertenecebd+"_curp").val().trim() ===""&& $("#informacion"+pertenecebd+"_apellidoPaterno").val().trim()===""
						&& $("#informacion"+pertenecebd+"_apellidoMaterno").val().trim()===""))
				{
					if($("#informacionRENAPO_curp").val() === $("#informacion"+pertenecebd+"_curp").val().trim()) {
				        $("#informacion"+pertenecebd+"_curp").attr("disabled", true).css("background-color","#FFF");
				    } else{
				        $("#informacion"+pertenecebd+"_curp").attr("disabled", false).css("background-color","#FE2E2E");
				        $("#slide1 input[type=checkbox]").prop('checked', false);
				    }
				
				 if($("#informacionRENAPO_apellidoPaterno").val() == $("#informacion"+pertenecebd+"_apellidoPaterno").val()) {
				        $("#informacion"+pertenecebd+"_apellidoPaterno").attr("disabled", true).css("background-color","#FFF");
				    } else{
				        $("#informacion"+pertenecebd+"_apellidoPaterno").attr("disabled", false).css("background-color","#FE2E2E");			     
				    }
				
				if($("#informacionRENAPO_apellidoMaterno").val() == $("#informacion"+pertenecebd+"_apellidoMaterno").val()) {
			        $("#informacion"+pertenecebd+"_apellidoMaterno").attr("disabled", true).css("background-color","#FFF");
			    } else{
			        $("#informacion"+pertenecebd+"_apellidoMaterno").attr("disabled", false).css("background-color","#FE2E2E");		       
			    }
				
				if($("#informacionRENAPO_nombre").val() == $("#informacion"+pertenecebd+"_nombre").val()) {
			        $("#informacion"+pertenecebd+"_nombre").attr("disabled", true).css("background-color","#FFF");
			    } else{
			        $("#informacion"+pertenecebd+"_nombre").attr("disabled", false).css("background-color","#FE2E2E");		       
			    }
				
				if($("#informacionRENAPO_sexo").val() == $("#informacion"+pertenecebd+"_sexo").val()) {
			        $("#informacion"+pertenecebd+"_sexo").attr("disabled", true).css("background-color","#FFF");
			    } else{
			        $("#informacion"+pertenecebd+"_sexo").attr("disabled", false).css("background-color","#FE2E2E");
			    }
				
				if($("#informacionRENAPO_fechaNacimiento").val() == $("#informacion"+pertenecebd+"_fechaNacimiento").val()) {
			        $("#informacion"+pertenecebd+"_fechaNacimiento").attr("disabled", true).css("background-color","#FFF");
			    } else{
			        $("#informacion"+pertenecebd+"_fechaNacimiento").attr("disabled", false).css("background-color","#FE2E2E");
			    }
				
				if($("#informacionRENAPO_lugarNacimiento").val() == $("#informacion"+pertenecebd+"_lugarNacimiento").val()) {
			        $("#informacion"+pertenecebd+"_lugarNacimiento").attr("disabled", true).css("background-color","#FFF");
			    } else{
			        $("#informacion"+pertenecebd+"_lugarNacimiento").attr("disabled", false).css("background-color","#FE2E2E");
			    }
				
				if($("#informacionRENAPO_nacionalidad").val() == $("#informacion"+pertenecebd+"_nacionalidad").val()) {
			        $("#informacion"+pertenecebd+"_nacionalidad").attr("disabled", true).css("background-color","#FFF");
			    } else{
			        $("#informacion"+pertenecebd+"_nacionalidad").attr("disabled", false).css("background-color","#FE2E2E");
			    }
				
				if($("#informacionRENAPO_datosDocumentoProbatorio").val() == $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").val()) {
			        $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", true).css("background-color","#FFF");
			    } else{
			        $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", false).css("background-color","#FE2E2E");
			    }
				}				
				}			 
		};	
			
			AutorizadorController.prototype.informaciontemp = function(tabDB){
				
				$("#informacion"+pertenecebd+"_curp").val(data[i].curp);									
				$("#informacion"+pertenecebd+"_apellidoPaterno").val(data[i].apellidoPaterno);									
				$("#informacion"+pertenecebd+"_apellidoMaterno").val(data[i].apellidoMaterno);									
				$("#informacion"+pertenecebd+"_nombre").val(data[i].nombre);									
				$("#informacion"+pertenecebd+"_sexo").val(data[i].sexo);									
				$("#informacion"+pertenecebd+"_fechaNacimiento").val(data[i].fechaNacimiento);									
				$("#informacion"+pertenecebd+"_lugarNacimiento").val(data[i].lugarNacimiento);									
				$("#informacion"+pertenecebd+"_nacionalidad").val(data[i].nacionalidad);									
				$("#informacion"+pertenecebd+"_datosDocumentoProbatorio").val(data[i].pertenecebd);	
								
			};
			
			/*
			 * Obtiene lo valores del menu
			 */
			var origentab="canase";
			var origendbtab;
			AutorizadorController.prototype.informaciontempCheck = function(tabDB){
				if(origendbtab == undefined )
					{
					origendbtab= origentab;
					}				
				var tabDBo = origendbtab;
				var cda = new Object();
				//certificador;
				if($('#slide1:checked').val()==undefined)
				{
					cda.certificado="0";
				}
				else{cda.certificado=$('#slide1:checked').val();}
				//Asociado al certificado;
				if($('#slide2:checked').val()==undefined)
				{
					cda.asociadoCertificado="0";
				}
				else{cda.asociadoCertificado=$('#slide2:checked').val();}
				
				//Asociado a otra persona;
				if($('#slide3:checked').val()==undefined)
				{
					cda.otraPersona="0";
				}
				else{cda.otraPersona=$('#slide3:checked').val();}
				
				//No existe en canase;
				if($('#slide4:checked').val()==undefined)
				{
					cda.nocanase="0";
				}
				else{cda.nocanase=$('#slide4:checked').val();}
				
				//Cancelado por duplicidad;
				if($('#slide5:checked').val()==undefined)
				{
					cda.canceladoDuplicidad="0";
				}
				else{cda.canceladoDuplicidad=$('#slide5:checked').val();}
				
				//Corresponde a un homonimo;
				if($('#slide6:checked').val()==undefined)
				{
					cda.homonimo="0";
				}
				else{cda.homonimo=$('#slide6:checked').val();}
				
				//No existe en canase;
				if($('#slide7:checked').val()==undefined)
				{
					cda.NoCanasee="0";
				}
				else{cda.NoCanasee=$('#slide7:checked').val();}
				
				//Corresponde a otro asegurado;
				if($('#slide8:checked').val()==undefined)
				{
					cda.otroAsegu="0";
				}
				else{cda.otroAsegu=$('#slide8:checked').val();}
				
				
				//Correccion de nombre;
				if($('#slide9:checked').val()==undefined)
				{
					cda.correcNombre="0";
				}
				else{cda.correcNombre=$('#slide9:checked').val();}
				
				//Correccion de datos estadisticos;
				if($('#slide10:checked').val()==undefined)
				{
					cda.carrecEstad="0";
				}
				else{cda.carrecEstad=$('#slide10:checked').val();}
				
				cda.fuente=origendbtab;
//				// console.log(cda);
				origendbtab=tabDB.parentNode.firstChild.id;
				
//				guarada informacion
							
				module.controller.guardaractualizacionNSS(cda);
				
			};
			
			
			AutorizadorController.prototype.guardaractualizacionNSS = function(cdaCheck){
//					this.module.service.datoNSS();
//					var params = this.module.controller.model.solicitarInformacion;
				var origenDB = (cdaCheck.fuente).toUpperCase(); 
			
				var nombredat=$('#informacion'+origenDB+'_nombre').val();
				var apellidoPdata=$('#informacion'+origenDB+'_apellidoPaterno').val();
				var apellidoMdata=$('#informacion'+origenDB+'_apellidoMaterno').val();
				var curpdata=$('#informacion'+origenDB+'_curp').val();
				var sexodata=$('#informacion'+origenDB+'_sexo').val();
				var fechaNacimientodata=$('#informacion'+origenDB+'_fechaNacimiento').val();
				var lugarNacimientodata=$('#informacion'+origenDB+'_lugarNacimiento').val();
				var nacionalidaddata=$('#informacion'+origenDB+'_nacionalidad').val();
				var documentosdata=$('#informacion'+origenDB+'_datosDocumentoProbatorio').val();
				//Checkbox
				var noCanasee =cdaCheck.NoCanasee;
				var asociadoCertificado = cdaCheck.asociadoCertificado;
				var canceladoDuplicidad = cdaCheck.canceladoDuplicidad;
				var carrecEstad = cdaCheck.carrecEstad;
				var certificado = cdaCheck.certificado;
				var correcNombre = cdaCheck.correcNombre;
				var fuente = cdaCheck.correcNombre;
				var homonimo = cdaCheck.homonimo;
				var nocanase = cdaCheck.nocanase;
				var otraPersona = cdaCheck.otraPersona;
				var otroAsegu = cdaCheck.otroAsegu;
				//idTramite
				var idTramitePrincipal = idTramite;
				
					var url = "guardardoDatosAseguradoNSS.do";
					if (url !== null) {					
//						var params =JSON.stringify(eval({listadatosNSS:{nombre:nombredat,apellidopaterno:apellidoPdata,apellidomaterno:apellidoMdata,curp:curpdata,
//							sexo:sexodata,fechaNacimiento:fechaNacimientodata,lugarNacimiento:lugarNacimientodata,nacionalidad:nacionalidaddata,Documentos:Documentosdata}}));
						var params =JSON.stringify(eval({nombredat:nombredat,apellidoPdata:apellidoPdata,apellidoMdata:apellidoMdata,
							curpdata:curpdata,sexodata:sexodata,fechaNacimientodata:fechaNacimientodata,lugarNacimientodata:lugarNacimientodata,
							nacionalidaddata:nacionalidaddata,documentosdata:documentosdata,noCanasee:noCanasee,asociadoCertificado:asociadoCertificado,
							canceladoDuplicidad:canceladoDuplicidad,carrecEstad:carrecEstad,certificado:certificado,correcNombre:correcNombre,
							fuente:fuente,homonimo:homonimo,nocanase:nocanase,otraPersona:otraPersona,otroAsegu:otroAsegu,origenDB:origenDB,
							idTramitePrincipal:idTramitePrincipal}));
						}
						if(nombredat != undefined &&apellidoPdata !=undefined && curpdata !=undefined && sexodata != undefined)
							{
							$.ajax({
								url : url,
								type : 'POST',
								async : false,
								dataType : 'json',
								contentType : "application/json; charset=utf-8",
								data : params,
								success : function(data) {
									
				  	          										
								},
								error : function(error) {
									fnProcesarErrores(error,"form#informacionHistoriaLaboralForm");
									$.unblockUI();
								}
							});		
							}
						
				};

				
				
				/*
				 * Valida y da los movimineto 
				 * de panel de homonimia certificador 
				 */
			
				AutorizadorController.prototype.validarCheckboxMenu = function(basedatos, maxNSS){
				//RNF-UE-15
					if(!($("#informacionRENAPO_curp").val()==$("#informacion"+basedatos+"_curp").val())||
							!($("#informacionRENAPO_sexo").val()==$("#informacion"+basedatos+"_sexo").val())||
							!($("#informacionRENAPO_fechaNacimiento").val()==$("#informacion"+basedatos+"_fechaNacimiento").val())||
							!($("#informacionRENAPO_lugarNacimiento").val()==$("#informacion"+basedatos+"_lugarNacimiento").val()
							))
						{
							$("#slide10 input[type=checkbox]").prop('checked', true);
							$("#labelslide10").css("left", "23px");
						}
					else
						{
						$("#slide10 input[type=checkbox]").removeAttr('checked');
						$("#labelslide10").css("left", "0px");
						}
					
					//RNF-UE-16
					if(!($("#informacionRENAPO_apellidoPaterno").val()==$("#informacion"+basedatos+"_apellidoPaterno").val()||
							$("#informacionRENAPO_apellidoMaterno").val()==$("#informacion"+basedatos+"_apellidoMaterno").val()||
							$("#informacionRENAPO_nombre").val()==$("#informacion"+basedatos+"_nombre").val()))
						{
						
							$("#slide9 input[type=checkbox]").prop('checked', true);
							$("#labelslide9").css("left", "23px");
						}
					else
						{
						$("#slide9 input[type=checkbox]").removeAttr('checked');
						$("#labelslide9").css("left", "0px");
						}
					
					//RNF-UE-17
					if(maxNSS == 1)
						{
							$("#slide1 input[type=checkbox]").prop('checked', true);
							$("#labelslide1").css("left", "23px");
						}
					else
						{
						$("#slide1 input[type=checkbox]").removeAttr('checked');
						$("#labelslide1").css("left", "0px");
						}
					//RNF-UE-18
					if($("#informacionCANASE_curp").val()==""&&
							$("#informacionCANASE_sexo").val()==""&&
							$("#informacionCANASE_fechaNacimiento").val()==""&&
							$("#informacionCANASE_lugarNacimiento").val()==""&&
							$("#informacionRENAPO_apellidoPaterno").val()==""&&
							$("#informacionRENAPO_apellidoMaterno").val()==""&&
							$("#informacionRENAPO_nombre").val()=="")
						{
							$("#slide4 input[type=checkbox]").prop('checked', true);
							$("#slide7 input[type=checkbox]").prop('checked', true);
							$("#labelslide4").css("left", "23px");
							$("#labelslide7").css("left", "23px");
						}
					else
						{
							$("#slide4 input[type=checkbox]").removeAttr('checked');
							$("#slide7 input[type=checkbox]").removeAttr('checked');
							$("#labelslide4").css("left", "0px");
							$("#labelslide7").css("left", "0px");
						}
					
					//RNF-UE-19
						if( $("#slide3 input[type=checkbox]").prop('checked')   ) {
							$("#slide3 input[type=checkbox]").prop('checked', true);
							$("#labelslide3").css("left", "23px");
						}
					
				else
					{
					$("#slide3 input[type=checkbox]").removeAttr('checked');
					$("#labelslide3").css("left", "0px");
					}
					
					
					
				};
				
AutorizadorController.prototype.accionCheck = function(componentecheck)
	{
		var nombrecomp = componentecheck.name;
		var valor = $("#label"+nombrecomp+"").css("left");
		if(valor == "23px" )
			{
				$("#label"+nombrecomp+"").css("left", "3px");
				$("#label"+nombrecomp+"").removeAttr('checked');
			}
			else $("#label"+nombrecomp+"").css("left", "23px");
				};
				
AutorizadorController.prototype.postFetchCorreccionDatosUI = function(){
//	this.module.view.correccionDatosUI.colorCampos();
//	this.actualizarControles(this.module.view.correccionDatosUI);
};


AutorizadorController.prototype.rechazar = function(){
	  // Abre rechazar

//		this.module.updateModel( "CardLayoutComponent", "responsableCardLayout",5);
	this.module.controller.transition("rechazarUI");
	};
	
	
AutorizadorController.prototype.regresabandejaCuentaIndividual = function(){
	  // Regresa Cuenta Individual

//		this.module.updateModel( "CardLayoutComponent", "responsableCardLayout",14);
	this.module.controller.transition("bandejaCuentaIndividualUI");
	};
	
	
AutorizadorController.prototype.regresarInicioCorreccion = function(){
		
		$("#modal").modal('show');
//		this.module.updateModel("CardLayoutComponent","responsableCardLayout", 1);
		this.module.controller.transition("informacionSolicitud");
		$("#modal").modal('hide');
		
	};


AutorizadorController.prototype.transition = function( screen ){
	  
	  this.screens = {
			  
 
		bandejaSolicitudesUI:{ card: 0},		  
	    informacionSolicitud:{ card: 1},
	    cambiosAutorizarUI:{ card: 2},
	    reasignacionUI:{ card: 3},
	    solicitarInformacionUI:{ card: 4},
	    rechazarUI:{ card: 5},
	    consultaSolicitudReasignarUI:{ card: 6},
	    correccionDatosUI:{ card: 7},
	    bitacoraUI:{ card: 8},
	    cancelarUI:{ card: 9},
	    consultaPreviaUI:{ card: 10},
	    consultaPreviaDetalleUI:{ card: 11},
	    consultaAtencionResponsableUI:{ card: 12},
	    consultaDetalleAtencionResponsableUI:{ card: 13},
	    bandejaCuentaIndividualUI:{ card: 14},
	    bandejaCuentaIndividualResumenUI:{ card: 15},
	    confirmarUI:{ card: 16}, 
	    correccionDatosAutorizar: {card: 17},
	    confirmarCorreccionDatosLectura: {card: 18},
	    confirmarCorreccionDatosAutorizar:{ card:19},
	    correccionDatosConsultaPrevia:{card:20},
	    confirmarCorreccionConsultaPrevia:{ card:21},
        confrontaCuentaIndividual:{ card:22},
        consultaCuentaIndividualAutorizador:{ card:23},
	    confrontaCuentaIndividualResponsable:{card:24},
        consultaCuentaIndividualResponsable:{card:25},
	    confrontaCuentaIndividualConsultaPrevia:{card:26},
        consultaCuentaIndividualConsultaPrevia:{card:27}
	  };
	  // console.log("Pasando a pantalla: " + this.screens[screen].card);
	  this.module.updateModel("AlertComponent","alert", {});    
	  this.module.updateModel("CardLayoutComponent", "responsableCardLayout" , this.screens[screen].card);
};

if ( screen === "informacionSolicitud" && this.model.consultaSolicitud !== null ) {
	this.estadoTabsConsulta( this.model.consultaSolicitud );
}

AutorizadorController.prototype.next = function( screen ){
  switch( screen ){
    
    
    case "correccionDatosAutorizar":
      this.module.service.confirmarCorreccionDatos.fetch( this.module, this.model.correccionDatosAutorizar, "confirmarCorreccionDatosAutorizar");
      this.transition("confirmarCorreccionDatosAutorizar");
      break;
    case "correccionDatosConsultaPrevia":
      this.module.service.confirmarCorreccionDatos.fetch( this.module, this.model.correccionDatosConsultaPrevia, "confirmarCorreccionConsultaPrevia");
      this.transition("confirmarCorreccionConsultaPrevia");
      break;
    
    case "confirmarCorreccionDatosAutorizar":
      this.module.service.confrontaCuentaIndividual.fetch( this.module, "confrontaCuentaIndividualAutorizador");
      this.transition("confrontaCuentaIndividual");
      break;
      
    case "confrontaCuentaIndividualAutorizador":
      this.module.controller.model.consultaCuentaIndividualAutorizador = JSON.parse( JSON.stringify( this.module.controller.model.confrontaCuentaIndividualAutorizador) );
      this.transition("consultaCuentaIndividualAutorizador");
      break;
	  
	  
	case "confirmarCorreccionDatosLectura":
        this.module.service.confrontaCuentaIndividual.fetch( this.module, "confrontaCuentaIndividualResponsable");
        this.transition("confrontaCuentaIndividualResponsable");
        break;
        
      case "confrontaCuentaIndividualResponsable":
        this.module.controller.model.consultaCuentaIndividualResponsable = JSON.parse( JSON.stringify( this.module.controller.model.confrontaCuentaIndividualResponsable) );
        this.transition("consultaCuentaIndividualResponsable");
        break;
    
	case "confirmarCorreccionConsultaPrevia":
        this.module.service.confrontaCuentaIndividual.fetch( this.module, "confrontaCuentaIndividualConsultaPrevia");
        this.transition("confrontaCuentaIndividualConsultaPrevia");
        break;
        
      case "confrontaCuentaIndividualConsultaPrevia":
        this.module.controller.model.consultaCuentaIndividualConsultaPrevia = JSON.parse( JSON.stringify( this.module.controller.model.confrontaCuentaIndividualConsultaPrevia) );
        this.transition("consultaCuentaIndividualConsultaPrevia");
        break;
  }
};

AutorizadorController.prototype.back = function( screen ){
  switch( screen ){
	  
	case "consultaCuentaIndividualConsultaPrevia":
      this.transition("confrontaCuentaIndividualConsultaPrevia");
    break;
    
    case "confrontaCuentaIndividualConsultaPrevia":
      this.transition("confirmarCorreccionConsultaPrevia");
    break;
	 
  case "consultaCuentaIndividualResponsable":
      this.transition("confrontaCuentaIndividualResponsable");
    break;
    
    case "confrontaCuentaIndividualResponsable":
      this.transition("confirmarCorreccionDatosLectura");
    break;
     
    case "consultaCuentaIndividualAutorizador":      
      this.transition("confrontaCuentaIndividualAutorizador");
      break;
    
    case "confrontaCuentaIndividualAutorizador":      
      this.transition("confirmarCorreccionDatosAutorizar");
      break;
    
    case "correccionDatosAutorizar":
      this.transition("informacionSolicitud");
      break;
    case "correccionDatosConsultaPrevia":
      this.transition("informacionSolicitud");
      break;
    case "confirmarCorreccionConsultaPrevia":
      this.transition("correccionDatosConsultaPrevia");
      break;
    case "confirmarCorreccionDatosLectura":
      this.transition("informacionSolicitud");
      break;
    case "confirmarCorreccionDatosAutorizar":
      this.transition("correccionDatosAutorizar");
      break;
          
  }
};
AutorizadorController.prototype.mostrarDocumentonss = function (nombreArchivo, idBoveda) {
    idPersona = this.module.controller.model.userProfile.idPersona;
    folio = this.module.controller.consultaSolicitudController.model.consultaSolicitud.folio;

    document.forms['auxForm'].action = "atencionAutorizador/obtenerDocumento/" + idPersona + "/" + folio + "/ext/" + nombreArchivo + "/" + idBoveda;
    document.forms['auxForm'].submit();
};
