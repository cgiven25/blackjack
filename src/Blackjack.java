public class Blackjack {

    private Dealer dealer;
    public Player[] players;

    public Blackjack() { this(1); }

    public Blackjack(int numPlayers) {
        this.dealer = new Dealer();

        this.players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            this.players[i] = new Player();
        }
    }

    public void displayDealerHand() {

    }

    public void displayPlayerHand(int playerIndex) {

    }

    public void displayAllPlayerHands() {

    }

    public void play() {
        Player player = this.players[0];
        dealer.deal(dealer);
        dealer.displayHand();
        
    }

    public static void main(String[] args) {

        Blackjack game = new Blackjack();
        game.play();

    }
}

