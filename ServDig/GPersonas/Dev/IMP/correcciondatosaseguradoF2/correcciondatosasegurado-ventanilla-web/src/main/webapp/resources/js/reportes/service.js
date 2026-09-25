function ReportesServices(module) {
  this.module = module;
  this.READ_TRAMITES_ASIGNADOS="visorReportes/tramitesReportes.do";
  this.READ_VARIABLE_ELEGIDA="visorReportes/variableReporte.do";
  
  
  this.READ_HISTORICO_SOLICITUDES="atencionAutorizador/historicoSolicitudes.do";
  this.READ_USER_PROFILE="userProfile.do";
  this.READ_SOLICITUD = "atencionResponsable/obtenerSolicitud";
  this.READ_COMBO = "atencionAutorizador/obtenerCombo.do";
  this.ORIGEN_COMBO = "visorReportes/obtenerOrigenCombo.do";
  this.TIPO_TRAMITE_COMBO="visorReportes/obtenerTipoTramiteCombo.do";
  this.TIPO_TRAMITE_REPORTE_COMBO="visorReportes/obtenerTipoTramiteReporteCombo.do";
  this.DELEGACION_COMBO="visorReportes/obtenerDelegacionCombo.do";
  this.SUBDELEGACION_COMBO="visorReportes/obtenerSubdelegacionCombo.do";
  this.AUTORIZO_COMBO="visorReportes/obtenerAutorizoCombo.do";
  this.RESPONSABLE_COMBO="visorReportes/obtenerResponsableCombo.do";	  
  this.CONFIRMAR_SOLICITUD = "atencionAutorizador/autorizarSolicitud.do";
  this.VARIABLE_COMBO = "visorReportes/obtenerVariables.do";
  this.REASIGNAR = "atencionAutorizador/reasignarResponsable.do";
  this.SOLICITAR_INFORMACION = "atencionAutorizador/solicitarInformacion.do";
  this.RECHAZAR = "atencionAutorizador/rechazarSolicitud.do";
  this.LOGOUT="j_spring_security_logout";
  this.READ_ESTATUS = "atencionAutorizador/obtenerEstatus";
  this.CANCELAR_SOLICITUD = "atencionAutorizador/cancelarSolicitud";
  
};

ReportesServices.prototype.selectField = function(fieldId,valor) {
	$("#" + fieldId).val(valor);
};

ReportesServices.prototype.reasignar = function() { 
  $("#modal").modal();
	var url = this.REASIGNAR;
	var module = this.module;
	var params = this.module.controller.model.reasignacion;
	if (url !== null) {
		$.ajax({
			url : url,
			type : "POST",
			contentType : "application/json; charset=UTF-8",
			data : JSON.stringify(params),
			dataType : "json",
			success : function(data) {
        $("#modal").modal('hide');
        $('div.modal-backdrop.fade').remove();
				 module.updateModel( "AlertComponent", "alert", {level:"info",message:"Operaci\u00F3n exitosa"} );
			        module.updateModel("CardLayoutComponent","responsableCardLayout", 0);
			},
			error : function(errMsg) {
				module.service.removeModal();
				module.updateModel( "AlertComponent", "alert", {level:"danger",message:"No fue posible realizar la operaci\u00F3n "} );
			}
		});
	}
};

ReportesServices.prototype.rechazar = function() { 
  $("#modal").modal();
	var url = this.RECHAZAR;
  var module = this.module;
  var params = this.module.controller.model.rechazar;
  if( url !== null ){
    $.ajax({
      data: JSON.stringify( params ),
      url: url,
      type: "POST",     
      contentType: "application/json; charset=UTF-8",
      dataType: "json",            
      success: function(data){    
        $("#modal").modal('hide');
        $('div.modal-backdrop.fade').remove();
        module.updateModel( "AlertComponent", "alert", {level:"info",message:"Operaci\u00F3n exitosa"} );
        module.updateModel("CardLayoutComponent","responsableCardLayout", 0);
      },
      error: function(errMsg){
        $("#modal").modal('hide');
        $('div.modal-backdrop.fade').remove();
        module.updateModel( "AlertComponent", "alert", {level:"danger",message:"No fue posible realizar la operaci\u00F3n"} );        
      }
    });
  }
};

ReportesServices.prototype.solicitarInformacion = function() { 
  $("#modal").modal();
	var url = this.SOLICITAR_INFORMACION;
  var module = this.module;
  var params = this.module.controller.model.solicitarInformacion;
  if( url !== null ){
    $.ajax({
      data: JSON.stringify( params ),
      url: url,
      type: "POST",     
      contentType: "application/json; charset=UTF-8",
      dataType: "json",            
      success: function(data){   
        $("#modal").modal('hide');
        $('div.modal-backdrop.fade').remove();
        module.updateModel( "AlertComponent", "alert", {level:"info",message:"Ser\u00e1 necesario que contacte al Asegurado para solicitarle la informaci\u00f3n adicional que se requiere para continuar con el tr\u00e1mite."} );
        module.updateModel("CardLayoutComponent","responsableCardLayout", 0);
      },
      error: function(errMsg){
        $("#modal").modal('hide');
        $('div.modal-backdrop.fade').remove();
        module.updateModel( "AlertComponent", "alert", {level:"danger",message:"No fue posible realizar la operaci\u00F3n"} );        
      }
    });
  }
};

ReportesServices.prototype.combos = function(model, fieldId, module ) { 
  switch( model ){
//    case 'fetchResponsables' : this.fetchResponsables(fieldId, module);
//      break;
    case 'gruposCorreccion' : module.updateModel("SelectFieldComponent", fieldId, this.module.controller.model[model] );
      break;
    case 'tiposNSS': module.updateModel("SelectFieldComponent", fieldId,this.module.controller.model[model]);
		break;
    case 'fetchCombo' : this.fetchCombo(fieldId, module);
    	break;
    case 'fetchOrigenCombo': this.fetchOrigenCombo(fieldId, module);
		break;
    case 'fetchTipoTramiteCombo': this.fetchTipoTramiteCombo(fieldId, module);
		break;
    case 'fetchDelegacionCombo': this.fetchDelegacionCombo(fieldId, module);
		break;
    case 'fetchSubdelegacionCombo': this.fetchSubdelegacionCombo(null,fieldId, module);
		break;
    case 'fetchAutorizoCombo': this.fetchAutorizoCombo(null,null,fieldId, module);
		break;
    case 'fetchResponsableCombo': this.fetchResponsableCombo(null,null,fieldId, module);
		break;
    case 'fetchVariableCombo': this.fetchVariableCombo(fieldId, module);
		break;
	
  }
//	$('#FormPanelComponent-filter').show();
//	$('#grid').show();
//	$('#generaReporte').hide();
};

ReportesServices.prototype.confirmar = function() {
  $("#modalEnvioSindo").modal('hide');	
  $("#modal").modal();
	var url = this.CONFIRMAR_SOLICITUD;
  var module = this.module;
  var params = this.module.controller.model.confirmar;
  if( url !== null ){
    $.ajax({
      data: JSON.stringify( params ),
      url: url,
      type: "POST",     
      contentType: "application/json; charset=UTF-8",
      dataType: "json",            
      success: function(data){    
        $("#modal").modal('hide');
        $('div.modal-backdrop.fade').remove();
        module.updateModel( "AlertComponent", "alert", {level:"info",message:"Operaci\u00F3n exitosa"} );
        module.updateModel("CardLayoutComponent","responsableCardLayout", 0);
      },
      error: function(errMsg){
        $("#modal").modal('hide');
        $('div.modal-backdrop.fade').remove();
        module.updateModel( "AlertComponent", "alert", {level:"danger",message:"No fue posible realizar la operaci\u00F3n"} );        
      }
    });
  }
};

ReportesServices.prototype.cancelar = function() {
	  $("#modal").modal();
		var url = this.CANCELAR_SOLICITUD;
		var module = this.module;
		var params = this.module.controller.model.cancelar;
		if (url !== null) {
			$.ajax({
				url : url,
				type : "POST",
				contentType : "application/json; charset=UTF-8",
				data : JSON.stringify(params),
				dataType : "json",
				success : function(data) {
	        $("#modal").modal('hide');	        
					module.updateModel("AlertComponent", "alert", {
						level : "info",
						message : "Operaci\u00F3n exitosa"
					});
					module.updateModel("CardLayoutComponent",
							"responsableCardLayout", 0);
					$('div.modal-backdrop.fade').remove();
				},
				error : function(errMsg) {
	        $("#modal").modal('hide');
	        $('div.modal-backdrop.fade').remove();
					module.updateModel("AlertComponent", "alert", {
						level : "danger",
						message : "No fue posible realizar la operaci\u00F3n"
					});
				}
			});
		}
	};

ReportesServices.prototype.getUserProfile = function(module) {
	var url = this.READ_USER_PROFILE;
	if (url !== null) {
		$
				.ajax({
					url : url,
					type : "GET",
					contentType : "application/json; charset=UTF-8",
					dataType : "json",
					success : function(data) {
						module.updateModel("UserProfileComponent",
								"userProfile", data);
						module.controller.model.perfil.delegacion = data.idDelegacion;
						module.controller.model.perfil.subdelegacion = data.idSubdelegacion;
						module.controller.model.perfil.perfilDescripcion = data.perfilDescripcion;
					},
					error : function(errMsg) {
						if (errMsg === null) {
							errMsg = "create.error";
						}
						module.updateModel("alert", {
							level : "danger",
							message : errMsg
						});
					}
				});
	}
};

ReportesServices.prototype.fetchNSS = function(gridId, module, page ) { 
	// Los datos de los grids vendram dentro del solicitud
  var grid = module.controller.model.consultaSolicitud.gridNSS;  
  module.updateModel( "GridComponent", gridId, grid );
};
ReportesServices.prototype.fetchDocumentos = function(gridId, module, page ) { 
	// Los datos de los grids vendram dentro del solicitud
  var grid = module.controller.model.consultaSolicitud.gridDocumentos;
  module.updateModel( "GridComponent", gridId, grid );
};
ReportesServices.prototype.fetchDocumentosBeneficiario = function(gridId, module, page) {  
    // Los datos de los grids vendran dentro del solicitud
    var grid = module.controller.model.consultaSolicitud.gridDocumentosBeneficiario;
    module.updateModel("GridComponent", gridId, grid);
};
ReportesServices.prototype.fetchHistoriaLaboral = function(gridId, module, page ) { 
	// Los datos de los grids vendram dentro del solicitud
  var grid = module.controller.model.consultaSolicitud.gridHistoriaLaboral;
  module.updateModel( "GridComponent", gridId, grid );
};
ReportesServices.prototype.fetchDatosAdicionales = function(gridId, module, page ) { 
	// Los datos de los grids vendram dentro del solicitud
  var grid = module.controller.model.consultaSolicitud.gridDatosAdicionales;
  module.updateModel( "GridComponent", gridId, grid );
};

ReportesServices.prototype.fetchGeneracionReporte = function() { 
//$("p").css({"background-color": "yellow", "font-size": "200%"});
//	$("#estadisticaReporte").css({"background": "#fff", "border": "0px","box-shadow": "0px 0px #fff"});
//	$("#subdelegacionReporte").css({"background": "#fff", "border": "0px","box-shadow": "0px 0px #fff"});
//	$("#variableSeleccionadaReporte").css({"background": "#fff", "border": "0px","box-shadow": "0px 0px #fff"});

};
var cont=0;
ReportesServices.prototype.fetchTramites = function(gridId, module, page ) {
if(cont != 0){	
	if($("#modalReporte").size() > 0 && typeof $("#modalReporte").modal === 'function'){
		$("#modalReporte").modal();
	}
	if(page === 1){
        if(this.module.controller.model.paginaTramitesActualTramites != undefined){
            page = this.module.controller.model.paginaTramitesActualTramites;
            this.module.controller.model.paginaTramitesActualTramites = undefined;
        }
    }
	var url = this.READ_TRAMITES_ASIGNADOS;
	var filtrosBusqueda = {};
	if (this.module.controller.model.filtros != undefined){
		filtrosBusqueda = this.module.controller.model.filtros;
	}	
  var params = {
		  page: page,
		  pageSize:10,
		  filter: filtrosBusqueda };
  if( url !== null ){
    $.ajax({
      url: url,
      data: JSON.stringify( params ),
      type: "POST",     
      contentType: "application/json; charset=UTF-8",
      dataType: "json",            
      success: function(data){
    	module.service.removeModal();
        module.updateModel( "GridComponent", gridId, data );
      },
      error: function(errMsg){
    	module.service.removeModal();
        if( errMsg === null ){
          errMsg = "create.error";
        }
        module.updateModel( "alert", { level:"danger", message: errMsg} );
      }
    });
  }
}
cont = cont + 1;
};

ReportesServices.prototype.fetchHistorico = function(gridId, module, page) {
	if($("#modal").size() > 0 && typeof $("#modal").modal === 'function'){
		$("#modal").modal();
	}
	
	if(page === 1){
        if(this.module.controller.model.paginaTramitesActualHistorico != undefined){
            page = this.module.controller.model.paginaTramitesActualHistorico;
            this.module.controller.model.paginaTramitesActualHistorico = undefined;
        }
    }
	
	var url = this.READ_HISTORICO_SOLICITUDES;
	var filtrosBusqueda = {};
	if (this.module.controller.model.filtrosBusqueda != undefined){
		filtrosBusqueda = this.module.controller.model.filtrosBusqueda;
	}
	var params = {
		filter : filtrosBusqueda,
		page : page,
		pageSize : 5
	};
	if (url !== null) {
		$.ajax({
			url : url,
			data : JSON.stringify(params),
			type : "POST",
			contentType : "application/json; charset=UTF-8",
			dataType : "json",
			success : function(data) {
				module.service.removeModal();
				module.updateModel("GridComponent", gridId, data);
			},
			error : function(errMsg) {
				module.service.removeModal();
				if (errMsg === null) {
					errMsg = "create.error";
				}
				module.updateModel("alert", {
					level : "danger",
					message : errMsg
				});
			}
		});
	}
};




ReportesServices.prototype.fetchSolicitud = function(module, id) { 
	//$("#modal").modal();
		var url = this.READ_TRAMITES_ASIGNADOS;
		var tmp = {};
	tmp = this.module.controller.model.filtros;
	tmp.nssInvolucrado = this.module.controller.model.gridTramites.data[id].nssInvolucrados;
	tmp.folio = this.module.controller.model.gridTramites.data[id].folio;
	  var errorFetch = false;
	  
		var params = {
			filter : tmp,
			page : 1,
			pageSize : 5
		};
		if (url !== null) {
			$.ajax({
				url : url,
				data : JSON.stringify(params),
				type : "POST",
				contentType : "application/json; charset=UTF-8",
				dataType : "json",
				success : function(data) {
				module.updateModel("GridComponent", "gridTramitesDetalle", data);				
				},
				error : function(errMsg) {
					errorFetch = true;
				}
			});
		}	
				
		if(errorFetch){
			$("#modal").modal('hide');
	        $('div.modal-backdrop.fade').remove();
			module.updateModel("AlertComponent", "alert", {
				level : "danger",
				message : "No se encontr\u00F3 informaci\u00F3n."
			});
		}
    };


ReportesServices.prototype.fetchEstatus = function(componentId, module, id) {
	$("#modal").modal();
  var url = this.READ_ESTATUS;
	var params = {
		idTramite : this.module.controller.model.gridTramites.data[id].idTramite,
		folio : this.module.controller.model.gridTramites.data[id].folio,
		origen : this.module.controller.model.gridTramites.data[id].origen
	};
	if (url !== null) {
		$.ajax({
			url : url,
			data : JSON.stringify(params),
			type : "POST",
			contentType : "application/json; charset=UTF-8",
			dataType : "json",
			success : function(data) {
				module.updateModel("CardLayoutComponent","responsableCardLayout", 8);
				//module.updateModel("BitacoraUI", componentId,data);
		        $("#modal").modal('hide');
		        $('div.modal-backdrop.fade').remove();
			},
			error : function(errMsg) {
		        $("#modal").modal('hide');
		        $('div.modal-backdrop.fade').remove();
				if (errMsg === null) {
					errMsg = "create.error";
				}
				module.updateModel("alert", {
					level : "danger",
					message : errMsg
				});
			}
		});
	}
};

ReportesServices.prototype.fetchEstatusHistorico = function(componentId, module, id) {
	$("#modal").modal();
  var url = this.READ_ESTATUS;
	var params = {
		idTramite : this.module.controller.model.gridHistorico.data[id].idTramite,
		folio : this.module.controller.model.gridHistorico.data[id].folio,
		origen : this.module.controller.model.gridHistorico.data[id].origen
	};
	if (url !== null) {
		$.ajax({
			url : url,
			data : JSON.stringify(params),
			type : "POST",
			contentType : "application/json; charset=UTF-8",
			dataType : "json",
			success : function(data) {
				module.updateModel("CardLayoutComponent","responsableCardLayout", 8);
				//module.updateModel("BitacoraUI", componentId,data);
		        $("#modal").modal('hide');
		        $('div.modal-backdrop.fade').remove();
			},
			error : function(errMsg) {
		        $("#modal").modal('hide');
		        $('div.modal-backdrop.fade').remove();
				if (errMsg === null) {
					errMsg = "create.error";
				}
				module.updateModel("alert", {
					level : "danger",
					message : errMsg
				});
			}
		});
	}
};

ReportesServices.prototype.fetchListEstatus = function(gridId, module, page) {  
	var grid = module.controller.model.bitacora.gridEstatus;
	module.updateModel("GridComponent", gridId, grid);
};

var responsable;
var curp;
var filtro=true;
ReportesServices.prototype.fetchFiltros = function(gridId,module,page) {
	
		var url = this.READ_TRAMITES_ASIGNADOS;
		var auxModel = {};
		var filtrosVacios = false;
		var contadorFiltrosVacios = 0; 

  this.module.validator.formToModel("FormPanelComponent-filter", auxModel);
  this.module.controller.model.filtros = auxModel;
	  
	  if(module.controller.model.perfil.perfilDescripcion == 'NORMATIVO'){
		if(auxModel.delegacion == null || auxModel.delegacion == '-1'){
			contadorFiltrosVacios++;
		}
		if(auxModel.subdelegacion == null || auxModel.subdelegacion == '-1'){
			contadorFiltrosVacios++;
		}
	  }
	  if(module.controller.model.perfil.perfilDescripcion =='SUPERVISOR' || module.controller.model.perfil.perfilDescripcion =='JAC'){
		if(auxModel.subdelegacion == null || auxModel.subdelegacion == '-1'){
			contadorFiltrosVacios++;
		}
	  }
	  
		if(auxModel.origen == null || auxModel.origen == '-1'){
			contadorFiltrosVacios++;
		}
		if(auxModel.folio == ''){
			contadorFiltrosVacios++;
		}
		if(auxModel.curp == ''){
			contadorFiltrosVacios++;
		}
		if(auxModel.nssInvolucrado == ''){
			contadorFiltrosVacios++;
		}
		if(auxModel.tipoTramite == null || auxModel.tipoTramite == '-1'){
			contadorFiltrosVacios++;
		}
		if(auxModel.estado == null || auxModel.estado == '-1'){
			contadorFiltrosVacios++;
		}
		if(auxModel.curpBeneficiario == ''){
			contadorFiltrosVacios++;
		}
		if(auxModel.fechaSolicitudDesde == ''){
			contadorFiltrosVacios++;
		}
		if(auxModel.fechaSolicitudHasta == ''){
			contadorFiltrosVacios++;
		}
		if(auxModel.fechaFinalizacionDesde == ''){
			contadorFiltrosVacios++;
		}
		if(auxModel.fechaFinalizacionHasta == ''){
			contadorFiltrosVacios++;
		}
		if(auxModel.fechaActualizacionDesde == ''){
			contadorFiltrosVacios++;
		}
		if(auxModel.fechaActualizacionHasta == ''){
			contadorFiltrosVacios++;
		}		
		if(auxModel.autorizo == null || auxModel.autorizo == '-1'){
			contadorFiltrosVacios++;
		}
		if(auxModel.responsable == null || auxModel.responsable == '-1'){
			contadorFiltrosVacios++;
		}		
	  
		if(module.controller.model.perfil.perfilDescripcion == 'NORMATIVO' && contadorFiltrosVacios ==17){
			  filtrosVacios = true;
			  $("#modalReporte").modal('hide');
			  $("#modalSinFiltrosReporte").modal();
			  filtro = true;
			  
			  }
			  if(module.controller.model.perfil.perfilDescripcion =='SUPERVISOR' || module.controller.model.perfil.perfilDescripcion =='JAC'){
				if(contadorFiltrosVacios == 16){
					filtrosVacios = true;
					$("#modalReporte").modal('hide');
					$("#modalSinFiltrosReporte").modal();
				    filtro = true;	
				}
			  }
			  if(module.controller.model.perfil.perfilDescripcion =='AUTORIZADOR' || module.controller.model.perfil.perfilDescripcion =='SUBDELEGADO'){
				  if(contadorFiltrosVacios == 15){
					filtrosVacios = true;
					$("#modalReporte").modal('hide');
					$("#modalSinFiltrosReporte").modal();
					filtro = true;
				  }
			  }
			  
	  var errorFetch = false;
	  
		var params = {
			filter : auxModel,
			page : page,
			pageSize : 5
		};
		if (url !== null && !filtrosVacios) {
			$.ajax({
				url : url,
				data : JSON.stringify(params),
				type : "POST",
				contentType : "application/json; charset=UTF-8",
				dataType : "json",
				success : function(data) {	    
					responsable =  data.data[0].responsable;
					curp = data.data[0].curp;
					
					module.updateModel("GridComponent", gridId, data);
					module.service.removeModal();
					filtro = false;
				},
				error : function(errMsg) {
					module.service.removeModal();
					errorFetch = true;
				}
			});
		}else{
			//module.service.removeModal();
			//$("#modalSinFiltrosReporte").modal();
		}			
				
		if(errorFetch){
			module.service.removeModal();
	        $('div.modal-backdrop.fade').remove();
			module.updateModel("AlertComponent", "alert", {
				level : "danger",
				message : "No se encontr\u00F3 informaci\u00F3n."
			});
		}
};

ReportesServices.prototype.fetchEstadistica = function(gridId) {
	
	$("#modalReporte").modal();
	// console.log($('#variableSelectField').val());
	if($('#variableSelectField').val()!=undefined && $('#variableSelectField').val()!= -1 ){
		var url = this.READ_VARIABLE_ELEGIDA;
	  var filtros = this.module.controller.model.filtros;
	  filtros.variable = $('#variableSelectField').val();
	  var errorFetch = false;
		var params = {
			filter : filtros,
			page : 1,
			pageSize : 5
		};
		if (url !== null) {
			$.ajax({
				url : url,
				data : JSON.stringify(params),
				type : "POST",
				contentType : "application/json; charset=UTF-8",
				dataType : "json",
				success : function(data) {
					if(data != null){
					$('#delegacionReporteGrid').val(data.data[0].delegacionReporte);
					$('#subdelegacionReporteGrid').val(data.data[0].subdelegacionReporte);
					$('#variableSeleccionadaReporte').val(data.data[0].variableSeleccionadaReporte);
					$('#estadisticaReporteGrid').val(data.data[0].estadisticaReporte);
				if(gridId=="gridReportes"){
				data = data.data[0].variablesReportes[0];
					module.updateModel("GridComponent", gridId, data);
				}
				if(gridId=="gridOrigen"){
				data = data.data[0].estadisticasReporteCDAVO[0];
					module.updateModel("GridComponent", gridId, data);
				}
					$('div.modal-backdrop.fade').remove();
				}
				module.service.removeModal();
				},
				error : function(errMsg) {
					module.service.removeModal();
					errorFetch = true;
				}
			});
		}	
				
		if(errorFetch){
			module.service.removeModal();
	        //$('div.modal-backdrop.fade').remove();
			module.updateModel("AlertComponent", "alert", {
				level : "danger",
				message : "No se encontr\u00F3 informaci\u00F3n."
			});
		}
	}else{
		module.service.removeModal();
	}
	  
};


ReportesServices.prototype.fetchGeneraPDF = function(gridId,model) {
	var variable = this.module.controller.model.filtros.variable;
	 $('form[name=auxForm]').attr('action', 'visorReportes/generarPDF.do/'+variable);
	 $('form[name=auxForm]').submit();
//	 this.module.updateModel( "CardLayoutComponent", "responsableCardLayout", 1);
};


ReportesServices.prototype.fetchGeneraXLS = function(gridId,model) {
	 var variable=$('#variable').val();
	 $('form[name=auxForm]').attr('action', 'visorReportes/generarXLS.do/'+variable);
	 $('form[name=auxForm]').submit();
//	 this.module.updateModel( "CardLayoutComponent", "responsableCardLayout", 1);
};

ReportesServices.prototype.salir = function() {
	$("#modalSalir").modal();	
};

ReportesServices.prototype.salirAceptar = function() {
	$("#modalSalir").modal();	
	document.forms["aux"].action = this.LOGOUT;
	this.invalidateCache();
	document.forms["aux"].submit();
};

ReportesServices.prototype.invalidateCache = function(){
	
	var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))
    {
    	document.execCommand("ClearAuthenticationCache",false);   
    }	
};

ReportesServices.prototype.salirCancelar = function() {
	$("#modalSalir").modal('hide');	
};

ReportesServices.prototype.cancelarEnvioSindo = function() {
	$("#modalEnvioSindo").modal('hide');	
};

ReportesServices.prototype.cleanFiltros = function(gridId,gridHistoricoId,model){
	$.each(model.filtros, function(){ 
			$("#" + arguments[0]).val(arguments[0]==="estado"?"-1":"");
			$("#" + arguments[0]).prop('disabled',false);
		}
	);	
	this.module.validator.formToModel("FormPanelComponent-filter", model.filtros );
	this.fetchFiltros(gridId,gridHistoricoId,model);
};

ReportesServices.prototype.disableElement = function(filtros){
	$.each( filtros, function(){
			if(arguments[1].length>0 && arguments[0] !== 'foliosAsociados'){
				var name = arguments[0];
				$.each( filtros, function(){
						if(name !== arguments[0]){
							$("#"+ arguments[0]).prop('disabled',true);
						}
					}
				);
				return;
			}
		}
	);		
};

ReportesServices.prototype.fetchCombo = function(gridId, module) { 
	  var url = this.READ_COMBO;	
	  var params = { enumeracion : "EstadoTramiteEnum"};
	  if( url !== null ){
	    $.ajax({
	      url: url,
	      data: JSON.stringify( params ),
	      type: "POST",     
	      contentType: "application/json; charset=UTF-8",
	      dataType: "json",            
	      success: function(data){
	        module.updateModel( "SelectFieldComponent", gridId, data );
	        if(module.controller.model.filtros != undefined){
	        	module.service.selectField("estado",module.controller.model.filtros.estado);
			}
	      },
	      error: function(errMsg){
	        if( errMsg === null ){
	          errMsg = "create.error";
	        }
	        module.updateModel( "alert", { level:"danger", message: errMsg} );
	      }
	    });
	  }
	};
	
ReportesServices.prototype.fetchOrigenCombo = function(gridId, module) { 
	  var url = this.ORIGEN_COMBO;	
	  var params = { enumeracion : "OrigenSolicitudEnum"};
	  if( url !== null ){
	    $.ajax({
	      url: url,
	      data: JSON.stringify( params ),
	      type: "POST",     
	      contentType: "application/json; charset=UTF-8",
	      dataType: "json",            
	      success: function(data){
	        module.updateModel( "SelectFieldComponent", gridId, data );
	        if(module.controller.model.filtros != undefined){
	        	module.service.selectField("origen",module.controller.model.filtros.origen);
			}
	      },
	      error: function(errMsg){
	        if( errMsg === null ){
	          errMsg = "create.error";
	        }
	        module.updateModel( "alert", { level:"danger", message: errMsg} );
	      }
	    });
	  }
	};
	
ReportesServices.prototype.fetchTipoTramiteCombo = function(gridId, module) { 
	  var url = this.TIPO_TRAMITE_REPORTE_COMBO;	
	  var params = { enumeracion : "TipoRegularizacionSolicitudCDAEnum"};
	  if( url !== null ){
	    $.ajax({
	      url: url,
	      data: JSON.stringify( params ),
	      type: "POST",     
	      contentType: "application/json; charset=UTF-8",
	      dataType: "json",            
	      success: function(data){
	        module.updateModel( "SelectFieldComponent", gridId, data );
	        if(module.controller.model.filtros != undefined){
	        	module.service.selectField("tipoTramite",module.controller.model.perfil.tipoTramite);
			}
	      },
	      error: function(errMsg){
	        if( errMsg === null ){
	          errMsg = "create.error";
	        }
	        module.updateModel( "alert", { level:"danger", message: errMsg} );
	      }
	    });
	  }
	};

ReportesServices.prototype.fetchDelegacionCombo = function(gridId, module) { 
	  var url = this.DELEGACION_COMBO;	
	  var params = { enumeracion : "DicDelegacion"};
	  if( url !== null ){
	    $.ajax({
	      url: url,
	      data: JSON.stringify( params ),
	      type: "POST",     
	      contentType: "application/json; charset=UTF-8",
	      dataType: "json",            
	      success: function(data){
	        module.updateModel( "SelectFieldComponent", gridId, data );
	        if(module.controller.model.filtros != undefined){
	        	module.service.selectField("delegacion",module.controller.model.filtros.delegacion);
			}
	        if(module.controller.model.perfil.perfilDescripcion =='SUPERVISOR' || module.controller.model.perfil.perfilDescripcion =='JAC' ||
	           module.controller.model.perfil.perfilDescripcion =='AUTORIZADOR' || module.controller.model.perfil.perfilDescripcion =='SUBDELEGADO'){
			$("#delegacionSelectField").val(module.controller.model.perfil.delegacion);
			$("#delegacionSelectField").change();
			$("#delegacionSelectField").prop('disabled', true);
	        }
	      },
	      error: function(errMsg){
	        if( errMsg === null ){
	          errMsg = "create.error";
	        }
	        module.updateModel( "alert", { level:"danger", message: errMsg} );
	      }
	    });
	  }
	};

ReportesServices.prototype.fetchSubdelegacionCombo = function(delegacion,gridId, module) { 
	if(delegacion != null && delegacion != -1){
		  var url = this.SUBDELEGACION_COMBO;	
		  var params =  { enumeracion : delegacion};
		  if( url !== null ){
		    $.ajax({
		      url: url,
		      data: JSON.stringify( params ),
		      type: "POST",     
		      contentType: "application/json; charset=UTF-8",
		      dataType: "json",            
		      success: function(data){
		        module.updateModel( "SelectFieldComponent", "subdelegacionSelectField", data );
		        if(module.controller.model.filtros != undefined){
		        	module.service.selectField("subdelegacion",module.controller.model.filtros.subdelegacion);
				}
		        if(module.controller.model.perfil.perfilDescripcion =='AUTORIZADOR' || module.controller.model.perfil.perfilDescripcion =='SUBDELEGADO'){
				$("#subdelegacionSelectField").val(module.controller.model.perfil.subdelegacion);
				$("#subdelegacionSelectField").change();
				$("#subdelegacionSelectField").prop('disabled', true);
		        }
		      },
		      error: function(errMsg){
		        if( errMsg === null ){
		          errMsg = "create.error";
		        }
		        module.updateModel( "alert", { level:"danger", message: errMsg} );
		      }
		    });
		  }
		}  
	};

ReportesServices.prototype.fetchAutorizoCombo = function(delegacion,subdelegacion,gridId, module) { 
	if(subdelegacion != null && subdelegacion != -1){
		  var url = this.AUTORIZO_COMBO;	
		  var params =  { delegacion:delegacion,subdelegacion : subdelegacion};
		  if( url !== null ){
		    $.ajax({
		      url: url,
		      data: JSON.stringify( params ),
		      type: "POST",     
		      contentType: "application/json; charset=UTF-8",
		      dataType: "json",            
		      success: function(data){
		        module.updateModel( "SelectFieldComponent", "autorizoSelectField", data );
		        if(module.controller.model.filtros != undefined){
		        	module.service.selectField("subdelegacion",module.controller.model.filtros.subdelegacion);
				}
		      },
		      error: function(errMsg){
		        if( errMsg === null ){
		          errMsg = "create.error";
		        }
		        module.updateModel( "alert", { level:"danger", message: errMsg} );
		      }
		    });
		  }
		}
	};
	
ReportesServices.prototype.fetchResponsableCombo = function(delegacion,subdelegacion,gridId, module) {
	if(subdelegacion != null && subdelegacion != -1){
		  var url = this.RESPONSABLE_COMBO;	
		  var params =  { delegacion:delegacion,subdelegacion : subdelegacion};
		  if( url !== null ){
		    $.ajax({
		      url: url,
		      data: JSON.stringify( params ),
		      type: "POST",     
		      contentType: "application/json; charset=UTF-8",
		      dataType: "json",            
		      success: function(data){
		        module.updateModel( "SelectFieldComponent", "responsableSelectField", data );
		        if(module.controller.model.filtros != undefined){
		        	module.service.selectField("subdelegacion",module.controller.model.filtros.subdelegacion);
				}
		      },
		      error: function(errMsg){
		        if( errMsg === null ){
		          errMsg = "create.error";
		        }
		        module.updateModel( "alert", { level:"danger", message: errMsg} );
		      }
		    });
		  }
		}
	};

	
ReportesServices.prototype.fetchVariableCombo = function(gridId, module) { 
	  $("#modal").modal();
	  var url = this.VARIABLE_COMBO;	
	  var params = { enumeracion : "VariableReporteEnums"};
	  if( url !== null ){
	    $.ajax({
	      url: url,
	      data: JSON.stringify( params ),
	      type: "POST",     
	      contentType: "application/json; charset=UTF-8",
	      dataType: "json",            
	      success: function(data){
	        $("#modal").modal('hide');
	        module.updateModel( "SelectFieldComponent", gridId, data );
	      },
	      error: function(errMsg){
	        $("#modal").modal('hide');
	        if( errMsg === null ){
	          errMsg = "create.error";
	        }
	        module.updateModel( "alert", { level:"danger", message: errMsg} );
	      }
	    });
	  }
	};


ReportesServices.prototype.removeModal = function() {
	$("#modalReporte").modal('hide');
	$("#modal").modal('hide');
    $('div.modal-backdrop.fade').remove();
    $('body.modal-open').removeClass('modal-open').removeAttr( 'style' );
};

ReportesServices.prototype.removeModalSinFiltros = function() {
	$("#modalSinFiltrosReporte").modal('hide');
	$("#modalReporte").modal('hide');
    $('div.modal-backdrop.fade').remove();
    $('body.modal-open').removeClass('modal-open').removeAttr( 'style' );
};

ReportesServices.prototype.modalNoAGeneradoUnReporte = function() {
	$("#modalNoAGeneradoUnReporte").modal('hide');
	$('div.modal-backdrop.fade').remove();
    $('body.modal-open').removeClass('modal-open').removeAttr( 'style' );
};