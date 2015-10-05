package com.ns.monitor.command.couchbase;

public class Set extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        if (args.length < 1) {
            throw new IllegalArgumentException("invalid arguments");
        }

        String[] arguments = new String[0];
        if (args.length > 2) {
            arguments = new String[args.length - 1];
            System.arraycopy(args, 1, arguments, 0, arguments.length);
        }

        this.client.set(args[0], String.join(" ", arguments));
    }
}
