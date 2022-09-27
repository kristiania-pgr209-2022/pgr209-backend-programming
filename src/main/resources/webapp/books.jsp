<%@ page import="no.kristiania.library.BookRepository" %>
<%@ page import="no.kristiania.library.Book" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello Library</title>
</head>
<body>

<h2>Here are all the books</h2>

<ul>
<% for (Book book : BookRepository.getInstance().listAll()) { %>
    <li><%= book %></li>
<% } %>
</ul>

</body>
</html>
