<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<table class="tablaverde2" width="100%">
		<tr>
			<td colspan="4">
				<form:form id="autAviDictamenGenericoTabForm" method="Post" modelAttribute="seguimientoGenericoVO" action="seguimiento/generico/guardaAvisoDictamen.do" >
					<form:hidden id="cvePromocion" path="cvePromocion"/>
					<form:hidden path="nombreFuncionarioRegistra" name="hidFuncionarioRegistra" id="hidFuncionarioRegistra"/>
						<input type="hidden" id="fechaEmisionOficioDictamenGenerico" >
						<input type="hidden" id="fechaNotificacionOficioDictamenGenerico" >											
					<table class="tablaverde2" style="width: 100%">	
					 	<tr valign="top" class="impar">
							<td align="left" colspan="4">&nbsp;</td>
						</tr>
						<tr class="par">
							<td align="left" width="20%" class="etiqueta2">
								<span class="required">* </span><label>Fecha de Aut. de aviso de Dict : </label>
							</td> 
							<td align="left" width="30%" >
								<form:input path="avisoDictamenGenericoTabVO.fecAutAvisoDictamen" id="fecAvisoDictGenericoTab" readonly="readOnly" onchange="validaFechaDictamenGenerico()" />
								<span class="boton_limpiar" onclick="javascript:limpiaFechaAutorizacionAviso()" id="spnFecAvisoDictGenericoTab">X</span>
							<div id="labelFecAvisoDictGenericoTab"></div>
							</td>		
							<td align="left" width="20%" colspan="2" class="etiqueta2">
								<label>Periodo a dictaminar</label>
							</td>				
						</tr>
						<tr class="par">
							<td align="left" width="20%" class="etiqueta2">
								<span class="required">* </span><label>N&uacute;mero de aviso de Dictamen : </label>
							</td> 
							<td align="left" width="30%">
								<form:input path="avisoDictamenGenericoTabVO.numAvisoDictamen" size="20" maxlength="12" id="numAvisoDictGenericoTab" onkeyup="validaCampo('noCaracteresEspeciales','numAvisoDictGenericoTab','autAviDictamenGenericoTabForm');"/>
								<div id="labelnumAvisoDictGenericoTab"></div>
							</td>
							<td colspan="2" align="left">
							<span class="required">* </span>
							<span class="etiqueta2">Del : </span>
							<form:input size="12" readonly="true" path="avisoDictamenGenericoTabVO.fecIniDictamen" id="fecIniPeriodoDictGenericoTab" name="fecIniPeriodoDictGenericoTab" onchange="javaScript:validaFechasPeriodoDicGT(this.id)" />
							<span class="etiqueta2">Al : </span>
							<form:input size="12" readonly="true" path="avisoDictamenGenericoTabVO.fecFinDictamen" id="fecFinPeriodoDictGenericoTab" name="fecFinPeriodoDictGenericoTab" onchange="javaScript:validaFechasPeriodoDicGT(this.id)" />
							<span class="boton_limpiar" onclick="javascript:limpiaFechasPeriodoDictamenGenerico()" id="spnFecPerDictGenericoTab">X</span>
							<div id="labelFecPeriodoAvisoDictGenericoTab"></div>
							</td>
					</tr>
					<tr class="par">
						<td align="left" width="20%" class="etiqueta2">
							<label>Funcionario que registra : </label>
						</td> 
						<td align="left" width="30%" colspan="2">							
							<label id="labelFuncionarioRegistraADGT"></label>
						</td>
						<td align="left" width="50%">
							&nbsp;
						</td>
					</tr>
					<tr valign="top" class="par">
				    	<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr valign="top" class="par">
				    	<td align="center" colspan="4">
							<input type="button" class="boton" value="Guardar" id="btnGuardardictamenGenericoTab" width="10px" height="12px" class="boton" onclick="javascript:procesaFormularioDictamenGenerico('validaCamposDictamenGenerico()');" >
						</td>
					</tr>
					<tr valign="top" class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
				</table>
					<!-- Funcion generica extra para completar flujo de cancelacion -->
						<input  type="hidden"  id="functionAuxAutAvisoDict"/>
				</form:form>
			</td>
		</tr>
	</table>