/**
 * 
 */
var panelesCtrl = {
	init: function() {
		$("#pnlPrivado").click(function() {
			$("#pnlPrivado").css("border-color", "green");
			$("#pnlPublico").css("border-color", "");

			$("#pnlPrivado").css("box-shadow","3px 3px 10px rgba(0,0,0,0.6)");
			$("#pnlPublico").css("box-shadow", "");

			$("#imgPriv").addClass("hidden");
			$("#imgPrivSel").removeClass("hidden");
			$("#imgPublicSel").addClass("hidden");
			$("#imgPublic").removeClass("hidden");

			$("#msgObra").addClass("hidden");
			tipoObra = 1;

		});

		$("#pnlPublico").click(function() {
			$("#pnlPublico").css("border-color", "green");
			$("#pnlPrivado").css("border-color", "");

			$("#pnlPublico").css("box-shadow","3px 3px 10px rgba(0,0,0,0.6)");
			$("#pnlPrivado").css("box-shadow", "");

			$("#imgPublic").addClass("hidden");
			$("#imgPublicSel").removeClass("hidden");
			$("#imgPrivSel").addClass("hidden");
			$("#imgPriv").removeClass("hidden");

			$("#msgObra").addClass("hidden");
			tipoObra = 2;

		});

		$("#pnlPropietario").click(function() {
			$("#pnlPropietario").css("border-color", "green");
			$("#pnlPropietario").css("box-shadow","3px 3px 10px rgba(0,0,0,0.6)");

			// propietario seleccionado
			$("#propietary").addClass("hidden");
			$("#propietarySel").removeClass("hidden");

			$("#contratist").removeClass("hidden");
			$("#contratistSel").addClass("hidden");

			$("#subcontratist").removeClass("hidden");
			$("#subcontratistSel").addClass("hidden");

			$("#intermediary").removeClass("hidden");
			$("#intermediarySel").addClass("hidden");

			$("#pnlContratista").css("border-color", "");
			$("#pnlContratista").css("box-shadow","");

			$("#pnlSubcontratista").css("border-color", "");
			$("#pnlSubcontratista").css("box-shadow", "");

			$("#pnlIntermediario").css("border-color", "");
			$("#pnlIntermediario").css("box-shadow", "");

			$("#txtPropietary").removeClass("hidden");
			$("#txtContratist").addClass("hidden");
			$("#txtSubcontratist").addClass("hidden");
			$("#txtIntermediary").addClass("hidden");

			$("#msgPatron").addClass("hidden");
			$("#numProcedimiento").addClass("hidden");
			$("#pnlObjetoContrato").addClass("hidden");
			tipoPatron = "1";
		});

		$("#pnlContratista").click(function() {
			$("#pnlContratista").css("border-color", "green");
			$("#pnlContratista").css("box-shadow","3px 3px 10px rgba(0,0,0,0.6)");

			$("#contratist").addClass("hidden");
			$("#contratistSel").removeClass("hidden");

			$("#propietary").removeClass("hidden");
			$("#propietarySel").addClass("hidden");

			$("#subcontratist").removeClass("hidden");
			$("#subcontratistSel").addClass("hidden");

			$("#intermediary").removeClass("hidden");
			$("#intermediarySel").addClass("hidden");

			$("#pnlPropietario").css("border-color", "");
			$("#pnlPropietario").css("box-shadow","");

			$("#pnlSubcontratista").css("border-color", "");
			$("#pnlSubcontratista").css("box-shadow", "");

			$("#pnlIntermediario").css("border-color", "");
			$("#pnlIntermediario").css("box-shadow", "");

			$("#txtPropietary").addClass("hidden");
			$("#txtContratist").removeClass("hidden");
			$("#txtSubcontratist").addClass("hidden");
			$("#txtIntermediary").addClass("hidden");

			$("#msgPatron").addClass("hidden");
			$("#pnlObjetoContrato").addClass("hidden");

			tipoPatron = "2";

			if (tipoObra == 2) {
				$("#numProcedimiento").removeClass("hidden");
			} else {
				$("#numProcedimiento").addClass("hidden");
			}

		});

		$("#pnlSubcontratista").click(function() {
			$("#pnlSubcontratista").css("border-color","green");
			$("#pnlSubcontratista").css("box-shadow","3px 3px 10px rgba(0,0,0,0.6)");

			$("#subcontratist").addClass("hidden");
			$("#subcontratistSel").removeClass("hidden");

			$("#contratist").removeClass("hidden");
			$("#contratistSel").addClass("hidden");

			$("#propietary").removeClass("hidden");
			$("#propietarySel").addClass("hidden");

			$("#intermediary").removeClass("hidden");
			$("#intermediarySel").addClass("hidden");

			$("#pnlContratista").css("border-color", "");
			$("#pnlContratista").css("box-shadow", "");

			$("#pnlPropietario").css("border-color", "");
			$("#pnlPropietario").css("box-shadow", "");

			$("#pnlIntermediario").css("border-color", "");
			$("#pnlIntermediario").css("box-shadow", "");

			$("#txtSubcontratist").removeClass("hidden");
			$("#txtContratist").addClass("hidden");
			$("#txtPropietary").addClass("hidden");
			$("#txtIntermediary").addClass("hidden");

			$("#msgPatron").addClass("hidden");
			// $("#numProcedimiento").addClass("hidden");
			$("#pnlObjetoContrato").addClass("hidden");
			tipoPatron = "3";

			if (tipoObra == 2) {
				$("#numProcedimiento")
						.removeClass("hidden");
			} else {
				$("#numProcedimiento").addClass("hidden");
			}
		});

		$("#pnlIntermediario").click(function() {
			$("#pnlIntermediario").css("border-color", "green");
			$("#pnlIntermediario").css("box-shadow","3px 3px 10px rgba(0,0,0,0.6)");

			$("#intermediary").addClass("hidden");
			$("#intermediarySel").removeClass("hidden");

			$("#subcontratist").removeClass("hidden");
			$("#subcontratistSel").addClass("hidden");

			$("#contratist").removeClass("hidden");
			$("#contratistSel").addClass("hidden");

			$("#propietary").removeClass("hidden");
			$("#propietarySel").addClass("hidden");

			$("#pnlPropietario").css("border-color", "");
			$("#pnlPropietario").css("box-shadow","");

			$("#pnlContratista").css("border-color", "");
			$("#pnlContratista").css("box-shadow","");

			$("#pnlSubcontratista").css("border-color", "");
			$("#pnlSubcontratista").css("box-shadow", "");

			$("#txtIntermediary").removeClass("hidden");
			$("#txtContratist").addClass("hidden");
			$("#txtPropietary").addClass("hidden");
			$("#txtSubcontratist").addClass("hidden");

			$("#msgPatron").addClass("hidden");
			// $("#numProcedimiento").addClass("hidden");
			$("#pnlObjetoContrato").removeClass("hidden");
			
			tipoPatron = "4";

			if (tipoObra == 2) {
				$("#numProcedimiento").removeClass("hidden");
			} else {
				$("#numProcedimiento").addClass("hidden");
			}
		});
	}
}