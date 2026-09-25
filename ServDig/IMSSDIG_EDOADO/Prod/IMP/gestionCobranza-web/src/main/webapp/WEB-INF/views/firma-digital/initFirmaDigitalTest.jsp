<%@ include file="../general/taglibs.jsp"%>

<style>
	.cell {
		padding-bottom: 10px;
	}
	
	.etiqueta {
		padding-right: 20px;
		text-align: right;
	}
</style>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/firma-digital/FirmaDigital.js" htmlEscape="true" />"></script>

<script type="text/javascript">
	var objCtrl;

	$(document).ready(function() {
		$('#firmaBtn').click(function() {
			objCtrl = FirmaDigitalCtrl;

			objCtrl.init('firmaDigitalDlg');
			objCtrl.setOnCloseCallback(function() {
			});

			//Se settean los valores de entrada
			objCtrl.datosEntrada.rfc = $('#rfcTest').val();
			objCtrl.datosEntrada.nrp = $('#nrpTest').val();
			objCtrl.datosEntrada.contenido = $('#contenidoTest').val();
			objCtrl.datosEntrada.firmarArchivo = $('#firmarArchivoTest').is(':checked');

			objCtrl.firmaDigital();

		});
		
		$('#validarFIELbtn').click(function() {
			
			fnHideErrores("form#validarFIELTestForm");
			
			var pkcs7 = $('#pkcsTest').val();
			
			$.postJSON('/gestionCobranza-web/firma-digital/validar-fiel', pkcs7, function(data) {
				$('#cadenaOriginalCert').val(data.firmaElectronica.cadenaOriginal);
				$('#rfcCert').val(data.firmaElectronica.rfc);
				$('#curpCert').val(data.firmaElectronica.curp);
				$('#nombreCert').val(data.firmaElectronica.nombreCompleto);
				$('#inicioVigenciaCert').val(data.firmaElectronica.iniciaVigenciaCertificado);
				$('#finVigenciaCert').val(data.firmaElectronica.finVigenciaCertificado);
			}).error(function(data){
				$('#validarFIELTestForm').clearForm();
				fnProcesarErrores(data, "form#validarFIELTestForm");
			});	
		});
	});
</script>

<div class="page-holder">
	<div class="contenedor">
		<div class="form-comment">
			<div class="row">
				<div class="cell informacion">
					<fieldset>
						<legend>
							<strong>&nbsp;TEST DE FIRMA DIGITAL&nbsp;</strong>
						</legend>
						<form action="" id="testFirmaDigitalForm">
							<div class="row">
								<div class="cell etiqueta">
									<label>RFC:</label>
								</div>
								<div class="cell">
									<input type="text" id="rfcTest" />
								</div>
							</div>
							<div class="row">
								<div class="cell etiqueta">
									<label>NRP:</label>
								</div>
								<div class="cell">
									<input type="text" id="nrpTest" />
								</div>
							</div>
							<div class="row">
								<div class="cell etiqueta">
									<label>Firmar archivo?:</label>
								</div>
								<div class="cell">
									<input type="checkbox" id="firmarArchivoTest" />
								</div>
							</div>
							<div class="row">
								<div class="cell etiqueta" style="vertical-align: middle;">
									<label>Contenido:</label>
								</div>
								<div class="cell">
									<textarea rows="10" cols="20" id="contenidoTest"></textarea>
								</div>
							</div>
	
							<div style="float: right;">
								<button type="button" class="mboton" id="firmaBtn">
									EJECUTAR COMPONENTE FIRMA DIGITAL</button>
							</div>
	
						</form>
					</fieldset>
				</div>
			</div>
			<div class="row">
				<div class="cell informacion">
					<fieldset>
						<legend>
							<strong>&nbsp;TEST DE VALIDACION CERTIFICADO FIEL&nbsp;</strong>
						</legend>
						<form action="" id="validarFIELTestForm">
							<span id="errorNegocioLabel" class="error hiddenElement"></span>
							<div class="row">
								<div class="cell etiqueta" style="vertical-align: middle;">
									<label>PKCS:</label>
								</div>
								<div class="cell">
									<textarea rows="10" cols="20" id="pkcsTest"></textarea>
								</div>
							</div>
							<br><br>
							<div style="float: right;">
								<button type="button" class="mboton" id="validarFIELbtn">
									VALIDAR FIEL</button>
							</div>
							<br><br>
							<div class="row">
								<div class="cell etiqueta" style="vertical-align: middle;">
									<label>Cadena original:</label>
								</div>
								<div class="cell">
									<input type="text" id="cadenaOriginalCert"/>
								</div>
							</div>
							
							<div class="row">
								<div class="cell etiqueta" style="vertical-align: middle;">
									<label>RFC:</label>
								</div>
								<div class="cell">
									<input type="text" id="rfcCert"/>
								</div>
							</div>
							
							<div class="row">
								<div class="cell etiqueta" style="vertical-align: middle;">
									<label>CURP:</label>
								</div>
								<div class="cell">
									<input type="text" id="curpCert"/>
								</div>
							</div>
							
							<div class="row">
								<div class="cell etiqueta" style="vertical-align: middle;">
									<label>Nombre completo:</label>
								</div>
								<div class="cell">
									<input type="text" id="nombreCert"/>
								</div>
							</div>
							
							<div class="row">
								<div class="cell etiqueta" style="vertical-align: middle;">
									<label>Inicio vigencia:</label>
								</div>
								<div class="cell">
									<input type="text" id="inicioVigenciaCert"/>
								</div>
							</div>
							
							<div class="row">
								<div class="cell etiqueta" style="vertical-align: middle;">
									<label>Fin vigencia:</label>
								</div>
								<div class="cell">
									<input type="text" id="finVigenciaCert"/>
								</div>
							</div>
						</form>
					</fieldset>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="firmaDigitalDlg"></div>