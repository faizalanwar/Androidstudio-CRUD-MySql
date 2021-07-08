<?php
//Mendapatkan Nilai ID
$id = $_GET['id'];
//Import File Koneksi Database
require_once('koneksi.php');
//Membuat SQL Query
$sql = "DELETE FROM tb_mhs WHERE id=$id;";
//Menghapus Nilai pada Database
if(mysqli_query($con,$sql)){
echo 'Berhasil Menghapus Data Mahasiswa';
}else{
echo 'Gagal Menghapus Data Mahasiswa';
}
mysqli_close($con);
?>
