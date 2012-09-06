/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.division.ticketer.mysql;

import com.division.ticketer.core.Ticket;
import org.bukkit.entity.Player;

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

    public void createTicket(Ticket ticket);

    public boolean hasOpenTicket(Player p);

    public int getTicketId(Player p);

    public String getSubject(int ticketid);

    public void setFlag(int ticketid, String msg, int flag);

    public boolean isFlagged(int ticketid);

    public String getFlagged();

    public String getFlagMsg(int ticketid);
}
