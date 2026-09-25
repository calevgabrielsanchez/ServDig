$(document).ready(function() {
//	$('#btnContinuar').click(function() {
//		var ctrl = parent.AcuseCtrl;
//
//		if (ctrl != null) {
//			ctrl.cerrar();
//		} else {
//			$("#formaRegresoDetalleFin").submit();
//		}
//	});
});

function recuperaCargaAcuse(){
	$("#formaRegresoDetalle").submit();
	$("#formaRegresoDetalleFin").submit();
}

function regresaDetalleSO(){
	$("#formaRegresoDetalle2").submit();
}

function recuperaAvisoMod(){
	$("#formaGeneraAviso").submit();
}

function back() {
	history.back();
}
