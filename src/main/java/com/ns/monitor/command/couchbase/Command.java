package com.ns.monitor.command.couchbase;

import com.ns.connector.CouchbaseClient;

import jline.console.ConsoleReader;

public interface Command {
    public Command setClient(CouchbaseClient client);
    public Command setConsole(ConsoleReader console);
    public void execute(String[] args) throws Exception;
}
