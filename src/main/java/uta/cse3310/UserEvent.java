package uta.cse3310;
// User events are sent from the webpage to the server

public class UserEvent {

    int GameId; 
    PlayerType PlayerType; 
    ActionType Action;
    
    UserEvent(int GameId, PlayerType PlayerType, ActionType Action) {
        this.GameId = GameId;
        this.PlayerType = PlayerType;
        this.Action = Action;
    }
    public int getGameIdx(){
        return GameId;
    }
    public PlayerType getPlayerType(){
        return PlayerType;
    }
    public ActionType getAction(){
        return Action;
    }

    public void setAction(ActionType Action){
        this.Action = Action;
    }

    public void setPlayerType(PlayerType PlayerType){
        this.PlayerType = PlayerType;
    }
}
