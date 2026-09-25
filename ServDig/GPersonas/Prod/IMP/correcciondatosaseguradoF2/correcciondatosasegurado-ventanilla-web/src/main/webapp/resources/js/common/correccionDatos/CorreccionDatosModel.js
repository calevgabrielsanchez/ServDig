function CorreccionDatosModel(module) {
  this.module = module;
  this.init();
}

CorreccionDatosModel.prototype.mostrasMensaje = function (model, currentNss) {
	
	if(model.listaNss[currentNss].origen==="Solicitud")
	{
		return "Agregado durante el registro de la Solicitud.";
	}
	else if(model.listaNss[currentNss].origen==="Responsable")
	{
		return "Localizado por NSS, incluido por personal de ventanilla.";
	}
	else if(model.listaNss[currentNss].origen==="Sistema")
	{
		return "Localizado por CURP, incluido por sistema.";
	}	
}
CorreccionDatosModel.prototype.noExisteInformacion = function (model, currentNss) {

  var origenDatos = {
    canase: false,
    historico: false,
    cizUno: false,
    cizDos: false,
    cizTres: false,
    bdtu: false
  };

  var contador = currentNss;
  var fuente=0;
  var fuenterec=0;
  
  for (fuenterec in model.listaNss[contador].canase) {
		if(model.listaNss[contador].canase[fuenterec]=== null){
			fuente = fuente + 1;
			if(fuente == 7){
					origenDatos.canase = true;
				}				
	    }	    
	}
  fuente = 0;
  fuenterec = 0;
  
  for (fuenterec in model.listaNss[contador].historico) {
		if(model.listaNss[contador].historico[fuenterec]=== null)
	    {
			fuente = fuente + 1;
			if(fuente == 7){
				origenDatos.historico = true;
				}					
	    }	    
	}
  fuente = 0;  
  fuenterec = 0;
 
  for (fuenterec in model.listaNss[contador].cizUno) {
		if(model.listaNss[contador].cizUno[fuenterec]=== null)
	    {
			fuente = fuente + 1;
			if(fuente == 7){
				origenDatos.cizUno = true;
				}			
	    }	    
	}
  fuente = 0;
  fuenterec =0;
  
  for (fuenterec in model.listaNss[contador].cizDos) {
		if(model.listaNss[contador].cizDos[fuenterec]=== null)
	    {
			fuente = fuente + 1;
			if(fuente == 7){
				origenDatos.cizDos = true;
				}						
	    }   
	}
  fuente = 0;
  fuenterec = 0;
  
  for (fuenterec in model.listaNss[contador].cizTres) {
		if(model.listaNss[contador].cizTres[fuenterec]=== null)
	    {
			fuente = fuente + 1;
			if(fuente == 7){
				origenDatos.cizTres = true;
				}			
	    }	    
	}
  fuente = 0;
  fuenterec = 0;
  
  for (fuenterec in model.listaNss[contador].bdtu) {
		if(model.listaNss[contador].bdtu[fuenterec]=== null)
	    {
			fuente = fuente + 1;
			if(fuente == 7){
				origenDatos.bdtu = true;
				}						
	    }	    
	}
  fuente = 0;  

  return origenDatos;
};
/*
 * En caso de identificar que canase no contiene datos, 
 * el sistema debe marcar por default la opci\u00f3n “Noexisteen canase"‿ 
 * como Tipo de NSS.
 */
CorreccionDatosModel.prototype.noExisteCanase = function (model, currentIndex) {
  if (!this.module.render.isEmpty(model)) {
    // console.log("Verificando CANASE");
    if (model.listaNss[currentIndex].canase.apellidoMaterno === null &&
            model.listaNss[currentIndex].canase.apellidoPaterno === null &&
            model.listaNss[currentIndex].canase.curp === null &&
            model.listaNss[currentIndex].canase.curpsHistoricas === null &&
            model.listaNss[currentIndex].canase.datosDocumentoProbatorio === null &&
            model.listaNss[currentIndex].canase.fechaNacimiento === null &&
            model.listaNss[currentIndex].canase.lugarNacimiento === null &&
            model.listaNss[currentIndex].canase.nacionalidad === null &&
            model.listaNss[currentIndex].canase.nombre === null &&
            model.listaNss[currentIndex].canase.sexo === null) {
      // console.log("Marcando CANASE");
      model.listaNss[currentIndex].tipoNss.noExisteCanase = true;
      model.listaNss[currentIndex].tipoNss.tipo = "noExisteCanase";
      model.listaNss[currentIndex].tipoAclaracion.noExisteCanase = true;
    }



  }
}

CorreccionDatosModel.prototype.RN028 = function (model, currentIndex) {
  var i, aux;
  aux = 0;
  for (i = 0; i < model.listaNss.length; i++) {
    if (model.listaNss[i].convencional !== undefined &&
            (model.listaNss[i].convencional === true || model.listaNss[i].convencional === "true")) {
				model.listaNss[i].tipoNss.certificador = false;


      this.module.updateModel("AlertComponent", "alert2", {
        level: "danger",
        message: "El NSS Certificador involucrado en la solicitud es de tipo Convencional, por lo que no es posible atender la solicitud, por favor verifique que el NSS sea Ordinario."
                + "Se identifica un NSS Convencional cuando:"
                + " <br/> -	La posici\u00f3n 1 y 2 del NSS tiene el valor de 36 o 77 o 79 o 80 o 97."
                + " <br/> -	La posici\u00f3n 1 y 2 del NSS tiene el valor de 89 y la posici\u00f3n 3 y 4 tiene el valor 97 o 98 o 99 o 00 o 01 o 02."
      });

    }
    else if(model.listaNss[i].convencional === false || model.listaNss[i].convencional === "false"){
		var nss = model.listaNss[i].nss;
		var posUNO = nss.substring(0,2);
		var posDOS = nss.substring(2,4);
		if(posUNO === '36' || posUNO ==='77' ||posUNO ==='79' || posUNO ==='80' || posUNO === '97'){
			model.listaNss[i].tipoNss.certificador = false;
			model.listaNss[i].convencional = true;
		}
		if(posUNO === '89'  && (posDOS ==='97' || posDOS ==='98' || posDOS ==='99' || posDOS === '00' || posDOS === '01' || posDOS === '02')){
			model.listaNss[i].tipoNss.certificador = false;
			model.listaNss[i].convencional = true;
		}	
	}
  }


};

/*
 * 
 * En caso de identificar que existen diferencias en el Nombre, Primer apellido 
 * o Segundo apellido, el sistema debe marcar por default la opci\u00f3n “Correcci\u00f3n 
 * de nombre‿ y dejarla habilitada en caso de que el Responsable requiera hacer 
 * alguna modificaci\u00f3n.
 */
CorreccionDatosModel.prototype.RNFUE16 = function (model, currentIndex) {

	if(model.listaNss[currentIndex].tipoAclaracion.canceladoDup===false
	&& model.listaNss[currentIndex].tipoAclaracion.correccionEstadis===false
	&& model.listaNss[currentIndex].tipoAclaracion.correccionNombre===false
	&& model.listaNss[currentIndex].tipoAclaracion.cuentaIlogica===false
	&& model.listaNss[currentIndex].tipoAclaracion.cuentaIndividual===false
	&& model.listaNss[currentIndex].tipoAclaracion.homonimio===false
	&& model.listaNss[currentIndex].tipoAclaracion.noExisteCanase===false
	&& model.listaNss[currentIndex].tipoAclaracion.otroAsegurado===false)
	{
	 //console.log("validacion por nombre apellido curp");
	  if (!this.module.render.isEmpty(model)) {
	    if (!this.module.render.isEmpty(model.listaNss[currentIndex])) {
	      var row = model.listaNss[currentIndex];
	      if (!this.module.render.isEmpty(row)) {
	        if (model.renapo.nombre !== row.canase.nombre ||
	                model.renapo.apellidoPaterno !== row.canase.apellidoPaterno ||
	                model.renapo.apellidoMaterno !== row.canase.apellidoMaterno ||
	                model.renapo.nombre !== row.canase.nombre) {
	          model.listaNss[currentIndex].tipoAclaracion.correccionNombre = true;
	          return;
	        }
			else{
				 model.listaNss[currentIndex].tipoAclaracion.correccionNombre = false;
	          return;
			}
	      }
	    }
	  }
	}
	};

/*
 Cuando el NSS sea de tipo ?Corresponde a otra persona?, las opciones del tipo de
 regularizaci󮠿Corresponde a un hom󮩭o? y ?Corresponde a otro asegurado?
 deben de ser mutuamente excluyentes, es decir, el sistema debe permitir que el
 Responsable seleccione solo una de ellas.
 */
CorreccionDatosModel.prototype.RNFUE19 = function (model, currentIndex) {
  if (!this.module.render.isEmpty(model)) {
    if (!this.module.render.isEmpty(model.listaNss[currentIndex])) {
      var row = model.listaNss[currentIndex];
      if (row.tipoNss.tipo === "corresOtraPersona") {

        if (row.tipoAclaracion.homonimio === "true" && row.tipoAclaracion.otroAsegurado === "true") {
          // console.log("Mostrar Modal");
          $("#modalCorreccionDatosOtraPersona").modal('show');
          return false;
        }

      }
    }
  }
  return true;
};


CorreccionDatosModel.prototype.existCertificador = function (model, currentIndex) {

  var i, aux;
  aux = 0;
  for (i = 0; i < model.listaNss.length; i++) {
    if (model.listaNss[i].tipoNss.certificador !== undefined &&
            (model.listaNss[i].tipoNss.certificador === true || model.listaNss[i].tipoNss.certificador === "true")) {
      aux++;
    }
  }
  if (aux === 0) {
    $("#modalDebeHaberCertificador").modal('show');
    return false;
  }
  return true;
};

/*
 * En caso de identificar que solo existe un NSS involucrado en la Solicitud, 
 * el sistema debe marcar por default la opci\u00f3n “Certificador‿ 
 * como Tipo de NSS.
 */
CorreccionDatosModel.prototype.RNFUE17 = function (model) {
  if (!this.module.render.isEmpty(model)) {
    if (!(model.listaNss[0].convencional !== undefined &&
            (model.listaNss[0].convencional === true || model.listaNss[0].convencional === "true"))) {

      if (model.listaNss.length === 1) {
        model.listaNss[0].tipoNss.certificador = "true";
        model.listaNss[0].tipoNss.tipo = "certificador";
      }
    }
  }
}

/*
 En caso de identificar que existen diferencias en la CURP, Sexo, Fecha o Lugar de
 nacimiento, el sistema debe marcar por default la opci�n ?Correcci�n de datos
 estad�sticos? y dejarla habilitada en caso de que el Responsable requiera hacer
 alguna modificaci�n.
 */
CorreccionDatosModel.prototype.RNFUE15 = function (model, currentIndex) {
	if(model.listaNss.length === 1){
	if(model.listaNss[currentIndex].tipoAclaracion.canceladoDup===false
	&& model.listaNss[currentIndex].tipoAclaracion.correccionEstadis===false
	&& model.listaNss[currentIndex].tipoAclaracion.correccionNombre===false
	&& model.listaNss[currentIndex].tipoAclaracion.cuentaIlogica===false
	&& model.listaNss[currentIndex].tipoAclaracion.cuentaIndividual===false
	&& model.listaNss[currentIndex].tipoAclaracion.homonimio===false
	&& model.listaNss[currentIndex].tipoAclaracion.noExisteCanase===false
	&& model.listaNss[currentIndex].tipoAclaracion.otroAsegurado===false)
	{
		
	//console.log("validacion por estadisticos");
	   if (!this.module.render.isEmpty(model)) {
	    if (!this.module.render.isEmpty(model.listaNss[currentIndex])) {
	      var row = model.listaNss[currentIndex];
		  if (!this.module.render.isEmpty(row)) {
			if (model.renapo.curp !== row.canase.curp ||
	                model.renapo.sexo !== (row.canase.sexo).toUpperCase()||
	                (model.renapo.fechaNacimiento).slice(3,5) !== row.canase.fechaNacimiento||
	                model.renapo.lugarNacimiento !== row.canase.lugarNacimiento
	                ||model.renapo.nacionalidad !== row.canase.nacionalidad) {
	          model.listaNss[currentIndex].tipoAclaracion.correccionEstadis = true;
	          return;
	        }
			else{
		      model.listaNss[currentIndex].tipoAclaracion.correccionEstadis = false;
	          return;
			}
	      }	  
	    }
	  }
	}
	}
	else if(model.listaNss.length > 1)
	{
		if(currentIndex === 0 )
		{
			currentIndexLocal = currentIndex;
			currentIndexLocal = currentIndexLocal + 1;
	if((model.listaNss[currentIndexLocal].tipoAclaracion.canceladoDup===false || model.listaNss[currentIndexLocal].tipoAclaracion.canceladoDup==='false')
	&& (model.listaNss[currentIndexLocal].tipoAclaracion.correccionEstadis===false || model.listaNss[currentIndexLocal].tipoAclaracion.correccionEstadis==='false')
	&& (model.listaNss[currentIndexLocal].tipoAclaracion.correccionNombre===false || model.listaNss[currentIndexLocal].tipoAclaracion.correccionNombre==='false')
	&& (model.listaNss[currentIndexLocal].tipoAclaracion.cuentaIlogica===false || model.listaNss[currentIndexLocal].tipoAclaracion.cuentaIlogica==='false')
	&& (model.listaNss[currentIndexLocal].tipoAclaracion.cuentaIndividual===false || model.listaNss[currentIndexLocal].tipoAclaracion.cuentaIndividual==='false')
	&& (model.listaNss[currentIndexLocal].tipoAclaracion.homonimio===false || model.listaNss[currentIndexLocal].tipoAclaracion.homonimio==='false')
	&& (model.listaNss[currentIndexLocal].tipoNss.noExisteCanase===false || model.listaNss[currentIndexLocal].tipoNss.noExisteCanase==='false')
	&& (model.listaNss[currentIndexLocal].tipoAclaracion.otroAsegurado===false || model.listaNss[currentIndexLocal].tipoAclaracion.otroAsegurado==='false')
	&& (model.listaNss[currentIndexLocal].tipoAclaracion.blanqueoCurp===false || model.listaNss[currentIndexLocal].tipoAclaracion.blanqueoCurp==='false'))
	{
		
	//console.log("validacion por estadisticos");
	   if (!this.module.render.isEmpty(model)) {
	    if (!this.module.render.isEmpty(model.listaNss[currentIndex])) {
	      var row = model.listaNss[currentIndex];
		  if (!this.module.render.isEmpty(row)) {
			if (model.renapo.curp !== row.canase.curp ||
	                model.renapo.sexo !== (row.canase.sexo).toUpperCase()||
	                (model.renapo.fechaNacimiento).slice(3,5) !== row.canase.fechaNacimiento||
	                model.renapo.lugarNacimiento !== row.canase.lugarNacimiento
	                ||model.renapo.nacionalidad !== row.canase.nacionalidad) {
	          model.listaNss[currentIndex].tipoAclaracion.correccionEstadis = true;
	          return;
	        }
			else{
		      model.listaNss[currentIndex].tipoAclaracion.correccionEstadis = false;
	          return;
			}
	      }	  
	    }
	  }
	}
		}
	}
};
	
	/*
 En caso de identificar que existen diferencias en la CURP, Sexo, Fecha o Lugar de
 nacimiento, el sistema debe marcar por default la opci�n ?Correcci�n de datos
 estad�sticos? y dejarla habilitada en caso de que el Responsable requiera hacer
 alguna modificaci�n.
 */
CorreccionDatosModel.prototype.correccionEstadistico = function (model, currentIndex) {
	
		
	//console.log("validacion por estadisticos");
	   if (!this.module.render.isEmpty(model)) {
	    if (!this.module.render.isEmpty(model.listaNss[currentIndex])) {
	      var row = model.listaNss[currentIndex];
		  if (!this.module.render.isEmpty(row)) {
			if (model.renapo.curp !== row.canase.curp ||
	                model.renapo.sexo !== (row.canase.sexo).toUpperCase()||
	                (model.renapo.fechaNacimiento).slice(3,5) !== row.canase.fechaNacimiento||
	                model.renapo.lugarNacimiento !== row.canase.lugarNacimiento
	                ||model.renapo.nacionalidad !== row.canase.nacionalidad) {	          
	          return true;
	        }
			else{		      
	          return false;
			}
	      }	  
	    }
	  }
	};

CorreccionDatosModel.prototype.correccionNombre = function (model, currentIndex) {


	 //console.log("validacion por nombre apellido curp");
	  if (!this.module.render.isEmpty(model)) {
	    if (!this.module.render.isEmpty(model.listaNss[currentIndex])) {
	      var row = model.listaNss[currentIndex];
	      if (!this.module.render.isEmpty(row)) {
	        if (model.renapo.nombre !== row.canase.nombre ||
	                model.renapo.apellidoPaterno !== row.canase.apellidoPaterno ||
	                model.renapo.apellidoMaterno !== row.canase.apellidoMaterno ||
	                model.renapo.nombre !== row.canase.nombre) {
	          
	          return true;
	        }
			else{				 
	          return false;
			}
	      }
	    }
	  }
	
	};


CorreccionDatosModel.prototype.prepareValidator = function (model) {
  var that = this;
  /*
   * En caso de identificar que solo existe un NSS involucrado en la Solicitud, 
   * el sistema debe marcar por default la opci\u00f3n “Certificador‿ 
   * como Tipo de NSS.
   */
  $.validator.addMethod("RNFUE17", function (value, element, params) {
    var i, aux;
    aux = 0;
    var fullModel = that.module.controller.model.correccionDatos;
    if (!that.module.render.isEmpty(fullModel)) {
      for (i = 0; i < fullModel.listaNss.length; i++) {
        if (fullModel.listaNss[i].tipoNss !== undefined &&
                (fullModel.listaNss[i].tipoNss.certificador === true || fullModel.listaNss[i].tipoNss.certificador === "true")

                ) {
          aux++;
        }
      }
    }

    return  aux === 1;
  });

  /*
   * El Responsable deberá especificar qué tipo de NSS le corresponde a cada uno de los NSS
   * involucrados en la Solicitud, en donde las opciones son las siguientes:
   * -Certificador.
   * -Asociado al certificador.
   * -Corresponde a otra persona.
   * -No existe en CANASE.
   * En la Solicitud siempre debe existir únicamente un NSS del tipo Certificador.
   */
  $.validator.addMethod("RN037", function (value, element, params) {
    var i, aux;
    aux = 0;
    var fullModel = that.module.controller.model.correccionDatos;
    for (i = 0; i < fullModel.listaNss.length; i++) {
      if (fullModel.listaNss[i].tipoNss !== undefined && (fullModel.listaNss[i].tipoNss.certificador === true || fullModel.listaNss[i].tipoNss.certificador === "true")) {
        aux++;
      }
    }

    return  aux === 1;
  });

};
CorreccionDatosModel.prototype.getDisabledTipoNss = function (model, currentIndex) {
 if(this.module.controller.model.correspondeUsuarioSolicitud === true){
  if (model.listaNss[currentIndex].tipoNss !== undefined &&
          (model.listaNss[currentIndex].tipoNss.certificador === true || model.listaNss[currentIndex].tipoNss.certificador === "true")) {
    /* model.listaNss[currentIndex].tipoNss.asociado = false;
     model.listaNss[currentIndex].tipoNss.corresOtraPersona = false;
     model.listaNss[currentIndex].tipoNss.noExisteCanase = false;*/

    return {certificador: false, asociado: false, corresOtraPersona: false, noExisteCanase: false};
  } else {
    var i, aux;
    aux = 0;
    for (i = 0; i < model.listaNss.length; i++) {
      if (model.listaNss[i].tipoNss !== undefined && (model.listaNss[i].tipoNss.certificador === true || model.listaNss[i].tipoNss.certificador === "true")) {
        aux++;
      }
    }
    if (aux > 0 || model.listaNss[currentIndex].convencional === true || model.listaNss[currentIndex].convencional === "true") {
      return {certificador: true, asociado: false, corresOtraPersona: false, noExisteCanase: false};
    }
    return {certificador: false, asociado: false, corresOtraPersona: false, noExisteCanase: false};
  }
 }
 else{
	 return {certificador: true, asociado: true, corresOtraPersona: true, noExisteCanase: true};
 }
};

CorreccionDatosModel.prototype.getDisabledTipoAclaracion = function (model, currentIndex) {
// console.log(model.listaNss[currentIndex].tipoNss);
 if(this.module.controller.model.correspondeUsuarioSolicitud === true){	 
  if (model.listaNss[currentIndex].tipoNss !== undefined) {
	  	 
    if (model.listaNss[currentIndex].tipoNss.noExisteCanase === true || model.listaNss[currentIndex].tipoNss.noExisteCanase === "true") {
      model.listaNss[currentIndex].tipoAclaracion.canceladoDup = false;
      model.listaNss[currentIndex].tipoAclaracion.homonimio = false;
      model.listaNss[currentIndex].tipoAclaracion.otroAsegurado = false;
      model.listaNss[currentIndex].tipoAclaracion.correccionNombre = false;
      model.listaNss[currentIndex].tipoAclaracion.correccionEstadis = false;
	  model.listaNss[currentIndex].tipoAclaracion.cuentaIlogica = false;
	  model.listaNss[currentIndex].tipoAclaracion.cuentaIndividual = false;
	  model.listaNss[currentIndex].tipoAclaracion.noExisteCanase = true;
      return {canceladoDup: true, homonimio: true, noExisteCanase: true, otroAsegurado: true, correccionNombre: true, correccionEstadis: true, cuentaIlogica: true, cuentaIndividual:true};
    }
    if (model.listaNss[currentIndex].tipoNss.certificador === true || model.listaNss[currentIndex].tipoNss.certificador === "true") {
      model.listaNss[currentIndex].tipoAclaracion.canceladoDup = false;
      model.listaNss[currentIndex].tipoAclaracion.homonimio = false;
      model.listaNss[currentIndex].tipoAclaracion.noExisteCanase = false;
      model.listaNss[currentIndex].tipoAclaracion.otroAsegurado = false;
	  model.listaNss[currentIndex].tipoAclaracion.cuentaIndividual = false;
      return {canceladoDup: true, homonimio: true, noExisteCanase: true, otroAsegurado: true, correccionNombre: false, correccionEstadis: false, cuentaIlogica: (module.controller.model.consultaSolicitud.bloqueoCuentaIlogia?false:true) , cuentaIndividual:true};
    }
    if (model.listaNss[currentIndex].tipoNss.asociado === true || model.listaNss[currentIndex].tipoNss.asociado === "true") {
	  model.listaNss[currentIndex].tipoAclaracion.canceladoDup = true;
      model.listaNss[currentIndex].tipoAclaracion.homonimio = false;
      model.listaNss[currentIndex].tipoAclaracion.noExisteCanase = false;
      model.listaNss[currentIndex].tipoAclaracion.otroAsegurado = false;
	  model.listaNss[currentIndex].tipoAclaracion.cuentaIlogica = false;
	  model.listaNss[currentIndex].tipoAclaracion.cuentaIndividual = false;
      return {canceladoDup: true, homonimio: true, noExisteCanase: true, otroAsegurado: true, correccionNombre: false, correccionEstadis: false, cuentaIlogica: true, cuentaIndividual:true};
    }
    if (model.listaNss[currentIndex].tipoNss.corresOtraPersona === true || model.listaNss[currentIndex].tipoNss.corresOtraPersona === "true") {
      model.listaNss[currentIndex].tipoAclaracion.canceladoDup = false;
      model.listaNss[currentIndex].tipoAclaracion.noExisteCanase = false;
      model.listaNss[currentIndex].tipoAclaracion.correccionNombre = false;
      model.listaNss[currentIndex].tipoAclaracion.correccionEstadis = false;
	  model.listaNss[currentIndex].tipoAclaracion.cuentaIlogica = false;
//	  model.listaNss[currentIndex].tipoAclaracion.cuentaIndividual = false;
      return {canceladoDup: true, homonimio: false, noExisteCanase: true, otroAsegurado: false, correccionNombre: true, correccionEstadis: true, cuentaIlogica: true, cuentaIndividual:true};
    }
    
  }
  return {canceladoDup: false, homonimio: false, noExisteCanase: false, otroAsegurado: false, correccionNombre: false, correccionEstadis: false, cuentaIlogica: false, cuentaIndividual:true};
 }
 else{
 return {canceladoDup: true, homonimio: true, noExisteCanase: true, otroAsegurado: true, correccionNombre: true, correccionEstadis: true, cuentaIlogica: true,  cuentaIndividual: true};
 }
};


CorreccionDatosModel.prototype.init = function () {
  this.entities = [
    {name: "tipoNss",
      fields: [
        {name: "certificador", domain: "certificador"}
      ]
    }
  ];
  this.domains = [
    {name: "certificador",
      rules: [
        {type: "Custom", name: "RN037", message: "Debe seleccionar un certificador"}
      ]
    }
  ];
};
