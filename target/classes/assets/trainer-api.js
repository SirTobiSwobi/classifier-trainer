function renderDocuments(){
	$("#list").empty();
	$("#list").append("<h2>Available documents:</h2>");
	$.getJSON("../documents",function(json){	
		if(json.documents==null){
			$("#list").append("<h3>There are currently no documents in this microservice. You can add one</h3>");
		}else{
			for (var i=0; i< json.documents.length; i++){
				$("#list").append("<li><a href=\"document.html?id="+json.documents[i].id+"\">/documents/"+json.documents[i].id+" - "+json.documents[i].label+
						"</a> - <a href=\"assignment.html?docId="+json.documents[i].id+"\" class=\"primary\"><button>Assign to category</button></a></li>");
			}
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
	    	console.log('something worked');
		 	console.log('succes: '+data);
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
	    	console.log('something worked');
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
	    	console.log('something worked');
		 	console.log('succes: '+data);
		}
	 });
}
