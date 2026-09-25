function CorreccionDatosService(module) {
	this.module = module;
	this.FETCH = "obtenerOrigenFuentes.do";
	this.SAVE = "guardarCorrecion.do"; 
}

CorreccionDatosService.prototype.save = function (module) {
	  var url = this.SAVE;
	  var params = this.module.controller.model.correccionDatos;
	  params.folioSolitud = this.module.controller.model.consultaSolicitud.folio;
	    
	  if( url !== null ){
	    $("#modal").modal();
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
		 
	      
						module.controller.next("correccionDatos");
						
	      },
	      error: function(errMsg){    
	        $("#modal").modal('hide');
	        
	        module.controller.next("correccionDatos");
	        module.updateModel( "AlertComponent", "alert", { level:"danger", message: "No se pudo hacer el guardado parcial de la solicitud reintente porfavor."} );
	      }
	    });
	  }
	};
	
CorreccionDatosService.prototype.fetch = function(module, componentId) {
	var url = this.FETCH;
	var params = {
		idSolicitud : this.module.controller.model.consultaSolicitud.idSolicitud,
		folio : this.module.controller.model.consultaSolicitud.folio,
		informacionRENAPO : this.module.controller.model.consultaSolicitud.informacionRENAPO
	};
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
				// console.log("Guardando model.." + componentId);
				module.updateModel("CorreccionDatosComponent", componentId,
						data);
				module.controller.consultaSolicitudController.postFetchCorreccionDatos(componentId);
			},
			error : function(errMsg) {
				if (errMsg === null) {
					errMsg = "create.error";
				}
				$("#modal").modal('hide');
				module.updateModel("AlertComponent", "alert", {
					level : "danger",
					message : "No esta disponible RENAPO en este momento."
				});
			}
		});
	}
};
