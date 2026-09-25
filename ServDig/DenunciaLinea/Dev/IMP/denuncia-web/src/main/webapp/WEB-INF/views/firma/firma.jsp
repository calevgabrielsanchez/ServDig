<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/firma/funcionesFirma.js"></script>

<div id="dgFirma" title="Firma"
	style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity =                         70) !important;">
	<div id="wrapperDialogRegistro" style="background-color: #f2fff2;">
		<APPLET name="applet_imss" mayscript codebase="../resources/applets/"
			archive="seguriSign_Client_imss.jar"
			code="SeguriSIGN_Client.SeguriSIGN_Client_Applet.class" width="0"
			height="0">
			<BR /> <B>[ Se requiere Java Plug-in 1.3 o superior para
				ejecutar el Applet ]</B><BR /> <A
				href="http://www.java.com/es/download/" target="_blank">Descargar</A><BR />
		</APPLET>
		<APPLET name="applet_sat" mayscript codebase="../resources/applets/"
			archive="seguriSign_Client_sat.jar"
			code="seguriSign_Client.seguriSign_Client.class" width="0" height="0">
			<BR /> <B>[ Se requiere Java Plug-in 1.3 o superior para
				ejecutar el Applet ]</B><BR /> <A
				href="http://www.java.com/es/download/" target="_blank">Descargar</A><BR />
		</APPLET>
		<form id="formaFirma" name="formaFirma">
			<table style="width: 900px" align="center">
				<tr valign="middle">
					<td align="center" width="900px">

						<table class="tablaverde2" style="width: 900px">
							<tbody>
								<tr valign="top" class="par">
									<td align="left" width="120px" colspan="1"><label
										style="color: red;">* </label> <label id="certificadoLbl">
											Certificado: </label></td>
									<td align="left" width="780px" colspan="3"><input
										type="file" id="certificado" /> <label id="certificadoMsg"></label></td>

								</tr>
								<tr valign="top" class="impar">
									<td align="left" width="120px" colspan="1"><label
										style="color: red;">* </label> <label id="contrasenaLbl">
											Contrase�a: </label></td>
									<td align="left" width="780px" colspan="3"><input
										type="password" maxlength="12" id="contrasena" /> <label
										id="contrasenaMsg"></label> <input id="tipoCert" type="hidden"
										value="<c:out value="${sessionScope.USR_SESSION.tipoCertificado}"/>" />
										<input id="noCert" type="hidden"
										value="<c:out value="${sessionScope.USR_SESSION.numeroSerialCertificadoPatron}"/>" />
									</td>
								</tr>
								<tr valign="top" class="par">
									<td align="left" width="120px" colspan="1"><label
										style="color: red;">* </label> <label id="llaveLbl">
											Llave SAT: </label></td>
									<td align="left" width="780px" colspan="3">
									<c:if test="${sessionScope.USR_SESSION.certificadoSAT}">
										<input type="file" id="llave" />
									</c:if> 
									<c:if test="${!sessionScope.USR_SESSION.certificadoSAT}">
										<input type="file" id="llave" disabled="disabled" />
									</c:if>
									<label id="llaveMsg"></label></td>

								</tr>
								<tr valign="top" class="par">
									<td align="left" colspan="4">&nbsp;</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>