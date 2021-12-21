<!--------------------------------------------- validation for player modal ------------------------------------------->

       function allowOnlyLetters(e, t)
            {
              if (window.event){ var charCode = window.event.keyCode;}
                else if (e){var charCode = e.which;}
                else{ return true;}
                if ((charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123)|| (charCode==32)){
                    $("#"+t.id).css('border-color', '');
                    document.getElementById(t.id+"msg").innerHTML = "";
                    return true;
                }else{
                    console.log("this :",t.id);
                    $("#"+t.id).css('border-color', 'red');
                    document.getElementById(t.id+"msg").innerHTML = "Please enter only alphabets";
                    return false;
                }

            }

<!-------------------------------------- Validation for date time picker in matches modal ---------------------------------------->

    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = today.getFullYear();
    var dateControl = document.querySelector('input[type="datetime-local"]');
    var time = today.getHours()+':'+today.getMinutes();
    dateControl.min = yyyy + '-' + mm + '-' +dd+ 'T'+time;
    dateControl.value = dateControl.min;

<!---------------------------------- DateTIme Validation and venue select same day ------------------------------------------------>
    function checkVenueDate() {
        var dateControl1 = document.querySelector('input[type="datetime-local"]').value;
        var selectedDate = new Date(dateControl1);
        var date1 = selectedDate.getFullYear()+','+(selectedDate.getMonth()+1)+','+selectedDate.getDate();
        var venueSelected = $("#venue_select option:selected").html();

        console.log("Present: "+isSlotBooked(venueSelected, date1));

        if(isSlotBooked(venueSelected, date1)){
            return true;
        }

       console.log("Runnnig------------- ");
    }


    function isSlotBooked(venueSelected, date1){
        var n1 = document.getElementById("tableData").rows.length;
        var i=0,j=0;
         for(i=1; i<n1;i++){
            var n2 = document.getElementById("tableData").rows[i].cells.length;
            var x=document.getElementById("tableData").rows[i].cells.item(5).innerHTML;
            var tableVenue =document.getElementById("tableData").rows[i].cells.item(4).innerHTML;
            var datetime = x.split(" "); //-- 2021-12-13
            var date = datetime[0].split("-");
            var date2 = date[0]+","+date[1]+","+date[2];
<!--            console.log("selectrf"+date2);-->

        console.log("DatePicker:  "+date1);
        function diff_minutes(dt2, dt1)
         {
          var diff =(dt2.getTime() - dt1.getTime()) / 1000;
          diff /= 60;
          return Math.abs(Math.round(diff));
         }
        dt1 = new Date(date1);
        dt2 = new Date(date2);
        console.log("Tablevenue ",tableVenue);
        console.log("Selected ",venueSelected);
        var timeDiff = diff_minutes(dt1, dt2)/60
        console.log("Hours: ",timeDiff);

        if(timeDiff == 0 && venueSelected == tableVenue){
            console.log(timeDiff ==0 && venueSelected == tableVenue);
             $("#venue_select").css('border-color', 'red');
             $("#date_picker").css('border-color', 'red');
            alert("This slot is booked for other match. Please select another date");
            document.getElementById("matchSubmit").disabled = true;
            return true;
            }else if(timeDiff !=0 || venueSelected != tableVenue){
                console.log("false condition");
                 $("#venue_select").css('border-color', '');
                 $("#date_picker").css('border-color', '');
                document.getElementById("matchSubmit").disabled = false;
            }
        }
    }

<!---------------------------------- Validation for Team selection in matches modal --------------------------------------------->

    function team1Options() {
        var venueId = $("#venue_select option:selected").val();
        var team1Id = $("#team1_select option:selected").val();
        var team2Id = $("#team2_select option:selected").val();

      if(team1Id == team2Id){
        alert("Team cannot be same. Please select different team instead");
            document.getElementById("matchSubmit").disabled = true;
            $("#team1_select").css('border-color', 'red');
            $("#team2_select").css('border-color', 'red');

            return true;
        }
      else if(team1Id==0 || team2Id==0){
          alert("Please select both teams");
          document.getElementById("matchSubmit").disabled = true;
          return true;
      }
      else if (team1Id != team2Id){
            $("#team1_select").css('border-color', '');
            $("#team2_select").css('border-color', '');
            document.getElementById("matchSubmit").disabled = false;
      }
        if(venueId == 0){
            alert("Please select the venue");
            $("#venue_select").css('border-color', 'red');
            document.getElementById("matchSubmit").disabled = true;
        }else {
             $("#venue_select").css('border-color', '');
    <!--        document.getElementById("matchSubmit").disabled = false;-->
        }
    }
    function formValidate(){

        if(checkVenueDate() ||  team1Options())
        {
            document.getElementById("matchSubmit").disabled = true;
        }
    }

<!------------------------------------    Add player validation-------------------------------------->

function checkPlayer(){
    var roleId = $("#role_select option:selected").val();
    var teamId = $("#team_select option:selected").val();
    var countryId = $("#country_select option:selected").val();
   if(roleId == 0 || teamId==0 || countryId==0){
            $("#playermsg").css('border-color', 'red');
            $("#playermsg").html("Please select all fields");
            $("#country_select").css('border-color', 'red');
            $("#team_select").css('border-color', 'red');
            $("#role_select").css('border-color', 'red');
            document.getElementById("playerSubmit").disabled = true;
            return true;
        }else {
             $("#playermsg").html("");
             $("#role_select").css('border-color', '');
             $("#team_select").css('border-color', '');
             $("#country_select").css('border-color', '');
            document.getElementById("playerSubmit").disabled = false;
            return false;
        }
    }

    function checkPlayerName(){
        var playerName = $("#player_name").val();
       if(playerName.length <3)
     {
        document.getElementById("playerSubmit").disabled = true;
        $("#playermsg").html("Please enter name, contains atleast 4 characters");
        return true;
     }else{
        document.getElementById("playerSubmit").disabled = false;
        return false;
     }
    }

    function playerSub(){
        if(checkPlayerName() ||  checkPlayer())
        {
            document.getElementById("playerSubmit").disabled = true;
        }
    }

<!----------------- Adding team Validation -------------------------->

    function validateCity(){
        var cityId = $("#select_city option:selected").val();
        if(cityId == 0){
           console.log("Submit");
           alert("Please select the city");
           $("#select_city").css('border-color', 'red');
           document.getElementById("teamSubmit").disabled = true;
           return true;
        }else {
           console.log("Submit");
           $("#select_city").css('border-color', '');
           document.getElementById("teamSubmit").disabled = false;
           return false;
        }
    }

     function checkTeamName(){
        var teamName = $("#teamName").val();
        if(teamName.length <1)
        {
          document.getElementById("playerSubmit").disabled = true;
          $("#teamNamemsg").html("Please enter name, contains atleast 2 characters");
          return true;
        }else{
        document.getElementById("playerSubmit").disabled = false;
        return false;
      }
    }

    function teamSub(){
        if(checkTeamName() ||  validateCity())
        {
            console.log("Submit");
            document.getElementById("teamSubmit").disabled = true;
        }
    }