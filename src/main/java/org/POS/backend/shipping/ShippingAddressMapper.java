package org.POS.backend.shipping;

public class ShippingAddressMapper{

    public ShippingAddress toShippingAddress(AddShippingRequestDto dto){
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setName(dto.name());
        shippingAddress.setPhoneNumber(dto.phoneNumber());
        shippingAddress.setShippingAddress(dto.shippingAddress());
        shippingAddress.setCity(dto.city());
        shippingAddress.setBarangay(dto.barangay());
        shippingAddress.setLandmark(dto.landmark());
        shippingAddress.setNote(dto.note());
        return shippingAddress;
    }
}
