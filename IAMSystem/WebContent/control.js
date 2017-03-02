/**
 * 
 */

function openWindow() {
	var x = 
		window
		.open(
				"about:blank",
				"_modification",
		"height=600, width=900, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=yes");
	document.getElementById("id_form").submit();
	return x;
}

function closeWindow(){
	window.opener=null;
	window.open('','_self');
	window.close();
}

function selectItem(i){
	document.getElementById("selectedItem").value=i;
}

function setOpcode(opcode){
	document.getElementById("opcode").value=opcode;
}

function modifyAttr(opcode){
	setOpcode(opcode);
	document.getElementById("form_modify").submit();
}

function addTableField(opcode){
	setOpcode(opcode);
}

function delTableField(clickedName){
	var result = confirm("Sure to delete the attribute?");
	if(result){
		var deletedAttr = document.getElementById("deleted")
		deletedAttr.value = clickedName;
		document.getElementById("form_delTableField").submit();
	}
}


function setElementAttr(name,attr,val){

	document.getElementsByName(name)[0].setAttribute(attr, val);

}

/**
 * @param attrName
 * @returns
 */
function delAttr(attrName){
	var result = confirm("Sure to delete the attribute?");
	var name = attrName.substring(1);
	if(result){
		document.getElementById("opcode").value="3";
		var deletedAttr = document.getElementsByName(name)[0];
		deletedAttr.value = deletedAttr.name;
		deletedAttr.name="~deleted";
		document.getElementById("form_modify").submit();
	}
}

