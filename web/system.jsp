<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.xxgames.core.GameContextListener" %>
<%@ page import="com.xxgames.core.GameStatus" %>
<%@ page import="com.xxgames.demo.task.DbSaveTask" %>

<%
    String op = request.getParameter("op");
    if (op.equals("maintain")) {
        GameContextListener.gameStatus = GameStatus.MAINTAIN;
        DbSaveTask.single.stop();
    }

%>