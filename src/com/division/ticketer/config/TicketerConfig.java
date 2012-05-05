/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.division.ticketer.config;

import com.division.ticketer.core.Ticketer;
import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Evan
 */
public class TicketerConfig {

    YamlConfiguration tcfg = new YamlConfiguration();
    File tconfig;
    Ticketer TI;
    private static String host;
    private static int port;
    private static String mysqlhost;
    private static String mysqlport;
    private static String mysqluser;
    private static String mysqlpass;
    private static String mysqldata;

    public TicketerConfig(Ticketer instance) {
        tconfig = new File(instance.getDataFolder() + "/config.yml");
        this.TI = instance;
    }

    public void load() {
        System.out.println("[Ticketer] Loading config...");
        try {
            tcfg.load(tconfig);
        } catch (Exception ex) {
            System.out.println("[Ticketer] Generating config...");
        }
        if (!tcfg.contains("server.host")) {
            tcfg.set("server.host", "0.0.0.0");
        } else {
            host = tcfg.getString("server.host");
        }
        if (!tcfg.contains("server.port")) {
            tcfg.set("server.port", 9184);
        } else {
            port = tcfg.getInt("server.port");
        }
        if (!tcfg.contains("mysql.host")) {
            tcfg.set("mysql.host", "localhost");
        } else {
            mysqlhost = tcfg.getString("mysql.host");
        }
        if (!tcfg.contains("mysql.port")) {
            tcfg.set("mysql.port", "3306");
        } else {
            mysqlport = tcfg.getString("mysql.port");
        }
        if (!tcfg.contains("mysql.username")) {
            tcfg.set("mysql.username", "root");
        } else {
            mysqluser = tcfg.getString("mysql.username");
        }
        if (!tcfg.contains("mysql.password")) {
            tcfg.set("mysql.password", "password");
        } else {
            mysqlpass = tcfg.getString("mysql.password");
        }
        if (!tcfg.contains("mysql.database")) {
            tcfg.set("mysql.database", "ticketer");
        } else {
            mysqldata = tcfg.getString("mysql.database");
        }
        if (!tconfig.exists()) {
            try {
                tcfg.save(tconfig);
                load();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("[Ticketer] Done loading config!");
        }
    }

    public String getMySQLDatabase() {
        return mysqldata;
    }

    public String getMySQLPassword() {
        return mysqlpass;
    }

    public String getMySQLUsername() {
        return mysqluser;
    }

    public String getMySQLHost() {
        return mysqlhost;
    }

    public String getMySQLPort() {
        return mysqlport;
    }

    public String getServerHost() {
        return host;
    }

    public int getServerPort() {
        return port;
    }
}
