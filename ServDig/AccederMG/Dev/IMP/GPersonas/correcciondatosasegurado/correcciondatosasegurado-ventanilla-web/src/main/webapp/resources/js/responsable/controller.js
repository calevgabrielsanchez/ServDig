function ResponsableController(module) {
    this.module = module;
    this.model = {};
    this.consultaSolicitudController = new ConsultaSolicitudController(this.module);
    this.cancelarController = new CancelarController(this.module);
    this.solicitarInformacionController = new SolicitarInformacionController(this.module);
}

ResponsableController.prototype.mostrarDocumento = function(index){
  var documento = this.model.consultaSolicitud.gridDocumentos.data[index];
  if( !this.module.render.isEmpty( documento ) ){
    document.forms['auxForm'].action = "atencionAutorizador/obtenerDocumento/"+documento.idPersona+"/"+documento.folio+"/"+documento.extension+"/"+documento.nombreArchivo+"/"+documento.idDocBoveda;
    document.forms['auxForm'].submit();
  }    
};

ResponsableController.prototype.mostrarDocumentoBeneficiario = function(index){
	  var documento = this.model.consultaSolicitud.gridDocumentosBeneficiario.data[index];
	  if( !this.module.render.isEmpty( documento ) ){
	    document.forms['auxForm'].action = "atencionAutorizador/obtenerDocumento/"+documento.idPersona+"/"+documento.folio+"/"+documento.extension+"/"+documento.nombreArchivo+"/"+documento.idDocBoveda;
	    document.forms['auxForm'].submit();
	  }    
};
	
ResponsableController.prototype.bandeja = function(){
  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 0);
  this.module.controller.model.paginaTramitesActualTramites = this.module.controller.model.gridTramites.currentPage;
  this.module.controller.model.paginaTramitesActualHistorico = this.module.controller.model.gridHistorico.currentPage;
  this.module.updateModel("FormPanelComponent","filter","filtros");
};

ResponsableController.prototype.confirmar = function(){
  // Cerrar la modal, regresar a la bandeja y mostrar el mensaje de respuesta del server
	this.module.service.mostarModalAvanzar();  
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
	this.actualizarControles(this.module.view.correccionDatosUI);
};

ResponsableController.prototype.tamanioGrid = function(){
	this.module.view.confirmarUI.tamanioGrid();
};

ResponsableController.prototype.tamanioGridConsultaSolicitud = function(){
	this.module.render.componentTemplates['ConsultaSolicitudComponent'].tamanioGridConsultaSolicitud();
};

ResponsableController.prototype.postFetchConsultaSolUI = function(){
	this.actualizarControles(this.module.view.consultaSolicitudUI);
	this.tamanioBotonesConsultaSolicitud(this.module.view.consultaSolicitudUI);
};

ResponsableController.prototype.actualizarControles = function( component){
	estado = this.model.consultaSolicitud.estadoResponsable;
	if(!((estado == 0 || estado == 3) && this.model.consultaSolicitud.esPropietario )){
		component.inhabilitarControles();
	}
};

ResponsableController.prototype.tamanioBotonesConsultaSolicitud = function( component){
	component.tamanioBotonesSolicitud();
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
  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 2);
  if( this.model.consultaSolicitud.gridNSS.data.length === (this.model.currentNSSIndex+1) ){
      $("#btnSiguiente").html("Finalizar");
  }
};


ResponsableController.prototype.regresarInicioCorreccion = function(){
	$("#modal").modal('show');
	this.module.updateModel("CardLayoutComponent","responsableCardLayout", 1);
	$("#modal").modal('hide');
	};

ResponsableController.prototype.regresarCorreccion = function(){
  // Guardar el modelo los datos actuales de la forma antes de hacer el cambio
  this.module.validator.formToModel("FormPanelComponent-correccionDatos",
   this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex] );
  
  if( this.model.currentNSSIndex > 0 ){
    this.model.currentNSSIndex--;  
    this.model.detalle = this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex];
    this.module.render.notify("FormPanelComponent", "panelCorreccionDatos", "detalle");
  }else{
    
      //multiples personas por NSS
      this.model.currentPersonaNSSIndex = 0;
      this.model.detalle.informacionBDTUU = this.model.detalle.informacionFuentesNSS[this.model.currentPersonaNSSIndex];
      
      if(this.model.detalle.informacionFuentesNSS.length == 0 ){
	      $("#modalSalirInicioMen").modal();
      } else{
	      this.module.updateModel("NavegacionPersonaNSSComponent","navegacionPersonaNSS", "detalle");
      }
    
      $("#modal").modal('show');
      this.module.updateModel("CardLayoutComponent","responsableCardLayout", 2);
      $("#modal").modal('hide');
	  
      if(this.model.detalle.informacionFuentesNSS.length <= 1 ){
	      //solo hay un registro inhabilitar boton siguiente
	      $("#btnSiguientePersona").prop('disabled',true);
      }
      $("#btnAnteriorPersona").prop('disabled',true);
  }
  
  
};
ResponsableController.prototype.siguienteCorreccion = function(){
  // Guardar el modelo los datos actuales de la forma antes de hacer el cambio
	this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex].grupoCorreccion.idRegularizacionNSS = [];
  this.module.validator.formToModel("FormPanelComponent-correccionDatos",
   this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex] );
  
  if( this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex].tipoNSS.idTipoNSSCorreccion < 1 ){
    this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"Indique el tipo de NSS"} );
    return;
  }
  // Validar que tenga alguna opcion seleccionada
  var valid = false;
  if( this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex].grupoCorreccion !== null 
    && typeof this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex].grupoCorreccion === 'object' ){
    var that = this;
    $.each( this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex].grupoCorreccion, function (key, value) {
      if( that.model.consultaSolicitud.gridNSS.data[that.model.currentNSSIndex].grupoCorreccion[key] === true || that.model.consultaSolicitud.gridNSS.data[that.model.currentNSSIndex].grupoCorreccion[key] === "true"){
        valid = true;
      }
    });
  }
  var errorField = "<span id='curpError' class='error showElement'>Campo requerido.</span>";
  if( !valid ){
    this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"Seleccione un tipo de regularizaci\u00F3n"} );
    //modificar aqui como lo hace el validate de jquery
    var component = this.module.render.getComponentById("panelCorreccionDatos");
    //var valid = this.module.validator.validForm(component);
    //console.log(valid);
    return;
  }else{
    this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:""} );
  }
  
  if( this.model.consultaSolicitud.gridNSS.data.length > (this.model.currentNSSIndex+1) ){
    this.model.currentNSSIndex++;
    this.model.detalle = this.model.consultaSolicitud.gridNSS.data[this.model.currentNSSIndex];    
    this.module.render.notify("FormPanelComponent", "panelCorreccionDatos", "detalle");
    if( this.model.consultaSolicitud.gridNSS.data.length === (this.model.currentNSSIndex+1) ){
      $("#btnSiguiente").html("Finalizar");
    }
  }else{
    this.model.resumenCorreccion = this.model.consultaSolicitud.gridNSS.data;
    this.module.render.notify("ResumenCorreccionComponent", "resumenCorreccion", "resumenCorreccion");
    $("#modal").modal('show');
    this.module.updateModel("CardLayoutComponent","responsableCardLayout", 3);
    $("#modal").modal('hide');
  }
};


ResponsableController.prototype.selectTramite = function(index){  
  this.module.service.fetchSolicitud("consultaSolicitud",this.module, index);
  this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );
};

ResponsableController.prototype.selectHistorico = function(index){
	this.module.service.fetchSolicitudHistorico("consultaSolicitud",this.module, index);
	this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );  
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
	var contenido =0;
	this.model.filtros = {};
	this.module.validator.formToModel("FormPanelComponent-filter", this.model.filtros );
	this.model.filtrosBusqueda = this.model.filtros;
	$.each( this.model.filtros, function(){ 
		if(arguments[1]===null || arguments[1]==="") {
				contenido++;
	}}
	);
	if(contenido==3){
		this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:"Debe ingresar un criterio de b\u00FAsqueda"} );
	}else{
		this.module.service.fetchFiltros("gridTramites","gridHistorico",this.model);	
		this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );
	}
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
    this.cancelarController.model = this.model;
    this.solicitarInformacionController.model = this.model;
    this.module.render.draw();
    this.module.service.getUserProfile(this.module);
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
	this.module.updateModel( "CardLayoutComponent", "responsableCardLayout", 1);
	data.estadoResponsable=parseInt(data.estadoResponsable);
	
	//Botones inferiores por Default
	var buttonCardIndex=0; //data.estadoResponsable;
	//Botones superiores por Default y para Si es Operada o Atendida 
	var buttonAccionesCardIndex=0;
	 
	if(data.esPropietario){
		if (data.estadoResponsable == 0){
			buttonCardIndex = 1;
			buttonAccionesCardIndex = 1;
		}else if (data.estadoResponsable == 1){
			buttonCardIndex = 2;
		}else if (data.estadoResponsable == 2){
			buttonCardIndex = 2;
			buttonAccionesCardIndex = 2;
		}
	}
	
	//solo en estado pantalla 0 y 3  debe permitir finalizar los cambios..
	var buttonConfirmarCardIndex = 1;
	
	if((data.estadoResponsable == 0 || data.estadoResponsable == 3) && data.esPropietario ){
		buttonConfirmarCardIndex = 0;
	}
	
	this.module.updateModel( "CardLayoutComponent", "responsableButtonsCardLayout", buttonCardIndex);//Botones inferiores
	this.module.updateModel( "CardLayoutComponent", "responsableAccionesButtonsCardLayout", buttonAccionesCardIndex);//Botones superiores
	
	this.module.updateModel("CardLayoutComponent", "responsableConfirmarButtonsCardLayout", buttonConfirmarCardIndex);
	this.module.updateModel("CardLayoutComponent", "responsableCorreccionButtonsCardLayout", buttonConfirmarCardIndex);
	this.module.updateModel( "ConsultaSolicitudComponent", componentId, data );
};

ResponsableController.prototype.salirInicio = function(){
	  $("#modalSalirInicioMen").modal('hide');
};

ResponsableController.prototype.clean = function(){
	this.model.filtros = {};
	this.model.filtrosBusqueda = this.model.filtros;
	this.module.validator.formToModel("FormPanelComponent-filter", this.model.filtros );
	this.module.service.cleanFiltros("gridTramites","gridHistorico",this.model);	
	this.module.updateModel( "AlertComponent", "alert", {level:"info",message:""} );
};

ResponsableController.prototype.showBtnNuevaSolicitud = function(){
	if ($('#divCollapse').hasClass('in')){
		document.getElementById("spanmenuBandeja").classList.remove("glyphicon-menu-up");
		document.getElementById("spanmenuBandeja").classList.add("glyphicon-menu-down");	
		$("#panelNuevaSolicitud").show();
    }else{
    	
		document.getElementById("spanmenuBandeja").classList.remove("glyphicon-menu-down");
		document.getElementById("spanmenuBandeja").classList.add("glyphicon-menu-up");
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

ResponsableController.prototype.aceptarCertificadoNSS = function(){
	  $("#modalcertificadoNSS").modal('hide');
};


ResponsableController.prototype.obtenerDatosNSS = function(){
debugger
	
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
					debugger
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
							    } else{
							        $("#informacion"+pertenecebd+"_curp").attr("disabled", false).css("background-color","#FE2E2E");
							        $("#slide1 input[type=checkbox]").prop('checked', false);
							    }
							$("#informacion"+pertenecebd+"_apellidoPaterno").val(data[i].apellidoPaterno);
							 if($("informacionRENAPO_apellidoPaterno").val() == $("#informacion"+pertenecebd+"_apellidoPaterno").val()) {
							        $("#informacion"+pertenecebd+"_apellidoPaterno").attr("disabled", true).css("background-color","#FFF");
							    } else{
							        $("#informacion"+pertenecebd+"_apellidoPaterno").attr("disabled", false).css("background-color","#FE2E2E");
							        modnombre= modnombre +1;
							    }
							$("#informacion"+pertenecebd+"_apellidoMaterno").val(data[i].apellidoMaterno);
							if($("informacionRENAPO_apellidoMaterno").val() == $("#informacion"+pertenecebd+"_apellidoMaterno").val()) {
						        $("#informacion"+pertenecebd+"_apellidoMaterno").attr("disabled", true).css("background-color","#FFF");
						    } else{
						        $("#informacion"+pertenecebd+"_apellidoMaterno").attr("disabled", false).css("background-color","#FE2E2E");
						        modnombre= modnombre +1;
						    }
							$("#informacion"+pertenecebd+"_nombre").val(data[i].nombre);
							if($("informacionRENAPO_nombre").val() == $("#informacion"+pertenecebd+"_nombre").val()) {
						        $("#informacion"+pertenecebd+"_nombre").attr("disabled", true).css("background-color","#FFF");
						    } else{
						        $("#informacion"+pertenecebd+"_nombre").attr("disabled", false).css("background-color","#FE2E2E");
						        modnombre= modnombre +1;
						    }
							$("#informacion"+pertenecebd+"_sexo").val(data[i].sexo);
							if($("informacionRENAPO_sexo").val() == $("#informacion"+pertenecebd+"_sexo").val()) {
						        $("#informacion"+pertenecebd+"_sexo").attr("disabled", true).css("background-color","#FFF");
						    } else{
						        $("#informacion"+pertenecebd+"_sexo").attr("disabled", false).css("background-color","#FE2E2E");
						    }
							$("#informacion"+pertenecebd+"_fechaNacimiento").val(data[i].fechaNacimiento);
							if($("informacionRENAPO_fechaNacimiento").val() == $("#informacion"+pertenecebd+"_fechaNacimiento").val()) {
						        $("#informacion"+pertenecebd+"_fechaNacimiento").attr("disabled", true).css("background-color","#FFF");
						    } else{
						        $("#informacion"+pertenecebd+"_fechaNacimiento").attr("disabled", false).css("background-color","#FE2E2E");
						    }
							$("#informacion"+pertenecebd+"_lugarNacimiento").val(data[i].lugarNacimiento);
							if($("informacionRENAPO_lugarNacimiento").val() == $("#informacion"+pertenecebd+"_lugarNacimiento").val()) {
						        $("#informacion"+pertenecebd+"_lugarNacimiento").attr("disabled", true).css("background-color","#FFF");
						    } else{
						        $("#informacion"+pertenecebd+"_lugarNacimiento").attr("disabled", false).css("background-color","#FE2E2E");
						    }
							$("#informacion"+pertenecebd+"_nacionalidad").val(data[i].nacionalidad);
							if($("informacionRENAPO_nacionalidad").val() == $("#informacion"+pertenecebd+"_nacionalidad").val()) {
						        $("#informacion"+pertenecebd+"_nacionalidad").attr("disabled", true).css("background-color","#FFF");
						    } else{
						        $("#informacion"+pertenecebd+"_nacionalidad").attr("disabled", false).css("background-color","#FE2E2E");
						    }
							$("#informacion"+pertenecebd+"_datosDocumentoProbatorio").val(data[i].pertenecebd);
							if($("informacionRENAPO_datosDocumentoProbatorio").val() == $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").val()) {
						        $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", true).css("background-color","#FFF");
						    } else{
						        $("#informacion"+pertenecebd+"_datosDocumentoProbatorio").attr("disabled", false).css("background-color","#FE2E2E");
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
  	          										
				},
				error : function(error) {
					fnProcesarErrores(error,"form#informacionHistoriaLaboralForm");
					$.unblockUI();
				}
			});
	}
	
	
};

var mov=0;
ResponsableController.prototype.siguienteNSS = function(){
	debugger
	var valor="";
	mov = mov -101; 
	valor = mov+"px";
	 $("#contBoton").css("left", valor );
};

ResponsableController.prototype.anteriorNSS = function(){
	debugger
	var valor="";
	mov = mov +101; 
	valor = mov+"px";
	 $("#contBoton").css("left", valor );
};

ResponsableController.prototype.guardaractualizacionNSS = function(){
	debugger
//		this.module.service.datoNSS();
//		var params = this.module.controller.model.solicitarInformacion;
	var nombredat="jorge";
	var apellidoPdata="hola";
	var apellidoMdata="hola";
	var curpdata="hola";
	var sexodata="hola";
	var fechaNacimientodata="hola";
	var lugarNacimientodata="hola";
	var nacionalidaddata="hola";
	var Documentosdata="hola";
		var url = "guardardoDatosAseguradoNSS.do";
		if (url !== null) {		
//			var params =JSON.stringify(eval({nss:nss0}));
			var params =JSON.stringify(eval({listadatosNSS:{nombre:nombredat,apellidopaterno:apellidoPdata,apellidomaterno:apellidoMdata,curp:curpdata,
				sexo:sexodata,fechaNacimiento:fechaNacimientodata,lugarNacimiento:lugarNacimientodata,nacionalidad:nacionalidaddata,Documentos:Documentosdata}}));
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
						fnProcesarErrores(error,"form#informacionHistoriaLaboralForm");
						$.unblockUI();
					}
				});		
	};