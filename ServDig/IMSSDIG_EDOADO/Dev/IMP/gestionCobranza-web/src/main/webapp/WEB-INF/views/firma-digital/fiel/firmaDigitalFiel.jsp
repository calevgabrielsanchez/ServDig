<%@ include file="../../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()    %>" />

<style type="text/css">
select,textarea,input[type="text"],input[type="password"],input[type="datetime"],input[type="datetime-local"],input[type="date"],input[type="month"],input[type="time"],input[type="week"],input[type="number"],input[type="email"],input[type="url"],input[type="search"],input[type="tel"],input[type="color"],.uneditable-input
	{
	height: auto;
}

.form-horizontal .control-label {
	width: auto;
	margin-left: 50px;
}

.form-horizontal .controls {
	margin-left: 160px;
}

input[disabled], select[disabled], textarea[disabled], input[readonly], select[readonly], textarea[readonly] {
    background-color: #E1E1E1;
}

</style>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/firma-digital/fiel/firma-fiel.js" htmlEscape="true" />"></script>

<div>
	<div class="row">
		<div class="cell">
			<c:choose>
				<c:when test="${not empty firmaElectronicaFIEL.errorFormGeneral}">
					<div class="alert alert-danger" style="width: 90%; margin: 0 auto;">
						<button type="button" class="close" data-dismiss="alert">�</button>
						<strong>Error: </strong>${firmaElectronicaFIEL.errorFormGeneral}
					</div>
					<br>
					<div style="float: right;">
						<input type="button" value="Aceptar" onclick="cancelar();" class="btn btn-default" />
					</div>
				</c:when>
				<c:otherwise>
					<fieldset>
						<legend>
							<strong>&nbsp;Firma Digital con FIEL&nbsp;</strong>
						</legend>

						<!-- Terminos y condiciones -->
						<p>
							<strong>Carta de T&eacute;rminos y Condiciones para la
								utilizaci&oacute;n de la Firma Electr&oacute;nica Avanzada
								(FIEL).</strong>
						</p>

						<p>
							<strong>T&eacute;rminos y Condiciones para la
								utilizaci&oacute;n de la Firma Electr&oacute;nica Avanzada
								(FIEL), expedida por el Servicio de Administraci&oacute;n
								Tributaria (SAT) en tr&aacute;mites ante el Instituto Mexicano
								del Seguro Social.</strong>
						</p>


						<p>Para la autorizaci&oacute;n de la utilizaci&oacute;n de la
							Firma Electr&oacute;nica Avanzada (en adelante FIEL) y
							Certificado Digital expedidos por el SAT, en los tr&aacute;mites
							electr&oacute;nicos o actuaciones electr&oacute;nicas que se
							requieran para cumplir con las obligaciones y ejercer sus
							derechos derivados de la Ley del Seguro Social y de los
							Reglamentos y disposiciones que de ella emanen, el interesado
							debe firmar la Carta T&eacute;rminos y Condiciones para la
							utilizaci&oacute;n de la Firma Electr&oacute;nica Avanzada
							(FIEL), expedida por el Servicio de Administraci&oacute;n
							Tributaria (SAT) en tr&aacute;mites ante el Instituto Mexicano
							del Seguro Social de conformidad con la normatividad aplicable.</p>


						<p>En la cual, debe manifestar bajo protesta de decir verdad,
							lo siguiente:</p>

						<ul>
							<li>
								<p>Que conoce la normatividad expedida por Instituto
									aplicable para la utilizaci&oacute;n de la FIEL y su
									correspondiente Certificado Digital, expedidos por el SAT, en
									los tr&aacute;mites electr&oacute;nicos o actuaciones
									electr&oacute;nicas ante el Instituto</p>
							</li>
							<li>
								<p>Que en cumplimiento de la normatividad vigente ha
									gestionado y obtenido del SAT la FIEL y su correspondiente
									Certificado Digital vigente de persona f&iacute;sica, y se
									encuentra en aptitud de utilizarlos en los tr&aacute;mites
									electr&oacute;nicos y actuaciones electr&oacute;nicas ante el
									Instituto.</p>
							</li>
							<li>
								<p>Que es su voluntad utilizar su FIEL y su correspondiente
									Certificado Digital emitidos por el SAT, en los tr&aacute;mites
									electr&oacute;nicos y actuaciones electr&oacute;nicas que
									as&iacute; sea procedente ante el Instituto Mexicano del Seguro
									Social, de conformidad con la normatividad y disposiciones
									legales aplicables.</p>
							</li>
							<li>
								<p>Que tanto su persona como su FIEL y su correspondiente
									Certificado Digital cubren el total de los requisitos
									requeridos por la normatividad aplicable.</p>
							</li>
						</ul>

						<p>Y en la cual, debe aceptar los siguientes:</p>

						<p style="text-align: center;">
							<strong>T&eacute;rminos y Condiciones</strong>
						</p>

						<ul>
							<li>
								<p>Para la utilizaci&oacute;n de la FIEL y su
									correspondiente Certificado Digital, emitida por el SAT, en los
									tr&aacute;mites electr&oacute;nicos o actuaciones
									electr&oacute;nicas ante el Instituto, adem&aacute;s de la
									normatividad expedida por el SAT, ser&aacute; aplicable la
									normatividad expedida por el Instituto al efecto as&iacute;
									como la relativa la Asignaci&oacute;n del N&uacute;mero
									Patronal de Identificaci&oacute;n Electr&oacute;nica y
									Certificado Digital asignado por el IMSS.</p>
							</li>
							<li>
								<p>El Titular de la FIEL es responsable de su uso adecuado
									ante el Instituto, en caso contrario se har&aacute; merecedor a
									las sanciones contenidas en la normatividad aplicable, estando
									considerada entre ellas la revocaci&oacute;n de la
									autorizaci&oacute;n para el uso de la FIEL y su Certificado
									Digital por parte del Instituto.</p>
							</li>
							<li>
								<p>En caso de que se presente una controversia legal entre
									el Titular de la FIEL y el Instituto, las partes se
									someter&aacute;n a la competencia de las autoridades y
									Tribunales Federales.</p>
							</li>
							<li>
								<p>Los Titulares de la FIEL que utilicen el intercambio de
									informaci&oacute;n por medios electr&oacute;nicos seguros,
									estar&aacute;n expresando su voluntad para que se utilice su
									FIEL y su correspondiente Certificado Digital de conformidad
									con la normatividad aplicable, en sustituci&oacute;n de la
									firma aut&oacute;grafa.</p>
							</li>
							<li>
								<p>Los Titulares de la FIEL aceptan y manifiestan su
									conformidad al realizar el intercambio de informaci&oacute;n a
									trav&eacute;s de medios electr&oacute;nicos para la
									recepci&oacute;n de notificaciones electr&oacute;nicas y se
									obligan a dar respuesta por la misma v&iacute;a al Instituto.
									Dichas promociones, producir&aacute;n los mismos efectos
									legales que los documentos con firma aut&oacute;grafa y en
									consecuencia tendr&aacute;n el mismo valor probatorio que las
									disposiciones aplicables les otorgan a &eacute;stos, siendo
									considerada como prueba la informaci&oacute;n contenida en los
									medios electr&oacute;nicos, &oacute;pticos, magneto
									&oacute;pticos o de cualquier otra tecnolog&iacute;a.</p>
							</li>
							<li>
								<p>El Titular de la FIEL deber&aacute; notificar al SAT la
									p&eacute;rdida, robo o destrucci&oacute;n del Certificado
									Digital correspondiente a su FIEL, para proceder a su
									inhabilitaci&oacute;n en t&eacute;rminos de las disposiciones
									aplicables.</p>
							</li>
							<li>
								<p>La presentaci&oacute;n de la notificaci&oacute;n por
									p&eacute;rdida, robo o destrucci&oacute;n a la que se refiere
									el p&aacute;rrafo anterior, no exime al Titular de la FIEL de
									cumplir con sus obligaciones legales de todos los actos
									realizados bajo el amparo de dicho Certificado Digital, los
									cuales gozar&aacute;n de absoluta validez hasta la
									presentaci&oacute;n de la notificaci&oacute;n correspondiente.</p>
							</li>
							<li>
								<p>Para la utilizaci&oacute;n de la FIEL y su Certificado
									Digital ante el Instituto, es necesario que el Titular de la
									FIEL concluya el procedimiento de autorizaci&oacute;n, a fin de
									que el Instituto corrobore con el SAT la validez, la vigencia
									del Certificado Digital de la FIEL y la correspondencia de la
									propiedad con la persona f&iacute;sica.</p>
							</li>
							<li>
								<p>Para llevar a cabo los tr&aacute;mites
									electr&oacute;nicos y actuaciones electr&oacute;nicas y obtener
									acceso a los sistemas de intercambio de informaci&oacute;n
									electr&oacute;nica del Instituto Mexicano del Seguro Social que
									as&iacute; lo requieran, el Titular de la FIEL debe utilizar
									los archivos de identificaci&oacute;n digital se&ntilde;alados en la
									normatividad aplicable a la FIEL.</p>
							</li>
							<li>
								<p>En los tr&aacute;mites electr&oacute;nicos y actuaciones
									electr&oacute;nicas, realizados con la FIEL amparada por un
									Certificado Digital vigente, la FIEL y su Certificado
									sustituyen la firma aut&oacute;grafa de su Titular y garantizan
									la integridad de los documentos, por lo que producen los mismos
									efectos que las leyes otorgan a los documentos firmados de
									manera aut&oacute;grafa por el Titular de la FIEL, teniendo el
									mismo valor probatorio.</p>
							</li>
						</ul>

						<br>

						<form action="validaFiel" name="validaFielForm"
							class="form-horizontal">

							<p style="text-align: center; font-size: small;">
								<strong>Datos de la Firma Electr&oacute;nica Avanzada
									(FIEL).</strong>
							</p>

							<div id="msgErrorDiv" class="alert alert-danger"
								style="margin: 15px auto; display: none; text-align: center;">
								<strong>Error: </strong><label id="msgError"
									style="display: inline;"></label>
							</div>
							
							<div class="control-group">
								<label class="control-label" for="rfcInput"
									style="font-size: smaller;">RFC:</label>
								<div class="controls">
									<input id="rfcInput" type="text" name="rfcInput"
										disabled="disabled" value="${firmaElectronicaFIEL.rfc}" />
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label" for="cerFile"
									style="font-size: smaller;">Certficado Digital:</label>
								<div class="controls">
									<input id="cerFile" type="text" size="45" name="cerFile" disabled="disabled"/> 
									<input id="cerFileButton" type="button" class="btn btn-sm btn-primary"
										onclick="selectTheFile(document.getElementById('cerFile'), 'Certificado digital', 'Certificado digital(*.cer)', 'cer', true)"
										value="Examinar ..." name="cerFileButton" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="keyFile"
									style="font-size: smaller;">Llave privada:</label>
								<div class="controls">
									<input id="keyFile" type="text" size="45" name="keyFile" disabled="disabled"/> 
									<input id="keyFileButton" type="button" class="btn btn-sm btn-primary"
										onclick="selectTheFile(document.getElementById('keyFile'), 'Llave Privada', 'Llave Privada(*.key)', 'key', true)"
										value="Examinar ..." name="keyFileButton">
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="keyPwd"
									style="font-size: smaller;">Contrase&ntilde;a:</label>
								<div class="controls">
									<input id="keyPwd" type="password" name="keyPwd" />
								</div>
							</div>
							
							<c:if test="${firmaElectronicaFIEL.firmarArchivo eq true }">							
								<div class="control-group">
									<label class="control-label" for="FILE_NAME"
										style="font-size: smaller;">Archivo:</label>
									<div class="controls">
										<input id="FILE_NAME" type="text" size="45" name="FILE_NAME" disabled="disabled">
										<input id="file2SignButton" type="button" class="btn btn-sm btn-primary"
											onclick="selectTheFile(document.getElementById('FILE_NAME'), 'Archivo a firmar', 'Todos los archivos(*.*)', '*', true)"
											value="Examinar ..." name="file2SignButton">
									</div>
								</div>
							</c:if>
						</form>

						<div style="float: right;">
							<input type="button" value="Firmar" id="btnFirmar"
								class="btn btn-default" /> <input type="button"
								value="Cancelar" onclick="cancelar();" class="btn btn-default" />
						</div>
					</fieldset>

					<form:form modelAttribute="firmaElectronicaFIEL" id="procesaFirmaForm" action="${contextpath}/firma-digital/procesa-firma-FIEL">
						<form:hidden path="rfc" id="rfc"/>
						<form:hidden path="cadenaOriginal" id="cadenaOriginal"/>
						<form:hidden path="firmarArchivo" id="firmarArchivo"/>
						<form:hidden path="fileNameToSign" id="fileNameToSign"/>
						<form:hidden path="sPKCS7" id="sPKCS7"/>
					</form:form>

					<!-- DEFINICION DEL APPLET REMOTO -->
					<!-- IDSE
					<APPLET name="applet_ssign"
						codebase="http://11.254.14.212/applets/"
						archive="seguriSignClient-1.5.jar"
						code="seguriSign_Client.seguriSign_Client.class" width="0"
						height="0" align="middle"> El Applet no pudo ser
						cargado. </APPLET>
					 -->
					 
					<!-- SEGURIDATA -->
					<applet id="seguriDataApplet" width="0" height="0"
						codebase="http://11.254.14.212/applets/"
						archive="SgSignSigner.jar"
						style="xdisplay: none; width: 0; height: 0; padding: 0; margin: 0;"
						mayscript="false" scriptable="false" name="seguriDataApplet"
						code="seguridata.segurisign.Signer"> El Applet no pudo
						ser cargado.</applet>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>