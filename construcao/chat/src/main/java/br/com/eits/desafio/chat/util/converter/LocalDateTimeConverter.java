package br.com.eits.desafio.chat.util.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp>{

	public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
		// TODO Auto-generated method stub
		return Timestamp.valueOf(localDateTime);
	}

	public LocalDateTime convertToEntityAttribute(Timestamp timeStamp) {
		return timeStamp.toLocalDateTime();
	}

}
