<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="control.js?ver=3333"></script>

<style type="text/css">
div#main-content {
	display: none;
}
</style>

<title>Welcome to IAM System- By Zexin Lin</title>
<%!String[] attrNames;%>

<%
	attrNames = (String[]) request.getSession().getAttribute("attrNames");
%>

<script>
	var wndCreate;
	$(document).ready(function() {
		$('#main-content').fadeIn();
	});
	<%Object attrObject = request.getAttribute("msg");
			String msg;
			if (attrObject != null) {
				msg = attrObject.toString();%>
		alert("<%=msg%>");

<%		request.removeAttribute("msg");
	}%>
	
</script>

</head>
<body>
	<div id="main-content">
		<div id="header" class="jumbotron">
			<h1 class="text-center">Welcome to IAM System</h1>
			<h2 class="text-center">
				<kbd>Made by Zexin Lin </kbd>
			</h2>

			<h2 class="text-center">Your Reliable Identity Manager</h2>

		</div>
		<div class="container-fluid">

			<ul class="nav nav-tabs nav-justified">
				<li>
					<a data-toggle="tab" id="tabSearch" href="#tab_search">Search </a>
				</li>
				<li>
					<a data-toggle="tab" id="tabCreate" href="#tab_create">Create</a>
				</li>
			</ul>

			<div class="tab-content">
				<div role="tabpanel" id="tab_search" class="tab-pane fade">
					<div class="well well-lg">
						<div class="panel-group" id="accordion" role="tablist">
							<div class="panel panel-default" role="tab" id="headingOne">
								<div class="panel-heading">
									<h2 class="panel-title">
										<a role="button" data-toggle="collapse" href="#searPanel" data-parent="#accordion" aria-expanded="true" aria-controls="searchPanel">Search For Identity</a>
									</h2>
								</div>
								<div id="searPanel" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
									<div class="panel-body">
										<h3>Set your search criteria</h3>
										<%
											for (int i = 0; i < attrNames.length; i++) {
										%>
										<form id="searchForm" class="form-horizontal" action="servSearch" method="POST">

											<div class="form-group">
												<label class="control-label col-sm-2" for="tb_<%=attrNames[i]%>">
													<%=attrNames[i]%>:
												</label>
												<div class="col-sm-10">
													<input class="form-control" id="tb_<%=attrNames[i]%>" name="<%=attrNames[i]%>" placeholder="<%=attrNames[i]%>" value="">
												</div>
											</div>

											<%
												}
											%>
											<div class="form-group">
												<div class="col-sm-offset-5">
													<input type="button" id="btn_postReq" class="btn btn-info btn-md" value="Search" />
												</div>
											</div>
										</form>
									</div>
								</div>
							</div>

							<div class="panel panel-default" role="tab" id="headingTwo">
								<div class="panel-heading">
									<h2 class="panel-title">
										<a role="button" data-toggle="collapse" href="#alterPanel" data-parent="#accordion" aria-expanded="false" aria-controls="alterPanel">Add New Attribute For
											The Whole</a>
									</h2>
								</div>
								<div id="alterPanel" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
									<div class="panel-body">
										<form id="form_addTableField" action="servAlterIdTable" method="post">
											<input type="hidden" id="opcode" name="opcode" value="0">
											<div class="form-group" style="width: 30%">
												<div class="input-group">
													<input type="text" class="form-control" name="addedFieldName" placeholder="Enter the new field name" value="">
													<span class="input-group-btn">
														<input type="submit" id="btn_addField" class="btn btn-info" value="Add" onclick="addTableField('0')">
													</span>
												</div>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div id="table_id"></div>
					<input type="button" value="Modify" class="btn btn-info btn-lg " onclick="wndCreate = openWindow()" style="margin-bottom: 20px;" />
				</div>

				<div role="tabpanel" id="tab_create" class="tab-pane fade">
					<div id="table_create"></div>
				</div>
			</div>
			<script>
				function loadData(url, selector) {
					$.get(url, function(data) {
						var divTable = $(data).filter('div.main-content');
						$(selector).html(divTable);
					});
				}
			<%if (request.getParameter("activated") != null) {%>
				$('#tabSearch').tab('show');
				loadData('search.jsp', '#table_id');
			<%}%>
				$("#tabSearch").on("click", function() {
					loadData('search.jsp', '#table_id');
				});

				$("#tabCreate").on("click", function() {
					loadData('create.jsp', '#table_create');
				});

				$('#btn_postReq').on(
						'click',
						function() {
							var form = $('#searchForm');
							var deferred = $.ajax({
								type : "POST",
								url : form.attr('action'),
								data : form.serialize(),
								success : function(data) {
									var div = $('#main-content');
									setTimeout(function() {
										$('#table_id').html(
												$(data).filter(
														'div.main-content'));
									}, 500);
									div.fadeOut();
									div.fadeIn();
								}
							});

						});

				$('#form_addTableField').submit(function(event) {
					event.preventDefault();
					var form = $(this);
					var deferred = $.ajax({
						type : "POST",
						url : form.attr('action'),
						data : form.serialize(),
						success : function(data) {
							var div = $('#main-content');
							setTimeout(function() {
								loadData('search.jsp', '#table_id');
							}, 500);
							div.fadeOut();
							div.fadeIn();
						}
					});

				});
			</script>
		</div>
	</div>
</body>
</html>