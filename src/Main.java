public class Main {

    private static String usageStr = "usage: java -jar blackjack.jar num_players";
    public static void main(String[] args) {
        Blackjack game;

        try {
            if (args.length == 0) {
                game = new Blackjack();
                game.play();
            } else if (args[0].equals("--help") || args[0].equals("-h") || args.length > 1) {
                System.out.println(usageStr);
            } else {
                try {
                    int numPlayers = Integer.parseInt(args[0]);
                    if (numPlayers > Blackjack.MAX_PLAYERS) {
                        System.out.println(usageStr);
                    } else {
                        game = new Blackjack(numPlayers);
                        game.play();
                    }
                }
                catch (NumberFormatException e) {
                    System.out.println(usageStr);
                }
            }
        } catch (InterruptedException e) {
            System.exit(0);
        }
    }
}