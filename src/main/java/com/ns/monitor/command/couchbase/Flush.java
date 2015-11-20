package com.ns.monitor.command.couchbase;

public class Flush extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        this.client.flush();
    }
}
