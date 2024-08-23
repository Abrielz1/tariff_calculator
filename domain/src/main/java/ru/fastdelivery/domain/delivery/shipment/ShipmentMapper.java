package ru.fastdelivery.domain.delivery.shipment;

public class ShipmentMapper {

    public static Shipment toShipment(ShipmentNewDTO newShipment) {

        return Shipment
                .builder()
                .packages(newShipment.packages())
                .currency(newShipment.currency())
                .departure(newShipment.departure())
                .destination(newShipment.destination())
                .build();
    }
}
