<?php
// Muss am anfang stehen
session_start();
$command = $_POST['command'];
// Erstellt die mysqli verbindung zur mysql Datenbank
$con = new mysqli("localhost", "root", "", "automaten2_0");
if($con->connect_errno)
{
	die("Verbindung fehlgeschlagen: " . $con->connect_error);
}

// Auswahl welche anwendung gestartet werden muss
switch($command)
{
	case "Login":
		$name = $_POST['name'];
		$password = $_POST['password'];
			$sql = "SELECT kid, kname, passwort, telefon, geburtsdatum, guthaben, email FROM kunde WHERE kname = '$name';";
			$result = mysqli_query($con,$sql);

			if(mysqli_num_rows($result)>0)
			{
				while($row = mysqli_fetch_assoc($result))
				{
					if(password_verify($password, $row['passwort'])){
						//schreiben von wichtigen variablen in die Session
						$_SESSION['kid'] = $row['kid'];
						$_SESSION['kname'] = $row['kname'];
						$_SESSION['passwort'] = $row['passwort'];
						$_SESSION['telefon'] = $row['telefon'];
						$_SESSION['geburtsdatum'] = $row['geburtsdatum'];
						$_SESSION['gut'] = $row['guthaben'];
						$_SESSION['email'] = $row['email'] ?: " ";
						print($_SESSION['kid'] . ";" . $_SESSION['kname'] . ";" . $password . ";" . $_SESSION['telefon'] . ";" . $_SESSION['geburtsdatum'] . ";" . $_SESSION['gut'] . ";" . $_SESSION['email']);
					} else {
						print(-1);
						break;
					}
				}
			}else
			{
				print(-1);
			}

		

		break;

	case "List":
		// momentan gibt es nur Feste produkte mit festen Preisen für alle Automaten daher wird hier alles aus der produkt Tabelle gehollt änderung auf json datei spezifisch auf den Automaten
		$sql = "SELECT * FROM produkt;";
		$result = mysqli_query($con,$sql);
		$list = "";
		if(mysqli_num_rows($result)>0)
		{
			while($row = mysqli_fetch_assoc($result))
			{
				$list .= $row['name'].";";
				$list .= $row['price']."|";
			}
			$list = substr($list,0 , -1);
			print($list);
		}else
		{
			print(-1);
		}
		break;

	case "Buy":
		$aid = $_POST['aid'];
		$kid = $_SESSION['kid'];
		$guthaben = $_SESSION['gut'];
		$kauf = $_POST['produkt'];

		// der Preis wird momentan noch aus der produkt tabelle geholt muss geändert werden auf json format
		$sql = "SELECT price FROM produkt WHERE name ='$kauf';";
		$result = mysqli_query($con,$sql);
		if(mysqli_num_rows($result)>0)
		{
			while($row = mysqli_fetch_assoc($result))
			{
				$preis = $row['price'];
			}

		}else
		{
			print(-1);
		}

		$guthaben = $guthaben - $preis;

		$sql = "UPDATE kunde SET guthaben = '$guthaben' WHERE kid = '$kid';";
		$result = mysqli_query($con,$sql);
		print("buch_result;". $result.";".$guthaben."|");
		$_SESSION['gut'] = $guthaben;


		// Tan momentaner fest wert der Momentan so in der datenbank steht muss geändert werden auf eine json damit mehrere tans vorhanden sind
		$sql = "SELECT tan FROM automat WHERE macadresse = '$aid';";
		$result = mysqli_query($con,$sql);
		if(mysqli_num_rows($result)>0)
		{
			while($row = mysqli_fetch_assoc($result))
			{
				$tan = $row['tan'];
			}
			print(md5($tan));
		}else
		{
			print(-1);
		}
		break;

	//logout komplette session wird zerstört
	case "End":
		session_destroy();
		print("1;Logout");
		break;

	case "Charge":
		$guthaben = $_SESSION['gut'];
		$kid = $_SESSION['kid'];
		$amount = $_POST['amount'];

		$sql = "Update kunde set guthaben = guthaben + '$amount' Where kid ='$kid'";
		$result = mysqli_query($con,$sql);
		$_SESSION['gut'] = $guthaben + $amount;
		print($result . "|" . $_SESSION['gut']);
		break;

	case "UpdateInfo":
		$kid = $_SESSION['kid'];
		$passwort = $_POST['password'];
		$email = $_POST['email'];
		$telefon = $_POST['telefon'];
		$geburtsdatum = $_POST['geburtsdatum'];
		$hash = password_hash($passwort, PASSWORD_DEFAULT);
		$sql = "Update kunde set passwort = '$hash', telefon = '$telefon', geburtsdatum = '$geburtsdatum', email = '$email' where kid = '$kid'";
		$result = mysqli_query($con, $sql);
		print($result. "|" . $passwort . "|" . $email . "|" . $telefon . "|" . $geburtsdatum);
		break;
		
			
     case "Registry": 
      $name = $_POST['name'];
	 $password = $_POST['password'];
     $telefon  = $_POST['telefon'];
     $geburtsdatum = $_POST['geburtsdatum'];
	 $email  =$_POST['email'];
	 $sql = "SELECT kname FROM kunde WHERE kname = '$name'";
	 $result = mysqli_query($con, $sql);
//     $passwort = password_hash($password ,PASSWORD_DEFAULT);
		 if(mysqli_num_rows($result)>0)
		 {
			 print(-1);
		 }else
		 {
			 $hash = password_hash($password, PASSWORD_DEFAULT);
		 	 $sql= "INSERT INTO  kunde (kname,email,passwort,telefon,geburtsdatum, guthaben)VALUES('$name','$email','$hash','$telefon','$geburtsdatum', 0)";
		 	 $result = mysqli_query($con, $sql);
			 print(1);
		 }
      break;


	case "Logout":
		session_destroy();
		break;
}


?>



