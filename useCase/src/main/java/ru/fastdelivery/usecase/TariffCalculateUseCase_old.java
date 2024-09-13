package ru.fastdelivery.usecase;

import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

public interface TariffCalculateUseCase_old {

    Price calculatorPriceByCargoWeight(Shipment shipment);
}
