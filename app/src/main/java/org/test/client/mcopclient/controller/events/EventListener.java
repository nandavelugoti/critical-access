package org.test.client.mcopclient.controller.events;

import android.content.Intent;

public interface EventListener {
    void handleEvent(Intent action);
}
