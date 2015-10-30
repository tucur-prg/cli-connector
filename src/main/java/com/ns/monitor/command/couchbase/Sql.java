package com.ns.monitor.command.couchbase;

public class Sql extends Connector implements Command {
    private String command;

    public void setCommand(String command) {
      this.command = command;
    }

    public void execute(String[] args) throws Exception {
        if (args.length < 1) {
            throw new IllegalArgumentException("invalid arguments");
        }

        this.client.sql(this.command + " " + String.join(" ", args));
    }
}
