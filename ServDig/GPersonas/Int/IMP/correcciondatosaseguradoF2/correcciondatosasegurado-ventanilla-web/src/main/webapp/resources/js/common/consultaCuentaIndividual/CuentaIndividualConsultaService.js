function CuentaIndividualConsultaService(module) {
  this.module = module;  
  this.FETCH = "atencionResponsable/cuentaIndividual/create.do";      
}

CuentaIndividualConsultaService.prototype.fetch = function( module, componentId ) {
  var url = this.FETCH;
  var params = { folioSolicitud: this.module.controller.model.consultaSolicitud.folio};
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
        module.updateModel( "CuentaIndividualConsultaComponent", componentId, data );
        module.render.module.controller.transition("consultaCuentaIndividual");
      },
      error: function(errMsg){        
    	  $("#modal").modal('hide');
          $('div.modal-backdrop.fade').remove();
  				module.updateModel("AlertComponent", "alert", {
  					level : "danger",
  					message : "No se econtr\u00F3 tipo de aclaraci\u00F3n para el nss"
  				});
  		  
      }
    });
  }
};