<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@page import="mx.gob.imss.ctirss.correccion.session.UserSession"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="sp">

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/solicitud/FirmaDigital.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/solicitudProrroga/prorroga.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>

<div id="contenedorFirmaProrroga"></div>
<div id="cuerpo">
	<div class="separadorseccion">
		<span>
			Solicitud de Pr&oacute;rroga para la Presentaci&oacute;n de la Correcci&oacute;n Patronal
		</span>
	</div>
	<div id="dgPercepcion">
		<div id="wrapperDialogPercepcion">
			<form:form modelAttribute="crtAnexosolcorrpat" action="prorroga/muestraReporte.do" method="post" id="prorrogaForm">
				<fieldset>
					<table>
						<tr>
							<td>
								<table style="width: 960px">
									<tbody>
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td width="260px">
												<form:label for="nuFolio" id="nuFolioLabel" path="nuFolio" cssErrorClass="error">
													Folio de la Solicitud de Correcci&oacute;n:
												</form:label>
											</td>
											<td>
												<form:input path="nuFolio" id="nuFolioInput" size="20" maxlength="18"
													onclick="datosNumeroFolio();" onkeyup="validaCampo('noCaracteresEspeciales','nuFolioInput');"
													onblur="this.value=(this.value).toUpperCase();" /> 
												<form:errors path="nuFolio" />
											</td>
											<td>
												<form:label for="registroPatronal" id="registroPatronalLabel" path="registroPatronal" cssErrorClass="error">
													 Registro Patronal: 
												</form:label>
											</td>
											<td>
												<form:input path="registroPatronal" id="registroPatronalInput" size="20" maxlength="11" 
													onclick="datosRegPatronal();" onkeyup="validaCampo('noCaracteresEspeciales','registroPatronalInput');"
													onblur="this.value=(this.value).toUpperCase();" /> 
												<form:errors path="registroPatronal" />
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
										<tr align="center">
											<td align="center" colspan="4">
												<a id="btnBuscarProrroga" href="#" onclick="javascript:consultaGral()">
													<span class="btn btn-primary btn-sm">Buscar</span>
												</a>
											</td>
										</tr>
										<tr valign="top" class="impar">
											<td align="left" colspan="4">
												&nbsp;
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
			</form:form>
		</div>
	</div>
	<div id="prorrogaReg" style="width: 900px" align="center">
		<jsp:include page="datosProrroga.jsp" />
	</div>

	<table id="dtSolicitudCorreccion" style="width: 960px" class="table table-striped table-bordered">
		<thead>
		</thead>
		<tbody>
		</tbody>
	</table>

	<div id="dgProrrogaCaptura">
		<div id="wrapperDialogCapProrroga">
			<table width="810px" border="0px" align="center">
				<tr valign="middle">
					<td align="center" width="100px">
						<a href="#" onclick="javascript:porRegistroPat();">
							<span class="btn btn-primary btn-sm">Solicitar</span>
						</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<br>
</div>