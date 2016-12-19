<html>
<head>
  <title>View</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
  <p>${context.items.class}</p>
  <ul>
    <#list context.items.getList() as item>
    <li>${item}</li>
    </#list>
  </ul>
</body>
</html>
