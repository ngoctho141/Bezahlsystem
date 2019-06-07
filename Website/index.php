<!--
Florian Ludwig


Einfache Webseite für login und sehen welche KundenId und Guthaben jemand hat
-->
<!DOCTYPE html>
<head>
	<meta charset="UTF-8" />
	<title>Welcome</title>

</head>
<body>
	<?php
	session_start();
	$check = false;
	if($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['Username']))
	{
		$name = $_POST['Username'];
		//$password = md5($_POST['Password']);
		$password = $_POST['Password'];
		$servername = "localhost";
		$dbUsername = "root";
		$dbPassword = "";
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
				$_SESSION['kid'] = $kid;
				$_SESSION['guthaben'] = $guthaben;
				$_SESSION['name'] = $name;

			}
		} else
		{
			echo "Wrong Information";
		}
	}

	?>
	<?php


	if(isset($_SESSION['kid']))
	{
    ?>
	<fieldset>
		<legend>Welcome</legend>
		<label>
			Name: <?php echo($_SESSION['name']);?>
		</label>
		<br />
		<label>
			KundenId: <?php echo($_SESSION['kid']);?>
		</label>
		<br />
		<label>
			Guthaben: <?php echo($_SESSION['guthaben']);?>
		</label>
		<br />
		<form method="post">
			<input type="submit" name="logout" value="Logout" />
		</form>
	</fieldset>
	<?php
		if(isset($_POST['logout'])) {
			//Unset cookies and other things you want to
			session_destroy();
			header('Location: index.php'); //Dont forget to redirect
			exit;
		}
    ?>
	<?php
	}
	else{ ?>
	<fieldset>
		<legend>Login</legend>
		<form action="index.php" method="post" action="<?php echo SERVER['PHP_SELF'];?>">
			Username:
			<br />
			<input type="text" name="Username" placeholder="Username" />
			<br />
			Password:
			<br />
			<input type="password" name="Password" placeholder="Password" />
			<input type="submit" value="Login" />
		</form>
	</fieldset>
	<?php } ?>
</body>
