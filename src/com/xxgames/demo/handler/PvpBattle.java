package com.xxgames.demo.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.xxgames.core.ErrCode;
import com.xxgames.core.GameAct;
import com.xxgames.core.GameReq;
import com.xxgames.core.GameResp;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.SystemConfConfig;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.battle.Battle;
import com.xxgames.demo.model.battle.BattleParam;
import com.xxgames.demo.model.battle.BattleResult;
import com.xxgames.demo.model.battle.PvpRanks;
import com.xxgames.util.TimeUtil;

/**
 * 搭配结束,开始战斗
 */
public class PvpBattle extends GameAct {
    @Override
    public void exec(GameReq req, GameResp resp) {
        long pid = req.data.getLongValue("pid");
        // 守方名次
        int rank = req.data.getIntValue("rank");
        // 攻方服饰搭配
        JSONArray equips = JSON.parseArray(req.data.getString("equips"));

        Player p = Cache.players.get(pid);

        // 跨天逻辑处理
        int now = TimeUtil.nowInt();
        if (!TimeUtil.sameDay(now, p.getPvp().getBattletime())) {
            p.getPvp().setBattlednum(0);
        }
        if (!TimeUtil.sameDay(p.getPvp().getBuybattletime(), now)) {
            p.getPvp().setBuybattlenum(0);
        }

        int styleid = p.getPvp().getStyleid();
        if (p.getPvp().canBattleNum() <= 0) {
            resp.send(ErrCode.UNKONW_ERR, "剩余挑战次数为0");
            return;
        }

        BattleParam param0 = new BattleParam(p, equips);
        BattleParam param1 = new BattleParam(rank, styleid);

        Battle battle = new Battle(param0, param1);
        BattleResult battleResult = battle.battle();

        int crystalAdd;
        if (battleResult.win()) {
            int old_rank = p.getPvp().getRank().get();
            // 攻方胜
            battleResult.setRank(rank);
            // 攻方名次提升
            p.getPvp().getRank().set(rank);
            PvpRanks.addOneRank(rank, pid);
            // 攻方生成1批新对手
            p.getPvp().refreshEnemyRanks();
            battleResult.setEnemys(p.getPvp().genEnemys());
            if (!param1.isRobt()) {
                // 守方名次降低, 但不一定获得攻方攻打时的名次
                Player defencePlayer = Cache.players.get(param1.getPid());
                synchronized (defencePlayer) {
                    defencePlayer.getPvp().getRank().set(old_rank);
                    // 守方生成1批新对手
                    defencePlayer.getPvp().refreshEnemyRanks();
                    PvpRanks.addOneRank(old_rank, defencePlayer.getId());
                }
            }
            crystalAdd = SystemConfConfig.getInstance().getCfg().getPvp_battle_win_crystal();
        } else {
            crystalAdd = SystemConfConfig.getInstance().getCfg().getPvp_battle_fail_crystal();
        }

        p.getPvp().addBattleNum(1, now);
        // 加水晶
        p.getPvp().addCrystal(crystalAdd);

        battleResult.setStyleid(styleid);
        battleResult.setCrystal(crystalAdd);

        resp.data.putAll(battleResult.toJSON());
        resp.send(ErrCode.SUCC);
    }
}
