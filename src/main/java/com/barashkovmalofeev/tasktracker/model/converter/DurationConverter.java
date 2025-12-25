package com.barashkovmalofeev.tasktracker.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Duration;

@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, Long> {
    @Override
    public Long convertToDatabaseColumn(Duration duration) {
        return (duration == null) ? null : duration.getSeconds();
    }

    @Override
    public Duration convertToEntityAttribute(Long dbData) {
        return (dbData == null) ? null : Duration.ofSeconds(dbData);
    }
}
