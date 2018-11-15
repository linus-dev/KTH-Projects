<head>
  <script src="/static/jquery-3.3.1.js"></script>
</head>
<body>
  <script>
    $.ajax({
        type : 'GET',
        url : 'GetComments.php',
        dataType : 'json',
        data: {
          dish_id : 1,
          user: 'linus'
        },
        success : function(data){
          console.log(data);    
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
          console.log(errorThrown); 
          console.log(XMLHttpRequest); 
        }
    });
  </script>
</body>
