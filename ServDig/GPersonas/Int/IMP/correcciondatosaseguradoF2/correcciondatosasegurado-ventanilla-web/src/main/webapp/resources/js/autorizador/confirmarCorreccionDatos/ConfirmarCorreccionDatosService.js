function ConfirmarCorreccionDatosService(module) {
  this.module = module;
  this.SAVE = "guardarCorrecion.do"; 
}
ConfirmarCorreccionDatosService.prototype.save = function (module) {
  var url = this.SAVE;
  var params = this.module.controller.model.correccionDatos;
  
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
        
        module.controller.transition("fin");
        module.updateModel( "AlertComponent","alert", { level:"info", message: "Se almacen&oacute; de manera exitosa"} );
      },
      error: function(errMsg){    
        $("#modal").modal('hide');
        
        module.controller.transition("fin");
        module.updateModel( "AlertComponent", "alert", { level:"danger", message: "Ocurrio un error al guardar"} );
      }
    });
  }
};

ConfirmarCorreccionDatosService.prototype.compareTo = function (model, source, data, dato, origenInformacion) {
  if (source !== model) {
    data[data.length] = {origenInformacion: origenInformacion,
      dato: dato, informacionIMSS: model,
      informacionActualizacion: source,
      estatus: "", fechaProceso: ""
    };
  }
};
ConfirmarCorreccionDatosService.prototype.armaDetalle = function (model) {
  var detalle = "";
  if( !this.module.render.isEmpty( model.tipoAclaracion ) ){    
      if( model.tipoAclaracion.canceladoDup === "true"){
        detalle += "Cancelado por duplicidad,";
      }
      // console.log( model.tipoAclaracion.correccionEstadis );
      if( model.tipoAclaracion.correccionEstadis === "true"){
        detalle += "Corrección de datos estadísticos, ";
      }
      if( model.tipoAclaracion.correccionNombre === "true"){
        detalle += "Corrección de nombre, ";
      }
      if( model.tipoAclaracion.homonimio === "true"){
        detalle += "Corresponde a un homónimo, ";
      }
      if( model.tipoAclaracion.noExisteCanase === "true"){
        detalle += "No existe en CANASE, ";
      }
      if( model.tipoAclaracion.otroAsegurado === "true"){
        detalle += "Corresponde a otro asegurado, ";
      }
      if( detalle.length > 0){ 
        detalle = detalle.substring( 0, detalle.length-2 );
        detalle += ".";
      }
    }
    return detalle;
};
ConfirmarCorreccionDatosService.prototype.fetch = function (module) {

  var confirmarCorreccionDatos = {};
  var correccionDatos = this.module.controller.model.correccionDatos;
  var consultaSolicitud = this.module.controller.model.consultaSolicitud;
  // console.log(correccionDatos);
  confirmarCorreccionDatos.renapo = correccionDatos.renapo;

  confirmarCorreccionDatos.solicitud = {
    folio: consultaSolicitud.folio,
    tipoTramite: ""};

  confirmarCorreccionDatos.certificador = [];
  confirmarCorreccionDatos.asociado = [];
  confirmarCorreccionDatos.noCorresponde = [];

  var entities = [
    {name: "bdtu", label: "BDTU"},
    {name: "canase", label: "CANASE"},    
    {name: "historico", label: "HISTORICO"},
    {name: "cizUno", label: "CIZ1"},
    {name: "cizDos", label: "CIZ2"},
    {name: "cizTres", label: "CIZ3"}
  ];

  var fields = [
    {dato: "Nombre", model: "nombre"},
    {dato: "Apellido Paterno", model: "apellidoPaterno"},
    {dato: "Apellido Materno", model: "apellidoMaterno"},
    {dato: "CURP", model: "curp"},
    {dato: "Sexo", model: "sexo"},
   // {dato: "Fecha de nacimiento", model: "fechaNacimiento"},
    {dato: "Lugar de nacimiento", model: "lugarNacimiento"},
    {dato: "Nacionalidad", model: "nacionalidad"},
    {dato: "Datos doc. prob.", model: "datosDocumentoProbatorio"}
  ];
  var renapo = correccionDatos.renapo;
  var i, j, k;
  // EncontrR CERTIFICADOR
  for (i = 0; i < correccionDatos.listaNss.length; i++) {
    
    var model = correccionDatos.listaNss[i];
    // console.log( model );
    var detalle = this.armaDetalle(model);
    
    if (model.tipoNss.certificador === true ||
            model.tipoNss.certificador === "true") {
      var data = [];
      var certificador = {nss: model.nss, detalle: detalle};

      for (j = 0; j < entities.length; j++) {
        var entity = entities[j];
        if (!this.module.render.isEmpty(model[entity.name])) {
          var aux1="";
          for (k = 0; k < fields.length; k++) {
            var row = model[entity.name];
            var field = fields[k].model;
            aux1+=row[field];
            if(aux1 === "null"){
              aux1 = "";
            }
          }
          // console.log(">>>>"+aux1+"<<<<");
          if(aux1.length ===0){
            continue;
          }
          
          for (k = 0; k < fields.length; k++) {
            var row = model[entity.name];
            var field = fields[k].model;
            this.compareTo(row[field], renapo[field], data, fields[k].dato, entity.label);
          }
        }
      }
      
      if( !this.module.render.isEmpty(model.bdtu) ){
        var row = model.bdtu;
        this.compareTo(row.fechaNacimiento, renapo.fechaNacimiento, data, "Fecha de nacimiento", "BDTU");
      }
      
      for (j = 1; j < entities.length; j++) {
        var entity = entities[j];
        if (!this.module.render.isEmpty(model[entity.name])) {          
            var row = model[entity.name];
            if( row.fechaNacimiento !== null && row.fechaNacimiento !== "null" ){
              // console.log("BASE:" + (""+row.fechaNacimiento).substring(3, 5) );
              this.compareTo( (""+row.fechaNacimiento).substring(3, 5), (""+renapo.fechaNacimiento).substring(3, 5), data, "Fecha de nacimiento", entity.label);          
            }
        }
      }
      
      // console.log(data);
      if (data.length > 0) {
        certificador.data = data;
        confirmarCorreccionDatos.certificador[ confirmarCorreccionDatos.certificador.length] = certificador;
      }


    }


  }

  // Asociado
  // EncontrR CERTIFICADOR

  for (i = 0; i < correccionDatos.listaNss.length; i++) {
    var model = correccionDatos.listaNss[i];
    // console.log(model);
    var detalle = this.armaDetalle(model);
    if (model.tipoNss.asociado === true || model.tipoNss.asociado === "true") {

      var asociado = {nss: model.nss, detalle: detalle};
      // canase
      if (!this.module.render.isEmpty(model.canase)) {
        // console.log(model.canase);
        if (renapo.nombre !== model.canase.nombre
                || renapo.apellidoPaterno !== model.canase.apellidoPaterno
                || renapo.apellidoMaterno !== model.canase.apellidoMaterno
                || renapo.apellidoMaterno !== model.canase.apellidoMaterno
                || renapo.curp !== model.canase.curp
                || renapo.sexo !== model.canase.sexo
                || renapo.fechaNacimiento !== model.canase.fechaNacimiento
                || renapo.lugarNacimiento !== model.canase.lugarNacimiento
                || renapo.nacionalidad !== model.canase.nacionalidad
                || renapo.datosDocumentoProbatorio !== model.canase.datosDocumentoProbatorio) {
          asociado.canase = model.canase;
        }
      }

      if (!this.module.render.isEmpty(model.bdtu)) {
        // console.log(model.bdtu);
        if (renapo.nombre !== model.bdtu.nombre
                || renapo.apellidoPaterno !== model.bdtu.apellidoPaterno
                || renapo.apellidoMaterno !== model.bdtu.apellidoMaterno
                || renapo.apellidoMaterno !== model.bdtu.apellidoMaterno
                || renapo.curp !== model.bdtu.curp
                || renapo.sexo !== model.bdtu.sexo
                || renapo.fechaNacimiento !== model.bdtu.fechaNacimiento
                || renapo.lugarNacimiento !== model.bdtu.lugarNacimiento
                || renapo.nacionalidad !== model.bdtu.nacionalidad
                || renapo.datosDocumentoProbatorio !== model.bdtu.datosDocumentoProbatorio) {
          asociado.bdtu = model.bdtu;
        }
        if (!this.module.render.isEmpty(model.cizUno)) {
          // console.log(model.cizUno);
          if (renapo.nombre !== model.cizUno.nombre
                  || renapo.apellidoPaterno !== model.cizUno.apellidoPaterno
                  || renapo.apellidoMaterno !== model.cizUno.apellidoMaterno
                  || renapo.apellidoMaterno !== model.cizUno.apellidoMaterno
                  || renapo.curp !== model.cizUno.curp
                  || renapo.sexo !== model.cizUno.sexo
                  || renapo.fechaNacimiento !== model.cizUno.fechaNacimiento
                  || renapo.lugarNacimiento !== model.cizUno.lugarNacimiento
                  || renapo.nacionalidad !== model.cizUno.nacionalidad
                  || renapo.datosDocumentoProbatorio !== model.cizUno.datosDocumentoProbatorio) {
            asociado.cizUno = model.cizUno;
          }
        }

        if (!this.module.render.isEmpty(model.cizDos)) {
          // console.log(model.cizDos);
          if (renapo.nombre !== model.cizDos.nombre
                  || renapo.apellidoPaterno !== model.cizDos.apellidoPaterno
                  || renapo.apellidoMaterno !== model.cizDos.apellidoMaterno
                  || renapo.apellidoMaterno !== model.cizDos.apellidoMaterno
                  || renapo.curp !== model.cizDos.curp
                  || renapo.sexo !== model.cizDos.sexo
                  || renapo.fechaNacimiento !== model.cizDos.fechaNacimiento
                  || renapo.lugarNacimiento !== model.cizDos.lugarNacimiento
                  || renapo.nacionalidad !== model.cizDos.nacionalidad
                  || renapo.datosDocumentoProbatorio !== model.cizDos.datosDocumentoProbatorio) {
            asociado.cizDos = model.cizDos;
          }
        }


        if (!this.module.render.isEmpty(model.ciztres)) {
          // console.log(model.ciztres);
          if (renapo.nombre !== model.ciztres.nombre
                  || renapo.apellidoPaterno !== model.ciztres.apellidoPaterno
                  || renapo.apellidoMaterno !== model.ciztres.apellidoMaterno
                  || renapo.apellidoMaterno !== model.ciztres.apellidoMaterno
                  || renapo.curp !== model.ciztres.curp
                  || renapo.sexo !== model.ciztres.sexo
                  || renapo.fechaNacimiento !== model.ciztres.fechaNacimiento
                  || renapo.lugarNacimiento !== model.ciztres.lugarNacimiento
                  || renapo.nacionalidad !== model.ciztres.nacionalidad
                  || renapo.datosDocumentoProbatorio !== model.ciztres.datosDocumentoProbatorio) {
            asociado.ciztres = model.ciztres;
          }
        }


        if (!this.module.render.isEmpty(model.historico)) {
          // console.log(model.historico);
          if (renapo.nombre !== model.historico.nombre
                  || renapo.apellidoPaterno !== model.historico.apellidoPaterno
                  || renapo.apellidoMaterno !== model.historico.apellidoMaterno
                  || renapo.apellidoMaterno !== model.historico.apellidoMaterno
                  || renapo.curp !== model.historico.curp
                  || renapo.sexo !== model.historico.sexo
                  || renapo.fechaNacimiento !== model.historico.fechaNacimiento
                  || renapo.lugarNacimiento !== model.historico.lugarNacimiento
                  || renapo.nacionalidad !== model.historico.nacionalidad
                  || renapo.datosDocumentoProbatorio !== model.historico.datosDocumentoProbatorio) {
            asociado.historico = model.historico;
          }
        }
      }


      // console.log(asociado);

      if (asociado.canase !== undefined
              || asociado.bdtu !== undefined
              || asociado.ciZUno !== undefined
              || asociado.cizDos !== undefined
              || asociado.cizTres !== undefined
              || asociado.historico !== undefined) {
        // console.log(asociado);

        confirmarCorreccionDatos.asociado[confirmarCorreccionDatos.asociado.length] = asociado;

      }
    }
  }


  // Asociado
  // EncontrR CERTIFICADOR

  for (i = 0; i < correccionDatos.listaNss.length; i++) {
    var model = correccionDatos.listaNss[i];
    // console.log(model);
    var detalle = this.armaDetalle(model);
    if (model.tipoNss.corresOtraPersona === true || model.tipoNss.corresOtraPersona === "true") {
      var corresOtraPersona = {nss: model.nss, detalle: detalle };
      confirmarCorreccionDatos.noCorresponde[confirmarCorreccionDatos.noCorresponde.length] = corresOtraPersona;
    }

  }



  module.updateModel("ConfirmarCorreccionDatosComponent", "confirmarCorreccionDatos", confirmarCorreccionDatos);




  this.module.controller.transition("confirmarCorreccionDatos");


};
/*
 * 
 ConfirmarCorreccionDatosService.prototype.fetch = function( module ) {
 var url = this.FETCH;
 var params = this.module.controller.model.correccionDatos;
 
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
 module.updateModel( "ConfirmarCorreccionDatosComponent", "confirmarCorreccionDatos", data );
 this.module.updateModel("CardLayoutComponent","responsableCardLayout", 16);
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
 */