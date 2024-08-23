package ru.fastdelivery.domain.delivery.shipment;

import jakarta.validation.constraints.NotNull;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.delivery.Departure;
import ru.fastdelivery.domain.common.delivery.Destination;
import ru.fastdelivery.domain.delivery.pack.Pack;
import java.util.List;

/**
 * @param packages упаковки в грузе
 * @param currency валюта объявленная для груза
 * @param destination
 * @param departure
 */
public record ShipmentNewDTO(

        @NotNull
        List<Pack> packages,

        @NotNull
        Currency currency,

        @NotNull
        Destination destination,

        @NotNull
        Departure departure
) {
}
