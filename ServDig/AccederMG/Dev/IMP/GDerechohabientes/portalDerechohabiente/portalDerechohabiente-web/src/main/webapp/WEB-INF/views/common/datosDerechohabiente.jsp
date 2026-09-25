<%@ include file="../general/taglibs.jsp"%>
<div class="separadorseccion">
	<span>
		Datos generales del derechohabiente
	</span>
</div>
<div>
	<form>
	<table class="table table-striped table-bordered">	
		<tbody >
			<tr>
				<td align="left">
					 <label class="control-label" for="detalleNombre"><spring:message code="label.nombre"/>:</label>
				</td>
				<td>
					<input type="text" disabled="disabled" id="detalleNombre"value="${integrante.derechohabiente.nombre}" class="form-control"/>
				</td>
				<td align="left">
					<label class="control-label" for="detalleParentesco"><spring:message code="label.parentesco"/>:</label>		
				</td>
				<td>
					<input type="text" disabled="disabled" id="detalleParentesco" value="${integrante.parentesco.descripcion}" class="form-control"/>
				</td>
				
				
			</tr>
			<tr>				
				<td align="left">
					<label class="control-label" for="detallePrimerApellido"><spring:message code="label.primerApe"/>:</label>
				</td>
				<td align="right">
					<input type="text" disabled="disabled" id="detallePrimerApellido" value="${integrante.derechohabiente.primerApellido}" class="form-control"/>
				</td>

				<td align="left">
					<label class="control-label" for="detalleFechaNac"><spring:message code="label.fechaNac"/>:</label>
				</td >
				<td>
					<input id="fechaNacimientoGrupoFamiliar" id="detalleFechaNac" type="text" disabled="disabled" 
						value='<fmt:formatDate pattern="dd/MM/yyyy" value="${integrante.derechohabiente.fechaNacimiento}"/>' class="form-control"/>
				</td>

				
			</tr>
			<tr>
				<td align="left">
					<label class="control-label" for="detalleSegundoApe"><spring:message code="label.segundoApe"/>:</label>
				</td>
				<td>
					<input type="text" disabled="disabled" id="detalleSegundoApe" value="${integrante.derechohabiente.segundoApellido}" class="form-control"/>
				</td>

				<td align="left">
					<label class="control-label" for="detalleEdad"><spring:message code="label.edad"/>:</label>
				</td>
				<td>
					<input id="edad" type="text" disabled="disabled" id="detalleEdad" value="${edad}" class="form-control"/>
				</td>
						
			</tr>
			<tr>
				<td align="left">
					<label class="control-label" for="detalleCurp"><spring:message code="label.curp"/>:</label>
				</td>
				<td>
					<input type="text" disabled="disabled" id="detalleCurp" value="${integrante.derechohabiente.curp}" class="form-control"/>
				</td>
				
				<td align="left">
					<label class="control-label" for="detallesexo"><spring:message code="label.sexo"/>:</label>
				</td>
				<td>
					<input type="text" disabled="disabled" id="detallesexo" value="${integrante.derechohabiente.sexo.descripcion}" class="form-control"/>
				</td>
				
										
			</tr>
		</tbody>			
	</table>
	</form>
</div>

<div class="separadorseccion">
	<span>
		Datos de vigencia del derechohabiente
	</span>
</div>

<div >
	<form>
		<table  class="table table-striped table-bordered">
			<tr>
				
				<td align="left">
					<label class="control-label" for="detalleEstadoDer">Situaci&oacute;n:</label>
				</td>
				<td>
					<input type="text" disabled="disabled" id="detalleEstadoDer" value="${integrante.estadoDerechohabiente.descripcion}" class="form-control"/>
				</td>	
				<td align="left">
					<label class="control-label" for="detalleVencimientoVig">Vencimiento vigencia:</label>
				</td>
				<td align="right">
					<input type="text" id="detalleVencimientoVig" value='' 
						disabled="disabled" class="form-control" />
				</td>			
			</tr>
			<tr>
				<td align="left">
					<label class="control-label" for="detalleUmf">UMF:</label>
				</td>
				<td >
					<input type="text" id="detalleUmf" value="${integrante.medicoEnTurno.unidadMedicaFamiliar.nombreCorto}" 
						disabled="disabled" class="form-control"/>
					<input type="hidden" id="idUmf" name="idUmf" value="${integrante.medicoEnTurno.unidadMedicaFamiliar.idUMF}"></input>	
				</td>	
				<td align="left">
					<label class="control-label" for="detalleConsultorio">Consultorio:</label>
				</td>
				<td>
					<input type="text" id="detalleConsultorio" value="${integrante.medicoEnTurno.consultorio.descripcion}" disabled="disabled" class="form-control"/>
				</td>
			</tr>
			<tr>
				<td align="left">
					<label class="control-label" for="detalleDelegacion">Delegaci&oacute;n:</label>
				</td>
				<td colspan="3">
					<input id="detalleDelegacion" type="text" value="${integrante.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion}" 
						disabled="disabled" class="form-control"/>
				</td>		
			</tr>
		</table>
	</form>
</div>

<script type="text/javascript">
		$('#edad').val(0);
		var fechaNac = ($("#fechaNacimientoGrupoFamiliar").val()).toString();
	   
	   	var fechaArr = fechaNac.split('/');
		var aho = fechaArr[2];
		var mes = fechaArr[1];
		var dia = fechaArr[0];
		 
		var fecha = new Date(aho, mes - 1, dia);
		var hoy = new Date();
     	   	
		//var milis = hoy.getTime() - fecha.getTime();
     	    //var ed = parseInt((milis)/1000/60/60/24/365);
		var ed = datediff(hoy, fecha);
		$('#edad').val(ed[0]);
		
     	   	
   	 	function datediff(date1, date2) {  		 
			var y1 = date1.getFullYear(), m1 = date1.getMonth(), d1 = date1.getDate(),
			y2 = date2.getFullYear(), m2 = date2.getMonth(), d2 = date2.getDate();
	
			if (d1 < d2) {
					m1--;
					d1 += DaysInMonth(y2, m2);
			}
			if (m1 < m2) {
					y1--;
					m1 += 12;
			}
			
			return [y1 - y2, m1 - m2, d1 - d2];
		}
 		
 		
	  	function DaysInMonth(Y, M) {
 			with (new Date(Y, M, 1, 12)) {
 				setDate(0);
 				return getDate();
 			}
	  	}
			
	</script>