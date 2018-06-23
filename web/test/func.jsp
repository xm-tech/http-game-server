<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<script src="jquery-1.9.1.js"></script>
<script>

    function begin() {
        setInterval("heartBeat()", 5000);
    }

    function heartBeat() {
        var url = "http://127.0.0.1:8080/demo/game?cmd=settle&parm={\"pid\":1000}";
        url = encodeURI(url);
        $.ajax({
            url: url, async: false, success: function (data) {
                $("#respArea").append(data);
            }
        });
    }

    function login() {
        var pid = $("#pid_login").val();
        var url = "http://127.0.0.1:8080/demo/game?cmd=login&parm={\"pid\":" + pid + "}";
        url = encodeURI(url);
        $.ajax({
            url: url, async: false, success: function (data) {
                $("#respArea").append(data);
            }
        });
    }

    function saveDecro() {
        var pid = $("#pid_login").val();
        var decros = $("#decorStr").val();
        var url = "http://127.0.0.1:8080/demo/game?cmd=saveDecros&parm={\"pid\":" + pid + ",\"decros\":\"" + decros + "\"}";
        url = encodeURI(url);
        $.ajax({
            url: url, async: false, success: function (data) {
                $("#respArea").append(data);
            }
        });
    }
</script>

<input type="hidden" id="saleState"/>
<p>玩家初始信息: id:1000, gold: <span style="color:red" id="goldnumid">1000000</span></p>
<p>货架初始信息: itemid: 1000, num: <span id="itemnumid" style="color:red">1000000</span></p>
<p>测试每5秒钟销售1件货物，货物单价=200金币,暂未考虑任何buff、销售顺序机制、结束机制,只是简单地按货物价格来销售1种指定货物， 玩家信息亦暂未持久化.</p>

<p>
<textarea id="respArea" cols="100" rows="50">	
</textarea>
</p>
<p>
    <input type="button" value="挂机售卖开始" onclick="begin();"/>
</p>

<p>
    pid:<input type="text" id="pid_login" name="pid"></input>
</p>
<p>
    <input type="button" value="登陆" onclick="login();"></input>
</p>
<p>
    <input type="text" value="" name="" id="decorStr"/>
    <input type="button" value="装修" onclick="saveDecro();"></input>
</p>