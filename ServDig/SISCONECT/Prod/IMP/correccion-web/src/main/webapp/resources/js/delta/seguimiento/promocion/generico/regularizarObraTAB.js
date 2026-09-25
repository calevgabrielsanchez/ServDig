//id Div de jsp anexoPagosGenerico.jsp
var idDatosAnexoPagosGenericos = "#dgAnexoPagosGenerico";  
var oDgDatosAnexoPagosGenericos;

//Dialog Confirmar Generico para Seguimiento SATIC B
var idDgConfirmarSATICBAnexoTab = "#dgConfirmarSaticB";
var oDgConfirmarSATICBAnexoTab;



var jsPeriodoDelRegularObra = "form#regularizarObraGenericoTABForm #perRegularizaDel";
var jsPeriodoAlRegularObra = "form#regularizarObraGenericoTABForm #perRegularizaAl";
var jsPorcAvanceRegularObra =  "form#regularizarObraGenericoTABForm #porcentajeAvance";
var jsPorcRegularizadoRegularObra =  "form#regularizarObraGenericoTABForm #porcentajeRegularizado";
var jsNumParcialidadesRegularObra =  "form#regularizarObraGenericoTABForm #numeroParcialidades";
var jsTrabRevisadosRegularObra =  "form#regularizarObraGenericoTABForm #trabRevisados";
var jsTrabOmisosUniRegularObra = "form#regularizarObraGenericoTABForm #trabOmisosUni";
var jsTrabSubdeclaUniRegularObra = "form#regularizarObraGenericoTABForm #trabSubdeclaUni";
var jsBaseDeterminadaRegularObra = "form#regularizarObraGenericoTABForm #baseDeterminada";
var jsSuertePpalDetCopRegularObra = "form#regularizarObraGenericoTABForm #suertePpalDetCop";
var jsSuertePpalDetRcvRegularObra = "form#regularizarObraGenericoTABForm #suertePpalDetRcv"




/**Funcion donde se inicializa todo el comportamiento y/o funcionalidad que se deberia de cargar en el Ready de Jquery , 
 * se separo del Ready principal para facilitar la carga en diferentes momentos y no sea tan pésada al iniciar la pantalla
 * principale de consulta de promociones. 
 * funcionalidad inicial para la pestaña de Derivar a fiscalizacion.
 * @author Oscar Beltran
 * @version 1.0.1
 */
function initTabRegularizarObra(){
	$(jsPeriodoDelRegularObra).datepicker( { dateFormat: 'dd-mm-yy' });
	$(jsPeriodoAlRegularObra).datepicker( { dateFormat: 'dd-mm-yy' });
	
	/*seccion para definir la fecha maxima del servidor y establecerle un limite maximo a las fechas
	 * maximas de los calendarios para las fechas siguientes , con el formato dd-MM-yyyy  */ 
	$.postJSON(getAppContextParaJS() + "/promocion/seguimiento/generico/obtenerFechaServidor.do", null,function(data) {
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(data){
			$(jsPeriodoDelRegularObra).datepicker('option', 'maxDate', data.responseText);
			$(jsPeriodoAlRegularObra).datepicker('option', 'maxDate', data.responseText);
	});

	
	//estilos 
	$(jsPeriodoDelRegularObra).addClass("red");
	$(jsPeriodoAlRegularObra).addClass("red");
	
	$(jsPorcAvanceRegularObra).addClass("red");
	$(jsPorcRegularizadoRegularObra).addClass("red");
	$(jsNumParcialidadesRegularObra).addClass("red");
	
	$(jsTrabRevisadosRegularObra).addClass("red");
	$(jsTrabOmisosUniRegularObra).addClass("red");
	$(jsTrabSubdeclaUniRegularObra).addClass("red");
	
	$(jsBaseDeterminadaRegularObra).addClass("red");

	$(jsSuertePpalDetCopRegularObra).addClass("red");
	$(jsSuertePpalDetRcvRegularObra).addClass("red");
	
	//disable 
	$(jsPeriodoDelRegularObra).removeAttr('disabled');
	$(jsPeriodoAlRegularObra).removeAttr('disabled');

	$(jsPeriodoDelRegularObra).prop('readonly', true);
	$(jsPeriodoAlRegularObra).prop('readonly', true);
	
	$(jsPorcAvanceRegularObra).removeAttr('disabled');
	$(jsPorcRegularizadoRegularObra).removeAttr('disabled');
	$(jsNumParcialidadesRegularObra).removeAttr('disabled');
	
	$(jsTrabRevisadosRegularObra).removeAttr('disabled');
	$(jsTrabOmisosUniRegularObra).removeAttr('disabled');
	$(jsTrabSubdeclaUniRegularObra).removeAttr('disabled');
	
	$(jsBaseDeterminadaRegularObra).removeAttr('disabled');

	$(jsSuertePpalDetCopRegularObra).removeAttr('disabled');
	$(jsSuertePpalDetRcvRegularObra).removeAttr('disabled');
	
	//btn
	$("form#regularizarObraGenericoTABForm #btnDatosRegularizacion").prop("disabled", "disabled");
	$("form#regularizarObraGenericoTABForm #labelTrabRegularizadosRegObra").html("");
	
	//btnFecha
	$("#spnPeriodoRegObra").hide();
	
	
	//declaracion de dialogo de pagos
	oDgDatosAnexoPagosGenericos  = $(idDatosAnexoPagosGenericos).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 1300,
		closeOnEscape: false,
		open:function(event, ui){
		
		},
		close:function(event,ui){
			limpiarPagoDetalle();
		}
	});
	
	var idRegulaPagoGral = $('form#anexoPagosGenericoForm #cveRegulaPagosGral').val();
	if(idRegulaPagoGral != null && idRegulaPagoGral != undefined && idRegulaPagoGral != ''){
		var sCrtRegulaPago = '{"crtRegulapagos": {"cveRegulapagos":"'  +(idRegulaPagoGral != ''?idRegulaPagoGral:0)+'"}}';
		
		var regulaPagoObj = jQuery.parseJSON(sCrtRegulaPago);
		$.postJSON(getAppContextParaJS() + "/promocion/consulta/consultar.do",regulaPagoObj ,function(data) {
			
			
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(data){
			$(jsPeriodoDelRegularObra).datepicker('option', 'maxDate', data.responseText);
			$(jsPeriodoAlRegularObra).datepicker('option', 'maxDate', data.responseText);
			//desHabilitaCapturaFechasPeriodoRegulaObra();
		});
	}else{
		//alert("NO existe");
	}
	
	
	
	
	
	
	initTabAnexoPagosGenerico();
	//completaPantallaAnexoPagos();
	
	
}

/**
 * Función que ejecuta la peticion de guardar del tab de Regularizar obra
 * @author Oscar German Beltrán Ortega
 */
function procesaFormularioRegObraGenerico(funcionValidacion){
	if(validaCamposRegularizaObra()){
		document.forms["regularizarObraGenericoTABForm"].action = jsContextoPromocion + "seguimiento/generico/regulaObraGenerico.do";
		oDgConfirmarSATICBAnexoTab = $(idDgConfirmarSATICBAnexoTab).dialog({
			autoOpen: false,
			modal:true,
			resizable:false,
			width: 700,
			closeOnEscape: false,
			buttons: {
				   "Si": function() {

						if(ejecutaPeticion(funcionValidacion)){
							$("form#regularizarObraGenericoTABForm #btnDatosRegularizacion").removeAttr('disabled');
							alert("Los datos han sido guardados");
							oDgConfirmarSATICBAnexoTab.dialog("close");
							
							try {
								//recupera el nombre de la funcion generica
								eval($("form#regularizarObraGenericoTABForm #functionAuxRegularizaObra").val());	
							} catch (e) {

							}
							$("form#regularizarObraGenericoTABForm #perRegularizaDel").prop('disabled','disabled');
							$("form#regularizarObraGenericoTABForm #perRegularizaAl").prop('disabled','disabled');
							$("form#regularizarObraGenericoTABForm #porcentajeAvance").prop('disabled','disabled');
							$("form#regularizarObraGenericoTABForm #porcentajeRegularizado").prop('disabled','disabled');
							$("form#regularizarObraGenericoTABForm #numeroParcialidades").prop('disabled','disabled');
							$("form#regularizarObraGenericoTABForm #trabRevisados").prop('disabled','disabled');
							$("form#regularizarObraGenericoTABForm #trabOmisosUni").prop('disabled','disabled');
							$("form#regularizarObraGenericoTABForm #trabSubdeclaUni").prop('disabled','disabled');
							$("form#regularizarObraGenericoTABForm #trabRegularizadosRegObra").prop('disabled','disabled');
							$("form#regularizarObraGenericoTABForm #baseDeterminada").prop('disabled','disabled');
						}
						oDgConfirmarSATICBAnexoTab.dialog("close");
					   
				   }, "No": function(){
					$(this).dialog("close"); 
				} 
			}
		});
		
		oDgConfirmarSATICBAnexoTab.dialog("open");
	}	
}


/**
 * Función guarda los datos de regularizar obra
 * @author Oscar German Beltrán Ortega
 */
function ejecutaPeticion(funcionValidacion){
	
	
	//$("form#regularizarObraGenericoTABForm input#cvePromocion").val(cvePromocion);
	var objForma =$("form#regularizarObraGenericoTABForm").toObject({mode:'first'});
	document.forms["regularizarObraGenericoTABForm"].action = jsContextoPromocion + "seguimiento/generico/regulaObraGenerico.do";
	var formAction = $("form#regularizarObraGenericoTABForm").attr('action');
	 
	
	try{
		if(eval(funcionValidacion)){
			bloquear();
			$.postJSON(formAction, objForma, function(data) {
				if(data == null){
					alert('Error General: Consulte a su administrador');
					return false;
				}else{					
					verifyCustomDataError(data);
					$("form#regularizarObraGenericoTABForm #cveRegulaPagos").val(data.segRegularizaObraVo.cveRegulaPagos);
					$("form#anexoPagosGenericoForm #cveRegulaPagosGral").val(data.segRegularizaObraVo.cveRegulaPagos);
					desbloquear();
				}
			}).error(function(data){ 
				validarSesionExpirada(data);				
				desbloquear();
			});
			
		}else{
			desbloquear();
			return false;
			
		}
	}catch(error){
		alert("Error al procesar la forma, verifique el códido JS:"+error);
		desbloquear();		
	}
	
	return true;
}

/**
 * Función de validacion de los datos requeridos y de datos ingresados validos
 * @author Oscar German Beltrán Ortega
 */
function validaCamposRegularizaObra(){
	
	var regresa = false;

	$("form#regularizarObraGenericoTABForm #labelPerRegularizaDel").html('');
	$("form#regularizarObraGenericoTABForm #labelPerRegularizaAl").html('');
	$("form#regularizarObraGenericoTABForm #labelTrabRevisados").html('');
	$("form#regularizarObraGenericoTABForm #labelTrabOmisosUni").html('');
	$("form#regularizarObraGenericoTABForm #labelTrabSubdeclaUni").html('');
	$("form#regularizarObraGenericoTABForm #labelBaseDeterminada").html('');
	$("form#regularizarObraGenericoTABForm #labelSuertePpalDetCop").html('');
	$("form#regularizarObraGenericoTABForm #labelSuertePpalDetRcv").html('');
	
	

	if($(jsPeriodoDelRegularObra).val() == ''){
		$("form#regularizarObraGenericoTABForm #labelPerRegularizaDel").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($(jsPeriodoAlRegularObra).val() == ''){
		$("form#regularizarObraGenericoTABForm #labelPerRegularizaAl").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($(jsTrabRevisadosRegularObra).val() == ''){
		$("form#regularizarObraGenericoTABForm #labelTrabRevisados").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($(jsTrabOmisosUniRegularObra).val() == ''){
		$("form#regularizarObraGenericoTABForm #labelTrabOmisosUni").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($(jsTrabSubdeclaUniRegularObra).val() == ''){
		$("form#regularizarObraGenericoTABForm #labelTrabSubdeclaUni").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($(jsBaseDeterminadaRegularObra).val() == ''){
		$("form#regularizarObraGenericoTABForm #labelBaseDeterminada").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if(($(jsSuertePpalDetCopRegularObra).val()=='' || parseInt($(jsSuertePpalDetCopRegularObra).val(),10)<=0 ) && 
			( $(jsSuertePpalDetRcvRegularObra).val()== '' || parseInt($(jsSuertePpalDetRcvRegularObra).val(),10)<=0 )){
		$("form#regularizarObraGenericoTABForm #labelSuertePpalDetCop").html('<label class="etiquetaError"  align="rigth">Ingrese COP</label>');
		$("form#regularizarObraGenericoTABForm #labelSuertePpalDetRcv").html('<label class="etiquetaError" align="left"> o RCV </label>');
		
	}

	
	if($(jsPeriodoDelRegularObra).val() != '' && $(jsPeriodoAlRegularObra).val() !=''
		&& $(jsTrabRevisadosRegularObra).val() != '' && $(jsTrabOmisosUniRegularObra).val() !='' 
		&& $(jsTrabSubdeclaUniRegularObra).val() != '' && $(jsBaseDeterminadaRegularObra).val() != '' 
			&& ( $(jsSuertePpalDetCopRegularObra).val() != '' && $(jsSuertePpalDetCopRegularObra).val() != "0" )
					&&  ($(jsSuertePpalDetRcvRegularObra).val() != '' || $(jsSuertePpalDetRcvRegularObra).val() != "0")   
			&& validaPorcentaje('porcentajeAvance','labelPorcAvanceGen')  && validaPorcentaje('porcentajeRegularizado','labelPorcRegularizadoGen') 
		&& validaTrabRegularizados() && validaParcialidadesSegProm() && validaSuertePrincipalBaseDeter() && validaMontoSuertePpalBaseDeterminada() 
		&& validaMontoSuertePpalRCVBaseDeterminada()){
		
		regresa = true;
		
	}
	
	//alert("regresa principal : "+ regresa);
	return regresa;
}


function validaSuertePrincipalBaseDeter(){
	var resultado = false;

	var baseDeterminadaReg = $("form#regularizarObraGenericoTABForm #baseDeterminada").val();
	if( parseInt($(jsSuertePpalDetCopRegularObra).val())>0   ||  parseInt($(jsSuertePpalDetRcvRegularObra).val())>0 ){
		
		if(baseDeterminadaReg != null && baseDeterminadaReg != ''){
			if( parseFloatComas(baseDeterminadaReg)>0 ){

				resultado = true;
				$("form#regularizarObraGenericoTABForm #labelBaseDeterminada").html('');
			}else{
				
				$("form#regularizarObraGenericoTABForm #labelBaseDeterminada").html('<label class="etiquetaError" align="left"> Base determinada debe ser mayor a 0 </label>');
				resultado = false;
			}
			
			
		}else{
			$("form#regularizarObraGenericoTABForm #labelBaseDeterminada").html('<label class="etiquetaError" align="left"> Base determinada debe ser mayor a 0 </label>');
			resultado = false;
		}
	}
	
	return resultado;
}

/**
 * Función para abrir el dialogo de Anexar pagos
 * @author Oscar German Beltrán Ortega
 */
function openDialogoPagos(){
	
	openDialogoPagosGenerico();
	
}

function openDialogoPagosVersion2(){
	var regPatronalValidacion =  $("form#regularizarObraGenericoTABForm #registroPatronalPagosDt").val();
	var fechaNotificacionOficio = $("form#regularizarObraGenericoTABForm #fechaNotificacionHdn").val(); 
	if(regPatronalValidacion != null && regPatronalValidacion != '' ) {
		
	
		if(confirm("Este proceso puede tardar varios minutos, desea continuar?")){
		
			bloquear();
		
			var periodoInicialPag = $("form#regularizarObraGenericoTABForm #perRegularizaDel").val();
			var periodoFinalPag = $("form#regularizarObraGenericoTABForm #perRegularizaAl").val();
			var cveRegulaPagosParam =$("form#regularizarObraGenericoTABForm #cveRegulaPagos").val();
			var indTipoPagoGenerico = "3";
		
			var accion = "seguimiento/ec/pagos.do?periodoInicial=" + periodoInicialPag
				+"&periodoFinal="+periodoFinalPag+"&cveRegulaPagos="+cveRegulaPagosParam
				+"&indTipoPago="+indTipoPagoGenerico+"&fechaMinDateCalendar=" + fechaNotificacionOficio;
			
			//alert("accion : " + accion);
			
			//regresa mayor a 0 en caso de que se haya guardado un pago en la pantalla de pagos
			var respuesta = openWindowPagosSeguimientoFII(getAppContextParaJS(),accion); 

			//variable para almacenar el numero de pagos
			//numeroPagosPorCorreccion = respuesta;
		
			salirPagosTemporalVersion2();
			desbloquear();
		}else{
			//alert("Cancelo.");
		}
	}//fin fi de reg patronal 
	else {
		confirm("Debe tener asociado un registro patronal");
	}
}

	

function salirPagosTemporalVersion2(){
	
		//continuaFlujoPagos();
	var cveRegulaPagosTxt =	$('form#regularizarObraGenericoTABForm #cveRegulaPagos').val();
	if(cveRegulaPagosTxt != null && cveRegulaPagosTxt != ''){
		
		obtenerTotalesPagosVersion2();
	}else{
		alert("NO existe un reg de regula pagos");
	}

}

function obtenerTotalesPagosVersion2(){

	var cveRegulaPagosTxt =	$('form#regularizarObraGenericoTABForm #cveRegulaPagos').val();
	
	var sCveRegulaPagosCadena = '{"cveRegulaPagos":"'+cveRegulaPagosTxt+'",'+
	'"banderaTipoPago":"3"}';

	var corrSeguimientoVO = jQuery.parseJSON(sCveRegulaPagosCadena);
	var respuesta = true;
	
	$.postJSON(getAppContextParaJS() +"/promocion/seguimiento/generico/obtenerTotalesPagosPromocion.do", corrSeguimientoVO, function(data) {
		
		if(data != null){
			
			var myNumber = Number(data.impCopsp);
			$('form#regularizarObraGenericoTABForm #suertePrincipalCop').val(myNumber.formatMoney(2, '.', ','));//listo
			myNumber = Number(data.impRcvsp);
			$('form#regularizarObraGenericoTABForm #suertePrincipalRcv').val(myNumber.formatMoney(2, '.', ','));//listo
			
			myNumber = Number(data.impCopact);
			$('form#regularizarObraGenericoTABForm #actualizacionCop').val(myNumber.formatMoney(2, '.', ',')); //Listo
			myNumber = Number(data.impRcvact);
			$('form#regularizarObraGenericoTABForm #actualizacionRcv').val(myNumber.formatMoney(2, '.', ',')); //listo
			
			myNumber = Number(data.impCoprec);
			$('form#regularizarObraGenericoTABForm #recargosCop').val(myNumber.formatMoney(2, '.', ','));//listo
			myNumber = Number(data.impRcvrec);
			$('form#regularizarObraGenericoTABForm #recargosRcv').val(myNumber.formatMoney(2, '.', ','));//Listo
		
			myNumber = Number(data.impCopmulta);
			$('form#regularizarObraGenericoTABForm #multasCopSegProm').val(myNumber.formatMoney(2, '.', ','));
			myNumber = Number(data.impRcvmulta);
			$('form#regularizarObraGenericoTABForm #multasRcvSegProm').val(myNumber.formatMoney(2, '.', ','));
			
			myNumber = Number(data.impCoptot);
			$('form#regularizarObraGenericoTABForm #totalPagadoCop').val(myNumber.formatMoney(2, '.', ','));//listo
			myNumber = Number(data.impRcvtot);
			$('form#regularizarObraGenericoTABForm #totalPagadoRcv').val(myNumber.formatMoney(2, '.', ','));//listo
		
			calculaSuertePpalPentPagoVersion2(data);
			
			
			
		}

	}).error(function(data) {
		validarSesionExpirada(data);
		alert("error"+data);
	}).complete(function(){				
		desbloquear();
	});	
	
	return respuesta;
}


function calculaSuertePpalPentPagoVersion2(data){
	
	//calculo de Suerte ppal pent pago
	//Suerte Ppal Aut - Suerte ppal pagada
	var suertPpalAutDetCop =$('form#regularizarObraGenericoTABForm #suertePpalDetCop').val() != '' ?$('form#regularizarObraGenericoTABForm #suertePpalDetCop').val():"0";
	var suertPpalAutDetRcv =$('form#regularizarObraGenericoTABForm #suertePpalDetRcv').val() != '' ? $('form#regularizarObraGenericoTABForm #suertePpalDetRcv').val():"0";
	var suertePpalPentPagoCop = null;
	var suertePpalPentPagoRcv = null;
	if(data == null){	
	 suertePpalPentPagoCop = parseIntComas(suertPpalAutDetCop) - parseInt($('form#regularizarObraGenericoTABForm #suertePrincipalCop').val());
	 suertePpalPentPagoRcv = parseIntComas(suertPpalAutDetRcv) - parseInt($('form#regularizarObraGenericoTABForm #suertePrincipalRcv').val());
	} else{

		suertePpalPentPagoCop = parseIntComas(suertPpalAutDetCop) - parseIntComas(data.impCopsp!=null ? data.impCopsp:0);
		suertePpalPentPagoRcv = parseIntComas(suertPpalAutDetRcv) - parseIntComas(data.impRcvsp != null ? data.impRcvsp:0);
	}
	var myNumber = Number(suertePpalPentPagoCop);
	$('form#regularizarObraGenericoTABForm #suertePpalPenPagoCop').val(myNumber.formatMoney(2, '.', ',')); 
	myNumber = Number(suertePpalPentPagoRcv);
	$('form#regularizarObraGenericoTABForm #suertePpalPenPagoRcv').val(myNumber.formatMoney(2, '.', ',')); 
	
	validaBloquearPagosAct(suertePpalPentPagoCop,suertePpalPentPagoRcv);
}

/**
 * Función 
 * @author Oscar German Beltrán Ortega
 */
function complementaFecha(){

	$("form#anexoPagosGenericoForm #fechaPagoGenerico").datepicker('option', 'minDate', $(jsPeriodoAlRegularObra).val());
	$("form#regularizarObraGenericoTABForm #labelPerRegularizaAl").html('');
	//if($(jsPeriodoAlRegularObra).val() != '' &&  $(jsPeriodoDelRegularObra).val()!= null){
		//generaListaPeriodoCOP();
		//generaListaPeriodoRCV();
	//}
	
}

/**
 * Funcuon que genera las opciones del periodo por mes
 * @author Oscar German Beltran Ortega
 */
/*function generaListaPeriodoCOP(){ borrar
	 var options = "<option value='-1' >--Por favor seleccione--</option>";
	 var fechaAl = $(jsPeriodoAlRegularObra).val();
	 var fechaDel = $(jsPeriodoDelRegularObra).val();
	 var arrayFechaAl = fechaAl.split("-");
	 var arrayFechaDel = fechaDel.split("-");
	 var mesAl = arrayFechaAl[1];
	 var anioAl = arrayFechaAl[2];
	 var mesDel = arrayFechaDel[1];
	 var anioDel = arrayFechaDel[2];
	 var mesTxt = "" ;
	 if(anioAl == anioDel){
		 var i = mesDel;
		 for (i = mesDel; i <= mesAl; i++) {
			 if(i.length != 2 && !((i>= 10) && (i <=12))){
				 mesTxt = "0" + i; 
			 }else{
				 mesTxt =  i;
			 }
			 options += "<option value='"+ anioAl + mesTxt +"'>"+ anioAl + mesTxt +"</option>";
		 }
		 
	 }else{
		 var i = anioDel;
		 for (i = anioDel; i <= anioAl; i++) {
			 
			 var mesDesde ;
			 var mesHasta ;
			 if(i == anioDel){
				 mesDesde = mesDel;
			 }else {
				 mesDesde = 1;
			 }
			 
			 if(i == anioAl){
				 mesHasta = mesAl;
			 }else {
				 mesHasta = 12;
			 }
			 
			 for (var j = mesDesde ; j <= mesHasta ; j++) {
				 if(j.length != 2 && !((j>= 10) && (j <=12))){
					 j = "0" + j; 
				 }else{
					 j =  j;
				 }
				 options += "<option value='"+ i + j +"'>"+ i + j +"</option>";
			 }
		 }
		 
	 }
	 
	 $('select#periodoCOPGen').html(options);
	
	
}*/

/**
 * Funcuon que genera las opciones del periodo por bimestre
 * @author Oscar German Beltran Ortega
 */
/*function generaListaPeriodoRCV(){ borrar
	 var options = "<option value='-1' >--Por favor seleccione--</option>";
	 var fechaAl = $(jsPeriodoAlRegularObra).val();
	 var fechaDel = $(jsPeriodoDelRegularObra).val();
	 var arrayFechaAl = fechaAl.split("-");
	 var arrayFechaDel = fechaDel.split("-");
	 var mesAl = arrayFechaAl[1];
	 var anioAl = arrayFechaAl[2];
	 var mesDel = arrayFechaDel[1];
	 var anioDel = arrayFechaDel[2];
	 var mesTxt = "0" ;
	 if(anioAl == anioDel){
		 
		 var i = parseInt(mesDel,10);
		 
		 for (i = parseInt(mesDel,10); i <= parseInt(mesAl,10); i++) {
			 if(i%2 == 0){
				 options += "<option value='"+ anioAl + mesTxt+i +"'>"+ anioAl + mesTxt+i +"</option>";
			 }
		 }
		 
	 }else{
		 var i = parseInt(anioDel,10);
		 for (i = parseInt(anioDel,10); i <= parseInt(anioAl,10); i++) {
			 
			 var mesDesde ;
			 var mesHasta ;
			 if(i == anioDel){
				 mesDesde = mesDel;
			 }else {
				 mesDesde = 1;
			 }
			 
			 if(i == anioAl){
				 mesHasta = mesAl;
			 }else {
				 mesHasta = 12;
			 }
			 
			 for (var j = mesDesde ; j <= mesHasta ; j++) {
				 if(j.length != 2 && !((j>= 10) && (j <=12))){
					 j = "0" + j; 
				 }else{
					 j =  j;
				 }
				 if(j%2 == 0){
					 options += "<option value='"+ i + j +"'>"+ i + j +"</option>";
				 }
			 }
		 }
		 
	 }
	 $('select#periodoRCVGen').html(options);

}*/

/**
 * Función que realiza los calculos para obtener el total de Trabajadores
 * regularizados
 * @author Oscar German Beltrán Ortega
 */
function calcTotalTrabRegularizado(){
	
	var valorTotalReg = 0;
	var valorTrabSub = 0;
	var valorOmisosUni = 0; 
	

	if(document.getElementById("trabSubdeclaUni").value != ''){
		valorTrabSub = parseIntComas(document.getElementById("trabSubdeclaUni").value,10);
	}
	if(document.getElementById("trabOmisosUni").value !=''){
		valorOmisosUni = parseIntComas(document.getElementById("trabOmisosUni").value,10);
	}
	
	
	valorTotalReg = valorTrabSub +  valorOmisosUni;
	
	var myNumber = Number(valorTotalReg);
	$("#regularizarObraGenericoTAB_saticb,form#regularizarObraGenericoTABForm,#trabRegularizadosRegObra").val(myNumber.formatMoney(0, '.', ','));
	validaTrabRegularizados();
}

/**
 * Función 
 * @author Oscar German Beltrán Ortega
 */
function completaFechaPeriodo(){
	
	$(jsPeriodoAlRegularObra).datepicker('option', 'minDate', $(jsPeriodoDelRegularObra).val());
	$("form#regularizarObraGenericoTABForm #labelPerRegularizaDel").html('');
	$('#spnPeriodoRegObra').show();
}

/**
 * Función que valida que los datos capturados en las text de tipo porcentaje , sean mayor a cero, y menores o iguales a 100
 * @author Oscar German Beltrán Ortega
 */
function validaPorcentaje(campo,labelMsg){
	var respuesta= true;
	var valorValidar = $("form#regularizarObraGenericoTABForm #" +campo).val();
	$("form#regularizarObraGenericoTABForm #" + labelMsg).html('');

	if(valorValidar != ''){
		if(parseInt(valorValidar,10) >0 && parseInt(valorValidar,10)<= 100){
			respuesta = true;
		}else{
		//caso erroneo
			$("form#regularizarObraGenericoTABForm #" + labelMsg).html('<label class="etiquetaError">El valor debe ser entre 1 y 100 </label>');
			respuesta = false;
			
		}
	}
	
	return respuesta;
}

/**
 * Función  de validacion de los datos requeridos y datos validos para el tab de regularizar obra
 * @author Oscar German Beltrán Ortega
 */
function validaTrabRegularizados(){
	
	$("form#regularizarObraGenericoTABForm #labelTrabRegularizadosRegObra").html('');
	var trabajosRegularizados = parseIntComas($("form#regularizarObraGenericoTABForm #trabRegularizadosRegObra").val() != ''?$("form#regularizarObraGenericoTABForm #trabRegularizadosRegObra").val():"0",10);
	var trabajosRevisados = parseIntComas($(jsTrabRevisadosRegularObra).val() != '' ? $(jsTrabRevisadosRegularObra).val():"0",10);
	var respuesta = false;

	if(trabajosRegularizados <= trabajosRevisados){
		respuesta = true
		
	}else{
		
		$("form#regularizarObraGenericoTABForm #labelTrabRegularizadosRegObra").html('<label class="etiquetaError"  align="rigth">La suma de los trab. Omisos + trab. Subdeclarados No puede ser MAYOR que los Trabajadores Revisados</label>');
		respuesta = false;
	}
	
	
	return respuesta;
}



/**
 * Función que limpia la fecha de notificacion del tab de regulariza oba
 * @author Oscar German Beltrán Ortega
 */
function limpiaFechaNotificacionRegObra(){
	
		$(jsPeriodoDelRegularObra).val("");
		$(jsPeriodoAlRegularObra).val("");
		

		
		$('#spnPeriodoRegObra').hide();
	
}

/**
 * Función para deshabilitar la captura los periodos del tab de regularizar obra ,
 * despues de que han sido persistidos
 * @author Oscar German Beltrán Ortega
 */
function desHabilitaCapturaFechasPeriodoRegulaObra(){
	//alert("desHabilitaCapturaFechasPeriodoRegulaObra");
	if($(jsPeriodoDelRegularObra).val() != '' || $(jsPeriodoAlRegularObra).val() != ''){

		$(jsPeriodoDelRegularObra).datepicker('destroy');
		$( jsPeriodoAlRegularObra).datepicker('destroy');
		
		$( jsPeriodoDelRegularObra).removeClass("red");
		$( jsPeriodoAlRegularObra).removeClass("red");
	}
}
	
	/**
	 * Función para validar que un entero no salga del rango permitido
	 * @author Saúl Rosales Piedragil
	 */
	function validaRangoEntero(campo, minimo, maximo){
		var longitud =campo.value.length;
		if(longitud>maximo.length){
			campo.value=campo.value.substring(0,maximo.length);
		}
		var min=parseInt(minimo);
		var max=parseInt(maximo);
		var aValidar =parseInt(campo.value);
		if(aValidar<min){
			campo.value="0";
			return;
		}
		if(aValidar>max){
			campo.value=campo.value.substring(0,campo.value.length-1);
			return;
		}
    }
	
	
	function validaParcialidadesSegProm(){
		
		var respuesta = false;
		if($("form#regularizarObraGenericoTABForm #numeroParcialidades").val() != '' ){
			var valorNumParcialid = parseInt($("form#regularizarObraGenericoTABForm #numeroParcialidades").val());
			
			if(valorNumParcialid>0 && valorNumParcialid <= 48){
				respuesta = true;
				$("form#regularizarObraGenericoTABForm #labelNumeroParcialidadesPro").html('');
			}else{
				//mensaje de validacion
				respuesta = false
				
				$("form#regularizarObraGenericoTABForm #labelNumeroParcialidadesPro").html('<label class="etiquetaError">Valor de 1 a 48</label>');
			}
			
		}else{
			respuesta=true;
		}
		return respuesta;
	}
	
	/**
	 * Valida el monto de 
	 * La Suerte Principal de la C.O.P, DEBE ser MENOR a la BASE DETERMINADA y NO puede ser IGUAL a CERO
	 * @author Oscar Beltran
	 */
	function validaMontoSuertePpalBaseDeterminada(){
		
		var resultado = false;
		var suertePpalDetCop =  $("form#regularizarObraGenericoTABForm #suertePpalDetCop").val();
		var baseDeterminada =  $("form#regularizarObraGenericoTABForm #baseDeterminada").val();
		
		$("form#regularizarObraGenericoTABForm #labelSuertePpalDetCop").html('');
		
		if((suertePpalDetCop != null && suertePpalDetCop != "") &&  (baseDeterminada != null && baseDeterminada != "")){
			if(parseFloatComas(suertePpalDetCop) < parseFloatComas(baseDeterminada)){
				if(parseFloatComas(suertePpalDetCop)<= 0){
					$("form#regularizarObraGenericoTABForm #labelSuertePpalDetCop").html('<label class="etiquetaError">Suerte Ppal COP no puede ser igual a 0</label>');
					resultado = false;
				}else{
					resultado = true;
				}
				
			}else {
				$("form#regularizarObraGenericoTABForm #labelSuertePpalDetCop").html('<label class="etiquetaError">Debe ser menor a Base Determinada</label>');
				resultado = false;
			}
		}else {
			resultado = true;
		}
		return 	resultado;
	}
	
	
	/**
	 * Valida el monto de 
	 * La Suerte Principal del R.C.V, DEBE ser MENOR a la BASE DETERMINADA y SI puede ser IGUAL a CERO.
	 * @author Oscar Beltran
	 */
	function validaMontoSuertePpalRCVBaseDeterminada(){
		
		var resultado = false;
		var suertePpalDetRCV =  $("form#regularizarObraGenericoTABForm #suertePpalDetRcv").val();
		var baseDeterminada =  $("form#regularizarObraGenericoTABForm #baseDeterminada").val();
		
		$("form#regularizarObraGenericoTABForm #labelSuertePpalDetRcv").html('');
		
		if((suertePpalDetRCV != null && suertePpalDetRCV != "") &&  (baseDeterminada != null && baseDeterminada != "")){
			if(parseFloatComas(suertePpalDetRCV) < parseFloatComas(baseDeterminada)){
				//alert("correcto");
				/*if(parseFloatComas(suertePpalDetRCV)<= 0){
					$("form#regularizarObraGenericoTABForm #labelSuertePpalDetRcv").html('<label class="etiquetaError">Suerte Ppal COP no puede ser igual a 0</label>');
					resultado = false;
				}else{
					resultado = true;
				}*/
				resultado = true;
			}else {
				$("form#regularizarObraGenericoTABForm #labelSuertePpalDetRcv").html('<label class="etiquetaError">Debe ser menor a Base Determinada</label>');
				//alert("debe ser menor");
				resultado = false;
			}
		}else{
			resultado = true;
		}
		return 	resultado;
	}
	
	
	function validaBloquearPagosAct(suertePpalPenPagoCop,suertePpalPenPagoRcv){
		
		var suertePrincipalDetCop =parseFloatComas( $("form#regularizarObraGenericoTABForm #suertePpalDetCop").val() != ''?$("form#regularizarObraGenericoTABForm #suertePpalDetCop").val():0 ,10);
		var suertePrincipalDetRcv =parseFloatComas( $("form#regularizarObraGenericoTABForm #suertePpalDetRcv").val() != ''?$("form#regularizarObraGenericoTABForm #suertePpalDetRcv").val():0 ,10);
		
	
		if((suertePpalPenPagoCop <= 0 && suertePrincipalDetCop != '' && suertePrincipalDetCop >= 0 && suertePrincipalDetCop != "NaN") && 
				((suertePpalPenPagoRcv <= 0 && suertePrincipalDetRcv != '') &&  (suertePrincipalDetRcv>= 0 && suertePrincipalDetRcv != "NaN")) ){
		//bloquear todo
			$("form#regularizarObraGenericoTABForm #btnDatosRegularizacion").prop('disabled','disabled');
			$("form#regularizarObraGenericoTABForm #btnGuardarRegularizaObra").prop('disabled','disabled');
			$('form#regularizarObraGenericoTABForm input[type=text]').prop('readonly','readonly');
			//$('form#regularizarObraGenericoTABForm input[type=hidden]').prop('disabled','disabled');
			$('form#regularizarObraGenericoTABForm input[type=text]').removeClass("red");
			desHabilitaCapturaFechasPeriodoRegulaObra ();
		}else{
			if(suertePpalPenPagoCop > 0 || suertePpalPenPagoRcv > 0){
				$("form#regularizarObraGenericoTABForm #btnDatosRegularizacion").removeAttr('disabled');
			}
		}
		
	}