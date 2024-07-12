package uta.cse3310;

public class Action {
    ActionType YourAction;
    PlayerType PlayerType;
    int GameId;

    Action(int GameId, PlayerType PlayerType, ActionType YourAction){
        this.GameId = GameId;
        this.PlayerType = PlayerType;
        this.YourAction = YourAction;
    }

    public void takeAction(ActionType Action){
        // Here imlements the method that takes the action
    }
}
