package ru.fastdelivery.usecase;

import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

public interface TariffCalculateUseCase {

    Price calculatorPriceByCargoWeight(Shipment shipment);
}
