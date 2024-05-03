package hw0;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BallTest {

	private static Ball b = null;
	private static final double BALL_VOLUME = 20.0;
	private static final double JUNIT_DOUBLE_DELTA = 0.0001;
	private static final Color COLOR = Color.BLUE;
	
	@BeforeAll
	public static void setupBeforeTests() throws Exception {
		b = new Ball(BALL_VOLUME, COLOR);
	}
	
	@Test
	public void testVolume() {
		assertEquals(BALL_VOLUME, b.getVolume(), JUNIT_DOUBLE_DELTA, "b.getVolume()");
	}
	
	@Test
	public void testColor() {
		assertEquals(COLOR, b.getColor(), "b.getColor()");
	}
	
	@Test
	public void testCreateWithValidStringVolume() {
		String volume = "21.4e2";
		assertEquals(Double.parseDouble(volume),
				new Ball(volume, Color.WHITE).getVolume(), JUNIT_DOUBLE_DELTA,
				"new(" + volume + ").getVolume()");
	}
}
