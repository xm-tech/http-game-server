package com.xxgames.demo.model.battle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.util.MathUtil;

public class Battle {

    BattleParam p0;
    BattleParam p1;

    public Battle(BattleParam p0, BattleParam p1) {
        this.p0 = p0;
        this.p1 = p1;
    }

    public BattleResult battle() {
        BattleResult result = new BattleResult();
        JSONArray p0Score = p0.getScore();
        JSONArray p1Score = p1.getScore();

        long score0 = MathUtil.sumOf(p0Score);
        long score1 = MathUtil.sumOf(p1Score);

        result.score0 = p0Score;
        result.score1 = p1Score;

        // FIXME 增加一些随机策略性, 奖励处理
        if (score0 >= score1) {
            // 挑战方胜
            result.winner = BattleResult.winner_me;
        } else {
            // 被挑战方胜
            result.winner = BattleResult.winner_other;
        }
        // 回传enemy给客户端,因为挑战的有可能不是客户端选择的对手
        JSONObject enemy = new JSONObject();
        enemy.put("equips", p1.getEquips());
        enemy.put("heents", p1.getLogo());
        enemy.put("name", p1.getName());
        result.setEnemy(enemy);

        return result;
    }
}
