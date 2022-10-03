import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.stream.Collectors;
public class Blackjack {

    private Dealer dealer;
    private List<Player> players;

    private static final int MIN_BET = 5;
    public static final int MAX_PLAYERS = 7;
    private static final int SHORT_DELAY_MS = 1500;
    private static final int STANDARD_DELAY_MS = 2000;
    private static final int LONG_DELAY_MS = 2500;

    public Blackjack() { 
        this(1); 
    }

    public Blackjack(int numPlayers) {
        this.dealer = new Dealer();
        this.players = new ArrayList<Player>();
        for (int i = 0; i < numPlayers; i++)
            this.players.add(new Player(String.format("Player %d", i + 1)));
    }

    private void inputPlayerNames(Scanner reader) {
        for (int i = 0; i < players.size(); i++) {
            System.out.format("Player %d, please enter your name: ", i + 1);
            players.get(i).setName(reader.nextLine());
        }
    }

    private boolean playerHit (Scanner reader, Player player) {
        System.out.print(String.format("%s, would you like to 'hit' or 'stay'? ", player.getName()));

        do {
            String ans = reader.nextLine().toLowerCase();
            if (ans.equals("h") || ans.equals("hit"))
                return true;
            else if (ans.equals("s") || ans.equals("stay"))
                return false;
            else
                System.out.println("Please enter a valid string: 'hit' or 'stay'");
        } while (true);
    }

    private void takeBet(Player player, Scanner reader) {
        String errorStr = String.format("Please provide a valid bet (between $%d and $%d).", MIN_BET, player.getMoney());
        String betPrompt = String.format("How much would you like to bet (min. bet is $%d)? $", MIN_BET);
        
        System.out.format("%s, you have $%d.%n", player.getName(), player.getMoney());
        
        do {
            try {
                System.out.print(betPrompt);
                int amt = reader.nextInt();
                if (amt >= 5 && amt <= player.getMoney()) {
                    player.setBetAmount(amt);
                    System.out.println();
                    reader.nextLine();
                    return;
                } else {
                    System.out.println(errorStr);
                    continue;
                }
            }
            catch (InputMismatchException e) {
                System.out.println(errorStr);
                reader.nextLine();
                continue;
            }
        } while (true);
    }

    private boolean decideToPlayAgain(Scanner reader, Player player) {
        System.out.format("%s, would you like to keep playing? ('yes' or 'no')? ", player.getName());

        do {
            String ans = reader.nextLine().toLowerCase();
            if (ans.equals("y") || ans.equals("yes"))
                return true;
            else if (ans.equals("n") || ans.equals("no"))
                return false;
            else
                System.out.println("Please enter a valid string ('yes' or 'no'): ");
        } while(true);
    }

    private void playHand(Player player, Scanner reader) throws InterruptedException {
        player.displayHand();
        if (player.getHand().isBlackjack()) {
            System.out.format("%s has blackjack!%n%n", player.getName());
            Thread.sleep(STANDARD_DELAY_MS);
        }
        else {
            dealer.displayHand();
            Thread.sleep(STANDARD_DELAY_MS);
            do {
                boolean hit = playerHit(reader, player);
                if (hit) {
                    System.out.println("You chose 'hit'.");
                    System.out.format(String.format("Dealing another card to %s.%n%n", player.getName()));
                    dealer.deal(player);
                    Thread.sleep(STANDARD_DELAY_MS);
                    player.displayHand();
                    Thread.sleep(STANDARD_DELAY_MS);
                } else {
                    System.out.println("You chose 'stay'.\n");
                    Thread.sleep(STANDARD_DELAY_MS);
                    break;
                }
            } while (player.getCount() < 21);
        }
    }

    private void resolveAllBets() throws InterruptedException {
        int dealerCount = dealer.getCount();
        for (Player p : players) {
            Thread.sleep(LONG_DELAY_MS);
            int playerCount = p.getCount();
            p.displayHand();
            if (p.getHand().isBlackjack()) {
                System.out.format("%s has blackjack! (+$%d)%n", p.getName(), (int) Math.floor(p.getBetAmount()*1.5));
                p.resolveBet(Result.BLACKJACK);
            } else if (p.getCount() > 21) {
                System.out.format("%s busts. (-$%d)%n", p.getName(), p.getBetAmount());
                p.resolveBet(Result.LOSE);
            } else {
                if (dealerCount > 21) {
                    System.out.format("The house busts. %s wins. (+$%d)%n", p.getName(), p.getBetAmount());
                    p.resolveBet(Result.WIN);
                } else if (playerCount > dealerCount) {
                    System.out.format("%s beats the house. (+$%d)%n", p.getName(), p.getBetAmount());
                    p.resolveBet(Result.WIN);
                } else if (dealerCount > playerCount) {
                    System.out.format("The house beats %s. (-$%d)%n", p.getName(), p.getBetAmount());
                    p.resolveBet(Result.LOSE);
                } else {
                    System.out.format("%s pushes. (-$0)%n", p.getName());
                    p.resolveBet(Result.PUSH);
                }
            }
            System.out.format("Total money: $%d%n%n", p.getMoney());
            System.out.println("----------------------------------------\n");
            Thread.sleep(LONG_DELAY_MS);
        }
    }

    /* Looks at each player and determines if they have enough money
     *   to place the minimum bet. If not, removes them from the list of players.
     */
    private void filterForBankruptcy() {
        players = players.stream()
                .filter(p -> {
                    boolean bankrupt = p.getMoney() < MIN_BET;
                    if (bankrupt) {
                        String bankruptString = String.format(("%s cannot meet the minimum bet requirement ($%d)," + 
                            " so they are bankrupt and removed from the game."), p.getName(), MIN_BET);
                        System.out.println(bankruptString);
                    }
                    return !bankrupt;
                })
        .collect(Collectors.toList());
    }

    /* Asks each player if they want to continue playing.
     *   If no, removes them from the list of players.
     */
    private void filterForExitingPlayers(Scanner reader) {
        players = players.stream()
            .filter(p -> {
                boolean yes = decideToPlayAgain(reader, p);
                return yes;
            })
        .collect(Collectors.toList());
    }

    public void play() throws InterruptedException {
        Scanner reader = new Scanner(System.in);

        System.out.println("Welcome to Blackjack!\n");
        Thread.sleep(STANDARD_DELAY_MS);
        
        inputPlayerNames(reader);
        System.out.println();

        do {
            filterForBankruptcy();

            if (players.size() == 0) {
                System.out.println("All players are bankrupt.");
                break;
            }

            for (Player p : players) {
                takeBet(p, reader);
            }

            System.out.println("Dealing two cards to each player and the dealer.");
            Thread.sleep(STANDARD_DELAY_MS);

            for (Player p: players) {
                dealer.deal(p);
            }
            dealer.deal(dealer);
            for (Player p: players) {
                dealer.deal(p);
            }
            dealer.deal(dealer, true);

            if (dealer.shownCardIsTenOrAce()) {
                dealer.displayHand();
                Thread.sleep(STANDARD_DELAY_MS);
                System.out.println("The dealer is showing a face card or an ace, and must peek for Blackjack.");
                Thread.sleep(STANDARD_DELAY_MS);
                if (dealer.peekForBlackjack()) {
                    System.out.println("The dealer has Blackjack. Collecting bets from all players that do not also have blackjack.");
                    for (Player p : players) {
                        Thread.sleep(LONG_DELAY_MS);
                        if (p.getHand().isBlackjack()) {
                            p.resolveBet(Result.PUSH);
                        } else {
                            p.resolveBet(Result.LOSE);
                        }
                    }
                } else {
                    System.out.println("The dealer does not have blackjack, and play will continue as normal.");
                    Thread.sleep(2000);
                }
            }

            System.out.println("\n----------------------------------------\n");

            // Let every player play their hand.
            for (Player p : players) {
                playHand(p, reader);
                System.out.println("----------------------------------------\n");
            }

            // Bring dealer up to 17
            System.out.println("Revealing the dealer's hidden card.");
            dealer.getHand().revealAll();
            Thread.sleep(STANDARD_DELAY_MS);
            dealer.displayHand();
            
            if (dealer.getCount() < 17) {
                Thread.sleep(STANDARD_DELAY_MS);
                System.out.println("The dealer must hit to 17.");
            }

            while (dealer.getCount() < 17) {
                System.out.println("Dealing another card to the dealer.");
                Thread.sleep(SHORT_DELAY_MS);
                dealer.deal(dealer);
                dealer.displayHand();
                Thread.sleep(SHORT_DELAY_MS);
            }

            System.out.println("----------------------------------------\n");
            Thread.sleep(STANDARD_DELAY_MS);

            dealer.displayHand();

            System.out.println("----------------------------------------\n");

            resolveAllBets();

            for (Player p : players)
                p.getHand().discardAll();

            dealer.getHand().discardAll();

            filterForBankruptcy();

            filterForExitingPlayers(reader);
            System.out.println();

            if (players.size() == 0) {
                System.out.println("All players are bankrupt or have left the table. Thanks for playing!");
            }

        } while(players.size() != 0);

        reader.close();
    }
}

