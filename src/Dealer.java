// In Blackjack, the dealer is also a player.
public class Dealer extends Player {
    
    private Deck deck;

    public Dealer() {
        deck = new Deck();
    }

    public void deal(Player recipient) {
        Card dealtCard = this.deck.takeFromTop();
        recipient.addCardToHand(dealtCard);
    }

}
