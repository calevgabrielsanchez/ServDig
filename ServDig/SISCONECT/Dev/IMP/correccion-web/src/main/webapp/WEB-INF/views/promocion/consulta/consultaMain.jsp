<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="sp">
<style>
	label.error { float: none; font-size:xx-small; color: red; padding-left: .5em; vertical-align: top; }
	label  {  float: none; }
</style> 

<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/resources/estilos/styleTabs.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/resources/estilos/cedulasStyle.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/resources/estilos/estilo.css">	
<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/promocion/sbc/seguimientoSBC.js"></script>
<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/promocion/construccion/seguimientoConstruccion.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/promocion/satica/seguimientoPromocionSatica.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/promocion/saticb/seguimientoPromocionSaticb.js"></script>

<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/promocion/promocionConsulta.js"></script>
<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>


<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/delta/tabControler.js"></script>

<!-- Del archivo seguimientoPromocionSaticbMain -->
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/promocion/generico/estatusObraGenericoTab.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/promocion/generico/cancelacionGenericoTAB.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/promocion/generico/derivarSubdelegacionGenericoTAB.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/promocion/generico/derivarFiscalizacionTAB.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/promocion/generico/anexoPagosGenerico.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/promocion/generico/regularizarObraTAB.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/promocion/generico/avisoDictamenGenericoTAB.js"></script>
<script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/promocion/generico/cierrePorCotizarRGenerico.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/calendarios.js"></script>
		
<div id="cuerpo">
		
	<div class="menu_principal" style="height: 2em !important;"> 
		<!--inicia menu principal-->
		<div align="center">
			<div class="centrado">
		    	<ul style="height: 1em !important;">
					<li><a>Consulta de Promociones</a></li>
				</ul>
			</div>
		<!--fin centrado--> 
		</div>
	</div>
		    
	<div id="dgPromocionConsulta" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
		<div id="wrapperDialogPromocionConsulta" style="background-color: #f2fff2;">	
			<form action="/consulta/setupPromocion.do" method="post" name="promocionConsultaForm" id="promocionConsultaForm">
			<input type="hidden" id="tipoPromocionHiden" value="${seguimientoPromoVO.tipoPromocion}">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">	
								<table class="tablaverde2" style="width: 900px" >
									<tbody>
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="25%" colspan="1" class="etiqueta2">
											<label style="color: red;">* </label>
											Tipo de Promoci&oacute;n:	
											</td>
											
											<td align="left" width="25%" colspan="1" class="etiqueta2">
											
											<select name="tipoPromocion" id="tipoPromocion" class="etiqueta2" onchange="limpiaCampos(this);seleccionaPromocion(this);">
												<option value="">--Por favor seleccione--</option>														
											</select>
																		
												
											</td>
											<td valign="top" class="par" width="25%">&nbsp;<div id="labelTipoProm"></div>
											</td>
											<td align="left" valign="top" class="par"  width="25%">
											<div id="labelGeneral"></div>
											</td>
											
												
											
										</tr>																				
										<tr valign="top" class="par">   											   	
											<td align="left" width="25%" colspan="1" class="etiqueta2">
											<label style="color: red;">* </label>
											Fecha de Emisi&oacute;n de:												
											</td>
											<td align="left" width="75%" colspan="3" class="etiqueta2">
												<input name="fechaIncial" id="fechaIncial" readonly="readonly" class="etiqueta2"
												onchange="limpiaCampos(this);jsValidaFecPeriodoBusqueda();"/>
											<!-- 	onchange="limpiaCampos(this);validaFechaConsultaPeriodo();"/> -->
												 
												a: 
												<input name="fechaFinal" id="fechaFinal" readonly="readonly" class="etiqueta2" 
												onchange="limpiaCampos(this);jsValidaFecPeriodoBusqueda();"/>
												<!-- onchange="limpiaCampos(this); validaFechaConsultaPeriodo();"/> -->
												
												<div id="labelFechas"></div>
											</td>
																														
										</tr>	
										<!-- SE COMENTA DEBIDO A QUE NO SE PUEDE CONSULTAR  EL SEGUIMIENTO CUANDO SE CONSULTA SOLO POR FOLIO-->																																																																																																																																																																																																																																																																																																																																							
										<!-- <tr valign="top" class="par">
											<td align="left" width="25%" class="etiqueta2">Folio
												de Promoci&oacute;n:</td>
											<td align="left" width="25%">
												<input type="text"
												name="nuFoliopromocion" id="nuFoliopromocion"
												class="etiqueta2" size="25" maxlength="21"  onmousedown="limpiaCampos(this)"
												onkeyup="validaCampo('noCaracteresEspeciales','nuFoliopromocion','promocionConsultaForm');limpiaCampos(this);"
												/>
												<label for="nuFoliopromocion"> </label>
											</td>
											<td valign="top" class="par" width="25%">&nbsp;
											</td>
											<td valign="top" class="par"  width="25%">&nbsp;
											</td>
										</tr> -->
										<tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
										<tr valign="top" class="par">
											<td align="center" width="80px" colspan="4">
												<a href="#" onclick="javascript:buscar();"><span class="boton">Buscar</span></a>
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</table>										
				</fieldset>
			</form>							    
	  	</div>
	</div>				
	
	<div class="menu_principal" style="height: 2em !important;display:none;" id="divTitulo"> 
		<!--inicia menu principal-->
		<div align="center">
			<div class="centrado">
		    	<ul style="height: 1em !important;">
					<li><a>Obras Promovidas Registradas</a></li>
				</ul>
			</div>
		<!--fin centrado--> 
		</div>
	</div>
	<br>		
		
	<div id="promocionDisponiblesPromocion" style="overflow: auto; width:950px; height:400px;display:none; border:thin;border-color: black;" align="center" >		
		<jsp:include page="registrosDisponiblesPromocion.jsp" />
	</div>
	
	<br>    		
	<div id="buttons" style="display:none" align="center">
		<jsp:include page="promocionButtons.jsp" />
	</div>
	
	<div id="confirmarCCRGDiv" style="display: none;">
		<jsp:include page="/WEB-INF/views/seguimiento/promocion/genericos/confirmarCCRG.jsp" />
	</div>
	
		<c:if test="${seguimientoPromoVO.tipoPromocion==7}">
											
					<div id="datosSBC" align="center" style="display: none;">		
						<jsp:include page="../sbc/datosSBC.jsp" />
					</div>		
		</c:if>	
		
		<c:if test="${seguimientoPromoVO.tipoPromocion==3}">
											
					<div id="saticaMain" align="center" style="display: none;">
						<jsp:include page="../saticA/saticAMain.jsp" flush="true"/>
					</div>	
		</c:if>	
		
		<c:if test="${seguimientoPromoVO.tipoPromocion==4}">
											
					<div id="divSeguimientoPromocionSaticb" align="center" style="display: none;">		
						<jsp:include page="/WEB-INF/views/seguimiento/promocion/saticb/seguimientoPromocionSaticbMain.jsp" />
					</div>	
		</c:if>	
		
		<c:if test="${seguimientoPromoVO.tipoPromocion==5}">
											
					<div id="divSeguimientoPromocionConstruccion" align="center" style="display: none;">		
						<jsp:include page="/WEB-INF/views/seguimiento/promocion/construccion/seguimientoConstruccionMain.jsp" />
					</div>	
		</c:if>				
				
														
				<div id="invitacionPromocion" align="center" style="display: none;">		
					<jsp:include page="regularizacion/regularizacionMain.jsp" />
				</div>	
															
				<div id="seguimientoPromocion" align="center" style="display: none;">		
					<jsp:include page="seguimientoPromocion.jsp" />
				</div>	
																	
				<div id="invitacionGuardarIni" style="display: none;">
						<jsp:include page="/WEB-INF/views/invitacion/invitacionGuardar.jsp" />
					</div>	
				 <div id="confirmarInvitacion" style="display: none;">
						<jsp:include page="/WEB-INF/views/invitacion/confirmarInvitacion.jsp" />
				</div>
				<div id="confirmarRP" style="display: none;">
					<jsp:include page="/WEB-INF/views/promocion/sbc/confirmarRegularizaObra.jsp" />
				</div>
				<div id="confirmarPagos" style="display: none;">
					<jsp:include page="/WEB-INF/views/seguimiento/promocion/saticb/confirmarSaticBSeguimiento.jsp" />
				</div>
	
	<input type="hidden" name="accessTabs" id="accessTabs" value="">
	<input type="hidden" name="forbidenTabs" id="forbidenTabs" value="">
		
						    	   
</div>	 