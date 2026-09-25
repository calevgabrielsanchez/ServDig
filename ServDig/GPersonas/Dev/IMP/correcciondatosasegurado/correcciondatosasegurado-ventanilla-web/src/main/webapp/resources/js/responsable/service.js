function ResponsableServices(module) {
	this.module = module;
	this.READ_TRAMITES_ASIGNADOS = "atencionResponsable/tramitesAsignados.do";
	this.READ_HISTORICO_SOLICITUDES = "atencionResponsable/historicoSolicitudes.do";
	this.READ_USER_PROFILE = "userProfile.do";
	this.READ_SOLICITUD = "atencionResponsable/obtenerSolicitud";
	this.READ_COMBO = "atencionAutorizador/obtenerCombo.do";
	this.READ_COMBO_ORIGEN = "atencionAutorizador/obtenerComboOrigen.do";
	this.READ_COMBO_TIPO_TRAMITE = "atencionAutorizador/obtenerComboTipoTramite.do";
	this.READ_COMBO_RESPONSABLES = "atencionAutorizador/obtenerComboResponsables.do";
	this.READ_COMBO_AUTORIZADORES = "atencionAutorizador/obtenerComboAutorizadores.do";
	this.CONFIRMAR_SOLICITUD = "atencionResponsable/confirmarSolicitud";
	this.CANCELAR_SOLICITUD = "atencionResponsable/cancelarSolicitud";
	this.REGISTRO_SOLICITUD_RESPONSABLE = "wizard/correccionDatosAsegurado";
	this.SOLICITAR_INFORMACION = "atencionResponsable/solicitarInformacion.do";
	this.LOGOUT="j_spring_security_logout";
	this.READ_ESTATUS = "atencionResponsable/obtenerEstatus";
	this.UPDATE_CORREO_ASEGURADO = "atencionAutorizador/capturarCorreoAsegurado.do";
	this.READ_CUENTA_INDIVIDUAL = "cuentaIndividual/inicio.do";
};

ResponsableServices.prototype.nuevaSolicitud = function() {
	document.forms["aux"].action = this.REGISTRO_SOLICITUD_RESPONSABLE;
	document.forms["aux"].submit();
};

ResponsableServices.prototype.selectField = function(fieldId,valor) {
	var component = this.module.render.componentTemplates["SelectFieldComponent"];
	component.setValue(fieldId,valor);
};

ResponsableServices.prototype.combos = function(model, fieldId, module) {
	switch (model) {
	case 'fetchResponsables':
		this.fetchResponsables(fieldId, module);
		break;
	case 'tiposNSS':
		module.updateModel("SelectFieldComponent", fieldId,
				this.module.controller.model[model]);
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

ResponsableServices.prototype.confirmar = function() {
  $("#modalAvanzarResponsable").modal('hide');
  $("#modal").modal();
	var url = this.CONFIRMAR_SOLICITUD;
	var module = this.module;
	var params = this.module.controller.model.consultaSolicitud;
	if (url !== null) {
		$.ajax({
			data : JSON.stringify(params),
			url : url,
			type : "POST",
			contentType : "application/json; charset=UTF-8",
			dataType : "json",
			success : function(data) {
        $("#modal").modal('hide');
        $('div.modal-backdrop.fade').remove();
				module.updateModel("AlertComponent", "alert", {
					level : "info",
					message : "Se ha registrado exitosamente la solicitud "+ data.folio 
					+" con los cambios por aplicar y se ha enviado para autorizaci\u00F3n."
				});
				module.updateModel("CardLayoutComponent",
						"responsableCardLayout", 0);
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

ResponsableServices.prototype.cancelar = function() {
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
        $('div.modal-backdrop.fade').remove();
				module.updateModel("AlertComponent", "alert", {
					level : "info",
					message : "Operaci\u00F3n exitosa"
				});
				module.updateModel("CardLayoutComponent",
						"responsableCardLayout", 0);
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

ResponsableServices.prototype.getUserProfile = function(module) {
  
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

ResponsableServices.prototype.getSizeGridNss = function(gridGroupId, module, page) {  
	// El tamaño de la lista de nss
	var numGridsNss = module.controller.model.consultaSolicitud.gridsNSS.length;
    module.updateModel("GridGroupComponent",gridGroupId, numGridsNss); //"gridGroupNss"
};
ResponsableServices.prototype.fetchNSS = function(gridId, module, position) {  
	// Los nss de la solicitud
	var grid = module.controller.model.consultaSolicitud.gridsNSS[position];
	module.updateModelGroupGrids("GridComponent", gridId, grid,"gridNSS");
};

ResponsableServices.prototype.getSizeGridDocumentosNss = function(gridGroupId, module, page) {  
	// El tamaño de la lista de documentos de cada nss
	var numGridsDoctosNss = module.controller.model.consultaSolicitud.gridsDocumentosNss.length;
    module.updateModel("GridGroupComponent",gridGroupId, numGridsDoctosNss);//
};
ResponsableServices.prototype.fetchDocumentosNss = function(gridId, module, position) {  
	// Los documentos de cada nss de la solicitud
	var grid = module.controller.model.consultaSolicitud.gridsDocumentosNss[position];
	module.updateModelGroupGrids("GridComponent", gridId, grid,"gridDocumentos");
};

ResponsableServices.prototype.fetchDocumentos = function(gridId, module, page) {  
	// Los datos de los grids vendram dentro del solicitud
	var grid = module.controller.model.consultaSolicitud.gridDocumentos;
	module.updateModel("GridComponent", gridId, grid);
};

ResponsableServices.prototype.getSizeGridDocumentosNssOrigen = function(gridGroupId, module, page) {  
	var numGridsDoctosNssOrigen = module.controller.model.consultaSolicitud.gridsDocumentosNssOrigen.length;
    module.updateModel("GridGroupComponent",gridGroupId, numGridsDoctosNssOrigen);//
};
ResponsableServices.prototype.fetchDocumentosNssOrigen = function(gridId, module, position) {  
	var grid = module.controller.model.consultaSolicitud.gridsDocumentosNssOrigen[position];
	module.updateModelGroupGrids("GridComponent", gridId, grid,"gridsDocumentosNssOrigen");
};

ResponsableServices.prototype.fetchDocumentosBeneficiario = function(gridId, module, page) {  
    // Los datos de los grids vendran dentro del solicitud
    var grid = module.controller.model.consultaSolicitud.gridDocumentosBeneficiario;
    module.updateModel("GridComponent", gridId, grid);
};
ResponsableServices.prototype.fetchHistoriaLaboral = function(gridId, module,
		page) {  
	// Los datos de los grids vendram dentro del solicitud
	var grid = module.controller.model.consultaSolicitud.gridHistoriaLaboral;
	module.updateModel("GridComponent", gridId, grid);
};
ResponsableServices.prototype.fetchDatosAdicionales = function(gridId, module,
		page) {  
	// Los datos de los grids vendram dentro del solicitud
	var grid = module.controller.model.consultaSolicitud.gridDatosAdicionales;
	module.updateModel("GridComponent", gridId, grid);
};

ResponsableServices.prototype.fetchTramites = function(gridId, module, page) {	
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
	var params = {
		filter : filtrosBusqueda,
		page : page,
		pageSize : 10
	};
	//revisar si ya hay algun dato en el grid ya no mostrarlo. y si regresa datos quitar el alert 
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
				//FIXME validar si es el modelo correcto
				if(module.controller.model[gridId] == null){ 
					module.updateModel("AlertComponent", "alert", {
						level : "danger",
						message : "No se encontr\u00F3 informaci\u00F3n."
					});
				}
			}
		});
	}
};

ResponsableServices.prototype.fetchCuentaIndividual = function(gridId, module, page) {	
	if($("#modal").size() > 0 && typeof $("#modal").modal === 'function'){
		$("#modal").modal();
	}
	
	if(page === 1){
        if(this.module.controller.model.paginaTramitesActualTramites != undefined){
            page = this.module.controller.model.paginaTramitesActualTramites;
            this.module.controller.model.paginaTramitesActualTramites = undefined;
        }
    }
	
  var url = this.READ_CUENTA_INDIVIDUAL;
	var filtrosBusqueda = {};
	if (this.module.controller.model.filtrosBusqueda != undefined){
		filtrosBusqueda = this.module.controller.model.filtrosBusqueda;
	} 
	var params = {
		filter : filtrosBusqueda,
		page : page,
		pageSize : 10
	};
	//revisar si ya hay algun dato en el grid ya no mostrarlo. y si regresa datos quitar el alert 
	if (url !== null) {
		$.ajax({
			url : url,
			data : JSON.stringify(params),
			type : "POST",
			contentType : "application/json; charset=UTF-8",
			dataType : "json",
			success : function(data) {
				module.service.removeModal();
				module.updateModel("HeaderComponent", gridId, data);
			},
			error : function(errMsg) { 
				module.service.removeModal();
				//FIXME validar si es el modelo correcto
				if(module.controller.model[gridId] == null){ 
					module.updateModel("AlertComponent", "alert", {
						level : "danger",
						message : "No se encontr\u00F3 informaci\u00F3n."
					});
				}
			}
		});
	}
};

ResponsableServices.prototype.fetchHistorico = function(gridId, module, page) {
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

ResponsableServices.prototype.fetchSolicitud = function(componentId, module, id) {
	$("#modal").modal();
  var url = this.READ_SOLICITUD;
	var params = {
		idTramite : this.module.controller.model.gridTramites.data[id].idTramite,
		esPropietario : this.module.controller.model.gridTramites.data[id].esPropietario
	};
	var idTarea = this.module.controller.model.gridTramites.data[id].idTarea;
	if (url !== null) {
		$.ajax({
			url : url,
			data : JSON.stringify(params),
			type : "POST",
			contentType : "application/json; charset=UTF-8",
			dataType : "json",
			success : function(data) {
				data.idTarea = idTarea;
				module.updateModel("CardLayoutComponent",
						"responsableCardLayout", 1);
        // FIX: Mientras ponemos mas este es el default    
        //data.tipoRegularizacion.correccionDatosBasicos = true;    
				module.updateModel("ConsultaSolicitudComponent", componentId, data);
				
				module.controller.checkEstadoResponsable(data,componentId);
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

ResponsableServices.prototype.fetchEstatus = function(componentId, module, id) {
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
				module.updateModel("CardLayoutComponent","responsableCardLayout", 5);
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

ResponsableServices.prototype.fetchEstatusHistorico = function(componentId, module, id) {
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
				module.updateModel("CardLayoutComponent","responsableCardLayout", 5);
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

ResponsableServices.prototype.fetchSolicitudHistorico = function(componentId, module, id ) { 
	  $("#modal").modal();
		var url = this.READ_SOLICITUD;
	  var params = { 
			  idTramite: this.module.controller.model.gridHistorico.data[id].idTramite,
			  esPropietario : this.module.controller.model.gridHistorico.data[id].esPropietario
	  };
	  var idTarea = this.module.controller.model.gridHistorico.data[id].idTarea;
	  
	  
	  if( url !== null ){
	    $.ajax({
	      url: url,
	      data: JSON.stringify( params ),
	      type: "POST",     
	      contentType: "application/json; charset=UTF-8",
	      dataType: "json",            
	      success: function(data){
	    	  data.idTarea = idTarea;
				module.updateModel("CardLayoutComponent",
						"responsableCardLayout", 1);
      // FIX: Mientras ponemos mas este es el default    
      //data.tipoRegularizacion.correccionDatosBasicos = true;    
				module.updateModel("ConsultaSolicitudComponent", componentId,
						data);
				module.controller.checkEstadoResponsable(data,componentId);
      $("#modal").modal('hide');
      $('div.modal-backdrop.fade').remove();
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

ResponsableServices.prototype.fetchListEstatus = function(gridId, module, page) {  
	var grid = module.controller.model.bitacora.gridEstatus;
	module.updateModel("GridComponent", gridId, grid);
};

ResponsableServices.prototype.fetchFiltros = function(gridId,gridHistoricoId,model) {
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

ResponsableServices.prototype.solicitarInformacion = function() { 
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

ResponsableServices.prototype.salir = function() {
	$("#modalSalir").modal();	
};

ResponsableServices.prototype.salirAceptar = function() {
	$("#modalSalir").modal();	
	document.forms["aux"].action = this.LOGOUT;
	this.invalidateCache();
	document.forms["aux"].submit();
};

ResponsableServices.prototype.invalidateCache = function(){
	
	var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))
    {
    	document.execCommand("ClearAuthenticationCache",false);   
    }	
};

ResponsableServices.prototype.salirCancelar = function() {
	$("#modalSalir").modal('hide');	
};

ResponsableServices.prototype.mostarModalAvanzar = function() {
	$("#modalAvanzarResponsable").modal('show');	
};

ResponsableServices.prototype.cancelarAvanzaAutorizar = function() {
	$("#modalAvanzarResponsable").modal('hide');	
};


ResponsableServices.prototype.cleanFiltros = function(gridId,gridHistoricoId,model){
	$.each( model.filtros, function(){ 
		if(arguments[0] !== "foliosAsociados"){
			$("#" + arguments[0]).val(arguments[0]==="estado"?"-1":"");
		}else{
			$("#" + arguments[0]).prop('checked', false);
			model.filtros.foliosAsociados="false";
		}
		}
	);
	this.module.validator.formToModel("FormPanelComponent-filter", model.filtros );
	this.fetchFiltros(gridId,gridHistoricoId,model);
};

ResponsableServices.prototype.disableElement = function(filtros){
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

ResponsableServices.prototype.fetchCombo = function(fieldId, module) { 
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
	
ResponsableServices.prototype.fetchOrigen = function(fieldId, module) { 
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
		
ResponsableServices.prototype.fetchTipoTramite = function(fieldId, module) { 
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

ResponsableServices.prototype.fetchResponsable = function(fieldId, module) { 
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

ResponsableServices.prototype.fetchAutorizo = function(fieldId, module) { 
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

ResponsableServices.prototype.guardarCorreoAsegurado = function() { 
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
	        module.updateModel("CardLayoutComponent","responsableCardLayout", 6);
	      },
	      error: function(errMsg){
	        $("#modal").modal('hide');
	        $('div.modal-backdrop.fade').remove();
	      }
	    });
	  }
};

ResponsableServices.prototype.removeModal = function() {
	$("#modal").modal('hide');
    $('div.modal-backdrop.fade').remove();
    $('body.modal-open').removeClass('modal-open').removeAttr( 'style' );
};
