package ru.fastdelivery.domain.common.delivery;

import ru.fastdelivery.domain.common.destinations.Latitude;
import ru.fastdelivery.domain.common.destinations.Longitude;

public record Departure(Latitude latitude,
                        Longitude longitude) {
}
