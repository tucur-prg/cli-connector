package com.ns.monitor;

import java.io.IOException;

import jline.console.ConsoleReader;

import com.ns.connector.CouchbaseClient;

import com.ns.monitor.command.couchbase.*;

public class Couchbase extends Monitor {
    private CouchbaseClient client = new CouchbaseClient();

    Couchbase(ConsoleReader input) {
        super(input);
    }

    public String read() throws IOException {
        String bucketName = client.bucketName();
        if (bucketName != null) {
            return c.readLine(String.format("couchbase:%s> ", bucketName));
        }

        return c.readLine("couchbase> ");
    }

    public void shutdown() throws Exception {
        client.close();
    }

    @Override
    public void help() {
        super.help();
        CommandFactory.help();
    }

    protected void doCommand(String cmd, String... args) throws Exception {
        Command command = CommandFactory.doCommand(cmd);
        command.setClient(client).setConsole(c).execute(args);
    }
}
