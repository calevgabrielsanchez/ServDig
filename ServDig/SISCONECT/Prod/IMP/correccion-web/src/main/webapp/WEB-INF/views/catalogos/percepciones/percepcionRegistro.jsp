<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>			    
		    
<div id="dgPercepcionesRegistro">
    <div id="wrapperDialogRegistro">	
		<form:form modelAttribute="crcPercepciones" action="/catalogo/percepciones/agregar.do" method="post" id="percepcionesFormRegistro">
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
											onkeyup="validaCampo('noCaracteresEspeciales','folioRegistro','percepcionesFormRegistro');" />
										<label id="labelFolioRegistro"></label>
										</td>
									</tr>
									<tr>
										<td>
											<span class="required">*</span>
											<form:label id="txRemuneracionLabel" for="txRemuneracion" path="txRemuneracion" cssErrorClass="error">
												Percepci&oacute;n:
											</form:label>
										</td>
										<td>
										<form:input	path="txRemuneracion" value="" label="Percepci%oacute;n" maxlength="50" id="remuneracionRegistro"
											onkeyup="validaCampo('noCaracteresEspeciales','remuneracionRegistro','percepcionesFormRegistro');"/>
										<label id="labelRemuneraRegistro"></label>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
				</table>				
				<form:hidden path="cvePercepcion" />
				<form:hidden path="cveSolicitudCorr" />
			</fieldset>
		</form:form>							    
   	</div>
</div>