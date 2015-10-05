package com.ns.monitor.command.couchbase;

import com.ns.monitor.command.couchbase.show.ShowCommandFactory;

public class Show extends Connector implements Command {
    public void execute(String[] args) throws Exception {
        if (args.length < 1) {
            throw new IllegalArgumentException("invalid arguments");
        }

        String subCommand = args[0].toLowerCase();
        String[] arguments = new String[0];
        if (args.length > 1) {
            arguments = new String[args.length - 1];
            System.arraycopy(args, 1, arguments, 0, arguments.length);
        }

        Command command =  ShowCommandFactory.doCommand(subCommand);
        command.setClient(client).setConsole(console).execute(args);
    }
}
