<!--
Florian Ludwig


Einfache Webseite für login und sehen welche KundenId und Guthaben jemand hat
-->
<!DOCTYPE html>
<head>
	<meta charset= "UTF-8">
	<title>Welcome</title>

</head>
<body>
	<?php
		$check = false;
		if($_SERVER["REQUEST_METHOD"] == "POST")
		{
			$name = $_POST['Username'];
			//$password = md5($_POST['Password']);
			$password = $_POST['Password'];
			$servername = "localhost";
			$dbUsername = "handy";
			$dbPassword = "blu3";
			$dbname= "automaten2_0";
						
			
			$conn = new mysqli($servername,$dbUsername,$dbPassword,$dbname);
			
			if($conn -> connect_error)
			{
				die("Connection failed: " . $conn->connect_error);
			}
			
			
			$sql = "SELECT kid, guthaben FROM kunde WHERE kname = '$name' AND passwort = '$password';";
			
			$result = mysqli_query($conn, $sql);
			
			if (mysqli_num_rows($result) > 0)
			{
				while($row = mysqli_fetch_assoc($result))
				{
					$check = true;
					$kid = $row['kid'];
					$guthaben = $row['guthaben'] ."€";
				}
			} else 
			{
				echo "Wrong Information";
			}
		}

	?>
	<?php
	
	
	if($check)
	{
	?>
	<fieldset>
		<legend>Welcome</legend>
		<label>Name: <?php echo($name);?></label><br />
		<label>KundenId: <?php echo($kid);?></label><br />
		<label>Guthaben: <?php echo($guthaben);?></label>
		
	</fieldset>
	
<?php
	}
	else{ ?>
	<fieldset>
		<legend>Login</legend>
		<form action="index.php" method="post" action="<?php echo SERVER['PHP_SELF'];?>">
			Username:<br>
			<input type="text" name="Username" placeholder="Username"><br>
			Password:<br>
			<input type="password" name="Password" placeholder="Password">
			<input type="submit">
		</form>
	</fieldset>
	<?php } ?>
</body>
