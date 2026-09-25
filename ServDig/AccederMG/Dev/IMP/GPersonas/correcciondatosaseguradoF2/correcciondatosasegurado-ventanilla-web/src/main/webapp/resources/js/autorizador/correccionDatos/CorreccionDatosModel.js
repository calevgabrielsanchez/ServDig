function CorreccionDatosModel(module) {
  this.module = module;
  this.init();
}

/*
 * 
 * En caso de identificar que existen diferencias en el Nombre, Primer apellido 
 * o Segundo apellido, el sistema debe marcar por default la opci贸n 鈥淐orrecci贸n 
 * de nombre鈥? y dejarla habilitada en caso de que el Responsable requiera hacer 
 * alguna modificaci贸n.
 */
CorreccionDatosModel.prototype.RNFUE16 = function( currentIndex ){
  /*
  var model = this.module.controller.model.correccionDatos.listaNss[currentIndex];
  var renapo = this.module.controller.model.correccionDatos.renapo;
  
  if( this.module.render.isEmpty( model.tipoAclaracion ) ){
    model.tipoAclaracion = {};
  }
  
  if( !this.module.render.isEmpty(model.canase) ){
    if( renapo.nombre !== model.canase.nombre ||
        renapo.apellidoPaterno !== model.canase.apellidoPaterno ||
        renapo.apellidoMaterno !== model.canase.apellidoMaterno ){
        model.tipoAclaracion.correccionNombre = true;
    }
  }
  // console.log( model.tipoAclaracion.correccionNombre );
  */
};


CorreccionDatosModel.prototype.prepareValidator = function( model ){  
  var that = this;
  /*
   * En caso de identificar que solo existe un NSS involucrado en la Solicitud, 
   * el sistema debe marcar por default la opci贸n 鈥淐ertificador鈥? 
   * como Tipo de NSS.
   */
  $.validator.addMethod("RNFUE17", function (value, element, params) {        
    var i,aux;
    aux = 0;
    var fullModel = that.module.controller.model.correccionDatos;
    for( i=0; i<fullModel.listaNss.length; i++ ){
      if( fullModel.listaNss[i].tipoNss !== undefined && 
        ( fullModel.listaNss[i].tipoNss.certificador === true || fullModel.listaNss[i].tipoNss.certificador === "true" ) 
        
        ){
        aux++;
      }
    }  
    
    return  aux === 1;
  });
  
  /*
   * El Responsable deber谩 especificar qu茅 tipo de NSS le corresponde a cada uno de los NSS
   * involucrados en la Solicitud, en donde las opciones son las siguientes:
   * -Certificador.
   * -Asociado al certificador.
   * -Corresponde a otra persona.
   * -No existe en CANASE.
   * En la Solicitud siempre debe existir 煤nicamente un NSS del tipo Certificador.
   */
  $.validator.addMethod("RN037", function (value, element, params) {        
    var i,aux;
    aux = 0;
    var fullModel = that.module.controller.model.correccionDatos;
    for( i=0; i<fullModel.listaNss.length; i++ ){
      if( fullModel.listaNss[i].tipoNss !== undefined && ( fullModel.listaNss[i].tipoNss.certificador === true || fullModel.listaNss[i].tipoNss.certificador === "true" ) ){
        aux++;
      }
    }  
    
    return  aux === 1;
  });
  
};
CorreccionDatosModel.prototype.getDisabledTipoNss = function( currentIndex ){
  var model = this.module.controller.model.correccionDatos;
  if( model.listaNss[currentIndex].tipoNss !== undefined && 
      ( model.listaNss[currentIndex].tipoNss.certificador === true || model.listaNss[currentIndex].tipoNss.certificador === "true" ) ){
        model.listaNss[currentIndex].tipoNss.asociado = false;
        model.listaNss[currentIndex].tipoNss.corresOtraPersona = false;
        model.listaNss[currentIndex].tipoNss.noExisteCanase = false;
      return { certificador: false, asociado: true, corresOtraPersona:true, noExisteCanase:true  };
  }else{
    var i,aux;
    aux=0;
    for( i=0; i<model.listaNss.length; i++ ){
      if( model.listaNss[i].tipoNss !== undefined && ( model.listaNss[i].tipoNss.certificador === true || model.listaNss[i].tipoNss.certificador === "true" ) ){
        aux++;
      }
    }
    if( aux > 0 ){
      model.listaNss[currentIndex].tipoNss.certificador = false;            
      return { certificador: true, asociado: false, corresOtraPersona:false, noExisteCanase:false  };
    }
    return { certificador: false, asociado: false, corresOtraPersona:false, noExisteCanase:false  };
  }  
};

CorreccionDatosModel.prototype.getDisabledTipoAclaracion = function( currentIndex ){
  var model = this.module.controller.model.correccionDatos;
  // console.log( model.listaNss[currentIndex].tipoNss );
  if( model.listaNss[currentIndex].tipoNss !== undefined ){
    if( model.listaNss[currentIndex].tipoNss.certificador === true || model.listaNss[currentIndex].tipoNss.certificador === "true" ){
      return { canceladoDup: true, homonimio:true, noExisteCanase:true, otroAsegurado: true, correccionNombre: false, correccionEstadis: false };
    }
    if( model.listaNss[currentIndex].tipoNss.asociado === true || model.listaNss[currentIndex].tipoNss.asociado === "true" ){
      return { canceladoDup: false, homonimio:true, noExisteCanase:true, otroAsegurado: true, correccionNombre: false, correccionEstadis: false };
    }
    if( model.listaNss[currentIndex].tipoNss.corresOtraPersona === true || model.listaNss[currentIndex].tipoNss.corresOtraPersona === "true" ){
      return { canceladoDup: true, homonimio:false, noExisteCanase:true, otroAsegurado: false, correccionNombre: true, correccionEstadis: true };
    }
    if( model.listaNss[currentIndex].tipoNss.noExisteCanase === true || model.listaNss[currentIndex].tipoNss.noExisteCanase === "true" ){
      return { canceladoDup: true, homonimio:true, noExisteCanase:false, otroAsegurado: true, correccionNombre: true, correccionEstadis: true };
    }
  }
  return { canceladoDup: false, homonimio:false, noExisteCanase:false, otroAsegurado: false, correccionNombre: false, correccionEstadis: false };
};


CorreccionDatosModel.prototype.init = function () {
  this.entities = [
    {name: "tipoNss",
      fields: [
        {name: "certificador", domain: "certificador"}
      ]
    }
  ];
  this.domains=[
    { name: "certificador",
      rules: [
        { type:"Custom", name: "RN037", message: "Debe seleccionar un certificador"}
      ]
    }
  ];
};
