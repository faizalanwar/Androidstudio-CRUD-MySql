
<?php


//Mendefinisikan Konstanta


define('HOST', 'localhost');

define('USER', 'root');

define('PASS', 'root');

define('DB', 'db_android');

//membuat koneksi untuk database


$con = mysqli_connect(HOST, USER, PASS, DB) or die('Unable to Connect');


?>  