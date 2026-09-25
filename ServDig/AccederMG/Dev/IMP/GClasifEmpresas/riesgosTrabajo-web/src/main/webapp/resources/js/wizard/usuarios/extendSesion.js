var estenderSesion = {
    validaSesion: 0,
    init: function() {
        setTimeout(llamadaSesion,900000);
    }
};

function llamadaSesion(){
    estenderSesion.validaSesion = 0;
    $('#confirmarCerrarSesion').modal('show');
    setTimeout(() => {
        responseNull(estenderSesion.validaSesion)
    }, 60000);
}

function refrescarSesion(){
    estenderSesion.validaSesion = 1;
    $('#confirmarCerrarSesion').modal('hide');
    $.blockUI();
    refrescaSesion(getCookie("iPlanetDirectoryPro"));
    estenderSesion.init();
}

function responseNull(responseSesion){
    /*
     * Ejecución de cierre de sesión.
     */
    if(responseSesion == 0){
        $('#confirmarCerrarSesion').modal('hide');
        cerrarSesion();
    }
}

$(document).ready(function() {
    estenderSesion.init();
});