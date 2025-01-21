package com.ufrn.imd.divide.ai.sporting.model;

public enum SportingsModalities {
    FOOTBALL("Futebol"),
    BASKETBALL("Basquete"),
    TENNIS("Tênis"),
    SWIMMING("Natação"),
    VOLLEYBALL("Vôlei"),
    ATHLETICS("Atletismo"),
    GYMNASTICS("Ginástica"),
    BOXING("Boxe"),
    CYCLING("Ciclismo"),
    MARTIAL_ARTS("Artes Marciais"),
    RUGBY("Rugby"),
    CRICKET("Cricket"),
    BASEBALL("Beisebol"),
    HOCKEY("Hóquei"),
    GOLF("Golfe"),
    SKIING("Esqui"),
    SURFING("Surfe"),
    BADMINTON("Badminton");

    private final String description;

    SportingsModalities(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
