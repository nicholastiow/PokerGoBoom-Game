class Card {
    private Suit suit;
    private Rank rank;
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }
    
    public Suit getSuit() {
        return suit;
    }
    
    public Rank getRank() {
        return rank;
    }
    
    @Override
    public String toString() {
        // Return a file path string representation of the card
        return "./images/" + suit + "" + rank + ".png";
    }
}    