package org.eam.games.poj.drawable;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import lombok.SneakyThrows;

/**
 * Provides common methods for displayable entities. Returned type is {@link BufferedImage} to provide additional
 * functionality.
 */
public interface WithImage {

    /**
     * Reads an image from resources folder.
     */
    @SneakyThrows
    default BufferedImage fromResource(String filename) {
        return ImageIO.read(WithImage.class.getResource(filename));
    }

    /**
     * Reads an image from full filename.
     */
    @SneakyThrows
    default BufferedImage fromPath(Path path) {
        return ImageIO.read(path.toFile());
    }

}
