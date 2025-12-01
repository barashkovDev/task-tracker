package com.barashkovmalofeev.tasktracker.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Duration;

@Converter(autoApply = true) // Применяется автоматически ко всем Duration
public class DurationConverter implements AttributeConverter<Duration, Long> {
    @Override
    public Long convertToDatabaseColumn(Duration duration) {
        // Сохраняем длительность как общее количество секунд
        return (duration == null) ? null : duration.getSeconds();
    }

    @Override
    public Duration convertToEntityAttribute(Long dbData) {
        // Восстанавливаем Duration из секунд
        return (dbData == null) ? null : Duration.ofSeconds(dbData);
    }
}
