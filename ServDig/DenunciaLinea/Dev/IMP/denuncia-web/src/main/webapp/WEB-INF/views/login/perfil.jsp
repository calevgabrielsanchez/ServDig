<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/resources/js/delta/funcionesComunes.js"></script>
<%-- 	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/denuncia/denunciaLinea.js"></script> --%>


    <form:form modelAttribute="dlcUsuarioFuncionario"
    action="seleccionarPeril.do" method="post" id="LoginForm">

    <table width="100%" border="0" cellpadding="0" cellspacing="0"
        bgcolor="#FFFFFF">
        <fieldset>
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
										src="<%=request.getContextPath()%>/resources/images/login/portada_corrnet_DL_perfil_V2.jpg"
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
                                                            <tbody>
                                                                <tr valign="top">
                                                                    <td align="left" colspan="2">&nbsp;</td>
                                                                </tr>

                                                                <tr valign="top">

                                                                    <td align="left" width="100px"><form:label
                                                                            for="idPerfil" id="idPerfilLbl" path="dlcUsuario.idPerfil"
                                                                            cssErrorClass="error">Perfil: </form:label></td>
                                                                    <td align="left" width="100px"><form:select
                                                                            path="dlcUsuario.idPerfil" id="idPerfil">
                                                                            <form:options
                                                                                items="${dlcUsuarioFuncionario.dlcUsuario.perfilesDisponibles}" />
                                                                        </form:select></td>
                                                                </tr>

                                                                </tr>
                                                                <tr align="center">
                                                                    <td align="center"  colspan="2"><input
                                                                        type="button" name="validar" value="Aceptar" onclick="setAndSubmitNewWindow('ingresar')"
                                                                        class="boton" /></td>
                                                                </tr>

                                                            </tbody>
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
        </fieldset>
    </table>

</form:form>
<form action="<%=request.getContextPath()%>/login/inicio.do"  method="get" id="frmReenviar" name="frmReenviar" commandName="dlcUsuario" ></form>