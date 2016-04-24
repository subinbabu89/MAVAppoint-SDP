<%@ include file="templates/header.jsp"%>
<%  String message = (String)session.getAttribute("message"); %>
<%  String accMessage = (String)session.getAttribute("accMessage"); %>
<style>
.panel-heading {
	padding: 5px 15px;
}

.panel-footer {
	padding: 1px 15px;
	color: #A0A0A0;
}

.profile-img {
	width: 96px;
	height: 96px;
	margin: 0 auto 10px;
	display: block;
	-moz-border-radius: 50%;
	-webkit-border-radius: 50%;
	border-radius: 50%;
}

#forgotPasswordLink {
	text-align: center;
	margin-left: 120px;
}

.messageForUser{
color : #000000;
font-size : 11px;
font-style : italic;
}

.modal-header{
background-color :#104e8b;
}
.modal-title{
color : #FFFFFF;
}

</style>

<div class="container" style="margin-top: 40px">

	<div class="row">
		<div class="col-sm-6 col-md-4 col-md-offset-4">
			<div class="panel panel-default">
				<div class="panel-heading">
					<strong>Sign in to continue</strong>
				</div>
				<div class="panel-body">
					<form role="form" action="#" method="POST">
						<fieldset>
							<div class="row">
								<div class="center-block">
									<img class="profile-img" src="img/mavblue.jpg" alt="">
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12 col-md-10  col-md-offset-1 ">
									<div class="form-group">
										<div class="input-group">
											<span class="input-group-addon"> <i
												class="glyphicon glyphicon-user"></i>
											</span> <input type="text" class="form-control" name=emailAddress
												placeholder="yourname@mavs.uta.edu">
										</div>
									</div>
									<div class="form-group">
										<div class="input-group">
											<span class="input-group-addon"> <i
												class="glyphicon glyphicon-lock"></i>
											</span> <input type="password" class="form-control" name=password>
										</div>
									</div>
									<div class="form-group">
										<input type="submit" class="btn btn-lg btn-primary btn-block"
											value="Sign in">
									</div>
								</div>
								<!-- Changes - Rudy -->
								<a href="#" data-toggle="modal" id="forgotPasswordLink" data-target="#forgotPassword">Forgot Password</a> <label
									for="message"><font color="#e67e22" size="4"><%=message%></label>
									<label for="message"><font color="#e67e22" size="4"><%=accMessage%></label>
							</div>
						</fieldset>
					</form>
				</div>
				<div class="panel-footer "></div>
			</div>
		</div>
	</div>
</div>

<form name=forgotPasswordForm action="forgotPasswordAction" method="post">
	<div class="modal fade" id="forgotPassword" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="forgotPasswordTitle">Forgot Password</h4>
				</div>
				<div class="modal-body">
					<div class="input-group">
						<span class="input-group-addon">&nbsp;<i
							class="glyphicon glyphicon-user"></i>
						</span> <input type="text" class="form-control" name=emailAddress
							placeholder="yourname@mavs.uta.edu">
					</div>
					<br>
				<span class="messageForUser"> Temporary Password will be sent to your email Id</span>
				</div>
				<div class="modal-footer">
					<input type="submit" value="submit" style="color:#000;" class="btn btn-default">
				</div>
			</div>
		</div>
	</div>
</form>




<%@ include file="templates/footer.jsp"%>