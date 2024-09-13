package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.dimesions.CargoDimensions;
import ru.fastdelivery.domain.common.dimesions.Length;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import javax.inject.Named;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;
import org.springframework.beans.factory.annotation.Value;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {

    private final CurrencyFactory currencyFactory;

    @Value("${cost.rub.perKg}")
    Integer costPerKgs;

    public Price calculatorPriceByCargoWeight(Shipment shipment) {

        this.isValidate(shipment);
        this.computerOfVolumes(shipment);
        this.computerOfPriceFullVolumeOfPackages(shipment);

        shipment.setTotalPrice(shipment.getTotalVolumeOfCargo() * shipment.getTotalVolumeOfCargo());

        shipment.getPackages().sort(Comparator.comparing(Pack::getWeight)
                .thenComparing(Pack::getPricePerCargoUnit));

        BigDecimal maxPrice = BigDecimal.valueOf(
                shipment.getPackages().get(shipment.getPackages().size() - 1).getPricePerCargoUnit());

        return new Price(maxPrice, shipment.getCurrency());
    }

    private void isValidate(Shipment shipment) {
        var currency = currencyFactory.create(
                shipment.getCurrency().getCode());

        if (!currency.getCode().equals("RUB")) {
            throw new RuntimeException("Your currency is not supported yet~!");
        }

        shipment.getPackages().forEach(t -> {

            if (!t.getWeight().greaterThan(new Weight(new BigInteger(String.valueOf(150000))))) {
                throw new RuntimeException("Weight of Package is more then 1500 kg");
            }

            this.dimensionsLimiterChecker(shipment);
        });
    }

    private void dimensionsLimiterChecker(Shipment shipment) {

        for (Pack cargoUnit : shipment.getPackages()) {
            if (cargoUnit.getDimensions().getHeight().length() > 1500 ||
                    cargoUnit.getDimensions().getHeight().length() < 1) {
                throw new RuntimeException("Weight of Package height is more then 1500 cm or less then 1 cm");
            }

            if (cargoUnit.getDimensions().getWidth().length() > 1500 ||
                    cargoUnit.getDimensions().getWidth().length() < 1) {
                throw new RuntimeException("Weight of Package width is more then 1500 cm or less then 1 cm");
            }

            if (cargoUnit.getDimensions().getLength().length() > 1500 ||
                    cargoUnit.getDimensions().getLength().length() < 1) {
                throw new RuntimeException("Weight of Package length is more then 1500 cm or less then 1 cm");
            }
        }
    }

    private void computerOfVolumes(Shipment shipment) {

        this.rounderOfVolumes(shipment);

        for (Pack cargo : shipment.getPackages()) {
            double volumeOfCargo = ((double)
                    (cargo.getDimensions().getHeight().length()
                            * cargo.getDimensions().getLength().length()
                            * cargo.getDimensions().getWidth().length()
                    ) / 1_000_000_000);

            cargo.setVolumeOfCargoUnit(Math.round(volumeOfCargo * 10_000d) / 10_000d);
        }
    }

    private void computerOfPriceFullVolumeOfPackages(Shipment shipment) {

        Double totalVolumeOfCargo = 0.0d;

        for (Pack cargo : shipment.getPackages()) {
            cargo.setPricePerCargoUnit(cargo.getVolumeOfCargoUnit() * costPerKgs);
            totalVolumeOfCargo += cargo.getVolumeOfCargoUnit();
        }

        shipment.setTotalVolumeOfCargo(totalVolumeOfCargo);
    }

    private void rounderOfVolumes(Shipment shipment) {

        for (Pack cargo : shipment.getPackages()) {
            Integer tempValue = cargo.getDimensions().getWidth().length();

            if (tempValue % 50 == 0) {
                continue;
            } else {

                tempValue = (tempValue * 50) / 50;

                cargo.setDimensions(new CargoDimensions(
                        cargo.getDimensions().getLength(),
                        new Length(tempValue),
                        cargo.getDimensions().getHeight()
                ));
            }
        }

        for (Pack cargo : shipment.getPackages()) {
            Integer tempValue = cargo.getDimensions().getLength().length();

            if (tempValue % 50 == 0) {
                continue;
            } else {

                tempValue = (tempValue * 50) / 50;

                cargo.setDimensions(new CargoDimensions(
                        new Length(tempValue),
                        cargo.getDimensions().getWidth(),
                        cargo.getDimensions().getHeight()
                ));
            }
        }

        for (Pack cargo : shipment.getPackages()) {
            Integer tempValue = cargo.getDimensions().getHeight().length();

            if (tempValue % 50 == 0) {
                continue;
            } else {

                tempValue = (tempValue * 50) / 50;

                cargo.setDimensions(new CargoDimensions(cargo.getDimensions().getLength(),
                        cargo.getDimensions().getWidth(),
                        new Length(tempValue)
                ));
            }
        }
    }
}
