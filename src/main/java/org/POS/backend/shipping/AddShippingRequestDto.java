package org.POS.backend.shipping;

public record AddShippingRequestDto(
        String name,
        String phoneNumber,
        String shippingAddress,
        String city,
        String barangay,
        String landmark,
        String note
) {
}
