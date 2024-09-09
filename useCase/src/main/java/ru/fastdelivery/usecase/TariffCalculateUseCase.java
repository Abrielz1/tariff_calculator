package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import javax.inject.Named;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {

    private final WeightPriceProvider weightPriceProvider;

    private final CurrencyFactory currencyFactory;

    @Value("${cost.rub.perKg}")
    Integer costPerKgs;

    public Price calculatorPriceByCargoWeight(Shipment shipment) {

        this.isValidate(shipment);

        List<Double> packageVolumesList = this.computerOfVolumes(shipment);
        List<Double> pricePerPackage = this.computerOfPriceFullVolumeOfPackages(packageVolumesList);

        return null;
    }

    private void isValidate(Shipment shipment) {
        var currency = currencyFactory.create(shipment.getCurrency().getCode());

        if (!currency.getCode().equals("RUB")) {
            throw new RuntimeException("Your currency is not supported yet~!");
        }

        shipment.getPackages().forEach(t -> {

            if (!t.weight().greaterThan(new Weight(new BigInteger(String.valueOf(150000))))) {
                throw new RuntimeException("Weight of Package is more then 1500 kg");
            }

            this.dimensionsLimiterChecker(shipment);
        });
    }

    private void dimensionsLimiterChecker(Shipment shipment) {

        for (Pack cargoUnit : shipment.getPackages()) {
            if (cargoUnit.dimensions().height().length() > 1500 || cargoUnit.dimensions().height().length() < 1) {
                throw new RuntimeException("Weight of Package height is more then 1500 cm or less then 1 cm");
            }

            if (cargoUnit.dimensions().width().length() > 1500 || cargoUnit.dimensions().width().length() < 1) {
                throw new RuntimeException("Weight of Package width is more then 1500 cm or less then 1 cm");
            }

            if (cargoUnit.dimensions().length().length() > 1500 || cargoUnit.dimensions().length().length() < 1) {
                throw new RuntimeException("Weight of Package length is more then 1500 cm or less then 1 cm");
            }
        }
    }

    private List<Double> computerOfVolumes(Shipment shipment) {

        List<Double> result = new ArrayList<>();

        for (Pack cargo : shipment.getPackages()) {
            double volumeOfCargo = ((double)
                    (cargo.dimensions().height().length()
                    * cargo.dimensions().length().length()
                    * cargo.dimensions().width().length()
                    ) / 1_000_000_000);

            result.add(Math.round(volumeOfCargo * 10_000d) / 10_000d);
        }

        return result;
    }

    private List<Double> computerOfPriceFullVolumeOfPackages(List<Double> packageVolumesList) {

        List<Double> resultPricePerPackageList = new ArrayList<>();

        for (Double cargo : packageVolumesList) {
            resultPricePerPackageList.add(cargo * costPerKgs);
        }

        return resultPricePerPackageList;
    }
}
