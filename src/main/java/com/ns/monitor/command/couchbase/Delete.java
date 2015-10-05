package com.ns.monitor.command.couchbase;

public class Delete extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        if (args.length < 1) {
            throw new IllegalArgumentException("invalid arguments");
        }

        this.client.delete(args[0]);
    }
}
