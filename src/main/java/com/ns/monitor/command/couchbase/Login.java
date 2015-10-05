package com.ns.monitor.command.couchbase;

public class Login extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        if (args.length < 1) {
            throw new IllegalArgumentException("invalid arguments");
        }

        String password = console.readLine("Password: ", new Character('*'));
        this.client.login(args[0], password);
    }
}
