<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<div id="dgDeteccionCancelacio" title=" Cancelar Detecci&oacute;n" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
	<div id="wrapperDialogCancelacion" style="background-color: #f2fff2;">
		<form:form  method="post" id="deteccionCancelacionForm" action="/catalogo/deteccion/cancelacion.do" modelAttribute="crtDeteccion">
			<form:hidden path="cveDeteccion" id="cveDeteccion"/>
			<table  style="width: 900px">
		        <tr valign="middle">
		               <td align="center" width="900px">  
		                  <table class="tablaverde2" style="width: 900px" >
		                    <thead>
		                        <tr><td colspan="2">Motivo de la Cancelaci&oacute;n</td></tr>
		                    </thead>
		                    <tbody>
		                        <tr valign="top" class="impar"><td align="left" colspan="2">&nbsp;</td></tr>
		                        <tr valign="top" class="par">
		                            <td align="right" width="200px">
		                                <label> Motivo de Cancelaci&oacute;n: </label>
		                            </td>
		                            <td align="left">
										<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatmotivocancelacion"
														 idHtml="idMotivocancelacion"
														 idHtmlContenedor="deteccionCancelacionForm"/>				                        
								  	</td>
		                        </tr>		                       		                       
		                        <tr valign="top" class="impar"><td align="left" colspan="2">&nbsp;</td></tr>
		                    </tbody>
		                </table>
		            </td>
		        </tr>
		    </table>
		</form:form>
	</div>
</div>