package com.ns.monitor.command.couchbase;

import com.ns.connector.CouchbaseClient;

import jline.console.ConsoleReader;

public abstract class Connector implements Command {
    protected CouchbaseClient client;
    protected ConsoleReader console;

    public Command setClient(CouchbaseClient client) {
        this.client = client;
        return this;
    }

    public Command setConsole(ConsoleReader console) {
        this.console = console;
        return this;
    }
}
