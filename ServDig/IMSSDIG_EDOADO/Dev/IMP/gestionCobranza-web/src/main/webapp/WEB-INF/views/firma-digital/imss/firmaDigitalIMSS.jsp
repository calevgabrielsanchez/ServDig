<%@ include file="../../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

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
	src="<spring:url value="/static/resources/js/delta/firma-digital/imss/firma-imss.js" htmlEscape="true" />"></script>

<div>
	<div class="row">
		<div class="cell">
			<c:choose>
				<c:when test="${not empty firmaElectronicaIMSS.errorFormGeneral}">
					<div class="alert alert-danger" style="width: 90%; margin: 0 auto;">
						<button type="button" class="close" data-dismiss="alert">�</button>
						<strong>Error: </strong>${firmaElectronicaIMSS.errorFormGeneral}
					</div>
					<br>
					<div style="float: right;">
						<input type="button" value="Aceptar" onclick="cancelar();"
							class="btn btn-default" />
					</div>
				</c:when>
				<c:otherwise>
					<fieldset>

						<legend>
							<strong>&nbsp;Firma Digital IMSS&nbsp;</strong>
						</legend>

						<!-- Terminos y condiciones -->
						<p>
							<strong>T&eacute;rminos y condiciones de la Solicitud</strong>
						</p>


						<p>
							He le&iacute;do y estoy de acuerdo con los <a href="#">Lineamientos
								Operativos del Programa. </a>
						</p>

						<ul>
							<li><p>El registro patronal por el que solicito el
									registro no corresponde a una entidad p&uacute;blica cuyas
									relaciones laborales se rijan por el apartado A del
									art&iacute;culo 123 de la Constituci&oacute;n Pol&iacute;tica
									de los Estados Unidos Mexicanos.</p></li>
							<li><p>Manifiesto bajo protesta de decir verdad que,
									conforme a los criterios de selecci&oacute;n del Programa,
									establecidos en sus Lineamientos, no me encuentro en ninguno de
									los supuestos establecidos en el art&iacute;culo 32-D del
									C&oacute;digo Fiscal de la Federaci&oacute;n, para acceder al
									otorgamiento de los subsidios. Se entender&aacute; que se
									cumple este requisito cuando el patr&oacute;n:</p></li>
							<li><p>Ha cumplido con sus obligaciones en materia de
									inscripci&oacute;n y avisos al Registro Federal de
									Contribuyentes, a que se refieren el C&oacute;digo Fiscal de la
									Federaci&oacute;n y su Reglamento.</p></li>
							<li><p>Se encuentra al corriente en el cumplimiento de
									sus obligaciones fiscales respecto de la presentaci&oacute;n de
									la declaraci&oacute;n anual del impuesto sobre la renta, por
									los dos &uacute;ltimos ejercicios fiscales por los que se
									encuentre obligado; as&iacute; como de los pagos mensuales del
									impuesto al valor agregado y retenciones del impuesto sobre la
									renta de salarios de los 12 meses anteriores a la fecha de
									presentaci&oacute;n de la solicitud de inscripci&oacute;n al
									Programa. Cuando los contribuyentes tengan menos de 2
									a&ntilde;os de inscritos en el Registro Federal de
									Contribuyentes, la manifestaci&oacute;n a que se refiere este
									inciso corresponder&aacute; al periodo transcurrido desde la
									inscripci&oacute;n y hasta la fecha que presenten la solicitud
									de inscripci&oacute;n al Programa, sin que en ning&uacute;n
									caso los pagos mensuales excedan de los &uacute;ltimos 12
									meses.</p></li>
							<li><p>No tenga cr&eacute;ditos fiscales determinados
									firmes a su cargo por impuestos federales, distintos al
									impuesto sobre autom&oacute;viles nuevos e impuesto sobre
									tenencia y uso de veh&iacute;culos, entendi&eacute;ndose por
									impuestos federales, el impuesto sobre la renta, impuesto al
									valor agregado, impuesto al activo, impuestos generales de
									importaci&oacute;n y de exportaci&oacute;n y todos los
									accesorios, como recargos, sanciones, gastos de
									ejecuci&oacute;n y la indemnizaci&oacute;n por cheque devuelto,
									que deriven de los anteriores.</p></li>
							<li><p>En el caso de que existan cr&eacute;ditos
									fiscales determinados firmes el patr&oacute;n deber&aacute;
									manifestar si los mismos se encuentran garantizados o bien se
									encuentra transcurriendo el plazo previsto por el
									art&iacute;culo 65 del C&oacute;digo Fiscal de la
									Federaci&oacute;n. De lo contrario, deber&aacute;
									se&ntilde;alar, en su caso, que se compromete a celebrar
									convenio con las autoridades fiscales para pagarlos en los
									t&eacute;rminos de lo previsto por el art&iacute;culo 66 del
									C&oacute;digo Fiscal de la Federaci&oacute;n.</p></li>
							<li><p>Trat&aacute;ndose de contribuyentes que hubieran
									solicitado autorizaci&oacute;n para pagar a plazos o hubieran
									interpuesto alg&uacute;n medio de defensa contra
									cr&eacute;ditos fiscales a su cargo, se requerir&aacute; que
									los mismos se encuentren garantizados conforme al
									art&iacute;culo 141 del C&oacute;digo Fiscal de la
									Federaci&oacute;n, y</p></li>
							<li><p>En caso de contar con autorizaci&oacute;n para el
									pago a plazos, no haber incurrido en las causales de
									revocaci&oacute;n a que hace referencia el art&iacute;culo
									66-A, fracci&oacute;n IV, del C&oacute;digo Fiscal de la
									Federaci&oacute;n.</p></li>
							<li><p>
									Autorizo al Instituto Mexicano del Seguro Social a verificar y
									utilizar la informaci&oacute;n que he proporcionado con motivo
									del Programa Primer Empleo, para efecto de que se lleve a cabo
									sus atribuciones y facultades.<br /> La informaci&oacute;n
									publicada en este Portal no crea derechos ni establece
									obligaciones distintas de los contenidos en las disposiciones
									fiscales vigentes.
								</p></li>
						</ul>

						<p>En el caso espec&iacute;fico de mis obligaciones ante el
							IMSS, manifiesto bajo protesta de decir verdad que:</p>

						<ul type="circle">
							<li><p>He cumplido con mis obligaciones en materia de
									registro como patr&oacute;n ante el IMSS y he inscrito a mis
									trabajadores en el R&eacute;gimen Obligatorio del Seguro
									Social, en la forma y t&eacute;rminos que se&ntilde;alan la Ley
									del Seguro Social y sus reglamentos.</p></li>
							<li><p>Me encuentro al corriente en el cumplimiento de
									mis obligaciones patronales respecto del entero en tiempo y
									forma de las Cuotas Obrero Patronales (COP) y de las Cuotas del
									Seguro de Retiro, Cesant&iacute;a en Edad Avanzada y Vejez
									(RCV) del presente ejercicio, as&iacute; como por los dos
									&uacute;ltimos ejercicios fiscales. En caso de que el registro
									patronal tenga una antig&uuml;edad menor a dos a&ntilde;os, la
									manifestaci&oacute;n a que se refiere este inciso,
									corresponder&aacute; al periodo transcurrido desde el registro
									y hasta la fecha que presente el escrito.</p></li>
							<li><p>Respecto a cr&eacute;ditos fiscales por COP y/o
									RCV, capitales constitutivos (su actualizaci&oacute;n y
									recargos), multas, por gastos relacionados con inscripciones
									improcedentes y por la atenci&oacute;n a personas no
									derechohabientes (se&ntilde;alar la opci&oacute;n que le
									aplique):</p></li>
						</ul>


						<br>

						<form action="validaFirmaIMSS" name="validaFirmaImssForm"
							class="form-horizontal">

							<p style="text-align: center; font-size: small;">
								<strong>Datos de la Firma IMSS.</strong>
							</p>

							<div id="msgErrorDiv" class="alert alert-danger"
								style="margin: 15px auto; display: none; text-align: center;">
								<strong>Error: </strong><label id="msgError"
									style="display: inline;"></label>
							</div>

							<div class="control-group">
								<label class="control-label" for=""nrpInput""
									style="font-size: smaller;">NRP:</label>
								<div class="controls">
									<input id="nrpInput" type="text" name=""nrpInput""
										disabled="disabled" value="${firmaElectronicaIMSS.registroPatronal}" />
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
								<label class="control-label" for="pwdInput"
									style="font-size: smaller;">Contrase&ntilde;a:</label>
								<div class="controls">
									<input id="pwdInput" type="password" name="contrasena" />
								</div>
							</div>
							
							<c:if test="${firmaElectronicaIMSS.firmarArchivo eq true }">							
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

						<form:form modelAttribute="firmaElectronicaIMSS" id="procesaFirmaForm" action="${contextpath}/firma-digital/procesa-firma-IMSS">
							<form:hidden path="registroPatronal" id="registroPatronal"/>
							<form:hidden path="cadenaOriginal" id="cadenaOriginal"/>
							<form:hidden path="firmarArchivo" id="firmarArchivo"/>
							<form:hidden path="fileNameToSign" id="fileNameToSign"/>
							<form:hidden path="sPKCS7" id="sPKCS7"/>
						</form:form>

						<!-- DEFINICION DEL APPLET -->
						<!-- REMOTO -->
						<applet name="applet_ssign"
							codebase="http://11.254.14.212/applets/"
							archive="seguriSignClient-1.5.jar"
							code="seguriSign_Client.seguriSign_Client.class" width="0"
							height="0" align="middle"> El Applet no pudo ser
							cargado. </applet>
							
						<!-- SEGURIDATA -->
						<applet id="seguriDataApplet" width="0" height="0"
							codebase="http://11.254.14.212/applets/"
							archive="SgSignSigner.jar"
							style="xdisplay: none; width: 0; height: 0; padding: 0; margin: 0;"
							mayscript="false" scriptable="false" name="seguriDataApplet"
							code="seguridata.segurisign.Signer"> El Applet no pudo
							ser cargado.</applet>
					</fieldset>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>