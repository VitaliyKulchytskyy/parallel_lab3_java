import org.apache.commons.cli.CommandLine;

public class Main {
    public static void main(String[] args) {
        CliParser cli = new CliParser();

        try {
            cli.parseArgs(args);
            Callback ConsumerCallback = (String msg) -> System.out.println("[Consumer]: consume " + msg);
            Callback ProducerCallback = (String msg) -> System.out.println("[Producer]: produce " + msg);
            new ParallelQueries(cli.Items, cli.StorageSize)
                    .OnConsumerCallback(ConsumerCallback)
                    .OnProducerCallback(ProducerCallback);
        } catch (org.apache.commons.cli.ParseException e) {
            System.out.println(e.getMessage());
            cli.printHelp();
            System.exit(1);
        }
    }
}