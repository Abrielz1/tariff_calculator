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
import java.math.RoundingMode;
import org.springframework.beans.factory.annotation.Value;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {

    private final CurrencyFactory currencyFactory;

    @Value("${cost.rub.perKg}")
    Integer costPerKgs;

    @Value("${cost.rub.minimalDistancePrice}")
    Integer costOfMinimalDistance;


    public Price calculatorPriceByCargoWeight(Shipment shipment) {

        this.isValidate(shipment);
        this.computerOfVolumes(shipment);
        this.computerOfPriceFullVolumeOfPackages(shipment);

        shipment.setTotalPrice(shipment.getTotalVolumeOfCargo() * shipment.getTotalVolumeOfCargo());

        Integer distanceBetweenDepartureAndDestination = this.distanceBetweenDepartureAndDestinationComputer(shipment);
        Double priceOfShipmentBetweenCoordinates = this.costOfShipmentBetweenDepartureAndDestinationComputer(shipment,
                distanceBetweenDepartureAndDestination);

        return new Price(BigDecimal.valueOf(priceOfShipmentBetweenCoordinates).setScale(2, RoundingMode.HALF_UP),
                shipment.getCurrency());
    }

    private void isValidate(Shipment shipment) {
        var currency = currencyFactory.create(
                shipment.getCurrency().getCode());

        if (!currency.getCode().equals("RUB")) {
            throw new RuntimeException("Your currency is not supported yet~!");
        }

        shipment.getPackages().forEach(t -> {

            if (t.getWeight().greaterThan(new Weight(new BigInteger(String.valueOf(150000))))) {
                throw new RuntimeException("Weight of Package is more then 150 kg");
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

        if (costPerKgs == null) {
            costPerKgs = 400;
        }

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

    private Integer distanceBetweenDepartureAndDestinationComputer(Shipment shipment) {

        Double PI = 3.14159265358979d;

        Double radiusEarthSphere = 6372795d;

        Double divisor = PI / 180;


        Double latitudeOfDepartureInRadians = shipment.getDeparture().latitude().coordinates() * divisor;

        Double longitudeOfDepartureInRadians =shipment.getDeparture().longitude().coordinates() * divisor;


        Double latitudeOfDestinationInRadians = shipment.getDestination().latitude().coordinates() * divisor;

        Double longitudeOfDestinationInRadians = shipment.getDestination().longitude().coordinates() * divisor;


        Double cosinesOfLatitudeDeparture = Math.cos(latitudeOfDepartureInRadians);

        Double cosinesOfLatitudeDestination = Math.cos(longitudeOfDepartureInRadians);

        Double sinusOfLatitudeDeparture = Math.sin(latitudeOfDestinationInRadians);

        Double sinusOfLatitudeDestination = Math.sin(longitudeOfDestinationInRadians);


        Double deltaBetweenDepartureAndDestinationLongitudes = longitudeOfDestinationInRadians - longitudeOfDepartureInRadians;

        Double cosinesDeltaBetweenDepartureAndDestinationLongitudes = Math.cos(deltaBetweenDepartureAndDestinationLongitudes);

        Double sinusDeltaBetweenDepartureAndDestinationLongitudes = Math.sin(deltaBetweenDepartureAndDestinationLongitudes);


        Double yCoordinate = Math.sqrt(Math.pow(sinusOfLatitudeDestination * sinusDeltaBetweenDepartureAndDestinationLongitudes, 2)
                + Math.pow(cosinesOfLatitudeDeparture * sinusOfLatitudeDestination -
                sinusOfLatitudeDeparture * cosinesOfLatitudeDestination * cosinesDeltaBetweenDepartureAndDestinationLongitudes, 2));

        Double xCoordinate = sinusOfLatitudeDeparture*sinusOfLatitudeDestination
                + cosinesOfLatitudeDeparture * cosinesOfLatitudeDestination * cosinesDeltaBetweenDepartureAndDestinationLongitudes;

        Double ad = Math.atan2(yCoordinate, xCoordinate);

        Double distanceOfBigCircle = ad * radiusEarthSphere;

        return Math.toIntExact(Math.round(distanceOfBigCircle));
    }

    private Double costOfShipmentBetweenDepartureAndDestinationComputer(Shipment shipment, Integer distance) {

        if (costOfMinimalDistance == null || costOfMinimalDistance == 0) {
            costOfMinimalDistance = 450;
        }

        if (distance <= costOfMinimalDistance) {

            return shipment.getTotalVolumeOfCargo();
        }

        return ((double) distance / costOfMinimalDistance) * shipment.getTotalVolumeOfCargo();
    }
}
