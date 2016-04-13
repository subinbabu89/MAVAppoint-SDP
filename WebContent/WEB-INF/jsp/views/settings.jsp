<jsp:include page='<%=(String) request.getAttribute("includeHeader")%>' />
<%@ page import="java.util.ArrayList"%>
<%
	String message = (String) session.getAttribute("message");
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
	<div class="panel panel-default resize center-block">
		<!-- Default panel contents -->
		<div class="panel-heading text-center">
			<h1>Account Settings</h1>
		</div>
		<label style="text-align: center" for="message"><font
			color="#0" size="4"><%=message%></font></label> <br>
		<div class="panel-body resize-body center-block">
			<div class="form-group">
			<center>
					<a href="changePassword"><font style="color: #e67e22" size="3">Change
							Password</font></a>
			
				<br />
				<br />
				<br />
				
					<a href="notifyEmail"><font style="color: #e67e22" size="3">
							Email Notifications</font></a>
				</center>
			</div>
		</div>
		<div class="panel-footer text-center"></div>
		</form>
	</div>
</div>
<%@include file="templates/footer.jsp"%>