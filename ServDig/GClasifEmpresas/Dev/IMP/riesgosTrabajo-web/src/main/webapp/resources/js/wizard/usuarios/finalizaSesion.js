var finalizaSesion = {
    init: function() {
        $('#cerrarSesion').modal('show');
        $('#cerrarSesion').modal({backdrop: 'static', keyboard: false});

        setTimeout(cerrarSesion(),8000);
    }
};

$(document).ready(finalizaSesion.init);