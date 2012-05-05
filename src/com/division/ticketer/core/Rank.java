/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.division.ticketer.core;

/**
 *
 * @author Evan
 */
public enum Rank {

    SUPERUSER("superuser"),
    USER("user"),
    GUEST("guest");
    private String rank;

    private Rank(String rankname) {
        this.rank = rankname;
    }

    public String getRank() {
        return rank;
    }
}
