package org.eam.games.poj.engine;

import com.google.common.base.Stopwatch;
import java.awt.Component;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eam.games.poj.ui.Display;

/**
 * Starts game loop and periodically notifies the world to repaint itself, eventually calling
 * {@link Display#paintComponent(Graphics)} method.
 */
@Log4j2
@RequiredArgsConstructor
public class GameTimer extends TimerTask {

    private final Component display;
    private final AtomicInteger framesCounter = new AtomicInteger();
    private final AtomicLong durationStart = new AtomicLong(System.currentTimeMillis());

    @Override
    public void run() {
        Stopwatch started = Stopwatch.createStarted(); // todo create by property

        display.repaint();

        log.trace("Redrawn on timer: elapsedMcs={}", () -> started.elapsed(TimeUnit.MICROSECONDS));
        framesCounter.incrementAndGet();
        long current = System.currentTimeMillis();
        if (current - durationStart.get() > 1000L) {
            log.info("Control period passed: durationMs={}, FPS={}",
                () -> current - durationStart.get(), framesCounter::get);
            durationStart.set(current);
            framesCounter.set(0);
        }
    }

    public static void start(Display display) {
        GameTimer gameTimer = new GameTimer(display);
        Timer timer = new Timer("gameLoop");
        timer.scheduleAtFixedRate(gameTimer, 0, 1000L / 60);
    }

}
