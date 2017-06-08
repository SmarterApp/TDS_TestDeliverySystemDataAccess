package tds.dll.common.performance.caching.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import tds.dll.common.performance.domain.Externs;

import static org.assertj.core.api.Assertions.assertThat;

public class RedisJsonSerializerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private RedisJsonSerializer serializer;

    @Before
    public void setup() {
        serializer = new RedisJsonSerializer(objectMapper);
    }

    @Test
    public void itShouldSerializeAndDeserializeAnObject() {
        final Externs externs = new Externs();
        externs.setClientName("client");
        externs.setClientStylePath("path");
        externs.setEnvironment("env");
        externs.setIsPracticeTest(true);
        externs.setProctorCheckin("checkin");

        final byte[] serializedBytes = serializer.serialize(externs);
        final Externs deserialized = (Externs) serializer.deserialize(serializedBytes);
        assertThat(deserialized.getClientName()).isEqualTo(externs.getClientName());
        assertThat(deserialized.getClientStylePath()).isEqualTo(externs.getClientStylePath());
        assertThat(deserialized.getEnvironment()).isEqualTo(externs.getEnvironment());
        assertThat(deserialized.getIsPracticeTest()).isEqualTo(externs.getIsPracticeTest());
        assertThat(deserialized.getProctorCheckin()).isEqualTo(externs.getProctorCheckin());
    }
}