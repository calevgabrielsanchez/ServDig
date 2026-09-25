<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<div id="dgPromocionCancelacio"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
	<div id="wrapperDialogCancelacion" style="background-color: #f2fff2;">
		<form  method="post" id="promocionCancelacionForm" action="">
			<input type="hidden" name="cvePromocion" id="cvePromocion"/>
			<table  style="width: 900px">
		        <tr valign="middle">
		               <td align="center" width="900px">  
		                  <table class="tablaverde2" style="width: 900px" >
		                    <thead>
		                        <tr><td colspan="4">Motivo de la Cancelaci&oacute;n</td></tr>
		                    </thead>
		                    <tbody>
		                        <tr valign="top" class="impar"><td align="left" colspan="4">&nbsp;</td></tr>
		                        <tr valign="top" class="par">
		                        	<td align="left" width="20%" class="etiqueta2">
		                        		<span class="required">*</span> Oficio de Cancelacion:</td>
									<td align="left" width="20%"><input type="text"
										name="nuVolanteCancela" id="nuVolanteCancela"
										onkeyup="validaCampo('PermiteSoloNumeros','nuVolanteCancela','promocionCancelacionForm')"
										size="15" maxlength="10"/>
										<div id="nuVolanteCancelaError"></div>
									</td>
									<td align="left" width="200px" class="etiqueta2"><span
										class="required">*</span>Fecha de Cancelaci&oacute;n</td>
									<td align="left" width="100px"><input
										name="fechaCancelacion" id="fechaCancelacion"
										onchange="validafechaSistema('promocionCancelacionForm','fechaCancelacion','Fecha de Cancelaci&oacute;n')" 
										readonly="readonly"/>																			
										<div id="fechaCancelacionError"></div>
									</td>
								<tr>
								<tr valign="top" class="par">
		                            <td align="left" width="200px" class="etiqueta2" colspan="1">
										<span class="required">*</span>Motivo de Cancelaci&oacute;n: </label>
		                            </td>
		                            <td align="left" colspan="3">
										<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatmotivocancelacion"
														 idHtml="idMotivoCancelacion"
														 idHtmlContenedor="promocionCancelacionForm"/>				                        
								  	</td>
		                        </tr>		
		                        <tr valign="top" class="par">
		                            <td align="left" width="200px" class="etiqueta2" colspan="1">
										<label>Funcionario:</label>
		                            </td>
		                            <td align="left" colspan="2">
										<input type="text" readonly="readonly" id="labelFuncionario" size="50">			                        
								  	</td>
		                        </tr>                       		                       
		                        <tr valign="top" class="impar"><td align="left" colspan="4">&nbsp;</td></tr>
		                    </tbody>
		                </table>
		            </td>
		        </tr>
		    </table>
		</form>
	</div>
</div>