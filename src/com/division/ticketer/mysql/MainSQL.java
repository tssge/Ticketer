/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.division.ticketer.mysql;

import com.division.ticketer.config.TicketerConfig;
import com.division.ticketer.core.Rank;
import com.division.ticketer.core.Ticket;
import com.division.ticketer.core.Ticketer;
import java.sql.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author Evan
 */
public abstract class MainSQL implements DataSource {
    
    protected Connection conn;
    Ticketer TI;
    
    protected abstract void getConnection() throws ClassNotFoundException,
            SQLException;
    
    protected abstract void setup() throws SQLException;
    
    public MainSQL(Ticketer instance) {
        this.TI = instance;
    }

    //TicketPanel Requests
    @Override
    public String getOpenTickets() {
        Statement st = null;
        ResultSet rs = null;
        String output = "";
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT id FROM tickets WHERE NOT status='CLOSED' AND GMAssign IS NULL");
            if (rs != null) {
                while (rs.next()) {
                    if (rs.getInt("id") != -1) {
                        output += rs.getInt("id") + "%";
                    }
                }
                return output;
            }
        } catch (Exception ex) {
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }
        }
        return null;
    }
    
    @Override
    public String getAssignments(String username) {
        Statement st = null;
        ResultSet rs = null;
        String output = "";
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT id FROM tickets WHERE NOT status='CLOSED' AND GMAssign='" + username + "'");
            if (rs != null) {
                while (rs.next()) {
                    if (rs.getInt("id") != -1) {
                        output += rs.getInt("id") + "%";
                    }
                }
                return output;
            } else {
            }
        } catch (SQLException ex) {
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }
        }
        return null;
    }
    
    @Override
    public String getPlayer(int ticketid) {
        Statement st = null;
        ResultSet rs = null;
        String output = "";
        
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT player FROM tickets WHERE id=" + ticketid);
            if (rs != null) {
                while (rs.next()) {
                    if (!rs.getString("player").equals("")) {
                        output += rs.getString("player");
                    }
                }
                return output;
            }
        } catch (Exception ex) {
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }
        }
        return null;
    }
    
    @Override
    public String getMessage(int ticketid) {
        Statement st = null;
        ResultSet rs = null;
        String output = "";
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT message FROM tickets WHERE id=" + ticketid);
            if (rs != null) {
                while (rs.next()) {
                    if (!rs.getString("message").equals("")) {
                        output += rs.getString("message");
                    }
                }
                return output;
            }
        } catch (Exception ex) {
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }
        }
        return null;
    }
    
    @Override
    public String getStatus(int ticketid) {
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT status FROM tickets WHERE id=" + ticketid);
            while (rs.next()) {
                if (!rs.getString("status").equals("")) {
                    return rs.getString("status");
                }
            }
        } catch (Exception ex) {
        }
        return null;
    }
    
    @Override
    public String getFlagMsg(int ticketid) {
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT flag_msg FROM tickets WHERE id=" + ticketid);
            while (rs.next()) {
                if (!rs.getString("flag_msg").equals("")) {
                    System.out.print(rs.getString("flag_msg"));
                    return rs.getString("flag_msg");
                }
            }
        } catch (Exception ex) {
        }
        return null;
    }
    
    @Override
    public boolean hasOpenTicket(Player p) {
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM tickets WHERE player='" + p.getName() + "' AND NOT status='CLOSED'");
            while (rs.next()) {
                return true;
            }
        } catch (Exception ex) {
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (Exception ex) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                }
            }
        }
        return false;
    }
    
    @Override
    public void setState(int ticketid, String state) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("UPDATE tickets SET status=? WHERE id=?");
            pst.setString(1, state);
            pst.setInt(2, ticketid);
            pst.executeUpdate();
        } catch (SQLException ex) {
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException ex) {
                }
            }
        }
    }
    
    @Override
    public void setGMAssign(int ticketid, String gm) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("UPDATE tickets SET GMAssign=? WHERE id=?");
            pst.setString(1, gm);
            pst.setInt(2, ticketid);
            pst.executeUpdate();
        } catch (SQLException ex) {
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException ex) {
                }
            }
        }
    }
    
    @Override
    public void setFlag(int ticketid, String msg, int flag) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("UPDATE tickets SET flag=?,flag_msg=? WHERE id=?");
            pst.setInt(1, flag);
            pst.setString(2, msg);
            pst.setInt(3, ticketid);
            pst.executeUpdate();
        } catch (SQLException ex) {
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException ex) {
                }
            }
        }
    }
    
    @Override
    public boolean isFlagged(int ticketid) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT flag FROM tickets WHERE id=?");
            pst.setInt(1, ticketid);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getInt("flag") == 0) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (SQLException ex) {
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException ex) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }
        }
        return false;
    }
    
    @Override
    public String getFlagged() {
        Statement st = null;
        ResultSet rs = null;
        String output = "";
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT id FROM tickets WHERE NOT status='CLOSED' AND NOT flag=0");
            if (rs != null) {
                while (rs.next()) {
                    if (rs.getInt("id") != -1) {
                        output += rs.getInt("id") + "%";
                    }
                }
                return output;
            }
        } catch (Exception ex) {
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }
        }
        return null;
    }
    
    @Override
    public void createTicket(Ticket ticket) {
        String rankformat = TicketerConfig.getRankFormat(Rank.SYSTEM);
        ChatColor chatcolor = TicketerConfig.getChatColor();
        if (!hasOpenTicket(ticket.getPlayer())) {
            PreparedStatement pst = null;
            try {
                pst = conn.prepareStatement("INSERT INTO tickets (player,subject,message) VALUES(?,?,?)");
                String subject = ticket.getSubject();
                String message = ticket.getMessage();
                pst.setString(1, ticket.getPlayer().getName());
                pst.setString(2, subject);
                pst.setString(3, message);
                pst.executeUpdate();
                ticket.getPlayer().sendMessage(rankformat + ": " + chatcolor + "Your ticket has been submitted successfully.");
                TI.removeTicket(ticket);
            } catch (Exception ex) {
            } finally {
                if (pst != null) {
                    try {
                        pst.close();
                    } catch (Exception ex) {
                    }
                }
            }
        } else {
            ticket.getPlayer().sendMessage(rankformat + ": " + chatcolor + "You already have an open ticket.");
        }
    }
    
    @Override
    public int getTicketId(Player p) {
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT id FROM tickets WHERE player='" + p.getName() + "' AND NOT status='CLOSED'");
            while (rs.next()) {
                if (rs.getInt("id") != 0) {
                    return rs.getInt("id");
                }
            }
        } catch (Exception ex) {
            return 0;
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (Exception ex) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                }
            }
        }
        return 0;
    }
    
    @Override
    public String getSubject(int ticketid) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT subject FROM tickets WHERE id=?");
            pst.setInt(1, ticketid);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (!rs.getString("subject").equals("")) {
                    return rs.getString("subject");
                }
            }
            return null;
        } catch (Exception ex) {
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception ex) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                }
            }
        }
        return null;
    }
}
