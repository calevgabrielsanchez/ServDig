<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>			    
		    <div id="dgGastosRegistro"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogRegistro" style="background-color: #f2fff2;">	
					<form:form modelAttribute="crcGastos" action="/catalogo/gastos/agregar.do"
						method="post" id="gastosFormRegistro">
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
											<td align="left" width="120px" colspan="1" class="etiqueta2">
												<label style="color: red;">* </label>
												<form:label id="folioCorreccionLabel" for="folioCorreccion" path="folioCorreccion"
													cssErrorClass="error"> Folio Correcci&oacute;n: </form:label>
											</td>
											<td align="left" width="780px" colspan="3" >
											<form:input	path="folioCorreccion" value="" label="Folio Corrección" maxlength="20" id="folioRegistro"
												onkeyup="validaCampo('noCaracteresEspeciales','folioRegistro','gastosFormRegistro');"
											/>
											<label id="labelFolioRegistro"></label>
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="120px" colspan="1" class="etiqueta2" >
												<label style="color: red;">* </label>
												<form:label id="txGastoLabel" for="txGasto" path="txGasto"
													cssErrorClass="error"> Gastos: </form:label>
											</td>
											<td align="left" width="780px" colspan="3">
											<form:input	path="txGasto" value="" label="Gastos" maxlength="50" id="gastoRegistro"
												onkeyup="validaCampo('noCaracteresEspeciales','gastoRegistro','gastosFormRegistro');"
											/>
											<label id="labelGastoRegistro"></label>
											</td>
										</tr>
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
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