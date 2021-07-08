<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
//MEndapatkan Nilai Dari Variable
$id = $_POST['id'];
$name = $_POST['name'];
$nrp = $_POST['nrp'];
$kelas = $_POST['kelas'];
$blog = $_POST['blog'];
//import file koneksi database
require_once('koneksi.php');
//Membuat SQL Query
$sql = "UPDATE tb_mhs SET nama = '$name', nrp = '$nrp', kelas = '$kelas', blog = '$blog' WHERE id = $id;";
//Meng-update Database
if(mysqli_query($con,$sql)){
echo 'Berhasil Update Data Mahasiswa';
}else{
echo 'Gagal Update Data Mahasiswa';
}
mysqli_close($con);
}
?>
