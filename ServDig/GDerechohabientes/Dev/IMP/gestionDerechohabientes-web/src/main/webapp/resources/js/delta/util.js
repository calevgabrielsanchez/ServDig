function openModal(urlDest) {
	window.showModalDialog(urlDest, null, "dialogWidth:1000px;dialogHeight:600px;status=yes,toolbar=no,menubar=no,location=no");
	send('fin.do', '#cuerpo');
	
}

function validaMarca() {
	var marca = document.forms[0].cambioLey.checked;
	if (marca == true) {
		this.forms.getElementById("Continuar").disabled=false;
 	} else {
 		this.forms.getElementById("Continuar").disabled=true;
 	}
}