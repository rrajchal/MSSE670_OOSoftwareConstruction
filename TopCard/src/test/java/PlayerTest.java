import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.topcard.Card;
import org.topcard.Deck;
import org.topcard.Player;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

public class PlayerTest {

    @Test
    public void testPlayerConstructor() {
        LocalDate dob = LocalDate.of(1990, 1, 1); // January 1, 1990
        Player player = new Player("", "", "Mickey", "Mouse", dob);

        assertEquals("Mickey", player.getFirstName());
        assertEquals("Mouse", player.getLastName());
        assertEquals(dob, player.getDateOfBirth());
        assertEquals(0, player.getPoints());
        assertFalse(player.isAdmin());
        assertNotNull(player.getHand());
        assertEquals(3, player.getHand().length);
    }

    @Test
    public void testSetAdmin() {
        Player player = new Player("", "", "Donald", "Duck", LocalDate.of(1995, 6, 15));
        player.setAdmin(true);
        assertTrue(player.isAdmin());
    }

    @Test
    public void testDrawCard() {
        Player player = new Player("", "", "Michael", "Smith", LocalDate.of(1985, 11, 25));
        Deck deck = new Deck();
        deck.shuffle();

        player.drawCard(deck);
        player.drawCard(deck);
        player.drawCard(deck);

        int nonNullCards = 0;
        for (Card card : player.getHand()) {
            if (card != null) {
                nonNullCards++;
            }
        }

        assertEquals(3, nonNullCards);
    }

    @Test
    public void testGetHandValue() {
        Player player = new Player("", "", "Rajesh", "Rajchal", LocalDate.of(1980, 4, 10));
        Deck deck = new Deck();
        deck.shuffle();

        player.drawCard(deck);
        player.drawCard(deck);
        player.drawCard(deck);

        int handValue = player.getHandValue();
        assertTrue(handValue > 0);
    }

    @Test
    public void testAddPoints() {
        Player player = new Player("", "", "Charlie", "Brown", LocalDate.of(2000, 2, 1));
        player.addPoints(10);
        assertEquals(10, player.getPoints());

        player.addPoints(5);
        assertEquals(15, player.getPoints());
    }

    @Test
    public void testGetAge() {
        LocalDate dob = LocalDate.of(1995, 6, 15); // June 15, 1995
        Player player = new Player("", "", "Dana", "White", dob);
        int age = player.getAge();
        assertTrue(age > 0);
    }

    @Test
    public void testShowHand() {
        Player player = new Player("", "", "Mickey", "Mouse", LocalDate.of(1990, 1, 1));
        Deck deck = new Deck();
        deck.shuffle();

        player.drawCard(deck);
        player.drawCard(deck);
        player.drawCard(deck);

        // Capture the output of showHand()
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent)); //
        //System.out.println(outContent);

        player.showHand();

        // Verify the output is not empty and contains cards
        String output = outContent.toString();
        assertFalse(output.isEmpty());
        assertTrue(output.contains(" OF "));
    }

    @Test
    public void testIsAuthorized() {
        Player player = new Player("", "", "Baby", "King", LocalDate.of(2010, 5, 20)); // Under 18
        assertFalse(player.isAuthorized());

        player = new Player("", "", "Adult", "Queen", LocalDate.of(1990, 3, 10)); // Over 18
        assertTrue(player.isAuthorized());
    }

    @Test
    void testAuthenticateSuccessful() {
        Player player = new Player("user123", "pass123", "", "", LocalDate.of(2000, 1, 1));
        assertTrue(player.authenticate("user123", "pass123"), "Authentication should be successful with correct credentials.");
    }

    @Test
    void testAuthenticateWrongUsername() {
        Player player = new Player("user123", "pass123", "", "", LocalDate.of(2000, 1, 1));
        assertFalse(player.authenticate("wrongUser", "pass123"), "Authentication should fail with wrong username.");
    }

    @Test
    void testAuthenticateWrongPassword() {
        Player player = new Player("user123", "pass123", "", "", LocalDate.of(2000, 1, 1));
        assertFalse(player.authenticate("user123", "wrongPass"), "Authentication should fail with wrong password.");
    }

    @Test
    void testAuthenticateNullUsername() {
        Player player = new Player("user123", "pass123", "", "", LocalDate.of(2000, 1, 1));
        assertFalse(player.authenticate(null, "pass123"), "Authentication should fail with null username.");
    }

    @Test
    void testAuthenticateNullPassword() {
        Player player = new Player("user123", "pass123", "", "", LocalDate.of(2000, 1, 1));
        assertFalse(player.authenticate("user123", null), "Authentication should fail with null password.");
    }

    @Test
    void testAuthenticateNullCredentials() {
        Player player = new Player("user123", "pass123", "", "", LocalDate.of(2000, 1, 1));
        assertFalse(player.authenticate(null, null), "Authentication should fail with null credentials.");
    }
}
