
function confirmDeleteNews(confirmationMessage){
	return confirm(confirmationMessage);
}

function confirmDeleteNewsList(confirmationMessage){
	var selectedCount = checkedCheckboxesCount('listNewsId');
	if ( selectedCount != 0)
	{
		return confirm(confirmationMessage + " (" + selectedCount + ")");
	}
	else {
		return false;
	}
}

function checkedCheckboxesCount(checkboxName) {
	var checkboxes = document.getElementsByName(checkboxName);
	var selected = [];
	var selectedCount = 0;
	for (var i=0; i<checkboxes.length; i++) {
	    if (checkboxes[i].checked) {
	    	selectedCount++;
	    }
	}
	return selectedCount;
}

