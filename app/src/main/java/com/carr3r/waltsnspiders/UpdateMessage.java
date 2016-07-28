package com.carr3r.waltsnspiders;

/**
 * Created by Neto on 25/10/2015.
 */
public class UpdateMessage {

    private int component;

    public UpdateMessage(int component) {
        this.component = component;
    }

    public void setComponent(int newValue) {
        component = newValue;
    }

    public int getComponent() {
        return component;
    }
}
