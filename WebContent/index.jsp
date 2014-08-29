<%@ page import="query.QueryManager" %>
<%@ page import="java.io.File" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<jsp:useBean id="queryData" class="beans.QueryData" scope="request">
	<jsp:setProperty name="queryData" property="*" />
</jsp:useBean>
<!DOCTYPE HTML>
<html lang="en"><!-- InstanceBegin template="/Templates/hyris.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- InstanceBeginEditable name="doctitle" -->
<title>Land Use Demo</title>
<!-- InstanceEndEditable -->
<!-- SSEC PROJECT TEMPLATE FORMATTING AND JS: BEGIN -->
<link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>
<link href="http://www.ssec.wisc.edu/css/all_pages.php" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
	<link href="http://www.ssec.wisc.edu/css/all_pages.ie.css?2012-Nov-05" rel="stylesheet" type="text/css">
	<script src="http://www.ssec.wisc.edu/js/IE9.js"></script>
<![endif]-->
<link href="http://www.ssec.wisc.edu/css/print.css?2012-Nov-05" rel="stylesheet" type="text/css" media="print">
<!--<script type="text/javascript" src="http://www.ssec.wisc.edu/webcode/javascript/jquery.js"></script>-->
<!--DEBUG <script type="text/javascript" src="http://www.ssec.wisc.edu/js/all_pages.js?2012-Nov-05"></script>-->
<!-- SSEC PROJECT TEMPLATE FORMATTING AND JS: END -->
<script src="js/vendor/modernizr-2.7.1.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<link rel="stylesheet" href="js/dist/themes/default/style.min.css"/>
<script src="js/dist/jstree.js"></script>
<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
<script>
     	$(document).ready(function() {
       		$("#all").change(function() {
       			//check all boxes
       			if (this.checked) {
       				$("#bayLakes").prop('checked', true);
       				$("#daneCounty").prop('checked', true);
       				$("#eauClaire").prop('checked', true);
       				$("#ecwprc").prop('checked', true);
       				$("#madison").prop('checked', true);
       				$("#ncwrpc").prop('checked', true);
       				$("#sewrpc").prop('checked', true);
       				
       			} else {
       				//uncheck all boxes
       				$("#bayLakes").prop('checked', false);
       				$("#daneCounty").prop('checked', false);
       				$("#eauClaire").prop('checked', false);
       				$("#ecwprc").prop('checked', false);
       				$("#madison").prop('checked', false);
       				$("#ncwrpc").prop('checked', false);
       				$("#sewrpc").prop('checked', false);
       			}
       		});
       		
       		$(".all_group").change(function() {
       			
       			if(!this.checked) {
       				$("#all").prop('checked', false);
       			}
       			
       		});
       	});
</script>
        
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="css/hyris.css">
<link rel="stylesheet" type="text/css" href="css/normalize.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/index.css">
<!-- InstanceBeginEditable name="head" -->
<!-- InstanceEndEditable -->
</head>
<body>
<div id="header">
	<div id="banner">
		<div id="banner_inner" class="central_column">
			<div id="title">
				<h1>Land Use Demo</h1>
				<div class="logos">
					<a href="http://www.wisc.edu/"><img src="img/uw-logo.png" id="uw_logo" alt="UW-Madison"></a> 
					<a href="http://www.ssec.wisc.edu/"><img src="img/ssec-logo.png" id="sseclogo" alt="SSEC"></a> 
				</div>
			</div>
		</div>
		<!--#banner_inner-->
	</div>
	<!--#banner-->
	<div id="top_nav" class="navbar">
		<div class="navbar_inner central_column">
			<ul class="inner_padding">
				<li><a href="http://www.ssec.wisc.edu">Home</a></li>
 
			</ul>
		</div>
		<!--.navbar_inner-->
		
	</div>
	<!--#top_nav-->
</div>
<!--#header-->

<div id="content_container" class="central_column">
	<div id="content" class="inner_padding">
		<noscript>
		<p>This website requires JavaScript to function properly.</p>
		</noscript>
		<div id="content_inner" class="centralColumn">
			<div id="main_content">
				<!-- InstanceBeginEditable name="content" -->
				<div class = 'clearfix'>
					<div class = 'left_container'>
						<img src="img/madison.jpg" width="350px"/>
					</div>
					
					<div class = 'right_container'>
						<form method = POST action="index.jsp">
							<h2>Land Use Code</h2>
							<%
								String query = queryData.getQuery();
								if(query == null) {
									query = "";
								} else {
									query = query.replaceAll("[^\\x00-\\x7F]", "");
									query = query.replaceAll(" ", "_");
								}
							%>
							<input type="text" class = 'input_text' name="query" value="<%= query %>"/>
							<h2>Areas</h2>
							<div class = "checkbox one_line_checkbox">
								<input type="checkbox" id="all"/>
								<label>All</label>
							</div>
							
							<div class = "checkbox">
								<input type="checkbox" name="bayLakes" value="true" id="bayLakes" class="all_group"/>
								<label>Bay Lakes RPC</label>
							</div>
							
							<div class = "checkbox">
								<input type="checkbox" name="daneCounty" value="true" id="daneCounty" class="all_group"/>
								<label>Dane County</label>
							</div>
							
							<div class = "checkbox">
								<input type="checkbox" name="eauClaire" value="true" id="eauClaire" class="all_group"/>
								<label>Eau Claire County</label>
							</div>
							
							<div class = "checkbox">
								<input type="checkbox" name="ecwrpc" value="true" id="ecwprc" class="all_group"/>
								<label>ECWRPC</label>
							</div>
							
							<div class = "checkbox">
								<input type="checkbox" name="madison" value="true" id="madison" class="all_group"/>
								<label>Madison(city)</label>
							</div>
							
							<div class = "checkbox">
								<input type="checkbox" name="ncwrpc" value="true" id="ncwrpc" class="all_group"/>
								<label>NCWRPC</label>
							</div>
							
							<div class = "checkbox">
								<input type="checkbox" name="sewrpc" value="true" id="sewrpc" class="all_group"/>
								<label>SEWRPC</label>
							</div>
							
							<input type="submit" value="Run Query" class = "button"/>
						</form>
						</div>
				</div>
				
				<h2>Results</h2>
			
				<%
					
					//simple method to remove stuff we don't want from query
					
					//String getSecureString(String subjectString)
					//{
					//	String resultString = subjectString.replaceAll("[^\\x00-\\x7F]", "");
					//	return resultString;
					//}
				
				
					//final String OWL_FILE = "data/WisLandUseCodes.owl";
					if(queryData != null && queryData.getQuery() != null) {
						//replace any character we don't want with nothing
						String filePath = session.getServletContext().getRealPath("/WisLandUseCodes.owl");
						//String filePath = jspPath + File.separator + OWL_FILE;
						File f = new File(filePath);
						QueryManager manager = new QueryManager(f);
						manager.setAreas(queryData.isBayLakes(), queryData.isDaneCounty(), queryData.isEauClaire(), 
								queryData.isEcwrpc(), queryData.isMadison(), queryData.isNcwrpc(), queryData.isSewrpc());
						
						if(query != null && !query.equals("")) {
							%>
							<table id = 'result_table'>
							<%
							int i = 0;
							for(String s : manager.runQuery(query)){
								System.out.println("iteratred through loop");
								if(s == null) {
									System.out.println("s is null");
								}
								s = s.replaceAll("Bay Lakes", "Bay Lakes RPC");
								s = s.replaceAll("Eau Claire", "Eau Claire County");
								s = s.replaceAll("Madison", "Madison(city)");
								//even i, put class even on td tag
								if(i % 2 == 0) {
									%><tr><td class = 'even'><a><%= s %></a></td></tr><%
								} else {
									%><tr><td><a><%= s %></a></td></tr><%
								}
				            	i++;
				            }
							// no results if i was never incremented
							
							%>
							</table>
							<%
							if(i == 0) {
								%><p id='no_results' >No results for query</p><%
							}
						} else {
							%><p id='no_results'>No results for query</p><%
						}
					} else {
						%><p id='no_results'>No results for query</p><%
					}
					
					
				%>
				<!-- InstanceEndEditable -->
				<div style="padding-top:50px">
				<h2>Integrated Land Use Ontology</h2>
				<h3>Double click a code to select it for the query above</h3>
				<div id="jstree_demo_div"></div>
				</div>
			</div>
		</div>
		<br style="clear: both;">

<script>
function Node(name){
	this.id = name;
	this.text = name;
	this.parent = "#";
}

function getObs(declarations){
	var obs = {};
	for(var i=0; i< declarations.length;i++){
		var name = declarations[i].children[0].attributes['IRI'].value.slice(1);
		obs[name] = new Node(name);
	}
	return obs;
}

function populateParents(subclassof,obs){
	for(var i=0;i<subclassof.length;i++){
		var parent = subclassof[i].children[1];
		var child = subclassof[i].children[0];
		obs[child.attributes['IRI'].value.slice(1)].parent = parent.attributes['IRI'].value.slice(1);
	}
}

function getItems(obs){
	var items = [];
	for(var i in obs){
		if(obs.hasOwnProperty(i)){
			items.push(obs[i]);
		}
	}
	return items;
}

function getNodes(data){
	var declarations = data.getElementsByTagName('Declaration');
	var subclassof = data.getElementsByTagName('SubClassOf');
	var obs = getObs(declarations);
	populateParents(subclassof,obs);
	return getItems(obs);
}

function makeTree(){
	$.ajax('WisLandUseCodes.owl',{dataType:'xml'}).then(function(d){
		var nodes = getNodes(d);
		return $('#jstree_demo_div').jstree({'core':{'data':nodes}});
	})
}

function initAutoComplete(){
	$.ajax('WisLandUseCodes.owl',{dataType:'xml'}).then(function(d){
		var declarations = d.getElementsByTagName('Declaration');
		var data = new Array(declarations.length);
		for(var i=0;i<declarations.length;i++){
			var name = declarations[i].children[0].attributes['IRI'].value.slice(1);
			data[i] = name;
		}
		$('.input_text').autocomplete({
			source:data,
			minLength:3
		});
	})
}

function linkResults(){
	$('#jstree_demo_div').on('ready.jstree',function(){
		var jstree = $('#jstree_demo_div').jstree();
		var pat = /.*: (.*?)[, ]*(subclass|synonym|superclass|$).*/;
		var results = $('#result_table td');
		for(var i=0;i<results.length;i++){
			if(pat.test($(results[i]).text())){
				$(results[i]).click(function(){
					var id = pat.exec($(this).text())[1].replace(/\s/g,"_");
					var node = jstree.get_node({'id':id});
					var text = $(this).text();
					jstree.deselect_all();
					jstree.close_all();
					jstree.select_node(node);
				});
				$(results[i]).hover(function(){
					$(this).css('cursor','pointer');
				},
				function(){
					$(this).css('cursor','auto');
				});
			}
		}
	});
}

$(document).ready(function(){
	var jstree = makeTree();
	$('#jstree_demo_div').dblclick(function(evt){
		$('.input_text').val($(evt.target).text());
	});
	linkResults(jstree);
	initAutoComplete();
});

</script>
	</div>
	<!--#content-->
	
	<div id="footer">
		<div id="bottom_nav" class="navbar">
			<div class="navbar_inner central_column">
			</div>
			<!--.navbar_inner-->
		</div>
		<!--#bottom_nav-->
		<div class="inner_padding">
			<a href="http://www.wisc.edu/"><img src="http://www.ssec.wisc.edu/images/logo-uw.png" width="89" height="60" alt="UW-Madison" id="uw_madison_logo"></a>
			<div id="address">
				&copy; 2014 Space Science &amp; Engineering Center<br>
				University of Wisconsin -Madison<br>
				1225 W. Dayton Street, Madison, WI 53706, USA
			</div><!--#address-->
		</div>
		<!--.inner_padding-->
	</div>
	<!--#footer-->
	
</div>
<!--#content_container-->
</body>
<!-- InstanceEnd --></html>
