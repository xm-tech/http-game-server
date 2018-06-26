package com.xxgames.demo.handler;

import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.model.mail.MailManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/8.
 */
public class MailAttachAll extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        com.xxgames.demo.model.mail.MailList mailList = MailManager.getInstance().getMailList(pid);
        if (mailList == null) {
            resp.send(ErrCode.UNKONW_ERR);
            return;
        }

        ArrayList<Long> ret = mailList.getAllMailAttach();
        resp.data.put("mail", ret);
        resp.send(ErrCode.SUCC);
    }
}
