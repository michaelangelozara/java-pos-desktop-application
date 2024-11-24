package org.POS.backend.inventory_adjustment;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;

import java.time.LocalDate;
import java.util.List;

public class InventoryAdjustmentMapper {

    private CodeGeneratorService codeGeneratorService;

    public InventoryAdjustmentMapper() {
        this.codeGeneratorService = new CodeGeneratorService();
    }

    public InventoryAdjustment toInventoryAdjustmentForSimpleProduct(AddInventoryAdjustmentDtoForSimpleProduct dto) {
        InventoryAdjustment inventoryAdjustment = new InventoryAdjustment();
        inventoryAdjustment.setReason(dto.reason());
        inventoryAdjustment.setCreatedDate(LocalDate.now());
        inventoryAdjustment.setQuantity(dto.quantity());
        inventoryAdjustment.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.INVENTORY_ADJUSTMENT_PREFIX));
        return inventoryAdjustment;
    }

    public InventoryAdjustment toInventoryAdjustmentForVariableProduct(AddInventoryAdjustmentDtoForVariableProduct dto) {
        InventoryAdjustment inventoryAdjustment = new InventoryAdjustment();
        inventoryAdjustment.setReason(dto.reason());
        inventoryAdjustment.setCreatedDate(LocalDate.now());
        inventoryAdjustment.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.INVENTORY_ADJUSTMENT_PREFIX));
        return inventoryAdjustment;
    }

    public InventoryAdjustmentResponseDto toInventoryAdjustmentResponseDto(InventoryAdjustment inventoryAdjustment) {
        return new InventoryAdjustmentResponseDto(
                inventoryAdjustment.getId(),
                inventoryAdjustment.getProduct(),
                inventoryAdjustment.getUser(),
                inventoryAdjustment.getCode(),
                inventoryAdjustment.getReason(),
                inventoryAdjustment.getCreatedDate(),
                inventoryAdjustment.getQuantity()
        );
    }

    public List<InventoryAdjustmentResponseDto> toInventoryAdjustmentResponseDtoList(List<InventoryAdjustment> inventoryAdjustments) {
        return inventoryAdjustments
                .stream()
                .map(this::toInventoryAdjustmentResponseDto)
                .toList();
    }

    public InventoryAdjustment toUpdatedInventoryAdjustment(InventoryAdjustment inventoryAdjustment, UpdateInventoryAdjustmentRequestDto dto) {
        inventoryAdjustment.setReason(dto.reason());
        inventoryAdjustment.setQuantity(dto.quantity());
        return inventoryAdjustment;
    }
}
