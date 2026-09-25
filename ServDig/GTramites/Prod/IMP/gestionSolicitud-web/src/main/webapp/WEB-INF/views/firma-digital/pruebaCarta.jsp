<form name = "terminos" id="terminos" action="/gestionSolicitud-web/firma-digital/cartaTerminos" method="POST">
	<table>
		<tr>
			<td>Cadena original</td>
			<td>
				<textarea name="cadenaOriginal" id="cadenaOriginal"></textarea>
			</td>
			
		</tr>
		<tr>
			<td>Sello digital</td>
			<td><textarea name="selloDigital" id="selloDigital"></textarea></td>
			
		</tr>
		<tr>
			<td>Se firma el documento con fiel</td>
			<td><textarea name="firmaDocto" id="firmaDocto"></textarea></td>
			
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="Enviar"></td>
		</tr>
	</table>
</form>