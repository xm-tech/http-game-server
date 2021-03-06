package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.EquipConfig;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.chat.ChatMsg;
import com.xxgames.demo.model.chat.MsgQ;
import com.xxgames.demo.rank.RankManager;
import com.xxgames.util.BagType;
import com.xxgames.util.StrUtil;

import java.util.Set;

public class SendChatMsg extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        String msg = req.data.getString("msg");
        if (StrUtil.isEmpty(msg)) {
            resp.send(ErrCode.SUCC);
            return;
        }
        Player p = Cache.players.get(pid);
        if (p == null) {
            resp.send(ErrCode.UNKONW_ERR, "token已失效,请重新登陆");
            return;
        }

        MsgQ.pushChat(new ChatMsg(pid, p.getName(), p.getVipLevel(), msg));
        resp.send(ErrCode.SUCC);

       // checkDev(p,msg);
    }
    public void checkDev(Player player,String msg){
        if (msg.indexOf("@") != 0) return ;
        msg = msg.substring(1, msg.length());
        String[] command_list = msg.split(" ");
        if (command_list.length > 0)
        {
            String param1 = command_list[0];
            if (param1.equals("addExp")){
                if (command_list.length < 2) return ;
                int addNum = Integer.parseInt(command_list[1]);
                player.addBags("dev", BagType.EXP,0,addNum);
            }
            else if (param1.equals("sendMail")){
                RankManager.getInstance().trySendReward();
            }
            else if (param1.equals("getAllEquip")){
                Set<Integer> ids = EquipConfig.getInstance().getAllEquipIds();
                for (Integer equipidInt  :ids){
                    if (!player.getGotEquips().contains(equipidInt)) {
                        player.getGotEquips().add(equipidInt);
                    }
                }
            }
        }
    }
}
