package com.baseball.players_by_position.service.search;

import com.baseball.players_by_position.model.AbstractPlayer;

import java.text.Normalizer;
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

        static Boolean hasValue(int depthSetting) {

            for (int i = 0; i < DepthSettings.values().length; i++) {

                if (DepthSettings.values()[i].getValue() == depthSetting) {
                    return true;
                }

            }

            return false;
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

        public String getString(AbstractPlayer player) {

            if (this.value == TEAM.value) {
                return player.getTeam();
            } else if (this.value == LAST_NAME.value) {
                return normalize(player.getLastName());
            } else {
                return normalize(player.getFirstName());
            }

        }

        private String normalize(String stringToNormalize) {

            String normalizedString = Normalizer.normalize(stringToNormalize, Normalizer.Form.NFD);

            StringBuilder stringBuilder = new StringBuilder();
            for (char c : normalizedString.toCharArray()) {
                if (c <= '\u007F') {
                    stringBuilder.append(c);
                }
            }

            return stringBuilder.toString();

        }

    }

}
