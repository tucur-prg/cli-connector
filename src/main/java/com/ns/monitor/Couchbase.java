package com.ns.monitor;

import java.lang.IllegalArgumentException;

import java.io.IOException;

import com.ns.connector.CouchbaseClient;

import static java.lang.System.out;

public class Couchbase extends Monitor {
    private CouchbaseClient conn = new CouchbaseClient();

    private String targetBucket;

    public String read() throws IOException {
        if (targetBucket != null) {
            return c.readLine(String.format("couchbase:%s>", targetBucket));
        }

        return c.readLine("couchbase> ");
    }

    public void shutdown() throws Exception {
        conn.close();
    }

    @Override
    public void help() {
        super.help();

        out.println("");
        out.println("Couchbase command:");
        out.println("");
        out.println("open <host> <username>");
        out.println("use <bucketName>");
        out.println("set <key> <value>");
        out.println("get <key>");
        out.println("delete <key>");
        out.println("query <design> <view>");
        out.println("show buckets");
        out.println("show info");
    }

    protected void doCommand(String cmd, String... args) throws Exception {
        String subCommand;
        String[] arguments;

        switch (cmd) {
            case "open":
                if (args.length < 1) {
                    throw new IllegalArgumentException("invalid arguments");
                }

                if (args.length == 2) {
                    char[] password = c.readPassword("Password: ");

                    conn.settings(args[1], String.valueOf(password));
                }

                conn.open(args[0]);
                break;
            case "use":
                if (args.length < 1) {
                    throw new IllegalArgumentException("invalid arguments");
                }

                char[] password = c.readPassword("Bucket Password: ");

                conn.openBucket(args[0], String.valueOf(password));
                targetBucket = args[0];
                break;
            case "set":
                if (args.length < 2) {
                    throw new IllegalArgumentException("invalid arguments");
                }

                arguments = new String[0];
                if (args.length > 2) {
                    arguments = new String[args.length - 1];
                    System.arraycopy(args, 1, arguments, 0, arguments.length);
                }

                conn.set(args[0], String.join(" ", arguments));
                break;
            case "get":
                if (args.length < 1) {
                    throw new IllegalArgumentException("invalid arguments");
                }

                conn.get(args[0]);
                break;
            case "delete":
                if (args.length < 1) {
                    throw new IllegalArgumentException("invalid arguments");
                }

                conn.delete(args[0]);
                break;
            case "query":
                if (args.length < 2) {
                    throw new IllegalArgumentException("invalid arguments");
                }

                conn.query(args[0], args[1]);
                break;
            case "show":
                subCommand = args[0].toLowerCase();
                arguments = new String[0];
                if (args.length > 1) {
                    arguments = new String[args.length - 1];
                    System.arraycopy(args, 1, arguments, 0, arguments.length);
                }

                doShowCommand(subCommand, arguments);
                break;
            default:
                throw new IllegalArgumentException(cmd + ": command not found");
        }
    }

    protected void doShowCommand(String cmd, String... args) throws Exception {
        switch (cmd) {
            case "buckets":
                conn.bucketList();
                break;
            case "info":
                conn.info();
                break;
            default:
                throw new IllegalArgumentException("show " + cmd + ": command not found");
        }
    }
}
