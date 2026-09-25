<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<html lang="sp">

 
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/catalogos/grupoCategoria/grupoCategoria.js"></script>
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
		            <li><a> Grupo Categor&iacute;a</a></li>
		          </ul>
		        </div>
		        <!--fin centrado--> 
		      </div>
		    </div>
		    
		    <div id="dgGrupoCategoria"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
		<div id="wrapperDialogGrupoCategoria" style="background-color: #f2fff2;">	
			<form:form modelAttribute="crcGrupoCategoria" action="/catalogo/grupoCategoria/consultar.do"
						method="post" id="grupoCategoriaForm">
						<form:hidden path="cveSubdelegcionOficial" id="cveSubdelegcionOficialCat"/>
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
											<td align="left" width="120px" colspan="1" class="etiqueta2">
												<label style="color: red;">* </label>
												<form:label	id="folioCorreccionLabel" for="folioCorreccion" path="folioCorreccion"
													cssErrorClass="error"> Folio Correcci&oacute;n: 
												</form:label>
											</td>
											<td align="left" width="780px" colspan="3">
												<form:input	path="folioCorreccion" value="" label="Folio Corrección" maxlength="20" id="folioMain" onkeyup="this.value=(this.value).toUpperCase();"  onblur="this.value=(this.value).toUpperCase();"/>
												<label id="labelFolioMain"></label>
											</td>
										</tr>
										<tr valign="top" class="impar">
											<td align="left" width="100px" colspan="1" class="etiqueta2"><form:label
													id="txGrupoCategoriaLabel" for="txGrupoCategoria" path="txGrupoCategoria" 
													cssErrorClass="error"> Grupo Categor&iacute;a: </form:label>
											</td>
											<td align="left" width="100px" colspan="1">
												<form:input	path="txGrupoCategoria" value="" label="GrupoCategoria" maxlength="18" id="txGrupoCategoriaMain" onkeyup="mayusculasTextField(this)"/>
												<label id="labelGrupoCategoriaMain"></label>
											</td>
										</tr>
									</tbody>
								</table>
								<br>
								<table>
									<tbody>
										<tr align="center">
											<td align="center" width="100px" colspan="2">
										  		<a id="btnBuscarCategoria" href="#" onclick="javascript:buscar();"><span class="boton">Buscar</span></a>
										  	</td>
										  	<td align="center" width="100px" colspan="2">
										  		<a href="#" onclick="javascript:limpiar();"><span class="boton">Limpiar</span></a>
										  	</td>
											<td align="center" width="100px" colspan="2">
												<a href="#" onclick="javascript:registra();">
													<span class="boton">Agregar</span>
												</a>
											</td>
										  	
										</tr>	
										<tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
									</tbody>
								</table>							
									
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
		
			
			<div id="grupoCategoriaData" style="overflow: auto; width:900px; height:320px;" align="center">
				<jsp:include page="grupoCategoriaData.jsp" />
			</div>
			
			<div id="grupoCategoriaBorrar" align="center">
				<jsp:include page="grupoCategoriaBorrar.jsp" />
			</div>
			
			<div id="grupoCategoriaButtons">
				<jsp:include page="grupoCategoriaButtons.jsp" />
			</div>
			
			<br>	    	    
</div>	    
	
