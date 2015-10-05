package com.ns.monitor.command.couchbase;

public class Close extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        this.client.close();
    }
}
