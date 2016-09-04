/**
 * 
 */

function validateSparesForm(id) {
	var spareCode = $("#spareCode").val();
	var numberTest = "/^d[0-9]+/";
	if (!spareCode) {
		alert("Enter spare Code ");
		return false;
	} else if (!$("#partDescription").val()) {
		alert("Enter part Description");
		return false;
	} else if (!$("#price").val()) {
		alert("Enter price");
		return false;
	}else if (!$("#minimumOrder").val()) {
		alert("Enter Minimum Order");
		return false;
	}else if(id == 'saveSpares') {
		saveSparesDetail(sparesform);
	} else if(id == 'updateSpares') {
		var value = confirmUpdate('Do you want to continue with Spares detail update ?');
		return value;
	}
}

function saveSparesDetail(form) {
	$('#saveSpares').attr('disabled', 'disabled');
	form.action = "addSparesDetails.do";
	form.submit();
}