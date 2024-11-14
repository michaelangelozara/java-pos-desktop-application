package org.POS.backend.inventory_adjustment;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;

import java.time.LocalDate;
import java.util.List;

public class InventoryAdjustmentMapper {

    private CodeGeneratorService codeGeneratorService;

    public InventoryAdjustmentMapper(){
        this.codeGeneratorService = new CodeGeneratorService();
    }

    public InventoryAdjustment toInventoryAdjustment(AddInventoryAdjustmentDto dto){
        InventoryAdjustment inventoryAdjustment = new InventoryAdjustment();
        inventoryAdjustment.setNote(dto.note());
        inventoryAdjustment.setReason(dto.reason());
        inventoryAdjustment.setStatus(dto.status());
        inventoryAdjustment.setCreatedDate(LocalDate.now());
        inventoryAdjustment.setQuantity(dto.quantity());
        inventoryAdjustment.setType(dto.type());
        inventoryAdjustment.setCode(this.codeGeneratorService.generateProductCode(GlobalVariable.INVENTORY_ADJUSTMENT_PREFIX));
        return inventoryAdjustment;
    }

    public InventoryAdjustmentResponseDto toInventoryAdjustmentResponseDto(InventoryAdjustment inventoryAdjustment){
        return new InventoryAdjustmentResponseDto(
                inventoryAdjustment.getId(),
                inventoryAdjustment.getProduct(),
                inventoryAdjustment.getUser(),
                inventoryAdjustment.getCode(),
                inventoryAdjustment.getReason(),
                inventoryAdjustment.getCreatedDate(),
                inventoryAdjustment.getStatus(),
                inventoryAdjustment.getType(),
                inventoryAdjustment.getNote()
        );
    }

    public List<InventoryAdjustmentResponseDto> toInventoryAdjustmentResponseDtoList(List<InventoryAdjustment> inventoryAdjustments){
        return inventoryAdjustments
                .stream()
                .map(this::toInventoryAdjustmentResponseDto)
                .toList();
    }

    public InventoryAdjustment toUpdatedInventoryAdjustment(InventoryAdjustment inventoryAdjustment, UpdateInventoryAdjustmentRequestDto dto){
        inventoryAdjustment.setReason(dto.reason());
        inventoryAdjustment.setType(dto.type());
        inventoryAdjustment.setQuantity(dto.quantity());
        inventoryAdjustment.setNote(dto.note());
        inventoryAdjustment.setType(dto.type());
        inventoryAdjustment.setUpdatedAt(LocalDate.now());
        return inventoryAdjustment;
    }
}
