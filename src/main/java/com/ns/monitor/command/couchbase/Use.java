package com.ns.monitor.command.couchbase;

public class Use extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        if (args.length < 1) {
            throw new IllegalArgumentException("invalid arguments");
        }

        String password = console.readLine("Bucket Password: ", new Character('*'));
        this.client.openBucket(args[0], password);
    }
}
