package com.ppgames.demo.handler;

import com.ppgames.core.ErrCode;
import com.ppgames.core.GameAct;
import com.ppgames.core.GameReq;
import com.ppgames.core.GameResp;
import com.ppgames.demo.model.mail.MailManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/8.
 */
public class MailAttachAll extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        com.ppgames.demo.model.mail.MailList mailList = MailManager.getInstance().getMailList(pid);
        if (mailList == null) {
            resp.send(ErrCode.UNKONW_ERR);
            return;
        }

        ArrayList<Long> ret = mailList.getAllMailAttach();
        resp.data.put("mail", ret);
        resp.send(ErrCode.SUCC);
    }
}
