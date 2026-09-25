<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
 "http://www.w3.org/TR/html4/strict.dtd">
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="dgCorreccionMain" style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity = 70) !important;">
	
	<div class="menu_principal" style="height: 2em !important;"> 
		<!--inicia menu principal-->
		<div align="center">
			<div class="centrado">
		    	<ul style="height: 1em !important;">
					<li><a> Seguimiento Correcci&oacute;n</a></li>
				</ul>
			</div>
		<!--fin centrado--> 
		</div>
	</div>
	<div id="wrapperDialogDatosCorreccion" style="background-color: #f2fff2;">
	<form:form method="post" id="seguimientoCorreccionForm" modelAttribute="correccionSeguimientoGenericoVO">
	
	
		<form:hidden path="cveSolCorr" id="cveSolCorrSeguimientoCorreccion"/>
		<form:hidden path="cvePresentaCorr" id="cvePresentaCorreccionHdnSeg"/>
		<form:hidden path="user.cveRol" id="cveRolUsuario"/>
		<form:hidden path="fecFechaPeriodoIni" id="fecFechaPeriodoIniSegHdn"/>
		<form:hidden path="fecFechaPeriodoFin" id="fecFechaPeriodoFinSegHdn"/>
		<form:hidden path="fecElaboraPre" id="fecElaboraPreSegCorrMainHdn"/>
		<form:hidden path="nuFolio" id="nuFolioSegCorrHdn"/>
		<fieldset>
			<table  style="width: 900px" align="center">
		 		<tr valign="middle">		 
		 			<td align="center" width="900px">
						<table class="tablaverde2" style="width: 900px" >
							<thead>
								<tr>
									<td colspan="6" class="etiqueta2">Informaci&oacute;n de la Correcci&oacute;n</td>
								</tr>
							</thead>
							<tbody>
								<tr class="impar">
									<td align="left" colspan="6">&nbsp;</td>
								</tr>
								<tr class="par">
									<td align="left" class="etiqueta2">
										<label>Folio de Correcci&oacute;n :</label>
									</td>
									<td align="left">
										<label id="labelFolioCorr"></label>
									</td>
									<td colspan="2">
										<table>
											<tr>
												<td align="left" class="etiqueta2">
													<label>Periodo de Correcci&oacute;n Del:</label>
												</td>
												<td align="left">
													<label id="labelFecInicio"></label>
												</td>
												<td align="left" class="etiqueta2">
													<label>Al:</label>
												</td>
												<td align="left">
													<label id="labelFecFinal"></label>
												</td>
											</tr>
										</table>
									</td>
									<td align="left" class="etiqueta2">
										<label>Sem&aacute;foro</label>
									</td>
									<td align="left">
										<label id="labelSemaforo"></label>
									</td>
								</tr>
								<tr class="par">
									<td align="left" class="etiqueta2">
										<label>Estatus :</label>
									</td>
									<td align="left">
										<label id="labelEstatus"></label>
									</td>
									<td colspan="2">
										&nbsp;
									</td>
									<td align="left" class="etiqueta2">
										<label>Dias Transcurridos :</label>
									</td>
									<td align="left">
										<a id="labelDiasTrans"></a>
									</td>
								</tr>
								<tr class="par">
									<td align="left" class="etiqueta2"><label>Fecha de Presentaci&oacute;n :</label></td>
									<td align="left"><label id="labelFechaPresentacionCorr"></label></td>
									<td align="left" colspan="2">&nbsp;</td>
									<td align="left" colspan="2">&nbsp;</td>
								</tr>
								<thead>
									<tr>
										<td colspan="6" class="etiqueta2">Datos del Patr&oacute;n</td>
									</tr>
								</thead>
								<tr class="impar">
									<td align="left" colspan="6">&nbsp;</td>
								</tr>
								<tr class="par">
									<td align="left" class="etiqueta2">
										<label>Registro Patronal :</label>
									</td>
									<td align="left">
										<label id="labelRegPatronal"></label>
									</td>
									<td align="left" colspan="4">
										<table>
											<tr>
												<td align="left" class="etiqueta2">
													<label>Nombre &oacute; Raz&oacute;n Social :</label>
												</td>
												<td align="left" colspan="3">
													<label id="labelRazonSocial"></label>
												</td>
											</tr>
										</table>
									</td>									
								</tr>
								<tr class="par">
									<td align="left" class="etiqueta2">
										<label>Registro de Obra :</label>
									</td>
									<td align="left">
										<label id="labelRegObra"></label>
									</td>
									<td align="left" colspan="4">
										&nbsp;
									</td>
								</tr>
								<tr class="par">
									<td align="left" colspan="6">&nbsp;</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		</fieldset>
	</form:form>
	</div>
	<br>
	<!-- Seccion de Tabs (Pestañas) -->
	<div style="width: auto; overflow: auto; ">
		<table  style="width: 900px; overflow: auto;" align="center" >
			<tr	>
				<td colspan="6" align="center">			
					<ul class="tabs">
						<li class="etiqueta2" id="seguimientoCorreccionResumenLI">			<a href="#seguimientoCorreccionResumen" id="seguimientoCorreccionResumenLink">					Resumen</a></li>
						<li class="etiqueta2" id="seguimientoCorreccionRecepcionLI">		<a href="#seguimientoCorreccionRecepcion" id="seguimientoCorreccionRecepcionnLink">				Recepci&oacute;n</a></li>
						<li class="etiqueta2" id="seguimientoCorreccionCedRevisionLI">      <a href="#seguimientoCorreccionCedRevision" id="seguimientoCorreccionCedRevisionLink">			C&eacute;dula Revisi&oacute;n</a></li>
						<li class="etiqueta2" id="seguimientoCorreccionReqDocumentacionLI"> <a href="#seguimientoCorreccionReqDocumentacion" id="seguimientoCorreccionReqDocumentacionLink"> Requerimiento Documentaci&oacute;n</a></li>
						<li class="etiqueta2" id="seguimientoCorreccionCedValidacionLI">	<a href="#seguimientoCorreccionCedValidacion" id="seguimientoCorreccionCedValidacionLink">		C&eacute;dula Validaci&oacute;n</a></li>
						<li class="etiqueta2" id="seguimientoCorreccionOfiResultadosLI">	<a href="#seguimientoCorreccionOfiResultados" id="seguimientoCorreccionOfiResultadosLink">		Oficio de Resultados</a></li>
						<li class="etiqueta2" id="seguimientoCorreccionDerivarSubLI">		<a href="#seguimientoCorreccionDerivarSub" id="seguimientoCorreccionDerivarSubLink">				Derivar a Otra Subdelegaci&oacute;n</a></li>
						<li class="etiqueta2" id="seguimientoCorreccionDerivarFisLI">		<a href="#seguimientoCorreccionDerivarFis" id="seguimientoCorreccionDerivarFisLink">				Derivar a Fiscalizaci&oacute;n</a></li>
						<li class="etiqueta2" id="seguimientoCorreccionReactivarLI">		<a href="#seguimientoCorreccionReactivar" id="seguimientoCorreccionReactivarLink">				Reactivaci&oacute;n</a></li>
						<li class="etiqueta2" id="seguimientoCorreccionDerivarDicLI">		<a href="#seguimientoCorreccionDerivarDic" id="seguimientoCorreccionDerivarDicLink">				Derivar a Dictamen</a></li>
						<li class="etiqueta2" id="seguimientoCorreccionCancelacionLI">		<a href="#seguimientoCorreccionCancelacion" id="seguimientoCorreccionCancelacionLink">			Cancelaci&oacute;n</a></li>									
						<li class="etiqueta2" id="seguimientoCorreccionConclusionLI">		<a href="#seguimientoCorreccionConclusion" id="seguimientoCorreccionConclusionLink">				Conclusi&oacute;n</a></li>
					</ul>		
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
					&nbsp;
				</td>
			</tr>
				<tr>
				<td>
					&nbsp;
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					<div id="seguimientoCorreccionResumen" 		 	 class="tab_content">	<jsp:include page="/WEB-INF/views/seguimiento/estudioCorreccion/recepcion/resumenSeguimientoMain.jsp" /></div>
					<div id="seguimientoCorreccionRecepcion" 		 class="tab_content">	<jsp:include page="/WEB-INF/views/seguimiento/estudioCorreccion/recepcion/recepcionSeguimientoMain.jsp" /></div>
					<div id="seguimientoCorreccionCedRevision" 	 	 class="tab_content">	<jsp:include page="/WEB-INF/views/seguimiento/estudioCorreccion/cedulaRevision/cedulaRevPrincipal.jsp" /></div>
					<div id="seguimientoCorreccionReqDocumentacion"  class="tab_content"> 	<jsp:include page="/WEB-INF/views/seguimiento/estudioCorreccion/reqDocumentacion/reqDocumentacionSeguimientoCorreccion.jsp" /></div>
					<div id="seguimientoCorreccionCedValidacion" 	 class="tab_content">	<jsp:include page="/WEB-INF/views/seguimiento/estudioCorreccion/cedulaValidacion/cedulaValidacionMain.jsp" /></div>					
					<div id="seguimientoCorreccionOfiResultados" 	 class="tab_content">	<jsp:include page="/WEB-INF/views/seguimiento/estudioCorreccion/ofResultados/oficioResultadosSeguimientoCorreccion.jsp" /></div>
					<div id="seguimientoCorreccionDerivarSub" 		 class="tab_content">	<jsp:include page="/WEB-INF/views/seguimiento/estudioCorreccion/devSubDelegacion/devSubDelegacionSeguimientoCorreccion.jsp" /></div>
					<div id="seguimientoCorreccionDerivarFis" 		 class="tab_content">	<jsp:include page="/WEB-INF/views/seguimiento/estudioCorreccion/derivacionFiscalizacion/devFiscalizacionSeguimientoCorreccion.jsp" /></div>
					<div id="seguimientoCorreccionReactivar" 		 class="tab_content">	<jsp:include page="/WEB-INF/views/seguimiento/estudioCorreccion/reactivacion/reactivacionSeguimientoCorreccion.jsp" /></div>
					<div id="seguimientoCorreccionDerivarDic" 		 class="tab_content">	<jsp:include page="/WEB-INF/views/seguimiento/estudioCorreccion/derivacionDictamen/devDictamenSeguimientoCorreccion.jsp" /></div>
					<div id="seguimientoCorreccionCancelacion" 	 	 class="tab_content">	<jsp:include page="/WEB-INF/views/seguimiento/estudioCorreccion/cancelacion/cancelacionSeguimientoCorreccion.jsp" /></div>
					<div id="seguimientoCorreccionConclusion" 		 class="tab_content">	<jsp:include page="/WEB-INF/views/seguimiento/estudioCorreccion/conclusion/conclusionSeguimientoCorreccion.jsp" /></div>
				</td>
			</tr>
		</table>	
	</div>
	
</div>