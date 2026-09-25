function selectTramite(idTramite, propietarioTarea, idTarea) {
	fetchSolicitudSeguimiento("consultaSolicitud", module, idTramite, propietarioTarea, idTarea);
	module.updateModel("AlertComponent", "alert", {level : "info",	message : ""});
}

function fetchSolicitudSeguimiento(componentId, module, idTramite, esPropietario, idTarea) {
	var url = "atencionResponsable/obtenerSolicitud";
	var params = {
		idTramite : idTramite,
		esPropietario : esPropietario
	};
	if (url !== null) {
		$.ajax({
			url : url,
			data : JSON.stringify(params),
			type : "POST",
			contentType : "application/json; charset=UTF-8",
			dataType : "json",
			success : function(data) {
				data.idTarea = idTarea;
				module.updateModel("CardLayoutComponent","responsableCardLayout", 1);
				module.updateModel("ConsultaSolicitudComponent", componentId, data);
				module.controller.checkEstadoResponsable(data,componentId);
		        $("#modal").modal('hide');
		        $('div.modal-backdrop.fade').remove();
			},
			error : function(errMsg) {
				$("#modal").modal('hide');
				$('div.modal-backdrop.fade').remove();
				if (errMsg === null) {
					errMsg = "create.error";
				}
				module.updateModel("alert", {level : "danger", message : errMsg});
			}
		});
	}
}