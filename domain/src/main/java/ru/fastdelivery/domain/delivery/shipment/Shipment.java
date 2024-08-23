package ru.fastdelivery.domain.delivery.shipment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.delivery.Departure;
import ru.fastdelivery.domain.common.delivery.Destination;
import ru.fastdelivery.domain.delivery.pack.Pack;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

    List<Pack> packages;

    Currency currency;

    Destination destination;

    Departure departure;
}
