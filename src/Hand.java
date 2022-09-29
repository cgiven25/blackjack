import java.util.*;
public class Hand {

    private List<Card> cards;
    
    public Hand() {
        this.cards = new ArrayList<Card>();
    }

    public int calculateScore() {
        int score = 0;
        int numAces = 0;
        for(Card c: this.cards) {
            if (c.isAce())
                numAces++;

            score += c.getPointValue();
        }

        while (numAces > 0 && score > 21) {
            numAces--;
            score -= 10;
        }

        return score;
    }
    
    public void insert(Card card) {
        this.cards.add(card);
    }

    public void revealAll() {
        for (Card c : this.cards) {
            if (c.isHidden())
                c.flip();
        }
    }

    public boolean isBlackjack() {
        return this.cards.size() == 2 && this.calculateScore() == 21;
    }

    public void discardAll() {
        this.cards = new ArrayList<Card>();
    }

    public String toString() {
        StringBuilder strRepr = new StringBuilder();
        for (Card c: this.cards) {
            String line = c.toString() + "\n";
            strRepr.append(line);
        }

        return strRepr.toString();
    }
}
