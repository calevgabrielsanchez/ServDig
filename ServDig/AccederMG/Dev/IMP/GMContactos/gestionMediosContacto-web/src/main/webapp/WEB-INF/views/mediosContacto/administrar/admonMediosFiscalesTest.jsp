<script type="text/javascript">
	$(document).ready(function(){
		$('#admonMediosBtn').click(function (){
			ejecutarAdmonMedios();
		});
	});
	
	var ejecutarAdmonMedios = function (){
		var idPersona = $('#idPersona').val();
		var url = '/gestionMediosContacto-web/medios/fiscales/administrar/init/' + idPersona;
		
		$.post(url, function(data) {
			$("#admonMediosContactoDiv").html(data);
		}).error(function(data) {
			
		});	
	};
</script>

<div class="page-holder">
	<div class="contenedor">
		<div class="row">
			<div class="cell">
				<fieldset>
					<legend>
						<strong>&nbsp;TEST DE ADMON. MEDIOS CONTACTO&nbsp;</strong>
					</legend>
					<form action="" id="admonMediosContactoTestForm">
						<div class="row">
							<div class="cell etiqueta">
								<label>CVE FISICA / CVE MORAL:</label>
							</div>
							<div class="cell">
								<input type="text" id="idPersona" />
							</div>
						</div>
						<div class="row">
							<div class="cell etiqueta">
								<label>TIPO PERSONA:</label>
							</div>
							<div class="cell">
								<select id="cveTipoPersona">
									<option value="1">FISICA</option>
									<option value="2">MORAL</option>
								</select>
							</div>
						</div>
						<div style="float: right;">
							<button type="button" class="mboton" id="admonMediosBtn">
								Administrar Medios</button>
						</div>
					</form>
				</fieldset>
			</div>
		</div>
	</div>
</div>

<div id="admonMediosContactoDiv"></div>