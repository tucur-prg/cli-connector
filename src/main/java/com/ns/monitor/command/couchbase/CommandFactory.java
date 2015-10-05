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
        out.println("delete <key>");
        out.println("query <design> <view>");
        out.println("login <username>");

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
            case "delete":
                return new Delete();
            case "query":
                return new Query();
            case "login":
                return new Login();
            case "show":
                return new Show();
            default:
                throw new IllegalArgumentException(cmd + ": command not found");
        }
    }
}
