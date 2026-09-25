function validaCurp(nombre, primerApe, segundoApe, fechaNac, idLugarNacimiento, curp, idSexo, funcio) {
	
	fisica = {
		'nombre': nombre,
		'primerApellido': primerApe,
		'segundoApellido': segundoApe,
		'fechaNacimiento': fechaNac,
		'curp': curp,
		'lugarNacimiento': {
			'clave': idLugarNacimiento
		},
		'sexo': {
			'idSexo': idSexo
		}
	}
	
	$.postJSON(context_path+'/derechohabientesUtil/validaCurpRenapo', fisica);
	
}
