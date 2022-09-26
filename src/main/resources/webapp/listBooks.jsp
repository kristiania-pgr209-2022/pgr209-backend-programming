<%@ page import="no.kristiania.pgr209.books.BookRepository" %>
<h1>Books</h1>

<% for (String book : BookRepository.getInstance().listBooks()) { %>
    <li><%= book %></li>
<% } %>
