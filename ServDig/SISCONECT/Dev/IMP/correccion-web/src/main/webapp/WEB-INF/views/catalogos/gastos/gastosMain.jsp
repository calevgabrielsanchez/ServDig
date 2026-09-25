<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<html lang="sp">

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/catalogos/gastos/gastos.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>

<div id="cuerpo">
    <div class="separadorseccion">
		<span>
			Gastos
		</span>
	</div>
		    
    <div id="dgGasto">
		<div id="wrapperDialogGasto">	
			<form:form modelAttribute="crcGastos" action="/catalogo/gastos/consultar.do" method="post" id="gastosForm">
				<form:hidden path="cveSubdelegcionOficial" id="cveSubdelegcionOficialGastos"/>
				<fieldset>
					<table>
						<tr>
							<td>
								<table style="border-collapse: separate; border-spacing:  5px 5px;">
									<tbody>
										<tr>
											<td width="160px">
												<span class="required">*</span>
												<form:label id="folioCorreccionLabel" for="folioCorreccion" path="folioCorreccion" cssErrorClass="error">
													Folio Correcci&oacute;n:
												</form:label>
											</td>
											<td>
												<form:input	path="folioCorreccion" value="" label="Folio Corrección" maxlength="20" id="folioMain"
													onkeyup="validaCampo('noCaracteresEspeciales','folioMain','gastosForm');"/>
												<label id="labelFolioMain"></label>
											</td>
										</tr>
										<tr>
											<td>
												<form:label id="txGastoLabel" for="txGasto" path="txGasto" cssErrorClass="error">
													Gasto:
												</form:label>
											</td>
											<td>
												<form:input path="txGasto" value="" label="Gasto" maxlength="50" id="txGastoMain"
													onkeyup="validaCampo('noCaracteresEspeciales','txGastoMain','gastosForm');"/>
											</td>
										</tr>			
									</tbody>
								</table>
								<br>
								<table style="width: 960px">
									<tbody>
										<tr align="center">
											<td>
										  		<a id="btnGastosBusqueda" href="#" onclick="javascript:buscar();"><span class="btn btn-primary btn-sm">Buscar</span></a>
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
				</fieldset>
			</form:form>							    
	  	</div>
	</div>
			
	<div id="registroGasto">
		<jsp:include page="gastosRegistro.jsp" />
	</div>
	
	<div id="gastoData">
		<jsp:include page="gastosData.jsp" />
	</div>
	
	<div id="gastoBorrar" align="center">
		<jsp:include page="gastosBorrar.jsp" />
	</div>
	
	<div id="gastoButtons">
		<jsp:include page="gastosButtons.jsp" />
	</div>
	
	<br>	    	    
</div>	    
	
