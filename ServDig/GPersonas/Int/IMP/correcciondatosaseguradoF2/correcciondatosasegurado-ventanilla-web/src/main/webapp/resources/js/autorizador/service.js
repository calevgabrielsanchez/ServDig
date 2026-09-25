
function AutorizadorServices(module) {
  this.module = module;
  this.correccionDatosService = new CorreccionDatosService( module );
  this.confirmarCorreccionDatos = new ConfirmarCorreccionDatosService( module );
  this.confrontaCuentaIndividual = new ConfrontaCuentaIndividualService( module );
  
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
  this.REPORTES = "visorReportes";
};

AutorizadorServices.prototype.reportes = function() {
	document.forms["aux"].action = this.REPORTES;
	document.forms["aux"].submit();
};

AutorizadorServices.prototype.selectField = function(fieldId,valor) {
	$("#" + fieldId).val(valor);
};

AutorizadorServices.prototype.reasignar = function() { 
  $("#modal").modal();
  
  var responsableCombo=$('#responsableSelectField').val();
  var observacion=$('#detalle').val();
  var error="";
  
   if(observacion==""){
  error="Indique el detalle de Reasignaci�n de la Solicitud.";
  }
  if(responsableCombo=="" || responsableCombo == null){
  error="Es necesario seleccionar un Responsable de la lista para reasignar la solicitud.";
  }
  if(error!=""){
   this.module.updateModel( "AlertComponent", "alert", {level:"danger",message:error} );
    $("#modal").modal('hide');
  }
  else{
 
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
		          //module.service.fetchSolicitud("consultaSolicitud",module, null);    
//                  module.updateModel("CardLayoutComponent","responsableCardLayout", 1);
                  module.render.module.controller.transition("bandejaSolicitudesUI");
			      $('div.modal-backdrop.fade').remove();
			      module.updateModel( "AlertComponent", "alert", {level:"info",message:"Operaci\u00F3n exitosa"} );
                                
			},
			error : function(errMsg) {
				module.service.removeModal();
				module.updateModel( "AlertComponent", "alert", {level:"danger",message:"No fue posible realizar la operaci\u00F3n "} );
			}
		});
	}
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
//        module.updateModel("CardLayoutComponent","responsableCardLayout", 0);
        module.render.module.controller.transition("bandejaSolicitudesUI");
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
//        module.updateModel("CardLayoutComponent","responsableCardLayout", 0);
        module.render.module.controller.transition("bandejaSolicitudesUI");
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
        module.render.module.controller.transition("bandejaSolicitudesUI");
        if (data.error != null) {
            module.updateModel( "AlertComponent", "alert", {level: "danger", message: data.error} );
        } else {
            module.updateModel( "AlertComponent", "alert", {level: "info", message: "Operaci\u00F3n exitosa"} );
        }
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
					module.controller.model.consultaSolicitud.idEstadoSolicitud = "9";
					module.controller.model.consultaSolicitud.estatus="CANCELADA";
	                  $("#modal").modal('hide');
	                  $('div.modal-backdrop.fade').remove();
//					  module.service.fetchSolicitud("consultaSolicitud",module, null);    
//	                  module.updateModel("CardLayoutComponent","responsableCardLayout", 1);
					  module.render.module.controller.transition("informacionSolicitud");
					  $('div.modal-backdrop.fade').remove();
					  module.updateModel("AlertComponent", "alert", {level : "info",message : "Operaci\u00F3n exitosa"});
				},
				error : function(errMsg) {
			        $("#modal").modal('hide');
			        $('div.modal-backdrop.fade').remove();
					module.updateModel("AlertComponent", "alert", {level : "danger",message : "No fue posible realizar la operaci\u00F3n"});
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
	// El tama�o de la lista de nss
	var numGridsNss = module.controller.model.consultaSolicitud.gridsNSS.length;
    module.updateModel("GridGroupComponent",gridGroupId, numGridsNss); //"gridGroupNss"
};
AutorizadorServices.prototype.fetchNSS = function(gridId, module, position) {  
	// Los nss de la solicitud
	var grid = module.controller.model.consultaSolicitud.gridsNSS[position];
	module.updateModelGroupGrids("GridComponent", gridId, grid,"gridNSS");
};
AutorizadorServices.prototype.getSizeGridDocumentosNss = function(gridGroupId, module, page) {  
	// El tama�o de la lista de documentos de cada nss
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
AutorizadorServices.prototype.fetchDocumentosAdicionales = function(gridId, module, page) { 
	var grid = module.controller.model.consultaSolicitud.gridDocumentosAdicionales;
	if (grid==null || grid.data==null){
		$("#grid_gridDoctosAdicionalesAsegurado").hide();
    }else{
    	module.updateModel("GridComponent", gridId, grid);
    	$("#grid_gridDoctosAdicionalesAsegurado").show();
    }
};
AutorizadorServices.prototype.getSizeGridDocumentosNssOrigen = function(gridGroupId, module, page) {  
	var numGridsDoctosNssOrigen = module.controller.model.consultaSolicitud.gridsDocumentosNssOrigen.length;
    module.updateModel("GridGroupComponent",gridGroupId, numGridsDoctosNssOrigen);//
};
AutorizadorServices.prototype.fetchDocumentosNssOrigen = function(gridId, module, position) {  
	var grid = module.controller.model.consultaSolicitud.gridsDocumentosNssOrigen[position];
	module.updateModelGroupGrids("GridComponent", gridId, grid,"gridDocumentosNssOrigen");
};

AutorizadorServices.prototype.fetchDocumentosNssAdicionales = function(gridId, module, page) { 	
	var grid = module.controller.model.consultaSolicitud.gridDocumentosNssAdicionales;
	if (grid==null || grid.data==null){
		$("#grid_gridDocumentosNssAdicional").hide();
    }else{
    	module.updateModel("GridComponent", gridId, grid);
    	$("#grid_gridDocumentosNssAdicional").show();
    }
};

AutorizadorServices.prototype.fetchDocumentosBeneficiario = function (gridId, module, page) {
    var grid = module.controller.model.consultaSolicitud.gridDocumentosBeneficiario;
    if (grid != null) {
        if (grid.data != null) {
            module.updateModel("GridComponent", gridId, grid);
            $("#tabButtonbeneficiario").show()
            //$("#panelbeneficiario").show();

        } else {
            $("#tabButtonbeneficiario").hide()
            $("#panelbeneficiario").hide();
        }

    }
};

AutorizadorServices.prototype.fetchDocumentosBeneficiarioAdicionales = function(gridId, module, page) {  
	var grid = module.controller.model.consultaSolicitud.gridDocumentosBeneficiarioAdicionales;
	if (grid==null || grid.data==null){
    	$("#grid_gridDocumentosBeneficiarioAdicionales").hide();
    }else{
    	module.updateModel("GridComponent", gridId, grid);
    	$("#grid_gridDocumentosBeneficiarioAdicionales").show();
    }
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

    $('#a-tabs-panelTabsBandejas-0').attr("onclick", "module.controller.model.tabs_panelTabsBandejas=0;document.forms[\"filter\"].reset();module.controller.filtros(\"tramites\");");
    $('#a-tabs-panelTabsBandejas-1').attr("onclick", "module.controller.model.tabs_panelTabsBandejas=1;document.forms[\"filter\"].reset();module.controller.filtros(\"historico\");");

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
  
  this.module.validator.formToModel("FormPanelComponent-filter", this.module.controller.model.filtros );	
  
	var filtrosBusqueda = {};
	if (this.module.controller.model.filtros != undefined){
		filtrosBusqueda = this.module.controller.model.filtros;
	}	
	
	var isConsulta=false;
	
	if(this.module.controller.isConsulta != undefined ){
		isConsulta=this.module.controller.isConsulta;
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
        module.updateModel( "GridComponent", gridId, data );
		
		if(isConsulta){
			module.controller.recuperarFiltros(filtrosBusqueda);
		}
		
        module.service.removeModal();
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

    var url = null;
    if($('#li-tabs-panelTabsBandejas-1').attr('class').trim() == "active"){
        url = this.READ_HISTORICO_SOLICITUDES;
    }



  this.module.controller.model.filter = {};
  this.module.validator.formToModel("FormPanelComponent-filter", this.module.controller.model.filtros );
  
	var filtrosBusqueda = {};
	if (this.module.controller.model.filtros != undefined){
		filtrosBusqueda = this.module.controller.model.filtros;
	}
	
	var isConsulta=false;
	
	if(this.module.controller.isConsulta != undefined ){
		isConsulta=this.module.controller.isConsulta;
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

				module.updateModel("GridComponent", gridId, data);
				if(isConsulta){
					module.controller.recuperarFiltros(module,filtrosBusqueda);
				}
				module.service.removeModal();
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
          
  		
  		var error="";

          if( errMsg.status === 404 ){
  		
            error = "No existen Responsables asignados a la Subdelegaci�n";
  		 
          }
  		if( errMsg.status === 500 ){
  		
            error = "No se pudieron recuperar los datos correspondientes a los Responsables por Subdelegaci�n";
          }
  		//alert(error);
  		module.validator.formToModel("PanelComponent-panelConsultaSolicitud", null);
         module.updateModel( "AlertComponent", "alert", {level:"danger",message:error} );
         }
    });
  }
};

var definicion;
AutorizadorServices.prototype.fetchSolicitud = function(componentId, module, id ) { 
  $("#modal").modal();
	var url = this.READ_SOLICITUD;
	 var tipoTramite = "";
        if(id === null) {
         var params = {folio: this.module.controller.model.consultaSolicitud.folio};
        } else {
         var params = {folio: this.module.controller.model.gridTramites.data[id].folio};
		 tipoTramite = this.module.controller.model.gridTramites.data[id].tipo;
        }
  
  if( url !== null ){
    $.ajax({
      url: url,
      data: JSON.stringify( params ),
      type: "POST",     
      contentType: "application/json; charset=UTF-8",
      dataType: "json",            
      success: function(data){
    	$("#modal").modal('hide');
        module.updateModel( "ConsultaSolicitudComponent", componentId, data );
//        module.updateModel( "CardLayoutComponent", "responsableCardLayout", 1);
        module.render.module.controller.transition("informacionSolicitud");
        module.controller.model.consultaSolicitud = data;
		//Incluir el tipoTramite
		module.controller.model.consultaSolicitud.tipoTramite = tipoTramite;
      	module.controller.checkEstadoAutorizacion(data,componentId);
      	module.controller.estadoTabsConsulta(data,componentId);
        module.updateModel("NssDocumentsComponent", "gridNssSolicitud", data.gridNssSolicitud.data);
        module.updateModel("NssDocumentsComponent", "gridNssVentanilla", data.gridNssVentanilla.data);
        module.updateModel("NssDocumentsComponent", "gridNssSolicitudInfoSol", data.gridNssSolicitud.data);                                                            
		module.updateModel("NssDocumentsComponent", "gridNssVentanillaInfoSol", data.gridNssVentanilla.data);
        definicion=data.definicion;
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


AutorizadorServices.prototype.fetchSolicitudReporte = function(componentId, module, folio) { 
  //$("#modal").modal();
  var url = this.READ_SOLICITUD;
         var params = { folio: folio };
  
  if( url !== null ){
    $.ajax({
      url: url,
      data: JSON.stringify( params ),
      type: "POST",     
      contentType: "application/json; charset=UTF-8",
      dataType: "json",            
      success: function(data){
      $("#modal").modal('hide');
        module.updateModel( "ConsultaSolicitudComponent", componentId, data );
//        module.updateModel( "CardLayoutComponent", "responsableCardLayout", 1);
        module.render.module.controller.transition("informacionSolicitud");
        module.controller.model.consultaSolicitud = data;
        module.controller.checkEstadoAutorizacion(data,componentId);
        module.controller.estadoTabsConsulta(data,componentId);
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
				module.updateModel("BitacoraUI", componentId,data);
//				module.updateModel("CardLayoutComponent","responsableCardLayout", 8);
				  module.render.module.controller.transition("bitacoraUI");
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
				module.updateModel("BitacoraUI", componentId,data);
//				module.updateModel("CardLayoutComponent","responsableCardLayout", 8);
				 module.render.module.controller.transition("bitacoraUI");
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

    console.log("gridId: " + gridId + ", gridHistoricoId: " + gridHistoricoId);
    var module = this.module;

    module.updateModel("GridComponent", gridId, {});
    module.updateModel("GridComponent", "gridHistorico", {});
    $("#modal").modal();
    var url = this.READ_TRAMITES_ASIGNADOS;
    var urlhistoricos = this.READ_HISTORICO_SOLICITUDES;
    var filtros = model.filtros;
    var activos = null;
    var errorFetch = false;

    var params = {
        filter : filtros,
        page : 1,
        pageSize : 10
    };
    if (url !== null && gridId != "") {
        $.ajax({
            url : url,
            data : JSON.stringify(params),
            type : "POST",
            contentType : "application/json; charset=UTF-8",
            dataType : "json",
            success : function(data) {
                activos = data.totalOfRecords;
                $('#li-tabs-panelTabsBandejas-0').attr("class", "active");
                $('#li-tabs-panelTabsBandejas-1').attr("class", "");
                $('#tabs-panelTabsBandejas-0').attr("class", "tab-pane fade  active in");
                $('#tabs-panelTabsBandejas-1').attr("class", "tab-pane fade");
                /*if( activos >0){
                    $("#a-tabs-panelTabsBandejas-0").click();
                }*/
                module.updateModel("GridComponent", gridId, data);
                module.service.removeModal();
            },
            error : function(errMsg) {
                errorFetch = true;
            }
        });
    }

    if (urlhistoricos !== null && gridHistoricoId != "") {
        $.ajax({
            url : urlhistoricos,
            data : JSON.stringify(params),
            type : "POST",
            contentType : "application/json; charset=UTF-8",
            dataType : "json",
            success : function(data) {
                activos = data.totalOfRecords;
                /*if( activos >0){
                    $("#a-tabs-panelTabsBandejas-1").click();
                }*/
                module.updateModel("GridComponent", gridHistoricoId, data);
                module.service.removeModal();
            },
            error : function(errMsg) {
                errorFetch=true;
            }
        });
    }

    if(errorFetch){
        module.service.removeModal();
        module.updateModel("AlertComponent", "alert", {
            level : "danger",
            message : "No se encontr\u00F3 informaci\u00F3n."
        });
    }

};


AutorizadorServices.prototype.fetchSolicitudHistorico = function(componentId, module, id ) { 
	  $("#modal").modal();
		var url = this.READ_SOLICITUD;
	  var params = { idTramite: this.module.controller.model.gridHistorico.data[id].idTramite, 
			         folio: this.module.controller.model.gridHistorico.data[id].folio};
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
		        module.updateModel( "ConsultaSolicitudComponent", componentId, data );
		    	data.idTarea = idTarea;
		    	data.nss = nss;
		    	data.responsable = responsable;
		    	module.controller.checkEstadoAutorizacion(data,componentId);
		    	module.controller.estadoTabsConsulta(data,componentId);
//		    	module.updateModel( "CardLayoutComponent", "responsableCardLayout", 1);
		    	 module.render.module.controller.transition("informacionSolicitud");
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

AutorizadorServices.prototype.salirAutorizar = function() {
	$("#modalAutorizar").modal('hide');	
};
AutorizadorServices.prototype.autorizarSindo = function() {
	$("#modalAutorizar").modal('show');	
};


AutorizadorServices.prototype.cancelarEnvioSindo = function() {
	$("#modalEnvioSindo").modal('hide');	
};

AutorizadorServices.prototype.cleanFiltros = function(gridId,gridHistoricoId,model){
	$( "select[id*='SelectField']" ).val(-1);
	$( "input" ).val("");
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
          for(var i=0;i<data.length;i++){
            if( data[i].key !== "-1"){
              data[i].key = data[i].value;
            }
          }
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
			  correoAsegurado : $('#correoAseguradoText').val()
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
//	        module.updateModel("CardLayoutComponent","responsableCardLayout", 4);
	        module.render.module.controller.transition("solicitarInformacionUI");
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

AutorizadorServices.prototype.getSizeNss = function(gridGroupId, module, page) {  
    // El tama�o de la lista de nss
	
    var numNss = module.controller.model.consultaSolicitud.idTramite;
    module.updateModel("cargarValores",gridGroupId, numNss); 

   
};
AutorizadorServices.prototype.fetNSS = function(gridId, module, position) {  
    // Los nss de la solicitud
	
    var tiponss = module.controller.model.consultaSolicitud;
    module.updateModelGroupGrids("cargarValores", gridId, tiponss,"gridNSS");

};


AutorizadorServices.prototype.fetNSSLectura = function(gridId, module, position) {  
    // Los nss de la solicitud
	
    var tiponss = module.controller.model.consultaSolicitud;
    module.updateModelGroupGrids("cargarValoresLectura", gridId, tiponss,"gridNSS");

};

AutorizadorServices.prototype.fetchCuentaIndividualResumen = function(gridId, module, page) {	
	if($("#modal").size() > 0 && typeof $("#modal").modal === 'function'){
		$("#modal").modal();
	}	
	var url = this.READ_CUENTA_INDIVIDUAL_RESUMEN;
	var params = {
		folio :folioTramite
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

var cuentaIndividual;
AutorizadorServices.prototype.fetchCuentaIndividualConsulta = function(gridId, module, page) {  
	 cuentaIndividual = module.controller.model.consultaSolicitud.cuentaIndividual;
	module.updateModel("HeaderComponent", gridId, cuentaIndividual);
};

AutorizadorServices.prototype.fetchCuentaIndividual = function(gridId, module, page) {  
	var grid = module.controller.model.consultaSolicitud.cuentaIndividual;
	module.updateModel("HeaderComponent", gridId, grid);
};
