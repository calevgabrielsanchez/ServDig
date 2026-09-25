<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<div id="dgPromocionBuscar"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;" >
<form action="" method="post" id="formBuscaPromo" >
<table>
	<tr>
		<td>
			Clasificación
		</td>
		
		<td>
			Fuente
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
		idHtmlContenedor="formBuscaPromo" param="idTipo" paramValue="5,6,7,8"
		 />
	<td>
			<select id="findFuente" autofocus="autofocus"  name="findFuente"  style="width:250px; font-family: Verdana;	font-size: 9px;	color: #000000;	letter-spacing : 0px;	line-height : 12px;	font-weight: bold;" >
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
<table id="dtBuscarPromociones"  style="width: 1000px" >
	
			 <thead>
                   <tr>
                   				
                   				<th colspan="1" rowspan="1" width="6%" >Selección</th>
                   				<th colspan="1" rowspan="1" width="10%">Clasificación</th>
                   				<th colspan="1" rowspan="1" width="10%">Fuente</th>
                                <th colspan="1" rowspan="1" width="10%">Folio</th>
                                <th colspan="1" rowspan="1" width="7%">RP</th>
                                 <th colspan="1" rowspan="1" width="10%">Modalidad</th>
                                <th colspan="1" rowspan="1" width="20%">Nombre</th>
                                <th colspan="1" rowspan="1" width="5%">AFIL 15</th>
                                <th colspan="1" rowspan="1" width="7%">SP</th>
                                <th colspan="1" rowspan="1" width="7%">OI</th>
                                <th colspan="1" rowspan="1" width="7%">PR</th>
                   </tr>
                               </thead>
               
	</table>
	<table>
		<tr>
			<td><input type="button" value="Salir" onclick="salirDetallePromocion();"/> </td>
			<td><input type="button" value="Detalle" name="detalle" id="detalle" onclick="cargaDetallePromocion();" /></td>
			
			
		</tr>
	</table>
	 </form>
</div>



