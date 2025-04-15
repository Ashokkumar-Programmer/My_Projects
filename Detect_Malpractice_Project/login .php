<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <link rel="stylesheet" href="login_style.css">
</head>
<body style="background-color: grey; padding-top: 100px;">
    <div class="section">
    <button style="margin-left:184px; margin-top:226px; position: absolute; border: none;" onclick="type_convert()" type="submit">
            <img src="hide_button.png" style="width: 20px;" id="image">
    </button><br><br><br>
    <img src='' style="margin-left:216px; margin-top:100px; position: absolute; border: none; background-color:black;" width='28px' id='img_username'>
    <img src='' style="margin-left:215px; margin-top:163px; position: absolute; border: none; background-color:black;" width='28px' id='img_password'>
    <form method='post'>
        <h1 class="text">Staff/Student Login</h1><br>
        <label for="username" class="label_field">Enter your username: </label>&nbsp;<input type="text" id="username" name='username' placeholder="email" class="input_field"><br><br><br>
        <label for="password" class="label_field">Enter your password: </label>&nbsp;<input type="password" id="password" name="password" class="input_field"><br><br><br>
        <button type="submit" id="button" name='bt' value='Login'>Login</button>
    </form><br><br>
    <label style='text-align:center; font-size:20px;'>Don't have an account?</label>&nbsp;<a style='text-align:center; text-decoration:none; color: black; font-size:20px; font-weight:bold;' href="signup_page.php">Signup Page</a>
    </div>
</body>
<?php
        $servername = "localhost";
        $username = "root";
        $password = "ashok@12345";
        $dbname = "exam_management";
        $conn = new mysqli($servername, $username, $password,$dbname);
?>
<script>
    var clicked='hide';
    function type_convert(){
        if(clicked=='hide'){
            document.getElementById("password").type='text';
            document.getElementById("image").src='view_button.png';
            clicked='view';
        }
        else if(clicked=='view'){
            document.getElementById("password").type='password';
            document.getElementById("image").src='hide_button.png';
            clicked='hide';
        }
    }
</script>
<script>
    var username = '<?php echo $_POST['username'];?>';
    var password = '<?php echo $_POST['password'];?>';
    <?php
        $get_data =  "SELECT Username, Password, Role FROM login_details WHERE Username='" . $_POST['username'] . "'";
        $data = $conn->query($get_data);
        $username = '';
        $password = '';
        $role = '';
        if ($data->num_rows > 0) {
            while($row = $data->fetch_assoc()) {
                $username = $row['Username'];
                $password = $row['Password'];
                $role = $row['Role'];
            }
        }
    ?>
    var d_password = '<?php echo $password; ?>';
    var role = '<?php echo $role; ?>';
    var wrong = 0;
    if((username.trim()).length==0){
        document.getElementById('img_username').src = 'warning.png';
        document.getElementById('username').value = username;
        wrong = wrong+1;
    }
    else{
        document.getElementById('img_username').src = 'correct.png';
        document.getElementById('username').value = username;
    }
    if((password.trim()).length==0){
        document.getElementById('img_password').src = 'warning.png';
        document.getElementById('password').value = password;
        wrong = wrong+1;
    }
    else if(d_password.trim()!=password.trim()){
        document.getElementById('img_password').src = 'wrong.png';
        document.getElementById('password').value = password;
        wrong = wrong+1;
    }
    else{
        document.getElementById('img_password').src = 'correct.png';
        document.getElementById('password').value = password;
    }
    if(role=='Staff'){
        window.location.href = "staff_dashboard.php";
    }
    else if(role=='Student'){
        window.location.href = "student_dashboard.php";
    }
</script>
</html>


