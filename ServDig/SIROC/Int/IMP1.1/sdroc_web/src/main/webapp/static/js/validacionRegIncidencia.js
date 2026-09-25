var tipoIncidencia = 0;
var datosRegIncidencia = {};
var regExtemporaneo = 0;

$(document).ready(function() {
	
	var configMask = {prefix:'', thousands:',', allowZero:true, allowNegative:false, defaultZero:false, decimal: '.', precision: 0, affixesStay : false, symbolPosition : 'left',allowEmpty: true};
	
	$('#txtMontoEjercido').maskMoney(configMask);
	$('#txtMonto').maskMoney(configMask);
	$('#txtSuperficie').maskMoney(configMask);
	
	$("#txtMontoEjercido").keypress(function(e) {
		if (!onlyNumber(e)) {
			return false;
		}
	});
	
	$('input').bind("cut copy paste",function(e) {
         e.preventDefault();
     });
	
	/**
	 * Incidencia actualizacion
	 */
	$("#fecTerminoActualiza").datepicker({		
		beforeShow : function() {
			setTimeout(function() {
				$('.ui-datepicker').css(
						'z-index',
						99999999999999);
				}, 0);
			}, 
			onSelect : function() {
					var fechaTermino = $(this).datepicker('getDate');
					
					var idMensaje = $("#msgeFecTermino").attr('id');
					var idCampo = $("#fecTerminoActualiza").attr('id');
					var fechaRegistrada = $("#lblFechaInicio").text();
					var arr = fechaRegistrada.split('/');

					console.log(arr[0]);
					console.log(arr[1]);
					console.log(arr[2]);

					var r = getDate();
					r.setDate(arr[0]);
					r.setMonth(arr[1]-1);
					r.setYear(arr[2]);
					var fechaInicio = r;
					
					var mensajeFecInicio = "La fecha seleccionada debe ser igual o mayor a la fecha de inicio";
				
					var btnFecTermino = false;
					if(fechaTermino.toDateString() === fechaInicio.toDateString() || fechaTermino > fechaInicio){
						setDefaultColor(idCampo);
						btnFecTermino = true;
						limpiarEtiqueta(idMensaje);
					}else{
						btnFecTermino = false;
						setErrorColor(idCampo);
						mostrarEtiqueta(idMensaje, mensajeFecInicio);
					}
					var bndFecReanuda = false;
					var bndFecRepBim = false;
					var mensajeErr;
					var ultimaFecReanudacion = $("#ultimaFecReanudacion").text();
					if(ultimaFecReanudacion!=0){
						ultimaFecReanudacion = new Date($("#ultimaFecReanudacion").text());
						if(fechaTermino < ultimaFecReanudacion){
							setErrorColor(idCampo);
							mensajeErr = "Fecha de término no puede ser menor a la última fecha de reanudación.";
							mostrarEtiqueta(idMensaje, mensajeErr);
						}else{
							bndFecReanuda=true;
						}
					}
					var reporteBimestralPresentar = $("#reporteBimestralPresentar").text();
					if(reporteBimestralPresentar!=0){
						
						
						//reporteBimestralPresentar = converToDate($("#reporteBimestralPresentar").text());
						 var a = reporteBimestralPresentar.split("/");
						 var fechaReporteBimestral;
						 
						 if((a[2] % 4 == 0) && ((a[2] % 100 != 0) || (a[2] % 400 == 0))){
							 if(a[1] == 2){
								 fechaReporteBimestral = new Date(a[2],a[1]-1,29);
							 }else{
								 fechaReporteBimestral = new Date(a[2],a[1]-1,a[0]);
							 }
						 }else{
							 fechaReporteBimestral = new Date(a[2],a[1]-1,a[0]);
						 }		 
						
						if(fechaTermino <= fechaReporteBimestral){
							setErrorColor(idCampo);
							mensajeErr = "Fecha de término no puede ser menor al último \"Bimestre y Año a presentar\"";
							mostrarEtiqueta(idMensaje, mensajeErr);
						}else{
							bndFecRepBim=true;
						}
					}
					if(bndFecReanuda && bndFecRepBim && btnFecTermino){
						setDefaultColor(idCampo);
						limpiarEtiqueta(idMensaje);
					}
			}
	});
	
	/**
	 * RN para Fecha de reanudacion :  
	 * 	- debe ser menor a la fecha del sistema 
	 *  - debe ser mayor a la ultima fecha de de suspension
	 *  - debe ser menor a la fecha de termino 
	 */
	$("#fecReanudacion").datepicker({
		beforeShow : function() {
			setTimeout(function() {
				$('.ui-datepicker').css(
						'z-index',
						99999999999999);
				}, 0);
			},
			onSelect : function() {
				
				var fechaSeleccionada = $(this).datepicker('getDate');
				var idMensaje = $("#msgeFecReanudacion").attr('id');
				var idCampo = $("#fecReanudacion").attr('id');
				var idMensajeFecTer = $("#msgeFecTermino").attr('id');
				var idCampoFecTer = $("#fecTerminoReanuda").attr('id');
						
				if (validarFechaMenorActual(fechaSeleccionada, idMensaje)) {
					var fechaTermino = $("#lblFechaTermino").text();
					var fechaSuspension = $("#lblFecSuspencionR").text();
					
					if(validarFechaDentroPeriodo(fechaSeleccionada, fechaSuspension, fechaTermino, idMensaje)){
						limpiarEtiqueta(idMensaje);
						setDefaultColor(idCampo);
						$("#fecTerminoReanuda").removeAttr('disabled');
					}else{
						var mensaje = '';
						if(fechaSeleccionada < convertirFechaFormatoDatePicker(fechaSuspension)) {
							mensaje = "La fecha seleccionada debe ser mayor o igual a la fecha de suspensión registrada";
						} else {
							mensaje = $("#msgeFecReanudacion").text();
						}
						mostrarEtiqueta(idMensaje, mensaje);
						setErrorColor(idCampo);
						$("#fecTerminoReanuda").attr('disabled');
						$("#fecTerminoReanuda").datepicker('setDate', null);
						limpiarEtiqueta(idMensajeFecTer);
						setDefaultColor(idCampoFecTer);
					}
				}else{
					setErrorColor(idCampo);
					$("#fecTerminoReanuda").attr('disabled');
					$("#fecTerminoReanuda").datepicker('setDate', null);
					limpiarEtiqueta(idMensajeFecTer);
					setDefaultColor(idCampoFecTer);
				}
			}
	});
	
	/**
	 * RN - Opcional - Fecha de termino en reanudacion
	 *  - debe ser mayor a la fecha suspension
	 *  - debe ser mayor a la fecha de reanudacion
	 */
	$("#fecTerminoReanuda").datepicker({
		beforeShow : function() {
			setTimeout(function() {
				$('.ui-datepicker').css(
						'z-index',
						99999999999999);
				}, 0);
			},
		onSelect : function() {
			
			var fechaTerminoNueva = $(this).datepicker('getDate');
			var fechaSuspension = $("#lblFecSuspencionR").text();
			var fechaReanudacion = $("#fecReanudacion").datepicker('getDate');
			
			var idMensaje = $("#msgeFecTermino").attr('id'); 			
			var mensajeSuspension = "La fecha de término no puede ser menor a la fecha de suspensión";
			var mensajeReanudacion = "Fecha de término no puede ser menor a la última fecha de reanudación.";
			
			var monto = $("#txtMonto").val();
			var superficie = $("#txtSuperficie").val();
			
			if(!validarFechaMenor(fechaTerminoNueva, fechaReanudacion, idMensaje, mensajeReanudacion)){
				console.log('fecha de termino menor a la reanudacion ');
				$("#motivo").removeAttr('disabled');
				
				if(!validarFechaMenor(fechaTerminoNueva, fechaSuspension, mensajeSuspension)){
					console.log('fecha de termino menor a la fecha de suspension');
					$("#motivo").removeAttr('disabled');
				}
			} else {
				if((superficie != undefined && superficie != '' && parseInt(superficie) >= 0) 
						|| (monto != undefined && monto != '' && parseInt(monto) >= 0) 
						|| (fechaTerminoNueva != null && fechaTerminoNueva.length != 0)) {
					$("#motivo").removeAttr('disabled');
				} else {
					$("#motivo").val(0);
					$("#motivo").attr("disabled", true);
					limpiarEtiqueta($("#msgeMotivoReanudacion").attr('id'));
					setDefaultColor($("#motivo").attr('id'));
				}
			}
			
			var reporteBimestralPresentar = $("#reporteBimestralPresentar").text();
			var idCampo = $("#fecTerminoReanuda").attr('id');
			
			if(reporteBimestralPresentar!=0){
				 var a = reporteBimestralPresentar.split("/");
				 var fechaReporteBimestral;
				 
				 if((a[2] % 4 == 0) && ((a[2] % 100 != 0) || (a[2] % 400 == 0))){
					 if(a[1] == 2){
						 fechaReporteBimestral = new Date(a[2],a[1]-1,29);
					 }else{
						 fechaReporteBimestral = new Date(a[2],a[1]-1,a[0]);
					 }
				 }else{
					 fechaReporteBimestral = new Date(a[2],a[1]-1,a[0]);
				 }		 
				 	 
				 
				if(fechaTerminoNueva <= fechaReporteBimestral){
					console.log('fecha termino menor a la del reporte ');
					setErrorColor(idCampo);
					mensajeErr = "Fecha de término no puede ser menor al último reporte bimestral presentado";
					mostrarEtiqueta(idMensaje, mensajeErr);
				}else{
					console.log('todo bien, fecha de termino mayor a la del reporte bimestral');
					setDefaultColor(idCampo);
					limpiarEtiqueta(idMensaje);
				}
			}else{
				console.log('sin fecha de reporte bimestral');
			}
		}
	});
	/**
	 * Incidencia cancelacion
	 */
	$("#fecCancelacion")
	.datepicker(
			{
				beforeShow : function() {
					setTimeout(function() {
						$('.ui-datepicker').css(
								'z-index',
								99999999999999);
					}, 0);
				},
				onSelect : function() {

					var fechaSeleccionada = $(this).datepicker('getDate');
					var idMsgFecha = $("#msgefecCancelacion").attr('id');
					var idFechaCancela = $("#fecCancelacion").attr('id');
					
					
					if (validarFechaMenorActual(fechaSeleccionada, idMsgFecha)) {
						console.log('fecha menor a la actual ... bien ');
						var fechaInicio = $("#lblFechaInicio").text();
						var fechaTermino = $("#lblFechaTermino").text();
						if(validarFechaDentroPeriodo(fechaSeleccionada, fechaInicio, fechaTermino, idMsgFecha)){
							console.log('fecha dentro del periodo');
							var ultimaSusp = $('#ultimaSuspencion').text();
							if(ultimaSusp!=0){
								console.log('fecha de suspension existe');
								if(fechaSeleccionada>=new Date(ultimaSusp)){
									console.log('fecha seleccionada mayor a la fecha de suspension');
									limpiarEtiqueta(idMsgFecha);
									setDefaultColor(idFechaCancela);
								}else{
									console.log('Fecha de cancelación debe ser mayor o igual a última fecha de suspensión');
									mostrarEtiqueta(idMsgFecha, "Fecha de cancelación debe ser mayor o igual a última Fecha de suspensión");
									setErrorColor(idFechaCancela);
								}
							}
						}else{
							console.log('la obra no tiene fecha de suspension');
							setErrorColor(idFechaCancela);
						}
					}else{
						console.log('la fecha seleccionada es mayor a la actual');
						setErrorColor(idFechaCancela);
					}

				}
			});
	
	/**
	 * mayor o igual a la fecha de inicio o a la ultima fecha de reanudacion 
	 * 
	 */
	$("#fecSuspension").datepicker({
		beforeShow : function() {
			setTimeout(function() {
				$('.ui-datepicker').css(
						'z-index',
						99999999999999);
				}, 0);
			}
	});
	
	/**
	 * mayor o igual a la fecha de inicio o a la ultima fecha de reanudacion 
	 */
	$("#fecTerminacion").datepicker({
		beforeShow : function() {
			setTimeout(function() {
				$('.ui-datepicker').css(
						'z-index',
						99999999999999);
				}, 0);
			},
			onSelect : function() {
				var fechaSeleccionada = $(this).datepicker('getDate');
				var idMsgFecha = $("#msgeFecTerminacion").attr('id');
				var idFecTerminacion = $("#fecTerminacion").attr('id');
				
				if (validarFechaMenorActual(fechaSeleccionada, idMsgFecha)) {
					console.log('fecha menor a la actual ... bien ');
					var fechaInicio = $("#lblFechaInicio").text();
					var fechaTermino = $("#lblFechaTermino").text();
					if(validarFechaDentroPeriodo(fechaSeleccionada, fechaInicio, fechaTermino, idMsgFecha)){
						setDefaultColor(idFecTerminacion);
						console.log('fecha dentro del periodo');
					}else{
						console.log('fecha fuera del periodo');
					}
				}else{
					console.log('la fecha seleccionada es mayor a la actual');
					setErrorColor(idFecTerminacion);
				}
			}
	});	

	$("#btnRegistroIncidencia").click(function(e) {
		tipoIncidencia = $("#idAccion").val();
		
		var tituloRegistroIncidencia =  "Registro de Incidencia",
			tituloRemplazoObra = "Registro de obra";

		if (tipoIncidencia == 1) {
			console.log('cancelacion');
			
			if(validarIncidenciaCancelacion()){
				console.log('validaciones bien');
				$('#msgConfirmacion').text('¿Está seguro de registrar la cancelación de la obra con la información capturada?');
				crearDialogo("#regIncidenciaModal",{"NO":function(){$(this).dialog("close")},"SI":function(){$(this).dialog("close");firmarIncidencia()}},tituloRegistroIncidencia);
			}else{
				console.log('validaciones fallaron');
			}
		} else if (tipoIncidencia == 2) {
			console.log('suspension');
			if(validarIncidenciaSuspension()){
				console.log('validaciones bien');
				$('#msgConfirmacion').text('¿Está seguro de registrar la suspensión de la obra con la información capturada?');
				crearDialogo("#regIncidenciaModal",{"NO":function(){$(this).dialog("close")},"SI":function(){$(this).dialog("close");firmarIncidencia()}},tituloRegistroIncidencia);			}else{
				console.log('validaciones fallaron');
			}
		} else if (tipoIncidencia == 3) {
			console.log('terminacion');
			if(validarIncidenciaTerminacion()){
				console.log('validaciones bien');
				$('#msgConfirmacion').text('¿Está seguro de registrar la terminación de la obra con la información capturada?');
				crearDialogo("#regIncidenciaModal",{"NO":function(){$(this).dialog("close")},"SI":function(){$(this).dialog("close");firmarIncidencia()}},tituloRegistroIncidencia);			}else{
				console.log('validaciones fallaron');
			}
		} else if (tipoIncidencia == 4) {
			console.log('actualizacion');
			if(validarIncidenciaActualizacion()){
				console.log('validacion dato bien');
				$('#msgConfirmacion').text('¿Está seguro de registrar la actualización de la obra con la información capturada?');
				crearDialogo("#regIncidenciaModal",{"NO":function(){$(this).dialog("close")},"SI":function(){$(this).dialog("close");firmarIncidencia()}},tituloRegistroIncidencia);			}else{
				console.log('validaciones fallaron');
			}
		} else if (tipoIncidencia == 5) {
			console.log('reanudacion');
			if(validarIncidenciaReanudacion()){
				console.log('validacion dato bien');
				$('#msgConfirmacion').text('¿Está seguro de registrar la reanudación de la obra con la información capturada?');
				crearDialogo("#regIncidenciaModal",{"NO":function(){$(this).dialog("close")},"SI":function(){$(this).dialog("close");firmarIncidencia()}},tituloRegistroIncidencia);			}else{
				console.log('validaciones fallaron');
			}
		} else if (tipoIncidencia == 6) {
			console.log('reporte bimestral');
			if(validarIncidenciaReporteBimestral()){
				console.log('validacion dato bien');
				$('#msgConfirmacion').text('¿Está seguro de registrar el reporte bimestral de la obra con la información capturada?');
				crearDialogo("#regIncidenciaModal",{"NO":function(){$(this).dialog("close")},"SI":function(){$(this).dialog("close");firmarIncidencia()}},tituloRegistroIncidencia);
			}else{
				console.log('validaciones fallaron');
			}
		}
		else if (tipoIncidencia == 7) {
			console.log('remplazo de obra');
			if(validarIncidenciaRemplazo()){
				console.log('validacion dato bien');
				$('#msgConfirmacion').text('¿Está seguro de registrar la obra con la información capturada?');
				crearDialogo("#regIncidenciaModal",{"NO":function(){$(this).dialog("close")},"SI":function(){$(this).dialog("close");firmarIncidencia()}},tituloRemplazoObra);
			}else{
				console.log('validaciones fallaron');
			}
		}
	});
	
	
	$("#txtMonto").change(function(e) {
		var monto = $("#txtMonto").val();
		var superficie = $("#txtSuperficie").val();
		var fechaTerminoReanuda = $("#fecTerminoReanuda").datepicker('getDate');
		var tipoIncidencia = $("#idAccion").val();

		if((superficie != undefined && superficie != '' && parseInt(superficie) >= 0) 
				|| (monto != undefined && monto != '' && parseInt(monto) >= 0) 
				|| (fechaTerminoReanuda != null && fechaTerminoReanuda.length != 0)) {
			$("#motivo").removeAttr('disabled');
		} else {
			if(tipoIncidencia == 5) {		// Solo aplica para reanudacion
				$("#motivo").val(0);
				$("#motivo").attr("disabled", true);
				limpiarEtiqueta($("#msgeMotivoReanudacion").attr('id'));
				setDefaultColor($("#motivo").attr('id'));
			}
		}
	});
	
	$("#txtSuperficie").change(function(e) {
		var monto = $("#txtMonto").val();
		var superficie = $("#txtSuperficie").val();
		var fechaTerminoReanuda = $("#fecTerminoReanuda").datepicker('getDate');
		var tipoIncidencia = $("#idAccion").val();
		
		if((superficie != undefined && superficie != '' && parseInt(superficie) >= 0) 
				|| (monto != undefined && monto != '' && parseInt(monto) >= 0) 
				|| (fechaTerminoReanuda != null && fechaTerminoReanuda.length != 0)) {
			$("#motivo").removeAttr('disabled');
		} else {
			if(tipoIncidencia == 5) {		// Solo aplica para reanudacion
				$("#motivo").val(0);
				$("#motivo").attr("disabled", true);
				limpiarEtiqueta($("#msgeMotivoReanudacion").attr('id'));
				setDefaultColor($("#motivo").attr('id'));
			}
		}
	});
	
	
	/**
	 * Solicita firma digital
	 */
	function firmarIncidencia() {
		firmaExternaCtrl.firmarRegistroObra(VerificarIncidencia,"|Tramite: Registro de incidencia de obra de construcción");
	}	
	
	/**
	 * Realiza la invocacion al registro de incidencia
	 */
	function VerificarIncidencia(firmaResponse) {
		var accion = "/sdroc_web/datosFirma";
		
		$.blockUI();
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : accion,
			data : JSON.stringify(firmaResponse),
			cache: false,
			success : function(response) {
				$.unblockUI();
				if (tipoIncidencia == 1) {
					if(registrarIncidenciaCancelacion()){
						console.log('todo bien');
						$('#pnlIncCancelacion').find('*').prop('disabled',true);
					}
				} else if(tipoIncidencia == 2){
					if(registrarIncidenciaSuspencion()){
						console.log('registro suspension bien ');
						$('#pnlIncSuspension').find('*').prop('disabled',true);
					}
				} else if(tipoIncidencia == 3){
					if(registrarIncidenciaTerminacion()){
						console.log('registro terminacion bien ');
						$('#pnlIncTerminacion').find('*').prop('disabled',true);
					}
				} else if(tipoIncidencia == 4){
					if(registrarIncidenciaActualizacion()){
						console.log('registro actualizacion bien ');
						$('#pnlIncActualizacion').find('*').prop('disabled',true);
					}
				} else if(tipoIncidencia == 5){
					if(registrarIncidenciaReanudacion()){
						console.log('registro reanudacion bien ');
						$('#pnlIncReanudacion').find('*').prop('disabled',true);
					}
				} else if(tipoIncidencia == 6){
					if(registrarIncidenciaReporteBimestral()){
						console.log('registro reporte bimestral bien ');
						$('#pnlIncReporteB').find('*').prop('disabled',true);
					}
				} else if(tipoIncidencia == 7){
					if(registrarRemplazoObra()){
						console.log('registro remplazo de obra ');
						$('#pnlIncRemplazo').find('*').prop('disabled',true);
					}
				}
				$('#pnlAcuseRegIncidencia').removeClass('hidden');
				finalizarRegIncidencia();
			}
		});
	}
	
	/**
	 * Registro de terminacion
	 */
	function registrarIncidenciaTerminacion(){
		
		var fecTerminacion = $("#fecTerminacion").datepicker('getDate');
		var montoEjercido = $("#txtMontoEjercido").val().replace(/,/g,'');
		
		var cveObra = $("#cveInformacionObra").val();
		
		
		if($("#txtSuperficie").val() != undefined){
			var superficieConstruida = $("#txtSuperficie").val().replace(/,/g,'');
		}

		regExtemporaneo = validarRegistroExtemporaneo(fecTerminacion);
		
		datosRegIncidencia = {
				cveInformacionObra : cveObra,
				motivoTipoIncidenciaDTO:{cveMotivoTipoIncidencia:22,
					 					 tipoIncidenciaDTO:{cveTipoIncidencia:3},
					 					 motivoDTO:{cveMotivo:22}
										 },							
			  tipoRegistroDTO:{cveTipoRegistro : regExtemporaneo},
			  stpRegIncidencia : getDate(),
			  fecFinObra : fecTerminacion,
			  
			  impEjercido : parseFloat(montoEjercido),
			  };
		
		if($("#txtSuperficie").val() != undefined){
			datosRegIncidencia.refSupConstruccion = superficieConstruida;
		}
		
		var accionTerminacion = '/sdroc_web/registroIncidencia';
		var resultado = null;
		resultado = comunicacionController(accionTerminacion, datosRegIncidencia);
		
		return resultado;
	}	
	
	/**
	 * Registro de suspension
	 */
	function registrarIncidenciaSuspencion(){
		var fecSuspension = $("#fecSuspension").datepicker('getDate');
		var monto = $("#txtMonto").val().replace(/,/g,'');
		var motivo = $("#motivo").val();
		var cveObra = $("#cveInformacionObra").val();
		regExtemporaneo = validarRegistroExtemporaneo(fecSuspension);
		datosRegIncidencia = {
				cveInformacionObra : cveObra,
				motivoTipoIncidenciaDTO:{cveMotivoTipoIncidencia:motivo,
										 tipoIncidenciaDTO:{cveTipoIncidencia:2},
									     motivoDTO:{cveMotivo:motivo}
										},									
			  tipoRegistroDTO:{cveTipoRegistro : regExtemporaneo},
			  stpRegIncidencia : getDate(),
			  fecSuspencion : fecSuspension,
			  //impObra : parseFloat(4),
			  impEjercido : parseFloat(monto),
			  };
		
		var accionCancelacion = '/sdroc_web/registroIncidencia';
		var resultado = null;
		resultado = comunicacionController(accionCancelacion, datosRegIncidencia);
		
		return resultado;
	}
	
	/**
	 * Registro de cancelacion
	 */
	function registrarIncidenciaReanudacion(){
		var cveObra = $("#cveInformacionObra").val();
		var fechaReanudacion = $("#fecReanudacion").datepicker('getDate');
		var fechaTermino = $("#fecTerminoReanuda").datepicker('getDate');
		var monto = $("#txtMonto").val().replace(/,/g,'');
		
		var motivo = $("#motivo").val();
		var montoObra = $("#lblMontoObra").val();
		var fechaTerminoIn = $("#lblFechaTermino").text();
		var idSuperficieIn = $("#idSuperficieIn").val();
		if(monto == null || monto == ''){
			monto = montoObra;
		}else{
			$("#motivo").val(0);
			$("#motivo").attr("disabled", true);
			limpiarEtiqueta($("#msgeMotivoReanudacion").attr('id'));
			setDefaultColor($("#motivo").attr('id'));
		}
		
		var superficie = null;
		if($("#txtSuperficie").val() != undefined){
			superficie = $("#txtSuperficie").val().replace(/,/g,'');
			
			if(superficie == null || superficie.length === 0 ){
				superficie = idSuperficieIn;
			}else{
				$("#motivo").val(0);
				$("#motivo").attr("disabled", true);
				limpiarEtiqueta($("#msgeMotivoReanudacion").attr('id'));
				setDefaultColor($("#motivo").attr('id'));
			}
		}
		
		
		if(fechaTermino == null || fechaTermino == ''){
			var fecTermino = fechaTerminoIn;
			var partsFin = fecTermino.split('/');
			var dateFin = new Date(partsFin[2], partsFin[1] - 1,
					partsFin[0]);
			fechaTermino = dateFin;
		}
		if(motivo == 0){
			motivo = 1;
		}
		regExtemporaneo = validarRegistroExtemporaneo(fechaReanudacion);
		if($("#motivo").val() == ''){
		datosRegIncidencia = {
				cveInformacionObra : cveObra,
				motivoTipoIncidenciaDTO:{cveMotivoTipoIncidencia: motivo,
										   tipoIncidenciaDTO:{cveTipoIncidencia:5},
									       motivoDTO:{cveMotivo:motivo}
										},									
			  tipoRegistroDTO:{cveTipoRegistro : regExtemporaneo},
			  stpRegIncidencia : getDate(),
			  fecReanudacion : fechaReanudacion,
			  fecFinObra : fechaTermino,
			  impObra : parseFloat(monto)
			 // ,impEjercido : parseFloat(monto),
			  };
		
		}else{
			datosRegIncidencia = {
					cveInformacionObra : cveObra,
					motivoTipoIncidenciaDTO:{cveMotivoTipoIncidencia: 23,
											   tipoIncidenciaDTO:{cveTipoIncidencia:5},
										       motivoDTO:{cveMotivo:motivo}
											},									
				  tipoRegistroDTO:{cveTipoRegistro : regExtemporaneo},
				  stpRegIncidencia : getDate(),
				  fecReanudacion : fechaReanudacion,
				  fecFinObra : fechaTermino,
				  impObra : parseFloat(monto)
				 // ,impEjercido : parseFloat(monto),
				  };
		}
		
		if($("#txtSuperficie").val() != undefined){
			datosRegIncidencia.refSupConstruccion = parseFloat(superficie);
		}
		
		var accionReanudacion = '/sdroc_web/registroIncidencia';
		var resultado = null;
		resultado = comunicacionController(accionReanudacion, datosRegIncidencia);
		
		return resultado;
	}
	
	/**
	 * Registro de actualizacion
	 */
	function registrarIncidenciaActualizacion(){
		var fechaTermino = $("#fecTerminoActualiza").datepicker('getDate');
//		var fechaTerminoText = fechaTermino.text();
		var monto = $("#txtMonto").val().replace(/,/g,'');
		var superficie = null;
		if($("#txtSuperficie").length){
			superficie = $("#txtSuperficie").val().replace(/,/g,'');
		}
		
		
		
		var motivo = $("#motivo").val();
		var cveObra = $("#cveInformacionObra").val();
		
		if (fechaTermino == '' || fechaTermino == "0") {
			console.log('actualizacion ...');
			console.log('fecha termino vacia');
			
		}else{
			//regExtemporaneo = validarRegistroExtemporaneo(fechaTermino);
			datosRegIncidencia = {
					cveInformacionObra : cveObra,
					motivoTipoIncidenciaDTO:{cveMotivoTipoIncidencia:1,
						 tipoIncidenciaDTO:{cveTipoIncidencia:4},
						 motivoDTO:{cveMotivo:motivo}
						 },
					tipoRegistroDTO:{cveTipoRegistro : '1'},					
					stpRegIncidencia : getDate(),			  
					impObra : parseFloat(monto),
					fecFinObra: fechaTermino,
					fecActualizacion: getDate()
				  };
			
			if($("#txtSuperficie").length){
				datosRegIncidencia.refSupConstruccion = parseFloat(superficie);
			}
			
			var accionActualizacion = '/sdroc_web/registroIncidencia';
			var resultado = null;
			resultado = comunicacionController(accionActualizacion, datosRegIncidencia);
			return resultado;
		}
		
	}
	
	
	/**
	 * Registro de cancelacion
	 */
	function registrarIncidenciaCancelacion(){
		var fecha = $("#fecCancelacion").datepicker('getDate');
		var monto = $("#txtMontoEjercido").val().replace(/,/g,'');
		var motivo = $("#motivo").val();
		var cveObra = $("#cveInformacionObra").val();
		
		//calcular extraornidarios en datepicker
		regExtemporaneo = validarRegistroExtemporaneo(fecha);
		datosRegIncidencia = {
				cveInformacionObra : cveObra,
				motivoTipoIncidenciaDTO:{cveMotivoTipoIncidencia:motivo,
										 tipoIncidenciaDTO:{cveTipoIncidencia:1},
									     motivoDTO:{cveMotivo:motivo}
										},									
			  tipoRegistroDTO:{cveTipoRegistro : regExtemporaneo},
			  stpRegIncidencia : getDate(),
			  fecCanObra : fecha,
			  //impObra : parseFloat(4),
			  impEjercido : parseFloat(monto),
			  };
		
		var accionCancelacion = '/sdroc_web/registroIncidencia';
		var resultado = null;
		resultado = comunicacionController(accionCancelacion, datosRegIncidencia);
		
		return resultado;
		
	}
	
	/**
	 * Registro de reporte bimestral
	 */
	function registrarIncidenciaReporteBimestral(){
		var cveObra = $("#cveInformacionObra").val();
		var fecBimestral = $("#idBimestre").val();
		var monto = $("#txtMontoEjercido").val().replace(/,/g,'');
		var arregloFecBimestral = fecBimestral.split('-');
		var anio = arregloFecBimestral[1];
		var cveBimestre = arregloFecBimestral[0];
		
		datosRegIncidencia = {
				cveInformacionObra : cveObra,
				motivoTipoIncidenciaDTO:{cveMotivoTipoIncidencia:14,
					 tipoIncidenciaDTO:{cveTipoIncidencia:6},
					 motivoDTO:{cveMotivo:13}
					 },
				tipoRegistroDTO:{cveTipoRegistro : '1'},
				calendarioReporteDTO :{cveBimCalendario : parseFloat(cveBimestre)},
				stpRegIncidencia : getDate(),			  
				impObra : parseFloat(monto),
				impEjercido : parseFloat(monto),
				numAnio : parseInt(anio)
			  };
		
		var accionRepBimes = '/sdroc_web/registroIncidencia';
		var resultado = null;
		resultado = comunicacionController(accionRepBimes, datosRegIncidencia);
		
		return resultado;
	}

	/**
	 * Registro de remplazo de obra
	 */
	function registrarRemplazoObra(){

		var url = "/sdroc_web/registrarObraRemplazo";
		var resultado = null;

		var fecTermino = $("#fecTerminoRemplazo").val();
		dateFin = null;
		if (fecTermino != '') {
			var partsFin = fecTermino.split('/');
			dateFin = new Date(partsFin[2],	parseInt(partsFin[1],10) - 1, partsFin[0]);
		}

		var desObjetoContratoSubEsp = $("#txtObjetoContratoSubEsp").val();
		var monto = $("#txtMontoRemplazo").val().replace(/,/g, '');
		var numAproxTrabajadores = $("#txtNumTrabajadores").val().replace(/,/g, '');
		var numRegStps = $("#txtNumRegSTPS").val();
		var cveInformacionObra = $("#cveInformacionObra").val();
		var aplicaIncRemplazo = true;
		var refObservacion = $("#comment").val();
		var cveObraRemplazado = $("#idCveRegistroObra").val();

		datosRegObra = {
			fecFinObra : dateFin,
			desObjetoContratoSubEsp : desObjetoContratoSubEsp,
			impObra : parseFloat(monto),
			numAproxTrabajadores : numAproxTrabajadores,
			numRegStps : numRegStps,
			cveInformacionObra : cveInformacionObra,
			aplicaIncRemplazo : aplicaIncRemplazo,
			refObservacion : refObservacion,
			cveObraRemplazado :cveObraRemplazado
		};
		var resultado = enviarControllerRemplazo(url, datosRegObra);
		return resultado;
	}

	function enviarControllerRemplazo(accion, data) {
		var resultado = true;
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : accion,
			async : false,
			data : JSON.stringify(data),
			timeout : 100000,
			cache : false,
			success : function(response) {

				$('#pnlRepIncidencia').attr("src",'/sdroc_web/getRegistroObraPDF');

			},
			error : function(response) {
				resultado =  false;
			}
		});
		return resultado;
	}
	
	/**
	 * 
	 */
	function comunicacionController(accion, data) {
		var resultado = true;
		$.ajax({
			type : "POST",
			contentType : "application/json",
			async : false,
			url : accion,
			data : JSON.stringify(data),
			timeout : 100000,
			cache: false,
			success:function(response) { 
				console.log("validacionRegIncidencia: " + response);
				$('#pnlRepIncidencia').attr("src",'/sdroc_web' + response);
//				$("#firmaDigitalIncidencia").modal('toggle');
				
				
			},
			error:function(response){
				resultado =  false;
			}
		});
		
		return resultado;
	}
	
	/**
	 * 	
	 */
	function validarIncidenciaCancelacion() {
		var resulFec = true;
		var resulMon = true;
		var resulMot = true;
		var resultadoValidacion = true;
		var idMsgFecha = $("#msgefecCancelacion").attr('id');
		var idFechaCancela = $("#fecCancelacion").attr('id');
		var mensajeFecha = 'Seleccione la fecha de cancelación';

		if (!validarValorMontos(idFechaCancela,
			idMsgFecha, mensajeFecha)) {
			resulFec = false;
		}else{
			var fechaSeleccionada = $("#fecCancelacion").datepicker('getDate');	
			var fechaInicio = $("#lblFechaInicio").text();
			var fechaTermino = $("#lblFechaTermino").text();
			var ultimaSusp = $('#ultimaSuspencion').text();
			
			if (validarFechaMenorActual(fechaSeleccionada, idMsgFecha)) {
				if(validarFechaDentroPeriodo(fechaSeleccionada, fechaInicio, fechaTermino, idMsgFecha)){
					resulFec = true;
					if(ultimaSusp!=0){
						if(fechaSeleccionada>=new Date(ultimaSusp)){
							limpiarEtiqueta(idMsgFecha);
							setDefaultColor(idFechaCancela);
						}else{
							resulFec = false;
							mostrarEtiqueta(idMsgFecha, "Fecha de cancelación debe ser mayor o igual a última Fecha de suspensión");
							setErrorColor(idFechaCancela);
						}
					}
				}else{
					resulFec = false;
					setErrorColor(idFechaCancela);
				}
			}else{
				resulFec = false;
				setErrorColor(idFechaCancela);
			}

		}

		var idMonto = $("#txtMontoEjercido").attr('id');
		var idMsgMonto = $("#msgMontoEjercido").attr('id');
		var mensajeMontoCaptura = 'Capture el monto ejercido a la fecha';

		if (!validarValorMontos(idMonto, idMsgMonto, mensajeMontoCaptura)) {
			resulMon = false;
		} else {
			var montoObra = $("#lblMontoObra").text().replace(/,/g, '').replace('$','');
			var montoEjerFec = $("#lblMontoEjercido").text().replace(/,/g, '').replace('$','');
			var montoCap = $("#txtMontoEjercido").val().replace(/,/g, '').replace('$','');
			
			
			
			if(parseInt(montoCap) >= parseInt(montoEjerFec)){
				if(parseInt(montoCap) <= parseInt(montoObra)){
					resulMon = true;
					limpiarEtiqueta(idMsgMonto);
					setDefaultColor(idMonto);
				}else{
					resulMon = false;
					mostrarEtiqueta(idMsgMonto, 'El monto ejercido a la fecha, no puede ser mayor al monto de la obra registrado, debe actualizar.');
					setErrorColor(idMonto);
					
				}
				
			} else {
				resulMon = false;
				mostrarEtiqueta(idMsgMonto, 'El monto ingresado no puede ser menor al último monto ejercido a la fecha');
				setErrorColor(idMonto);
			}			
		}

		var idMotivo = $("#motivo").attr('id');
		var idMsgMotivo = $("#msgeMotivoCancela").attr('id');
		var mensajeMotivoCaptura = 'Seleccione el motivo de la cancelación';

		if (!validarValorProporcionado(idMotivo, idMsgMotivo,
				mensajeMotivoCaptura)) {
			resulMot = false;
		}
	
		if(resulFec && resulMot && resulMon){
			resultadoValidacion = true;
		} else {
			resultadoValidacion = false;
		}
		return resultadoValidacion;

	}
	
	
	
	
	/**
	 * valida los campos requeridos para el registro de la actualización de la obra.
	 */
	function validarIncidenciaActualizacion() {

		var resulFec = true;
		var resulMon = true;
		var resulMot = true;
		var resulSuper = true;
		var existeSuper = true;
		var resultadoValidacion = true;

		var idMsgFecha = $("#msgeFecTermino").attr('id');
		var idFechaTer = $("#fecTermino").attr('id');

		var idMotivo = $("#motivo").attr('id');
		var idMsgMotivo = $("#msgeMotivoActualiza").attr('id');
		var mensajeMotivoCaptura = 'Seleccione el motivo de la actualización';
		$("#motivo").removeAttr('disabled');
		
		if($("#txtSuperficie").val() != undefined){
			
			existeSuper = true;
			var idSuperficie = $("#txtSuperficie").attr('id');
			var idMsgSuperficie = $("#msgeSuperficie").attr('id');
			//var mensajeSuperficie = 'Capture la superficie construida';
			
			var superficie = $("#txtSuperficie").val().replace(/,/g,'');
			
			if(superficie.length != 0 && parseInt(superficie) < 1){
				//resulSus = false;
				mostrarEtiqueta(idMsgSuperficie, 'Error en  Superficie');
				setErrorColor(idSuperficie);	
				 resulSuper = false;
				//resultadoValidacion = false;
			}else{
				resulSuper = true;
				//resultadoValidacion = true;
				limpiarEtiqueta(idMsgSuperficie);
				setDefaultColor(idSuperficie);
			}
		}
		
		
		var idMonto = $("#txtMontoEjercido").attr('id');
		var monto = $("#txtMonto").val().replace(/,/g,'');
		var montoE = $("#lblMontoEjercido").text();
		var montoEjercido = montoE.replace(/,/g,'').replace('$','');
		var idMsgMonto = $("#msgeMonto").attr('id');
		//valida superficie opcional 
		
		
		console.log('monto : ' + monto); 
		if(monto != '' && parseInt(monto) > 0){
			if(parseInt(monto) < parseInt(montoEjercido)){
				resulMon = false;
				console.log('El monto a capturar no puede ser menor que el último monto ejercido');
				mostrarEtiqueta(idMsgMonto, 'El monto a capturar no puede ser menor que el último monto ejercido');
				setErrorColor(idMonto);
				
			}else{
				resulMon = true;
				console.log('todo bien monto > 0 y monto > a montoejercido');
				limpiarEtiqueta(idMsgMonto);
				setDefaultColor(idMonto);
			}
//BRHG Se integran cambio para validar que el monto de obra no sea 0			
		}else{
			if(parseInt(monto) <= 0){
				resulMon = false;
				mostrarEtiqueta(idMsgMonto, 'El Monto de la Obra no puede ser 0');
				setErrorColor(idMonto);	
			}else{
				resulMon = true;
				console.log('todo bien monto no requerido');
				limpiarEtiqueta(idMsgMonto);
				setDefaultColor(idMonto);
			}
		}
		/*else{ 
			console.log('todo bien');
			limpiarEtiqueta(idMsgMonto);
			setDefaultColor(idMonto);
		}*/
		
		//valida monto opcional

		if (!validarValorProporcionado(idMotivo, idMsgMotivo, mensajeMotivoCaptura)) {
			resulMot = false;
		}else{
			resulMot = true;
		
		}
		
		var ultimaFecReanudacion = $("#ultimaFecReanudacion").text();
		var reporteBimestralPresentar = $("#reporteBimestralPresentar").text();
		var fechaTermino = $("#fecTerminoActualiza").datepicker( "getDate" );
		var fechaRegistrada = $("#lblFechaInicio").text();
		var arr = fechaRegistrada.split('/');
		var idMensaje = $("#msgeFecTermino").attr('id');
		var idCampo = $("#fecTerminoActualiza").attr('id');

		console.log(arr[0]);
		console.log(arr[1]);
		console.log(arr[2]);

		var r = getDate();
		r.setDate(arr[0]);
		r.setMonth(arr[1]-1);
		r.setYear(arr[2]);
		var fechaInicio = r;
		
		var mensajeFecInicio = "La fecha seleccionada debe ser igual o mayor a la fecha de inicio";
		var resulFecRepBim = true;
		if(fechaTermino){
			if(fechaTermino.toDateString() === fechaInicio.toDateString() || fechaTermino > fechaInicio){
				setDefaultColor(idCampo);
				limpiarEtiqueta(idMensaje);
				resulFec = true;
				if(ultimaFecReanudacion!=0){
					ultimaFecReanudacion=new Date(ultimaFecReanudacion);
					if(fechaTermino!=null && fechaTermino < ultimaFecReanudacion){
						setErrorColor(idCampo);
						mensajeErr = "Fecha de término no puede ser menor a la última fecha de reanudación";
						mostrarEtiqueta(idMensaje, mensajeErr);
						resulFec = false;
					}else{
						setDefaultColor(idCampo);
						limpiarEtiqueta(idMensaje);
						resulFec = true;
					}
				}
			}else{
//				setErrorColor(idCampo);			
//				mostrarEtiqueta(idMensaje, mensajeFecInicio);
				resulFec = false;
			}
			if(reporteBimestralPresentar!=0){
				//reporteBimestralPresentar = converToDate($("#reporteBimestralPresentar").text());
				var a = reporteBimestralPresentar.split("/");
				 var fechaReporteBimestral;
				 
				 if((a[2] % 4 == 0) && ((a[2] % 100 != 0) || (a[2] % 400 == 0))){
					 
					 if(a[1] == 2){
						 fechaReporteBimestral = new Date(a[2],a[1]-1,29);
					 }else{
						 fechaReporteBimestral = new Date(a[2],a[1]-1,a[0]);
					 }
				 }else{
					 fechaReporteBimestral = new Date(a[2],a[1]-1,a[0]);
				 }		 
				if(fechaTermino!=null && fechaTermino <= fechaReporteBimestral){
					setErrorColor(idCampo);
					mensajeErr = "Fecha de término no puede ser menor al último \"Bimestre y Año a presentar\"";
					mostrarEtiqueta(idMensaje, mensajeErr);
					resulFecRepBim = false;
				}else{
//					setDefaultColor(idCampo);
//					limpiarEtiqueta(idMensaje);
					resulFecRepBim = true;
				}
			}
		}
		
		if(resulFec && resulMot && resulMon && resulFecRepBim){
			if(existeSuper){
				if(resulSuper){
					resultadoValidacion = true;
					setDefaultColor(idCampo);
					limpiarEtiqueta(idMensaje);
				}else{
					resultadoValidacion = false;
				}
			}
			
		} else {
			resultadoValidacion = false;
		}
		
		return resultadoValidacion;

	}
	
	/**
	 * valida los campos requeridos para el registro de la actualización de la obra.
	 */
	function validarIncidenciaReporteBimestral() {
		var resultadoValidacion = true;
		
		var idMonto = $("#txtMontoEjercido").attr('id');
		var idMsgMonto = $("#msgMontoEjercido").attr('id');
		var mensajeMontoCaptura = 'Capture el monto ejercido a la fecha';

		if (validarValorMontos(idMonto, idMsgMonto,
				mensajeMontoCaptura)) {			
			var montoObra = $("#lblMontoObra").text().replace('$','').replace(/,/g,'');
			var montoEjerFec = $("#lblMontoEjercido").text().replace(/,/g,'').replace('$','');
			var montoCap = $("#txtMontoEjercido").val().replace(/,/g,'').replace('$','');
			
			if(parseInt(montoCap) >= parseInt(montoEjerFec)){
				if(parseInt(montoCap) <= parseInt(montoObra)){
					resultadoValidacion = true;
					limpiarEtiqueta(idMsgMonto);
					setDefaultColor(idMonto);
				}else{
					resultadoValidacion = false;
					mostrarEtiqueta(idMsgMonto, 'El monto ejercido a la fecha, no puede ser mayor al monto de la obra registrado, debe actualizar.');
					setErrorColor(idMonto);
					
				}
				
			} else {
				resultadoValidacion = false;
				mostrarEtiqueta(idMsgMonto, 'El monto ingresado no puede ser menor al último monto ejercido a la fecha');
				setErrorColor(idMonto);
			}			
		} else {
			resultadoValidacion = false;
		}
		return resultadoValidacion;
	}

	/**
	 * valida los campos requeridos para el remplazo de obra.
	 */

	function validarIncidenciaRemplazo(){

		var resultadoValidacion = true,
			resulObjetoObraSubEsp = true,
			resultadoNumAproxTrabajadores = true,
			resultadoSTPS = true;

		resulObjetoObraSubEsp = validarObjetoContratoSubEsp();
		resultadoNumAproxTrabajadores = validarNumAproxTrabajadores();
		resultadoSTPS = validarNumSTPS();

		if (!resulObjetoObraSubEsp || !resultadoNumAproxTrabajadores || !resultadoSTPS) {
			resultadoValidacion = false;
		}
		return resultadoValidacion;
	}

	/**
	 * valida los campos requeridos para el registro de la suspensión de la obra.
	 */
	function validarIncidenciaSuspension() {
		
		var resulFec = true;
		var resulMon = true;
		var resulMot = true;
		var resultadoValidacion = true;

		var idMsgFecha = $("#msgeFecSuspension").attr('id');
		var idFechaSus = $("#fecSuspension").attr('id');
		var mensajeFecha = 'Seleccione la fecha de suspensión';

		if (!validarValorMontos(idFechaSus,
				idMsgFecha, mensajeFecha)) {
			resulFec = false;
		} else {
			var fechaSeleccionada = $("#fecSuspension").datepicker('getDate');		
			
			if (!validarFechaMenorActual(fechaSeleccionada, idMsgFecha)) {
				resulFec = false;
				setErrorColor(idFechaSus);
			}else{
				var fechaInicio = $("#lblFechaInicio").text();
				var fechaTermino = $("#lblFechaTermino").text();
				
				if(validarFechaDentroPeriodo(fechaSeleccionada, fechaInicio, fechaTermino, idMsgFecha)){
					//if(validarRegistroExtemporaneo(fechaSeleccionada)){
						var ultimaFecReanudacion = $("#ultimaFecReanudacion").text();
						if(ultimaFecReanudacion!=0){
							ultimaFecReanudacion = new Date($("#ultimaFecReanudacion").text());
							if(fechaSeleccionada >= ultimaFecReanudacion){
								setDefaultColor(idFechaSus);
								limpiarEtiqueta(idMsgFecha);
								resulFec = true;
							}else{
								setErrorColor(idFechaSus);
								var mensajeErr = "La fecha de suspensión debe ser posterior a la última fecha de reanudación.";
								mostrarEtiqueta(idMsgFecha, mensajeErr);
								resulFec = false;
							}
						}
					//}else{
						//resulFec = false;
					//}
				}else{
					resulFec = false;
					setErrorColor(idFechaSus);
				}
				
				
			}
		}
		
		var idMotivo = $("#motivo").attr('id');
		var idMsgMotivo = $("#msgeMotivoSuspension").attr('id');
		var mensajeMotivoCaptura = 'Seleccione el motivo de la suspensión';

		if (!validarValorProporcionado(idMotivo, idMsgMotivo,
				mensajeMotivoCaptura)) {
			resulMot = false;
		}
		
		var idMonto = $("#txtMonto").attr('id');
		var idMsgMonto = $("#msgeMonto").attr('id');
		var mensajeMontoCaptura = 'Capture el monto ejercido a la fecha';
		var montoObra = $("#lblMontoObra").text().replace(/,/g,'').replace('$','');
		var montoEjerFec = $("#lblMontoEjercido").text().replace(/,/g,'').replace('$','');
		var montoCap = $("#txtMonto").val().replace(/,/g,'').replace('$','');

		if (validarValorMontos(idMonto, idMsgMonto, mensajeMontoCaptura)) {
			
			if(parseInt(montoCap) >= parseInt(montoEjerFec)){
				if(parseInt(montoCap) <= parseInt(montoObra)){
					resulMon = true;
					limpiarEtiqueta(idMsgMonto);
					setDefaultColor(idMonto);
				}else{
					resulMon = false;
					mostrarEtiqueta(idMsgMonto, 'El monto ejercido a la fecha, no puede ser mayor al monto de la obra registrado, debe actualizar.');
					setErrorColor(idMonto);
					
				}
				
			} else {
				resulMon = false;
				mostrarEtiqueta(idMsgMonto, 'El monto ingresado no puede ser menor al último monto ejercido a la fecha');
				setErrorColor(idMonto);
			}			
		}else{
			resulMon = false;
		}
		
		if(resulFec && resulMot && resulMon){
			resultadoValidacion = true;
		} else {
			resultadoValidacion = false;
		}
		
		return resultadoValidacion;
	}
	
	
	/**
	 * valida los campos requeridos para el registro de la reanudación de la obra.
	 * 
	 */
	function validarIncidenciaReanudacion() {
		var resulMontivo = true;
		var resultadoValidacion = true;
		
		var idMsgFecha = $("#msgeFecReanudacion").attr('id');
		var idFechaReanuda = $("#fecReanudacion").attr('id');
		var mensajeFecha = 'Seleccione la fecha de reanudación';

		//valida que la fecha de reanudacion sea seeccionada - requerida
		if (!validarValorProporcionado(idFechaReanuda, idMsgFecha, mensajeFecha)) {
			console.log('fecha de reanudacion NO seleccionada');
			resultadoValidacion = false;
		} else {
			console.log('fecha de reanudacion seleccionada');
			var fechaSeleccionada = $("#fecReanudacion").datepicker('getDate');
			var fecSuspension =  new Date($("#fecSuspension").val());
			//validamos que la fecha sea mayor a la del dia de registro 
			if (!validarFechaMenorActual(fechaSeleccionada, idMsgFecha)) {
				console.log('la fecha es mayor o igual a la fecha de hoy ');
				 // resulFecReanuda = false;
				setErrorColor(idFechaReanuda);
				resultadoValidacion = false;
			}else{
				
				var fechaSeleccionada = $("#fecReanudacion").datepicker('getDate');
                fechaSeleccionada.setHours(23, 59, 59, 999);
				var fechaTermino = $("#lblFechaTermino").text();
				var fechaSuspension = $("#lblFecSuspencionR").text();
				
				console.log('la fecha es menor o igual a la fecha de hoy ');
				//se valida que la fecha de reanudacion sea mayor a la fecha de suspension
				if(fechaSeleccionada < fecSuspension){
					console.log('la fecha es menor a la de suspension ');
					mostrarEtiqueta(idMsgFecha, 'La fecha seleccionada debe ser mayor o igual a la fecha de suspensión registrada');
				    setErrorColor(idFechaReanuda);
				    //resulFecReanuda = false;
				    resultadoValidacion = false;
				} else if(!validarFechaDentroPeriodo(fechaSeleccionada, fechaSuspension, fechaTermino, idMsgFecha)) {
					console.log('la fecha no se encuentra dentro del periodo de ejecucion');
					setErrorColor(idFechaReanuda);
					$("#fecTerminoReanuda").attr('disabled');
					$("#fecTerminoReanuda").datepicker('setDate', null);
					resultadoValidacion = false;
				} else {
					console.log('la fecha es mayor a la de suspension y esta dentro del periodo de ejecucion ');
				    //resulFecReanuda = true;
				    limpiarEtiqueta(idMsgFecha);
				    setDefaultColor(idFechaReanuda);
				}
			}
		}
		
		
		//si la fecha de termino tiene valor validar el motivo 
		var fecTermino = $("#fecTerminoReanuda").val();
		if(fecTermino != null && fecTermino.length != 0) {
			var idMensaje = "msgeFecTermino";
		    var idCampo = "fecTerminoReanuda";
			fecTermino = $("#fecTerminoReanuda").datepicker('getDate');
			var ultimaFecReanudacion = $("#fecReanudacion").datepicker('getDate'); 
			
			if(ultimaFecReanudacion != 0) {
			  	var mensaje = "Fecha de término no puede ser menor a la última fecha de reanudación.";
				    
			    if(fecTermino < ultimaFecReanudacion){
			    	console.log('Fecha de término no puede ser menor a la Fecha de reanudación');
			    	//resulFecTermino=false;
				    mostrarEtiqueta(idMensaje, mensaje);
				    setErrorColor(idCampo);
				    resultadoValidacion = false;
			    }
			}	
			  
			var reporteBimestralPresentar = $("#reporteBimestralPresentar").text();
			var idCampo = $("#fecTerminoReanuda").attr('id');
				
			if(reporteBimestralPresentar!=0){
				 var a = reporteBimestralPresentar.split("/");
			     
				 //var fechaReporteBimestral = new Date(a[2],a[1]-1,a[0]);
				 var fechaReporteBimestral;
				 
				 if((a[2] % 4 == 0) && ((a[2] % 100 != 0) || (a[2] % 400 == 0))){
					 if(a[1] == 2){
						 fechaReporteBimestral = new Date(a[2],a[1]-1,29);
					 }else{
						 fechaReporteBimestral = new Date(a[2],a[1]-1,a[0]);
					 }
				 }else{
					 fechaReporteBimestral = new Date(a[2],a[1]-1,a[0]);
				 }		 
				if(fecTermino <= fechaReporteBimestral){
					resultadoValidacion = false;
					console.log('fecha termino menor a la del reporte ');
					setErrorColor(idCampo);
					mensajeErr = "Fecha de término no puede ser menor al último reporte bimestral presentado";
					mostrarEtiqueta(idMensaje, mensajeErr);
				}
			}else{
				 //resultadoValidacion = true;
				console.log('sin fecha de reporte bimestral');
			}	
			  
			if(resultadoValidacion) {
				console.log('Fecha de término es mayor a la Fecha de reanudación y mayor a la del reporte bimestral');
				limpiarEtiqueta(idMensaje);
				setDefaultColor(idCampo);
			}
			  
			resulMontivo = validaMotivo();
		}
		
		//si el monto de obra tiene valor valida el motivo 
		
		var montoObraCapturado = $("#txtMonto").val().replace(/,/g, '').replace('$','');
		var montoEjerFec = $("#lblMontoEjercido").text().replace(/,/g, '').replace('$','');
		var idMsgMonto = $("#msgeMonto").attr('id');
		var idMonto = $("#txtMonto").attr('id');
		
		if(montoObraCapturado){
				//valida el monto de la obra para que sea menor al monto ejercido 
			  if(parseInt(montoObraCapturado) >= parseInt(montoEjerFec)){
				   console.log('el monto capturado es mayor al ejercido ... BIEN');
				   //resulMon = true;
				   limpiarEtiqueta(idMsgMonto);
				   setDefaultColor(idMonto);
				  
			  }else{
				  console.log('el monto capturado es menor o igual al ejercido');
				  //resulMon = false;
				  mostrarEtiqueta(idMsgMonto, 'El monto ingresado no puede ser menor al último monto ejercido a la fecha');
				  setErrorColor(idMonto);
				  resultadoValidacion = false;
			  }
			  resulMontivo = validaMotivo();
		}else{
			//20161109, JNLO, Se quita validacion inecesaria de acuerdo a lo revisado con los campos de la pantalla
//			if(fecTermino == null || fecTermino.length == 0){
//				$("#motivo").val('0');
//				$("#motivo").prop('disabled', true);
//				resulMontivo= true;
//			}
			limpiarEtiqueta(idMsgMonto);
			setDefaultColor(idMonto);
		}
		
		
		//si la superficie tiene valor valida el motivo 
		var varsuperficie = $("#txtSuperficie").val();
		if(varsuperficie != undefined){
			
			var idSuperficie = $("#txtSuperficie").attr('id');
			var idMsgSuperficie = $("#msgeSuperficie").attr('id');
			
			if(varsuperficie != ""){
				
				var superficie = $("#txtSuperficie").val().replace(/,/g,'');
				
				if(superficie){
					
					  if(superficie == '' || parseInt(superficie).lenght === 0) {
						  mostrarEtiqueta(idMsgSuperficie, 'Capture la superficie construida');
					      setErrorColor(idSuperficie);
					      resultadoValidacion = false;
					  } else if(parseInt(superficie) < 1) {
						  mostrarEtiqueta(idMsgSuperficie, 'Error en Superficie');
					      setErrorColor(idSuperficie);
					      resultadoValidacion = false;
					  } else {
						  limpiarEtiqueta(idMsgSuperficie);
					      setDefaultColor(idSuperficie);
					      resultadoValidacion = resultadoValidacion && true;
					  }
					  
					  resulMontivo = validaMotivo();
				}
			} else {
				limpiarEtiqueta(idMsgSuperficie);
				setDefaultColor(idSuperficie);
			}
		}
		
		if(resultadoValidacion && resulMontivo){
			return true;
		}else{
			return false;
		}
	}
	
	function validaMotivo() {
		$("#motivo").removeAttr('disabled');
		var idMotivo = $("#motivo").attr('id');
		var idMsgMotivo = $("#msgeMotivoReanudacion")
				.attr('id');
		var mensajeMotivoCaptura = 'Seleccione el motivo de la actualización';
		
		return validarValorProporcionado(idMotivo, idMsgMotivo, mensajeMotivoCaptura);
		
	}
	
	/**
	 * validarIncidenciaTerminacion
	 */	
	function validarIncidenciaTerminacion() {

		var resulFec = true;
		var resulMon = true;
		var resulSus = true;
		var resulSuper = true;
		var existeSuper = true;
		var resultadoValidacion = true;
		var idMsgFecha = $("#msgeFecTerminacion").attr('id');
		//var idFechaTerminacion = $("#fecTerminacion").attr('id');
		var mensajeFecha = 'Seleccione la fecha de terminación';		
		var mensajeFechaIni = 'Fecha de terminación no puede ser menor a la fecha inicio';
		var fechaSeleccionada = $("#fecTerminacion").datepicker('getDate');
		
		//var fechaSeleccionada = $(this).datepicker('getDate');
		//var idMsgFecha = $("#msgeFecTerminacion").attr('id');
		var idFecTerminacion = $("#fecTerminacion").attr('id');
		if (!validarValorMontos(idFecTerminacion,idMsgFecha, mensajeFecha)) {
			resulFec = false;
		} else{
			if (validarFechaMenorActual(fechaSeleccionada, idMsgFecha)) {
				console.log('fecha menor a la actual ... bien ');
				var fechaInicio = $("#lblFechaInicio").text();
				var fechaTermino = $("#lblFechaTermino").text();
				
				if(validarFechaDentroPeriodo(fechaSeleccionada, fechaInicio, fechaTermino, idMsgFecha)){
					console.log('fecha dentro del periodo');
					setDefaultColor(idFecTerminacion);
				}else{
					resulFec = false;
					console.log('fecha fuera del periodo');
				}
			}else{
				console.log('la fecha seleccionada es mayor a la actual');
				setErrorColor(idFecTerminacion);
				resulFec = false;
			}
		}

		
		

//		if (!validarValorMontos(idFechaTerminacion,idMsgFecha, mensajeFecha)) {
//			resulFec = false;
//		}else{
//			if (!validarFechaMenorActual(fechaSeleccionada, idMsgFecha)) {
//				resulFec = false;
//				setErrorColor(idFechaTerminacion);
//			}else{
//				resulFec = true;
//				limpiarEtiqueta(idMsgFecha);
//				setDefaultColor(idFechaTerminacion);
//				var fechaInicio = $("#lblFechaInicio").text();
//				
//				if(!validarFechaMenor(fechaSeleccionada, fechaInicio, idMsgFecha, mensajeFechaIni)){
//					resulFec = false;
//					setErrorColor(idFechaTerminacion);
//				}else{
//					resulFec = true;
//					limpiarEtiqueta(idMsgFecha);
//					setDefaultColor(idFechaTerminacion);
//				}
//				
//			}
//		}

		var idMonto = $("#txtMontoEjercido").attr('id');
		var idMsgMonto = $("#msgMontoEjercido").attr('id');
		var mensajeMontoCaptura = 'Capture el monto ejercido a la fecha';

		if (validarValorMontos(idMonto, idMsgMonto, mensajeMontoCaptura)) {
			
			var montoObra = $("#lblMontoObra").text().replace(/,/g,'').replace('$','');
			var montoEjerFec = $("#lblMontoEjercido").text().replace(/,/g,'').replace('$','');
			var montoCap = $("#txtMontoEjercido").val().replace(/,/g,'').replace('$','').replace('$','');
			
			
			if(parseInt(montoCap) >= parseInt(montoEjerFec)){
				if(parseInt(montoCap) <= parseInt(montoObra)){
					resulMon = true;
					limpiarEtiqueta(idMsgMonto);
					setDefaultColor(idMonto);
				}else{
					resulMon = false;
					mostrarEtiqueta(idMsgMonto, 'El monto ejercido a la fecha, no puede ser mayor al monto de la obra registrado, debe actualizar.');
					setErrorColor(idMonto);
					
				}
				
			} else {
				resulMon = false;
				mostrarEtiqueta(idMsgMonto, 'El monto ingresado no puede ser menor al último monto ejercido a la fecha');
				setErrorColor(idMonto);
			}			
		} else {
			resulMon = false;
		}
		
		
		
		if($("#txtSuperficie").val() != undefined){
			existeSuper = true;
			var idSuperficie = $("#txtSuperficie").attr('id');
			var idMsgSuperficie = $("#msgeSuperficie").attr('id');
			var superficie = $("#txtSuperficie").val().replace(/,/g,'');
			var superficieConstruida = $("#lblSuperficieConstruccion").text().replace(/,/g,'');
			
			if($("#txtSuperficie").val() != ""){
				if(parseInt(superficie) < 1) {
					mostrarEtiqueta(idMsgSuperficie, 'Error en Superficie');
					setErrorColor(idSuperficie);	
					 resulSuper = false;
				} else {
					if(parseInt(superficie) < parseInt(superficieConstruida)){
						mostrarEtiqueta(idMsgSuperficie, 'La superficie construida, no puede ser menor a la superficie de construcción, debe actualizar');
						setErrorColor(idSuperficie);	
						 resulSuper = false;
					}else{
						resulSuper = true;
						limpiarEtiqueta(idMsgSuperficie);
						setDefaultColor(idSuperficie);
					}
					
				}
			}else{
				mostrarEtiqueta(idMsgSuperficie, 'Capture la superficie construida');
				setErrorColor(idSuperficie);	
				 resulSuper = false;
				resulSuper = false;
			}
		}
		if(resulFec && resulMon && resulSus && resulSuper){
			if(existeSuper){
				if(resulSuper){
					resultadoValidacion = true;
				}else{
					resultadoValidacion = false;
				}
			}
		} else {
			resultadoValidacion = false;
		}

		return resultadoValidacion;

	}
	
	/**
	 * validarFechaMayorActual
	 */
	function validarFechaMayorActual(fechaSeleccionada, id) {

		var fechaHoy = getDate();

		limpiarEtiqueta(id);

		if (fechaSeleccionada < fechaHoy) {
			var mensaje = "La fecha seleccionada tiene que ser mayor al día de registro.";
			mostrarEtiqueta(id, mensaje);
			
			return false;
		} else {
			limpiarEtiqueta(id);
			return true;
		}

	}
	
	/**
	 * validarFechaMayorActual
	 */
	function validarFechaMenorActual(fechaSeleccionada, id) {

		var fechaHoy = getDate();

		limpiarEtiqueta(id);

		if (fechaSeleccionada >= fechaHoy) {
			var mensaje = "La fecha seleccionada no puede ser mayor al día de registro.";
			mostrarEtiqueta(id, mensaje);
			
			return false;
		} else {
			limpiarEtiqueta(id);
			return true;
		}

	}
	/**
	 * Cuando el registro es posterior a 5 dias naturales 
	 * con respecto al dia del registro se considera extemporanea.
	 * 1 Ordinario
	 * 2 Extraordinario
	 */
	function validarRegistroExtemporaneo(fechaSeleccionada){
		var fechaLimite = getDate();
		var dias_diff = calculaDiferenciaDias(fechaSeleccionada, fechaLimite);

		if (dias_diff <=  5) {
			return 1;
		} else {
			return 2;
		}
	}
	/**
	 * Calcula la diferencia de dias entre dos fechas
	 */
	function calculaDiferenciaDias(fechaSeleccionada, fechaLimite){
		
		var UN_DIA = 1000 * 60 * 60 * 24;
		var diferencia_ms = Math.abs(fechaSeleccionada - fechaLimite);
		var diferencia_dia  = Math.round(diferencia_ms/UN_DIA);
		return  diferencia_dia;
	}
	
	
	
	/**
	 * validarFechaMenor
	 */
	function validarFechaMenorIgual(fechaCapturada, fechaLimite, idMensaje, mensaje){
		var result = true;
		
		if(fechaCapturada <= fechaLimite){
			mostrarEtiqueta(idMensaje, mensaje);
			result = false;
		}else{
			limpiarEtiqueta(idMensaje, mensaje);
		}
		
		return result;	
	}
	
	/**
	 * validarFechaMenor
	 */
	function validarFechaMenor(fechaCapturada, fechaLimite, idMensaje, mensaje){
		var result = true;
		
		if(fechaCapturada < fechaLimite){
			mostrarEtiqueta(idMensaje, mensaje);
			result = false;
		}else{
			limpiarEtiqueta(idMensaje, mensaje);
		}
		
		return result;	
	}
	
	/**
	 * validarFechaDentroPeriodo
	 */
	function validarFechaDentroPeriodo(fechaSel, fechaInicio, fechaTermino, idMensaje) {
		var result = true;
		var mensaje = 'La fecha seleccionada debe estar dentro del periodo de ejecución de la obra.';

		var fechaSeleccionada = new Date(
				fechaSel.getFullYear(), fechaSel.getMonth(),
				fechaSel.getDate());

		var fechaIni = fechaInicio.split("/");

		var diaIni = fechaIni[0];
		var mesIni = fechaIni[1];
		var anioIni = fechaIni[2];

		var fechaInicioObra = new Date(anioIni, mesIni - 1, diaIni);

		var fechaTer = fechaTermino.split("/");
		var diaTer = fechaTer[0];
		var mesTer = fechaTer[1];
		var anioTer = fechaTer[2];

		var fechaTerminoObra = new Date(anioTer, mesTer - 1, diaTer);

		if ((fechaSeleccionada >= fechaInicioObra && fechaSeleccionada <= fechaTerminoObra)) {
			limpiarEtiqueta(idMensaje);
		} else {
			mostrarEtiqueta(idMensaje, mensaje);
			result = false;
		}
		return result;
	}
	
	
	
	/**
	 * validarFechaTermino
	 */
	function validarValorProporcionado(idCampo, idMensaje, mensaje) {

		if ($("#" + idCampo).val() == '' || $("#" + idCampo).val() == "0") {
			mostrarEtiqueta(idMensaje, mensaje);
			setErrorColor(idCampo);
			return false;
		} else {
			limpiarEtiqueta(idMensaje);
			setDefaultColor(idCampo);
			return true;
		}

	}
	
	function validarValorMontos(idCampo, idMensaje, mensaje) {

		if ($("#" + idCampo).val() == '') {
			mostrarEtiqueta(idMensaje, mensaje);
			setErrorColor(idCampo);
			return false;
		} else {
			limpiarEtiqueta(idMensaje);
			setDefaultColor(idCampo);
			return true;
		}

	}

	/**
	 * 
	 */
	function setErrorColor(idCampo){
		$("#" + idCampo).css('border-color', '#a94442');
	}
	
	/**
	 * 
	 */
	function setDefaultColor(idCampo){
		$("#" + idCampo).css('border-color', '#ccc');
	}
	
	/**
	 * 
	 */
	function mostrarEtiqueta(id, mensaje) {

		idCampo = "#" + id;
		$(idCampo).text(mensaje);
		$(idCampo).removeClass("hidden");
	}
	/**
	 * 
	 */
	function limpiarEtiqueta(id) {
		idCampo = "#" + id;
		$(idCampo).empty();
	}
	
	/**
	 * 
	 */
	function finalizarRegIncidencia(){
		$('#btnRegistroIncidencia').remove(); 
		$('#btnRegresarResumen').remove();  
		var r= $('<form> <button class="btn btn-primary" formaction="/sdroc_web/escritorio">Finalizar</button> </form>'); 		
		$('#pnlFinish').append(r);
	}
	
	/**
	 * Validacion ReporteBimestral
	 */
	function converToDate(fechaStringDMA){
		var a = fechaStringDMA.split("/");
		return new Date(a[2],a[1]-1,a[0]);
	}
	
	/**
	 * Validacion validar Periodo del Contrato subcontratista especializado
	 */

	function validarPeriodo() {
		var resultadol = true;

		if ($("#fecTerminoActualiza").val() == '') {

			$("#msgeFecFin").removeClass("hidden");
			$("#fecTermino").css('border-color', '#a94442');
			$("#msgeFecFin").html('Seleccione la fecha de t&eacute;rmino');
			resultadol = false;
		}
		return resultadol;
	}

	/**
	 * Validacion validar Objeto del Contrato subcontratista especializado
	 */
	function validarObjetoContratoSubEsp() {
		var resultado1 = true,
			txtObjetoContratoSubEsp = $("#txtObjetoContratoSubEsp").val();

		if (txtObjetoContratoSubEsp == "") {
			$("#txtObjetoContratoSubEsp").marcarBordeError(true, "#msgeObjetoContratoSubEsp");
			resultado1 = false;
		} else {
			$("#txtObjetoContratoSubEsp").marcarBordeError(false, "#msgeObjetoContratoSubEsp")
		}
		return resultado1;
	}

	/**
	 * Validacion validar monto para subcontratista especializado
	 */

	function validarMonto() {
		var resultadol = true,
			mensajeError = null,
			valorMonto = $("#txtMonto").val();

		if (valorMonto == '') {
			mensajeError = "Capture el monto de la obra";
			$("#txtMonto").marcarBordeError(true, "#msgeMonto", mensajeError);
			resultadol = false;
		} else if (parseInt(valorMonto, 10) < 1) {
			mensajeError = "El monto de obra a capturar debe ser mayor a cero";
			$("#txtMonto").marcarBordeError(true, "#msgeMonto", mensajeError);
			resultadol = false;
		} else {
			$("#txtMonto").marcarBordeError(false, "#msgeMonto");
		}
		return resultadol;

	}

	/**
	 * Validacion validar no aproximado de trabajadores para subcontratista especializado
	 */

	function validarNumAproxTrabajadores() {
		var resultado1 = true, txtNumTrabajadores = $("#txtNumTrabajadores").val();
		if (txtNumTrabajadores == "") {
			$("#txtNumTrabajadores").marcarBordeError(true, "#msgeNumTrabajadores");
			resultado1 = false;
		} else if (parseInt(txtNumTrabajadores) < 1) {
			mensajeError = "El numero de trabajadores debe de ser mayor a cero.";
			$("#txtNumTrabajadores").marcarBordeError(true, "#msgeNumTrabajadores", mensajeError);
			resultado1 = false;
		} else {
			$("#txtNumTrabajadores").marcarBordeError(false, "#msgeNumTrabajadores");
		}
		return resultado1;
	}

	/**
	 * Validacion validar numero REPSE para subcontratista especializado
	 */


	function validarNumSTPS() {
		console.log("Inicia validar num STPS");
		var resultado1 = true, txtNumRegSTPS = $("#txtNumRegSTPS").val();
		console.log(txtNumRegSTPS);
		if (txtNumRegSTPS == "") {
			console.log('cadena vacia');
			$("#txtNumRegSTPS").marcarBordeError(true, "#msgeNumRegSTPS");
			resultado1 = false;
		} else {
			console.log('todo ok');
			$("#txtNumRegSTPS").marcarBordeError(false, "#msgeNumRegSTPS")
		}
		return resultado1;
	}

	if($('#msgSinBimestres').length>0){
		var fecBi = $('#fecBimestral').text();
	    var fechaInicio = converToDate($('#lblFechaInicio').text());
	    var fechaTermino = converToDate($('#lblFechaTermino').text());
	    var bimIni = $('#bimIni').text().split(',');
	    var bimTer = $('#bimTer').text().split(',');
	    var bimAct = $('#bimAct').text().split(',');
	    var today = getDate();
	    var dd = today.getDate();
	    var mm = today.getMonth()+1;
	    var yyyy = today.getFullYear();
	    if(dd<10) {
	        dd='0'+dd
	    } 
	    if(mm<10) {
	        mm='0'+mm
	    } 
	    var cadenaHoy=''+dd+'/'+mm+'/'+yyyy;
	    var hoy=converToDate(cadenaHoy);
	    banderaBimestre=false;
	    if(fechaInicio>hoy){
	    	banderaBimestre=true;
	    }
	    if(fechaTermino>hoy){
	    	if(bimIni[1]==bimAct[1]){
		    	if(bimIni[0]==bimAct[0]){
		    		banderaBimestre=true;
		    	}
	    	}
	    }
    	if(bimIni[1]==bimTer[1]){
    		if(bimIni[0]==bimTer[0]){
    			banderaBimestre=true;
    		}
    	}
	    
	    if(fecBi=='00-0000' || banderaBimestre){
	    	$('#pnlIncReporteB').addClass('hidden');
	    	$('#pnlFinish').addClass('hidden');
	    	$('#msgSinBimestres').removeClass('hidden');
	    	$('#btnRegresarResumen').text('Aceptar');
	    }
	}
	
});
