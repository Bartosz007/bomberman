package Objects;

import Basic.GameObject;

public interface State {
    boolean checkState();
    boolean checkState(Hero obj);
}
