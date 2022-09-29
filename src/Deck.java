import java.util.*;
public class Deck {

    private static final String[] STANDARD_RANKS = {
        "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"
    };

    private static final String[] STANDARD_SUITS = {
        "Hearts", "Spades", "Clubs", "Diamonds"
    };

    private List<Card> cards;
    private int top;

    public Deck() { this(1); }

    // Default to standard deck
    public Deck(int numRepeats) {
        this.cards = new ArrayList<Card>();

        for (String rank : STANDARD_RANKS) {
            for (String suit : STANDARD_SUITS) {
                this.cards.add(new Card(rank, suit));
            }
        }

        // Add remaining cards, copying from the cards already added
        int numCards = this.cards.size();
        for(int i = 0; i < numRepeats - 1; i++) {
            for (int j = 0; j < numCards; j++) {
                Card currCard = this.cards.get(j);
                cards.add(new Card(currCard.getRank(), currCard.getSuit()));
            }
        }

        this.top = cards.size() - 1;

        this.shuffle();
    }

    // sneaky way to "remove a card" without actually removing
    // When top == 0, I can shuffle without reinstantiating the entire deck
    public Card takeFromTop() {
        return this.cards.get(top--);
    }

    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    public void sortInOrder() {

    }

    // using for testing, delete later
    public String toString() {
        StringBuilder strRepr = new StringBuilder();
        for (Card c: this.cards) {
            String line = c.toString() + "\n";
            strRepr.append(line);
        }

        return strRepr.toString();
    }

    // Test client, remove later
    public static void main(String[] args) {
        Deck deck = new Deck(6);
        System.out.println(deck);
    }
}
