jQuery(document).ready(function () {
	var dialog;
	var options = new primitives.orgdiagram.Config();
	
	var buttons = [];
	
	$('form').on('submit', function(e){
		e.preventDefault();
		return false;
	});
	
	dialog = $("#create-Profile-form").dialog({
		autoOpen: false,
		height: 400,
		width: 350,
		modal: true,
		buttons: {
	        "Create an Profile": function(){
	        	
	        	var profileData = {};
	        	profileData.profileName = $("#in_profileName").val();
	        	profileData.firstName = $("#in_firstName").val();
	        	profileData.lastName = $("#in_lastName").val();
	        	profileData.gender = $("#in_gender").val(),
	        	profileData.parentId = $("#in_parentId").val();

	        	$.ajax({
					type: "POST",
					url: "../profile/save",
					async: true,
					headers: {
	                    'Accept': 'application/json',
	                    'Content-Type': 'application/json'
	                },
					data: JSON.stringify(profileData),
					success: function(result){
						console.log(result);
						dialog.dialog("close");
						items = treeNodesToItems(result.object);
						updateTreeWithItems(items);
						$("#add-profile").trigger("reset");
					},
					error: function(jqXhr, textStatus, errorThrown){
						console.log(textStatus);
						 alert('error:' +textStatus + '\n:' +errorThrown);
					}
				});
				dialog.dialog( "close" );
	        },
	        Cancel: function() {
	          dialog.dialog( "close" );
	        }
	      },
	      close: function() {
	        //form[ 0 ].reset();
	        //allFields.removeClass( "ui-state-error" );
	        $("#add-profile").trigger("reset");
	      }
	});
	
	dialog.dialog("close");
	
	buttons.push(new primitives.orgdiagram.ButtonConfig("add", "ui-icon-person", "Add"));
	buttons.push(new primitives.orgdiagram.ButtonConfig("delete", "ui-icon-close", "Delete"));
	        	
	
	
	options.pageFitMode = primitives.common.PageFitMode.None;
	options.items = items;
	options.buttons = buttons;			
	options.cursorItem = 0;
	options.hasSelectorCheckbox = primitives.common.Enabled.False;
	options.onButtonClick = function (e, data) {
		switch (data.name) {
			case "delete": //alert('clicked on delete:'+JSON.stringify(data.context));
					if(data.context.parent==null || data.context.parent==''){
						alert('you can not delete the parent node')
					}
					else{
						$.ajax({
							type: "DELETE",
							url: "../profile/delete/"+data.context.id,
							//dataType: "json",
							headers: {
		                	    'Accept': 'application/json',
		                    	'Content-Type': 'application/json'
		                	} ,
							success: function(result){
								items = treeNodesToItems(result.object);
								updateTreeWithItems(items);
								console.log(result);
							},
							error: function(jqXhr, textStatus, errorThrown){
								console.log(textStatus);
								alert('error:' +textStatus + '\n:' +errorThrown);
							}
						});
					}
				break;
			case "add": 
				/* get items collection */
				//var items = jQuery("#orgdiagram").orgDiagram("option", "items");
				/* create new item */
				$('#in_parentId').val(data.context.id);
				dialog.dialog( "open" );
				break;
		}
	};
	jQuery("#orgdiagram").orgDiagram(options);

	$("#btn50").button().click(function () { onScale(0.5); });
	$("#btn80").button().click(function () { onScale(.8); });
	$("#btn100").button().click(function () { onScale(1); });
	$("#btn130").button().click(function () { onScale(1.3); });
});

function treeNodesToItems(treeNodes){
	var new_items = [];
	jQuery.each(treeNodes, function(index , val){
		new_items.push(
				new primitives.orgdiagram.ItemConfig({
					id: val.id,
					parent: val.parent,
					title: val.title,
					description: val.description,
					image: "${var_alpha}/../"+val.gender+".png"
				}));
	});
	
	return new_items;
}

function updateTreeWithItems(items){
	//alert('updating tree graph');
	jQuery("#orgdiagram").orgDiagram({
		items: items
	});
	jQuery("#orgdiagram").orgDiagram("update", /*Refresh: use fast refresh to update chart*/ primitives.orgdiagram.UpdateMode.Refresh);
}


function onScale(scale) {
	if (scale != null) {
		jQuery("#orgdiagram").orgDiagram({ scale: scale });
	}
	jQuery("#orgdiagram").orgDiagram("update", primitives.orgdiagram.UpdateMode.Refresh);
}