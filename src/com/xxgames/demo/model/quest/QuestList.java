package com.xxgames.demo.model.quest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.demo.activity.ActivityBase;
import com.xxgames.demo.activity.ActivityGroup;
import com.xxgames.demo.activity.ActivityManager;
import com.xxgames.demo.cache.Cache;
import com.xxgames.demo.config.config.*;
import com.xxgames.demo.model.Player;
import com.xxgames.demo.utils.Const;
import com.xxgames.util.RandomUtil;
import com.xxgames.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Created by Tony on 2017/6/12.
 */
public class QuestList {

    /**
     * 顾客任务
     */
    private final List<QuestCustomer> customerQuests = new ArrayList<>();
    private int customerQuestFinishedNumPerDay;
    private int customerQuestBeginTime;
    /**
     * 主线任务
     */
    private QuestMainLine mainLineQuest;
    /**
     * 活跃度任务
     */
    private QuestActiveValue activeValueQuest;


    /**
     * NPC任务
     */

    private QuestNpc npcQuest;
    private int npcQuestBeginTime;
    private int npcQuestFinishedNumPerDay;

    /**
     * 销售任务
     */
    private QuestSale saleQuest;
    private int saleQuestId;
    private int saleQuestBeginTime;
    private int saleQuestStatus;



    /**
     * 运营活动任务
     */
    private final List<QuestActivity> activityQuests = new ArrayList<>();
    public List<QuestActivity> getActivityQuests() {
        return activityQuests;
    }

    private long pid;

    public QuestList() {
    }

    public QuestList(long pid) {
        this.pid = pid;
        JSONObject oneQuestConf = JSON.parseObject(JSON.toJSONString(MainLineQuestConfig.getInstance().getItem(0)));
        JSONArray tasks = JSON.parseArray(oneQuestConf.getString("tasks"));
        mainLineQuest = (QuestMainLine) QuestFacotry.createQuest(EQuestType.MAIN_LINE, oneQuestConf, tasks, pid, this);
        JSONArray actvieTasks = JSON.parseArray(ActiveValueQuestConfig.getInstance().getItem(0).getTasks());
        activeValueQuest = (QuestActiveValue) QuestFacotry
            .createQuest(EQuestType.ACTIVE_VALUE, JSON.parseObject(JSON.toJSONString(ActiveValueQuestConfig.getInstance().getItem(0))), actvieTasks, pid, this);
        createNewPlayerActivityQuest();
        createNormalActivityQuest();
    }
    public void createActivityQuest(Map<Integer, ActivityGroup> activityGroupMap){
        for (Entry<Integer, ActivityGroup> e: activityGroupMap.entrySet()) {
            ActivityGroup activityGroup = e.getValue();
            if (!activityGroup.isValid()) continue;
            int type = activityGroup.getType();
            if (type == Const.ACT_TYPE_FUND_LEVEL_UP) continue; //基金类不给开启
            Map<Integer,ActivityBase> ActivityMap = activityGroup.getActivityMap();
            for (Entry<Integer, ActivityBase> activityEntry : ActivityMap.entrySet()){
                QuestActivity activity = addNewActivityQuest(activityEntry.getValue());
                if (type == Const.ACT_TYPE_LOGIN){
                    activity.setBeginTime(TimeUtil.startOfDay());
                }
            }
        }
    }
    private void createNormalActivityQuest(){
        Map<Integer, ActivityGroup> activityGroupMap = ActivityManager.getInstance().getNormalGroupMap();
        createActivityQuest(activityGroupMap);
    }
    private void createNewPlayerActivityQuest(){
        Map<Integer, ActivityGroup> activityGroupMap = ActivityManager.getInstance().getNewPlayerGroupMap();
        createActivityQuest(activityGroupMap);
    }
    public QuestList(Map m) {
        //主线任务
        loadMainLineQuest(m);
        //活跃度任务
        loadActiveValueQuest(m);
        //顾客任务
        loadCustomerQuest(m);
        //npc任务
        loadNpcQuest(m);
        //销售任务
        loadSaleQuest(m);
        //运营活动任务
        loadActivityQuest(m);
    }

    public QuestNpc getNpcQuest() {
        return npcQuest;
    }

    public Quest getMainLineQuests() {
        return mainLineQuest;
    }

    public QuestSale getSaleQuest(){return saleQuest;}

    /*
    void InitQuests(EString questsString, List<Quest> list, JSONArray tasksConfigData, long pid) {
        JSONArray array = JSONArray.parseArray(questsString);
        for (Object obj : array) {
            JSONObject jsonObject = (JSONObject) obj;
            Quest quest = QuestFacotry.createQuest(jsonObject, tasksConfigData, pid);
            list.add(quest);
        }
    }
*/
    public void onTakeMainLineReward() {
        int nextQuestId = (int) (mainLineQuest.getId() + 1);
        if (MainLineQuestConfig.getInstance().getItem(nextQuestId) == null) {
            return;
        }
        loadMainLineQuest(nextQuestId);
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public QuestActiveValue getActiveValueQuest() {
        return activeValueQuest;
    }

    public boolean isNeedUpdateCustomerQuests() {

        return updateCustomerQuests();
    }

    public boolean isNeedUpdateNpcQuest() {
        return updateNpcQuest();
    }

    public boolean isNeedUpdateSaleQuest(){
        return updateSaleQuest();
    }

    public List<QuestCustomer> getCustomerQuests() {
        updateCustomerQuests();
        return customerQuests;
    }

    public JSONObject getMainLineDataForDb(){
        return mainLineQuest.getDataForDb();
    }

    public JSONObject getActiveValueDataForDb(){
        return activeValueQuest.getDataForDb();
    }

    public JSONObject getCustomerQuestDataForDb() {
        JSONObject data = new JSONObject();
        data.put("beginTime", customerQuestBeginTime);
        data.put("finishedNum", customerQuestFinishedNumPerDay);
        return data;
    }

    public JSONObject getNpcQuestDataForDb() {
        JSONObject data = new JSONObject();
        data.put("beginTime", npcQuestBeginTime);
        data.put("finishedNum", npcQuestFinishedNumPerDay);
        return data;
    }

    public JSONObject getSaleQuestDataForDb()    {
        JSONObject data = new JSONObject();
        data.put("questId", saleQuestId);
        data.put("beginTime", saleQuestBeginTime);
        data.put("status", saleQuestStatus);
        return data;
    }
    public JSONArray getActivityQuestDataForDb(){
        JSONArray array = new JSONArray();
        for (int i = 0 ;i < activityQuests.size() ; i ++){
            QuestActivity activity = activityQuests.get(i);
            JSONObject data = new JSONObject();
            data.put("groupId",activity.getGroupId());
            data.put("aid",activity.getAid());
            data.put("status",activity.getStatus());
            data.put("beginTime",activity.getBeginTime());
            array.add(data);
        }
        return array;
    }

    public void customerQuestFinish(QuestCustomer quest) {
        if (customerQuests.contains(quest)) {
            customerQuestFinishedNumPerDay++;
        }
    }

    public void npcQuestFinish() {
        npcQuestFinishedNumPerDay++;
    }

    public void npcQuestFailed() {
        npcQuest = null;
    }

    public void saleQuestFinish(){
        saleQuestStatus = EQuestStatus.FINISH.ordinal();
        saleQuest.setStatus(saleQuestStatus);
    }

    public QuestSale beginSaleQuest(){
        saleQuest = null;
        addNewSaleQuest(Cache.players.get(pid).getLevel());

        saleQuestBeginTime = TimeUtil.nowInt();
        saleQuest.setBeginTime(saleQuestBeginTime);

        saleQuestStatus = EQuestStatus.IN_PROGRESS.ordinal();
        saleQuest.setStatus(saleQuestStatus);

        return saleQuest;
    }

    public JSONArray getCustomerQuestsForClient() {
        JSONArray array = new JSONArray();
        for (int i = 0; i < customerQuests.size(); ++i) {
            array.add(customerQuests.get(i).getDataForClient());
        }

        return array;
    }

    public JSONObject getNpcQuestForClient() {
        if (npcQuest == null || npcQuest.status != EQuestStatus.IN_PROGRESS.ordinal()) {
            return null;
        }

        return npcQuest.getDataForClient();
    }

    public JSONObject GetSaleQuestForClient(){
        if(saleQuest == null ){
            return null;
        }

        if(saleQuest.isOutOfDate() || saleQuest.status == EQuestStatus.FINISH.ordinal())
        {
            return null;
        }

        if(saleQuest.status == EQuestStatus.IN_PROGRESS.ordinal()){
            saleQuest.updateSaleStatus();
            saleQuestStatus = saleQuest.getStatus();
        }
        return saleQuest.getDataForClient();
    }


    public void addNewCustomerQuest() {
        int level = Cache.players.get(pid).getLevel();
        JSONObject data = JSON.parseObject(JSON.toJSONString(CustomerQuestConfig.getInstance().getItem(level)));
        JSONArray tasks = JSON.parseArray(data.getString("tasks"));
        QuestCustomer questCustomer = (QuestCustomer) QuestFacotry
            .createQuest(EQuestType.DAILY_CUSTOMER, data, tasks, pid, this);
        questCustomer.setDataId(questCustomer.getId());

        if (customerQuests.size() == 0) {
            questCustomer.setId(0);
        } else {
            questCustomer.setId(customerQuests.get(customerQuests.size() - 1).getId() + 1);
        }
        customerQuests.add(questCustomer);
    }

    public void addNewNpcQuest() {
        int id = RandomUtil.nextInt(NpcQuestConfig.getInstance().getItemSize() - 1);
        JSONObject data = JSON.parseObject(JSON.toJSONString(NpcQuestConfig.getInstance().getItem(id)));
        JSONArray tasks = JSON.parseArray(data.getString("tasks"));
        npcQuest =
            (QuestNpc) QuestFacotry.createQuest(EQuestType.DAILY_NPC, data, tasks, pid, this);
        npcQuestBeginTime = TimeUtil.nowInt();
        npcQuest.setDataId(npcQuest.getId());
        npcQuest.setId(npcQuestFinishedNumPerDay);
    }

    public void addNewSaleQuest(int questId)
    {
        JSONObject data = JSON.parseObject(JSON.toJSONString(SaleQuestConfig.getInstance().getItem(questId)));
        JSONArray tasks = JSON.parseArray(data.getString("tasks"));
        saleQuest = (QuestSale) QuestFacotry.createQuest(EQuestType.DAILY_SALE, data, tasks, pid, this);

        saleQuestId = questId;
        saleQuestBeginTime = TimeUtil.nowInt();
        saleQuestStatus = EQuestStatus.REJECTED.ordinal();

        saleQuest.setStatus(saleQuestStatus);
        saleQuest.setBeginTime(saleQuestBeginTime);
    }
    public QuestActivity addNewActivityQuest(int groupId ,int aid){
        ActivityBase activity = ActivityManager.getInstance().getActivity(groupId,aid);
        if (activity == null) return null;
        return addNewActivityQuest(activity);
    }
    public QuestActivity addNewActivityQuest(ActivityBase activity){
        JSONObject actData = JSONObject.parseObject(JSON.toJSONString(activity));
        JSONArray tasks = activity.getTasks();
        QuestActivity activityQuest = (QuestActivity) QuestFacotry.createQuest(EQuestType.ACTIVITY, actData, tasks, pid, this);
        activityQuests.add(activityQuest);
        return activityQuest;
    }

    public boolean updateCustomerQuests() {
        boolean isNeedUpdateCustomerQuests = false;
        int timeNow = TimeUtil.nowInt();
        //判读是否已经过了一天
        if (!TimeUtil.sameDay(timeNow, customerQuestBeginTime)) {
            customerQuestFinishedNumPerDay = 0;
            customerQuests.clear();
            isNeedUpdateCustomerQuests = true;
        }

        //判读是否有过期任务
        for (int i = customerQuests.size() - 1; i >= 0; --i) {
            if (customerQuests.get(i).isOutOfDate()) {
                customerQuests.remove(i);
                isNeedUpdateCustomerQuests = true;
            }
        }
        int max_times = SystemConfConfig.getInstance().getCfg().getCustomer_task_times();
        Player player = Cache.players.get(pid);
        if (player != null && player.checkMonthCard()){
            max_times += 5;
        }
        //判断是否需要新加任务
        if ((customerQuests.size() == 0
             || customerQuestFinishedNumPerDay <= max_times)
            && timeNow - customerQuestBeginTime >= SystemConfConfig.getInstance().getCfg().getCustomer_task_begin()){
            customerQuestBeginTime = timeNow;
            addNewCustomerQuest();
            isNeedUpdateCustomerQuests = true;
        }

        return isNeedUpdateCustomerQuests;
    }

    public boolean updateNpcQuest() {
        boolean isNeedUpdate = false;
        int timeNow = TimeUtil.nowInt();
        //判读是否已经过了一天
        if (!TimeUtil.sameDay(timeNow, npcQuestBeginTime)) {
            npcQuestFinishedNumPerDay = 0;
            npcQuest = null;
            isNeedUpdate = true;
        }

        //判断任务是否过期
        if (npcQuest != null && npcQuest.isOutOfDate()) {
            npcQuest = null;
            isNeedUpdate = true;
        }

        //判读是否有需要新加任务
        if ((npcQuest == null || npcQuest.isOutOfDate()) &&
            timeNow - npcQuestBeginTime >= SystemConfConfig.getInstance().getCfg().getNpc_task_begin()){
            isNeedUpdate = true;
            addNewNpcQuest();
        }

        return isNeedUpdate;
    }

    public boolean updateSaleQuest(){
        boolean isNeedUpdate = false;

        //判断任务是否过期
        if (saleQuest != null && saleQuest.isOutOfDate()) {
            if(saleQuestStatus == EQuestStatus.IN_PROGRESS.ordinal()){
                saleQuest.updateSaleStatus();
                saleQuestStatus = saleQuest.getStatus();
            }

            //判断上一次任务是否成功，奖励是否已发放
            if(saleQuestStatus == EQuestStatus.SUCCESS.ordinal()){
                //发送奖励邮件
                saleQuest.postRewardMail();
                saleQuestFinish();
            }

            saleQuest = null;
            isNeedUpdate = true;
        }

        //判断任务状态是否发生变化
        if(saleQuest != null && saleQuestStatus == EQuestStatus.IN_PROGRESS.ordinal()){

            saleQuest.updateSaleStatus();
            saleQuestStatus = saleQuest.getStatus();

            if(saleQuestStatus != EQuestStatus.IN_PROGRESS.ordinal()){
                isNeedUpdate = true;
            }
        }

        int playerLevel = Cache.players.get(pid).getLevel();
        //判读是否有需要新加任务
        if (saleQuest == null && playerLevel >= SystemConfConfig.getInstance().getCfg().getSale_quest_start_level()){
            int timeBegin1 = TimeUtil.getTimeForDay(-1, -1, SystemConfConfig.getInstance().getCfg().getSale_task_begin1());
            int timeEnd1 = TimeUtil.getTimeForDay(-1, -1, SystemConfConfig.getInstance().getCfg().getSale_task_end1());

            int timeBegin2 = TimeUtil.getTimeForDay(-1, -1, SystemConfConfig.getInstance().getCfg().getSale_task_begin2());
            int timeEnd2 = TimeUtil.getTimeForDay(-1, -1, SystemConfConfig.getInstance().getCfg().getSale_task_end2());

            int now = TimeUtil.nowInt();

            if((now >= timeBegin1 && now <= timeEnd1 ) || (now >= timeBegin2 && now <= timeEnd2)) {
                addNewSaleQuest(playerLevel);
                isNeedUpdate = true;
            }
        }

        return isNeedUpdate;
    }

    public void loadMainLineQuest(int questId) {
        JSONObject oneQuestConf = JSON.parseObject(JSON.toJSONString(MainLineQuestConfig.getInstance().getItem(questId)));
        JSONArray tasks = JSON.parseArray(oneQuestConf.getString("tasks"));
        mainLineQuest =
            (QuestMainLine) QuestFacotry.createQuest(EQuestType.MAIN_LINE, oneQuestConf, tasks, pid, this);
    }

    public void loadMainLineQuest(Map m) {
        JSONObject mainLineSaveData = JSON.parseObject(m.get("mainline").toString());
        pid = Long.parseLong(m.get("pid").toString());
        int questId = mainLineSaveData.getIntValue("id");
        loadMainLineQuest(questId);
    }

    public void loadActiveValueQuest(Map m) {
        JSONObject savedData = JSON.parseObject(m.get("activeValue").toString());
        JSONArray taskArray = JSON.parseArray(ActiveValueQuestConfig.getInstance().getItem(0).getTasks());
        activeValueQuest =
            (QuestActiveValue) QuestFacotry.createQuest(EQuestType.ACTIVE_VALUE, savedData, taskArray, pid, this);
    }

    public void loadCustomerQuest(Map m) {
        //顾客任务
        JSONObject customerQuestSaveData = JSON.parseObject(m.get("customer").toString());
        customerQuestBeginTime = 0;
        if (customerQuestSaveData != null && customerQuestSaveData.containsKey("beginTime")) {
            customerQuestBeginTime = customerQuestSaveData.getInteger("beginTime");
        }
        customerQuestFinishedNumPerDay = 0;
        if (customerQuestSaveData != null && customerQuestSaveData.containsKey("finishedNum")) {
            customerQuestFinishedNumPerDay = customerQuestSaveData.getInteger("finishedNum");
        }
    }

    public void loadNpcQuest(Map m) {
        //npc任务
        JSONObject saveData = JSON.parseObject(m.get("npc").toString());
        npcQuestBeginTime = 0;
        if (saveData != null && saveData.containsKey("beginTime")) {
            npcQuestBeginTime = saveData.getInteger("beginTime");
        }
        npcQuestFinishedNumPerDay = 0;
        if (saveData != null && saveData.containsKey("finishedNum")) {
            npcQuestFinishedNumPerDay = saveData.getInteger("finishedNum");
        }
    }

    public void loadSaleQuest(Map m){
        JSONObject saveData = JSON.parseObject(m.get("sale").toString());

        if(saveData != null && saveData.containsKey("questId")){
            addNewSaleQuest(saveData.getInteger("questId"));
        }
        else{
            addNewSaleQuest(Cache.players.get(pid).getLevel());
        }

        if(saveData != null && saveData.containsKey("beginTime")){
            saleQuestBeginTime = saveData.getInteger("beginTime");
            saleQuest.setBeginTime(saleQuestBeginTime);
        }

        if (saveData != null && saveData.containsKey("status")) {
            saleQuestStatus = saveData.getInteger("status");
            saleQuest.setStatus(saleQuestStatus);
        }
    }
    public void loadActivityQuest(Map m){
        JSONArray saveDataArr = JSON.parseArray(m.get("activity").toString());
        for (int i = 0 ; i < saveDataArr.size(); i ++){
            JSONObject saveData = saveDataArr.getJSONObject(i);
            if (saveData == null) return ;
            if (saveData.containsKey("groupId") && saveData.containsKey("aid")){
                QuestActivity activity = addNewActivityQuest(saveData.getInteger("groupId"),saveData.getInteger("aid"));
                activity.setStatus(saveData.getInteger("status"));
                activity.setBeginTime(saveData.getInteger("beginTime"));
            }
        }
    }
    public List<QuestActivity> getActivityQuestsByGroup(int groupId){
        List<QuestActivity> list = new ArrayList<QuestActivity>() ;
        for (QuestActivity quest:activityQuests) {
            if (quest.getGroupId() == groupId && !quest.isOutOfDate()){
                list.add(quest);
            }
        }
        return list;
    }
    public int getActivityGroupStatus(int groupId){
        List<QuestActivity> list = getActivityQuestsByGroup(groupId);
        if (list.size() == 0) return -1 ;
        for (QuestActivity quest:list) {
            if (!quest.isOutOfDate()){
                if (quest.getStatus() == EQuestStatus.SUCCESS.ordinal()){
                    return 1;
                }
            }
        }
        return 0 ;
    }
    public void addFundQuestList(){
        ActivityGroup fundGroup = ActivityManager.getInstance().getFundActivityGroups();
        if (fundGroup == null) return ;
        Set<Map.Entry<Integer, ActivityBase>> entrySet = fundGroup.getActivityMap().entrySet();

        for (Entry<Integer, ActivityBase> e : entrySet){
            addNewActivityQuest(e.getValue());
        }
    }
    public QuestActivity getActivityQuest(int groupId ,int aid){
        for (QuestActivity quest:activityQuests) {
            if (quest.getGroupId() == groupId && quest.getAid() == aid){
                return quest;
            }
        }
        return null;
    }
}
