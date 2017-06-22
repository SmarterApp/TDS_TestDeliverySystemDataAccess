/***************************************************************************************************
 * Educational Online Test Delivery System
 * Copyright (c) 2017 Regents of the University of California
 *
 * Distributed under the AIR Open Source License, Version 1.0
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 *
 * SmarterApp Open Source Assessment Software Project: http://smarterapp.org
 * Developed by Fairway Technologies, Inc. (http://fairwaytech.com)
 * for the Smarter Balanced Assessment Consortium (http://smarterbalanced.org)
 **************************************************************************************************/

package tds.dll.common.performance.caching.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.IOException;

/**
 * This serializer is responsible for serializing/deserializing an object using the Jackson ObjectMapper.
 */
public class RedisJsonSerializer implements RedisSerializer<Object> {

    private final ObjectMapper objectMapper;

    /**
     * Constructor
     * NOTE: We clone and modify the application-wide ObjectMapper and tell it to embed
     * class information in the serialized values because we cannot pass the expected
     * class into the ObjectMapper.readValue method.
     *
     * @param objectMapper The application ObjectMapper
     */
    public RedisJsonSerializer(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper.copy();
        this.objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
    }

    @Override
    public byte[] serialize(final Object obj) throws SerializationException {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (final JsonProcessingException e) {
            throw new SerializationException("Redis is unable to serialize object", e);
        }
    }

    @Override
    public Object deserialize(final byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }

        try {
            return objectMapper.readValue(bytes, Object.class);
        } catch (final IOException e) {
            throw new SerializationException("Redis is unable to deserialize object", e);
        }
    }
}
