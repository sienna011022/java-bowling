package bowling.domain.frame;

import bowling.domain.State.State;

public class FinalFrame extends Frame {
    public FinalFrame(FrameNumber frameNumber, State state) {
        super(frameNumber, state);

        if (!frameNumber.isFinal()) {
            throw new IllegalArgumentException(
                    String.format("frameNumber(%s)는 마지막 FramenNumber(%s)가 아닙니다.", frameNumber, FrameNumber.MAX));
        }
    }

    @Override
    public Frame next() {
        throw new IllegalArgumentException("마지막 프레임은 다음 프레임을 생성 할 수 없습니다.");
    }

    @Override
    public boolean isFinal() {
        return true;
    }

    @Override
    public boolean isDone() {
        return state.isDone();
    }
}
