<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<%@ page import="java.util.ArrayList"%>
<%
	String message = (String) session.getAttribute("message");
	System.out.println("message jsp    "+message);
	String emailnotification = (String) session.getAttribute("emailnotification");
	System.out.println("emailnotification jsp    "+emailnotification);
%>
<style>
.resize {
	width: 60%;
}

.resize-body {
	width: 80%;
}
</style>
<div class="container">
	<!-- Panel -->
	<div class="panel panel-default resize  -block">
		<!-- Default panel contents -->
		<div class="panel-heading text- ">
			<h1>Account Settings</h1>
		</div>

		<div class="panel-body resize-body  -block">
			<div class="form-group">



				<form action="notifyEmail" method="post">
				<%	
					if(emailnotification.equalsIgnoreCase("false")){
					%>
					<label for="emailnotification">E-mail notifications: <input
						type="checkbox" name="emailnotification" value="true"
						 /></label>
					<%
						} else {
					%>
					<label for="emailnotification">E-mail notifications: <input
						type="checkbox" name="emailnotification" value="true" checked="checked"
						 /></label>
					<%
						}
					%> 
					
					 <input type="submit" class="btn-lg" value="Submit" onclick="javascript:FormSubmit();">

				</form>

			</div>
			<!--  <label id="result"><font color="#0" size="4"></font></label>-->
		</div>
		<div class="panel-footer text- "></div>



	</div>
</div>
  <script> function FormSubmit(){
									
									
									var emailnotification = document.getElementById("emailnotification").checked;
									var params = ('emailnotification='+emailnotification);
									var xmlhttp;
									xmlhttp = new XMLHttpRequest();
									xmlhttp.onreadystatechange=function(){
										if (xmlhttp.readyState==4){
											document.getElementById("result").innerHTML = xmlhttp.responseText;	
										}
									}
									xmlhttp.open("POST","notifyEmail",true);
									xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
									xmlhttp.setRequestHeader("Content-length",params.length);
									xmlhttp.setRequestHeader("Connection","close");
									xmlhttp.send(params);
									document.getElementById("result").innerHTML = "Attempting to change Notification...";
								}
								</script>
 
<%@include file="templates/footer.jsp"%>