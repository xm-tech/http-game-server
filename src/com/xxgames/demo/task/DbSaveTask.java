package com.xxgames.demo.task;

import com.xxgames.core.db.GameLogicDataSource;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.SystemConfConfig;
import com.xxgames.demo.dao.PassPortDao;
import com.xxgames.demo.dao.PlayerDao;
import com.xxgames.demo.dao.QuestDao;
import com.xxgames.demo.model.Passport;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.model.quest.QuestList;
import com.xxgames.demo.model.quest.QuestManager;
import com.xxgames.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DbSaveTask implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(DbSaveTask.class);

    public static final DbSaveTask single = new DbSaveTask();

    // set to false only by outer maintain
    public static volatile boolean run = true;
    public static boolean saving;

    long db_write_period = 5 * 60 * 1000L;

    private DbSaveTask() {
        init();
    }

    public void init() {
        db_write_period = SystemConfConfig.getInstance().getCfg().getDb_write_period();
        if (db_write_period <= 0L) {
            db_write_period = 5 * 60 * 1000L;
        }
        log.debug("db_write_period=" + db_write_period);
    }

    @Override
    public void run() {

        log.debug("DbSaveTask run ...");

        while (true) {
            if (!run) {
                // exit
                break;
            }
            try {
                Thread.sleep(db_write_period);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
                continue;
            }

            dbSave();
        }
    }

    public void dbSave() {
        log.debug("update cache begin ... ");
        saving = true;
        int beginTime = TimeUtil.nowInt();

        dbSavePlayer();
        dbSaveQuests();
        dbSavePlayerPassPort();
        dbSavePlayerTag();

        int endTime = TimeUtil.nowInt();
        saving = false;
        log.debug("update cache end ..., used : " + (endTime - beginTime) + "s");
    }
    // out thread call
    public void stop() {
        log.info("stop thread loop ");
        run = false;
        while (saving) {
            // wait last save over
            try {
                // try 1000ms later
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
        // end save
        dbSave();
        log.info("the last save end");
    }
    private void dbSavePlayer(){
        Map<Long, Player> players = Cache.players;
        Set<Entry<Long, Player>> entrySet = players.entrySet();

        String sql = "replace into t_player(" + PlayerDao.getInstance().fields + ") values ";
        int noDataSqlLen = sql.length();
        for (Entry<Long, Player> e : entrySet) {
            Player p = e.getValue();
            sql += PlayerDao.getInstance().getColumnsStr(p) + ",";
        }

        if (sql.length() > noDataSqlLen) {
            sql = sql.substring(0, sql.length() - 1);
            try {
                log.debug(sql);
                GameLogicDataSource.instance.update(sql);
            } catch (Exception e) {
                // danger op, FIXME record this exception
                run = false;
                // sql 执行异常,需手动检查错误
                log.error("DbSaveTask.dbSavePlayer exception, " + e.getMessage(), e);
            }
        }
    }
    private void dbSaveQuests() {
        Map<Long, QuestList> quests = QuestManager.getInstance().getQuestListManaager();
        if (quests.size() <= 0) {
            return;
        }

        Set<Entry<Long, QuestList>> entrySet = quests.entrySet();

        String sql = "replace into t_quest(" + QuestDao.getInstance().fields + ") values ";
        int noDataSqlLen = sql.length();
        for (Entry<Long, QuestList> e : entrySet) {
            QuestList q = e.getValue();
            sql += QuestDao.getInstance().getColumnsStr(q) + ",";
        }

        if (sql.length() > noDataSqlLen) {
            sql = sql.substring(0, sql.length() - 1);
            try {
                log.debug(sql);
                GameLogicDataSource.instance.update(sql);
            } catch (Exception e) {
                // danger op, FIXME record this exception
                run = false;
                // sql 执行异常,需手动检查错误
                log.error("DbSaveTask.dbSaveQuests exception, " + e.getMessage(), e);
            }
        }

    }
    private void dbSavePlayerPassPort(){
        Map<String, Passport> passports = Cache.passports;
        Set<Entry<String, Passport>> entrySet = passports.entrySet();

        String sql = "replace into t_passport(" + PassPortDao.getInstance().fields + ") values ";
        int noDataSqlLen = sql.length();
        for (Entry<String, Passport> e : entrySet) {
            Passport passport = e.getValue();
            sql += PassPortDao.getInstance().getColumnsStr(passport) + ",";
        }

        if (sql.length() > noDataSqlLen) {
            sql = sql.substring(0, sql.length() - 1);
            try {
                log.debug(sql);
                GameLogicDataSource.instance.update(sql);
            } catch (Exception e) {
                // danger op, FIXME record this exception
                run = false;
                // sql 执行异常,需手动检查错误
                log.error("DbSaveTask.dbSavePlayerPassPort exception, " + e.getMessage(), e);
            }
        }
    }
    private void dbSavePlayerTag(){
        dbSavePlayerCountTag();
        dbSavePlayerTimeTag();
    }
    private void dbSavePlayerCountTag(){
        Map<Long, Player> players = Cache.players;
        Set<Entry<Long, Player>> entrySet = players.entrySet();

        String fields =
            "`player_id`,`tag_id`,`count`,`time`";
        String sql = "insert into t_player_count_tag(" + fields + ") values ";

        int noDataSqlLen = sql.length();
        for (Entry<Long, Player> e : entrySet) {
            Player p = e.getValue();
            String str = p.getCountTagManager().getDbPoolStr();
            if (str != ""){
                sql += str;
            }

        }
        if (sql.length() > noDataSqlLen) {
            sql = sql.substring(0, sql.length() - 1);
            try {
                log.debug(sql);
                GameLogicDataSource.instance.update(sql);
            } catch (Exception e) {
                // danger op, FIXME record this exception
                run = false;
                // sql 执行异常,需手动检查错误
                log.error("DbSaveTask.dbSavePlayerCountTag exception, " + e.getMessage(), e);
            }
        }
    }
    private void dbSavePlayerTimeTag(){

    }

}