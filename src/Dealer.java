// In Blackjack, the dealer is also a player.
public class Dealer extends Player {
    
    private Deck deck;

    public Dealer() {
        super("The dealer");
        deck = new Deck();
    }

    public void deal(Player recipient) { 
        deal(recipient, false); 
    }

    public void deal(Player recipient, boolean faceDown) {
        Card dealtCard = deck.takeFromTop(faceDown);
        recipient.addCardToHand(dealtCard);
        if (deck.getNumRemainingCards() == 0)
            deck.shuffle();
    }

    /* Casino blackjack rules dictate that if a 10 or an Ace is showing,
    *  the dealer should peek at the bottom card (in casinos, done with a machine)
    *  that tells them whether or not their face-down card gives them Blackjack.
    *  if so, all bets are collected from the plyaers who do not also have Blackjack. 
    */
    public boolean peekForBlackjack() {
        Hand hand = getHand();
        if (hand.getNumCards() == 2) {
            return hand.peekHidden() + hand.getCount() == 21 ? true: false;
        }
        return false;
    }

    public boolean shownCardIsTenOrAce() {
        Hand hand = getHand();
        if (hand.getNumCards() == 2) {
            for (Card c : hand.getCards()) {
                if (!c.isHidden())
                    return c.isAce() || c.getPointValue() == 10;
            }
        }
        return false;
    }

}
