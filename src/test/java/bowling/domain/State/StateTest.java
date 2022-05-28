package bowling.domain.State;

import org.junit.jupiter.api.Test;

import static bowling.domain.State.PinTest.ONE;
import static bowling.domain.State.PinTest.TEN;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StateTest {
    public static final State READY = State.ready();
    public static final State GUTTER = new Gutter();
    public static final State FIRST = new First(ONE);
    public static final State SECOND = new Second(ONE, FIRST);
    public static final State SPARE = new Spare(TEN, GUTTER);
    public static final State MISS = new Miss(FIRST);
    public static final State STRIKE = new Strike(TEN);

    @Test
    void isDone은_프레임_종료_여부를_반환한다() {
        assertAll(
                () -> assertFalse(READY.isDone()),
                () -> assertFalse(FIRST.isDone()),
                () -> assertFalse(GUTTER.isDone()),
                () -> assertTrue(MISS.isDone()),
                () -> assertTrue(SECOND.isDone()),
                () -> assertTrue(SPARE.isDone()),
                () -> assertTrue(STRIKE.isDone())
        );
    }
}
