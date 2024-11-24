package org.POS.backend.inventory_adjustment;

import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.product.ProductDAO;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;

import java.time.LocalDate;
import java.util.List;

public class InventoryAdjustmentService {

    private InventoryAdjustmentDAO inventoryAdjustmentDAO;

    private UserDAO userDAO;

    private ProductDAO productDAO;

    private InventoryAdjustmentMapper inventoryAdjustmentMapper;

    public InventoryAdjustmentService() {
        this.inventoryAdjustmentDAO = new InventoryAdjustmentDAO();
        this.userDAO = new UserDAO();
        this.productDAO = new ProductDAO();
        this.inventoryAdjustmentMapper = new InventoryAdjustmentMapper();
    }

    public void addSimpleProduct(AddInventoryAdjustmentDtoForSimpleProduct dto) {
        var user = this.userDAO.getUserById(CurrentUser.id);
        var product = this.productDAO.getValidProductById(dto.productId());

        if (user != null && product != null) {
            var inventoryAdjustment = this.inventoryAdjustmentMapper.toInventoryAdjustmentForSimpleProduct(dto);
            user.addInventoryAdjustment(inventoryAdjustment);
            product.addInventoryAdjustment(inventoryAdjustment);

            if (dto.type().equals(InventoryAdjustmentType.INCREMENT)) {
                product.setStock(product.getStock() + dto.quantity());
                inventoryAdjustment.setType(InventoryAdjustmentType.INCREMENT);
            } else {
                product.setStock(product.getStock() - dto.quantity());
                inventoryAdjustment.setType(InventoryAdjustmentType.DECREMENT);

            }

            UserLog userLog = new UserLog();
            userLog.setCode(inventoryAdjustment.getCode());
            userLog.setDate(LocalDate.now());
            userLog.setAction(UserActionPrefixes.INVENTORY_ADJUSTMENT_ADD_ACTION_LOG_PREFIX);
            user.addUserLog(userLog);

            this.inventoryAdjustmentDAO.add(inventoryAdjustment, product, userLog);
        }
    }

    public void addVariableProduct(AddInventoryAdjustmentDtoForVariableProduct dto) {
        var user = this.userDAO.getUserById(CurrentUser.id);
        var product = this.productDAO.getValidProductById(dto.productId());

        if (user != null && product != null) {
            var inventoryAdjustment = this.inventoryAdjustmentMapper.toInventoryAdjustmentForVariableProduct(dto);
            user.addInventoryAdjustment(inventoryAdjustment);
            product.addInventoryAdjustment(inventoryAdjustment);

            UserLog userLog = new UserLog();

            int quantityUpdated = 0;
            if (dto.type().equals(InventoryAdjustmentType.INCREMENT)) {
                inventoryAdjustment.setType(InventoryAdjustmentType.INCREMENT);
                userLog.setAction(UserActionPrefixes.INVENTORY_ADJUSTMENT_ADD_ACTION_LOG_PREFIX);
                for(int i = 0; i < product.getProductAttributes().size(); i++){
                    for(int j = 0; j < product.getProductAttributes().get(i).getProductVariations().size(); j++){
                        // quantity coming from the dto
                        int variationQuantityFromDto = dto.productAttributes().get(i).getProductVariations().get(j).getQuantity();

                        var tempVar = product.getProductAttributes().get(i).getProductVariations().get(j);
                        tempVar.setQuantity(tempVar.getQuantity() + variationQuantityFromDto);
                        quantityUpdated += variationQuantityFromDto;
                    }
                }
            } else {
                inventoryAdjustment.setType(InventoryAdjustmentType.DECREMENT);
                userLog.setAction(UserActionPrefixes.INVENTORY_ADJUSTMENT_DEDUCT_ACTION_LOG_PREFIX);
                for(int i = 0; i < product.getProductAttributes().size(); i++){
                    for(int j = 0; j < product.getProductAttributes().get(i).getProductVariations().size(); j++){
                        // quantity coming from the dto
                        int variationQuantityFromDto = dto.productAttributes().get(i).getProductVariations().get(j).getQuantity();
                        var tempVar = product.getProductAttributes().get(i).getProductVariations().get(j);
                        tempVar.setQuantity(tempVar.getQuantity() - variationQuantityFromDto);
                        quantityUpdated += variationQuantityFromDto;
                    }
                }
            }

            inventoryAdjustment.setQuantity(quantityUpdated);

            userLog.setCode(inventoryAdjustment.getCode());
            userLog.setDate(LocalDate.now());
            user.addUserLog(userLog);

            this.inventoryAdjustmentDAO.add(inventoryAdjustment, product, userLog);
        }
    }

    public void update(UpdateInventoryAdjustmentRequestDto dto) {
        var user = this.userDAO.getUserById(CurrentUser.id);
        var inventoryAdjustment = this.inventoryAdjustmentDAO.getValidInventoryAdjustmentById(dto.id());

        if (user != null && inventoryAdjustment != null) {
            boolean isInventoryAdjustmentInTheUserAlready = false;
            for (var tempInventoryAdjustment : user.getInventoryAdjustments()) {
                // check if the inventory adjustment is in the user already
                if (inventoryAdjustment.getId().equals(tempInventoryAdjustment.getId())) {
                    isInventoryAdjustmentInTheUserAlready = true;
                    break;
                }
            }

            // if the inventoryAdjustment is not the user's inventory adjustment yet then add
            if (!isInventoryAdjustmentInTheUserAlready) {
                user.addInventoryAdjustment(inventoryAdjustment);
            }

            this.inventoryAdjustmentMapper.toUpdatedInventoryAdjustment(inventoryAdjustment, dto);
            var product = inventoryAdjustment.getProduct();

            if (dto.type().equals(InventoryAdjustmentType.INCREMENT)) {
                product.setStock(product.getStock() + dto.quantity());
            } else {
                product.setStock(product.getStock() - dto.quantity());
            }

            UserLog userLog = new UserLog();
            userLog.setCode(inventoryAdjustment.getCode());
            userLog.setDate(LocalDate.now());
            userLog.setAction(UserActionPrefixes.INVENTORY_ADJUSTMENT_DEDUCT_ACTION_LOG_PREFIX);
            user.addUserLog(userLog);

            this.inventoryAdjustmentDAO.update(inventoryAdjustment, user, product);
        }
    }

    public List<InventoryAdjustmentResponseDto> getAllValidInventoryAdjustment(int number) {
        return this.inventoryAdjustmentMapper.toInventoryAdjustmentResponseDtoList(
                this.inventoryAdjustmentDAO.getAllValidInventoryAdjustment(number)
        );
    }

    public InventoryAdjustmentResponseDto getValidInventoryAdjustmentById(int id) {
        return this.inventoryAdjustmentMapper.toInventoryAdjustmentResponseDto(this.inventoryAdjustmentDAO.getValidInventoryAdjustmentById(id));
    }

    public List<InventoryAdjustmentResponseDto> getAllValidInventoryAdjustment() {
        return this.inventoryAdjustmentMapper.toInventoryAdjustmentResponseDtoList(this.inventoryAdjustmentDAO.getAllValidInventoryAdjustment());
    }

    public List<InventoryAdjustmentResponseDto> getAllValidInventoryAdjustmentByQuery(String query) {
        return this.inventoryAdjustmentMapper.toInventoryAdjustmentResponseDtoList(this.inventoryAdjustmentDAO.getAllValidInventoryAdjustmentByQuery(query));
    }
}
