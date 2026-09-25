<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/denuncia/navegadorUtils.js"></script>


<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
<form:form modelAttribute="dlcUsuarioFuncionario"
	action="${contextpath}/login/validarCredenciales.do" method="post"
	id="LoginForm">

	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		bgcolor="#FFFFFF">
		<tr>
			<td align="center" valign="top">
				<table width="584" border="0" align="center" cellpadding="0"
					cellspacing="0">

					<tr>
						<td width="11"
							background="<%=request.getContextPath()%>/resources/images/login/sdwLeft.jpg">&nbsp;</td>
						<td><table width="566" border="0" align="center"
								cellpadding="0" cellspacing="0">
								<tr>
									<td width="792" bgcolor="#FFFFFF" align="left"><img
										src="<%=request.getContextPath()%>/resources/plantilla_aplicacion_final/images/sistema_r2_c2.jpg"
										></td>
								</tr>
								<tr>
									<td><img
										src="<%=request.getContextPath()%>/resources/images/login/portada_corrnet_DL_V2.jpg"
										width="566" height="329"></td>
								</tr>
							</table>
							<table width="566" height="70" border="0" align="center"
								cellpadding="4" cellspacing="0" bgcolor="#FFFFFF">
								<tr>
									<td colspan="4" class="body">
										<table style="width: 100%" align="center">
											<tr valign="middle">
												<td align="center" width="900px">

													<table class="tablaverde2" style="width: 100%">
														<tr valign="top">
															<td align="left" colspan="2">&nbsp; <form:errors
																	path="dlcUsuario.nomUsuarioSistema" cssClass="required" />
															</td>
														</tr>

														<tr valign="top">

															<td align="left" width="100px">Usuario:</td>
															<td align="left" width="100px"><form:input
																	path="dlcUsuario.nomUsuarioSistema" id="nomUsuarioSistema"
																	size="20" maxlength="11" /></td>
														</tr>

														<tr valign="top">

															<td align="left" width="100px"><form:label
																	for="dlcUsuario.refPassword" id="desEtiquetaLabel"
																	path="dlcUsuario.refPassword" cssErrorClass="error">Contrase&ntilde;a: </form:label>
															</td>
															<td align="left" width="100px"><form:password
																	path="dlcUsuario.refPassword" id="refPassword" size="20"
																	maxlength="11" /> <form:errors path="dlcUsuario.refPassword" />
															</td>
														</tr>
														<tr align="center">
															<td align="center" colspan="2"><input type="submit"
																name="validar" value="Iniciar Sesi&oacute;n" class="boton" id="botonSubmit"/></td>
														</tr>

													</table>
												</td>
											</tr>
										</table>

									</td>
								</tr>
							</table></td>
						<td width="11"
							background="<%=request.getContextPath()%>/resources/images/login/sdwRight.jpg"></td>
					</tr>
					<tr>
						<td colspan="3" height="20">&nbsp;</td>
					</tr>
				</table>
		</tr>
	</table>

</form:form>
