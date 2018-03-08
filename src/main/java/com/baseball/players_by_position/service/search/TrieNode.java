package com.baseball.players_by_position.service.search;

import com.baseball.players_by_position.model.AbstractPlayer;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {

    Map<String, TrieNode> children;
    private AbstractPlayer player;

    public TrieNode() {
        this.player = null;
        this.children = new HashMap<>();
    }

    public TrieNode(AbstractPlayer player) {
        this.player = player;
        this.children = new HashMap<>();
    }

    public AbstractPlayer getPlayer() {
        return player;
    }

    public void setPlayer(AbstractPlayer player) {
        this.player = player;
    }
}
