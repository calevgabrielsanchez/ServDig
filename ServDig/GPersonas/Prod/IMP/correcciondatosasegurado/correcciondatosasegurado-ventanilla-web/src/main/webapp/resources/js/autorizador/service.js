function AutorizadorServices(module) {
  this.module = module;
  this.READ_TRAMITES_ASIGNADOS="atencionAutorizador/tramitesAsignados.do";
  this.READ_HISTORICO_SOLICITUDES="atencionAutorizador/historicoSolicitudes.do";
  this.READ_USER_PROFILE="userProfile.do";
  this.READ_SOLICITUD = "atencionResponsable/obtenerSolicitud";
  this.READ_COMBO = "atencionAutorizador/obtenerCombo.do";
  this.READ_COMBO_ORIGEN = "atencionAutorizador/obtenerComboOrigen.do";
  this.READ_COMBO_TIPO_TRAMITE = "atencionAutorizador/obtenerComboTipoTramite.do";
  this.READ_COMBO_RESPONSABLES = "atencionAutorizador/obtenerComboResponsables.do";
  this.READ_COMBO_AUTORIZADORES = "atencionAutorizador/obtenerComboAutorizadores.do";
  this.CONFIRMAR_SOLICITUD = "atencionAutorizador/autorizarSolicitud.do";
  this.READ_RESPONSABLES = "atencionAutorizador/obtenerResponsables.do";
  this.REASIGNAR = "atencionAutorizador/reasignarResponsable.do";
  this.SOLICITAR_INFORMACION = "atencionAutorizador/solicitarInformacion.do";
  this.RECHAZAR = "atencionAutorizador/rechazarSolicitud.do";
  this.LOGOUT="j_spring_security_logout";
  this.READ_ESTATUS = "atencionAutorizador/obtenerEstatus";
  this.CANCELAR_SOLICITUD = "atencionAutorizador/cancelarSolicitud";
  this.UPDATE_CORREO_ASEGURADO = "atencionAutorizador/capturarCorreoAsegurado.do";
};

AutorizadorServices.prototype.selectField = function(fieldId,valor) {
	var component = this.module.render.componentTemplates["SelectFieldComponent"];
	component.setValue(fieldId,valor);
};

AutorizadorServices.prototype.reasignar = function() { 
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

AutorizadorServices.prototype.rechazar = function() { 
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

AutorizadorServices.prototype.solicitarInformacion = function() { 
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

AutorizadorServices.prototype.combos = function(model, fieldId, module ) { 
  switch( model ){
    case 'fetchResponsables' : this.fetchResponsables(fieldId, module);
      break;
    case 'gruposCorreccion' : module.updateModel("SelectFieldComponent", fieldId, this.module.controller.model[model] );
      break;
    case 'tiposNSS': module.updateModel("SelectFieldComponent", fieldId,this.module.controller.model[model]);
		break;
    case 'fetchCombo' : this.fetchCombo(fieldId, module);
    	break;
    case 'fetchOrigen' : this.fetchOrigen(fieldId, module);
	    break;
    case 'fetchTipoTramite' : this.fetchTipoTramite(fieldId, module);
        break;
    case 'fetchResponsable' : this.fetchResponsable(fieldId, module);
    	break;
    case 'fetchAutorizo' : this.fetchAutorizo(fieldId, module);
    	break;
        
  }
	
};

AutorizadorServices.prototype.confirmar = function() {
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

AutorizadorServices.prototype.cancelar = function() {
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

AutorizadorServices.prototype.getUserProfile = function(module) {
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

AutorizadorServices.prototype.getSizeGridNss = function(gridGroupId, module, page) {  
	// El tamaño de la lista de nss
	var numGridsNss = module.controller.model.consultaSolicitud.gridsNSS.length;
    module.updateModel("GridGroupComponent",gridGroupId, numGridsNss); //"gridGroupNss"
};
AutorizadorServices.prototype.fetchNSS = function(gridId, module, position) {  
	// Los nss de la solicitud
	var grid = module.controller.model.consultaSolicitud.gridsNSS[position];
	module.updateModelGroupGrids("GridComponent", gridId, grid,"gridNSS");
};
AutorizadorServices.prototype.getSizeGridDocumentosNss = function(gridGroupId, module, page) {  
	// El tamaño de la lista de documentos de cada nss
	var numGridsDoctosNss = module.controller.model.consultaSolicitud.gridsDocumentosNss.length;
    module.updateModel("GridGroupComponent",gridGroupId, numGridsDoctosNss);//
};
AutorizadorServices.prototype.fetchDocumentosNss = function(gridId, module, position) {  
	// Los documentos de cada nss de la solicitud
	var grid = module.controller.model.consultaSolicitud.gridsDocumentosNss[position];
	module.updateModelGroupGrids("GridComponent", gridId, grid,"gridDocumentos");
};
AutorizadorServices.prototype.fetchDocumentos = function(gridId, module, page) {  
	// Los datos de los grids vendram dentro del solicitud
	var grid = module.controller.model.consultaSolicitud.gridDocumentos;
	module.updateModel("GridComponent", gridId, grid);
};
AutorizadorServices.prototype.getSizeGridDocumentosNssOrigen = function(gridGroupId, module, page) {  
	var numGridsDoctosNssOrigen = module.controller.model.consultaSolicitud.gridsDocumentosNssOrigen.length;
    module.updateModel("GridGroupComponent",gridGroupId, numGridsDoctosNssOrigen);//
};
AutorizadorServices.prototype.fetchDocumentosNssOrigen = function(gridId, module, position) {  
	var grid = module.controller.model.consultaSolicitud.gridsDocumentosNssOrigen[position];
	module.updateModelGroupGrids("GridComponent", gridId, grid,"gridsDocumentosNssOrigen");
};

AutorizadorServices.prototype.fetchDocumentosBeneficiario = function(gridId, module, page) {  
    // Los datos de los grids vendran dentro del solicitud
    var grid = module.controller.model.consultaSolicitud.gridDocumentosBeneficiario;
    module.updateModel("GridComponent", gridId, grid);
};
AutorizadorServices.prototype.fetchHistoriaLaboral = function(gridId, module, page ) { 
	// Los datos de los grids vendram dentro del solicitud
  var grid = module.controller.model.consultaSolicitud.gridHistoriaLaboral;
  module.updateModel( "GridComponent", gridId, grid );
};
AutorizadorServices.prototype.fetchDatosAdicionales = function(gridId, module, page ) { 
	// Los datos de los grids vendram dentro del solicitud
  var grid = module.controller.model.consultaSolicitud.gridDatosAdicionales;
  module.updateModel( "GridComponent", gridId, grid );
};



AutorizadorServices.prototype.fetchTramites = function(gridId, module, page ) { 
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
  var params = { page: page, pageSize:10, filter: filtrosBusqueda };
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

AutorizadorServices.prototype.fetchHistorico = function(gridId, module, page) {
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
		pageSize : 10
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


AutorizadorServices.prototype.fetchResponsables = function(gridId, module) { 
  $("#modal").modal();
  var url = this.READ_RESPONSABLES;	
  var params = { curpResponsable : this.module.controller.model.consultaSolicitud.curpResponsable};
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

AutorizadorServices.prototype.fetchSolicitud = function(componentId, module, id ) { 
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

AutorizadorServices.prototype.fetchEstatus = function(componentId, module, id) {
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
				module.updateModel("BitacoraUI", componentId,data);
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

AutorizadorServices.prototype.fetchEstatusHistorico = function(componentId, module, id) {
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
				module.updateModel("BitacoraUI", componentId,data);
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

AutorizadorServices.prototype.fetchListEstatus = function(gridId, module, page) {  
	var grid = module.controller.model.bitacora.gridEstatus;
	module.updateModel("GridComponent", gridId, grid);
};

AutorizadorServices.prototype.fetchFiltros = function(gridId,gridHistoricoId,model) {
	$("#modal").modal();
	  var url = this.READ_TRAMITES_ASIGNADOS;
	  var urlhistoricos = this.READ_HISTORICO_SOLICITUDES;
	  var filtros = model.filtros;
	  
	  var errorFetch = false;
	  
		var params = {
			filter : filtros,
			page : 1,
			pageSize : 10
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
		
		if (urlhistoricos !== null) {
			$.ajax({
				url : urlhistoricos,
				data : JSON.stringify(params),
				type : "POST",
				contentType : "application/json; charset=UTF-8",
				dataType : "json",
				success : function(data) {
	        $("#modal").modal('hide');
	        $('div.modal-backdrop.fade').remove();
					module.updateModel("GridComponent", gridHistoricoId, data);        
				},
				error : function(errMsg) {
					errorFetch=true;
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


AutorizadorServices.prototype.fetchSolicitudHistorico = function(componentId, module, id ) { 
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

AutorizadorServices.prototype.salir = function() {
	$("#modalSalir").modal();	
};

AutorizadorServices.prototype.salirAceptar = function() {
	$("#modalSalir").modal();	
	document.forms["aux"].action = this.LOGOUT;
	this.invalidateCache();
	document.forms["aux"].submit();
};

AutorizadorServices.prototype.invalidateCache = function(){
	
	var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))
    {
    	document.execCommand("ClearAuthenticationCache",false);   
    }	
};

AutorizadorServices.prototype.salirCancelar = function() {
	$("#modalSalir").modal('hide');	
};

AutorizadorServices.prototype.cancelarEnvioSindo = function() {
	$("#modalEnvioSindo").modal('hide');	
};

AutorizadorServices.prototype.cleanFiltros = function(gridId,gridHistoricoId,model){
	$.each(model.filtros, function(){ 
			$("#" + arguments[0]).val(arguments[0]==="estado"?"-1":"");
			$("#" + arguments[0]).prop('disabled',false);
		}
	);	
	this.module.validator.formToModel("FormPanelComponent-filter", model.filtros );
	this.fetchFiltros(gridId,gridHistoricoId,model);
};

AutorizadorServices.prototype.disableElement = function(filtros){
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

AutorizadorServices.prototype.fetchCombo = function(fieldId, module) { 
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
	        module.updateModel( "SelectFieldComponent", fieldId, data );
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
	
AutorizadorServices.prototype.fetchOrigen = function(fieldId, module) { 
		  var url = this.READ_COMBO_ORIGEN;	
		  var params = { enumeracion : "OrigenSolicitudEnum"};
		  if( url !== null ){
		    $.ajax({
		      url: url,
		      data: JSON.stringify( params ),
		      type: "POST",     
		      contentType: "application/json; charset=UTF-8",
		      dataType: "json",            
		      success: function(data){
		        module.updateModel( "SelectFieldComponent", fieldId, data );
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

AutorizadorServices.prototype.fetchTipoTramite = function(fieldId, module) { 
	var url = this.READ_COMBO_TIPO_TRAMITE;	
	var params = { enumeracion : "TipoRegularizacionSolicitudCDAEnum"};
	if( url !== null ){
		$.ajax({
			url: url,
			data: JSON.stringify( params ),
			type: "POST",     
			contentType: "application/json; charset=UTF-8",
			dataType: "json",            
			success: function(data){
				module.updateModel( "SelectFieldComponent", fieldId, data );
				if(module.controller.model.filtros != undefined){
					module.service.selectField("tramite",module.controller.model.filtros.tramite);
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

AutorizadorServices.prototype.fetchResponsable = function(fieldId, module) { 
	var url = this.READ_COMBO_RESPONSABLES;	
	var params = { };
	if( url !== null ){
		$.ajax({
			url: url,
			data: JSON.stringify( params ),
			type: "POST",     
			contentType: "application/json; charset=UTF-8",
			dataType: "json",            
			success: function(data){
				module.updateModel( "SelectFieldComponent", fieldId, data );
				if(module.controller.model.filtros != undefined){
					module.service.selectField("responsable",module.controller.model.filtros.tramite);
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

AutorizadorServices.prototype.fetchAutorizo = function(fieldId, module) { 
	var url = this.READ_COMBO_AUTORIZADORES;	
	var params = { };
	if( url !== null ){
		$.ajax({
			url: url,
			data: JSON.stringify( params ),
			type: "POST",     
			contentType: "application/json; charset=UTF-8",
			dataType: "json",            
			success: function(data){
				module.updateModel( "SelectFieldComponent", fieldId, data );
				if(module.controller.model.filtros != undefined){
					module.service.selectField("autorizo",module.controller.model.filtros.tramite);
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

AutorizadorServices.prototype.guardarCorreoAsegurado = function() { 
	  $("#modalCapturaCorreo").modal('hide');
	  $('div.modal-backdrop.fade').remove(); 
	
	  $("#modal").modal();
	  var url = this.UPDATE_CORREO_ASEGURADO;
	  var module = this.module;
	  var params = {
			  idTramite: this.module.controller.model.consultaSolicitud.idTramite,
			  correoAsegurado : $('#correoAsegurado').val()
			};
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
	        module.updateModel("CardLayoutComponent","responsableCardLayout", 4);
	      },
	      error: function(errMsg){
	        $("#modal").modal('hide');
	        $('div.modal-backdrop.fade').remove();
	      }
	    });
	  }
};


AutorizadorServices.prototype.removeModal = function() {
	$("#modal").modal('hide');
    $('div.modal-backdrop.fade').remove();
    $('body.modal-open').removeClass('modal-open').removeAttr( 'style' );
};