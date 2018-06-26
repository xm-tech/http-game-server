package com.xxgames.demo.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xxgames.demo.config.config.OfficeConfig;
import com.xxgames.demo.config.config.SystemConfConfig;
import com.xxgames.demo.config.item.OfficeItem;
import com.xxgames.demo.utils.Const;

import java.util.Set;

/**
 * Created by PhonePadPC on 2017/7/19.
 */
public class PlayerShops {
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public JSONObject getDecros() {
        return decros;
    }

    public void setDecros(JSONObject decros) {
        this.decros = decros;
    }

    public JSONArray getShelves() {
        return shelves;
    }

    public void setShelves(JSONArray shelves) {
        this.shelves = shelves;
    }

    public int getRubbishs() {
        return rubbishs;
    }

    public void setRubbishs(int rubbishs) {
        this.rubbishs = rubbishs;
    }

    private int level = 0 ;
    private JSONObject decros ;
    private JSONArray shelves ;
    private int rubbishs = 0 ;
    private Player player;
    public PlayerShops(Player player){
        level = 0 ;
        decros = new JSONObject();
        shelves = new JSONArray();
        rubbishs = 0 ;
        this.player = player;
    }
    public void init (){
        level = SystemConfConfig.getInstance().getCfg().getShop_init_level();
        decros = JSON.parseObject(SystemConfConfig.getInstance().getCfg().getShop_init_decros());
        shelves = JSON.parseArray(SystemConfConfig.getInstance().getCfg().getShelf_init_conf());
        rubbishs = 0 ;
    }
    public void parseFormJson(String text){
        JSONObject json_data = JSON.parseObject(text);
        level = json_data.getInteger("level");
        rubbishs = json_data.containsKey("rubbishs") ?json_data.getInteger("rubbishs") : 0 ;

        decros = json_data.getJSONObject("decros");
        shelves = json_data.getJSONArray("shelves");
    }
    public String toJSONString(){
        return JSON.toJSONString(this);
    }

    public void addRubbishs(){
        Set<String> ids = player.getEmployees().keySet();
        for (String id : ids) {
            OfficeItem employeeConf = OfficeConfig.getInstance().getItem(Integer.parseInt(id));
            if (employeeConf.getType() == Const.EMPLOYEE_TYPE_BAOJIE) return;
        }
        rubbishs ++ ;
        if (rubbishs > 10){
            rubbishs = 10 ;
        }
    }
    public  void clearnOneRubbish(){
        if (rubbishs > 0) rubbishs -- ;
    }

}
