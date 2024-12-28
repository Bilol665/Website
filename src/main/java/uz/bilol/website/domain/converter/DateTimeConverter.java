package uz.bilol.website.domain.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeConverter implements Converter<String, Date> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date convert(MappingContext<String, Date> context) {
        String source = context.getSource();
        if (source == null || source.isEmpty()) {
            return null;
        }

        try {
            return dateFormat.parse(source); // Converts string to Date
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format is " + dateFormat.toPattern(), e);
        }
    }
}
