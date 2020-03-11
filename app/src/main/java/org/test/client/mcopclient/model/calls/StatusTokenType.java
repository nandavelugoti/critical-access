package org.test.client.mcopclient.model.calls;

public enum StatusTokenType {
    NONE {
        @Override
        public String toString() {
            return "NONE";
        }
    },
    GRANTED{
        @Override
        public String toString() {
            return "GRANTED";
        }
    },
    IDLE{
        @Override
        public String toString() {
            return "IDLE";
        }
    },
    TAKEN{
        @Override
        public String toString() {
            return "TAKEN";
        }
    }
}
