package org.eam.games.poj.actor;

/**
 * Provides some simple statistics to be displayed on HUD.
 */
public interface WithStats {

    default String stats() {
        return "";
    }

}
