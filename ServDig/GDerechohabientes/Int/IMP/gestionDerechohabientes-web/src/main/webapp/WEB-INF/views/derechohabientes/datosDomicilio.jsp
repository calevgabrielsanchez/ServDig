<%@ include file="/WEB-INF/views/general/taglibs.jsp" %>

<center>
<table  class="page_holder_no_height" width="100%">
	<tr>						
		<td>
			<label for="domicilio.vialidadPrimaria.nombre" style="width: 150px">
					Calle&nbsp;:&nbsp;
			</label>
		</td>
		<td colspan="7">
			<input type="text" readonly="readonly" style="width: 650px;" value="${miGrupoFamiliar.domicilio.vialidadPrimaria.nombre}" />
		</td>
	</tr>
	<tr>
			<td>
			<label for="domicilio.numExteriorAlf">
				N&uacute;mero/Letra Exterior&nbsp;:&nbsp;
			</label>
		</td>
		<td>
			<input type="text" readonly="readonly" style="width: 50px;" value="${miGrupoFamiliar.domicilio.numExteriorAlf}" />
		</td>
		<td>
			<label for="domicilio.numInteriorAlf">
				N&uacute;mero/Letra Interior&nbsp;:&nbsp;
			</label>
		</td>
		<td>
			<input type="text" readonly="readonly" style="width: 50px;" value="${miGrupoFamiliar.domicilio.numInteriorAlf}" />
		</td>	
	</tr>
	
	<tr>
		<td colspan="8">&nbsp;&nbsp;</td>
	</tr>
	<tr>
	<tr>
		<td colspan="8" align="center" ><strong>Datos del asentamiento</strong></td>
	</tr>
	<tr><td colspan="8">&nbsp;&nbsp;</td></tr>
	<tr>		
		<td >		
			<label for="domicilio.asentamiento.nombre"  style="width: 150px">										
				Colonia(Asentamiento)&nbsp;:&nbsp;
			</label>
		</td>
		<td  colspan="3">
			<input type="text" readonly="readonly" value="${miGrupoFamiliar.domicilio.asentamiento.nombre}" />
		</td>	
		<td>		
			<label for="domicilio.asentamiento.localidad.nombre">										
				Localidad&nbsp;:&nbsp;
			</label>
		</td>			
		<td  colspan="3">
			<input type="text" readonly="readonly" value="${miGrupoFamiliar.domicilio.asentamiento.localidad.nombre}" />
		</td>
	</tr>
	<tr>					
		<td >		
			<label for="domicilio.asentamiento.localidad.municipio.nombre"  style="width: 150px">								
				Municipio o delegaci&oacute;n&nbsp;:&nbsp;
			</label>
		</td>
		<td  colspan="3">
			<input type="text" readonly="readonly" value="${miGrupoFamiliar.domicilio.asentamiento.localidad.municipio.nombre}" />
		</td>
		<td >
			<label for="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre">
				Entidad Federativa&nbsp;:&nbsp;
			</label>
		</td>
		<td  colspan="3">
			<input type="text" readonly="readonly" value="${miGrupoFamiliar.domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre}" />
		</td>
	</tr>
	<tr>
		<td>
			<label for="domicilio.codigoPostal.codigoPostal"  style="width: 150px">
				C&oacute;digo Postal&nbsp;:&nbsp;
			</label>
		</td>
		<td colspan="3">
			<input type="text" readonly="readonly" value="${miGrupoFamiliar.domicilio.codigoPostal.codigoPostal}" />
		</td>
		<td colspan="4"></td>
	</tr>
</table>
</center>