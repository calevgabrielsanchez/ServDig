<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>


<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
<form:form modelAttribute="dlcUsuario"
	action="${contextpath}/loginFuncionario/validarCredencialesFuncionario.do" method="post"
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
									<td width="792" bgcolor="#FFFFFF"><img
										src="<%=request.getContextPath()%>/resources/images/login/logoImss.gif"
										width="379" height="62"></td>
								</tr>
								<tr>
									<td><img
										src="<%=request.getContextPath()%>/resources/images/login/portada_corrnet.jpg"
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
																	path="nomUsuarioSistema" cssClass="required" />
															</td>
														</tr>

														<tr valign="top">

															<td align="left" width="100px">Usuario:</td>
															<td align="left" width="100px"><form:input
																	path="nomUsuarioSistema" id="nomUsuarioSistema"
																	size="20" maxlength="11" /></td>
														</tr>

														<tr valign="top">

															<td align="left" width="100px"><form:label
																	for="refPassword" id="desEtiquetaLabel"
																	path="refPassword" cssErrorClass="error">Contrase&ntilde;a: </form:label>
															</td>
															<td align="left" width="100px"><form:password
																	path="refPassword" id="refPassword" size="20"
																	maxlength="11" /> <form:errors path="refPassword" />
															</td>
														</tr>
														<tr align="center">
															<td align="center" colspan="2"><input type="submit"
																name="validar" value="Iniciar Sesi&oacute;n" class="boton" /></td>
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
