/**
 * 
 */


function openWindow() {
	window
			.open(
					"about:blank",
					"_modification",
					"height=600, width=900, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=yes");
	var i = document.getElementById("selectedItem").value;
	document.getElementById("id_form_"+i).submit();
}

function selectItem(i){
	document.getElementById("selectedItem").value=i;
	var radioGroup = document.getElementsByName("idSelected");
	for(var j=0; j<radioGroup.length;j++){
		if(radioGroup[j].checked && j!=i-1)
			radioGroup[j].checked=false;
	}
	
}

function setItemValue(opcode){
	document.getElementById("opcode").value=opcode;
	if(opcode=="0"){
		var x = document.getElementsByName("newAttr")[0];
		var y = document.getElementsByName("newVal")[0];
		var attrName = x.value;
		x.name = x.value;
		x.value = y.value;
		y.value = attrName;
	}
	document.getElementById("form_modify").submit();
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

