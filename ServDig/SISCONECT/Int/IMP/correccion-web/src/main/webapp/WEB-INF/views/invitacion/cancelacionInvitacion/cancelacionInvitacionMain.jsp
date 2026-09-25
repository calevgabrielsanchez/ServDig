<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<html lang="sp">

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/invitacion/cancelarInvitacion/cancelarInvitacion.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
	
	
		<br>
		<div id="principal" >
		
		
			<div class="menu_principal" style="height: 2em !important; width: 100%;"> 
		      	<div align="center">
			        <div class="centrado">
			          <ul style="height: 1em !important;">
			            <li><a> Cancelar Invitaci&oacute;n </a></li>
			          </ul>
			        </div> 
		      	</div>
	    	</div>
	    	
	    	<div id="dgCancelarInvitacion" title="Cancelar Invitacion" style="width: 100%;">		
				<div id="wrapperData" style="background-color: #f2fff2;">
	 				<form:form action="" method="post" id="formCancelarInvitacion">
	 					<table align="center">
							<tr>
								<td>
									<table style="width: 900px" align="center" class="tablaverde2">
	 									<tr valign="top" class="par">
											<td align="center" colspan="4">
												<div id="labelOficio"></div>
											</td>	
										</tr>
	 									<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>	
										</tr>
	 									<tr valign="middle" class="par">
	 										
								  			<td align="center" width="100%" class="etiqueta2">
								  				<label style="color: red;">* </label>Tipo Programa</td>
								  			
								  		</tr>
					  					<tr valign="middle" class="par">
					  						<td align="center" width="100%">
					  							<combo:creaCombo 
					  								entidad="mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr"
					  								idHtml="cveTipocorr"
					  								idHtmlContenedor="formCancelarInvitacion"
					  								param="idTipocorr" paramValue="4"	  														
					  							/>
					  							<div id="labelTipoProagrama"></div>
											</td>
											
										</tr>
	  								</table>
					  			</td>
					  		</tr>
						</table>
						<br />
					</form:form>
				</div>
			</div>
					
						<div id="dgInvitacion2" title="Cancelar Invitacion" style=" width:100%; ">
							<div id="wrapperData2" style="background-color: #f2fff2;">
							 <form:form modelAttribute="crtInvitacion" action="" method="post" id="formInvitacion2">
							 	<table style="width: 900px" align="center">
									<tr>
										<td>
											<table style="width: 900px" align="center" class="tablaverde2">
												<tr valign="middle" class="par">
										  			<td align="center" width="50%" class="etiqueta2"><label style="color: red;">* </label>Fecha Inicial</td>
										  			
										  			<td align="center" width="50%" class="etiqueta2"><label style="color: red;">* </label>Fecha Final</td>
										  			
										  		</tr>
										  		<tr valign="middle" class="par">
							  						<td align="center" width="50%">
							  							<form:input path="fechaIncial" id="fecIni" maxlength="10" onchange="jsValidaFecIni(this.value);jsValidaFechasLimite();"/>
							  							<div id="labelFecIni"></div>
													</td>
													
													<td align="center" width="50%">
							  							<form:input path="fechaFinal" id="fecFin" maxlength="10" onchange="jsValidaFecFinal(this.value);jsValidaFechasLimite();"/>
							  							<div id="labelFecFin"></div>
													</td>
													
												</tr>
												<tr class="par">
													<td colspan="4" align="center">
														<a href="#" id="btnBuscar">
															<span class="boton">Buscar</span>
														</a>
													</td>
												</tr>
												<tr valign="top" class="impar">
													<td align="left" colspan="4">&nbsp;</td>	
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</form:form>
						</div>
					</div>
					
					<div class="menu_principal">
						<!--inicia menu principal-->
						<div align="center">
							<div class="centrado">
								<ul>
									<li><a> RESULTADO DE LA BUSQUEDA </a>
									</li>
								</ul>
							</div>
							<!--fin centrado-->
						</div>
					</div>
					
					<div id="cancelarInvitacionBody" >
						<jsp:include page="cancelarInvitacionCuerpo.jsp" />
					</div>	
					
					<div id="cancelarInvitacionButtons">
						<jsp:include page="cancelarInvitacionButtons.jsp"/>
					</div>		
					
		</div>
		
		
		 <div id="invitacionGuardarIni">
			<jsp:include page="cancelarInvitacionConfirmar.jsp" />
		</div>		