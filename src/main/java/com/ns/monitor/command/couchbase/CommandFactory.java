package com.ns.monitor.command.couchbase;

import com.ns.monitor.command.couchbase.show.ShowCommandFactory;

import static java.lang.System.out;

public class CommandFactory {
    public static void help() {
        out.println("");
        out.println("Couchbase command:");
        out.println("");
        out.println("open <host>");
        out.println("close");
        out.println("use <bucketName>");
        out.println("set <key> <value>");
        out.println("get <key>");
        out.println("del <key>");
        out.println("query <design> <view>");
        out.println("login <username>");
        out.println("");
        out.println("N1QL:");
        out.println(" SELECT * FROM `beer-sample` LIMIT 5");

        ShowCommandFactory.help();
    }

    public static Command doCommand(String cmd) {
        switch (cmd) {
            case "open":
                return new Open();
            case "close":
                return new Close();
            case "use":
                return new Use();
            case "set":
                return new Set();
            case "get":
                return new Get();
            case "del":
                return new Delete();
            case "query":
                return new Query();
            case "explain":
            case "select":
            case "build":
            case "create":
            case "drop":
            case "insert":
            case "update":
            case "upsert":
            case "delete":
            case "merge":
            case "prepare":
                Sql sql = new Sql();
                sql.setCommand(cmd);
                return sql;
            case "login":
                return new Login();
            case "show":
                return new Show();
            default:
                throw new IllegalArgumentException(cmd + ": command not found");
        }
    }
}
