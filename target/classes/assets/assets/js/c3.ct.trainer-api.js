function getIds(array){
	var ids = new Array();
	for(var i=0;i<array.length;i++){
		ids.push(array[i].id);
	}
	return ids;
}

function getIdObject(){
	
	var pageURL = window.location.search.substring(1);
	var urlVariables = pageURL.split('&');
	var assId, docId, catId;
	for(var i=0; i< urlVariables.length; i++){
		var paramName = urlVariables[i].split('=');
		if(paramName[0] == "assId"){
			assId = paramName[1];
		}else if(paramName[0] == "docId"){
			docId = paramName[1];
		}else if(paramName[0] == "catId"){
			catId = paramName[1];
		}
	}
	
	var output = {assId: assId, docId: docId, catId: catId};
	return output;
	
}

function deleteFromArray(array, value){
	var index = array.indexOf(value);
	if(index>-1){
		array.splice(index, 1);
	}
	return array;
}

function buildArrayFromIds(allEntries,ids){
	var output = new Array();
	for(var i=0;i<allEntries.length;i++){
		if(ids.includes(allEntries[i].id)){
			output.push(allEntries[i]);
		}
	}
	return output;
}

function getObjectById(allObjects, id){
	var output=null;
	for(var i=0; i<allObjects.length; i++){
		if(allObjects[i].id==id){
			output=allObjects[i];
		}
	}
	return output;
}

function idIsSet(id){
	var returnValue=true;
	if(typeof id == "undefined" || id == null || id == -1){
		returnValue=false;
	}
	return returnValue;
}

function renderDocuments(){
	$("#list").empty();
	$("#list").append("<h2>Available documents:</h2>");
	$.getJSON("../documents",function(json){	
		if(json.documents==null){
			$("#list").append("<h3>There are currently no documents in this microservice. You can add one</h3>");
		}else{
			for (var i=0; i< json.documents.length; i++){
				$("#list").append("<li><a href=\"document.html?docId="+json.documents[i].id+"\">/documents/"+json.documents[i].id+" - "+json.documents[i].label+
						"</a> - <a href=\"assignment.html?docId="+json.documents[i].id+"\" class=\"button next\">Assign to category</a></li>");
			}
		}
	});
}

function renderDocument(docId){
	$("#list").empty();
	$("#list").append("<h2>Available document:</h2>");
	$.getJSON("../documents/"+docId,function(json){
		if(json==null){
			$("#list").append("<h3>The document with id "+docId+" does not exist. You can create it</h3>");
		}else{
			$("#list").append("<h3>Id: "+json.id+": "+json.label+"</h3>");
			$("#list").append("<p>URL: "+json.url+"</p>");
			$("#list").append("<p>");
			$("#list").append(json.content);
			$("#list").append("</p>");
		}
	});
}

function createDocument(form){
	var json = "{ \"documents\":[{\"id\":"+form[0].value+
	",\"label\":\""+form[1].value;
	json = json+"\",\"content\":\"";
	json = json+form[2].value.replace(/(\r\n|\n|\r)/gm," ");;
	json = json+"\",\"url\":\""+form[3].value+"\"}]}";
	console.log(json);
	
	var url="../documents";
	
	
	$.ajax({
		url: url,
		headers: {
		    'Accept': 'application/json',
	        'Content-Type':'application/json'
	    },
	    method: 'POST',
	    dataType: 'json',
	    data: json,
	    success: function(data){
		 	console.log('succes: '+data);
		}
	 });
}

function updateDocument(form,docId){
	var json = "{\"id\":"+form[0].value+
	",\"label\":\""+form[1].value;
	json = json+"\",\"content\":\"";
	json = json+form[2].value.replace(/(\r\n|\n|\r)/gm," ");;
	json = json+"\",\"url\":\""+form[3].value+"\"}";
	console.log(json);
	
	var url="../documents/"+docId;
	
	
	$.ajax({
		url: url,
		headers: {
		    'Accept': 'application/json',
	        'Content-Type':'application/json'
	    },
	    method: 'PUT',
	    dataType: 'json',
	    data: json,
	    success: function(data){
			 console.log('succes: '+data);
		}
	 });
}

function renderDocumentUpdate(docId){
	$.getJSON("../documents/"+docId,function(json){
		if(json==null){
			//nothing but empty fields
		}else{
			$("#id").val(json.id);
			$("#label").val(json.label);
			$("#url").val(json.url);
			$("#content").empty();
			$("#content").append(json.content);
		}
	});
}

function uploadDocumentJSON(json){
	var url="../documents";	
	$.ajax({
		url: url,
		headers: {
		    'Accept': 'application/json',
	        'Content-Type':'application/json'
	    },
	    method: 'POST',
	    dataType: 'json',
	    data: json,
	    success: function(data){
		 	console.log('succes: '+data);
		}
	 });
}

function deleteAllDocuments(){
	var url="../documents";	
	var json="";
	$.ajax({
		url: url,
		headers: {
		    'Accept': 'application/json',
	        'Content-Type':'application/json'
	    },
	    method: 'DELETE',
	    dataType: 'json',
	    data: json,
	    success: function(data){
		 	console.log('succes: '+data);
		}
	 });
}

function deleteDocument(docId){
	var url="../documents/"+docId;
	var json="";
	
	$.ajax({
		url: url,
		headers: {
		    'Accept': 'application/json',
	        'Content-Type':'application/json'
	    },
	    method: 'DELETE',
	    dataType: 'json',
	    data: json,
	    success: function(data){
			 	console.log('succes: '+data);
		}
	 });
}

function renderCategories(){
	$("#list").empty();
	$("#list").append("<h2>Available categories:</h2>");
	$.getJSON("../categories",function(json){
	
	if(json.categories==null){
		$("#list").append("<h3>There are currently no categories in this microservice. You can add one</h3>");
	}else{
		for (var i=0; i< json.categories.length; i++){
			$("#list").append("<li><a href=\"category.html?id="+json.categories[i].id+"\">/categories/"+json.categories[i].id+" - "+json.categories[i].label+
			"</a> - <a href=\"assignment.html?catId="+json.categories[i].id+"\" class=\"button next\">Assign documents</a></li>");
			}
		}
	});
}

function createCategory(form){
	var json = "{ \"categories\":[{\"id\":"+form[0].value+
		",\"label\":\""+form[1].value;
	json = json+"\",\"description\":\"";
	json = json+form[2].value.replace(/(\r\n|\n|\r)/gm," ")+"\"}]}";
	var url="../categories";
	$.ajax({
		url: url,
		headers: {
		    'Accept': 'application/json',
	        'Content-Type':'application/json'
	    },
	    method: 'POST',
	    dataType: 'json',
	    data: json,
	    success: function(data){
			 	console.log('succes: '+data);
		}
	 });
}

function uploadCategoryJSON(form){
	var json = form[0].value;
	var url="../categories";
	$.ajax({
		url: url,
		headers: {
		    'Accept': 'application/json',
	        'Content-Type':'application/json'
	    },
	    method: 'POST',
	    dataType: 'json',
	    data: json,
	    success: function(data){
	    	console.log('succes: '+data);
		}
	});
}

function deleteAllCategories(){
	var url="../categories";
	var json="";

	$.ajax({
		url: url,
		headers: {
		    'Accept': 'application/json',
	        'Content-Type':'application/json'
	    },
	    method: 'DELETE',
	    dataType: 'json',
	    data: json,
	    success: function(data){
	    	console.log('succes: '+data);
		}
	 });
}
