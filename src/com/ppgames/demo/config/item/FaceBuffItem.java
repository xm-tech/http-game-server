package com.ppgames.demo.config.item;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/15.
 */
public class FaceBuffItem {
    private int styleid;
    private ArrayList<Integer> bufAdd = new ArrayList<Integer>();
    public FaceBuffItem(int styleid, int level1buf, int level2buf, int level3buf){
        this.styleid = styleid;
        bufAdd.add(level1buf);
        bufAdd.add(level2buf);
        bufAdd.add(level3buf);
    }

    public int getStyleId() {
        return styleid;
    }

    public int getBuffAdd(int level){
        if(level > bufAdd.size()){
            System.err.print("FaceBuffItem::getBuffAdd() unknown level "+level);
            return 0;
        }
        return bufAdd.get(level);
    }
}
