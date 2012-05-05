/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.division.ticketer.spout;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 *
 * @author Evan
 */
public class SpoutNotif {

    public SpoutPlayer getSpoutPlayer(String player) {
        for (Player x : Bukkit.getServer().getOnlinePlayers()) {
            if (x.getName().equalsIgnoreCase(player)) {
                return SpoutManager.getPlayer(x);
            }
        }
        return null;
    }

    public void sendNotification(String player, String title, String message, Material mat) {
        SpoutPlayer sPlayer = getSpoutPlayer(player);
        if (sPlayer != null) {
            sPlayer.sendNotification(title, message, mat);
        }
    }
}
