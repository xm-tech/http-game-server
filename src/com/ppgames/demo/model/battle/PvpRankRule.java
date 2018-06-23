package com.ppgames.demo.model.battle;

import com.alibaba.fastjson.JSON;
import com.ppgames.util.RandomUtil;

import java.util.ArrayList;

public class PvpRankRule {

    public static ArrayList<Integer> getEnemyRanks(final int myrank) {
        ArrayList<Integer> ranks = new ArrayList<>();
        int tmpRank = myrank + 1;

        if (tmpRank > 20) {
            int r0 = RandomUtil.getRandomNoTo(tmpRank - 4 * tmpRank / 10, tmpRank - 3 * tmpRank / 10) - 1;
            if (r0 <= PvpRanks.max_rank) {
                ranks.add(r0);
            }
            int r1 = RandomUtil.getRandomNoTo(tmpRank - 3 * tmpRank / 10, tmpRank - 2 * tmpRank / 10) - 1;
            if (r1 <= PvpRanks.max_rank) {
                ranks.add(r1);
            }
            int r2 = RandomUtil.getRandomNoTo(tmpRank - 2 * tmpRank / 10, tmpRank - tmpRank / 10) - 1;
            if (r2 <= PvpRanks.max_rank) {
                ranks.add(r2);
            }
            int r3 = RandomUtil.getRandomNoTo(tmpRank - tmpRank / 10, tmpRank) - 1;
            if (r3 <= PvpRanks.max_rank) {
                ranks.add(r3);
            }
            int r4 = RandomUtil.getRandomNoTo(tmpRank + tmpRank / 10, tmpRank + 2 * tmpRank / 10) - 1;
            if (r4 <= PvpRanks.max_rank) {
                ranks.add(r4);
            }
        } else if (tmpRank > 4 && tmpRank <= 20) {
            ranks.add(tmpRank - 4 - 1);
            ranks.add(tmpRank - 3 - 1);
            ranks.add(tmpRank - 2 - 1);
            ranks.add(tmpRank - 1 - 1);
            ranks.add(tmpRank + 1 - 1);
            ranks.add(tmpRank + 2 - 1);
        } else if (tmpRank == 4) {
            ranks.add(1 - 1);
            ranks.add(2 - 1);
            ranks.add(3 - 1);
            ranks.add(5 - 1);
            ranks.add(6 - 1);
            ranks.add(7 - 1);
        } else if (tmpRank == 3) {
            ranks.add(1 - 1);
            ranks.add(2 - 1);
            ranks.add(4 - 1);
            ranks.add(5 - 1);
            ranks.add(6 - 1);
            ranks.add(7 - 1);
        } else if (tmpRank == 2) {
            ranks.add(1 - 1);
            ranks.add(3 - 1);
            ranks.add(4 - 1);
            ranks.add(5 - 1);
            ranks.add(6 - 1);
            ranks.add(7 - 1);
        } else if (tmpRank == 1) {
            ranks.add(2 - 1);
            ranks.add(3 - 1);
            ranks.add(4 - 1);
            ranks.add(5 - 1);
            ranks.add(6 - 1);
            ranks.add(7 - 1);
        }
        return ranks;
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSON(getEnemyRanks(0)));
        System.out.println(JSON.toJSON(getEnemyRanks(1)));
        System.out.println(JSON.toJSON(getEnemyRanks(2)));
        System.out.println(JSON.toJSON(getEnemyRanks(3)));
        System.out.println(JSON.toJSON(getEnemyRanks(4)));
        System.out.println(JSON.toJSON(getEnemyRanks(5)));
        System.out.println(JSON.toJSON(getEnemyRanks(6)));
        System.out.println(JSON.toJSON(getEnemyRanks(10)));
        System.out.println(JSON.toJSON(getEnemyRanks(20)));
        System.out.println(JSON.toJSON(getEnemyRanks(1000)));
    }

}