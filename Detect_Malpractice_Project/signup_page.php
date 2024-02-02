<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Signup Page</title>
    <link rel="stylesheet" href="signup_style.css">
</head>
<body style="background-color: grey; padding-top: 100px;">
    <div class="section" id='div_id'>
    <button style="margin-left:166px; margin-top:297px; position: absolute; border: none;" onclick="type_convert()" type="submit">
        <img src="hide_button.png" style="width: 20px;" id="image">
    </button>
    <button style="margin-left:172px; margin-top:360px; position: absolute; border: none;" onclick="ctype_convert()" type="submit">
        <img src="hide_button.png" style="width: 20px;" id="cimage">
    </button>
    <img src='' style="margin-left:198px; margin-top:100px; position: absolute; border: none; background-color:black;" width='30px' id='img_name'>
    <img src='' style="margin-left:198px; margin-top:163px; position: absolute; border: none; background-color:black;" width='30px' id='img_date'>
    <img src='' style="margin-left:199px; margin-top:225px; position: absolute; border: none; background-color:black;" width='30px' id='img_username'>
    <img src='' style="margin-left:199px; margin-top:288px; position: absolute; border: none; background-color:black;" width='30px' id='img_password'>
    <img src='' style="margin-left:206px; margin-top:350px; position: absolute; border: none; background-color:black;" width='30px' id='img_cpassword'>
    <img src='' style="margin-left:310px; margin-top:526px; position: absolute; border: none; background-color:black;" width='30px' id='img_subject'>
    <form method='post' target='_self'>
        <h1 class="text">Staff/Student Signup</h1><br>
        <label for="name" class="label_field">Enter your name: </label>&nbsp;<input type="text" id="name" name="name" class="input_field" style='font-weight:bold;'><br><br><br>
        <label for="date" class="label_field">Enter your DOB: </label>&nbsp;<input type="date" id="date" name="date" class="input_field" style='font-weight:bold;'><br><br><br>
        <label for="username" class="label_field">Create username: </label>&nbsp;<input type="text" id="username" name='username' style='font-weight:bold;' placeholder="email" class="input_field"><br><br><br>
        <label for="pass" class="label_field">Create password: </label>&nbsp;<input type="password" id="pass" name="pass" class="input_field" style='font-weight:bold;'><br><br><br>
        <label for="cpass" class="label_field">Confirm password: </label>&nbsp;<input type="password" id="cpass" name="cpass" class="input_field" style='font-weight:bold;'><br><br><br>
        <label for="ln" class="label_field">Select your department: </label>&nbsp;
        <select name="ds" id="ds" style="font-size: 18px;">
            <option>MCA</option>
        </select><br><br><br>
        <select name="ss" id="ss" onchange='select_staff()' style="font-size: 18px;">
            <option id='student'>Student</option>
            <option id='staff'>Staff</option>
        </select><br><br>
        <label id='label'></label>&nbsp;&nbsp;<input id='subject' name='subject' class="input_field" style='background-color:grey; border:none;' disabled><br><br><br>
        <button type="submit" id="button" name='bt' value='signup'>Signup</button><br><br><br>
    </form>
    </div>
    <?php
        $servername = "localhost";
        $username = "root";
        $password = "ashok@12345";
        $dbname = "exam_management";
        $conn = new mysqli($servername, $username, $password,$dbname);
    ?>
    <script>
        var subject_correction = 0;
        function select_staff(){
            let selected = document.getElementById('ss').value;
            let label = document.getElementById('label');
            let input = document.getElementById('subject');
            if(selected=='Staff'){
                input.disabled = false;
                input.style.backgroundColor = 'white';
                input.style.border = 'solid';
                input.style.fontWeight = 'bold';
                input.style.fontSize = '20px';
                input.style.borderColor = 'black';
                input.style.color = 'black';
                label.innerHTML = 'Enter the subject handled by you:';
                label.style.fontSize = '25px';
                label.style.fontWeight = 'bold';
                document.getElementById('staff').selected='selected';
                document.getElementById('student').selected='';
                if(subject_correction==0){
                    document.getElementById('img_subject').src = 'warning.png';
                }
                else{
                    document.getElementById('img_subject').src = 'correct.png';
                }
            }
            else{
                input.disabled = true;
                input.style.color = 'grey';
                input.style.backgroundColor = 'grey';
                input.style.border = 'none';
                label.innerHTML = '';
                document.getElementById('staff').selected='';
                document.getElementById('student').selected='selected';
                document.getElementById('img_subject').src = '';
            }
        }
        var clicked='hide';
        function type_convert(){
            if(clicked=='hide'){
                document.getElementById("pass").type='text';
                document.getElementById("image").src='view_button.png';
                clicked='view';
            }
            else if(clicked=='view'){
                document.getElementById("pass").type='password';
                document.getElementById("image").src='hide_button.png';
                clicked='hide';
            }
        }
        var c_clicked='hide';
        function ctype_convert(){
            if(c_clicked=='hide'){
                document.getElementById("cpass").type='text';
                document.getElementById("cimage").src='view_button.png';
                c_clicked='view';
            }
            else if(c_clicked=='view'){
                document.getElementById("cpass").type='password';
                document.getElementById("cimage").src='hide_button.png';
                c_clicked='hide';
            }
        }
    </script>
    <script>
        <?php
            $name = $_POST['name'];
            $date = $_POST['date'];
            $username = $_POST['username'];
            $password = $_POST['pass'];
            $department = $_POST['ds'];
            $selection = $_POST['ss'];
            $staff_subject = '';
            if($selection=='Staff')
                $staff_subject = $_POST['subject'];
        ?>
        var name = '<?php echo $_POST['name'];?>';
        var date = '<?php echo $_POST['date'];?>';
        var username = '<?php echo $_POST['username'];?>';
        var password = '<?php echo $_POST['pass'];?>';
        var confirm_password = '<?php echo $_POST['cpass'];?>';
        var department = '<?php echo $_POST['ds'];?>';
        var selection = '<?php echo $_POST['ss'];?>';
        var subject = '<?php echo $staff_subject?>';
        var label = document.getElementById('label');
        var input = document.getElementById('subject');
        if(selection=='Staff'){
            input.disabled = false;
            input.style.backgroundColor = 'white';
            input.style.border = 'solid';
            input.style.fontWeight = 'bold';
            input.style.fontSize = '20px';
            input.style.borderColor = 'black';
            label.innerHTML = 'Enter the subject handled by you:';
            label.style.fontSize = '25px';
            label.style.fontWeight = 'bold';
            document.getElementById('staff').selected='selected';
            document.getElementById('student').selected='';
        }
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        var wrong = 0;
        if((name.trim()).length==0){
            document.getElementById('img_name').src = 'warning.png';
            document.getElementById('name').value = name;
            wrong = wrong+1;
        }
        else{
            document.getElementById('img_name').src = 'correct.png';
            document.getElementById('name').value = name;
        }
        if((date.trim()).length==0){
            document.getElementById('img_date').src = 'warning.png';
            document.getElementById('date').value = date;
            wrong = wrong+1;
        }
        else{
            document.getElementById('img_date').src = 'correct.png';
            let date1 = new Date(date);
            var modified_date = date1.getFullYear()+'-'+(date1.getMonth()+1)+'-'+(date1.getDate()+1);
            document.getElementById('date').value = modified_date;
        }
        if((username.trim()).length==0){
            document.getElementById('img_username').src = 'warning.png';
            document.getElementById('username').value = username;
            wrong = wrong+1;
        }
        else if(!emailRegex.test(username.trim())){
            document.getElementById('img_username').src = 'wrong.png';
            document.getElementById('username').value = username;
            wrong = wrong+1;
        }
        else{
            document.getElementById('img_username').src = 'correct.png';
            document.getElementById('username').value = username;
        }
        if((password.trim()).length==0){
            document.getElementById('img_password').src = 'warning.png';
            document.getElementById('pass').value = password;
            wrong = wrong+1;
        }
        else{
            document.getElementById('img_password').src = 'correct.png';
            document.getElementById('pass').value = password;
        }
        if((confirm_password.trim()).length==0){
            document.getElementById('img_cpassword').src = 'warning.png';
            document.getElementById('cpass').value = confirm_password;
            wrong = wrong+1;
        }
        else if(confirm_password.trim()!=password.trim()){
            document.getElementById('img_cpassword').src = 'wrong.png';
            document.getElementById('cpass').value = confirm_password;
            wrong = wrong+1;
        }
        else{
            document.getElementById('img_cpassword').src = 'correct.png';
            document.getElementById('cpass').value = confirm_password;
        }
        if(selection=='Staff'){
            if((subject.trim()).length==0){
                document.getElementById('img_subject').src = 'warning.png';
                document.getElementById('subject').value = '<?php echo $staff_subject?>';
                subject_correction = 0;
                wrong = wrong+1;
            }
            else{
                document.getElementById('img_subject').src = 'correct.png';
                document.getElementById('subject').value = '<?php echo $staff_subject?>';
                subject_correction = 1;
            }
        }
        if(wrong>=1){
            alert("Please fill all the fields correctly");
        }
        else if(wrong==0){
            if(selection=='Staff'){
                <?php
                    $modified_date = $_POST['date'];
                    $current_date = date("Y-m-d");
                    $age = date_diff(date_create($modified_date), date_create($current_date))->y;
                    $insert_data = "INSERT INTO login_details (Name, Age, Username, Password, Dept, Role, Staff_Subject) VALUES ('$name','$age','$username','$password','$department','$selection','$staff_subject')";
                    $conn->query($insert_data);
                    $delete = "DELETE FROM login_details WHERE Age=0;";
                    $conn->query($delete);
                    $conn->close();
                ?>
            }
            window.location.href = "login .php";
            
        }
    </script>
</body>
</html>