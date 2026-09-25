$( document ).ready(function() {
    console.log('entra a la rutina');
    refrescaSesion(getCookie("iPlanetDirectoryPro"));

});

function refrescaSesion(tokenid){
    console.log('tokenid:' + tokenid )
    var urlOpenAM = '${mvn.url.openAM}';
    var refresh = true;
    var url = '/openam_10.0.0/identity/isTokenValid';
    var params = {'tokenid': tokenid , 'refresh':refresh};
    console.log('llama a ajax: ' + 'http://' + urlOpenAM + url);

    $.ajax({
        data : params,
        type : 'GET',
        url : 'http://' + urlOpenAM + url,
        contentType: 'application/json; charset=utf-8',
        crossDomain: true,
        dataType: 'jsonp'
    }).done(
        function(data) {
            console.log(data);
        });

    console.log('Llamó Ajax');
    $.unblockUI();
}

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function cerrarSesion(){
    $('#salida').on("load", function() {
        setTimeout(function () {
            window.location.replace(context_path + "/j_spring_security_logout");
        }, 1000 );
    });
    $('#salida').attr('src',context_path + "/home/cerrarSesion");
}

async function connection(){
    var url = busquedaRTTCtrl.context + '/historialRiesgoTrabajo/connection'
    var respuesta = false;

    var opciones = {
        titulo: 'Sesi\u00F3n',
        mensaje:"Intermitencia en la comunicaci\u00F3n con la Base de Datos. Favor de ingresar nuevamente."
    };

    const connectResponse = await fetch(url).then(function (response) {
        if (response.ok) {
            return respuesta = response.ok;
        } else {
            console.log("No se estableció la conexión con Base de datos:" + error.message);
            return respuesta;
        }
    }).catch(function (error) {
        console.log("No se estableció la conexión con Base de datos:" + error.message);
        return respuesta;
    });

    if (!connectResponse){
        dialogosCtrl.abrirDialogo(opciones);
        //setTimeout(cerrarSesion(),3000);
    }

    return respuesta;
}
