/* ------------------------------------------------------------------------
	Función: 	fnTrim
	Uso: 		Elimina espacios, (funcionará en navegadores que acepten expresiones regulares)
	Autor: 		Juan Manuel López Lozano
------------------------------------------------------------------------- */

function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}
function ltrim(stringToTrim) {
	return stringToTrim.replace(/^\s+/,"");
}
function rtrim(stringToTrim) {
	return stringToTrim.replace(/\s+$/,"");
}