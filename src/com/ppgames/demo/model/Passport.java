package com.ppgames.demo.model;

import com.ppgames.core.db.GameLogicDataSource;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by PhonePadPC on 2017/7/28.
 */
public class Passport {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassPort() {
        return passPort;
    }

    public void setPassPort(String passPort) {
        this.passPort = passPort;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public int getBinding() {
        return isBinding;
    }

    public void setBinding(int binding) {
        isBinding = binding;
    }

    private int id ;
    private String passPort ;
    private int serverId;
    private long pid ;
    private int isBinding;
    public Passport(int serverId,String passPort){
        id = 0 ;
        pid = 0 ;
        isBinding = 0;
        setServerId(serverId);
        setPassPort(passPort);
    }
    public Passport(Map m){
        id = Integer.parseInt(m.get("id") + "");
        passPort = m.get("passPort") + "";
        serverId = Integer.parseInt(m.get("serverId")+ "");
        pid = Long.parseLong(m.get("pid") + "");
        isBinding = Integer.parseInt(m.get("isBinding")+"");
    }
    public void insterToDB(){
        try {
            Object idObj = GameLogicDataSource.instance.insertAndReturnKey("insert into t_passport(`passPort`,`serverId`,`pid`,`isBinding`) values (?,?,?,?)",getPassPort(),getServerId(),getPid(),getBinding());
            this.id = Integer.parseInt(idObj!=null?idObj.toString():"");
        }
        catch (SQLException e){
            e.printStackTrace();
            return;
        }

    }
}
