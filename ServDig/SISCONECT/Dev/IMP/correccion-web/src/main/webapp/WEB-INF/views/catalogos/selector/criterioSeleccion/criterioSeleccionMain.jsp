<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<html lang="sp">

 
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/catalogos/selector/criterioSeleccion/criterioSeleccion.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>


		
		<div id="cuerpo">
			
			
			<div class="menu_principal" style="height: 2em !important;"> 
		      <!--inicia menu principal-->
		      <div align="center">
		        <div class="centrado">
		          <ul style="height: 1em !important;">
		            <li><a> Criterio Selecci&oacute;n</a></li>
		          </ul>
		        </div>
		        <!--fin centrado--> 
		      </div>
		    </div>
		    
		    <div id="dgcriterioSeleccion"  style="width: 100%;">		
				<div style="background-color: #f2fff2;">
	 				<form:form action="" method="post" id="formcriterioSeleccion" modelAttribute="selectorCatVO" >
	 					<table align="center">
							<tr>
								<td>
									<table style="width: 900px" align="center" class="tablaverde2">
	 									<tr valign="top" class="impar">
											<td align="left" colspan="2">&nbsp;</td>	
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="120px" colspan="1">
												
												<label> Tipo : </label>
											</td>
											<td align="left" width="780px">
												<combo:creaCombo 
					  								entidad="mx.gob.imss.ctirss.correccion.model.CgcCatTipo"
					  								idHtml="tipo.idTipo"
					  								idHtmlContenedor="formcriterioSeleccion"					  								 														
					  							/>								
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" colspan="2">&nbsp;</td>	
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="120px" colspan="1">
												
												<label> Origen : </label>
											</td>
											<td align="left" width="780px">
					  							<combo:creaCombo 
					  								 entidad="mx.gob.imss.ctirss.correccion.model.CgcCatOrigen"
													 idHtml="origen.idOrigen"
													 idHtmlContenedor="formcriterioSeleccion"
												 />								
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" colspan="2">&nbsp;</td>
										</tr>	
										<tr valign="top" class="par">
											<td colspan="2">
												<table style="width: 700px" align="center" >
													<tbody>
														<tr>
															<td align="center" colspan="1">
																<a href="#" onclick="javascript:buscar();"><span class="boton">Buscar</span></a>
															</td>
															<td align="center" colspan="1">
																<a href="#" onclick="javascript:agregar();"><span class="boton">Agregar</span></a>
															</td>
														</tr>
													</tbody>
												</table>
											</td>
											
										</tr>	
										<tr valign="top" class="par">
											<td align="left" colspan="2">&nbsp;</td>
										</tr>	
										<tr valign="top" class="impar">
											<td align="left" colspan="2">&nbsp;</td>
										</tr>				
	  								</table>
					  			</td>
					  		</tr>
						</table>
						<br />
					</form:form>
				</div>
			</div>
			
			
						
			
			
		    <div id="criterioSeleccionData" style="overflow: auto; width:900px; height:320px;" align="center">
				<jsp:include page="criterioSeleccionData.jsp" />
			</div>
			
			
			
			<%-- <div id="criterioSeleccionButtons">
				<jsp:include page="criterioSeleccionButtons.jsp" />
			</div> --%>
			
			<br>	    	    
</div>	    

			<div id="criterioSeleccionGuardar">
				<jsp:include page="criterioSeleccionGuardar.jsp" />
			</div>
			
			

			
			
	
