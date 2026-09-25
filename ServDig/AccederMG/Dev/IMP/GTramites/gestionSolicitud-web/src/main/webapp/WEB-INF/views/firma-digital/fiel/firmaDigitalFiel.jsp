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
						<button type="button" class="close" data-dismiss="alert">×</button>
						<strong>Error: </strong>${firmaElectronicaFIEL.errorFormGeneral}
					</div>
					<br>
					<div style="float: right;">
						<input type="button" value="Aceptar" onclick="cancelar();" class="btn btn-secondary" />
					</div>
				</c:when>
				<c:otherwise>
					<fieldset>
						<legend>
							<strong>&nbsp;Firma Digital con FIEL&nbsp;</strong>
						</legend>

						<!-- Terminos y condiciones -->
						<p>
							<strong>CARTA DE T&Eacute;RMINOS Y CONDICIONES PARA UTILIZAR LA FIRMA
								 ELECTR&Oacute;NICA AVANZADA EN LOS ACTOS QUE SE REALICEN ANTE EL IMSS.</strong>
						</p>


						<p>
							Con fundamento en las "Reglas de car&aacute;cter general para el uso de la firma electr&oacute;nica avanzada,
						 	cuyo certificado digital sea emitido por el Servicio de Administraci&oacute;n Tributaria, en los actos que se realicen 
							ante el Instituto Mexicano del Seguro Social", publicadas en el Diario Oficial de la Federaci&oacute;n el __ de ______ de 2013, 
						 	los particulares, ya sean personas f&iacute;sicas y morales, podr&aacute;n optar por realizar los actos que señala la Ley del Seguro Social,
						  	sus Reglamentos y dem&aacute;s disposiciones que de ella emanen, de manera electr&oacute;nica y firm&aacute;ndola directamente con su firma 
						  	electr&oacute;nica avanzada (FIEL) o la de su representante legal, cuyo certificado digital est&eacute; vigente y haya sido emitido
						  	por el Servicio de Administraci&oacute;n Tributaria (SAT), siempre que el Instituto Mexicano del Seguro Social (IMSS) 
						   	ponga a disposici&oacute;n de los particulares las herramientas tecnol&oacute;gicas necesarias para ello.
						</p>
						
						<p>La FIEL sustituye la firma aut&oacute;grafa del firmante y producir&aacute; los mismos efectos que las leyes otorgan a los documentos
							 con firma aut&oacute;grafa, teniendo el mismo valor probatorio. Asimismo, con el uso de la FIEL se tiene por reconocida la garant&iacute;a
						 	 de la autor&iacute;a del firmante y de la integridad de los documentos electr&oacute;nicos que se firmen con ella y, por ende, el contenido
						 	 de los mismos no podr&aacute; desconocerse ni admitir&aacute; prueba en contrario.
						</p>

						<p>
							La expedici&oacute;n de los certificados digitales y la generaci&oacute;n de las claves p&uacute;blicas y privadas que conforman la FIEL de 
							personas f&iacute;sicas y morales, se regir&aacute;n por el C&oacute;digo Fiscal de la Federaci&oacute;n, su reglamentaci&oacute;n secundaria en la materia y,
							en su caso, por la Ley de la Firma Electr&oacute;nica Avanzada. Por lo tanto, la expedici&oacute;n de los certificados digitales, 
							su renovaci&oacute;n, revocaci&oacute;n y dem&aacute;s tr&aacute;mites relacionados con la FIEL, se deber&aacute;n realizar ante el SAT, cumpliendo con los 
							requisitos y procedimientos establecidos en la normatividad aplicable.
						</p>
						
						<p>
							Los particulares, ya sean personas f&iacute;sicas o morales, que opten por autenticarse y/o realizar actos ante el IMSS con el uso de su FIEL, 
							reconocen que es de su exclusiva responsabilidad  el resguardo del certificado digital y la confidencialidad de la clave privada que conforma
							su FIEL, con el fin de evitar la utilizaci&oacute;n no autorizada de la misma. Por lo tanto, en cualquier acto firmado con la FIEL se tendr&aacute; 
							por v&aacute;lido y sin que se admita prueba en contrario, el v&iacute;nculo entre el firmante, sea persona moral o f&iacute;sica, y los datos que fueron
							utilizados para la creaci&oacute;n de la respectiva FIEL; por lo cual, los actos firmados con la FIEL ser&aacute;n imputables al titular del certificado 
							digital que se haya utilizado. 
						</p>
						
						<p>
							Los actos firmados con la FIEL ser&aacute;n considerados hechos leg&iacute;tima y aut&eacute;nticamente por el firmante y, en caso de personas morales,
							por el administrador &uacute;nico, el Presidente del Consejo de Administraci&oacute;n o la persona o personas, cualquiera que sea el nombre con
							el que se les designe, que tengan conferida la direcci&oacute;n general, la gerencia general o la administraci&oacute;n de la persona moral de
							que se trate, en el momento en el que se presentaron los documentos digitales. Lo anterior no admitir&aacute; prueba en contrario ante
						 	el IMSS y el titular del certificado digital ser&aacute; responsable de las consecuencias jur&iacute;dicas que deriven de los actos que se 
							realicen ante el IMSS utilizando la FIEL.
						</p>
						
						<p>
							Los particulares, ya sean personas f&iacute;sicas o morales, podr&aacute;n realizar actos a trav&eacute;s de la FIEL de sus representantes legales, 
							siempre que &eacute;stos sean señalados por aqu&eacute;llos y as&iacute; sea aceptado mediante consentimiento expreso de ambos ante el IMSS, para 
							lo cual firmar&aacute;n mancomunadamente, con sus respectivas FIEL y en momentos sucesivos inmediatos, el documento electr&oacute;nico que el 
							Instituto ponga a su disposici&oacute;n para tal efecto.
						</p>
						
						<p>
							En caso de p&eacute;rdida, robo o destrucci&oacute;n de la FIEL, o cualquier otro evento que ponga en riesgo la confidencialidad de los 
							certificados electr&oacute;nicos, las llaves o claves que conforman la FIEL, o la utilizaci&oacute;n no autorizada de la FIEL, la persona f&iacute;sica 
							o moral, bajo su absoluta responsabilidad, deber&aacute; proceder con su inmediata revocaci&oacute;n o reposici&oacute;n ante el SAT, sujet&aacute;ndose a los 
							procesos y lineamientos que el mismo determine.
						</p>
						
						<p>
							La falta de vigencia o revocaci&oacute;n del certificado digital que ampara la FIEL, no eximir&aacute; a la persona f&iacute;sica o moral de cumplir con 
							sus obligaciones ante el IMSS; por lo cual, ser&aacute; su responsabilidad tener vigente el certificado digital que ampara su FIEL en los 
							tiempos en que deba presentar cualquier promoci&oacute;n o tr&aacute;mite ante el IMSS, de conformidad con la Ley del Seguro Social, sus Reglamentos 
							y dem&aacute;s disposiciones aplicables.
						</p>
						
						<p>
							Los t&eacute;rminos y condiciones antes señalados son aplicables a la aceptaci&oacute;n de los mismos que en este acto realice el firmante, mediante 
							su firma electr&oacute;nica FIEL.
						</p>

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
								class="btn btn-secondary" /> <input type="button"
								value="Cancelar" onclick="cancelar();" class="btn btn-secondary" />
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