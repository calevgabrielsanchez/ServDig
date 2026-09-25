<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/moral/actualizacion-datos/contenido.js" htmlEscape="true" />"></script>
	
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="escritura" value="${icaDatosAux.personaMoralIMSS.escrituraConstitutiva}" />
<c:set var="sindicato" value="${icaDatosAux.personaMoralIMSS.registroSindicato}" />
<c:set var="idTipoTramite" value="<%=TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo()%>" />	
<c:set var="origenINTERNET" value="<%=OrigenSolicitudEnum.INTERNET.getId()%>" />
<c:set var="origenVENTANILLA" value="<%=OrigenSolicitudEnum.VENTANILLA.getId()%>" />

<style>
.contenedor .pie .opciones {
    float: left;
    width: 60%;
}

.contenedor .pie .controles {
    float: right;
    width: 40%;
}
</style>

<script>
	var nuevosDatosPersonales = {
		nombreRazonSocial : "${icaDatosAux.personaMoralEE.razonSocial}",
		rfc : "${icaDatosAux.personaMoralEE.rfc}"
	};
	var idTipoTramite = ${idTipoTramite};
	
	if(parent.WizardActualizacionDatosCtrl.config.idOrigen == 2){
		var codigoTipoSolicitud = ${codigoTipoSolicitud};
		var descripcionTipoSolicitud = "${descripcionTipoSolicitud}";
		var arrayCodigoTipoTramite = ${codigoTipoTramite};
		var context_path='<%=request.getContextPath()%>';
		var datosEntradaFirma = {
			fechaElectronica : '${datosFirmaElectronica.fechaElectronicaFormateada}',
			nombreCompleto : "${datosFirmaElectronica.nombreCompleto}",
			registroPatronal : '${datosFirmaElectronica.registroPatronal}',
			rfc : '${datosFirmaElectronica.rfc}',
			curp : "${datosFirmaElectronica.curp}"
		};
	}
	
</script>


<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
		<form:form modelAttribute="icaDatosAux" id="forma" method="post">
			<input type="hidden" id="idSolicitud" value="${idSolicitud}" />
			<input type="hidden" id="folioSolicitud" value="${folioSolicitud}" />
			<input type="hidden" id="rfcPersona" value="${rfcPersona}" />
			<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />

			<c:if test="${not empty icaDatosAux.errorFormGeneral && fn:contains(icaDatosAux.errorFormGeneral , 'No existen diferencias')}">
				<div class="alert alert-success">
					<button type="button" class="close" data-dismiss="alert">×</button>
					${icaDatosAux.errorFormGeneral}
				</div>

				<div class="alert alert-success">
					<c:choose>
						<c:when test="${!isRetomar}">
						Su solicitud ha sido iniciado correctamente y se le ha asignado a dicha solicitud el folio: <strong>${folioSolicitud}</strong>
						</c:when>
						<c:otherwise>
						El folio de la solicitud que esta retomando es: <strong>${folioSolicitud}</strong>
						</c:otherwise>
					</c:choose>
				</div>
			</c:if>

			<c:if test="${not empty icaDatosAux.errorFormGeneral && !fn:contains(icaDatosAux.errorFormGeneral , 'No existen diferencias')}">
				<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Importante: </strong>${icaDatosAux.errorFormGeneral}
				</div>
			</c:if>

			<c:if test="${(origenApp eq origenVENTANILLA ) or 
			(origenApp ne origenVENTANILLA && not empty icaDatosAux.errorFormGeneral && fn:contains(icaDatosAux.errorFormGeneral , 'No existen diferencias'))}">
			<div class="checkbox">
				<label> 
					<c:if test="${escritura != null }">
						<input type="checkbox" id="esSindicato" />
					</c:if> 
					<c:if test="${sindicato != null }">
						<input type="checkbox" id="esSindicato" checked="checked" />
					</c:if> 
					<c:if test="${sindicato == null && escritura == null}">
						<input type="checkbox" id="esSindicato" />
					</c:if> 
					
					Es Sindicato
				</label>
			</div>
			
			
			<div id="seccionEscritura">
				<div class="separadorseccion">
					<span> Escritura </span>
				</div>
				
				<div class="row">
					<div class="col-xs-6">
						<div class="form-group">
							<label for="numEscritura">
								<span class="required">*</span>
								N&uacute;mero de Escritura:
							</label>
							<input type="hidden" id="cveEscritura"
								value="${icaDatosAux.personaMoralIMSS.escrituraConstitutiva.cveEscrituraConstitutiva }" />
							<input id="numEscritura" type="text" maxlength="12"
								class="numericoSinPunto form-control input-sm"
								value="${ icaDatosAux.personaMoralIMSS.escrituraConstitutiva.numEscritura }" />
							<span class="error hiddenElement" id="cveEscrituraError"></span> 
							<span class="error hiddenElement" id="numEscrituraError"></span> 
							<input type="hidden" id="numEscritura"
								value="${icaDatosAux.personaMoralIMSS.escrituraConstitutiva.numEscritura }" />
						</div>
					</div>
					<div class="col-xs-6">
						<div class="form-group">
							<label for="numNotaria">
								<span class="required">*</span>
								N&uacute;mero de Notar&iacute;a o Corredur&iacute;a:
							</label>
							<input id="numNotaria" type="text" maxlength="15"
								class="caracterNotaria form-control input-sm"
								value="${ icaDatosAux.personaMoralIMSS.escrituraConstitutiva.numNotaria }" />
							<span class="error hiddenElement" id="numNotariaError"></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-6">
						<div class="form-group">
							<label for="idEstado">
								<span class="required">*</span>
								Estado:
							</label>
							<combo:creaCombo idHtml="idEstado" idHtmlContenedor="forma"
								entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
								idHtmlValor="${escritura.lugarExpedicion.entidadFederativa.clave}"
								mostrarSoloActivos="true" cssClassname="form-control input-sm" />
							<span class="error hiddenElement" id="idEstadoError"></span>
						</div>
					</div>
					<div class="col-xs-6">
						<div class="form-group">
							<label for="idMunicipio">
								<span class="required">*</span>
								Municipio o Delegaci&oacute;n:
							</label>
							<combo:creaCombo
								entidad="mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio"
								idHtml="idMunicipio" entidadPadre="dgCatEstado.cveEnt"
								idHtmlPadre="idEstado" idHtmlContenedor="forma"
								idHtmlValor="${escritura.lugarExpedicion.clave }"
								idHtmlValorPadre="${escritura.lugarExpedicion.entidadFederativa.clave }"
								mostrarSoloActivos="false" cssClassname="form-control input-sm" />
							<span class="error hiddenElement" id="idMunicipioError"></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-6">
						<div class="form-group">
							<label for="fecExpedicion" style="display: block;">
								Fecha Expedici&oacute;n:
							</label>
							<input id="fecExpedicion" class="form-control input-sm" maxlength="10"
								style="width: auto; display: inline; margin-right: 10px;"
								value="<fmt:formatDate pattern="dd/MM/yyyy" value="${icaDatosAux.personaMoralIMSS.escrituraConstitutiva.fechaExpedicion}"/>" />
							<span class="error hiddenElement" id="fecExpedicionError"></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-6">
						<div class="form-group">
							<label for="folioMercantil">
								Folio Mercantil:
							</label>
							<input id="folioMercantil"
								class="asterisco form-control input-sm" maxlength="13"
								value="${ icaDatosAux.personaMoralIMSS.escrituraConstitutiva.folioMercantil }" />
							<span class="error hiddenElement" id="folioMercantilError"></span>
						</div>
					</div>
					<div class="col-xs-6">
						<div class="form-group">
							<label for="seccion">
								Secci&oacute;n:
							</label>
							<input id="seccion" class="caracterEsp form-control input-sm" maxlength="18"
								value="${ icaDatosAux.personaMoralIMSS.escrituraConstitutiva.seccion }" />
							<span class="error hiddenElement" id="seccionError"></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-6">
						<div class="form-group">
							<label for="partida">
								Partida:
							</label>
							<input id="partida" class="caracterEsp form-control input-sm" maxlength="15"
								value="${ icaDatosAux.personaMoralIMSS.escrituraConstitutiva.partida }" />
							<span class="error hiddenElement" id="partidaError"></span>
						</div>
					</div>
					<div class="col-xs-6">
						<div class="form-group">
							<label for="volumen">
								Volumen:
							</label>
							<input id="volumen" class="caracterEsp form-control input-sm" maxlength="15"
								value="${ icaDatosAux.personaMoralIMSS.escrituraConstitutiva.volumen }" />
							<span class="error hiddenElement" id="volumenError"></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-6">
						<div class="form-group">
							<label for="foja">Foja:</label>
							<input id="foja" class="caracterEsp form-control input-sm" maxlength="15"
								value="${ icaDatosAux.personaMoralIMSS.escrituraConstitutiva.foja }"/> 
								<span class="error hiddenElement" id="fojaError"></span>
						</div>
					</div>				
				</div>
				
			</div>
			
			<div id="seccionSindicato">
				<div class="separadorseccion">
					<span> Sindicato </span>
				</div>
				<input type="hidden" name="cveSindicato" id="cveSindicato"
				value="${icaDatosAux.personaMoralIMSS.registroSindicato.cveRegistroSindicato}">
				<div class="row">
					<div class="col-xs-6">
						<div class="form-group">
							<label for="numRerencia">
								<span class="required">*</span>
								N&deg; Referencia Registro:
							</label>
							<input type="text" class="numerico form-control input-sm" 
								id="numRerencia" maxlength="20"
								value="${icaDatosAux.personaMoralIMSS.registroSindicato.numReferenciadocRegistro}" />
							<span class="error hiddenElement" id="numReferenciaError"></span>
						</div>
					</div>
					<div class="col-xs-6">
						<div class="form-group">
							<label for="fecRegistro" style="display: block;">
								<span class="required">*</span>
								Fecha Registro:
							</label>
							<input type="text" class="form-control input-sm"
								id="fecRegistro" maxlength="18" style="width: auto; display: inline; margin-right: 10px;"
								value="<fmt:formatDate pattern="dd/MM/yyyy" value="${icaDatosAux.personaMoralIMSS.registroSindicato.fechaRegistro}"/>" />
							<span class="error hiddenElement" id="fecRegistroError"></span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-6">
						<div class="form-group">
							<label for="sindicato">
								<span class="required">*</span>
								Autoridad Laboral:
							</label>
							<input type="text" maxlength="100" id="autLaboral"
							style="text-transform:uppercase;"  class="autoridad form-control input-sm" 
								value="${icaDatosAux.personaMoralIMSS.registroSindicato.autoridadLaboral}" />
							<span class="error hiddenElement" id="autLaboralError"></span>
						</div>
					</div>
				</div>
			</div>
			</c:if>
			<c:if test="${empty icaDatosAux.errorFormGeneral}">
				<div class="alert alert-info m-t-md">
					<button type="button" class="close" data-dismiss="alert">×</button>
					A continuaci&oacute;n se detallan las diferencias entre la
					informaci&oacute;n registrada en el Instituto y las entidades
					externas.
				</div>
				<div id="datosConfirmadosSat">

					<table class="table table-bordered" id="tabla-imss-sat">
						<thead>
							<tr>
								<th><b>DATOS CONFIRMADOS POR SAT</b></th>
							</tr>
						</thead>

						<tbody>
							<tr>
								<td>
									<table cellpadding="0" cellspacing="0" class="tblICA table-striped">
										<tr>
											<td colspan="4" style="text-align: center;"><b>DATOS
													B&Aacute;SICOS</b></td>
										</tr>
										<tr>
											<td style="width: 15%;">&nbsp;</td>
											<td style="text-align: center; width: 40%;"><b>DATOS
													IMSS</b></td>
											<td style="text-align: center; width: 40%;"><b>DATOS
													ENTIDAD EXTERNA</b></td>
											<td style="width: 5%;">&nbsp;</td>
										</tr>
										<tr>
											<td style="font-weight: bold;">Nombre &oacute;
												Raz&oacute;n Social</td>
											<td>${icaDatosAux.personaMoralIMSS.razonSocial}</td>
											<td>${icaDatosAux.personaMoralEE.razonSocial}</td>
											<td><c:if
													test="${icaDatosAux.cambios['nombreRazonSocial'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
										<tr>
											<td style="font-weight: bold;">Fecha de
												Constituci&oacute;n</td>
											<td>${icaDatosAux.personaMoralIMSS.fechaCreacionFormateada}</td>
											<td>${icaDatosAux.personaMoralEE.fechaCreacionFormateada}</td>
											<td><c:if
													test="${icaDatosAux.cambios['fechaConstitucion'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
										<tr>
											<td style="font-weight: bold;">Tipo de Sociedad</td>
											<td>${icaDatosAux.personaMoralIMSS.tipoSociedad.descripcionAbreviada}</td>
											<td>${icaDatosAux.personaMoralEE.tipoSociedad.descripcionAbreviada}</td>
											<td><c:if
													test="${icaDatosAux.cambios['tipoSociedad'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
										<tr>
											<td style="font-weight: bold;">RFC</td>
											<td>${icaDatosAux.personaMoralIMSS.rfc}</td>
											<td>${icaDatosAux.personaMoralEE.rfc}</td>
											<td><c:if test="${icaDatosAux.cambios['rfc'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
										<tr>
											<td style="font-weight: bold;">Situaci&oacute;n</td>
											<td>${icaDatosAux.personaMoralIMSS.situacionesSAT[0].descripcion}</td>
											<td>${icaDatosAux.personaMoralEE.situacionesSAT[0].descripcion}</td>
											<td><c:if
													test="${icaDatosAux.cambios['situacionSAT'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table cellpadding="0" cellspacing="0" class="tblICA table-striped">
										<tr>
											<td colspan="4" style="text-align: center;"><b>DOMICILIO
													FISCAL</b></td>
										</tr>
										<tr>
											<td style="width: 15%;">&nbsp;</td>
											<td style="text-align: center; width: 40%;"><b>DATOS
													IMSS</b></td>
											<td style="text-align: center; width: 40%;"><b>DATOS
													ENTIDAD EXTERNA</b></td>
											<td style="width: 5%;">&nbsp;</td>
										</tr>
										<tr>
											<td style="font-weight: bold;">C&oacute;digo Postal</td>
											<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.codigoPostal.codigoPostal}</td>
											<td>${icaDatosAux.personaMoralEE.domicilioFiscal.codigoPostal.codigoPostal}</td>
											<td><c:if
													test="${icaDatosAux.cambios['codigoPostal'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
										<tr>
											<td style="font-weight: bold;">Calle</td>
											<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.calle}</td>
											<td>${icaDatosAux.personaMoralEE.domicilioFiscal.calle}</td>
											<td><c:if test="${icaDatosAux.cambios['calle'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
										<tr>
											<td style="font-weight: bold;">Colonia</td>
											<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.colonia}</td>
											<td>${icaDatosAux.personaMoralEE.domicilioFiscal.colonia}</td>
											<td><c:if
													test="${icaDatosAux.cambios['colonia'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
										<tr>
											<td style="font-weight: bold;">Entidad Federativa</td>
											<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}</td>
											<td>${icaDatosAux.personaMoralEE.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}</td>
											<td><c:if
													test="${icaDatosAux.cambios['entidad'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
										<tr>
											<td style="font-weight: bold;">Localidad</td>
											<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.asentamiento.localidad.nombre}</td>
											<td>${icaDatosAux.personaMoralEE.domicilioFiscal.asentamiento.localidad.nombre}</td>
											<td><c:if
													test="${icaDatosAux.cambios['localidad'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
										<tr>
											<td style="font-weight: bold;">Municipio</td>
											<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.asentamiento.localidad.municipio.nombre}</td>
											<td>${icaDatosAux.personaMoralEE.domicilioFiscal.asentamiento.localidad.municipio.nombre}</td>
											<td><c:if
													test="${icaDatosAux.cambios['municipio'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
										<tr>
											<td style="font-weight: bold;">Entre Calle 1</td>
											<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.vialidadReferenciaPrimaria.nombre}</td>
											<td>${icaDatosAux.personaMoralEE.domicilioFiscal.vialidadReferenciaPrimaria.nombre}</td>
											<td><c:if
													test="${icaDatosAux.cambios['entreCalle1'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
										<tr>
											<td style="font-weight: bold;">Entre Calle 2</td>
											<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.vialidadReferenciaSecundaria.nombre}</td>
											<td>${icaDatosAux.personaMoralEE.domicilioFiscal.vialidadReferenciaSecundaria.nombre}</td>
											<td><c:if
													test="${icaDatosAux.cambios['entreCalle2'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
										<tr>
											<td style="font-weight: bold;">Inmueble</td>
											<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.asentamiento.tipoAsentamiento.descripcion}</td>
											<td>${icaDatosAux.personaMoralEE.domicilioFiscal.asentamiento.tipoAsentamiento.descripcion}</td>
											<td><c:if
													test="${icaDatosAux.cambios['inmueble'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
										<tr>
											<td style="font-weight: bold;">Referencia</td>
											<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.descripcion}</td>
											<td>${icaDatosAux.personaMoralEE.domicilioFiscal.descripcion}</td>
											<td><c:if
													test="${icaDatosAux.cambios['referencia'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
										<tr>
											<td style="font-weight: bold;">Vialidad</td>
											<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.vialidadPrimaria.nombre}</td>
											<td>${icaDatosAux.personaMoralEE.domicilioFiscal.vialidadPrimaria.nombre}</td>
											<td><c:if
													test="${icaDatosAux.cambios['vialidad'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
										<tr>
											<td style="font-weight: bold;">N&uacute;mero Interior</td>
											<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.numInteriorAlf}</td>
											<td>${icaDatosAux.personaMoralEE.domicilioFiscal.numInteriorAlf}</td>
											<td><c:if
													test="${icaDatosAux.cambios['numeInt'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
										<tr>
											<td style="font-weight: bold;">N&uacute;mero Exterior</td>
											<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.numExteriorAlf}</td>
											<td>${icaDatosAux.personaMoralEE.domicilioFiscal.numExteriorAlf}</td>
											<td><c:if
													test="${icaDatosAux.cambios['numeExt'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table cellpadding="0" cellspacing="0" class="tblICA table-striped">
										<tr>
											<td colspan="4" style="text-align: center;"><b>FORMA
													DE CONTACTO 1 (TEL&Eacute;FONO FIJO)</b></td>
										</tr>
										<tr>
											<td style="width: 15%;">&nbsp;</td>
											<td style="text-align: center; width: 40%;"><b>DATOS
													IMSS</b></td>
											<td style="text-align: center; width: 40%;"><b>DATOS
													ENTIDAD EXTERNA</b></td>
											<td style="width: 5%;">&nbsp;</td>
										</tr>
										<tr>
											<td style="font-weight: bold;">N&uacute;mero</td>
											<td>${icaDatosAux.personaMoralIMSS.telefonoFijoFiscal.numero}</td>
											<td>${icaDatosAux.personaMoralEE.telefonoFijoFiscal.numero}</td>
											<td><c:if
													test="${icaDatosAux.cambios['telefonoFijo.numero'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
										<tr>
											<td style="font-weight: bold;">Clave lada</td>
											<td>${icaDatosAux.personaMoralIMSS.telefonoFijoFiscal.claveLada}</td>
											<td>${icaDatosAux.personaMoralEE.telefonoFijoFiscal.claveLada}</td>
											<td><c:if
													test="${icaDatosAux.cambios['telefonoFijo.lada'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
										<tr>
											<td style="font-weight: bold;">Extensi&oacute;n</td>
											<td>${icaDatosAux.personaMoralIMSS.telefonoFijoFiscal.extension}</td>
											<td>${icaDatosAux.personaMoralEE.telefonoFijoFiscal.extension}</td>
											<td><c:if
													test="${icaDatosAux.cambios['telefonoFijo.extension'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table cellpadding="0" cellspacing="0" class="tblICA table-striped">
										<tr>
											<td colspan="4" style="text-align: center;"><b>FORMA
													DE CONTACTO 2 (TEL&Eacute;FONO M&Oacute;VIL)</b></td>
										</tr>
										<tr>
											<td style="width: 15%;">&nbsp;</td>
											<td style="text-align: center; width: 40%;"><b>DATOS
													IMSS</b></td>
											<td style="text-align: center; width: 40%;"><b>DATOS
													ENTIDAD EXTERNA</b></td>
											<td style="width: 5%;">&nbsp;</td>
										</tr>
										<tr>
											<td style="font-weight: bold;">N&uacute;mero</td>
											<td>${icaDatosAux.personaMoralIMSS.telefonoMovilFiscal.numero}</td>
											<td>${icaDatosAux.personaMoralEE.telefonoMovilFiscal.numero}</td>
											<td><c:if
													test="${icaDatosAux.cambios['telefonoMovil'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table cellpadding="0" cellspacing="0" class="tblICA table-striped">
										<tr>
											<td colspan="4" style="text-align: center;"><b>FORMA
													DE CONTACTO 3 (CORREO ELECTR&Oacute;NICO)</b></td>
										</tr>
										<tr>
											<td style="width: 15%;">&nbsp;</td>
											<td style="text-align: center; width: 40%;"><b>DATOS
													IMSS</b></td>
											<td style="text-align: center; width: 40%;"><b>DATOS
													ENTIDAD EXTERNA</b></td>
											<td style="width: 5%;">&nbsp;</td>
										</tr>
										<tr>
											<td style="font-weight: bold;">Correo</td>
											<td>${icaDatosAux.personaMoralIMSS.correoElectronicoFiscal.correo}</td>
											<td>${icaDatosAux.personaMoralEE.correoElectronicoFiscal.correo}</td>
											<td><c:if
													test="${icaDatosAux.cambios['correoElectronico'].id ne 4}">
													<span class="label label-danger"
														style="font-size: xx-small;" title="CAMBIO">*</span>
												</c:if></td>
										</tr>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
					<br />
				</div>
			</c:if>
		</form:form>
		<form action="" method="post" id="soForm"></form>
		</div>
	</div>

	<div class="pie row">
		<div class="opciones col-sm-6">
			<c:if test="${empty icaDatosAux.errorFormGeneral or (not empty icaDatosAux.errorFormGeneral && fn:contains(icaDatosAux.errorFormGeneral , 'No existen diferencias')) }">
			<div class="btn-group dropup">
				<a href="#" class="btn btn-primary">Acciones</a> <a href="#"
					data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
					class="caret"></span></a>
				<ul class="dropdown-menu">
					<c:if test="${origenApp eq origenINTERNET}">
						<li><a id="finalizarTramite"><i class="glyphicon glyphicon-ok"></i> Finalizar Tr&aacute;mite</a></li>
					</c:if>
						<li><a id="guardarTramite"><i class="glyphicon glyphicon-download-alt"></i> Guardar Tr&aacute;mite</a></li>
						<li><a id="cancelarTramite"><i class="glyphicon glyphicon-trash"></i> Cancelar Tr&aacute;mite</a></li>
				</ul>
			</div>
			</c:if>
		</div>

		<div class="controles col-sm-6"> 
			<div class="pull-right">
				<button class="btn btn-default" id="cerrarWizard">CERRAR</button>
				<c:if test="${empty icaDatosAux.errorFormGeneral or (not empty icaDatosAux.errorFormGeneral 
						&& fn:contains(icaDatosAux.errorFormGeneral , 'No existen diferencias')) }">
					<c:if test="${origenApp eq origenVENTANILLA}">			
						<a id="mostrarRLVentanilla" class="btn btn-primary"><i class="glyphicon glyphicon-step-forward"></i> Siguiente</a>									
					</c:if>
				</c:if>
			</div>
		</div>

	</div>
</div>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar"
	title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> ¿Desea cancelar la
		solicitud pendiente con folio: <strong>${folioSolicitud}</strong>?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogo"></label>
	</p>
</div>