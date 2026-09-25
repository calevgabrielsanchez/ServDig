<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="sp">

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/catalogos/percepciones/percepciones.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>

<div id="cuerpo">

	<div class="separadorseccion">
		<span>
			Percepciones
		</span>
	</div>
    
    <div id="dgPercepcion">
		<div id="wrapperDialogPercepcion">	
			<form:form modelAttribute="crcPercepciones" action="/catalogo/percepciones/consultar.do" method="post" id="percepcionesForm">
				<form:hidden path="cveSubdelegcionOficial" id="cveSubdelegcionOficialHtml"/>
				<fieldset>
					<table>
						<tr>
							<td>
								<table style="border-collapse: separate; border-spacing:  5px 5px;">
									<tbody>
										<tr>
											<td width="160px">
												<span class="required">*</span>
												<form:label	id="folioCorreccionLabel" for="folioCorreccion" path="folioCorreccion" cssErrorClass="error">
													Folio Correcci&oacute;n: 
												</form:label>
											</td>
											<td>
												<form:input	path="folioCorreccion" id="mainFolio" label="Folio Corrección" maxlength="20" onkeyup="validaCampo('noCaracteresEspeciales','mainFolio','percepcionesForm');" onblur="pasaParametroFolio()"/>
												<label id="labelFolioMain"></label>												
											</td>
										</tr>
										<tr>
											<td>
												<form:label id="txRemuneracionLabel" for="txRemuneracion" path="txRemuneracion" cssErrorClass="error"> 
													Percepci&oacute;n: 
												</form:label>
											</td>
											<td>
												<form:input	path="txRemuneracion" value="" label="Remuneracion" maxlength="50" onkeyup="validaCampo('noCaracteresEspeciales','txRemuneracion','percepcionesForm');"/>
											</td>
										</tr>
									</tbody>
								</table>
								<br>
								<table style="width: 960px">
									<tbody>
										<tr align="center">
											<td>
										  		<a id="btnBuscaPercepcion"  href="#" onclick="javascript:buscar();"><span class="btn btn-primary btn-sm">Buscar</span></a>
										  	</td>
										  	<td>
										  		<a href="#" onclick="javascript:limpiar();"><span class="btn btn-primary btn-sm">Limpiar</span></a>
										  	</td>
										  	<td>
										  		<a href="#" onclick="javascript:registra();"><span class="btn btn-primary btn-sm">Agregar</span></a>
										  	</td>
										</tr>
									</tbody>	
								</table>
								<br>
							</td>
						</tr>
					</table>
					<form:hidden path="cvePercepcion" />
				</fieldset>
			</form:form>							    
	  	</div>
	</div>
	
	<div id="registroPercepcion">
		<jsp:include page="percepcionRegistro.jsp" />
	</div>
	
	<div id="percepcionData">
		<jsp:include page="percepcionData.jsp" />
	</div>
	
	<div id="percepcionBorrar" align="center">
		<jsp:include page="percepcionBorrar.jsp" />
	</div>
	
	<div id="percepcionButtons">
		<jsp:include page="percepcionButtons.jsp" />
	</div>
	
	<br>	    	    
</div>	    
	
