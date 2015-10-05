package com.ns.monitor.command.couchbase;

public class Query extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        if (args.length < 2) {
            throw new IllegalArgumentException("invalid arguments");
        }

        this.client.query(args[0], args[1]);
    }
}
