package com.xxgames.demo.rank;

import com.alibaba.fastjson.JSONArray;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.RankListSaleConfig;
import com.xxgames.demo.config.item.RankListSaleItem;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.mail.Mail;
import com.xxgames.demo.model.mail.MailManager;
import com.xxgames.demo.config.item.PropItem;
import com.xxgames.demo.utils.Const;

import java.util.*;

/**
 * Created by PhonePadPC on 2017/7/10.
 */
public class PlayerSellRank {
    private PlayerSellRank(){
    }
    private static final PlayerSellRank instance = new PlayerSellRank();
    public static PlayerSellRank getInstance(){
        return instance;
    }

    private static JSONArray _ranks = new JSONArray();

    public JSONArray getRanks(){
        return _ranks;
    }

    private List<Player> _all_player_list ;
    public synchronized void updateRank(){
        _ranks.clear();
        Map<Long, Player> all_palyer_map = Cache.players;
        _all_player_list = new ArrayList<Player>(all_palyer_map.values());

        Collections.sort(_all_player_list, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                if (o1.getTodaySell() == o2.getTodaySell()){
                    return o1.getId() > o2.getId() ? 1 : -1 ;
                }
                else return o2.getTodaySell() > o1.getTodaySell() ? 1 : -1;
            }
        });
        int rank = 0;
        for(Player player :_all_player_list) {
            player.setSellRank(rank);
            RankData rank_data = new RankData();
            rank_data.setPid(player.getId());
            rank_data.setRank(rank);
            rank_data.setName(player.getName());
            rank_data.setLevel(player.getLevel());
            rank_data.setEquips(player.getDressRoom().getJSONArray("equips"));
            rank_data.setHeents(player.getLogo());
            rank_data.setVipLevel(player.getVipLevel());
            rank_data.setRankScore((int)player.getTodaySell());
            _ranks.add(rank,rank_data);

            rank ++ ;
            if (rank >= Const.MAX_RANK_PEOPLE) break;
        }
    }
    public ArrayList<PropItem> getRewardByRank(int rank){
        int length = RankListSaleConfig.getInstance().getMap().size();
        for (int i = 0 ; i < length ; i ++){
//            JSONObject data = JSON.parseObject(DataManager.ranking_list_sale.getString(i));
            RankListSaleItem item = RankListSaleConfig.getInstance().getItem(i);
            int min = item.getMin();
            int max = item.getMax();
            if (rank <= max && rank >= min){
                //JSONArray rewards = JSON.parseArray(data.getString("rewards"));
                return item.getRewards_list();
            }
        }
        return null;
    }
    public synchronized void sendRankReward(){
        updateRank();
        int num = 0 ;
        for(Player player :_all_player_list){
            if (num++ > 1000) return ;
            if (player.getSellRank() == -1) continue;
            int rank = player.getSellRank() + 1;
            ArrayList<PropItem> attached = new ArrayList<PropItem>();
            ArrayList<PropItem> rewards = getRewardByRank(rank);
            if (rewards == null) continue;
            for (int i = 0 ; i < rewards.size(); i ++ ){
                int type = rewards.get(i).getType();
                int id = rewards.get(i).getId();
                int n = rewards.get(i).getNum();
                PropItem reward = new PropItem(type,id,n);
                attached.add(reward);
            }
            String mialContent = "恭喜你在营业额排行中获得第"+rank+"名。";
            MailManager.getInstance().insertMail(player.getId(), 0, "系统邮件", "营业额排行", mialContent, attached, Mail.MTYPE_SYSTEM);
        }
    }

}
