function ConfirmarCorreccionDatosService(module) {
  this.module = module;  
  this.COMPLETE = "atencionResponsable/cuentaIndividual/complete.do";
}
ConfirmarCorreccionDatosService.prototype.save = function (module) { 


  //module.controller.next("confirmarCorreccionDatos");controller.model.correccionDatos.listaNss
  var model = module.controller.model.correccionDatos;
  
  var validacion = 0;
	        if((model.listaNss[0].tipoAclaracion.correccionNombre === true || model.listaNss[0].tipoAclaracion.correccionNombre === 'true') 
				&& model.listaNss.length == 1 || (model.listaNss[0].tipoAclaracion.correccionEstadis === true || model.listaNss[0].tipoAclaracion.correccionEstadis=== 'true') && model.listaNss.length == 1 )
			{
				//module.controller.next("confirmarCorreccionDatos");
				//this.render.module.service.confirmarCorreccionDatosService.complete('correccionDatos');
				if((model.listaNss[0].tipoAclaracion.cuentaIlogica === false || model.listaNss[0].tipoAclaracion.cuentaIlogica === 'false')||
				(model.listaNss[0].tipoAclaracion.cuentaIndividual === false || model.listaNss[0].tipoAclaracion.cuentaIndividual === 'false'))
				{
						this.complete('confirmarCorreccionDatos');
				}
				else 
				{
					module.controller.next("confirmarCorreccionDatos");
					
				}
				
				
			}
			else {
				for(var i=0 ; i< model.listaNss.length ; i++)
				{
					if((model.listaNss[i].tipoAclaracion.correccionNombre=== true 
					|| model.listaNss[i].tipoAclaracion.correccionNombre === 'true'
					|| model.listaNss[i].tipoAclaracion.correccionEstadis === true 
					|| model.listaNss[i].tipoAclaracion.correccionEstadis === 'true'
					|| model.listaNss[i].tipoNss.noExisteCanase === true 
					|| model.listaNss[i].tipoNss.noExisteCanase === 'true'
					|| model.listaNss[i].tipoAclaracion.canceladoDup === true 
					|| model.listaNss[i].tipoAclaracion.canceladoDup === 'true') &&
					(model.listaNss[i].tipoAclaracion.cuentaIlogica === 'false' || model.listaNss[i].tipoAclaracion.cuentaIlogica === false))
					{
						validacion = validacion + 1;
						if(validacion === model.listaNss.length)
						{
							//this.render.module.service.confirmarCorreccionDatosService.complete();
							//module.controller.next("confirmarCorreccionDatos");
							//ConfirmarCorreccionDatosService.complete('confirmarCorreccionDatos');
							this.complete('confirmarCorreccionDatos');
							break;
						}
					}
				else if(((model.listaNss[i].tipoAclaracion.canceladoDup === "false" || model.listaNss[i].tipoAclaracion.canceladoDup === false )&&
						(model.listaNss[i].tipoAclaracion.correccionEstadis === "false" || model.listaNss[i].tipoAclaracion.correccionEstadis === false )&&
						(model.listaNss[i].tipoAclaracion.correccionNombre === "false" || model.listaNss[i].tipoAclaracion.correccionNombre === false )&&
						(model.listaNss[i].tipoAclaracion.homonimio === "false" || model.listaNss[i].tipoAclaracion.homonimio === false )&&
						(model.listaNss[i].tipoAclaracion.noExisteCanase === "false" || model.listaNss[i].tipoAclaracion.noExisteCanase === false )&&
						(model.listaNss[i].tipoAclaracion.otroAsegurado === "false" || model.listaNss[i].tipoAclaracion.otroAsegurado === false )&&
						(model.listaNss[i].tipoAclaracion.cuentaIlogica === "false" || model.listaNss[i].tipoAclaracion.cuentaIlogica === false )&&
						(model.listaNss[i].tipoAclaracion.cuentaIndividual === "false" || model.listaNss[i].tipoAclaracion.cuentaIndividual === false ))
						&& (model.listaNss[i].tipoNss.certificador=== true || model.listaNss[i].tipoNss.certificador=== 'true'))
						{
							validacion = validacion + 1;
						if(validacion === model.listaNss.length)
						{
							
							this.complete('confirmarCorreccionDatos');
							break;
						}
						
					}
				else{
					module.controller.next("confirmarCorreccionDatos");
						break;
				}					
  
  
  
}
}};



ConfirmarCorreccionDatosService.prototype.compareToLugarNacimiento = function (model, renapo, data, dato, origenInformacion, modelLugarnacimiento, renapoLugarNacimiento) { 
  if ( !this.customCompare ( ""+ parseInt(renapo) , ""+ parseInt(model) ) ) {
    data[data.length] = {origenInformacion: origenInformacion,
      dato: dato, informacionIMSS: modelLugarnacimiento,
      informacionActualizacion: renapoLugarNacimiento,
      estatus: "", fechaProceso: ""
    };
  }
};

ConfirmarCorreccionDatosService.prototype.compareTo = function (model, renapo, data, dato, origenInformacion) { 
  if ( !this.customCompare ( renapo , model ) ) {
    data[data.length] = {origenInformacion: origenInformacion,
      dato: dato, informacionIMSS: model,
      informacionActualizacion: renapo,
      estatus: "", fechaProceso: ""
    };
  }
};
ConfirmarCorreccionDatosService.prototype.armaDetalle = function (model) {
  var detalle = "";
  if( !this.module.render.isEmpty( model.tipoAclaracion ) ){    
      if( model.tipoAclaracion.canceladoDup === "true" ||  model.tipoAclaracion.canceladoDup === true){
        detalle += definicion[2] + " ";//"Cancelado por duplicidad,";
      }
      console.log( model.tipoAclaracion.correccionEstadis );
      if( model.tipoAclaracion.correccionEstadis === "true" || model.tipoAclaracion.correccionEstadis === true){
        detalle += definicion[1] + " ";//"Correcci\u00f3n de datos estad\u00EDsticos, ";
      }
      if( model.tipoAclaracion.correccionNombre === "true" || model.tipoAclaracion.correccionNombre === true){
        detalle += definicion[0]+ " ";//"Correcci\u00f3n de nombre, ";
      }
      if( model.tipoAclaracion.homonimio === "true" || model.tipoAclaracion.homonimio === true){
        detalle += definicion[3]+ " ";// "Corresponde a un hom\u00f3nimo, ";
      }
      if( model.tipoAclaracion.noExisteCanase === "true" ||  model.tipoAclaracion.noExisteCanase === true){
        detalle += definicion[5]+ " ";// "No existe en CANASE, ";
      }
      if( model.tipoAclaracion.otroAsegurado === "true" || model.tipoAclaracion.otroAsegurado === true){
        detalle += definicion[4]+ " ";//"Corresponde a otro asegurado, ";
      }
	  if( model.tipoAclaracion.blanqueoCurp === "true" || model.tipoAclaracion.blanqueoCurp === true){
        detalle += definicion[6]+ " ";//Blanqueamientode curp;
      }
	  if( model.tipoAclaracion.cuentaIlogica === "true" || model.tipoAclaracion.cuentaIlogica === true){
        detalle += definicion[7]+ " ";//Cuenta ilogica;
      }
      if( detalle.length > 0){ 
        //detalle = detalle.substring( 0, detalle.length-2 );
        detalle += ".";
      }
    }
    return detalle;
};
ConfirmarCorreccionDatosService.prototype.fetch = function (module,model, componentId) {

  var confirmarCorreccionDatos = {};
  var correccionDatos = model;
  var consultaSolicitud = this.module.controller.model.consultaSolicitud;
  console.log(correccionDatos);
  confirmarCorreccionDatos.renapo = correccionDatos.renapo;

  confirmarCorreccionDatos.solicitud = {
    folio: consultaSolicitud.folio,
//    tipoTramite: "CORRECCI\u00d3N DE DATOS DEL ASEGURADO"};
    tipoTramite: consultaSolicitud.tipoTramite };

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
    {dato: "Lugar de nacimiento", model: "idlugarNacimiento"},
    {dato: "Nacionalidad", model: "nacionalidad"},
    {dato: "Datos doc. prob.", model: "datosDocumentoProbatorio"}
  ];
  var renapo = correccionDatos.renapo;
  var i, j, k;
  // EncontrR CERTIFICADOR
  for (i = 0; i < correccionDatos.listaNss.length; i++) {    
    var model = correccionDatos.listaNss[i];
    console.log( model );
	if(model.canase){
	if(model.canase.sexo === 'Mujer'){
		model.canase.sexo = 'MUJER';
	}else if(model.canase.sexo === 'Hombre'){
		model.canase.sexo = 'HOMBRE';
	}else if (model.canase.sexo === 'No Binario') {
	    model.canase.sexo = 'NO BINARIO';
    }
	
	if(model.canase.lugarNacimiento === 'CIUDAD DE MÉXICO' )
	{
	  model.canase.lugarNacimiento ='DISTRITO FEDERAL';
	}	 
	
		model.canase.nacionalidad = confirmarCorreccionDatos.renapo.nacionalidad;
		model.canase.datosDocumentoProbatorio = confirmarCorreccionDatos.renapo.datosDocumentoProbatorio;	
	}
	
	if(model.bdtu){
	if(model.bdtu.sexo === 'Mujer'){
		model.bdtu.sexo = 'MUJER';
	}else if(model.bdtu.sexo === 'Hombre'){
		model.bdtu.sexo = 'HOMBRE';
	}else if (model.canase.sexo === 'No Binario') {
        model.canase.sexo = 'NO BINARIO';
    }

	if(model.bdtu.lugarNacimiento === 'CIUDAD DE MÉXICO' )
	{
	  model.bdtu.lugarNacimiento ='DISTRITO FEDERAL';
	}	 
	}
	
	if(model.cizUno){
	if(model.cizUno.sexo === 'Mujer'){
		model.cizUno.sexo = 'MUJER';
	}else if(model.cizUno.sexo === 'Hombre'){
		model.cizUno.sexo = 'HOMBRE';
	}else if (model.canase.sexo === 'No Binario') {
        model.canase.sexo = 'NO BINARIO';
    }
	if(model.cizUno.lugarNacimiento === 'CIUDAD DE MÉXICO' )
	{
	  model.cizUno.lugarNacimiento ='DISTRITO FEDERAL';
	}	 
		
	}
	
	if(model.cizDos){
	if(model.cizDos.sexo === 'Mujer'){
		model.cizDos.sexo = 'MUJER';
	}else if(model.cizDos.sexo === 'Hombre'){
		model.cizDos.sexo = 'HOMBRE';
	}else if (model.canase.sexo === 'No Binario') {
        model.canase.sexo = 'NO BINARIO';
    }
	if(model.cizDos.lugarNacimiento === 'CIUDAD DE MÉXICO' )
	{
	  model.cizDos.lugarNacimiento ='DISTRITO FEDERAL';
	}	 
	}
	
	if(model.ciztres){
	if(model.ciztres.sexo === 'Mujer'){
		model.ciztres.sexo = 'MUJER';
	}else if(model.ciztres.sexo === 'Hombre'){
		model.ciztres.sexo = 'HOMBRE';
	}else if (model.canase.sexo === 'No Binario') {
        model.canase.sexo = 'NO BINARIO';
    }
	if(model.ciztres.lugarNacimiento === 'CIUDAD DE MÉXICO' )
	{
	  model.ciztres.lugarNacimiento ='DISTRITO FEDERAL';
	}
	
	}
	
	if(model.historico){
	if(model.historico.sexo === 'Mujer'){
		model.historico.sexo = 'MUJER';
	}else if(model.historico.sexo === 'Hombre'){
		model.historico.sexo = 'HOMBRE';
	}else if (model.canase.sexo === 'No Binario') {
        model.canase.sexo = 'NO BINARIO';
    }
	if(model.historico.lugarNacimiento === 'CIUDAD DE MÉXICO' )
	{
	  model.historico.lugarNacimiento ='DISTRITO FEDERAL';
	}
	}
		
	
	
	
	
	
	
    var detalle = this.armaDetalle(model);

    
    if ((model.tipoNss.certificador === true || model.tipoNss.certificador === "true")
			&&(model.tipoAclaracion.cuentaIlogica === false || model.tipoAclaracion.cuentaIlogica === "false"
			|| model.tipoAclaracion.correccionEstadis === true || model.tipoAclaracion.correccionEstadis === "true"
				|| model.tipoAclaracion.correccionNombre === true || model.tipoAclaracion.correccionNombre === "true" )) {
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
          console.log(">>>>"+aux1+"<<<<");
          if(aux1.length ===0){
            continue;
          }
          
          for (k = 0; k < fields.length; k++) {
            var row = model[entity.name];
            var field = fields[k].model;
			if(field === 'idlugarNacimiento')
			{
				this.compareToLugarNacimiento(row[field], renapo[field], data, fields[k].dato, entity.label , row.lugarNacimiento, renapo.lugarNacimiento);
			}
			else{
				this.compareTo(row[field], renapo[field], data, fields[k].dato, entity.label);
			}
            
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
              console.log("BASE:" + (""+row.fechaNacimiento).substring(3, 5) );
			  if(row.fechaNacimiento.length > 8){
				  this.compareTo( (""+row.fechaNacimiento).substring(3, 5), (""+renapo.fechaNacimiento).substring(3, 5), data, "Fecha de nacimiento", entity.label); 
			  } else {
              this.compareTo( (""+row.fechaNacimiento), (""+renapo.fechaNacimiento).substring(3, 5), data, "Fecha de nacimiento", entity.label);          
			  }
            }
        }
      }
      
      console.log(data);
      if (data.length > 0) {
        certificador.data = data;
        confirmarCorreccionDatos.certificador[ confirmarCorreccionDatos.certificador.length] = certificador;
      }
    }
    model.canase.nacionalidad = "";
	model.canase.datosDocumentoProbatorio = null;
  }

  // Asociado
  // EncontrR CERTIFICADOR

  for (i = 0; i < correccionDatos.listaNss.length; i++) {
    var model = correccionDatos.listaNss[i];
    console.log(model);
    var detalle = this.armaDetalle(model);
    if ((model.tipoNss.asociado === true || model.tipoNss.asociado === "true")
		&&(model.tipoAclaracion.cuentaIlogica === false || model.tipoAclaracion.cuentaIlogica === "false"
			|| model.tipoAclaracion.correccionEstadis === true || model.tipoAclaracion.correccionEstadis === "true"
				|| model.tipoAclaracion.correccionNombre === true || model.tipoAclaracion.correccionNombre === "true" 
				|| model.tipoAclaracion.canceladoDup === true || model.tipoAclaracion.canceladoDup === "true" ))  {

      var asociado = {nss: model.nss, detalle: detalle};
      // canase
      if (!this.module.render.isEmpty(model.canase)) {
        console.log(model.canase);
		if(!this.module.render.isEmpty(model.canase.fechaNacimiento) && model.canase.fechaNacimiento.length > 8) {
			model.canase.fechaNacimiento = model.canase.fechaNacimiento.substring(3, 5);	
			model.canase.nacionalidad=renapo.nacionalidad;
			model.canase.datosDocumentoProbatorio=renapo.datosDocumentoProbatorio;			
		}
		var sexocubeta = model.canase.sexo;
		if(sexocubeta != undefined){
			model.canase.sexo =  sexocubeta.toUpperCase();
			model.canase.nacionalidad=renapo.nacionalidad;
			model.canase.datosDocumentoProbatorio=renapo.datosDocumentoProbatorio;		
			
		}
        if ( !this.customCompare( renapo.nombre , model.canase.nombre )
                || !this.customCompare( renapo.apellidoPaterno , model.canase.apellidoPaterno )
                || !this.customCompare( renapo.apellidoMaterno , model.canase.apellidoMaterno )
                || !this.customCompare( renapo.apellidoMaterno , model.canase.apellidoMaterno )
                || !this.customCompare( renapo.curp , model.canase.curp )
                || !this.customCompare( renapo.sexo ,model.canase.sexo )
                || !this.customCompare( renapo.fechaNacimiento.substring(3, 5) , model.canase.fechaNacimiento )
                || !this.customCompare( renapo.lugarNacimiento , model.canase.lugarNacimiento )
                || !this.customCompare( renapo.nacionalidad , model.canase.nacionalidad )
                || !this.customCompare( renapo.datosDocumentoProbatorio , model.canase.datosDocumentoProbatorio ) ) {
          asociado.canase = model.canase;
        }
      }

      if (!this.module.render.isEmpty(model.bdtu)) {
        console.log(model.bdtu);
        var sexocubetabdtu = model.bdtu.sexo;
		if(sexocubetabdtu != undefined){
			model.bdtu.sexo =  sexocubetabdtu.toUpperCase();
			model.bdtu.nacionalidad=renapo.nacionalidad;
			model.bdtu.datosDocumentoProbatorio=renapo.datosDocumentoProbatorio;
		}
        if (renapo.nombre !== model.bdtu.nombre
                || renapo.apellidoPaterno !== model.bdtu.apellidoPaterno
                || renapo.apellidoMaterno !== model.bdtu.apellidoMaterno
                || renapo.apellidoMaterno !== model.bdtu.apellidoMaterno
                || renapo.curp !== model.bdtu.curp
                || renapo.sexo !==  model.bdtu.sexo
                || renapo.fechaNacimiento !== model.bdtu.fechaNacimiento
                || renapo.lugarNacimiento !== model.bdtu.lugarNacimiento
                || renapo.nacionalidad !== model.bdtu.nacionalidad
                || renapo.datosDocumentoProbatorio !== model.bdtu.datosDocumentoProbatorio) {
          asociado.bdtu = model.bdtu;
        }
		
        if (!this.module.render.isEmpty(model.cizUno)) {
			if(!this.module.render.isEmpty(model.cizUno.fechaNacimiento) && model.cizUno.fechaNacimiento.length > 8) {
			model.cizUno.fechaNacimiento = model.cizUno.fechaNacimiento.substring(3, 5);	
			model.cizUno.nacionalidad=renapo.nacionalidad;
			model.cizUno.datosDocumentoProbatorio=renapo.datosDocumentoProbatorio;				
		}
          console.log(model.cizUno);         
          
          var sexocubetacizUno = model.cizUno.sexo;
		  if(sexocubetacizUno != undefined){
			model.cizUno.sexo =  sexocubetacizUno.toUpperCase();
		  }
          if ( !this.customCompare( renapo.nombre , model.cizUno.nombre )
                  || !this.customCompare( renapo.apellidoPaterno , model.cizUno.apellidoPaterno )
                  || !this.customCompare( renapo.apellidoMaterno , model.cizUno.apellidoMaterno )
                  || !this.customCompare( renapo.apellidoMaterno , model.cizUno.apellidoMaterno )
                  || !this.customCompare( renapo.curp , model.cizUno.curp )
                  || !this.customCompare( renapo.sexo , model.cizUno.sexo)
                  || !this.customCompare( renapo.fechaNacimiento.substring(3, 5) , model.cizUno.fechaNacimiento )
                  || !this.customCompare( renapo.lugarNacimiento , model.cizUno.lugarNacimiento )
                  || !this.customCompare( renapo.nacionalidad , model.cizUno.nacionalidad )
                  || !this.customCompare( renapo.datosDocumentoProbatorio , model.cizUno.datosDocumentoProbatorio) ) {
            asociado.cizUno = model.cizUno;
          }
        }

        if (!this.module.render.isEmpty(model.cizDos)) {
			if(!this.module.render.isEmpty(model.cizDos.fechaNacimiento) && model.cizDos.fechaNacimiento.length > 8) {
			model.cizDos.fechaNacimiento = model.cizDos.fechaNacimiento.substring(3, 5);
			model.cizDos.nacionalidad=renapo.nacionalidad;
			model.cizDos.datosDocumentoProbatorio=renapo.datosDocumentoProbatorio;	
			
		}
          console.log(model.cizDos);
          var sexocubetacizDos = model.cizDos.sexo;
		  if(sexocubetacizDos != undefined){
          model.cizDos.sexo =  sexocubetacizDos.toUpperCase();
		  }
          if ( !this.customCompare( renapo.nombre , model.cizDos.nombre )
                  || !this.customCompare( renapo.apellidoPaterno , model.cizDos.apellidoPaterno )
                  || !this.customCompare( renapo.apellidoMaterno , model.cizDos.apellidoMaterno )
                  || !this.customCompare( renapo.apellidoMaterno , model.cizDos.apellidoMaterno )
                  || !this.customCompare( renapo.curp , model.cizDos.curp )
                  || !this.customCompare( renapo.sexo , model.cizDos.sexo )
                  || !this.customCompare( renapo.fechaNacimiento.substring(3, 5) , model.cizDos.fechaNacimiento )
                  || !this.customCompare( renapo.lugarNacimiento , model.cizDos.lugarNacimiento )
                  || !this.customCompare( renapo.nacionalidad , model.cizDos.nacionalidad )
                  || !this.customCompare( renapo.datosDocumentoProbatorio , model.cizDos.datosDocumentoProbatorio) ){
            asociado.cizDos = model.cizDos;
          }
        }


        if (!this.module.render.isEmpty(model.ciztres)) {
			if(!this.module.render.isEmpty(model.ciztres.fechaNacimiento) && model.ciztres.fechaNacimiento.length > 8) {
				model.ciztres.fechaNacimiento = model.ciztres.fechaNacimiento.substring(3, 5);			
			}
          console.log(model.ciztres);
          var sexocubetaciztres =  model.ciztres.sexo;
		  if(sexocubetaciztres != undefined){
			model.ciztres.sexo =  sexocubetaciztres.toUpperCase();
		  }
          if ( !this.customCompare( renapo.nombre , model.ciztres.nombre )
                  || !this.customCompare( renapo.apellidoPaterno , model.ciztres.apellidoPaterno )
                  || !this.customCompare( renapo.apellidoMaterno , model.ciztres.apellidoMaterno )
                  || !this.customCompare( renapo.apellidoMaterno , model.ciztres.apellidoMaterno )
                  || !this.customCompare( renapo.curp , model.ciztres.curp )
                  || !this.customCompare( renapo.sexo , model.ciztres.sexo)
                  || !this.customCompare( renapo.fechaNacimiento.substring(3, 5) , model.ciztres.fechaNacimiento )
                  || !this.customCompare( renapo.lugarNacimiento , model.ciztres.lugarNacimiento )
                  || !this.customCompare( renapo.nacionalidad , model.ciztres.nacionalidad )
                  || !this.customCompare( renapo.datosDocumentoProbatorio , model.ciztres.datosDocumentoProbatorio )) {
            asociado.ciztres = model.ciztres;
          }
        }


        if (!this.module.render.isEmpty(model.historico)) {
			if(!this.module.render.isEmpty(model.historico.fechaNacimiento) && model.historico.fechaNacimiento.length > 8) {
			model.historico.fechaNacimiento = model.historico.fechaNacimiento.substring(3, 5);			
		}
          console.log(model.historico);
		 
          var sexohistorico = model.historico.sexo ;
		   if(sexohistorico != undefined){
			   model.historico.sexo =  sexohistorico.toUpperCase();
		  }
          
          if (  !this.customCompare( renapo.nombre , model.historico.nombre )
                  || !this.customCompare( renapo.apellidoPaterno , model.historico.apellidoPaterno )
                  || !this.customCompare( renapo.apellidoMaterno , model.historico.apellidoMaterno )
                  || !this.customCompare( renapo.apellidoMaterno , model.historico.apellidoMaterno )
                  || !this.customCompare( renapo.curp , model.historico.curp )
                  || !this.customCompare( renapo.sexo , model.historico.sexo )
                  || !this.customCompare( renapo.fechaNacimiento.substring(3, 5) , model.historico.fechaNacimiento )
                  || !this.customCompare( renapo.lugarNacimiento , model.historico.lugarNacimiento )
                  || !this.customCompare( renapo.nacionalidad , model.historico.nacionalidad )
                  || !this.customCompare( renapo.datosDocumentoProbatorio , model.historico.datosDocumentoProbatorio) ){
            asociado.historico = model.historico;
          }
        }
      }


      console.log(asociado);

      if (asociado.canase !== undefined
              || asociado.bdtu !== undefined
              || asociado.ciZUno !== undefined
              || asociado.cizDos !== undefined
              || asociado.cizTres !== undefined
              || asociado.historico !== undefined) {
        console.log(asociado);

        confirmarCorreccionDatos.asociado[confirmarCorreccionDatos.asociado.length] = asociado;

      }
    }
  }


  // Asociado
  // EncontrR CERTIFICADOR

  for (i = 0; i < correccionDatos.listaNss.length; i++) {
    var model = correccionDatos.listaNss[i];
    console.log(model);
    var detalle = this.armaDetalle(model);
    if ((model.tipoNss.corresOtraPersona === true || model.tipoNss.corresOtraPersona === "true")&&(model.tipoAclaracion.cuentaIlogica === false || model.tipoAclaracion.cuentaIlogica === "false")) {
      var corresOtraPersona = {nss: model.nss, detalle: detalle };
      confirmarCorreccionDatos.noCorresponde[confirmarCorreccionDatos.noCorresponde.length] = corresOtraPersona;
    }

  }



  module.updateModel("ConfirmarCorreccionDatosComponent", componentId, confirmarCorreccionDatos);

};


ConfirmarCorreccionDatosService.prototype.complete = function( componentId ) {
  var url = this.COMPLETE;
  var module = this.module;
  var params = this.module.controller.model[componentId];
  params.solicitud.folioSolicitud = params.solicitud.folio;
  params =params.solicitud;
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
        module.updateModel("LabelComponent", "labelM02_022",
        "Se ha registrado exitosamente la solicitud " + data.folioSolicitud + " con los cambios por aplicar y se ha enviado  para autorizaci\u00f3n." 
        );
        $("#modalFinResponsable").modal();
        
        
        //document.location.reload();
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

// Comparar con los #
ConfirmarCorreccionDatosService.prototype.customCompare = function (renapo, model) {
  if(model==='Hombre' || model==='Mujer' || model==='No Binario'){
      model = model.toUpperCase();
  }
  return !this.module.render.isEmpty( model ) && renapo === model.replace(/#/g, '\u00D1');
};
