$(document).ready(
		function() {
			var wrapperFisico = $('form#individuoFisicoForm').toObject();
			parent.fisica=wrapperFisico;
//			alert("Wrapper: "+wrapperFisico.nombre+wrapperFisico.primerApellido+wrapperFisico.segundoApellido);
//			var invoker = parent.IndividuoFisicoInvoker;
//			if(invoker != null){
//				invoker.fisica = wrapperFisico;
//				invoker.cerrar();
//			}
		});
