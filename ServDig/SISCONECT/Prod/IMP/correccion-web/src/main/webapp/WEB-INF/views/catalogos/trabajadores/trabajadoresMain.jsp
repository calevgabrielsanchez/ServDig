<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<style>
	label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
	label  {  float: none; }
</style>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="sp">

	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/catalogos/trabajadores/trabajadores.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/KeyPressed.js"></script>	
	
<div id="cuerpo">
		
	<div class="separadorseccion">
		<span>
			Gastos
		</span>
	</div>
		  
	<div id="dgTrabajadores">
		<div id="wrapperDialogTrabajadores">
			<table>
				<tr>
					<td>
						<form action="/catalogo/trabajadores/consultar.do" 	method="post" id="trabajadoresForm">
							<input type="hidden"  id="cveSubdelegcionOficialTrab" value="<%=request.getAttribute("valCveSubdel") %>"/>	
							<fieldset>
								<table style="border-collapse: separate; border-spacing: 5px 5px;">
      								<tbody>
								  		<tr>
								    		<td width="170px">
								   				<span class="required">*</span><label>Folio de Correcci&oacute;n:</label>
							   				</td>
								    		<td width="310px">
								    			<input type="text" id="folioCorreccion" name="folioCorreccion" size="20" maxlength="18" onblur="this.value=(this.value).toUpperCase();" 
											 			onkeyup="validaCampo('noCaracteresEspeciales','folioCorreccion','trabajadoresForm');"/>
									 			<label for="folioCorreccion"></label>
								 			</td>
										    <td width="170px">
										    	<label>Per&iacute;odo:</label>
										    </td>
										    <td>
										    	<input type="text" id="periodo" name="periodo" size="20" maxlength="4"  onkeyup="validaCampo('PermiteSoloNumeros','periodo','trabajadoresForm');"/>
										    </td>
									  	</tr>
									  	<tr>
									    	<td>
										    	<label>Nombre Asegurado:</label>
										    </td>
										    <td>
										    	<input type="text" id="nombreAsegurado" name="nombreAsegurado" onkeyup="validaCampo('PermiteSoloLetrasYPunto','nombreAsegurado','trabajadoresForm');" size="20" maxlength="30" />
										    </td>
										    <td>
										    	<label>Apellido Paterno:</label>
										    </td>
										    <td>
										    	<input type="text" id="apPaternoAsegurado" name="apPaternoAsegurado" onkeyup="validaCampo('PermiteSoloLetrasYPunto','apPaternoAsegurado','trabajadoresForm');" size="20" maxlength="30" />
										    </td>
									  	</tr>
									  	<tr>
								    		<td>
								    			<label>Apellido Materno:</label>
								    		</td>
										    <td>
										    	<input type="text" id="apMaternoAsegurado" name="apMaternoAsegurado" onkeyup="validaCampo('PermiteSoloLetrasYPunto','apMaternoAsegurado','trabajadoresForm');" size="20" maxlength="30" />
										    </td>
										    <td>
										    	<label>N&uacute;mero de Seguro Social:</label>
										    </td>
										    <td>
										    	<input  type="text" id="nuNss" name="nuNss" size="20" maxlength="11" onkeyup="validaCampo('PermiteSoloNumeros','nuNss','trabajadoresForm');"/>
									    	</td>
									  	</tr>
									  	<tr>
									    	<td>
									    		<label>RFC:</label>
									    	</td>
										    <td>
									      		<input type="text" id="txRfc" name="txRfc" maxlength="13" size="20" onkeyup="validaCampo('noCaracteresEspeciales','txRfc','trabajadoresForm');"/>
									    	</td>
										    <td>
										    	<label>Indicador:</label>
										    </td>
										    <td>
							     				<select id="indicadorTrabajador" name="indicadorTrabajador">
													<option value="-1">Seleccione</option>
										  			<option value="1">Prueba Selectiva</option>
										  			<option value="2">Salarios Topados</option>
										  			<option value="3">An&aacute;lisis Tiempo Extra</option>
										  			<option value="4">An&aacute;lisis Honorarios</option>
									  	  		</select>
									    	</td>
							  			</tr>
								  	</tbody>
								</table>
								<br/>
								<table width="960px" align="center">
							  		<tr align="center">
										<td>
											<a id="btnBusquedaTrabajadores" href="#" onclick="javascript:buscar();"><span class="btn btn-primary btn-sm">Buscar</span></a>
										</td>
										<td>
											<a href="#" onclick="javascript:registra();"><span class="btn btn-primary btn-sm">Agregar</span></a>
										</td>
								   </tr>
								</table>
								<br/>
							</fieldSet>
						</form>
					</td>
				</tr>
			</table>
		</div>
	</div>
		
	<div id="registroTrabajadores">
		<jsp:include page="trabajadoresRegistro.jsp" />
	</div>
	
	<div id="trabajadoresData">
		<jsp:include page="trabajadoresData.jsp" />
	</div>
	
	<div id="trabajadoresBorrar" align="center">
		<jsp:include page="trabajadoresBorrar.jsp" />
	</div>
	
	<div id="trabajadoresButtons">
		<jsp:include page="trabajadoresButtons.jsp" />
	</div>
</div>
