<?php include("koneksi.php"); ?>

<!DOCTYPE html>
<html>
<head>
    <title>14518569</title>
</head>

<body>
    <header>
        <h3>Mahasiswa</h3>
    </header>


    <br>

    <table border="1" cellpadding="10" cellspacing="0">
    <thead>
        <tr>
            <th>No</th>
            <th>Nama</th>
            <th>Alamat</th>
            <th>Jenis Kelamin</th>
            <th>Agama</th>
        </tr>
    </thead>
    <tbody>

        <?php
        $sql = "SELECT * FROM tb_mhs";
        $query = mysqli_query($con, $sql);

        while($siswa = mysqli_fetch_array($query)){
            echo "<tr>";

            echo "<td>".$siswa['id']."</td>";
            echo "<td>".$siswa['nama']."</td>";
            echo "<td>".$siswa['nrp']."</td>";
            echo "<td>".$siswa['kelas']."</td>";
            echo "<td>".$siswa['blog']."</td>";
          

            echo "</tr>";
        }
        ?>

    </tbody>
    </table>

    <p>Total: <?php echo mysqli_num_rows($query) ?></p>

    </body>
</html>