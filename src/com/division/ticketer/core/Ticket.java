/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.division.ticketer.core;

import org.bukkit.entity.Player;

/**
 *
 * @author Evan
 */
public class Ticket {

    private String subject;
    private String message;
    private Player player;

    public Ticket(Player p) {
        this.player = p;
    }

    public Player getPlayer() {
        return player;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        message = msg;
    }

    public void setPlayer(Player p) {
        player = p;
    }

    public boolean appendMessage(String append) {
        if (!message.equals("")) {
            message += (" " + append);
            return true;
        } else {
            setMessage(append);
            return true;
        }
    }

    public void setSubject(String sub) {
        subject = sub;
    }
}
