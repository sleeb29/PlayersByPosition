package com.baseball.players_by_position.service.search;

import com.baseball.players_by_position.model.AbstractPlayer;

import java.util.Map;

public class PlayerTrie {

    private TrieNode root;

    public PlayerTrie() {
        this.root = new TrieNode();
    }

    public AbstractPlayer get(AbstractPlayer player) {
        TrieNode targetNode = get(root, player, 0);
        if (targetNode == null) {
            return null;
        } else {
            return targetNode.getPlayer();
        }
    }

    private TrieNode get(TrieNode node, AbstractPlayer player, int depth) {

        if (node == null) {
            return null;
        } else if (!SearchLookup.DepthLookup.hasValue(depth)) {
            return node;
        }

        SearchLookup.DepthLookup level = SearchLookup.DepthLookup.valueOf(depth);
        if (!node.children.containsKey(level.getLookupStringBasedOnDepth(player)) && !level.equals(SearchLookup.DepthLookup.FIRST_NAME)) {
            return null;
        } else if (!node.children.containsKey(level.getLookupStringBasedOnDepth(player))) {
            return tryToFindSecondaryPlayerMatch(node, player);
        }

        return get(node.children.get(level.getLookupStringBasedOnDepth(player)), player, depth + 1);

    }

    private TrieNode tryToFindSecondaryPlayerMatch(TrieNode node, AbstractPlayer player) {

        Map<String, TrieNode> children = node.children;

        //try matching on jersey
        for (Map.Entry<String, TrieNode> entry : children.entrySet()) {

            TrieNode child = entry.getValue();
            if (child.getPlayer().getJersey() == player.getJersey()) {
                return child;
            }

        }

        //try matching on position
        for (Map.Entry<String, TrieNode> entry : children.entrySet()) {

            TrieNode child = entry.getValue();
            if (child.getPlayer().getPosition().equals(player.getPosition())) {
                return child;
            }

        }

        return null;

    }

    public void put(AbstractPlayer player) {
        put(root, player, 0);
    }

    private TrieNode put(TrieNode node, AbstractPlayer player, int depth) {

        if (node == null) {
            node = new TrieNode();
        }

        if (!SearchLookup.DepthLookup.hasValue(depth)) {
            node.setPlayer(player);
            return node;
        }

        SearchLookup.DepthLookup level = SearchLookup.DepthLookup.valueOf(depth);
        String stringToPut = level.getLookupStringBasedOnDepth(player);

        TrieNode trieNode;
        if (!node.children.containsKey(stringToPut)) {
            trieNode = new TrieNode();
            node.children.put(stringToPut, trieNode);
        } else {
            trieNode = node.children.get(stringToPut);
        }

        return put(trieNode, player, depth + 1);

    }

}
