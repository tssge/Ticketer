/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.division.ticketer.mysql;

import com.division.ticketer.core.Ticketer;
import java.sql.*;

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
                        output += rs.getString("player") + "%";
                        if (TI.getServer().getPlayer(rs.getString("player")) != null) {
                            output += "true";
                        } else {
                            output += "false";
                        }
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
        Statement st = null;
        ResultSet rs = null;
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
            pst = conn.prepareStatement("UPDATE tickets set GMAssign=? WHERE id=?");
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
}
