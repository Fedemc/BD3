<jsp:useBean id='mensajeError' scope='request' class='java.lang.String' />

<html>
<head>
<title>Error</title>
</head>
<body bgcolor="#ffa366">
<h2><b>Ocurrió un error!</b></h2>
<p>
<p><b>${mensajeError}</b></p>
<p><a href='Ingreso.jsp'>Volver al ingreso</a></p>
</body>
</html>