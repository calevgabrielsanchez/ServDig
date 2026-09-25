/**
 * JS para el soporte del catalogo de clase.
 */


var idDataTable 	= "#dtPromocion";
var idBuscaTable    = "#dtBuscarPromociones";
var idDgNuevo 		= "#dgPromocionNuevo";
var idDgModificar 	= "#dgPromocionModificar";
var idDgBorrar 		= "#dgPromocionBorrar";
var idDgAyuda		= "#dgPromocionAyuda";
var idDgAnexoPagos 	= "#dgPromocionAnexoPagos";
var idDgBuscar		= "#dgPromocionBuscar";

// Objeto del DataTable
var oDtPromocion;
var oDtBuscaPromocion;
// Dialogos
var oDgNuevo;
var oDgBorrar;
var oDgModificar;
var oDgAyuda;
var oDgAnexoPagos;
var oDgBuscar;
var fnGeneraFolioFinal;
var fnGuardaPromocion;


/**
 * Iniciamos la configuracion de los componentes visuales de jQuery.
 */





$(document).ready(function() {
	
	jQuery.support.cors = true;
	
	
	
	$("#btnGuardarEles").click(function(e){
		if(validaPagos.form()){
			var idPago = 0;
			if(	$("input[name=radioPromoPagos]:checked").val()!=null){
				idPago = $("input[name=radioPromoPagos]:checked").val();
			}
			var folio = document.getElementById("txtFolio").value+ $('input#txtFolioAnio').val() + '/' +$('input#txtFolioNum').val() ;;
			var cve_Patron = document.getElementById("regpat").value;
			var FolioSUA = document.getElementById("txtFolioSUA").value;
			var FolioOrdenIngreso = document.getElementById("txtOrdenIngreso").value;
			var NoCredito =  document.getElementById("txtNoCredito").value;
			var fechaPago =  formateaFecha(document.getElementById("fechaPagoAnexoPagos").value);
			var COPPeriodo=  document.getElementById("periodoCOP").value;
			if(COPPeriodo == -1 || COPPeriodo == ''){
				COPPeriodo = 0;
			}
			var COPSP =  	$('form#formPagos input#txtCOPSP').asNumber();
			var COPAct =	$('form#formPagos input#txtCOPAct').asNumber(); 
			var COPRec = 	$('form#formPagos input#txtCOPRec').asNumber(); 
			var COPMultas = $('form#formPagos input#txtCOPMultas').asNumber(); 
			var RCVPeriodo = document.getElementById("periodoRCV").value;
			if(RCVPeriodo == -1 || RCVPeriodo == ''){
				RCVPeriodo = 0;
			}
			var RCVSP = $('form#formPagos input#txtRCVSP').asNumber(); 
			var RCVAct = $('form#formPagos input#txtRCVAct').asNumber(); 
			var RCVRec = $('form#formPagos input#txtRCVRec').asNumber(); 
			var RCVMultas = $('form#formPagos input#txtRCVMultas').asNumber(); 
			var sAnexoPagos = '{' +
			 '"idPago":"'+idPago+'",'+
			 '"folio":"'+folio+'",'+
			 '"cvePatron":"'+cve_Patron+'",'+
			 '"foliosua":"'+FolioSUA+'",'+
			 '"folioordeningreso":"'+FolioOrdenIngreso+'",'+
			 '"nocredito":"'+NoCredito+'",'+
			 '"fechapago":"'+fechaPago+'",'+
			 '"copperiodo":"'+COPPeriodo+'",'+
			 '"copsp":"'+COPSP+'",'+
			 '"copact":"'+COPAct+'",'+
			 '"coprec":"'+COPRec+'",'+
			 '"copmultas":"'+COPMultas+'",'+
			 '"rcvperiodo":"'+RCVPeriodo+'",'+
			 '"rcvsp":"'+RCVSP+'",'+
			 '"rcvact":"'+RCVAct+'",'+
			 '"rcvrec":"'+RCVRec+'",'+
			 '"idProceso":"3",'+
			 '"rcvmultas":"'+RCVMultas+'"}';
			  var anexoPagos = jQuery.parseJSON(sAnexoPagos);
			  
			  // alert (reglaNegocio.id.nombrecontrol);
			  var sumaCOP = COPSP + COPAct + COPRec;
			  var sumaRCV = RCVSP + RCVAct + RCVRec;	
			  var idTipo = $('form#formPromo select#cgcCatTipo\\.idTipo').val(); 
			 // if(idTipo != 7){
				//  TIPO_ADEUDO = validaSolicitudPago(folio, sumaCOP, sumaRCV, 'promocion');
			  //}
			  
			 
			//  if(idTipo == 7 || validaTipoPago(TIPO_ADEUDO)){
				  $.postJSON("promocion/guardaAnexoPagos.do", anexoPagos, function(data) {
					  
					  var oTable = $(idDataTable).dataTable(); 
					  
					  oTable.fnDraw();
					  $('form#formPagos select#regpat').prop('value', '');
					  $('form#formPagos select#concepto').prop('value','' );
					  $('form#formPagos input#txtFolioSUA').prop('value','' );
					  $('form#formPagos input#txtOrdenIngreso').prop('value','' );
					  $('form#formPagos input#txtNoCredito').prop('value', '');
					  $('form#formPagos input#fechaPagoAnexoPagos').prop('value', '');
					  $('form#formPagos select#periodoCOP').prop('value', '');
					  $('form#formPagos input#txtCOPSP').prop('value', '');
					  $('form#formPagos input#txtCOPAct').prop('value', '');
					  $('form#formPagos input#txtCOPRec').prop('value', '');
					  $('form#formPagos input#txtCOPTotal').prop('value', '');
					  $('form#formPagos input#txtCOPMultas').prop('value', '');
					  $('form#formPagos select#periodoRCV').prop('value', '');
					  $('form#formPagos input#txtRCVSP').prop('value', '');
					  $('form#formPagos input#txtRCVAct').prop('value', '');
					  $('form#formPagos input#txtRCVRec').prop('value', '');
					  $('form#formPagos input#txtRCVTotal').prop('value', '');
					  $('form#formPagos input#txtRCVMultas').prop('value', '');
					  
				  }).error(function(data){ 
					  validarSesionExpirada(data);
					}).complete(function(){
						// Instrucciones para el 'complete'
					});  
				  
			  	
			//  }
		  
		}
	});
	
	
	
	
	
	fnGeneraFolioFinal = function(){
		bloquear();
		var numFolio = $('input#txtFolio').val();
		var numero = 0;
		var subdelegacion = '01';
		var delegacion = '03'; 
		var fecha = new Date();
		var anio = fecha.getFullYear();
		var tokens = numFolio.split("/");
		desbloquear();
		bloquear();
		if(tokens.length==4){
	  		if(tokens[3]==''){
	  		 var sFolio = '{"cveDelegacion": "'+delegacion+'", "cveSubdelegacion": "'+subdelegacion+'", "numAnio" : "'+anio+'", "crcTipoCorr":{"cveTipocorr": "6" }}';
	  		  var folio = jQuery.parseJSON(sFolio);
	  		  //alert (reglaNegocio.id.nombrecontrol);
	  		  bloquear();
	  		  $.postJSON("promocion/obtenerFolio.do", folio, function(data) {
	  			numFolio = numFolio + data[0].numNumero;
	  			$('input#txtFolio').prop("value", numFolio);
	  			guardaPromocion(numFolio);
	  			return data[0].numNumero;
	  			}).error(function(data){ 
	  				validarSesionExpirada(data);
	  			}).complete(function(){
	  				desbloquear();
	  			});
	  	}
	  		desbloquear();
	  		}
		
		
	}
	
	
	fgGuardaPromocion = function(){
		if(validaCaptura.form()){
			if (document.getElementById('txtCOPConvSP').disabled == false && document.getElementById('txtRCVConvSP').disabled == false){
				if(document.getElementById('txtCOPConvSP').value == ''){
					alert('Debe introducir el monto de la SP Determinada de COP')
					return false;
				}
				if(document.getElementById('txtRCVConvSP').value == ''){
					alert('Debe introducir el monto de la SP Determinada de RCV')
					return false;
				}
				
			
			}
			if(document.getElementById('txtTrabRegularizados').value != '' &&  document.getElementById('txtTrabRegularizados').value > 0){
				if($('form#formPromo input#txtCOPPagTotal').asNumber() == '' || $('form#formPromo input#txtCOPPagTotal').asNumber() == 0){
					alert('Si existen Trabajadores Regularizados deben existir pagos');
				}
			}
			
			if(document.getElementById('dtpOPE').value!= ''){
				var ope = document.getElementById('dtpOPE').value;
				var fec = numFolio.split("/");
				var anio = fec[2];
				if(anio!=document.getElementById('txtFolioAnio').value){
					return false;
				}
				
			}
			
			
		
				var regPat =  $('form#formPromo input#txtRP').val();
				var numFolio = $('input#txtFolio').val();
				var numero = 0;
				var fechaFolio = new Date();
				var anio = fechaFolio.getFullYear();
				var tokens = numFolio.split("/");
				var folio = numFolio;
				var afil15 = $('form#formPromo input#txtAFLI15').val();
				var idSubdelegacion = '01';
				var idTipo = $('form#formPromo select#cgcCatTipo\\.idTipo').val(); 
				var idOrigen =$('form#formPromo select#cbxOrigen').val(); 
				var idCriterioSel = $('form#formPromo select#cbxCriterioSeleccion').val();
				var cvePatron = regPat;
				var nombre = $('form#formPromo input#txtNombre').val();
				var ubicacion = $('form#formPromo input#txtUbicacion').val();
				var mesesEstimados = $('form#formPromo input#txtMesesEstimados').val();
				var estTrabajReg = $('form#formPromo input#txtEstimadoTrabReg').val();
				var afil15 = $('form#formPromo input#txtAFLI15').val();
				var idCodigoObra = "";
				var superficieEstimada =  $('form#formPromo input#txtSuperficieEstimada').val();
				
				var costoTotalContratado = $('form#formPromo input#txtCostoTotalContratado').asNumber()
				var porcAvanceEstimado = $('form#formPromo input#txtPorcAvanceEstimado').val();
				var ope = formateaFecha($('form#formPromo input#dtpOPE').val());
				var rNoOficioOPE = $('form#formPromo input#txtNoOficioOPE').val();
				var nop = formateaFecha($('form#formPromo input#dtpNOP').val());
				var aop = formateaFecha($('form#formPromo input#dtpAOP').val());
				var pai = formateaFecha($('form#formPromo input#dtpPAI').val());
				var oi = formateaFecha($('form#formPromo input#dtpOI').val());
				var sp = formateaFecha($('form#formPromo input#dtpSP').val());
				var pr = formateaFecha($('form#formPromo input#dtpPR').val());
				var cr = formateaFecha($('form#formPromo input#dtpCR').val());
				var periodoDel =formateaFecha($('form#formPromo input#dtpPeriodoDel').val());
				var periodoAl = formateaFecha($('form#formPromo input#dtpPeriodoAl').val());
				var noConvenio = $('form#formPromo input#txtNoConvenio').val();
				var noParcialidades = $('form#formPromo input#txtNoParcialidades').val();
				var porcRegularizado = $('form#formPromo input#txtPorcRegularizado').val();
				var porcAvance = $('form#formPromo input#txtPorcAvance').val();
				var copconvsp = $('form#formPromo input#txtCOPConvSP').asNumber();
				var rcvconvsp = $('form#formPromo input#txtRCVConvSP').asNumber();
				var trabRevisados = $('form#formPromo input#txtTrabRevisados').val();
				var trabOmisos = $('form#formPromo input#txtTrabOmisos').val();
				var trabSubdeclarados = $('form#formPromo input#txtTrabSubdeclarados').val();
				var idStatus = $('form#formPromo input#idStatusHidden').val();
				//var folioCorreccion = $('form#formPromo input#txtObservaciones').val();
				var observaciones = $('form#formPromo input#txtObservaciones').val();
				var c = formateaFecha($('form#formPromo input#dtpC').val());
				var idMotivoCancelacion = $('form#formPromo select#cbxMotivoCancelacion').val();
				var noOficionC = $('form#formPromo input#txtNoOficioC').val();
				var tipoObra = $('form#formPromo select#cbxTipoObra').val();
				var fecFechaReg = "";
				var cveUsuario = "";
				bloquear();
				 var sPromocion = '{' +
					 '"folio":"'+folio+'",'+
					 '"cgcCatTipo": {"idTipo": "'+idTipo+'"},'+
					 '"cgcCatOrigen" : {"idOrigen":"'+idOrigen+'"},'+
					 '"cgtCatCriterioSeleccion": {"idCriterioseleccion":"'+idCriterioSel+'"},'+
					 '"cvePatron":"'+cvePatron+'",'+
					 '"dv":"'+dv+'",'+
					 '"nombre":"'+nombre+'",'+
					 '"ubicacion":"'+ubicacion+'",'+
					 '"mesesestimados":"'+mesesEstimados+'",'+
					 '"esttrabajreg":"'+estTrabajReg+'",'+
					 '"afil15":"'+afil15+'",'+
					 '"idCodigoobra":"'+idCodigoObra+'",'+
					 '"superficieestimada":"'+superficieEstimada+'",'+
					 '"costototalcontratado":"'+costoTotalContratado+'",'+
					 '"porcavanceestimado":"'+porcAvanceEstimado+'",'+
					 '"ope":"'+ope+'",'+
					 '"rnooficioope":"'+rNoOficioOPE+'",'+
					 '"nop":"'+nop+'",'+
					 '"aop":"'+aop+'",'+
					 '"pai":"'+pai+'",'+
					 '"oi":"'+oi+'",'+
					 '"sp":"'+sp+'",'+
					 '"pr":"'+pr+'",'+
					 '"cr":"'+cr+'",'+
					 '"periododel":"'+periodoDel+'",'+
					 '"periodoal":"'+periodoAl+'",'+
					 '"noconvenio":"'+noConvenio+'",'+
					 '"noparcialidades":"'+noParcialidades+'",'+
					 '"porcregularizado":"'+porcRegularizado+'",'+
					 '"porcavance":"'+porcAvance+'",'+
					 '"copconvsp":"'+copconvsp+'",'+
					 '"rcvconvsp":"'+rcvconvsp+'",'+
					 '"trabrevisados":"'+trabRevisados+'",'+
					 '"trabomisos":"'+trabOmisos+'",'+
					 '"trabsubddeclarados":"'+trabSubdeclarados+'",'+
					 '"idStatus":"'+idStatus+'",'+
					 '"observaciones":"'+observaciones+'",'+
					 '"c":"'+c+'",'+
					 '"idMotivocancelacion"	:"'+idMotivoCancelacion+'",'+
					 '"nooficioc":"'+noOficionC+'",'+
					 '"idCodigoobra":"'+tipoObra+'",'+
					 '"fecFechareg":"'+fecFechaReg+'",'+
					 '"cveUsuario":"'+cveUsuario+'"}';
				  var promocion = jQuery.parseJSON(sPromocion);
				  var nivel = '';
				  desbloquear();
				  bloquear();
				  //alert (reglaNegocio.id.nombrecontrol);
				  
				 
				  
				  $.postJSON("promocion/guardaPromocion.do", promocion, function(data) {
					  document.getElementById('guardadoHidden').value = 1;
					  validaGuardado();
					  alert('El registro se guardo con exito');
					  	}).error(function(data){ 
					  		validarSesionExpirada(data);
					}).complete(function(){
						desbloquear();//Instrucciones para el 'complete'
					});
		
				 
			}
			
			
	}
	
	

	$("#cmdGuardar").click(function(e){

		if(validaCaptura.form()){
		if (document.getElementById('txtCOPConvSP').disabled == false && document.getElementById('txtRCVConvSP').disabled == false){
			if(document.getElementById('txtCOPConvSP').value == ''){
				alert('Debe introducir el monto de la SP Determinada de COP')
				return false;
			}
			if(document.getElementById('txtRCVConvSP').value == ''){
				alert('Debe introducir el monto de la SP Determinada de RCV')
				return false;
			}
		}
		if (document.getElementById('dtpOPE').disabled == true  || document.getElementById('dtpOPE').value == ''){
			alert('Debe capturar la fecha de elaboraci\u00F3n del Oficio de Promoci\u00F3n (OPE)')
			return false;
		}
		
	
		var idTipo = $('#cgcCatTipo\\.idTipo').val();
		/*
		if(idTipo == '7' &&  document.getElementById('txtTrabRegularizados').value == '' &&  document.getElementById('cmdPagos').disabled == false ){
			alert('Antes de Capturar los pagos debe indicar los Trabajadores Regularizados');
			return false;
			
		}*/
		
		
		if(document.getElementById('dtpOPE').value!= ''){
			var ope = document.getElementById('dtpOPE').value;
			var fec = ope.split("/");
			var anio = fec[2];
			if(anio!=document.getElementById('txtFolioAnio').value){
				alert('La fecha debe ser del a\u00f1o: ' + anio);
				return false;
			}
			
		}
		
		if (idTipo == '8') {
			var afil =  document.getElementById('txtAFLI15').value;
			if (afil == '') {
				alert('Es necesario esten completos los datos del patr\u00F3n (RP,AFIL15/RO)');
				return false;
			}
		}
		
		if(document.getElementById('txtTrabRegularizados').value != '' &&  document.getElementById('txtTrabRegularizados').value > 0){
			if($('form#formPromo input#txtCOPPagTotal').asNumber() == '' || $('form#formPromo input#txtCOPPagTotal').asNumber() == 0){
				alert('Si existen Trabajadores Regularizados deben existir pagos');
			}
		}
		
		
			var regPat =  $('form#formPromo input#txtRP').val();
			var numFolio = $('input#txtFolio').val();
			var folio = numFolio;
			var folioAnio = $('input#txtFolioAnio').val();
			var folioNum = $('input#txtFolioNum').val();
			var afil15 = $('form#formPromo input#txtAFLI15').val();
			var idTipo = $('form#formPromo select#cgcCatTipo\\.idTipo').val(); 
			var idOrigen =$('form#formPromo select#cbxOrigen').val(); 
			var idCriterioSel = $('form#formPromo select#cbxCriterioSeleccion').val();
			var cvePatron = regPat;
			var nombre = $('form#formPromo input#txtNombre').val(); 
			var ubicacion = $('form#formPromo input#txtUbicacion').val();
			var mesesEstimados = $('form#formPromo input#txtMesesEstimados').val();
			var estTrabajReg = $('form#formPromo input#txtEstimadoTrabReg').val();
			var afil15 = $('form#formPromo input#txtAFLI15').val();
			var idCodigoObra =  $('form#formPromo select#cbxTipoObra').val();
			var superficieEstimada =  $('form#formPromo input#txtSuperficieEstimada').val();
			var costoTotalContratado = $('form#formPromo input#txtCostoTotalContratado').asNumber()
			var porcAvanceEstimado = $('form#formPromo input#txtPorcAvanceEstimado').val();
			var ope = formateaFecha($('form#formPromo input#dtpOPE').val());
			var rNoOficioOPE = $('form#formPromo input#txtNoOficioOPE').val();
			var nop = formateaFecha($('form#formPromo input#dtpNOP').val());
			var aop = formateaFecha($('form#formPromo input#dtpAOP').val());
			var pai = formateaFecha($('form#formPromo input#dtpPAI').val());
			var oi = formateaFecha($('form#formPromo input#dtpOI').val());
			var sp = formateaFecha($('form#formPromo input#dtpSP').val());
			var pr = formateaFecha($('form#formPromo input#dtpPR').val());
			var cr = formateaFecha($('form#formPromo input#dtpCR').val());
			var periodoDel =formateaFecha($('form#formPromo input#dtpPeriodoDel').val());
			var periodoAl = formateaFecha($('form#formPromo input#dtpPeriodoAl').val());
			var noConvenio = $('form#formPromo input#txtNoConvenio').val();
			var noParcialidades = $('form#formPromo input#txtNoParcialidades').val();
			var porcRegularizado = $('form#formPromo input#txtPorcRegularizado').val();
			var porcAvance = $('form#formPromo input#txtPorcAvance').val();
			var copconvsp = $('form#formPromo input#txtCOPConvSP').asNumber();
			var rcvconvsp = $('form#formPromo input#txtRCVConvSP').asNumber();
			var trabRevisados = $('form#formPromo input#txtTrabRevisados').val();
			var trabOmisos = $('form#formPromo input#txtTrabOmisos').val();
			var trabSubdeclarados = $('form#formPromo input#txtTrabSubdeclarados').val();
			var idStatus = $('form#formPromo input#idStatusHidden').val();
			//var folioCorreccion = $('form#formPromo input#txtObservaciones').val();
			var observaciones = $('form#formPromo input#txtObservaciones').val();
			var c = formateaFecha($('form#formPromo input#dtpC').val());
			var idMotivoCancelacion = $('form#formPromo select#cbxMotivoCancelacion').val();
			var tipoObra = $('form#formPromo select#cbxTipoObra').val();
			if (tipoObra == null){
				tipoObra = -1;
			}
			var noOficionC = $('form#formPromo input#txtNoOficioC').val();
			var fecFechaReg = "";
			var cveUsuario = "";
			bloquear();
			 var sPromocion = '{' +
				 '"folio":"'+folio+folioAnio+'/'+folioNum+'",'+
				 '"cgcCatTipo": {"idTipo": "'+idTipo+'"},'+
				 '"cgcCatOrigen" : {"idOrigen":"'+idOrigen+'"},'+
				 '"cgtCatCriterioSeleccion": {"idCriterioseleccion":"'+idCriterioSel+'"},'+
				 '"cvePatron":"'+cvePatron+'",'+
				 '"nombre":"'+nombre+'",'+
				 '"ubicacion":"'+ubicacion+'",'+
				 '"mesesestimados":"'+mesesEstimados+'",'+
				 '"esttrabajreg":"'+estTrabajReg+'",'+
				 '"afil15":"'+afil15+'",'+
				 '"idCodigoobra":"'+idCodigoObra+'",'+
				 '"superficieestimada":"'+superficieEstimada+'",'+
				 '"costototalcontratado":"'+costoTotalContratado+'",'+
				 '"porcavanceestimado":"'+porcAvanceEstimado+'",'+
				 '"ope":"'+ope+'",'+
				 '"rnooficioope":"'+rNoOficioOPE+'",'+
				 '"nop":"'+nop+'",'+
				 '"aop":"'+aop+'",'+
				 '"pai":"'+pai+'",'+
				 '"oi":"'+oi+'",'+
				 '"sp":"'+sp+'",'+
				 '"pr":"'+pr+'",'+
				 '"cr":"'+cr+'",'+
				 '"periododel":"'+periodoDel+'",'+
				 '"periodoal":"'+periodoAl+'",'+
				 '"noconvenio":"'+noConvenio+'",'+
				 '"noparcialidades":"'+noParcialidades+'",'+
				 '"porcregularizado":"'+porcRegularizado+'",'+
				 '"porcavance":"'+porcAvance+'",'+
				 '"copconvsp":"'+copconvsp+'",'+
				 '"rcvconvsp":"'+rcvconvsp+'",'+
				 '"trabrevisados":"'+trabRevisados+'",'+
				 '"trabomisos":"'+trabOmisos+'",'+
				 '"trabsubddeclarados":"'+trabSubdeclarados+'",'+
				 '"idStatus":"'+idStatus+'",'+
				 '"observaciones":"'+observaciones+'",'+
				 '"c":"'+c+'",'+
				 '"idMotivocancelacion"	:"'+idMotivoCancelacion+'",'+
				 '"nooficioc":"'+noOficionC+'",'+
				 '"idCodigoobra":"'+tipoObra+'",'+
				 '"fecFechareg":"'+fecFechaReg+'",'+
				 '"cveUsuario":"'+cveUsuario+'"}';
			  var promocion = jQuery.parseJSON(sPromocion);
			  var nivel = '';
			  bloquear();
			  //alert (reglaNegocio.id.nombrecontrol);}
			  
				if (document.getElementById('txtFolioNum').disabled == false  && document.getElementById('txtFolioAnio').disabled == false){
					$.postJSON("promocion/consultaPromocion.do", promocion, function(dataConsultaPromo) {
						if(dataConsultaPromo!=null && dataConsultaPromo.folio != ''){
							alert('El folio ya se encuentra registrado');
							return false;
						}else{
							$.postJSON("promocion/guardaPromocion.do", promocion, function(data) {
								  document.getElementById('guardadoHidden').value = 1;
								  validaGuardado();
									//$('input#txtFolio').prop("value", data.folio);
								  alert('El registro se guardo con \u00E9xito');
								  	}).error(function(data){ 
								  		validarSesionExpirada(data);
								}).complete(function(){
								});
						}
					  }).error(function(data){ 
					  		validarSesionExpirada(data);
					}).complete(function(){
						desbloquear();//Instrucciones para el 'complete'
					});
				}else{
					$.postJSON("promocion/guardaPromocion.do", promocion, function(data) {
						  document.getElementById('guardadoHidden').value = 1;
						  validaGuardado();
							//$('input#txtFolio').prop("value", data.folio);
						  alert('El registro se guardo con \u00E9xito');
						  	}).error(function(data){ 
						  		validarSesionExpirada(data);
						}).complete(function(){
						});
	
				}
			  
			
		
				  
					
		
			  
				desbloquear();//Instrucciones para el 'complete'
					 
		}
		
		
	});

	var natDays = [
		           [7, 23], [7, 24], [8, 13], [8, 14]
		       ];

		       function nationalDays(date) {
		           for (i = 0; i < natDays.length; i++) {
		               if (date.getMonth() == natDays[i][0] - 1 && date.getDate() == natDays[i][1]) {
		                   return [false, natDays[i][2] + '_day'];
		               }
		           }
		           return [true, ''];
		       }

		       function noWeekendsOrHolidays(date) {
		           var noWeekend = $.datepicker.noWeekends(date);
		           if (noWeekend[0]) {
		               return nationalDays(date);
		           } else {
		               return noWeekend;
		           }
		       }


		      
	 // Fecha de Inicio y Termino
	 $( "#dtpOPE, #dtpNOP, #dtpC, #dtpAOP, #dtpSP, #dtpPAI , #dtpOI, #dtpPR, #dtpCR, #fechaPagoAnexoPagos" ).datepicker( {
		 dateFormat: 'dd/mm/yy', 
		 beforeShowDay: noWeekendsOrHolidays
		 });

	 $('#dtpPeriodoAl').datepicker(
				{ dateFormat: 'dd/mm/yy'
					
				  	 });
	 
	 $('#dtpPeriodoDel').datepicker(
				{ dateFormat: 'dd/mm/yy',
				  beforeShow:customRange
				  
	 });
	 
	
	 
	
			 
	 
	 
	 function customRange(input) 
	 { 
	
	 return {
	          maxDate: (input.id == "dtpPeriodoDel" ? $("#dtpOPE").datepicker("getDate") : null)
	          
	        }; 
	 }
		$.postJSON("promocion/obtenerFechaServidor.do", null,function(data) {
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(data){
			$('#dtpOPE, #dtpNOP, #dtpAOP, #dtpSP, #dtpOI, #dtpPR, #dtpCR, #dtpPeriodoDel ').datepicker('option', 'maxDate', data.responseText);
			
			$('#dtpPeriodoAl, #fechaPagoAnexoPagos').datepicker('option', 'maxDate', $("#dtpOPE").datepicker("getDate"));
			
		});
	
	/**
	 * Inicializacion del data table
	 */
	 oDtPromocion = $(idDataTable).dataTable({
			"bJQueryUI" : true,
			 "bScrollInfinite": true,
		        "bScrollCollapse": true,
		        "sScrollY": "200px",
			"bFilter" : true,
			"bAutoWidth" : true,
			"bServerSide" :	true,
			"iDeferLoading": 0,
			"bSort": false,
			"aoColumnDefs": [
			              {  fnRender :function(oObj){
			  				var retVal = '<input type="radio" value="' +
							oObj.aData['idPago'] +'" id="radioPromoPagos" class="radioClase" name="radioPromoPagos" onclick="cargaPago('+oObj.aData['idPago']+');"/> ';
							return retVal;
			              	}, "aTargets": [0], "mDataProp" :"idPago" },
			              {  "aTargets": [1], "mDataProp" :"cvePatron" },
			              {  "aTargets": [2], "mDataProp" :"foliosua" },
			              {  "aTargets": [3], "mDataProp" :"folioordeningreso" },
			              {  "aTargets": [4], "mDataProp" :"nocredito" },
			              {  "aTargets": [5], "mDataProp" :"fechapago" },
			              {  "aTargets": [6], "mDataProp" :"copperiodo" },
			              {  "aTargets": [7], "mDataProp" :"copsp" },
			              {  "aTargets": [8], "mDataProp" :"copact" },
			              {  "aTargets": [9], "mDataProp" :"coprec" },
			              {fnRender :function(oObj){
				  				var retVal = oObj.aData['copact'] + oObj.aData['copsp'] + oObj.aData['coprec'] 
								return retVal; }, "aTargets": [10] },
			              {  "aTargets": [11], "mDataProp" :"copmultas" },
			              {  "aTargets": [12], "mDataProp" :"rcvperiodo" },
			              {  "aTargets": [13], "mDataProp" :"rcvsp" },
			              {  "aTargets": [14], "mDataProp" :"rcvact" },
			              {  "aTargets": [15], "mDataProp" :"rcvrec" },
			              {fnRender :function(oObj){
				  				var retVal = oObj.aData['rcvact'] + oObj.aData['rcvsp'] + oObj.aData['rcvrec'] 
								return retVal; }, "aTargets": [16] },
			              {  "aTargets": [17], "mDataProp" :"rcvmultas" }
			              
			              
			          ],
			 "sAjaxSource" : 'promocion/paginar.do',
			 "fnServerData" : function(sSource, aoData, fnCallback) {
				 aoData.push({
						"name" : "sSearch",
						"value" : $('#txtFolio').val()+$('#txtFolioAnio').val()+'/'+$('#txtFolioNum').val()
					});
					var wrapper = new Object();
					wrapper.aoData = aoData;
					$.postJSON(sSource, wrapper, function(data) {
						fnCallback(data);
					});
				}
			});
	
	
	 /**
		 * Inicializacion del data table
		 */
	 oDtBuscaPromocion = $(idBuscaTable).dataTable({
				
		 "bJQueryUI" : true,
				"bPaginate": true,
				"bAutoWidth" : true,
				"bServerSide" :	true,
				"iDeferLoading": 0,
				"aoColumnDefs": [
				              {  fnRender :function(oObj){
				  				var retVal = '<input type="radio" value="' +
								oObj.aData['folio'] +'" id="radioTable" class="radioClase" name="radio" onclick=""/> ';
								return retVal;
				              	}, "sClass": "centro","aTargets": [0], "mDataProp" :"folios" },
				              { "sClass": "centro", "aTargets": [1], "mDataProp" :"cgcCatTipo.descripcion" },
				              {  "sClass": "centro","aTargets": [2], "mDataProp" :"cgcCatOrigen.descOrigen" },
				              {  "sClass": "centro","aTargets": [3], "mDataProp" :"folio" },
				              {  "sClass": "centro","aTargets": [4], "mDataProp" :"cvePatron" },
				              {  "sClass": "centro","aTargets": [5], "mDataProp" :"cveModalidad" },
				              {  "sClass": "centro","aTargets": [6], "mDataProp" :"nombre" },
				              {  "sClass": "centro","aTargets": [7], "mDataProp" :"afil15" },
				              {  "sClass": "centro","aTargets": [8], "mDataProp" :"sp" },
				              {  "sClass": "centro","aTargets": [9], "mDataProp" :"oi" },
				              {  "sClass": "centro","aTargets": [10], "mDataProp" :"pr" }
				             
				             
				              
				              
				          ],
				 "sAjaxSource" : 'promocion/paginaPromociones.do',
				 "fnServerData" : function(sSource, aoData, fnCallback) {
					 var sSearch = '';
					 if($("form#formBuscaPromo select#findClasificacion").val()!=null && $("form#formBuscaPromo select#findClasificacion").val()!= '-1'){
						 sSearch =sSearch + 'findClasificacion:' + $('form#formBuscaPromo select#findClasificacion').val() + '|';
					 }
					 if($("form#formBuscaPromo select#findFuente").val()!=null && $("form#formBuscaPromo select#findFuente").val()!= '-1'){
						 sSearch =sSearch + 'findFuente:' +  $('form#formBuscaPromo select#findFuente').val() + '|';
					 }
					 if($("form#formBuscaPromo input#findFolio").val()!=null && $("form#formBuscaPromo input#findFolio").val()!= ''){
						 sSearch =sSearch + 'findFolio:' + $('form#formBuscaPromo input#findFolio').val() + '|';
					 }
					 if($("form#formBuscaPromo input#findRP").val()!=null && $("form#formBuscaPromo input#findRP").val()!= ''){
						 sSearch =sSearch + 'findRP:' + $('form#formBuscaPromo input#findRP').val() + '|';
					 }
					 if($("form#formBuscaPromo input#findNombre").val()!=null && $("form#formBuscaPromo input#findNombre").val()!= ''){
						 sSearch =sSearch + 'findNombre:' + $('form#formBuscaPromo input#findNombre').val() + '|';
					 }
					 if($("form#formBuscaPromo input#findAfil").val()!=null && $("form#formBuscaPromo input#findAfil").val()!= ''){
						 sSearch =sSearch + 'findAfil:' + $('form#formBuscaPromo input#findAfil').val() ;
					 }
					 
					 aoData.push({
							"name" : "sSearch",
							"value" : sSearch
						});
						var wrapper = new Object();
						wrapper.aoData = aoData;
						$.postJSON(sSource, wrapper, function(data) {
							fnCallback(data);
						}).error(function(data){ 
							validarSesionExpirada(data);							
						});
					}
				});
		
	 
	// Dialog de Elemento Nuevo			
	 oDgNuevo = $(idDgNuevo).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 600,
		beforeClose :function(event,ui){
		    limpiarFormulario("#promocionForm");
		},
		buttons: {
			"Aceptar": function() { 
				var clase = $("#promocionForm").serializeObject(true);				
				$.postJSON("promocion/agregar.do", clase, function(data) {
				}).error(function(data){ 
					validarSesionExpirada(data);
				}).complete(function(){
					alert("La clase ha sido agregada...");
					inicializaPosicionPaginador();					
				});
				$(this).dialog("close"); 
			}, 
			"Cancelar": function() { 
				$(this).dialog("close"); 
			} 
		}
	});
	
	
	// Dialog de Elemento a Modificar			
	 oDgModificar = $(idDgModificar).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 600,
		beforeClose :function(event,ui){
		    limpiarFormulario("#promocionFormModificar");
		},		
		buttons: {
			"Aceptar": function() { 
				var clase = $("#promocionFormModificar").serializeObject(true);				
				$.postJSON("promocion/modificar.do", clase, function(data) {					
				}).error(function(data){ 
					validarSesionExpirada(data);
				}).complete(function(){
				
					alert("La clase ha sido modificada...");
					inicializaPosicionPaginador();					
				});
				$(this).dialog("close"); 
			}, 
			"Cancelar": function() { 
				$(this).dialog("close"); 
			} 
		}
	});
	

	// Dialog de Elemento a Borrar			
	 oDgBorrar = $(idDgBorrar).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		height: 140,
		buttons: {
			"Aceptar": function() { 
				var clase = $("#promocionFormBorrar").serializeObject(true);
				$.postJSON("promocion/eliminar.do", clase, function(data) {
				}).error(function(data){ 
					validarSesionExpirada(data);
				}).complete(function(){
					//Instrucciones para el complete
					alert("La clase ha sido eliminada...");		
					inicializaPosicionPaginador();					
				});
				$(this).dialog("close"); 
			}, 
			"Cancelar": function() { 
				$(this).dialog("close"); 
			} 
		}
	});
	 
	 
	// Dialog de Anexos Pagos			
	 oDgAnexoPagos = $(idDgAnexoPagos).dialog({
		autoOpen: false,
		modal:true,
		resizable:true,
		height: 600,
		width: 1100,
		open:function(event, ui)
        {
			
			var numTrabs = $('form#formPromo input#txtTrabRegularizados').val();
			if(numTrabs != null && numTrabs != '' && numTrabs > 0){
				$('form#formPagos input#txtOrdenIngreso').prop("disabled", true);
				$('form#formPagos input#txtOrdenIngreso').removeClass("red");
				
			}else{
				$('form#formPagos input#txtOrdenIngreso').prop("disabled", false);
				$('form#formPagos input#txtOrdenIngreso').addClass("red");
				
			}
			
			var f =  $('form#formPromo input#txtFolio').val()+ $('input#txtFolioAnio').val() + '/' +$('input#txtFolioNum').val() ; 
			var sFolio = '{"folio": "'+f+'" }';
			  var folio = jQuery.parseJSON(sFolio);
			  $.postJSON("promocion/consultaPatronesAP.do", folio, function(datas) {
					 var options = "<option value='' >--Por favor seleccione--</option>";
					 if(datas!=null)
					   for (var i = 0; i < datas.length; i++) {
				         options += "<option value='"+ jQuery.trim(datas[i].cvePatron) + jQuery.trim(datas[i].cveModalidad) +"'>"+ jQuery.trim(datas[i].cvePatron) + jQuery.trim(datas[i].cveModalidad) +"</option>";		     
				       }
					 $('select#regpat').html(options);
					//Agrega las opciones al control
					 var oTable = $(idDataTable).dataTable(); 
					  oTable.fnDraw();
				}).error(function(datas){ 
					validarSesionExpirada(datas);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
			  $('select#concepto').val("");
			 var options = "<option value='' >--Por favor seleccione--</option>";
			 $('form#formPagos select#periodoRCV').html(options);
			 $('form#formPagos select#periodoCOP').html(options);
			  
			 $('input#txtFolioSUA').prop("value", "");
			  $('input#txtOrdenIngreso').prop("value", "");
			  $('input#txtNoCredito').prop("value", "");
			  $('input#fechaPagoAnexoPagos').prop("value", "");
			  $('input#txtCOPSP').prop("value", "");
			  $('input#txtCOPAct').prop("value", "");
			  $('input#txtCOPRec').prop("value", "");
			  $('input#txtCOPTotal').prop("value", "");
			  $('input#txtCOPMultas').prop("value", "");
			  
			  $('input#txtRCVSP').prop("value", "");
			  $('input#txtRCVAct').prop("value", "");
			  $('input#txtRCVRec').prop("value", "");
			  $('input#txtRCVTotal').prop("value", "");
			  $('input#txtRCVMultas').prop("value", "");
        }
	         ,
		 close: function(event, ui)
	        {
			 var numFolio =  document.getElementById('txtFolio').value+ $('input#txtFolioAnio').val() + '/' +$('input#txtFolioNum').val() ;;
			 var sFolio = '{"folio": "'+numFolio+'" }';
			  var folio = jQuery.parseJSON(sFolio);
			  var totalcopsp = 0;
			  var totalrcvsp = 0;
			  var totalcopact = 0;
			  var totalrcvact = 0;
			  var totalcoprec = 0;
			  var totalrcvrec = 0;
			 $.postJSON("promocion/consultaAnexoPagos.do", folio, function(data) {
				 var options = "<option value='-1' >--Por favor seleccione--</option>";
				 if(data!=null){
				   for (var i = 0; i < data.length; i++) {
					 totalcopsp = totalcopsp + data[i].copsp;
					 totalrcvsp = totalrcvsp + data[i].rcvsp;
					 totalcopact = totalcopact + data[i].copact;
					 totalrcvact =totalrcvact + data[i].rcvact;
					 totalcoprec = totalcoprec + data[i].coprec;
					 totalrcvrec = totalrcvrec + data[i].rcvrec;
				   }
				   
				 }
				 var totalCOP = 0;
				 totalCOP = totalcopsp + totalcopact + totalcoprec;
				 var totalRCV = 0;
				 totalRCV = totalrcvsp + totalrcvrec+ totalrcvact;
				 sumariza("txtCOPPagSP", totalcopsp);
				 sumariza("txtRCVPagSP", totalrcvsp);
				 sumariza("txtCOPPagAct", totalcopact);
				 sumariza("txtRCVPagAct", totalrcvact);
				 sumariza("txtCOPPagRec", totalcoprec);
				 sumariza("txtRCVPagRec", totalrcvrec);
				 var totalCOP = totalcopsp + totalcopact + totalcoprec;
				 var totalRCV = totalrcvsp + totalrcvact + totalrcvrec;
				 
				 $('form#formPromo input#txtCOPPagTotal').prop('value', totalCOP);
				 $('form#formPromo input#txtCOPPagTotal').prop('readonly', true);
				 $('form#formPromo input#txtCOPPagTotal').formatCurrency();
				 
				 $('form#formPromo input#txtRCVPagTotal').prop('value', totalRCV);
				 $('form#formPromo input#txtRCVPagTotal').prop('readonly', true);
				 $('form#formPromo input#txtRCVPagTotal').formatCurrency();
				 
				 var idTipo = $('#cgcCatTipo\\.idTipo').val();
				 if(idTipo == '6'){
				 	 var difCOP = parseFloat( $('input#txtCOPConvSP').asNumber()).toFixed(2) - parseFloat(totalcopsp).toFixed(2);
					 var difRCV = parseFloat( $('input#txtRCVConvSP').asNumber()).toFixed(2) - parseFloat(totalrcvsp).toFixed(2);
					 $('form#formPromo input#txtCOPxPagSP').prop('value', difCOP);
					 $('form#formPromo input#txtCOPxPagSP').prop('readonly', true);
					 $('form#formPromo input#txtCOPxPagSP').formatCurrency();
					 $('form#formPromo input#txtRCVxPagSP').prop('value', difRCV);
					 $('form#formPromo input#txtRCVxPagSP').prop('readonly', true);
					 $('form#formPromo input#txtRCVxPagSP').formatCurrency();
				 }
				 
				 
				//Agrega las opciones al control
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
	        }
	 
		
	});
	 
	 
	 
	 
	// Dialog de Anexos Pagos			
	 oDgBuscar = $(idDgBuscar).dialog({
		autoOpen: false,
		modal:true,
		resizable:true,
		height: 600,
		width: 1100,
		open:function(event, ui)
        {
			desabilitaForma();
	 		llenaComboFindFuentePromocion();
        }
		
	});
	 
	// Dialog de Elemento Ayuda		
	oDgAyuda = $(idDgAyuda).dialog({
		modal:		true,
		buttons: {
			"Aceptar": function() {
				$(this).dialog("close"); 
			}
		}
	});	 	
	 
	
	 var validaCaptura = $("#formPromo").validate({
	  	  rules: {
	  		  txtRP: {
	  			required: function(element) {
	  				return document.getElementById('dtpAOP').value != '';
	  	         },
	  	      maxlength: 10,
	  	      minlength: 10,
	  	      alphanumeric: true
	  	    },
	  	  txtFolio: {
	  			required: true
	  	    },
	  	  txtFolioAnio: {
	  			required: true,
	  	      maxlength: 4,
	  	      minlength: 4,
	  	      max: 2014,
	  	      min: 2008,
	  	      digits: true
	  	    },
	  	  txtFolioNum: {
	  			required: true,
	  	      maxlength: 4,
	  	      minlength: 4,
	  	      min : 1,
	  	      digits: true
	  	    },
	  	    txtNombre: {
	   		  required: true,
	   		  maxlength: 100,
	   		 alphanumeric: true
	   		},
	   	 dtoOPE: {
	   		  required: true
	   		
	   		},
	   		txtUbicacion: {
		   		  maxlength: 100,
		   		 alphanumeric: true
		   		},
	  	  txtMesesEstimados : {
	  		  maxlength: 3,
	  		  digits: true
	  	  },
	  	txtEstimadoTrabReg:{
	  		  maxlength: 5,
	  		  digits: true
	  	  },
	  	txtAFLI15:{
	  		  maxlength: 22,
	  		  alphanumeric: true
	  	  },
	  	txtSuperficieEstimada:{
	  		  maxlength: 7,
	  		  digits: true
	  	  },
	  	txtPorcAvanceEstimado:{
	  		  maxlength: 3,
	  		digits: true,
	  		min : 1,
	  		max : 100
	  	  },
	  	txtNoOficioOPE:{
	  		required: function(element) {
  				return document.getElementById('dtpOPE').value != '';
  	         },
	  		  maxlength: 10,
	  		  digits: true
	  	  },
	  	txtTrabRevisados:{
	  		  maxlength: 4,
	  		  digits: true
	  	  },
	  	txtTrabOmisos:{
	  		  maxlength: 4,
	  		 digits: true
	  	  },
	  	txtTrabSubdeclarados:{
	  		  maxlength: 4,
		  		 digits: true
		  	  },
	  	  txtNoConvenio:{
	  		  maxlength : 20,
	  		alphanumeric: true
	  	  },
	  	txtPorcAvance:{
	  		maxlength : 3,
	  		digits: true,
	  		min : 1,
	  		max : 100
	  	},
	  	txtNoParcialidades : {
	  		maxlength : 2,
	  		digits: true,
	  		min : 2,
	  		max : 48
	  	},
	  	txtCOPConvSP: {
	  		maxlength : 14
	  	},
	  	txtRCVConvSP: {
	  		maxlength : 14
	  	},
	  	txtNoOficioC :{
	  		required: function(element) {
  				return document.getElementById('dtpC').value != '';
  	         },
	  		digits : true,
	  		maxlength: 10
	  	},
	  	txtPorcRegularizado : {
	  		maxlength : 3,
	  		digits: true,
	  		min : 1,
	  		max : 100
	  	}
	  	
	  		
	  	  }
	  
	  });
	
	
	 var validaPagos = $("#formPagos").validate({
	  	  rules: {
	  		  regpat: {
	  			 required: true
	  	    },
	  	    concepto:{
	  	    	 required: true
	  	    }, 
	  	  txtFolioSUA: {
	  		required: function(element) {
	  				return document.getElementById('txtOrdenIngreso').value == '';
	  	         }
	  	       },
	  	  txtOrdenIngreso: {
	  		required: function(element){
	  			  return document.getElementById('txtFolioSUA').value == '';
	  		  }
	  	  },
	  	fechaPagoAnexoPagos: {
	  		required: true
	  	},
	  	txtNoCredito: {
	  		required: function(element){
	  			  return document.getElementById('txtOrdenIngreso').value != '';
	  		  }
	  	},
	  	periodoCOP: {
	  		required:function(element){
	  			  return (document.getElementById('concepto').value == 'COP' || document.getElementById('concepto').value == 'COPRCV');
	  		  }
	  	}, 
	  	periodoRCV: {
	  		required:function(element){
	  			  return (document.getElementById('concepto').value == 'RCV' || document.getElementById('concepto').value == 'COPRCV');
	  		  }
	  	},
	  	txtCOPSP:{
	  		maxlength: 14 
	  	},
	  	txtCOPAct:{
	  		maxlength: 14
	  	},
	  	txtCOPRec:{
	  		maxlength: 14
	  	},
	  	txtCOPTotal: {
	  		required:function(element){
	  			  return (document.getElementById('concepto').value == 'COP' || document.getElementById('concepto').value == 'COPRCV');
	  		  }
	  		
	  	},
	  	txtRCVSP: {
	  		maxlength: 14
	  		
	  	},
	  	txtRCVAct: {
	  		maxlength: 14
	  		
	  	},
	  	txtRCVRec: {
	  		maxlength: 14
	  		
	  	},
	  	txtRCVTotal:{
	  		required:function(element){
	  			  return (document.getElementById('concepto').value == 'RCV' || document.getElementById('concepto').value == 'COPRCV');
	  		  }
	  		
	  		
		  
	  	}
	  	  
	  	  },
	  	 messages: {
	  		regpat: "Seleccione un registro patronal",
	  		concepto: "Seleccione un concepto",
	  		txtFolioSUA : "Debe Ingresar Folio SUA / Orden Cr&eacute;dito",
	  		txtOrdenIngreso: "Debe Ingresar Folio SUA / Orden Cr&eacute;dito",
	  		fechaPagoAnexoPagos : "Debe seleccionar la fecha de pago",
	  		txtNoCredito : "Debe ingresar el n&uacute;mero de No. Cr&eacute;dito",
	  		periodoCOP	: "Debe seleccionar un Per&iacute;odo de COP",
	  		periodoRCV: "Debe seleccionar un Per&iacute;odo de RCV",
	  		txtCOPTotal: "No ha registrado el Monto COP Pagado",
	  		txtRCVTotal: "No ha registrado el Monto RCV Pagado"
	  	 }
	 	
	  });
	 
	
	
	  
	  $('select#cbxTipoObra').prop('disabled', true);
	  $('select#cbxMotivoCancelacion').prop('disabled', true);
	  $('select#cbxOrigen').prop('disabled', true);
	  $('select#cbxCriterioSeleccion').prop('disabled', true);
	  
	  
	  $('#txtCostoTotalContratado').blur(function()
              {
                  $('#txtCostoTotalContratado').formatCurrency();
              });
	  
	  $('#txtCOPPagSP').change(function()
              {
                  $('#txtCOPPagSP').formatCurrency();
              });
	  $('#txtRCVPagSP').change(function()
              {
                  $('#txtRCVPagSP').formatCurrency();
              });
	  $('#txtCOPConvSP').blur(function()
              {
                  $('#txtCOPConvSP').formatCurrency();
              });
	  $('#txtRCVConvSP').blur(function()
              {
                  $('#txtRCVConvSP').formatCurrency();
              });
	  $('#txtCOPSP').blur(function()
              {
                  $('#txtCOPSP').formatCurrency();
              });
	  $('#txtCOPAct').blur(function()
              {
                  $('#txtCOPAct').formatCurrency();
              });
	  $('#txtCOPRec').blur(function()
              {
                  $('#txtCOPRec').formatCurrency();
              });
	  $('#txtCOPTotal').blur(function()
              {
                  $('#txtCOPTotal').formatCurrency();
              });
	  $('#txtRCVSP').blur(function()
              {
                  $('#txtRCVSP').formatCurrency();
              });
	  $('#txtRCVAct').blur(function()
              {
                  $('#txtRCVAct').formatCurrency();
              });
	  $('#txtRCVRec').blur(function()
              {
                  $('#txtRCVRec').formatCurrency();
              });
	  $('#txtRCVTotal').blur(function()
              {
                  $('#txtRCVTotal').formatCurrency();
              });
	  
	  
	 
	 
});//$(document).ready(function()

//Inicializa el paginador
function inicializaPosicionPaginador(){
	oDtClase.fnDisplayStart(0);
}


function sumariza(elemento, valor){
	$('form#formPromo input#'+elemento).prop('disabled', false);
	 $('form#formPromo input#'+elemento).prop('value', valor);
	 $('form#formPromo input#'+elemento).formatCurrency();
	 $('form#formPromo input#'+elemento).prop('readonly', true);
}

function nuevo(){
	oDgNuevo.dialog('open');
}

function paginar(){
	oDtClase.fnDraw();
}

function modificar(){
	var idClase = $('#:checked').val();
	var sClase = '{"cveIdClase":'+idClase+'}';
	var clase = jQuery.parseJSON(sClase);
	// Buscamos el elemento
	$.postJSON("clase/consultaPorClave.do", clase, function(data) {
		$('#wrapperDialogModif,#promocionFormModificar,#cveIdClase').val(data.cveIdClase);
		$('#wrapperDialogModif,#promocionFormModificar,#desClase').val(data.desClase);
		$('#wrapperDialogModif,#promocionFormModificar,#fecIni').val(data.fecIni);
		$('#wrapperDialogModif,#promocionFormModificar,#fecFin').val(data.fecFin);
		$('#wrapperDialogModif,#promocionFormModificar,#indPrimaMedia').val(data.indPrimaMedia);
		$('#wrapperDialogModif,#promocionFormModificar,#numGradoRiesgo').val(data.numGradoRiesgo);
		$('#wrapperDialogModif,#promocionFormModificar,#numPorcentaje').val(data.numPorcentaje);
		oDgModificar.dialog('open');
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
}

function activa(elemento){
	//--Por favor seleccione--

	var idSeleccion = $('form#formPromo select#cbxCriterioSeleccion').val();
	if(idSeleccion != -1 && idSeleccion!=0){
		if(elemento != null && elemento != ''){
			//alert('activando :: '+ elemento);
			$('form#formPromo input#'+elemento ).prop('disabled', false);
			$('form#formPromo input#'+elemento ).addClass("red");
		}
		 
	}
	

	
}


function generaPeriodo(){
	
	
	
}


function sumaTrabajadores(e){
	

var trabRevi =  $('input#txtTrabRevisados').val();
var trabOmi =  $('input#txtTrabOmisos').val(); 
var trabSub =  $('input#txtTrabSubdeclarados').val(); 
var trabReg = $('input#txtTrabRegularizados').val(); 

tecla = (document.all) ? e.keyCode : e.which;
if (tecla==13  ) {
	if(trabRevi == ''){
		alert('La suma de los Trabajadores Omisos + T. Subdeclarados NO puede ser MAYOR que los Trabajadores Revisados');
		return false;
	}
	if(trabOmi == '' &&  trabSub==''){
		return false;
	}
	if(trabOmi == ''){
		trabOmi = 0;
	}
	if(trabSub == ''){
		trabSub = 0;
	}
		
	
	trabReg = parseInt(trabOmi) + parseInt(trabSub);
	if(trabReg > trabRevi){
		alert('La suma de los Trabajadores Omisos + T. Subdeclarados NO puede ser MAYOR que los Trabajadores Revisados');
	}else{
		document.getElementById('txtTrabRegularizados').value = trabReg;
	}
}
	
}

function sumarTrabajadores(){
	

	var trabRevi =  $('input#txtTrabRevisados').val();
	var trabOmi =  $('input#txtTrabOmisos').val(); 
	var trabSub =  $('input#txtTrabSubdeclarados').val(); 
	var trabReg = $('input#txtTrabRegularizados').val(); 

	
	
		if(trabRevi == ''){
			alert('La suma de los Trabajadores Omisos + T. Subdeclarados NO puede ser MAYOR que los Trabajadores Revisados');
			return false;
		}
		if(trabOmi == ''){
			trabOmi = 0;
		}
		if(trabSub == ''){
			trabSub = 0;
		}
			
		
		trabReg = parseInt(trabOmi) + parseInt(trabSub);
		if(trabReg > trabRevi){
			alert('La suma de los Trabajadores Omisos + T. Subdeclarados NO puede ser MAYOR que los Trabajadores Revisados');
		}else{
			document.getElementById('txtTrabRegularizados').value = trabReg;
		}
	
		
	}

function activaCombos(combo){
	//CgcCatTipoObra\\.idTipoobra
	$('form#formPromo select#'+ combo).prop('disabled', false);
	 $('form#formPromo select#'+combo ).addClass("red");
}


function desactivaCombos(combo){
	//--Por favor seleccione--
	$('form#formPromo select#'+ combo).html('');
	$('form#formPromo select#'+ combo).html("<option value='-1'>--Por favor seleccione--</option>");
	 $('form#formPromo select#'+combo ).removeClass("red");
	$('form#formPromo select#'+ combo).prop("disabled", true);
	
	
}



function desactivaDirecto(elemento){
	//--Por favor seleccione--
	if( elemento.indexOf("cmd") == -1){
		$('form#formPromo input#'+elemento ).prop("value", ""); 
	}
		
		 $('form#formPromo input#'+elemento ).prop("disabled", true);
		 $('form#formPromo input#'+elemento ).removeClass("red");
}


function activaDirecto(elemento, value){
	//--Por favor seleccione--
	$('form#formPromo input#'+elemento ).prop("disabled", false);
	$('form#formPromo input#'+elemento ).prop("value", value);
	$('form#formPromo input#'+elemento ).addClass("red");
}

function activaDirectos(elemento, value, digver){
	//--Por favor seleccione--
	if(value != null){
		if(digver == 1){
			$('form#formPromo input#'+elemento ).prop("disabled", true);
			$('form#formPromo input#'+elemento ).prop("value", value);
			$('form#formPromo input#'+elemento ).removeClass("red");
		}else{
			$('form#formPromo input#'+elemento ).prop("disabled", false);
			$('form#formPromo input#'+elemento ).prop("value", value);
			$('form#formPromo input#'+elemento ).addClass("red");
		}
		
	}
}


function desactiva(elemento){
	//--Por favor seleccione--
	var idSeleccion = $('form#formPromo select#cbxCriterioSeleccion').val(); 
	if(idSeleccion != -1 && idSeleccion!=0){
		if( elemento.indexOf("cmd") == -1){
			$('form#formPromo input#'+elemento ).prop("value", ""); 
		}
		//alert('desactivando :: ' + elemento);		
		$('form#formPromo input#'+elemento ).prop("disabled", true);
		 $('form#formPromo input#'+elemento ).removeClass("red");
		
	}
}


function agregarPagos(){
	var iStatus = document.getElementById('guardadoHidden').value;
	if (document.getElementById('txtTrabRevisados').disabled == false && document.getElementById('txtTrabOmisos').disabled == false 
			&& document.getElementById('txtTrabSubdeclarados').disabled == false 
			&& document.getElementById('txtTrabOmisos').value== '' ){
					alert('Antes de Capturar los pagos debe indicar los trabajadores regularizados');
					return false;
	}
	if (document.getElementById('txtCOPConvSP').disabled == false && document.getElementById('txtRCVConvSP').disabled == false){
		if(document.getElementById('txtCOPConvSP').value == ''){
				alert('Debe introducir el monto de la SP Determinada de COP')
				return false;
		}
		if(document.getElementById('txtRCVConvSP').value == ''){
			alert('Debe introducir el monto de la SP Determinada de RCV')
			return false;
		}
	}
	if(iStatus == '1'){
		oDgAnexoPagos.dialog('open');
		var f =  $('form#formPromo input#txtFolio').val()+ $('input#txtFolioAnio').val() + '/' +$('input#txtFolioNum').val() ;; 
		var sFolio = '{"folio": "'+f+'" }';
		  var folio = jQuery.parseJSON(sFolio);
		  $.postJSON("promocion/consultaPatronesAP.do", folio, function(datas) {
				 var options = "<option value='' >--Por favor seleccione--</option>";
				 if(datas!=null)
				   for (var i = 0; i < datas.length; i++) {
			         options += "<option value='"+ jQuery.trim(datas[i].cvePatron) + jQuery.trim(datas[i].cveModalidad) +"'>"+ jQuery.trim(datas[i].cvePatron)+ jQuery.trim(datas[i].cveModalidad) +"</option>";		     
			       }
				 $('select#regpat').html(options);
				//Agrega las opciones al control
			}).error(function(datas){ 
				validarSesionExpirada(datas);
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
	}else{
		alert('Debe guardar el registro primero')
	}
}


function ayuda(){
	alert("ayuda");
	oDgAyuda.dialog('open');
}


function generaFolioInicial(){
	var delegacion = '';
	var subdelegacion = '';
	  $.postJSON("promocion/obtenerDelegacion.do", '', function(data) {
			delegacion = data.cveCodigoDelegacion;
			subdelegacion = data.cveCodigoSubDelegacion;
			if(delegacion.length == 1){
				delegacion = '0' + delegacion;
			}
			if(subdelegacion.length == 1){
				subdelegacion = '0' + subdelegacion;
			}
			var idTipo = $('form#formPromo select#cgcCatTipo\\.idTipo').val(); 
			var idOrigen =$('form#formPromo select#cbxOrigen').val(); 
			if(idOrigen != -1 && idOrigen != 0){
			var numFolio = delegacion +''+ subdelegacion + '/';
			if(idTipo == 5)
				numFolio = numFolio + 'EXO/';
			else if(idTipo == 6)
				numFolio = numFolio + 'EX/';
			else if(idTipo == 7)
				numFolio = numFolio + 'SATICA/';
			else if(idTipo == 8)
				numFolio = numFolio + 'SATICB/';
			
			
			document.getElementById('txtFolio').value = numFolio;
			buscaElemento('cbxOrigen');
			$('form#formPromo select#txtFolioNum').prop("disabled", "");
			$('form#formPromo select#txtFolioNum').prop("readonly", "");
			//Agrega las opciones al control
			}
		}).error(function(datas){ 
			validarSesionExpirada(datas);
		}).complete(function(){
			//Instrucciones para el 'complete'
		});
	
	
	
	
	
//	alert(folio);
	
	
}

function generaFolioFinal(){
	var numFolio = $('input#txtFolio').val();
	var numero = 0;
	var subdelegacion = '01';
	var delegacion = '03'; 
	var fecha = new Date();
	var anio = fecha.getFullYear();
	var tokens = numFolio.split("/");
  	if(tokens.length==4){
  		if(tokens[3]==''){
  		 var sFolio = '{"cveDelegacion": "'+delegacion+'", "cveSubdelegacion": "'+subdelegacion+'", "numAnio" : "'+anio+'", "crcTipoCorr":{"cveTipocorr": "6" }}';
  		  var folio = jQuery.parseJSON(sFolio);
  		  //alert (reglaNegocio.id.nombrecontrol);
  		  $.postJSON("promocion/obtenerFolio.do", folio, function(data) {
  			 numFolio = numFolio + data[0].numNumero;
   			
 			  $('input#txtFolio').prop("value", numFolio);
 			  
 			  return  numFolio;
  			}).error(function(data){ 
  				validarSesionExpirada(data);
  			}).complete(function(){
  				//Instrucciones para el 'complete'
  			});
  	}
  		}
	
	
	
	
}

function validar(e, elemento) {
	var tecla = '';
	if(e!=null){
		tecla = (document.all) ? e.keyCode : e.which;
	}
	  
	  if (tecla==13 || elemento.indexOf("dtp") != -1 ) {
		  if(document.getElementById(elemento).value.length > 0){
			  var idTipo = $('form#formPromo select#cgcCatTipo\\.idTipo').val();
			  var idOrigen = $('form#formPromo select#cbxOrigen').val();
			  var idCritS = $('form#formPromo select#cbxCriterioSeleccion').val();
			  var sReglaNegocio = '{"nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
			  var reglaNegocio = jQuery.parseJSON(sReglaNegocio);
			  if(idTipo == '6' && idOrigen == '2' && idCritS == '102' && elemento == 'txtUbicacion'){
					activa('dtpOPE');
					activa('cmdGuardar');
					
				}else{
			  $.postJSON("promocion/consultaRegla.do", reglaNegocio, function(data) {
			  var hijos = data.hijos;
			  var dependencia = data.cveUsuario;
			
			  if(document.getElementById(dependencia) != null && document.getElementById(dependencia).value != ''){
				  hijos = data.nombreCampo
			  }
			  if(hijos != null){
				  var tokens = hijos.split(",");
			  	for(var i=0; i< tokens.length; i++){
			  		if(tokens[i]=='cbxMotivoCancelacion'){
			  			activaCombos('cbxMotivoCancelacion');
			  		}
			  		if(tokens[i]=='cbxTipoObra'){
			  			activaCombos('cbxTipoObra');
			  			llenaDatosTiposObras();
			  		}else{
			  			activa(tokens[i]);
			  		}
			  	}
			  }
			  var hijosIndependientes = data.hijosindependientes;
			  
			  if(hijosIndependientes!=null){
				  var itokens = hijosIndependientes.split(",");
				  for(var i=0; i< itokens.length; i++){
					  if(itokens[i]=='cbxMotivoCancelacion'){
				  			activaCombos('cbxMotivoCancelacion');
				  		}
				  		if(itokens[i]=='cbxTipoObra'){
				  			activaCombos('cbxTipoObra');
				  			llenaDatosTiposObras();
				  		}else{
					  		activa(itokens[i]);
				  		}
				  }
			  }
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
				}
		  
	  }
	  }
	}



function validaConcepto(){
	var concepto = document.getElementById('concepto').value; 
	if(concepto == 'COP'){
		llenaComboPeriodoCOP();
		eliminaOpcionesSelect('form#formPagos select#periodoRCV');
		habilitaCOP(concepto, 'on');
		habilitaRCV(concepto, 'off');
	}else if(concepto == 'RCV'){
		llenaComboPeriodoRCV();
		eliminaOpcionesSelect('form#formPagos select#periodoCOP');
		habilitaRCV(concepto, 'on');	
		habilitaCOP(concepto, 'off');
	}else if(concepto == 'COPRCV'){
		llenaComboPeriodoCOP();
		llenaComboPeriodoRCV();
		habilitaCOP(concepto, 'on');
		habilitaRCV(concepto, 'on');
	}else{
		eliminaOpcionesSelect('form#formPagos select#periodoCOP');
		eliminaOpcionesSelect('form#formPagos select#periodoRCV');
		habilitaCOP(concepto, 'off');
		habilitaRCV(concepto, 'off');
	}
	
}


function validaGuardado(){
	 var guardado = document.getElementById('guardadoHidden').value; 
	 if(guardado == '1'){
		 $('select#cgcCatTipo\\.idTipo').prop("disabled", true);
		 $('select#cbxOrigen').prop("disabled", true);
		 $('#formPromo select#cbxOrigen').removeClass("red");
		 $('select#cbxCriterioSeleccion').prop("disabled", true);
		 $('#formPromo select#cbxCriterioSeleccion').removeClass("red");
		 $('input#txtFolioAnio').prop("disabled", true);
		 $('input#txtFolioNum').prop("disabled", true);
		 $('#formPromo input#txtFolioAnio').removeClass("red");
		 $('#formPromo input#txtFolioNum').removeClass("red");
		 
	 }else if(guardado == '0'){
		 $('select#cgcCatTipo\\.idTipo').prop("disabled", false);
		 $('select#cbxOrigen').prop("disabled", false);;
		 $('select#cbxCriterioSeleccion').prop("disabled", false);
		 $('input#txtFolioAnio').prop("disabled", false);
		 $('input#txtFolioNum').prop("disabled", false);
		 $('#formPromo input#txtFolioAnio').addClass("red");
		 $('#formPromo input#txtFolioNum').addClass("red");
	 }
}

function eliminaOpcionesSelect(selector){
	$(selector).html('');
	$(selector).html("<option value='-1'>--Por favor seleccione--</option>");
}

function habilitaCOP(concepto, opcion){
	if(opcion == 'on'){
		$('form#formPagos input#txtCOPSP' ).prop("disabled", false);
		$('form#formPagos input#txtCOPSP' ).addClass("red");
		$('form#formPagos input#txtCOPAct' ).prop("disabled", false);
		$('form#formPagos input#txtCOPAct' ).addClass("red");
		$('form#formPagos input#txtCOPRec' ).prop("disabled", false);
		$('form#formPagos input#txtCOPRec' ).addClass("red");
	}else{
		$('form#formPagos input#txtCOPSP' ).prop("value", "0.00");
		$('form#formPagos input#txtCOPSP' ).prop("disabled", true);
		$('form#formPagos input#txtCOPSP' ).removeClass("red");
		$('form#formPagos input#txtCOPAct' ).prop("value", "0.00");
		$('form#formPagos input#txtCOPAct' ).prop("disabled", true);
		$('form#formPagos input#txtCOPAct' ).removeClass("red");
		$('form#formPagos input#txtCOPRec' ).prop("value", "0.00");
		$('form#formPagos input#txtCOPRec' ).prop("disabled", true);
		$('form#formPagos input#txtCOPRec' ).removeClass("red");
		$('form#formPagos input#txtCOPTotal').prop("value", "0.00");
		
		 
		
	}
	
}

function habilitaRCV(concepto, opcion){
	if(opcion == 'on'){
		$('form#formPagos input#txtRCVSP' ).prop("disabled", false);
		$('form#formPagos input#txtRCVSP' ).addClass("red");
		$('form#formPagos input#txtRCVAct' ).prop("disabled", false);
		$('form#formPagos input#txtRCVAct' ).addClass("red");
		$('form#formPagos input#txtRCVRec' ).prop("disabled", false);
		$('form#formPagos input#txtRCVRec' ).addClass("red");
	}else{
		$('form#formPagos input#txtRCVSP' ).prop("value", "0.00");
		$('form#formPagos input#txtRCVSP' ).prop("disabled", true);
		$('form#formPagos input#txtRCVSP' ).removeClass("red");
		$('form#formPagos input#txtRCVAct' ).prop("value", "0.00");
		$('form#formPagos input#txtRCVAct' ).prop("disabled", true);
		$('form#formPagos input#txtRCVAct' ).removeClass("red");
		$('form#formPagos input#txtRCVRec' ).prop("value", "0.00");
		$('form#formPagos input#txtRCVRec' ).prop("disabled", true);
		$('form#formPagos input#txtRCVRec' ).removeClass("red");
		$('form#formPagos input#txtRCVTotal').prop("value", "0.00");
		 
	}
	
}
function validarRegPat(e, regPat, digVer) {
	//F7028494100
	  tecla = (document.all) ? e.keyCode : e.which;
	  if (tecla==13 ) {
		  if(document.getElementById(regPat).value.length > 0  && document.getElementById(regPat).value.length <=11 ){
			  if(document.getElementById(regPat).value != '99999999999'){
				  var sPatron = '{"registroPatronal":"'+document.getElementById(regPat).value+'"}';
				  var patron = jQuery.parseJSON(sPatron);
				  //	alert (reglaNegocio.id.nombrecontrol);
				  bloquear();
				  $.postJSON("promocion/validaRegPat.do", patron, function(data) {
					  if(data==null){
						  desbloquear();
						  alert('Ocurrio un error al consultar el patron');
						  document.getElementById(regPat).value = '';
						  document.getElementById('txtNombre').value = '';
						  document.getElementById('txtUbicacion').value = '';
					  }else if(data.codigo=='1001'){
						  desbloquear();
						  alert('El patron se encuentra dado de baja');
						  document.getElementById(regPat).value = '';
						  document.getElementById('txtNombre').value = '';
						  document.getElementById('txtUbicacion').value = '';
					  }else if(data.codigo=='1002'){
						  desbloquear();
						  alert('El patron no existe en el datamart');
						  document.getElementById(regPat).value = '';  
						  document.getElementById('txtNombre').value = '';
						  document.getElementById('txtUbicacion').value = '';
					  }
					  var nombre = '';
					  var idPat = '';
					  if(data != null && data.razonSocial != null){
						  nombre = data.razonSocial;
						  document.getElementById('txtNombre').value = nombre;
					 }
		             if(data != null && data.razonSocial != null){
		            	 idPat = data.cvePK;
		            	 document.getElementById('regPatHidden').value = idPat;
					 }
					 
					 if(data != null && data.fkUbicacion!= null){
						 var sUbicacion = '{"cvePK":"'+data.fkUbicacion+'"}';
						  var ubicacion = jQuery.parseJSON(sUbicacion);  
						  $.postJSON("promocion/consultaUbicacion.do", ubicacion, function(dataU) {
							  document.getElementById('txtUbicacion').value = dataU.calle.replace('%','');
							  document.getElementById('txtUbicacion').value = document.getElementById('txtUbicacion').value.replace('','');
							  $('form#formPromo input#txtUbicacion' ).prop("disabled", false);
							  $('form#formPromo input#txtUbicacion' ).addClass("red");
							  buscaElemento('txtUbicacion');
						  }).error(function(data){ 
							  validarSesionExpirada(data);
						  }).complete(function(){
							 
						  });
					 }
				}).error(function(data){ 
					alert('Ocurrio un error al consultar al patr\u00F3n, intentelo nuevamente por favor');
					document.getElementById(regPat).value = '';
					desbloquear();
					validarSesionExpirada(data);
				}).complete(function(){
					desbloquear();
				});
			  
		  }  
		  }
	  }
	}

function validarRegPatronal(regPat) {
	//F7028494100
	 	  if(document.getElementById(regPat).value.length > 0  && document.getElementById(regPat).value.length <=11 ){
			  if(document.getElementById(regPat).value != '99999999999'){
				  var sPatron = '{"registroPatronal":"'+document.getElementById(regPat).value+'"}';
				  var patron = jQuery.parseJSON(sPatron);
				  //	alert (reglaNegocio.id.nombrecontrol);
				  bloquear();
				  $.postJSON("promocion/validaRegPat.do", patron, function(data) {
					  if(data==null){
						  desbloquear();
						  alert('El patr\u00F3n no existe o no esta activo');
						  document.getElementById(regPat).value = '';
						  document.getElementById('txtNombre').value = '';
						  document.getElementById('txtUbicacion').value = '';
					  }else if(data.codigo=='1001'){
						  desbloquear();
						  alert('El patron se encuentra dado de baja');
						  document.getElementById(regPat).value = '';
						  document.getElementById('txtNombre').value = '';
						  document.getElementById('txtUbicacion').value = '';
					  }else if(data.codigo=='1002'){
						  desbloquear();
						  alert('El patron no existe en el datamart');
						  document.getElementById(regPat).value = '';  
						  document.getElementById('txtNombre').value = '';
						  document.getElementById('txtUbicacion').value = '';
					  }
					  var nombre = '';
					  var idPat = '';
					  if(data != null && data.razonSocial != null){
						  nombre = data.razonSocial;
						  document.getElementById('txtNombre').value = nombre;
					 }
		             if(data != null && data.razonSocial != null){
		            	 idPat = data.cvePK;
		            	 document.getElementById('regPatHidden').value = idPat;
					 }
					 
					 if(data != null && data.fkUbicacion!= null){
						 var sUbicacion = '{"cvePK":"'+data.fkUbicacion+'"}';
						  var ubicacion = jQuery.parseJSON(sUbicacion);  
						  $.postJSON("promocion/consultaUbicacion.do", ubicacion, function(dataU) {
							  document.getElementById('txtUbicacion').value = dataU.calle.replace('%','');
							  document.getElementById('txtUbicacion').value = document.getElementById('txtUbicacion').value.replace('/','');
							  $('form#formPromo input#txtUbicacion' ).prop("disabled", false);
							  $('form#formPromo input#txtUbicacion' ).addClass("red");
							  buscaElemento('txtUbicacion');
							  buscaElemento('txtNombre');
						  }).error(function(data){ 
							  validarSesionExpirada(data);
						  }).complete(function(){
							 
						  });
					 }
					}).error(function(data){ 
						alert('Ocurrio un error al consultar al patr\u00F3n, intentelo nuevamente por favor');
						document.getElementById(regPat).value = '';
						desbloquear();
						validarSesionExpirada(data);
					}).complete(function(data){
						desbloquear();
					});
		 
				 }
		  }  
		  else{
			  alert('El registro patronal es invalido');
			  return false;
		  }
	  
	}
function validarFolioSUA(e, hab, inhab) {
	//F7028494100
	  tecla = (document.all) ? e.keyCode : e.which;
	  if (tecla==13 ) {
		  if(document.getElementById(hab).value == ''){
			  if(inhab == 'txtOrdenIngreso'){
				  if(document.getElementById(hab).value == '' || document.getElementById(hab).value == 0){
					  $('form#formPagos input#'+inhab ).prop("disabled", false);
					  $('form#formPagos input#'+inhab ).addClass("red");
				  }
			  }else{
				  $('form#formPagos input#'+inhab ).prop("disabled", false);
				  $('form#formPagos input#'+inhab ).addClass("red");
			  }
			  
			  
		  }else{
			  $('form#formPagos input#'+inhab ).prop("disabled", true);
			  $('form#formPagos input#'+inhab ).removeClass("red");
		  }
		  

	  }  
	}

function validaFolioSUA(hab, inhab) {
	//F7028494100

		  if(document.getElementById(hab).value == ''){
			  if(inhab == 'txtOrdenIngreso'){
				  if(document.getElementById(hab).value == '' || document.getElementById(hab).value == 0){
					  $('form#formPagos input#'+inhab ).prop("disabled", false);
					  $('form#formPagos input#'+inhab ).addClass("red");
				  }
			  }else{
				  $('form#formPagos input#'+inhab ).prop("disabled", false);
				  $('form#formPagos input#'+inhab ).addClass("red");
			  }
			  
			  
		  }else{
			  $('form#formPagos input#'+inhab ).prop("disabled", true);
			  $('form#formPagos input#'+inhab ).removeClass("red");
		  }
		  

	    
	}

function validarCamposOn(e, hab, inhab) {
	//F7028494100
	  tecla = (document.all) ? e.keyCode : e.which;
	  if (tecla==13 ) {
		  if(document.getElementById(hab).value == ''){
			  $('form#formPagos input#'+inhab ).prop("disabled", true);
			  $('form#formPagos input#'+inhab ).removeClass("red");
		  }else{
			  $('form#formPagos input#'+inhab ).prop("disabled", false);
			  $('form#formPagos input#'+inhab ).addClass("red");
		  }
		  

	  }  
	}

function validarCamposOns(hab, inhab) {
	//F7028494100
	  
		  if(document.getElementById(hab).value == ''){
			  $('form#formPagos input#'+inhab ).prop("disabled", true);
			  $('form#formPagos input#'+inhab ).removeClass("red");
		  }else{
			  $('form#formPagos input#'+inhab ).prop("disabled", false);
			  $('form#formPagos input#'+inhab ).addClass("red");
		  }
		  

	    
	}


function llenaComboPatrones(numFolio){
	
		 var sFolio = '{"cgtPromocion":{"folio": "'+numFolio+'" }}';
		  var folio = jQuery.parseJSON(sFolio);
		 $.postJSON("promocion/consultaAnexoPagos.do", folio, function(data) {
			 var options = "<option value='' >--Por favor seleccione--</option>";
			 if(data!=null)
			   for (var i = 0; i < data.length; i++) {
		         options += "<option value='"+ data[i].idPago +"'>"+ data[i].cgtPromocion.cvePatron + '' + data[i].cgtPromocion.cveModalidad+ "</option>";		     
		       }
			 $('form#formPromo select#regpat').html(options);
			//Agrega las opciones al control
		}).error(function(data){ 
			validarSesionExpirada(data);
		}).complete(function(){
			//Instrucciones para el 'complete'
		});
	
}


function llenaComboOrigen(){
	var idTipo = $('form#formPromo select#cgcCatTipo\\.idTipo').val();
	if(idTipo!= -1){
	 $.postJSON("promocion/consultaOrigen.do", idTipo, function(data) {
		 var options = "<option value='' >--Por favor seleccione--</option>";
		 if(data!=null)
		   for (var i = 0; i < data.length; i++) {
	         options += "<option value='"+ data[i].cgcCatOrigen.idOrigen +"'>"+ data[i].cgcCatOrigen.descOrigen +"</option>";		     
	       }
		 $('select#cbxOrigen').prop("disabled", false);
		 $('select#cbxOrigen').html(options);
		//Agrega las opciones al control
		//limpia el tercer combo
		$('select#cbxCriterioSeleccion').html('');
		$('select#cbxCriterioSeleccion').html("<option value='-1'>--Por favor seleccione--</option>");
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
	}else{
		$('select#cbxOrigen').html('');
		$('select#cbxOrigen').html("<option value='-1'>--Por favor seleccione--</option>");
		$('select#cbxOrigen').prop("disabled", true);
		$('select#cbxCriterioSeleccion').html('');
		$('select#cbxCriterioSeleccion').html("<option value='-1'>--Por favor seleccione--</option>");

	}

}

function llenaCriterioSeleccion(){
	var idTipo = $('form#formPromo select#cgcCatTipo\\.idTipo').val();
	var idOrigen = $('form#formPromo select#cbxOrigen').val();
	var sParam =  '["'+idTipo+'","'+idOrigen+'"]';
	 var param = jQuery.parseJSON(sParam);
	if(idTipo!= -1 && idOrigen != ''){
	 $.postJSON("promocion/consultaCriterios.do", param, function(data) {
		 var options = "<option value='' >--Por favor seleccione--</option>";
		 if(data!=null)
		   for (var i = 0; i < data.length; i++) {
	         options += "<option value='"+ data[i].idCriterioseleccion +"'>"+ data[i].descCriterioseleccion +"</option>";		     
	       }
		 $('select#cbxCriterioSeleccion').prop("disabled", false);
		 $('select#cbxCriterioSeleccion').html(options);
		//Agrega las opciones al control
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
	}else{
		$('select#cbxCriterioSeleccion').html('');
		$('select#cbxCriterioSeleccion').html("<option value='-1'>--Por favor seleccione--</option>");
		 
	}

}


function llenaComboPeriodoCOP(){
	var fechaInicio =  $('input#dtpPeriodoDel').val();
	var fechaFin = $('input#dtpPeriodoAl').val(); 
	var sParam =  '["'+fechaInicio+'","'+fechaFin+'"]';
	 var param = jQuery.parseJSON(sParam);
	$.postJSON("promocion/armaPeriodos.do", param, function(dataC) {
		 var options = "<option value='' >--Por favor seleccione--</option>";
		 if(dataC!=null)
		   for (var i = 0; i < dataC.length; i++) {
	         options += "<option value='"+ dataC[i] +"'>"+ dataC[i] +"</option>";		     
	       }
		 $('form#formPagos select#periodoCOP').html(options);
	
	  }).error(function(data){ 
		  validarSesionExpirada(data);
	  }).complete(function(){
		//Instrucciones para el 'complete'
	  });
	
	
}

function llenaComboPeriodoRCV(){
	var fechaInicio =  $('input#dtpPeriodoDel').val();
	var fechaFin = $('input#dtpPeriodoAl').val(); 
	var sParam =  '["'+fechaInicio+'","'+fechaFin+'"]';
	 var param = jQuery.parseJSON(sParam);
	$.postJSON("promocion/armaBimestres.do", param, function(dataC) {
		 var options = "<option value='' >--Por favor seleccione--</option>";
		 if(dataC!=null)
		   for (var i = 0; i < dataC.length; i++) {
	         options += "<option value='"+ dataC[i] +"'>"+ dataC[i] +"</option>";		     
	       }
		 $('form#formPagos select#periodoRCV').html(options);
	
	  }).error(function(data){ 
		  validarSesionExpirada(data);
	  }).complete(function(){
		//Instrucciones para el 'complete'
	  });
	
	
}




function eliminaAnexoPagos(){
	var idPago = 0;
	if(	$("input[name=radioPromoPagos]:checked").val()!=null){
		idPago = $("input[name=radioPromoPagos]:checked").val();
	}
	else{
		alert('Debe seleccionar un pago');
		return false;
	}
	
	var sPago = '{"idPago":"'+idPago+'"}';
	var pago = jQuery.parseJSON(sPago);
	
	// Buscamos el elemento
	$.postJSON("promocion/eliminaAnexoPagos.do", pago, function(data) {
		 var oTable = $(idDataTable).dataTable(); 
		  oTable.fnDraw();
		  $('select#concepto').val("");
			 var options = "<option value='' >--Por favor seleccione--</option>";
			 $('form#formPagos select#periodoRCV').html(options);
			 $('form#formPagos select#periodoCOP').html(options);
			  
			 $('input#txtFolioSUA').prop("value", "");
			  $('input#txtOrdenIngreso').prop("value", "");
			  $('input#txtNoCredito').prop("value", "");
			  $('input#fechaPagoAnexoPagos').prop("value", "");
			  $('input#txtCOPSP').prop("value", "");
			  $('input#txtCOPAct').prop("value", "");
			  $('input#txtCOPRec').prop("value", "");
			  $('input#txtCOPTotal').prop("value", "");
			  $('input#txtCOPMultas').prop("value", "");
			  
			  $('input#txtRCVSP').prop("value", "");
			  $('input#txtRCVAct').prop("value", "");
			  $('input#txtRCVRec').prop("value", "");
			  $('input#txtRCVTotal').prop("value", "");
			  $('input#txtRCVMultas').prop("value", "");
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
	
}


function suma(e, concepto) {
	//F7028494100
	  tecla = (document.all) ? e.keyCode : e.which;
	  if (tecla==13 ) {
		  var SP = 0.00;
		  var Act = 0.00;
		  var Rec = 0.00;
		  var total = 0.00;
		  if($("#txt"+ concepto + "SP").asNumber() == '' || isNaN($("#txt"+ concepto + "SP").asNumber())){
			  SP = 0.00;
			  document.getElementById("txt"+ concepto + "SP").value = '0.00';
		  }else{
		 	  SP =$("#txt"+ concepto + "SP").asNumber();
		  }
		  if($("#txt"+ concepto + "Rec").asNumber() == '' || isNaN($("#txt"+ concepto + "Rec").asNumber())){
			  Rec = 0.00;
			  document.getElementById("txt"+ concepto + "Rec").value = '0.00';
		  }else{
			  Rec = $("#txt"+ concepto + "Rec").asNumber()
		  }
		  if($("#txt"+ concepto + "Act").asNumber() == '' || isNaN($("#txt"+ concepto + "Act").asNumber())){
			Act = 0.00;
			document.getElementById("txt"+ concepto + "Act").value = '0.00';
		  }else{
			  Act = $("#txt"+ concepto + "Act").asNumber()
		  }
					
			
					
			
			  
			  total = parseFloat(SP).toFixed(2) + parseFloat(Act).toFixed(2) + parseFloat(Rec).toFixed(2);
			  document.getElementById("txt"+ concepto + "Total").value = total;
			  $('form#formPagos input#txt'+ concepto + 'Total').formatCurrency();
	  }  
	}

function sumar(concepto) {
	//F7028494100
	  
	    var SP = 0.00;
		  var Act = 0.00;
		  var Rec = 0.00;
		  var total = 0.00;
		  if($("#txt"+ concepto + "SP").asNumber() == '' || isNaN($("#txt"+ concepto + "SP").asNumber())){
			  SP = 0.00;
			  document.getElementById("txt"+ concepto + "SP").value = '0.00';
		  }else{
		 	  SP =$("#txt"+ concepto + "SP").asNumber();
		  }
		  if($("#txt"+ concepto + "Rec").asNumber() == '' || isNaN($("#txt"+ concepto + "Rec").asNumber())){
			  Rec = 0.00;
			  document.getElementById("txt"+ concepto + "Rec").value = '0.00';
		  }else{
			  Rec = $("#txt"+ concepto + "Rec").asNumber()
		  }
		  if($("#txt"+ concepto + "Act").asNumber() == '' || isNaN($("#txt"+ concepto + "Act").asNumber())){
			Act = 0.00;
			document.getElementById("txt"+ concepto + "Act").value = '0.00';
		  }else{
			  Act = $("#txt"+ concepto + "Act").asNumber()
		  }
					
			
			  
		  	  total = parseFloat(SP.toFixed(2)) + parseFloat(Act.toFixed(2)) + parseFloat(Rec.toFixed(2));
			  document.getElementById("txt"+ concepto + "Total").value = total;
			  $('form#formPagos input#txt'+ concepto + 'Total').formatCurrency();
	}

/**
 * Valida la activaci‹n de los hijos del elemento.
 * @param elemento
 */
function buscaElemento(elemento) {
//alert('elemento :: ' + elemento);
		var idTipo = $('form#formPromo select#cgcCatTipo\\.idTipo').val();
		if(idTipo == -1){
	  		
	  			desactivaCombos('cbxOrigen');
	  		
  		 
  			desactivaCombos('cbxCriterioSeleccion');
  			
	  		
	  	}
		var idOrigen = $('form#formPromo select#cbxOrigen').val();
		if(idOrigen == '-1' || idOrigen == ''){
			desactivaCombos('cbxCriterioSeleccion');
			activa('txtFolioNum');
			generaFolioInicial();
		}
		var idCritS = $('form#formPromo select#cbxCriterioSeleccion').val();
		if(idCritS == '-1' || idCritS == ''){
			desactivaDirecto('txtRP');
			desactivaDirecto('txtNombre');
		}
		if(idTipo == '6' && idOrigen == '2' && idCritS == '102' && elemento == 'txtUbicacion'){
			activa('dtpOPE');
			activa('cmdGuardar');
			
		} else{
			if(idTipo == '6' && idOrigen == '6' && elemento == 'txtUbicacion'){
				activaCombos('cbxTipoObra');
				
			}
			var sReglaNegocio = '{"nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
			  var reglaNegocio = jQuery.parseJSON(sReglaNegocio);
			  //alert (reglaNegocio.id.nombrecontrol);
			  $.postJSON("promocion/consultaRegla.do", reglaNegocio, function(data) {
				  var hijos = data.hijos;
				   if(hijos!=null){
					  var tokens = hijos.split(",");
					for(var i=0; i< tokens.length; i++){
				  	
				  		if(tokens[i]=='cbxOrigen'){
				  			activaCombos('cbxOrigen');
				  			activa(tokens[i]);
				  		}
			  		 else	if(tokens[i]=='cbxCriterioSeleccion'){
				  			activaCombos('cbxCriterioSeleccion');
				  			activa(tokens[i]);
				  		}
			  		 else	if(tokens[i]=='cbxOrigen'){
				  			activa(tokens[i]);
				  		}
			  		 else	if(tokens[i]=='cbxMotivoCancelacion'){
				  			activaCombos('cbxMotivoCancelacion');
				  			activa(tokens[i]);
				  		}
				  		
				  		
			  		 else	if(tokens[i]=='cbxTipoObra'){
				  			activaCombos('cbxTipoObra');
				  			activa(tokens[i]);
				  			llenaDatosTiposObras();
				  		}
			  		 else{
					  		activa(tokens[i]);
				  		}
				  	
				  		
					}
				  }
				  var hijosIndependientes = data.hijosindependientes;
				  
				  if(hijosIndependientes!=null){
					  var itokens = hijosIndependientes.split(",");
					  for(var i=0; i< itokens.length; i++){
						  activa(itokens[i]);
					  }
				  }
				}).error(function(data){ 
					validarSesionExpirada(data);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
		}
		  
	  
}

function buscaElementoCargaDetalle(elemento, idTipo, idOrigen, idCritS) {

	  
	  
		  if(document.getElementById(elemento).value.length > 0){
			  var sReglaNegocio = '{"nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
			  var reglaNegocio = jQuery.parseJSON(sReglaNegocio);
			  $.postJSON("promocion/consultaRegla.do", reglaNegocio, function(data) {
			  var hijos = data.hijos;
			  var dependencia = data.cveUsuario;
			  if(document.getElementById(dependencia) != null && document.getElementById(dependencia).value != ''){
				  hijos = data.nombreCampo
			  }
			  if(hijos != null){
				  var tokens = hijos.split(",");
			  	for(var i=0; i< tokens.length; i++){
			  		if(tokens[i]=='cbxMotivoCancelacion'){
			  			activaCombos('cbxMotivoCancelacion');
			  		}
			  		if(tokens[i]=='cbxTipoObra'){
			  			activaCombos('cbxTipoObra');
			  		}else{
			  			activa(tokens[i]);
			  		}
			  	}
			  }
			  var hijosIndependientes = data.hijosindependientes;
			  
			  if(hijosIndependientes!=null){
				  var itokens = hijosIndependientes.split(",");
				  for(var i=0; i< itokens.length; i++){
					  if(itokens[i]=='cbxMotivoCancelacion'){
				  			activaCombos('cbxMotivoCancelacion');
				  		}
				  		if(itokens[i]=='cbxTipoObra'){
				  			activaCombos('cbxTipoObra');
				  		}else{
					  		activa(itokens[i]);
				  		}
				  }
			  }
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
		  
		  
	  }
	  
}



function buscaElementoDes(elemento) {

	var idTipo = $('form#formPromo select#cgcCatTipo\\.idTipo').val(); 
	var sReglaNegocio = '{"nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
	  var reglaNegocio = jQuery.parseJSON(sReglaNegocio);
	  //alert (reglaNegocio.id.nombrecontrol);
	  $.postJSON("promocion/consultaRegla.do", reglaNegocio, function(data) {
		  var hijos = data.hijos;
		  if(hijos!=null){
			  var tokens = hijos.split(",");
		  	for(var i=0; i< tokens.length; i++){
		  		 if(tokens[i]=='cbxOrigen'){
		  			desactivaCombos('cbxOrigen');
			  		}
		  		 else if(tokens[i]=='cbxCriterioSeleccion'){
		  			desactivaCombos('cbxCriterioSeleccion');
			  	}else if(tokens[i]=='cbxMotivoCancelacion'){
		  			desactivaCombos('cbxMotivoCancelacion');
			  		}
			  	else if(tokens[i]=='cbxTipoObra'){
		  			desactivaCombos('cbxTipoObra');
			  		}
		  		 else{
			  			desactiva(tokens[i]);
			  		}
		  		
		  	}
		  }
		  var hijosIndependientes = data.hijosindependientes;
		  
		  if(hijosIndependientes!=null){
			  var itokens = hijosIndependientes.split(",");
			  for(var i=0; i< itokens.length; i++){
				  desactiva(itokens[i]);
			  }
		  }
		}).error(function(data){ 
			validarSesionExpirada(data);
		}).complete(function(){
			//Instrucciones para el 'complete'
		});
	  
	  

}

function validaFechasCGPromo(campoFecha, limiteInferior, limiteSuperior){
	var sFecha =  document.getElementById(campoFecha).value;
	if(sFecha!= ''){
		var sFechaMinima = '';
		var sFechaMaxima = '';
		if(limiteInferior != 'dtpFechaSistema' && limiteInferior != 'dtpPFechaMin'){
			var tokens = limiteInferior.split(',');
			if(tokens.length>1){
					if(tokens[0]=='dtpNOP' && document.getElementById(tokens[1]).value == ''){
						sFechaMinima = document.getElementById(tokens[0]).value;
					}else if(tokens[0]=='dtpNOP' && document.getElementById(tokens[1]).value != ''){
						sFechaMinima = document.getElementById(tokens[1]).value;
					}
				
			
			}else{
				sFechaMinima = document.getElementById(limiteInferior).value;
			}
		}
		if(limiteSuperior != 'dtpFechaSistema' && limiteSuperior != 'dtpPFechaMax'){
			sFechaMaxima = document.getElementById(limiteSuperior).value;
		}
		
		var hoy = new Date();
		var fecha = new Date();	
		var fechaMaxima = new Date();
		var fechaMinima = new Date();
		
		var anio = sFecha.substring(6,10);
		var mes = sFecha.substring(3,5) ;
		mes = mes - 1; 
		var dia = sFecha.substring(0,2);
		fecha.setFullYear(anio, mes, dia);
		
		if(sFechaMinima != ''){
			anio = sFechaMinima.substring(6,10);
			mes = sFechaMinima.substring(3,5);
			mes = mes - 1 ;
			dia = sFechaMinima.substring(0,2);
			fechaMinima.setFullYear(anio, mes, dia);
		}
		if(sFechaMaxima != ''){
			anio = sFechaMaxima.substring(6,10);
			mes = sFechaMaxima.substring(3,5);
			mes = mes - 1 ;
			dia = sFechaMaxima.substring(0,2);
			fechaMaxima.setFullYear(anio, mes, dia);
		}
		
		if(limiteSuperior == 'dtpPFechaMax'){
			if(!(fecha > fechaMinima || (fecha -0 == fechaMinima -0))){
				var d = sFechaMinima.substring(0,2);
				var m = sFechaMinima.substring(3,5);
			
				var a = sFechaMinima.substring(6,10);
				if(d=='' && m == '' && a == ''){
					alert('La fecha no puede ser menor a hoy');
				}else{
					alert('La fecha no puede ser menor a ' + d +'/'+m+'/'+ a);
				}
				
				$('input#'+campoFecha).prop("value", "");
				buscaElementoDes(campoFecha);
				return false;
			}
		}else if(limiteInferior == 'dtpPFechaMin'){
			var d = sFechaMaxima.substring(0,2);
			var m = sFechaMaxima.substring(3,5);
		
			var a = sFechaMaxima.substring(6,10);
			if(!(fecha < fechaMaxima || (fecha -0 == fechaMaxima -0))){
				if(d == '' && a == '' && m== ''){
					alert('La fecha no puede ser mayor a hoy');
				}else{
					alert('La fecha no puede ser mayor a ' + d +'/'+m+'/'+ a);
				}
				
				$('input#'+campoFecha).prop("value", "");
				buscaElementoDes(campoFecha);
				return false;
			}
		}
		
		else if( !((fecha > fechaMinima || (fecha -0 == fechaMinima -0)) && (fecha < fechaMaxima || (fecha - 0 == fechaMaxima -0))) ){
			if(fecha != ''){
				var dM = sFechaMaxima.substring(0,2);
				var mM = sFechaMaxima.substring(3,5);
			
				var aM = sFechaMaxima.substring(6,10);
				var d = sFechaMinima.substring(0,2);
				var m = sFechaMinima.substring(3,5);
	 		
				var a = sFechaMinima.substring(6,10);
				alert('La fecha no puede ser menor a ' + d +'/'+m+'/'+ a + ' o mayor a hoy' );
				
			}
				
			$('input#'+campoFecha).prop("value", "");
			
			return false;
		}
	}
	return true;
}

function setMensaje(elemento) {
	var idTipo = $('form#formPromo select#cgcCatTipo\\.idTipo').val(); 
	var sReglaNegocio = '{"nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
	  var reglaNegocio = jQuery.parseJSON(sReglaNegocio);
	  var nivel = '';
	  //alert (reglaNegocio.id.nombrecontrol);
	  $.postJSON("promocion/consultaRegla.do", reglaNegocio, function(data) {
		  var hijos = data.hijos;
		  if(data!=null && data.mensaje!=null)
			  document.getElementById("txtMensajeAyuda").value= data.mensaje;
		  else
			  document.getElementById("txtMensajeAyuda").value= '';
		  if(data.cgcCatStatus != null){
			  if(data!=null && data.cgcCatStatus.descStatus != null)
				  document.getElementById("txtStatus").value= data.cgcCatStatus.descStatus;
			  else
				  document.getElementById("txtStatus").value= '';
			  document.getElementById("idStatusHidden").value= data.cgcCatStatus.idStatus;
		  }
		  	}).error(function(data){ 
		  		validarSesionExpirada(data);
		}).complete(function(){
			//Instrucciones para el 'complete'
		});
}

/**
 * Verifica a los del mismo nivel para deshabilitarlos (campos excluyentes)
 * @param elemento
 */
function verificaNivel(elemento) {
	var idTipo = $('form#formPromo select#cgcCatTipo\\.idTipo').val();
	
	var sReglaNegocio = '{"nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
	var reglaNegocio = jQuery.parseJSON(sReglaNegocio);
	$.postJSON("promocion/consultaRegla.do", reglaNegocio, function(data) {
		var nivel = '';  
		if(data!=null && data.nivel != null && data.nivel != ''){
				nivel = data.nivel;
		  }
		   if(nivel != null && nivel != ''){
			  var sReglaNegocioNivel = '{"nivel":"'+nivel +'" , "nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
			  var reglaNegocioNivel = jQuery.parseJSON(sReglaNegocioNivel);
			  $.postJSON("promocion/consultaReglaNivel.do", reglaNegocioNivel, function(dataNivel) {
				  for(i=0;i<dataNivel.length; i++){
					  if(document.getElementById(elemento).value=='' )
						  activa(dataNivel[i].nombrecontrol);
					  else
						  desactiva(dataNivel[i].nombrecontrol);
				  }
				  }).error(function(data){ 
					  validarSesionExpirada(data);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
			 }
		  	}).error(function(data){ 
		  		validarSesionExpirada(data);
		}).complete(function(){
			//Instrucciones para el 'complete'
		});
	
	 
}


function verificaNivelExcluye(elemento, excluye, flujo) {
	var idTipo = $('form#formPromo select#cgcCatTipo\\.idTipo').val();
	
	var sReglaNegocio = '{"nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
	var reglaNegocio = jQuery.parseJSON(sReglaNegocio);
	$.postJSON("promocion/consultaRegla.do", reglaNegocio, function(data) {
		var nivel = '';  
		if(data!=null && data.nivel != null && data.nivel != ''){
				nivel = data.nivel;
		  }
		   if(nivel != null && nivel != ''){
			  var sReglaNegocioNivel = '{"nivel":"'+nivel +'" , "nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
			  var reglaNegocioNivel = jQuery.parseJSON(sReglaNegocioNivel);
			  $.postJSON("promocion/consultaReglaNivel.do", reglaNegocioNivel, function(dataNivel) {
				  for(i=0;i<dataNivel.length; i++){
					  if(document.getElementById(elemento).value=='' ){
						  if(dataNivel[i].nombrecontrol!=excluye && idTipo == flujo)
							  activa(dataNivel[i].nombrecontrol);
					  }else{
						  if(dataNivel[i].nombrecontrol!=excluye && idTipo == flujo)
							  desactiva(dataNivel[i].nombrecontrol);
					  }
				  }
				  }).error(function(data){ 
					  validarSesionExpirada(data);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
			 }
		  	}).error(function(data){ 
		  		validarSesionExpirada(data);
		}).complete(function(){
			//Instrucciones para el 'complete'
		});
	
	 
}

function inhabilitar(e, elemento) {
	
	  if(document.getElementById(elemento) != null && document.getElementById(elemento).value.length == 0){
		  var idTipo = $('form#formPromo select#cgcCatTipo\\.idTipo').val(); 
		  var sReglaNegocio = '{"nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
		  var reglaNegocio = jQuery.parseJSON(sReglaNegocio);
		  //alert (reglaNegocio.id.nombrecontrol);
		  $.postJSON("promocion/consultaRegla.do", reglaNegocio, function(data) {
			  var hijos = data.hijos;
			  if(hijos != null){
				  var tokens = hijos.split(",");
			  	var nivel = data.nivel;
			 // alert(data.hijosindependientes);
				  for(var i=0; i< tokens.length; i++){
						  if(tokens[i]=='cbxMotivoCancelacion'){
							  	eliminaOpcionesSelect('cbxMotivoCancelacion');
								desactivaCombos('cbxMotivoCancelacion');
								buscaElementoDes(tokens[i]);
					  		}
						  else if(tokens[i]=='cbxTipoObra'){
							  eliminaOpcionesSelect('cbxTipoObra');
							 	desactivaCombos('cbxTipoObra');
							 	buscaElementoDes(tokens[i]);
					  		}else{
					  			desactiva(tokens[i]);
					  			inhabilitar(e, tokens[i]);
					  		}
				  		
				  }
			  }
			  var hijosIndependientes = data.hijosindependientes;
			  
			  if(hijosIndependientes!=null){
				  var itokens = hijosIndependientes.split(",");
				  for(var i=0; i< itokens.length; i++){
						  if(itokens[i]=='cbxMotivoCancelacion'){
							  
							  desactivaCombos('cbxMotivoCancelacion');
							  inhabilitar(e, itokens[i]);
					  		}
						  else if(itokens[i]=='cbxTipoObra'){
							  
							  desactivaCombos('cbxTipoObra');
							  inhabilitar(e, itokens[i]);
					  		}else{
					  			
					  			desactiva(itokens[i]);
					  			inhabilitar(e, itokens[i]);
					  		}
					 
				  }
			  }
			 
			
			  
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
		  
		  
	  }
	  }

function nuevaPromocion(){
	location.reload();
}
	

function listarPromociones(){
    enviaAPaginar();
	oDgBuscar.dialog('open');
}


function cargaDetallePromocion(){
	
	var radios = document.getElementsByName("radio");
	 var seleccionado = 0;
		for (i=0;i<radios.length;i++)
		 {
			if(radios[i].checked)
			{
				seleccionado = 1;
				var folio = radios[i].value;
				var sPromocion = '{"folio" : "'+folio+'"}';
				  var promocion = jQuery.parseJSON(sPromocion);
				  jQuery.ajaxSetup({async:false});
				  jQuery.ajax({
					async: false,
				    type: 'POST',
				    url: 'promocion/consultaPromocion.do',
				    data: sPromocion, // or JSON.stringify ({name: 'jonas'}),
				    success: function(data) { 
				    	oDgBuscar.dialog('close');
				    	 $('#txtNoParcialidades').prop("value", data.noparcialidades);
						  $('select#cgcCatTipo\\.idTipo').html('');
						  $('select#cgcCatTipo\\.idTipo').html("<option value='"+data.cgcCatTipo.idTipo+"'>"+data.cgcCatTipo.descripcion+"</option>");
						  $('select#cgcCatTipo\\.idTipo').prop('disabled', true);
						 
						  $('select#cbxOrigen').html('');
						  $('select#cbxOrigen').html("<option value='"+data.cgcCatOrigen.idOrigen+"'>"+data.cgcCatOrigen.descOrigen+"</option>");
						  $('select#cbxOrigen').prop('disabled', true);
						 
						  
						  $('select#cbxCriterioSeleccion').html('');
						  $('select#cbxCriterioSeleccion').html("<option value='"+data.cgtCatCriterioSeleccion.idCriterioseleccion+"'>"+data.cgtCatCriterioSeleccion.descCriterioseleccion+"</option>");
						 $('select#cbxCriterioSeleccion').prop('disabled', true);
						 $('select#cbxCriterioSeleccion').removeClass("red");
					 	 
						 var tokens = data.folio.split("/");
						 
						 
				    	  $('#txtFolio').prop("value", tokens[0]+'/'+ tokens[1]+'/');
				    	  $('#txtFolioAnio').prop("value", tokens[2]);
				    	  $('#txtFolioNum').prop("value", tokens[3]);
						  buscaElementoCargaDetalle('txtFolio', data.cgcCatTipo.idTipo);
						  
						  
						  
						  
						  activaDirectos('txtRP', jQuery.trim(data.cvePatron) + jQuery.trim(data.cveModalidad), 2);
						  buscaElementoCargaDetalle('txtRP', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
						  
						  if(data.nombre!=null && data.nombre.length > 0){
							  activaDirectos('txtNombre', data.nombre, 2);
							  buscaElementoCargaDetalle('txtNombre', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
						  }else{
							  activaDirectos('txtNombre', '', 1);
						  }
						  if(data.ubicacion!=null && data.ubicacion.length > 0){
							  activaDirectos('txtUbicacion', data.ubicacion, 2);
							  buscaElementoCargaDetalle('txtUbicacion', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							  buscaElemento('txtUbicacion');
						  }else{
							  activaDirectos('txtUbicacion', '', 2);
						  }
						  if(data.mesesestimados != null && data.mesesestimados != ''){
							  activaDirectos('txtMesesEstimados', data.mesesestimados, 2);
							  buscaElementoCargaDetalle('txtMesesEstimados', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
						  }else{
							  if(document.getElementById('txtMesesEstimados').disabled == true){
								  activaDirectos('txtMesesEstimados', '', 1);
							  }
							  else{
								  $('#txtMesesEstimados').prop("value", "");
							  }
						  }
						  if(data.esttrabajreg != null && data.esttrabajreg != '' ){
							  activaDirectos('txtEstimadoTrabReg', data.esttrabajreg, 2);
							  buscaElementoCargaDetalle('txtEstimadoTrabReg', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
						  }else{
							  if(document.getElementById('txtEstimadoTrabReg').disabled == true){
								  activaDirectos('txtEstimadoTrabReg', '', 1);
							  }
								  
						  }
						  if(data.afil15 != null && data.afil15!= '' ){
							  activaDirectos('txtAFLI15', data.afil15, 2);
							  buscaElementoCargaDetalle('txtAFLI15', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
						  }else{
							  if(document.getElementById('txtAFLI15').disabled == true){
								  activaDirectos('txtAFLI15', '', 1);
							  }
						  }
						  if(data.superficieestimada != null && data.superficieestimada != ''){
							  activaDirectos('txtSuperficieEstimada', data.superficieestimada, 2);
							  buscaElementoCargaDetalle('txtSuperficieEstimada', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
						  }else{
							  if(document.getElementById('txtSuperficieEstimada').disabled == true){
								  activaDirectos('txtSuperficieEstimada', '', 1);
							  }
						  }
						  if(data.costototalcontratado != null && data.costototalcontratado != ''){
							  activaDirectos('txtCostoTotalContratado', data.costototalcontratado, 2);
							  buscaElementoCargaDetalle('txtCostoTotalContratado', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
						  }else{
							  if(document.getElementById('txtCostoTotalContratado').disabled == true){
							  activaDirectos('txtCostoTotalContratado','',1);
							  }
						  }
						  if(data.porcavanceestimado != null && data.porcavanceestimado.length != ''){
							  activaDirectos('txtPorcAvanceEstimado', data.porcavanceestimado, 2);
							  buscaElementoCargaDetalle('txtPorcAvanceEstimado', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
						  }else{
							  if(document.getElementById('txtPorcAvanceEstimado').disabled == true){
							  activaDirectos('txtPorcAvanceEstimado', '', 1);
							  }
						  }
						  if(data.ope != null && data.ope.length > 0){
							  activaDirectos('dtpOPE', desformateaFecha(data.ope), 2);
							  buscaElementoCargaDetalle('dtpOPE', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 // verificaNivel('dtpOPE');
							  buscaElemento('dtpOPE');
						  }else{
							  if(document.getElementById('dtpOPE').disabled == true){
								  activaDirectos('dtpOPE', '', 1);
							  }
						  }
						  if(data.rnooficioope != null && data.rnooficioope != ''){
							  activaDirectos('txtNoOficioOPE', data.rnooficioope, 2);
							  buscaElementoCargaDetalle('txtNoOficioOPE', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
						  }else{
							  if(document.getElementById('txtNoOficioOPE').disabled == true){
								  activaDirectos('txtNoOficioOPE', '', 1);
							  }
						  }
						  var numTrabs = 0;
						  if(data.trabrevisados != null && data.trabrevisados != ''){
							  activaDirectos('txtTrabRevisados', data.trabrevisados, 2);
							  buscaElementoCargaDetalle('txtTrabRevisados', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
						  }else{
							  if(document.getElementById('txtTrabRevisados').disabled == true){
								  activaDirectos('txtTrabRevisados', '0', 1);
							  }
						  }
						  if(data.trabomisos != null && data.trabomisos != ''){
							  activaDirectos('txtTrabOmisos', data.trabomisos, 2);
							  numTrabs = numTrabs + data.trabomisos;
							  buscaElementoCargaDetalle('txtTrabOmisos', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
						  }else{
							  if(document.getElementById('txtTrabOmisos').disabled == true){
								  activaDirectos('txtTrabOmisos', '0', 1);
							  }
						  }
						  if(data.trabsubddeclarados != null && data.trabsubddeclarados != ''){
							  activaDirectos('txtTrabSubdeclarados', data.trabsubddeclarados, 2);
							  numTrabs = numTrabs + data.trabsubddeclarados;
							  buscaElementoCargaDetalle('txtTrabSubdeclarados', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
						  }else{
							  if(document.getElementById('txtTrabSubdeclarados').disabled == true){
								  activaDirectos('txtTrabSubdeclarados', '0', 1);
							  }
						  }
						  
						  $('#txtTrabRegularizados').prop('value', numTrabs);
						  document.getElementById('txtTrabRegularizados').value = numTrabs;
						  if(data.nop != null && data.nop != ''){
							  activaDirectos('dtpNOP', desformateaFecha(data.nop), 2);
							  inhabilitar(null, 'dtpNOP')
							  buscaElementoCargaDetalle('dtpNOP', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							  verificaNivel('dtpNOP');
						  }else{
							  
							  if(data.c != null && data.c != ''){
								  if(document.getElementById('dtpNOP').disabled == true){
								  activaDirectos('dtpNOP', '', 1);
							  }
							  }
						  }
						  if(data.c != null && data.c != ''){
							  activaDirectos('dtpC', desformateaFecha(data.c), 2);
							  buscaElementoCargaDetalle('dtpC', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							  inhabilitar(null, 'dtpC');
						  }else{
							if (data.nop != null && data.nop != ''){
								 if(document.getElementById('dtpC').disabled == true){
								activaDirectos('dtpC', '', 1);
								 }
							}
						  }
						  if(data.aop != null && data.aop != ''){
							  activaDirectos('dtpAOP', desformateaFecha(data.aop),2);
							  buscaElementoCargaDetalle('dtpAOP', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							  verificaNivelExcluye('dtpAOP', 'dtpOI', '6');
							  verificaNivelOI('dtpAOP', 'dtpPAI', '7');
							  verificaNivelOI('dtpPAI', 'dtpAOP', '7');
						  }else{
						   if(data.pai != null && data.pai != ''){
							   if(document.getElementById('dtpAOP').disabled == true){
								activaDirectos('dtpAOP', '', 1);
							   }
							  }
						  }
						  if(data.nooficioc != null && data.nooficioc != ''){
							  activaDirectos('txtNoOficioC', data.nooficioc,2);
//							  alert('after activaDirectos(txtNoOficioC)');
							  buscaElementoCargaDetalle('txtNoOficioC', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 
						  }else{
							  if(document.getElementById('txtNoOficioC').disabled == true){
								  	activaDirectos('txtNoOficioC', '',1);
							  }
						  }
						  if(data.sp != null && data.sp != ''){
							  activaDirectos('dtpSP', desformateaFecha(data.sp), 2);
							  buscaElementoCargaDetalle('dtpSP', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							  verificaNivel('dtpSP');
						  }else{
							  if(document.getElementById('dtpSP').disabled == true){
								  activaDirectos('dtpSP', '', 1);
							  }
						  }
						  if(data.pai != null && data.pai != ''){
							  activaDirectos('dtpPAI', desformateaFecha(data.pai),2);
							  buscaElementoCargaDetalle('dtpPAI', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							  verificaNivel('dtpPAI');
							  verificaNivelOI('dtpAOP', 'dtpPAI', '6'); 
							  verificaNivelOI('dtpOI', 'dtpPAI', '6')
//							  alert('after verificaNivelOI(dtpAOP) 2603');
						  }else{
							  if(data.aop != null && data.aop != ''){
								  if(document.getElementById('dtpPAI').disabled == true){
									  activaDirectos('dtpPAI', '', 1);
								  }
								  //alert('after activaDirectos(dtpPAI) 2607');
							  }
						  }
						  if(data.oi != null && data.oi != ''){
							  activaDirectos('dtpOI', desformateaFecha(data.oi), 2);
							  buscaElementoCargaDetalle('dtpOI', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							  verificaNivel('dtpOI');
							  verificaNivelOI('dtpAOP', 'dtpOI', '5')
						  }else{
							  if(document.getElementById('dtpOI').disabled == true){
								  activaDirectos('dtpOI', '',1);
							  }
						  }
						  if(data.pr != null && data.pr != ''){
							  activaDirectos('dtpPR', desformateaFecha(data.pr), 2);
							  buscaElementoCargaDetalle('dtpPR', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							  verificaNivel('dtpPR');
						  }else{
							  if(document.getElementById('dtpPR').disabled == true){
								  activaDirectos('dtpPR', '', 1);
							  }
						  }
						  if(data.periododel != null && data.periododel != ''){
							  activaDirectos('dtpPeriodoDel', desformateaFecha(data.periododel), 2);
							  buscaElementoCargaDetalle('dtpPeriodoDel', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							  verificaNivel('dtpPeriodoDel');
						  }else{
							  if(document.getElementById('dtpPeriodoDel').disabled == true){
								  activaDirectos('dtpPeriodoDel', '', 1);
							  }
						  }
						  if(data.periodoal != null && data.periodoal != ''){
							  activaDirectos('dtpPeriodoAl', desformateaFecha(data.periodoal), 2);
							  buscaElementoCargaDetalle('dtpPeriodoAl', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							  verificaNivel('dtpPeriodoAl');
							  inhabilitar(null, 'dtpPeriodoAl');
							  validar(null, 'dtpPeriodoAl');
						 }else{
							 if(document.getElementById('dtpPeriodoAl').disabled == true){
								 activaDirectos('dtpPeriodoAl', '', 1);
							 }
						 }						  
						  if(data.cr != null && data.cr != ''){
							  activaDirectos('dtpCR', desformateaFecha(data.cr),2);
							  buscaElementoCargaDetalle('dtpCR', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							  verificaNivel('dtpCR');
						  }else{
							  if(document.getElementById('dtpCR').disabled == true){
								  activaDirectos('dtpCR', '', 1);  
							  }
						  }
						  
						  if(data.porcregularizado != null && data.porcregularizado != ''){
						  
							  activaDirectos('txtPorcRegularizado', data.porcregularizado, 2);
							  buscaElementoCargaDetalle('txtPorcRegularizado', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
//							  alert('after buscaElementoCargaDetalle(txtPorcRegularizado)');
						  }else{
							  if(document.getElementById('txtPorcRegularizado').disabled == true){
								  activaDirectos('txtPorcRegularizado', '', 1);
							  }
						  }
						  if(data.noconvenio != null && data.noconvenio != ''){
							  activaDirectos('txtNoConvenio', data.noconvenio, 2);
							  buscaElementoCargaDetalle('txtNoConvenio', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
//							  alert('after buscaElementoCargaDetalle(txtNoConvenio)');
						  }else{
							  if(document.getElementById('txtNoConvenio').disabled == true){
								  activaDirectos('txtNoConvenio', '', 1);
							  }
//							  alert('after activaDirectos(txtNoConvenio)');
						  }
						  if(data.porcavance != null && data.porcavance != ''){
							  activaDirectos('txtPorcAvance', data.porcavance, 2);
							  buscaElementoCargaDetalle('txtPorcAvance', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
						  }else{
							  if(document.getElementById('txtPorcAvance').disabled == true){
							  activaDirectos('txtPorcAvance', '', 1);
							  }
						  }
						  if(data.noparcialidades != null && data.noparcialidades != ''){
							  activaDirectos('txtNoParcialidades', data.noparcialidades, 2);
							  buscaElementoCargaDetalle('txtNoParcialidades', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							  
						  }else{
							  if(document.getElementById('txtNoParcialidades').disabled == true){
							  activaDirectos('txtNoParcialidades', '', 1);
							  }
						  }
						  if(data.idCodigoobra != null && data.idCodigoobra != ''){
							  $('select#cbxTipoObra').val(data.idCodigoobra);
							  buscaElementoCargaDetalle('cbxTipoObra', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							  
						  }
						  if(data.idMotivocancelacion != null && data.idMotivocancelacion != '' && data.idMotivocancelacion != '-1'){
							  $('select#cbxMotivoCancelacion').val(data.idMotivocancelacion);
							  buscaElementoCargaDetalle('cbxMotivoCancelacion', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							  
						  }
						  if(data.copconvsp != null && data.copconvsp != ''){
							  activaDirectos('txtCOPConvSP', data.copconvsp, 2);
							  buscaElementoCargaDetalle('txtCOPConvSP', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							  
						  }else{
							  if(document.getElementById('txtCOPConvSP').disabled == true){
								  activaDirectos('txtCOPConvSP', '', 1);
							  }
						  }
						  if(data.rcvconvsp != null && data.rcvconvsp != ''){
							  activaDirectos('txtRCVConvSP', data.rcvconvsp, 2);
							  buscaElementoCargaDetalle('txtRCVConvSP', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							  
						  }else{
							  if(document.getElementById('txtRCVConvSP').disabled == true){
								  activaDirectos('txtRCVConvSP', '', 1);
							  }
						  }if(data.observaciones != null && data.observaciones != ''){
							  activaDirectos('txtObservaciones', data.observaciones, 2);
							  buscaElementoCargaDetalle('txtObservaciones', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							  
						  }else{
							  if(document.getElementById('txtObservaciones').disabled == true){
								  activaDirectos('txtObservaciones', '', 1);
							  }
						  }
						  
						  var numFolio = data.folio;
						  cargaDetallePagos(numFolio, data.cgcCatTipo.idTipo);

							  document.getElementById('guardadoHidden').value = 1;
							
							 // ajustarCamposCargaDetallePromocion(data);
							  
							  
				    },
				    contentType: "application/json"
				    
				});
				  break;
		 }
			
		}
		if(seleccionado == 0){
			alert('Debe seleccionar una promoci\u00F3n');
		}
}





function desformateaFecha(sFecha){
	var fecha = '';
	if(sFecha != ''){
		var anio = sFecha.substring(0,4);
		var mes = sFecha.substring(5,7) ;
		var dia = sFecha.substring(8,10);
		fecha = fecha +dia + '/' + mes + '/' +anio ;
	}
	return fecha;
	
}


function validaGuardadoAl(){
	fgGuardaPromocion();
	
}


function guardaPromocion(folioF){
	
	
	 $.postJSON("promocion/verificaFolioExistente.do", folioF, function(dataCF) {
		 
		if(dataCF==null){
		  	
	
		if (document.getElementById('txtCOPConvSP').disabled == false && document.getElementById('txtRCVConvSP').disabled == false){
			if(document.getElementById('txtCOPConvSP').value == ''){
				alert('Debe introducir el monto de la SP Determinada de COP')
				return false;
			}
			if(document.getElementById('txtRCVConvSP').value == ''){
				alert('Debe introducir el monto de la SP Determinada de RCV')
				return false;
			}
		}
		
			var regPat =  $('form#formPromo input#txtRP').val();
			var numFolio = $('input#txtFolio').val()+ $('input#txtFolioAnio').val() + '/' +$('input#txtFolioNum').val() ;;
			var numero = 0;
			var fechaFolio = new Date();
			var anio = fechaFolio.getFullYear();
			var tokens = numFolio.split("/");
			bloquear();
		  	desbloquear();
			var folio = folioF;
			var folioNum = $('form#formPromo input#txtFolioNum').val();
			
			var afil15 = $('form#formPromo input#txtAFLI15').val();
			var idTipo = $('form#formPromo select#cgcCatTipo\\.idTipo').val(); 
			var idOrigen =$('form#formPromo select#cbxOrigen').val(); 
			var idCriterioSel = $('form#formPromo select#cbxCriterioSeleccion').val();
			var cvePatron = regPat;
			var nombre = $('form#formPromo input#txtNombre').val(); 
			var ubicacion = $('form#formPromo input#txtUbicacion').val();
			var mesesEstimados = $('form#formPromo input#txtMesesEstimados').val();
			var estTrabajReg = $('form#formPromo input#txtEstimadoTrabReg').val();
			var afil15 = $('form#formPromo input#txtAFLI15').val();
			var idCodigoObra =  $('form#formPromo select#cbxTipoObra').val();
			var superficieEstimada =  $('form#formPromo input#txtSuperficieEstimada').val();
			var costoTotalContratado = $('form#formPromo input#txtCostoTotalContratado').val();
			var porcAvanceEstimado = $('form#formPromo input#txtPorcAvanceEstimado').val();
			var ope = formateaFecha($('form#formPromo input#dtpOPE').val());
			var rNoOficioOPE = $('form#formPromo input#txtNoOficioOPE').val();
			var nop = formateaFecha($('form#formPromo input#dtpNOP').val());
			var aop = formateaFecha($('form#formPromo input#dtpAOP').val());
			var pai = formateaFecha($('form#formPromo input#dtpPAI').val());
			var oi = formateaFecha($('form#formPromo input#dtpOI').val());
			var sp = formateaFecha($('form#formPromo input#dtpSP').val());
			var pr = formateaFecha($('form#formPromo input#dtpPR').val());
			var cr = formateaFecha($('form#formPromo input#dtpCR').val());
			var periodoDel =formateaFecha($('form#formPromo input#dtpPeriodoDel').val());
			var periodoAl = formateaFecha($('form#formPromo input#dtpPeriodoAl').val());
			var noConvenio = $('form#formPromo input#txtNoConvenio').val();
			var noParcialidades = $('form#formPromo input#txtNoParcialidades').val();
			var porcRegularizado = $('form#formPromo input#txtPorcRegularizado').val();
			var porcAvance = $('form#formPromo input#txtPorcAvance').val();
			var copconvsp = $('form#formPromo input#txtCOPConvSP').val();
			var rcvconvsp = $('form#formPromo input#txtRCVConvSP').val();
			var trabRevisados = $('form#formPromo input#txtTrabRevisados').val();
			var trabOmisos = $('form#formPromo input#txtTrabOmisos').val();
			var trabSubdeclarados = $('form#formPromo input#txtTrabSubdeclarados').val();
			var idStatus = $('form#formPromo input#idStatusHidden').val();
			//var folioCorreccion = $('form#formPromo input#txtObservaciones').val();
			var observaciones = $('form#formPromo input#txtObservaciones').val();
			var c = formateaFecha($('form#formPromo input#dtpC').val());
			var idMotivoCancelacion = $('form#formPromo select#cbxMotivoCancelacion').val(); 
			var noOficionC = $('form#formPromo input#txtNoOficioC').val();
			var fecFechaReg = "";
			var cveUsuario = "";
			//folio = folio +folioNum;
			bloquear();
			 var sPromocion = '{' +
				 '"folio":"'+folio+'",'+
				 '"folioNum":"'+folioNum+'",'+
				 '"cgcCatTipo": {"idTipo": "'+idTipo+'"},'+
				 '"cgcCatOrigen" : {"idOrigen":"'+idOrigen+'"},'+
				 '"cgtCatCriterioSeleccion": {"idCriterioseleccion":"'+idCriterioSel+'"},'+
				 '"cvePatron":"'+cvePatron+'",'+
				 '"nombre":"'+nombre+'",'+
				 '"ubicacion":"'+ubicacion+'",'+
				 '"mesesestimados":"'+mesesEstimados+'",'+
				 '"esttrabajreg":"'+estTrabajReg+'",'+
				 '"afil15":"'+afil15+'",'+
				 '"idCodigoobra":"'+idCodigoObra+'",'+
				 '"superficieestimada":"'+superficieEstimada+'",'+
				 '"costototalcontratado":"'+costoTotalContratado+'",'+
				 '"porcavanceestimado":"'+porcAvanceEstimado+'",'+
				 '"ope":"'+ope+'",'+
				 '"rnooficioope":"'+rNoOficioOPE+'",'+
				 '"nop":"'+nop+'",'+
				 '"aop":"'+aop+'",'+
				 '"pai":"'+pai+'",'+
				 '"oi":"'+oi+'",'+
				 '"sp":"'+sp+'",'+
				 '"pr":"'+pr+'",'+
				 '"cr":"'+cr+'",'+
				 '"periododel":"'+periodoDel+'",'+
				 '"periodoal":"'+periodoAl+'",'+
				 '"noconvenio":"'+noConvenio+'",'+
				 '"noparcialidades":"'+noParcialidades+'",'+
				 '"porcregularizado":"'+porcRegularizado+'",'+
				 '"porcavance":"'+porcAvance+'",'+
				 '"copconvsp":"'+copconvsp+'",'+
				 '"rcvconvsp":"'+rcvconvsp+'",'+
				 '"trabrevisados":"'+trabRevisados+'",'+
				 '"trabomisos":"'+trabOmisos+'",'+
				 '"trabsubddeclarados":"'+trabSubdeclarados+'",'+
				 '"idStatus":"'+idStatus+'",'+
				 '"observaciones":"'+observaciones+'",'+
				 '"c":"'+c+'",'+
				 '"idMotivocancelacion"	:"'+idMotivoCancelacion+'",'+
				 '"nooficioc":"'+noOficionC+'",'+
				 '"fecFechareg":"'+fecFechaReg+'",'+
				 '"cveUsuario":"'+cveUsuario+'"}';
			  var promocion = jQuery.parseJSON(sPromocion);
			  var nivel = '';
			  desbloquear();
			  bloquear();
			  //alert (reglaNegocio.id.nombrecontrol);
			  $.postJSON("promocion/guardaPromocion.do", promocion, function(data) {
				  document.getElementById('guardadoHidden').value = 1;
				  validaGuardado();
				
				  	}).error(function(data){ 
				  		validarSesionExpirada(data);
				}).complete(function(){
					desbloquear();//Instrucciones para el 'complete'
				});
			  
			  
		}else{
			alert("El folio ya existe");
		}
		
	 }).error(function(data){ 
	  		validarSesionExpirada(data);
	}).complete(function(){
		desbloquear();//Instrucciones para el 'complete'
	});
}

function validaActivacion(campo){

	if(document.getElementById(campo).value.length > 0 || document.getElementById(campo).value!=''){
		$('#'+campo).prop("disabled", false);
	}
}


function verificaDatos(){
			if(document.getElementById('txtRP').disabled == false){
  				if(document.getElementById('txtRP').value == ''){
  					alert("No podra capturar este valor hasta que esten completos los datos del patron (RP, AFIL15/RO)");
  					document.getElementById('dtpSP').value = '';
  					return false;
  				}
  			}
			if(document.getElementById('txtAFLI15').disabled == false){
  				if( document.getElementById('txtAFLI15').value == ''){
  					alert("No podra capturar este valor hasta que esten completos los datos del patron (RP, AFIL15/RO)");
  					document.getElementById('dtpSP').value = '';
  					return false;
  				}
  			}
  			validar(event, 'dtpSP');
  			inhabilitar(event, 'dtpSP');
  			verificaNivel('dtpSP');
  			validaFechas('dtpSP', 'dtpAOP', 'dtpFechaSistema');
  			
  			
  			
}



function desabilitaForma(){
	$('#formPromo :input:text').prop("value", "");
	$('#formPromo :input:text').prop("disabled", true);
	 $('#formPromo :input:text').removeClass("red");
	 document.getElementById('txtObservaciones').disabled = false;	
	 $('#formPromo input#cmdGuardar').removeClass("red");
	 $('#formPromo input#cmdGuardar').prop("disabled", true);
	
	
	 $('#formPromo :input:text').removeClass("red");
	 $('#formPromo input#cmdGuardar').removeClass("red");
	 
	 $('select#cbxTipoObra').val("-1");
	 $('select#cbxTipoObra').prop("disabled", true);
	 $('select#cbxTipoObra').removeClass("red");
	 
	 var idTipo = $('select#cgcCatTipo\\.idTipo').val();
	//  if(idTipo == '-1' || idTipo == -1 ){
		// $('#formPromo :select').html('');
		 //$('form#formPromo :select').html("<option value=''>--Por favor seleccione--</option>");
		 //$('form#formPromo :select').removeClass("red");
	// }
	 
	 
	
}



function cambio(){
	alert('elemento cambio');
}


function llenaDatosMotCanc(){
	var idTipo = $('form#formPromo input#dtpC').val();
	if(idTipo!= ''){
	 $.postJSON("promocion/consultaMotivoCancelacion.do", '1', function(data) {
		 var options = "<option value='' >--Por favor seleccione--</option>";
		 if(data!=null)
		   for (var i = 0; i < data.length; i++) {
	         options += "<option value='"+ data[i].idMotivocancelacion +"'>"+ data[i].motivocancelacion +"</option>";		     
	       }
		 $('select#cbxMotivoCancelacion').prop("disabled", false);
		 $('select#cbxMotivoCancelacion').html(options);
		 $('select#cbxMotivoCancelacion').addClass("red");
		 
		//Agrega las opciones al control
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
	}else{
		$('select#cbxMotivoCancelacion').html('');
		$('select#cbxMotivoCancelacion').html("<option value='-1'>--Por favor seleccione--</option>");
		 $('select#cbxMotivoCancelacion').prop("disabled", true);
		 $('select#cbxMotivoCancelacion').removeClass("red");
		 
			

	}
}

function llenaDatosTiposObras(){
	
	var idTipo = $('select#cgcCatTipo\\.idTipo').val();
	var idOrigen = $('form#formPromo select#cbxOrigen').val();
	var ubica = $('input#txtUbicacion').val();
	var idCriterioSel = $('form#formPromo select#cbxCriterioSeleccion').val();
	if((idTipo== '7' || (idTipo == '6' && idOrigen == 6)) && ubica != '' ){
	 $.postJSON("promocion/consultaTiposObras.do", idCriterioSel, function(data) {
		 var options = "<option value='' >--Por favor seleccione--</option>";
		 if(data!=null)
		   for (var i = 0; i < data.length; i++) {
	         options += "<option value='"+ data[i].id.idCodigoobra +"'>"+ data[i].tipoobra +"</option>";		     
	       }
		 $('select#cbxTipoObra').prop("disabled", false);
		 $('select#cbxTipoObra').html(options);
		 $('select#cbxTipoObra').addClass("red");
		 
		//Agrega las opciones al control
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
	}else{
		$('select#cbxTipoObra').html('');
		$('select#cbxTipoObra').html("<option value='-1'>--Por favor seleccione--</option>");
		 $('select#cbxTipoObra').prop("disabled", true);
		 $('select#cbxTipoObra').removeClass("red");
		 
			

	}
}


function cancelarPromocion(){
	$('#formPagos :input:text').prop("value", "");
	
	 
}



function enviaAPaginar(){
	var oTable = $(idBuscaTable).dataTable(); 
	  
	  oTable.fnDraw();
}



function verificaNivelOI(elemento1, elemento2, nivel) {
	var idTipo = $('select#cgcCatTipo\\.idTipo').val();
	if(idTipo + '' == nivel){
		if(document.getElementById(elemento1).disabled == false && document.getElementById(elemento1).value == '' && document.getElementById(elemento2).value != ''){
			document.getElementById(elemento1).disabled = true;
			$('#'+elemento1).removeClass("red");
		}else if(document.getElementById(elemento1).disabled == true && document.getElementById(elemento1).value == '' && document.getElementById(elemento1).value == ''){
			document.getElementById(elemento1).disabled = false;
			$('#'+elemento1).addClass("red");
		}
	}
}



function cargaDetallePagos(numFolio, idTipo){
	 
		 var numFolio =  document.getElementById('txtFolio').value+ $('input#txtFolioAnio').val() + '/' +$('input#txtFolioNum').val() ;;
		 var sFolio = '{"folio": "'+numFolio+'" }';
		  var folio = jQuery.parseJSON(sFolio);
		  var totalcopsp = 0;
		  var totalrcvsp = 0;
		  var totalcopact = 0;
		  var totalrcvact = 0;
		  var totalcoprec = 0;
		  var totalrcvrec = 0;
		 $.postJSON("promocion/consultaAnexoPagos.do", folio, function(data) {
			 var options = "<option value='-1' >--Por favor seleccione--</option>";
			 if(data!=null){
			   for (var i = 0; i < data.length; i++) {
				 totalcopsp = totalcopsp + data[i].copsp;
				 totalrcvsp = totalrcvsp + data[i].rcvsp;
				 totalcopact = totalcopact + data[i].copact;
				 totalrcvact =totalrcvact + data[i].rcvact;
				 totalcoprec = totalcoprec + data[i].coprec;
				 totalrcvrec = totalrcvrec + data[i].rcvrec;
			   }
			   
			 }
			 var totalCOP = 0;
			 totalCOP = totalcopsp + totalcopact + totalcoprec;
			 var totalRCV = 0;
			 totalRCV = totalrcvsp + totalrcvrec+ totalrcvact;
			 sumariza("txtCOPPagSP", totalcopsp);
			 sumariza("txtRCVPagSP", totalrcvsp);
			 sumariza("txtCOPPagAct", totalcopact);
			 sumariza("txtRCVPagAct", totalrcvact);
			 sumariza("txtCOPPagRec", totalcoprec);
			 sumariza("txtRCVPagRec", totalrcvrec);
			 var totalCOP = totalcopsp + totalcopact + totalcoprec;
			 var totalRCV = totalrcvsp + totalrcvact + totalrcvrec;
			 
			 $('form#formPromo input#txtCOPPagTotal').prop('value', totalCOP);
			 $('form#formPromo input#txtCOPPagTotal').prop('readonly', true);
			 $('form#formPromo input#txtCOPPagTotal').formatCurrency();
			 
			 $('form#formPromo input#txtRCVPagTotal').prop('value', totalRCV);
			 $('form#formPromo input#txtRCVPagTotal').prop('readonly', true);
			 $('form#formPromo input#txtRCVPagTotal').formatCurrency();
			
			 if(idTipo == '6'){
				 var copConvSP = 0;
				 if($('input#txtCOPConvSP').val() != null  && $('input#txtCOPConvSP').val() != ''){
					 copConvSP = $('input#txtCOPConvSP').val();
				 }
				 var rcvConvSP = 0;
				 if($('input#txtRCVConvSP').val() != null  && $('input#txtRCVConvSP').val() != ''){
					 rcvConvSP = $('input#txtRCVConvSP').val();
				 }
			 	 var difCOP = parseFloat(copConvSP ).toFixed(2) - parseFloat(totalcopsp).toFixed(2);
				 var difRCV = parseFloat(rcvConvSP ).toFixed(2) - parseFloat(totalrcvsp).toFixed(2);
				 $('form#formPromo input#txtCOPxPagSP').prop('value', difCOP);
				 $('form#formPromo input#txtCOPxPagSP').prop('readonly', true);
				 $('form#formPromo input#txtCOPxPagSP').formatCurrency();
				 $('form#formPromo input#txtRCVxPagSP').prop('value', difRCV);
				 $('form#formPromo input#txtRCVxPagSP').prop('readonly', true);
				 $('form#formPromo input#txtRCVxPagSP').formatCurrency();
			 }
			 
			 
			//Agrega las opciones al control
		}).error(function(data){ 
			validarSesionExpirada(data);
		}).complete(function(){
			//Instrucciones para el 'complete'
		});
        }
 
function salirAnexoPagos(){
	oDgAnexoPagos.dialog('close');
}


function salirDetallePromocion(){
	oDgBuscar.dialog('close');
}

function ajustarCamposCargaDetallePromocion(data){
	 // if(data.periodoal != null && data.periodoal != ''){
		 // document.getElementById('dtpPeriodoDel').disabled = true;
		 // $('form#formPromo input#dtpPeriodoDel').removeClass("red");
		 // document.getElementById('dtpPeriodoAl').disabled = true;
		 // $('form#formPromo input#dtpPeriodoAl').removeClass("red");		  
	 // }

//	  if ((data.ope != null && data.ope != '') && ((data.nop==null || data.nop == '')) && (data.c==null || data.c == '')) {
//		  document.getElementById('dtpNOP').disabled = false;
//		  $('form#formPromo input#dtpNOP').addClass("red");
//		  document.getElementById('dtpC').disabled = false;
//		  $('form#formPromo input#dtpC').addClass("red");		  
//	  }
	  
	  deshabilitarEncabezado(); 
	  document.getElementById('txtObservaciones').disabled = false;	
}

/**
 * Deshabilita el encabezado (los primeros tres renglones)
 */
function deshabilitarEncabezado(){
	document.getElementById('cgcCatTipo.idTipo').disabled = true;
	 $('form#formPromo select#cgcCatTipo\\.idTipo').removeClass("red");
	document.getElementById('cbxCriterioSeleccion').disabled = true;
	 $('form#formPromo select#cbxCriterioSeleccion').removeClass("red");
	document.getElementById('txtFolio').disabled = true;
	 $('form#formPromo input#txtFolio').removeClass("red");
	document.getElementById('cbxCriterioSeleccion').disabled = true;
	 $('form#formPromo select#cbxCriterioSeleccion').removeClass("red");
	document.getElementById('txtRP').disabled = true;
	$('form#formPromo input#txtRP').removeClass("red");
	document.getElementById('txtNombre').disabled = true;
	$('form#formPromo input#txtNombre').removeClass("red");	
	document.getElementById('txtUbicacion').disabled = true;
	$('form#formPromo input#txtUbicacion').removeClass("red");	
	document.getElementById('txtMesesEstimados').disabled = true;
	$('form#formPromo input#txtMesesEstimados').removeClass("red");	
	document.getElementById('txtEstimadoTrabReg').disabled = true;
	$('form#formPromo input#txtEstimadoTrabReg').removeClass("red");	
	document.getElementById('txtAFLI15').disabled = true;
	$('form#formPromo input#txtAFLI15').removeClass("red");
	document.getElementById('cbxTipoObra').disabled = true;
	 $('form#formPromo select#cbxTipoObra').removeClass("red");
	document.getElementById('txtSuperficieEstimada').disabled = true;
	$('form#formPromo input#txtSuperficieEstimada').removeClass("red");
	document.getElementById('txtCostoTotalContratado').disabled = true;
	$('form#formPromo input#txtCostoTotalContratado').removeClass("red");
	document.getElementById('txtPorcAvanceEstimado').disabled = true;
	$('form#formPromo input#txtPorcAvanceEstimado').removeClass("red");
	
}


function validaFecha(fec,fec2){
	var fecha;
	var fecha2;
	if(fec!=''){		
		var tokens = fec.split(',');
		if(tokens.length>1){
			if(document.getElementById(tokens[1]).value != ''){
				fecha = document.getElementById(tokens[1]).value;
			}else if(document.getElementById(tokens[0]).value != ''){
				fecha = document.getElementById(tokens[0]).value;
			}
		}else
			fecha = document.getElementById(fec).value;
		while (fecha.toString().indexOf("/") != -1)
			fecha = fecha.replace("/","-");	    
	}
	if(fec2!=''){
		fecha2 = document.getElementById(fec2).value;
		while (fecha2.toString().indexOf("/") != -1)
			fecha2 = fecha2.replace("/","-");	    
	}
	if(fecha2!=""){
		if(!validaFechas(fecha2,fecha)){
			alert("La fecha no puede ser menor a "+fecha);
			$('input#'+fec2).prop("value", "");
			return false;
		}else{
			return true;
		}
	}	
}

function setFecha(campo){
alert(campo);	
	
	
}

function cargaPago(idPago){
	var folio = $('input#radioTable').val();
	var sPromocion = '{' +
	'"folio": "'+folio+'"}';
	var promocion = jQuery.parseJSON(sPromocion);
	jQuery.ajax({
		async: false,
	    type: 'POST',
	    url: 'promocion/consultaPromocion.do',
	    data: sPromocion, // or JSON.stringify ({name: 'jonas'}),
	    success: function(data) { 
	    $.postJSON("promocion/consultaPago.do", idPago, function(data) {
		 var concepto = '';
		var COP = data.copsp + data.copact + data.coprec;
		var RCV = data.rcvsp + data.rcvact + data.rcvrec;
		if(parseFloat(COP) > 0.00 && parseFloat(RCV) > 0.00){
			concepto = 'COPRCV';
		}else if(parseFloat(COP) > 0.00){
			concepto = 'COP';
		}else if(parseFloat(RCV) > 0.00){
			concepto = 'RCV';
		} 
		 $('select#concepto').val(concepto);
		 validaConcepto();
		 $('select#regpat').val(jQuery.trim(data.cvePatron)+''+jQuery.trim(data.cveModalidad));
		 $('form#formPagos select#periodoCOP').val(data.copperiodo);
		 $('form#formPagos select#periodoRCV').val(data.rcvperiodo);
		 // Folio SUA
		 if(data.foliosua != null){
			 activa('txtFolioSUA');
			 $('form#formPagos input#txtFolioSUA').prop("value", data.foliosua);
			 validaFolioSUA( 'txtFolioSUA', 'txtOrdenIngreso')
		 }
		 if(data.folioordeningreso != null){
		 	activa('txtOrdenIngreso');
			 $('form#formPagos input#txtOrdenIngreso').prop("value", data.folioordeningreso);
			 activa('txtNoCredito');
			 validaFolioSUA('txtOrdenIngreso', 'txtFolioSUA');validarCamposOns('txtOrdenIngreso', 'txtNoCredito')
			 $('form#formPagos input#txtNoCredito').prop("value", data.nocredito);
			 
		 }
		 //Cantidades COP
		 $('form#formPagos input#fechaPagoAnexoPagos').prop("value", desformateaFecha(data.fechapago));	
		 $('form#formPagos input#txtCOPSP').prop("value", data.copsp);
		 $('#txtCOPSP').formatCurrency();
		 $('form#formPagos input#txtCOPAct').prop("value", data.copact);
		 $('#txtCOPAct').formatCurrency();
		 $('form#formPagos input#txtCOPRec').prop("value", data.coprec);
		 $('#txtCOPRec').formatCurrency();
		 $('form#formPagos input#txtCOPTotal').prop("value", COP);
		 $('#txtCOPTotal').formatCurrency();
		 
		 //Cantidades RCV
		 $('form#formPagos input#txtRCVSP').prop("value", data.rcvsp);
		 $('#txtRCVSP').formatCurrency();
		 $('form#formPagos input#txtRCVAct').prop("value", data.rcvact);
		 $('#txtRCVAct').formatCurrency();
		 $('form#formPagos input#txtRCVRec').prop("value", data.rcvrec);
		 $('#txtRCVRec').formatCurrency();
		 $('form#formPagos input#txtRCVTotal').prop("value", RCV);
		 $('#txtRCVTotal').formatCurrency();
		 $('form#formPagos select#periodoCOP').val(data.copperiodo);
		 $('form#formPagos select#periodoRCV').val(data.rcvperiodo);
		//Agrega las opciones al control
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
	 
	    },
	    contentType: "application/json"
	    
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
}



function llenaComboFindFuentePromocion(){
	var idTipo = $('select#cgcCatTipo\\.idTipo').val();
	 $.postJSON("promocion/consultaComboFindFuentePromocion.do", idTipo, function(data) {
		 var options = "<option value='' >--Por favor seleccione--</option>";
		 if(data!=null)
		   for (var i = 0; i < data.length; i++) {
	         options += "<option value='"+ data[i].idOrigen +"'>"+ data[i].descOrigen +"</option>";		     
	       }
		 $('select#findFuente').prop("disabled", false);
		 $('select#findFuente').html(options);
		//Agrega las opciones al control
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
	

}


function validafechaSistema(fec){		
	var fecha = document.getElementById(fec).value;		
	while (fecha.toString().indexOf("/") != -1)
		fecha = fecha.replace("/","-");	    
	var fechaSis = new Date();
	var diaS = fechaSis.getDate();
	var mesS = fechaSis.getMonth() + 1;
	var anioS = fechaSis.getFullYear()+"";
	if(diaS<10){
		diaS = "0"+diaS;
	}
	if(mesS<10){
		mesS = "0"+mesS;
	}
	var fecha2 = diaS+"-"+mesS+"-"+anioS;
	if(fecha!=""){
		if(!validaFechas(fecha2,fecha)){
			alert("La fecha debe ser menor o igual a "+fecha2);
			$('input#'+fec).prop("value", "");
			return false;
		}else{
			return true;
		}
	}
}


function validaRP(){
	var rp = $('input#txtRP').val();
	if(rp.length==10){
		validarRegPatronal('txtRP');
		
	}
	
	
	
}

function llenaCeros(campo){
	var folioNum =$('input#'+campo).val();
	var numero = ''+folioNum;
	while(numero.length<4){
		numero = "0"+numero;
	}
	$('input#'+campo).prop("value", numero);
	return numero;
	
}


function minFecha(){
	$('#dtpNOP, #dtpNOP, #dtpAOP, #dtpSP, #dtpOI, #dtpPR, #dtpCR').datepicker('option', 'minDate', $("#dtpOPE").datepicker("getDate"));
}
function minFechaAl(){
	$('#dtpPeriodoAl').datepicker('option', 'minDate', $("#dtpPeriodoDel").datepicker("getDate"));
	$('#dtpPeriodoAl').datepicker('option', 'maxDate', $("#dtpAOP").datepicker("getDate"));
}