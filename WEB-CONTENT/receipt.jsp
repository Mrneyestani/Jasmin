<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Payment Receipt</title>
<style type="text/css">
	table { border: 0; }
	table td { padding: 5px; }
</style>
</head>
<body>
<div align="center">
	<h1>Paiement effectué. Merci d'avoir acheté nos produits</h1>
	<br/>
	<h2>Détails de la facture :</h2>
	<table>
		<tr>
			<td><b>Merchand:</b></td>
			<td>Company MRN Ltd.</td>
		</tr>
		<tr>
			<td><b>Payeur(se):</b></td>
			<td>${payer.firstName} ${payer.lastName}</td>		
		</tr>
		<tr>
			<td><b>Description:</b></td>
			<td>${transaction.description}</td>
		</tr>	
		<tr>
			<td><b>Sous-total:</b></td>
			<td>${transaction.amount.details.subtotal} €</td>
		</tr>
		<tr>
			<td><b>Livraison:</b></td>
			<td>${transaction.amount.details.shipping} €</td>
		</tr>
		<tr>
			<td><b>Tax:</b></td>
			<td>${transaction.amount.details.tax} €</td>
		</tr>
		<tr>
			<td><b>Total:</b></td>
			<td>${transaction.amount.total} €</td>
		</tr>						
	</table>
</div>
</body>
</html>