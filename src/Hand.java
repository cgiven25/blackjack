import java.util.List;
import java.util.ArrayList;
public class Hand {

    private List<Card> cards;
    private int count;

    public Hand() {
        cards = new ArrayList<Card>();
        count = 0;
    }

    public int getCount() {
        return count;
    }
    
    public int getNumCards() {
        return cards.size();
    }

    public List<Card> getCards() {
        return cards;
    }

    private void calculateCount() {
        int total = 0;
        int numAces = 0;
        for(Card c: cards) {
            if (c.isHidden()) {
                continue;
            }

            if (c.isAce())
                numAces++;

            total += c.getPointValue();
        }

        while (numAces > 0 && total > 21) {
            numAces--;
            total -= 10;
        }

        count = total;;
    }

    public void insert(Card card) {
        cards.add(card);
        calculateCount();
    }

    // Tally values of hidden cards. Used by the dealer to peek for blackjack.
    public int peekHidden() {
        int total = 0;

        for (Card c : cards) {
            if (c.isHidden()) {
                total += c.getPointValue();
            }
        }

        return total;
    }

    public void revealAll() {
        for (Card c : cards) {
            if (c.isHidden())
                c.flip();
        }
        calculateCount();
    }

    public boolean isBlackjack() {
        return cards.size() == 2 && count == 21;
    }

    public void discardAll() {
        cards = new ArrayList<Card>();
        count = 0;
    }

    @Override
    public String toString() {
        StringBuilder strRepr = new StringBuilder();
        for (Card c: cards) {
            strRepr.append(c.toString() + " ");
        }

        return strRepr.toString();
    }
}
