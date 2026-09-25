function ResponsableServices(module) {
	this.module = module;
  this.correccionDatosService = new CorreccionDatosService( module );
  this.confirmarCorreccionDatos = new ConfirmarCorreccionDatosService( module );
  this.cuentaIndividual = new CuentaIndividualService( module );
  this.consultaCuentaIndividual = new CuentaIndividualConsultaService( module );
  this.confrontaCuentaIndividual = new ConfrontaCuentaIndividualService( module );
  
  this.cuentaIndividualService = new CuentaIndividualService( module );
  
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
	this.READ_CUENTA_INDIVIDUAL = "atencionResponsable/cuentaIndividual/inicio.do";
	this.CUENTA_INDIVIDUAL_ILOGICA = "atencionResponsable/cuentaIndividual/cuentaIlogicaInicio.do";
	this.READ_CUENTA_INDIVIDUAL_RESUMEN = "cuentaIndividual/cuentaIndividualResumen.do";
	this.GUARDA_CUENTA_INDIVIDUAL = "atencionResponsable/cuentaIndividual/moficarCuentaIndividual.do";
	this.AGREGAR_NSS_DOCUMENTO_BUSCARNSS ="atencionResponsable/agregarNssDocumentoBuscarNSS.do";
	this.OBTENER_COMBO_DOCUMENTO_NSS ="atencionResponsable/obtenerComboDocumentoNSS.do";
	this.GUARDAR_DOCUMENTO_BOVEDA ="atencionResponsable/guardarDocumentoBoveda.do";
	this.GUARDAR_NSS ="atencionResponsable/guardarNss.do";
	this.ELIMINAR_DOCUMENTO ="atencionResponsable/eliminarDocumentoBoveda.do";
	this.ELIMINAR_NSS ="atencionResponsable/eliminarNss.do";
	this.OBTENER_ORIGEN ="atencionResponsable/porOrigenTramite.do";
	this.DELETE_CI ="atencionResponsable/cuentaIndividual/delete.do";
};

ResponsableServices.prototype.post = function( myUrl, myData, mySuccess ){
    
    var finalURL = myUrl;
    var finalData = myData;    
    var module = this.module;        
    $.ajax({
      url: finalURL,
      data: finalData,
      type: "POST",     
      contentType: "application/json; charset=UTF-8",
      dataType: "json",            
      success: function (data) {
        if( data.status === "ERROR"){
          module.updateModel( "AlertComponent","alert", { level:"danger", message: data.message} );
          $("#modal").modal('hide');
        }else{
          mySuccess(data);
        }
      },
      error: function(errMsg){
        $("#modal").modal('hide');
        if( errMsg === null ){
          errMsg = "create.error";
        }
        module.updateModel( "AlertComponent","alert", { level:"danger", message: errMsg} );
      }
    });
};

ResponsableServices.prototype.nuevaSolicitud = function() {
	document.forms["aux"].action = this.REGISTRO_SOLICITUD_RESPONSABLE;
	document.forms["aux"].submit();
};

ResponsableServices.prototype.selectField = function(fieldId,valor) {
	$("#" + fieldId).val(valor);
};

ResponsableServices.prototype.combos = function(model, fieldId, module) {
if(this.module.render.isEmpty(this.module.controller.model[model])){


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
				 module.render.module.controller.transition("bandejaSolicitudesUI");
//				module.updateModel("CardLayoutComponent",
//						"responsableCardLayout", 1);
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
			
				module.controller.model.consultaSolicitud.idEstadoSolicitud = "9";
				module.controller.model.consultaSolicitud.estatus="CANCELADA";
				$("#modal").modal('hide');
                $('div.modal-backdrop.fade').remove();
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
	module.updateModelGroupGrids("GridComponent", gridId, grid,"gridDocumentosNss");
};

//ResponsableServices.prototype.fetchCuentaIndividual = function(gridId, module, page) {  
//	var grid = module.controller.model.consultaSolicitud.cuentaIndividual;
//	module.updateModel("HeaderComponent", gridId, grid);
//};

var cuentaIndividual;
ResponsableServices.prototype.fetchCuentaIndividualConsulta = function(gridId, module, page) {  
	 cuentaIndividual = module.controller.model.consultaSolicitud.cuentaIndividual;
	module.updateModel("HeaderComponent", gridId, cuentaIndividual);
};

var cuentaIndividualPrevia;
ResponsableServices.prototype.fetchCuentaIndividualConsultaPrevia = function(gridId, module, page) {  
	cuentaIndividualPrevia = module.controller.model.consultaSolicitud.cuentaIndividualPrevia;
	module.updateModel("HeaderComponent", gridId, cuentaIndividualPrevia);
};

ResponsableServices.prototype.fetchDocumentos = function(gridId, module, page) { 
	var grid = module.controller.model.consultaSolicitud.gridDocumentos;
	module.updateModel("GridComponent", gridId, grid);
};

ResponsableServices.prototype.fetchDocumentosAdicionales = function(gridId, module, page) { 
	var grid = module.controller.model.consultaSolicitud.gridDocumentosAdicionales;
		if (grid==null || grid.data==null){
			$("#grid_gridDoctosAdicionalesAsegurado").hide();
	    }else{
	    	module.updateModel("GridComponent", gridId, grid);
	    	$("#grid_gridDoctosAdicionalesAsegurado").show();
	    }

};

ResponsableServices.prototype.getSizeGridDocumentosNssOrigen = function(gridGroupId, module, page) { 
	var numGridsDoctosNssOrigen = module.controller.model.consultaSolicitud.gridsDocumentosNssOrigen.length;
    module.updateModel("GridGroupComponent",gridGroupId, numGridsDoctosNssOrigen);
};
ResponsableServices.prototype.fetchDocumentosNssOrigen = function(gridId, module, position) { 
	var grid = module.controller.model.consultaSolicitud.gridsDocumentosNssOrigen[position];
	module.updateModelGroupGrids("GridComponent", gridId, grid,"gridDocumentosNssOrigen");
};

ResponsableServices.prototype.fetchDocumentosNssAdicionales = function(gridId, module, page) { 
	var grid = module.controller.model.consultaSolicitud.gridDocumentosNssAdicionales;
	if (grid==null || grid.data==null){
		$("#grid_gridDocumentosNssAdicional").hide();
    }else{
    	module.updateModel("GridComponent", gridId, grid);
    	$("#grid_gridDocumentosNssAdicional").show();
    }
	
	
};

ResponsableServices.prototype.fetchDocumentosBeneficiario = function (gridId, module, page) {
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

ResponsableServices.prototype.fetchDocumentosBeneficiarioAdicionales = function(gridId, module, page) {  
    var grid = module.controller.model.consultaSolicitud.gridDocumentosBeneficiarioAdicionales;
	if (grid==null || grid.data==null){
    	$("#grid_gridDocumentosBeneficiarioAdicionales").hide();
    }else{
    	module.updateModel("GridComponent", gridId, grid);
    	$("#grid_gridDocumentosBeneficiarioAdicionales").show();
    }
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


ResponsableServices.prototype.fetchConsulta = function(gridId, module, page) {	
// console.log("FetchConsulta------")
	if($("#modal").size() > 0 && typeof $("#modal").modal === 'function'){
		$("#modal").modal();
	}
	
  module.render.module.controller.transition("bandejaSolicitudesUI");
  return;
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
	
	//filtrosBusqueda.foliosAsociados= true;
	
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
				
				$("#modal").modal('hide');
		        $('div.modal-backdrop.fade').remove();
				module.updateModel("GridComponent", 'gridTramites', data);	
				var solicitudes = module.controller.model.gridTramites.data;
				if(solicitudes > 0 && solicitudes[0].pantallaConsulta != null && solicitudes[0].folioConsulta != null){
					
							
					module.service.fetchSolicitudConsulta("consultaSolicitud",solicitudes[0].folioConsulta);
					
				
				}
				else{
				module.render.module.controller.transition("bandejaSolicitudesUI");
//				module.updateModel("CardLayoutComponent","responsableCardLayout", 1);
				
				}
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

ResponsableServices.prototype.fetchTramites = function(gridId, module, page) {	
  
  // console.log("AAA");
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
  
  this.module.validator.formToModel("FormPanelComponent-filter", this.module.controller.model.filter );
	this.module.controller.model.filtrosBusqueda = this.module.controller.model.filtros;
	
  var url = this.READ_TRAMITES_ASIGNADOS;
	var filtrosBusqueda = {};
	if (this.module.controller.model.filter !== undefined){
		filtrosBusqueda = this.module.controller.model.filter;
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
	//revisar si ya hay algun dato en el grid ya no mostrarlo. y si regresa datos quitar el alert 
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
					module.controller.recuperarFiltros(filtrosBusqueda);
				}
				
				module.service.removeModal();
				
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

ResponsableServices.prototype.fetchCuentaIndividual = function() {	
	if($("#modal").size() > 0 && typeof $("#modal").modal === 'function'){
		$("#modal").modal();
            }
	var url = this.READ_CUENTA_INDIVIDUAL;
        var module = this.module;
	var params = {
			folio :this.module.controller.model.consultaSolicitud.folio
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
        module.controller.model.cuentaIndividualAsegurado = {};
        module.updateModel("CuentaIndividualComponent", "cuentaIndividualAsegurado", data);
        module.render.module.controller.transition("cuentaIndividual");
//        module.updateModel("CardLayoutComponent", "responsableCardLayout", 13);
        
			},
			error : function(errMsg) { 
				module.service.removeModal();
				//FIXME validar si es el modelo correcto
				//if(module.controller.model[gridId] == null){ 
					module.updateModel("AlertComponent", "alert", {
						level : "danger",
						message : "No se encontr\u00F3 informaci\u00F3n."
					});
				//}
			}
		});
	}
};

ResponsableServices.prototype.fetchCuentaIndividualResumen = function(gridId, module, page) {	
	if($("#modal").size() > 0 && typeof $("#modal").modal === 'function'){
		$("#modal").modal();
	}	
	var url = this.READ_CUENTA_INDIVIDUAL_RESUMEN;
	var filioTramiteCI = this.module.controller.model.gridNSS.idTramite;
	var params = {
		folio :filioTramiteCI
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
				module.updateModel("CuentaIndividualConfirmarMainComponent", "cuentaIndividualResumen", data);
//				module.updateModel("CardLayoutComponent", "responsableCardLayout", 14);
				module.render.module.controller.transition("CuentaIndividualConfirmarMainComponent");

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

	var url = null;
	if($('#li-tabs-panelTabsBandejas-1').attr('class').trim() == "active"){
		url = this.READ_HISTORICO_SOLICITUDES;
	}


  this.module.controller.model.filter = {};
  this.module.validator.formToModel("FormPanelComponent-filter", this.module.controller.model.filter );
	
  // console.log(">>>>fetchHistorico");
  
	var filtrosBusqueda = {};
	if (this.module.controller.model.filter !== undefined){
		filtrosBusqueda = this.module.controller.model.filter;
	}
	
	var isConsulta=false;
	
	if(this.module.controller.isConsulta != undefined ){
		isConsulta=this.module.controller.isConsulta;
	}
	
	//filtrosBusqueda.foliosAsociados= true;
	
	var params = {
		filter : this.module.controller.model.filter,
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
         $("#modal").modal('hide');
		        $('div.modal-backdrop.fade').remove();
				
			},
			error : function(errMsg) {
				
				if (errMsg === null) {
					errMsg = "create.error";
				}
				module.updateModel("alert", {
					level : "danger",
					message : errMsg
				});
         $("#modal").modal('hide');
		        $('div.modal-backdrop.fade').remove();
			}
		});
	}
};


ResponsableServices.prototype.fetchSolicitudConsulta = function(componentId, folioConsulta, id) {
	$("#modal").modal();
  var url = this.READ_SOLICITUD;
	var params = {
		
		folio:         folioConsulta
	};
	
	if (url !== null) {
		$.ajax({
			url : url,
			data : JSON.stringify(params),
			type : "POST",
			contentType : "application/json; charset=UTF-8",
			dataType : "json",
			success : function(data) {
				
				
        // FIX: Mientras ponemos mas este es el default    
        //data.tipoRegularizacion.correccionDatosBasicos = true;
				module.updateModel("ConsultaSolicitudComponent", componentId, data);	
        // Primero deja el informacion en el controller
				module.render.module.controller.transition("informacionSolicitud");	
//        module.updateModel("CardLayoutComponent",
//						"responsableCardLayout", 2);
            
				module.controller.checkEstadoResponsable(data,componentId);
				module.controller.estadoTabsConsulta(data,componentId);
				definicion=data.definicion;
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

/*
 * 
 * Se invoca desde el flujo de seguimiento de solicitud
 */
ResponsableServices.prototype.fetchSolicitudSeguimiento = function(folio) {
  var module = this.module;
  try{
    $("#modal").modal();
  }catch(error){
    
  }
  var url = this.READ_SOLICITUD;       
  var params = {folio: folio};
   
  
	if (url !== null) {
		$.ajax({
			url : url,
			data : JSON.stringify(params),
			type : "POST",
			contentType : "application/json; charset=UTF-8",
			dataType : "json",
			success : function(data) {
				module.updateModel("ConsultaSolicitudComponent", "consultaSolicitud", data);
				module.render.module.controller.transition("informacionSolicitud");
				module.controller.checkEstadoResponsable(data,"consultaSolicitud");
				module.controller.estadoTabsConsulta(data,"consultaSolicitud");
        module.updateModel("NssDocumentsComponent", "gridNssSolicitud", data.gridNssSolicitud.data);
        module.updateModel("NssDocumentsComponent", "gridNssVentanilla", data.gridNssVentanilla.data); 
				module.updateModel("NssDocumentsComponent", "gridNssSolicitudInfoSol", data.gridNssSolicitud.data);                                                            
				module.updateModel("NssDocumentsComponent", "gridNssVentanillaInfoSol", data.gridNssVentanilla.data);
				definicion=data.definicion;
		    $("#modal").modal('hide');
		    $('div.modal-backdrop.fade').remove();    
			},
			error : function(errMsg) {
		        $("#modal").modal('hide');
		        $('div.modal-backdrop.fade').remove();
				if (errMsg === null) {
					errMsg = "create.error";
				}
				module.updateModel("alert", {level : "danger",message : errMsg});
        
			}
		});
	}
};
var definicion;
ResponsableServices.prototype.fetchSolicitud = function(componentId, module, id) {
	$("#modal").modal();
  var url = this.READ_SOLICITUD;
  var tipoTramite = "";
  
    if(id === null) {
      var params = {folio: this.module.controller.model.consultaSolicitud.folio};
    } else {
       var params = {
    			idTramite :     this.module.controller.model.gridTramites.data[id].idTramite,
    			esPropietario : this.module.controller.model.gridTramites.data[id].esPropietario,
    			folio:          this.module.controller.model.gridTramites.data[id].folio,
    			idSubdelegacion:this.module.controller.model.userProfile.idSubdelegacion
       };
       tipoTramite = this.module.controller.model.gridTramites.data[id].tipo;
    }
  
	if (url !== null) {
		$.ajax({
			url : url,
			data : JSON.stringify(params),
			type : "POST",
			contentType : "application/json; charset=UTF-8",
			dataType : "json",
			success : function(data) {
				module.updateModel("ConsultaSolicitudComponent", componentId, data);
				//Incluir el tipoTramite
		        module.controller.model.consultaSolicitud.tipoTramite = tipoTramite;
		        
				module.render.module.controller.transition("informacionSolicitud");
//				module.updateModel("CardLayoutComponent","responsableCardLayout", 2);
				module.controller.checkEstadoResponsable(data,componentId);
				module.controller.estadoTabsConsulta(data,componentId);
				                module.updateModel("NssDocumentsComponent", "gridNssSolicitud", data.gridNssSolicitud.data);
                                module.updateModel("NssDocumentsComponent", "gridNssVentanilla", data.gridNssVentanilla.data); 
								module.updateModel("NssDocumentsComponent", "gridNssSolicitudInfoSol", data.gridNssSolicitud.data);                                                            
								module.updateModel("NssDocumentsComponent", "gridNssVentanillaInfoSol", data.gridNssVentanilla.data);
								definicion=data.definicion;
		        $("#modal").modal('hide');
		        $('div.modal-backdrop.fade').remove();    
			},
			error : function(errMsg) {
		        $("#modal").modal('hide');
		        $('div.modal-backdrop.fade').remove();
				if (errMsg === null) {
					errMsg = "create.error";
				}
				module.updateModel("alert", {level : "danger",message : errMsg});
        
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
				module.updateModel("BitacoraUI", componentId,data);
				module.render.module.controller.transition("bitacoraUI");
//				module.updateModel("CardLayoutComponent","responsableCardLayout", 6);
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
				module.updateModel("BitacoraUI", componentId,data);
				module.render.module.controller.transition("bitacoraUI");
//				module.updateModel("CardLayoutComponent","responsableCardLayout", 6);
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
			  esPropietario : this.module.controller.model.gridHistorico.data[id].esPropietario,
			  folio: this.module.controller.model.gridHistorico.data[id].folio
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
              module.updateModel("ConsultaSolicitudComponent", componentId,data);
			  module.controller.checkEstadoResponsable(data,componentId);
			  module.controller.estadoTabsConsulta(data,componentId);
			  module.render.module.controller.transition("informacionSolicitud");
//			  module.updateModel("CardLayoutComponent","responsableCardLayout", 2);
				definicion=data.definicion;
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
	console.log("gridId: " + gridId + ", gridHistoricoId: " + gridHistoricoId);
	var module = this.module;

	module.updateModel("GridComponent", gridId, {});
	module.updateModel("GridComponent", "gridHistorico", {});
	$("#modal").modal();
	// console.log("fetchFiltros-------");
	var url = this.READ_TRAMITES_ASIGNADOS;
	var urlhistoricos = this.READ_HISTORICO_SOLICITUDES;
	var filtros = model;
	var activos = null;
	var errorFetch = false;
	this.module.validator.formToModel("FormPanelComponent-filter", this.module.controller.model.filter );
	// console.log("Filtros: fetchFiltros");
	var params = {
		filter : this.module.controller.model.filter,
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
				$("#modal").modal('hide');
				$('div.modal-backdrop.fade').remove();
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
				$("#modal").modal('hide');
				$('div.modal-backdrop.fade').remove();
			},
			error : function(errMsg) {
				errorFetch=true;
				$("#modal").modal('hide');
				$('div.modal-backdrop.fade').remove();
			}
		});
	}

	if(errorFetch){
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
	        module.render.module.controller.transition("bandejaSolicitudesUI");
//	        module.updateModel("CardLayoutComponent","responsableCardLayout", 1);
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
		$( "select[id*='SelectField']" ).val(-1);
		$( "input" ).val("");
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
          
          for(var i=0;i<data.length;i++){
            if( data[i].key !== "-1"){
              data[i].key = data[i].value;
            }
          }
	        module.updateModel( "SelectFieldComponent", fieldId, data );
	        module.updateModel("FormPanelComponent","filter",module.controller.model.filter);
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
	        module.render.module.controller.transition("solicitarInformacionUI");
//	        module.updateModel("CardLayoutComponent","responsableCardLayout", 7);
	      },
	      error: function(errMsg){
	        $("#modal").modal('hide');
	        $('div.modal-backdrop.fade').remove();
	      }
	    });
	  }
};


var paramsCuentaIndividual = [];
var paramsCuentaIlogica = [];
ResponsableServices.prototype.guardarCuentaIndividual = function() { 	 
	  $("#modal").modal();
	  
	  var url = this.GUARDA_CUENTA_INDIVIDUAL;
	  var module = this.module; 
	  
	  var params = {
				folio :this.module.controller.model.consultaSolicitud.folio,
				periodos : paramsCuentaIndividual
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
	        //module.updateModel("HeaderComponent", gridId, data);
	      },
	      error: function(errMsg){
	        $("#modal").modal('hide');
	        $('div.modal-backdrop.fade').remove();
	      }
	    });
	  }
};



ResponsableServices.prototype.datoNSS = function() {
	
	var params = this.module.controller.model.solicitarInformacion;
	var nss0 = $("btonselect0").val();
	var nss1 = $("btonselect1").val();
	var nss2 = $("btonselect2").val();
	var nss3 = $("btonselect3").val();
	var nss4 = $("btonselect4").val();
	var nss5 = $("btonselect5").val();
	var url = "/wizard/correccionDatosAsegurado/obtenerInformacionNSS"
	if (url !== null) {
		var nss0 = $("#btonselect0").val();      
	
		$.ajax({
				url : url,
				type : 'post',
				async : false,
				dataType : 'json',
				contentType : "application/json; charset=utf-8",
				data : JSON.stringify({
							
						}),
				success : function(response) {
					
				},
				error : function(error) {
					fnProcesarErrores(error,"form#informacionHistoriaLaboralForm");
					$.unblockUI();
				}
			});
	}
};



ResponsableServices.prototype.removeModal = function() {
	$("#modal").modal('hide');
    $('div.modal-backdrop.fade').remove();
    $('body.modal-open').removeClass('modal-open').removeAttr( 'style' );
};



ResponsableServices.prototype.getSizeNss = function(gridGroupId, module, page) {  
    // El tamaño de la lista de nss
	
    var numNss = module.controller.model.consultaSolicitud.idTramite;
    module.updateModel("cargarValores",gridGroupId, numNss); 

   
};
ResponsableServices.prototype.fetNSS = function(gridId, module, position) {  
    // Los nss de la solicitud
	
    var tiponss = module.controller.model.consultaSolicitud;
    module.updateModelGroupGrids("cargarValores", gridId, tiponss,"gridNSS");

};

ResponsableServices.prototype.fetNSSLectura = function(gridId, module, position) {  
    // Los nss de la solicitud
	
    var tiponss = module.controller.model.consultaSolicitud;
    module.updateModelGroupGrids("cargarValoresLectura", gridId, tiponss,"gridNSS");

};


ResponsableServices.prototype.idTramite = function() {  
    // Los nss de la solicitud
	
    var idTramite = module.controller.model.consultaSolicitud.idTramite;
    return idTramite;

};


ResponsableServices.prototype.readCuentaIlogica = function(gridId, module, index) {	
	if($("#modal").size() > 0 && typeof $("#modal").modal === 'function'){
		$("#modal").modal();
	}
	
	var url = this.CUENTA_INDIVIDUAL_ILOGICA;

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

ResponsableServices.prototype.readCuentaIlogicaConsulta = function(gridId, module, index) {	
	if($("#modal").size() > 0 && typeof $("#modal").modal === 'function'){
		$("#modal").modal();
	}
	
	var url = this.CUENTA_INDIVIDUAL_ILOGICA;
	$('#informacionRENAPO_folio').val();
	var folioTramite =$('#informacionRENAPO_folio').val();// "${folioTramite}";

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

ResponsableServices.prototype.obtenerDatosporNSS = function(){
    var url = this.AGREGAR_NSS_DOCUMENTO_BUSCARNSS;
    var nss0 = $("#nssBuscar").val();
    var isEdicion = "";
    var isDuplicado = "";
                
                
    for(i=0 ; i < this.module.controller.consultaSolicitudController.model.gridNssSolicitudInfoSol.length ; i++){
        var nssComparacion = this.module.controller.consultaSolicitudController.model.gridNssSolicitudInfoSol[i].nss;
        if(nss0 == nssComparacion){
            isDuplicado = "estaDuplicado";
            break;
        }
    }
    
    if(isEdicion == ""){
        //this.module.controller.consultaPreviaController.model.consultaSolicitud.gridsNSS[0].data[0].nss
        //this.module.controller.consultaSolicitudController.model.gridNssVentanilla.length
        for(i=0 ; i < this.module.controller.consultaPreviaController.model.consultaSolicitud.gridsNSS[0].data.length; i++){
            var nssComparacion = this.module.controller.consultaPreviaController.model.consultaSolicitud.gridsNSS[0].data[i].nss
            if(nss0 == nssComparacion){
                isDuplicado = "estaDuplicado";
                break;
            }				
        }
    } 
                
                
    if(this.module.controller.agregarNssDocumentoController.model.nssEditado!=null&&this.module.controller.agregarNssDocumentoController.model.nssEditado.nss!=""){
        isEdicion = "EditandoNSS";
    }
    
    var modnombre=0;
    var params =JSON.stringify({nss:nss0,tipoCorreccion:isEdicion});
    var module=this.module;
    
    if(isDuplicado == "estaDuplicado" && isEdicion == ""){
        module.service.removeModal();
        module.updateModel("AlertComponent", "nssMessage", {message: "El NSS ingresado ya fue agregado por el sistema o por el responsable anteriormente.", level: "danger "});
        module.controller.agregarNssDocumentoController.model.nssOculto=null;
        module.controller.agregarNssDocumentoController.model.nssEditado= null;
        
    } else {
        $.ajax({
                url : url,
                type : 'POST',
                async : false,
                dataType : 'json',
                contentType : "application/json; charset=utf-8",
                data : params,
                success : function(data) {
                        // console.log("fue correcto, se obtiene el nss");
                        var i = 0;
                        var canase=0;
                        var max = data.length;
                        if(max==0){
                                module.updateModel("AlertComponent", "alert", {
                                level : "danger",
                                message : "No se encontr\u00F3 informaci\u00F3n relacionada al NSS."
                                });
                        }
                        // console.log(data);
                        for(i=0 ; i< data.length ; i++)
                                {
                                    var msgErr = data[i].msgError;
                                    if(msgErr !== null){

                                            module.service.removeModal();
                                            module.updateModel("AlertComponent", "nssMessage", {message: msgErr, level: "danger "});
                                            module.controller.agregarNssDocumentoController.model.nssOculto=null;
                                            module.controller.agregarNssDocumentoController.model.nssEditado= null;
                                    }                                                    
                                    else{
                                     var pertenecebd =data[i].pertenecebd;

                                        if(pertenecebd ==="SINDO CIZ 3"){pertenecebd = "CIZ3"}
                                        else if(pertenecebd ==="SINDO CIZ 2"){pertenecebd = "CIZ2"}
                                        else if(pertenecebd ==="SINDO CIZ 1"){pertenecebd = "CIZ1"}
                                        else if(pertenecebd ==="HIST&Oacute;RICO CENTRAL"||pertenecebd ==="HIST&Oacute;RICO"){pertenecebd = "HISTORICO"}
                                        // console.log(pertenecebd);
                                        $("#agregaNss"+pertenecebd+"curp").val(data[i].curp);

                                        $("#agregaNss"+pertenecebd+"apellidoPaterno").val(data[i].apellidoPaterno);

                                        $("#agregaNss"+pertenecebd+"apellidoMaterno").val(data[i].apellidoMaterno);

                                        $("#agregaNss"+pertenecebd+"nombre").val(data[i].nombre);

                                        $("#agregaNss"+pertenecebd+"sexo").val(data[i].sexo);

                                        $("#agregaNss"+pertenecebd+"fechaNacimiento").val(data[i].fechaNacimiento);

                                        $("#agregaNss"+pertenecebd+"lugarNacimiento").val(data[i].lugarNacimiento);

                                        $("#agregaNss"+pertenecebd+"nacionalidad").val(data[i].nacionalidad);

                                        $("#agregaNss"+pertenecebd+"datosDocumentoProbatorio").val(data[i].pertenecebd);   
                                    }

                                }
                        module.controller.agregarNssDocumentoController.model.nssOculto=nss0;
                },
                error : function(error) {
                        // console.log(error);
                        module.service.removeModal();
                        module.updateModel("AlertComponent", "alert", {
                                        level : "danger",
                                        message : "No se encontr\u00F3 informaci\u00F3n relacionada al NSS."
                        });
                        module.controller.agregarNssDocumentoController.model.nssOculto=null;
                        module.controller.agregarNssDocumentoController.model.nssEditado= null;
                }
        });
    }	
};

ResponsableServices.prototype.obtenerComboDocumentosNSS = function(){
	var url = this.OBTENER_COMBO_DOCUMENTO_NSS;
	$.ajax({
        url : url,
        type : 'post',
        async : false,
        dataType : 'json',
        data : JSON.stringify({idTipo:0}),
        contentType : "application/json; charset=utf-8",     
        success : function(response) {
            // console.log(response);
            var select = "#combodocumentosSelectField";
            var options = "<option value='-1'>--Por favor seleccione--</option>";
            var optId = 1;
            var names = [];
            var ids = [];
            for (var o in response ) {
                var res = o.split("=");
                // console.log(res);
                var otr=res[2].split("]");
                var desded=otr[0];
                names.push(desded);
                var idrep=res[1].split(",");
                ids.push(idrep[0]);
            }
            var e=0;
            $.each(response, function(arrayID,tipoDocumentoProbatorio) {
                 var descripcion = names[e],
                        idTipoDocumentoProbatorio = ids[e],
                        lObligatorio = (idTipoDocumentoProbatorio != "16")? " (Obligatorio)" : "",
                        docsPorTipo = tipoDocumentoProbatorio[0].tipoDocumento; 
                        e++;
                     options += "<optgroup id='optgroup"+idTipoDocumentoProbatorio+"' label='" + descripcion + lObligatorio + "' style='color: #101010; font-weight: 700;' tipo='"+ docsPorTipo +"' value='" + idTipoDocumentoProbatorio + "'>";
                $.each(tipoDocumentoProbatorio, function(documentoArrayId,documentoObjeto) {
                        // console.log("tipodocumento "+documentoObjeto);
                        var cveIdDocumento = documentoObjeto.cveIdDocumento,
                            idDocumentoPorTipo = documentoObjeto.idDocumentoPorTipo;
                            var desDocumento =documentoObjeto.desDocumento; 
                            var tipoDocumento = documentoObjeto.tipoDocumento;
                        options += "<option value='"+ cveIdDocumento +"' docportipo='"+ idDocumentoPorTipo +"' tipoDoc='"+ tipoDocumento +"' docpara='"+""+"'>"+ desDocumento +"</option>";
                        optId++;
                 });
            });
            $(select).html(options);
        },
        error : function(error) {
        	// console.log(error);
			alert("No se pudo obtener la lista de documentos");
			module.service.removeModal();
			
			if(module.controller.model[gridId] == null){ 
				module.updateModel("AlertComponent", "alert", {
					level : "danger",
					message : "No se encontr\u00F3 informaci\u00F3n."
				});
			}
        }
    });
}

ResponsableServices.prototype.crearElementos = function(){
	var htmls="<table class='table' id='listadoDocumentosGrid' style=''>";
	htmls +="<tr></tr>";
	htmls +="</table>";
	$("#listadoDocumentosGrid" ).replaceWith(htmls);
	
	var htmlfileHidden="<div class='col-md-2' style='display: none;'>";
		htmlfileHidden+= "<input id='fileData' type='file' name='fileData' class='mboton'>";
		htmlfileHidden+= "</div>";
		$("#fileData" ).replaceWith(htmlfileHidden);
};

ResponsableServices.prototype.indexarListaDocumento = function() {
	$('#listadoDocumentosGrid tr').each(function (index) {
	      $(this).children(' td:first').html('<p style="font-size: 1.5em;">' + index + "</p>");
	});
};

ResponsableServices.prototype.guardarDocumento = function() {
	module.updateModel("AlertComponent", "alert", {level : "info",message : ""});
	var indiceDocumentoProbatorio = module.controller.agregarNssDocumentoController.model.indiceDocumentoProbatorio;
	var registroDocumentoProbatorio = module.controller.agregarNssDocumentoController.model.registroDocumentoProbatorio;
	var tipoDocumento = module.controller.agregarNssDocumentoController.model.tipoDocumento;
        var descTipoDocumento = module.controller.agregarNssDocumentoController.model.registroDocumentoProbatorio;
	var idDocumentoPorTipo = module.controller.agregarNssDocumentoController.model.idDocumentoPorTipo;
	var desDocumento = module.controller.agregarNssDocumentoController.model.desDocumento;
	// console.log("indiceDocumentoProbatorio " +indiceDocumentoProbatorio);
	// console.log("guardarDocumento " +idDocumentoPorTipo);
	var urlDocumentoAdjunto = this.GUARDAR_DOCUMENTO_BOVEDA;
	var ext = $("#fileData").val().split("\\");
	var nombreArchivo = ext[ext.length - 1];
	nombreArchivo = (idDocumentoPorTipo + "_").concat(nombreArchivo);
	var ext = $("#fileData").val();
	var n = ext.split("\\");
	var nombreExtension = n[n.length - 1];
	n = nombreExtension.split(".");
	nombreExtension = n[n.length - 1];
	// console.log("filedata");
	// console.log($("#fileData").val());
	// console.log($("#fileData").val() != "");
	if ($("#fileData").val() != "") {
		if (nombreExtension == 'gif' || nombreExtension == 'tif'
				|| nombreExtension == 'jpg' || nombreExtension == 'png'
				|| nombreExtension == 'pdf') {
			nombreExtension = "";
			var cveIdDocumento = module.controller.agregarNssDocumentoController.model.cveIdDocumento;
//			 = $("#combodocumentosSelectField").val();
			var objFormData = new FormData();
			var objFile = $("#fileData")[0].files[0];
			objFormData.append('fileData', objFile);
			objFormData.append('idDocPorTipo', idDocumentoPorTipo);
			objFormData.append('cveIdDocumento', cveIdDocumento);
			objFormData.append('desDocumento',unescape(registroDocumentoProbatorio));
			objFormData.append('tipoDocumento',tipoDocumento);
			objFormData.append('noFolioSolicitud', new String(module.controller.agregarNssDocumentoController.model.folio));
			objFormData.append('solicitudId', new String(module.controller.agregarNssDocumentoController.model.idSolicitud));
			objFormData.append('nombre', nombreArchivo);
			var valida_docto = {'idDocumentoPorTipo': idDocumentoPorTipo, 'cveIdDocumento':cveIdDocumento,'desDocumento':registroDocumentoProbatorio,'tipoDocumento':tipoDocumento, 'nombre':nombreArchivo};
			// console.log(objFormData);
			$.ajax({
						url : urlDocumentoAdjunto,
						async : false,
						contentType : false,
						type: 'POST',
						data : objFormData,
						processData : false,
						error : function(data, status) {
							// console.log(data);
							if(data.status==412){
								module.updateModel("AlertComponent", "alert", {
									level : "danger",
									message : "Error al adjuntar Documento"
								});
                                                                mensageConfirmacion(data.responseJSON.error);
								// console.log(data);
							}
							$("#combodocumentosSelectField").val(-1);
							ResponsableServices.prototype
									.limpiarCamposAgregados([ "fileData" ]);
							module.service.removeModal();
						},
						success : function(data, status) {
                                                        // console.log("data:" + data);
							var params = JSON.parse(data); //data;//JSON.parse(data);
							// console.log(params.idDocBoveda);
							var idDocBoveda =params.idDocBoveda;
							// console.log("Guardo en boveda y el id es : "+ idDocBoveda);
							var arrayCampos = [ ++indiceDocumentoProbatorio,
									registroDocumentoProbatorio ];
							var arrayCamposHidden = [ nombreArchivo,
									desDocumento, idDocumentoPorTipo ];
							arrayCamposHidden.push(idDocBoveda);
							ResponsableServices.prototype
									.agregaDocProTabla(arrayCampos,
											arrayCamposHidden,
											indiceDocumentoProbatorio,
											'listadoDocumentosGrid',
											descTipoDocumento,
											cveIdDocumento,"true");
							var idTipoDato = $(
									"#combodocumentosSelectField option[value='"
											+ cveIdDocumento + "']").parent()
									.attr("value");
//							ResponsableServices.prototype.selectDocumentoNSS(
//									desDocumento, true);
							$("#combodocumentosSelectField").val(-1);
							ResponsableServices.prototype
									.limpiarCamposAgregados([ "fileData" ]);
							valida_docto.idDocBoveda = idDocBoveda;
							module.controller.agregarNssDocumentoController.model.documentosAgregados.push(valida_docto);
							module.service.removeModal();
                                                        mensageConfirmacion("Documento adjuntado exitosamente.");
						}
					});
			ResponsableServices.prototype
					.limpiarCamposAgregados([ "fileData" ]);
		} else {
			$("#combodocumentosSelectField").val(-1);
			ResponsableServices.prototype
					.limpiarCamposAgregados([ "fileData" ]);                                
                        mensageConfirmacion("El archivo no cumple con el tipo de formato permitido [.pdf, .jpg, .png, .gif, .tif]. No es posible adjuntar el archivo.");        

			
		}
	}
};

ResponsableServices.prototype.agregaDocProTabla= function(arrayCampos, arrayCamposHidden, rowCount,
        tableName, tipoDocto, cveIdDocumento, eliminar) {
    var idDocBoveda = arrayCamposHidden[arrayCamposHidden.length - 1];
    var idTr = tableName + rowCount;
    var trHtml = '<tr id=\'' + idTr + '\'>';
    for (var i = 0; i < arrayCampos.length; i++) {
        trHtml += '<td width="5%" nowrap><p style="font-size: 1.5em;">' + arrayCampos[i] + '</p></td>';
    }
    for (var i = 0; i < arrayCamposHidden.length; i++) {
        trHtml += '<td hidden="hidden">' + arrayCamposHidden[i] + '</td>';
    }
    trHtml += '<td hidden="hidden">' + tipoDocto + '</td>';
	if(eliminar != undefined && eliminar === "true"){
		trHtml += '<td align="right" width="90%" height="55"> <a href="#" onclick="module.controller.agregarNssDocumentoController.eliminarDocumentos(\'' + idTr
            + '\',' + cveIdDocumento + ',\'' + idDocBoveda + '\');" ><p style="font-size: 1.5em;">Eliminar</p></a> </td></tr>';
	}
    $('#' + tableName + ' tbody').append(trHtml);
    ResponsableServices.prototype.indexarListaDocumento();
};

ResponsableServices.prototype.limpiarCamposAgregados = function(arrayCamposLimpiar) {
    jQuery.each(arrayCamposLimpiar, function (i, val) {
        $("#" + val).val("");
    });
};

ResponsableServices.prototype.selectDocumentoNSS= function(claveTipoDocumento, bandera) {
    if (bandera) {
        $(
            "#registroDocumentoProbatorio option[value='"
            + claveDocumentoNSS + "']").attr("disabled", true);
    } else {
        $("#registroDocumentoProbatorio option[value='"
        + claveDocumentoNSS + "']").removeAttr("disabled");
    }
};

ResponsableServices.prototype.salirAConsulta = function() {
	$("#modalAConsulta").modal();
};

ResponsableServices.prototype.irAConsulta = function() {
	module.service.fetchSolicitud("consultaSolicitud",module, null);  
	module.render.module.controller.transition("informacionSolicitud");
//	module.updateModel("CardLayoutComponent",
//			"responsableCardLayout", 2);
};

/**
 * Limpia los campos de la vista de AgregarNss
 */
ResponsableServices.prototype.limpiarCamposNssAgregados = function(){
	 $("#combodocumentosSelectField").val(-1);
	ResponsableServices.prototype.limpiarCamposAgregados(["nssBuscar","observacion"]);
	ResponsableServices.prototype.limpiarCamposAgregados(["agregaNssCANASEcurp",
	                                                      "agregaNssCANASEsexo",
	                                                      "agregaNssCANASEapellidoPaterno",
	                                                      "agregaNssCANASEfechaNacimiento",
	                                                      "agregaNssCANASEapellidoMaterno",
	                                                      "agregaNssCANASElugarNacimiento",
	                                                      "agregaNssCANASEnombre",
	                                                      "agregaNssCANASEnacionalidad"]);
	ResponsableServices.prototype.limpiarCamposAgregados(["agregaNssCIZ1curp",
	                                                      "agregaNssCIZ1sexo",
	                                                      "agregaNssCIZ1apellidoPaterno",
	                                                      "agregaNssCIZ1fechaNacimiento",
	                                                      "agregaNssCIZ1apellidoMaterno",
	                                                      "agregaNssCIZ1lugarNacimiento",
	                                                      "agregaNssCIZ1nombre",
	                                                      "agregaNssCIZ1nacionalidad"]);
	ResponsableServices.prototype.limpiarCamposAgregados(["agregaNssCIZ2curp",
	                                                      "agregaNssCIZ2sexo",
	                                                      "agregaNssCIZ2apellidoPaterno",
	                                                      "agregaNssCIZ2fechaNacimiento",
	                                                      "agregaNssCIZ2apellidoMaterno",
	                                                      "agregaNssCIZ2lugarNacimiento",
	                                                      "agregaNssCIZ2nombre",
	                                                      "agregaNssCIZ2nacionalidad"]);
	ResponsableServices.prototype.limpiarCamposAgregados(["agregaNssCIZ3curp",
	                                                      "agregaNssCIZ3sexo",
	                                                      "agregaNssCIZ3apellidoPaterno",
	                                                      "agregaNssCIZ3fechaNacimiento",
	                                                      "agregaNssCIZ3apellidoMaterno",
	                                                      "agregaNssCIZ3lugarNacimiento",
	                                                      "agregaNssCIZ3nombre",
	                                                      "agregaNssCIZ3nacionalidad"]);
	ResponsableServices.prototype.limpiarCamposAgregados(["agregaNssHISTORICOcurp",
	                                                      "agregaNssHISTORICOsexo",
	                                                      "agregaNssHISTORICOapellidoPaterno",
	                                                      "agregaNssHISTORICOfechaNacimiento",
	                                                      "agregaNssHISTORICOapellidoMaterno",
	                                                      "agregaNssHISTORICOlugarNacimiento",
	                                                      "agregaNssHISTORICOnombre",
	                                                      "agregaNssHISTORICOnacionalidad"]);
	ResponsableServices.prototype.limpiarCamposAgregados(["agregaNssBDTUcurp",
	                                                      "agregaNssBDTUsexo",
	                                                      "agregaNssBDTUapellidoPaterno",
	                                                      "agregaNssBDTUfechaNacimiento",
	                                                      "agregaNssBDTUapellidoMaterno",
	                                                      "agregaNssBDTUlugarNacimiento",
	                                                      "agregaNssBDTUnombre",
	                                                      "agregaNssBDTUnacionalidad"]);
};


/**
 * Guarda o actualiza un tramite (nss) cosn sus documentos probatorios de AgregarNss
 */
ResponsableServices.prototype.guardarNss = function(){
	// console.log("guardarNss");
	var url = this.GUARDAR_NSS;
	var module=this.module;
	module.updateModel("AlertComponent", "alert", {level : "info",message : ""});
	var arrayDocumentosProbatorios=module.controller.agregarNssDocumentoController.model.documentosAgregados;
	var arrayEliminados=module.controller.agregarNssDocumentoController.model.documentosEliminados;
	var observacion=$("#observacion").val();
	var idSolicitud = module.controller.agregarNssDocumentoController.model.idSolicitud;
	var folio = module.controller.agregarNssDocumentoController.model.folio;
	var nssDatos = module.controller.agregarNssDocumentoController.model.nssOculto;
	

	
				
				
  $("#modal").modal();
        $.ajax({
			url : url,
			data : JSON.stringify({nss:module.controller.agregarNssDocumentoController.model.nssOculto
				,observacion:observacion,documentosProbatorios:arrayDocumentosProbatorios
				,idSolicitud:idSolicitud,folio: folio,documentosProbatoriosEliminados : (arrayEliminados.length>0 ? arrayEliminados : null)}),
			type : "POST",
			contentType : "application/json; charset=UTF-8",
			dataType : "json",
			async:false,
			success : function(data) {
				// console.log("datos de agregar nss");
				// console.log(data);
    			
				
				
				/*module.updateModel("AlertComponent", "alert", {
					level : "info",
					message : "Operaci\u00f3n realizada con \u00e9xito."
				});*/
                                
                mensageConfirmacion("Operaci\u00f3n realizada con \u00e9xito.");
				//module.service.tramitesAgregarNssVentanilla(module);
//				module.service.fetchSolicitudAgregarNss(module);
//		        module.service.fetchSolicitud( "consultaSolicitud", module , null );
//		        module.controller.transition("agregarNSS");
				//actualiar el modelo para la tabla de nss agregados por ventanilla
                                var obs = $("#observacion").val();
				//var nuevoNss = JSON.stringify({nss:module.controller.agregarNssDocumentoController.model.nssOculto
				//,observaciones:obs,documentosProbatorios:arrayDocumentosProbatorios,
				//origen : "2"});
				//module.controller.agregarNssDocumentoController.model.gridNssVentanilla.push(JSON.parse(nuevoNss));
				module.updateModel("NssDocumentsComponent", "gridNssSolicitud", data.gridNssSolicitud.data);
                                module.updateModel("NssDocumentsComponent", "gridNssVentanilla", data.gridNssVentanilla.data);
				module.controller.agregarNssDocumentoController.model.nssOculto=null; 
                                
                                /** Lista de documentos temporales(al final se agrega a un nss) **/
                                module.controller.agregarNssDocumentoController.model.documentosAgregados=new Array();
                                /** Lista de documentos temporales(eliminados en temporal) **/
                                module.controller.agregarNssDocumentoController.model.documentosEliminados=new Array();
				ResponsableServices.prototype.limpiarCamposNssAgregados();
                                module.controller.agregarNssDocumentoController.reiniciarDocumentos();
                                ResponsableServices.prototype.limpiarCamposNssAgregados();
		        $("#modal").modal("hide");
        
			},
			error : function(errMsg) {
				// console.log(errMsg);
				$("#modal").modal("hide");
				module.service.removeModal();
				module.updateModel("AlertComponent", "alert", {
					level : "danger",
					message : "No fue posible realizar la operaci\u00F3n"
				});
			}
		});
        
};


/**
 * Elimina un documento de boveda y actualiza la vista de AgregarNss
 * @param idRow
 * @param idDocumento
 * @param idBoveda
 * @param indexdocumento
 */
ResponsableServices.prototype.eliminarDocumentoBoveda = function(idRow,idDocumento,idBoveda,indexdocumento){
	// console.log("******** Eliminacion de documentos service ************");
	var url= this.ELIMINAR_DOCUMENTO;
	 var item = $('#' + idRow);
	    idRow = $("#listadoDocumentosGrid tr").index(item);
	    idRow = idRow - 1;
	    var documentos=module.controller.agregarNssDocumentoController.model.documentosAgregados;
		// console.log(documentos);
	    var objFormData = new FormData();
	    objFormData.append('idDocBoveda', idBoveda);
		objFormData.append('cveIdDocumento', idDocumento);
		$.ajax({
			url : url,
			async : false,
			contentType : false,
			type: 'POST',
			data : objFormData,
			processData : false,
			error : function(data, status) {
				// console.log(params);
			},
			success : function(data, status) {
				// console.log(data);
				$(item).remove();
				documentos.splice(indexdocumento,1);
				ResponsableServices.prototype.indexarListaDocumento();
				module.service.removeModal();
			}
		});
};


/**
 * Metodo que elimina un documento en boveda de AgregarNss
 * @param idDocumento
 * @param idBoveda
 * @param indexdocumento
 */
ResponsableServices.prototype.eliminarByBoveda = function(idDocumento,idBoveda,indexdocumento){
	// console.log("******** Eliminacion de documentos service ************");
	var documentos=module.controller.agregarNssDocumentoController.model.documentosAgregados;
	var url= this.ELIMINAR_DOCUMENTO;
	    var objFormData = new FormData();
	    objFormData.append('idDocBoveda', idBoveda);
		objFormData.append('cveIdDocumento', idDocumento);
		$.ajax({
			url : url,
			async : false,
			contentType : false,
			type: 'POST',
			data : objFormData,
			processData : false,
			error : function(data, status) {
				var params =data; //JSON.parse(data);
				module.service.removeModal();
			},
			success : function(data, status) {
				var params =JSON.parse(data);
				// console.log(params);
				documentos.splice(indexdocumento,1);
				// console.log(documentos);
				module.service.removeModal();
			}
		});
};


/**
 * Guarda o actualiza un tramite (nss) cosn sus documentos probatorios de AgregarNss
 */
ResponsableServices.prototype.eliminarNSS = function(nss){
	// console.log("guardarNss");
	var url = this.ELIMINAR_NSS;
	
        $.ajax({
			url : url,
			data : JSON.stringify({nss:nss}),
			type : "POST",
			contentType : "application/json; charset=UTF-8",
			dataType : "json",
			async:false,
			success : function(data) {
				// console.log("datos de agregar nss");
				// console.log(data);
				module.updateModel("AlertComponent", "alert", {
					level : "info",
					message : "Operaci\u00f3n realizada con \u00e9xito."
				});
			},
			error : function(errMsg) {
				// console.log(errMsg);
				module.service.removeModal();
				module.updateModel("AlertComponent", "alert", {
					level : "danger",
					message : "No fue posible realizar la operaci\u00F3n"
				});
			}
		});
};

ResponsableServices.prototype.eliminarDocumento = function(nss, idBoveda, folio){
	// console.log("guardarNss");
	var url = this.ELIMINAR_NSS + "?nss=" + nss  + "&idBoveda=" + idBoveda + "&folio=" + folio ;
                
        $.ajax({
			url : url,
			contentType : false,
			type: 'GET',
			async:false,
			success : function(data) {
				// console.log("datos de agregar nss");
				// console.log(data);
				dtos = JSON.parse(data);
				module.updateModel("NssDocumentsComponent", "gridNssVentanilla", dtos.data.data);
                                var documentos=module.controller.agregarNssDocumentoController.model.documentosAgregados;
				if(documentos == "undefined" || documentos.length == 0){
					module.service.limpiarCamposNssAgregados();
					module.controller.agregarNssDocumentoController.model.nssOculto=null;
					module.controller.agregarNssDocumentoController.reiniciarDocumentos();
				}
				module.updateModel("AlertComponent", "alert", {
					level : "info",
					message : "Operaci\u00f3n realizada con \u00e9xito."
				});
			},
			error : function(errMsg) {
				// console.log(errMsg);
				module.service.removeModal();
				module.updateModel("AlertComponent", "alert", {
					level : "danger",
					message : "No fue posible realizar la operaci\u00F3n"
				});
			}
		});
};




ResponsableServices.prototype.obtenerGridNssVentanilla = function() {
	$("#modal").modal();
	// console.log("Inicia la busqueda de nss agregar nss")
  var url = this.OBTENER_ORIGEN;
	
	var objFormData = new FormData();
    objFormData.append('folio', this.module.controller.agregarNssDocumentoController.model.folio);
	objFormData.append('idOrigen', 2);
	$.ajax({
		url : url,
		async : false,
		contentType : false,
		type: 'POST',
		data : objFormData,
		processData : false,
		error : function(data, status) {
			// console.log("***Error *****");
			// console.log(data)
			// console.log(data.listNss);
			var params =data; //JSON.parse(data);
			module.service.removeModal();
		},
		success : function(data, status) {
			// console.log("***Exito *****");
			module.controller.model.nssModificado=data.listNss;
			// console.log(data);
			// console.log(data.listNss);
			// console.log(JSON.stringify(module.controller.model.nssModificado));
			module.render.notify("GridComponent","gridAgregarDocumentosNssOrigen"); //updateModel("GridComponent", "gridAgregarDocumentosNssOrigen", data.listNss,"nssModificados");
			
			module.service.removeModal();
		}
	});
};


ResponsableServices.prototype.obtenerGridNssPortal = function() {
	$("#modal").modal();
	// console.log("Inicia la busqueda de nss agregar nss")
  var url = this.OBTENER_ORIGEN;
	
	var objFormData = new FormData();
    objFormData.append('folio', this.module.controller.agregarNssDocumentoController.model.folio);
	objFormData.append('idOrigen', 1);
	$.ajax({
		url : url,
		async : false,
		contentType : false,
		type: 'POST',
		data : objFormData,
		processData : false,
		error : function(data, status) {
			// console.log(data)
			// console.log(data.documentos);
			var params =data; //JSON.parse(data);
			module.service.removeModal();
		},
		success : function(data, status) {
			// console.log("***Exito *****");
			// console.log(data);
			// console.log(data.listNss);
			module.controller.model.nssModificado=data;
			// console.log(JSON.stringify(module.controller.model.nssModificado));
			module.render.notify("GridComponent","gridAgregarDocumentosNssOrigen");
//			module.updateModelGroupGrids("GridComponent", "gridAgregarDocumentosNssOrigen", data,"gridDocumentosNssOrigen");
//			
			module.service.removeModal();
		}
	});
};

ResponsableServices.prototype.borrarCI = function(module) {
	var url = this.DELETE_CI;
	var params = module.controller.model.consultaSolicitud.cveCorreccionDatos;	
	$("#borradoCI").modal('hide');	
	$("#modal").modal();
	if (url !== null) {
		$.ajax({
			url : url,
			data : JSON.stringify(params),
			type : "POST",
			contentType : "application/json; charset=UTF-8",
			dataType : "json",
			mode : "abort",
			port : "uniqueport",
			success : function(data) {				
				$("#modal").modal('hide');
			},
			error : function(errMsg) {
				if (errMsg === null) {
					errMsg = "create.error";
				}
				$("#modal").modal('hide');
				module.updateModel("AlertComponent", "alert", {
					level : "danger",
					message : errMsg
				});
			}
		});
	}
};
