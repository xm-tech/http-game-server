package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.DataManager;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Passport;
import com.xxgames.demo.model.Player;

/**
 * Created by PhonePadPC on 2017/7/31.
 */
public class BindingWXSuccess extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        Player p = Cache.players.get(pid);
        if (p == null){
            resp.send(ErrCode.UNKONW_ERR,"don't have player");
        }
        String oldPassport = req.data.getString("oldPassport");
        int serverId = req.data.getInteger("serverId");
        String newPassportStr = req.data.getString("newPassport");
        String newKey = serverId+"_"+newPassportStr;
        String oldKey = serverId + "_" + oldPassport ;
        Passport passport = Cache.passports.get(oldKey);
        Passport newPassport = Cache.passports.get(newKey);

        if (newPassport != null){
            resp.send(ErrCode.UNKONW_ERR,"该微信号已经被绑定！");
        }

        if (passport == null){
            resp.send(ErrCode.UNKONW_ERR,"don't have passport");
        }
        else if (passport.getBinding() == 1){//已经绑定了别的
            resp.send(ErrCode.UNKONW_ERR,"该账号已经绑定过微信！");
        }
        else if (passport.getPid() != pid){
            resp.send(ErrCode.UNKONW_ERR,"账号异常！");
        }
        else {//成功
            passport.setPassPort(newPassportStr);
            passport.setBinding(1);
            Cache.passports.remove(oldKey);
            Cache.passports.put(newKey,passport);
            resp.data.put("player", p);
            long token = System.currentTimeMillis();
            DataManager.tokens.put(p.getId(), token);
            resp.data.put("token", token);
            resp.data.put("isBinding", passport.getBinding());
            resp.send(ErrCode.SUCC);
        }
    }
}
