<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">

<html lang="sp">

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/invitacion/invitacion.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/KeyPressed.js"></script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>

		<div id="principal" >		
		
			<div class="menu_principal" style="height: 2em !important; width: 100%;"> 
		      	<div align="center">
			        <div class="centrado">
			          <ul style="height: 1em !important;">
			            <li><a> Invitaci&oacute;n </a></li>
			          </ul>
			        </div> 
		      	</div>
	    	</div>
	    	
	    	<div id="dgInvitacion" style="width: 100%;">		
	    	<div id="dgInvitacion" style="width: 100%;">		
				<div id="wrapperData" style="background-color: #f2fff2;">
	 				<form action="" method="post" id="formInvitacion">
	 					<input type="hidden" id="cveTemp" name="cveTemp">
	 					<input type="hidden" id="tipoPrograma" name="tipoPrograma">
	 					<table align="center">
							<tr>
								<td>
									<table style="width: 900px" align="center" class="tablaverde2">
	 									<tr class="impar">
											<td align="left" colspan="4">&nbsp;</td>	
										</tr>
	 									<tr class="par">	 										
								  			<td align="left" class="etiqueta2">
								  				<label style="color: red;">* </label>Fuente
								  			</td>
								  			<td align="left" colspan="3">
					  							<select id="selectFuente" name="cveTipocorr" onchange="$('form#formInvitacion #labelTipoProagrama').html('');">
					  								<option value="-1">--Seleccione Por favor--</option>
					  							</select>
					  							<div id="labelTipoProagrama"></div>
											</td>								  			
								  		</tr>
								  		<tr class="par">
								  			<td align="left" class="etiqueta2">
								  				<label style="color: red;">* </label>Fecha Inicial:
								  			</td>
								  			<td align="left">
								  				<input id="fecIni" name="fechaIncial" maxlength="10" size="10" readonly="readonly" onchange="jsValidaFecFinal();jsValidaFechasLimite();">
								  				<div id="labelFecIni"></div>
								  			</td>
								  			<td align="left" class="etiqueta2">
								  				<label style="color: red;">* </label>Fecha Final:
								  			</td>
								  			<td align="left">
								  				<input id="fecFin" name="fechaFinal" maxlength="10" size="10" readonly="readonly" onchange="jsValidaFecFinal();jsValidaFechasLimite();">
								  				<div id="labelFecFin"></div>
								  			</td>
								  		</tr>
					  					<tr class="par">
					  						<td align="left" class="etiqueta2">
								  				<label>N&uacute;mero de Folio:</label>
								  			</td>
								  			<td align="left" colspan="3">
								  				<input type="text" id="folioTemp" name="folioTemp"size="22" maxlength="22" onkeyup = "this.value=this.value.toUpperCase()" onkeypress="return jsvalidarAlfaNumerico(event);">
								  				<label id="labelNuFolio"></label>
								  			</td>
					  					</tr>
					  					<tr class="par">
											<td align="left" colspan="4">&nbsp;</td>	
										</tr>
					  					<tr class="par">
											<td colspan="4" align="center">
												<a href="#" id="btnBuscar">
													<span class="boton">Buscar</span>
												</a>
												<a href="#" id="btnLimpiar">
													<span class="boton">Limpiar</span>
												</a>
											</td>
										</tr>
										<tr class="impar">
											<td align="left" colspan="4">&nbsp;</td>	
										</tr>
	  								</table>
					  			</td>
					  		</tr>
						</table>
					</form>
				</div>
			</div>
					
			<div id="invitacionGuardarIni" style="overflow:auto; width: 950px; height: 300px ">
				<jsp:include page="invitacionCuerpo.jsp" />
			</div>	
			
			<div id="invitacionButtons">
				<jsp:include page="invitacionButtons.jsp"/>
			</div>		
					
		</div>
		
		
		 <div id="invitacionGuardarIni">
			<jsp:include page="invitacionGuardar.jsp" />
		</div>		
		
		 <div id="confirmarInvitacion">
			<jsp:include page="confirmarInvitacion.jsp" />
		</div>	