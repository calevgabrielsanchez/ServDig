<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<div id="dgCorreccionBuscar"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;" >
<form action="" method="post" id="formCorreccionBuscar" >
<table>
	<tr>
		<td>
			Tipo
		</td>
		
		<td>
			Origen
		</td>
		
		<td>
			Folio
		</td>
		<td>
			Registro Patronal
		</td>
		<td>
			Nombre
		</td>
		<td>
		 	AFIL15
		</td>
		
	</tr>
	<tr>
	<td>
	<combo:creaCombo  
		entidad="mx.gob.imss.ctirss.correccion.model.CgcCatTipo" 
		idHtml="findClasificacion"
		idHtmlContenedor="formCorreccionBuscar" param="idTipo" paramValue="1,2,3,4"
		 />
	<td>
			<select id="findFuente" name="findFuente" style="width:250px; font-family: Verdana;	font-size: 9px;	color: #000000;	letter-spacing : 0px;	line-height : 12px;	font-weight: bold;">
  			 	<option value="">--Por favor seleccione--</option>
  			</select>
		</td>
		</td>
		<td> 
			<input type="text" id="findFolio" name="findFolio" size="20"> 
		</td>
		<td> 
			<input type="text" id="findRP" name="findRP" size="20"> 
		</td>
		<td> 
			<input type="text" id="findNombre" name="findNombre" size="20">
		</td>
		<td> 
			<input type="text" id="findAfil" name="findAfil" size="20"> 
		</td>
		<td>
			<input type="button" onclick="enviaAPaginar();" value="Buscar">
		</td>
	</tr>


</table>
<table id="dtBuscarCorrecciones"  style="width: 850px" >
	
			 <thead>
                   <tr>
                   				<th colspan="1" rowspan="1" width="5%">Selección</th>
                   				<th colspan="1" rowspan="1" width="5%">Tipo</th>
                   				<th colspan="1" rowspan="1" width="10%">Origen</th>
                   				<th colspan="1" rowspan="1" width="10%">Folio</th>
                                <th colspan="1" rowspan="1" width="10%">RP</th>
                                <th colspan="1" rowspan="1" width="10%">Nombre</th>
                                <th colspan="1" rowspan="1" width="10%">Status</th>
                                <th colspan="1" rowspan="1" width="5%">Del</th>
                                <th colspan="1" rowspan="1" width="5%">Al</th>
                   </tr>
                               </thead>
               
	</table>
	<table>
		<tr>
			<td><input type="button" value="Salir" onclick="salirDetalleCorreccion();"/> </td>
			<td><input type="button" value="Detalle"  onclick="cargaDetalleCorreccion();"/></td>
			
			
		</tr>
	</table>
	 </form>
</div>



