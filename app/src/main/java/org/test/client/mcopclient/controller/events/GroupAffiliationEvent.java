package org.test.client.mcopclient.controller.events;

import android.content.Intent;

import org.test.client.mcopclient.ConstantsMCOP;

import java.util.HashMap;
import java.util.Map;

import static org.test.client.mcopclient.ConstantsMCOP.ERROR_CODE_DEFAULT;

public class GroupAffiliationEvent implements EventListener {
    private final static String TAG = GroupAffiliationEvent.class.getCanonicalName();

    @Override
    public void handleEvent(Intent action) {
        int eventTypeInt = action.getIntExtra(ConstantsMCOP.GroupAffiliationEventExtras.EVENT_TYPE, ERROR_CODE_DEFAULT);
        ConstantsMCOP.GroupAffiliationEventExtras.GroupAffiliationEventTypeEnum eventTypeAffiliation = null;
        if (eventTypeInt != ERROR_CODE_DEFAULT &&
                (eventTypeAffiliation = ConstantsMCOP.GroupAffiliationEventExtras.GroupAffiliationEventTypeEnum.fromInt(eventTypeInt)) != null) {
            switch (eventTypeAffiliation) {
                case GROUP_AFFILIATION_UPDATE:
                    Map<String, Integer> groups = (HashMap<String, Integer>) action.getSerializableExtra(ConstantsMCOP.GroupAffiliationEventExtras.GROUPS_LIST);

                    break;
                case GROUP_AFFILIATION_ERROR:
                    if (action.getIntExtra(ConstantsMCOP.GroupAffiliationEventExtras.ERROR_CODE, ERROR_CODE_DEFAULT) != ERROR_CODE_DEFAULT) {
                        // Error in unLoginEvent
                        String stringError = action.getStringExtra(ConstantsMCOP.GroupAffiliationEventExtras.ERROR_STRING);
                        String groupID = action.getStringExtra(ConstantsMCOP.GroupAffiliationEventExtras.GROUP_ID);
                    }
                    break;
                case REMOTE_AFFILIATION:
                    break;
                default:
                    break;
            }
        } else {
        }

    }
}