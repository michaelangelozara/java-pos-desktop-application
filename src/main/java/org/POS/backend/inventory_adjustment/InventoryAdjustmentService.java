package org.POS.backend.inventory_adjustment;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.CurrentUser;
import org.POS.backend.global_variable.UserActionPrefixes;
import org.POS.backend.product.ProductDAO;
import org.POS.backend.stock.Stock;
import org.POS.backend.stock.StockType;
import org.POS.backend.user.UserDAO;
import org.POS.backend.user_log.UserLog;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class InventoryAdjustmentService {

    private InventoryAdjustmentDAO inventoryAdjustmentDAO;

    private UserDAO userDAO;

    private ProductDAO productDAO;

    private InventoryAdjustmentMapper inventoryAdjustmentMapper;

    private CodeGeneratorService codeGeneratorService;

    public InventoryAdjustmentService() {
        this.inventoryAdjustmentDAO = new InventoryAdjustmentDAO();
        this.userDAO = new UserDAO();
        this.productDAO = new ProductDAO();
        this.inventoryAdjustmentMapper = new InventoryAdjustmentMapper();
        this.codeGeneratorService = new CodeGeneratorService();
    }

    public void addSimpleProduct(AddInventoryAdjustmentDtoForSimpleProduct dto) {
        var user = this.userDAO.getUserById(CurrentUser.id);
        var product = this.productDAO.getValidProductById(dto.productId());

        if (user != null && product != null) {
            var inventoryAdjustment = this.inventoryAdjustmentMapper.toInventoryAdjustmentForSimpleProduct(dto);
            user.addInventoryAdjustment(inventoryAdjustment);
            product.addInventoryAdjustment(inventoryAdjustment);

            Stock stock = new Stock();
            product.addStock(stock);
            stock.setRecentQuantity(product.getStock());
            stock.setUser(user);
            stock.setDate(LocalDate.now());
            stock.setStockInOrOut(dto.quantity());
            stock.setRecentQuantity(product.getStock());
            stock.setCode(product.getProductCode());

            if (dto.type().equals(InventoryAdjustmentType.INCREMENT)) {
                product.setStock(product.getStock() + dto.quantity());
                inventoryAdjustment.setType(InventoryAdjustmentType.INCREMENT);
                stock.setPrice(product.getSellingPrice().multiply(BigDecimal.valueOf(dto.quantity())));
                stock.setType(StockType.IN);
            } else {
                product.setStock(product.getStock() - dto.quantity());
                inventoryAdjustment.setType(InventoryAdjustmentType.DECREMENT);
                stock.setPrice(product.getSellingPrice().multiply(BigDecimal.valueOf(dto.quantity())));
                stock.setType(StockType.OUT);
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

            Stock stock = new Stock();
            product.addStock(stock);
            stock.setUser(user);
            stock.setDate(LocalDate.now());
            stock.setCode(product.getProductCode());

            // get the recent product's quantity
            int recentQuantity = 0;
            for(var attribute : product.getProductAttributes()){
                for(var variation : attribute.getProductVariations()){
                    recentQuantity += variation.getQuantity();
                }
            }
            stock.setRecentQuantity(recentQuantity);

            int quantityUpdated = 0;
            BigDecimal addedOrDeductedPrice = BigDecimal.ZERO;
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

                        // price for stock record
                        addedOrDeductedPrice = addedOrDeductedPrice.add(tempVar.getSrp().multiply(BigDecimal.valueOf(variationQuantityFromDto)));
                    }
                }
                stock.setType(StockType.IN);
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

                        // price for stock record
                        addedOrDeductedPrice = addedOrDeductedPrice.add(tempVar.getSrp().multiply(BigDecimal.valueOf(variationQuantityFromDto)));
                    }
                }
                stock.setType(StockType.OUT);
            }
            stock.setPrice(addedOrDeductedPrice);

            inventoryAdjustment.setQuantity(quantityUpdated);
            stock.setStockInOrOut(quantityUpdated);


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
