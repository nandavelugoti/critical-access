package org.test.client.mcopclient.model.calls;

import org.test.client.mcopclient.model.ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum;
import org.test.client.mcopclient.model.ConstantsMCOP.CallEventExtras.CallTypeEnum;
public class Call implements Callable {
    String id;
    private int priority;
    private CallTypeEnum callType;
    private FloorControlEventTypeEnum state;

    Call() {

    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public FloorControlEventTypeEnum getState() {
        return state;
    }

    public void setState(FloorControlEventTypeEnum state) {
        this.state = state;
    }

    @Override
    public void call() {

    }

    @Override
    public void hangup() {

    }

    @Override
    public void update() {

    }


}