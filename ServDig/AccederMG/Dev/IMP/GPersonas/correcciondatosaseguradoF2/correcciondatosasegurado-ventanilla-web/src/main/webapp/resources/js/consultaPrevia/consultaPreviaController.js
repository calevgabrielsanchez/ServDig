function ConsultaPreviaController(module) {
    this.module = module;
	
}

ConsultaPreviaController.prototype.siguienteCorreccion = function(model){
	if(origendbtab==undefined)
		{
			origendbtab ='canase';
		}
	if(origendbtab.fuente == undefined )
	{
		origenDB = (origendbtab).toUpperCase(); 
	}
	else {origenDB = (origendbtab.fuente).toUpperCase(); }
	
	var nombredat=$('#informacion'+origenDB+'_nombre').val();
	var apellidoPdata=$('#informacion'+origenDB+'_apellidoPaterno').val();
	var apellidoMdata=$('#informacion'+origenDB+'_apellidoMaterno').val();
	var curpdata=$('#informacion'+origenDB+'_curp').val();
	var sexodata=$('#informacion'+origenDB+'_sexo').val();
	var fechaNacimientodata=$('#informacion'+origenDB+'_fechaNacimiento').val();
	var lugarNacimientodata=$('#informacion'+origenDB+'_lugarNacimiento').val();
	var nacionalidaddata=$('#informacion'+origenDB+'_nacionalidad').val();
	var documentosdata=$('#informacion'+origenDB+'_datosDocumentoProbatorio').val();
	//Checkbox
	var noCanasee =origendbtab.NoCanasee;
	var asociadoCertificado = origendbtab.asociadoCertificado;
	var canceladoDuplicidad = origendbtab.canceladoDuplicidad;
	var carrecEstad = origendbtab.carrecEstad;
	var certificado = origendbtab.certificado;
	var correcNombre = origendbtab.correcNombre;
	var fuente = origendbtab.correcNombre;
	var homonimo = origendbtab.homonimo;
	var nocanase = origendbtab.nocanase;
	var otraPersona = origendbtab.otraPersona;
	var otroAsegu = origendbtab.otroAsegu;
	//idTramite
	
	
	datosModificados = new Object();
	datosModificados.curp = $('#informacion'+origenDB+'_curp').val();
	datosModificados.apellidoPaterno = $('#informacion'+origenDB+'_apellidoPaterno').val();
	datosModificados.apellidoMaterno = $('#informacion'+origenDB+'_apellidoMaterno').val();
	datosModificados.nombre = $('#informacion'+origenDB+'_nombre').val();
	datosModificados.sexo = $('#informacion'+origenDB+'_sexo').val();
	datosModificados.fechaNacimiento = $('#informacion'+origenDB+'_fechaNacimiento').val();
	datosModificados.lugarNacimiento = $('#informacion'+origenDB+'_lugarNacimiento').val();
	datosModificados.nacionalidad = $('#informacion'+origenDB+'_nacionalidad').val();
	datosModificados.documentos = $('#informacion'+origenDB+'_datosDocumentoProbatorio').val();
	datosModificados.pertenecebd = origenDB;
	
	if($('#slide1:checked').val()!=undefined)
		{
		 datosModificados.tipoNSS = "certificador";
		}
	else if ($('#slide2:checked').val()!=undefined)
		{
			datosModificados.tipoNSS = "asociadoCertificador";
		}
	else if ($('#slide3:checked').val()!=undefined)
		{
			datosModificados.tipoNSS = "otraPersona";
		}
	
	
	datosCDAModificado = [];
	datosCDAModificado.push(datosModificados);
	
	
	var idTramitePrincipal = idTramite;
	eval({nombredat:nombredat,apellidoPdata:apellidoPdata,apellidoMdata:apellidoMdata,
		curpdata:curpdata,sexodata:sexodata,fechaNacimientodata:fechaNacimientodata,lugarNacimientodata:lugarNacimientodata,
		nacionalidaddata:nacionalidaddata,documentosdata:documentosdata,noCanasee:noCanasee,asociadoCertificado:asociadoCertificado,
		canceladoDuplicidad:canceladoDuplicidad,carrecEstad:carrecEstad,certificado:certificado,correcNombre:correcNombre,
		fuente:fuente,homonimo:homonimo,nocanase:nocanase,otraPersona:otraPersona,otroAsegu:otroAsegu,origenDB:origenDB,
		idTramitePrincipal:idTramitePrincipal})
	
	model.resumenCorreccion = model.consultaSolicitud.gridsNSS;
//    this.module.render.notify("ResumenCorreccionComponent", "resumenCorreccion", "resumenCorreccion");
//    $("#modal").modal('show');
	
	 
    
    
};





ConsultaPreviaController.prototype.siguienteDetalleCorreccion = function(model){
	cuentaIndividualPrevia = module.controller.model.consultaSolicitud.cuentaIndividualPrevia.data;
	cuentaIndividual = module.controller.model.consultaSolicitud.cuentaIndividual.data;
	detalleCuentaIndividual = new Object();
	for(var dato=0; dato<cuentaIndividualPrevia.length;dato++ )
   	{
   		
   		if(cuentaIndividualPrevia[dato].fechaRecepcionMovimiento != cuentaIndividual[dato].fechaRecepcionMovimiento){
   			detalleCuentaIndividual.origen = "CIZ"+cuentaIndividualPrevia[dato].claveCiz;
   	   		detalleCuentaIndividual.fechaProceso = cuentaIndividualPrevia[dato].fechaCarga;
   			detalleCuentaIndividual.dato = "Recep Mov Ini";
   			detalleCuentaIndividual.infoPrevia = cuentaIndividualPrevia[dato].fechaRecepcionMovimiento;
   			detalleCuentaIndividual.infoRegularizada = cuentaIndividual[dato].fechaRecepcionMovimiento;

   		}

   		if(cuentaIndividualPrevia[dato].tipoMovimientoIniintcial !=cuentaIndividual[dato].tipoMovimientoIniintcial){
   			detalleCuentaIndividual.origen = "CIZ"+cuentaIndividualPrevia[dato].claveCiz;
   	   		detalleCuentaIndividual.fechaProceso = cuentaIndividualPrevia[dato].fechaCarga;
   			detalleCuentaIndividual.dato = "Mov Ini";
   			detalleCuentaIndividual.infoPrevia = cuentaIndividualPrevia[dato].tipoMovimientoIniintcial;
   			detalleCuentaIndividual.infoRegularizada = cuentaIndividual[dato].tipoMovimientoIniintcial;
   		}


   		if(cuentaIndividualPrevia[dato].origenMovimientoInicial != cuentaIndividual[dato].origenMovimientoInicial){
   			detalleCuentaIndividual.origen = "CIZ"+cuentaIndividualPrevia[dato].claveCiz;
   	   		detalleCuentaIndividual.fechaProceso = cuentaIndividualPrevia[dato].fechaCarga;
   			detalleCuentaIndividual.dato = "Ori Mov";
   			detalleCuentaIndividual.infoPrevia = cuentaIndividualPrevia[dato].origenMovimientoInicial;
   			detalleCuentaIndividual.infoRegularizada = cuentaIndividual[dato].origenMovimientoInicial;
   		}


   		if(cuentaIndividualPrevia[dato].fechaInicioMovimiento != cuentaIndividual[dato].fechaInicioMovimiento){
   			detalleCuentaIndividual.origen = "CIZ"+cuentaIndividualPrevia[dato].claveCiz;
   	   		detalleCuentaIndividual.fechaProceso = cuentaIndividualPrevia[dato].fechaCarga;
   			detalleCuentaIndividual.dato = "Fec Mov Ini";
   			detalleCuentaIndividual.infoPrevia = cuentaIndividualPrevia[dato].fechaInicioMovimiento;
   			detalleCuentaIndividual.infoRegularizada = cuentaIndividual[dato].fechaInicioMovimiento;
   		}

   		if(cuentaIndividualPrevia[dato].salarioBase != cuentaIndividual[dato].salarioBase){
   			detalleCuentaIndividual.origen = "CIZ"+cuentaIndividualPrevia[dato].claveCiz;
   	   		detalleCuentaIndividual.fechaProceso = cuentaIndividualPrevia[dato].fechaCarga;
   			detalleCuentaIndividual.dato = "SBC";
   			detalleCuentaIndividual.infoPrevia = cuentaIndividualPrevia[dato].salarioBase;
   			detalleCuentaIndividual.infoRegularizada = cuentaIndividual[dato].salarioBase;
   		}


   		if(cuentaIndividualPrevia[dato].tipoSalario != cuentaIndividual[dato].tipoSalario){
   			detalleCuentaIndividual.origen = "CIZ"+cuentaIndividualPrevia[dato].claveCiz;
   	   		detalleCuentaIndividual.fechaProceso = cuentaIndividualPrevia[dato].fechaCarga;
   			detalleCuentaIndividual.dato = "T SBC";
   			detalleCuentaIndividual.infoPrevia = cuentaIndividualPrevia[dato].tipoSalario;
   			detalleCuentaIndividual.infoRegularizada = cuentaIndividual[dato].tipoSalario;
   		}


   		if(cuentaIndividualPrevia[dato].eventual != cuentaIndividual[dato].eventual){
   			detalleCuentaIndividual.origen = "CIZ"+cuentaIndividualPrevia[dato].claveCiz;
   	   		detalleCuentaIndividual.fechaProceso = cuentaIndividualPrevia[dato].fechaCarga;
   			detalleCuentaIndividual.dato = "TT";
   			detalleCuentaIndividual.infoPrevia = cuentaIndividualPrevia[dato].eventual;
   			detalleCuentaIndividual.infoRegularizada = cuentaIndividual[dato].eventual;
   		}


   		if(cuentaIndividualPrevia[dato].extemporaneoConvenioSuspencion != cuentaIndividual[dato].extemporaneoConvenioSuspencion){
   			detalleCuentaIndividual.origen = "CIZ"+cuentaIndividualPrevia[dato].claveCiz;
   	   		detalleCuentaIndividual.fechaProceso = cuentaIndividualPrevia[dato].fechaCarga;
   			detalleCuentaIndividual.dato = "EXT";
   			detalleCuentaIndividual.infoPrevia = cuentaIndividualPrevia[dato].extemporaneoConvenioSuspencion;
   			detalleCuentaIndividual.infoRegularizada = cuentaIndividual[dato].extemporaneoConvenioSuspencion;
   		}


   		if(cuentaIndividualPrevia[dato].subrogacionServicio != cuentaIndividual[dato].subrogacionServicio){
   			detalleCuentaIndividual.origen = "CIZ"+cuentaIndividualPrevia[dato].claveCiz;
   	   		detalleCuentaIndividual.fechaProceso = cuentaIndividualPrevia[dato].fechaCarga;
   			detalleCuentaIndividual.dato = "SS";
   			detalleCuentaIndividual.infoPrevia = cuentaIndividualPrevia[dato].subrogacionServicio;
   			detalleCuentaIndividual.infoRegularizada = cuentaIndividual[dato].subrogacionServicio;
   		}


   		if(cuentaIndividualPrevia[dato].tipoMovimientoFinal !=cuentaIndividual[dato].tipoMovimientoFinal){
   			detalleCuentaIndividual.origen = "CIZ"+cuentaIndividualPrevia[dato].claveCiz;
   	   		detalleCuentaIndividual.fechaProceso = cuentaIndividualPrevia[dato].fechaCarga;
   			detalleCuentaIndividual.dato = "Mov Fin";
   			detalleCuentaIndividual.infoPrevia = cuentaIndividualPrevia[dato].tipoMovimientoFinal;
   			detalleCuentaIndividual.infoRegularizada = cuentaIndividual[dato].tipoMovimientoFinal;
   		}



   		if(cuentaIndividualPrevia[dato].origenMovimientoFinal != cuentaIndividual[dato].origenMovimientoFinal){
   			detalleCuentaIndividual.origen = "CIZ"+cuentaIndividualPrevia[dato].claveCiz;
   	   		detalleCuentaIndividual.fechaProceso = cuentaIndividualPrevia[dato].fechaCarga;
   			detalleCuentaIndividual.dato = "Ori Mov";
   			detalleCuentaIndividual.infoPrevia = cuentaIndividualPrevia[dato].origenMovimientoFinal;
   			detalleCuentaIndividual.infoRegularizada = cuentaIndividual[dato].origenMovimientoFinal;
   		}


   		if(cuentaIndividualPrevia[dato].fechaFinalMovimiento != cuentaIndividual[dato].fechaFinalMovimiento){
   			detalleCuentaIndividual.origen = "CIZ"+cuentaIndividualPrevia[dato].claveCiz;
   	   		detalleCuentaIndividual.fechaProceso = cuentaIndividualPrevia[dato].fechaCarga;
   			detalleCuentaIndividual.dato = "Fec Mov Fin";
   			detalleCuentaIndividual.infoPrevia = cuentaIndividualPrevia[dato].fechaFinalMovimiento;
   			detalleCuentaIndividual.infoRegularizada = cuentaIndividual[dato].fechaFinalMovimiento;
   		}


   		if(cuentaIndividualPrevia[dato].numeroConsecutivoPeriodos !=cuentaIndividual[dato].numeroConsecutivoPeriodos){
   			detalleCuentaIndividual.origen = "CIZ"+cuentaIndividualPrevia[dato].claveCiz;
   	   		detalleCuentaIndividual.fechaProceso = cuentaIndividualPrevia[dato].fechaCarga;
   			detalleCuentaIndividual.dato = "Consec";
   			detalleCuentaIndividual.infoPrevia = cuentaIndividualPrevia[dato].numeroConsecutivoPeriodos;
   			detalleCuentaIndividual.infoRegularizada = cuentaIndividual[dato].numeroConsecutivoPeriodos;
   		}


   		if(cuentaIndividualPrevia[dato].nss !=cuentaIndividual[dato].nss){
   			detalleCuentaIndividual.origen = "CIZ"+cuentaIndividualPrevia[dato].claveCiz;
   	   		detalleCuentaIndividual.fechaProceso = cuentaIndividualPrevia[dato].fechaCarga;
   			detalleCuentaIndividual.dato = "NSS origen";
   			detalleCuentaIndividual.infoPrevia = cuentaIndividualPrevia[dato].nss;
   			detalleCuentaIndividual.infoRegularizada = cuentaIndividual[dato].nss;
   		}

   		cuentaIndividualDetalle=[];
   		cuentaIndividualDetalle.push(detalleCuentaIndividual);
   	}
	
    
};