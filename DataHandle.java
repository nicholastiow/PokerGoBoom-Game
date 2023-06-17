import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataHandle {
    private HashSet<String> savedFileSet = new HashSet<String>();

    public void writeCardsToFile(List<Card> cards, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            for (Card card : cards) {
                writer.write(card.toString() + ",");
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writePlayerToFile(List<Player> players, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {

            for (Player player : players) {
                writer.write("\n");
                writer.write(player.getName() + "\n");
                writer.write(player.getScore() + "\n");

                List<Card> hand = player.getHand();
                for (Card card : hand) {
                    writer.write(card.toString() + ",");
                }
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeStringToFile(List<String> colors, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            for (String color : colors) {
                writer.write(color + ",");
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeIntToFile(int data, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(Integer.toString(data));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLeadCard(Card leadCard, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(leadCard.toString());
            writer.newLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void writeMapToFile(Map<Integer, Card> map, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            if (map.size() == 0) {
                writer.write("");
            }
            for (Entry<Integer, Card> entry : map.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue().toString() + ",");
            }
            writer.newLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<Card> readCardFromFile(String fileName, int lineNumber) {
        List<Card> cards = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            for (int i = 1; i <= lineNumber; i++) {
                String line = reader.readLine();
                if (i == lineNumber) {
                    if(line.isEmpty()) {
                        return cards;
                    }
                    String[] cardStrings = line.split(",");
                    for (String cardString : cardStrings) {
                        Card card = parseCard(cardString);
                        if (card != null) {
                            cards.add(card);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cards;
    }

    private Card parseCard(String line) {
        String extracted = line.replace("./images/", "").replace(".png", "");
        String suitSymbol = extracted.substring(0, 1);
        String rankSymbol = extracted.substring(1, extracted.length());



        if (suitSymbol.equals("c")) {
            suitSymbol = "CLUBS";
        } else if (suitSymbol.equals("d")) {
            suitSymbol = "DIAMONDS";
        } else if (suitSymbol.equals("h")) {
            suitSymbol = "HEARTS";
        } else if (suitSymbol.equals("s")) {
            suitSymbol = "SPADES";
        } else if (suitSymbol.equals("b")) {
            suitSymbol = "BLANK";
        }

        if (rankSymbol.equals("B")) {
            rankSymbol = "BLANK";
        } else if (rankSymbol.equals("A")) {
            rankSymbol = "ACE";
        } else if (rankSymbol.equals("2")) {
            rankSymbol = "TWO";
        } else if (rankSymbol.equals("3")) {
            rankSymbol = "THREE";
        } else if (rankSymbol.equals("4")) {
            rankSymbol = "FOUR";
        } else if (rankSymbol.equals("5")) {
            rankSymbol = "FIVE";
        } else if (rankSymbol.equals("6")) {
            rankSymbol = "SIX";
        } else if (rankSymbol.equals("7")) {
            rankSymbol = "SEVEN";
        } else if (rankSymbol.equals("8")) {
            rankSymbol = "EIGHT";
        } else if (rankSymbol.equals("9")) {
            rankSymbol = "NINE";
        } else if (rankSymbol.equals("10")) {
            rankSymbol = "TEN";
        } else if (rankSymbol.equals("J")) {
            rankSymbol = "JACK";
        } else if (rankSymbol.equals("Q")) {
            rankSymbol = "QUEEN";
        } else if (rankSymbol.equals("K")) {
            rankSymbol = "KING";
        }

        try {
            Suit suit = Suit.valueOf(suitSymbol);
            Rank rank = Rank.valueOf(rankSymbol);
            return new Card(suit, rank);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid card: " + line);
            return null;
        }
    }

    public List<Player> readPlayerFromFile(String fileName, int lineNumber) {
        List<Player> players = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            for (int i = 1; i <= lineNumber; i++) {
                String line = reader.readLine();
                if (i == lineNumber) {
                    String name = line;
                    Player player = new Player(name);
                    int score = Integer.parseInt(reader.readLine());
                    player.setScore(score);
                    String newLine = reader.readLine();
                    String[] cardStrings = newLine.split(",");
                    List<Card> cards = new ArrayList<>();
                    for (String cardString : cardStrings) {
                        Card card = parseCard(cardString);
                        if (card != null) {
                            cards.add(card);
                        }
                    }
                    for (Card card : cards) {
                        player.addToHand(card);
                    }

                    players.add(player);

                    String name2 = reader.readLine();
                    System.out.println(name2);
                    Player player2 = new Player(name2);
                    int score2 = Integer.parseInt(reader.readLine());
                    player2.setScore(score2);
                    String newLine2 = reader.readLine();
                    String[] cardStrings2 = newLine2.split(",");
                    List<Card> cards2 = new ArrayList<>();
                    for (String cardString2 : cardStrings2) {
                        Card card2 = parseCard(cardString2);
                        if (card2 != null) {
                            cards2.add(card2);
                        }
                    }
                    for (Card card2 : cards2) {
                        player2.addToHand(card2);
                    }

                    players.add(player2);

                    String name3 = reader.readLine();
                    System.out.println(name3);
                    Player player3 = new Player(name3);
                    int score3 = Integer.parseInt(reader.readLine());
                    player3.setScore(score3);
                    String newLine3 = reader.readLine();
                    String[] cardStrings3 = newLine3.split(",");
                    List<Card> cards3 = new ArrayList<>();
                    for (String cardString3 : cardStrings3) {
                        Card card3 = parseCard(cardString3);
                        if (card3 != null) {
                            cards3.add(card3);
                        }
                    }
                    for (Card card3 : cards3) {
                        player3.addToHand(card3);
                    }

                    players.add(player3);

                    String name4 = reader.readLine();
                    System.out.println(name4);
                    Player player4 = new Player(name4);
                    int score4 = Integer.parseInt(reader.readLine());
                    player4.setScore(score4);
                    String newLine4 = reader.readLine();
                    String[] cardStrings4 = newLine4.split(",");
                    List<Card> cards4 = new ArrayList<>();
                    for (String cardString : cardStrings4) {
                        Card card4 = parseCard(cardString);
                        if (card4 != null) {
                            cards4.add(card4);
                        }
                    }
                    for (Card card4 : cards4) {
                        player4.addToHand(card4);
                    }

                    players.add(player4);

                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return players;
    }

    public List<String> readColorFromFile(String fileName, int lineNumber) {
        List<String> colors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            for (int i = 1; i <= lineNumber; i++) {
                String line = reader.readLine();
                if (i == lineNumber) {
                    String[] colorStrings = line.split(",");
                    for (String colorString : colorStrings) {
                        colors.add(colorString);
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return colors;
    }

    public int readIntFromFile(String fileName, int lineNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            for (int i = 1; i <= lineNumber; i++) {
                String line = reader.readLine();
                if (i == lineNumber) {
                    int data = Integer.parseInt(line);
                    return data;
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public Card readLeadCardFromFile(String fileName, int lineNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            for (int i = 1; i <= lineNumber; i++) {
                String line = reader.readLine();
                if (i == lineNumber) {
                    Card card = parseCard(line);
                    return card;
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Map<Integer, Card> readMapFromFile(String fileName, int lineNumber) {
        Map<Integer, Card> map = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            
            for (int i = 1; i <= lineNumber; i++) {
                String line = reader.readLine();
                if (i == lineNumber) {
                    System.out.println("line: " + line);
                    if (line == null || line.isEmpty()) {
                        return map;
                    }
                    String[] entryStrings = line.split(",");
                    for (String entryString : entryStrings) {
                        String[] parts = entryString.split(":");
                        int key = Integer.parseInt(parts[0]);
                        System.out.println("key: " + key);
                        Card card = parseCard(parts[1]);
                        System.out.println("card: " + card.toString());
                        map.put(key, card);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public void readDataFolder() {
        String folderPath = "./data/";

        try {
            Path folder = Paths.get(folderPath);
            Files.walk(folder).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    savedFileSet.add(filePath.toString());
                }
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for (String file : savedFileSet) {
            System.out.println(file);
        }
    }

    public HashSet<String> getSavedFileSet() {
        return savedFileSet;
    }

}
