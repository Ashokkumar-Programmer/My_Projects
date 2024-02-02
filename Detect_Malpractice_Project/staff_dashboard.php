<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Staff Dashboard</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<style>
    .dashboard{
        border-color: black;
        border-style: solid;
        box-shadow: 0 0 20px white;
        text-align: center;
        margin-top:20px;
        width:160px;
        height:40px;
    }
    .button{
        border-color: black;
        border-style: solid;
        text-align: center;
        margin-top:10px;
        width:160px;
        height:30px;
        border-radius:20px;
    }
    .main_screen{
        margin-top:-10px;
        width:1130px;
        position:absolute;
        margin-left:220px;
        height:650px;
        background-color:white;
        overflow-y:scroll;
        overflow-x:scroll;
    }
    .question_make{
        word-wrap: break-word;
        width:190px;
    }
    .label1{
        font-size:20px;
        margin-left:300px;
        font-weight:bold;
    }
    .input1{
        border-radius:20px;
        border:none;
        height:20px;
        width:100px;
        text-align:center;
        font-weight:bold;
        box-shadow:0px 0px 10px black;
        -moz-appearance: textfield;
    }
    #bt1{
        margin-left:450px;
        width:150px;
        height:30px;
        background-color:green;
        color:white;
        font-size:18px;
        border:none;
    }
    #textarea{
        resize:none;
        width:900px;
        height:300px;
        margin-left:100px;
        margin-top:30px;
        font-size:16px;
    }
    #label{
        position:absolute;
        margin-left:100px;
        margin-top:20px;
        font-size: 20px;
        font-weight: bold;
    }
    #label2{
        position:absolute;
        margin-left:280px;
        margin-top:20px;
        font-size: 20px;
        font-weight: bold;
    }
    #answer_type{
        margin-top: 10px;
        margin-left:400px;
        font-size:15px;
        font-weight:bold;
        border:none;
        background-color:white;
    }
    .options{
        font-size:15px;
        font-weight:bold;
        border-radius:20px;
        border:none;
        box-shadow:0px 0px 5px black;
        height:30px;
        padding-left:10px;
    }
    .q_button{
        width:40px;
        height:40px;
        margin-top:186px;
        font-size:30px;
        border-radius:50%;
        border:none;
    }
    #next_button{
        margin-left:972px;
    }
    #next_button:hover{
        background-color:green;
        color:white;
    }
    #make_json{
        font-size:18px;
        font-weight: bold;
        width:80px;
        height:28px;
        border-radius:20px;
        background-color:green;
        color:white;
        border:none;
        margin-left:972px;
        margin-top:180px;
    }
</style>
<body style='background-color:grey;'>
    <div class="main_screen">
        <div  id='main'>
            <div id='main_screen1' style='display:none;'>
                <label class='label1' style='margin-left:400px;'>Test Title: </label><input style='margin-top:80px; margin-left:8px; font-size:18px; width:190px; height:40px;' type='text' id='test_title' name='test_title' class='input1'><br><br><br>
                <label class='label1' style='margin-top:80px;'>How many 2 marks should be asked: </label><input style='margin-left:10px;' type='number' id='mark_2' class='input1'><br><br><br>
                <label class='label1'>How many 15 marks should be asked: </label><input type='number' id='mark_15' class='input1'><br><br><br>
                <label class='label1'>How many 16 marks should be asked: </label><input type='number' id='mark_16' class='input1'><br><br><br>
                <button type='submit' id='bt1' onclick='make_question()'>Make</button>
            </div>
            <div id='type_question' style='display:none;'>
                <label id='label'>Enter your question</label><label id='label2'></label><br><textarea name="textarea" id="textarea"></textarea><br><br>
                <select id="answer_type" onchange='answer_type_function()' name='answer_type'>
                    <option style='font-weight:normal;' value='1'>Paragraph answer</option>
                    <option style='font-weight:normal;' value='2'>Pick one answer</option>
                    <option style='font-weight:normal;' value='3'>Select one or more answer</option>
                </select><br>
                <div id='type_option' style='display:none;'>
                    <label id='label'>Enter your options</label><br><br>
                    <input type="text" style='margin-left:100px; margin-top:20px;' name="option1" id="option1" class='options'>&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="option2" id="option2" class='options'><br><br>
                    <input type="text" style='margin-left:100px; margin-top:20px;' name="option3" id="option3" class='options'>&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="option4" id="option4" class='options'><br><br>
                </div>
                <button type='submit' onclick='change_question()' id='next_button' class='q_button'>&#8250;</button>
                <button type='submit' onclick='make_json()' id='make_json'>Correct</button>
            </div>
        </div>
    </div>
    <div class='dashboard'>
        <h1 style="margin-top:0px;">Dashboard</h1>
    </div>
    <br><br><br>
    <div class='question_make'>
        <form method="post">
            <button class='button' name="b1" style=" background-color:grey; font-size:20px; font-weight:bold; border-radius:20px;">New Question</button><br><br><br>
        </form>
    </div>
</body>
<script>
    var n_questions = 0;
    var mark_2 = 0;
    var mark_15 = 0;
    var mark_16 = 0;
    var temp=0;
    var json_data = {};
    var test_title;
    var question_number = 1;
    function answer_type_function(){
        var select = document.getElementById('answer_type').value;
        if(select=='2' || select=='3'){
            document.getElementById('type_option').style.display = 'block';
            document.getElementById('next_button').style.marginTop = '10px';
            document.getElementById('make_json').style.marginTop = '10px';
        }
        else{
            document.getElementById('type_option').style.display = 'none';
            document.getElementById('next_button').style.marginTop = '186px';
            document.getElementById('make_json').style.marginTop = '186px';
        }
    }
    function make_question(){
        test_title = document.getElementById('test_title').value;
        if(document.getElementById('mark_2').value!='')
            mark_2 = document.getElementById('mark_2').value;
        if(document.getElementById('mark_15').value!='')
            mark_15 = document.getElementById('mark_15').value;
        if(document.getElementById('mark_16').value!='')
            mark_16 = document.getElementById('mark_16').value;
        n_questions = parseInt(mark_2) + parseInt(mark_15) + parseInt(mark_16);
        if((test_title.trim()).length==0){
            alert('Please set the title for the test');
        }
        else if(mark_2!=0 || mark_15!=0 || mark_16!=0){
            json_data.question_details = {};
            json_data.question_details.n_questions = n_questions;
            json_data.question_details.mark_2 = mark_2;
            json_data.question_details.mark_15 = mark_15;
            json_data.question_details.mark_16 = mark_16;
            json_data.questions = {};
            json_data.questions.mark_2 = {};
            json_data.questions.mark_15 = {};
            json_data.questions.mark_16 = {};
            document.getElementById('main_screen1').style.display = 'none';
            change_question();
        }
        else{
            alert('Please add one or question question');
        }
    }
    function make_json(){
        let question = document.getElementById('textarea').value;
        let question_length = (question.trim()).length;
        if(question_length!=0){
            question_number1 = 'q'+question_number;
            if(mark_2>0){
                json_data.questions.mark_2[question_number1] = {};
                json_data.questions.mark_2[question_number1].question = document.getElementById('textarea').value;
                json_data.questions.mark_2[question_number1].answer_type = document.getElementById('answer_type').value;
                json_data.questions.mark_2[question_number1].choices = {};
                json_data.questions.mark_2[question_number1].choices.one = document.getElementById('option1').value;
                json_data.questions.mark_2[question_number1].choices.two = document.getElementById('option2').value;
                json_data.questions.mark_2[question_number1].choices.three = document.getElementById('option3').value;
                json_data.questions.mark_2[question_number1].choices.four = document.getElementById('option4').value;
                question_number++;
                if(mark_2==1){
                    question_number = 1;
                }
            }
            else if(mark_15>0){
                json_data.questions.mark_15[question_number1] = {};
                json_data.questions.mark_15[question_number1].question = document.getElementById('textarea').value;
                json_data.questions.mark_15[question_number1].answer_type = document.getElementById('answer_type').value;
                json_data.questions.mark_15[question_number1].choices = {};
                json_data.questions.mark_15[question_number1].choices.one = document.getElementById('option1').value;
                json_data.questions.mark_15[question_number1].choices.two = document.getElementById('option2').value;
                json_data.questions.mark_15[question_number1].choices.three = document.getElementById('option3').value;
                json_data.questions.mark_15[question_number1].choices.four = document.getElementById('option4').value;
                question_number++;
                if(mark_15==1){
                    question_number = 1;
                }
            }
            else if(mark_16>0){
                json_data.questions.mark_16[question_number1] = {};
                json_data.questions.mark_16[question_number1].question = document.getElementById('textarea').value;
                json_data.questions.mark_16[question_number1].answer_type = document.getElementById('answer_type').value;
                json_data.questions.mark_16[question_number1].choices = {};
                json_data.questions.mark_16[question_number1].choices.one = document.getElementById('option1').value;
                json_data.questions.mark_16[question_number1].choices.two = document.getElementById('option2').value;
                json_data.questions.mark_16[question_number1].choices.three = document.getElementById('option3').value;
                json_data.questions.mark_16[question_number1].choices.four = document.getElementById('option4').value;
                question_number++;
                if(mark_16==1){
                    question_number = 1;
                }
                alert(JSON.stringify(json_data));
            }
            if(mark_2>0){
                mark_2--;
            }
            else if(mark_15>0){
                mark_15--;
            }
            else if(mark_16>0){
                mark_16--;
            }
            document.getElementById('make_json').style.display='none';
            document.getElementById('next_button').style.display='block';
        }
        else{
            alert('Please enter the questions')
        }
    }
    function change_question(){
        document.getElementById('next_button').style.display='none';
        document.getElementById('type_question').style.display = 'block';
        let question = document.getElementById('textarea').value;
        let question_length = (question.trim()).length;
        if(temp==0){
            question_length=1;
        }
        if(mark_2>0){
            document.getElementById('label2').innerHTML = ' (2 mark):';
        }
        else if(mark_15>0){
            document.getElementById('label2').innerHTML = ' (15 mark):';
        }
        else if(mark_16>0){            
            document.getElementById('label2').innerHTML = ' (16 mark):';
        }
        if(question_length!=0){
            if(n_questions>0){
                document.getElementById('textarea').value = '';
                document.getElementById('option1').value = '';
                document.getElementById('option2').value = '';
                document.getElementById('option3').value = '';
                document.getElementById('option4').value = '';
                document.getElementById('type_question').style.display = 'block';
                document.getElementById('make_json').style.display='block';
                document.getElementById('next_button').style.display='none';
                n_questions--;
            }
            else{
                fetch('process.php', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        test_title:test_title,
                        questions: JSON.stringify(json_data)
                    })
                })
                document.getElementById('type_question').style.display = 'none';
            }
        }
        else{
            alert('Please enter the questions')
        }
        temp+=1;
    }
</script>
<?php
    if(isset($_POST['b1'])){
        echo"
        <script>
            document.getElementById('main_screen1').style.display = 'block';
        </script>";
    }
?>
</html>