/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.division.ticketer.core;

import com.division.ticketer.config.Accounts;
import com.division.ticketer.config.TicketerConfig;
import com.division.ticketer.mysql.DataSource;
import com.division.ticketer.mysql.MySQLc;
import com.division.ticketer.net.Net_Framework;
import java.io.IOException;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Evan
 */
public class Ticketer extends JavaPlugin {

    private static Ticketer instance;
    private static TicketerConfig tcfg;
    private static Accounts acc;
    private static DataSource database;

    @Override
    public void onEnable() {
        Ticketer.instance = this;
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        tcfg = new TicketerConfig(this);
        tcfg.load();
        acc = new Accounts(this);
        try {
            acc.load();
        } catch (IOException ex) {
            System.out.println("[Ticketer] failed to load files...");
            this.getServer().getPluginManager().disablePlugin(this);
        }

        Net_Framework netFrame = new Net_Framework(this);
        netFrame.setDaemon(true);
        netFrame.setName("ServerListener");
        netFrame.start();
        try {
            database = new MySQLc(this);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Can't load database");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    @Override
    public void onDisable() {
    }

    public static TicketerConfig getTConfig() {
        return tcfg;
    }

    public static Accounts getAccounts() {
        return acc;
    }

    public static DataSource getDatasource() {
        return database;
    }
}
