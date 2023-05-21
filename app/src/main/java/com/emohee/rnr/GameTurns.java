package com.emohee.rnr;

import android.util.Log;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class GameTurns {

    String gameMode;

    ArrayList<GameTurn> allTurns = new ArrayList<>();
    int firstPickPosition = -1;
    int firstPickPicIdentifier;
    Instant firstPickTime;
    int secondPickPosition = -1;
    int secondPickPicIdentifier;
    Instant secondPickTime;


    GameTurns(String gameMode) {
        this.gameMode = gameMode;
    }

    //return a boolean (true means a turn has ended)
    public boolean addPick(String player, int position, int picIdentifier, Instant pickTime, int clownPosition) {

        if (firstPickPosition == -1) {

            //first pick of a turn
            firstPickPosition = position;
            firstPickPicIdentifier = picIdentifier;
            firstPickTime = pickTime;

            return false;

        } else {

            //second pick of a turn
            secondPickPosition = position;
            secondPickPicIdentifier = picIdentifier;
            secondPickTime = pickTime;

            if (gameMode.equals(AppHelper.GAME_MODE1)) {

                if ((position == clownPosition) || (firstPickPosition == clownPosition)){
                    allTurns.add(new GameTurn(player, firstPickPosition, firstPickPicIdentifier, firstPickTime, secondPickPosition, secondPickPicIdentifier, secondPickTime, true));
                } else {
                    allTurns.add(new GameTurn(player, firstPickPosition, firstPickPicIdentifier, firstPickTime, secondPickPosition, secondPickPicIdentifier, secondPickTime, false));
                }

                clearPicks();
                return true;

            } else if (gameMode.equals(AppHelper.GAME_MODE2)) {

                if ((position == clownPosition) || (firstPickPosition == clownPosition)){
                    allTurns.add(new GameTurn(player, firstPickPosition, firstPickPicIdentifier, firstPickTime, secondPickPosition, secondPickPicIdentifier, secondPickTime, true));
                } else {
                    allTurns.add(new GameTurn(player, firstPickPosition, firstPickPicIdentifier, firstPickTime, secondPickPosition, secondPickPicIdentifier, secondPickTime, false));
                }

                clearPicks();
                return true;

            } else {

                return false;

            }

        }

    }

    public void addClownAsFinalPick(String player, int position, int picIdentifier, Instant pickTime) {

        allTurns.add(new GameTurn(player, position, picIdentifier, pickTime, -1, -1, null, true));

    }

    private void clearPicks() {

        firstPickPosition = -1;
        secondPickPosition = -1;

    }

    public boolean successfulMatchInLatestTurn() {

        if (allTurns.size() > 0) {
            return allTurns.get(allTurns.size() - 1).successfulMatch(gameMode);
        } else {
            return false;
        }

    }

    public ArrayList<Integer> getPickPositionsInLatestTurn() {

        if (allTurns.size() > 0) {
            return allTurns.get(allTurns.size() - 1).getPickPositions();
        } else {
            return (new ArrayList<Integer>());
        }

    }


    class GameTurn {

        String player;
        int firstPickPosition;
        int firstPickPicIdentifier;
        Instant firstPickTime;
        int secondPickPosition;
        int secondPickPicIdentifier;
        Instant secondPickTime;
        boolean clownWasPicked;

        GameTurn(String player, int firstPickPosition, int firstPickPicIdentifier, Instant firstPickTime, int secondPickPosition, int secondPickPicIdentifier, Instant secondPickTime, boolean clownWasPicked) {

            this.player = player;
            this.firstPickPosition = firstPickPosition;
            this.firstPickPicIdentifier = firstPickPicIdentifier;
            this.firstPickTime = firstPickTime;
            this.secondPickPosition = secondPickPosition;
            this.secondPickPicIdentifier = secondPickPicIdentifier;
            this.secondPickTime = secondPickTime;
            this.clownWasPicked = clownWasPicked;

        }

        public String printDetails() {
            return ("Player = " + this.player + "; firstPickPosition = " + firstPickPosition + "; firstPickPicIdentifier = " + firstPickPicIdentifier + "; firstPickTime = " + firstPickTime + "; secondPickPosition = " + secondPickPosition + "; secondPickPicIdentifier = " + secondPickPicIdentifier + "; secondPickTime = " + secondPickTime + "; clownWasPicked = " + clownWasPicked);
        }

        public boolean successfulMatch(String gameMode) {

            if (gameMode.equals(AppHelper.GAME_MODE1) || gameMode.equals(AppHelper.GAME_MODE2)) {

                if (firstPickPicIdentifier == secondPickPicIdentifier) {
                    return true;
                } else {
                    return false;
                }

            } else {

                return false;

            }

        }

        public ArrayList<Integer> getPickPositions() {

            ArrayList<Integer> result = new ArrayList<>();

            if (firstPickPosition != -1) {
                result.add(firstPickPosition);
            }

            if (secondPickPosition != -1) {
                result.add(secondPickPosition);
            }

            return result;

        }

        public Instant getFirstPickTime() {
            return firstPickTime;
        }

        public Instant getLastPickTime() {

            if (secondPickTime == null) {
                return firstPickTime;
            } else {
                return secondPickTime;
            }

        }

        public boolean wasClownWasPicked() {
            return clownWasPicked;
        }

        public String getPlayer() { return player; }

    }


    public void printDetails() {

        if (allTurns.size() == 0) {

        } else {
            for (int i=0; i<allTurns.size(); i++) {
                Log.i("info", "GameTurns printDetails: turn " + i + "; " + allTurns.get(i).printDetails());
            }

        }

    }

    public Duration getGameDuration() {

        if (allTurns.size() == 0) {
            return null;
        } else {

            Instant t1 = allTurns.get(0).getFirstPickTime();
            Instant t2 = allTurns.get(allTurns.size() - 1).getLastPickTime();

            return Duration.between(t1, t2).abs();

        }

    }

    public int getTurnsTaken() {

        if (allTurns.size() == 0) {
            return 0;
        } else {
            return allTurns.size();
        }

    }

    //returns the turn number in which clown was picked; starts from 1
    public int getClownPickTurn() {

        int result = -1;

        for (int i=0; i<allTurns.size(); i++) {
          if (allTurns.get(i).wasClownWasPicked()) {
              result = i + 1;
          }
        }

        return result;

    }

    //returns the player who picked the clown
    public String getClownPickPlayer() {

        String result = "";

        for (int i=0; i<allTurns.size(); i++) {
            if (allTurns.get(i).wasClownWasPicked()) {
                result = allTurns.get(i).getPlayer();
            }
        }

        return result;

    }

}
