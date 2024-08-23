package ru.fastdelivery.domain.common.delivery;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import ru.fastdelivery.domain.common.destinations.Latitude;
import ru.fastdelivery.domain.common.destinations.Longitude;

public record Departure(

        @Min(45)
        @Max(65)
        Latitude latitude,

        @Min(30)
        @Max(96)
        Longitude longitude) {

}
