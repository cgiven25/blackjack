import java.util.Map;
import java.util.HashMap;
public class Card {
    
    private static Map<String, Integer> specialRankToPoints = new HashMap<>();

    static {
        specialRankToPoints.put("Ace", 11);
        specialRankToPoints.put("King", 10);
        specialRankToPoints.put("Queen", 10);
        specialRankToPoints.put("Jack", 10);
    }

    private String rank;
    private String suit;
    private boolean hidden;

    private int pointValue;

    public Card(String rank, String suit) { 
        this(rank, suit, false); 
    }

    public Card(String rank, String suit, boolean hidden) {
        this.rank = rank;
        this.suit = suit;
        this.hidden = hidden;

        if (specialRankToPoints.containsKey(rank)) {
            this.pointValue = specialRankToPoints.get(rank);
        } else {
            this.pointValue = Integer.parseInt(rank);
        }
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public boolean isHidden() {
        return hidden;
    }

    public int getPointValue() {
        return pointValue;
    }

    public void flip() {
        hidden = !hidden;
    }

    public boolean isAce() {
        return rank.equals("Ace");
    }

    @Override
    public String toString() {
        if (isHidden()) {
            return "?";
        } else {
            if (specialRankToPoints.containsKey(rank)) {
                return String.format("%s%s", rank.substring(0, 1), suit.substring(0, 1));
            } else {
                return String.format("%s%s", rank, suit.substring(0, 1));
            }
        }
    }
}
