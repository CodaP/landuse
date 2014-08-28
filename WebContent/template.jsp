<%@ page import="query.QueryManager" %>
<%@ page import="java.io.File" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<jsp:useBean id="queryData" class="beans.QueryData" scope="session"/>
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
				<li><a href="/hyris/">Home</a></li>
<li><a href="/hyris/products/">Products</a></li>
 
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
						<form method = POST action="formProcessor.jsp">
							<h2>Residential Code</h2>
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
								<label>Bay Lakes</label>
							</div>
							
							<div class = "checkbox">
								<input type="checkbox" name="daneCounty" value="true" id="daneCounty" class="all_group"/>
								<label>Dane County</label>
							</div>
							
							<div class = "checkbox">
								<input type="checkbox" name="eauClaire" value="true" id="eauClaire" class="all_group"/>
								<label>Eau Claire</label>
							</div>
							
							<div class = "checkbox">
								<input type="checkbox" name="ecwrpc" value="true" id="ecwprc" class="all_group"/>
								<label>ECWRPC</label>
							</div>
							
							<div class = "checkbox">
								<input type="checkbox" name="madison" value="true" id="madison" class="all_group"/>
								<label>Madison</label>
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
				
				
					final String OWL_FILE = "WisLandUseCodes.owl";
					if(queryData != null && queryData.getQuery() != null) {
						//replace any character we don't want with nothing
						String jspPath = session.getServletContext().getRealPath("");
						String filePath = jspPath + File.separator + OWL_FILE;
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
								s = s.replaceAll("Bay Lakes", "Bay Lakes RPC");
								s= s.replaceAll("Eau Claire", "Eau Claire County");
								s = s.replaceAll("Madison", "Madison(city)");
								//even i, put class even on td tag
								if(i % 2 == 0) {
									%><tr><td class = 'even'><%= s %></td></tr><%
								} else {
									%><tr><td><%= s %></td></tr><%
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
			</div>
		</div>
		<br style="clear: both;">
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
