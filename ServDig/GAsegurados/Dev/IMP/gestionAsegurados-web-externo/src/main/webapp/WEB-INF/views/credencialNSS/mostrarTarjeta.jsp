<!-- Vista para la captura de los datos de la persona -->
<%@ include file="../general/taglibs.jsp"%>

<div class="contenedor">
			<div id="info-paso" style="margin-bottom: 50px;">
				<h3>Tarjeta NSS</h3>
				<hr class="red" style="margin-bottom: 20px;">
			</div>
			
			<!-- Forma de la consulta de personas por datos basicos. -->
			<div>
				<div class="row">
					<form
						id="registroPersonaFisicaForm"
						action="#"
						class="form-horizontal" role="form">

						<div class="col-md-7">
							<div id="datosBasicosDiv">
								<fieldset>
									<legend>
										Informacion encontrada
									</legend>
									
									<div class="form-group">
										<label for="registroCurp" class="col-xs-4 control-label">
											NSS
										</label>
										<div class="col-xs-8">
											<input type="text" readonly="readonly" id="nss" value="${nss}"
												class="form-control"/>
										</div>
									</div>
									
									<div class="form-group">
										<label for="registroNombres" class="col-xs-4 control-label">
											Tarjeta
										</label>
										<div class="col-xs-8">
											<img alt="" src="/gestionAsegurados-web-externo/tarjetaNSS/obtener/${nss}" width="339px" height="213px">
										</div>
									</div>
								</fieldset>
							</div>							
						</div>

						
					</form>
				</div>
			</div>
</div>