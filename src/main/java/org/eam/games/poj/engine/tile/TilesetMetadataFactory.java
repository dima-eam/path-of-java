package org.eam.games.poj.engine.tile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.InputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

/**
 * Auxiliary class for creating {@link TilesetMetadata} instances from YAML file. May not be used in application code.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class TilesetMetadataFactory {

    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

    /**
     * Reads YAML file and deserializes its content into {@link TilesetMetadata} instance.
     */
    @SneakyThrows
    static TilesetMetadata fromFile(String filename) {
        InputStream resourceAsStream = TilesetMetadataFactory.class.getResourceAsStream(filename);

        return mapper.readValue(resourceAsStream, TilesetMetadata.class);
    }

}
