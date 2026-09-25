<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@page import="mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Constantes"%>
<script>
String.prototype.trim = function() {return this.replace(/^\s+|\s+$/g, '')}
	function textarea(){
		var maximos = new Array();
		$("textarea").attr("maxlength", function(i){
			if (maximos[i] = this.getAttribute('maxlength')){
				$(this).keypress(function(event){
					return ((event.which == 8) || (event.which == 9) || (this.value.length < maximos[i]));
				})
			}
		});
	}
	/* 	
	function checkCaracterEspecial(e) {
		var tecla = (document.all)? e.keyCode : e.which;
		console.log("tecla presionada: " + tecla);
	}
	*/	 
 
	function checkCaracterEspecial(e) {	
		var tecla = (document.all)? e.keyCode : e.which;
		
		if(tecla == 8) {
			return true;
		}
		if(tecla == 13) {
			return true;
		}
		if(tecla == 32) {
			return true;
		}
		//valida guion y signo +
		if(tecla == 45 || tecla == 43) {
			return false;
		}

		var regex = /[A-Z-Aa-z0-9\d\u00F1\u00E1\u00E9\u00ED\u00F3\u00FA\u00D1\u00C1\u00C9\u00CD\u00D3\u00DA,.;:_/?!@#$%&+\{\}\(\)\[\]\"\"\'\']/
		var teclaFinal = String.fromCharCode(tecla);
		
		return regex.test(teclaFinal);
	}


	var delSub=false;

	function seleccionSubDel(bool){
		delSub=bool;
	}
	//Agregado y modificado para validar si es un finalizar o un Generar CLEM, para la parte de agregar un documento pdf
	function enviarFormularioClem(){
		<c:if test="${cveIdPatronDictamen == null}">
	    	document.getElementById('datosClemForm').action="<%=request.getContextPath()%>/rectificacion/${reporteClemBean.idAnalisis}/autorizarRectificacion/generaClem/rectificadoAutorizado";
	    </c:if>
	    <c:if test="${cveIdPatronDictamen != null}">
	    	document.getElementById('datosClemForm').action="<%=request.getContextPath()%>/rectificacion/${reporteClemBean.idAnalisis}/autorizarRectificacion/generaClem/rectificadoAutorizado/${cveIdPatronDictamen}";
	    </c:if>
	    
		document.getElementById("datosClemForm").submit();
		$.blockUI();
	}
	
	function generarClem(){
		if('${reporteClemBean.botonClem}' == 'Finalizar'){
			if(validarDocumento() && validaDatos()){
				enviarFormularioClem();
			}
		} else if('${reporteClemBean.botonClem}' == '<%=Constantes.CLEM_GENERAR%>'){
			if(validaDatos()){
				enviarFormularioClem();
			}
		} else if('${reporteClemBean.botonClem}' == '<%=Constantes.CLEM_MODIFICAR%>'){
			if(validaDatos()){
				document.getElementById('datosClemForm').action="<%=request.getContextPath()%>/clem/${reporteClemBean.idAnalisis}/modificarClem/guardaModificacion";
				document.getElementById("datosClemForm").submit();
				$.blockUI();
			}
		}
	}
	//Agregado y modificado para validar si es un finalizar o un Generar CLEM, para la parte de agregar un documento pdf
	
	function validaDatos(){
		
		if($('#checkFirma').attr('checked'))
			document.getElementById('firmaAusencia').value = 1;
		else
			document.getElementById('firmaAusencia').value = 0;
		
		var bool=true;
		titular=document.getElementById('titular').value;
		lugarFechaExpedicion=document.getElementById('lugarFechaExpedicion').value;
		motivos=document.getElementById('motivos').value;
		puesto=document.getElementById('puesto').value;
		
		if(!delSub){ // si la clem es delegacional el campo titular es requerido
			if(titular==null || titular.trim().length==0){
				bool=false;
				alert("Debe ingresar el nombre del Titular");
			}else if(soloLetras(titular)) {
				bool=false;
				alert("No debe de ingresar caracteres especiales en nombre del Titular");
			}			
		}else{ // si la clem es subdelegacional 

			suplente=document.getElementById('suplente').value;

			if($('#checkFirma').attr('checked')){ // si la clem es con firma por ausencia
				if(suplente==null || suplente.trim().length==0){
					bool=false;
					alert("Debe ingresar el nombre de la persona que firma por ausencia");
				}else if(soloLetras(suplente)) {
					bool=false;
					alert("No debe de ingresar caracteres especiales en nombre del Suplente");
				}
			}else{
				if(titular==null || titular.trim().length==0){
					bool=false;
					alert("Debe ingresar el nombre del Titular");
				}else if(soloLetras(titular)) {
					bool=false;
					alert("No debe de ingresar caracteres especiales en nombre del Titular");
				}			
				
				if(puesto==null || puesto.trim().length==0){
					bool=false;
					alert("Debe ingresar el puesto");
				}else if(soloLetras(puesto)) {
					bool=false;
					alert("No debe de ingresar caracteres especiales en Puesto");
				}			
				
				if(suplente!=null && suplente.trim().length>0){
					alert("El campo de suplente tiene datos y no ha seleccionado la opci&oacute;n de firma por ausencia, la clem se va generar a nombre del titular");
				}				
			}			
		}	
		
		if(lugarFechaExpedicion==null || lugarFechaExpedicion.trim().length==0){
			bool=false;
			alert("Debe ingresar lugar y fecha de expedici&oacute;n");
		}else if(soloLetras(lugarFechaExpedicion)) {
			bool=false;
			alert("No debe de ingresar caracteres especiales en el campo lugar y fecha de expedici&oacute;n");
		}else if(motivos==null || motivos.trim().length==0){
			bool=false;
			alert("Debe ingresar los motivos para la rectificaci&oacute;n");
		}else if(soloLetras(motivos)) {
			bool=false;
			alert("No debe de ingresar caracteres especiales en el campo de Motivos");
		}

		return bool;
	}
	
	function soloLetras(txt){
		var regex = /[A-Z-Aa-z0-9\d\u00F1\u00E1\u00E9\u00ED\u00F3\u00FA\u00D1\u00C1\u00C9\u00CD\u00D3\u00DA,.;:_/?!@#$%&+\{\}\(\)\[\]\"\"\'\'\s ]/		
		for(var z = 0; z<txt.length;z++) {
			if(!regex.test(txt.charAt(z))) {
				return true;
			}
		}
		return false;
	}
		
	function borrarError(control)
	{
		document.getElementById("divErrorMotivos").style.visibility = "hidden";
	}	
	
</script>
<%
String strTipoTramite = request.getParameter("tipoTramite").trim();
%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/clasificacion/datosClem/capturaDatosClem.js" htmlEscape="true"/>"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/autocomplete/jquery.ui.autocomplete.html.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="reporteClemBean" value="${reporteClemBean}" />

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">
		<div class="form-comment">
			<form:form id="datosClemForm" modelAttribute="reporteClemBean" method="post" enctype="multipart/form-data">
				<input type="hidden" id="cveIdDelegacion" name="cveIdDelegacion" value="${reporteClemBean.cveIdDelegacion}" />
				<input type="hidden" id="delegacion" name="delegacion" value="${reporteClemBean.delegacion}" />
				<input type="hidden" id="cveIdSubdelegacion" name="cveIdSubdelegacion" value="${reporteClemBean.cveIdSubdelegacion}" />
				<input type="hidden" id="subdelegacion" name="subdelegacion" value="${reporteClemBean.subdelegacion}" />
				<input type="hidden" id="idAnalisis" name="idAnalisis" value="${reporteClemBean.idAnalisis}" />
				<input type="hidden" id="mostrarComboArt155" name="mostrarComboArt155" value="${reporteClemBean.mostrarComboArt155}" />
				<input type="hidden" id="tipoTramite" name="tipoTramite" value="${reporteClemBean.tipoTramite}" />
				<input type="hidden" id="pspArt15A" name="pspArt15A" value="${reporteClemBean.psp}" />
				<input type="hidden" id="fechaTramite" name="fechaTramite" value="${reporteClemBean.fechaTramite}" />
				<input type="hidden" id="fechaSurteEfecto" name="fechaSurteEfecto" value="${reporteClemBean.fechaSurteEfecto}" />
				<input type="hidden" id="tipoPersona" name="tipoPersona" value="${reporteClemBean.tipoPersona}" />
				<input type="hidden" id="cveIdTipoCausa" name="cveIdTipoCausa" value="${reporteClemBean.cveIdTipoCausa}" />
				<input type="hidden" id="insMod" name="insMod" value="${reporteClemBean.insMod}" />
				<input type="hidden" id="botonClem" name="botonClem" value="${reporteClemBean.botonClem}" />
				<input type="hidden" id="firmaAusencia" name="firmaAusencia" value="${reporteClemBean.firmaAusencia}" />
				
				<fieldset>
					<c:if test="${cveIdPatronDictamen != null}">
						<div style="display:none">
					</c:if>
							<div class="separadorseccion" align="center">
								Resoluci&oacute;n de rectificaci&oacute;n de la clasificaci&oacute;n de las empresas en el Seguro de Riesgos de Trabajo
							</div>
							<fieldset class="fsInterno">
								<%-- <form : errors path="errorClem" cssClass="error"></form : errors/> --%>
								<input type="radio" class="submit_no_margin" id="cveTipoClem" name="cveTipoClem" value="1" checked="checked" onClick="seleccionSubDel(false);showSuplente(false);" /> 
								<label class="wide">CLEM Delegacional</label>
								<input type="radio" class="submit_no_margin" id="cveTipoClem" name="cveTipoClem" value="2" onClick="seleccionSubDel(true);showSuplente(true);" />
								<label class="wide">CLEM Subdelegacional</label>
							</fieldset>
		
							<fieldset class="fsInternosinlineas">
								<form:errors path="titular" cssClass="error"></form:errors>
								<form:errors path="suplente" cssClass="error"></form:errors>
								<form:errors path="puesto" cssClass="error"></form:errors>
								<label class="mwide">Titular de la Delegaci&oacute;n o Subdelegaci&oacute;n seg&uacute;n corresponda:</label>
								<c:if test="${cveIdPatronDictamen == null}">
									<input type="text" onkeypress="return checkCaracterEspecial(event)" id="titular" name="titular" maxlength="50" style="width: 500px; margin-left: 0px" value="${reporteClemBean.titular}" />
								</c:if>
								<c:if test="${cveIdPatronDictamen != null}">
									<input type="text" onkeypress="return checkCaracterEspecial(event)" id="titular" name="titular" maxlength="50" style="width: 500px; margin-left: 0px" value="DICTAMEN" />
								</c:if>
							</fieldset>
	
							<div id="divsuplente">
								<fieldset class=fsInternosinlineas id="fieldsuplente" style="visibility: hidden">
									<label class="mwide" style="width: 100%;">
									<input type="checkbox" id="checkFirma" name="checkFirma" style="width: 20px;" /> En caso de que el titular no se encuentre marque la casilla y capture el nombre del suplente</label>
								</fieldset>
								<fieldset class=fsInternosinlineas id="fieldsuplente2" style="visibility: hidden">
									<input type="text" onkeypress="return checkCaracterEspecial(event)" id="suplente" name="suplente" maxlength="50" style="width: 500px; margin-left: 180px;" value="${reporteClemBean.suplente}" />
								</fieldset>
							</div>
	
							<fieldset class=fsInternosinlineas>
								<label class="mwide">Puesto:</label>
							</fieldset>
							<fieldset class=fsInternosinlineas>
								<input type="text" onkeypress="return checkCaracterEspecial(event)" id="puesto" name="puesto" maxlength="100" style="width: 500px; margin-left: 0px" value="${reporteClemBean.puesto}" />
							</fieldset>
		
							<fieldset class="fsInterno"></fieldset>
			
							<fieldset class="fsInternosinlineas" style="width: 100%;">
								<label class="wide">Fracci&oacute;n Art&iacute;culo 26:</label>
							</fieldset>
							<fieldset class="fsInternosinlineas">
								<label class="wideA" style="width: 100%;">
									<input type="radio" class="submit_no_margin" id="fraccionArticulo26" name="fraccionArticulo26" value="I" ${reporteClemBean.fraccionArticulo26=="I" || reporteClemBean.fraccionArticulo26==null?"checked='checked'":""}>
										<b>I.</b> Si se trata de una empresa que realice varias actividades o que tenga diversos centros de trabajo en el
										territorio o jurisdicci&oacute;n de un mismo municipio o en el Distrito Federal, se le fijar&aacute; una solo
										clasificaci&oacute;n y no podr&aacute;n disociarse sus diversas actividades o grupos componentes para asignar
										clasificaci&oacute;n y prima diferentes a cada una 
								</label> 
								<label class="wide"></label>
							</fieldset>
	
							<fieldset class="fsInternosinlineas">
								<label class="wideA" style="width: 100%;">
									<input type="radio" class="submit_no_margin" id="fraccionArticulo26" name="fraccionArticulo26" value="II" ${reporteClemBean.fraccionArticulo26=="II"?"checked='checked'":""}>
										<b>II.</b> Cuando una empresa tenga varios centros de trabajo con actividades similares o diferentes en diversos municipios o en el
										Distrito Federal, sus actividades o grupos componentes ser&aacute;n considerados como una solo unidad de riesgo en cada municipio o
										en el Ditrito Federal y deber&aacute; asignarse una sola clasificaci&oacute;n 
								</label>
								<label class="wide"></label>
							</fieldset>
		
							<fieldset class="fsInterno"></fieldset>
	
							<fieldset class="fsInternosinlineas">
								<label class="wideA" style="width: 100%;">
									Si la actividad de una empresa no se se&ntilde;ala en forma espec&iacute;fica en el Cat&aacute;logo de Actividades establecido en el Reglamento, se
									proceder&aacute; a determinar la clasificaci&oacute;n considerando la analog&iacute;a o similitud en la actividad, los
									procesos de trabajo y los riesgos de dicha actividad con los que se establecen en el Cat&aacute;logo mencionado, en estos casos se
									deber&aacute; se&ntilde;alar claramente dicha situaci&oacute;n en el apartado de la motivaci&oacute;n de la resoluci&oacute;n. Indique
									si la presente determinaci&oacute;n se encuentra en este supuesto
								</label>
								<label class="wide"></label>
							</fieldset>
							
							<fieldset class="fsInternosinlineas"></fieldset>
		
							<fieldset class="fsInternosinlineas" style="margin-left: 375px;">
								<input type="radio" class="submit_no_margin" id="fraccionArticulo20" name="fraccionArticulo20" value="1" style="display: inline;" ${reporteClemBean.fraccionArticulo20=="1" || reporteClemBean.fraccionArticulo20==null?"checked='checked'":""}>
								<label class="mwideA3" style="width: 25%; display: inline;">Si</label>
								<input type="radio" class="submit_no_margin" id="fraccionArticulo20" name="fraccionArticulo20" value="0" style="display: inline;" ${reporteClemBean.fraccionArticulo20=="0"?"checked='checked'":""}>
								<label class="mwideA3" style="width: 25%; display: inline;">No</label>
							</fieldset>
							
							<fieldset class="fsInterno"></fieldset>
	
							<!-- Inicio. Validacion para insMod, si es Inscipcion(0) o es Modificacion (1) -->
							<c:if test="${reporteClemBean.insMod ne '0'}">
								<fieldset class="fsInternosinlineas">
									<label class="wide" style="width: 100%;">Fracci&oacute;n Art&iacute;culo 28:</label>
								</fieldset>
								<fieldset class="fsInternosinlineas">
									<!--<label class="wideA" style="width: 100%;">
										<input type="radio" class="submit_no_margin" id="cveArticulo28" name="cveArticulo28" value="I" < %= (strTipoTramite.equals("11") || strTipoTramite.equals("13") || strTipoTramite.equals("14") || strTipoTramite.equals("15") || strTipoTramite.equals("16") || strTipoTramite.equals("17") || strTipoTramite.equals("18"))?"checked=\"checked\"":"" % > </label> -->
									<b>Seleccione uno de los tipos correspondientes a la Fracci&oacute;n I del Art&iacute;culo 28
										<table>
											<tr>
												<td>
													<input type="radio" class="submit_no_margin" id="fraccionArticulo28" name="fraccionArticulo28" value="1" ${reporteClemBean.tipoTramite=="11"?"checked='checked'":""} />
													Cambio de actividades
												</td>
												<td>
													<input type="radio" class="submit_no_margin" id="fraccionArticulo28" name="fraccionArticulo28" value="2" ${reporteClemBean.tipoTramite=="13"?"checked='checked'":""} />
													Incorporaci&oacute;n de nuevas actividades
												</td>
												<td>
													<input type="radio" class="submit_no_margin" id="fraccionArticulo28" name="fraccionArticulo28" value="3" ${reporteClemBean.tipoTramite=="14"?"checked='checked'":""} />
													Compra de activos
												</td>
											</tr>
											<tr>
												<td>
													<input type="radio" class="submit_no_margin" id="fraccionArticulo28" name="fraccionArticulo28" value="4" ${reporteClemBean.tipoTramite=="16"?"checked='checked'":""} />
													Enajenaci&oacute;n
												</td>
												<td>
													<input type="radio" class="submit_no_margin" id="fraccionArticulo28" name="fraccionArticulo28" value="5" ${reporteClemBean.tipoTramite=="17"?"checked='checked'":""} />
													Arrendamiento
												</td>
												<td>
													<input type="radio" class="submit_no_margin" id="fraccionArticulo28" name="fraccionArticulo28" value="6" ${reporteClemBean.tipoTramite=="15"?"checked='checked'":""} />
													Comodato
												</td>
											</tr>
											<tr>
												<td>
													<input type="radio" class="submit_no_margin" id="fraccionArticulo28" name="fraccionArticulo28" value="7" ${reporteClemBean.tipoTramite=="18"?"checked='checked'":""} />
													Fideicomiso traslativo
												</td>
												<td>
													<!-- 5134215 / WO1962561 - Cambio de domicilio con diferente municipio -->
													<!-- Se agrega nuevo valor a los tipos de causas --> 
													<input type="radio" class="submit_no_margin" id="fraccionArticulo28" name="fraccionArticulo28" value="17" ${reporteClemBean.tipoTramite=="176"?"checked='checked'":""} />
													Modificaci&oacute;n en SRT por cambio de domicilio en diferentes municipios
												</td>
											</tr>
										</table>
									</b>
								</fieldset>
								
								<fieldset class="fsInternosinlineas"></fieldset>
								<fieldset class="fsInternosinlineas"></fieldset>
								<fieldset class="fsInternosinlineas"></fieldset>
								<fieldset class="fsInternosinlineas"></fieldset>
								
								<fieldset class="fsInternosinlineas">
									<input type="radio" class="submit_no_margin" id="fraccionArticulo28" name="fraccionArticulo28" value="8" style="display: inline;" ${reporteClemBean.tipoTramite=="7"?"checked='checked'":""}>
									<label class="wideA2" style="width: 25%; display: inline;">
										<b>II</b>-Cambio de domicilio patronal
									</label>
									<input type="radio" class="submit_no_margin" id="fraccionArticulo28" name="fraccionArticulo28" value="9" style="display: inline;" ${reporteClemBean.tipoTramite=="20"?"checked='checked'":""}>
									<label class="wideA2" style="width: 25%; display: inline;">
										<b>III-</b>Sustituci&oacute;n patronal
									</label>
								</fieldset>
								<fieldset class="fsInternosinlineas">
									<input type="radio" class="submit_no_margin" id="fraccionArticulo28" name="fraccionArticulo28" value="10" style="display: inline;" ${reporteClemBean.tipoTramite=="21"?"checked='checked'":""}>
									<label class="wideA2" style="width: 25%; display: inline;">
										<b>IV-</b>Fusi&oacute;n
									</label>
									<input type="radio" class="submit_no_margin" id="fraccionArticulo28" name="fraccionArticulo28" value="11" style="display: inline;" ${reporteClemBean.tipoTramite=="19"?"checked='checked'":""}>
									<label class="wideA2" style="width: 25%; display: inline;">
										<b>V-</b>Escisi&oacute;n
									</label>
								</fieldset>
								<fieldset class="fsInternosinlineas">
									<label class="wideA" style="width: 100%;">
										<input type="radio" class="submit_no_margin" id="fraccionArticulo28" name="fraccionArticulo28" value="12" disabled="disabled">
											<b>VI-</b>En cualquier otra circunstancia que afecte su registro, se estar&aacute; a lo dispuesto a las reglas
											establecidas en la fracci&oacute;n VII del art&iacute;culo 32 de este Reglamento
									</label>
								</fieldset>
								
								<fieldset class="fsInternosinlineas"></fieldset>
								<fieldset class="fsInternosinlineas"></fieldset>
								<fieldset class="fsInternosinlineas"></fieldset>
								<fieldset class="fsInternosinlineas"></fieldset>
								
								<fieldset class="fsInternosinlineas">
									<b>Decreto del 23 de abril, Subcontrataci&oacute;n laboral
										<table>
											<tr>
												<td>
													<input type="radio" class="submit_no_margin" id="fraccionArticulo28" name="fraccionArticulo28" value="13" ${reporteClemBean.tipoTramite=="175"?"checked='checked'":""} />
													Sustituaci&oacute;n por Subcontrataci&oacute;n Laboral
												</td>
											</tr>	
										</table>
									</b>
								</fieldset>
		
								<fieldset class="fsInternosinlineas"></fieldset>
								<fieldset class="fsInternosinlineas"></fieldset>
								<fieldset class="fsInternosinlineas"></fieldset>
								<fieldset class="fsInternosinlineas"></fieldset>
								<fieldset class="fsInterno"></fieldset>
							</c:if>
							<!-- Fin. Validacion para insMod, si es Inscipcion(1) o es Modificacion (0) -->
	
							<c:if test="${reporteClemBean.mostrarComboArt155 == 1}">
								<fieldset class="fsInternosinlineas">
									<label class="wide" style="font-weight: normal !important; width: 100%;">
										Inciso del Art&iacute;culo 155
									</label>
								</fieldset>
								<fieldset class="fsInternosinlineas">
									<p>Seleccione el inciso que corresponda, en caso de que apliquen los dos incisos seleccione la opci&oacute;n ambos:</p>
									<form:errors path="incisio155" cssClass="error"></form:errors>
									<input type="hidden" value="${reporteClemBean.incisio115}">
									<div style="display: block !important;">
										<input type="radio" class="submit_no_margin" id="incisio155" name="incisio155" value="a" style="display: inline;" checked="checked">
										<!-- ${reporteClemBean.incisio115=="a"?"checked='checked'":"" } -->
										<label class="wideA2" style="width: 25%; display: inline">Inciso a)</label>
									</div>
									<div style="display: block !important;">
										<input type="radio" class="submit_no_margin" id="incisio155" name="incisio155" value="b" style="display: inline;" ${reporteClemBean.incisio115=="b"?"checked='checked'":""}>
										<label class="wide" style="width: 25%; display: inline;">Inciso b)</label>
									</div>
									<div style="display: block !important;">
										<input type="radio" class="submit_no_margin" id="incisio155" name="incisio155" value="ab" ${reporteClemBean.incisio115=="ab"?"checked='checked'":""}>
										<label class="wide" style="width: 25%; display: inline;">Ambos</label>
									</div>
								</fieldset>
								<br>
								<fieldset class="fsInterno"></fieldset>
								<br>
							</c:if>
	
							<fieldset class="fsInternosinlineas">
								<form:errors path="lugarFechaExpedicion" cssClass="error"></form:errors>
								<label class="mwide" style="width: 100%;"><b>Lugar y fecha de expedici&oacute;n:</b></label>
								<input type="text" onkeypress="return checkCaracterEspecial(event)" id="lugarFechaExpedicion" name="lugarFechaExpedicion" maxlength="80" style="width: 500px; margin-left: 0px" value="${reporteClemBean.lugarFechaExpedicion}" />
							</fieldset>
		
							<fieldset class="fsInterno"></fieldset>
							<fieldset class="fsInternosinlineas"></fieldset>
					<c:if test="${cveIdPatronDictamen != null}">
						</div>
					</c:if>
					
					<script>
						document.addEventListener('DOMContentLoaded', function() {
							var txt = document.getElementById("motivos");
							if (txt){
								txt.ondrop = function(e) {
									e.preventDefault();
									return false;
								};
						
								txt.ondragover = function(e) {
									e.preventDefault();
									return false;
								};
							}
						});
					</script>
						
					<fieldset class="fsInternosinlineas">
						<c:if test="${cveIdPatronDictamen != null}">
							<div style="display:none">
						</c:if>
								<label class="mwide" style="width: 100%;"><b>Motivos</b></label>
								<div id="divErrorMotivos">
									<form:errors path="motivos" cssClass="error"></form:errors>
								</div>
								<c:if test="${cveIdPatronDictamen == null}">
									<textarea onfocus="borrarError()" onkeypress="return checkCaracterEspecial(event)" name="motivos" id="motivos" cols="120" rows="4" style="width: 600px; margin-left: 0px;">${reporteClemBean.motivos}</textarea>
								</c:if>
								<c:if test="${cveIdPatronDictamen != null}">
									<textarea onfocus="borrarError()" onkeypress="return checkCaracterEspecial(event)" name="motivos" id="motivos" cols="120" rows="4" style="width: 600px; margin-left: 0px;">SIN MOTIVOS DICTAMEN</textarea>
								</c:if>
						<c:if test="${cveIdPatronDictamen != null}">
							</div>
						</c:if>
							
						<br>
						<br>
						
						<!-- Se agrega la seccion para para cargar el nuevo archivo -->
						<c:if test="${reporteClemBean.botonClem == 'Finalizar'}">
							<div>
	        					<h2 style="text-align: center !important; margin: 0 0 20px 0 !important; color: #000 !important;">Adjuntar Documentos</h2>
	        					
								<table style="width: 100% !important; margin: 0 auto !important; text-align: center !important;">
									<!-- Fila 1: Documento Ejemplo -->
									<tr>
						                <td colspan="2" style="text-align: right !important; padding: 10px 0 !important;">
						                    <label for="documentoEjemplo" style="font-weight: bold !important; width: 40%">Documentos:</label>
						                    <input type="text" id="documentoEjemplo" readonly 
						                    style="border: 1px !important; 
						                    width: 250px !important; 
						                    height: 22px !important; 
						                    text-transform: none;
						                    margin: 0 10px 5px 5px;" />
						                </td>
						            </tr>
								
									<!-- Fila 2: Mensaje de error -->
									<tr>
						                <td colspan="2" style="text-align: center !important; padding: 5px 0 !important;">
						                    <label id="errDocumento" 
						                    style="color: red !important;
						                    display: none !important;
						                    font-weight: bold !important;
						                    width: 100% !important">Tipo de documento incorrecto</label>
						                </td>
						            </tr>
						            <!-- Fila 3: Etiqueta "Documento a Adjuntar" -->
									<tr>
						                <td colspan="2" style="text-align: center !important; padding: 10px 0 !important;">
						                    <label style="font-weight: bold !important; width: 100% !important">Documento a Adjuntar:</label>
						                </td>
						            </tr>
						            <!-- Fila 4: Boton y estado del archivo -->
									<tr>
						                <td colspan="2" style="text-align: center !important; padding: 10px 0 !important;">
						                    <input type="file" id="fileUpload" name="docRect" accept=".pdf" style="display: none;">
						                    <button type="button" id="btnSeleccionarArchivo" 
						                    style="padding: 2px 8px !important;
						                    border: 1px solid #767676 !important; 
						                    background-color: #f0f0f0 !important; 
						                    color: #000 !important; 
						                    font-family: Arial, Helvetica, sans-serif !important; 
						                    font-size: 13px !important; 
						                    font-weight: normal !important; 
						                    cursor: pointer !important;
						                    text-transform: none;">Seleccionar archivo</button>
						                    <span id="nombreArchivoSeleccionado" style="margin-left: 10px !important; color: #999 !important;">Ning&uacute;n archivo seleccionado</span>
						                </td>
						            </tr>
								</table>
							</div>
							
							<script>
								//Colocar el nombre de ejemplo que debe tener el archivo
								var ejemploDocumentoNombre = "";
								$(document).ready(function(){
									var registroPatronalTratado = document.getElementById('registroPatronal').value;
									ejemploDocumentoNombre = "Hoja_Irregularidades_CE_" + registroPatronalTratado + ".pdf";
									document.getElementById('documentoEjemplo').value = ejemploDocumentoNombre;
								});
								
								// Al hacer clic en el boton, abre el explorador de archivos
								document.getElementById('btnSeleccionarArchivo').addEventListener('click', function () {
									document.getElementById('fileUpload').click();
								});
					
								// Al seleccionar un archivo, valida que sea .docx y si si actualiza el texto
								document.getElementById('fileUpload').addEventListener('change', function (event) {
									var fileNameSpan = document.getElementById('nombreArchivoSeleccionado');
									var btnSeleccionar = document.getElementById('btnSeleccionarArchivo');
									var errDocumento = document.getElementById('errDocumento');
					
									//Cambiar si hay archivo seleccionado
									if (this.files.length > 0) {
					        			var file = this.files[0];
					        			var fileName = file.name;
					        			var fileExtension = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
					        			var fileSizeMB = file.size / (1024 * 1024);
					        
					        			// Si no es .pdf, mostrar error de tipo incorrecto
					        			if (fileExtension !== '.pdf') {
					        				mostrarErrorDocumento('Tipo de documento incorrecto', 'Archivo no válido')
								            return;
								        } 
					        			// valida el nombre correcto
					        	        if (fileName !== ejemploDocumentoNombre) {
					        	            mostrarErrorDocumento('Nombre de archivo incorrecto', 'Nombre no válido');
					        	            return;
					        	        }
					        	        //Tamanio maximo 4 MB
					        	        if (fileSizeMB > 4) {
					        	            mostrarErrorDocumento('El archivo debe tener un tamaño menor a 4 MB. Tamaño actual: ' + fileSizeMB.toFixed(2) + ' MB', 'Archivo mayor a 4 MB');
					        	            return;
					        	        }
					        			else{
								        	// Si es .pdf, ocultar error y procesar
									        errDocumento.style.display = 'none';
									        
									        // Actualizar el span con el nombre del archivo
									        fileNameSpan.textContent = fileName;
									        fileNameSpan.style.color = '#333';
									
									        // Deshabilitar el boton
									        btnSeleccionar.disabled = true;
									        btnSeleccionar.style.opacity = '0.5';
									        btnSeleccionar.style.cursor = 'not-allowed';
								        }
								    } else {
								        // Si no hay archivo, limpiar todo
								        errDocumento.style.display = 'none';
								        fileNameSpan.textContent = 'Ningún archivo seleccionado';
								        fileNameSpan.style.color = '#999';
					
								        // Habilitar el boton
								        btnSeleccionar.disabled = false;
								        btnSeleccionar.style.opacity = '1';
								        btnSeleccionar.style.cursor = 'pointer';
								    }
									
									// Funcion para mostrar error
									function mostrarErrorDocumento(mensajeError, textoSpan) {
									    var errDocumento = document.getElementById('errDocumento');
									    var fileNameSpan = document.getElementById('nombreArchivoSeleccionado');
									    var btnSeleccionar = document.getElementById('btnSeleccionarArchivo');
									    
									    errDocumento.textContent = mensajeError;
									    errDocumento.style.display = 'block';
									    fileNameSpan.textContent = textoSpan;
									    fileNameSpan.style.color = '#d00';
									    btnSeleccionar.disabled = false;
									    btnSeleccionar.style.opacity = '1';
									    btnSeleccionar.style.cursor = 'pointer';
									    document.getElementById('fileUpload').value = '';
									}
								});
								function validarDocumento(){
									// Validar si hay archivo seleccionado
									 var fileUpload = document.getElementById('fileUpload');
									 var errDocumento = document.getElementById('errDocumento');
									 
									 if (fileUpload.files.length === 0) {
										errDocumento.textContent = 'Documento obligatorio';
									    errDocumento.style.display='block';
									    return false;
									 }
									 errDocumento.style.display = 'none';
								     return true;
								}
							</script>
						</c:if>
						<!-- Se agrega la seccion para para cargar el nuevo archivo -->
			
						<div style="text-align: right; float: right;">
							<input type="button" class="mboton" style="width: 120px;" id="generarrClem" name="generarrClem" value="${reporteClemBean.botonClem}" onclick="generarClem()" />
							<input type="button" class="mboton" style="width: 120px;" id="btnRegresar" name="btnRegresar" value="Regresar" />
						</div>
					</fieldset>
				</fieldset>
			</form:form>
			<c:if test="${cveIdPatronDictamen == null}">
				<form id="regresaForm" action="<%=request.getContextPath()%>/solicitud/${idSolicitud}/${regPatronal}/${reporteClemBean.tipoPersona}/detalle" method="POST"></form>
			</c:if>
			<c:if test="${cveIdPatronDictamen != null}">
				<form id="regresaForm" action="<%=request.getContextPath()%>/solicitud/detalle" method="POST">
					<input type="hidden" name="cveIdPatronDictamen" id="cveIdPatronDictamen" value="${cveIdPatronDictamen}" /> 
					<input type="hidden" name="cveIdPatronSujetoObligado" id="cveIdPatronSujetoObligado" value="${sujetoObligado.cveIdSujetoObligado}" />
					<input type="hidden" name="idSolicitud" id="idSolicitud" value="${idSolicitud}" /> 
					<input type="hidden" name="registroPatronal" id="registroPatronal" value="${regPatronal}" /> 
					<input type="hidden" name="rfc" id="rfc" value="${rfc}" />
				</form>
			</c:if>
		</div>
	</div>
</div>

<script>
document.oncontextmenu = function(){return false}

$(document).ready(function(){
	<c:if test="${reporteClemBean!=null}">
		/*Inicializamos*/
		<c:if test="${reporteClemBean.cveTipoClem!=null && reporteClemBean.cveTipoClem!=''}">
			if(${reporteClemBean.cveTipoClem}=="1"){
			$('input:radio[name="cveTipoClem"]').filter('[value="1"]').attr('checked', true);
			$('input:checkbox[name="checkFirma"]').attr('checked', false);
			document.getElementById('suplente').value = "";		
			}
			
			if(${reporteClemBean.cveTipoClem}=="2"){
			$('input:radio[name="cveTipoClem"]').filter('[value="2"]').attr('checked', true);
			delSub = true;
			showSuplente(true);
			firmaAus = document.getElementById('firmaAusencia').value;
			if(firmaAus!= null && firmaAus=="1")
				$('input:checkbox[name="checkFirma"]').attr('checked', true);
			}
		</c:if>
		<c:if test="${mostrarComboArt155 == '1'}">
			 //$("input#incisoArticulo155").val(${datosClem.incisoArticulo155});
			 var $radios = $('input:radio[name=incisio155]');
			 $radios.filter('[value="${reporteClemBean.incisio155}"]').attr('checked', true);
		</c:if>	
	</c:if>
	limitarCampo('motivos', 3999);
});
</script>