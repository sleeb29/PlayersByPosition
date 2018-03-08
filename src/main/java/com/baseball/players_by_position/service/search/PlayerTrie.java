package com.baseball.players_by_position.service.search;

import com.baseball.players_by_position.model.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerTrie {

    private TrieNode root;

    public PlayerTrie() {
        this.root = new TrieNode();
    }

    public PlayerTrie(TrieNode root) {
        this.root = root;
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
        }

        SearchSettings.DepthSettings level = SearchSettings.DepthSettings.valueOf(depth);

        if (level.equals(SearchSettings.DepthSettings.TEAM)) {
            if (!node.children.containsKey(player.getTeam())) {
                return null;
            }
            return get(node.children.get(player.getTeam()), player, depth + 1);
        } else if (level.equals(SearchSettings.DepthSettings.LAST_NAME)) {
            if (!node.children.containsKey(player.getLastName())) {
                return null;
            }
            return get(node.children.get(player.getLastName()), player, depth + 1);
        } else if (level.equals(SearchSettings.DepthSettings.FIRST_NAME)) {
            if (node.children.containsKey(player.getFirstName()) && node.children.size() == 0) {
                return node;
            } else if (node.children.containsKey(player.getFirstName())) {
                return get(node.children.get(player.getFirstName()), player, depth + 1);
            } else {

                Map<String, TrieNode> children = node.children;
                FirstNameEditDistance firstNameEditDistance = new FirstNameEditDistance();
                int minMatchValue = Integer.MAX_VALUE;
                List<TrieNode> lowestMatches = new ArrayList<>();

                for (Map.Entry<String, TrieNode> entry : children.entrySet()) {

                    String firstName = entry.getKey();
                    TrieNode trieNode = entry.getValue();

                    Boolean foundMatch = firstNameEditDistance.isFuzzyMatch(player.getFirstName(), firstName);

                    if (foundMatch) {

                        int distance = firstNameEditDistance.getEditDistance(player.getFirstName(), firstName);

                        if (distance > minMatchValue) {
                            continue;
                        } else if (distance < minMatchValue) {
                            lowestMatches = new ArrayList<>();
                            minMatchValue = distance;
                        }

                        lowestMatches.add(trieNode);
                    }

                }

                if (lowestMatches.size() == 1 && lowestMatches.get(0).getPlayer().getJersey() == player.getJersey()) {
                    return lowestMatches.get(0);
                } else if (lowestMatches.size() == 1) {
                    return lowestMatches.get(0);
                } else if (lowestMatches.size() > 1) {
                    for (TrieNode trieNode : lowestMatches) {
                        if (trieNode.getPlayer().getJersey() == player.getJersey()) {
                            return trieNode;
                        }
                    }
                }

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

        SearchSettings.DepthSettings level = SearchSettings.DepthSettings.valueOf(depth);

        if (level.equals(SearchSettings.DepthSettings.TEAM)) {

            TrieNode teamTrie = null;
            if (!node.children.containsKey(player.getTeam())) {
                teamTrie = new TrieNode();
                node.children.put(player.getTeam(), teamTrie);
            } else {
                teamTrie = node.children.get(player.getTeam());
            }
            return put(teamTrie, player, depth + 1);

        } else if (level.equals(SearchSettings.DepthSettings.LAST_NAME)) {

            TrieNode lastNameTrie = null;
            if (!node.children.containsKey(player.getLastName())) {
                lastNameTrie = new TrieNode();
                node.children.put(player.getLastName(), lastNameTrie);
            } else {
                lastNameTrie = node.children.get(player.getLastName());
            }
            return put(lastNameTrie, player, depth + 1);

        } else {

            TrieNode firstNameTrie = new TrieNode(player);
            node.children.put(player.getFirstName(), firstNameTrie);
            return node;

        }

    }

}
