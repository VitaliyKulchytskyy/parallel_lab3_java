import org.apache.commons.cli.*;

public class CliParser {
    private final Options options = new Options();
    private final CommandLineParser parser = new GnuParser();
    private final HelpFormatter formatter = new HelpFormatter();
    private boolean IsVerbose;
    public int Items = 1;
    public int StorageSize = 1;

    public CliParser() {
        options.addOption(new Option("h", "help", false, "Display this help text"));
        options.addOption(new Option("v", "verbose", false, "Print verbose information"));
        options.addOption(new Option("i", "items", true, "Set the number of items"));
        options.addOption(new Option("s", "storage", true, "Set the storage size"));
    }

    public void parseArgs(String[] args) throws ParseException {
        CommandLine cmd = this.parser.parse(this.options, args);

        if (cmd.hasOption("h")) {
            printHelp();
            System.exit(0);
        }

        Items = cmd.hasOption("i") ? Integer.parseInt(cmd.getOptionValue("i")) : 1;
        StorageSize= cmd.hasOption("s") ? Integer.parseInt(cmd.getOptionValue("s")) : 1;
        IsVerbose = cmd.hasOption("v");

        if (IsVerbose()) {
            System.out.println("Items:        " + Items);
            System.out.println("Storage size: " + StorageSize);
            System.out.println();
        }
    }

    public boolean IsVerbose() {
        return IsVerbose;
    }

    public void printHelp() {
        this.formatter.printHelp("Lab3", this.options);
    }
}
