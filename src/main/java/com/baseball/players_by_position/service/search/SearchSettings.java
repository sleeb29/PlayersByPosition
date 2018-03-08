package com.baseball.players_by_position.service.search;

import java.util.HashMap;
import java.util.Map;

public class SearchSettings {

    public enum DepthSettings {

        TEAM(0),
        LAST_NAME(1),
        FIRST_NAME(2);

        private static Map map = new HashMap<>();

        static {
            for (DepthSettings depthSettings : DepthSettings.values()) {
                map.put(depthSettings.value, depthSettings);
            }
        }

        private int value;

        DepthSettings(int value) {
            this.value = value;
        }

        public static DepthSettings valueOf(int depthSetting) {
            return (DepthSettings) map.get(depthSetting);
        }

        public int getValue() {
            return value;
        }

    }

}
