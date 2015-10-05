package com.ns.monitor.command.couchbase.show;

import com.ns.monitor.command.couchbase.Connector;
import com.ns.monitor.command.couchbase.Command;

public class Bucket extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        this.client.bucket();
    }
}
