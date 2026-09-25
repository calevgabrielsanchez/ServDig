function CuentaIndividualService(module){
    this.module = module;
    this.READ_CUENTA_INDIVIDUAL_NSS_URL = "readCuentaIndividualNss";
    this.READ_LISTA_CUENTA_INDIVIDUAL_REGISTRO_PATRONAL_URL = "readListaCuentaIndividualRegistroPatronal";
    this.READ_PAGE_CUENTA_INDIVIDUAL_PERIODO_URL = "readPageCuentaIndividualPeriodo";
    this.CREATE_CUENTA_INDIVIDUAL_CORRECCION_NSS_REGISTRO_PATRONAL_URL = "createCuentaIndividualCorreccionNssRegistroPatronal";
    this.CREATE_CUENTA_INDIVIDUAL_LISTA_CORRECCION_URL = "createCuentaIndividualListaCorreccion";
    this.FETCH = "atencionResponsable/cuentaIndividual/inicio.do";
    this.COMPLETE = "atencionResponsable/cuentaIndividual/complete.do";
}

//TMP
CuentaIndividualService.prototype.fetch = function( module, componentId ) {
  $("#modal").modal();
  var url = this.FETCH;
  var params = { folio: this.module.controller.model.consultaSolicitud.folio};
  
  module.controller.model[componentId] ={};
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
	  if(data.listaCuentaIndividualNssCertificador.length != 0)
	  {
	  for(var i = 0 ; i < data.listaCuentaIndividualNssCertificador[0].listaPeriodosRegistroPatronal.length ; i++)
	  {
		  if(data.listaCuentaIndividualNssCertificador[0].listaPeriodosRegistroPatronal[i].periodosNuevos != undefined)
		  for(var h = 0 ; h < data.listaCuentaIndividualNssCertificador[0].listaPeriodosRegistroPatronal[i].periodosNuevos.length; h++ )
		  {
			  if(data.listaCuentaIndividualNssCertificador[0].listaPeriodosRegistroPatronal[i].periodosNuevos != undefined)
			  if(h+1 < data.listaCuentaIndividualNssCertificador[0].listaPeriodosRegistroPatronal[i].periodosNuevos.length)
			  {
				if((data.listaCuentaIndividualNssCertificador[0].listaPeriodosRegistroPatronal[i].periodosNuevos[h].fechaInicioMovimiento ===
					data.listaCuentaIndividualNssCertificador[0].listaPeriodosRegistroPatronal[i].periodosNuevos[h+1].fechaInicioMovimiento)
					&& (data.listaCuentaIndividualNssCertificador[0].listaPeriodosRegistroPatronal[i].periodosNuevos[h].salarioBase ===
					data.listaCuentaIndividualNssCertificador[0].listaPeriodosRegistroPatronal[i].periodosNuevos[h+1].salarioBase)
					&& (data.listaCuentaIndividualNssCertificador[0].listaPeriodosRegistroPatronal[i].periodosNuevos[h].fechaRecepcionMovimiento ===
					data.listaCuentaIndividualNssCertificador[0].listaPeriodosRegistroPatronal[i].periodosNuevos[h+1].fechaRecepcionMovimiento)					)
				{
				  data.listaCuentaIndividualNssCertificador[0].listaPeriodosRegistroPatronal[i].periodosNuevos.splice(h,1);
				}
			  }
		  }		 	  
	  }
	  }
        //module.service.removeModal();
        if( data.status !== undefined && data.status === "error"){
          var message = "No es posible consultar la cuenta individual";                
          module.updateModel( "AlertComponent","alert", { level:"danger", message: message} );
        }else{
          module.service.cuentaIndividualService.readCuentaIndividualNss(componentId, module);
          /*module.updateModel( "CuentaIndividualComponent", componentId, data );
          var disabled = [false,false, false, false, false];
          module.updateModel("ButtonGroupComponent", "cuentaIndividualButtonGroup", disabled);*/
        }
      },
      error: function(errMsg){  
        var message = "No es posible consultar la cuenta individual";        
        $("#modal").modal('hide');
        module.updateModel( "AlertComponent","alert", { level:"danger", message: message} );
      }
    });
  }
};


CuentaIndividualService.prototype.readPageCuentaIndividualPeriodo = function( componentId, page, registroPatronal ){
  console.log( registroPatronal );
  $("#modal").modal();
  var url = this.READ_PAGE_CUENTA_INDIVIDUAL_PERIODO_URL;
  var params = { page: page, model: registroPatronal };
  params.model.data = null;
  params.model.periodos = null;
  params.model.nuevos=0;
  params.model.incluidos=0;
  params.model.eliminados=0;
  params.model.modificados=0;
  var data = JSON.stringify( params );
  var module = this.module;
  this.module.service.post( url, data,
    function(data){
        console.log("Pagina");
        console.log(data);
        // Vamos a mantener la estructura de la pagina para
        // simplificar la vista
        // Almacenamos una copia de la pagina para la comunicacion con el servidor
        registroPatronal.data = JSON.parse( JSON.stringify(data.data) );
        registroPatronal.incluidos = [];
        for( var i=0; i<data.incluidos.length; i++ ){
          var cveIdPeriodoCuentaIndividual = data.incluidos[i].destino.cuentaIndividualPeriodo.cveIdPeriodoAnterior;
          for(var j=0; j<data.data.length; j++){
            if( cveIdPeriodoCuentaIndividual === data.data[j].cveIdPeriodoCuentaIndividual){
              data.data[j] = data.incluidos[i].destino.cuentaIndividualPeriodo;
              data.data[j].tipoRegularizacionPeriodo = "I";
              data.data[j].cuentaIndividualNss.cveIdDetalleNssCda = data.incluidos[i].destino.cuentaIndividualNss.cveIdDetalleNssCda;
              data.data[j].cuentaIndividualNss.nss = data.incluidos[i].destino.cuentaIndividualNss.nss;
              data.data[j].cveIdPeriodoCuentaIndividual = cveIdPeriodoCuentaIndividual;
              data.data[j].indicadorConsecutivoMovimiento = data.incluidos[i].indicadorConsecutivoMovimiento;
              registroPatronal.incluidos[registroPatronal.incluidos.length] = data.incluidos[i];
              break;
            }
          }
        }
        registroPatronal.eliminados = [];
        for( var i=0; i<data.eliminados.length; i++ ){
          var cveIdPeriodoCuentaIndividual = data.eliminados[i].origen.cuentaIndividualPeriodo.cveIdPeriodoCuentaIndividual;
          for(var j=0; j<data.data.length; j++){
            if( cveIdPeriodoCuentaIndividual === data.data[j].cveIdPeriodoCuentaIndividual){
              data.data[j].tipoRegularizacionPeriodo = "E";
              data.data[j].cveIdPeriodoCuentaIndividual = cveIdPeriodoCuentaIndividual;
              data.data[j].indicadorConsecutivoMovimiento = data.eliminados[i].indicadorConsecutivoMovimiento;
              registroPatronal.eliminados[registroPatronal.eliminados.length] = data.eliminados[i];
              break;
            }
          }
        }
        registroPatronal.modificados = [];
        for( var i=0; i<data.modificados.length; i++ ){
          var cveIdPeriodoCuentaIndividual = data.modificados[i].origen.cuentaIndividualPeriodo.cveIdPeriodoCuentaIndividual;
          for(var j=0; j<data.data.length; j++){
            if( cveIdPeriodoCuentaIndividual === data.data[j].cveIdPeriodoCuentaIndividual){
              data.data[j] = data.modificados[i].destino.cuentaIndividualPeriodo;
              data.data[j].cveIdPeriodoCuentaIndividual = cveIdPeriodoCuentaIndividual;
              data.data[j].tipoRegularizacionPeriodo = "M";
              data.data[j].indicadorConsecutivoMovimiento = data.modificados[i].indicadorConsecutivoMovimiento;
              registroPatronal.modificados[registroPatronal.modificados.length] = data.modificados[i];
              break;
            }
          }
        }
        registroPatronal.nuevos = data.nuevos;
        
        registroPatronal.registroPatronal= data.registroPatronal;
        
        registroPatronal.periodos = data.data;
        
        
        module.render.notify( "IteratorComponent", componentId + ".periodos", registroPatronal.periodos );
        // Armar pager
        var totalOfRecords = data.totalOfRecords;
        var pageSize = data.pageSize;
        var currentPage = data.currentPage;
        var totalOfPages = Math.ceil( totalOfRecords / pageSize );
        
        var minPage = currentPage - 5;
        if (minPage < 1) {
            minPage = 1;
        }
        var maxPage = currentPage + 5;
        if (maxPage > totalOfPages) {
            maxPage = totalOfPages;
        }
        if( (maxPage - minPage)<10 && maxPage < totalOfPages ){
            var faltantes = 10-(maxPage - minPage);
            if( totalOfPages >= (maxPage+faltantes) ){
              maxPage += faltantes;
            }else{
              maxPage = totalOfPages;
            }
        }
        registroPatronal.pager = [];
        var j=0;
        for(var i=1;i<=maxPage;i++){
          if( page === i ){
            j = registroPatronal.pager.length;
          }
          registroPatronal.pager[registroPatronal.pager.length] = { nss:i};
            
        }
      
        // Calcuar el indice que corresponde a la pagina actual
        module.controller.model[module.render.toId(componentId + ".pager")] = j;
        module.render.notify( "NssPagerComponent",  module.render.toId(componentId + ".pager"), registroPatronal.pager );
        module.service.removeModal();
        
        window.location.hash = module.render.toId( componentId + "-anchor");
    }
  );
};

CuentaIndividualService.prototype.createCuentaIndividualCorreccionNssRegistroPatronal = function( model, parentId ){
  console.log( model );
  $("#modal").modal();
  var url = this.CREATE_CUENTA_INDIVIDUAL_CORRECCION_NSS_REGISTRO_PATRONAL_URL;
  model.data = null;
  model.periodos = null;
  model.nuevos=0;
  model.incluidos=0;
  model.eliminados=0;
  model.modificados=0;
  var params = model;
  var data = JSON.stringify( params );
  var module = this.module;
  this.module.service.post( url, data,
    function(data){
        console.log(data);
        module.service.removeModal();
        $("#cuentaIndividualModal5").modal();
        module.updateModel("LabelComponent", "labelModal5", "Se almacenaron las correcciones de cuenta individual" );
        var parameters = { type: 'CuentaIndividualNssComponent', event: 'loadPage', key: parentId, model:'' };
        module.render.triggerEvent( window.btoa( JSON.stringify( parameters )) );
    }
  );
};

CuentaIndividualService.prototype.createCuentaIndividualListaCorreccion = function( model, parentId, registroPatronalId ){
  console.log( model );
  $("#modal").modal();
  var url = this.CREATE_CUENTA_INDIVIDUAL_LISTA_CORRECCION_URL;
  var params = model;
  var data = JSON.stringify( params );
  var module = this.module;
  this.module.service.post( url, data,
    function(data){
        console.log(data);
        module.service.removeModal();
        $("#cuentaIndividualModal5").modal();
        module.updateModel("LabelComponent", "labelModal5", "Se almacenaron las correcciones de cuenta individual" );
        var parameters = { type: 'CuentaIndividualNssComponent', event: 'loadPage', key: parentId, model:'' };
        module.render.triggerEvent( window.btoa( JSON.stringify( parameters )) );
        //module.render.triggerEvent('CuentaIndividualNssComponent', 'loadPage', parentId , '' );
        window.location.hash = module.render.toId( registroPatronalId + "-anchor");
    }
  );
};


CuentaIndividualService.prototype.readCuentaIndividualNss = function( componentId, module ){
  console.log( componentId );
  //$("#modal").modal();
  
  var cveIdCorreccionDatosAsegurado = module.controller.model.consultaSolicitud.cveCorreccionDatos;
  var url = this.READ_CUENTA_INDIVIDUAL_NSS_URL;
  var params = cveIdCorreccionDatosAsegurado;
  var data = JSON.stringify( params );
  
  this.module.service.post( url, data,
    function(data){
        console.log(data);
        data.folioSolicitud = module.controller.model.consultaSolicitud.folio;
        data.tipoTramite = module.controller.model.consultaSolicitud.tipoTramite;
        
        //RN si los NSS tienen tipo certificacion de DUPLICIDAD se eliminan de la CI
        // Los NSS de este tipo no deben aparecer en el combo del certificador
        var nssEliminar = [];
        for(var i=0; i< data.listaNssTipoAsociadoAsegurado.length; i++){
          var row = data.listaNssTipoAsociadoAsegurado[i];
          for( var j=0; j< row.listaTipoRegularizacion.length; j++ ){
            if( row.listaTipoRegularizacion[j] === "CANCELADO POR DUPLICIDAD"){
              nssEliminar[nssEliminar.length] = row.nss;
              data.listaNssTipoAsociadoAsegurado.splice(i,1);
              i--;
              break;
            }
          }          
        }
        for(var i=0; i< data.listaNssTipoNoCorrespondeAsegurado.length; i++){
          var row = data.listaNssTipoNoCorrespondeAsegurado[i];
          for( var j=0; j< row.listaTipoRegularizacion.length; j++ ){
            if( row.listaTipoRegularizacion[j] === "CANCELADO POR DUPLICIDAD"){
              data.listaNssTipoNoCorrespondeAsegurado.splice(i,1);
              nssEliminar[nssEliminar.length] = row.nss;
              i--;
              break;
            }
          }          
        }
        
        //Eliminar del combo del Certificador los NSS no adminitidos para incluicion
        
        for(var i=0; i< data.listaNssTipoCertificador.length; i++){
          var row = data.listaNssTipoCertificador[i];
          for( var j=0; j< nssEliminar.length; j++ ){
            var nss = nssEliminar[j];
            for( var k=0; k< row.listaNssDestino.length; k++){
              if( nss === row.listaNssDestino[k].nss ){
                row.listaNssDestino.splice(k,1);
                k--;
              }
            }
          }
        }
        
       
        
        module.updateModel("CuentaIndividualComponent", componentId, data);
        if(data.listaNssTipoCertificador.length > 0){
          var parameters = { type: 'CuentaIndividualNssComponent', event: 'page', key: componentId +'.listaNssTipoCertificador', model:'0' };
          module.render.triggerEvent( window.btoa( JSON.stringify( parameters )) );
          //module.render.triggerEvent('CuentaIndividualNssComponent', 'page', componentId +'.listaNssTipoCertificador', '0' );
        }
        if(data.listaNssTipoAsociadoAsegurado.length > 0){
          var parameters = { type: 'CuentaIndividualNssComponent', event: 'page', key: componentId +'.listaNssTipoAsociadoAsegurado', model:'0' };
          module.render.triggerEvent( window.btoa( JSON.stringify( parameters )) );
          //module.render.triggerEvent('CuentaIndividualNssComponent', 'page', componentId +'.listaNssTipoAsociadoAsegurado', '0' );
        }
        if(data.listaNssTipoNoCorrespondeAsegurado.length > 0){
          var parameters = { type: 'CuentaIndividualNssComponent', event: 'page', key: componentId +'.listaNssTipoNoCorrespondeAsegurado', model:'0' };
          module.render.triggerEvent( window.btoa( JSON.stringify( parameters )) );
          //module.render.triggerEvent('CuentaIndividualNssComponent', 'page', componentId +'.listaNssTipoNoCorrespondeAsegurado', '0' );
        }
        module.service.removeModal();
    }
  );
  
};

CuentaIndividualService.prototype.readListaCuentaIndividualRegistroPatronal = function( componentId, modelName, module, cveIdDetalleNssCda, listaNssDestino  ){
  console.log( componentId );
  $("#modal").modal();
  var url = this.READ_LISTA_CUENTA_INDIVIDUAL_REGISTRO_PATRONAL_URL;
  var params = cveIdDetalleNssCda;
  var data = JSON.stringify( params );
  
  this.module.service.post( url, data,
    function(data){
        console.log(data);
        var model = module.render.getModel(modelName);
        model.listaPeriodosRegistroPatronal = data;
        for(var i=0; i<model.listaPeriodosRegistroPatronal.length; i++ ){
            model.listaPeriodosRegistroPatronal[i].listaNss = listaNssDestino;
        }
        module.render.notify("CuentaIndividualNssComponent", componentId);
        module.service.removeModal();
    }
  );
  
};


CuentaIndividualService.prototype.complete = function( componentId ) {
  var url = this.COMPLETE;
  var module = this.module;
  var params = { folioSolicitud: this.module.controller.model[componentId].folioSolicitud };
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
        success: function (data) {
          console.log("Validando mensaje del backend " + data.message + " folio: " + data.folioSolicitud);
            let message;
            if (data.message !== "" || data.message !== "null") {
                message = data.message;
            } else {
                message = "Se ha registrado exitosamente la solicitud " + data.folioSolicitud + " con los cambios por aplicar y se ha enviado  para autorizaci\u00f3n."
            }
            module.service.removeModal();
            module.updateModel("LabelComponent", "labelM02_022", message);
            $("#modalFinResponsable").modal();
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
