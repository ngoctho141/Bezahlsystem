<!--
Florian Ludwig

test Seite für änderungen am Apache Server oder zum testen von neuén ideen 
-->
<?php
session_start();
?>
<?php
	// $name = $_POST['name'];
	// $passwort = $_POST['passwort'];
	
	$name = $_SESSION['username'];
	$passwort = $_SESSION['password'];
	

	$con = new mysqli("localhost", "root", "", "automaten2_0");
	if($con->connect_errno)
	{
		die("Verbindung fehlgeschlagen: " . $con->connect_error);
	}

	$sql = "SELECT kid, guthaben FROM kunde WHERE kname = '$name' AND passwort = '$passwort';";	

	$result = mysqli_query($con,$sql);

	if(mysqli_num_rows($result)>0)
	{
		while($row = mysqli_fetch_assoc($result))
		{
			$kid = $row['kid'];
			$guthaben = $row['guthaben'];
		}
	}else
	{
		print("Fehler");
	}

	print($kid . "\n" . $guthaben);
	
	
?>
