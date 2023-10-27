<html>

<head>
    <script>
        const evtSource = new EventSource("http://localhost:8080/bakalarkaProjekt/webapi/SSE");
        evtSource.addEventListener("message", function(e) {
        console.log(e.data)
      })
    </script>
</head>

<body>
   <ul id="list"></ul>
</body>


</html>