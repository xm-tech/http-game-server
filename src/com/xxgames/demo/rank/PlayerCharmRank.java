package com.xxgames.demo.rank;

import com.alibaba.fastjson.JSONArray;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.utils.Const;

import java.util.*;

/**
 * Created by PhonePadPC on 2017/7/10.
 */
public class PlayerCharmRank {
    private PlayerCharmRank(){
    }
    private static final PlayerCharmRank instance = new PlayerCharmRank();
    public static PlayerCharmRank getInstance(){
        return instance;
    }

    private static JSONArray _charm_ranks = new JSONArray();
    List<Player> _all_player_list ;
    public synchronized void updateRank(){
        _charm_ranks.clear();

        Map<Long, Player> all_palyer_map = Cache.players;
        _all_player_list = new ArrayList<Player>(all_palyer_map.values());

        Collections.sort(_all_player_list, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                if (o1.getCharm() == o2.getCharm()){
                    return o1.getId() > o2.getId() ? 1 : -1 ;
                }
                else return o2.getCharm() - o1.getCharm();
            }
        });
        int rank = 0;
        for(Player player :_all_player_list) {
            player.setCharmRank(rank );
            RankData rank_data = new RankData();
            rank_data.setPid(player.getId());
            rank_data.setRank(rank);
            rank_data.setName(player.getName());
            rank_data.setLevel(player.getLevel());
            rank_data.setEquips(player.getDressRoom().getJSONArray("equips"));
            rank_data.setHeents(player.getLogo());
            rank_data.setVipLevel(player.getVipLevel());
            rank_data.setRankScore(player.getCharm());
            _charm_ranks.add(rank,rank_data);

            rank ++ ;
            if (rank >= Const.MAX_RANK_PEOPLE) break;
        }
    }
    public JSONArray getRanks(){
        return _charm_ranks;
    }

    public void sendRankReward(){
        updateRank();

        //_ranks
    }
}
