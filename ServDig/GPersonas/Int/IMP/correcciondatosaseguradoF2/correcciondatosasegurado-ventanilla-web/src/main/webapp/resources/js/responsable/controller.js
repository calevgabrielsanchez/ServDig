function ResponsableController(module) {
    this.module = module;
    this.model = {};
    this.isConsulta= false;
    this.consultaSolicitudController = new ConsultaSolicitudController(this.module);
    this.consultaPreviaController= new ConsultaPreviaController(this.model);
    this.cancelarController = new CancelarController(this.module);
    this.solicitarInformacionController = new SolicitarInformacionController(this.module);
    this.agregarNssDocumentoController = new AgregarNSSDocumentoController(this.module);
}

ResponsableController.prototype.mostrarDocumento = function(index){
  var documento = this.model.consultaSolicitud.gridDocumentos.data[index];
  if( !this.module.render.isEmpty( documento ) ){
    document.forms['auxForm'].action = "atencionAutorizador/obtenerDocumento/"+documento.idPersona+"/"+documento.folio+"/"+documento.extension+"/"+documento.nombreArchivo+"/"+documento.idDocBoveda;
    document.forms['auxForm'].submit();
  }    
};

ResponsableController.prototype.next = function( screen ){
  switch( screen ){
    
    case "correccionDatosConsultaPrevia":
      this.module.service.confirmarCorreccionDatos.fetch( this.module, this.model.correccionDatosConsultaPrevia, "confirmarCorreccionConsultaPrevia");
      this.transition("confirmarCorreccionConsultaPrevia");
      break;
      
    case "correccionDatos":
      this.module.service.confirmarCorreccionDatos.fetch( this.module, this.model.correccionDatos, "confirmarCorreccionDatos" );
      this.transition("confirmarCorreccionDatos");
    break;
    
    case "correccionDatosLectura":
      this.module.service.confirmarCorreccionDatos.fetch( this.module, this.model.correccionDatosLectura, "confirmarCorreccionDatosLectura");
      this.transition("confirmarCorreccionDatosLectura");
      break;
    
    case "confirmarCorreccionDatos":
      $("#modal").modal();
      this.transition("cuentaIndividual");
      this.module.service.cuentaIndividual.fetch( this.module, "cuentaIndividualAsegurado");
      
    break; 
  
    case "consultaCuentaIndividual":
      this.module.service.cuentaIndividual.complete( "cuentaIndividualAsegurado" );
    break; 
    
    case "confirmarCorreccionDatosLectura":
/*	var contador=0;
	var maximo = this.model.correccionDatosLectura.listaNss.length
	for(var x=0 ; x < maximo; x++ )
	{
		if((this.model.correccionDatosLectura.listaNss[x].tipoAclaracion.cuentaIlogica===true || this.model.correccionDatosLectura.listaNss[x].tipoAclaracion.cuentaIlogica==='true')||
			(this.model.correccionDatosLectura.listaNss[x].tipoAclaracion.homonimio===true || this.model.correccionDatosLectura.listaNss[x].tipoAclaracion.homonimio==='true')||
		(this.model.correccionDatosLectura.listaNss[x].tipoAclaracion.cuentaIndividual===true || this.model.correccionDatosLectura.listaNss[x].tipoAclaracion.cuentaIndividual==='true')||
		(this.model.correccionDatosLectura.listaNss[x].tipoAclaracion.otroAsegurado===true || this.model.correccionDatosLectura.listaNss[x].tipoAclaracion.otroAsegurado==='true'))
		{
			contador = contador + 1;
			if(contador === maximo ){
		    this.module.service.confrontaCuentaIndividual.fetch( this.module, "confrontaCuentaIndividualResponsable");
			this.transition("confrontaCuentaIndividualResponsable");
			break;}
		}
		else 
		{*/
			 this.module.service.confrontaCuentaIndividual.fetch( this.module, "confrontaCuentaIndividualResponsable");
			 this.transition("confrontaCuentaIndividualResponsable");
			 break;
//		}
//	}
		

       
        
      case "confrontaCuentaIndividualResponsable":
        this.module.controller.model.consultaCuentaIndividualResponsable = JSON.parse( JSON.stringify( this.module.controller.model.confrontaCuentaIndividualResponsable) );
        this.transition("consultaCuentaIndividualResponsable");
        break;
    
	case "confirmarCorreccionConsultaPrevia":
        this.module.service.confrontaCuentaIndividual.fetch( this.module, "confrontaCuentaIndividualConsultaPrevia");
        this.transition("confrontaCuentaIndividualConsultaPrevia");
        break;
        
      case "confrontaCuentaIndividualConsultaPrevia":
        this.module.controller.model.consultaCuentaIndividualConsultaPrevia = JSON.parse( JSON.stringify( this.module.controller.model.confrontaCuentaIndividualConsultaPrevia ));
        this.transition("consultaCuentaIndividualConsultaPrevia");
        break;
  }
};

ResponsableController.prototype.back = function( screen ){
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
    
    case "correccionDatos":
      this.transition("informacionSolicitud");
    break;
    
    case "confirmarCorreccionDatos":
      this.transition("correccionDatos");
    break;
    
    case "correccionDatosLectura":
      this.transition("informacionSolicitud");
      break;
    case "confirmarCorreccionDatosLectura":
      this.transition("informacionSolicitud");
      break;
      
    case "correccionDatosConsultaPrevia":
      this.transition("informacionSolicitud");
      break;
    case "confirmarCorreccionConsultaPrevia":
      this.transition("correccionDatosConsultaPrevia");
      break;
    
    case "confrontaCuentaIndividual":
      this.transition("confirmarCorreccionDatos");
    break;   
    
    case "consultaCuentaIndividual":
      this.transition("cuentaIndividual");
      this.module.service.cuentaIndividual.fetch( this.module, "cuentaIndividualAsegurado");
    break;
    
  }
};


ResponsableController.prototype.transition = function( screen ){
  
  this.screens = {    
   
	bandejaSolicitudesUI:{ card: 1},
    informacionSolicitud:{ card: 2},
    correccionDatosLectura: {card: 3},
    confirmarUI:{card: 4},
    cancelarUI:{card: 5},
    bitacoraUI:{card: 6},
    solicitarInformacionUI:{card: 7},
    correccionDatos:{ card:8 },
    confirmarCorreccionDatosLectura: {card: 9},
    consultaAtencionResponsableUI:{card: 10},
    consultaDetalleAtencionResponsableUI:{card: 11},
    agregarNss:{ card:12},
    cuentaIndividual:{ card: 13},
    consultaCuentaIndividual:{card :14},
    confirmarCorreccionDatos:{ card:15},
    fin:{ card: 16},
    confrontaCuentaIndividual:{ card:17},
    correccionDatosConsultaPrevia:{card:18},
    confirmarCorreccionConsultaPrevia:{ card:19},
	confrontaCuentaIndividualResponsable:{card:20},
    consultaCuentaIndividualResponsable:{card:21},
	confrontaCuentaIndividualConsultaPrevia:{card:22},
    consultaCuentaIndividualConsultaPrevia:{card:23}
  };  
  this.module.updateModel("AlertComponent","alert", {});    
  this.module.updateModel("CardLayoutComponent", "responsableCardLayout" , this.screens[screen].card);
};

ResponsableController.prototype.cuentaIndividual = function(){
  this.module.controller.model.consultaSolicitud = { folio: "123"};
  this.module.service.cuentaIndividual.fetch( this.module, "cuentaIndividualAsegurado");
  this.transition("cuentaIndividual");
};


ResponsableController.prototype.closeModal = function(modalId){
  $("#"+modalId).modal('hide');
};


ResponsableController.prototype.descargarComprobante = function(){
	
	folio = this.module.controller.model.consultaSolicitud.folio;
	
	if ($("#documentoSolicitud").val() == null || typeof $("#documentoSolicitud").val() === 'undefined') {
		$('form[name=auxForm]').append("<input type='hidden' name='documentoSolicitud' id='documentoSolicitud' />");
	}    

  
  $("#documentoSolicitud").val(folio);
  $('form[name=auxForm]').attr('action', 'atencionResponsable/descargarComprobante.do');
  $('form[name=auxForm]').submit();  
  
	  
};

ResponsableController.prototype.mostrarDocumentoBeneficiario = function(index){
	  var documento = this.model.consultaSolicitud.gridDocumentosBeneficiario.data[index];
	  if( !this.module.render.isEmpty( documento ) ){
	    document.forms['auxForm'].action = "atencionAutorizador/obtenerDocumento/"+documento.idPersona+"/"+documento.folio+"/"+documento.extension+"/"+documento.nombreArchivo+"/"+documento.idDocBoveda;
	    document.forms['auxForm'].submit();
	  }    
};

ResponsableController.prototype.bandeja = function(){
	  this.isConsulta = true;
//	  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 1);
	  this.module.controller.transition("bandejaSolicitudesUI");
	//  this.module.controller.model.paginaTramitesActualTramites = this.module.controller.model.gridTramites.currentPage;
//	  this.module.controller.model.paginaTramitesActualHistorico = this.module.controller.model.gridHistorico.currentPage;
};

ResponsableController.prototype.recuperarFiltros = function(module,filtrosBusqueda){	
	
	
};

ResponsableController.prototype.confirmar = function(){
	  // Cerrar la modal, abre pagina de cuenta individual	   
	folioAutorizar = $("#informacionRENAPO_folio").val();
	$("#modal").modal('show');
        this.module.service.fetchCuentaIndividual();
	$("#modal").modal('hide');
	
};
	
ResponsableController.prototype.volverBandeja = function(){
	 //Guardado Parcial
	 this.module.service.guardarCuentaIndividual();
	
     // Regresa a la bandeja 
	 this.module.controller.model.filtrosBusqueda = {};
	 this.module.validator.formToModel("FormPanelComponent-filter", this.module.controller.model.filtrosBusqueda );
//	 this.module.updateModel("CardLayoutComponent","responsableCardLayout", 1);
	 this.module.controller.transition("bandejaSolicitudesUI");
	 this.module.controller.model.paginaTramitesActualTramites = this.module.controller.model.gridTramites.currentPage;
	 this.module.controller.model.paginaTramitesActualHistorico = this.module.controller.model.gridHistorico.currentPage;
	
};

ResponsableController.prototype.avanzarAutorizar= function(){
	this.module.service.confirmar();
};

ResponsableController.prototype.cancelarAutorizar= function(){
	this.module.service.cancelarAvanzaAutorizar();
};


ResponsableController.prototype.checkMotivoAclaracion = function(){
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

ResponsableController.prototype.postFetchCorreccionDatosUI = function(){
//	this.module.view.correccionDatosUI.colorCampos();
//	this.actualizarControles(this.module.view.correccionDatosUI);
};

ResponsableController.prototype.tamanioGrid = function(){
	this.module.view.confirmarUI.tamanioGrid();
};

ResponsableController.prototype.tamanioGridConsulta = function(){
	this.module.view.consultaPreviaDetalleUI.tamanioGrid();
};


ResponsableController.prototype.postFetchConsultaSolicitudComponent = function(){
	this.module.render.componentTemplates['ConsultaSolicitudComponent'].validarCurpsHistoricas(this.module.controller.model.consultaSolicitud.gridsNSS, this.module.controller.model.consultaSolicitud.informacionBeneficiario);
	this.module.render.componentTemplates['ConsultaSolicitudComponent'].tamanioCamposConsultaSolicitud();
};

ResponsableController.prototype.cambioTipoNSS = function(){
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


ResponsableController.prototype.iniciar = function(){
  // Actualizar los tipos de regularización, si es correcto
  // mostrar la pantalla de inicio tramite
  
  this.module.validator.formToModel("FormPanelComponent-consultaSolicitud",
   this.model.consultaSolicitud );  
  
  this.model.currentNSSIndex = 0;
  this.model.detalle = this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex];
  
  this.iniciarCorreccion();
};

ResponsableController.prototype.iniciarCorreccion = function(){
	
  //FIX: Solo se usa el tipoNSS Correccion
  this.model.detalle.tipoNSS = "1";
//  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 8);
  this.module.controller.transition("correccionDatos");
  if( this.model.consultaSolicitud.gridNSS.data.length === (this.model.currentNSSIndex+1) ){
      $("#btnSiguiente").html("Finalizar");
  }
};


ResponsableController.prototype.regresarInicioCorreccion = function(){
	
	$("#modal").modal('show');
//	this.module.updateModel("CardLayoutComponent","responsableCardLayout", 2);
	this.module.controller.transition("informacionSolicitud");
	$("#modal").modal('hide');
	
	};


	ResponsableController.prototype.regresarModificarDatos = function(){		
		$("#modal").modal('show');
//		this.module.updateModel("CardLayoutComponent","responsableCardLayout", 8);
		this.module.controller.transition("correccionDatos");
		$("#modal").modal('hide');		
		};
		
ResponsableController.prototype.regresarCorreccion = function(){
	datosCDAModificado =[];
  // Guardar el modelo los datos actuales de la forma antes de hacer el cambio
  this.module.validator.formToModel("FormPanelComponent-correccionDatos",
   this.model.consultaSolicitud.gridsNSS[0].data[this.model.currentNSSIndex] );
  
  if( this.model.currentNSSIndex > 0 ){
    this.model.currentNSSIndex--;  
    this.model.detalle = this.model.consultaSolicitud.gridsNSS[0].data[this.model.currentNSSIndex];
    this.module.render.notify("FormPanelComponent", "panelCorreccionDatos", "detalle");
  }else{
    
      //multiples personas por NSS
      this.model.currentPersonaNSSIndex = 0;
      this.model.detalle.informacionBDTU = this.model.detalle[0].informacionFuentesNSS[this.model.currentPersonaNSSIndex];
      
      if(this.model.detalle[0].informacionFuentesNSS.length == 0 ){
	      $("#modalSalirInicioMen").modal();
      } else{
	      this.module.updateModel("NavegacionPersonaNSSComponent","navegacionPersonaNSS", "detalle");
      }
		
      $("#modal").modal('show');
//      this.module.updateModel("CardLayoutComponent","responsableCardLayout", 8);
      this.module.controller.transition("correccionDatos");
      $("#modal").modal('hide');
	  
      if(this.model.detalle.informacionFuentesNSS.length <= 1 ){
	      //solo hay un registro inhabilitar boton siguiente
	      $("#btnSiguientePersona").prop('disabled',true);
      }
      $("#btnAnteriorPersona").prop('disabled',true);
  }
  
  
};
 
ResponsableController.prototype.siguienteCorreccion = function(){
//  // Guardar el modelo los datos actuales de la forma antes de hacer el cambio

	if(origendbtab==undefined)
		{
			origendbtab ='canase';
		}
	ResponsableController.prototype.guardaractualizacionNSS(origendbtab);
	this.model.resumenCorreccion = this.model.consultaSolicitud.gridsNSS;
  $("#modal").modal('show');
	contadorNSS = 0;
	 
//    this.module.updateModel("CardLayoutComponent","responsableCardLayout", 4);
	this.module.controller.transition("confirmarUI");
    
    $("#modal").modal('hide');
};

ResponsableController.prototype.siguienteCorreccionCuanetaIndividual = function(){
	  // Redireccion a cuenta inivual
	
//	var url=window.location;
//	location.remplace(url+"/correccionDatosAsegurado-web-ventanilla/atencionResponsable/cuentaIndividual?folioTramite="+idTramite);
	folioAutorizar = $("#informacionRENAPO_folio").val();
//	this.module.updateModel( "CardLayoutComponent", "responsableCardLayout", 13);
	this.module.controller.transition("cuentaIndividual");
};


ResponsableController.prototype.selectTramite = function(index){  
  this.model.usuariosession=this.model.userProfile.nombreCompleto;
  this.model.usuarioasignado = this.model.gridTramites.data[index].nombreCompletoResponsable;
  this.module.service.fetchSolicitud("consultaSolicitud",this.module, index);
  this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );
};

ResponsableController.prototype.selectHistorico = function(index){
	this.module.service.fetchSolicitudHistorico("consultaSolicitud",this.module, index);
	this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );  
};

ResponsableController.prototype.reload = function(){
	  window.location.reload();
};

ResponsableController.prototype.nuevaSolicitud = function(){
	  this.module.service.nuevaSolicitud();
};

ResponsableController.prototype.salir = function(){
	  this.module.service.salir();
};

ResponsableController.prototype.salirCancelar = function(){
	  this.module.service.salirCancelar();
};

ResponsableController.prototype.salirAceptar = function(){
	module.updateModel("UserProfileComponent","userProfile", {});
	this.module.service.salirAceptar();
};

ResponsableController.prototype.estatus = function(index){  
  this.module.service.fetchEstatus("bitacora",this.module, index);
  this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );
};

ResponsableController.prototype.estatusHistorico = function(index){  
	  this.module.service.fetchEstatusHistorico("bitacora",this.module, index);
	  this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );
};

	
ResponsableController.prototype.filtros = function(){
	this.model.filter = {};
	this.module.validator.formToModel("FormPanelComponent-filter", this.model.filter );
	this.model.filtrosBusqueda = this.model.filtros;
	this.module.service.fetchFiltros("gridTramites","gridHistorico",this.model.filter);
	this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );
};

ResponsableController.prototype.filtros = function(grid){
    this.model.filter = {};
    this.module.validator.formToModel("FormPanelComponent-filter", this.model.filter );
    this.model.filtrosBusqueda = this.model.filtros;
    console.log("Cambio el fetchFiltros de responsable");

    if (grid === "buscar") {
        if($('#li-tabs-panelTabsBandejas-0').attr('class').trim() == "active"){
            this.module.service.fetchFiltros("gridTramites","",this.model.filter);
        } else {
            this.module.service.fetchFiltros("","gridHistorico",this.model.filter);
        }
    } else if(grid === "tramites"){
        this.module.service.fetchFiltros("gridTramites","",this.model.filter);
    }else if ("historico") {
        this.module.service.fetchFiltros("","gridHistorico",this.model.filter);
    }

    this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );

};

ResponsableController.prototype.init = function(){
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
      solicitarInformacion:{}
    };
    this.consultaSolicitudController.model = this.model;
    this.consultaPreviaController.model= this.model;
    this.cancelarController.model = this.model;
    this.solicitarInformacionController.model = this.model;
    this.module.render.draw();
    this.module.service.getUserProfile(this.module);
    this.agregarNssDocumentoController.model = this.model;
};

ResponsableController.prototype.siguientePersona = function(){
	this.module.validator.formToModel("FormPanelComponent-correccionDatos", this.model.detalle );
	var isLast = false;
	var isFirst = true;
	if(this.model.currentPersonaNSSIndex + 1 < this.model.detalle.informacionFuentesNSS.length){
		this.model.currentPersonaNSSIndex++;
		this.model.detalle.informacionBDTUU = this.model.detalle.informacionFuentesNSS[this.model.currentPersonaNSSIndex];
		this.module.render.notify("FormPanelComponent", "panelCorreccionDatos", "detalle");
		this.module.updateModel("NavegacionPersonaNSSComponent","navegacionPersonaNSS", "detalle");
		isFirst = false;
	}
	if(this.model.currentPersonaNSSIndex + 1 >= this.model.detalle.informacionFuentesNSS.length){
		isLast = true;
	}
	$("#btnSiguientePersona").prop('disabled',isLast);
	$("#btnAnteriorPersona").prop('disabled',isFirst);
};

ResponsableController.prototype.anteriorPersona = function(){
	this.module.validator.formToModel("FormPanelComponent-correccionDatos", this.model.detalle );
	var isLast = true;
	var isFirst = false;
	if(this.model.currentPersonaNSSIndex - 1  >= 0){
		this.model.currentPersonaNSSIndex--;
		this.model.detalle.informacionBDTTU = this.model.detalle.informacionFuentesNSS[this.model.currentPersonaNSSIndex];
		this.module.render.notify("FormPanelComponent", "panelCorreccionDatos", "detalle");
		this.module.updateModel("NavegacionPersonaNSSComponent","navegacionPersonaNSS", "detalle");
		isLast = false;
	}
	if(this.model.currentPersonaNSSIndex - 1 < 0){
		isFirst = true;
	}
	$("#btnSiguientePersona").prop('disabled',isLast);
	$("#btnAnteriorPersona").prop('disabled',isFirst);
};

ResponsableController.prototype.checkEstadoResponsable = function(data,componentId){
	
	data.estadoResponsable=parseInt(data.estadoResponsable);
	
	//Botones inferiores por Default
	var buttonCardIndex=0; //data.estadoResponsable;
	//Botones superiores por Default y para Si es Operada o Atendida 
	var buttonAccionesCardIndex=0;
	 
	if (data.estadoResponsable == 0){
		buttonCardIndex = 1;
		buttonAccionesCardIndex = 1;
	}else if (data.estadoResponsable == 1){
		buttonCardIndex = 2;
	}else if (data.estadoResponsable == 2){
		buttonCardIndex = 2;
		buttonAccionesCardIndex = 2;
	}
	
	//solo en estado pantalla 0 y 3  debe permitir finalizar los cambios..
	var buttonConfirmarCardIndex = 1;
	
	if((data.estadoResponsable == 0 || data.estadoResponsable == 3) && data.esPropietario ){
		buttonConfirmarCardIndex = 0;
	}
	
	this.module.updateModel( "CardLayoutComponent", "responsableButtonsCardLayout", buttonCardIndex);//Botones inferiores
//	this.module.updateModel( "CardLayoutComponent", "responsableButtonsCardLayout", 1);//Botones inferiores
	this.module.updateModel( "CardLayoutComponent", "responsableAccionesButtonsCardLayout", buttonAccionesCardIndex);//Botones superiores
	
	this.module.updateModel("CardLayoutComponent", "responsableConfirmarButtonsCardLayout", buttonConfirmarCardIndex);
	this.module.updateModel("CardLayoutComponent", "responsableCorreccionButtonsCardLayout", buttonConfirmarCardIndex);
	
};

ResponsableController.prototype.estadoTabsConsulta = function(data,componentId){
	//Validar mostrar tab beneficiario o representante legal 
	if(data.informacionBeneficiario==null || data.informacionBeneficiario.curp==null){
		$("#tabButtonbeneficiario").hide()
		$("#panelbeneficiario").hide();
    $("#panelbeneficiario").css('display','none');
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

ResponsableController.prototype.salirInicio = function(){
	  $("#modalSalirInicioMen").modal('hide');
};

ResponsableController.prototype.clean = function(){
	document.forms["filter"].reset();
  /*this.module.service.fetchTramites( "gridTramites", this.module,  1);*/
  /*this.module.service.fetchHistorico( "gridHistorico", this.module,   1);*/
    this.model.filter = {};
    this.module.service.fetchFiltros("gridTramites","",this.model.filter);
	this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );
};

ResponsableController.prototype.showBtnNuevaSolicitud = function(){
	if ($('#divCollapse').hasClass('in')){
		document.getElementById("spanmenuBandeja").classList.remove("glyphicon-chevron-down");
    	document.getElementById("spanmenuBandejaOposite").classList.remove("glyphicon-chevron-up");
    	document.getElementById("spanmenuBandeja").classList.add("glyphicon-chevron-up");
    	document.getElementById("spanmenuBandejaOposite").classList.add("glyphicon-chevron-down");
		$("#panelNuevaSolicitud").show();
    }else{
    	document.getElementById("spanmenuBandeja").classList.remove("glyphicon-chevron-up");
		document.getElementById("spanmenuBandejaOposite").classList.remove("glyphicon-chevron-down");
		document.getElementById("spanmenuBandeja").classList.add("glyphicon-chevron-down");
		document.getElementById("spanmenuBandejaOposite").classList.add("glyphicon-chevron-up");
		$("#panelNuevaSolicitud").hide();
    }
	
	this.module.validator.formToModel("FormPanelComponent-filter", this.model.filtros );
	this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );
	
};

ResponsableController.prototype.disableNombre = function(){
	if($("#foliosAsociados").prop('checked')){
		$("#curp").val("");
		$("#curp").prop('disabled',true);
	}else {
		$("#curp").prop('disabled',false);
	}
};

ResponsableController.prototype.disableFilter = function(){
	this.model.filtros = {};
	this.module.validator.formToModel("FormPanelComponent-filter", this.model.filtros );
	this.module.service.disableElement(this.model.filtros);
};

ResponsableController.prototype.modalCoreeccionDatos = function(){
	$("#modalCorreccionDatos").modal('hide');
};

ResponsableController.prototype.modalMoviminto5 = function(){
	$("#modalMov5").modal('hide');
	
};

ResponsableController.prototype.modalCorreccionNo = function(){
	$("#modalCorreccionNo").modal('hide');
	
};

ResponsableController.prototype.modalNoCANASE = function(){
	$("#noCANSE").modal('hide');
	
};

ResponsableController.prototype.rechazarBorrado = function(){	
	$("#borradoCI").modal('hide');		
	this.module.service.correccionDatosService.fetch( this.module, "correccionDatos" ); 
	this.next("correccionDatos");
};

ResponsableController.prototype.acptarBorrado = function(){	
	this.module.service.borrarCI(this.module);	
	
};

ResponsableController.prototype.modaltipoRtipoN = function(){
	$("#modalCorreccionDatosOtraPersona").modal('hide');
};

ResponsableController.prototype.aceptarCertificadoNSS = function(){
	  $("#modalcertificadoNSS").modal('hide');
};

ResponsableController.prototype.datosNSS = function(){
	
	return this.module.service.fetNSS("gridGroupDocumentosNss","module","1");
	
};

/*
 * obtiene la informacion de cada boton 
 * del slaider obteniendo la informacion del NSS
 * 
 */

ResponsableController.prototype.obtenerDatosNSS = function(){

	
	var url = "correccionDatosAseguradoNSS.do";
	$("#numeroNSS").text($("#btonselect0").val());
	if (url !== null) {
		var nss0 = $("#btonselect0").val();      
		var modnombre=0;
		var validarCertificador =  nss0.substr(0,1);
		var validarCertificadorpos =  nss0.substr(2,3);
		if(validarCertificador=="36" || validarCertificador=="77" || validarCertificador=="79" || validarCertificador=="80" || validarCertificador=="97")
			{
			 $("#slide1 input[type=checkbox]").prop('checked', false); 			
			}
		if( validarCertificador=="36" && (validarCertificador=="97"|| validarCertificador=="98"||validarCertificador=="99" 
			||validarCertificador=="00"|| validarCertificador=="01"|| validarCertificador=="02"))
			{
				$("#slide1 input[type=checkbox]").prop('checked', false); 	
			}
		var params =JSON.stringify(eval({nss:nss0}));
	
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
						if(pertenecebd=="CANASE"){
							canase=canase+1;
						}
							$("#informacion"+pertenecebd+"_curp").val(data[i].curp);
							 if($("informacionRENAPO_curp").val() == $("#informacion"+pertenecebd+"_curp").val()) {
							        $("#informacion"+pertenecebd+"_curp").attr("disabled", true).css("background-color","#FFF");
							        $("#diferenteValorinformacion"+pertenecebd+"_curp").attr("visibility", "hidden");
							    } else{
							        $("#informacion"+pertenecebd+"_curp").attr("disabled", false).css("background-color","#FE2E2E");
							        $("#slide1 input[type=checkbox]").prop('checked', false);
							        $("#diferenteValorinformacion"+pertenecebd+"_curp").attr("visibility", "visible");
							    }
							$("#informacion"+pertenecebd+"_apellidoPaterno").val(data[i].apellidoPaterno);
							 if($("informacionRENAPO_apellidoPaterno").val() == $("#informacion"+pertenecebd+"_apellidoPaterno").val()) {
							        $("#informacion"+pertenecebd+"_apellidoPaterno").attr("disabled", true).css("background-color","#FFF");
							        $("#diferenteValorinformacion"+pertenecebd+"_apellidoPaterno").attr("visibility", "hidden");
							    } else{
							        $("#informacion"+pertenecebd+"_apellidoPaterno").attr("disabled", false).css("background-color","#FE2E2E");
							        $("#diferenteValorinformacion"+pertenecebd+"_apellidoPaterno").attr("visibility", "visible");
							        modnombre= modnombre +1;
							    }
							$("#informacion"+pertenecebd+"_apellidoMaterno").val(data[i].apellidoMaterno);
							if($("informacionRENAPO_apellidoMaterno").val() == $("#informacion"+pertenecebd+"_apellidoMaterno").val()) {
						        $("#informacion"+pertenecebd+"_apellidoMaterno").attr("disabled", true).css("background-color","#FFF");
						        $("#diferenteValorinformacion"+pertenecebd+"_apellidoMaterno").attr("visibility", "hidden");
						    } else{
						        $("#informacion"+pertenecebd+"_apellidoMaterno").attr("disabled", false).css("background-color","#FE2E2E");
						        $("#diferenteValorinformacion"+pertenecebd+"_apellidoMaterno").attr("visibility", "visible");
						        modnombre= modnombre +1;
						    }
							$("#informacion"+pertenecebd+"_nombre").val(data[i].nombre);
							if($("informacionRENAPO_nombre").val() == $("#informacion"+pertenecebd+"_nombre").val()) {
						        $("#informacion"+pertenecebd+"_nombre").attr("disabled", true).css("background-color","#FFF");
						        $("#diferenteValorinformacion"+pertenecebd+"_nombre").attr("visibility", "hidden");
						    } else{
						        $("#informacion"+pertenecebd+"_nombre").attr("disabled", false).css("background-color","#FE2E2E");
						        $("#diferenteValorinformacion"+pertenecebd+"_nombre").attr("visibility", "visible");
						        modnombre= modnombre +1;
						    }
							$("#informacion"+pertenecebd+"_sexo").val(data[i].sexo);
							if($("informacionRENAPO_sexo").val() == $("#informacion"+pertenecebd+"_sexo").val()) {
						        $("#informacion"+pertenecebd+"_sexo").attr("disabled", true).css("background-color","#FFF");
						        $("#diferenteValorinformacion"+pertenecebd+"_sexo").attr("visibility", "hidden");
						    } else{
						        $("#informacion"+pertenecebd+"_sexo").attr("disabled", false).css("background-color","#FE2E2E");
						        $("#diferenteValorinformacion"+pertenecebd+"_sexo").attr("visibility", "visible");
						    }
							$("#informacion"+pertenecebd+"_fechaNacimiento").val(data[i].fechaNacimiento);
							if($("informacionRENAPO_fechaNacimiento").val() == $("#informacion"+pertenecebd+"_fechaNacimiento").val()) {
						        $("#informacion"+pertenecebd+"_fechaNacimiento").attr("disabled", true).css("background-color","#FFF");
						        $("#diferenteValorinformacion"+pertenecebd+"_fechaNacimiento").attr("visibility", "hidden");
						    } else{
						        $("#informacion"+pertenecebd+"_fechaNacimiento").attr("disabled", false).css("background-color","#FE2E2E");
						        $("#diferenteValorinformacion"+pertenecebd+"_fechaNacimiento").attr("visibility", "visible");
						    }
							$("#informacion"+pertenecebd+"_lugarNacimiento").val(data[i].lugarNacimiento);
							if($("informacionRENAPO_lugarNacimiento").val() == $("#informacion"+pertenecebd+"_lugarNacimiento").val()) {
						        $("#informacion"+pertenecebd+"_lugarNacimiento").attr("disabled", true).css("background-color","#FFF");
						        $("#diferenteValorinformacion"+pertenecebd+"_lugarNacimiento").attr("visibility", "hidden");
						    } else{
						        $("#informacion"+pertenecebd+"_lugarNacimiento").attr("disabled", false).css("background-color","#FE2E2E");
						        $("#diferenteValorinformacion"+pertenecebd+"_lugarNacimiento").attr("visibility", "visible");
						    }
							$("#informacion"+pertenecebd+"_nacionalidad").val(data[i].nacionalidad);
							if($("informacionRENAPO_nacionalidad").val() == $("#informacion"+pertenecebd+"_nacionalidad").val()) {
						        $("#informacion"+pertenecebd+"_nacionalidad").attr("disabled", true).css("background-color","#FFF");
						        $("#diferenteValorinformacion"+pertenecebd+"_nacionalidad").attr("visibility", "hidden");
						    } else{
						        $("#informacion"+pertenecebd+"_nacionalidad").attr("disabled", false).css("background-color","#FE2E2E");
						        $("#diferenteValorinformacion"+pertenecebd+"_nacionalidad").attr("visibility", "visible");
						    }
							$("#informacion"+pertenecebd+"_datosDocumentoProbatorio").val(data[i].pertenecebd);
							if($("informacionRENAPO_datosDocumentoProbatorio").val() == $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").val()) {
								$("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", true).css("background-color","#FFF");
						        $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", false).css("color","#000");
						    } else{
						    	$("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", false).css("background-color","#585858");
						        $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", false).css("color","#FFF");
						    }
						}
					if(canase<0)
						{
						 $("#slide4 input[type=checkbox]").prop('checked', true); 
						}
					if(modnombre > 0)
						{
						 $("#slide9 input[type=checkbox]").prop('checked', true);
						 $("#labelslide9").css("left", "23px");
						}
					ResponsableController.prototype.validarNSS();
  	          										
				},
				error : function(error) {
					fnProcesarErrores(error,"form#informacionHistoriaLaboralForm");
					$.unblockUI();
				}
			});
	}
	
	
};


/*
 * Moviemiento del Slaider hacia la izquierda
 */
var mov=0;
ResponsableController.prototype.siguienteNSS = function(){
	var valor='';
	mov = mov -132; 
	valor = mov+'px';
	$('#contBoton').css('left', valor );
};

/*
 * Moviemiento del Slaider hacia la derecha
 */
ResponsableController.prototype.anteriorNSS = function(){
	if(mov != 0){
		var valor='';
		mov = mov +132; 
		valor = mov+'px';
		 $('#contBoton').css('left', valor );
		}
};


	/*
	 * Consulta la informacion y llena los campos de todas las fuentes
	 */
var datosfuentesprincipal;
var datosCDA;
var contadorNSS = 0;
var nssCorreccion="";
	ResponsableController.prototype.obtenerDatosNSSinicio = function(nss){
		
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
		nssCorreccion = nss0;						
		if(nss0.substring(0, 2) === "33" ||
           nss0.substring(0, 2) === "77" ||
           nss0.substring(0, 2) === "79" ||
           nss0.substring(0, 2) === "80" ||
           nss0.substring(0, 2) === "97")
    	{
			$("#ErrorCANASE").modal('show');
    	}
        else if(nss0.substring(0, 2) === "89"&&(
        		nss0.substring(2, 4) === "97" ||
        	    nss0.substring(2, 4) === "98" ||
           	    nss0.substring(2, 4) === "99" ||
           		nss0.substring(2, 4) === "00" ||
           		nss0.substring(2, 4) === "01" ||
                nss0.substring(2, 4) === "02"))
        {
        	$("#ErrorCANASE").modal('show');
        }
		else 
		{		
		
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
									documentos = documentos.replace("Numero: de Acta","Número: de Acta");
									documentos = documentos.replace("Numero de Libro","Número de Libro"); 
									documentos = documentos.replace("Numero de Foja","Número de Foja"); 
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
						},
						error : function(error) {
							
							alert("No se pudo obtener la informaicon");							
							return false; 
//							this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"No se pudo obtener la informaicon"} );

						}
					});
				}
			}			
		};
		
		ResponsableController.prototype.obtenerDatosNSSinicioBoton = function(nss){
		
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
				
		
		ResponsableController.prototype.guardaractualizacionNSS("canase");
		nssCorreccion = nss0;		
		if(nss0.substring(0, 2) === "33" ||
           nss0.substring(0, 2) === "77" ||
           nss0.substring(0, 2) === "79" ||
           nss0.substring(0, 2) === "80" ||
           nss0.substring(0, 2) === "97")
    	{
			$("#ErrorCANASE").modal('show');
    	}
        else if(nss0.substring(0, 2) === "89"&&(
        		nss0.substring(2, 4) === "97" ||
        	    nss0.substring(2, 4) === "98" ||
           	    nss0.substring(2, 4) === "99" ||
           		nss0.substring(2, 4) === "00" ||
           		nss0.substring(2, 4) === "01" ||
                nss0.substring(2, 4) === "02"))
        {
        	$("#ErrorCANASE").modal('show');
        }
		else 
		{		
		
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
									documentos = documentos.replace("Numero: de Acta","Número: de Acta");
									documentos = documentos.replace("Numero de Libro","Número de Libro"); 
									documentos = documentos.replace("Numero de Foja","Número de Foja"); 
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
						},
						error : function(error) {
							
							alert("No se pudo obtener la informaicon");							
							return false; 
//							this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"No se pudo obtener la informaicon"} );

						}
					});
				}
			}			
		};

		
		/*
		 * Valida similitudes de los datos entre las fuentes
		 * y los datos de renapo
		 */
		
		ResponsableController.prototype.validarNSS = function(){
			
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
				        $("#diferenteValorinformacion"+pertenecebd+"_curp").css("visibility", "hidden");
				    }else if($("#informacion"+pertenecebd+"_curp").val().trim() == "") { 
						$("#informacion"+pertenecebd+"_curp").attr("disabled", true).css("background-color","#585858");
						$("#diferenteValorinformacion"+pertenecebd+"_curp").css("visibility", "hidden");
						$("#informacion"+pertenecebd+"_curp").attr("disabled", true).css("color","#FFF");
					}else{
				        $("#informacion"+pertenecebd+"_curp").attr("disabled", true).css("background-color","#FE2E2E");
				        $("#slide1 input[type=checkbox]").prop('checked', false);
				        $("#diferenteValorinformacion"+pertenecebd+"_curp").css("visibility", "visible");
				    }
				
				 if($("#informacionRENAPO_apellidoPaterno").val() == $("#informacion"+pertenecebd+"_apellidoPaterno").val()) {
				        $("#informacion"+pertenecebd+"_apellidoPaterno").attr("disabled", true).css("background-color","#FFF");
				        $("#diferenteValorinformacion"+pertenecebd+"_apellidoPaterno").css("visibility", "hidden");
				    }else if($("#informacion"+pertenecebd+"_apellidoPaterno").val().trim() == "") { 
						$("#informacion"+pertenecebd+"_apellidoPaterno").attr("disabled", true).css("background-color","#585858");
						$("#diferenteValorinformacion"+pertenecebd+"_apellidoPaterno").css("visibility", "hidden");
						$("#informacion"+pertenecebd+"_apellidoPaterno").attr("disabled", true).css("color","#FFF");
					} else{
				        $("#informacion"+pertenecebd+"_apellidoPaterno").attr("disabled", false).css("background-color","#FE2E2E");
				        $("#diferenteValorinformacion"+pertenecebd+"_apellidoPaterno").css("visibility", "visible");
				    }
				
				if($("#informacionRENAPO_apellidoMaterno").val() == $("#informacion"+pertenecebd+"_apellidoMaterno").val()) {
			        $("#informacion"+pertenecebd+"_apellidoMaterno").attr("disabled", true).css("background-color","#FFF");
			        $("#diferenteValorinformacion"+pertenecebd+"_apellidoMaterno").css("visibility", "hidden");
			    }else if($("#informacion"+pertenecebd+"_apellidoMaterno").val().trim() == "") { 
						$("#informacion"+pertenecebd+"_apellidoMaterno").attr("disabled", true).css("background-color","#585858");
						$("#diferenteValorinformacion"+pertenecebd+"_apellidoMaterno").css("visibility", "hidden");
						$("#informacion"+pertenecebd+"_apellidoMaterno").attr("disabled", true).css("color","#FFF");
				}else{
			        $("#informacion"+pertenecebd+"_apellidoMaterno").attr("disabled", true).css("background-color","#FE2E2E");	
			        $("#diferenteValorinformacion"+pertenecebd+"_apellidoPaterno").css("visibility", "visible");
			    }
				
				if($("#informacionRENAPO_nombre").val() == $("#informacion"+pertenecebd+"_nombre").val()) {
			        $("#informacion"+pertenecebd+"_nombre").attr("disabled", true).css("background-color","#FFF");
			        $("#diferenteValorinformacion"+pertenecebd+"_nombre").css("visibility", "hidden");
			    }else if($("#informacion"+pertenecebd+"_nombre").val().trim() == "") { 
						$("#informacion"+pertenecebd+"_nombre").attr("disabled", true).css("background-color","#585858");
						$("#diferenteValorinformacion"+pertenecebd+"_nombre").css("visibility", "hidden");
						$("#informacion"+pertenecebd+"_nombre").attr("disabled", true).css("color","#FFF");
				}else{
			        $("#informacion"+pertenecebd+"_nombre").attr("disabled", true).css("background-color","#FE2E2E");	
			        $("#diferenteValorinformacion"+pertenecebd+"_nombre").css("visibility", "visible");
			    }
				
				if($("#informacionRENAPO_sexo").val() == $("#informacion"+pertenecebd+"_sexo").val()) {
			        $("#informacion"+pertenecebd+"_sexo").attr("disabled", true).css("background-color","#FFF");
			        $("#diferenteValorinformacion"+pertenecebd+"_sexo").css("visibility", "hidden");
			    }else if($("#informacion"+pertenecebd+"_sexo").val().trim() == "") { 
						$("#informacion"+pertenecebd+"_sexo").attr("disabled", true).css("background-color","#585858");
						$("#diferenteValorinformacion"+pertenecebd+"_sexo").css("visibility", "hidden");
						$("#informacion"+pertenecebd+"_sexo").attr("disabled", true).css("color","#FFF");
				}else{
			        $("#informacion"+pertenecebd+"_sexo").attr("disabled", true).css("background-color","#FE2E2E");
			        $("#diferenteValorinformacion"+pertenecebd+"_sexo").css("visibility", "visible");
			    }
				
				if($("#informacionRENAPO_fechaNacimiento").val() == $("#informacion"+pertenecebd+"_fechaNacimiento").val()) {
			        $("#informacion"+pertenecebd+"_fechaNacimiento").attr("disabled", true).css("background-color","#FFF");
			        $("#diferenteValorinformacion"+pertenecebd+"_fechaNacimiento").css("visibility", "hidden");
			    }else if($("#informacion"+pertenecebd+"_fechaNacimiento").val().trim() == "") { 
						$("#informacion"+pertenecebd+"_fechaNacimiento").attr("disabled", true).css("background-color","#585858");
						$("#diferenteValorinformacion"+pertenecebd+"_fechaNacimiento").css("visibility", "hidden");
						$("#informacion"+pertenecebd+"_fechaNacimiento").attr("disabled", true).css("color","#FFF");
				} else{
			        $("#informacion"+pertenecebd+"_fechaNacimiento").attr("disabled", true).css("background-color","#FE2E2E");
			        $("#diferenteValorinformacion"+pertenecebd+"_fechaNacimiento").css("visibility", "visible");
			    }
				
				if($("#informacionRENAPO_lugarNacimiento").val() == $("#informacion"+pertenecebd+"_lugarNacimiento").val()) {
			        $("#informacion"+pertenecebd+"_lugarNacimiento").attr("disabled", true).css("background-color","#FFF");
			        $("#diferenteValorinformacion"+pertenecebd+"_lugarNacimiento").css("visibility", "hidden");
			    }else if($("#informacion"+pertenecebd+"_lugarNacimiento").val().trim() == "") { 
						$("#informacion"+pertenecebd+"_lugarNacimiento").attr("disabled", true).css("background-color","#585858");
						$("#diferenteValorinformacion"+pertenecebd+"_lugarNacimiento").css("visibility", "hidden");
						$("#informacion"+pertenecebd+"_lugarNacimiento").attr("disabled", true).css("color","#FFF");
				} else{
			        $("#informacion"+pertenecebd+"_lugarNacimiento").attr("disabled", true).css("background-color","#FE2E2E");
			        $("#diferenteValorinformacion"+pertenecebd+"_lugarNacimiento").css("visibility", "visible");
			    }
				
				if($("#informacionRENAPO_nacionalidad").val() == $("#informacion"+pertenecebd+"_nacionalidad").val()) {
			        $("#informacion"+pertenecebd+"_nacionalidad").attr("disabled", true).css("background-color","#FFF");
			        $("#diferenteValorinformacion"+pertenecebd+"_nacionalidad").css("visibility", "hidden");
			    }else if($("#informacion"+pertenecebd+"_nacionalidad").val().trim() == "") { 
						$("#informacion"+pertenecebd+"_nacionalidad").attr("disabled", true).css("background-color","#585858");
						$("#diferenteValorinformacion"+pertenecebd+"_nacionalidad").css("visibility", "hidden");
						$("#informacion"+pertenecebd+"_nacionalidad").attr("disabled", true).css("color","#FFF");
				} else{
			        $("#informacion"+pertenecebd+"_nacionalidad").attr("disabled", true).css("background-color","#FE2E2E");
			        $("#diferenteValorinformacion"+pertenecebd+"_nacionalidad").css("visibility", "visible");
			    }
				
				if($("#informacionRENAPO_datosDocumentoProbatorio").val() == $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").val()) {
			        $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", true).css("background-color","#FFF");
			        $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", true).css("color","#000");
			    } else{
			        $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", true).css("background-color","#585858");
			        $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", true).css("color","#FFF");
			    }
				}
				else 
					{
					 $("#informacion"+pertenecebd+"_curp").attr("disabled", true).css("background-color","#FFF");
				        $("#diferenteValorinformacion"+pertenecebd+"_curp").css("visibility", "hidden");
				        $("#informacion"+pertenecebd+"_apellidoPaterno").attr("disabled", true).css("background-color","#FFF");
				        $("#diferenteValorinformacion"+pertenecebd+"_apellidoPaterno").css("visibility", "hidden");
				        $("#informacion"+pertenecebd+"_apellidoMaterno").attr("disabled", true).css("background-color","#FFF");
				        $("#diferenteValorinformacion"+pertenecebd+"_apellidoMaterno").css("visibility", "hidden");
				        $("#informacion"+pertenecebd+"_nombre").attr("disabled", true).css("background-color","#FFF");
				        $("#diferenteValorinformacion"+pertenecebd+"_nombre").css("visibility", "hidden");
				        $("#informacion"+pertenecebd+"_sexo").attr("disabled", true).css("background-color","#FFF");
				        $("#diferenteValorinformacion"+pertenecebd+"_sexo").css("visibility", "hidden");
				        $("#informacion"+pertenecebd+"_fechaNacimiento").attr("disabled", true).css("background-color","#FFF");
				        $("#diferenteValorinformacion"+pertenecebd+"_fechaNacimiento").css("visibility", "hidden");
				        $("#informacion"+pertenecebd+"_lugarNacimiento").attr("disabled", true).css("background-color","#FFF");
				        $("#diferenteValorinformacion"+pertenecebd+"_lugarNacimiento").css("visibility", "hidden");
				        $("#informacion"+pertenecebd+"_nacionalidad").attr("disabled", true).css("background-color","#FFF");
				        $("#diferenteValorinformacion"+pertenecebd+"_nacionalidad").css("visibility", "hidden");
				        $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", true).css("background-color","#FFF");
				        $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", false).css("color","#000");
					}
				
				
				}
			 
		};
		
	ResponsableController.prototype.validarNSSLectura = function(){
			
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
				        $("#diferenteValorinformacion"+pertenecebd+"_curp").css("visibility", "hidden");
				    }else if($("#informacion"+pertenecebd+"_curp").val().trim() == "") { 
						$("#informacion"+pertenecebd+"_curp").attr("disabled", true).css("background-color","#585858");
						$("#diferenteValorinformacion"+pertenecebd+"_curp").css("visibility", "hidden");
						$("#informacion"+pertenecebd+"_curp").attr("disabled", true).css("color","#FFF");
					}else{
				        $("#informacion"+pertenecebd+"_curp").attr("disabled", true).css("background-color","#FE2E2E");
				        $("#slide1 input[type=checkbox]").prop('checked', true);
				        $("#diferenteValorinformacion"+pertenecebd+"_curp").css("visibility", "visible");
				    }
				
				 if($("#informacionRENAPO_apellidoPaterno").val() == $("#informacion"+pertenecebd+"_apellidoPaterno").val()) {
				        $("#informacion"+pertenecebd+"_apellidoPaterno").attr("disabled", true).css("background-color","#FFF");
				        $("#diferenteValorinformacion"+pertenecebd+"_apellidoPaterno").css("visibility", "hidden");
				    }else if($("#informacion"+pertenecebd+"_apellidoPaterno").val().trim() == "") { 
						$("#informacion"+pertenecebd+"_apellidoPaterno").attr("disabled", true).css("background-color","#585858");
						$("#diferenteValorinformacion"+pertenecebd+"_apellidoPaterno").css("visibility", "hidden");
						$("#informacion"+pertenecebd+"_apellidoPaterno").attr("disabled", true).css("color","#FFF");
					} else{
				        $("#informacion"+pertenecebd+"_apellidoPaterno").attr("disabled", true).css("background-color","#FE2E2E");
				        $("#diferenteValorinformacion"+pertenecebd+"_apellidoPaterno").css("visibility", "visible");
				    }
				
				if($("#informacionRENAPO_apellidoMaterno").val() == $("#informacion"+pertenecebd+"_apellidoMaterno").val()) {
			        $("#informacion"+pertenecebd+"_apellidoMaterno").attr("disabled", true).css("background-color","#FFF");
			        $("#diferenteValorinformacion"+pertenecebd+"_apellidoMaterno").css("visibility", "hidden");
			    }else if($("#informacion"+pertenecebd+"_apellidoMaterno").val().trim() == "") { 
						$("#informacion"+pertenecebd+"_apellidoMaterno").attr("disabled", true).css("background-color","#585858");
						$("#diferenteValorinformacion"+pertenecebd+"_apellidoMaterno").css("visibility", "hidden");
						$("#informacion"+pertenecebd+"_apellidoMaterno").attr("disabled", true).css("color","#FFF");
				}else{
			        $("#informacion"+pertenecebd+"_apellidoMaterno").attr("disabled", true).css("background-color","#FE2E2E");	
			        $("#diferenteValorinformacion"+pertenecebd+"_apellidoMaterno").css("visibility", "visible");
			    }
				
				if($("#informacionRENAPO_nombre").val() == $("#informacion"+pertenecebd+"_nombre").val()) {
			        $("#informacion"+pertenecebd+"_nombre").attr("disabled", true).css("background-color","#FFF");
			        $("#diferenteValorinformacion"+pertenecebd+"_nombre").css("visibility", "hidden");
			    }else if($("#informacion"+pertenecebd+"_nombre").val().trim() == "") { 
						$("#informacion"+pertenecebd+"_nombre").attr("disabled", true).css("background-color","#585858");
						$("#diferenteValorinformacion"+pertenecebd+"_nombre").css("visibility", "hidden");
						$("#informacion"+pertenecebd+"_nombre").attr("disabled", true).css("color","#FFF");
				}else{
			        $("#informacion"+pertenecebd+"_nombre").attr("disabled", true).css("background-color","#FE2E2E");	
			        $("#diferenteValorinformacion"+pertenecebd+"_nombre").css("visibility", "visible");
			    }
				
				if($("#informacionRENAPO_sexo").val() == $("#informacion"+pertenecebd+"_sexo").val()) {
			        $("#informacion"+pertenecebd+"_sexo").attr("disabled", true).css("background-color","#FFF");
			        $("#diferenteValorinformacion"+pertenecebd+"_sexo").css("visibility", "hidden");
			    }else if($("#informacion"+pertenecebd+"_sexo").val().trim() == "") { 
						$("#informacion"+pertenecebd+"_sexo").attr("disabled", true).css("background-color","#585858");
						$("#diferenteValorinformacion"+pertenecebd+"_sexo").css("visibility", "hidden");
						$("#informacion"+pertenecebd+"_sexo").attr("disabled", true).css("color","#FFF");
				}else{
			        $("#informacion"+pertenecebd+"_sexo").attr("disabled", true).css("background-color","#FE2E2E");
			        $("#diferenteValorinformacion"+pertenecebd+"_sexo").css("visibility", "visible");
			    }
				
				if($("#informacionRENAPO_fechaNacimiento").val() == $("#informacion"+pertenecebd+"_fechaNacimiento").val()) {
			        $("#informacion"+pertenecebd+"_fechaNacimiento").attr("disabled", true).css("background-color","#FFF");
			        $("#diferenteValorinformacion"+pertenecebd+"_fechaNacimiento").css("visibility", "hidden");
			    }else if($("#informacion"+pertenecebd+"_fechaNacimiento").val().trim() == "") { 
						$("#informacion"+pertenecebd+"_fechaNacimiento").attr("disabled", true).css("background-color","#585858");
						$("#diferenteValorinformacion"+pertenecebd+"_fechaNacimiento").css("visibility", "hidden");
						$("#informacion"+pertenecebd+"_fechaNacimiento").attr("disabled", true).css("color","#FFF");
				} else{
			        $("#informacion"+pertenecebd+"_fechaNacimiento").attr("disabled", true).css("background-color","#FE2E2E");
			        $("#diferenteValorinformacion"+pertenecebd+"_fechaNacimiento").css("visibility", "visible");
			    }
				
				if($("#informacionRENAPO_lugarNacimiento").val() == $("#informacion"+pertenecebd+"_lugarNacimiento").val()) {
			        $("#informacion"+pertenecebd+"_lugarNacimiento").attr("disabled", true).css("background-color","#FFF");
			        $("#diferenteValorinformacion"+pertenecebd+"_lugarNacimiento").css("visibility", "hidden");
			    }else if($("#informacion"+pertenecebd+"_lugarNacimiento").val().trim() == "") { 
						$("#informacion"+pertenecebd+"_lugarNacimiento").attr("disabled", true).css("background-color","#585858");
						$("#diferenteValorinformacion"+pertenecebd+"_lugarNacimiento").css("visibility", "hidden");
						$("#informacion"+pertenecebd+"_lugarNacimiento").attr("disabled", true).css("color","#FFF");
				} else{
			        $("#informacion"+pertenecebd+"_lugarNacimiento").attr("disabled", true).css("background-color","#FE2E2E");
			        $("#diferenteValorinformacion"+pertenecebd+"_lugarNacimiento").css("visibility", "visible");
			    }
				
				if($("#informacionRENAPO_nacionalidad").val() == $("#informacion"+pertenecebd+"_nacionalidad").val()) {
			        $("#informacion"+pertenecebd+"_nacionalidad").attr("disabled", true).css("background-color","#FFF");
			        $("#diferenteValorinformacion"+pertenecebd+"_nacionalidad").css("visibility", "hidden");
			    }else if($("#informacion"+pertenecebd+"_nacionalidad").val().trim() == "") { 
						$("#informacion"+pertenecebd+"_nacionalidad").attr("disabled", true).css("background-color","#585858");
						$("#diferenteValorinformacion"+pertenecebd+"_nacionalidad").css("visibility", "hidden");
						$("#informacion"+pertenecebd+"_nacionalidad").attr("disabled", true).css("color","#FFF");
				} else{
			        $("#informacion"+pertenecebd+"_nacionalidad").attr("disabled", true).css("background-color","#FE2E2E");
			        $("#diferenteValorinformacion"+pertenecebd+"_nacionalidad").css("visibility", "visible");
			    }
				
				if($("#informacionRENAPO_datosDocumentoProbatorio").val() == $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").val()) {
			        $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", true).css("background-color","#FFF");
			        $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", true).css("color","#000");
			    } else{
			        $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", true).css("background-color","#585858");
			        $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", true).css("color","#FFF");
			    }
				}
				else 
					{
					 $("#informacion"+pertenecebd+"_curp").attr("disabled", true).css("background-color","#FFF");
				        $("#diferenteValorinformacion"+pertenecebd+"_curp").css("visibility", "hidden");
				        $("#informacion"+pertenecebd+"_apellidoPaterno").attr("disabled", true).css("background-color","#FFF");
				        $("#diferenteValorinformacion"+pertenecebd+"_apellidoPaterno").css("visibility", "hidden");
				        $("#informacion"+pertenecebd+"_apellidoMaterno").attr("disabled", true).css("background-color","#FFF");
				        $("#diferenteValorinformacion"+pertenecebd+"_apellidoMaterno").css("visibility", "hidden");
				        $("#informacion"+pertenecebd+"_nombre").attr("disabled", true).css("background-color","#FFF");
				        $("#diferenteValorinformacion"+pertenecebd+"_nombre").css("visibility", "hidden");
				        $("#informacion"+pertenecebd+"_sexo").attr("disabled", true).css("background-color","#FFF");
				        $("#diferenteValorinformacion"+pertenecebd+"_sexo").css("visibility", "hidden");
				        $("#informacion"+pertenecebd+"_fechaNacimiento").attr("disabled", true).css("background-color","#FFF");
				        $("#diferenteValorinformacion"+pertenecebd+"_fechaNacimiento").css("visibility", "hidden");
				        $("#informacion"+pertenecebd+"_lugarNacimiento").attr("disabled", true).css("background-color","#FFF");
				        $("#diferenteValorinformacion"+pertenecebd+"_lugarNacimiento").css("visibility", "hidden");
				        $("#informacion"+pertenecebd+"_nacionalidad").attr("disabled", true).css("background-color","#FFF");
				        $("#diferenteValorinformacion"+pertenecebd+"_nacionalidad").css("visibility", "hidden");
				        $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", true).css("background-color","#FFF");
				        $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", true).css("color","#000");
					}
				
				
				}
			 
		};
		
	
			
			ResponsableController.prototype.informaciontemp = function(tabDB){
				
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
			ResponsableController.prototype.informaciontempCheck = function(tabDB){
				
				if(origendbtab == undefined )
					{
					origendbtab= origentab;
					}
				
				var tabDBo = origendbtab;
				origendbtab = tabDB.attributes[0].nodeValue;
				var cda = new Object();
				
				//certificador;
				var infoCertificador=false;
				if(datosCDAModificado != undefined)
				{
					var maxdatosCDAModificado = datosCDAModificado.length;
				if(maxdatosCDAModificado > 0)
				{
					for(var contador=0;contador<maxdatosCDAModificado;contador++ )
					{
						if(datosCDAModificado[contador].tipoNSS==="certificador")
						{
							infoCertificador=true;
						}
					}
				}
				infoCertificador =true;
				}
				
				
				
				if(infoCertificador)
				{
					if($('#slide1:checked').val()==undefined)
				{
					cda.certificado="0";
				}
					
				}else{cda.certificado=$('#slide1:checked').val();}
				
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
					cda.correcEstad="0";
				}
				else{cda.correcEstad=$('#slide10:checked').val();}
				
				cda.fuente=origendbtab;
//				// console.log(cda);
				origendbtab=tabDB.parentNode.firstChild.id;
				
//				guarada informacion
							
				
				
				datosModificados = new Object();
				tabDBo =tabDBo.toUpperCase();
				datosModificados.curp = $('#informacion'+tabDBo+'_curp').val();
				datosModificados.apellidoPaterno = $('#informacion'+tabDBo+'_apellidoPaterno').val();
				datosModificados.apellidoMaterno = $('#informacion'+tabDBo+'_apellidoMaterno').val();
				datosModificados.nombre = $('#informacion'+tabDBo+'_nombre').val();
				datosModificados.sexo = $('#informacion'+tabDBo+'_sexo').val();
				datosModificados.fechaNacimiento = $('#informacion'+tabDBo+'_fechaNacimiento').val();
				datosModificados.lugarNacimiento = $('#informacion'+tabDBo+'_lugarNacimiento').val();
				datosModificados.nacionalidad = $('#informacion'+tabDBo+'_nacionalidad').val();
				datosModificados.documentos = $('#informacion'+tabDBo+'_datosDocumentoProbatorio').val();
				datosModificados.pertenecebd = tabDBo;
				

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


			
				datosCDAModificado.push(datosModificados);
				
			};
			
						
			/*
			 * Guarda las modificaciones que se realicen en cada NSS
			 */
			
			var datosModificados = new Object();
			var datosCDAModificado = [];
			var datosCDAModificadoTipoCorreccion = new Object();
			var datosCDAModificadoTipoCorreccionArreglo = [];
			ResponsableController.prototype.guardaractualizacionNSS = function(cdaCheck){
				
				var tipoNSS=0;
				var tipoRegularizacion =0;
			
				var tipoCorreccion=[];
				
				tabDBo = ["CANASE","CIZ1","CIZ2","CIZ3","HISTORICO","BDTU"];
				for(var bd = 0; bd < 4 ; bd++)
				{
					datosModificados.curp = $('#informacion'+tabDBo[bd]+'_curp').val();
					datosModificados.apellidoPaterno = $('#informacion'+tabDBo[bd]+'_apellidoPaterno').val();
					datosModificados.apellidoMaterno = $('#informacion'+tabDBo[bd]+'_apellidoMaterno').val();
					datosModificados.nombre = $('#informacion'+tabDBo[bd]+'_nombre').val();
					datosModificados.sexo = $('#informacion'+tabDBo[bd]+'_sexo').val();
					datosModificados.fechaNacimiento = $('#informacion'+tabDBo[bd]+'_fechaNacimiento').val();
					datosModificados.lugarNacimiento = $('#informacion'+tabDBo[bd]+'_lugarNacimiento').val();
					datosModificados.nacionalidad = $('#informacion'+tabDBo[bd]+'_nacionalidad').val();
					datosModificados.documentos = $('#informacion'+tabDBo[bd]+'_datosDocumentoProbatorio').val();
					datosModificados.pertenecebd = tabDBo[bd];
					datosModificados.nss =nssCorreccion;
					datosCDAModificado.push(datosModificados);
					datosModificados = new Object();
					
				}		
				
				if($('#slide1:checked').val()!=undefined)
					{
					 datosCDAModificadoTipoCorreccion.tipoNSSC = "certificador";
					 tipoNSS = '1';					 
					}
				 if ($('#slide2:checked').val()!=undefined)
					{
						datosCDAModificadoTipoCorreccion.tipoNSSC = "asociadoCertificador";
						tipoNSS = '2';
					}
				 if ($('#slide3:checked').val()!=undefined)
					{
						datosCDAModificadoTipoCorreccion.tipoNSSC = "otraPersona";
						tipoNSS = '3';
					}
				 if ($('#slide4:checked').val()!=undefined)
					{
						datosCDAModificadoTipoCorreccion.tipoNSSC = "noExisteCanase";
						tipoNSS = '4';
					}
					///tipo regularizacion
				 if ($('#slide5:checked').val()!=undefined)
					{
						datosCDAModificadoTipoCorreccion.tipoNSSRD = "canceladoDuplicidad";
						tipoRegularizacion = '1';
						tipoCorreccion.push(tipoRegularizacion);
					}
				 if ($('#slide6:checked').val()!=undefined)
					{
						datosCDAModificadoTipoCorreccion.tipoNSSRCH = "CorrespondeHomonimo";
						tipoRegularizacion = '2';
						tipoCorreccion.push(tipoRegularizacion);
					}
				 if ($('#slide7:checked').val()!=undefined)
					{
						datosCDAModificadoTipoCorreccion.tipoNSSRNC = "noExisteCanase";
						tipoRegularizacion = '3';
						tipoCorreccion.push(tipoRegularizacion);
					}
				 if ($('#slide8:checked').val()!=undefined)
					{
						datosCDAModificadoTipoCorreccion.tipoNSSRCA = "CorrespondeOtroAsegurado";
						tipoRegularizacion = '4';
						tipoCorreccion.push(tipoRegularizacion);
					}
				 if ($('#slide9:checked').val()!=undefined)
					{
						datosCDAModificadoTipoCorreccion.tipoNSSCN = "CorreccionNomre";
						tipoRegularizacion = '5';
						tipoCorreccion.push(tipoRegularizacion);
					}
				 if ($('#slide10:checked').val()!=undefined)
					{
						datosCDAModificadoTipoCorreccion.tipoNSSCDA = "CorreccionDatoAsegurado";
						tipoRegularizacion = '6';
						tipoCorreccion.push(tipoRegularizacion);
					}
					
				datosCDAModificadoTipoCorreccion.nss =nssCorreccion;
				datosCDAModificadoTipoCorreccionArreglo.push(datosCDAModificadoTipoCorreccion);
				datosCDAModificadoTipoCorreccion = new Object();
				
				var idTramitePrincipal = idTramite;
				
					var url = "guardardoDatosAseguradoNSS.do";
					if (url !== null) {					

						var params =JSON.stringify(eval({idTramitePrincipal:idTramitePrincipal,
														 tipoCorreccion:tipoCorreccion,
														 nssCorreccion:nssCorreccion,
														 tipoRegularizacion:tipoRegularizacion,
														 tipoNSS:tipoNSS}));
						}						
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
//									 this.module.updateModel( "AlertComponent", "alert", {level:"info",message:"Error"} );

								}
							});		
													
				};

				
				
				/*
				 * Valida y da los movimineto 
				 * de panel de homonimia certificador 
				 */
			
ResponsableController.prototype.validarCheckboxMenu = function(basedatos, maxNSS){
	
				//RNF-UE-15
					if($("#informacionRENAPO_curp").val().trim()!=$("#informacion"+basedatos+"_curp").val()||
							$("#informacionRENAPO_sexo").val().trim()!=$("#informacion"+basedatos+"_sexo").val()||
							$("#informacionRENAPO_fechaNacimiento").val().trim()!=$("#informacion"+basedatos+"_fechaNacimiento").val()||
							$("#informacionRENAPO_lugarNacimiento").val().trim()!=$("#informacion"+basedatos+"_lugarNacimiento").val())
						{
							$("#slide10").prop('checked', true);
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
							$("#slide9").prop('checked', true);
							$("#labelslide9").css("left", "23px");
						}
					else
						{
						$("#slide9").removeAttr('checked');
						$("#labelslide9").css("left", "0px");
						}
					
					//RNF-UE-17
					if(maxNSS == 1)
						{
							$("#slide1").prop("checked", true);
							$("#labelslide1").css("left", "23px");
						}
					else
						{
						$("#slide1").removeAttr('checked');
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
				
ResponsableController.prototype.accionCheck = function(componentecheck)
	{
	
	var nombrecomp = componentecheck.name;
	var valor = $("#label"+nombrecomp+"").css("left");
	if(valor == "23px" )
		{
			$("#label"+nombrecomp+"").css("left", "3px");
			$("#label"+nombrecomp+"").removeAttr('checked');
		}
	else 
		{
			$("#label"+nombrecomp+"").css("left", "23px");
			$("#label"+nombrecomp+":checkbox").attr('checked', true);
		}
	};
				
ResponsableController.prototype.readCuentaIlogica = function(index){ 
//  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 15);
	this.module.controller.transition("confirmarCorreccionDatos");
  this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );
};

ResponsableController.prototype.readCuentaIndividualResumen = function(index){ 

	//if (guardadoParcial){
		//si realizó cambios, los marca en gris y comienza a validar (si aplican excepciones) 
		
	   //if(periodosIncompletos){
	     $("#modalCuentaIndividualPeriodos").modal('show');
	   //El sistema verifica que el Responsable haya realizado ajustes a todos los periodos de la cuenta individual por cada NRP
	   //} else{
	     //$("#modalCuentaIndividualCambiosCuentas").modal('show');
	   //}
	  
	//}else{
		//$("#modalCuentaIndividualCambiosCuenta").modal('show');
	//}
	 	
};

ResponsableController.prototype.confirmarSinCambios = function(){ 
	$("#modalCuentaIndividualCambiosCuenta").modal('hide');
	$("#modalCuentaIndividualCambiosCuentas").modal('show');
	
}

ResponsableController.prototype.confirmarPeriodos = function(){ 
	$("#modalCuentaIndividualPeriodos").modal('hide');
	$("#modalCuentaIndividualCambiosCuentas").modal('show');
	
}

ResponsableController.prototype.confirmarCambiosFinales = function(){ 
	//Conlleva la creacion de negocio
	this.module.service.guardarCuentaIndividual();
	$("#modalCuentaIndividualCambiosCuentas").modal('hide');
//	this.module.updateModel("CardLayoutComponent","responsableCardLayout", 14);
	this.module.controller.transition("CuentaIndividualConfirmarMainComponent");
}

ResponsableController.prototype.regresardeCuentaIlogica = function(index){ 
	$("#modal").modal('show');
	//Guardado Parcial
	this.module.service.guardarCuentaIndividual();
	//Regresa Resumen de Correcion
//	this.module.updateModel("CardLayoutComponent","responsableCardLayout", 4);  
	this.module.controller.transition("confirmarUI");
	$("#modal").modal('hide');
	
};

ResponsableController.prototype.cuentaIndividualGuardar = function(){
	$("#modal").modal('show');
	this.module.service.guardarCuentaIndividual();
	//this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );
	$("#modal").modal('hide');
	
};

ResponsableController.prototype.addPeriodoCuentaIlogica = function(index){
	if($("#modal").size() > 0 && typeof $("#modal").modal === 'function'){
		$("#modal").modal();
	}	
	var id = '#tabla' + index;	
	var rowCount = $(id + ' tr').length;	
	$(id + '> tbody:last').append($('<tr id="' + rowCount + '">')
						  .append($('<td>')
						  .append($('<input type="text" id="' + rowCount + 'fecRep" name="uname" required maxLength="10" size="10"/>')))
						  .append($('<td>')
						  .append($('<select id="' + rowCount + 'tipoMovIni" >   <option value="1" selected="">1</option>  <option value="7">7</option>  <option value="8">8</option> </select>')))
						  .append($('<td>')
						  .append($('<select id="' + rowCount + 'origenMovIni">   <option value="0" selected="">0</option>  <option value="1" >1</option>  <option value="2">2</option>  <option value="3">3</option>  <option value="4">4</option>  <option value="5">5</option>  <option value="6">6</option>  <option value="7">7</option>  <option value="8">8</option>  <option value="9">9</option> </select>')))
						  .append($('<td>')
						  .append($('<input type="text" id="' + rowCount + 'fecIniMov" name="uname" required maxLength="10" size="10"/>')))
						  .append($('<td>')
						  .append($('<input type="text" id="' + rowCount + 'salario" name="uname" required maxLength="10" size="10"/>')))
						  .append($('<td>')
						  .append($('<select id="' + rowCount + 'tipoSalario">   <option value="0" selected="">0</option>  <option value="1">1</option>  <option value="2">2</option>  <option value="3">3</option>  <option value="4">4</option>  <option value="5">5</option>  <option value="6">6</option>  <option value="7">7</option>  <option value="8">8</option>  <option value="9">9</option> </select>')))
						  .append($('<td>')
						  .append($('<select id="' + rowCount + 'tipoTrabajador">   <option value="1" selected="">1</option>  <option value="2">2</option>  <option value="3">3</option>  <option value="4">4</option> </select>')))
						  .append($('<td>')
						  .append($('<select id="' + rowCount + 'ext">   <option value="0" selected="">0</option>  <option value="1">1</option>  <option value="2">2</option>  <option value="3">3</option>  <option value="4">4</option>  <option value="5">5</option>  <option value="6">6</option>  <option value="7">7</option>  <option value="8">8</option>  <option value="9">9</option> </select>')))
						  .append($('<td>')
						  .append($('<select id="' + rowCount + 'subro">   <option value="0" selected="">0</option>  <option value="1">1</option>  <option value="2">2</option>  <option value="3">3</option>  <option value="4">4</option>  <option value="5">5</option>  <option value="6">6</option>  <option value="7">7</option>  <option value="8">8</option>  <option value="9">9</option> </select>')))
						  .append($('<td>')
						  .append($('<select id="' + rowCount + 'tipoMovFin">   <option value="0" selected="">0</option>  <option value="2" >2</option>  <option value="7">7</option> </select>')))
						  .append($('<td>')
						  .append($('<select id="' + rowCount + 'origenMovFin">   <option value="0" selected="">0</option>  <option value="1" >1</option>  <option value="2">2</option>  <option value="3">3</option>  <option value="4">4</option>  <option value="5">5</option>  <option value="6">6</option>  <option value="7">7</option>  <option value="8">8</option>  <option value="9">9</option> </select>')))
						  .append($('<td>')
						  .append($('<input type="text" id="' + rowCount + 'fecFinMov" name="uname" required maxLength="10" size="10"/>')))
						  .append($('<td>')
						  .append($('<select id="' + rowCount + 'jornada">   <option value="0" selected="">0</option>  <option value="1">1</option>  <option value="2">2</option>  <option value="3">3</option>  <option value="4">4</option>  <option value="5">5</option>  <option value="6">6</option>  <option value="7">7</option>  <option value="8">8</option>  <option value="9">9</option> </select>')))
						  .append($('<td>')
						  .append($('')))
						  .append($('<td bgcolor="#00FF00">')
						  .append($('')))
						  .append($('<td>')
						  .append($('<a href="#" onclick="module.controller.validar(' + rowCount + ')">Validar</a><br><a href="#" onclick="module.controller.descartar(' + rowCount + ')">Descartar</a>')))						  
						  );	
	module.service.removeModal();
};

ResponsableController.prototype.descartar = function(index){
	var id = '#' + index;	
	$(id).remove();
};

ResponsableController.prototype.validar = function(indice){
	var MSG_REQUIRED = 'Campo requerido.';
	var MSG_DATE_FORMAT = 'Las fechas no cumplen con el formato correcto.';
	var MSG_DATE_BEFORE = 'La fecha es posterior o anterior a la fecha actual.';
	var MSG_DATE_TM = 'La fecha inicio del movimiento es posterior a la fecha final del movimiento.';
	var MSG_TIPO_MOVIMIENTO = 'La combinación de tipo de movimiento inicial y final no es v\u00E1lida.';
	var MSG_SALARIO = 'El SBC no cumple con el formato correcto.';
	var dateValids = true;
	if(!module.controller.isValidDateFormat($("#" + indice + "fecRep").val())) {		
		module.controller.validateMsg(indice + "fecRep", {isValid:false, message :MSG_DATE_FORMAT, modal:false});
	    return false;
	}
	if(!module.controller.isValidDateFormat($("#" + indice + "fecIniMov").val())) {		
		module.controller.validateMsg(indice + "fecIniMov", {isValid:false, message :MSG_DATE_FORMAT, modal:false});
		dateValids = false;
	    return false;
	}
	if(!module.controller.isValidDateFormat($("#" + indice + "fecFinMov").val())) {		
		module.controller.validateMsg(indice + "fecFinMov", {isValid:false, message :MSG_DATE_FORMAT, modal:false});
		dateValids = false;
	    return false;
	}
	
	if (dateValids) {
		if(!module.controller.compareDates($("#" + indice + "fecIniMov").val(), new Date())) {
			module.controller.validateMsg(indice + "fecFinMov", {isValid:false, message :MSG_DATE_BEFORE, modal:false});			
			return false;
		}
		if(!module.controller.compareDates($("#" + indice + "fecFinMov").val(), new Date())) {
			module.controller.validateMsg(indice + "fecFinMov", {isValid:false, message :MSG_DATE_BEFORE, modal:false});			
			return false;
		}
		if(!module.controller.compareDates($("#" + indice + "fecIniMov").val(), $("#" + indice + "fecFinMov").val())) {
			module.controller.validateMsg(indice + "fecFinMov", {isValid:false, message :MSG_DATE_TM, modal:false});			
			return false;
		}
	}
	
	if(!module.controller.validarTipoMovimientoCorrecto($( '#' + indice + 'tipoMovIni option:selected' ).text(), 
				$( '#' + indice + 'tipoMovFin option:selected' ).text())) {		
		module.controller.validateMsg(indice + "fecFinMov", {isValid:false, message :MSG_TIPO_MOVIMIENTO, modal:false});		
	    return false;
	}
	
	if(!module.controller.isValidSBC($("#" + indice + "salario").val())) {		
		module.controller.validateMsg(indice + "salario", {isValid:false, message :MSG_SALARIO, modal:false});
		dateValids = false;
	    return false;
	}
	
	return true;
};


ResponsableController.prototype.compareDates = function(date1, date2) {
	var x = new Date(date1);
	var y = new Date(date2);
	return !y > x;
};

ResponsableController.prototype.registrarCambios = function(index, idTabla, idRenglon) {
	// console.log("Entrando: " + paramsCuentaIndividual.length);
	var valorAnterior = $("#" + index + "nssAnterior").val();
	var valorActual = $( "#" + index + "nssActual option:selected" ).text();
	var cveIdPeriodo = $( "#" + index + "nssId").val();	
	// console.log("valorAnterior: " + valorAnterior);
	// console.log("index: " + index);
	// console.log("valorActual: " + valorActual);
	// console.log("cveIdPeriodo: " + cveIdPeriodo);
	var r = 0;
	var arrayInput = {index: index, valorAnterior: valorAnterior, valorActual: valorActual, cveIdPeriodo: cveIdPeriodo, nss: valorActual};
	//Indica si se va a agregar el periodo
	var agregarPeriodo = true;	
	var changeColor = false;
	//Si el arreglo no tiene contenido	
	var diferentes= valorAnterior  != valorActual;
	if(paramsCuentaIndividual.length == 0) {        
        paramsCuentaIndividual.push(arrayInput);
		changeColor = true;
    }	
	else {
		for (r = 0; r < paramsCuentaIndividual.length; r++) {
			//Se revisa si existe el cambio en el arreglo
			if(paramsCuentaIndividual[r].cveIdPeriodo === cveIdPeriodo) {
				//Se remueve el cambio ya que regresa al actual
				paramsCuentaIndividual.splice(r,1);	
				//Se verifica si regreso al valor original para ver si se agrega al cambio				
				if (diferentes) {					
					//Se agrega con el valor actualizado	
					paramsCuentaIndividual.push(arrayInput);
					changeColor = true;					
				}
				agregarPeriodo = false;
				break;				
			}						
		}
		//Se valida si se agrega el periodo y si los valores son diferentes.
		if (agregarPeriodo && diferentes) {
			//Si no existe se agrega
			paramsCuentaIndividual.push(arrayInput);
			changeColor = true;
		}		
	}	
		//Cada que se agregue un objeto al arreglo debe cambiar de color la celda
		if (changeColor) {		
			$("#" + idTabla + "TablaPeriodos tr:nth-child(" + (idRenglon +1) + ") td:nth-child(14)" ).css("background-color", "LightGray");
		}
		else {
			$("#" +  idTabla + "TablaPeriodos tr:nth-child(" + (idRenglon +1) + ") td:nth-child(14)" ).css("background-color", "white");
		}		
	// console.log("Saliendo: " + paramsCuentaIndividual.length);
};

ResponsableController.prototype.registrarCambiosIlogica = function(index) {
	// console.log("Entrando: " + paramsCuentaIlogica.length);
	var cveIdPeriodo = $( "#" + index + "tipoMovimientoIniintcialId").val();
	
	var tipoMovimientoIniintcialAnterior = $("#" + index + "tipoMovimientoIniintcialAnterior").val();
	var tipoMovimientoIniintcialActual = $( "#" + index + "tipoMovimientoIniintcialActual option:selected" ).text();
	var diferentes= tipoMovimientoIniintcialAnterior  != tipoMovimientoIniintcialActual;	
	
	var origenMovimientoInicialAnterior = $("#" + index + "origenMovimientoInicialAnterior").val();
	var origenMovimientoInicialActual = $( "#" + index + "origenMovimientoInicialActual option:selected" ).text();
	var diferentes= origenMovimientoInicialAnterior  != origenMovimientoInicialActual;	
	
	var fechaInicioMovimientoAnterior = $("#" + index + "fechaInicioMovimientoAnterior").val();
	var fechaInicioMovimientoActual = $( "#" + index + "fechaInicioMovimientoActual").val();
	var diferentes= fechaInicioMovimientoAnterior  != fechaInicioMovimientoActual;		
	
	var salarioBaseAnterior = $("#" + index + "salarioBaseAnterior").val();
	var salarioBaseActual = $( "#" + index + "salarioBaseActual").val();
	var diferentes= salarioBaseAnterior  != salarioBaseActual;	
	
	var tipoSalarioAnterior = $("#" + index + "tipoSalarioAnterior").val();
	var tipoSalarioActual = $( "#" + index + "tipoSalarioActual option:selected" ).text();
	var diferentes= tipoSalarioAnterior  != tipoSalarioActual;		
	
	var eventualAnterior = $("#" + index + "eventualAnterior").val();	
	var eventualActual = $( "#" + index + "eventualActual option:selected" ).text();
	var diferentes= eventualAnterior  != eventualActual;		

	var extemporaneoConvenioSuspencionAnterior = $("#" + index + "extemporaneoConvenioSuspencionAnterior").val();
	var extemporaneoConvenioSuspencionActual = $( "#" + index + "extemporaneoConvenioSuspencionActual option:selected" ).text();
	var diferentes= extemporaneoConvenioSuspencionAnterior  != extemporaneoConvenioSuspencionActual;	
	
	var subrogacionServicioAnterior = $("#" + index + "subrogacionServicioAnterior").val();
	var subrogacionServicioActual = $( "#" + index + "subrogacionServicioActual option:selected" ).text();
	var diferentes= subrogacionServicioAnterior  != subrogacionServicioActual;		
	
	var tipoMovimientoFinalAnterior = $("#" + index + "tipoMovimientoFinalAnterior").val();
	var tipoMovimientoFinalActual = $( "#" + index + "tipoMovimientoFinalActual option:selected" ).text();
	var diferentes= tipoMovimientoFinalAnterior  != tipoMovimientoFinalAnterior;			
	
	var origenMovimientoFinalAnterior = $("#" + index + "origenMovimientoFinalAnterior").val();
	var origenMovimientoFinalActual = $( "#" + index + "origenMovimientoFinalActual option:selected" ).text();
	var diferentes= origenMovimientoFinalAnterior  != origenMovimientoFinalActual;	
	
	var fechaFinalMovimientoAnterior = $("#" + index + "fechaFinalMovimientoAnterior").val();
	var fechaFinalMovimientoActual = $( "#" + index + "fechaFinalMovimientoActual").val();
	var diferentes= fechaFinalMovimientoAnterior  != fechaFinalMovimientoActual;
	
	var jornadaAnterior = $("#" + index + "jornadaAnterior").val();	
	var jornadaActual = $( "#" + index + "jornadaActual option:selected" ).text();	
	var diferentes= jornadaAnterior  != jornadaActual;
	
	var r = 0;
	var arrayInput = {
					cveIdPeriodo: cveIdPeriodo,
					tipoMovimientoIniintcial: tipoMovimientoIniintcialActual,
					origenMovimientoInicial: origenMovimientoInicialActual,
					fechaInicioMovimiento: fechaInicioMovimientoActual,
					salarioBase: salarioBaseActual,
					tipoSalario: tipoSalarioActual,
					eventual: eventualActual,
					extemporaneoConvenioSuspencion: extemporaneoConvenioSuspencionActual,
					subrogacionServicio: subrogacionServicioActual,
					tipoMovimientoFinal: tipoMovimientoFinalActual,
					origenMovimientoFinal: origenMovimientoFinalActual,
					fechaFinalMovimiento: fechaFinalMovimientoActual,
					jornada: jornadaActual};
	//Indica si se va a agregar el periodo
	var agregarPeriodo = true;	
	//Si el arreglo no tiene contenido		
	if(paramsCuentaIlogica.length == 0) {        
        paramsCuentaIlogica.push(arrayInput);
    }	
	else {
		for (r = 0; r < paramsCuentaIlogica.length; r++) {
			//Se revisa si existe el cambio en el arreglo
			if(paramsCuentaIlogica[r].cveIdPeriodo === cveIdPeriodo) {
				//Se remueve el cambio ya que regresa al actual
				paramsCuentaIlogica.splice(r,1);	
				//Se verifica si regreso al valor original para ver si se agrega al cambio				
				if (diferentes) {					
					//Se agrega con el valor actualizado	
					paramsCuentaIlogica.push(arrayInput);					
				}
				agregarPeriodo = false;
				break;				
			}						
		}
		//Se valida si se agrega el periodo y si los valores son diferentes.
		if (agregarPeriodo && diferentes) {
			//Si no existe se agrega
			paramsCuentaIlogica.push(arrayInput);
		}
	}	
	// console.log("Saliendo: " + paramsCuentaIlogica.length);
};

ResponsableController.prototype.registrarCambiosTodos = function(index) {
	//var valorAnterior = $("#" + index + "Anterior").val();
	var valorActual = $( "#" + index + "Actual option:selected" ).text();
	var id = '#' + index + 'TablaPeriodos';	
	//Se obtiene el número de columnas de la tabla de periodos
	var rowCount = $(id + ' tr').length;
	var i = 0;
	//Se barren las columas 	
	for(i = 0; i < rowCount; i++) {
		var indice = '' + index + i;
		var indexNum = '' + index + i + 'nss';
		var isSelect = '#' + indexNum + 'Actual';
		//// console.log('isSelect: ' + isSelect);
		//Se valida que exista el componente en la tabla
		if ($(isSelect).val() != undefined) {
			$(isSelect).val(valorActual);
			ResponsableController.prototype.registrarCambios(indice, index, i);
		}
	}	
};

ResponsableController.prototype.validarTipoMovimientoCorrecto = function(tipoMovIni, tipoMovFin) {
	var combinacionCorrecta = false;
	switch (tipoMovIni) {
		case '1':
			if (tipoMovFin == '0' || tipoMovFin == '2' || tipoMovFin == '7') {
				combinacionCorrecta = true;
			}
			break;
		case '8':
			if (tipoMovFin == '0' || tipoMovFin == '2' || tipoMovFin == '7') {
				combinacionCorrecta = true;
			}
			break;
		case '7':
			if (tipoMovFin == '0' || tipoMovFin == '2' || tipoMovFin == '7') {
				combinacionCorrecta = true;
			}
			break;					
	}
	return combinacionCorrecta;
};

ResponsableController.prototype.isValidDateFormat = function(date){ 
	  return /^([0-2][0-9]|(3)[0-1])(\/)(((0)[0-9])|((1)[0-2]))(\/)\d{4}$/.test(date); 
};

ResponsableController.prototype.isValidSBC = function(date){ 
	  return /^[0-9]$/.test(date); 
};

ResponsableController.prototype.validateMsg = function( $key, $data ){
	alert($key)
    var textField = $('#' + $key);
    if ( textField ) {
        if( $data.isValid === true ){
            textField.parent().parent().removeClass('has-error');
        } else {
            textField.parent().parent().addClass('has-error');
            if( $data.message.length > 0 ){
            	if($data.modal===true){
            	 this.printMessage($key,$data.message);	
            	}else{
				 
                 	module.updateModel("AlertComponent", "alert", {level : "error",	message : "xxxxx"});
            	}
                
            }
        }
    }
};

ResponsableController.prototype.salirAgregarCancelar = function() {
	$("#modalAConsulta").modal('hide');	
};

ResponsableController.prototype.irAConsulta = function() {
	$("#modalAConsulta").modal('hide');	
	this.module.service.irAConsulta();
};
ResponsableController.prototype.mostrarDocumentonss = function (nombreArchivo, idBoveda) {
    idPersona = this.module.controller.model.userProfile.idPersona;
    folio = this.module.controller.consultaSolicitudController.model.consultaSolicitud.folio;

    document.forms['auxForm'].action = "atencionAutorizador/obtenerDocumento/" + idPersona + "/" + folio + "/ext/" + nombreArchivo + "/" + idBoveda;
    document.forms['auxForm'].submit();
};
