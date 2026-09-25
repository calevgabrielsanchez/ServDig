<div class="form-group">
	<div class="row">
		<div class="col-md-12 col-sm-7 col-xs-12">
			<table class="table table-bordered"
				id='informacionCuenta'>

				<tr>
					<th name="recepMovIni"><spring:message
							code="label.mov.ini.recep" /></th>
					<th name="movIni"><spring:message
							code="label.mov.ini" /></th>
					<th name="movOri"><spring:message
							code="label.mov.ori" /></th>
					<th name="movIniFec"><spring:message
							code="label.mov.ini.fec" /></th>
					<th name="sbc"><spring:message
							code="label.sbc" /></th>
					<th name="tsbc"><spring:message
							code=label.tsbc" /></th>
					<th name="tt"><spring:message
							code="label.tt" /></th>
					<th name="ext"><spring:message
							code="label.ext" /></th>
					<th name="ss"><spring:message
							code="label.ss" /></th>
					<th name="movFin"><spring:message
							code="label.mov.fin" /></th>
					<th name="movOri"><spring:message
							code="label.ori.mov" /></th>
					<th name="fecMovFin"><spring:message
							code="label.fec.mov.fin" /></th>
					<!-- 
					<th name="jornada"><spring:message
							code="label.jornada" /></th> -->														
					<th name="consec"><spring:message
							code="label.consec" /></th>
					<!-- 
					<th name="nssDestino"><spring:message
							code="label.nss.destino" /></th> -->
					<th name="regularizacion"><spring:message
							code="label.regularizacion" /></th>
					<th name="consecutivo"><spring:message
							code="label.consecutivo" /></th>
				</tr>
				<c:forEach var="cuentaIndividualVO" varStatus="individual"
					items="${datosAdicionalesHistoriaLaboral.datosAdicionalesList}">
					<tr>						
						<td>${cuentaIndividualVO.fechaRecepcionMovimiento}</td>
						<td>${cuentaIndividualVO.tipoMovimientoIniintcial}</td>
						<td>${cuentaIndividualVO.origenMovimientoInicial}</td>
						<td>${cuentaIndividualVO.fechaInicioMovimiento}</td>
						<td>${cuentaIndividualVO.salarioBase}</td>
						<td>${cuentaIndividualVO.tipoSalario}</td>
						<td>${cuentaIndividualVO.eventual}</td>
						<td>${cuentaIndividualVO.extemporaneoConvenioSuspencion}</td>
						<td>${cuentaIndividualVO.subrogacionServicio}</td>
						<td>${cuentaIndividualVO.tipoMovimientoFinal}</td>
						<td>${cuentaIndividualVO.origenMovimientoFinal}</td>
						<td>${cuentaIndividualVO.fechaFinalMovimiento}</td>
						<!-- <td>${cuentaIndividualVO.jornadaSemanal}</td> -->
						<td>${cuentaIndividualVO.numeroConsecutivoPeriodos}</td>
						<td>reg</td>
						<td>consec</td>
						
						<!-- 
						<td>${cuentaIndividualVO.numeroRegistroPatronal}</td>
						<td>${cuentaIndividualVO.nss}</td>
						<td>${cuentaIndividualVO.registroPatronal}</td>
						<td>${cuentaIndividualVO.claveModalidad}</td>
						
						
						<td>${cuentaIndividualVO.curp}</td>																																													
						
						
						<td>${cuentaIndividualVO.huelga}</td>
						
						<td>${cuentaIndividualVO.fechaActualizacion}</td>
						<td>${cuentaIndividualVO.claveDelegacionOrigen}</td>
						<td>${cuentaIndividualVO.claveCiz}</td>
						<td>${cuentaIndividualVO.fechaCarga}</td> -->									
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>