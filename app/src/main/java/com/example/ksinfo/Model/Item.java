package com.example.ksinfo.Model;

public class Item {

    private String text, subText;
    private boolean isExpandable;

    public int getDrawableID() {
        return drawableID;
    }

    public void setDrawableID(int drawableID) {
        this.drawableID = drawableID;
    }

    private int drawableID;

    public int getFunctionID() {
        return functionID;
    }

    public void setFunctionID(int functionID) {
        this.functionID = functionID;
    }

    private int functionID;



    public Item(String text, String subText, boolean isExpandable,int iconId,int functionID){
        this.text = text;
        this.subText = subText;
        this.isExpandable = isExpandable;
        this.drawableID = iconId;
        this.functionID = functionID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

}
