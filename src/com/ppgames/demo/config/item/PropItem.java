package com.ppgames.demo.config.item;

import com.alibaba.fastjson.JSONObject;

public class PropItem {
    private int _type;
    private int _id;
    private int _num;

    //fastjson 反序列化需要默认构造函数
    public PropItem() {
    }
    public PropItem(int type,int id ,int num ){
        this._id = id ;
        this._type = type ;
        this._num = num;
    }
    public PropItem(JSONObject object) {
        _type = object.getIntValue("type");
        _id = object.getIntValue("id");
        _num = object.getIntValue("num");
    }
    public JSONObject toJsonObject(){
        JSONObject ob = new JSONObject();
        ob.put("type",_type);
        ob.put("id",_id);
        ob.put("num",_num);
        return ob;
    }
    public int getType() {
        return _type;
    }

    public int getId() {
        return _id;
    }

    public void setId(int value) {
        _id = value;
    }

    public int getNum() {
        return _num;
    }

    public void setNum(int value) {
        _num = value;
    }

    public void setType(int value) {
        _type = value;
    }
}
