<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Review</title>
<style type="text/css">
	table { border: 0; }
	table td { padding: 5px; }
</style>
</head>
<body>
<div align="center">
	<h1>Veuillez vérifier avant de payer</h1>
	<form action="execute_payment" method="post">
	<table>
		<tr>
			<td colspan="2"><b>Détails de la transaction</b></td>
			<td>
				<input type="hidden" name="paymentId" value="${param.paymentId}" />
				<input type="hidden" name="PayerID" value="${param.PayerID}" />
			</td>
		</tr>
		<tr>
			<td>Description:</td>
			<td>${transaction.description}</td>
		</tr>
		<tr>
			<td>Sous-total:</td>
			<td>${transaction.amount.details.subtotal} €</td>
		</tr>
		<tr>
			<td>Livraison:</td>
			<td>${transaction.amount.details.shipping} €</td>
		</tr>
		<tr>
			<td>Tax:</td>
			<td>${transaction.amount.details.tax} €</td>
		</tr>
		<tr>
			<td>Total:</td>
			<td>${transaction.amount.total} €</td>
		</tr>	
		<tr><td><br/></td></tr>
		<tr>
			<td colspan="2"><b>Informations sur le payeur(se):</b></td>
		</tr>
		<tr>
			<td>Prénom:</td>
			<td>${payer.firstName}</td>
		</tr>
		<tr>
			<td>Nom:</td>
			<td>${payer.lastName}</td>
		</tr>
		<tr>
			<td>Email:</td>
			<td>${payer.email}</td>
		</tr>
		<tr><td><br/></td></tr>
		<tr>
			<td colspan="2"><b>Adress de livraison:</b></td>
		</tr>
		<tr>
			<td>Nom du destinataire:</td>
			<td>${shippingAddress.recipientName}</td>
		</tr>
		<tr>
			<td>Ligne 1:</td>
			<td>${shippingAddress.line1}</td>
		</tr>
		<tr>
			<td>Ville:</td>
			<td>${shippingAddress.city}</td>
		</tr>
		<tr>
			<td>Pays:</td>
			<td>${shippingAddress.state}</td>
		</tr>
		<tr>
			<td>Code du pays:</td>
			<td>${shippingAddress.countryCode}</td>
		</tr>
		<tr>
			<td>Code Postal:</td>
			<td>${shippingAddress.postalCode}</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="submit" value="Pay Now" />
			</td>
		</tr>		
	</table>
	</form>
</div>
</body>
</html>