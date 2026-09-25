   var cntPresionada = false; 
   var altPresionada = false; 

   ns4 = (document.layers)? true:false; 
   ie4 = (document.all)? true:false; 
   
   document.onkeydown = keyDown; 
   if (ns4) document.captureEvents(Event.KEYDOWN); 

   function keyDown(e){ 

		var tecla, res = true; 
		if (ns4) tecla = e.which; 
		if (ie4) tecla = event.keyCode; 
	
		switch(tecla){ 
		 case 17: 
		  cntPresionada = true; 
		  break; 
		 case 86: 
		  if (cntPresionada){ 
		  	//CRT+V
		   res = false; 
		  } 
		  break; 
		 default: 
		  altPresionada = false; 
		  cntPresionada = false; 
		  break; 
		} 
		return res; 
   } 