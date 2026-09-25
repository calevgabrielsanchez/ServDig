<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<table class="tablaverde2" width="100%" id="tablaFiscalizacion">
	<tr>
		<td colspan="4" width="100%">
			<form:form id="derivarFiscalizacionTABForm" method="post"  modelAttribute="seguimientoGenericoVO" action="seguimiento/generico/fiscalizacion.do" >
				<form:hidden  path="cvePromocion" id="cvePromocion"/>
				<form:hidden  path="fechaNotificacionOficio" id="fechaNotificacionOficioFiscalizacion"/>
				<form:hidden path="nombreFuncionarioRegistra" id="funcionarioDerFiscaSaticb" />
	                <table class="tablaverde2" width="100%" >
	                <tr class="impar">
						<td align="left" class="etiqueta2" width="20%" colspan="4">
								&nbsp;
							</td> 
					</tr>
	                 <tr valign="top" class="par">
						    	<td align="center" colspan="4">
									&nbsp;
								</td>
							</tr>
	                    <tr valign="top" class="par">
	                    
		                   	<td align="left" width="20%" class="etiqueta2">
	    	               		<span class="required">*</span> Referencia :
	    	               	</td>
								<td align="left" width="30%" >
									<form:input path="segDerivaFiscaTabVo.referenciaDerFisca" id="referenciaFiscalizacionGen" 
									onkeyup="validaCampo('noCaracteresEspeciales','referenciaFiscalizacionGen','derivarFiscalizacionTABForm');jsBorraLabelFiscalizacion();"
									size="40" maxlength="50" />
									<div id="labelreferenciaFiscalizaGen"></div>
								</td>
								<td align="right" width="20%" class="etiqueta2">
									<label><span class="required">*</span>Fecha de la Derivaci&oacute;n :</label>
								</td>
								<td align="left" width="30%" >
									<form:input path="segDerivaFiscaTabVo.fecDerivacionGenerica" readonly="true" size="12" id="fecDerivacionGenerica" name="fecDerivacionGenerica" 
										onchange="javaScript:jsValidaFecDerivacion(this.value);"/>
	                            	<span class="boton_limpiar" onclick="limpiaFechaDerivacion()" id="spnFecDerFisc">X</span>
	                            	<div id="labelfecDerivacionGenrico"></div>
								</td>
							<tr>
							<tr valign="top" class="par" class="etiqueta2">
	                            <td align="left" width="20%" class="etiqueta2" colspan="1">
									Funcionario : 
								</td>
	                            <td align="left" colspan="2" width="50%">
	                            <label id="lblFuncionarioDerFiscaSaticb" > </label>
	                            	<!-- form:input path="nombreFuncionarioRegistra" id="funcionarioDerFiscaSaticb" size="50" readonly="true" / -->
	                            </td>
	                            <td align="left" width="30%">
							  	</td>
	                        </tr>		
	                                            		                       
	                        <tr valign="top" class="par"><td align="left" colspan="4">&nbsp;</td></tr>
	                        <tr valign="top" class="par">
						    	<td align="center" colspan="4">
									
									<input type="button" value="Confirmar" id="btnConfirmarDerivar" width="9px" height="9px" class="boton" onclick="procesaFormularioFiscalizacion()" >

								</td>
							</tr>
							 <tr valign="top" class="par">
						    	<td align="center" colspan="4">
									&nbsp;
								</td>
							</tr>
							<tr class="impar">
								<td align="left" class="etiqueta2" width="20%" colspan="4">
								&nbsp;
								</td> 
							</tr>
		       		</table>
		       		<input  type="hidden"  id="functionAuxFiscalizacion"/>
	       		</form:form>
            </td>
        </tr>
    </table>
