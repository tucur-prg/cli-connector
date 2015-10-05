package com.ns.monitor.command.couchbase;

public class Get extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        if (args.length < 1) {
            throw new IllegalArgumentException("invalid arguments");
        }

        this.client.get(args[0]);
    }
}
