<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="dgTrabajadoresRegistro" title="Detalle del Trabajador">
	<div id="wrapperDialogRegistro">
		<form  action="/catalogo/trabajadores/agregar.do" method="post" id="trabajadoresFormRegistro">
	  		<input type="hidden" name="cveTrabajador" id="cveTrabajador"/>
			<fieldset>
				<table>
					<tr>
						<td>
    						<table style="border-collapse: separate; border-spacing:  5px 5px;">
							    <tbody>
									<tr>
										<td>
											<label>Tipo Trabajador:</label>
										</td>
										<td>
											<input type="radio" name="tipoTrabajador" value="1" checked><label>Permanente</label>
											<br>
											<input type="radio" name="tipoTrabajador" value="2"><label>Honorarios</label> 
										</td>
									</tr>	
							      	<tr>
								        <td>
								        	<span class="required">*</span><label>Folio de Corrección:</label>
								        	<label for="folioCorreccion"></label>
								        </td>
								        <td>
								        	<input type="text" id="folioCorreccion" name="folioCorreccion" size="35" maxlength="18" 
								        		onblur="this.value=(this.value).toUpperCase();" onkeyup="validaCampo('noCaracteresEspeciales','folioCorreccion','trabajadoresFormRegistro');"/>
										</td>
								        <td>
								        	<span class="required">*</span><label>Nombre Asegurado:</label>
								        	<label for="nombreAsegurado"></label>
								        </td>
								        <td>
							        		<input type="text" id="nombreAsegurado" name="nombreAsegurado" onkeypress="return KeyPressed(this,event,'validchar', null, 'uppercase=yes', null, 'no');" 
							        			size="35" maxlength="30" >
								        </td>
							      	</tr>
							      	<tr>
				    					<td>
				    						<label>Apellido Paterno:</label>
				    						<label for="apPaternoAsegurado"></label>
				    					</td>
								    	<td>
									      	<input id="apPaternoAsegurado" name="apPaternoAsegurado" onkeypress="return KeyPressed(this,event,'validchar', null, 'uppercase=yes', null, 'no');" 
								      			size="35" maxlength="30" type="text" />
									    </td>
									    <td>
									    	<label>Apellido Materno:</label>
									    </td>
									    <td>
								      		<input id="apMaternoAsegurado" name="apMaternoAsegurado" onkeypress="return KeyPressed(this,event,'validchar', null, 'uppercase=yes', null, 'no');" 
								      			type="text" size="35" maxlength="30" />
									   	</td>
								  	</tr>
								  	<tr> 
									    <td>
									    	<label>N&uacute;mero de Seguro Social:</label>
									    	<label for="nuNss"></label>
									    </td>
									    <td>
								      		<input type="text" name="nuNss" id="nuNss" size="35" maxlength="11"
									      		onkeyup="validaCampo('PermiteSoloNumeros','nuNss','trabajadoresFormRegistro');"/>
									    </td>
									    <td>
									    	<label>RFC:</label>
									    </td>
									    <td>
								      		<input type="text" name="txRfc" id="txRfc" size="35" maxlength="13" 
									      		onkeyup="validaCampo('noCaracteresEspeciales','txRfc','trabajadoresFormRegistro');"/>
									    </td>
							  		</tr>
						  		</tbody>
					  		</table>
						</td>
					</tr>
				</table>
			</fieldSet>
		</form>
	</div>
</div>