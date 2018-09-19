function getIds(array){
	var ids = new Array();
	for(var i=0;i<array.length;i++){
		ids.push(array[i].id);
	}
	return ids;
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