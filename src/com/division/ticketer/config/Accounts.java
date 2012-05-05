/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.division.ticketer.config;

import com.division.ticketer.core.Rank;
import com.division.ticketer.core.Ticketer;
import com.division.ticketer.crypto.SHA1;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Evan
 */
public class Accounts {

    private static YamlConfiguration acc = new YamlConfiguration();
    File aconfig;
    Ticketer TI;

    public Accounts(Ticketer instance) {
        aconfig = new File(instance.getDataFolder() + "/accounts.yml");
        this.TI = instance;
    }

    public void load() throws IOException {
        acc = YamlConfiguration.loadConfiguration(aconfig);
        if (!aconfig.exists()) {
            SHA1 sha1 = new SHA1();
            System.out.println("Creating Accounts database...");
            aconfig.createNewFile();
            acc.set("accounts", null);
            acc.set("accounts.admin.password", sha1.getHash(sha1.getHash("admin" + "admin")));
            acc.set("accounts.admin.rank", "superuser");
            acc.save(aconfig);
        }
    }

    public boolean VerifiedUser(String username, String password) {
        SHA1 sha1 = new SHA1();
        if (acc.contains("accounts." + username)) {
            if (acc.contains("accounts." + username + ".password")) {
                String sha1pass = sha1.getHash(password);
                if (sha1pass.equals(acc.getString("accounts." + username + ".password"))) {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return false;
    }

    public void createUser(String username, String password, String rank) {
        SHA1 sha1 = new SHA1();
        if (!acc.contains("accounts." + username)) {
            acc.set("accounts." + username + ".password", sha1.getHash(password + username));
            acc.set("accounts." + username + ".rank", rank);
        }
    }

    public Rank getRank(String username) {
        for (Rank rank : Rank.values()) {
            if (rank.getRank().equals(acc.getString("accounts." + username + ".rank"))) {
                return rank;
            }
        }
        return null;
    }

    public String getAccounts() {
        Set<String> accountList;
        accountList = acc.getConfigurationSection("accounts").getKeys(false);
        String output = "";
        for (String s : accountList) {
            output += s;
            output += "%";
        }
        return output;
    }
}
