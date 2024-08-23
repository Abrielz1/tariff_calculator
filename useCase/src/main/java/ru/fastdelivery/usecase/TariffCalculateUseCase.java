package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;
import java.math.BigInteger;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {

    private final WeightPriceProvider weightPriceProvider;

    private final CurrencyFactory currencyFactory;

    public Price calculatorPriceByCargoWeight(Shipment shipment) {

        this.isValidate(shipment);

        return null;
    }

    private void isValidate(Shipment shipment) {
        var currency = currencyFactory.create(shipment.getCurrency().getCode());

        if (!currency.getCode().equals("RUB")) {
            throw new RuntimeException(); //todo слепить спец исключение
        }

        shipment.getPackages().forEach(t -> {

            if (!t.weight().greaterThan(new Weight(new BigInteger(String.valueOf(150000))))) {
                throw new RuntimeException();//todo слепить спец исключение
            }

            //todo слепить спец проверку размеров
        });
    }
}
