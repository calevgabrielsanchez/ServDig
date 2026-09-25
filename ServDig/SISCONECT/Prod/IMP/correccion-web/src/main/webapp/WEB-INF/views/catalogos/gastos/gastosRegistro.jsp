<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<div id="dgGastosRegistro">
    <div id="wrapperDialogRegistro">	
		<form:form modelAttribute="crcGastos" action="/catalogo/gastos/agregar.do" method="post" id="gastosFormRegistro">
			<fieldset>
				<table>
					<tr>
						<td>
							<table style="border-collapse: separate; border-spacing:  5px 5px;">
								<tbody>
									<tr>
										<td width="150px">
											<span class="required">*</span>
											<form:label id="folioCorreccionLabel" for="folioCorreccion" path="folioCorreccion" cssErrorClass="error">
												Folio Correcci&oacute;n:
											</form:label>
										</td>
										<td>
											<form:input	path="folioCorreccion" value="" label="Folio Corrección" maxlength="20" id="folioRegistro"
												onkeyup="validaCampo('noCaracteresEspeciales','folioRegistro','gastosFormRegistro');"/>
											<label id="labelFolioRegistro"></label>
										</td>
									</tr>
									<tr>
										<td>
											<span class="required">*</span>
											<form:label id="txGastoLabel" for="txGasto" path="txGasto" cssErrorClass="error">
												Gastos:
											</form:label>
										</td>
										<td>
											<form:input	path="txGasto" value="" label="Gastos" maxlength="50" id="gastoRegistro"
												onkeyup="validaCampo('noCaracteresEspeciales','gastoRegistro','gastosFormRegistro');"/>
											<label id="labelGastoRegistro"></label>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
				</table>
				<form:hidden path="cveGasto" />
	  			<form:hidden path="cveSolicitudCorr" />
			</fieldset>
		</form:form>							    
   	</div>
</div>