<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<html lang="sp">

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/detBaseCotOmitida/detBaseCotOmitida.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>

<div id="cuerpo">
	
	<div class="menu_principal" style="height: 2em !important;"> 
		<!--inicia menu principal-->
		<div align="center">
			<div class="centrado">
			    <ul style="height: 1em !important;">
			    	<li><a> Determinaci&oacute;n de la Base de Cotizaci&oacute;n Omitida</a></li>
			  	</ul>
			</div>
		<!--fin centrado--> 
	  </div>
	</div>
	
	<div id="dgBusqueda" title="Determinaci&oacute;n de la base de cotizaci&oacute; omitida">
		<div id="wrapperDialogBusqueda" style="background-color: #f2fff2;">	
			
				<fieldset>
				<input type = "hidden" id ="tpSeleccion" name="tpSeleccion"/>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>	
										</tr>
										<tr valign="top" class="par">
											<td align="right" width="50%" colspan="2" class="etiqueta2">
												<label style="color: red;">* </label>
												<label>Folio de Correcci&oacute;n  </label>
											</td>
											<td align="left" colspan="2" width="50%">
												<input type="text" id="folioCorreccionMain"  onkeypress="mayusculasTextField(this)"  maxlength="20" class="inputFolio"/>
												<a id="btnValidarDetBase" href="#" onclick="javascript:validar();"><span class="boton">Validar</span></a>
												<label id="labelFolioMain"></label>
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>	
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="20%" class="etiqueta2">
												<label style="color: red;">* </label>
												<label>Registro Patronal</label>
											</td>
											<td align="left"  width="30%">
												<select id="selectRegPatron" onchange="jsLlenaEjercicio(this.value);"><option id="-1">--Por favor seleccione--</option></select>
												<label id="labelRegPatronalMain"></label>
											</td>
											<td align="left" width="20%" class="etiqueta2">
												<label style="color: red;">* </label>
												<label>Ejercicio</label>
											</td>
											<td align="left" width="30%">
												<select id="selectEjercicio"><option id="-1">--Por favor seleccione--</option></select>
												<label id="labelEjercicioMain"></label>
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>	
										</tr>
										<tr class="par" height="10%">
											<td colspan="4" width="80%" align="center">
												<a href="#" id="btnAgregar"><span class="boton">Agregar</span></a>
											</td>	
											<!-- <td colspan="2" width="50%">
												<a href="#" id="buscar"><span class="boton">Buscar</span></a>
											</td>	 -->
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
			
		</div>
	</div>
	
	
	
	<div id="detBaseCotOmitidaBaseMayorJSP">
		<jsp:include page="detBaseCotOmitidaBaseMayor.jsp" />
	</div>
	
</div>