package com.hk.single_selection_recycler_view;

/**
 * Created by Hovhannisyan.Karo on 03.01.2018.
 */

public class MFont {
    private String text;
    private boolean isSelected;

    public MFont() {
    }

    public MFont(String text, boolean isSelected) {
        this.text = text;
        this.isSelected = isSelected;
    }

    public String getName() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
