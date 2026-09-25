<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<div id="dgGrupoCategoriaRegistro">
    <div id="wrapperDialogRegistro">	
		<form:form modelAttribute="crcGrupoCategoria" action="/catalogo/grupoCategoria/agregar.do" method="post" id="grupoCategoriaFormRegistro">
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
											<form:input	path="folioCorreccion" value="" label="Folio Corrección" maxlength="20" id="folioRegistro"/>
											<label id="labelFolioCorreccion"></label>
										</td>
									</tr>
									<tr>
										<td>
											<span class="required">*</span>
											<form:label id="txGrupoCategoriaLabel" for="txGrupoCategoria" path="txGrupoCategoria" cssErrorClass="error"> 
												Grupo Categor&iacute;a:
											</form:label>
										</td>
										<td>
											<form:input	path="txGrupoCategoria" value=""  maxlength="50" id="txGrupoCategoria"  
												onchange="mayusculasTextField(this);" onkeyup="mayusculasTextField(this);"/>
											<label id="labelGrupoCategoriaRegistro"></label>
										</td>
									</tr>										
								</tbody>
							</table>
						</td>
					</tr>
				</table>				
				<form:hidden path="cveGrupoCategoria" />
	  			<form:hidden path="cveSolicitudCorr" />
			</fieldset>
		</form:form>							    
   	</div>
</div>