function ConfrontaCuentaIndividualService(module) {
  this.module = module;  
  this.FETCH = "atencionResponsable/cuentaIndividual/inicio.do";  
}

ConfrontaCuentaIndividualService.prototype.fetch = function( module, componentId ) {
  var url = this.FETCH;
  var params = { folio: this.module.controller.model.consultaSolicitud.folio};
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
        // console.log("Guardando model.." + componentId);
        module.service.removeModal();
        module.updateModel( "ConfrontaCuentaIndividualComponent", componentId, data );
        
      },
      error: function(errMsg){        
        if( errMsg === null ){
          errMsg = "create.error";
        }
        $("#modal").modal('hide');
        module.updateModel( "AlertComponent","alert", { level:"danger", message: errMsg} );
      }
    });
  }
};
