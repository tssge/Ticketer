/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.division.ticketer.mysql;

/**
 *
 * @author Evan
 */
public interface DataSource {

    public String getOpenTickets();

    public String getAssignments(String username);

    public String getPlayer(int ticketid);

    public String getMessage(int ticketid);

    public String getStatus(int ticketid);

    public void setState(int ticketid, String state);

    public void setGMAssign(int ticketid, String gm);
}
