function findRootId(categories, assignments){
	var rootId=0;
	var maxImplicit=0;
	for(var i=0;i<categories.length;i++){
		console.log("category "+categories[i].id+" - "+categories[i].label);
		var implicits=0;
		for(var j=0; j<assignments.length; j++){
			if(assignments[j].categoryId==categories[i].id&&assignments[j].id==-1){
				implicits++;
			}
		}
		console.log("has "+implicits+" implicit assignments. Previous Maximum: "+maxImplicit);
		if(implicits>maxImplicit){
			console.log("assigning root role to category "+categories[i].id);
			maxImplicit=implicits;
			rootId = categories[i].id;
		}
		
	}
	return rootId;
}

function getCategoryAssignmentArray(assignments, catId){
	var output = new Array();
	for(var i=0;i<assignments.length;i++){
		if(assignments[i].categoryId==catId){
			output.push(assignments[i]);
		}
	}
	return output;
}

function renderTargetFunction(categoryDisplay, categoryDescendants, categoryEqualities, categoryAssignments, documents){
	if(categoryDisplay.length==0){
		$("#assList").append("<h3>There is currently no target function in this microservice. You can add one</h3>");
	}else{
		for(var i=0;i<categoryDisplay.length;i++){
			var appendString = "<div id=\"cat"+categoryDisplay[i].id+"\">";
			appendString += "Category "+categoryDisplay[i].label;
			if(categoryEqualities[i].length>0){
				appendString += " equal to ";
				for(var j=0;j<categoryEqualities[i].length;j++){
					var eq = getObjectById(categoryDisplay, categoryEqualities[i][j]);
					appendString += "<a href=\"#cat"+eq.id+"\">"
					appendString += eq.label+"</a>";
					if(j<categoryEqualities[i].length-1){
						appendString += ", ";
					}
					
				}
			}
			appendString += "<br/>";
			if(categoryAssignments[i].length>0){
				appendString += "Documents: </br>";
				for(var j=0; j<categoryAssignments[i].length; j++){
					var document = getObjectById(documents, categoryAssignments[i][j].documentId);
					if(categoryAssignments[i][j].id==-1){
						appendString += "Implicit: ";
					}else{
						appendString += "Direct: ";
					}
					appendString += "<a href=\"document.html?id="+document.id+"\">"
					appendString += document.label+"</a>"
					appendString += "<br/>";
				}
			}
			appendString += "</div>";
			$("#assList").append(appendString);
		}
		
	}
	
}

function readTargetFunction(){
	console.log("Reading the target function");
	var categoryJSON;
	var documentJSON;
	var relationshipJSON;
	var targetfunctionJSON;
	
	$.getJSON("../categories",function(json){
		categoryJSON = json;
		console.log(categoryJSON);
	}).done(function(){
		$.getJSON("../documents",function(json){
			documentJSON = json;
			console.log(documentJSON);
		}).done(function(){
				$.getJSON("../relationships",function(json){
				relationshipJSON = json;
				console.log(relationshipJSON);
			}).done(function(){
				$.getJSON("../targetfunction",function(json){
					targetfunctionJSON = json;
					console.log(targetfunctionJSON);
				}).done(function(){
					var rootId=findRootId(categoryJSON.categories, targetfunctionJSON.assignments);
					console.log("Root has categoryId: "+rootId);
					var categoryDisplay = new Array();
					var categoryDescendants = new Array();
					var categoryEqualities = new Array();
					var categoryAssignments = new Array();
					
					
					var categoryDisplayCounter = 0;
					if(relationshipJSON.relationships==null){
						console.log("There are no relationships. Display will be just for categories without their hierarchies");
						for(var i=0;i<categoryJSON.categories.length;i++){
							categoryDisplay[categoryDisplayCounter]=categoryJSON.categories[i];
							categoryAssignments[categoryDisplayCounter]=getCategoryAssignmentArray(targetfunctionJSON.assignments,categories[i].id);
							categoryDisplayCounter++;
						}
					}else{
						//implement BFS to find everything connected to the root. Repeat for all remaining nodes.
						var visited = new Array();
						var queue = new Array();
						var unvisited = getIds(categoryJSON.categories);
						//visited.push(rootId);
						queue.push(rootId);
						while(queue.length>0){
							var catId=queue.pop();
							visited.push(catId);
							unvisited=deleteFromArray(unvisited, catId);
							console.log("Processing category "+catId);
							console.log(visited);	
							console.log(unvisited)
							for(var j=0;j<categoryJSON.categories.length;j++){
								if(categoryJSON.categories[j].id==catId){
									categoryDisplay[categoryDisplayCounter]=categoryJSON.categories[j];
								}
							}
							categoryDescendants[categoryDisplayCounter]=new Array();
							categoryEqualities[categoryDisplayCounter]=new Array();
							for (var i=0;i<relationshipJSON.relationships.length;i++){
								var rel=relationshipJSON.relationships[i];
								if(rel.type=="Sub"&&rel.fromId==catId&&!visited.includes(rel.toId)){
									queue.push(rel.toId)
									for(var j=0;j<categoryJSON.categories.length;j++){
										var cat=categoryJSON.categories[j];
										if(cat.id==rel.toId){
											categoryDescendants[categoryDisplayCounter].push(cat);
										}
									}							
								}else if(rel.type=="Equality"&&rel.fromId==catId){
									categoryEqualities[categoryDisplayCounter].push(rel.toId);
								}else if(rel.type=="Equality"&&rel.toId==catId){
									categoryEqualities[categoryDisplayCounter].push(rel.fromId);
								}
							}
							categoryAssignments[categoryDisplayCounter]=getCategoryAssignmentArray(targetfunctionJSON.assignments,catId);
							categoryDisplayCounter++;
							if(queue.length==0&&unvisited.length>0){
								//check for alternative roots
								rootId=findRootId(buildArrayFromIds(categoryJSON.categories,unvisited), targetfunctionJSON.assignments);
								console.log("Found new root: "+rootId);
								queue.push(rootId);
							}
						}
						
						
					}
					
					console.log(categoryDisplay);
					console.log(categoryDescendants);
					console.log(categoryEqualities);
					console.log(categoryAssignments);
					renderTargetFunction(categoryDisplay, categoryDescendants, categoryEqualities, categoryAssignments, documentJSON.documents);
					
				});
			});
			
		});
		
	});
}

$(document).ready(function(){
	
	$("#createForm").hide();
	$("#uploadForm").hide();
	$("#deleteConfirm").hide();
	
	readTargetFunction();
	
});