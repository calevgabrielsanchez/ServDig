function ReportesServices(module) {
  this.module = module;
  //this.READ_TRAMITES_ASIGNADOS="atencionAutorizador/tramitesAsignados.do";
  //this.READ_TRAMITES_ASIGNADOS="atencionAutorizador/tramitesReportes.do";
  this.READ_TRAMITES_ASIGNADOS="vistaReportes/tramitesReportes.do";
  this.READ_VARIABLE_ELEGIDA="vistaReportes/variableReporte.do";
  
  
  this.READ_HISTORICO_SOLICITUDES="atencionAutorizador/historicoSolicitudes.do";
  this.READ_USER_PROFILE="userProfile.do";
  this.READ_SOLICITUD = "atencionResponsable/obtenerSolicitud";
  this.READ_COMBO = "atencionAutorizador/obtenerCombo.do";
  this.ORIGEN_COMBO = "vistaReportes/obtenerOrigenCombo.do";
  this.TIPO_TRAMITE_COMBO="vistaReportes/obtenerTipoTramiteCombo.do";
  this.DELEGACION_COMBO="vistaReportes/obtenerDelegacionCombo.do";
  this.SUBDELEGACION_COMBO="vistaReportes/obtenerSubdelegacionCombo.do";
  this.AUTORIZO_COMBO="vistaReportes/obtenerAutorizoCombo.do";
  this.RESPONSABLE_COMBO="vistaReportes/obtenerResponsableCombo.do";	  
  this.CONFIRMAR_SOLICITUD = "atencionAutorizador/autorizarSolicitud.do";
  this.VARIABLE_COMBO = "vistaReportes/obtenerVariables.do";
  this.REASIGNAR = "atencionAutorizador/reasignarResponsable.do";
  this.SOLICITAR_INFORMACION = "atencionAutorizador/solicitarInformacion.do";
  this.RECHAZAR = "atencionAutorizador/rechazarSolicitud.do";
  this.LOGOUT="j_spring_security_logout";
  this.READ_ESTATUS = "atencionAutorizador/obtenerEstatus";
  this.CANCELAR_SOLICITUD = "atencionAutorizador/cancelarSolicitud";
  
};

ReportesServices.prototype.selectField = function(fieldId,valor) {
	var component = this.module.render.componentTemplates["SelectFieldComponent"];
	component.setValue(fieldId,valor);
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



ReportesServices.prototype.fetchTramites = function(gridId, module, page ) { 
	if($("#modal").size() > 0 && typeof $("#modal").modal === 'function'){
		$("#modal").modal();
	}
	if(page === 1){
        if(this.module.controller.model.paginaTramitesActualTramites != undefined){
            page = this.module.controller.model.paginaTramitesActualTramites;
            this.module.controller.model.paginaTramitesActualTramites = undefined;
        }
    }
	var url = this.READ_TRAMITES_ASIGNADOS;
	var filtrosBusqueda = {};
	if (this.module.controller.model.filtrosBusqueda != undefined){
		filtrosBusqueda = this.module.controller.model.filtrosBusqueda;
	}	
  var params = { page: page, pageSize:5, filter: filtrosBusqueda };
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




ReportesServices.prototype.fetchSolicitud = function(componentId, module, id ) { 
  $("#modal").modal();
	var url = this.READ_SOLICITUD;
  var params = { idTramite: this.module.controller.model.gridTramites.data[id].idTramite };
  var idTarea = this.module.controller.model.gridTramites.data[id].idTarea;
  var nss = this.module.controller.model.gridTramites.data[id].nssInvolucrados;
  var responsable = this.module.controller.model.gridTramites.data[id].responsable;
  if( url !== null ){
    $.ajax({
      url: url,
      data: JSON.stringify( params ),
      type: "POST",     
      contentType: "application/json; charset=UTF-8",
      dataType: "json",            
      success: function(data){
        $("#modal").modal('hide');
    	data.idTarea = idTarea;
    	data.nss = nss;
    	data.responsable = responsable;
    	module.controller.checkEstadoAutorizacion(data,componentId);
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

ReportesServices.prototype.fetchFiltros = function(gridId,gridHistoricoId,model) {
	$("#modal").modal();
	  var url = this.READ_TRAMITES_ASIGNADOS;
	
	  var filtros = model.filtros;
	  
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
					module.updateModel("GridComponent", gridId, data);        
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

ReportesServices.prototype.fetchEstadistica = function(gridId,model) {
	$("#modal").modal();
	  var url = this.READ_VARIABLE_ELEGIDA;
//	  var url = this.READ_TRAMITES_ASIGNADOS;
	
	  var filtros = model.filtros;
	  var variable=$('#variable').val();
//	  alert(filtros.folio);
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
					module.updateModel("GridComponent", gridId, data);
					$('div.modal-backdrop.fade').remove();
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


ReportesServices.prototype.fetchGeneraPDF = function(gridId,model) {
	 var variable=$('#variable').val();
	 $('form[name=auxForm]').attr('action', 'vistaReportes/generarPDF.do/'+variable);
	 $('form[name=auxForm]').submit();
	 this.module.updateModel( "CardLayoutComponent", "responsableCardLayout", 1);
};


ReportesServices.prototype.fetchGeneraXLS = function(gridId,model) {
	 var variable=$('#variable').val();
	 $('form[name=auxForm]').attr('action', 'vistaReportes/generarXLS.do/'+variable);
	 $('form[name=auxForm]').submit();
	 this.module.updateModel( "CardLayoutComponent", "responsableCardLayout", 1);
};

ReportesServices.prototype.fetchSolicitudHistorico = function(componentId, module, id ) { 
	  $("#modal").modal();
		var url = this.READ_SOLICITUD;
	  var params = { idTramite: this.module.controller.model.gridHistorico.data[id].idTramite };
	  var idTarea = this.module.controller.model.gridHistorico.data[id].idTarea;
	  var nss = this.module.controller.model.gridHistorico.data[id].nssInvolucrados;
	  var responsable = this.module.controller.model.gridHistorico.data[id].responsable;
	  if( url !== null ){
	    $.ajax({
	      url: url,
	      data: JSON.stringify( params ),
	      type: "POST",     
	      contentType: "application/json; charset=UTF-8",
	      dataType: "json",            
	      success: function(data){
	        $("#modal").modal('hide');

	    	data.idTarea = idTarea;
	    	data.nss = nss;
	    	data.responsable = responsable;
	    	module.controller.checkEstadoAutorizacion(data,componentId);
			$('div.modal-backdrop.fade').remove();
	      },
	      error: function(errMsg){
	        $("#modal").modal('hide');
	        if( errMsg === null ){
	          errMsg = "create.error";
	        }
	        module.updateModel( "alert", { level:"danger", message: errMsg} );
			$('div.modal-backdrop.fade').remove();
	      }
	    });
	  }
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
	  var url = this.TIPO_TRAMITE_COMBO;	
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
	        	module.service.selectField("tipoTramite",module.controller.model.filtros.tipoTramite);
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
	$("#modal").modal('hide');
    $('div.modal-backdrop.fade').remove();
    $('body.modal-open').removeClass('modal-open').removeAttr( 'style' );
};