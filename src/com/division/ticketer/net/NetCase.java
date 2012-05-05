/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.division.ticketer.net;

/**
 *
 * @author Evan
 */
public enum NetCase {

    CONN("&C81"),
    CONNC("&C82"),
    CONNR("&C83"),
    DC("&C89"),
    CTO("&E82"),
    LRQ("&R12"),
    UKNC("&E74"),
    NOTIF("&F91"),
    PINFO("&R59"),
    MSG("&R92"),
    GML("&R99"),
    RSTATUS("&R49"),
    USTATUS("&F49"),
    ASNG("&R25");
    private String netcase;

    private NetCase(String ncase) {
        this.netcase = ncase;
    }

    public String getNetCase() {
        return netcase;
    }
}
