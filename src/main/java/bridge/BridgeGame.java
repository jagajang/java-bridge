package bridge;

import java.util.List;

/**
 * 다리 건너기 게임을 관리하는 클래스
 */
public class BridgeGame {
    private final BridgeMaker bridgeMaker;
    private final InputView inputView;
    private final OutputView outputView;

    private int size;
    private boolean success;

    BridgeGame(BridgeMaker bridgeMaker, InputView inputView, OutputView outputView) {
        this.bridgeMaker = bridgeMaker;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void startGameCatchingException() {
        try {
            startGame();
        } catch(IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    public void startGame() {
        int trial = 0;
        outputView.printStartGameMessage();
        do {
            trial++;
            outputView.printAskSizeMessage();
            size = inputView.readBridgeSize();
            success = move(bridgeMaker.makeBridge(size));
        } while(!success && retry());

        outputView.printResult(success, trial);
    }

    /**
     * 사용자가 칸을 이동할 때 사용하는 메서드
     * <p>
     * 이동을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public boolean move(List<String> bridge) {
        boolean correct = true;
        for(int pos = 0; pos < size; pos++) {
            outputView.printAskMoveMessage();
            correct = inputView.readMoving().equals(bridge.get(pos));
            outputView.printMap(bridge, pos, correct);
            if(!correct) {
                break;
            }
        }
        return correct;
    }

    /**
     * 사용자가 게임을 다시 시도할 때 사용하는 메서드
     * <p>
     * 재시작을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public boolean retry() {
        String userInput;

        outputView.printAskRetryMessage();
        userInput = inputView.readGameCommand();

        return userInput.equals("R");
    }
}
