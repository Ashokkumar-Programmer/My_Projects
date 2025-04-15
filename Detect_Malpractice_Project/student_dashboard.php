<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard</title>
</head>

<style>
    .title{
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
    .options{
        word-wrap: break-word;
        width:190px;
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
    .malpractice{
        margin-top:-10px;
        width:1130px;
        position:absolute;
        margin-left:220px;
        height:650px;
        background-color:grey;
    }
    #question{
        resize:none;
        font-size:19px;
        text-wrap:wrap;
        width:950px;
        height:150px;
        color:black;
        margin-left:90px;
    }
    #answer{
        margin-top:20px;
        margin-left:90px;
        width:950px;
        height:350px;
        resize:none;
    }
    .q_button{
        width:40px;
        height:40px;
        margin-top:36px;
        margin-bottom:30px;
        font-size:30px;
        border-radius:50%;
        border:none;
        margin-left:972px;
    }
    .q_button:hover{
        background-color:green;
        color:white;
    }
    #checkbox1,#checkbox3{
        margin-left:90px;
    }
    #checkbox2,#checkbox4{
        margin-left:50px;
    }
    .checkbox_input{
        width:20px;
        height:15px;
    }
    .checkbox_label{
        font-size:17px;
        font-weight:bold;
        margin-left:10px;
    }
    

    #radio1,#radio3{
        margin-left:90px;
    }
    #radio2,#radio4{
        margin-left:50px;
    }
    .radio_input{
        width:20px;
        height:15px;
    }
    .radio_label{
        font-size:17px;
        font-weight:bold;
        margin-left:10px;
    }
</style>

<body style='background-color:grey;'>
    <div class='malpractice' id='malpractice' style='text-align:center; display:none;'>
        <h1>Malpractice Detected</h1><br><br>
        <h2 id='mal'></h2>
    </div>
    <div class='main_screen' id='main_screen'>
        <p style="font-size:20px; font-weight:bold;">Question <label id='mark'></label></p>
        <textarea id="question" disabled></textarea>
        <p style="font-size:20px; font-weight:bold;">Enter your answer:</p>
        <div class='textarea' id='textarea' style='display:none;'>
            <textarea id="answer"></textarea>
        </div>
        <div class='checkbox' id='checkbox' style='display:none;'>
            <input type="checkbox" id="checkbox1" class="checkbox_input"><label id="checkbox_1" class="checkbox_label">Choice1</label>
            <input type="checkbox" id="checkbox2" class="checkbox_input"><label id="checkbox_2" class="checkbox_label">Choice1</label><br><br><br>
            <input type="checkbox" id="checkbox3" class="checkbox_input"><label id="checkbox_3" class="checkbox_label">Choice1</label>
            <input type="checkbox" id="checkbox4" class="checkbox_input"><label id="checkbox_4" class="checkbox_label">Choice1</label>
        </div>
        <div class='radio' id='radio' style='display:none;'>
            <input type="radio" id="radio1" class="radio_input"><label id="radio_1" class="radio_label">Choice1</label>
            <input type="radio" id="radio2" class="radio_input"><label id="radio_2" class="radio_label">Choice1</label><br><br><br>
            <input type="radio" id="radio3" class="radio_input"><label id="radio_3" class="radio_label">Choice1</label>
            <input type="radio" id="radio4" class="radio_input"><label id="radio_4" class="radio_label">Choice1</label>
        </div>
        <button type='submit' onclick='change_question()' id='next_button' class='q_button'>&#8250;</button>
    </div>
    <div class='title'>
        <h1 style='margin-top:0px;'>Dashboard</h1>
    </div>
    <br><br><br>
    <div class='options'>
        <button class='button' name="b1" id="b1" style=" background-color:grey; font-size:20px; font-weight:bold; border-radius:20px;" onclick='change_question()'>Test</button><br><br><br>
    </div>
</body>
<?php
$servername = "localhost";
$username = "root";
$password = "ashok@12345";
$dbname = "exam_management";
$conn = new mysqli($servername, $username, $password,$dbname);
$query = 'SELECT exam_title,questions FROM exam_questions;';
$result = $conn->query($query);
if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    $test_title = $row["exam_title"];
    $questions = $row['questions'];
}
$conn->close();
?>
<script>
    document.getElementById('main_screen').style.display = 'none';
    var test_title = '<?php echo $test_title; ?>';
    var questions = '<?php echo $questions; ?>';
    var json_data = JSON.parse(questions);
    var no_questions = parseInt(json_data.question_details.n_questions);
    var mark_2 = parseInt(json_data.question_details.mark_2);
    var mark_15 = parseInt(json_data.question_details.mark_15);
    var mark_16 = parseInt(json_data.question_details.mark_16);
    var question_number1 = 1;
    function questions1(question,answer_type,c1,c2,c3,c4){
        document.getElementById('question').innerHTML = question;
        if(answer_type==1){
            document.getElementById('textarea').style.display = 'block';
        }
        else if(answer_type==2){
            document.getElementById('radio').style.display = 'block';
            document.getElementById('radio_1').innerHTML = c1;
            document.getElementById('radio_2').innerHTML = c2;
            document.getElementById('radio_3').innerHTML = c3;
            document.getElementById('radio_4').innerHTML = c4;
        }
        else if(answer_type==3){
            document.getElementById('checkbox').style.display = 'block';
            document.getElementById('checkbox_1').innerHTML = c1;
            document.getElementById('checkbox_2').innerHTML = c2;
            document.getElementById('checkbox_3').innerHTML = c3;
            document.getElementById('checkbox_4').innerHTML = c4;
        }
    }
    function change_question(){
        document.addEventListener('keydown', function(event) {
            if (event.ctrlKey && event.key === 'v') {
                event.preventDefault();
                document.getElementById('main_screen').style.display = 'none';
                document.getElementById('malpractice').style.display = 'block';
                document.getElementById('b1').disabled = true;
                document.getElementById('mal').innerHTML = 'crtl+v Pressed';
            }
            if (event.ctrlKey && event.key === 'c') {
                event.preventDefault();
                document.getElementById('main_screen').style.display = 'none';
                document.getElementById('malpractice').style.display = 'block';
                document.getElementById('b1').disabled = true;
                document.getElementById('mal').innerHTML = 'crtl+c Pressed';
            }
            if (event.ctrlKey && event.key === 'x') {
                event.preventDefault();
                document.getElementById('main_screen').style.display = 'none';
                document.getElementById('malpractice').style.display = 'block';
                document.getElementById('b1').disabled = true;
                document.getElementById('mal').innerHTML = 'crtl+x Pressed';
            }
        });
        document.addEventListener("visibilitychange", function() {
        if (document.hidden) {
            document.getElementById('main_screen').style.display = 'none';
            document.getElementById('malpractice').style.display = 'block';
            document.getElementById('b1').disabled = true;
            document.getElementById('mal').innerHTML = 'Tab/Application Switch occur';
        }
        });
        document.getElementById('main_screen').style.display = 'block';
        var question_number = 'q'+question_number1;
        if(no_questions>0){
            if(mark_2>0){
                document.getElementById('mark').innerHTML = '(2 mark) :'
                questions1(json_data.questions.mark_2[question_number].question,json_data.questions.mark_2[question_number].answer_type,json_data.questions.mark_2[question_number].choices.one,json_data.questions.mark_2[question_number].choices.two,json_data.questions.mark_2[question_number].choices.three,json_data.questions.mark_2[question_number].choices.four);
                mark_2--;
                question_number1++;
                if(mark_2==0){
                    question_number1 = 1;
                }
            }
            else if(mark_15>0){
                document.getElementById('mark').innerHTML = '(15 mark) :'
                questions1(json_data.questions.mark_15[question_number].question,json_data.questions.mark_15[question_number].answer_type,json_data.questions.mark_15[question_number].choices.one,json_data.questions.mark_15[question_number].choices.two,json_data.questions.mark_15[question_number].choices.three,json_data.questions.mark_15[question_number].choices.four);
                mark_15--;
                question_number1++;
                if(mark_15==0){
                    question_number1 = 1;
                }
            }
            else if(mark_16>0){
                document.getElementById('mark').innerHTML = '(16 mark) :'
                questions1(json_data.questions.mark_16[question_number].question,json_data.questions.mark_16[question_number].answer_type,json_data.questions.mark_16[question_number].choices.one,json_data.questions.mark_16[question_number].choices.two,json_data.questions.mark_16[question_number].choices.three,json_data.questions.mark_16[question_number].choices.four);
                mark_16--;
                question_number1++;
                if(mark_16==0){
                    question_number1 = 1;
                }
            }
            no_questions--;
        }
        else{
            document.getElementById('main_screen').style.display = 'none';
        }
    }
</script>
</html>