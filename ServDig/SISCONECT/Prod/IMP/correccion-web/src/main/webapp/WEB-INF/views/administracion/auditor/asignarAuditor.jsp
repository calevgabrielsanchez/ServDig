<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<html lang="sp">

 
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/administracion/auditor/asignarAuditor.js"></script>
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
					<li><a> Asignaci&oacute;n de Auditor</a></li>
				</ul>
			</div>
		<!--fin centrado--> 
		</div>
	</div>
		    
	<div id="dgAsignarAuditor"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
		<div id="wrapperAsignarAuditor" style="background-color: #f2fff2;">	
			<form action=""	method="post" id="asignarAuditorForm">
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
											<td align="left" class="etiqueta2">
												<span class="required">*</span> Origen : 
											</td>
											<td align="left" >
												<select id="cboOrigen" name="idOrigen" onchange="$('form#asignarAuditorForm #labelOrigen').html('');">
													<option value="">--Seleccione por favor--</option>
												</select>
												<label id="labelOrigen"></label>
											</td>	
											<td align="left" class="etiqueta2">
												<span> Folio :</span> 
											</td>
											<td align="left" >
												<input type="text" id="folioTemp" name="folioTemp" size="21" maxlength="21" onkeyup="javaScript:jsValidaOficio(this.value);validaCampo('noCaracteresEspeciales','folioTemp','asignarAuditorForm');" >
												<label id="labelFolio"></label>
											</td>																								
										</tr>	
										<tr>
											<td align="left" class="etiqueta2">
												<span class="required">*</span> Periodo : 
											</td>
											<td align="left" colspan="2">
												<table>
													<tr>
														<td align="left" class="etiqueta2">
															<label>&nbsp; Del :&nbsp;&nbsp;</label>
														</td>
														<td align="left">
															<input type="text" id="fechaIncial" name="fechaIncial" size="12" readonly="readonly" >
														</td>
														<td align="left" class="etiqueta2">
															<label>&nbsp; Al :&nbsp;&nbsp;</label>
														</td>
														<td align="left">
															<input type="text" id="fechaFinal" name="fechaFinal" size="12" readonly="readonly" >
															<label id="labelPeriodo"></label>
														</td>
													</tr>
												</table>
											</td>
										</tr>																																			
										<tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
										<tr valign="top" class="par">
											<td align="center" width="80px" colspan="4">
												<a href="#" onclick="javascript:buscar();"><span class="boton">Consultar</span></a>
												<a href="#" onclick="javascript:limpiar();"><span class="boton">Limpiar</span></a>
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
	
			
	 <div id="asignarAuditorResult"  style="overflow: auto; width:950px; height:300px;" align="center" class="centrado">   
	         <table id="dtAsignarAuditor"  style="width: 930px" align="center">
				<thead>			
				</thead>
				<tbody align="center">
				</tbody>
	    	</table>
	 </div>
	<div id="btnVisualizar" style="display:none" align="center">
		<br>
		<a href="#" onclick="javascript:mostrar();"><span class="boton">Ver</span></a>
	</div>		
				
	<div id="datosFolio" align="center" class="centrado" >
		<jsp:include page="datosFolio.jsp" />
	</div>	
	
	<div id="confirmar" align="center" class="centrado" >
		<jsp:include page="confirmarAsignarAuditor.jsp" />
	</div>		    	   
</div>	 