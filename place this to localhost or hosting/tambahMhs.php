<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
//Mendapatkan Nilai Variable
$name = $_POST['name'];
$nrp = $_POST['nrp'];
$kelas = $_POST['kelas'];
$blog = $_POST['blog'];
//Pembuatan Syntax SQL
$sql = "INSERT INTO tb_mhs (nama,nrp,kelas,blog) VALUES ('$name','$nrp','$kelas','$blog')";
//Import File Koneksi database
require_once('koneksi.php');
//Eksekusi Query database
if(mysqli_query($con,$sql)){
echo 'Berhasil Menambahkan Data Mahasiswa';
}else{
echo 'Gagal Menambahkan Data Mahasiswa';
}
mysqli_close($con);
}
?>
