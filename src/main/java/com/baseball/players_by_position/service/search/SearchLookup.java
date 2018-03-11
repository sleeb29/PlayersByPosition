package com.baseball.players_by_position.service.search;

import com.baseball.players_by_position.model.AbstractPlayer;

import java.util.HashMap;
import java.util.Map;

public class SearchLookup {

    public enum DepthLookup {

        TEAM(0),
        LAST_NAME(1),
        FIRST_NAME(2);

        private static Map map = new HashMap<>();

        static {
            for (DepthLookup depthLookup : DepthLookup.values()) {
                map.put(depthLookup.value, depthLookup);
            }
        }

        DepthLookup(int value) {
            this.value = value;
        }

        private int value;

        static Boolean hasValue(int depthSetting) {

            for (int i = 0; i < DepthLookup.values().length; i++) {

                if (DepthLookup.values()[i].getValue() == depthSetting) {
                    return true;
                }

            }

            return false;
        }

        public static DepthLookup valueOf(int depthLookup) {
            return (DepthLookup) map.get(depthLookup);
        }

        public int getValue() {
            return value;
        }

        public String getLookupStringBasedOnDepth(AbstractPlayer player) {

            if (this.value == TEAM.value) {
                return player.getTeam();
            } else if (this.value == LAST_NAME.value) {
                return player.getLookupLastName();
            } else {
                return player.getLookupLastName();
            }

        }

    }

}
