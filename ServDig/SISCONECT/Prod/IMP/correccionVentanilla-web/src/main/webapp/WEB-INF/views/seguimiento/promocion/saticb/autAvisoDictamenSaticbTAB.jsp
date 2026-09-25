<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<table class="tablaverde2">
		<tr>
			<td colspan="4">
				<form:form id="autAviDictamenSaticbForm" method="Post" modelAttribute="seguimientoSaticbVO" >
					<form:hidden id="cvePromocion" path="cvePromocion"/>
					<table class="tablaverde2" style="width: 800px">		
						<tr class="par">
							<td align="left" class="etiqueta2" width="20%">
								<span class="required">* </span><label>Fecha de Aut. de aviso de Dict : </label>
							</td> 
							<td align="left" width="30%" colspan="1">
								<form:input path="segAvisoDictamenVo.fecAutAvisoDictamen" id="fecAvisoDictSaticb" readonly="readOnly"/>
								<span class="boton_limpiar" onclick="" id="spnFecAutSaticb">X</span>
							<div id="labelFecAutDictamenSaticb"></div>
							</td>		
							<td align="left" class="etiqueta2" width="20%" colspan="2">
								<label>Ejercicio a dictaminar</label>
							</td>				
						</tr>
						<tr class="par">
							<td align="left" class="etiqueta2" width="20%">
								<span class="required">* </span><label>N&uacute;mero de aviso de Dictamen : </label>
							</td> 
							<td align="left" width="30%">
								<form:input path="segAvisoDictamenVo.numAviso" size="20" id="numAvisoDictSaticB" maxlength="20"/>
								<div id="labelnumAvisoDictSaticB"></div>
							</td>
							<td colspan="2" align="left" class="etiqueta2">
								<!-- form:options  id="cmboEjerDictSaticb"/-->
							</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label>Funcionario que registra : </label>
						</td> 
						<td align="left" width="30%">
							<form:input path="segAvisoDictamenVo.funcionarioRegistraAutDict" size="45" readonly="readonly" disabled="disabled" id="funcionarioRegSaticb"/>
						</td>
						<td align="left" colspan="2" width="50%">
							&nbsp;
						</td>
					</tr>
					<tr valign="top" class="par">
				    	<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr valign="top" class="par">
				    	<td align="center" colspan="4">
							<input type="button" value="Guardar" id="btnGuardarAutSaticb" width="10px" height="12px" class="boton" onclick="" >
						</td>
					</tr>
				</table>
				</form:form>
			</td>
		</tr>
	</table>