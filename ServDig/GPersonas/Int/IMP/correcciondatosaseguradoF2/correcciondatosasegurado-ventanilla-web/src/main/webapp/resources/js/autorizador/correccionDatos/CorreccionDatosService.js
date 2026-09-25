function CorreccionDatosService(module) {
  this.module = module;  
  this.FETCH = "obtenerOrigenFuentes.do";    
}

CorreccionDatosService.prototype.fetch = function( module ) {
  var url = this.FETCH;
  var params = { idSolicitud: this.module.controller.model.consultaSolicitud.idSolicitud,
    informacionRENAPO: this.module.controller.model.consultaSolicitud.informacionRENAPO
  };
  $("#modal").modal();
  if( url !== null ){
    $.ajax({
      url: url,
      data: JSON.stringify( params ),
      type: "POST",     
      contentType: "application/json; charset=UTF-8",
      dataType: "json",
      mode:"abort",
      port:"uniqueport",
      success: function(data){        
        $("#modal").modal('hide');
        module.updateModel( "CorreccionDatosComponent", "correccionDatos", data );
        
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
