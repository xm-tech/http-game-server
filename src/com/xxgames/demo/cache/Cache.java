package com.xxgames.demo.cache;

import com.xxgames.demo.dao.PassPortDao;
import com.xxgames.demo.dao.PlayerDao;
import com.xxgames.demo.dao.QuestDao;
import com.xxgames.demo.dao.TagDao;
import com.xxgames.demo.model.Passport;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.quest.QuestList;
import com.xxgames.demo.model.quest.QuestManager;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    public static final Logger log = Logger.getLogger(Cache.class);

    // cache 始终保持数据最新
    public static Map<Long, Player> players = new ConcurrentHashMap<>();
    public static Map<String , Passport> passports = new ConcurrentHashMap<>();

    public static void rebuild() throws SQLException {
        rebuildPassPort();
        rebuildPlayer();
        rebuildPlayerQuests();

        rebuildTimeTag();
    }
    public static synchronized void rebuildPlayer() throws SQLException{
        players.clear();
        log.debug("clear cache player, " + players.size());
        List<Map<String, Object>> allPlayers = PlayerDao.getInstance().getAllPlayers();
        for (Map<String, Object> m : allPlayers) {
            Player p = new Player(m);
            p.int_goods_places();
            players.put(p.getId(), p);
        }
    }
    public static synchronized void rebuildPlayerQuests() throws SQLException{
        log.debug("clear cache player quests, " + players.size());
        List<Map<String, Object>> allPlayersQuests = QuestDao.getInstance().getAllPlayersQuests();
        for (Map<String, Object> m : allPlayersQuests) {
            QuestList questList = new QuestList(m);
            QuestManager.getInstance().AddQuestList(Long.parseLong(m.get("pid").toString()), questList);
        }
    }
    public static synchronized void rebuildPassPort() throws SQLException{
        passports.clear();
        log.debug("clear cache passport, " + passports.size());
        List<Map<String, Object>> allPassports = PassPortDao.getInstance().getAllPassports();
        for (Map<String, Object> m : allPassports) {
            Passport passport = new Passport(m);
            String key = passport.getServerId() + "_" + passport.getPassPort();
            passports.put(key, passport);
        }
    }
    public static synchronized void rebuildTimeTag()throws SQLException{
        List<Map<String, Object>> allPlayerTimeTags = TagDao.getInstance().loadAllPlayerTimeTag();
        List<Map<String, Object>> allPlayerCountTags = TagDao.getInstance().loadAllPlayerCountTag();

        for (Map<String,Object> m:
            allPlayerTimeTags) {
            long pid = Long.parseLong(m.get("player_id") + "");
            int tag_id = Integer.parseInt(m.get("tag_id") + "");
            int time = Integer.parseInt(m.get("time") + "");
            Player player = players.get(pid);
            if (player != null){
                player.getTimeTagManager().load(tag_id,time);
            }
        }

        for (Map<String,Object> m:
             allPlayerCountTags) {
            long pid = Long.parseLong(m.get("player_id") + "");
            int tag_id = Integer.parseInt(m.get("tag_id") + "");
            long count = Long.parseLong(m.get("count")+ "");
            int time = Integer.parseInt(m.get("time") + "");
            Player player = players.get(pid);
            if (player != null){
                player.getCountTagManager().load(tag_id,count,time);
            }
        }

    }
}
