<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<html lang="sp">

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/catalogos/grupoCategoria/grupoCategoria.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>

<div id="cuerpo">
	<div class="separadorseccion">
		<span>
			Grupo Categor&iacute;a
		</span>
	</div>
		    
    <div id="dgGrupoCategoria">
		<div id="wrapperDialogGrupoCategoria">	
			<form:form modelAttribute="crcGrupoCategoria" action="/catalogo/grupoCategoria/consultar.do" method="post" id="grupoCategoriaForm">
				<form:hidden path="cveSubdelegcionOficial" id="cveSubdelegcionOficialCat"/>
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
												<form:input	path="folioCorreccion" value="" label="Folio Corrección" maxlength="20" id="folioMain" 
													onkeyup="this.value=(this.value).toUpperCase();"  onblur="this.value=(this.value).toUpperCase();"/>
												<label id="labelFolioMain"></label>
											</td>
										</tr>
										<tr>
											<td>
												<form:label id="txGrupoCategoriaLabel" for="txGrupoCategoria" path="txGrupoCategoria" cssErrorClass="error"> 
													Grupo Categor&iacute;a: 
												</form:label>
											</td>
											<td>
												<form:input	path="txGrupoCategoria" value="" label="GrupoCategoria" maxlength="18" id="txGrupoCategoriaMain" 
													onkeyup="mayusculasTextField(this)"/>
												<label id="labelGrupoCategoriaMain"></label>
											</td>
										</tr>
									</tbody>
								</table>
								<br>
								<table style="width: 960px">
									<tbody>
										<tr align="center">
											<td>
										  		<a id="btnBuscarCategoria" href="#" onclick="javascript:buscar();"><span class="btn btn-primary btn-sm">Buscar</span></a>
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
								<br/>
							</td>
						</tr>
					</table>
				</fieldset>
			</form:form>
	  	</div>
	</div>
			
	<div id="registroGrupoCategoria">
		<jsp:include page="grupoCategoriaRegistro.jsp" />
	</div>
	
	<div id="grupoCategoriaData">
		<jsp:include page="grupoCategoriaData.jsp" />
	</div>
	
	<div id="grupoCategoriaBorrar" align="center">
		<jsp:include page="grupoCategoriaBorrar.jsp" />
	</div>
	
	<div id="grupoCategoriaButtons">
		<jsp:include page="grupoCategoriaButtons.jsp" />
	</div>
</div>	    
	
