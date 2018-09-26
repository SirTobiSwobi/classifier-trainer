$(document).ready(function(){
				$("#createForm").hide();
				$("#uploadForm").hide();
				$("#deleteConfirm").hide();
				$.getJSON("../documents",function(json){
					//console.log(json.documents[0].label);
					if(json.documents==null){
						$("#docList").append("<h3>There are currently no documents in this microservice. You can add one</h3>");
					}else{
						for (var i=0; i< json.documents.length; i++){
							$("#docList").append("<li><a href=\"document.html?id="+json.documents[i].id+"\">/documents/"+json.documents[i].id+" - "+json.documents[i].label+
							"</a><a href=\"assignment.html?docId="+json.documents[i].id+"\"><button>Assign to category</button></a></li>");
						}
					}
				});
				
				$("#read").click(function() {
					$("#result").empty()
					$("#createForm").hide("slow");
					$("#uploadForm").hide("slow");
					$("#deleteConfirm").hide("slow");
					$("#docList").empty();
					$("#docList").append("<h2>Available documents:</h2>");
					$.getJSON("../documents",function(json){	
						if(json.documents==null){
							$("#docList").append("<h3>There are currently no documents in this microservice. You can add one</h3>");
						}else{
							for (var i=0; i< json.documents.length; i++){
								$("#docList").append("<li><a href=\"document.html?id="+json.documents[i].id+"\">/documents/"+json.documents[i].id+" - "+json.documents[i].label+
							"</a><a href=\"assignment.html?docId="+json.documents[i].id+"\"><button>Assign to category</button></a></li>");
							}
						}
					});
					$("#docList").show("fast");
				});
			
				$("#create").click(function() {
					$("#result").empty()
					$("#docList").hide("slow");
					$("#uploadForm").hide("slow");
					$("#deleteConfirm").hide("slow");
					$("#createForm").show("fast");
				});
				
				$("#upload").click(function() {
					$("#result").empty()
					$("#docList").hide("slow");
					$("#createForm").hide("slow");
					$("#deleteConfirm").hide("slow");
					$("#uploadForm").show("fast");
				});
				
				$("#delete").click(function() {
					$("#result").empty()
					$("#docList").hide("slow");
					$("#uploadForm").hide("slow");
					$("#createForm").hide("slow");
					$("#deleteConfirm").show("fast");
				});
				
				
				$("#createF").submit(function( event ) {
					event.preventDefault();
					//console.log("Uploading data");
					var form = $("#createF").serializeArray();
					//console.log(form);
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
					
				});
				
				$("#uploadF").submit(function( event ) {
					event.preventDefault();
					//console.log("Uploading data");
					var form = $("#uploadF").serializeArray();
					//console.log(form);
					var json = form[0].value;
					//console.log(json);
					
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
					
				});
				
				$("#deleteF").submit(function( event ) {
					event.preventDefault();
					//console.log("Deleting data");
					var form = $("#deleteF").serializeArray();
					//console.log(form);
					var json="";
					if(form[0].value=="delete"){
						var url="../documents";
					
					
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
					
					
					
				});
				
			
			});