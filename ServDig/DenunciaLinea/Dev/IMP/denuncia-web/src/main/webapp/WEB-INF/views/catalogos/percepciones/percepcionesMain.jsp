<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="sp">

 
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/catalogos/percepciones/percepciones.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>


		
		<div id="cuerpo">
			
			
			<div class="menu_principal" style="height: 2em !important;"> 
		      <!--inicia menu principal-->
		      <div align="center">
		        <div class="centrado">
		          <ul style="height: 1em !important;">
		            <li><a> Percepciones</a></li>
		          </ul>
		        </div>
		        <!--fin centrado--> 
		      </div>
		    </div>
		    
		    <div id="dgPercepcion" title="Percepciones" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
				<div id="wrapperDialogPercepcion" style="background-color: #f2fff2;">	
					<form:form modelAttribute="crcPercepciones" action="/catalogo/percepciones/consultar.do" method="post" id="percepcionesForm">
						<fieldset>
							<table style="width: 900px" align="center">
								<tr valign="middle">
									<td align="center" width="900px">
			
										<table class="tablaverde2" style="width: 900px">
											<tbody>
												<tr valign="top" class="impar">
													<td align="left" colspan="4">&nbsp;</td>	
												</tr>
												<tr valign="top" class="par">
													<td align="left" width="120px" colspan="1">
														<label style="color: red;">* </label>
														<form:label	id="folioCorreccionLabel" for="folioCorreccion" path="folioCorreccion"
															cssErrorClass="error"> Folio Correcci&oacute;n: </form:label>
													</td>
													<td align="left" width="780px" colspan="3">
														<form:input	path="folioCorreccion" id="mainFolio" label="Folio Correcci�n" maxlength="20" onkeyup="validaCampo('noCaracteresEspeciales','mainFolio','percepcionesForm');"/>
														<label id="labelFolioMain"></label>												
													</td>
												</tr>
												<tr valign="top" class="par">
													<td align="left" width="100px" colspan="1"><form:label
															id="txRemuneracionLabel" for="txRemuneracion" path="txRemuneracion" 
															cssErrorClass="error"> Percepci&oacute;n: </form:label>
													</td>
													<td align="left" width="780px" colspan="3">
														<form:input	path="txRemuneracion" value="" label="Remuneracion" maxlength="50" onkeyup="validaCampo('noCaracteresEspeciales','txRemuneracion','percepcionesForm');"/>
													</td>
												</tr>
												<tr valign="top" class="impar">
													<td align="left" colspan="4">&nbsp;</td>
												</tr>										
											</tbody>
										</table>
										<br>
										<table class="tablaverde2" border="0" style="width: 700px">
											<tbody>
												<tr align="center">
													<td align="right" width="33%" colspan="2">
												  		<a href="#" onclick="javascript:buscar();"><span class="boton">Buscar</span></a>
												  	</td>
												  	<td align="center" width="33%" colspan="2">
												  		<a href="#" onclick="javascript:limpiar();"><span class="boton">Limpiar</span></a>
												  	</td>
												  	<td align="left" width="33%" colspan="2">
												  		<a href="#" onclick="javascript:registra();"><span class="boton">Agregar</span></a>
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
		
			
			<div id="percepcionData" style="overflow: auto; width:900px; height:320px;" align="center">
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
	
