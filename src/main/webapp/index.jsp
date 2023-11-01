<html>

<head>
  <script>
    /*https://www.tutorialspoint.com/websockets/websockets_events_actions.htm*/
    var source = new EventSource('http://localhost:8080/bakalarkaProjekt/webapi/SSE/register');

    source.onmessage = function (e) {
      console.log("event: " + e + "  data: " + e.data);
      var chatMsg = JSON.parse(e.data);
        console.log("chatMsg: " + chatMsg);
    };
  </script>
</head>

<body>
  <ul id="list"></ul>
</body>


</html>