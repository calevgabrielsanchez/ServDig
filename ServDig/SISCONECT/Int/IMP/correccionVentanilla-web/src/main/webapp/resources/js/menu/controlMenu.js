function agregarMenuHijo(nombreMenu,liga,idItem,padre){
		var menuSeleccionado = document.getElementById(padre);
		
		if(liga==null || liga=='') liga = "#";
		
		if(menuSeleccionado.title=='true'){
			menuSeleccionado.innerHTML = menuSeleccionado.innerHTML
			+" <li id="+idItem+" title='false'><a href="+liga+ " id='"+idItem+"-href' class='topLine'>"+nombreMenu+"</a></li>";
			
		}else{
			

			linkActualizar = document.getElementById( (padre.toString() + "-href") );

			var tipoClaseAsignada = trim(linkActualizar.className.toString());
			
			if(tipoClaseAsignada=="sub"){
			
				menuSeleccionado = document.getElementById( ("padre-"+padre) );
				
				
				menuSeleccionado.innerHTML = menuSeleccionado.innerHTML
				+" <li id="+idItem+" title='false'><a href="+liga+ " id="+idItem+"-href class='topLine'>"+nombreMenu+"</a></li>";
			}else{	

				linkActualizar.className='sub';

				menuSeleccionado.innerHTML = menuSeleccionado.innerHTML
				+" <ul title='true' id='padre-"+padre+"'>"
				+" <li id="+idItem+" title='false'><a href="+liga+ " id="+idItem+"-href class=\"topline\">"+nombreMenu+"</a></li>"
				+" </ul>";

			}
		}
		//alert('menuSeleccionado.innerHTML='+menuSeleccionado.innerHTML.toString());
	}
	function agregarMenuPadre(nombreMenu,liga,idItem){

		var mainMenuTree = document.getElementById("menu");
		
		if(liga==null || liga=='') { 
			liga = "#";
			mainMenuTree.innerHTML = mainMenuTree.innerHTML
			+" <li><a href="+liga+ " class=\"menulink\">"+nombreMenu+"</a>"
			+" <ul title='true' id="+idItem+"></ul>"
			+" </li>";
		} else {
			mainMenuTree.innerHTML = mainMenuTree.innerHTML
			+" <li><a href="+liga+ " class=\"menulink\">"+nombreMenu+"</a>"
			+" </li>";
		}
		//alert('mainMenuTree.innerHTML='+mainMenuTree.innerHTML.toString());

	}

	function trim (myString)
	{
		return myString.replace(/^\s+/g,'').replace(/\s+$/g,'');
	}
	



