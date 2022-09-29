import java.util.Map;
public class Card {
    
    private static final Map<String, Integer> specialRankToPoints = Map.of(
        "Ace", 11,
        "King", 10,
        "Queen", 10,
        "Jack", 10
    );

    private String rank;
    private String suit;
    private int pointValue;
    private boolean hidden;

    public Card(String rank, String suit) { this(rank, suit, false); }

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
        return this.rank;
    }

    public String getSuit() {
        return this.suit;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void flip() {
        this.hidden = !this.hidden;
    }

    public boolean isAce() {
        return this.rank == "Ace";
    }

    public int getPointValue() {
        return this.pointValue;
    }

    public String toString() {
        String strRepr;

        if (this.isHidden()) {
            strRepr = "?";
        } else {
            strRepr = String.format("%s of %s", this.rank, this.suit);
        }
        return strRepr;
    }

    // Test client, delete later
    public static void main(String[] args) {
        Card test1 = new Card("Ace", "Hearts");
        Card test2 = new Card("6", "Clubs", false);
        System.out.println(test1);
        System.out.println(test2);
    }

}
