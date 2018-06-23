<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.ppgames.core.db.GameLogicDataSource" %>
<%@ page import="com.ppgames.demo.cache.Cache" %>
<%@ page import="com.ppgames.demo.model.Player" %>
<%@ page import="com.ppgames.util.StrUtil" %>

// FIXME 正式服不能存在该接口
<form method="post" action="removePlayer.jsp">
    <p>
        pid:<input type="text" name="pid"/>
        <input type="submit" value="清号"/>
    </p>
</form>

<form method="post" action="removePlayer.jsp">
    <p>
        <input type="hidden" name="pid" value="-1"/>
        <input type="submit" value="清全部号"/>
    </p>
</form>

<%
    String pid = request.getParameter("pid");
    System.out.println(pid);
    if (StrUtil.isEmpty(pid)) {
        System.err.println("pid null");
    } else if (pid.equals("-1")) {
        // 清全部号
        Cache.players.clear();
        String sql = "delete from t_player;";
        GameLogicDataSource.instance.update(sql);
        System.err.println(pid + " remove db");
    } else {
        // 清指定号
        Player p = Cache.players.get(pid);
        if (p != null) {
            Cache.players.remove(pid);
            System.err.println(pid + " remove cache");
        }
        // remove from mysql
        String sql = "delete from t_player where id=" + pid;
        GameLogicDataSource.instance.update(sql);
        System.err.println(pid + " remove db");
    }
%>

