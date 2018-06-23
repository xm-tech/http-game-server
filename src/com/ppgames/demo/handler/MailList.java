package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.model.mail.MailManager;

/**
 * Created by Administrator on 2017/6/7.
 */
public class MailList extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        com.ppgames.demo.model.mail.MailList mailList = MailManager.getInstance().getMailList(pid);
        if (mailList == null) {
            resp.send(ErrCode.UNKONW_ERR);
            return;
        }

        resp.data.put("mail", mailList.getArrayList());
        resp.send(ErrCode.SUCC);
    }
}
