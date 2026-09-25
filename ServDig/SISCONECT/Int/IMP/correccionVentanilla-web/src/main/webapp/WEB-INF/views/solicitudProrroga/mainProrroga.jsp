<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@page import="mx.gob.imss.ctirss.correccion.session.UserSession"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="sp">

<script type="text/javascript"	
	src="<%=request.getContextPath()%>/resources/js/delta/solicitud/FirmaDigital.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/solicitudProrroga/prorroga.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<div id="contenedorFirmaProrroga"></div>
	<div id="cuerpo">
			
			
			
			
			<div class="menu_principal" style="height: 2em !important;"> 
		      <!--inicia menu principal-->
		      <div align="center">
		        <div class="centrado">
		          <ul style="height: 1em !important;">
		            <li><a> Solicitud de Pr&oacute;rroga para la Presentaci&oacute;n de la Correcci&oacute;n Patronal</a></li>
		          </ul>
		        </div>
		        <!--fin centrado--> 
		      </div>
		    </div>
		    
		    <div id="dgPercepcion" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
		<div id="wrapperDialogPercepcion" style="background-color: #f2fff2;">	
			<form:form modelAttribute="crtAnexosolcorrpat" action="prorroga/muestraReporte.do"
						method="post" id="prorrogaForm">
				<fieldset>
					<table style="width: 900px" align="left" border="0">
						<tr valign="middle">
							<td align="center" width="900px">
	
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
										<tr valign="top" class="par">   												
											<td align="left" width="120px"  class="etiqueta2">
											    	<form:label for="nuFolio" id="nuFolioLabel" path="nuFolio" 
										    		cssErrorClass="error">Folio de la Solicitud de Correcci&oacute;n:</form:label>	    		
											   </td>
											   <td align="left" width="80px">
											   		<form:input path="nuFolio" id="nuFolioInput" size="20" maxlength="18" onclick="datosNumeroFolio();" 
											   		onkeyup="validaCampo('noCaracteresEspeciales','nuFolioInput');" onblur="this.value=(this.value).toUpperCase();" />
											   		<form:errors path="nuFolio" />
											   </td>
										
											<td align="left" width="120px"  class="etiqueta2">
												<form:label for="registroPatronal" id="registroPatronalLabel" path="registroPatronal" 
										    		cssErrorClass="error"> Registro Patronal: </form:label>
											</td>
											
										
											<td align="left" width="80px">
												<form:input path="registroPatronal" id="registroPatronalInput" size="20" maxlength="11" onclick="datosRegPatronal();"
												onkeyup="validaCampo('noCaracteresEspeciales','registroPatronalInput');" onblur="this.value=(this.value).toUpperCase();"/>
											   	<form:errors path="registroPatronal" />
											</td>
											
										</tr>
										<tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>	
										<tr align="center">
										  <td align="center" colspan="4">
										  	<a id="btnBuscarProrroga" href="#"  onclick="javascript:consultaGral()"><span class="boton">Buscar</span></a>
										  </td>
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
	
			</form:form>							    
	  	</div>
	</div>
		<div id="prorrogaReg" style="width:900px" align="center">
				<jsp:include page="datosProrroga.jsp"/>
			</div>
			
			<table id="dtSolicitudCorreccion"  style="width: 900px">
								<thead>
								</thead>
								<tbody>
								</tbody>
			</table>
		
			<div id="dgProrrogaCaptura"
					style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity = 70) !important;">
						<div id="wrapperDialogCapProrroga" style="background-color: #f2fff2;">

					<table width="810px" border="0px" align="center">
						<tr valign="middle">
								<td align="center" width="100px"><a href="#"
										onclick="javascript:porRegistroPat();"><span class="boton">Solicitar</span></a>
								</td>				
						</tr>
					</table>

				</div>
			</div>
				
			
			
			<br>	    	    
</div>	    
		 
					
	