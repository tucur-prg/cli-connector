package com.ns.monitor.command.couchbase.show;

import com.ns.monitor.command.couchbase.Command;

import static java.lang.System.out;

public class ShowCommandFactory {
    public static void help() {
        out.println("");
        out.println("show bucket");
        out.println("show buckets");
        out.println("show info");
    }

    public static Command doCommand(String cmd) {
        switch (cmd) {
            case "bucket":
                return new Bucket();
            case "buckets":
                return new Buckets();
            case "info":
                return new Info();
            default:
                throw new IllegalArgumentException("show " + cmd + ": command not found");
        }
    }
}
