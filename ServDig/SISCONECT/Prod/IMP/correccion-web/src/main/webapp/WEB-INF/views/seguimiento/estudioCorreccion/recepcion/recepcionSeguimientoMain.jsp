<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/recepcion/recepcionSeguimiento.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js" type="text/javascript"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>

<link href="/correccion-web/resources/estilos/estilo.css" type="text/css" rel="stylesheet">
<link href="/correccion-web/resources/css/correcion.css" type="text/css" rel="stylesheet">
<link href="/correccion-web/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css" type="text/css" rel="stylesheet" >
<script>
	function mostrarVentana() {
		var ventana = document.getElementById('venanaModal');
		var x = screen.height / 2 - 300;
		var y = (screen.width / 2) - 425;
		ventana.style.top = x + "px";
		ventana.style.left = y + "px";
		ventana.style.display = 'block';
	}

	function ocultarVentana() {
		var ventana = document.getElementById('venanaModal');
		ventana.style.display = 'none';
	}

	function mostrarVentanaDetalle() {
		var ventana = document.getElementById('venanaModalDetalle');

		var x = screen.height / 2 - 250;
		var y = (screen.width / 2) - 475;

		ventana.style.top = x + "px";
		ventana.style.left = y + "px";
		ventana.style.display = 'block';
	}

	function ocultarVentanaDetalle() {
		var ventana = document.getElementById('venanaModalDetalle');
		ventana.style.display = 'none';
	}

	function bloquear() {
		$.blockUI({
			message : '<h1>Procesando...</h1>',
			css : {
				border : 'none',
				padding : '15px',
				opacity : .5
			}
		});
	}

	function desbloquear() {
		$.unblockUI();
	}

	function getAjaxObject() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		return xmlhttp;
	}

	function insertaTable(str) {
		var theDiv = document.getElementById("txtContent");
		theDiv.innerHTML = str;
	}

	function insertaTableDetalle(str) {
		var theDiv = document.getElementById("txtContentDetalle");
		theDiv.innerHTML = str;
	}

	function callBackEnd(url, option) {
		var ajax = getAjaxObject();
		ajax.open("GET", url, true);
		ajax.onreadystatechange = function() {
			if (ajax.readyState == 4) {
				if (option == 1) {
					insertaTable(ajax.responseText);
					desbloquear();
					mostrarVentana();
				}
				if (option == 2) {
					insertaTableDetalle(ajax.responseText);
					desbloquear();
					mostrarVentanaDetalle();
				}
			}
		};
		ajax.send();
	}

	function verPagos() {
		bloquear();
		var rp=registroPatronalSeguimientoCorreccion.substring(0,10);		
		var date = new Date();
		var timestamp = date.getTime();
		var queryStr = "?rp=" + rp + "&op=1&time=" + timestamp;
		callBackEnd('/correccion-web/AjaxService' + queryStr, 1);
	}

	function verDetalleMov(fecha, rp) {
		bloquear();
		var date = new Date();
		var timestamp = date.getTime();
		var queryStr = "?rp=" + rp + "&op=2&fecha=" + fecha + "&time="
				+ timestamp;
		callBackEnd('/correccion-web/AjaxService' + queryStr, 2);
	}
</script>
<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="2" width="100%">
			<form:form id="formRecepcionSeguimiento" name="formRecepcionSeguimiento" method="get" modelAttribute="recepcionSeguimientoVO" action="/seguimiento/estudioCorreccion/seguimiento.do">
				<input type="hidden" id="idPresentaCoor">
				<form:hidden path="idPresentaCorreccion" id="idPresentaCorreccionHtml"/>
				<form:hidden path="cveTipoCorreccion" id="cveTipoCorreccionHdn"/>
				<form:hidden path="cveRevRecepcion" id="cveRevRecepcionHdn"/>
				<form:hidden path="preGuardado" id="preGuardadoHdn"/>
				<form:hidden path="numeroFolio" id="nuFolioHdn"/>
				
				
				
					<fieldset>
					
					<table style="width: 900px" align="center" class="tablaverde2">
						<thead>
							<tr>
								<td align="center" colspan="2">Recepci&oacute;n</td>
							</tr>
						</thead>
						<tbody>
							<tr class="impar">
								<td align="left" colspan="2">&nbsp;</td>
							</tr>
							<tr class="par" valign="top">
								<td>
									
									<form:checkbox path="documentacionSustenta" id="cbRecDocu" onclick="seleccionaDocSustenta()" />
									<div id="labeldocSustenta"></div>
								</td>
								<td>
									<label id="lbDocu" class="etiqueta2">
										<span class="required" id="spnDocSusteReq">*</span>Documentaci&oacute;n que sustenta la correcci&oacute;n.
									</label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td>
									
									<form:checkbox path="comprobPagoConvenio" id="cbRecPagoTra" onclick="seleccionaComprobantePago()"/>
								</td>
								<td>
									<label id="lbPagoTra" class="etiqueta2">
										Comprobante de pago, y en su caso el convenio del tr&aacute;mite del pago diferido o en parcialidades.  
									</label>
								</td>
							</tr>					
							<tr class="par" valign="top">
								<td>
									<form:checkbox path="comprobPresAvisosAfil" id="cbRecAvisoAfi" onclick="seleccionaAvisoAfilia()"/>
								</td>
								<td>
									<label id="lbAvisoAfi" class="etiqueta2">
										Comprobante de la presentaci&oacute;n de los avisos afiliatorios, derivados de la correci&oacute;n.
									</label> &nbsp;&nbsp;&nbsp; <input type="button" value="Ver Avisos Afiliatorios" onclick="verPagos()">
								</td>
							</tr>
							<tr class="par">
								<td align="left" colspan="2">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="7">
								<!-- Empieza seccion Regulariza Obra -->
								<jsp:include page="/WEB-INF/views/seguimiento/estudioCorreccion/recepcion/consolidacionImportesRec.jsp" />
								<!-- termina seccion Regulariza Obra -->	
							</td>
							</tr>
							<tr class="par" valign="top">
								<td>
									<label id="lbObservacion" for="taRecObservacion" class="etiqueta2">
										Observaciones
									</label>
								</td>
								<td>
									<form:textarea path="observaciones" rows="6" cols="100" id="taRecObservacion"/>						
								</td>
							</tr>
							<tr class="par">
								<td align="left" colspan="2">&nbsp;</td>
							</tr>
							<tr class="impar">
								<td align="left" colspan="2">&nbsp;</td>
							</tr>
						</tbody>
					</table>
					
												
						
					<div id="btnGuardarSeguimiento" align="center" style="">
					
					<!--   <input type="button" value="Generar PDF" id="btnGeneraPDFRecepcion" width="9px" height="9px" class="boton" onclick="" >&nbsp;&nbsp; -->
						<input type="button" value="Guardar" id="btnGuardarRecepcion" width="9px" height="9px" class="boton" onclick="procesarRecepcionSeguimiento();" >
						
					</div>	
								
				</fieldset>	
			</form:form>
		</td>
	</tr>
</table>
<div id="confirmarSATICB" style="display: none;">
		<jsp:include page="/WEB-INF/views/seguimiento/estudioCorreccion/recepcion/confirmarRecepcionSeguimiento.jsp" />
		
		</div>
		
	<!-- Venatna modal para mostrar movimientos -->
	<div id=venanaModal style="position: fixed; width: 800px; height: 450px; top: 0; left: 0; font-family:Verdana, Arial, Helvetica, sans-serif; font-size: 12px; font-weight: normal; border: #cccccc 3px solid; background-color: #FAFAFA; color: #000000; display:none;">		
		<div id="myDiv" style="font-weight: bold; text-align: left; border: #555454 1px solid; color: #000000; padding: 15px; background-color:#bbFFbb">
			<center>Movimientos Operados y/o Rechazados</center>
		</div>		 
		 <div style="height: 330px; font-weight: bold; text-align: left; overflow:auto; color: #000000; padding: 15px; background-color:#cccccc" >
 			<p id="txtContent" align="center">
 			
			</p>
		</div>		
		<div id="myDiv" style="font-weight: bold; text-align: left; color: #000000; padding: 9px; background-color:#DDDDFF"><center>
			<input type=button value=Cerrar onclick="ocultarVentana();"></centrer>
		</div>
 	</div>

	<div id=venanaModalDetalle style="position: fixed; width: 900px; height: 400px; top: 0; left: 0; font-family:Verdana, Arial, Helvetica, sans-serif; font-size: 12px; font-weight: normal; border: #cccccc 3px solid; background-color: #FAFAFA; color: #000000; display:none;">		
		<div id="myDiv" style="font-weight: bold; text-align: left; border: #ea7825 1px solid; color: #000000; padding: 15px; background-color:#bbFFbb">
			<center>Detalle de Movimientos Operados y/o Rechazados</center>
		</div>		 
		 <div style="height: 280px; font-weight: bold; text-align: left; overflow:auto; color: #000000; padding: 15px; background-color:#cccccc" >
 			<p id="txtContentDetalle" align="center">
 			
			</p>
		</div>		
		<div id="myDiv" style="font-weight: bold; text-align: left; color: #000000; padding: 9px; background-color:#DDDDFF"><center>
			<input type=button value=Cerrar onclick="ocultarVentanaDetalle();"></centrer>
		</div>
 	</div>		