package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.model.mail.MailManager;

/**
 * Created by Administrator on 2017/6/7.
 */
public class MailList extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        com.xxgames.demo.model.mail.MailList mailList = MailManager.getInstance().getMailList(pid);
        if (mailList == null) {
            resp.send(ErrCode.UNKONW_ERR);
            return;
        }

        resp.data.put("mail", mailList.getArrayList());
        resp.send(ErrCode.SUCC);
    }
}
