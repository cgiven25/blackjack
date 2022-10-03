import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class Deck {

    private static final String[] STANDARD_RANKS = {
        "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"
    };

    private static final String[] STANDARD_SUITS = {
        "Hearts", "Spades", "Clubs", "Diamonds"
    };

    private List<Card> cards;
    private int numCardsRemaining;

    public Deck() { this(1); }

    // Default to standard deck
    public Deck(int numDecks) {
        this.cards = new ArrayList<Card>();

        for (int i = 0; i < numDecks; i++) {
            for (String rank : STANDARD_RANKS) {
                for (String suit : STANDARD_SUITS) {
                    this.cards.add(new Card(rank, suit));
                }
            }
        }

        this.shuffle();

        this.numCardsRemaining = cards.size() - 1;
    }

    public Card takeFromTop(boolean faceDown) {
        Card takenCard = cards.get(numCardsRemaining--);
        if (faceDown)
            takenCard.flip();
        return takenCard;
    }

    public void shuffle() {
        Collections.shuffle(cards);
        numCardsRemaining = cards.size();
    }

    public int getNumRemainingCards() {
        return numCardsRemaining;
    }
}
