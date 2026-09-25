function BandejaSolicitudesServices(module) {
  this.module = module;
  this.READ_TRAMITES_ASIGNADOS="bandejaSolicitudes/tramitesAsignados.do";
  this.READ_HISTORICO_SOLICITUDES="bandejaSolicitudes/historicoSolicitudes.do";
  this.READ_USER_PROFILE="userProfile.do";
};

BandejaSolicitudesServices.prototype.getUserProfile = function(module) { 
	var url = this.READ_USER_PROFILE;
  if( url !== null ){
    $.ajax({
      url: url,
      type: "GET",     
      contentType: "application/json; charset=UTF-8",
      dataType: "json",            
      success: function(data){
        module.updateModel( "UserProfileComponent", "userProfile", data );
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

BandejaSolicitudesServices.prototype.fetchTramites = function(gridId, module, page ) { 
	var url = this.READ_TRAMITES_ASIGNADOS;
  var params = { page: page, pageSize: 5 };
  if( url !== null ){
    $.ajax({
      url: url,
      data: JSON.stringify( params ),
      type: "POST",     
      contentType: "application/json; charset=UTF-8",
      dataType: "json",            
      success: function(data){
        console.log(data);
        module.updateModel( "GridComponent", gridId, data );
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

BandejaSolicitudesServices.prototype.fetchHistorico = function(gridId, module,page ) { 
	var url = this.READ_HISTORICO_SOLICITUDES;
  var params = { page: page, pageSize: 5 };
  if( url !== null ){
    $.ajax({
      url: url,
      data: JSON.stringify( params ),
      type: "POST",     
      contentType: "application/json; charset=UTF-8",
      dataType: "json",            
      success: function(data){
        console.log(data);
        module.updateModel( "GridComponent", gridId, data );
      },
      error: function(errMsg){        
        if( errMsg === null ){
          errMsg = "create.error";
        }
        if( errMsg.status === 404 ){
          errMsg = "No se encuentra el recurso solicitado";
        }
        module.updateModel( "AlertComponent", "alert", { level:"danger", message: errMsg} );
      }
    });
  }
};